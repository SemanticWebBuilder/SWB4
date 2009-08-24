<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>


<%
User owner=paramRequest.getUser();
User user=owner;
if(request.getParameter("user")!=null){
    SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("user"));
    user=(User)semObj.createGenericInstance();
}
String userParam="";
if(owner!=user) userParam="?user="+user.getEncodedURI();

WebPage wpage=paramRequest.getWebPage();
SWBResourceURL urlAction=paramRequest.getActionUrl();

String perfilPath=paramRequest.getWebPage().getWebSite().getWebPage("perfil").getUrl();
String requestedPath=paramRequest.getWebPage().getWebSite().getWebPage("mis_solicitudes").getUrl();

String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
boolean isStrategy=false;
if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) isStrategy=true;
String firstName="", lastName="";
int contTot=0;
%>
   <div class="miembros">
    <p class="addOn">Mis Solicitudes</p>
<%
Iterator<FriendshipProspect> itFriendshipProspect=FriendshipProspect.listFriendshipProspectByFriendShipRequester(owner, wpage.getWebSite());
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
                <a href="<%=perfilPath%>?user=<%=userRequested.getEncodedURI()%>"><img src="<%=photo%>" title="<%=firstName%> <%=lastName%>" width="80" height="70">
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
        <p class="titulo"><a href="<%=requestedPath%><%=userParam%>">Tienes <%=contTot%> Solicitud(es) de amistad</a></p>
     </div>     
  <%}else if(contTot==0){%>
     <p class="titulo">No tienes solicitudes de amistad &nbsp;</p>
  <%}%>  
</div>
