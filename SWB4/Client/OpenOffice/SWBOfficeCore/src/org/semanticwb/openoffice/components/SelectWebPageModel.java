/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.openoffice.components;

import java.util.HashSet;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author victor.lorenzana
 */
public class SelectWebPageModel extends javax.swing.tree.DefaultTreeModel implements NodeEvent
{
    private HashSet<NodeEvent> events = new HashSet<NodeEvent>();
    public SelectWebPageModel(String id)
    {
        super(new Root(id));
        ((Root)this.getRoot()).addAddNodeListener(this);

    }
    public void addAddNodeListener(NodeEvent event)
    {
        events.add(event);
    }
    public SelectWebPageModel()
    {
        super(new Root());
    }
    public void selectNode(DefaultMutableTreeNode node)
    {
        for (NodeEvent event : events) {
            event.selectNode(node);
        }
    }

    public void addNode(DefaultMutableTreeNode node)
    {
        for (NodeEvent event : events) {
            event.addNode(node);
        }
    }
}
