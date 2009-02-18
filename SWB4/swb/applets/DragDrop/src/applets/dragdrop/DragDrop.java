/*
 * DragDrop.java
 *
 * Created on 30 de octubre de 2002, 15:37
 */

package applets.dragdrop;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.io.*;
import java.util.List;
import java.util.*;
import javax.swing.table.*;
import java.net.*;
import javax.swing.filechooser.*;

/**
 * Applet para transferir archivos mediante drag and drop.
 *
 * Applet to transfer archives by drag and drop
 *
 * @author  Administrador
 */
public class DragDrop extends javax.swing.JApplet 
{
    
    private final String backgroundParam = "background";
    private final String foregroundParam = "foreground";
    private final String backgroundSelectionParam = "backgroundSelection";
    private final String foregroundSelectionParam = "foregroundSelection";
    private final String webPathParam = "webpath";
    private String webPath="/";
    private String downpath="";
    private String clientpath="";
    protected URL url=null;

    private String cgi="gtw";                   //cgi del gateway
    private String up="wbupload";                      //cgi del gateway
    //private String up="upload";                 //cgi del gateway
    private String jsess="";                    //session del usuario
    private URL gurl=null;                      //url de gateway
    
    boolean first=true;
    ModelRelation model;
    Locale locale=new Locale("es");
    
    
    /** Creates new form DragDrop */
    public DragDrop()
    {
        
    }
    
