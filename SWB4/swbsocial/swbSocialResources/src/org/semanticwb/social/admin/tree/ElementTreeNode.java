/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.tree;

import org.zkoss.zul.DefaultTreeNode;

/**
 *
 * @author jorge.jimenez
 */
public class ElementTreeNode extends DefaultTreeNode<Element> {
    /**
     *
     */
    private static final long serialVersionUID = -9145887024839938516L;

    private boolean open = false;

   

    public ElementTreeNode(Element data, DefaultTreeNode<Element>[] children) {
        super(data, children);
    }

    public ElementTreeNode(Element data, DefaultTreeNode<Element>[] children, boolean open) {
        super(data, children);
        setOpen(open);
    }

    public ElementTreeNode(Element data) {
        super(data);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

}