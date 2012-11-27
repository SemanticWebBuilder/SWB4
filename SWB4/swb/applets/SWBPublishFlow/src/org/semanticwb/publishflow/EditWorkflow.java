/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.publishflow;

import javax.swing.*;
import java.net.*;
import java.util.*;
import java.awt.*;


import applets.commons.*;
import com.sun.javafx.runtime.TypeInfo;
import com.sun.javafx.runtime.sequence.Sequence;



public class EditWorkflow extends javax.swing.JDialog
{
    public boolean cancel;
    private final String PRM_JSESS = "jsess";
    private final String PRM_CGIPATH = "cgipath";
    private final String PRM_TOPICMAP = "tm";
    private static String cgiPath = "/gtw.jsp";
    private static String jsess = "";
    public static String tm = "";
    public String url_script;    
    private static URL url = null;
    public Sequence<? extends String> resourceTypes;
    WBConnection con;
    String name;
    String description;
    String version;
    
    
    Locale locale=new Locale("es");

   public EditWorkflow()
    {
        super((Frame) null, true);
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ue)
        {
            // No debe hacer nada
            System.out.println(ue.getMessage());
        }
        initComponents();        
        pack();
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.jLabelDescription.setVisible(false);
        this.jTextAreaDescription.setVisible(false);
        this.jLabelName.setVisible(false);
        this.jTextFieldName.setVisible(false);
        this.jScrollPaneAreaDescription.setVisible(false);
        this.jCheckBoxEdit.setVisible(false);
        
   }

    public void init()
    {
        loadResources();
        if(resourceTypes!=null)
        {
            for(String res : resourceTypes)
            {
                String[] values=res.split("@");
                String id=values[0];
                String tmres=values[1];
                jTableResourceTypeModel model=(jTableResourceTypeModel)jTableTipos_Recursos.getModel();
                for(int i=0;i<model.getRowCount();i++)
                {
                    ResourceType type=model.getResourceType(i);
                    if(type.getID().equals(id) && type.getTopicMap().equals(tmres))
                    {
                        type.setSelected(true);
                    }
                }
            }
        }
        this.jTextFieldVersion.setText(version);
        this.jTextFieldName.setText(name);
        this.jTextAreaDescription.setText(description);
    }
    

   

    public void loadResources()
    {

        jTableResourceTypeModel model = new jTableResourceTypeModel(locale);
        this.jTableTipos_Recursos.setModel(model);
        if(tm!=null && con!=null)
        {
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getResourceTypeCat</cmd><tm>" + tm + "</tm></req>";
            xml = con.getData(xml);
            System.out.println("xml resourceType: "+xml);
            if(xml!=null && !xml.trim().equals(""))
            {
                WBXMLParser parser = new WBXMLParser();
                WBTreeNode exml = parser.parse(xml);
                if (exml!=null && exml.getFirstNode() != null)
                {
                    Vector nodes = exml.getFirstNode().getNodes();
                    for (int i = 0; i < nodes.size(); i++)
                    {
                        WBTreeNode node = (WBTreeNode) nodes.elementAt(i);
                        if (node.getName().equalsIgnoreCase("resourceType"))
                        {
                            WBTreeNode ereosurce = (WBTreeNode) node;
                            String sdescription = ereosurce.getFirstNode().getFirstNode().getText();
                            ResourceType type = new ResourceType(ereosurce.getAttribute("id"), ereosurce.getAttribute("name"), sdescription, ereosurce.getAttribute("topicmap"), ereosurce.getAttribute("topicmapname"));
                            model.addResourceType(type);
                        }

                    }
                }
            }
        }
    /*Iterator eresources=exml.getFirstNode().getNodesbyName("resourceType");
    while(eresources.hasNext())
    {
    WBTreeNode ereosurce=(WBTreeNode)eresources.next();
    String description=ereosurce.getFirstNode().getFirstNode().getText();
    ResourceType type=new ResourceType(ereosurce.getAttribute("id"), ereosurce.getAttribute("name"),description,ereosurce.getAttribute("topicmap"),ereosurce.getAttribute("topicmapname"));
    model.addResourceType(type);
    }*/
    }

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelPropiedades = new javax.swing.JPanel();
        jLabelName = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabelDescription = new javax.swing.JLabel();
        jLabelVersion = new javax.swing.JLabel();
        jTextFieldVersion = new javax.swing.JTextField();
        jCheckBoxEdit = new javax.swing.JCheckBox();
        jScrollPaneAreaDescription = new javax.swing.JScrollPane();
        jTextAreaDescription = new javax.swing.JTextArea();
        jPanelTiposRecursos = new javax.swing.JPanel();
        jScrollPaneTiposRescursos = new javax.swing.JScrollPane();
        jTableTipos_Recursos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow"); // NOI18N
        setTitle(bundle.getString("title")); // NOI18N

        jPanel2.setPreferredSize(new java.awt.Dimension(400, 300));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanelPropiedades.setFont(new java.awt.Font("Arial", 0, 12));
        jPanelPropiedades.setLayout(null);

        jLabelName.setLabelFor(jTextFieldName);
        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow",locale); // NOI18N
        jLabelName.setText(bundle1.getString("nombre")); // NOI18N
        jPanelPropiedades.add(jLabelName);
        jLabelName.setBounds(10, 60, 52, 14);

        jTextFieldName.setEditable(false);
        jPanelPropiedades.add(jTextFieldName);
        jTextFieldName.setBounds(90, 60, 290, 19);

        jLabelDescription.setLabelFor(jTextAreaDescription);
        jLabelDescription.setText(bundle1.getString("Description")); // NOI18N
        jPanelPropiedades.add(jLabelDescription);
        jLabelDescription.setBounds(10, 100, 58, 14);

        jLabelVersion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelVersion.setLabelFor(jTextFieldVersion);
        jLabelVersion.setText(bundle1.getString("version")); // NOI18N
        jPanelPropiedades.add(jLabelVersion);
        jLabelVersion.setBounds(20, 20, 47, 15);

        jTextFieldVersion.setEditable(false);
        jTextFieldVersion.setText("1.0");
        jPanelPropiedades.add(jTextFieldVersion);
        jTextFieldVersion.setBounds(90, 20, 27, 19);

        jCheckBoxEdit.setText(bundle.getString("modificarContenido")); // NOI18N
        jPanelPropiedades.add(jCheckBoxEdit);
        jCheckBoxEdit.setBounds(0, 210, 235, 23);

        jTextAreaDescription.setEditable(false);
        jTextAreaDescription.setLineWrap(true);
        jTextAreaDescription.setWrapStyleWord(true);
        jTextAreaDescription.setPreferredSize(new java.awt.Dimension(290, 20));
        jScrollPaneAreaDescription.setViewportView(jTextAreaDescription);

        jPanelPropiedades.add(jScrollPaneAreaDescription);
        jScrollPaneAreaDescription.setBounds(90, 100, 292, 100);

        jTabbedPane1.addTab(bundle1.getString("propiedades"), jPanelPropiedades); // NOI18N

        jPanelTiposRecursos.setLayout(new java.awt.BorderLayout());

        jTableTipos_Recursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneTiposRescursos.setViewportView(jTableTipos_Recursos);

        jPanelTiposRecursos.add(jScrollPaneTiposRescursos, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(bundle1.getString("Tipos_de_rescursos"), jPanelTiposRecursos); // NOI18N

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonOk.setText("Aceptar");
        jButtonOk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonOk);

        jButtonCancel.setText("Cancelar");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonCancel);

        jPanel2.add(jPanel1, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonOkActionPerformed
    {//GEN-HEADEREND:event_jButtonOkActionPerformed
        if (this.jTextFieldName.getText().trim().equals(""))
        {
            this.jTabbedPane1.setSelectedIndex(0);
            this.jTextFieldName.grabFocus();
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow", locale).getString("indicar_titutlo"), java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.jTextAreaDescription.getText().trim().equals(""))
        {
            this.jTabbedPane1.setSelectedIndex(0);
            this.jTextAreaDescription.grabFocus();
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow", locale).getString("Favor_de_indicar_la_descripcion_del_flujo"), java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean resselected = false;
        jTableResourceTypeModel modelres = (jTableResourceTypeModel) this.jTableTipos_Recursos.getModel();
        for (int i = 0; i < modelres.getRowCount(); i++)
        {
            if (modelres.getResourceType(i).isSelected().booleanValue())
            {
                resselected = true;
                break;
            }
        }
        if (!resselected)
        {
            this.jTabbedPane1.setSelectedIndex(1);
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow", locale).getString("Debe_indicar_a_que_tipos_de_recursos_aplica_el_flujo_de_publicacion"), java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        HashSet<String> otypes = new HashSet<String>();
        for (int i = 0; i < modelres.getRowCount(); i++)
        {
            if (modelres.getResourceType(i).isSelected().booleanValue())
            {
                ResourceType type=modelres.getResourceType(i);
                otypes.add(type.getID()+"@"+type.getTopicMap());
                
            }
        }
        TypeInfo t = TypeInfo.getTypeInfo(String.class);
        resourceTypes = com.sun.javafx.runtime.sequence.Sequences.fromCollection(t, otypes);
        cancel=true;
        this.setVisible(false);
    }//GEN-LAST:event_jButtonOkActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCancelActionPerformed
    {//GEN-HEADEREND:event_jButtonCancelActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCancelActionPerformed
    
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JCheckBox jCheckBoxEdit;
    private javax.swing.JLabel jLabelDescription;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelVersion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelPropiedades;
    private javax.swing.JPanel jPanelTiposRecursos;
    private javax.swing.JScrollPane jScrollPaneAreaDescription;
    private javax.swing.JScrollPane jScrollPaneTiposRescursos;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableTipos_Recursos;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldVersion;
    // End of variables declaration//GEN-END:variables
}
