/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.components.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.lib.SWBResponse;
import org.semanticwb.social.components.tree.ElementTreeNode;
import org.semanticwb.social.utils.SWBSocialResourceUtils;

/**
 *
 * @author jorge.jimenez
 * @date 10/03/2012
 */
/*
 * Recurso de prueba, solo para ver que si lleguen los parametros que se envían desde el árbol
 */
public class GenericSocialResource extends GenericAdmResource
{
    private static Logger log = SWBUtils.getLogger(GenericSocialResource.class);


    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        WebSite wsite=WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
        if(wsite!=null)
        {
            ElementTreeNode treeItem=SWBSocialResourceUtils.Components.getComponentbyUri(request);
            request.setAttribute("wsite", wsite);
            request.setAttribute("action", request.getParameter("action"));
            if(request.getParameter("objUri")!=null)
            {
                request.setAttribute("objUri", URLDecoder.decode(request.getParameter("objUri")));
            }
            request.setAttribute("treeItem", treeItem);
        }
        //Manda llamar al metodo del padre.
        super.processRequest(request, response, paramRequest);
    }


    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       String action=(String)request.getAttribute("action");
       String objUri=(String)request.getAttribute("objUri");
       WebSite wsite=(WebSite)request.getAttribute("wsite");
       User user=paramRequest.getUser();
       /*
       System.out.println("BrandResource View...action:"+action+",objUri:"+objUri+",wsite:"+wsite);
       System.out.println("User:"+user);
       System.out.println("adminTopic:"+paramRequest.getAdminTopic().getId());
       System.out.println("webPage id:"+paramRequest.getWebPage().getUrl());
        *
        */
       PrintWriter out = response.getWriter();
       out.println("Contenido de Prueba Genérico.Jogg...");
       if(objUri!=null && action!=null && action.equals(SWBSocialResourceUtils.ACTION_REMOVE)) //Eliminar un semanticObject
       {
           //Se elimina algo específico del objeto semantico (puede ser algo de filesystem, algo de BD, algun mensaje a un webservice,etc)
           //Y siempre se debe envíar a llamar al siguiente metodo para actualizar el arbol.
           SWBSocialResourceUtils.Components.updateTreeNode(request, paramRequest);
       }
       out.println("Contenido de Prueba Genérico.Jogg...-1");
    }
    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
       System.out.println("BrandResource Admin...");
    }

}