/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice.ui.wizard;

import java.util.Map;
import javax.swing.JOptionPane;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPanelNavResult;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.OfficeDocument;

/**
 *
 * @author victor.lorenzana
 */
public class PublishPage extends SelectPage {

    OfficeDocument document;
    public PublishPage(String siteid,OfficeDocument document)
    {
        super(siteid);
        this.document=document;
    }
    public static String getDescription()
    {
        return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectPage").getString("SELECCIONAR_PÁGINA");
    }
    @Override
    public WizardPanelNavResult allowNext(String stepName, Map map, Wizard wizardData)
    {
        
        try
        {
            WebPage page=(WebPage)map.get(WEBPAGE);
            WebPageInfo info=new WebPageInfo();
            info.siteID=page.getSite();
            info.id=page.getID();
            String type=document.getDocumentType().toString().toUpperCase();
            if (!OfficeApplication.getOfficeDocumentProxy().canPublishToResourceContent(type,info))
            {
                JOptionPane.showMessageDialog(this, "No tiene permisos para publicar en esta página",PublishPage.getDescription(),JOptionPane.OK_OPTION | JOptionPane.ERROR);
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
        String type=document.getDocumentType().toString().toUpperCase();
        page.setEnabled(false);
        try
        {
            if (OfficeApplication.getOfficeDocumentProxy().canPublishToResourceContent(type,info))
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
