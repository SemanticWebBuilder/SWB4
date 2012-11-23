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
 * AddEditActivity.java
 *
 * Created on 11 de octubre de 2004, 06:55 PM
 */

package applets.workflowadmin;

import java.util.*;
import javax.swing.*;
import applets.commons.*;
/**
 * Formulario que permite agregar una secuencia en caso de aprovación o de rechazo,
 * los posibles valores son, env�ar a aotra actividad, al autor del contenido o
 * terminar el flujo.
 * @author Victor Lorenzana
 */
public class AddEditActivitySecuence extends javax.swing.JDialog {

    /** Creates new form AddEditActivity */

    jTableActivitiesModel model;
    jTableLinkModel modelto;  
    jTableActivitiesModel newmodel;
    Link link;
    Locale locale;
    public AddEditActivitySecuence(Activity activity,jTableActivitiesModel model,jTableLinkModel modelto,String type,Locale locale) {
        this.locale=locale;
        initComponents();
        newmodel=new jTableActivitiesModel(locale);
        this.model=model;
        this.modelto=modelto;            
        link=new Link(new EndActivity(locale),activity,type);        
        Iterator it=model.getActivities().iterator();
        while(it.hasNext())
        {
            Activity oactivity=(Activity)it.next();
            if(!link.getActivityFrom().equals(oactivity))
            {
                newmodel.addActivity(oactivity);
            }
        }
        this.jTableActividades.setModel(newmodel);
        this.setTitle(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence",locale).getString("tit_add"));
        this.loadRoles(locale);
        this.loadUsers(locale);
        
        
    }
    public AddEditActivitySecuence(Link link,jTableActivitiesModel model,jTableLinkModel modelto,Locale locale) {        
        this(link.getActivityFrom(),model, modelto,link.getType(),locale);        
        this.link=link;
        this.setTitle(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence",locale).getString("tit_edit"));
        if(link.getActivityTo() instanceof EndActivity)
        {
            this.jRadioButtonTerminar.doClick();
            EndActivity endActivity=(EndActivity)link.getActivityTo();
            if(link.isPublish())
                this.jCheckBoxPublicar.setSelected(true);
            else
                this.jCheckBoxPublicar.setSelected(false);

            if(link.isAuthorized())
            {
                this.jRadioButtonAutorizado.setSelected(true);
            }
            else
            {
                this.jRadioButtonNoAutorizado.setSelected(true);
            }

        }
        else if(link.getActivityTo() instanceof AuthorActivity)
            this.jRadioButtonInicio.doClick();
        else
        {
            this.jRadioButtonActividad.doClick();
            int isel=-1;
            jTableActivitiesModel modelact=(jTableActivitiesModel)this.jTableActividades.getModel();
            for(int i=0;i<modelact.size();i++)
            {
                isel++;
                Activity objactivity=modelact.getActivity(i);
                if(objactivity.equals(link.getActivityTo()))
                {
                    this.jTableActividades.setRowSelectionInterval(isel,isel);
                    break;
                }
            }
        }
        jTableUserModel users=(jTableUserModel)jTableUsuarios.getModel();
        for(int i=0;i<this.link.getUsers().size();i++)
        {
            User user=(User)this.link.getUsers().get(i);
            for(int j=0;j<users.size();j++)
            {
                if(users.getUser(j).getID().equals(user.getID()))
                {
                    users.getUser(j).setChecked(true);
                }
            }
            
        }
        
        
        jTableRolesModel roles=(jTableRolesModel)this.jTableRoles.getModel();
        for(int i=0;i<this.link.getRoles().size();i++)
        {
            Role role=(Role)this.link.getRoles().get(i);
            for(int j=0;j<roles.size();j++)
            {
                if(roles.getRole(j).getID().equals(role.getID()) && roles.getRole(j).getRepository().equals(role.getRepository()))
                {
                    roles.getRole(j).setChecked(true);
                }
            }
            
        }        
        //this.jRadioButtonAutorizado.setVisible(false);
        this.jRadioButtonNoAutorizado.setVisible(false);
        
        
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroupEstatus = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jRadioButtonTerminar = new javax.swing.JRadioButton();
        jRadioButtonAutorizado = new javax.swing.JCheckBox();
        jCheckBoxPublicar = new javax.swing.JCheckBox();
        jRadioButtonNoAutorizado = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jRadioButtonInicio = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jRadioButtonActividad = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPanelActividades = new javax.swing.JScrollPane();
        jTableActividades = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRoles = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableUsuarios = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButtonAceptar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel9.add(jPanel8, java.awt.BorderLayout.SOUTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridLayout(3, 0));

        jPanel3.setPreferredSize(new java.awt.Dimension(10, 150));
        jPanel5.setLayout(new java.awt.GridLayout(0, 2));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setPreferredSize(new java.awt.Dimension(354, 80));
        buttonGroup1.add(jRadioButtonTerminar);
        jRadioButtonTerminar.setSelected(true);
        jRadioButtonTerminar.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence",locale).getString("terminar"));
        jRadioButtonTerminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonTerminarActionPerformed(evt);
            }
        });

