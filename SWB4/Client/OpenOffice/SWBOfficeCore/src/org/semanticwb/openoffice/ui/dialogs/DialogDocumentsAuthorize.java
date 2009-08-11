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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogDocumentsAtuhorize.java
 *
 * Created on 2/03/2009, 03:28:19 PM
 */
package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Cursor;
import java.awt.Frame;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.semanticwb.office.interfaces.FlowContentInformation;
import org.semanticwb.office.interfaces.ResourceInfo;
import org.semanticwb.office.interfaces.WebSiteInfo;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.ui.icons.ImageLoader;

/**
 *
 * @author victor.lorenzana
 */
public class DialogDocumentsAuthorize extends java.awt.Dialog
{

    private static final String ALL = "*";
    private static final String SHOW_LAST_VERSION = "Mostrar la última version";

    /** Creates new form DialogDocumentsAtuhorize */
    public DialogDocumentsAuthorize()
    {
        super((Frame) null, ModalityType.TOOLKIT_MODAL);
        this.setIconImage(ImageLoader.images.get("semius").getImage());
        this.setModal(true);
        initComponents();
        this.setLocationRelativeTo(null);
        ListSelectionModel listSelectionModel = jTableContents.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener()
        {

            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                int index = e.getFirstIndex();
                jButtonAuthorize.setEnabled(false);
                jButtonReject.setEnabled(false);
                jButtonSee.setEnabled(false);
                DefaultTableModel model = (DefaultTableModel) jTableContents.getModel();
                if (index >= 0 && model.getRowCount() > 1)
                {
                    ResourceInfo resourceInfo = (ResourceInfo) model.getValueAt(index, 0);
                    try
                    {
                        if (OfficeApplication.getOfficeApplicationProxy().isReviewer(resourceInfo))
                        {
                            jButtonAuthorize.setEnabled(true);
                            jButtonReject.setEnabled(true);
                        }
                    }
                    catch (Exception ue)
                    {
                        ue.printStackTrace();
                    }
                    jButtonSee.setEnabled(true);

                }
            }
        });
        loadSites();
        loadContents();
    }

    private void loadSites()
    {
        this.jComboBoxSites.removeAllItems();
        try
        {
            for (WebSiteInfo site : OfficeApplication.getOfficeApplicationProxy().getSites())
            {
                jComboBoxSites.addItem(site);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void loadContents()
    {
        DefaultTableModel model = (DefaultTableModel) jTableContents.getModel();
        int rows = model.getRowCount();
        for (int i = 1; i <= rows; i++)
        {
            model.removeRow(0);
        }
        try
        {
            if (this.jComboBoxSites.getSelectedItem() != null)
            {
                WebSiteInfo site = (WebSiteInfo) this.jComboBoxSites.getSelectedItem();
                if (this.jRadioButtonMyContents.isSelected())
                {
                    for (FlowContentInformation flowContentInformation : OfficeApplication.getOfficeApplicationProxy().getMyContents(site))
                    {
                        ResourceInfo resourceInfo = flowContentInformation.resourceInfo;
                        String version = flowContentInformation.resourceInfo.version;
                        if (version.equals(ALL))
                        {
                            version = SHOW_LAST_VERSION;
                        }
                        Object[] rowData =
                        {
                            resourceInfo, resourceInfo.page.title, flowContentInformation.step, version
                        };
                        model.addRow(rowData);
                    }
                }
                if (this.jRadioButtonForAuthorize.isSelected())
                {
                    for (FlowContentInformation flowContentInformation : OfficeApplication.getOfficeApplicationProxy().getContentsForAuthorize(site))
                    {
                        ResourceInfo resourceInfo = flowContentInformation.resourceInfo;
                        String version = flowContentInformation.resourceInfo.version;
                        if (version.equals(ALL))
                        {
                            version = SHOW_LAST_VERSION;
                        }
                        Object[] rowData =
                        {
                            resourceInfo, resourceInfo.page.title, flowContentInformation.step, version
                        };
                        model.addRow(rowData);
                    }
                }
                if (this.jRadioButtonAll.isSelected())
                {
                    for (FlowContentInformation flowContentInformation : OfficeApplication.getOfficeApplicationProxy().getAllContents(site))
                    {
                        ResourceInfo resourceInfo = flowContentInformation.resourceInfo;
                        String version = flowContentInformation.resourceInfo.version;
                        if (version.equals(ALL))
                        {
                            version = SHOW_LAST_VERSION;
                        }
                        Object[] rowData =
                        {
                            resourceInfo, resourceInfo.page.title, flowContentInformation.step, version
                        };
                        model.addRow(rowData);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanelCommands = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();
        jPanelTools = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonSee = new javax.swing.JButton();
        jButtonAuthorize = new javax.swing.JButton();
        jButtonReject = new javax.swing.JButton();
        jPanelContents = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jComboBoxSites = new javax.swing.JComboBox();
        jRadioButtonAll = new javax.swing.JRadioButton();
        jRadioButtonMyContents = new javax.swing.JRadioButton();
        jRadioButtonForAuthorize = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableContents = new javax.swing.JTable();

        setResizable(false);
        setTitle("Documentos por autorizar");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanelCommands.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanelCommands.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonClose.setText("Cerrar");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        jPanelCommands.add(jButtonClose);

        add(jPanelCommands, java.awt.BorderLayout.SOUTH);

        jPanelTools.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanelTools.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButtonSee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/see.png"))); // NOI18N
        jButtonSee.setToolTipText("Ver Contenido");
        jButtonSee.setEnabled(false);
        jButtonSee.setFocusable(false);
        jButtonSee.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonSee.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonSee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSeeActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonSee);

        jButtonAuthorize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/icono_autorizar.png"))); // NOI18N
        jButtonAuthorize.setToolTipText("Autorizar contenido");
        jButtonAuthorize.setEnabled(false);
        jButtonAuthorize.setFocusable(false);
        jButtonAuthorize.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAuthorize.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonAuthorize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAuthorizeActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonAuthorize);

        jButtonReject.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/icon_rechazar.png"))); // NOI18N
        jButtonReject.setToolTipText("Rechazar contenido");
        jButtonReject.setEnabled(false);
        jButtonReject.setFocusable(false);
        jButtonReject.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonReject.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonReject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRejectActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonReject);

        jPanelTools.add(jToolBar1, java.awt.BorderLayout.CENTER);

        add(jPanelTools, java.awt.BorderLayout.NORTH);

        jPanelContents.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(100, 30));

        buttonGroup1.add(jRadioButtonAll);
        jRadioButtonAll.setText("Todos");
        jRadioButtonAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAllActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButtonMyContents);
        jRadioButtonMyContents.setSelected(true);
        jRadioButtonMyContents.setText("Mis contenidos");
        jRadioButtonMyContents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMyContentsActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButtonForAuthorize);
        jRadioButtonForAuthorize.setText("Por autorizar");
        jRadioButtonForAuthorize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonForAuthorizeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jComboBoxSites, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonMyContents)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonForAuthorize)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxSites, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButtonForAuthorize)
                    .addComponent(jRadioButtonMyContents)
                    .addComponent(jRadioButtonAll))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelContents.add(jPanel4, java.awt.BorderLayout.NORTH);

        jTableContents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Título", "Página", "Paso", "Versión"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableContents.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTableContents);

        jPanelContents.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanelContents, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCloseActionPerformed
    {//GEN-HEADEREND:event_jButtonCloseActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonSeeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSeeActionPerformed
    {//GEN-HEADEREND:event_jButtonSeeActionPerformed
        if (this.jTableContents.getSelectedRow() != -1)
        {
            DefaultTableModel model = (DefaultTableModel) this.jTableContents.getModel();
            ResourceInfo info = (ResourceInfo) model.getValueAt(this.jTableContents.getSelectedRow(), 0);
            String version = info.version;

        }
    }//GEN-LAST:event_jButtonSeeActionPerformed

    private void jButtonAuthorizeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAuthorizeActionPerformed
    {//GEN-HEADEREND:event_jButtonAuthorizeActionPerformed
        if (this.jTableContents.getSelectedRow() != -1)
        {
            DefaultTableModel model = (DefaultTableModel) this.jTableContents.getModel();
            int row = this.jTableContents.getSelectedRow();
            ResourceInfo resourceInfo = (ResourceInfo) model.getValueAt(row, 0);
            try
            {
                DialogAuthorize dialogAuthorize = new DialogAuthorize("Autorizar contenido");
                dialogAuthorize.setVisible(true);
                if (!dialogAuthorize.cancel)
                {
                    OfficeApplication.getOfficeApplicationProxy().authorize(resourceInfo, dialogAuthorize.jTextAreaMessage.getText().trim());
                    loadContents();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButtonAuthorizeActionPerformed

    private void jButtonRejectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRejectActionPerformed
    {//GEN-HEADEREND:event_jButtonRejectActionPerformed
        if (this.jTableContents.getSelectedRow() != -1)
        {
            DefaultTableModel model = (DefaultTableModel) this.jTableContents.getModel();
            int row = this.jTableContents.getSelectedRow();
            ResourceInfo resourceInfo = (ResourceInfo) model.getValueAt(row, 0);
            try
            {
                DialogAuthorize dialogAuthorize = new DialogAuthorize("Rechazar contenido");
                dialogAuthorize.setVisible(true);
                if (!dialogAuthorize.cancel)
                {
                    OfficeApplication.getOfficeApplicationProxy().reject(resourceInfo, dialogAuthorize.jTextAreaMessage.getText().trim());
                    loadContents();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButtonRejectActionPerformed

    private void jRadioButtonAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButtonAllActionPerformed
    {//GEN-HEADEREND:event_jRadioButtonAllActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try
        {
            loadContents();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_jRadioButtonAllActionPerformed

    private void jRadioButtonMyContentsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButtonMyContentsActionPerformed
    {//GEN-HEADEREND:event_jRadioButtonMyContentsActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try
        {
            loadContents();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_jRadioButtonMyContentsActionPerformed

    private void jRadioButtonForAuthorizeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButtonForAuthorizeActionPerformed
    {//GEN-HEADEREND:event_jRadioButtonForAuthorizeActionPerformed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try
        {
            loadContents();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_jRadioButtonForAuthorizeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonAuthorize;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonReject;
    private javax.swing.JButton jButtonSee;
    private javax.swing.JComboBox jComboBoxSites;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelCommands;
    private javax.swing.JPanel jPanelContents;
    private javax.swing.JPanel jPanelTools;
    private javax.swing.JRadioButton jRadioButtonAll;
    private javax.swing.JRadioButton jRadioButtonForAuthorize;
    private javax.swing.JRadioButton jRadioButtonMyContents;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableContents;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
