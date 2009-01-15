/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice;

import java.awt.EventQueue;
import java.util.Map;
import org.netbeans.spi.wizard.DeferredWizardResult;
import org.netbeans.spi.wizard.ResultProgressHandle;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage.WizardResultProducer;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.interfaces.IOpenOfficeDocument;
import org.semanticwb.openoffice.ui.wizard.PublishVersion;
import org.semanticwb.openoffice.ui.wizard.SelectPage;

/**
 *
 * @author victor.lorenzana
 */
public class PublishContentToWebPageResultProducer implements WizardResultProducer{

    private String contentID,repositoryName;
    public PublishContentToWebPageResultProducer(String contentID,String repositoryName)
    {
        this.contentID=contentID;
        this.repositoryName=repositoryName;
    }

    class BackgroundResultCreator extends DeferredWizardResult
    {

        public void start(Map wizardData, ResultProgressHandle progress)
        {
            assert !EventQueue.isDispatchThread();
            try
            {
                IOpenOfficeDocument openOfficeDocument=OfficeApplication.getOfficeDocumentProxy();
                SelectPage.WebPage page=(SelectPage.WebPage)wizardData.get(SelectPage.WEBPAGE);
                WebPageInfo webpage= new WebPageInfo();
                webpage.id=page.getID();
                webpage.siteID=page.getSite();
                String version=wizardData.get(PublishVersion.VERSION).toString();
                openOfficeDocument.publishToPortletContent(repositoryName, contentID, version, "Demo", "Demo", webpage);
                progress.finished(null);
            }
            catch(Exception e)
            {
                
            }

        }
    }
    public Object finish(Map arg0) throws WizardException
    {
        return new BackgroundResultCreator();
    }

    public boolean cancel(Map arg0)
    {
        return true;
    }

}
