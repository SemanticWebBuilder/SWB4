/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources.zul;

import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.Twitter;
import org.semanticwb.social.components.tree.ElementTreeNode;
import org.semanticwb.social.utils.SWBSocialResourceUtils;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import java.util.Locale;
import org.semanticwb.SWBUtils;

/**
 *
 * @author jorge.jimenez
 * @date 10/03/2012
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
    ElementTreeNode treeItem;
    Button sendButton;
    String action;
    SemanticObject semObject;
    SocialNetwork socialNet;
    WebPage optionWepPage;
    String objUri;
    SWBParamRequest paramRequest=null;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        wsite=(WebSite)requestScope.get("wsite");
        treeItem=(ElementTreeNode)requestScope.get("treeItem");
        action=(String)requestScope.get("action");
        objUri=(String)requestScope.get("objUri");
        /*
        System.out.println("SocialNetworkComp/wsite:"+wsite);
        System.out.println("SocialNetworkComp/treeItem:"+treeItem);
        System.out.println("SocialNetworkComp/action:"+action);
        System.out.println("SocialNetworkComp/objUri:"+objUri);
        */
        if(action.equals(SWBSocialResourceUtils.ACTION_ADD))
        {
            sendButton.setLabel("Crear");
        }else if(action.equals(SWBSocialResourceUtils.ACTION_EDIT) ||  action.equals(SWBSocialResourceUtils.ACTION_DOUBLECLICK) && requestScope.get("treeItem")!=null)
        {
            semObject = SemanticObject.createSemanticObject(objUri);
            socialNet = (SocialNetwork) semObject.createGenericInstance();
            id.setValue(socialNet.getId());
            title.setValue(socialNet.getTitle());
            description.setValue(socialNet.getDescription());
            sendButton.setLabel("Actualizar");
        }else if(action.equals(SWBSocialResourceUtils.ACTION_REMOVE))
        {
            SWBSocialResourceUtils.Components.removeNodeFromTree(requestScope);
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
            SWBSocialResourceUtils.Components.setStatusMessage(SWBUtils.TEXT.getLocaleString("org.semanticwb.social.components.locales.genericCompMsgs", "msg_elementCreated",new Locale("es"))+":"+ctaNet.getTitle());
            SWBSocialResourceUtils.Components.updateTreeNode(treeItem, ctaNet);
        }else if(action.equals(SWBSocialResourceUtils.ACTION_EDIT) ||  action.equals(SWBSocialResourceUtils.ACTION_DOUBLECLICK) && socialNet!=null)
        {
            if(title.getValue()!=null)
            {
                socialNet.setTitle(title.getValue());
                //Actualizar el árbol (actualizar título de Nodo)
                SWBSocialResourceUtils.Components.updateTreeNode(treeItem, title.getValue());
                SWBSocialResourceUtils.Components.setStatusMessage("Titulo de nodo actualizado..");
            }
            if(description.getValue()!=null)
            {
                socialNet.setDescription(description.getValue());
            }
        }
    }
}
