/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * ActivityEdition.java
 *
 * Created on 11 de octubre de 2004, 06:09 PM
 */

package applets.workflowadmin;

import javax.swing.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

import applets.commons.*;

/**
 * Formulario para editar una actividad.
 * @author Victor Lorenzana
 */
public class ActivityEdition extends javax.swing.JDialog {

    /** Creates new form ActivityEdition */
    Activity activity=new Activity("","");
    jTableActivitiesModel model;
    jTableLinkModel aprovacion;
    jTableLinkModel rechazo;
    boolean add=false;
    Locale locale;
    public ActivityEdition(jTableActivitiesModel model,Locale locale)
    {
        aprovacion=new jTableLinkModel(locale);
        rechazo=new jTableLinkModel(locale);
        activity.setLocale(locale);
        this.locale=locale;
        initComponents();
        this.jTextFieldName.grabFocus();
        this.model=model;
        this.jTextFieldName.setDocument(new FixedNumericDocument(100, false));
        init();
        add=true;
        this.setJMenuBar(this.jMenuBar1);
        this.setTitle(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("tit_add"));
        

    }
    public ActivityEdition(Activity activity,jTableActivitiesModel model,Locale locale )
    {
        this(model,locale);
        this.activity=activity;
        this.model=model;
        this.jTextFieldName.setDocument(new FixedNumericDocument(100, false));
        add=false;
        init();        
        this.setTitle(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("tit_edit"));       
        
        
        jTableUserModel usermodel=(jTableUserModel)this.jTableUsuarios.getModel();
        jTableRolesModel rolemodel=(jTableRolesModel)this.jTableRoles.getModel();
        int irows=rolemodel.getRowCount();
        for(int i=0;i<irows;i++)
        {
            Role role=rolemodel.getRole(i);
            for(int j=0;j<this.activity.getRoleModel().getRowCount();j++)
            {
                if(role.equals(this.activity.getRoleModel().getRole(j)))
                {
                    role.setChecked(true);
                }
            }
        }
        for(int i=0;i<usermodel.getRowCount();i++)
        {
            User user=usermodel.getUser(i);
            for(int j=0;j<this.activity.getUserModel().getRowCount();j++)
            {
                if(user.equals(this.activity.getUserModel().getUser(j)))
                {
                    user.setChecked(true);
                }
            }
        }

    }
    public void loadUsers(Locale locale)
    {
        jTableUserModel model=new jTableUserModel(locale);
        this.jTableUsuarios.setModel(model);
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getcatUsers</cmd><tm>"+ EditWorkflow.tm +"</tm></req>";
        xml=EditWorkflow.getData(xml,this);        
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode exml=parser.parse(xml);     
        Iterator eusers=exml.getFirstNode().getNodesbyName("user");
        while(eusers.hasNext())
        {
            WBTreeNode wuser=(WBTreeNode)eusers.next();
            String id=wuser.getAttribute("id");
            String name=wuser.getAttribute("name");
            User user=new User(id,name);
            model.addUser(user);
        }
            
    }
    public void loadRoles(Locale locale)
    {
        jTableRolesModel model=new jTableRolesModel(locale);
        this.jTableRoles.setModel(model);
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getcatRoles</cmd><tm>"+ EditWorkflow.tm +"</tm></req>";
        xml=EditWorkflow.getData(xml,this);  
        WBXMLParser parser=new WBXMLParser();
        WBTreeNode exml=parser.parse(xml);
        Iterator eusers=exml.getFirstNode().getNodesbyName("role");
        while(eusers.hasNext())
        {
            WBTreeNode wuser=(WBTreeNode)eusers.next();
            String id=wuser.getAttribute("id");
            String name=wuser.getAttribute("name");
            String repository=wuser.getAttribute("repository");
            Role role=new Role(id,name,repository);
            model.addRole(role);
        }
    }
    private void init()
    {
        this.jTextFieldDias.setDocument(new FixedNumericDocument(4, true));
        //this.jTextFieldMinutos.setDocument(new FixedNumericDocument(4, true));
        //this.jTextFieldSegundos.setDocument(new FixedNumericDocument(4, true));
        this.jTextFieldHours.setDocument(new FixedNumericDocument(4, true));
        //this.jTextFieldSegundos.setText("0");
        //this.jTextFieldMinutos.setText("0");
        this.jTextFieldHours.setText("0");
        this.jTextFieldDias.setText("0");
        loadRoles(locale);
        loadUsers(locale);
        this.jTableAutorizacion.setModel(aprovacion);
        this.jTableRechazo.setModel(rechazo);

        aprovacion.setTable(jTableAutorizacion);
        rechazo.setTable(jTableRechazo);

        this.jTextFieldName.setText(activity.getName());
        this.jTextAreaDescription.setText(activity.getDescription());
        if(this.activity.hasDuration())
        {            
            this.jCheckBoxDuration.doClick();
            this.jTextFieldDias.setText(""+this.activity.getDias());
            //this.jTextFieldMinutos.setText(""+this.activity.getMinutos());
            //this.jTextFieldSegundos.setText(""+this.activity.getSegundos());            
            this.jTextFieldHours.setText(""+this.activity.getHours());            
        }        
        this.jTextFieldName.setText(activity.getName());
        this.jTextAreaDescription.setText(activity.getDescription());
        Validator v=new Validator(4,true);
        this.jTextFieldDias.addKeyListener(v);
        //this.jTextFieldMinutos.addKeyListener(v);
        this.jTextFieldHours.addKeyListener(v);
        //this.jTextFieldSegundos.addKeyListener(v);        
        
        Iterator links=activity.getLinks().iterator();
        while(links.hasNext())
        {
            Link link=(Link)links.next();
            if(link.getType().equals("authorized"))
                aprovacion.addLink(link);
            else
                this.rechazo.addLink(link);
        }
        Iterator it=this.activity.getLinks().iterator();
        while(it.hasNext())
        {
            Link link=(Link)it.next();
            if(link.getType().equals("authorized"))
            {
                this.aprovacion.addLink(link);
            }
            else
            {
                this.rechazo.addLink(link);
            }
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItemAddAprovacion = new javax.swing.JMenuItem();
        jMenuEditAprovacion = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItemRemoveAporvacion = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItemAddRechazo = new javax.swing.JMenuItem();
        jMenuItemEditRechazo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemRemoveNoApprovacion = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuHerramientas = new javax.swing.JMenu();
        jMenuItemSecApprova = new javax.swing.JMenuItem();
        jMenuItemSecNoApprova = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jMenuItemEditarApprova = new javax.swing.JMenuItem();
        jMenuItemEditarNoApprova = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        jMenuItemEliminarApprova = new javax.swing.JMenuItem();
        jMenuItemEliminarnoApprova = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        jMenuItemAceptar = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        jMenuItemCerrar = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelPropiedades = new javax.swing.JPanel();
        jLabelName = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabelDescription = new javax.swing.JLabel();
        jPanelDescription = new javax.swing.JPanel();
        jTextAreaDescription = new javax.swing.JTextArea();
        jPanelDuracion = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldDias = new javax.swing.JTextField();
        jTextFieldHours = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCheckBoxDuration = new javax.swing.JCheckBox();
        jPanelRoles = new javax.swing.JPanel();
        jScrollPaneRoles = new javax.swing.JScrollPane();
        jTableRoles = new javax.swing.JTable();
        jPanelUsuarios = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableUsuarios = new javax.swing.JTable();
        jPanelSecuencia = new javax.swing.JPanel();
        jPanelAutorizacion = new javax.swing.JPanel();
        jScrollPaneAutorizacion = new javax.swing.JScrollPane();
        jTableAutorizacion = new javax.swing.JTable();
        jPanelRechazo = new javax.swing.JPanel();
        jScrollPaneRechazo = new javax.swing.JScrollPane();
        jTableRechazo = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jButtonAceptar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();

        jMenuItemAddAprovacion.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("Add"));
        jMenuItemAddAprovacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddAprovacionActionPerformed(evt);
            }
        });
        jMenuItemAddAprovacion.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jMenuItemAddAprovacionPropertyChange(evt);
            }
        });

        jPopupMenu1.add(jMenuItemAddAprovacion);

        jMenuEditAprovacion.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("Edit"));
        jMenuEditAprovacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuEditAprovacionActionPerformed(evt);
            }
        });

        jPopupMenu1.add(jMenuEditAprovacion);

        jPopupMenu1.add(jSeparator2);

        jMenuItemRemoveAporvacion.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("Remove"));
        jMenuItemRemoveAporvacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRemoveAporvacionActionPerformed(evt);
            }
        });

        jPopupMenu1.add(jMenuItemRemoveAporvacion);

        jMenuItemAddRechazo.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("Add"));
        jMenuItemAddRechazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddRechazoActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItemAddRechazo);

        jMenuItemEditRechazo.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("Edit"));
        jMenuItemEditRechazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditRechazoActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItemEditRechazo);

        jPopupMenu2.add(jSeparator1);

        jMenuItemRemoveNoApprovacion.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("Remove"));
        jMenuItemRemoveNoApprovacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRemoveNoApprovacionActionPerformed(evt);
            }
        });

        jPopupMenu2.add(jMenuItemRemoveNoApprovacion);

        jMenuHerramientas.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("editar"));
        jMenuItemSecApprova.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("sec_aprova"));
        jMenuItemSecApprova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSecApprovaActionPerformed(evt);
            }
        });

        jMenuHerramientas.add(jMenuItemSecApprova);

        jMenuItemSecNoApprova.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("sec_no_aprova"));
        jMenuItemSecNoApprova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSecNoApprovaActionPerformed(evt);
            }
        });

        jMenuHerramientas.add(jMenuItemSecNoApprova);

        jMenuHerramientas.add(jSeparator3);

        jMenuItemEditarApprova.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("editar_approva"));
        jMenuItemEditarApprova.setEnabled(false);
        jMenuItemEditarApprova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditarApprovaActionPerformed(evt);
            }
        });

        jMenuHerramientas.add(jMenuItemEditarApprova);

        jMenuItemEditarNoApprova.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("editar_sec_no_approva"));
        jMenuItemEditarNoApprova.setEnabled(false);
        jMenuItemEditarNoApprova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditarNoApprovaActionPerformed(evt);
            }
        });

        jMenuHerramientas.add(jMenuItemEditarNoApprova);

        jMenuHerramientas.add(jSeparator4);

        jMenuItemEliminarApprova.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("del_approva"));
        jMenuItemEliminarApprova.setEnabled(false);
        jMenuItemEliminarApprova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEliminarApprovaActionPerformed(evt);
            }
        });

        jMenuHerramientas.add(jMenuItemEliminarApprova);

        jMenuItemEliminarnoApprova.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("del_no_approva"));
        jMenuItemEliminarnoApprova.setEnabled(false);
        jMenuItemEliminarnoApprova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEliminarnoApprovaActionPerformed(evt);
            }
        });

        jMenuHerramientas.add(jMenuItemEliminarnoApprova);

        jMenuHerramientas.add(jSeparator5);

        jMenuItemAceptar.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("aceptar"));
        jMenuItemAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAceptarActionPerformed(evt);
            }
        });

        jMenuHerramientas.add(jMenuItemAceptar);

        jMenuHerramientas.add(jSeparator6);

        jMenuItemCerrar.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("close"));
        jMenuItemCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCerrarActionPerformed(evt);
            }
        });

        jMenuHerramientas.add(jMenuItemCerrar);

        jMenuBar1.add(jMenuHerramientas);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jTabbedPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseMoved(evt);
            }
        });

        jPanelPropiedades.setLayout(null);

        jLabelName.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("nombre"));
        jPanelPropiedades.add(jLabelName);
        jLabelName.setBounds(10, 10, 70, 16);

        jPanelPropiedades.add(jTextFieldName);
        jTextFieldName.setBounds(110, 10, 280, 20);

        jLabelDescription.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("description"));
        jPanelPropiedades.add(jLabelDescription);
        jLabelDescription.setBounds(10, 40, 90, 16);

        jPanelDescription.setLayout(new java.awt.BorderLayout());

        jPanelDescription.add(jTextAreaDescription, java.awt.BorderLayout.CENTER);

        jPanelPropiedades.add(jPanelDescription);
        jPanelDescription.setBounds(110, 40, 280, 80);

        jPanelDuracion.setLayout(null);

        jPanelDuracion.setBorder(new javax.swing.border.TitledBorder(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("duracion")));
        jPanelDuracion.setEnabled(false);
        jLabel3.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("dias"));
        jPanelDuracion.add(jLabel3);
        jLabel3.setBounds(110, 20, 30, 16);

        jTextFieldDias.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldDias.setText("0");
        jTextFieldDias.setEnabled(false);
        jTextFieldDias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldDiasKeyPressed(evt);
            }
        });

        jPanelDuracion.add(jTextFieldDias);
        jTextFieldDias.setBounds(60, 20, 40, 20);

        jTextFieldHours.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldHours.setText("0");
        jTextFieldHours.setEnabled(false);
        jPanelDuracion.add(jTextFieldHours);
        jTextFieldHours.setBounds(230, 20, 40, 20);

        jLabel6.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("horas"));
        jPanelDuracion.add(jLabel6);
        jLabel6.setBounds(280, 20, 40, 16);

        jPanelPropiedades.add(jPanelDuracion);
        jPanelDuracion.setBounds(10, 160, 380, 60);

        jCheckBoxDuration.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("hasDuration"));
        jCheckBoxDuration.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxDurationItemStateChanged(evt);
            }
        });

        jPanelPropiedades.add(jCheckBoxDuration);
        jCheckBoxDuration.setBounds(10, 130, 140, 24);

        jTabbedPane1.addTab(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("propiedades"), jPanelPropiedades);

        jPanelRoles.setLayout(new java.awt.BorderLayout());

        jTableRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneRoles.setViewportView(jTableRoles);

        jPanelRoles.add(jScrollPaneRoles, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("Roles"), jPanelRoles);

        jPanelUsuarios.setLayout(new java.awt.BorderLayout());

        jTableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTableUsuarios);

        jPanelUsuarios.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("Usuarios"), jPanelUsuarios);

        jPanelSecuencia.setLayout(new java.awt.GridLayout(2, 0));

        jPanelAutorizacion.setLayout(new java.awt.BorderLayout());

        jPanelAutorizacion.setBorder(new javax.swing.border.TitledBorder(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("aprovacion")));
        jScrollPaneAutorizacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPaneAutorizacionMousePressed(evt);
            }
        });

        jTableAutorizacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableAutorizacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableAutorizacionMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableAutorizacionMouseReleased(evt);
            }
        });

        jScrollPaneAutorizacion.setViewportView(jTableAutorizacion);

        jPanelAutorizacion.add(jScrollPaneAutorizacion, java.awt.BorderLayout.CENTER);

        jPanelSecuencia.add(jPanelAutorizacion);

        jPanelRechazo.setLayout(new java.awt.BorderLayout());

        jPanelRechazo.setBorder(new javax.swing.border.TitledBorder(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("no_aporvacion")));
        jScrollPaneRechazo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPaneRechazoMousePressed(evt);
            }
        });

        jTableRechazo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        jTableRechazo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableRechazoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableRechazoMouseReleased(evt);
            }
        });

        jScrollPaneRechazo.setViewportView(jTableRechazo);

        jPanelRechazo.add(jScrollPaneRechazo, java.awt.BorderLayout.CENTER);

        jPanelSecuencia.add(jPanelRechazo);

        jTabbedPane1.addTab(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("Secuencia"), jPanelSecuencia);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel10.setPreferredSize(new java.awt.Dimension(10, 40));
        jButtonAceptar.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("aceptar"));
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });

        jPanel10.add(jButtonAceptar);

        jButtonCancelar.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("cancelar"));
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jPanel10.add(jButtonCancelar);

        getContentPane().add(jPanel10, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents

    private void jTextFieldDiasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDiasKeyPressed
        // Add your handling code here:
        
    }//GEN-LAST:event_jTextFieldDiasKeyPressed

    private void jMenuItemEliminarnoApprovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEliminarnoApprovaActionPerformed
        jMenuItemRemoveNoApprovacionActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemEliminarnoApprovaActionPerformed

    private void jMenuItemRemoveNoApprovacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRemoveNoApprovacionActionPerformed
        if(this.jTableRechazo.getSelectedRowCount()>0)
        {
            jTableLinkModel model=(jTableLinkModel)this.jTableRechazo.getModel();
            Link link=model.getLink(this.jTableRechazo.getSelectedRow());
            model.removeLink(link);
            this.jMenuItemEditarNoApprova.setEnabled(false);
            this.jMenuItemEditRechazo.setEnabled(false);
            this.jMenuItemEliminarnoApprova.setEnabled(false);
            this.jMenuItemRemoveNoApprovacion.setEnabled(false);            
        }
    }//GEN-LAST:event_jMenuItemRemoveNoApprovacionActionPerformed

    private void jMenuItemEliminarApprovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEliminarApprovaActionPerformed
        jMenuItemRemoveAporvacionActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemEliminarApprovaActionPerformed

    private void jMenuItemRemoveAporvacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRemoveAporvacionActionPerformed
        if(this.jTableAutorizacion.getSelectedRowCount()>0)
        {
            jTableLinkModel model=(jTableLinkModel)this.jTableAutorizacion.getModel();
            Link link=model.getLink(this.jTableAutorizacion.getSelectedRow());
            model.removeLink(link);
            this.jTableAutorizacion.updateUI();
            this.jMenuItemEditarApprova.setEnabled(false);    
            this.jMenuEditAprovacion.setEnabled(false);
            this.jMenuItemRemoveAporvacion.setEnabled(false);
            this.jMenuItemEliminarApprova.setEnabled(false);
        }
    }//GEN-LAST:event_jMenuItemRemoveAporvacionActionPerformed

    private void jMenuItemAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAceptarActionPerformed
        jButtonAceptarActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemAceptarActionPerformed

    private void jMenuItemCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCerrarActionPerformed
        jButtonCancelarActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemCerrarActionPerformed

    private void jTableRechazoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRechazoMouseReleased
        if( this.jTableRechazo.getSelectedRowCount()>0)
        {
            this.jMenuItemEditarNoApprova.setEnabled(true);
            this.jMenuItemEditRechazo.setEnabled(true);
            this.jMenuItemEliminarnoApprova.setEnabled(true);
            this.jMenuItemRemoveNoApprovacion.setEnabled(true);
            
        }
    }//GEN-LAST:event_jTableRechazoMouseReleased

    private void jTableAutorizacionMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAutorizacionMouseReleased
        if(this.jTableAutorizacion.getSelectedRowCount()>0)
        {
            this.jMenuItemEditarApprova.setEnabled(true);    
            this.jMenuEditAprovacion.setEnabled(true);
            this.jMenuItemRemoveAporvacion.setEnabled(true);
            this.jMenuItemEliminarApprova.setEnabled(true);
        }
    }//GEN-LAST:event_jTableAutorizacionMouseReleased

    private void jMenuItemEditarNoApprovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditarNoApprovaActionPerformed
        jMenuItemEditRechazoActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemEditarNoApprovaActionPerformed

    private void jMenuItemEditarApprovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditarApprovaActionPerformed
        jMenuEditAprovacionActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemEditarApprovaActionPerformed

    private void jMenuItemSecNoApprovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSecNoApprovaActionPerformed
        jMenuItemAddRechazoActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemSecNoApprovaActionPerformed

    private void jMenuItemSecApprovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSecApprovaActionPerformed
        jMenuItemAddAprovacionActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemSecApprovaActionPerformed

    private void jMenuItemAddAprovacionPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jMenuItemAddAprovacionPropertyChange
        
    }//GEN-LAST:event_jMenuItemAddAprovacionPropertyChange

    private void jScrollPaneRechazoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPaneRechazoMousePressed
        jTableRechazoMousePressed(evt);
    }//GEN-LAST:event_jScrollPaneRechazoMousePressed

    private void jTableRechazoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRechazoMousePressed
       
        if(evt.getButton()==evt.BUTTON1 && evt.getClickCount()==2 && this.jTableRechazo.getSelectedRowCount()>0)
        {
            jMenuItemEditRechazoActionPerformed(null);
        }
        
        if(evt.getButton()==evt.BUTTON3 && evt.getClickCount()==1)
        {
            this.jPopupMenu2.show(this.jTableRechazo, evt.getX(),evt.getY());
        }
    }//GEN-LAST:event_jTableRechazoMousePressed

    private void jMenuItemEditRechazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditRechazoActionPerformed
        jTableLinkModel modelto=this.rechazo;
        if(this.jTableRechazo.getSelectedRowCount()>0)
        {
            Link link=modelto.getLink(this.jTableRechazo.getSelectedRow());
            AddEditActivitySecuence frm=new AddEditActivitySecuence(link,model,modelto,locale);
            frm.ocultaRadioAutorizado();
            frm.setModal(true);
            frm.setLocation(200,200);
            frm.setSize(400,400);
            frm.show();
        }
    }//GEN-LAST:event_jMenuItemEditRechazoActionPerformed

    private void jMenuItemAddRechazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddRechazoActionPerformed
        jTableLinkModel modelto=this.rechazo;
        AddEditActivitySecuence frm=new AddEditActivitySecuence(this.activity,model,modelto,"unauthorized",locale);
        frm.ocultaRadioAutorizado();
        frm.setLocation(200,200);
        frm.setSize(400,400);
        frm.setModal(true);
        frm.show();
    }//GEN-LAST:event_jMenuItemAddRechazoActionPerformed

    private void jTabbedPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseMoved

    }//GEN-LAST:event_jTabbedPane1MouseMoved

    private void jMenuEditAprovacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuEditAprovacionActionPerformed
        jTableLinkModel modelto=this.aprovacion;
        if(this.jTableAutorizacion.getSelectedRowCount()>0)
        {
            Link link=modelto.getLink(this.jTableAutorizacion.getSelectedRow());
            AddEditActivitySecuence frm=new AddEditActivitySecuence(link,model,modelto,locale);
            frm.ocultaRadioRechazado();
            frm.setLocation(200,200);
            frm.setModal(true);
            frm.setSize(400,400);
            frm.show();
        }
    }//GEN-LAST:event_jMenuEditAprovacionActionPerformed

    private void jMenuItemAddAprovacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddAprovacionActionPerformed
        jTableLinkModel modelto=this.aprovacion;
        AddEditActivitySecuence frm=new AddEditActivitySecuence(this.activity,model,modelto,"authorized",locale);
        frm.ocultaRadioRechazado();
        frm.setLocation(200,200);
        frm.setSize(400,400);
        frm.setModal(true);
        frm.show();
    }//GEN-LAST:event_jMenuItemAddAprovacionActionPerformed

    private void jScrollPaneAutorizacionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPaneAutorizacionMousePressed
        jTableAutorizacionMousePressed(evt);
    }//GEN-LAST:event_jScrollPaneAutorizacionMousePressed

    private void jTableAutorizacionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAutorizacionMousePressed
       
        if(evt.getButton()==evt.BUTTON1 && evt.getClickCount()==2 && this.jTableAutorizacion.getSelectedRowCount()>0)
        {
            jMenuEditAprovacionActionPerformed(null);            
        }
        if(evt.getButton()==evt.BUTTON3 && evt.getClickCount()==1)
        {
            this.jPopupMenu1.show(this.jTableAutorizacion, evt.getX(),evt.getY());
        }
    }//GEN-LAST:event_jTableAutorizacionMousePressed

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        // Add your handling code here:
        if(this.jTextFieldName.getText().trim().equals(""))
        {
            this.jTabbedPane1.setSelectedIndex(0);
            this.jTextFieldName.grabFocus();
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("Favor_de_indicar_el_nombre_de_la_activitdad"),this.getTitle(),JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(this.jTextAreaDescription.getText().trim().equals(""))
        {
            this.jTabbedPane1.setSelectedIndex(0);
            this.jTextAreaDescription.grabFocus();
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("Favor_de_indicar_la_descripción_dela_actividad"),this.getTitle(),JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean existe=false;
        Iterator it=this.model.getActivities().iterator();
        while(it.hasNext())
        {
            Activity oactivity=(Activity)it.next();
            if(oactivity!=this.activity && oactivity.getName().equals(this.jTextFieldName.getText().trim()))
            {
                existe=true;
            }
        }

        if(existe)
        {
            this.jTabbedPane1.setSelectedIndex(0);
            this.jTextFieldName.grabFocus();
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("Ya_existe_una_actividad_con_ese_nombre"),this.getTitle(),JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        
        jTableRolesModel rolemodel=(jTableRolesModel)this.jTableRoles.getModel();
        jTableUserModel usermodel=(jTableUserModel)this.jTableUsuarios.getModel();
        
        
        boolean selected=false;        
        for(int i=0;i<rolemodel.getRowCount();i++)
        {
            if(rolemodel.getRole(i).isChecked())
            {
                selected=true;
            }
        }

        
        for(int i=0;i<usermodel.getRowCount();i++)
        {
            if(usermodel.getUser(i).isChecked())
            {
                selected=true;
            }
        }
        if(!selected)
        {
            this.jTabbedPane1.setSelectedIndex(1);
            JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("No_seleccionó_rol_o_usuario_que_tenga_permisos_sobre_esta_actividad"),this.getTitle(),JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(this.jCheckBoxDuration.isSelected())
        {
            if(this.jTextFieldDias.getText().equals(""))
            {
                this.jTextFieldDias.setText("0");
            }
            int dias=Integer.parseInt(this.jTextFieldDias.getText());
            //int minutos=Integer.parseInt(this.jTextFieldMinutos.getText());
            //int segundos=Integer.parseInt(this.jTextFieldSegundos.getText());
            int horas=Integer.parseInt(this.jTextFieldHours.getText());
            if(dias==0 && horas==0)
            {
                this.jTabbedPane1.setSelectedIndex(0);
                this.jTextFieldDias.grabFocus();
                JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/workflowadmin/ActivityEdition",locale).getString("La_duración_de_la_actividad_es_cero,_favor_de_indicar_una_dureción_mayor_a_cero"),this.getTitle(),JOptionPane.ERROR_MESSAGE);
                return;
            }
            activity.setDuraction(dias,horas);           
        }
        else
        {
            activity.setDuraction(false);
        }        

        this.activity.getLinks().clear();


        jTableLinkModel model=(jTableLinkModel)this.jTableAutorizacion.getModel();
        Iterator links=model.getLinks().iterator();
        while(links.hasNext())
        {
            Link link=(Link)links.next();            
            this.activity.addLink(link);
        }

        model=(jTableLinkModel)this.jTableRechazo.getModel();
        links=model.getLinks().iterator();
        while(links.hasNext())
        {            
            Link link=(Link)links.next();
            this.activity.addLink(link);
        }

        
        
        this.activity.getRoleModel().clear();
        this.activity.getUserModel().clear();
        
        
        
        int iroles=rolemodel.getRowCount();
        for(int i=0;i<iroles;i++)
        {
            Role role=rolemodel.getRole(i);
            if(role.isChecked())
            {
                this.activity.getRoleModel().addRole(role);
            }
        }
        
        this.activity.setName(this.jTextFieldName.getText().trim());
        this.activity.setDescription(this.jTextAreaDescription.getText().trim());
        
        for(int i=0;i<usermodel.getRowCount();i++)
        {
            User user=(User)usermodel.getUser(i);
            if(user.isChecked())
            {
                this.activity.getUserModel().addUser(user);
            }
        }
        System.out.println(add);
        if(add)
        {
            this.model.addActivity(this.activity);
            /*if(this.jRadioButtonSi.isSelected())
            {
                this.model.addFirstActivity(this.activity);                
            }
            else
            {
                this.model.addActivity(this.activity);
            }*/
        }          
       
        setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonAceptarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        // Add your handling code here:
        setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jCheckBoxDurationItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxDurationItemStateChanged
        if(this.jCheckBoxDuration.isSelected())
        {
            this.jPanelDuracion.setEnabled(true);
            this.jTextFieldDias.setEnabled(true);
            //this.jTextFieldMinutos.setEnabled(true);
            this.jTextFieldHours.setEnabled(true);
            //this.jTextFieldSegundos.setEnabled(true);            
        }
        else
        {
            this.jPanelDuracion.setEnabled(false);
            this.jTextFieldDias.setEnabled(false);
            //this.jTextFieldMinutos.setEnabled(false);
            this.jTextFieldHours.setEnabled(false);
            //this.jTextFieldSegundos.setEnabled(false);
            
        }
    }//GEN-LAST:event_jCheckBoxDurationItemStateChanged

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        setVisible(false);
        this.dispose();
    }//GEN-LAST:event_exitForm




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JCheckBox jCheckBoxDuration;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelDescription;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuEditAprovacion;
    private javax.swing.JMenu jMenuHerramientas;
    private javax.swing.JMenuItem jMenuItemAceptar;
    private javax.swing.JMenuItem jMenuItemAddAprovacion;
    private javax.swing.JMenuItem jMenuItemAddRechazo;
    private javax.swing.JMenuItem jMenuItemCerrar;
    private javax.swing.JMenuItem jMenuItemEditRechazo;
    private javax.swing.JMenuItem jMenuItemEditarApprova;
    private javax.swing.JMenuItem jMenuItemEditarNoApprova;
    private javax.swing.JMenuItem jMenuItemEliminarApprova;
    private javax.swing.JMenuItem jMenuItemEliminarnoApprova;
    private javax.swing.JMenuItem jMenuItemRemoveAporvacion;
    private javax.swing.JMenuItem jMenuItemRemoveNoApprovacion;
    private javax.swing.JMenuItem jMenuItemSecApprova;
    private javax.swing.JMenuItem jMenuItemSecNoApprova;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanelAutorizacion;
    private javax.swing.JPanel jPanelDescription;
    private javax.swing.JPanel jPanelDuracion;
    private javax.swing.JPanel jPanelPropiedades;
    private javax.swing.JPanel jPanelRechazo;
    private javax.swing.JPanel jPanelRoles;
    private javax.swing.JPanel jPanelSecuencia;
    private javax.swing.JPanel jPanelUsuarios;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneAutorizacion;
    private javax.swing.JScrollPane jScrollPaneRechazo;
    private javax.swing.JScrollPane jScrollPaneRoles;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableAutorizacion;
    private javax.swing.JTable jTableRechazo;
    private javax.swing.JTable jTableRoles;
    private javax.swing.JTable jTableUsuarios;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextFieldDias;
    private javax.swing.JTextField jTextFieldHours;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables

}
