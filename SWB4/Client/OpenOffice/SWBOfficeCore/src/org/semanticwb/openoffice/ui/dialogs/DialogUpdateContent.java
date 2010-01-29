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
 * DialogSummaryPublish.java
 *
 * Created on 4 de junio de 2008, 11:38 AM
 */
package org.semanticwb.openoffice.ui.dialogs;

import java.awt.Cursor;
import java.awt.Frame;
import java.io.File;
import java.util.Hashtable;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.semanticwb.office.interfaces.PFlow;
import org.semanticwb.office.interfaces.ResourceInfo;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.OfficeDocument;
import org.semanticwb.openoffice.ui.icons.ImageLoader;
import org.semanticwb.xmlrpc.Attachment;

/**
 *
 * @author  victor.lorenzana
 */
public class DialogUpdateContent extends javax.swing.JDialog
{

    private static final String NL = "\r\n";
    private boolean updated = false;
    private String workspaceid, contentid;
    private OfficeDocument document;

    class Update extends Thread
    {

        File zipFile;
        JDialog dialog;
        ResourceInfo[] resources;
        PFlow[] flows;
        String[] msg;

        public Update(File zipFile, JDialog dialog, ResourceInfo[] resources, PFlow[] flows, String[] msg)
        {
            this.zipFile = zipFile;
            this.dialog = dialog;
            this.resources = resources;
            this.flows = flows;
            this.msg = msg;
        }

