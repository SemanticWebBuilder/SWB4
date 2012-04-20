/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogAddFile.java
 *
 * Created on 19/04/2012, 11:10:51 AM
 */
package applets.ftp;

import applets.commons.WBTreeNode;
import applets.commons.WBXMLParser;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author victor.lorenzana
 */
public class DialogAddFile extends javax.swing.JDialog
{

    private final java.io.File dirlocal;
    private final ftpPanel ftp;
    private final Directory dir;

    /** Creates new form DialogAddFile */
    public DialogAddFile(java.awt.Frame parent, java.io.File dirlocal, ftpPanel ftp, Directory dir)
    {
        super(parent, true);
        initComponents();
        this.dirlocal = dirlocal;
        this.dir = dir;
        java.io.File[] files =
        {
            dirlocal
        };
        this.ftp = ftp;
        this.setTitle(dirlocal.getName() + " ...");
        int count = getCount(files);
        this.jProgressBar2.setMaximum(count);
        //setLocation(400, 300);
        //setSize(200, 50);
        setLocationRelativeTo(null);
    }

    public void addDir()
    {
        WorkerUploadDir w = new WorkerUploadDir(this, dir, dirlocal);
        w.start();
        this.setVisible(true);
    }

    public void downloadDir()
    {
        ArrayList<java.io.File> files = new ArrayList<java.io.File>();
        ArrayList<String> webfiles = new ArrayList<String>();
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>downloadDir</cmd><path>" + dir.getDirectory() + "</path></req>";

        String respxml = ftp.getData(xml);

        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);