    public void init()
    {
        String loc=getParameter("locale");
        if(loc!=null)locale=new Locale(loc);
        
        
        webPath = getParameter(webPathParam);
        try
        {
            //url=new URL("http://localhost:85/");
            url=new URL(getCodeBase().getProtocol(),getCodeBase().getHost(),getCodeBase().getPort(),webPath);
            //System.out.println(url);
        }catch(Exception e){System.out.println(e);}
        cgi="gtw";
        if(this.getParameter("jsess")!=null)
            jsess=this.getParameter("jsess");
        if(this.getParameter("path")!=null)
            downpath=this.getParameter("path");
        if(this.getParameter("clientpath")!=null)
            clientpath=this.getParameter("clientpath");
        
        clientpath.replace('\\','/');
        
        model= new ModelRelation(new String [] {java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("name"), java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("size"), java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("type"), java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("modify"), java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("path")},0);            
        
        initComponents();
        
        DropTargetListener dropLiten = new DropTargetListener()
        {
            public void dragEnter(DropTargetDragEvent enter)
            {
            }
            
            public void dragOver(DropTargetDragEvent over)
            {
            }
            
            public void dropActionChanged(DropTargetDragEvent action)
            {
            }
            
            public void dragExit(DropTargetEvent exit)
            {
            }
            
            public void drop(DropTargetDropEvent drop)
            {
                try
                {
                    Transferable transfer = drop.getTransferable();
                    if(transfer.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
                    {
                        drop.acceptDrop(DnDConstants.ACTION_REFERENCE);
                        List file_list = (List)transfer.getTransferData(DataFlavor.javaFileListFlavor);
                        for(int i=0;i<file_list.size();i++)
                        {
                            String file_path = file_list.get(i).toString();
                            
                            File file = new File(file_path);
                            Vector data=new Vector();
                            data.add(file.getName());
                            data.add(""+file.length());
                            String tp=java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("file");
                            if(file.isDirectory())
                                tp=java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("directory");
                            data.add(tp);
                            data.add(new Date(file.lastModified()));
                            data.add(file);
                            model.addRow(data);
                        }
                        drop.getDropTargetContext().dropComplete(true);
                    }
                }
                catch(Exception e)
                {
                    System.err.println(e);
                }
            }
        };
        DropTarget target = new DropTarget(jTable1,DnDConstants.ACTION_REFERENCE,dropLiten);
        DropTarget targetS = new DropTarget(jScrollPane1,DnDConstants.ACTION_REFERENCE,dropLiten);
        
        String files=this.getParameter("files");
        if(files!=null)
        {
            StringTokenizer st=new StringTokenizer(files,";");
            while(st.hasMoreTokens())
            {
                String sfile=st.nextToken();
                try
                {
                    File file = new File(clientpath+sfile);
                    Vector data=new Vector();
                    data.add(file.getName());
                    data.add(""+file.length());
                    String tp=java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("file");
                    if(file.isDirectory())
                        tp=java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("directory");
                    data.add(tp);
                    data.add(new Date(file.lastModified()));
                    data.add(file);
                    model.addRow(data);
                }catch(Exception e){System.out.println(e);}
            }
        }
        jLabel1.setText(java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("ready"));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        jToolBar1 = new javax.swing.JToolBar();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();

        jToolBar1.setBorder(new javax.swing.border.EtchedBorder());
        jToolBar1.setPreferredSize(new java.awt.Dimension(18, 31));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/dragdrop/images/b_abrir.gif")));
        jButton11.setToolTipText(java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("add"));
        jButton11.setActionCommand("add");
        jButton11.setBorderPainted(false);
        jButton11.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton11.setMinimumSize(new java.awt.Dimension(10, 10));
        jButton11.setPreferredSize(new java.awt.Dimension(10, 30));
        jButton11.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton11ActionPerformed(evt);
            }
        });
        jButton11.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButton11MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButton11MouseEntered(evt);
            }
        });

        jToolBar1.add(jButton11);

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/dragdrop/images/b_borrar.gif")));
        jButton12.setToolTipText(java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("delete"));
        jButton12.setActionCommand("remove");
        jButton12.setBorderPainted(false);
        jButton12.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton12.setMinimumSize(new java.awt.Dimension(10, 10));
        jButton12.setPreferredSize(new java.awt.Dimension(10, 30));
        jButton12.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton12ActionPerformed(evt);
            }
        });
        jButton12.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButton12MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButton12MouseEntered(evt);
            }
        });

        jToolBar1.add(jButton12);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/dragdrop/images/b_guardar.gif")));
        jButton1.setToolTipText(java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("save"));
        jButton1.setActionCommand("save");
        jButton1.setBorderPainted(false);
        jButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton1.setMinimumSize(new java.awt.Dimension(10, 10));
        jButton1.setPreferredSize(new java.awt.Dimension(10, 30));
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                jButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                jButton1MouseExited(evt);
            }
        });

        jToolBar1.add(jButton1);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(new javax.swing.border.EtchedBorder());
        jScrollPane1.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                jScrollPane1ComponentResized(evt);
            }
        });

        jTable1.setModel(model);
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jTable1.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                jTable1KeyPressed(evt);
            }
        });

        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBorder(new javax.swing.border.EtchedBorder());
        jPanel1.setMinimumSize(new java.awt.Dimension(20, 14));
        jPanel1.setPreferredSize(new java.awt.Dimension(158, 17));
        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        jPanel1.add(jLabel1);

        jProgressBar1.setBorder(null);
        jProgressBar1.setBorderPainted(false);
        jProgressBar1.setStringPainted(true);
        jPanel1.add(jProgressBar1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

    }//GEN-END:initComponents

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTable1KeyPressed
    {//GEN-HEADEREND:event_jTable1KeyPressed
        // Add your handling code here:
        if(evt.getKeyCode()==127)
        {
            if(jTable1.getSelectedRows().length>0)
            {
                int i[]=jTable1.getSelectedRows();
                for(int x=i.length-1;x>=0;x--)
                {
                    model.removeRow(i[x]);
                }
            }            
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton12ActionPerformed
    {//GEN-HEADEREND:event_jButton12ActionPerformed
        // Add your handling code here:
        if(jTable1.getSelectedRows().length>0)
        {
            int i[]=jTable1.getSelectedRows();
            for(int x=i.length-1;x>=0;x--)
            {
                model.removeRow(i[x]);
            }
        }
            
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton11ActionPerformed
    {//GEN-HEADEREND:event_jButton11ActionPerformed
        // Add your handling code here:
        JFileChooser fc = new JFileChooser();
        
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            Vector data=new Vector();
            data.add(file.getName());
            data.add(""+file.length());
            String tp=java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("file");
            if(file.isDirectory())
                tp=java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("directory");
            data.add(tp);
            data.add(new Date(file.lastModified()));
            data.add(file);
            model.addRow(data);
            
            //log.append("Saving: " + file.getName() + "." + newline);
        } else {
            //log.append("Save command cancelled by user." + newline);
        }
        
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        int maxs=0;
        int len=model.getRowCount();
        for(int x=0;x<len;x++)
        {
            try
            {
                File f=(File)model.getValueAt(x,4);
                maxs+=f.length();
            }catch(Exception e){System.out.println(e);}
        }
        
        jProgressBar1.setMinimum(0);
        jProgressBar1.setMaximum(maxs);
        jProgressBar1.setValue(0);
        
        Worker worker = new Worker(this);
        SwingUtilities.invokeLater(worker);
        //System.out.println("hola");
        
        /*
        String ret;
        boolean err=false;
        for(int x=0;x<len;x++)
        {
            try
            {
                File f=(File)model.getValueAt(x,4);
                ret=saveFiles(downpath,f.getName(),new FileInputStream(f),f.length());
                if(ret.length()>0)
                {
                    JOptionPane.showMessageDialog(this,ret,"Enviar Archivos",JOptionPane.ERROR_MESSAGE);
                    err=true;
                }
            }catch(Exception e){System.out.println(e);}
        }
        if(err)
            JOptionPane.showMessageDialog(this,"Se detectaron errores en el envio de los archivos...","Enviar Archivos",JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(this,"Los archivos fueron enviados...","Enviar Archivos",JOptionPane.INFORMATION_MESSAGE);
        **/
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton12MouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton12MouseExited
    {//GEN-HEADEREND:event_jButton12MouseExited
        // Add your handling code here:
        ((JButton)evt.getSource()).setBorderPainted(false);
    }//GEN-LAST:event_jButton12MouseExited

    private void jButton12MouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton12MouseEntered
    {//GEN-HEADEREND:event_jButton12MouseEntered
        // Add your handling code here:
        ((JButton)evt.getSource()).setBorderPainted(true);
    }//GEN-LAST:event_jButton12MouseEntered

    private void jButton11MouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton11MouseExited
    {//GEN-HEADEREND:event_jButton11MouseExited
        // Add your handling code here:
        ((JButton)evt.getSource()).setBorderPainted(false);
    }//GEN-LAST:event_jButton11MouseExited

    private void jButton11MouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton11MouseEntered
    {//GEN-HEADEREND:event_jButton11MouseEntered
        // Add your handling code here:
        ((JButton)evt.getSource()).setBorderPainted(true);
    }//GEN-LAST:event_jButton11MouseEntered

    private void jButton1MouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton1MouseExited
    {//GEN-HEADEREND:event_jButton1MouseExited
        // Add your handling code here:
        ((JButton)evt.getSource()).setBorderPainted(false);
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton1MouseEntered
    {//GEN-HEADEREND:event_jButton1MouseEntered
        // Add your handling code here:
        ((JButton)evt.getSource()).setBorderPainted(true);
    }//GEN-LAST:event_jButton1MouseEntered

    private void jScrollPane1ComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_jScrollPane1ComponentResized
    {//GEN-HEADEREND:event_jScrollPane1ComponentResized
        // Add your handling code here:
        if(first)
        {
            //jTable1.setPreferredSize(new Dimension((int)jScrollPane1.getSize().getWidth(),(int)jScrollPane1.getSize().getHeight()-19));
            first=false;
            //System.out.println("scroll:"+jScrollPane1.getSize()+" header:"+jTable1.getInsets());
        }
    }//GEN-LAST:event_jScrollPane1ComponentResized
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    private void usePageParams()
    {
        final String defaultBackground = java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("defaultBackground");
        final String defaultForeground = java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("defaultForeground");
        final String defaultBackgroundSelection = java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("defaultBackgroundSelection");
        final String defaultForegroundSelection = java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("defaultForegroundSelection");
        String backgroundValue;
        String foregroundValue;
        String backgroundSelectionValue;
        String foregroundSelectionValue;

        //if(getParameter("Home")!=null)viewer.home = getParameter("Home");
        /**
         * Read the <PARAM NAME="label" VALUE="some string">,
         * <PARAM NAME="background" VALUE="rrggbb">,
         * and <PARAM NAME="foreground" VALUE="rrggbb"> tags from
         * the applet's HTML host.
         */
        backgroundValue = getParameter(backgroundParam);
        foregroundValue = getParameter(foregroundParam);
        backgroundSelectionValue = getParameter(backgroundSelectionParam);
        foregroundSelectionValue = getParameter(foregroundSelectionParam);

        if ((backgroundValue == null) || (foregroundValue == null))
        {
            /**
             * There was something wrong with the HTML host tags.
             * Generate default values.
             */
            backgroundValue = defaultBackground;
            foregroundValue = defaultForeground;
            backgroundSelectionValue = defaultBackgroundSelection;
            foregroundSelectionValue = defaultForegroundSelection;
        }

        // /**
        // * Set the applet's string label, background color, and
        // * foreground colors.
        // */
        this.setBackground(stringToColor(backgroundValue));
        this.setForeground(stringToColor(foregroundValue));
        
        
        //Configuracion jTree2
/*
        jTree2.setBackground(stringToColor(backgroundValue));
        jTree2.setForeground(stringToColor(foregroundValue));

        namerenderer=new WBNamesRenderer();
        namerenderer.setBackgroundNonSelectionColor(stringToColor(backgroundValue));
        namerenderer.setBackgroundSelectionColor(stringToColor(backgroundSelectionValue));
        namerenderer.setTextSelectionColor(stringToColor(foregroundSelectionValue));
        namerenderer.setTextNonSelectionColor(stringToColor(foregroundValue));

        try
        {
            MediaTracker mt=new MediaTracker(this);
            Image icon1=getImage(getCodeBase(),"images/ico_titulo.gif");
            Image icon2=getImage(getCodeBase(),"images/ico_idioma.gif");
            namerenderer.setRootIcon(new ImageIcon(icon1));
            namerenderer.setLangIcon(new ImageIcon(icon2));
            mt.addImage(icon1,0);
            mt.addImage(icon2,0);
            mt.waitForAll();
        }catch(Exception e){System.out.println("Error al cargar iconos...");}
*/
        
        //Configuracion jTable1
        jTable1.setBackground(stringToColor(backgroundValue));
        jTable1.setForeground(stringToColor(foregroundValue));
        jTable1.setSelectionBackground(stringToColor(backgroundSelectionValue));
        jTable1.setSelectionForeground(stringToColor(foregroundSelectionValue));
        jTable1.setGridColor(stringToColor(foregroundSelectionValue));
        
        //Configuracion jScrollPane1
        jScrollPane1.setBackground(stringToColor(backgroundValue));
/*        
        this.jButton3.setEnabled(false);
        this.jButton4.setEnabled(false);
        this.jButton5.setEnabled(false);
        this.jButton6.setEnabled(false);
        this.jButton1.setEnabled(false);        
        this.jButton2.setEnabled(false);
*/
    }

    private Color stringToColor(String paramValue)
    {
        int red;
        int green;
        int blue;

        red = (Integer.decode("0x" + paramValue.substring(0,2))).intValue();
        green = (Integer.decode("0x" + paramValue.substring(2,4))).intValue();
        blue = (Integer.decode("0x" + paramValue.substring(4,6))).intValue();
        return new Color(red,green,blue);
    }

/*    
    private String getData(String xml)
    {
        StringBuffer ret=new StringBuffer();
        try
        {
            if(gurl==null)
            {
                gurl=new URL(this.url,cgi);
            }
            URLConnection urlconn=gurl.openConnection();
            urlconn.setUseCaches(false);
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            //System.out.println("JSESSIONID="+jsess);
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
                //System.out.println(inputLine);
            }
            in.close();
        }catch(Exception e){System.out.println("Error al obtener el servicio..."+e);}
        return ret.toString();
    }
*/
    
    private String saveFiles(String rpath, String file, InputStream data, long size)
    {
        StringBuffer ret=new StringBuffer();
        int port=80;
        try
        {
            if(gurl==null)
            {
                gurl=new URL(this.url,up);
            }
            //if(gurl.getPort()>0)port=gurl.getPort();
            //Socket socket=new Socket(gurl.getHost(),port);
            
            System.out.println(gurl.getHost());
            System.out.println(gurl.getPort());
            System.out.println(gurl.getFile());
            
            URLConnection urlconn=gurl.openConnection();
            urlconn.setUseCaches(false);
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            urlconn.setRequestProperty("Content-Type","multipart/form-data; boundary=---------------------------7d121ca241196");
            
            //StringBuffer iniData=new StringBuffer();
            StringBuffer iniPostData=new StringBuffer();
            StringBuffer finPostData=new StringBuffer();
            
            iniPostData.append("-----------------------------7d121ca241196\r\n");
            iniPostData.append("Content-Disposition: form-data; name=\"path\"\r\n");
            iniPostData.append("\r\n");
            iniPostData.append(rpath +"\r\n");
            iniPostData.append("-----------------------------7d121ca241196\r\n");
            iniPostData.append("Content-Disposition: form-data; name=\"file\"; filename=\""+ file + "\"\r\n");
            iniPostData.append("Content-Type: application/octet-stream\r\n");
            iniPostData.append("\r\n");


            finPostData.append("\r\n-----------------------------7d121ca241196\r\n");
            finPostData.append("Content-Disposition: form-data; name=\"ACL\"\r\n");
            finPostData.append("\r\n");
            finPostData.append("422\r\n");
            finPostData.append("-----------------------------7d121ca241196--\r\n");
            
            //iniData.append("POST "+gurl.getFile()+" HTTP/1.1\r\n");
            //iniData.append("Cookie: JSESSIONID="+jsess+"\r\n");
            //iniData.append("Content-Type: multipart/form-data; boundary=---------------------------7d121ca241196\r\n");
            //iniData.append("Content-Length: "+(size+iniPostData.length()+finPostData.length())+"\r\n");
            //iniData.append("User-Agent: Java(tm) 2 SDK, Standard Edition v1.4.1_01 Java/1.4.1_01\r\n");
            //iniData.append("Host: "+gurl.getHost()+":"+port+"\r\n");
            //iniData.append("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");
            //iniData.append("\r\n");
            //iniData.append("Connection: keep-alive\r\n");
            
            //System.out.println(iniData);
            //System.out.println(iniPostData);
            //System.out.println(finPostData);
            
            urlconn.setRequestProperty("Content-Length",""+(size+iniPostData.length()+finPostData.length()));
            urlconn.setDoOutput(true);
            DataOutputStream pout = new DataOutputStream(urlconn.getOutputStream());
            //DataOutputStream pout = new DataOutputStream(socket.getOutputStream());
            //pout.write(iniData.toString().getBytes());
            pout.write(iniPostData.toString().getBytes());
            
            byte b[]=new byte[8192];
            
            int x=0;
            while((x=data.read(b,0,8192))>-1)
            {
                jProgressBar1.setValue(jProgressBar1.getValue()+x);
                //jProgressBar1.repaint();
                pout.write(b,0,x);
            }
            
            pout.write(finPostData.toString().getBytes());
            pout.flush();
            pout.close();

            DataInputStream in = new DataInputStream(urlconn.getInputStream());
            //DataInputStream in = new DataInputStream(socket.getInputStream());
            while ((x= in.read(b,0,8192))>-1)
            {
                String inputLine=new String(b,0,x);
                //ret.append(inputLine);
                //ret.append("\n");
                //System.out.println(inputLine);
                //if(inputLine.equals("0")||in.available()==0)break;
            }
            in.close();
            //socket.close();
        }catch(Exception e){System.out.println(java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("error.upload.file")+": "+file+e);ret.append(java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("error.upload.file")+": "+file+"\n"+e);}
        return ret.toString();
    }
/*    
    private void getPath()
    {
        String data=getData("<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>1</cmd></req>");
        System.out.println(data);
    }
*/    
    
    private class Worker implements Runnable
    {
        Component comp;
        int x;
        int len;
        boolean err;
        
        Worker(Component comp)
        {
            this.comp=comp;
            len=model.getRowCount();
            x=0;
            err=false;
        }

        public void run()
        {
            int len=model.getRowCount();
            String ret;
            //boolean err=false;
            //for(int x=0;x<len;x++)
            {
                try
                {
                    File f=(File)model.getValueAt(x,4);
                    jLabel1.setText(java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("sent")+": "+f.getName());
                    ret=saveFiles(downpath,f.getName(),new FileInputStream(f),f.length());
                    jProgressBar1.repaint();
                    jLabel1.repaint();
                    jPanel1.repaint();
                    jPanel1.updateUI();
                    if(ret.length()>0)
                    {
                        JOptionPane.showMessageDialog(comp,ret,java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("sent.files"),JOptionPane.ERROR_MESSAGE);
                        err=true;
                    }
                    jLabel1.setText(f.getName()+" "+java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("sent"));
                    jLabel1.repaint();
                }catch(Exception e){System.out.println(e);}
            }
            x++;
            if(x==len)
            {
                if(err)
                    JOptionPane.showMessageDialog(comp,java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("error.sending.files")+"...",java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("sent.files"),JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(comp,java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("files.sent")+"...",java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("sent.files"),JOptionPane.INFORMATION_MESSAGE);
                jLabel1.setText(java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("ready"));
            }
            else
            {
                jLabel1.setText(java.util.ResourceBundle.getBundle("applets/dragdrop/locale",locale).getString("sent")+": "+((File)model.getValueAt(x,4)).getName());
                SwingUtilities.invokeLater(this);
            }
        }
    }
}



