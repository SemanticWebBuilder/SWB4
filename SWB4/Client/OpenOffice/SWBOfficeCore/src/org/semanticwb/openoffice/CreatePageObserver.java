/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice;

import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardObserver;
import org.semanticwb.openoffice.ui.wizard.SelectWebPageID;

/**
 *
 * @author victor.lorenzana
 */
public class CreatePageObserver implements WizardObserver {

    SelectWebPageID page;
    public CreatePageObserver(SelectWebPageID page)
    {
        this.page=page;
    }

    public void stepsChanged(Wizard wizard)
    {
        
    }

    public void navigabilityChanged(Wizard wizard)
    {
        String current=wizard.getCurrentStep();
        if(current.equals(page.getClass().getName()))
        {
            page.setId();
        }
    }

    public void selectionChanged(Wizard wizard)
    {
        
    }

}
