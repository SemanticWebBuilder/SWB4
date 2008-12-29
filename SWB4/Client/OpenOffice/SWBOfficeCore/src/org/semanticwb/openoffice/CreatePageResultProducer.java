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
import org.semanticwb.office.interfaces.IOfficeApplication;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.ui.wizard.TitleAndDescription;

/**
 *
 * @author victor.lorenzana
 */
public class CreatePageResultProducer implements WizardResultProducer
{

    private WebPageInfo parent;

    public CreatePageResultProducer(WebPageInfo parent)
    {
        this.parent = parent;
    }

    public Object finish(Map arg0) throws WizardException
    {
        return new BackgroundResultCreator();
    }

    class BackgroundResultCreator extends DeferredWizardResult
    {

        public void start(Map wizardData, ResultProgressHandle progress)
        {
            assert !EventQueue.isDispatchThread();
            try
            {
                IOfficeApplication openOfficeApplication = OfficeApplication.getOfficeApplicationProxy();
                String pageid = wizardData.get("").toString();
                String title = wizardData.get(TitleAndDescription.TITLE).toString();
                String description = wizardData.get(TitleAndDescription.DESCRIPTION).toString();
                openOfficeApplication.createPage(parent, pageid, title, description);
            }
            catch (Exception e)
            {
            }
        }
    }

    public boolean cancel(Map arg0)
    {
        return true;
    }
}
