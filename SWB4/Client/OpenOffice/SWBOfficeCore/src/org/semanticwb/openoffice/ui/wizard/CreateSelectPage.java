/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.ui.wizard;

import java.util.Map;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPanelNavResult;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.OfficeApplication;

/**
 *
 * @author victor.lorenzana
 */
public class CreateSelectPage extends SelectPage
{

    public CreateSelectPage(String siteid)
    {
        super(siteid,null);
    }

    public static String getDescription()
    {
        return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectPage").getString("SELECCIONAR_PÁGINA");
    }
    @Override
    public WizardPanelNavResult allowNext(String stepName, Map map, Wizard wizardData)
    {
        WebPageInfo info = new WebPageInfo();
        try
        {
            SelectPage.WebPage pageSelected = (SelectPage.WebPage) map.get(SelectPage.WEBPAGE);
            info.siteID=pageSelected.getSite();
            info.id=pageSelected.getID();
            if (!OfficeApplication.getOfficeApplicationProxy().canCreatePage(info))
            {
                return WizardPanelNavResult.REMAIN_ON_PAGE;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return super.allowNext(stepName, map, wizardData);
    }
    @Override
    protected void onAdd(WebPage page)
    {
        WebPageInfo info=new WebPageInfo();
        info.siteID=page.getSite();
        info.id=page.getID();
        page.setEnabled(false);
        try
        {
            if (OfficeApplication.getOfficeApplicationProxy().canCreatePage(info))
            {
                page.setEnabled(true);

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
