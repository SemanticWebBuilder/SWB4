/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin;

import org.semanticwb.social.admin.data.pojo.Contact;
import org.zkoss.zul.DefaultTreeNode;

/**
 *
 * @author jorge.jimenez
 */
public class ContactTreeNode extends DefaultTreeNode<Contact> {
    /**
     *
     */
    private static final long serialVersionUID = -7012663776755277499L;

    private boolean open = false;

    public ContactTreeNode(Contact data, DefaultTreeNode<Contact>[] children) {
        super(data, children);
    }

    public ContactTreeNode(Contact data, DefaultTreeNode<Contact>[] children, boolean open) {
        super(data, children);
        setOpen(open);
    }

    public ContactTreeNode(Contact data) {
        super(data);

    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

}