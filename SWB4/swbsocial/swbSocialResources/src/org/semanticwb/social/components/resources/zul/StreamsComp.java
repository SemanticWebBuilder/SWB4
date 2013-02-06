/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources.zul;

import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.social.Stream;
import org.semanticwb.social.components.tree.ElementTreeNode;
import org.semanticwb.social.utils.SWBSocialResourceUtils;
import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import java.util.Locale;
import org.semanticwb.SWBUtils;



/**
 *
 * @author jorge.jimenez
 * @date 10/03/2012
 */

/*
 * Clase de prueba que funciona para crear y actualizar streams
 * De esta manera se podría realizar estas tareas con el framework de zkoss,
 * sin embargo, lo mas probable es que todos quieran desarrollar lo mismo pero
 * mediante recursos, en lo cual, utilizarían el zul denominado "cnf_genCRUDElement"
 */
public class StreamsComp extends GenericForwardComposer
{

    private static final long serialVersionUID = -9145887024839938516L;
    private WebSite wsite=null;
    Textbox title;
    Textbox description;
    Textbox id;
    ElementTreeNode treeItem;
    Button sendButton;
    String action;
    SemanticObject semObject;
    Stream stream;
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
        
        comp.getPage().getDesktop().getSession().getAttribute("elemenetTreeModel");
        
        if(action.equals(SWBSocialResourceUtils.ACTION_ADD))
        {
            sendButton.setLabel("Crear");
        }

        if(action.equals(SWBSocialResourceUtils.ACTION_EDIT) ||  action.equals(SWBSocialResourceUtils.ACTION_DOUBLECLICK) && requestScope.get("treeItem")!=null)
        {
            treeItem=(ElementTreeNode)requestScope.get("treeItem");
            semObject = SemanticObject.createSemanticObject(objUri);
            stream = (Stream) semObject.createGenericInstance();
            id.setValue(stream.getId());
            title.setValue(stream.getTitle());
            description.setValue(stream.getDescription());
            sendButton.setLabel("Actualizar");
        }

    }


    public void onClick$sendButton()
    {
        if(action.equals(SWBSocialResourceUtils.ACTION_ADD))
        {
            Stream stream=Stream.ClassMgr.createStream(id.getValue(), wsite);
            if(title.getValue()!=null)
            {
                stream.setTitle(title.getValue());
            }
            if(description.getValue()!=null)
            {
                stream.setDescription(description.getValue());
            }
            //Actualizar el árbol (Insertar Nodo)
            SWBSocialResourceUtils.Zkoss.setStatusMessage(SWBUtils.TEXT.getLocaleString("org.semanticwb.social.components.locales.genericCompMsgs", "msg_elementCreated",new Locale("es"))+":"+stream.getTitle());
            SWBSocialResourceUtils.Zkoss.insertNode2Tree(treeItem, stream);
        }else if(action.equals(SWBSocialResourceUtils.ACTION_EDIT) ||  action.equals(SWBSocialResourceUtils.ACTION_DOUBLECLICK) && stream!=null)
        {
            if(title.getValue()!=null)
            {
                stream.setTitle(title.getValue());
                //Actualizar el árbol (actualizar título de Nodo)
                SWBSocialResourceUtils.Zkoss.refreshNode(treeItem); 
            }
            if(description.getValue()!=null)
            {
                stream.setDescription(description.getValue());
            }
        }
    }

}
