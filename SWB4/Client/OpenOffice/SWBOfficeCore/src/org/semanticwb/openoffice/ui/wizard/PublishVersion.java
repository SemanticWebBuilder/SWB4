/*
 * PublishVersion.java
 *
 * Created on 29 de diciembre de 2008, 04:41 PM
 */

package org.semanticwb.openoffice.ui.wizard;

import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPage;
import org.netbeans.spi.wizard.WizardPanelNavResult;
import org.semanticwb.office.interfaces.IOfficeDocument;
import org.semanticwb.office.interfaces.VersionInfo;
import org.semanticwb.openoffice.OfficeApplication;

/**
 *
 * @author  victor.lorenzana
 */
public class PublishVersion extends WizardPage {

    public static final String VERSION = "version";
    private String contentId,repositoryName;
    /** Creates new form PublishVersion */
    public PublishVersion(String contentId, String repositoryName) {
        initComponents();
        this.contentId=contentId;
        this.repositoryName=repositoryName;
        loadVersions(contentId, repositoryName);
    }
    public void loadVersions(String contentId, String repositoryName)
    {        
        try
        {            
            DefaultTableModel model=(DefaultTableModel) jTableVersions.getModel(); 
            int rows=model.getRowCount();
            for(int i=1;i<=rows;i++)
            {
                model.removeRow(0);
            }
            IOfficeDocument document = OfficeApplication.getOfficeDocumentProxy();
            for (VersionInfo versionInfo : document.getVersions(repositoryName, contentId))
            {
                String date=OfficeApplication.iso8601dateFormat.format(versionInfo.created);
                String[] rowData={versionInfo.nameOfVersion,date,versionInfo.user};
                model.addRow(rowData);
            }
        }
        catch (Exception e)
        {
        }
    }
    public static String getDescription()
    {
        return "Versión de Contenido";
    }
    @Override
    public WizardPanelNavResult allowNext(String arg, Map map, Wizard wizard)
    {
        WizardPanelNavResult result = WizardPanelNavResult.PROCEED;
        if (this.jTableVersions.getSelectedRow()==-1)
        {
            JOptionPane.showMessageDialog(this, "!Debe indicar una versión!", SelectVersionToOpen.getDescription(), JOptionPane.ERROR_MESSAGE);
            this.jTableVersions.requestFocus();
            result = WizardPanelNavResult.REMAIN_ON_PAGE;
        }
        else
        {
            Object selection=this.jTableVersions.getModel().getValueAt(this.jTableVersions.getSelectedRow(), 2);
            map.put( VERSION,selection);
        }
        return result;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableVersions = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();

        setLayout(new java.awt.BorderLayout());

        jTableVersions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Versión", "Fecha de creación", "Creador"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableVersions.setEnabled(false);
        jTableVersions.setFocusable(false);
        jTableVersions.setRowSelectionAllowed(false);
        jScrollPane3.setViewportView(jTableVersions);

        add(jScrollPane3, java.awt.BorderLayout.CENTER);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Publica la última versión");
        jRadioButton1.setToolTipText("Esta opción le permite que siempre que exista una nueva versión de contenido, esta sea mostrada.");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton1);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Publica una versión");
        jRadioButton2.setToolTipText("Esta opción permite que sólo una versión sea mostrada en el sitio");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton2);

        add(jPanel1, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
    this.jTableVersions.setEnabled(false);
}//GEN-LAST:event_jRadioButton1ActionPerformed

private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
    this.jTableVersions.setEnabled(true);
}//GEN-LAST:event_jRadioButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableVersions;
    // End of variables declaration//GEN-END:variables

}
