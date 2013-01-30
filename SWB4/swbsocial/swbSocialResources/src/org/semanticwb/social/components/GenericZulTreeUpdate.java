/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBClass;
import org.semanticwb.social.components.resources.SocialZulResource;
import org.semanticwb.social.components.tree.ElementTreeNode;
import org.semanticwb.social.utils.SWBSocialResourceUtils;
import org.semanticwb.social.utils.TreeNodeRefresh;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.*;

/**
 *
 * @author jorge.jimenez
 */
public class GenericZulTreeUpdate extends GenericForwardComposer
{
    private static Logger log = SWBUtils.getLogger(SocialZulResource.class);
    ElementTreeNode treeItem;
    Button sendButton;
    String action;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        treeItem=(ElementTreeNode)requestScope.get("treeItem");
        action=(String)requestScope.get("action");
        if(action.equalsIgnoreCase(SWBSocialResourceUtils.ACTION_REMOVE))
        {
            EventQueue<Event> eq = EventQueues.lookup("removeNodo2Tree", EventQueues.SESSION, true);
            eq.publish(new Event("onRemoveNode", null, treeItem));
        }else if(action.equalsIgnoreCase("createNewBrand")){
             EventQueue<Event> eq = EventQueues.lookup("createNewBrand", EventQueues.SESSION, true);
             eq.publish(new Event("onCreateNewBrand", null, treeItem));
        }else if(action.equalsIgnoreCase(SWBSocialResourceUtils.ACTION_ADD)){
             EventQueue<Event> eq = EventQueues.lookup("insertNode2Tree", EventQueues.SESSION, true);
             SWBClass swbClass=(SWBClass)requestScope.get("swbClass");
             TreeNodeRefresh treeNodeRefresh=new TreeNodeRefresh(treeItem,swbClass);
             eq.publish(new Event("onCreateNode", null, treeNodeRefresh));
        }else if(action.equalsIgnoreCase("statusMsg")){
             String message=(String)requestScope.get("message");
             EventQueue<Event> eq = EventQueues.lookup("updateMsgWin", EventQueues.SESSION, true);
             eq.publish(new Event("onUpdateMsgWin", null, message));
        }
    }
}
