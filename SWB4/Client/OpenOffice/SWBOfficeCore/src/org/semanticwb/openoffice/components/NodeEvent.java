/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice.components;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author victor.lorenzana
 */
public interface NodeEvent {

    public void selectNode(DefaultMutableTreeNode node);
    public void addNode(DefaultMutableTreeNode node);
}
