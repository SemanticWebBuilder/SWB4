<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%
            if (request.getParameter("user") == null)
            {
%>
<h2>Mis solicitudes</h2>
<%            }
%>

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

            String photo = SWBPortal.getContextPath() + "/swbadmin/jsp/microsite/perfil/profilePlaceholder.jpg";
            boolean isStrategy = false;
            if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY)
            {
                isStrategy = true;
            }
            int contTot = 0;
            boolean hasRequest = false;
            Iterator<FriendshipProspect> itFriendshipProspect = FriendshipProspect.ClassMgr.listFriendshipProspectByFriendShipRequester(owner, wpage.getWebSite());
            if (itFriendshipProspect.hasNext())
            {
                hasRequest = true;
            }
            if (hasRequest)
            {
%>


<%
                itFriendshipProspect = FriendshipProspect.ClassMgr.listFriendshipProspectByFriendShipRequester(owner, wpage.getWebSite());
                if (!isStrategy)
                {
%>
<div id="friendCards">
    <%                }
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
                        String path = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSite().getId() + "/css/images/";
                        String urluser = java.net.URLEncoder.encode(userRequested.getURI());
                        String email = userRequested.getEmail();
                        HashMap<String, SemanticProperty> mapa = new HashMap<String, SemanticProperty>();
                        Iterator<SemanticProperty> list = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#_ExtendedAttributes").listProperties();
                        while (list.hasNext())
                        {
                            SemanticProperty prop = list.next();
                            mapa.put(prop.getName(), prop);
                        }
                        String perfilurl = paramRequest.getWebPage().getWebSite().getWebPage("perfil").getUrl();
                        if (request.getParameter("user") != null)
                        {
                            perfilurl += "?user=" + java.net.URLEncoder.encode(request.getParameter("user"));
                        }
                        String usr_sex = (String) userRequested.getExtendedAttribute(mapa.get("userSex"));
                        Object usr_age = (Object) userRequested.getExtendedAttribute(mapa.get("userAge"));
                        if (null == usr_age)
                        {
                            usr_age = "";
                        }
                        if ("M".equals(usr_sex))
                        {
                            usr_sex = "Hombre";
                        }
                        if ("F".equals(usr_sex))
                        {
                            usr_sex = "Mujer";
                        }
    %>

    <div class="friendCard">
        <img class="profilePic" width="121" height="121" src="<%=photo%>" alt="<%=userRequested.getFullName()%>">
        <div class="friendCardInfo">
            <a class="ico" href="mailto:<%=email%>"><img src="<%=path%>icoMail.png" alt="enviar un mensaje"></a>
            <a class="ico" href="<%=perfilurl%>?user=<%=urluser%>"><img src="<%=path%>icoUser.png" alt="ir al perfil"></a>
                <%-- <a class="ico" href="#"><img src="<%=path%>icoMas.png" alt="agregar"></a> --%>
            <div class="friendCardName">
                <p><%=userRequested.getFullName()%></p>
            </div>
            <p>Sexo:<%=usr_sex%></p>
            <p>Edad:<%=usr_age%></p>
            <%urlAction.setAction("removeRequest");%>
            <p><a href="<%=urlAction%>">[Eliminar solicitud]</a></p>
        </div>
    </div>

    <%
                    }
                    contTot++;
                }
                if (!isStrategy)
                {
    %>
</div>
<%                }
                if (isStrategy && contTot > 0)
                {
%>

<%
    if (request.getParameter("user") == null)
    {
%>
<ul class="listaElementos">
    <li>
        <a class="contactos_nombre" href="<%=requestedPath%><%=userParam%>">Has solicidado a <%=contTot%> personas que se unan como tus amigos</a>
    </li>
</ul>
<%
    }
%>        


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

