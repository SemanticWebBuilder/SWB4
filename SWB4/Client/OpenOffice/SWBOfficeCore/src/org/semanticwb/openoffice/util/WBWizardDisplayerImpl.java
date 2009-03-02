/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice.util;

import java.awt.Dialog.ModalityType;
import java.awt.Frame;
import javax.swing.JDialog;
import org.netbeans.api.wizard.displayer.WizardDisplayerImpl;
import org.semanticwb.openoffice.ui.icons.ImageLoader;
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
        dialog.setIconImage(ImageLoader.images.get("semius").getImage());
        dialog.setModal(true);        
        dialog.setModalityType(ModalityType.TOOLKIT_MODAL);
        return dialog;
    }
    
}
