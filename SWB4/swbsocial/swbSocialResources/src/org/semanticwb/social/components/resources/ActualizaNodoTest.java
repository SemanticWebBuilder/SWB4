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
import org.semanticwb.social.Stream;
import org.semanticwb.social.components.tree.ElementTreeNode;
import org.semanticwb.social.utils.SWBSocialResourceUtils;

/**
 *
 * @author jorge.jimenez
 */
public class ActualizaNodoTest extends GenericSocialResource {
    
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        
        WebSite wsite=(WebSite)request.getAttribute("wsite");
        request.setAttribute("action", request.getParameter("action"));
        String action=(String)request.getAttribute("action");
        System.out.println("wsite:"+wsite);
        System.out.println("action:"+action);
        //Actualiza el árbol
        ElementTreeNode treeItem=(ElementTreeNode)request.getAttribute("treeItem");
        System.out.println("treeItem k llega:"+treeItem.getData().getName());
        if(treeItem!=null)
        {
            SWBSocialResourceUtils.Resources.refreshNodeTitle(request, treeItem, action);
            SWBSocialResourceUtils.Resources.setStatusMessage(request, "Nodo insertado..."); 
        }
        
    }
    
}
