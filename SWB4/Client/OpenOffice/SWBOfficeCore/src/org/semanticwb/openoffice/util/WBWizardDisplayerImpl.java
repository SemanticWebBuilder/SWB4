/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice.util;

import java.awt.Dialog.ModalityType;
import java.awt.Frame;
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
        JDialog dialog=new JDialog((Frame)null, ModalityType.TOOLKIT_MODAL);
        dialog.setModal(true);
        dialog.setAlwaysOnTop(true);
        dialog.setModalityType(ModalityType.TOOLKIT_MODAL);
        return dialog;
    }
    
}
