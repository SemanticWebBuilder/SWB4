<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<h2>Mis solicitudes</h2>
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
            String requestedPath = paramRequest.getWebPage().getWebSite().getWebPage("mis_solicitudes").getUrl();

            String photo = SWBPortal.getContextPath() + "/swbadmin/images/defaultPhoto.jpg";
            boolean isStrategy = false;
            if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY)
            {
                isStrategy = true;
            }
            int contTot = 0;
            boolean hasRequest = false;
            Iterator<FriendshipProspect> itFriendshipProspect = FriendshipProspect.listFriendshipProspectByFriendShipRequester(owner, wpage.getWebSite());
            if (itFriendshipProspect.hasNext())
            {
                hasRequest = true;
            }
            if (hasRequest)
            {
%>


<%
                itFriendshipProspect = FriendshipProspect.listFriendshipProspectByFriendShipRequester(owner, wpage.getWebSite());
                while (itFriendshipProspect.hasNext())
                {
                    FriendshipProspect friendshipProspect = itFriendshipProspect.next();
                    User userRequested = friendshipProspect.getFriendShipRequested();
                    if (userRequested.getPhoto() != null)
                    {
                        photo = SWBPortal.getWebWorkPath() + userRequested.getPhoto();
                    }
                    urlAction.setParameter("user", userRequested.getURI());
                    if (!isStrategy)
                    {
%>
<div class="moreUser">
    <a href="<%=perfilPath%>?user=<%=userRequested.getEncodedURI()%>"><img src="<%=photo%>" alt="<%=userRequested.getFullName()%>" width="80" height="70">
        <br>
        <%=userRequested.getFullName()%>
    </a>
    <br>
    <%urlAction.setAction("removeRequest");%>
    <br>
    <div class="editarInfo"><p><a href="<%=urlAction%>">Eliminar solicitud</a></p></div>
</div>
<%
                    }
                    contTot++;
                }
                if (isStrategy && contTot > 0)
                {%>
<ul class="listaElementos">
    <li>
        <a class="contactos_nombre" href="<%=requestedPath%><%=userParam%>">Has solicidado a <%=contTot%> personas que se unan como tus amigos</a>
    </li>
</ul>

<%}
                else if (contTot == 0)
                {
%>
<ul class="listaElementos">
    <li>
        No has solicitado personas que se unan a ti como amigos.
    </li>
</ul>

<%            }
%>

<%
            }
            else
            {
%>
<ul class="listaElementos">
    <li>
        No has solicitado personas que se unan a ti como amigos.
    </li>
</ul>  
<%            }

%>

