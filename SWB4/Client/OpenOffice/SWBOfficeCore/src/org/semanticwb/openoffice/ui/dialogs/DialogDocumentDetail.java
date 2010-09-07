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
    private static final String DOT = ".";
    private static final String FILE_SCHEMA = "file://";
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    
    /** Creates new form DisloDocumentDetail */
    public DialogDocumentDetail(OfficeDocument document) {
        super((Frame) null, ModalityType.TOOLKIT_MODAL);
        initComponents();
        this.setIconImage(ImageLoader.images.get("semius").getImage());
        this.setLocationRelativeTo(null);
        this.setModal(true);        

        DefaultTableModel model=(DefaultTableModel)this.jTableInformation.getModel();

        String name=java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("SIN_NOMBRE");
        String location=java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("SIN_UBICACIÓN");
        String tam=java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("EL_DOCUMENTO_NO_ESTA_GUARDADO");
        String observacion=tam;
        
        try
        {
            name=document.getLocalPath().getName();
            location=document.getLocalPath().getParentFile().getCanonicalPath();            
            observacion=validaNombre(document.getLocalPath().getAbsoluteFile());
            java.text.NumberFormat nf=NumberFormat.getInstance(Locale.getDefault());
            tam=nf.format(document.getLocalPath().length())+" "+java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("_BYTES");
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Object data[]={java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("NOMBRE_DEL_ARCHIVO"),name};
        model.addRow(data);
        
        data=new String[]{java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("UBICACIÓN"),location};
        model.addRow(data);

        

        data=new String[]{java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("IMAGENES_Y_DIBUJOS"),String.valueOf(document.getCountImages())};
        model.addRow(data);

        data=new String[]{java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("LIGAS"),String.valueOf(document.getLinks().length)};
        model.addRow(data);
        data=new String[]{java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("TAMAÑO_DEL_ARCHIVO"),tam};
        model.addRow(data);

        data=new String[]{java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("OBSERVACIÓN"),observacion};
        model.addRow(data);

        model=(DefaultTableModel)jTableDocuments.getModel();
        String[] links=document.getLinks();
        for(String archivo : links)
        {
            try
            {
                URI uribase=new URI(archivo);
                uribase=uribase.normalize();
                if(uribase.getScheme().equals(FILE_SCHEMA) || uribase.getScheme().equals("file"))
                {
                    File file=new File(uribase.toURL().getFile());
                    tam=file.length()+" "+java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("_BYTES");
                    name=file.getName();
                    location=file.getParentFile().getAbsolutePath();
                    String exists=java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("NO");
                    if(file.exists())
                    {
                        exists=java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("SÍ");
                    }
                    observacion=validaNombre(file);
                    data=new Object[]{name,location,tam,exists,observacion};
                    model.addRow(data);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        model=(DefaultTableModel)jTablePages.getModel();
        for(String archivo : links)
        {
            try
            {
                URI uribase=new URI(archivo);
                uribase=uribase.normalize();
                if(uribase.getScheme().equals(HTTP) || uribase.getScheme().equals(HTTPS))
                {
                    String exists=java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("NO");
                    URL url =new URL(archivo);
                    HttpURLConnection urlCon = (HttpURLConnection) url.openConnection ();
                    if(urlCon.getResponseCode()==200)
                    {
                        exists=java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("SÍ");
                    }
                    data=new Object[]{archivo,exists};
                    model.addRow(data);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private String validaNombre(File file)
    {
        int pos=file.getName().indexOf(DOT);
        String name=file.getName();
        if(pos!=-1)
        {
            name=file.getName().substring(0,pos);
        }
        if(name.length()>40)
        {
            return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("EL_NOMBRE_DEL_ARCHIVO_ES_MAYOR_A_40_CARACTERES");
        }
        char[] letras=name.toCharArray();
        for(int i=0;i<letras.length;i++)
        {
            char letra=letras[i];
            if(Character.isWhitespace(letra))
            {
                if(letra!='_')
                {
                    return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("EL_NOMBRE_DEL_ARCHIVO_TIENE_ESPACIOS");
                }
            }
            else if(!(Character.isDigit(letra) || Character.isLetter(letra)))
            {
                if(letra!='_')
                {
                    return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("EL_NOMBRE_DEL_ARCHIVO_TIENE_CARACTERES_NO_VÁLIDOS:")+letra;
                }
            }
            else if(letra>123)
            {
                if(letra!='_')
                {
                    return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("EL_NOMBRE_DEL_ARCHIVO_TIENE_CARACTERES_NO_VÁLIDOS:")+letra;
                }
            }
        }
        return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail").getString("EL_NOMBRE_DEL_ARCHIVO_ES_CORRECTO");
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
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogDocumentDetail"); // NOI18N
        setTitle(bundle.getString("INFORMACIÓN_DETALLE_DEL_DOCUMENTO")); // NOI18N

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 40));

        jButtonClose.setText(bundle.getString("CERRAR")); // NOI18N
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

        jTabbedPane1.addTab(bundle.getString("INFORMACIÓN_DEL_DOCUMENTO"), jPanelInformation); // NOI18N

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

        jTabbedPane1.addTab(bundle.getString("DOCUMENTOS_INCRUSTADOS"), jPanelDocuments); // NOI18N

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

        jTabbedPane1.addTab(bundle.getString("PÁGINAS_INCRUSTADAS"), jPanelPages); // NOI18N

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

