/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.community;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class PortalUsers extends GenericAdmResource {

     @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
         try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath()+"/swbadmin/jsp/microsite/perfil/portalUsers.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
     }

     @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        User owner=response.getUser();
        WebSite website=response.getWebPage().getWebSite();
        String action=response.getAction();

        if(action.equals("createProspect"))
        {
            SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("user"));
            User user=(User)semObj.createGenericInstance();
            if(!FriendshipProspect.findFriendProspectedByRequester(owner, user, website)){
                FriendshipProspect newFriendShip=FriendshipProspect.createFriendshipProspect(website);
                newFriendShip.setFriendShipRequester(owner);
                newFriendShip.setFriendShipRequested(user);
            }
        }
     }

}
