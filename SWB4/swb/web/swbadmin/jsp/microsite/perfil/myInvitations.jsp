<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.portal.community.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%!    private static final int ELEMENETS_BY_PAGE = 5;
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
            String cssPath = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/css/images/";
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

            HashMap<String,User> elements = new HashMap();
            

            Iterator<FriendshipProspect> itFriendshipProspect = FriendshipProspect.ClassMgr.listFriendshipProspectByFriendShipRequested(owner, wpage.getWebSite());
            while (itFriendshipProspect.hasNext())
            {
                FriendshipProspect friendshipProspect = itFriendshipProspect.next();
                User userRequester = friendshipProspect.getFriendShipRequester();
                elements.put(user.getURI(),userRequester);            
            }
            hasInvitations = elements.size() > 0;
            int elementos=elements.size();
            int paginas = elements.size() / ELEMENETS_BY_PAGE;
            if (elements.size() % ELEMENETS_BY_PAGE != 0)
            {
                paginas++;
            }
            int inicio = 0;
            int fin = ELEMENETS_BY_PAGE;
            int ipage = 1;
            if (request.getParameter("ipage") != null)
            {
                try
                {
                    ipage = Integer.parseInt(request.getParameter("ipage"));
                    inicio = (ipage * ELEMENETS_BY_PAGE) - ELEMENETS_BY_PAGE;
                    fin = (ipage * ELEMENETS_BY_PAGE);
                }
                catch (NumberFormatException nfe)
                {
                    ipage = 1;
                }
            }
            if (ipage < 1 || ipage > paginas)
            {
                ipage = 1;
            }
            if (inicio < 0)
            {
                inicio = 0;
            }
            if (fin < 0)
            {
                fin = ELEMENETS_BY_PAGE;
            }
            if (fin > elementos)
            {
                fin = elementos;
            }
            if (inicio > fin)
            {
                inicio = 0;
                fin = ELEMENETS_BY_PAGE;
            }
            if (fin - inicio > ELEMENETS_BY_PAGE)
            {
                inicio = 0;
                fin = ELEMENETS_BY_PAGE;
            }
            inicio++;
            if (hasInvitations)
            {
                if (request.getParameter("user") == null)
                {
%>
<!-- paginacion -->
<%
            if (paginas > 1 && !isStrategy)
            {
%>
<div class="paginacion">


    <%
            String nextURL = "#";
            String previusURL = "#";
            if (ipage < paginas)
            {
                nextURL = paramRequest.getWebPage().getUrl() + "?ipage=" + (ipage + 1);
            }
            if (ipage > 1)
            {
                previusURL = paramRequest.getWebPage().getUrl() + "?ipage=" + (ipage - 1);
            }
            if (ipage > 1)
            {
    %>
    <a href="<%=previusURL%>"><img src="<%=cssPath%>pageArrowLeft.gif" alt="anterior"/></a>
        <%
            }
            for (int i = 1; i <= paginas; i++)
            {
        %>
    <a href="<%=wpage.getUrl()%>?ipage=<%=i%>"><%
                if (i == ipage)
                {
        %>
        <strong>
            <%                            }
            %>
            <%=i%>
            <%
                if (i == ipage)
                {
            %>
        </strong>
        <%                            }
        %></a>
    <%
            }
    %>


    <%
            if (ipage != paginas)
            {
    %>
    <a href="<%=nextURL%>"><img src="<%=cssPath%>pageArrowRight.gif" alt="siguiente"/></a>
        <%
            }
        %>
</div>
<%
            }
%>
<!-- fin paginacion -->
<h2>Mis invitaciones</h2>
<%                }
%>


<%
                if (!isStrategy)
                {
%>
<div id="friendCards">
    <%                }                
                int iElement = 0;
                for (User userRequester : elements.values())
                {
                    iElement++;
                    if (iElement > fin)
                    {
                        break;
                    }
                    if (iElement >= inicio && iElement <= fin)
                    {
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
                            if (usr_age.toString().equals("0") || usr_age.toString().equals(""))
                            {
                                usr_age = "No indicó el usuario";
                            }

    %>

    <div class="friendCard">
        <img class="profilePic" width="121" height="121" src="<%=photo%>" alt="<%=userRequester.getFullName()%>"/>
        <div class="friendCardInfo">
            <%
                        if (userRequester.getEmail() != null)
                        {
            %>
            <a class="ico" href="mailto:<%=email%>"><img src="<%=path%>icoMail.png" alt="enviar un mensaje"/></a>
                <%
                        }
                %>



            <a class="ico" href="<%=perfilurl%>?user=<%=urluser%>"><img src="<%=path%>icoUser.png" alt="ir al perfil"/></a>
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
                    }
                }
                if (!isStrategy)
                {
    %>
</div>
<%                }
                if (isStrategy && elementos > 0)
                {
%>
<ul class="listaElementos">
    <%
                    if (request.getParameter("user") == null)
                    {
    %>
    <li><a class="contactos_nombre" href="<%=requesterPath%><%=userParam%>" >Te han invitado <%=elementos%> persona(s) a que te unas como su amigo</a></li>
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
