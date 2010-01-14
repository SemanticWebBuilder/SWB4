/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DisloDocumentDetail.java
 *
 * Created on 11/01/2010, 01:50:26 PM
 */

package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Frame;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
import org.semanticwb.openoffice.OfficeDocument;
import org.semanticwb.openoffice.ui.icons.ImageLoader;

/**
 *
 * @author victor.lorenzana
 */
public class DialogDocumentDetail extends javax.swing.JDialog {
    
    /** Creates new form DisloDocumentDetail */
    public DialogDocumentDetail(OfficeDocument document) {
        super((Frame) null, ModalityType.TOOLKIT_MODAL);
        initComponents();
        this.setIconImage(ImageLoader.images.get("semius").getImage());
        this.setLocationRelativeTo(null);
        this.setModal(true);        

        DefaultTableModel model=(DefaultTableModel)this.jTableInformation.getModel();

        String name="Sin nombre";
        String location="Sin ubicación";
        String tam="El documento no esta guardado";
        String observacion=tam;
        
        try
        {
            name=document.getLocalPath().getName();
            location=document.getLocalPath().getParentFile().getCanonicalPath();            
            observacion=validaNombre(document.getLocalPath().getAbsoluteFile());
            java.text.NumberFormat nf=NumberFormat.getInstance(Locale.getDefault());
            tam=""+nf.format(document.getLocalPath().length())+" bytes";
            
        }
        catch(Exception e){}
        Object data[]={"Nombre del archivo",name};
        model.addRow(data);
        
        data=new String[]{"Ubicación",location};
        model.addRow(data);

        

        data=new String[]{"Imagenes y Dibujos",""+document.getCountImages()};
        model.addRow(data);

        data=new String[]{"Ligas",""+document.getLinks().length};
        model.addRow(data);
        data=new String[]{"Tamaño del archivo",tam};
        model.addRow(data);

        data=new String[]{"Observación",observacion};
        model.addRow(data);

        model=(DefaultTableModel)jTableDocuments.getModel();
        String[] links=document.getLinks();
        for(String archivo : links)
        {
            try
            {
                URI uribase=new URI(archivo);
                uribase=uribase.normalize();
                if(uribase.getScheme().equals("file://"))
                {
                    File file=new File(uribase.toURL().getFile());
                    tam=file.length()+" bytes";
                    name=file.getName();
                    location=file.getParentFile().getAbsolutePath();
                    String exists="No";
                    if(file.exists())
                    {
                        exists="Sí";
                    }
                    observacion=validaNombre(file);
                    data=new Object[]{name,location,tam,exists,observacion};
                    model.addRow(data);
                }
            }
            catch(Exception e){}
        }
        model=(DefaultTableModel)jTablePages.getModel();
        for(String archivo : links)
        {
            try
            {
                URI uribase=new URI(archivo);
                uribase=uribase.normalize();
                if(uribase.getScheme().equals("http") || uribase.getScheme().equals("https"))
                {
                    String exists="No";
                    URL url =new URL(archivo);
                    HttpURLConnection urlCon = (HttpURLConnection) url.openConnection ();
                    if(urlCon.getResponseCode()==200)
                    {
                        exists="Sí";
                    }
                    data=new Object[]{archivo,exists};
                    model.addRow(data);
                }
            }
            catch(Exception e){}
        }
    }

    private String validaNombre(File file)
    {
        int pos=file.getName().indexOf(".");
        String name=file.getName();
        if(pos!=-1)
        {
            name=file.getName().substring(0,pos);
        }
        if(name.length()>40)
        {
            return "El nombre del archivo es mayor a 40 caracteres";
        }
        char[] letras=name.toCharArray();
        for(int i=0;i<letras.length;i++)
        {
            char letra=letras[i];
            if(Character.isWhitespace(letra))
            {
                return "El nombre del archivo tiene espacios";
            }
            else if(!(Character.isDigit(letra) || Character.isLetter(letra)))
            {
                return "El nombre del archivo tiene caracteres no válidos:"+letra;
            }
            else if(letra>123)
            {
                return "El nombre del archivo tiene caracteres no válidos:"+letra;
            }
        }
        return "El nombre del archivo es correcto";
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
        jButtonClose = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelInformation = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableInformation = new javax.swing.JTable();
        jPanelDocuments = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDocuments = new javax.swing.JTable();
        jPanelPages = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablePages = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Información detalle del documento");

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 40));

        jButtonClose.setText("Cerrar");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(327, Short.MAX_VALUE)
                .addComponent(jButtonClose)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanelInformation.setLayout(new java.awt.BorderLayout());

        jTableInformation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableInformation.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableInformation);
        jTableInformation.getColumnModel().getColumn(0).setResizable(false);
        jTableInformation.getColumnModel().getColumn(1).setResizable(false);

        jPanelInformation.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Información del documento", jPanelInformation);

        jPanelDocuments.setLayout(new java.awt.BorderLayout());

        jTableDocuments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Archivo", "Ubicación", "Tamaño", "Existe", "Observación"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDocuments.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTableDocuments);

        jPanelDocuments.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Documentos Incrustados", jPanelDocuments);

        jPanelPages.setLayout(new java.awt.BorderLayout());

        jTablePages.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Página", "Existe"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePages.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTablePages);

        jPanelPages.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Páginas incrustadas", jPanelPages);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCloseActionPerformed
    {//GEN-HEADEREND:event_jButtonCloseActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelDocuments;
    private javax.swing.JPanel jPanelInformation;
    private javax.swing.JPanel jPanelPages;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableDocuments;
    private javax.swing.JTable jTableInformation;
    private javax.swing.JTable jTablePages;
    // End of variables declaration//GEN-END:variables

}

