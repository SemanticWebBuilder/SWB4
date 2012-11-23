/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
**/
 


/*
 * DragDrop.java
 *
 * Created on 9 de enero de 2005, 03:23 PM
 */

package applets.htmleditor;

import java.net.URL;
import java.net.URLConnection;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.StringWriter;
import java.io.FileInputStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import java.applet.*;
import javax.swing.tree.*;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class DragDrop extends javax.swing.JDialog
{
    private URL gateway=null;
    private URL upload=null;
    private String topicMap=null;
    private String id=null;
    private String version=null;
    private String jsess=null;
    private String type=null;
    
    ModelRelation model;
    Locale locale=new Locale("es");    

    String filename=null;
    String html=null;    
    //private File curDir=null;    
    
    private boolean returnValue;
    
    /** Creates new form DragDrop */
    public DragDrop(java.awt.Frame parent, boolean modal, Locale locale)
    {
        super(parent, modal);
        
        this.locale=locale;
        model= new ModelRelation(new String [] {java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("name"), java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("size"), java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("type"), java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("modify"), java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("path")},0);        
        
        initComponents();
        
        jLabel1.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("ready"));
        setSize(500,300);
        setLocation(100,100);
        setTitle("Upload Attached Files");
    }
    
    public void addHTMLFile(String filename, String html)
    {
        this.filename=filename;
        this.html=html;
        //System.out.println("filename:"+filename);
        //System.out.println("html:"+html);
    }
    
    public void addFiles(String files, String clientpath)
    {
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
                    String tp=java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("file");
                    if(file.isDirectory())
                        tp=java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("directory");
                    data.add(tp);
                    data.add(new Date(file.lastModified()));
                    data.add(file);
                    model.addRow(data);
                }catch(Exception e){System.out.println(e);}
            }
        }
        
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
        jButton1 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Webbuilder");
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                closeDialog(evt);
            }
        });

        jToolBar1.setBorder(new javax.swing.border.EtchedBorder());
        jToolBar1.setPreferredSize(new java.awt.Dimension(18, 31));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/abrir.gif")));
        jButton11.setToolTipText(java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("add"));
        jButton11.setActionCommand("add");
        jButton11.setBorderPainted(false);
        jButton11.setFocusable(false);
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

        jToolBar1.add(jButton11);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/guardar.gif")));
        jButton1.setToolTipText(java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("save"));
        jButton1.setActionCommand("save");
        jButton1.setBorderPainted(false);
        jButton1.setFocusable(false);
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

        jToolBar1.add(jButton1);

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/htmleditor/images/remove.gif")));
        jButton12.setToolTipText(java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("delete"));
        jButton12.setActionCommand("remove");
        jButton12.setBorderPainted(false);
        jButton12.setFocusable(false);
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

        jToolBar1.add(jButton12);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(new javax.swing.border.EtchedBorder());
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

        pack();
    }//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        // Add your handling code here:
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
        
        Worker worker=new Worker(this);
        SwingUtilities.invokeLater(worker);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton11ActionPerformed
    {//GEN-HEADEREND:event_jButton11ActionPerformed
        // Add your handling code here:
        JFileChooser fc = new JFileChooser();
        
        
        fc.setCurrentDirectory(TemplateEditor.curDir);
        int returnVal = fc.showSaveDialog(this);
        TemplateEditor.curDir = fc.getCurrentDirectory();
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            
            Vector data=new Vector();
            data.add(file.getName());
            data.add(""+file.length());
            String tp=java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("file");
            if(file.isDirectory())
                tp=java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("directory");
            data.add(tp);
            data.add(new Date(file.lastModified()));
            data.add(file);
            model.addRow(data);
            
            //log.append("Saving: " + file.getName() + "." + newline);
        } else {
            //log.append("Save command cancelled by user." + newline);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

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
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt)//GEN-FIRST:event_closeDialog
    {
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        try
        {        
            DragDrop files=new DragDrop(new javax.swing.JFrame(), true,new Locale("es"));
            /*
            files.setGateway(new URL("http://localhost/wb/WBAdmin/WBAd_utl_HTMLEditor/_rid/1/_mto/3/_mod/gateway"));
            files.setUpload(new URL("http://localhost/wb/WBAdmin/WBAd_utl_HTMLEditor/_rid/1/_mto/3/_mod/upload"));
            files.addFiles("images/tit_normatividad_inst.gif;images/pix_1x1.gif;images/divisional_nametop_bkgd.gif;images/ic_busqueda_verde.gif;images/menuleft_borde1.gif;images/menu3.gif;images/ico_internet.gif;images/ico_mapa.gif;images/ico_directorio.gif;images/ico_ayuda.gif;images/ico_home.gif;images/menu4.gif;images/bullet_bienvenida.gif;","E:/borra/Plantillas Banorte/Plantillas Banorte/inner/");
            files.setTopicMap("Test");
            files.setId("2");
            files.setVersion("1");    
            files.setType("Template");
            */
            files.setGateway(new URL("http://localhost/wb/WBAdmin/WBAd_utl_HTMLEditor/_rid/1/_mto/3/_mod/gateway"));
            files.setUpload(new URL("http://localhost/wb/WBAdmin/WBAd_utl_HTMLEditor/_rid/1/_mto/3/_mod/upload"));
            files.addFiles("images/tit_normatividad_inst.gif;images/pix_1x1.gif;images/divisional_nametop_bkgd.gif;images/ic_busqueda_verde.gif;images/menuleft_borde1.gif;images/menu3.gif;images/ico_internet.gif;images/ico_mapa.gif;images/ico_directorio.gif;images/ico_ayuda.gif;images/ico_home.gif;images/menu4.gif;images/bullet_bienvenida.gif;","E:/borra/Plantillas Banorte/Plantillas Banorte/inner/");
            files.setTopicMap("Test");
            files.setId("7");
            files.setVersion("1");    
            files.setSType("LocalContent");
            
            files.show();
            //System.out.println("FIN:");
        }catch(Exception e){System.out.println(e);}        
    }
    
    /**
     * Getter for property gateway.
     * @return Value of property gateway.
     */
    public URL getGateway()
    {
        return gateway;
    }    
    
    /**
     * Setter for property gateway.
     * @param gateway New value of property gateway.
     */
    public void setGateway(URL gateway)
    {
        this.gateway = gateway;
    }
    
    /**
     * Getter for property upload.
     * @return Value of property upload.
     */
    public URL getUpload()
    {
        return upload;
    }
    
    /**
     * Setter for property upload.
     * @param upload New value of property upload.
     */
    public void setUpload(URL upload)
    {
        this.upload = upload;
    }
    
    /**
     * Getter for property topicMap.
     * @return Value of property topicMap.
     */
    public java.lang.String getTopicMap()
    {
        return topicMap;
    }
    
    /**
     * Setter for property topicMap.
     * @param topicMap New value of property topicMap.
     */
    public void setTopicMap(java.lang.String topicMap)
    {
        this.topicMap = topicMap;
    }
    
    /**
     * Getter for property id.
     * @return Value of property id.
     */
    public java.lang.String getId()
    {
        return id;
    }
    
    /**
     * Setter for property id.
     * @param id New value of property id.
     */
    public void setId(java.lang.String id)
    {
        this.id = id;
    }
    
    /**
     * Getter for property version.
     * @return Value of property version.
     */
    public java.lang.String getVersion()
    {
        return version;
    }
    
    /**
     * Setter for property version.
     * @param version New value of property version.
     */
    public void setVersion(java.lang.String version)
    {
        this.version = version;
    }
    
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
    
    private String saveFiles(String file, InputStream in)
    {
        StringBuffer ret=new StringBuffer();
        try
        {
            URLConnection urlconn=upload.openConnection();
            urlconn.setUseCaches(false);
            if(jsess!=null)urlconn.setRequestProperty("Cookie", "JSESSIONID="+jsess);
            
            //System.out.println("PATHFILEWB:"+file);
            
            urlconn.setRequestProperty("ATTACHWB","TRUE");
            urlconn.setRequestProperty("PATHFILEWB",file);
            urlconn.setRequestProperty("TM",topicMap);
            urlconn.setRequestProperty("ID",id);
            urlconn.setRequestProperty("VER",version);
            urlconn.setRequestProperty("TYPE", type);
            urlconn.setDoOutput(true);
            OutputStream fout = urlconn.getOutputStream();
            
            //System.out.println("file:"+file);
            try
            {
                byte[] bfile = new byte[8192];
                int x;
                while ((x = in.read(bfile, 0, 8192)) > -1)
                {
                    jProgressBar1.setValue(jProgressBar1.getValue()+x);
                    fout.write(bfile,0, x);
                }
                in.close();
            } catch (Exception e)
            {
                e.printStackTrace(System.out);
                return "Error";
            }                 
            fout.flush();
            fout.close();

            BufferedReader in2 = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
            String inputLine;
            while ((inputLine = in2.readLine()) != null)
            {
                ret.append(inputLine);
                ret.append("\n");
            }
            in.close();
            //JOptionPane.showConfirmDialog(this,"Submited data send succesfuly !","WebBuilder",JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            System.out.println("Error to open service..."+e);
            JOptionPane.showConfirmDialog(this,"Error sendig HTML, "+e.getMessage(),"WebBuilder",JOptionPane.CLOSED_OPTION,JOptionPane.ERROR_MESSAGE);
        }
        return ret.toString();
    }    
    
    /**
     * Getter for property jsess.
     * @return Value of property jsess.
     */
    public java.lang.String getJSession()
    {
        return jsess;
    }    
    
    public void setJSess(String jsess)
    {
        this.jsess=jsess;
    }
    
    /**
     * Getter for property returnValue.
     * @return Value of property returnValue.
     */
    public boolean getReturnValue()
    {
        return returnValue;
    }
    
    /**
     * Setter for property returnValue.
     * @param returnValue New value of property returnValue.
     */
    public void setReturnValue(boolean returnValue)
    {
        this.returnValue = returnValue;
    }
    
    /**
     * Getter for property type.
     * @return Value of property type.
     */
    public java.lang.String getSType()
    {
        return type;
    }
    
    /**
     * Setter for property type.
     * @param type New value of property type.
     */
    public void setSType(java.lang.String type)
    {
        this.type = type;
    }
    
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
            if(x<len)
            {
                try
                {
                    File f=(File)model.getValueAt(x,4);
                    jLabel1.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("sent")+": "+f.getName());
                    ret=saveFiles(f.getName(),new FileInputStream(f));
                    jProgressBar1.repaint();
                    jLabel1.repaint();
                    jPanel1.repaint();
                    jPanel1.updateUI();
                    if(ret.length()>0)
                    {
                        JOptionPane.showMessageDialog(comp,ret,java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("sent.files"),JOptionPane.ERROR_MESSAGE);
                        err=true;
                    }
                    jLabel1.setText(f.getName()+" "+java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("sent"));
                    jLabel1.repaint();
                }catch(Exception e){System.out.println(e);}
                x++;
            }
            if(x==len)
            {
                if(err)
                {
                    JOptionPane.showMessageDialog(comp,java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("error.sending.files")+"...",java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("sent.files"),JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(comp,java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("files.sent")+"...",java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("sent.files"),JOptionPane.INFORMATION_MESSAGE);
                    jLabel1.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("ready"));
                }
                returnValue=true;
                setVisible(false);
                dispose();                
            }
            else
            {
                jLabel1.setText(java.util.ResourceBundle.getBundle("applets/htmleditor/locale",locale).getString("sent")+": "+((File)model.getValueAt(x,4)).getName());
                SwingUtilities.invokeLater(this);
            }
        }
    }    
}
