/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice.util;

import javax.swing.JDialog;
import org.netbeans.api.wizard.displayer.WizardDisplayerImpl;
/**
 *
 * @author victor.lorenzana
 */
public class WBWizardDisplayerImpl extends WizardDisplayerImpl {

    public WBWizardDisplayerImpl()
    {
        super();
    }
    @Override
    protected JDialog createDialog()
    {
        JDialog dialog=super.createDialog();
        dialog.setAlwaysOnTop(true);
        return dialog;
    }
    
}