        @Override
        public void run()
        {
            try
            {
                dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                jLabel1.setText(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("ENVIANDO_ARCHIVO_DE_PUBLICACIÓN_") + " " + zipFile.getName());
                jLabel1.repaint();
                String name = document.getLocalPath().getName().replace(document.getDefaultExtension(), document.getPublicationExtension());
                document.getOfficeDocumentProxy().updateContent(workspaceid, contentid, name, resources, flows, msg);
                jProgressBar.setValue(2);
                jLabel1.setText(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("ACTUALIZACIÓN_TERMINADA"));
                summaryPublish1.loadVersions(contentid, workspaceid);
                jButtonUpdate.setEnabled(false);
                JOptionPane.showMessageDialog(dialog, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("¡CONTENIDO_ACTUALIZADO!"), dialog.getTitle(), JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("ACTUALIZACIÓN_DE_CONTENIDO"), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            }
            finally
            {
                if (zipFile != null && zipFile.exists())
                {
                    zipFile.delete();
                }
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    /** Creates new form DialogSummaryPublish */
    public DialogUpdateContent(String wokspaceid, String contentid, OfficeDocument document)
    {
        super((Frame) null, ModalityType.TOOLKIT_MODAL);
        initComponents();
        this.setIconImage(ImageLoader.images.get("semius").getImage());
        summaryPublish1.setType(document.getDocumentType().toString().toLowerCase());
        this.setModal(true);
        this.workspaceid = wokspaceid;
        this.contentid = contentid;
        summaryPublish1.loadVersions(contentid, wokspaceid);
        this.document = document;
        this.setLocationRelativeTo(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonClose = new javax.swing.JButton();
        jProgressBar = new javax.swing.JProgressBar();
        jButtonUpdate = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        summaryPublish1 = new org.semanticwb.openoffice.ui.wizard.SummaryPublish();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent"); // NOI18N
        setTitle(bundle.getString("ACTUALIZACÓN_DE_CONTENIDO")); // NOI18N
        setModal(true);
        setResizable(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(350, 50));

        jButtonClose.setText(bundle.getString("CERRAR")); // NOI18N
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jProgressBar.setMaximum(2);
        jProgressBar.setBorderPainted(false);
        jProgressBar.setOpaque(true);
        jProgressBar.setStringPainted(true);

        jButtonUpdate.setText(bundle.getString("ACTUALIZAR")); // NOI18N
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jLabel1.setText(bundle.getString("SELECIONE_LA_OPCIÓN_DE_ACTUALIZAR")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonClose)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonClose)
                        .addComponent(jButtonUpdate))
                    .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        summaryPublish1.setPreferredSize(new java.awt.Dimension(530, 250));
        getContentPane().add(summaryPublish1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
    if (!updated)
    {
        File zipFile = null;
        try
        {
            int limit = OfficeApplication.getOfficeApplicationProxy().getLimitOfVersions();
            if (limit > 0)
            {
                int versions = OfficeApplication.getOfficeDocumentProxy().getNumberOfVersions(workspaceid, contentid);
                if (OfficeApplication.getOfficeDocumentProxy().allVersionsArePublished(workspaceid, contentid))
                {
                    if (versions >= limit)
                    {
                        int resp = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("¡EL_LIMITE_MÁXIMO_DE_VERSIONES_ES_DE_") + " " + limit + "!" + NL + java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("PUEDE_PUBLICAR_ESTE_CONTENIDO,_DEBIDO_A_QUE_TIENE_TODAS_LAS_VERSIONES_PUBLICADAS,_PERO_EXCEDERÁ_DEL_LÍMITE_DE_VERSIONES") + NL + java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("¿DESEA_CONTINUAR?"), this.getTitle(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (resp == JOptionPane.NO_OPTION)
                        {
                            return;
                        }
                    }
                }
                else
                {
                    if (versions >= limit)
                    {
                        JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("¡EL_LIMITE_MÁXIMO_DE_VERSIONES_ES_DE_") + " " + limit + java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("!") + NL + java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("SI_DESEA_CREAR_UNA_NUEVA_VERSION,_DEBE_BORRAR_ALGUNA_DE_LAS_EXISTENTES,_QUE_NO_ESTE_PUBLICADA."), this.getTitle(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            //
            Hashtable<ResourceInfo, Hashtable<PFlow, String>> flows = new Hashtable<ResourceInfo, Hashtable<PFlow, String>>();
            ResourceInfo[] resources = OfficeApplication.getOfficeDocumentProxy().listResources(workspaceid, contentid);
            if (resources != null && resources.length > 0)
            {
                boolean showMessage = false;
                for (ResourceInfo resourceInfo : resources)
                {
                    if (OfficeApplication.getOfficeDocumentProxy().isInFlow(resourceInfo) && resourceInfo.version.endsWith("*"))
                    {
                        int res = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("LA_PUBLICACIÓN_DE_ESTE_CONTENIDO_EN_LA_PÁGINA_") + " " + resourceInfo.title + " " + java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("_ESTA_EN_TRÁMITE.") + NL + java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("SI_CONTINUA_SE_PERDERÁ_ESTE_PROCESO_DE_AUTORIZACIÓN,_¿DESEA_CONTINUAR?"), this.getTitle(), JOptionPane.YES_NO_OPTION);
                        if (res == JOptionPane.NO_OPTION)
                        {
                            return;
                        }
                        PFlow[] aflows = OfficeApplication.getOfficeDocumentProxy().getFlows(resourceInfo);
                        PFlow flowtoSend = null;
                        String msg = null;

                        DialogSelectFlow dialogSelectFlow = new DialogSelectFlow(resourceInfo);
                        dialogSelectFlow.setTitle(dialogSelectFlow.getTitle() + " " + java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("_PARA_PÁGINA_") + " " + resourceInfo.title);
                        dialogSelectFlow.setVisible(true);
                        if (dialogSelectFlow.selected == null)
                        {
                            return;
                        }
                        flowtoSend = dialogSelectFlow.selected;
                        msg = dialogSelectFlow.jTextAreaMessage.getText();

                        if (aflows.length == 1)
                        {
                            flowtoSend = aflows[0];
                        }
                        if (flowtoSend != null && msg != null)
                        {
                            flows.put(resourceInfo, new Hashtable<PFlow, String>());
                            flows.get(resourceInfo).put(flowtoSend, msg);
                        }
                        break;
                    }
                }

                for (ResourceInfo resourceInfo : resources)
                {
                    PFlow[] pflows = OfficeApplication.getOfficeDocumentProxy().getFlows(resourceInfo);
                    if (pflows != null && pflows.length >= 1)
                    {
                        if (resourceInfo.version.endsWith("*") && !flows.containsKey(resourceInfo))
                        {
                            showMessage = true;
                        }
                    }
                }
                if (showMessage)
                {
                    int res = JOptionPane.showConfirmDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("SE_ENCONTRÓ_QUE_ALGUNAS_PÁGINAS_REQUIREN_AUTORIZACIÓN_PARA_PUBLICAR_ESTE_CONTENIDO,_¿DESEA_CONTINUAR?"), this.getTitle(), JOptionPane.YES_NO_OPTION);
                    if (res == JOptionPane.NO_OPTION)
                    {
                        return;
                    }
                }
                for (ResourceInfo resourceInfo : resources)
                {
                    PFlow[] aflows = OfficeApplication.getOfficeDocumentProxy().getFlows(resourceInfo);
                    if (aflows != null && aflows.length >= 1)
                    {
                        // solo avisa de las que va a actualizar
                        if (resourceInfo.version.endsWith("*") && !flows.containsKey(resourceInfo))
                        {
                            PFlow flowtoSend = null;
                            String msg = null;
                            DialogSelectFlow dialogSelectFlow = new DialogSelectFlow(resourceInfo);
                            dialogSelectFlow.setTitle(dialogSelectFlow.getTitle() + " " + java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("_PARA_PÁGINA_") + " " + resourceInfo.title);
                            dialogSelectFlow.setVisible(true);
                            if (dialogSelectFlow.selected == null)
                            {
                                return;
                            }
                            flowtoSend = dialogSelectFlow.selected;
                            msg = dialogSelectFlow.jTextAreaMessage.getText();

                            if (aflows.length == 1)
                            {
                                flowtoSend = aflows[0];
                            }
                            if (flowtoSend != null && msg != null)
                            {
                                flows.put(resourceInfo, new Hashtable<PFlow, String>());
                                flows.get(resourceInfo).put(flowtoSend, msg);
                            }
                        }
                    }
                }
            }
            ResourceInfo[] resourcestoSend = flows.keySet().toArray(new ResourceInfo[flows.size()]);
            String[] msg = new String[flows.size()];
            PFlow[] flowsToSend = new PFlow[flows.size()];
            int i = 0;
            for (ResourceInfo res : resourcestoSend)
            {
                flowsToSend[i] = flows.get(res).keySet().iterator().next();
                msg[i] = flows.get(res).get(flowsToSend[i]);
                i++;
            }


            jProgressBar.setMaximum(2);
            this.jLabel1.setText(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("CREANDO_ARCHIVO_PARA_PUBLICACIÓN_..."));
            jLabel1.repaint();
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            jProgressBar.setValue(0);
            zipFile = document.createZipFile();
            this.jLabel1.setText(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/dialogs/DialogUpdateContent").getString("ARCHIVO_DE_PUBLICACIÓN_CREADO"));
            jLabel1.repaint();
            jProgressBar.setValue(1);
            document.getOfficeDocumentProxy().addAttachment(new Attachment(zipFile, zipFile.getName()));
            Update up = new Update(zipFile, this, resourcestoSend, flowsToSend, msg);
            up.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), this.getTitle(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
        }

    }

    updated = true;
}//GEN-LAST:event_jButtonUpdateActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar;
    private org.semanticwb.openoffice.ui.wizard.SummaryPublish summaryPublish1;
    // End of variables declaration//GEN-END:variables
}
