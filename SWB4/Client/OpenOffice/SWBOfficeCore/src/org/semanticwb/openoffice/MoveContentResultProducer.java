/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.awt.Cursor;
import java.util.Map;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage.WizardResultProducer;
import org.semanticwb.office.interfaces.ResourceInfo;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.ui.dialogs.DialogEditResource;
import org.semanticwb.openoffice.ui.wizard.SelectPage;
import org.semanticwb.openoffice.ui.wizard.SelectPage.WebPage;

/**
 *
 * @author victor.lorenzana
 */
public class MoveContentResultProducer implements WizardResultProducer
{

    DialogEditResource dialogEditResource;

    public MoveContentResultProducer(DialogEditResource dialogEditResource)
    {
        this.dialogEditResource = dialogEditResource;
    }

    public Object finish(Map map) throws WizardException
    {
        try
        {
            dialogEditResource.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            WebPage webpage = (SelectPage.WebPage) map.get(SelectPage.WEBPAGE);
            WebPageInfo webPageInfo=new WebPageInfo();
            webPageInfo.id=webpage.getID();
            webPageInfo.siteID=webpage.getSite();
            OfficeApplication.getOfficeDocumentProxy().changeResourceOfWebPage(dialogEditResource.pageInformation, webPageInfo);            
            for (ResourceInfo resourceInfo : OfficeApplication.getOfficeDocumentProxy().listResources(dialogEditResource.repositoryName, dialogEditResource.contentID))
            {
                if (resourceInfo.id.equals(dialogEditResource.pageInformation.id))
                {
                    dialogEditResource.pageInformation = resourceInfo;
                }
            }
            dialogEditResource.initialize();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            dialogEditResource.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        return null;
    }

    public boolean cancel(Map map)
    {
        return true;
    }
}
