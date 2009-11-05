<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>

<%
            User owner = paramRequest.getUser();
            User user = owner;
            if (request.getParameter("user") != null)
            {
                SemanticObject semObj = SemanticObject.createSemanticObject(request.getParameter("user"));
                user = (User) semObj.createGenericInstance();
            }
            String userParam = "";
            if (owner != user)
            {
                userParam = "?user=" + user.getEncodedURI();
            }

            WebPage wpage = paramRequest.getWebPage();
            SWBResourceURL urlAction = paramRequest.getActionUrl();

            String perfilPath = paramRequest.getWebPage().getWebSite().getWebPage("perfil").getUrl();
            String requesterPath = paramRequest.getWebPage().getWebSite().getWebPage("mis_invitaciones").getUrl();
            String photo = SWBPortal.getContextPath() + "/swbadmin/images/defaultPhoto.jpg";
            boolean isStrategy = false;
            if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY)
            {
                isStrategy = true;
            }
            boolean hasInvitations = false;
            Iterator<FriendshipProspect> itFriendshipProspect = FriendshipProspect.listFriendshipProspectByFriendShipRequested(owner, wpage.getWebSite());
            if (itFriendshipProspect.hasNext())
            {
                hasInvitations = true;
            }
            if (hasInvitations)
            {
%>
<!-- <p class="addOn">Mis Invitaciones</p> -->
<h2>Mis invitaciones</h2>
<%
                int contTot = 0;
                itFriendshipProspect = FriendshipProspect.listFriendshipProspectByFriendShipRequested(owner, wpage.getWebSite());
                while (itFriendshipProspect.hasNext())
                {
                    FriendshipProspect friendshipProspect = itFriendshipProspect.next();
                    User userRequester = friendshipProspect.getFriendShipRequester();
                    if (userRequester.getPhoto() != null)
                    {
                        photo = SWBPortal.getWebWorkPath() + userRequester.getPhoto();
                    }
                    urlAction.setParameter("user", userRequester.getURI());
                    if (!isStrategy)
                    {
%>
<div class="moreUser">
    <a href="<%=perfilPath%>?user=<%=userRequester.getEncodedURI()%>"><img src="<%=photo%>" alt="<%=userRequester.getFullName()%>" width="80" height="70">
        <br><%=userRequester.getFullName()%>
    </a>
    <br>
    <%urlAction.setAction("acceptfriend");%>
    <div class="editarInfo"><p><a href="<%=urlAction%>">Aceptar</a></p></div>
    <%urlAction.setAction("noacceptfriend");%>
    <div class="editarInfo"><p><a href="<%=urlAction%>">No aceptar</a></p></div>    
</div>
<%
                    }
                    contTot++;
                }
                if (isStrategy && contTot > 0)
                {
%>
<ul class="listaElementos">
    <li><a class="contactos_nombre" href="<%=requesterPath%><%=userParam%>" >Te han invitado <%=contTot%> persona(s) a que te unas como su amigo</a></li>
</ul>
<%
                }
                else if (!hasInvitations)
                {
%>
<p class="titulo">No Te han invitado personas a ser su amigo</p>
<%                }

            }

%>
