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
                WebPageInfo webpage=(WebPageInfo)wizardData.get(SelectPage.WEBPAGE);
                openOfficeDocument.publishToPortletContent(repositoryName, contentID, "*", "", "", webpage);
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
