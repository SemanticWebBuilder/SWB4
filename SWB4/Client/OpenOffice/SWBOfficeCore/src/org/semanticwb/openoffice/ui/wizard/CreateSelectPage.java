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
import org.semanticwb.openoffice.components.WebPage;

/**
 *
 * @author victor.lorenzana
 */
public class CreateSelectPage extends SelectPage
{

    public CreateSelectPage(String siteid)
    {
        super(siteid, false);
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
        WebPageInfo info = new WebPageInfo();
        try {
            WebPage pageSelected = (WebPage) map.get(SelectPage.WEBPAGE);
            info.siteID = pageSelected.getSite();
            info.id = pageSelected.getID();
            if (!OfficeApplication.getOfficeApplicationProxy().canCreatePage(info)) {
                JOptionPane.showMessageDialog(this, java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectPage").getString("NO_PERMISOS"), getDescription(), JOptionPane.ERROR_MESSAGE | JOptionPane.OK_OPTION);
                return WizardPanelNavResult.REMAIN_ON_PAGE;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return super.allowNext(stepName, map, wizardData);
    }

    public void addNode(DefaultMutableTreeNode node)
    {
        if (node instanceof WebPage) {
            WebPage page = (WebPage) node;
            page.setEnabled(false);
            try {
                if (OfficeApplication.getOfficeApplicationProxy().canCreatePage(page.getWebPageInfo())) {
                    page.setEnabled(true);

                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
