/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources.zul;

import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.Twitter;
import org.semanticwb.social.components.tree.ElementTreeNode;
import org.semanticwb.social.utils.SWBSocialResourceUtils;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;

/**
 *
 * @author jorge.jimenez
 */

/*
 * Clase de prueba que funciona para crear y actualizar redes sociales
 * De esta manera se podría realizar estas tareas con el framework de zkoss,
 * sin embargo, lo mas probable es que todos quieran desarrollar lo mismo pero
 * mediante recursos, en lo cual, utilizarían el zul denominado "cnf_genCRUDElement"
 */

public class SocialNetworkComp extends GenericForwardComposer {

    private static final long serialVersionUID = -9145887024839938516L;
    private WebSite wsite=null;
    Textbox title;
    Textbox description;
    Textbox id;
    ElementTreeNode parentItem;
    ElementTreeNode item;
    Button sendButton;
    String action;
    SemanticObject semObject;
    SocialNetwork socialNet;
    WebPage optionWepPage;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        wsite=(WebSite)requestScope.get("wsite");
        parentItem=(ElementTreeNode)requestScope.get("parentItem");
        action=(String)requestScope.get("action");
        optionWepPage=(WebPage)requestScope.get("optionWepPage");

        if(action.equals(SWBSocialResourceUtils.ACTION_ADD))
        {
            sendButton.setLabel("Crear");
        }

        if(action.equals(SWBSocialResourceUtils.ACTION_EDIT) && requestScope.get("item")!=null)
        {
            item=(ElementTreeNode)requestScope.get("item");
            System.out.println("item en SocialNetworkComp:"+item);
            semObject = SemanticObject.createSemanticObject(item.getData().getUri());
            System.out.println("semObject en SocialNetworkComp:"+semObject);
            socialNet = (SocialNetwork) semObject.createGenericInstance();
            id.setValue(socialNet.getId());
            title.setValue(socialNet.getTitle());
            description.setValue(socialNet.getDescription());
            sendButton.setLabel("Actualizar");
        }
    }
    
    public void onClick$sendButton()
    {
        if(action.equals(SWBSocialResourceUtils.ACTION_ADD))
        {
            //Facebook ctaNet=Facebook.ClassMgr.createFacebook(id.getValue(), wsite);
            Twitter ctaNet=Twitter.ClassMgr.createTwitter(id.getValue(), wsite);
            if(title.getValue()!=null)
            {
                ctaNet.setTitle(title.getValue());
            }
            if(description.getValue()!=null)
            {
                ctaNet.setDescription(description.getValue());
            }
            //Actualizar el árbol (Insertar Nodo)
            SWBSocialResourceUtils.Components.insertTreeNode(parentItem, ctaNet, optionWepPage);
        }else if(action.equals(SWBSocialResourceUtils.ACTION_EDIT) && socialNet!=null)
        {
            if(title.getValue()!=null)
            {
                socialNet.setTitle(title.getValue());
                //Actualizar el árbol (actualizar título de Nodo)
                SWBSocialResourceUtils.Components.updateTreeNode(item, title.getValue());
            }
            if(description.getValue()!=null)
            {
                socialNet.setDescription(description.getValue());
            }
        }
    }


}
