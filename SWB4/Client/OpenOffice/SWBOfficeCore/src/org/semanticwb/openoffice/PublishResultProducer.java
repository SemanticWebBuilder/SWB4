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
package org.semanticwb.openoffice;

import java.awt.EventQueue;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.netbeans.spi.wizard.DeferredWizardResult;
import org.netbeans.spi.wizard.ResultProgressHandle;
import org.netbeans.spi.wizard.Summary;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage.WizardResultProducer;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.office.interfaces.RepositoryInfo;
import org.semanticwb.openoffice.interfaces.IOpenOfficeDocument;
import org.semanticwb.openoffice.ui.wizard.ContentProperties;
import org.semanticwb.openoffice.ui.wizard.SelectCategory;
import org.semanticwb.openoffice.ui.wizard.SummaryPublish;
import org.semanticwb.openoffice.ui.wizard.TitleAndDescription;
import org.semanticwb.openoffice.util.StackTraceUtil;
import org.semanticwb.xmlrpc.Attachment;

/**
 *
 * @author victor.lorenzana
 */
public class PublishResultProducer implements WizardResultProducer
{

    private OfficeDocument document;

    public PublishResultProducer(OfficeDocument document)
    {
        this.document = document;
    }

    class BackgroundResultCreator extends DeferredWizardResult
    {

        private static final String NL = "\r\n";

        @Override
        public void start(Map wizardData, ResultProgressHandle progress)
        {
            assert !EventQueue.isDispatchThread();
            File zipFile = null;
            try
            {
                progress.setProgress(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishResultProducer").getString("PREPARANDO_DOCUMENTO_PARA_GUARDAR..."), 0, 2);
                zipFile = document.createZipFile();
                progress.setProgress(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishResultProducer").getString("GUARDANDO_DOCUMENTO..."), 1, 2);
                IOpenOfficeDocument openOfficeDocument = document.getOfficeDocumentProxy();
                openOfficeDocument.addAttachment(new Attachment(zipFile));
                String title = wizardData.get(TitleAndDescription.TITLE).toString();
                String description = wizardData.get(TitleAndDescription.DESCRIPTION).toString();
                String categoryID = wizardData.get(SelectCategory.CATEGORY_ID).toString();
                String repositoryName = wizardData.get(SelectCategory.REPOSITORY_ID).toString();
                RepositoryInfo info = (RepositoryInfo) wizardData.get(SelectCategory.REPOSITORY_ID);
                String nodeType = wizardData.get(TitleAndDescription.NODE_TYPE).toString();
                String name = document.getLocalPath().getName().replace(document.getDefaultExtension(), document.getPublicationExtension());
                HashMap<PropertyInfo, String> properties = (HashMap<PropertyInfo, String>) wizardData.get(ContentProperties.CONTENT_PROPERTIES);
                PropertyInfo[] propsToSave = properties.keySet().toArray(new PropertyInfo[properties.keySet().size()]);
                String[] values = properties.values().toArray(new String[properties.values().size()]);
                String contentID = openOfficeDocument.save(title, description, repositoryName, categoryID, document.getDocumentType().toString(), nodeType, name, propsToSave, values);
                document.deleteAssociation(false);
                document.saveContentId(contentID, repositoryName);
                int res = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishResultProducer").getString("¿DESEA_PUBLICAR_ESTE_CONTENIDO_EN_UNA_PÁGINA_WEB?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishResultProducer").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (res == JOptionPane.YES_OPTION)
                {
                    
                    if (info.exclusive && info.siteInfo != null)
                    {
                        document.publish(title, description, info.siteInfo.id);
                    }
                    else
                    {
                        document.publish(title, description, null);
                    }
                }
                Summary summary = Summary.create(new SummaryPublish(contentID, repositoryName, document.getDocumentType().toString().toUpperCase()), null);
                progress.finished(summary);
            }
            catch (Exception e)
            {
                progress.failed(e.getMessage() + NL + StackTraceUtil.getStackTrace(e), false);
            }
            finally
            {
                if (zipFile != null && zipFile.exists())
                {
                    zipFile.delete();
                }
            }
        }
    }

    @Override
    public boolean cancel(Map map)
    {
        return true;
    }

    @Override
    public Object finish(Map map) throws WizardException
    {
        return new BackgroundResultCreator();
    }
}
