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
            String photo = SWBPortal.getContextPath() + "/swbadmin/jsp/microsite/perfil/profilePlaceholder.jpg";
            boolean isStrategy = false;
            if (paramRequest.getCallMethod() == paramRequest.Call_STRATEGY)
            {
                isStrategy = true;
            }
            boolean hasInvitations = false;
            Iterator<FriendshipProspect> itFriendshipProspect = FriendshipProspect.ClassMgr.listFriendshipProspectByFriendShipRequested(owner, wpage.getWebSite());
            if (itFriendshipProspect.hasNext())
            {
                hasInvitations = true;
            }
            if (hasInvitations)
            {
                if(request.getParameter("user")==null)
                    {
                    %>
                    <h2>Mis invitaciones</h2>
                    <%
                    }
%>


<%
                if (!isStrategy)
                {
%>
<div id="friendCards">
    <%                }
                int contTot = 0;
                itFriendshipProspect = FriendshipProspect.ClassMgr.listFriendshipProspectByFriendShipRequested(owner, wpage.getWebSite());
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
                        String path = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSite().getId() + "/css/images/";
                        String urluser = java.net.URLEncoder.encode(userRequester.getURI());
                        String email = userRequester.getEmail();
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
                        String usr_sex = (String) userRequester.getExtendedAttribute(mapa.get("userSex"));
                        Object usr_age = (Object) userRequester.getExtendedAttribute(mapa.get("userAge"));
                        if (usr_sex == null)
                        {
                            usr_sex = "No indicó el usuario su sexo";
                        }
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
                        if (usr_age.equals("0") || usr_age.equals(""))
                        {
                            usr_age = "No indicó el usuario";
                        }

    %>

    <div class="friendCard">
        <img class="profilePic" width="121" height="121" src="<%=photo%>" alt="<%=userRequester.getFullName()%>">
        <div class="friendCardInfo">
            <a class="ico" href="mailto:<%=email%>"><img src="<%=path%>icoMail.png" alt="enviar un mensaje"></a>
            <a class="ico" href="<%=perfilurl%>?user=<%=urluser%>"><img src="<%=path%>icoUser.png" alt="ir al perfil"></a>
                <%-- <a class="ico" href="#"><img src="<%=path%>icoMas.png" alt="agregar"></a> --%>
            <div class="friendCardName">
                <p><%=userRequester.getFullName()%></p>
            </div>
            <p>Sexo:<%=usr_sex%></p>
            <p>Edad:<%=usr_age%></p>
            <%urlAction.setAction("acceptfriend");%>
            <p><a href="<%=urlAction%>">[Aceptar]</a></p>
            <%urlAction.setAction("noacceptfriend");%>
            <p><a href="<%=urlAction%>">[No aceptar]</a></p>
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
<ul class="listaElementos">
    <%
        if(request.getParameter("user")==null)
            {
            %>
                <li><a class="contactos_nombre" href="<%=requesterPath%><%=userParam%>" >Te han invitado <%=contTot%> persona(s) a que te unas como su amigo</a></li>
            <%
            }
    %>
    
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
