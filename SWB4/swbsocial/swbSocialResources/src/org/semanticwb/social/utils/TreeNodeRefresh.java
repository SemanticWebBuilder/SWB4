/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.utils;

import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBModel;
import org.semanticwb.social.components.tree.ElementTreeNode;

/**
 *
 * @author jorge.jimenez
 */
public class TreeNodeRefresh {

    private ElementTreeNode treeNode;
    //private SWBModel model;
    private SWBClass swbClass;
    private String title;

    public TreeNodeRefresh()
    {
        treeNode=null;
        //model=null;
        swbClass=null;
        title=null;
    }

    public TreeNodeRefresh(ElementTreeNode treeNode, SWBClass swbClass)
    {
        this.treeNode=treeNode;
        //this.model=model;
        this.swbClass=swbClass;
    }

    public TreeNodeRefresh(ElementTreeNode treeNode, String title)
    {
        this.treeNode=treeNode;
        //this.model=model;
        this.swbClass=null;
        this.title=title;
    }

    public ElementTreeNode getTreeNode()
    {
        return treeNode;
    }
    /*
    public SWBModel getModel()
    {
        return model;
    }*/

    public SWBClass getSWBClass()
    {
        return swbClass;
    }

    public String getTitle()
    {
        return title;
    }

}
