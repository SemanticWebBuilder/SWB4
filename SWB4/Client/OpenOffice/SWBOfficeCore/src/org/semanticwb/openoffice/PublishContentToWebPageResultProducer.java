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
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage.WizardResultProducer;
import org.semanticwb.office.interfaces.ResourceInfo;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.interfaces.IOpenOfficeDocument;
import org.semanticwb.openoffice.ui.wizard.PublishVersion;
import org.semanticwb.openoffice.ui.wizard.SelectPage;
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
                SelectPage.WebPage page = (SelectPage.WebPage) wizardData.get(SelectPage.WEBPAGE);
                WebPageInfo webpage = new WebPageInfo();
                webpage.id = page.getID();
                webpage.siteID = page.getSite();
                String version = wizardData.get(PublishVersion.VERSION).toString();
                HashMap<PropertyInfo, String> properties = (HashMap<PropertyInfo, String>) wizardData.get(ViewProperties.VIEW_PROPERTIES);
                PropertyInfo[] propertiesToSend=properties.keySet().toArray(new PropertyInfo[properties.keySet().size()]);
                String[] values=new String[properties.keySet().size()];
                int i=0;
                for(PropertyInfo prop : propertiesToSend)
                {
                    values[i]=properties.get(prop);
                    i++;
                }
                ResourceInfo info = openOfficeDocument.publishToResourceContent(repositoryName, contentID, version, title, description, webpage,propertiesToSend,values);
                int res = JOptionPane.showConfirmDialog(null, "¿Desea activar el contenido?", "Publicación de contenido", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION)
                {
                    openOfficeDocument.activateResource(info, true);
                }
                JOptionPane.showMessageDialog(null, "¡Se ha publicado el documento!", "Publicación de contenido", JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
                progress.finished(null);
            }
            catch (Exception e)
            {
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
