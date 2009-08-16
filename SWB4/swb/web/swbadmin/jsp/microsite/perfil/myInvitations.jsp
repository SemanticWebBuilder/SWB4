<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

<table>
<%
User user=paramRequest.getUser();
WebPage wpage=paramRequest.getWebPage();
SWBResourceURL urlAction=paramRequest.getActionUrl();

String firstName="", lastName="";
Iterator<FriendshipProspect> itFriendshipProspect=FriendshipProspect.listFriendshipProspectByFriendShipRequested(user, wpage.getWebSite());
while(itFriendshipProspect.hasNext()){
    FriendshipProspect friendshipProspect=itFriendshipProspect.next();
    User userRequester=friendshipProspect.getFriendShipRequester();
    String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
    if(userRequester.getPhoto()!=null) photo=userRequester.getPhoto();
    if(userRequester.getFirstName()!=null) firstName=userRequester.getFirstName();
    if(userRequester.getLastName()!=null) lastName=userRequester.getLastName();
    urlAction.setParameter("user", userRequester.getURI());
%>
    <tr>
        <td align="rigth">
            <a href="<%=wpage.getParent().getUrl()%>?user=<%=userRequester.getEncodedURI()%>"><img src="<%=photo%>" title="<%=firstName%> <%=lastName%>">
                <br>
                <%=firstName%>
                <%=lastName%>
            </a>
            <br>
            <%urlAction.setAction("acceptfriend");%>
            <a href="<%=urlAction%>">Aceptar</a>|
            <%urlAction.setAction("noacceptfriend");%><a href="<%=urlAction%>">No aceptar</a>
        </td>
    </tr>
<%
}
%>
</table>
