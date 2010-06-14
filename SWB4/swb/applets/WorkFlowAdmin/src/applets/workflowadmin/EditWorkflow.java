/**  
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
**/ 
 
/*
 * EditWorkflow.java
 *
 * Created on 11 de octubre de 2004, 05:59 PM
 */
package applets.workflowadmin;

import java.awt.event.MouseEvent;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;


import applets.commons.*;

/**
 * Formulario para editar un flujo der publicaci�n, contiene tres pesta�as, la de
 * propiedades, la de actividades y la de dise�o.
 * @author Victor Lorenzana
 */
public class EditWorkflow extends javax.swing.JApplet
{

    private final String PRM_JSESS = "jsess";
    private final String PRM_CGIPATH = "cgipath";
    private final String PRM_TOPICMAP = "tm";
    private static String cgiPath = "/gtw.jsp";
    private static String jsess = "";
    public static String tm = "";
    //public String url_script;
    private JActivityFlowPanel jPanelDesign = null;
    private static URL url = null;
    /** Initializes the applet EditWorkflow */
    public Workflow workflow;
    Locale locale;

   
    @Override
    public void init()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ue)
        {
            // No debe hacer nada
            System.out.println(ue.getMessage());
        }
        locale = Locale.getDefault();
        if (this.getParameter("locale") != null && !this.getParameter("locale").equals(""))
        {
            try
            {

                locale = new Locale(this.getParameter("locale"));
            }
            catch (Exception e)
            {
                e.printStackTrace(System.out);
            }
        }
        workflow = new Workflow(locale);
        initComponents();
        jsess = this.getParameter(PRM_JSESS);
        cgiPath = this.getParameter(PRM_CGIPATH);
        tm = this.getParameter(PRM_TOPICMAP);
        String id = this.getParameter("idworkflow");
        //url_script = this.getParameter("script");

        try
        {
            url = new URL(getCodeBase().getProtocol(), getCodeBase().getHost(), getCodeBase().getPort(), cgiPath);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        loadResources();
        if (id != null && !id.equals(""))
        {
            loadWorkflow(id);
        }
        this.setJMenuBar(this.jMenuBar1);

        this.jTextFieldName.grabFocus();
        jPanelDesign = new JActivityFlowPanel(workflow.getActivitiesModel(), this.jTableActivitiesG, locale);
        this.jScrollPaneDesign.setViewportView(this.jPanelDesign);
        workflow.getActivitiesModel().setTable(this.jTableActividades);
        this.jTableActividades.setModel(workflow.getActivitiesModel());
        workflow.getActivitiesModel().setTable(this.jTableActividades);
        DefaultListSelectionModel selectionmodel = new DefaultListSelectionModel();
        this.jTableActividades.setSelectionModel(selectionmodel);
        SelectActivities select = new SelectActivities(this.jPopupMenu1);
        this.jTableActividades.addMouseListener(select);
        this.jScrollPaneActividades.addMouseListener(select);
        this.jTextFieldVersion.setText(workflow.getVersion());
        
        this.jLabelDescription.setVisible(false);
        this.jTextAreaDescription.setVisible(false);
        this.jLabelName.setVisible(false);
        this.jTextFieldName.setVisible(false);
        this.jScrollPaneAreaDescription.setVisible(false);
        this.jCheckBoxEdit.setVisible(false);
    }

    public void loadWorkflow(String id)
    {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getWorkflow</cmd><id>" + id + "</id><tm>" + tm + "</tm></req>";
        String respxml = EditWorkflow.getData(xml, this);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode exml = parser.parse(respxml);
        if (exml.getFirstNode() != null && exml.getFirstNode().getFirstNode() != null)
        {
            WBTreeNode wfnode = exml.getFirstNode().getFirstNode();
            if (wfnode.getName().equals("workflow"))
            {
                this.workflow = new Workflow(wfnode, id, locale);
                this.jTextFieldName.setText(this.workflow.getName());
                if (workflow.canEdit())
                {
                    this.jCheckBoxEdit.setSelected(true);
                }
                else
                {
                    this.jCheckBoxEdit.setSelected(false);
                }
                this.jTextAreaDescription.setText(this.workflow.getDescription());
                this.jTextFieldVersion.setText(this.workflow.getVersion());
                Iterator it = this.workflow.getResourcesModel().iterator();
                while (it.hasNext())
                {
                    ResourceType res = (ResourceType) it.next();
                    jTableResourceTypeModel resmodel = (jTableResourceTypeModel) this.jTableTipos_Recursos.getModel();
                    Iterator recursos = resmodel.iterator();
                    while (recursos.hasNext())
                    {
                        ResourceType rescat = (ResourceType) recursos.next();
                        if (rescat.equals(res))
                        {
                            rescat.setSelected(true);
                        }
                    }

                }
                jTableTipos_Recursos.updateUI();
            }
        }
    }

    public void loadResources()
    {

        jTableResourceTypeModel model = new jTableResourceTypeModel(locale);
        this.jTableTipos_Recursos.setModel(model);
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getResourceTypeCat</cmd><tm>" + tm + "</tm></req>";
        xml = EditWorkflow.getData(xml, this);
        WBXMLParser parser = new WBXMLParser();
        WBTreeNode exml = parser.parse(xml);
        if (exml.getFirstNode() != null)
        {
            Vector nodes = exml.getFirstNode().getNodes();
            for (int i = 0; i < nodes.size(); i++)
            {
                WBTreeNode node = (WBTreeNode) nodes.elementAt(i);
                if (node.getName().equalsIgnoreCase("resourceType"))
                {
                    WBTreeNode ereosurce = (WBTreeNode) node;
                    String description = ereosurce.getFirstNode().getFirstNode().getText();
                    ResourceType type = new ResourceType(ereosurce.getAttribute("id"), ereosurce.getAttribute("name"), description, ereosurce.getAttribute("topicmap"), ereosurce.getAttribute("topicmapname"));
                    model.addResourceType(type);
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItemAdd = new javax.swing.JMenuItem();
        jMenuItemEdit = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemUP = new javax.swing.JMenuItem();
        jMenuItemDown = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        jMenuItemDelete = new javax.swing.JMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemMAdd = new javax.swing.JMenuItem();
        jMenuItemMEdit = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItemMUP = new javax.swing.JMenuItem();
        jMenuItemMDown = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        jMenuItemMDelete = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuItemSave = new javax.swing.JMenuItem();
        jPaneltoolbar = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonSave = new javax.swing.JButton();
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
        jPanelActividades = new javax.swing.JPanel();
        jScrollPaneActividades = new javax.swing.JScrollPane();
        jTableActividades = new javax.swing.JTable();
        jPanelDise�o = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableActivitiesG = new javax.swing.JTable();
        jScrollPaneDesign = new javax.swing.JScrollPane();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow",locale); // NOI18N
        jMenuItemAdd.setText(bundle.getString("Add")); // NOI18N
        jMenuItemAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemAdd);

        jMenuItemEdit.setText(bundle.getString("Edit")); // NOI18N
        jMenuItemEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemEdit);
        jPopupMenu1.add(jSeparator1);

        jMenuItemUP.setText(bundle.getString("up_text")); // NOI18N
        jMenuItemUP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemUPActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemUP);

        jMenuItemDown.setText(bundle.getString("down_text")); // NOI18N
        jMenuItemDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDownActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemDown);
        jPopupMenu1.add(jSeparator4);

        jMenuItemDelete.setText(bundle.getString("Delete")); // NOI18N
        jMenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemDelete);

        jMenu1.setMnemonic(java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow",locale).getString("Herramientas").charAt(0));
        jMenu1.setText(bundle.getString("Herramientas")); // NOI18N

        jMenuItemMAdd.setText(bundle.getString("Add")); // NOI18N
        jMenuItemMAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMAddActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemMAdd);

        jMenuItemMEdit.setText(bundle.getString("Edit")); // NOI18N
        jMenuItemMEdit.setEnabled(false);
        jMenuItemMEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMEditActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemMEdit);
        jMenu1.add(jSeparator2);

        jMenuItemMUP.setText(bundle.getString("up_text")); // NOI18N
        jMenuItemMUP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMUPActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemMUP);

        jMenuItemMDown.setText(bundle.getString("down_text")); // NOI18N
        jMenuItemMDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMDownActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemMDown);
        jMenu1.add(jSeparator5);

        jMenuItemMDelete.setText(bundle.getString("Delete")); // NOI18N
        jMenuItemMDelete.setEnabled(false);
        jMenuItemMDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMDeleteActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemMDelete);
        jMenu1.add(jSeparator3);

        jMenuItemSave.setText(bundle.getString("Guardar")); // NOI18N
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemSave);

        jMenuBar1.add(jMenu1);

        jPaneltoolbar.setPreferredSize(new java.awt.Dimension(10, 30));
        jPaneltoolbar.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);

        jButtonSave.setFont(new java.awt.Font("Arial", 1, 12));
        jButtonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/applets/workflowadmin/images/save.png"))); // NOI18N
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonSave);

        jPaneltoolbar.add(jToolBar1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPaneltoolbar, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanelPropiedades.setFont(new java.awt.Font("Arial", 0, 12));
        jPanelPropiedades.setLayout(null);

        jLabelName.setLabelFor(jTextFieldName);
        jLabelName.setText(bundle.getString("nombre")); // NOI18N
        jPanelPropiedades.add(jLabelName);
        jLabelName.setBounds(240, 210, 41, 14);

        jTextFieldName.setEditable(false);
        jPanelPropiedades.add(jTextFieldName);
        jTextFieldName.setBounds(150, 210, 11, 19);

        jLabelDescription.setLabelFor(jTextAreaDescription);
        jLabelDescription.setText(bundle.getString("Description")); // NOI18N
        jPanelPropiedades.add(jLabelDescription);
        jLabelDescription.setBounds(300, 220, 58, 14);

        jLabelVersion.setFont(new java.awt.Font("Arial", 1, 12));
        jLabelVersion.setLabelFor(jTextFieldVersion);
        jLabelVersion.setText(bundle.getString("version")); // NOI18N
        jPanelPropiedades.add(jLabelVersion);
        jLabelVersion.setBounds(10, 20, 47, 15);

        jTextFieldVersion.setEditable(false);
        jTextFieldVersion.setText("1.0");
        jPanelPropiedades.add(jTextFieldVersion);
        jTextFieldVersion.setBounds(70, 20, 27, 19);

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow"); // NOI18N
        jCheckBoxEdit.setText(bundle1.getString("modificarContenido")); // NOI18N
        jPanelPropiedades.add(jCheckBoxEdit);
        jCheckBoxEdit.setBounds(10, 50, 235, 23);

        jTextAreaDescription.setEditable(false);
        jTextAreaDescription.setLineWrap(true);
        jTextAreaDescription.setWrapStyleWord(true);
        jScrollPaneAreaDescription.setViewportView(jTextAreaDescription);

        jPanelPropiedades.add(jScrollPaneAreaDescription);
        jScrollPaneAreaDescription.setBounds(70, 120, 102, 20);

        jTabbedPane1.addTab(bundle.getString("propiedades"), jPanelPropiedades); // NOI18N

        jPanelTiposRecursos.setLayout(new java.awt.BorderLayout());

        jTableTipos_Recursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneTiposRescursos.setViewportView(jTableTipos_Recursos);

        jPanelTiposRecursos.add(jScrollPaneTiposRescursos, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(bundle.getString("Tipos_de_rescursos"), jPanelTiposRecursos); // NOI18N

        jPanelActividades.setLayout(new java.awt.BorderLayout());

        jTableActividades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableActividades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableActividadesMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableActividadesMouseReleased(evt);
            }
        });
        jScrollPaneActividades.setViewportView(jTableActividades);

        jPanelActividades.add(jScrollPaneActividades, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(bundle.getString("actividades"), jPanelActividades); // NOI18N

        jPanelDise�o.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(10, 100));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jTableActivitiesG.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableActivitiesG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableActivitiesGMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTableActivitiesG);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanelDise�o.add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanelDise�o.add(jScrollPaneDesign, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(bundle.getString("ver"), jPanelDise�o); // NOI18N

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemMDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMDownActionPerformed
        this.jMenuItemDownActionPerformed(null);
    }//GEN-LAST:event_jMenuItemMDownActionPerformed

    private void jMenuItemMUPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMUPActionPerformed
        this.jMenuItemUPActionPerformed(null);
    }//GEN-LAST:event_jMenuItemMUPActionPerformed

    private void jMenuItemDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDownActionPerformed
        if (this.jTableActividades.getSelectedRows().length > 0)
        {
            int[] irows = this.jTableActividades.getSelectedRows();
            for (int i = 0; i < irows.length; i++)
            {
                jTableActivitiesModel model = (jTableActivitiesModel) this.jTableActividades.getModel();
                Activity activity = model.getActivity(irows[i]);
                int pos = model.down(activity);
                this.jTableActividades.setRowSelectionInterval(pos, pos);
            }
        }
    }//GEN-LAST:event_jMenuItemDownActionPerformed

    private void jMenuItemUPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemUPActionPerformed
        if (this.jTableActividades.getSelectedRows().length > 0)
        {
            int[] irows = this.jTableActividades.getSelectedRows();
            for (int i = 0; i < irows.length; i++)
            {
                jTableActivitiesModel model = (jTableActivitiesModel) this.jTableActividades.getModel();
                Activity activity = model.getActivity(irows[i]);
                int pos = model.up(activity);
                this.jTableActividades.setRowSelectionInterval(pos, pos);
            }
        }
    }//GEN-LAST:event_jMenuItemUPActionPerformed

    private void jTableActivitiesGMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableActivitiesGMousePressed
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1 && this.jTableActivitiesG.getSelectedRowCount() > 0 && this.jTableActivitiesG.getSelectedRow() > 0 && this.jTableActivitiesG.getSelectedRow() < (this.jTableActivitiesG.getRowCount() - 1))
        {
            ActivityGModel model = (ActivityGModel) this.jTableActivitiesG.getModel();
            ActivityG act = model.getActivity(this.jTableActivitiesG.getSelectedRow());
            ActivityEdition frm = new ActivityEdition(act.getActivity(), this.workflow.getActivitiesModel(), locale);
            frm.setModal(true);
            frm.setLocation(200, 200);
            frm.setSize(500, 400);
            frm.show();
        }
    }//GEN-LAST:event_jTableActivitiesGMousePressed

    private void jTableActividadesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableActividadesMouseReleased
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1)
        {
            if (this.jTableActividades.getSelectedRowCount() > 0)
            {
                this.jMenuItemMEdit.setEnabled(true);
                this.jMenuItemMDelete.setEnabled(true);

                this.jMenuItemUP.setEnabled(true);
                this.jMenuItemDown.setEnabled(true);
                this.jMenuItemMDown.setEnabled(true);
                this.jMenuItemMUP.setEnabled(true);
            }
            else
            {
                this.jMenuItemMEdit.setEnabled(false);
                this.jMenuItemMDelete.setEnabled(false);

                this.jMenuItemUP.setEnabled(false);
                this.jMenuItemDown.setEnabled(false);
                this.jMenuItemMDown.setEnabled(false);
                this.jMenuItemMUP.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jTableActividadesMouseReleased

    private void jMenuItemMDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMDeleteActionPerformed
        jMenuItemDeleteActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemMDeleteActionPerformed

    private void jMenuItemMEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMEditActionPerformed
        jMenuItemEditActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemMEditActionPerformed

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
        jButtonSaveActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemSaveActionPerformed

    private void jMenuItemMAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMAddActionPerformed
        jMenuItemAddActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemMAddActionPerformed

    private void jMenuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDeleteActionPerformed
        if (this.jTableActividades.getSelectedRowCount() > 0)
        {
            int[] irows = this.jTableActividades.getSelectedRows();
            for (int i = 0; i < irows.length; i++)
            {
                int irow = irows[i];
                this.workflow.getActivitiesModel().removeActivity(this.workflow.getActivitiesModel().getActivity(irow));
            }
        }
    }//GEN-LAST:event_jMenuItemDeleteActionPerformed

    private void jTableActividadesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableActividadesMousePressed
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2)
        {
            jMenuItemEditActionPerformed(null);
        }
    }//GEN-LAST:event_jTableActividadesMousePressed

    private void jMenuItemEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditActionPerformed
        this.workflow.getActivitiesModel().setTable(this.jTableActividades);
        if (this.jTableActividades.getSelectedRows().length > 0)
        {
            Activity activity = workflow.getActivitiesModel().getActivity(this.jTableActividades.getSelectedRow());
            ActivityEdition frm = new ActivityEdition(activity, this.workflow.getActivitiesModel(), locale);
            frm.setModal(true);
            frm.setLocation(200, 200);
            frm.setSize(500, 400);
            frm.show();
        }
        this.jTableActividades.updateUI();
    }//GEN-LAST:event_jMenuItemEditActionPerformed
    public static String getData(String xml, Component cmp)
    {

        StringBuffer ret = new StringBuffer();
        try
        {

            URLConnection urlconn = url.openConnection();
            urlconn.setUseCaches(false);
            if (jsess != null)
            {
                urlconn.setRequestProperty("Cookie", "JSESSIONID=" + jsess);
            }
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
            JOptionPane.showMessageDialog(cmp, e.getMessage(), "INFOTEC WebBuilder 3.0", JOptionPane.ERROR_MESSAGE);
            System.out.println(java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow").getString("Error_to_open_service...") + e);
        }
        return ret.toString();
    }

    public boolean isCorrect()
    {
        HashSet olinks = new HashSet();
        HashSet oends = new HashSet();
        boolean hasEnd = false;
        Iterator activities = this.workflow.getActivitiesModel().getActivities().iterator();
        while (activities.hasNext())
        {
            Activity activity = (Activity) activities.next();
            Iterator links = activity.getLinks().iterator();
            while (links.hasNext())
            {
                Link link = (Link) links.next();
                olinks.add(link);
                if (link.getActivityTo() instanceof EndActivity)
                {
                    oends.add(link);
                    hasEnd = true;
                }
            }
        }
        if (!hasEnd)
        {
            this.jTabbedPane1.setSelectedIndex(2);
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("No_tiene_ninguna_actividad_que_permitar_terminar_con_el_flujo_de_publicacion"), java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Iterator ends = oends.iterator();
        while (ends.hasNext())
        {
            Link lend = (Link) ends.next();
            if (checkPath((EndActivity) lend.getActivityTo(), olinks))
            {
                return true;
            }
        }

        return true;
    }

    public boolean checkPath(EndActivity endactivity, HashSet links)
    {
        Activity activity = getActivityFrom(endactivity, links);
        if (activity.equals(this.workflow.getActivitiesModel().getActivity(0)))
        {
            return true;
        }
        while (activity != null)
        {
            activity = getActivityFrom(activity, links);
            if (activity != null && activity.equals(this.workflow.getActivitiesModel().getActivity(0)))
            {
                return true;
            }
        }
        return false;
    }

    public Activity getActivityFrom(Activity activity, HashSet links)
    {
        Iterator itLinks = links.iterator();
        while (itLinks.hasNext())
        {
            Link link = (Link) itLinks.next();
            if (link.getActivityTo().equals(activity))
            {
                return link.getActivityFrom();
            }
        }
        return null;
    }

    static public String encode(String data, String enc) throws java.io.UnsupportedEncodingException, java.io.IOException
    {
        ByteArrayOutputStream sw = new java.io.ByteArrayOutputStream();
        OutputStreamWriter out = new OutputStreamWriter(sw, enc);
        out.write(data);
        out.flush();
        return new String(sw.toByteArray());
    }
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        if (this.jTextFieldName.getText().trim().equals(""))
        {
            this.jTabbedPane1.setSelectedIndex(0);
            this.jTextFieldName.grabFocus();
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("indicar_titutlo"), java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.jTextAreaDescription.getText().trim().equals(""))
        {
            this.jTabbedPane1.setSelectedIndex(0);
            this.jTextAreaDescription.grabFocus();
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("Favor_de_indicar_la_descripcion_del_flujo"), java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.workflow.getActivitiesModel().size() == 0)
        {
            this.jTabbedPane1.setSelectedIndex(2);
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("indicar_por_lo_menos_una_actividad"), java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("Debe_indicar_a_que_tipos_de_recursos_aplica_el_flujo_de_publicacion"), java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        Iterator activities = this.workflow.getActivitiesModel().getActivities().iterator();
        while (activities.hasNext())
        {
            Activity activity = (Activity) activities.next();
            if (!((activity instanceof EndActivity) || (activity instanceof AuthorActivity)))
            {
                if (activity.getLinks().size() == 0)
                {
                    this.jTabbedPane1.setSelectedIndex(2);
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("La_actividad_") + " \"" + activity.getName() + "\" " + java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("_se_encuentra_aislada,_favor_de_indicar_actividades_de_aprovacion_y_de_rechazo"), java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (activity.getLinks().size() == 1)
                {
                    Link link = (Link) activity.getLinks().toArray()[0];
                    if (link.getType().equals("authorized"))
                    {
                        this.jTabbedPane1.setSelectedIndex(2);
                        JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("La_actividad_") + " \"" + activity.getName() + "\" " + java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("_no_tiene_actividad_en_case_de_rechazo"), java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    else
                    {
                        this.jTabbedPane1.setSelectedIndex(2);
                        JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("La_actividad_") + " \"" + activity.getName() + "\" " + java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("_no_tiene_actividad_en_caso_de_aprovacion"), java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                }
            }
        }

        if (!isCorrect())
        {
            return;
        }
        if (this.workflow.getID() != null)
        {
            int status = JOptionPane.showConfirmDialog(
                    this,
                    java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("ask1") +
                    "\r\n" +
                    java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("msg2"),
                    java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (status == JOptionPane.NO_OPTION)
            {
                return;
            }

        }
        try
        {
            WBXMLParser parse = new WBXMLParser();
            WBTreeNode node = parse.parse("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            WBTreeNode wf = node.addNode();
            wf.setName("workflow");
            wf.addAttribute("version", this.jTextFieldVersion.getText());
            if (this.jCheckBoxEdit.isSelected())
            {
                wf.addAttribute("canEdit", String.valueOf(true));
            }
            if (this.workflow.getID() != null)
            {
                wf.addAttribute("id", this.workflow.getID());
            }
            wf.addAttribute("name", this.jTextFieldName.getText().trim());
            WBTreeNode desc = wf.addNode();
            desc.setName("description");
            desc.setText(this.jTextAreaDescription.getText().trim());
            jTableActivitiesModel model = (jTableActivitiesModel) this.jTableActividades.getModel();
            for (int i = 0; i < model.size(); i++)
            {
                Activity activity = model.getActivity(i);
                WBTreeNode eactivity = wf.addNode();
                eactivity.setName("activity");
                eactivity.addAttribute("name", activity.getName());
                if (activity instanceof EndActivity)
                {
                    eactivity.addAttribute("type", "EndActivity");
                }
                else if (activity instanceof AuthorActivity)
                {
                    eactivity.addAttribute("type", "AuthorActivity");
                }
                else
                {
                    eactivity.addAttribute("type", "Activity");
                }
                eactivity.addAttribute("days", String.valueOf(activity.getDias()));
                eactivity.addAttribute("hours", String.valueOf(activity.getHours()));

                WBTreeNode edesc = eactivity.addNode();
                edesc.setName("description");
                edesc.setText(activity.getDescription());
                jTableRolesModel rolemodel = activity.getRoleModel();
                for (int j = 0; j < rolemodel.size(); j++)
                {
                    Role role = rolemodel.getRole(j);
                    WBTreeNode erole = eactivity.addNode();
                    erole.setName("role");
                    erole.addAttribute("id", String.valueOf(role.getID()));
                    erole.addAttribute("name", role.getName());
                    erole.addAttribute("repository", role.getRepository());
                }
                jTableUserModel usermodel = activity.getUserModel();
                for (int j = 0; j < usermodel.size(); j++)
                {
                    User user = usermodel.getUser(j);
                    WBTreeNode euser = eactivity.addNode();
                    euser.setName("user");
                    euser.addAttribute("id", user.getID());
                    euser.addAttribute("name", user.getName());
                }
            }
            boolean includeEndActivity = false;
            boolean includeInitActivity = false;
            for (int i = 0; i < model.size(); i++)
            {
                Activity activityFrom = model.getActivity(i);
                Iterator it = activityFrom.getLinks().iterator();
                while (it.hasNext())
                {
                    Link link = (Link) it.next();
                    Activity activityTo = link.getActivityTo();
                    if (!includeEndActivity && activityTo instanceof EndActivity)
                    {
                        WBTreeNode eactivity = wf.addNode();
                        eactivity.setName("activity");
                        eactivity.addAttribute("name", activityTo.getName());
                        eactivity.addAttribute("type", "EndActivity");
                        includeEndActivity = true;
                    }
                    else if (!includeInitActivity && activityTo instanceof AuthorActivity)
                    {
                        WBTreeNode eactivity = wf.addNode();
                        eactivity.setName("activity");
                        eactivity.addAttribute("name", activityTo.getName());
                        eactivity.addAttribute("type", "AuthorActivity");
                        includeInitActivity = true;
                    }
                }
            }

            for (int i = 0; i < model.size(); i++)
            {
                Activity activityFrom = model.getActivity(i);
                Iterator it = activityFrom.getLinks().iterator();
                while (it.hasNext())
                {
                    Link link = (Link) it.next();
                    WBTreeNode elink = wf.addNode();
                    elink.setName("link");


                    for (int iUser = 0; iUser < link.getUsers().size(); iUser++)
                    {
                        User user = (User) link.getUsers().get(iUser);
                        WBTreeNode notifica = elink.addNode();
                        notifica.setName("notification");
                        notifica.addAttribute("to", user.getID());
                        notifica.addAttribute("type", "user");
                    }

                    for (int iRole = 0; iRole < link.getRoles().size(); iRole++)
                    {
                        Role role = (Role) link.getRoles().get(iRole);
                        WBTreeNode notifica = elink.addNode();
                        notifica.setName("notification");
                        notifica.addAttribute("to", role.getID());
                        notifica.addAttribute("repository", role.getRepository());
                        notifica.addAttribute("type", "role");
                    }

                    elink.addAttribute("from", link.getActivityFrom().getName());
                    Activity activityTo = link.getActivityTo();
                    elink.addAttribute("to", activityTo.getName());
                    elink.addAttribute("type", link.getType());
                    elink.addAttribute("publish", String.valueOf(link.isPublish()));
                    elink.addAttribute("authorized", String.valueOf(link.isAuthorized()));
                    WBTreeNode eservicemail = elink.addNode();
                    eservicemail.setName("service");
                    eservicemail.setText("mail");

                    if (activityTo instanceof AuthorActivity)
                    {
                        if (link.isAuthorized())
                        {
                            WBTreeNode eservice = elink.addNode();
                            eservice.setName("service");
                            eservice.setText("authorize");
                        }
                        else
                        {
                            WBTreeNode eservice = elink.addNode();
                            eservice.setName("service");
                            eservice.setText("noauthorize");
                        }
                    }
                    else if (activityTo instanceof EndActivity)
                    {
                        if (link.isPublish())
                        {
                            WBTreeNode eservice = elink.addNode();
                            eservice.setName("service");
                            eservice.setText("publish");
                        }
                        if (link.isAuthorized())
                        {
                            WBTreeNode eservice = elink.addNode();
                            eservice.setName("service");
                            eservice.setText("authorize");
                        }
                        else
                        {
                            WBTreeNode eservice = elink.addNode();
                            eservice.setName("service");
                            eservice.setText("noauthorize");
                        }
                    }
                    else
                    {
                        if (!link.getType().equalsIgnoreCase("authorized"))
                        {
                            WBTreeNode eservice = elink.addNode();
                            eservice.setName("service");
                            eservice.setText("noauthorize");
                        }
                    }
                }
            }

            jTableResourceTypeModel modelresources = (jTableResourceTypeModel) this.jTableTipos_Recursos.getModel();
            for (int i = 0; i < modelresources.size(); i++)
            {
                ResourceType type = modelresources.getResourceType(i);
                if (type.isSelected().booleanValue())
                {
                    WBTreeNode etype = wf.addNode();
                    etype.setName("resourceType");
                    etype.addAttribute("name", type.getName());
                    etype.addAttribute("id", type.getID());
                    etype.addAttribute("topicmap", type.getTopicMap());
                }
            }
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>update</cmd><tm>" + tm + "</tm>";
            xml += node.getFirstNode().getXML() + "</req>";
            String resxml = getData(xml, this);
            try
            {
                WBXMLParser parser = new WBXMLParser();
                WBTreeNode respnode = parser.parse(resxml);
                if (respnode.getFirstNode() != null && respnode.getFirstNode().getFirstNode() != null)
                {
                    WBTreeNode eid = respnode.getFirstNode().getFirstNode();
                    if (eid.getName().equals("workflowid"))
                    {
                        if (this.workflow.getID() != null)
                        {
                            int iversion = (int) Double.parseDouble(this.jTextFieldVersion.getText());
                            iversion++;
                            this.jTextFieldVersion.setText(iversion + ".0");
                        }
                        WBTreeNode version = respnode.getFirstNode().getNodebyName("version");
                        this.jTextFieldVersion.setText(version.getFirstNode().getText() + ".0");
                        this.workflow.setID(eid.getFirstNode().getText());

                        /*try
                        {
                            String newurl = url_script + "?tm=" + EditWorkflow.tm + "&id=" + eid.getFirstNode().getText();
                            URL _url = new URL(getCodeBase().getProtocol(), getCodeBase().getHost(), getCodeBase().getPort(), newurl);
                            this.getAppletContext().showDocument(_url, "status");
                        }
                        catch (Exception e)
                        {
                            System.out.println(e.getMessage());
                            e.printStackTrace(System.out);
                        }*/
                        JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("save"), java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    else
                    {
                        WBTreeNode err = respnode.getFirstNode().getFirstNode();
                        if (err != null)
                        {
                            JOptionPane.showMessageDialog(this, err.getFirstNode().getText(), java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        else
                        {

                            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("err1"), java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                }
                else
                {
                    JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("err1"), java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("err1") + " : " + e.getMessage() + "\r\n" + resxml, java.util.ResourceBundle.getBundle("applets/workflowadmin/EditWorkflow", locale).getString("title"), JOptionPane.ERROR_MESSAGE);
                System.out.println("resxml: " + resxml);
                e.printStackTrace(System.out);
                return;
            }
        }
        catch (Exception e)
        {
        }




    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jMenuItemAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddActionPerformed
        this.workflow.getActivitiesModel().setTable(this.jTableActividades);
        ActivityEdition frm = new ActivityEdition(this.workflow.getActivitiesModel(), locale);
        frm.setModal(true);
        frm.setLocation(200, 200);
        frm.setSize(500, 400);
        frm.show();


    }//GEN-LAST:event_jMenuItemAddActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSave;
    private javax.swing.JCheckBox jCheckBoxEdit;
    private javax.swing.JLabel jLabelDescription;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelVersion;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemAdd;
    private javax.swing.JMenuItem jMenuItemDelete;
    private javax.swing.JMenuItem jMenuItemDown;
    private javax.swing.JMenuItem jMenuItemEdit;
    private javax.swing.JMenuItem jMenuItemMAdd;
    private javax.swing.JMenuItem jMenuItemMDelete;
    private javax.swing.JMenuItem jMenuItemMDown;
    private javax.swing.JMenuItem jMenuItemMEdit;
    private javax.swing.JMenuItem jMenuItemMUP;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemUP;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelActividades;
    private javax.swing.JPanel jPanelDise�o;
    private javax.swing.JPanel jPanelPropiedades;
    private javax.swing.JPanel jPanelTiposRecursos;
    private javax.swing.JPanel jPaneltoolbar;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneActividades;
    private javax.swing.JScrollPane jScrollPaneAreaDescription;
    private javax.swing.JScrollPane jScrollPaneDesign;
    private javax.swing.JScrollPane jScrollPaneTiposRescursos;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableActividades;
    private javax.swing.JTable jTableActivitiesG;
    private javax.swing.JTable jTableTipos_Recursos;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldVersion;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
