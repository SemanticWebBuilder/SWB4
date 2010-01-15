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
public class PublishVersion extends WizardPage
{

    public static final String VERSION = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/PublishVersion").getString("VERSION");
    private String contentId,  repositoryName;

    /** Creates new form PublishVersion */
    public PublishVersion(String contentId, String repositoryName)
    {
        initComponents();
        this.contentId = contentId;
        this.repositoryName = repositoryName;
        loadVersions(contentId, repositoryName);
    }

    public void loadVersions(String contentId, String repositoryName)
    {
        try
        {
            DefaultTableModel model = (DefaultTableModel) jTableVersions.getModel();
            int rows = model.getRowCount();
            for (int i = 1; i <= rows; i++)
            {
                model.removeRow(0);
            }
            IOfficeDocument document = OfficeApplication.getOfficeDocumentProxy();
            for (VersionInfo versionInfo : document.getVersions(repositoryName, contentId))
            {
                String date = OfficeApplication.iso8601dateFormat.format(versionInfo.created);
                String[] rowData =
                {
                    versionInfo.nameOfVersion, date, versionInfo.user
                };
                model.addRow(rowData);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getDescription()
    {
        return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/PublishVersion").getString("VERSIÓN_DE_CONTENIDO");
    }

    @Override
    public WizardPanelNavResult allowFinish(String arg, Map map, Wizard wizard)
    {
        return this.allowNext(arg, map, wizard);
    }

    @Override
    public WizardPanelNavResult allowNext(String arg, Map map, Wizard wizard)
    {
        WizardPanelNavResult result = WizardPanelNavResult.PROCEED;
        if (this.jRadioButtonOneVersion.isSelected())
        {
            if (this.jTableVersions.getSelectedRow() == -1)
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/PublishVersion").getString("!DEBE_INDICAR_UNA_VERSIÓN!"), SelectVersionToOpen.getDescription(), JOptionPane.ERROR_MESSAGE);
                this.jTableVersions.requestFocus();
                result = WizardPanelNavResult.REMAIN_ON_PAGE;
            }
            else
            {                
                int row=jTableVersions.getSelectedRow();
                DefaultTableModel model=(DefaultTableModel)jTableVersions.getModel();
                String version=model.getValueAt(row, 0).toString();
                map.put(VERSION, version);
            }
        }
        else
        {
            map.put(VERSION, "*");
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
        jRadioButtonLasVersion = new javax.swing.JRadioButton();
        jRadioButtonOneVersion = new javax.swing.JRadioButton();

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
        jTableVersions.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(jTableVersions);

        add(jScrollPane3, java.awt.BorderLayout.CENTER);

        buttonGroup1.add(jRadioButtonLasVersion);
        jRadioButtonLasVersion.setSelected(true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/PublishVersion"); // NOI18N
        jRadioButtonLasVersion.setText(bundle.getString("PUBLICA_LA_ÚLTIMA_VERSIÓN")); // NOI18N
        jRadioButtonLasVersion.setToolTipText(bundle.getString("ESTA_OPCIÓN_LE_PERMITE_QUE_SIEMPRE_QUE_EXISTA_UNA_NUEVA_VERSIÓN_DE_CONTENIDO,_ESTA_SEA_MOSTRADA.")); // NOI18N
        jRadioButtonLasVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonLasVersionActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButtonLasVersion);

        buttonGroup1.add(jRadioButtonOneVersion);
        jRadioButtonOneVersion.setText(bundle.getString("PUBLICA_UNA_VERSIÓN")); // NOI18N
        jRadioButtonOneVersion.setToolTipText(bundle.getString("ESTA_OPCIÓN_PERMITE_QUE_SÓLO_UNA_VERSIÓN_SEA_MOSTRADA_EN_EL_SITIO")); // NOI18N
        jRadioButtonOneVersion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonOneVersionActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButtonOneVersion);

        add(jPanel1, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonLasVersionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButtonLasVersionActionPerformed
    {//GEN-HEADEREND:event_jRadioButtonLasVersionActionPerformed
        this.jTableVersions.setVisible(false);
        this.jTableVersions.setEnabled(false);
}//GEN-LAST:event_jRadioButtonLasVersionActionPerformed

    private void jRadioButtonOneVersionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jRadioButtonOneVersionActionPerformed
    {//GEN-HEADEREND:event_jRadioButtonOneVersionActionPerformed
        this.jTableVersions.setVisible(true);
        this.jTableVersions.setEnabled(true);
}//GEN-LAST:event_jRadioButtonOneVersionActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButtonLasVersion;
    private javax.swing.JRadioButton jRadioButtonOneVersion;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableVersions;
    // End of variables declaration//GEN-END:variables
}
