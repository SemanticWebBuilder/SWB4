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
Iterator<FriendshipProspect> itFriendshipProspect=FriendshipProspect.listFriendshipProspectByFriendShipRequester(user, wpage.getWebSite());
while(itFriendshipProspect.hasNext()){
    FriendshipProspect friendshipProspect=itFriendshipProspect.next();
    User userRequested=friendshipProspect.getFriendShipRequested();
    String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
    if(userRequested.getPhoto()!=null) photo=userRequested.getPhoto();
    if(userRequested.getFirstName()!=null) firstName=userRequested.getFirstName();
    if(userRequested.getLastName()!=null) lastName=userRequested.getLastName();
    urlAction.setParameter("user", userRequested.getURI());
%>
    <tr>
        <td align="rigth">
            <a href="<%=wpage.getParent().getUrl()%>?user=<%=userRequested.getEncodedURI()%>"><img src="<%=photo%>" title="<%=firstName%> <%=lastName%>">
                <br>
                <%=firstName%>
                <%=lastName%>
            </a>
            <br>
            <%urlAction.setAction("removeRequest");%>
            <a href="<%=urlAction%>">Eliminar Solicitud</a>
        </td>
    </tr>
<%
}
%>
</table>
