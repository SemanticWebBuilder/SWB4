/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.awt.EventQueue;
import java.util.Map;
import javax.swing.JOptionPane;
import org.netbeans.spi.wizard.DeferredWizardResult;
import org.netbeans.spi.wizard.ResultProgressHandle;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage.WizardResultProducer;
import org.semanticwb.office.interfaces.IOfficeApplication;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.ui.wizard.SelectPage;
import org.semanticwb.openoffice.ui.wizard.SelectWebPageID;
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
    public CreatePageResultProducer()
    {

    }

    public Object finish(Map map) throws WizardException
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
                String pageid = wizardData.get(SelectWebPageID.WEBPAGEID).toString();
                String title = wizardData.get(TitleAndDescription.TITLE).toString();
                String description = wizardData.get(TitleAndDescription.DESCRIPTION).toString();
                if(parent==null)
                {
                    SelectPage.WebPage pageSelected=(SelectPage.WebPage)wizardData.get(SelectPage.WEBPAGE);
                    parent=new WebPageInfo();
                    parent.siteID=pageSelected.getSite();
                    parent.id=pageSelected.getID();
                }
                openOfficeApplication.createPage(parent, pageid, title, description);
                JOptionPane.showMessageDialog(null,"¡Se ha creado la página "+ title +"!" ,"Asistente de creación de página",JOptionPane.OK_OPTION | JOptionPane.INFORMATION_MESSAGE);
                progress.finished(null);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean cancel(Map map)
    {
        return true;
    }
}
