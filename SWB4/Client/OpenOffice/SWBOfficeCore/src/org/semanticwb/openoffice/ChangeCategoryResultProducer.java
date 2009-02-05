/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice;

import java.util.Map;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage.WizardResultProducer;
import org.semanticwb.openoffice.ui.wizard.SelectCategory;

/**
 *
 * @author victor.lorenzana
 */
public class ChangeCategoryResultProducer implements WizardResultProducer
{

    private String contentID,  repositoryID;

    public ChangeCategoryResultProducer(String contentID, String repositoryID)
    {
        this.contentID = contentID;
        this.repositoryID = repositoryID;
    }

    public Object finish(Map map) throws WizardException
    {
        String newcategoryID = map.get(SelectCategory.CATEGORY_ID).toString();
        String rep = map.get(SelectCategory.REPOSITORY_ID).toString();
        if (rep.equals(repositoryID))
        {

            try
            {
                String actualCategory=OfficeApplication.getOfficeDocumentProxy().getCategory(repositoryID, contentID);
                if(!actualCategory.equals(newcategoryID))
                {
                    OfficeApplication.getOfficeDocumentProxy().changeCategory(repositoryID, contentID, newcategoryID);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean cancel(Map map)
    {
        return true;
    }
}
