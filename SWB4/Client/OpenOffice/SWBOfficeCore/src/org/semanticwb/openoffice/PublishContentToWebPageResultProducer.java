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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.netbeans.spi.wizard.DeferredWizardResult;
import org.netbeans.spi.wizard.ResultProgressHandle;
import org.netbeans.spi.wizard.Summary;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage.WizardResultProducer;
import org.semanticwb.office.interfaces.ResourceInfo;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.components.WebPage;
import org.semanticwb.openoffice.interfaces.IOpenOfficeDocument;
import org.semanticwb.openoffice.ui.dialogs.DialogSelectFlow;
import org.semanticwb.openoffice.ui.wizard.PublishVersion;
import org.semanticwb.openoffice.ui.wizard.SelectPage;
import org.semanticwb.openoffice.ui.wizard.SummaryError;
import org.semanticwb.openoffice.ui.wizard.TitleAndDescription;
import org.semanticwb.openoffice.ui.wizard.ViewProperties;

/**
 *
 * @author victor.lorenzana
 */
public class PublishContentToWebPageResultProducer implements WizardResultProducer
{

    private String contentID,  repositoryName,  title,  description;

    public PublishContentToWebPageResultProducer(String contentID, String repositoryName)
    {
        this.contentID = contentID;
        this.repositoryName = repositoryName;
    }

    public PublishContentToWebPageResultProducer(String contentID, String repositoryName, String title, String description)
    {
        this(contentID, repositoryName);
        this.title = title;
        this.description = description;
    }

    class BackgroundResultCreator extends DeferredWizardResult
    {
        private static final String NL = "\r\n";
        String title, description;

        public BackgroundResultCreator(String title, String description)
        {
            this.title = title;
            this.description = description;
        }

        @Override
        public void start(Map wizardData, ResultProgressHandle progress)
        {
            assert !EventQueue.isDispatchThread();
            try
            {
                IOpenOfficeDocument openOfficeDocument = OfficeApplication.getOfficeDocumentProxy();
                WebPage page = (WebPage) wizardData.get(SelectPage.WEBPAGE);
                WebPageInfo webpage = page.getWebPageInfo();
                String version = wizardData.get(PublishVersion.VERSION).toString();
                HashMap<PropertyInfo, String> properties = (HashMap<PropertyInfo, String>) wizardData.get(ViewProperties.VIEW_PROPERTIES);
                PropertyInfo[] propertiesToSend = new PropertyInfo[0];
                String[] values = new String[0];
                if (properties != null)
                {
                    propertiesToSend = properties.keySet().toArray(new PropertyInfo[properties.keySet().size()]);
                    values = new String[properties.keySet().size()];
                }
                int i = 0;
                for (PropertyInfo prop : propertiesToSend)
                {
                    values[i] = properties.get(prop);
                    i++;
                }
                ResourceInfo info = openOfficeDocument.publishToResourceContent(repositoryName, contentID, version, title, description, webpage, propertiesToSend, values);

                if (openOfficeDocument.needsSendToPublish(info))
                {
                    int res = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishContentToWebPageResultProducer").getString("EL_CONTENIDO_NECESITA_SER_AUTORIZADO_PARA_PRESENTARSE_EN_EL_SITIO.")+ NL +java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishContentToWebPageResultProducer").getString("¿DESEA_ENVIARLO_A_AUTORIZACIÓN?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishContentToWebPageResultProducer").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (res == JOptionPane.YES_OPTION)
                    {
                        DialogSelectFlow dialogSelectFlow=new DialogSelectFlow(info);
                        dialogSelectFlow.setVisible(true);
                        if(dialogSelectFlow.selected!=null)
                        {
                            openOfficeDocument.sendToAuthorize(info,dialogSelectFlow.selected,dialogSelectFlow.jTextAreaMessage.getText().trim());
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishContentToWebPageResultProducer").getString("¡PARA_ACTIVAR_ESTE_CONTENIDO_DEBE_SER_AUTORIZADO_PRIMERO!"),java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishContentToWebPageResultProducer").getString("PUBLICACIÓN_DE_CONTENIDO"),JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
                else
                {
                    int res = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishContentToWebPageResultProducer").getString("¿DESEA_ACTIVAR_EL_CONTENIDO?"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishContentToWebPageResultProducer").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (res == JOptionPane.YES_OPTION)
                    {
                        openOfficeDocument.activateResource(info, true);
                        JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishContentToWebPageResultProducer").getString("¡SE_HA_PUBLICADO_EL_DOCUMENTO!"), java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishContentToWebPageResultProducer").getString("PUBLICACIÓN_DE_CONTENIDO"), JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                
                progress.finished(null);
            }
            catch (Exception e)
            {                
                Summary err=Summary.create(new SummaryError(java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/PublishContentToWebPageResultProducer").getString("ERROR_AL_PUBLICAR_CONTENIDO:"),e), null);
                progress.finished(err);                
                e.printStackTrace();
            }

        }
    }

    @Override
    public Object finish(Map map) throws WizardException
    {
        if (title == null || description == null)
        {
            title = map.get(TitleAndDescription.TITLE).toString();
            description = map.get(TitleAndDescription.DESCRIPTION).toString();
        }
        return new BackgroundResultCreator(title, description);
    }

    @Override
    public boolean cancel(Map arg0)
    {
        return true;
    }
}