        jPanel5.add(jRadioButtonTerminar);

        jRadioButtonAutorizado.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence",locale).getString("Autorizado"));
        jPanel5.add(jRadioButtonAutorizado);

        jCheckBoxPublicar.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence",locale).getString("publicar"));
        jPanel5.add(jCheckBoxPublicar);

        jRadioButtonNoAutorizado.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence",locale).getString("No_autorizado"));
        jPanel5.add(jRadioButtonNoAutorizado);

        jPanel3.add(jPanel5);

        jPanel6.setLayout(new java.awt.GridLayout(0, 2));

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setMinimumSize(new java.awt.Dimension(362, 10));
        jPanel6.setPreferredSize(new java.awt.Dimension(362, 10));
        buttonGroup1.add(jRadioButtonInicio);
        jRadioButtonInicio.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence",locale).getString("enviar_autor"));
        jRadioButtonInicio.setPreferredSize(new java.awt.Dimension(179, 20));
        jRadioButtonInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonInicioActionPerformed(evt);
            }
        });

        jPanel6.add(jRadioButtonInicio);

        jPanel3.add(jPanel6);

        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.X_AXIS));

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonGroup1.add(jRadioButtonActividad);
        jRadioButtonActividad.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence",locale).getString("otra_actividad"));
        jRadioButtonActividad.setPreferredSize(new java.awt.Dimension(30, 10));
        jRadioButtonActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonActividadActionPerformed(evt);
            }
        });

        jPanel7.add(jRadioButtonActividad);

        jPanel3.add(jPanel7);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(453, 100));
        jScrollPanelActividades.setPreferredSize(new java.awt.Dimension(453, 200));
        jScrollPanelActividades.setEnabled(false);
        jTableActividades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableActividades.setPreferredScrollableViewportSize(new java.awt.Dimension(450, 200));
        jTableActividades.setEnabled(false);
        jScrollPanelActividades.setViewportView(jTableActividades);

        jPanel4.add(jScrollPanelActividades, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence").getString("transition"), jPanel9);

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

        jTabbedPane2.addTab(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence").getString("roles"), jPanel11);

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

        jTabbedPane2.addTab(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence").getString("usuarios"), jPanel12);

        jPanel10.add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence").getString("avisos"), jPanel10);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.NORTH);

        jPanel1.setPreferredSize(new java.awt.Dimension(10, 40));
        jButtonAceptar.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence",locale).getString("Aceptar"));
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });

        jPanel1.add(jButtonAceptar);

        jButtonCancelar.setText(java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence",locale).getString("Cancelar"));
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jPanel1.add(jButtonCancelar);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonActividadActionPerformed
        jRadioButtonTerminarActionPerformed(evt);
    }//GEN-LAST:event_jRadioButtonActividadActionPerformed

    private void jRadioButtonInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonInicioActionPerformed
        jRadioButtonTerminarActionPerformed(evt);
    }//GEN-LAST:event_jRadioButtonInicioActionPerformed

    private void jRadioButtonTerminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonTerminarActionPerformed
        if(this.jRadioButtonTerminar.isSelected())
        {
            this.jTableActividades.setEnabled(false);
            this.jCheckBoxPublicar.setEnabled(true);
            this.jRadioButtonAutorizado.setEnabled(true);
            this.jRadioButtonNoAutorizado.setEnabled(true);
        }
        else if(this.jRadioButtonInicio.isSelected())
        {
            this.jTableActividades.setEnabled(false);
            this.jCheckBoxPublicar.setEnabled(false);
            this.jRadioButtonAutorizado.setEnabled(false);
            this.jRadioButtonNoAutorizado.setEnabled(false);
        }
        else
        {
            this.jRadioButtonAutorizado.setEnabled(false);
            this.jRadioButtonNoAutorizado.setEnabled(false);
            this.jTableActividades.setEnabled(true);
            this.jCheckBoxPublicar.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButtonTerminarActionPerformed

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed

        link.clearRoles();
        link.clearUsers();
        jTableUserModel users=(jTableUserModel)this.jTableUsuarios.getModel();
        jTableRolesModel roles=(jTableRolesModel)this.jTableRoles.getModel();
        for(int i=0;i<users.size();i++)
        {
            if(users.getUser(i).isChecked())
            {                
                link.addUser(users.getUser(i));
            }
        }
        
        for(int i=0;i<roles.size();i++)
        {
            if(roles.getRole(i).isChecked())
            {
                link.addRole(roles.getRole(i));
            }
        }
        
        if(this.jRadioButtonActividad.isSelected())
        {
            link.setPublish(false);
            link.setAuthorized(false);
            if(this.jTableActividades.getSelectedRows().length>0)
            {
                this.modelto.clear();
                int index=this.jTableActividades.getSelectedRow();
                jTableActivitiesModel modelsel=(jTableActivitiesModel)this.jTableActividades.getModel();
                Activity activity=modelsel.getActivity(index);                
                link.setActivityTo(activity);                
                this.modelto.addLink(link);

            }
            else
            {
                JOptionPane.showMessageDialog(this,java.util.ResourceBundle.getBundle("applets/workflowadmin/AddEditActivitySecuence",locale).getString("Favor_de_seleccionar_una_actividad"),this.getTitle(),JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if(this.jRadioButtonInicio.isSelected())
        {
             link.setPublish(false);
             link.setAuthorized(false);
             this.modelto.clear();
             link.setActivityTo(new AuthorActivity(locale));
             modelto.addLink(link);
        }
        else if(this.jRadioButtonTerminar.isSelected())
        {
            link.setAuthorized(false);
            link.setPublish(false);
            if(this.jRadioButtonAutorizado.isSelected())
            {                
                link.setAuthorized(true);
            }
            if(this.jCheckBoxPublicar.isSelected())
            {
                link.setPublish(true);
            }
            this.modelto.clear();
            link.setActivityTo(new EndActivity(locale));
            this.modelto.addLink(link);
        }
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonAceptarActionPerformed

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_exitForm

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroupEstatus;
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JCheckBox jCheckBoxPublicar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButtonActividad;
    private javax.swing.JCheckBox jRadioButtonAutorizado;
    private javax.swing.JRadioButton jRadioButtonInicio;
    private javax.swing.JCheckBox jRadioButtonNoAutorizado;
    private javax.swing.JRadioButton jRadioButtonTerminar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPanelActividades;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTableActividades;
    private javax.swing.JTable jTableRoles;
    private javax.swing.JTable jTableUsuarios;
    // End of variables declaration//GEN-END:variables
    public void ocultaRadioAutorizado()
    {
        jPanel5.remove(this.jRadioButtonAutorizado);
        jPanel5.remove(this.jCheckBoxPublicar);
        this.jRadioButtonAutorizado.setVisible(false);        
        this.jCheckBoxPublicar.setVisible(false);
        this.jRadioButtonNoAutorizado.setSelected(true);
    }
    public void ocultaRadioRechazado()
    {
        jPanel5.remove(this.jRadioButtonNoAutorizado);
        this.jRadioButtonAutorizado.setSelected(true);
        this.jRadioButtonNoAutorizado.setVisible(false);
        
    }
    
}
