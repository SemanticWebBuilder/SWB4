<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>


<div class="miembros">
<h2 class="titulo">Solicitudes</h2>
<%
User user=paramRequest.getUser();
WebPage wpage=paramRequest.getWebPage();
SWBResourceURL urlAction=paramRequest.getActionUrl();
Resource base=paramRequest.getResourceBase();
String requestedPath=base.getAttribute("requestedPath","/swb/Ciudad_Digital/Mis_Solicitudes");
String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
boolean isStrategy=false;
if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) isStrategy=true;

String firstName="", lastName="";
int contTot=0;

Iterator<FriendshipProspect> itFriendshipProspect=FriendshipProspect.listFriendshipProspectByFriendShipRequester(user, wpage.getWebSite());
while(itFriendshipProspect.hasNext()){
    FriendshipProspect friendshipProspect=itFriendshipProspect.next();
    User userRequested=friendshipProspect.getFriendShipRequested();
    if(userRequested.getPhoto()!=null) photo=userRequested.getPhoto();
    if(userRequested.getFirstName()!=null) firstName=userRequested.getFirstName();
    if(userRequested.getLastName()!=null) lastName=userRequested.getLastName();
    urlAction.setParameter("user", userRequested.getURI());
    if(!isStrategy){
%>
        <div class="moreUser">
                <a href="<%=wpage.getParent().getUrl()%>?user=<%=userRequested.getEncodedURI()%>"><img src="<%=photo%>" title="<%=firstName%> <%=lastName%>">
                    <br>
                    <%=firstName%>
                    <%=lastName%>
                </a>
                <br>
                <%urlAction.setAction("removeRequest");%>
                <a href="<%=urlAction%>">Eliminar Solicitud</a>
        </div>
    <%
        }
        contTot++;
  }
  if(isStrategy && contTot>0){%>
     <div class="clear">
        <p class="vermas"><a href="<%=requestedPath%>" >Tienes <%=contTot%> Solicitud(es) de amistad</a></p>
     </div>
  <%}else if(contTot==0){%>
     <p class="vermas">No tienes solicitudes de amistad &nbsp;</p>
  <%}%>
</div>
