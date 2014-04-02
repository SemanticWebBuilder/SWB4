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
        if(request.getParameter("uri")!=null && request.getParameter("neturi")!=null && request.getParameter("code")!=null)
        {
            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("uri"));
            if(semObj!=null)
            {
                PostOut postOut=(PostOut)semObj.createGenericInstance(); 
                if(postOut!=null)
                {
                    SemanticObject semObjNet=SemanticObject.createSemanticObject(request.getParameter("neturi"));
                    SocialNetwork socialNetwork=(SocialNetwork)semObjNet.createGenericInstance();
                    WebSite wsite=WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
                    Iterator<PostOutLinksHits> itLinks=PostOutLinksHits.ClassMgr.listPostOutLinksHitsByPostOut(postOut, wsite);
                    while(itLinks.hasNext())
                    {
                        PostOutLinksHits link=itLinks.next();
                        if(link.getSocialNet()!=null && link.getSocialNet().getURI().equals(socialNetwork.getURI()) && link.getPol_code().equals(request.getParameter("code")))
                        {
                            link.setPol_hits(link.getPol_hits()+1);
                            PrintWriter out=response.getWriter();
                            out.println("<meta http-equiv=\"refresh\" content=\"0;url="+link.getTargetUrl()+"\">");
                            break;
                       }
                    }
                }
            }
        }

    }
}