        try
        {
            if (enode != null && enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
            {
                WBTreeNode _dir = enode.getFirstNode().getFirstNode();
                if (_dir.getName().equals("dir"))
                {
                    Iterator it = _dir.getNodes().iterator();
                    while (it.hasNext())
                    {
                        _dir = (WBTreeNode) it.next();
                        if (_dir != null && _dir.getName().equals("dir"))
                        {
                            String newPath = dirlocal.getAbsolutePath() + "/" + dir.getName() + "/" + _dir.getAttribute("relpath");
                            newPath = newPath.replace("\\", "/");
                            newPath = newPath.replace("//", "/");
                            java.io.File newdir = new java.io.File(newPath);

                            if (!newdir.exists())
                            {
                                newdir.mkdirs();
                            }
                        }
                        else if (_dir != null && _dir.getName().equals("file"))
                        {
                            String newPath = dirlocal.getAbsolutePath() + "/" + dir.getName() + "/" + _dir.getAttribute("relpath");
                            newPath = newPath.replace("\\", "/");
                            newPath = newPath.replace("//", "/");
                            java.io.File newfile = new java.io.File(newPath);

                            java.io.File parent = newfile.getParentFile();

                            if (!parent.exists())
                            {
                                parent.mkdirs();
                            }
                            files.add(newfile);

                            webfiles.add(_dir.getAttribute("path"));
                        }
                    }
                }


            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        jProgressBar2.setMaximum(files.size());
        WorkerDownloadDir w = new WorkerDownloadDir(this, files, webfiles,ftp.urldownload);
        w.start();
        this.setVisible(true);

    }

    private int getCount(java.io.File[] files)
    {

        int count = files.length;
        for (java.io.File file : files)
        {
            java.io.File[] childs = file.listFiles();
            if (childs != null)
            {
                count += getCount(childs);
            }
        }
        return count;
    }

    private class WorkerUploadDir extends Thread
    {

        DialogAddFile dialog;
        Directory dir;
        java.io.File dirlocal;

        public WorkerUploadDir(DialogAddFile dialog, Directory dir, java.io.File dirlocal)
        {
            this.dialog = dialog;
            this.dir = dir;
            this.dirlocal = dirlocal;
        }

        @Override
        public void run()
        {
            boolean siAll = false;
            try
            {
                addDir(dirlocal, dir.getDirectory(), siAll);
                ftp.loadDirectories(dir);
                ftp.updateTreeUI();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            setVisible(false);
            dispose();
        }

        private boolean addDir(java.io.File dir, String path, boolean siAll) throws Exception
        {
            String pathdir = path + "/" + dir.getName();
            dialog.setText(dir.getName() + " ...");
            siAll = ftp.createDir(pathdir, siAll, dialog);
            dialog.incrementProgressBar();
            java.io.File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++)
            {
                java.io.File file = files[i];
                String pathchild = pathdir + "/" + file.getName();
                if (file.isDirectory())
                {
                    siAll = addDir(file, pathdir, siAll);
                }
                else
                {
                    dialog.setText(file.getName() + " ...");
                    siAll = ftp.createFile(file, pathchild, siAll, dialog);
                    dialog.incrementProgressBar();
                }
            }
            return siAll;
        }
    }

    private class WorkerDownloadDir extends Thread
    {

        DialogAddFile dialog;
        ArrayList<java.io.File> files;
        ArrayList<String> webfiles;
        URL urldownload;
        public WorkerDownloadDir(DialogAddFile dialog, ArrayList<java.io.File> files, ArrayList<String> webfiles,URL urldownload)
        {
            this.dialog = dialog;
            this.files = files;
            this.webfiles = webfiles;
            this.urldownload=urldownload;
        }

        @Override
        public void run()
        {
            try
            {                
                boolean siAll = false;
                for (int i = 0; i < files.size(); i++)
                {
                    boolean doit = false;
                    String path = webfiles.get(i);

                    java.io.File filelocal = files.get(i);
                    setText(filelocal.getName() + " ...");
                    if (filelocal.exists())
                    {
                        if (!siAll)
                        {
                            int selection = JOptionPane.showOptionDialog(
                                    dialog,
                                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", ftp.locale).getString("msg_file_overwrite1")
                                    + " '" + filelocal.getParentFile().getName() + "' "
                                    + java.util.ResourceBundle.getBundle("applets/ftp/ftp", ftp.locale).getString("msg_file_overwrite2")
                                    + " '" + filelocal.getName() + "'.\r\n"
                                    + java.util.ResourceBundle.getBundle("applets/ftp/ftp", ftp.locale).getString("msg_file_overwrite3"),
                                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", ftp.locale).getString("title"),
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    ftp.choices,
                                    ftp.choices[0]);
                            switch (selection)
                            {
                                case 0: //Si
                                    siAll = false;
                                    doit = true;
                                    break;
                                case 1: //Si All
                                    siAll = true;
                                    doit = true;
                                    break;
                                case 2: // No
                                    siAll = false;
                                    doit = false;
                                    break;
                                case 3: // Cancelar
                                    return;

                            }
                        }
                        else
                        {
                            doit = true;
                        }
                    }
                    else
                    {
                        doit = true;
                    }

                    if (doit)
                    {
                        try
                        {

                            FileOutputStream out = new FileOutputStream(filelocal);
                            URLConnection con = urldownload.openConnection();
                            con.setUseCaches(false);
                            if (ftp.jsess != null)
                            {
                                con.setRequestProperty("Cookie", "JSESSIONID=" + ftp.jsess);
                            }
                            con.addRequestProperty("PATHFILEWB", path);
                            con.setDoInput(true);
                            InputStream in = con.getInputStream();
                            byte[] bcont = new byte[8192];
                            int ret = in.read(bcont);
                            while (ret != -1)
                            {
                                out.write(bcont, 0, ret);
                                ret = in.read(bcont);
                            }
                            in.close();
                            out.close();
                        }
                        catch (Exception e)
                        {
                            JOptionPane.showMessageDialog(null, e.getMessage(), java.util.ResourceBundle.getBundle("applets/ftp/ftp", ftp.locale).getString("title"), JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    incrementProgressBar();
                }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage(), java.util.ResourceBundle.getBundle("applets/ftp/ftp", ftp.locale).getString("title"), JOptionPane.ERROR_MESSAGE);
            }
            setVisible(false);
            dispose();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jProgressBar2 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setResizable(false);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(50, 30));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setPreferredSize(new java.awt.Dimension(282, 20));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jProgressBar2.setPreferredSize(new java.awt.Dimension(146, 20));
        jPanel2.add(jProgressBar2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosed
    {//GEN-HEADEREND:event_formWindowClosed
        //setVisible(false);
        //dispose();
    }//GEN-LAST:event_formWindowClosed

    private void incrementProgressBar()
    {
        int value = this.jProgressBar2.getValue();
        value++;
        this.jProgressBar2.setValue(value);
        this.repaint();
    }

    private void setText(String text)
    {
        this.jLabel1.setText(text);
        this.repaint();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar2;
    // End of variables declaration//GEN-END:variables
}
