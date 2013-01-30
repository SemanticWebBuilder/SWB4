/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.components.resources;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.Stream;
import org.semanticwb.social.components.tree.ElementTreeNode;
import org.semanticwb.social.utils.SWBSocialResourceUtils;

/**
 *
 * @author jorge.jimenez
 */
public class CreaNodoTest extends GenericSocialResource {
    
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        
        WebSite wsite=(WebSite)request.getAttribute("wsite");
        Stream new_Stream=null;        
        if(wsite!=null)
        {
            new_Stream=Stream.ClassMgr.createStream(wsite);
            System.out.println("new_Stream:"+new_Stream);
        }else
        {
            System.out.println("wsite Attribute es nulo");
            wsite=WebSite.ClassMgr.getWebSite("WebBuider");
            System.out.println("wsite Attribute es nulo, pero ahora es:"+wsite.getId());
            new_Stream=Stream.ClassMgr.createStream(wsite);
        }
        
        //Actualiza el árbol
        ElementTreeNode treeItem=(ElementTreeNode)request.getAttribute("treeItem");
        System.out.println("treeItem k llega:"+treeItem.getData().getName());
        if(treeItem!=null)
        {
            SWBSocialResourceUtils.Resources.insertTreeNode(request, paramRequest, treeItem, new_Stream);
            SWBSocialResourceUtils.Resources.setStatusMessage(request, paramRequest, "Nodo insertado..."); 
        }
        
    }
    
}
