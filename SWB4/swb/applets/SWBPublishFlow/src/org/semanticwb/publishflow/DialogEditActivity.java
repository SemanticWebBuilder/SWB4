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

import applets.commons.WBConnection;
import applets.commons.WBTreeNode;
import applets.commons.WBXMLParser;
import com.sun.javafx.runtime.TypeInfo;
import com.sun.javafx.runtime.sequence.Sequence;
import java.awt.Frame;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author victor.lorenzana
 */
public class DialogEditActivity extends javax.swing.JDialog
{

    boolean cancel;
    String name, description,tm;
    Locale locale = new Locale("es");
    WBConnection con;
    Sequence<? extends java.lang.String> roles;
    Sequence<? extends java.lang.String> users;

    /** Creates new form DialogEditActivity */
    public DialogEditActivity()
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
        this.setLocationRelativeTo(null);
        this.setModal(true);
    }

    public void init()
    {
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.jTextFieldName.setText(name);
        this.jTextAreaDescription.setText(description);        
        this.jTextFieldName.grabFocus();
        loadRoles();
        loadUsers();
        jTableRolesModel rolemodel = (jTableRolesModel) this.jTableRoles.getModel();
        if (roles != null)
        {
            for (String srole : roles)
            {
                String[] values = srole.split("@");
                System.out.println("role: "+srole);
                System.out.println("values.length: "+values.length);
                if (values.length == 2)
                {

                    Role tempRole = new Role(values[0], "", values[1]);
                    int irows = rolemodel.getRowCount();
                    for (int i = 0; i < irows; i++)
                    {
                        Role role = rolemodel.getRole(i);
                        System.out.println("role.equals(tempRole): "+role.equals(tempRole));
                        if (role.equals(tempRole))
                        {
                            role.setChecked(true);
                        }
                    }
                }
            }
        }


        jTableUserModel usermodel = (jTableUserModel) this.jTableUsuarios.getModel();
        if (users != null)
        {
            for (String suser : users)
            {
                String[] values = suser.split("@");
                System.out.println("suser: "+suser);
                System.out.println("values.length: "+values.length);
                if (values.length == 1)
                {
                    User tempUser = new User(values[0], "");
                    int irows = usermodel.getRowCount();
                    for (int i = 0; i < irows; i++)
                    {
                        User user = usermodel.getUser(i);
                        System.out.println("user.equals(tempUser): "+user.equals(tempUser));
                        if (user.equals(tempUser))
                        {
                            user.setChecked(true);
                        }
                    }
                }
            }
        }
    }

    public DialogEditActivity(String name, String description, WBConnection con, Sequence<String> users, Sequence<String> roles)
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
        this.users = users;
        this.roles = roles;




        this.con = con;


        initComponents();
        this.setLocationRelativeTo(null);
        this.setModal(true);
        init();




    }

    public void loadUsers()
    {
        jTableUserModel model = new jTableUserModel(locale);
        this.jTableUsuarios.setModel(model);
        
            if (tm != null)
            {
                String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getcatUsers</cmd><tm>" + tm + "</tm></req>";
                xml = con.getData(xml);
                if(xml!=null && !xml.trim().equals(""))
                {
                    WBXMLParser parser = new WBXMLParser();
                    WBTreeNode exml = parser.parse(xml);
                    if(exml!=null && exml.getFirstNode()!=null && exml.getFirstNode().getNodesbyName("user")!=null)
                    {
                        Iterator eusers = exml.getFirstNode().getNodesbyName("user");
                        while (eusers.hasNext())
                        {
                            WBTreeNode wuser = (WBTreeNode) eusers.next();
                            String id = wuser.getAttribute("id");
                            String oname = wuser.getAttribute("name");
                            User user = new User(id, oname);
                            model.addUser(user);
                        }
                    }
                }
            }
        

    }

    public void loadRoles()
    {
        jTableRolesModel model = new jTableRolesModel(locale);
        this.jTableRoles.setModel(model);        
        if (tm != null)
        {
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getcatRoles</cmd><tm>" + tm + "</tm></req>";
            xml = con.getData(xml);
            if(xml!=null && !xml.trim().equals(""))
            {
                WBXMLParser parser = new WBXMLParser();
                WBTreeNode exml = parser.parse(xml);
                if(exml!=null && exml.getFirstNode()!=null && exml.getFirstNode().getNodesbyName("role")!=null)
                {
                    Iterator eusers = exml.getFirstNode().getNodesbyName("role");
                    while (eusers.hasNext())
                    {
                        WBTreeNode wuser = (WBTreeNode) eusers.next();
                        String id = wuser.getAttribute("id");
                        String oname = wuser.getAttribute("name");
                        String repository = wuser.getAttribute("repository");
                        Role role = new Role(id, oname, repository);
                        model.addRole(role);
                    }
                }
            }
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

        jPanel10 = new javax.swing.JPanel();
        jButtonAceptar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Propiedades de la actividad");

        jPanel10.setPreferredSize(new java.awt.Dimension(10, 40));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/DialogEditActivity",locale); // NOI18N
        jButtonAceptar.setText(bundle.getString("aceptar")); // NOI18N
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonAceptar);

        jButtonCancelar.setText(bundle.getString("cancelar")); // NOI18N
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonCancelar);

        getContentPane().add(jPanel10, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseMoved(evt);
            }
        });

        jPanelPropiedades.setLayout(null);

        jLabelName.setText(bundle.getString("nombre")); // NOI18N
        jPanelPropiedades.add(jLabelName);
        jLabelName.setBounds(10, 10, 70, 14);
        jPanelPropiedades.add(jTextFieldName);
        jTextFieldName.setBounds(110, 10, 280, 19);

        jLabelDescription.setText(bundle.getString("description")); // NOI18N
        jPanelPropiedades.add(jLabelDescription);
        jLabelDescription.setBounds(10, 40, 90, 14);

        jPanelDescription.setLayout(new java.awt.BorderLayout());
        jPanelDescription.add(jTextAreaDescription, java.awt.BorderLayout.CENTER);

        jPanelPropiedades.add(jPanelDescription);
        jPanelDescription.setBounds(110, 40, 280, 80);

        jPanelDuracion.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("duracion"))); // NOI18N
        jPanelDuracion.setEnabled(false);
        jPanelDuracion.setLayout(null);

        jLabel3.setText(bundle.getString("dias")); // NOI18N
        jPanelDuracion.add(jLabel3);
        jLabel3.setBounds(110, 20, 30, 14);

        jTextFieldDias.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldDias.setText("0");
        jTextFieldDias.setEnabled(false);
        jTextFieldDias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldDiasKeyPressed(evt);
            }
        });
        jPanelDuracion.add(jTextFieldDias);
        jTextFieldDias.setBounds(60, 20, 40, 19);

        jTextFieldHours.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldHours.setText("0");
        jTextFieldHours.setEnabled(false);
        jPanelDuracion.add(jTextFieldHours);
        jTextFieldHours.setBounds(230, 20, 40, 19);

        jLabel6.setText(bundle.getString("horas")); // NOI18N
        jPanelDuracion.add(jLabel6);
        jLabel6.setBounds(280, 20, 40, 14);

        jPanelPropiedades.add(jPanelDuracion);
        jPanelDuracion.setBounds(10, 160, 380, 60);

        jCheckBoxDuration.setText(bundle.getString("hasDuration")); // NOI18N
        jCheckBoxDuration.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxDurationItemStateChanged(evt);
            }
        });
        jPanelPropiedades.add(jCheckBoxDuration);
        jCheckBoxDuration.setBounds(10, 130, 140, 23);

        jTabbedPane1.addTab("Propiedades", jPanelPropiedades);

        jPanelRoles.setLayout(new java.awt.BorderLayout());

        jTableRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneRoles.setViewportView(jTableRoles);

        jPanelRoles.add(jScrollPaneRoles, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Roles", jPanelRoles);

        jPanelUsuarios.setLayout(new java.awt.BorderLayout());

        jTableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTableUsuarios);

        jPanelUsuarios.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Usuarios", jPanelUsuarios);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAceptarActionPerformed
    {//GEN-HEADEREND:event_jButtonAceptarActionPerformed
        // Add your handling code here:
        if (this.jTextFieldName.getText().trim().equals(""))
        {
            this.jTabbedPane1.setSelectedIndex(0);
            this.jTextFieldName.grabFocus();
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/DialogEditActivity", locale).getString("Favor_de_indicar_el_nombre_de_la_activitdad"), this.getTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.jTextAreaDescription.getText().trim().equals(""))
        {
            this.jTabbedPane1.setSelectedIndex(0);
            this.jTextAreaDescription.grabFocus();
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/DialogEditActivity", locale).getString("Favor_de_indicar_la_descripcion_dela_actividad"), this.getTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }
        name = this.jTextFieldName.getText();
        description = this.jTextAreaDescription.getText();
        jTableRolesModel rolemodel = (jTableRolesModel) this.jTableRoles.getModel();
        jTableUserModel usermodel = (jTableUserModel) this.jTableUsuarios.getModel();


        boolean selected = false;
        for (int i = 0; i < rolemodel.getRowCount(); i++)
        {
            if (rolemodel.getRole(i).isChecked())
            {
                selected = true;
            }
        }


        for(int i=0;i<usermodel.getRowCount();i++)
        {
            if(usermodel.getUser(i).isChecked())
            {
                selected=true;
            }
        }
        if (!selected)
        {
            this.jTabbedPane1.setSelectedIndex(1);
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/DialogEditActivity", locale).getString("No_selecciono_rol_o_usuario_que_tenga_permisos_sobre_esta_actividad"), this.getTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.jCheckBoxDuration.isSelected())
        {
            if (this.jTextFieldDias.getText().equals(""))
            {
                this.jTextFieldDias.setText("0");
            }
            int dias = Integer.parseInt(this.jTextFieldDias.getText());
            //int minutos=Integer.parseInt(this.jTextFieldMinutos.getText());
            //int segundos=Integer.parseInt(this.jTextFieldSegundos.getText());
            int horas = Integer.parseInt(this.jTextFieldHours.getText());
            if (dias == 0 && horas == 0)
            {
                this.jTabbedPane1.setSelectedIndex(0);
                this.jTextFieldDias.grabFocus();
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/DialogEditActivity", locale).getString("La_duracion_de_la_actividad_es_cero,_favor_de_indicar_una_durecion_mayor_a_cero"), this.getTitle(), JOptionPane.ERROR_MESSAGE);
                return;
            }
            //activity.setDuraction(dias,horas);
        }
        else
        {
            //activity.setDuraction(false);
        }

        //this.activity.getLinks().clear();


        /*jTableLinkModel model=(jTableLinkModel)this.jTableAutorizacion.getModel();
        Iterator links=model.getLinks().iterator();
        while(links.hasNext())
        {
        Link link=(Link)links.next();
        this.activity.addLink(link);
        }*/

        /*model=(jTableLinkModel)this.jTableRechazo.getModel();
        links=model.getLinks().iterator();
        while(links.hasNext())
        {
        Link link=(Link)links.next();
        this.activity.addLink(link);
        }*/




        HashSet<String> oroles = new HashSet<String>();

        int iroles = rolemodel.getRowCount();
        for (int i = 0; i < iroles; i++)
        {
            Role role = rolemodel.getRole(i);
            if (role.isChecked())
            {
                oroles.add(role.id + "@" + role.repository);
            }
        }
        TypeInfo t = TypeInfo.getTypeInfo(String.class);
        roles = com.sun.javafx.runtime.sequence.Sequences.fromCollection(t, oroles);


        HashSet<String> ousers = new HashSet<String>();

        for (int i = 0; i < usermodel.getRowCount(); i++)
        {
            User user = (User) usermodel.getUser(i);
            if (user.isChecked())
            {
                ousers.add(user.id);
            }
        }
        users = com.sun.javafx.runtime.sequence.Sequences.fromCollection(t, ousers);
        setVisible(false);
        this.dispose();
}//GEN-LAST:event_jButtonAceptarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCancelarActionPerformed
    {//GEN-HEADEREND:event_jButtonCancelarActionPerformed
        cancel = true;
        setVisible(false);
        this.dispose();
}//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jTextFieldDiasKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldDiasKeyPressed
    {//GEN-HEADEREND:event_jTextFieldDiasKeyPressed
        // Add your handling code here:
    }//GEN-LAST:event_jTextFieldDiasKeyPressed

    private void jCheckBoxDurationItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_jCheckBoxDurationItemStateChanged
    {//GEN-HEADEREND:event_jCheckBoxDurationItemStateChanged
        if (this.jCheckBoxDuration.isSelected())
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

    private void jTabbedPane1MouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTabbedPane1MouseMoved
    {//GEN-HEADEREND:event_jTabbedPane1MouseMoved
}//GEN-LAST:event_jTabbedPane1MouseMoved
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JCheckBox jCheckBoxDuration;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelDescription;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanelDescription;
    private javax.swing.JPanel jPanelDuracion;
    private javax.swing.JPanel jPanelPropiedades;
    private javax.swing.JPanel jPanelRoles;
    private javax.swing.JPanel jPanelUsuarios;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneRoles;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableRoles;
    private javax.swing.JTable jTableUsuarios;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextFieldDias;
    private javax.swing.JTextField jTextFieldHours;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
