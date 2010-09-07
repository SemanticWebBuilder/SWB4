/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.openoffice.components;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author victor.lorenzana
 */
public class TreeRender extends JPanel implements TreeCellRenderer
{

    public Component getTreeCellRendererComponent(JTree tree, Object object, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        Component component = this;
        tree.setToolTipText("");
        if (object instanceof Site) {
            component = ((Site) object).getComponent();
            if (hasFocus) {
                //tree.setToolTipText(((Site) object).);
            }
        }
        if (object instanceof WebPage) {
            component = ((WebPage) object).getComponent();
            if (hasFocus) {
                tree.setToolTipText(((WebPage) object).getWebPageInfo().description);
            }
        }
        if (component != null && component instanceof JLabel) {
            JLabel label = (JLabel) component;

            label.setFont(tree.getFont());
            if (object instanceof WebPage) {
                if (object instanceof HomeWebPage) {
                    HomeWebPage homeWebPage = (HomeWebPage) object;
                    if (homeWebPage.isActive()) {
                        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/home.png")));
                    }
                    else {
                        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/home_deactivated.png")));
                    }
                }
                else {
                    WebPage page = (WebPage) object;
                    if (page.isActive()) {
                        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/page.png")));
                    }
                    else {
                        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/pag_deactivated.png")));
                    }
                }
            }
            else {
                label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/semanticwb/openoffice/ui/icons/site.png")));
            }
            if (expanded) {
            }
            else {
            }
            if (selected) {
                label.setBackground(Color.BLUE);
                label.setForeground(Color.WHITE);
            }
            else {
                label.setBackground(tree.getBackground());
                label.setForeground(tree.getForeground());
            }
        }
        return component;

    }
}
