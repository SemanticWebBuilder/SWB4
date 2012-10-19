package org.semanticwb.social.resources;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.admin.resources.SWBACreateUser;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorge.jimenez
 */
public class SocialCreateUser extends SWBACreateUser
{

    private static Logger log = SWBUtils.getLogger(SocialCreateUser.class);

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        System.out.println("doEdit-1");
         try {
            WebSite wsite=paramRequest.getWebPage().getWebSite();
            System.out.println("wsite Social:"+wsite);
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher dispatcher = request.getRequestDispatcher(SWBPlatform.getContextPath()+"/work/models/"+wsite.getId()+"/admin/jsp/socialObjectTab.jsp");
            System.out.println("doEdit-2");
            if(dispatcher != null) {
                dispatcher.include(request, response);
            }
            System.out.println("doEdit-3");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
