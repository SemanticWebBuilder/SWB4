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
public class DialogEditLink extends javax.swing.JDialog
{

    public boolean cancel;
    private WBConnection con;
    public Sequence<? extends String> users;
    public Sequence<? extends String> roles;
    public Locale locale = new Locale("es");
    boolean published;
    boolean showPublicar;
    public String tm;

    /** Creates new form DialogEditLink */
    public DialogEditLink()
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
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.jCheckBoxPublicar.setSelected(published);        
        if (!showPublicar)
        {
            this.jTabbedPane1.remove(this.jPanel9);
        }

        this.loadRoles();
        this.loadUsers();
        jTableRolesModel rolemodel = (jTableRolesModel) this.jTableRoles.getModel();
        if (roles != null)
        {
            for (String srole : roles)
            {
                String[] values = srole.split("@");

                if (values.length == 3)
                {
                    Role tempRole = new Role(values[0], values[2], values[1]);
                    int irows = rolemodel.getRowCount();
                    for (int i = 0; i < irows; i++)
                    {
                        Role role = rolemodel.getRole(i);
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
            for (String srole : users)
            {
                String[] values = srole.split("@");

                if (values.length == 2)
                {
                    User tempUser = new User(values[0], values[1]);
                    int irows = usermodel.getRowCount();
                    for (int i = 0; i < irows; i++)
                    {
                        User user = usermodel.getUser(i);
                        if (user.equals(tempUser))
                        {
                            user.setChecked(true);
                        }
                    }
                }
            }
        }
    }

    public DialogEditLink(Boolean showPublicar, Boolean published, WBConnection con, Sequence<String> users, Sequence<String> roles)
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
        this.published = published;
        this.users = users;
        this.roles = roles;

        init();
    }

    public void loadUsers()
    {
        jTableUserModel model = new jTableUserModel(locale);
        this.jTableUsuarios.setModel(model);
        if (con != null)
        {            
            if (tm != null)
            {
                String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getcatUsers</cmd><tm>" + tm + "</tm></req>";
                xml = con.getData(xml);
                WBXMLParser parser = new WBXMLParser();
                WBTreeNode exml = parser.parse(xml);
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

    public void loadRoles()
    {
        jTableRolesModel model = new jTableRolesModel(locale);
        this.jTableRoles.setModel(model);
        if (con != null)
        {
            
            if (tm != null)
            {
                String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><cmd>getcatRoles</cmd><tm>" + tm + "</tm></req>";
                xml = con.getData(xml);
                WBXMLParser parser = new WBXMLParser();
                WBTreeNode exml = parser.parse(xml);
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonCancel = new javax.swing.JButton();
        jButtonok = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jCheckBoxPublicar = new javax.swing.JCheckBox();
        jPanel10 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRoles = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableUsuarios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/DialogEditLink_es"); // NOI18N
        setTitle(bundle.getString("editproperties")); // NOI18N

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 30));

        jButtonCancel.setText("Cancelar");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonok.setText("Aceptar");
        jButtonok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonokActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(226, Short.MAX_VALUE)
                .add(jButtonok)
                .add(18, 18, 18)
                .add(jButtonCancel)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonCancel)
                    .add(jButtonok))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new java.awt.BorderLayout());
        jPanel9.add(jPanel8, java.awt.BorderLayout.SOUTH);

        jPanel2.setLayout(null);

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("org/semanticwb/publishflow/DialogEditLink_es",locale); // NOI18N
        jCheckBoxPublicar.setText(bundle1.getString("publicar")); // NOI18N
        jPanel2.add(jCheckBoxPublicar);
        jCheckBoxPublicar.setBounds(0, 10, 395, 40);

        jPanel9.add(jPanel2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Transición", jPanel9);

        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel11.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 150));

        jTableRoles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableRoles);

        jPanel11.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Roles", jPanel11);

        jPanel12.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setPreferredSize(new java.awt.Dimension(453, 150));

        jTableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableUsuarios);

        jPanel12.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Usuarios", jPanel12);

        jPanel10.add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Avisos", jPanel10);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCancelActionPerformed
    {//GEN-HEADEREND:event_jButtonCancelActionPerformed
        cancel = true;
        this.setVisible(false);

    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonokActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonokActionPerformed
    {//GEN-HEADEREND:event_jButtonokActionPerformed

        this.published = this.jCheckBoxPublicar.isSelected();
        jTableRolesModel rolemodel = (jTableRolesModel) this.jTableRoles.getModel();
        jTableUserModel usermodel = (jTableUserModel) this.jTableUsuarios.getModel();
        HashSet<String> oroles = new HashSet<String>();

        int iroles = rolemodel.getRowCount();
        for (int i = 0; i < iroles; i++)
        {
            Role role = rolemodel.getRole(i);
            if (role.isChecked())
            {
                oroles.add(role.id + "@" + role.repository + "@" + role.name);
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
                ousers.add(user.id + "@" + user.name);
            }
        }
        users = com.sun.javafx.runtime.sequence.Sequences.fromCollection(t, ousers);
        this.setVisible(false);
    }//GEN-LAST:event_jButtonokActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonok;
    private javax.swing.JCheckBox jCheckBoxPublicar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTableRoles;
    private javax.swing.JTable jTableUsuarios;
    // End of variables declaration//GEN-END:variables
}
