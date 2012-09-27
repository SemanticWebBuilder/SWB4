/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.admin.components;

import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.admin.tree.ElementTreeNode;
import org.semanticwb.social.utils.SWBSocialResourceUtils;
import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;


/**
 *
 * @author jorge.jimenez
 */
public class TopicsComp extends GenericForwardComposer
{

    private static final long serialVersionUID = -9145887024839938515L;

    Textbox title;
    Textbox description;
    Textbox id;
    private WebSite wsite=null;
    ElementTreeNode parentItem;
    ElementTreeNode item;
    Button sendButton;
    String action;
    SemanticObject semObject;
    SocialTopic socialTopic;
    WebPage optionWepPage;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        wsite=(WebSite)requestScope.get("wsite");
        parentItem=(ElementTreeNode)requestScope.get("parentItem");
        optionWepPage=(WebPage)requestScope.get("optionWepPage");

        action=(String)requestScope.get("action");

        if(action!=null)
        {
            if(action.equals(SWBSocialResourceUtils.ACTION_ADD))
            {
                sendButton.setLabel("Crear");
            }else  if(action.equals(SWBSocialResourceUtils.ACTION_EDIT) && requestScope.get("item")!=null)
            {
                item=(ElementTreeNode)requestScope.get("item");
                semObject = SemanticObject.createSemanticObject(item.getData().getUri());
                socialTopic = (SocialTopic) semObject.createGenericInstance();
                id.setValue(socialTopic.getId());
                title.setValue(socialTopic.getTitle());
                description.setValue(socialTopic.getDescription());
                sendButton.setLabel("Actualizar");
            }else
            {
                System.out.println("action:"+action);
            }
        }


    }


    public void onClick$sendButton()
    {
        if(action.equals(SWBSocialResourceUtils.ACTION_ADD))
        {
            SocialTopic socialTopic=SocialTopic.ClassMgr.createSocialTopic(id.getValue(), wsite);
            if(title.getValue()!=null)
            {
                socialTopic.setTitle(title.getValue());
            }
            if(description.getValue()!=null)
            {
                socialTopic.setDescription(description.getValue());
            }
            //Actualizar el árbol (Insertar Nodo)
            SWBSocialResourceUtils.Components.insertTreeNode(parentItem, socialTopic, optionWepPage);
        }else if(action.equals(SWBSocialResourceUtils.ACTION_EDIT) && socialTopic!=null)
        {
            if(title.getValue()!=null)
            {
                socialTopic.setTitle(title.getValue());
                //Actualizar el árbol (actualizar título de Nodo)
                SWBSocialResourceUtils.Components.updateTreeNode(item, title.getValue());
            }
            if(description.getValue()!=null)
            {
                socialTopic.setDescription(description.getValue());
            }
        }
    }

}
