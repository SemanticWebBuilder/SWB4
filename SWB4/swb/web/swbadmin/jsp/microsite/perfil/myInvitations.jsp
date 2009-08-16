<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="java.util.*"%>

<table>
<%
User user=paramRequest.getUser();
WebPage wpage=paramRequest.getWebPage();
WebSite website=wpage.getWebSite();
Iterator<FriendshipProspect> itFriendshipProspect=FriendshipProspect.listFriendshipProspectByFriendShipRequested(user, wpage.getWebSite());
while(itFriendshipProspect.hasNext()){
    FriendshipProspect friendshipProspect=itFriendshipProspect.next();
    User userRequester=friendshipProspect.getFriendShipRequester();
    String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
    if(userRequester.getPhoto()!=null) photo=userRequester.getPhoto();
%>
    <tr>
        <td>
            <a href="/swb/Ciudad_Digital/Perfil?user=<%=userRequester.getURI()%>"><img src="<%=photo%>"></a>
        </td>
        <td><a href="<%=wpage.getUrl()%>?acceptfriend=<%=userRequester.getEncodedURI()%>">Aceptar</a>|<a href="<%=wpage.getUrl()%>?noacceptfriend=<%=userRequester.getEncodedURI()%>">No aceptar</a></td>
    </tr>
<%
}
%>
</table>
    <%
        if(request.getParameter("acceptfriend")!=null){
            String userProsp=request.getParameter("acceptfriend");
            SemanticObject semObj=SemanticObject.createSemanticObject(userProsp);
            User user2Friend=(User)semObj.createGenericInstance();
            Friendship newFriendShip=Friendship.createFriendship(website);
            newFriendShip.addFriend(user);
            newFriendShip.addFriend(user2Friend);
            removeFriendshipProspect(user, user2Friend, website);
        }

        if(request.getParameter("noacceptfriend")!=null){
            String userProsp=request.getParameter("noacceptfriend");
            SemanticObject semObj=SemanticObject.createSemanticObject(userProsp);
            User user2Friend=(User)semObj.createGenericInstance();
            removeFriendshipProspect(user, user2Friend, website);
        }
    %>

    <%!
        private boolean removeFriendshipProspect(User user, User user2Friend, WebSite website){
            Iterator<FriendshipProspect> itFriendshipProspect=FriendshipProspect.listFriendshipProspectByFriendShipRequested(user, website);
            while(itFriendshipProspect.hasNext()){
                FriendshipProspect friendshipProspect=itFriendshipProspect.next();
                User userRequester=friendshipProspect.getFriendShipRequester();
                if(userRequester.getURI().equals(user2Friend.getURI())){
                    friendshipProspect.remove();
                    return true;
                }
            }
            return false;
        }
    %>