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

Resource base=paramRequest.getResourceBase();
String requesterPath=base.getAttribute("requesterPath","/swb/Ciudad_Digital/Mis_invitaciones");
String perfilPath=base.getAttribute("perfilPath","/swb/Ciudad_Digital/Perfil");
String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";
boolean isStrategy=false;
if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY) isStrategy=true;
String firstName="", lastName="";
int contTot=0;
%>
   <div class="miembros">
    <p class="addOn">Mis Invitaciones</p>
<%
Iterator<FriendshipProspect> itFriendshipProspect=FriendshipProspect.listFriendshipProspectByFriendShipRequested(owner, wpage.getWebSite());
while(itFriendshipProspect.hasNext()){
    FriendshipProspect friendshipProspect=itFriendshipProspect.next();
    User userRequester=friendshipProspect.getFriendShipRequester();
    if(userRequester.getPhoto()!=null) photo=userRequester.getPhoto();
    if(userRequester.getFirstName()!=null) firstName=userRequester.getFirstName();
    if(userRequester.getLastName()!=null) lastName=userRequester.getLastName();
    urlAction.setParameter("user", userRequester.getURI());
    if(!isStrategy){
%>
    <div class="moreUser">
            <a href="<%=perfilPath%>?user=<%=userRequester.getEncodedURI()%>"><img src="<%=photo%>" title="<%=firstName%> <%=lastName%>" width="80" height="70">
                <br>
                <%=firstName%>
                <%=lastName%>
            </a>
            <br>
            <%urlAction.setAction("acceptfriend");%>
            <a href="<%=urlAction%>">Aceptar</a>|
            <%urlAction.setAction("noacceptfriend");%><a href="<%=urlAction%>">No aceptar</a>
     </div>
     <%
      }
      contTot++;
  }
if(isStrategy && contTot>0){%>
     <div class="clear">
        <p class="titulo"><a href="<%=requesterPath%><%=userParam%>" >Tienes <%=contTot%> invitacion(es) de amistad</a></p>
     </div>     
  <%}else if(contTot==0){%>
     <p class="titulo">No tienes invitaciones de amistad</p>
  <%}%>  
</div>