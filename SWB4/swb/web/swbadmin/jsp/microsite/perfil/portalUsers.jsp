        <jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
        <%@page import="org.semanticwb.model.User"%>
        <%@page import="org.semanticwb.portal.community.*"%>
        <%@page import="org.semanticwb.model.WebPage"%>
        <%@page import="java.util.*"%>
        <%@page import="org.semanticwb.platform.SemanticObject"%>
        <%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
        <%@page import="org.semanticwb.SWBPlatform"%>

        <div class="miembros">
          <h2 class="titulo">Usuarios del portal</h2>
        <%
        SWBResourceURL urlAction=paramRequest.getActionUrl();
        String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";

        Iterator<User> itUsers=paramRequest.getWebPage().getWebSite().getUserRepository().listUsers();
        while(itUsers.hasNext())
        {
            User userprosp=itUsers.next();
            urlAction.setAction("createProspect");
            urlAction.setParameter("user", userprosp.getURI());
            if(userprosp.getPhoto()!=null) photo=userprosp.getPhoto();
            %>
                <div class="moreUser">
                    <a href="<%=urlAction%>"><img src="<%=photo%>" valign="top" width="80" height="70"/><br><%=userprosp.getFullName()%></a>
                </div>
            <%
        }                
     %>
     </div>