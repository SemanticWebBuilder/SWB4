/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ftp.java
 *
 * Created on 10/08/2010, 03:54:54 PM
 */
package applets.ftp;

import applets.commons.WBTreeNode;
import applets.commons.WBXMLParser;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author victor.lorenzana
 */
public class ftp extends javax.swing.JDialog implements FileListener,ListSelectionListener
{
    
    public static java.io.File pathDir;
    private URL uploadpath, downloadpath;
    private static String jsess = "";
    private static URL url = null;
    private String[] choices = new String[4];
    private Locale locale=Locale.getDefault();        

    /** Creates new form ftp */
    public ftp(Locale locale,String jsess,URL uploadpath,URL downloadpath,String pathInit,URL urlgateway)
    {
        super((Frame) null, true);
        locale=Locale.getDefault();
        if(locale!=null)
        {
            try
            {

                this.locale=locale;
            }
            catch(Exception e)
            {
                e.printStackTrace(System.out);
            }
        }
        try
        {
            jFileChooser1.setLocale(this.locale);
        }
        catch(Exception e)
        {
            //e.printStackTrace(System.out);
        }
        ftp.url=urlgateway;

        choices[0]=java.util.ResourceBundle.getBundle("applets/ftp/ftp",locale).getString("si");
        choices[1]=java.util.ResourceBundle.getBundle("applets/ftp/ftp",locale).getString("si_todo");
        choices[2]=java.util.ResourceBundle.getBundle("applets/ftp/ftp",locale).getString("no");
        choices[3]=java.util.ResourceBundle.getBundle("applets/ftp/ftp",locale).getString("cancel");
        initComponents();
        this.setLocationRelativeTo(null);                
        jTreeDirs.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("")));
        this.jTableFiles.setDefaultRenderer(JLabel.class, new TableFileRender());
        this.jTableFiles.getSelectionModel().addListSelectionListener(this);
        ftp.jsess=jsess;
        this.uploadpath=uploadpath;
        this.downloadpath=downloadpath;        
        this.setJMenuBar(this.jMenuBar1);
        this.jTreeDirs.setCellRenderer(new DirectoryRenderer(this.jTableFiles));
        jTableFileModel filemodel=new jTableFileModel(this.jTableFiles,this.locale);
        this.jTableFiles.setModel(filemodel);
        this.setTitle("Documentos del servidor");
        loadDirectories();
        ArrayList<TreeNode> nodes=new ArrayList<TreeNode>();
        if(pathInit!=null && !pathInit.equals(""))
        {
            Directory currentDir=(Directory)this.jTreeDirs.getModel().getRoot();
            nodes.add(currentDir);
            StringTokenizer st=new StringTokenizer(pathInit,"/");
            while(st.hasMoreTokens())
            {                
                String dir=st.nextToken();
                if(dir!=null && !dir.equals(""))
                {                    
                    for(int i=0;i<currentDir.getChildCount();i++)
                    {
                        Directory dirTest=(Directory)currentDir.getChildAt(i);
                        
                        if(dirTest.getName().equals(dir))
                        {
                            // expand the node
                            nodes.add(dirTest);
                            TreePath treepath=new TreePath(nodes.toArray());
                            jTreeDirs.expandPath(treepath);                            
                            jTreeDirs.updateUI();
                            currentDir=dirTest;
                            break;
                        }
                    }
                }
            }
            TreePath treepath=new TreePath(nodes.toArray());
            jTreeDirs.setSelectionPath(treepath);
            jTreeDirs.scrollPathToVisible(treepath);
        }
    }
    public void valueChanged(ListSelectionEvent e) {
      Modificated();
    }
    private void downloadFile()
    {
        if (this.jTableFiles.getSelectedRowCount() > 0)
        {
            jTableFileModel filemodel = (jTableFileModel) this.jTableFiles.getModel();
            this.jFileChooser1 = new JFileChooser();
            if (pathDir != null)
                this.jFileChooser1.setCurrentDirectory(pathDir);
            this.jFileChooser1.setDialogTitle(java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("download"));
            this.jFileChooser1.setMultiSelectionEnabled(false);
            this.jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int ret = this.jFileChooser1.showSaveDialog(this);
            ftp.pathDir = this.jFileChooser1.getCurrentDirectory();
            if (ret == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    //URL urldownload = new URL(basepath, this.downloadpath);
                    URL urldownload = downloadpath;
                    java.io.File dir = this.jFileChooser1.getSelectedFile();
                    int[] irows = this.jTableFiles.getSelectedRows();

                    for (int i = 0; i < irows.length; i++)
                    {
                        applets.ftp.File file = filemodel.getFile(irows[i]);
                        String path = dir.getAbsolutePath() + "/" + file.getName();
                        java.io.File flocal = new java.io.File(path);
                        if (flocal.exists())
                        {
                            int status = JOptionPane.showConfirmDialog(
                                    this, // The parent frame
                                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_file_download1")
                                    + " " + flocal.getName()
                                    + " " + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_file_download2")
                                    + "\r\n" + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("sobreEscribir"),
                                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                            if (status == JOptionPane.NO_OPTION)
                                continue;
                        }
                        //FileDownload fdown=new FileDownload(file.getPath(), this.jProgressBar1, flocal, this.jsess, urldownload);
                        FDownload fdown = new FDownload(null, false, file.getPath(), flocal, ftp.jsess, urldownload, locale);
                        fdown.show();
                        fdown.setLocation(400, 300);
                        fdown.setSize(200, 50);
                        fdown.getFile();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getData(String xml)
    {

        StringBuilder ret = new StringBuilder();
        try
        {

            URLConnection urlconn = url.openConnection();
            urlconn.setUseCaches(false);
            if (jsess != null)
                urlconn.setRequestProperty("Cookie", "JSESSIONID=" + jsess);
            urlconn.setRequestProperty("Content-Type", "application/xml");
            urlconn.setDoOutput(true);
            PrintWriter pout = new PrintWriter(urlconn.getOutputStream());
            pout.println(xml);
            pout.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                ret.append(inputLine);
                ret.append("\n");
            }
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret.toString();
    }

    public boolean exists(String path)
    {
        String newpath = path;
        try
        {
            newpath = WBXMLParser.encode(path, "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>exists</cmd><path>" + newpath + "</path></req>";
        String respxml = ftp.getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("exists"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                    return true;
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    public boolean createDir(String path, boolean siAll) throws Exception
    {
        String newpath = path;
        try
        {
            newpath = WBXMLParser.encode(path, "UTF-8");
        }
        catch (Exception e)
        {
        }
        boolean exists = exists(path);
        if (exists)
        {
            if (!siAll)
            {
                java.io.File carpeta = new java.io.File(path);
                int selection = JOptionPane.showOptionDialog(
                        this,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("msg_carpeta_existe")
                        + " '" + carpeta.getName()
                        + "'.\r\n"
                        + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("msg_carpeta_sobreescribe"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        choices,
                        choices[0]);
                switch (selection)
                {
                    case 0: //Si
                        siAll = false;
                        break;
                    case 1: //Si All
                        siAll = true;
                        break;
                    case 2:
                        return siAll;
                    case 3:
                        throw new Exception(this.choices[selection]);

                }
            }
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>createDir</cmd><path>" + newpath + "</path></req>";
        String respxml = ftp.getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("create"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return siAll;
    }

    private void createDir()
    {
        if (this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {
            Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
            createDir(dir);
        }
    }

    private void createDir(Directory dir)
    {
        this.jTreeDirs.expandPath(this.jTreeDirs.getSelectionPath());
        String name = JOptionPane.showInputDialog(
                this, // The parent frame
                java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("nombre_dir"));
        name = name.trim();
        if (name.trim().equals(""))
        {
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no_nombre"),
                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < dir.getChildCount(); i++)
        {
            Directory child = (Directory) dir.getChildAt(i);
            if (child.getName().equalsIgnoreCase(name))
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_nombre"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        String path = dir.getDirectory() + "/" + name;
        String pathdir = path;
        try
        {
            pathdir = WBXMLParser.encode(pathdir, "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>createDir</cmd><path>" + pathdir + "</path></req>";
        String respxml = ftp.getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("create"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                    Directory child = new Directory(name, path);
                    dir.add(child);
                    TreePath newpath = this.jTreeDirs.getSelectionPath().pathByAddingChild(child);
                    this.jTreeDirs.setSelectionPath(newpath);
                    this.jTreeDirs.updateUI();
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);

            }
        }


    }

    private boolean addDir(java.io.File dir, String path, boolean siAll) throws Exception
    {
        String pathdir = path + "/" + dir.getName();
        siAll = this.createDir(pathdir, siAll);
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
                siAll = this.createFile(file, pathchild, siAll);
            }
        }
        return siAll;
    }

    public boolean createFile(java.io.File filelocal, String path, boolean siAll) throws Exception
    {
        String newpath = path;
        try
        {
            newpath = WBXMLParser.encode(path, "UTF-8");
        }
        catch (Exception e)
        {
        }
        if (exists(path))
        {
            if (!siAll)
            {

                int selection = JOptionPane.showOptionDialog(
                        this,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("msg_file_overwrite1")
                        + " '" + filelocal.getParentFile().getName() + "' "
                        + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("msg_file_overwrite2")
                        + " '" + filelocal.getName() + "'.\r\n"
                        + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("msg_file_overwrite3"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        choices,
                        choices[0]);
                switch (selection)
                {
                    case 0: //Si
                        siAll = false;
                        break;
                    case 1: //Si All
                        siAll = true;
                        break;
                    case 2:
                        return siAll;
                    case 3:
                        throw new Exception(this.choices[selection]);

                }
            }
        }
        try
        {
            //URL urlupload = new URL(basepath, this.uploadpath);
            URL urlupload = uploadpath;
            FileUpload fup = new FileUpload(ftp.jsess, urlupload, locale);
            fup.sendFile(path, filelocal);
        }
        catch (Exception e)
        {
        }
        return siAll;
    }

    private void addDir()
    {
        if (this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {
            try
            {

                //URL urlupload = new URL(basepath, this.uploadpath);
                URL urlupload = uploadpath;
                Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
                this.jFileChooser1 = new JFileChooser();
                if (pathDir != null)
                    this.jFileChooser1.setCurrentDirectory(pathDir);
                this.jFileChooser1.setDialogTitle(java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("add_dir"));
                this.jFileChooser1.setMultiSelectionEnabled(false);
                this.jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = this.jFileChooser1.showOpenDialog(this);
                pathDir = this.jFileChooser1.getCurrentDirectory();
                if (ret == JFileChooser.APPROVE_OPTION)
                {
                    java.io.File dirlocal = this.jFileChooser1.getSelectedFile();
                    boolean siAll = false;
                    try
                    {
                        addDir(dirlocal, dir.getDirectory(), siAll);
                        loadDirectories(dir);
                        this.jTreeDirs.updateUI();
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void addFile()
    {
        if (this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {

            try
            {

                //URL urlupload=new URL(getCodeBase().getProtocol(),getCodeBase().getHost(),getCodeBase().getPort(),this.uploadpath);
                URL urlupload = uploadpath;
                Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
                this.jFileChooser1 = new JFileChooser();
                if (pathDir != null)
                    this.jFileChooser1.setCurrentDirectory(pathDir);
                this.jFileChooser1.setMultiSelectionEnabled(true);
                this.jFileChooser1.setDialogTitle(java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("add_files"));
                this.jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int ret = this.jFileChooser1.showOpenDialog(this);
                pathDir = this.jFileChooser1.getCurrentDirectory();
                if (ret == JFileChooser.APPROVE_OPTION)
                {
                    ArrayList<java.io.File> vfiles = new ArrayList<java.io.File>();
                    applets.ftp.File fileexists = null;
                    java.io.File[] files = this.jFileChooser1.getSelectedFiles();
                    boolean siAll = false;
                    for (int i = 0; i < files.length; i++)
                    {
                        java.io.File f = files[i];
                        jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
                        boolean existe = false;
                        for (int j = 0; j < model.getRowCount(); j++)
                        {
                            applets.ftp.File filecat = model.getFile(j);
                            if (filecat.getName().equalsIgnoreCase(f.getName()))
                            {
                                existe = true;
                                fileexists = filecat;
                            }
                        }
                        if (existe)
                        {
                            if (siAll)
                            {
                                if (fileexists != null)
                                    model.removeFile(fileexists);
                            }
                            else
                            {
                                int selection = JOptionPane.showOptionDialog(
                                        this,
                                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_nombrefile") + " '" + fileexists.getName() + "' " + "\r\n" + java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("sobreEscribir"),
                                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        choices,
                                        choices[0]);
                                switch (selection)
                                {
                                    case 0: //Si
                                        siAll = false;
                                        if (fileexists != null)
                                            model.removeFile(fileexists);
                                        break;
                                    case 1: //Si All
                                        siAll = true;
                                        if (fileexists != null)
                                            model.removeFile(fileexists);
                                        break;
                                    case 2: // No
                                        continue;
                                    case 3:  //Cancel
                                        return;
                                }
                            }



                        }
                        vfiles.add(f);
                    }
                    this.jFileChooser1.updateUI();
                    final ArrayList<java.io.File> osendfiles = vfiles;
                    final Directory odir = dir;
                    final URL urlup = urlupload;

                    Runnable doWorkRunnable = new Runnable()
                    {

                        public void run()
                        {
                            SendFiles(osendfiles, odir, urlup);
                        }
                    };
                    SwingUtilities.invokeLater(doWorkRunnable);

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    private void SendFiles(ArrayList<java.io.File> files, Directory dir, URL urlupload)
    {
        jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
        Iterator it = files.iterator();
        while (it.hasNext())
        {
            java.io.File f = (java.io.File) it.next();
            String path = dir.getDirectory() + "/" + f.getName();
            FUpload fup = new FUpload(null, false, jsess, urlupload, locale);
            ftp.Worker wr = new ftp.Worker(f, path, fup, model, dir);
            SwingUtilities.invokeLater(wr);
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

        jPopupMenuDir = new javax.swing.JPopupMenu();
        jMenuItemNewFolder = new javax.swing.JMenuItem();
        jMenuDirAdd = new javax.swing.JMenu();
        jMenuItemDirAddFile = new javax.swing.JMenuItem();
        jMenuItemDirAddFolder = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemDirRename = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItemDirDelete = new javax.swing.JMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemCrearDirectorio = new javax.swing.JMenuItem();
        jMenuAdd = new javax.swing.JMenu();
        jMenuItemAddFile = new javax.swing.JMenuItem();
        jMenuItemAddFolder = new javax.swing.JMenuItem();
        jMenuBorrar = new javax.swing.JMenu();
        jMenuItemBorrarDir = new javax.swing.JMenuItem();
        jMenuItemBorrarFile = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuTools = new javax.swing.JMenu();
        jMenuItemDownload = new javax.swing.JMenuItem();
        jMenuRename = new javax.swing.JMenu();
        jMenuItemRenameFolder = new javax.swing.JMenuItem();
        jMenuItemRenameFile = new javax.swing.JMenuItem();
        jPopupMenuFile = new javax.swing.JPopupMenu();
        jMenuFileAdd = new javax.swing.JMenu();
        jMenuItemFileAddFile = new javax.swing.JMenuItem();
        jMenuItemFileAddDir = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuItemFileDownload = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        jMenuItemFileRename = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        jMenuItemFileDelete = new javax.swing.JMenuItem();
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanelToolbar = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonAddFile = new javax.swing.JButton();
        jButtonaddFolder = new javax.swing.JButton();
        jButtonNewFolder = new javax.swing.JButton();
        jButtonDownload = new javax.swing.JButton();
        jPanelContent = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeDirs = new javax.swing.JTree();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableFiles = new javax.swing.JTable();
        jPanelProgressBar = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("applets/ftp/ftp",locale); // NOI18N
        jMenuItemNewFolder.setText(bundle.getString("new_folder")); // NOI18N
        jMenuItemNewFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewFolderActionPerformed(evt);
            }
        });
        jPopupMenuDir.add(jMenuItemNewFolder);

        jMenuDirAdd.setText(bundle.getString("add")); // NOI18N

        jMenuItemDirAddFile.setText(bundle.getString("file")); // NOI18N
        jMenuItemDirAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDirAddFileActionPerformed(evt);
            }
        });
        jMenuDirAdd.add(jMenuItemDirAddFile);

        jMenuItemDirAddFolder.setText(bundle.getString("folder")); // NOI18N
        jMenuItemDirAddFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDirAddFolderActionPerformed(evt);
            }
        });
        jMenuDirAdd.add(jMenuItemDirAddFolder);

        jPopupMenuDir.add(jMenuDirAdd);
        jPopupMenuDir.add(jSeparator1);

        jMenuItemDirRename.setText(bundle.getString("rename")); // NOI18N
        jMenuItemDirRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDirRenameActionPerformed(evt);
            }
        });
        jPopupMenuDir.add(jMenuItemDirRename);
        jPopupMenuDir.add(jSeparator2);

        jMenuItemDirDelete.setText(bundle.getString("del")); // NOI18N
        jMenuItemDirDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDirDeleteActionPerformed(evt);
            }
        });
        jPopupMenuDir.add(jMenuItemDirDelete);

        jMenuBar1.setBackground(new java.awt.Color(213, 227, 248));

        jMenuFile.setBackground(new java.awt.Color(213, 227, 248));
        jMenuFile.setText(bundle.getString("file")); // NOI18N

        jMenuItemCrearDirectorio.setText(bundle.getString("new_folder")); // NOI18N
        jMenuItemCrearDirectorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCrearDirectorioActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemCrearDirectorio);

        jMenuAdd.setText(bundle.getString("add")); // NOI18N

        jMenuItemAddFile.setText(bundle.getString("file")); // NOI18N
        jMenuItemAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddFileActionPerformed(evt);
            }
        });
        jMenuAdd.add(jMenuItemAddFile);

        jMenuItemAddFolder.setText(bundle.getString("folder")); // NOI18N
        jMenuItemAddFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddFolderActionPerformed(evt);
            }
        });
        jMenuAdd.add(jMenuItemAddFolder);

        jMenuFile.add(jMenuAdd);

        jMenuBorrar.setText(bundle.getString("del")); // NOI18N

        jMenuItemBorrarDir.setText(bundle.getString("folder")); // NOI18N
        jMenuItemBorrarDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBorrarDirActionPerformed(evt);
            }
        });
        jMenuBorrar.add(jMenuItemBorrarDir);

        jMenuItemBorrarFile.setText(bundle.getString("file")); // NOI18N
        jMenuItemBorrarFile.setEnabled(false);
        jMenuItemBorrarFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBorrarFileActionPerformed(evt);
            }
        });
        jMenuBorrar.add(jMenuItemBorrarFile);

        jMenuFile.add(jMenuBorrar);
        jMenuFile.add(jSeparator6);

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("applets/ftp/ftp"); // NOI18N
        jMenuItemExit.setText(bundle1.getString("exit")); // NOI18N
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar1.add(jMenuFile);

        jMenuTools.setBackground(new java.awt.Color(213, 227, 248));
        jMenuTools.setText(bundle.getString("tools")); // NOI18N

        jMenuItemDownload.setText(bundle.getString("download")); // NOI18N
        jMenuItemDownload.setEnabled(false);
        jMenuItemDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDownloadActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemDownload);

        jMenuRename.setText(bundle.getString("rename")); // NOI18N

        jMenuItemRenameFolder.setText(bundle.getString("folder")); // NOI18N
        jMenuItemRenameFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRenameFolderActionPerformed(evt);
            }
        });
        jMenuRename.add(jMenuItemRenameFolder);

        jMenuItemRenameFile.setText(bundle.getString("file")); // NOI18N
        jMenuItemRenameFile.setEnabled(false);
        jMenuItemRenameFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRenameFileActionPerformed(evt);
            }
        });
        jMenuRename.add(jMenuItemRenameFile);

        jMenuTools.add(jMenuRename);

        jMenuBar1.add(jMenuTools);

        jMenuFileAdd.setText(bundle.getString("add")); // NOI18N

        jMenuItemFileAddFile.setText(bundle.getString("file")); // NOI18N
        jMenuItemFileAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileAddFileActionPerformed(evt);
            }
        });
        jMenuFileAdd.add(jMenuItemFileAddFile);

        jMenuItemFileAddDir.setText(bundle.getString("folder")); // NOI18N
        jMenuItemFileAddDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileAddDirActionPerformed(evt);
            }
        });
        jMenuFileAdd.add(jMenuItemFileAddDir);

        jPopupMenuFile.add(jMenuFileAdd);
        jPopupMenuFile.add(jSeparator3);

        jMenuItemFileDownload.setText(bundle.getString("download")); // NOI18N
        jMenuItemFileDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileDownloadActionPerformed(evt);
            }
        });
        jPopupMenuFile.add(jMenuItemFileDownload);
        jPopupMenuFile.add(jSeparator5);

        jMenuItemFileRename.setText(bundle.getString("rename")); // NOI18N
        jMenuItemFileRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileRenameActionPerformed(evt);
            }
        });
        jPopupMenuFile.add(jMenuItemFileRename);
        jPopupMenuFile.add(jSeparator4);

        jMenuItemFileDelete.setText(bundle.getString("del")); // NOI18N
        jMenuItemFileDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFileDeleteActionPerformed(evt);
            }
        });
        jPopupMenuFile.add(jMenuItemFileDelete);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelToolbar.setBackground(new java.awt.Color(225, 235, 251));
        jPanelToolbar.setPreferredSize(new java.awt.Dimension(10, 30));
        jPanelToolbar.setLayout(new java.awt.BorderLayout());

        jToolBar1.setBackground(new java.awt.Color(225, 235, 251));
        jToolBar1.setFloatable(false);

        jButtonAddFile.setBackground(new java.awt.Color(213, 227, 255));
        jButtonAddFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/archivonuevo2.gif"))); // NOI18N
        jButtonAddFile.setToolTipText(bundle.getString("add_files")); // NOI18N
        jButtonAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddFileActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonAddFile);

        jButtonaddFolder.setBackground(new java.awt.Color(213, 227, 255));
        jButtonaddFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/ftpupfolder.gif"))); // NOI18N
        jButtonaddFolder.setToolTipText(bundle.getString("menu_add_folder")); // NOI18N
        jButtonaddFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonaddFolderActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonaddFolder);

        jButtonNewFolder.setBackground(new java.awt.Color(213, 227, 255));
        jButtonNewFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/nuevofolder2.gif"))); // NOI18N
        jButtonNewFolder.setToolTipText(bundle.getString("new_folder")); // NOI18N
        jButtonNewFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewFolderActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonNewFolder);

        jButtonDownload.setBackground(new java.awt.Color(213, 227, 255));
        jButtonDownload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/ftp/images/archivodown2.gif"))); // NOI18N
        jButtonDownload.setToolTipText(bundle.getString("download")); // NOI18N
        jButtonDownload.setEnabled(false);
        jButtonDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDownloadActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonDownload);

        jPanelToolbar.add(jToolBar1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanelToolbar, java.awt.BorderLayout.NORTH);

        jPanelContent.setBackground(new java.awt.Color(225, 235, 251));
        jPanelContent.setLayout(new java.awt.GridLayout(0, 2));

        jPanel3.setLayout(new java.awt.BorderLayout());

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTreeDirs.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeDirs.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
            }
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                jTreeDirsTreeWillExpand(evt);
            }
        });
        jTreeDirs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTreeDirsMousePressed(evt);
            }
        });
        jTreeDirs.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeDirsValueChanged(evt);
            }
        });
        jTreeDirs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTreeDirsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeDirs);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanelContent.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(225, 235, 251));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(225, 235, 251));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBackground(new java.awt.Color(225, 235, 251));
        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPane2MousePressed(evt);
            }
        });

        jTableFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableFilesMousePressed(evt);
            }
        });
        jTableFiles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableFilesKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableFiles);

        jPanel6.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanelContent.add(jPanel4);

        getContentPane().add(jPanelContent, java.awt.BorderLayout.CENTER);

        jPanelProgressBar.setPreferredSize(new java.awt.Dimension(10, 20));
        jPanelProgressBar.setLayout(new java.awt.BorderLayout());
        jPanelProgressBar.add(jProgressBar1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanelProgressBar, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddFileActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddFileActionPerformed
    {//GEN-HEADEREND:event_jButtonAddFileActionPerformed
        this.addFile();
}//GEN-LAST:event_jButtonAddFileActionPerformed

    private void jButtonaddFolderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonaddFolderActionPerformed
    {//GEN-HEADEREND:event_jButtonaddFolderActionPerformed
        this.addDir();
}//GEN-LAST:event_jButtonaddFolderActionPerformed

    private void jButtonNewFolderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonNewFolderActionPerformed
    {//GEN-HEADEREND:event_jButtonNewFolderActionPerformed
        this.createDir();
}//GEN-LAST:event_jButtonNewFolderActionPerformed

    private void jButtonDownloadActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonDownloadActionPerformed
    {//GEN-HEADEREND:event_jButtonDownloadActionPerformed
        downloadFile();
}//GEN-LAST:event_jButtonDownloadActionPerformed

    private void jTreeDirsTreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException//GEN-FIRST:event_jTreeDirsTreeWillExpand
    {//GEN-HEADEREND:event_jTreeDirsTreeWillExpand
        if (evt.getPath().getLastPathComponent() instanceof Directory)
        {
            Directory dir = (Directory) evt.getPath().getLastPathComponent();
            if (dir.getChildCount() == 1 && !(dir.getChildAt(0) instanceof Directory))
            {
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                dir.remove(0);
                loadDirectories(dir);
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
}//GEN-LAST:event_jTreeDirsTreeWillExpand
    private void loadDirectories()
    {

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getDirectories</cmd></req>";
        String respxml = ftp.getData(xml);
        WBXMLParser parser = new WBXMLParser();
        try
        {
            WBTreeNode enode = parser.parse(respxml);
            if (enode != null && enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
            {
                WBTreeNode dir = enode.getFirstNode().getFirstNode();
                if (dir.getName().equals("dir"))
                {
                    Directory root = new Directory(dir.getAttribute("name"), dir.getAttribute("path"));
                    jTreeDirs.setModel(new DefaultTreeModel(root));

                    loadDirectories(dir, root);
                    loadFiles(root);
                    this.jTreeDirs.expandRow(0);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void loadFiles(Directory dir)
    {
        String path = dir.getDirectory();
        try
        {
            path = WBXMLParser.encode(path, "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getFiles</cmd><path>" + path + "</path></req>";
        String respxml = ftp.getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null)
        {
            jTableFileModel model = new jTableFileModel(this.jTableFiles, locale);
            model.addFileListener(this);
            this.jTableFiles.setModel(model);
            Iterator files = enode.getFirstNode().getNodes().iterator();
            while (files.hasNext())
            {
                WBTreeNode file = (WBTreeNode) files.next();
                applets.ftp.File ofile = new applets.ftp.File(dir, file.getAttribute("name"), file.getAttribute("path"), file.getAttribute("size"), file.getAttribute("lastupdate"));
                model.addFile(ofile);
            }
            this.jTableFiles.updateUI();
        }
    }

    private void loadDirectories(Directory odir)
    {
        odir.removeAllChildren();
        String path = odir.getDirectory();
        try
        {
            path = WBXMLParser.encode(path, "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getDirectories</cmd><path>" + path + "</path></req>";
        String respxml = ftp.getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        try
        {
            if (enode != null && enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
            {
                WBTreeNode dir = enode.getFirstNode().getFirstNode();
                if (dir.getName().equals("dir"))
                {
                    Iterator it = dir.getNodes().iterator();
                    while (it.hasNext())
                    {
                        dir = (WBTreeNode) it.next();
                        if (dir != null && dir.getName().equals("dir"))
                        {
                            Directory child = new Directory(dir.getAttribute("name"), dir.getAttribute("path"));
                            odir.add(child);
                            if (dir.getAttribute("hasChild").equals("true"))
                            {
                                child.add(new DefaultMutableTreeNode(""));
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean hasPermission(String path)
    {
        boolean hasPermission = false;

        try
        {
            path = WBXMLParser.encode(path, "UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>hasPermissionFile</cmd><path>" + path + "</path></req>";
        String respxml = ftp.getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        try
        {
            if (enode != null && enode.getFirstNode() != null)
            {
                WBTreeNode node = enode.getFirstNode().getNodebyName("dir");
                if (node != null && node.getAttribute("permission") != null)
                {
                    hasPermission = Boolean.valueOf(node.getAttribute("permission"));
                    return hasPermission;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return hasPermission;
    }

    public boolean hasPermission(File file)
    {
        return hasPermission(file.getPath());
    }

    public void Modificated()
    {
        this.jTableFiles.updateUI();
        if (this.jTableFiles.getSelectedRowCount() == 0 || this.jTableFiles.getRowCount() == 0)
        {
            this.jMenuItemBorrarFile.setEnabled(false);
            this.jMenuItemRenameFile.setEnabled(false);
            this.jMenuItemDownload.setEnabled(false);
            this.jButtonDownload.setEnabled(false);
        }
        else
        {
            this.jMenuItemBorrarFile.setEnabled(true);
            this.jMenuItemRenameFile.setEnabled(true);
            this.jMenuItemDownload.setEnabled(true);
            this.jButtonDownload.setEnabled(true);
        }
    }

    private void deleteDir()
    {
        if (this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {
            Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
            loadDirectories(dir);
            jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
            if (model.getRowCount() > 0 || dir.getChildCount() > 0)
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no_empty"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dir.getParent() == null)
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no_borrar"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(
                    this, // The parent frame
                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("askdir") + " " + dir.getName() + "?",
                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                    JOptionPane.YES_NO_OPTION, // The option type
                    JOptionPane.QUESTION_MESSAGE))
            {
                return;
            }




            if (dir.getParent() instanceof Directory)
            {
                Directory parent = (Directory) dir.getParent();
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                if (deleteDir(dir))
                {
                    this.jTreeDirs.setSelectionPath(this.jTreeDirs.getSelectionPath().getParentPath());
                    parent.remove(dir);
                    this.jTreeDirs.updateUI();
                }
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }

    }

    private boolean deleteDir(Directory dir)
    {
        String path = dir.getDirectory();
        try
        {
            path = WBXMLParser.encode(dir.getDirectory(), "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>delete</cmd><path>" + path + "</path></req>";
        String respxml = ftp.getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("delete"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                    return true;
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        return false;
    }

    private boolean deleteFile(File f)
    {

        String path = f.getPath();
        try
        {
            path = WBXMLParser.encode(f.getPath(), "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>delete</cmd><path>" + path + "</path></req>";
        String respxml = ftp.getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("delete"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                    return true;
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        return false;
    }

    private void deleteFile()
    {

        jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
        int[] isel = this.jTableFiles.getSelectedRows();
        ArrayList<File> files = new ArrayList<File>();
        for (int i = 0; i < isel.length; i++)
        {
            int index = isel[i];
            files.add(model.getFile(index));
        }
        boolean siAll = false;
        for (int i = 0; i < files.size(); i++)
        {
            File f = (File) files.get(i);
            if (!siAll)
            {
                int selection = JOptionPane.showOptionDialog(
                        this,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("ask1") + " " + f.getName() + "?",
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        choices,
                        choices[0]);
                switch (selection)
                {
                    case 0: //Si
                        siAll = false;
                        break;
                    case 1: //Si All
                        siAll = true;
                        break;
                    case 2: // No
                        continue;
                    case 3:  //Cancel
                        return;
                }
            }
            if (deleteFile(f))
            {
                model.removeFile(f);
                this.jTableFiles.updateUI();
                this.Modificated();
            }
        }
    }

    private void renameDir()
    {
        if (this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {
            Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
            if (dir.getParent() == null)
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no_renombrar"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Directory parent = (Directory) dir.getParent();
            String name = JOptionPane.showInputDialog(
                    this, // The parent frame
                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("nombre_dir"), dir.getName());
            name = name.trim();
            if (name.trim().equals(""))
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no_nombre"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int i = 0; i < parent.getChildCount(); i++)
            {
                Directory child = (Directory) parent.getChildAt(i);
                if (child.getName().equalsIgnoreCase(name))
                {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_nombre"),
                            java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String path = parent.getDirectory() + "/" + name;
            if (renameDir(dir, path))
            {
                dir.setName(name);
                dir.setDirectory(path);
                this.jTreeDirs.updateUI();

            }
        }
    }

    private boolean renameDir(Directory dir, String newpath)
    {
        String path = dir.getDirectory();
        try
        {
            path = WBXMLParser.encode(path, "UTF-8");
            newpath = WBXMLParser.encode(newpath, "UTF-8");
        }
        catch (Exception e)
        {
        }
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>rename</cmd><path>" + path + "</path><newpath>" + newpath + "</newpath></req>";
        String respxml = ftp.getData(xml);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode enode = parser.parse(respxml);
        if (enode.getFirstNode() != null && enode.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode edir = enode.getFirstNode().getFirstNode();
            if (edir.getName().equals("rename"))
            {
                if (edir.getFirstNode().getText().equals("true"))
                {
                    return true;
                }
            }
            else if (edir.getName().equals("err"))
            {
                String msg = edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this, msg,
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        return false;

    }

    private void renameFile()
    {
        if (this.jTableFiles.getSelectedRowCount() > 0)
        {
            jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
            File file = model.getFile(this.jTableFiles.getSelectedRow());
            String name = JOptionPane.showInputDialog(
                    this, // The parent frame
                    java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("nombre_file"), file.getName());
            name = name.trim();
            if (name.trim().equals(""))
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("no_filename"),
                        java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int i = 0; i < model.getRowCount(); i++)
            {
                File f = model.getFile(i);
                if (f.getName().equalsIgnoreCase(name))
                {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("existe_nombrefile"),
                            java.util.ResourceBundle.getBundle("applets/ftp/ftp", locale).getString("title"),
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            String newpath = file.getDirectory().getDirectory() + "/" + name;
            if (renameFile(file, newpath))
            {

                file.setName(name);
                file.setPath(newpath);
                this.jTableFiles.updateUI();
            }
        }
    }

     private boolean renameFile(File f,String newpath)
    {
        String path=f.getPath();
        try
        {
            path=WBXMLParser.encode(path,"UTF-8");
            newpath=WBXMLParser.encode(newpath,"UTF-8");
        }
        catch(Exception e){}
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>rename</cmd><path>"+ path +"</path><newpath>" + newpath + "</newpath></req>";
        String respxml=ftp.getData(xml);
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode enode=parser.parse(respxml);
        if(enode.getFirstNode()!=null && enode.getFirstNode().getFirstNode()!=null)
        {
            WBTreeNode edir=enode.getFirstNode().getFirstNode();
            if(edir.getName().equals("rename"))
            {
                if(edir.getFirstNode().getText().equals("true"))
                {
                    return true;
                }
            }
            else if(edir.getName().equals("err"))
            {
                String msg=edir.getFirstNode().getText();
                JOptionPane.showMessageDialog(this,msg,
                       java.util.ResourceBundle.getBundle("applets/ftp/ftp",locale).getString("title"),
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        return false;
    }

    private void loadDirectories(WBTreeNode dir, Directory root)
    {
        Iterator it = dir.getNodes().iterator();
        while (it.hasNext())
        {
            WBTreeNode enode = (WBTreeNode) it.next();
            if (enode != null && enode.getName().equals("dir"))
            {
                Directory child = new Directory(enode.getAttribute("name"), enode.getAttribute("path"));
                root.add(child);
                if (enode.getAttribute("hasChild").equals("true"))
                {
                    child.add(new DefaultMutableTreeNode(""));
                }
            }
        }


    }
    private void jTreeDirsMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTreeDirsMousePressed
    {//GEN-HEADEREND:event_jTreeDirsMousePressed
        if (evt.getButton() == MouseEvent.BUTTON3 && evt.getClickCount() == 1 && this.jTreeDirs.getSelectionPath() != null)
        {
            jMenuItemNewFolder.setEnabled(false);
            jMenuDirAdd.setEnabled(false);
            jMenuItemDirRename.setEnabled(false);
            jMenuItemDirDelete.setEnabled(false);
            Object obj = this.jTreeDirs.getSelectionPath().getLastPathComponent();
            if (obj != null && obj instanceof Directory)
            {
                Directory directory = (Directory) obj;
                if (hasPermission(directory.getDirectory()))
                {
                    jMenuItemNewFolder.setEnabled(true);
                    jMenuDirAdd.setEnabled(true);
                    jMenuItemDirRename.setEnabled(true);
                    jMenuItemDirDelete.setEnabled(true);
                }
            }
            this.jPopupMenuDir.show(this.jTreeDirs, evt.getX(), evt.getY());
        }
}//GEN-LAST:event_jTreeDirsMousePressed

    private void jTreeDirsValueChanged(javax.swing.event.TreeSelectionEvent evt)//GEN-FIRST:event_jTreeDirsValueChanged
    {//GEN-HEADEREND:event_jTreeDirsValueChanged

        this.jButtonAddFile.setEnabled(false);
        this.jButtonDownload.setEnabled(false);
        this.jButtonNewFolder.setEnabled(false);
        this.jButtonaddFolder.setEnabled(false);
        this.jMenuBorrar.setEnabled(false);
        this.jMenuDirAdd.setEnabled(false);
        this.jMenuAdd.setEnabled(false);
        this.jMenuItemCrearDirectorio.setEnabled(false);
        this.jMenuRename.setEnabled(false);
        this.jMenuFileAdd.setEnabled(false);

        if (this.jTreeDirs.getSelectionPath().getLastPathComponent() instanceof Directory)
        {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            Directory dir = (Directory) this.jTreeDirs.getSelectionPath().getLastPathComponent();
            if (this.hasPermission(dir.getDirectory()))
            {
                this.jMenuFileAdd.setEnabled(true);
                this.jMenuAdd.setEnabled(true);
                this.jMenuRename.setEnabled(true);
                this.jMenuItemCrearDirectorio.setEnabled(true);
                this.jMenuDirAdd.setEnabled(true);
                this.jMenuBorrar.setEnabled(true);
                this.jButtonAddFile.setEnabled(true);
                this.jButtonDownload.setEnabled(true);
                this.jButtonNewFolder.setEnabled(true);
                this.jButtonaddFolder.setEnabled(true);
            }
            loadFiles(dir);
        }
        this.jTreeDirs.updateUI();
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jTreeDirsValueChanged

    private void jTreeDirsKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTreeDirsKeyReleased
    {//GEN-HEADEREND:event_jTreeDirsKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE)
        {
            this.deleteDir();
        }
}//GEN-LAST:event_jTreeDirsKeyReleased

    private void jTableFilesMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTableFilesMousePressed
    {//GEN-HEADEREND:event_jTableFilesMousePressed
        this.jMenuItemFileDownload.setEnabled(false);
        this.jMenuItemFileRename.setEnabled(false);
        this.jMenuItemFileDelete.setEnabled(false);
        if (evt.getButton() == MouseEvent.BUTTON3 && evt.getClickCount() == 1)
        {
            if (this.jTableFiles.getSelectedRow() != -1)
            {
                jTableFileModel model = (jTableFileModel) jTableFiles.getModel();
                File file = model.getFile(this.jTableFiles.getSelectedRow());
                if (hasPermission(file))
                {
                    this.jMenuItemFileDownload.setEnabled(true);
                    this.jMenuItemFileRename.setEnabled(true);
                    this.jMenuItemFileDelete.setEnabled(true);
                }
            }
            this.jPopupMenuFile.show(this.jTableFiles, evt.getX(), evt.getY());

        }
}//GEN-LAST:event_jTableFilesMousePressed

    private void jTableFilesKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTableFilesKeyReleased
    {//GEN-HEADEREND:event_jTableFilesKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_DELETE)
        {
            this.deleteFile();
        }
}//GEN-LAST:event_jTableFilesKeyReleased

    private void jScrollPane2MousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jScrollPane2MousePressed
    {//GEN-HEADEREND:event_jScrollPane2MousePressed
        this.jMenuItemFileDownload.setEnabled(false);
        this.jMenuItemFileRename.setEnabled(false);
        this.jMenuItemFileDelete.setEnabled(false);
        if (evt.getButton() == MouseEvent.BUTTON3 && evt.getClickCount() == 1)
        {
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try
            {
                if (this.jTableFiles.getSelectedRowCount() > 0 && this.jTableFiles.getModel().getRowCount() > 0)
                {
                    jTableFileModel model = (jTableFileModel) this.jTableFiles.getModel();
                    File file = model.getFile(jTableFiles.getSelectedRow());
                    if (hasPermission(file))
                    {
                        this.jMenuItemFileDownload.setEnabled(true);
                        this.jMenuItemFileRename.setEnabled(true);
                        this.jMenuItemFileDelete.setEnabled(true);

                    }
                }
                this.jPopupMenuFile.show(this.jScrollPane2, evt.getX(), evt.getY());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
}//GEN-LAST:event_jScrollPane2MousePressed

    private void jMenuItemNewFolderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemNewFolderActionPerformed
    {//GEN-HEADEREND:event_jMenuItemNewFolderActionPerformed
        this.createDir();
}//GEN-LAST:event_jMenuItemNewFolderActionPerformed

    private void jMenuItemDirAddFileActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemDirAddFileActionPerformed
    {//GEN-HEADEREND:event_jMenuItemDirAddFileActionPerformed
        addFile();
}//GEN-LAST:event_jMenuItemDirAddFileActionPerformed

    private void jMenuItemDirAddFolderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemDirAddFolderActionPerformed
    {//GEN-HEADEREND:event_jMenuItemDirAddFolderActionPerformed
        this.addDir();
}//GEN-LAST:event_jMenuItemDirAddFolderActionPerformed

    private void jMenuItemDirRenameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemDirRenameActionPerformed
    {//GEN-HEADEREND:event_jMenuItemDirRenameActionPerformed
        this.renameDir();
}//GEN-LAST:event_jMenuItemDirRenameActionPerformed

    private void jMenuItemDirDeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemDirDeleteActionPerformed
    {//GEN-HEADEREND:event_jMenuItemDirDeleteActionPerformed
        this.deleteDir();
}//GEN-LAST:event_jMenuItemDirDeleteActionPerformed

    private void jMenuItemCrearDirectorioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemCrearDirectorioActionPerformed
    {//GEN-HEADEREND:event_jMenuItemCrearDirectorioActionPerformed
        this.createDir();
}//GEN-LAST:event_jMenuItemCrearDirectorioActionPerformed

    private void jMenuItemAddFileActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemAddFileActionPerformed
    {//GEN-HEADEREND:event_jMenuItemAddFileActionPerformed
        this.addFile();
}//GEN-LAST:event_jMenuItemAddFileActionPerformed

    private void jMenuItemAddFolderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemAddFolderActionPerformed
    {//GEN-HEADEREND:event_jMenuItemAddFolderActionPerformed
        addDir();
}//GEN-LAST:event_jMenuItemAddFolderActionPerformed

    private void jMenuItemBorrarDirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemBorrarDirActionPerformed
    {//GEN-HEADEREND:event_jMenuItemBorrarDirActionPerformed
        this.deleteDir();
}//GEN-LAST:event_jMenuItemBorrarDirActionPerformed

    private void jMenuItemBorrarFileActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemBorrarFileActionPerformed
    {//GEN-HEADEREND:event_jMenuItemBorrarFileActionPerformed
        this.deleteFile();
}//GEN-LAST:event_jMenuItemBorrarFileActionPerformed

    private void jMenuItemDownloadActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemDownloadActionPerformed
    {//GEN-HEADEREND:event_jMenuItemDownloadActionPerformed
        jMenuItemFileDownloadActionPerformed(null);
}//GEN-LAST:event_jMenuItemDownloadActionPerformed

    private void jMenuItemRenameFolderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemRenameFolderActionPerformed
    {//GEN-HEADEREND:event_jMenuItemRenameFolderActionPerformed
        this.renameDir();
}//GEN-LAST:event_jMenuItemRenameFolderActionPerformed

    private void jMenuItemRenameFileActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemRenameFileActionPerformed
    {//GEN-HEADEREND:event_jMenuItemRenameFileActionPerformed
        this.renameFile();
}//GEN-LAST:event_jMenuItemRenameFileActionPerformed

    private void jMenuItemFileAddFileActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemFileAddFileActionPerformed
    {//GEN-HEADEREND:event_jMenuItemFileAddFileActionPerformed

        this.addFile();
    }//GEN-LAST:event_jMenuItemFileAddFileActionPerformed

    private void jMenuItemFileAddDirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemFileAddDirActionPerformed
    {//GEN-HEADEREND:event_jMenuItemFileAddDirActionPerformed
        this.addDir();
}//GEN-LAST:event_jMenuItemFileAddDirActionPerformed

    private void jMenuItemFileDownloadActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemFileDownloadActionPerformed
    {//GEN-HEADEREND:event_jMenuItemFileDownloadActionPerformed
        downloadFile();
}//GEN-LAST:event_jMenuItemFileDownloadActionPerformed

    private void jMenuItemFileRenameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemFileRenameActionPerformed
    {//GEN-HEADEREND:event_jMenuItemFileRenameActionPerformed
        this.renameFile();
}//GEN-LAST:event_jMenuItemFileRenameActionPerformed

    private void jMenuItemFileDeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemFileDeleteActionPerformed
    {//GEN-HEADEREND:event_jMenuItemFileDeleteActionPerformed
        deleteFile();
}//GEN-LAST:event_jMenuItemFileDeleteActionPerformed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemExitActionPerformed
    {//GEN-HEADEREND:event_jMenuItemExitActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddFile;
    private javax.swing.JButton jButtonDownload;
    private javax.swing.JButton jButtonNewFolder;
    private javax.swing.JButton jButtonaddFolder;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JMenu jMenuAdd;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuBorrar;
    private javax.swing.JMenu jMenuDirAdd;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuFileAdd;
    private javax.swing.JMenuItem jMenuItemAddFile;
    private javax.swing.JMenuItem jMenuItemAddFolder;
    private javax.swing.JMenuItem jMenuItemBorrarDir;
    private javax.swing.JMenuItem jMenuItemBorrarFile;
    private javax.swing.JMenuItem jMenuItemCrearDirectorio;
    private javax.swing.JMenuItem jMenuItemDirAddFile;
    private javax.swing.JMenuItem jMenuItemDirAddFolder;
    private javax.swing.JMenuItem jMenuItemDirDelete;
    private javax.swing.JMenuItem jMenuItemDirRename;
    private javax.swing.JMenuItem jMenuItemDownload;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemFileAddDir;
    private javax.swing.JMenuItem jMenuItemFileAddFile;
    private javax.swing.JMenuItem jMenuItemFileDelete;
    private javax.swing.JMenuItem jMenuItemFileDownload;
    private javax.swing.JMenuItem jMenuItemFileRename;
    private javax.swing.JMenuItem jMenuItemNewFolder;
    private javax.swing.JMenuItem jMenuItemRenameFile;
    private javax.swing.JMenuItem jMenuItemRenameFolder;
    private javax.swing.JMenu jMenuRename;
    private javax.swing.JMenu jMenuTools;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelContent;
    private javax.swing.JPanel jPanelProgressBar;
    private javax.swing.JPanel jPanelToolbar;
    private javax.swing.JPopupMenu jPopupMenuDir;
    private javax.swing.JPopupMenu jPopupMenuFile;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JTable jTableFiles;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTreeDirs;
    // End of variables declaration//GEN-END:variables

    private class Worker implements Runnable
    {

        FUpload fup;
        String path;
        java.io.File f;
        jTableFileModel model;
        Directory dir;

        public Worker(java.io.File f, String path, FUpload fup, jTableFileModel model, Directory dir)
        {
            this.fup = fup;
            this.path = path;
            this.f = f;
            this.model = model;
            this.dir = dir;
        }

        public void run()
        {
            fup.setLocation(400, 300);
            fup.setSize(200, 50);
            fup.sendFile(path, f, model, dir);
            jTableFiles.updateUI();
        }
    }
}
