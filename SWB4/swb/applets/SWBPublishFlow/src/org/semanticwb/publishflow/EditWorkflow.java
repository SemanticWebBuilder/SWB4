
package org.semanticwb.publishflow;

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
public class EditWorkflow extends javax.swing.JDialog
{

    private final String PRM_JSESS = "jsess";
    private final String PRM_CGIPATH = "cgipath";
    private final String PRM_TOPICMAP = "tm";
    private static String cgiPath = "/gtw.jsp";
    private static String jsess = "";
    public static String tm = "";
    public String url_script;    
    private static URL url = null;
    /** Initializes the applet EditWorkflow */
    
    Locale locale;

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
   }

    
    public void init()
    {
        /*try
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
        url_script = this.getParameter("script");

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
        this.jCheckBoxEdit.setVisible(false);*/
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

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanelPropiedades.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanelPropiedades.setLayout(null);

        jLabelName.setLabelFor(jTextFieldName);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow",locale); // NOI18N
        jLabelName.setText(bundle.getString("nombre")); // NOI18N
        jPanelPropiedades.add(jLabelName);
        jLabelName.setBounds(20, 90, 52, 14);

        jTextFieldName.setEditable(false);
        jPanelPropiedades.add(jTextFieldName);
        jTextFieldName.setBounds(90, 90, 290, 19);

        jLabelDescription.setLabelFor(jTextAreaDescription);
        jLabelDescription.setText(bundle.getString("Description")); // NOI18N
        jPanelPropiedades.add(jLabelDescription);
        jLabelDescription.setBounds(20, 130, 58, 14);

        jLabelVersion.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelVersion.setLabelFor(jTextFieldVersion);
        jLabelVersion.setText(bundle.getString("version")); // NOI18N
        jPanelPropiedades.add(jLabelVersion);
        jLabelVersion.setBounds(10, 20, 47, 15);

        jTextFieldVersion.setEditable(false);
        jTextFieldVersion.setText("1.0");
        jPanelPropiedades.add(jTextFieldVersion);
        jTextFieldVersion.setBounds(70, 20, 27, 19);

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/EditWorkflow"); // NOI18N
        jCheckBoxEdit.setText(bundle1.getString("modificarContenido")); // NOI18N
        jPanelPropiedades.add(jCheckBoxEdit);
        jCheckBoxEdit.setBounds(20, 50, 235, 23);

        jTextAreaDescription.setEditable(false);
        jTextAreaDescription.setLineWrap(true);
        jTextAreaDescription.setWrapStyleWord(true);
        jScrollPaneAreaDescription.setViewportView(jTextAreaDescription);

        jPanelPropiedades.add(jScrollPaneAreaDescription);
        jScrollPaneAreaDescription.setBounds(90, 130, 102, 100);

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

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
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

    

    

    

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBoxEdit;
    private javax.swing.JLabel jLabelDescription;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelVersion;
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
