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
public class MyInvitations extends GenericAdmResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() + "/swbadmin/jsp/microsite/perfil/myInvitations.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        User user=response.getUser();
        WebSite website=response.getWebPage().getWebSite();
        String action = response.getAction();
        if (action.equals("acceptfriend")) {
            String userProsp = request.getParameter("user");
            SemanticObject semObj = SemanticObject.createSemanticObject(userProsp);
            User user2Friend = (User) semObj.createGenericInstance();
            Friendship newFriendShip = Friendship.createFriendship(website);
            newFriendShip.addFriend(user);
            newFriendShip.addFriend(user2Friend);
            FriendshipProspect.removeFriendshipProspectByRequested(user, user2Friend, website);
        } else if (action.equals("noacceptfriend")) {
            String userProsp = request.getParameter("user");
            SemanticObject semObj = SemanticObject.createSemanticObject(userProsp);
            User user2Friend = (User) semObj.createGenericInstance();
            FriendshipProspect.removeFriendshipProspectByRequested(user, user2Friend, website);
        }
    }

}