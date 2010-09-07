/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice.ui.wizard;

import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardPanelNavResult;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.openoffice.OfficeApplication;
import org.semanticwb.openoffice.OfficeDocument;
import org.semanticwb.openoffice.components.WebPage;

/**
 *
 * @author victor.lorenzana
 */
public class PublishPage extends SelectPage {

    private OfficeDocument document;
    public PublishPage(String siteid,OfficeDocument document)
    {
        super(siteid,true);
        this.document=document;
    
    }
    public static String getDescription()
    {
        return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectPage").getString("SELECCIONAR_PÁGINA");
    }
    @Override
    public WizardPanelNavResult allowNext(String stepName, Map map, Wizard wizardData)
    {
        WizardPanelNavResult res = WizardPanelNavResult.PROCEED;
        if(selectWebPageComponent.getSelectedWebPage()==null)
        {
            res = WizardPanelNavResult.REMAIN_ON_PAGE;
            JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectPage").getString("¡DEBE_INDICAR_UNA_PÁGINA_WEB!"), getDescription(), JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
            return res;
        }
        try
        {
            WebPage page=(WebPage)map.get(WEBPAGE);
            WebPageInfo info=new WebPageInfo();
            info.siteID=page.getSite();
            info.id=page.getID();
            String type=document.getDocumentType().toString().toUpperCase();
            if (!OfficeApplication.getOfficeDocumentProxy().canPublishToResourceContent(type,info))
            {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectPage").getString("NO_PERMISSION_TO_PUBLISH"),PublishPage.getDescription(),JOptionPane.OK_OPTION | JOptionPane.ERROR_MESSAGE);
                return WizardPanelNavResult.REMAIN_ON_PAGE;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return super.allowNext(stepName, map, wizardData);
    }    
    public void addNode(DefaultMutableTreeNode node)
    {
        if(node instanceof  WebPage)
        {
            WebPage page=(WebPage)node;
            String type=document.getDocumentType().toString().toUpperCase();
            page.setEnabled(false);
            try
            {
                if (OfficeApplication.getOfficeDocumentProxy().canPublishToResourceContent(type,page.getWebPageInfo()))
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
}
