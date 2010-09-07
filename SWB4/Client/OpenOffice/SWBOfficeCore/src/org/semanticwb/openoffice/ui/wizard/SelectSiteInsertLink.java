/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice.ui.wizard;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author victor.lorenzana
 */
public class SelectSiteInsertLink extends SelectPage
{
    public SelectSiteInsertLink()
    {
        super(null,true);
    }
    @Override
    public void addNode(DefaultMutableTreeNode node)
    {
        
    }
    public static String getDescription()
    {
        return java.util.ResourceBundle.getBundle("org/semanticwb/openoffice/ui/wizard/SelectPage").getString("SELECCIONAR_PÁGINA");
    }

}
