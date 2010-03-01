/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.security.auth.Subject;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfirmRegistry.
 * 
 * @author jorge.jimenez
 */
public class ConfirmRegistry extends GenericResource {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(ConfirmRegistry.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        try{
            User olduser=paramsRequest.getUser();
            String login=request.getParameter("login");
            User user = paramsRequest.getWebPage().getWebSite().getUserRepository().getUserByLogin(login);
            user.setActive(true);
            user.setRequireConfirm(false);

            olduser.getSemanticObject().setRDFResource(user.getSemanticObject().getRDFResource());
            //TODO:
            //Subject subject = SWBPortal.getUserMgr().getSubject(request, website.getId());
            //subject.getPrincipals().clear();
            //user.setDefaultData(user);
            //subject.getPrincipals().add(user);

            request.setAttribute("2confirm","1");
            request.setAttribute("user",user);
            request.setAttribute("paramRequest", paramsRequest);
            RequestDispatcher dis = request.getRequestDispatcher("/swbadmin/jsp/microsite/RegisterUser/messages.jsp");
            dis.include(request, response);
         }catch(Exception e){
            log.error(e);
        }
    }

}
