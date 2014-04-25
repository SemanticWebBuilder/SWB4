/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.PostOutLinksHits;
import org.semanticwb.social.PostOutLinksHitsIp;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.SocialNetwork;

/**
 *
 * @author jorge.jimenez
 */
public class LinksRedirector extends GenericResource {

    private static Logger log = SWBUtils.getLogger(LinksRedirector.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        System.out.println("Entra a LinksRedirector/doView/uri:"+request.getParameter("uri"));
        if(request.getParameter("uri")!=null && request.getParameter("neturi")!=null && request.getParameter("code")!=null)
        {
            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("uri"));
            if(semObj!=null)
            {
                PostOut postOut=(PostOut)semObj.createGenericInstance(); 
                System.out.println("Entra a LinksRedirector/doView/postOut:"+postOut);
                if(postOut!=null)
                {
                    SemanticObject semObjNet=SemanticObject.createSemanticObject(request.getParameter("neturi"));
                    SocialNetwork socialNetwork=(SocialNetwork)semObjNet.createGenericInstance();
                    WebSite wsite=WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
                    Iterator<PostOutLinksHits> itLinks=PostOutLinksHits.ClassMgr.listPostOutLinksHitsByPostOut(postOut, wsite);
                    while(itLinks.hasNext())
                    {
                        PostOutLinksHits link=itLinks.next();
                        System.out.println("Entra a LinksRedirector/doView/link:"+link);
                        if(link.getSocialNet()!=null && link.getSocialNet().getURI().equals(socialNetwork.getURI()) && link.getPol_code().equals(request.getParameter("code")))
                        {
                            link.setPol_hits(link.getPol_hits()+1);
                            //Ver si guardo o no la ip de cada usuario que da click en una liga en cada red, pudieran ser millones de triples a generar
                            //pero por otro lado puede ser un buen dato, despues revisar si la red social me puede enviar el user de la red social
                            //y yo pueda extraer sus datos, esto sería mas valioso, pero no lo tengo por el momento.
                            //Por el momento con la ip de los usuarios podría saber su ubicación y talvez ubicarlos en un mapa de google maps. (No esta por el mpmento)
                            PostOutLinksHitsIp postOutHitIp=PostOutLinksHitsIp.ClassMgr.createPostOutLinksHitsIp(wsite); 
                            postOutHitIp.setUserIP(request.getRemoteAddr());
                            
                            PrintWriter out=response.getWriter(); 
                            System.out.println("Entra a LinksRedirector/doView/REDIRECT A:"+link.getTargetUrl());
                            out.println("<meta http-equiv=\"refresh\" content=\"0;url="+link.getTargetUrl()+"\">");
                            break;
                       }
                    }
                }
            }
        }

    }
}
