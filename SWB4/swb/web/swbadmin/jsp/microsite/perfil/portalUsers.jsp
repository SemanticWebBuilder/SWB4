        <jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
        <%@page import="org.semanticwb.model.User"%>
        <%@page import="org.semanticwb.portal.community.*"%>
        <%@page import="org.semanticwb.model.WebPage"%>
        <%@page import="java.util.*"%>
        <%@page import="org.semanticwb.platform.SemanticObject"%>
        <%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
        <%@page import="org.semanticwb.SWBPlatform"%>
        <%@page import="org.semanticwb.model.*"%>

        <div class="miembros">
          <h2 class="titulo">Usuarios del portal</h2>
          <table>
        <%
        Resource base=paramRequest.getResourceBase();
        String perfilPath=base.getAttribute("perfilPath","");

        SWBResourceURL urlAction=paramRequest.getActionUrl();
        String photo=SWBPlatform.getContextPath()+"/swbadmin/images/defaultPhoto.jpg";

        boolean flag=false;
        int cont=0;

        Iterator<User> itUsers=paramRequest.getWebPage().getWebSite().getUserRepository().listUsers();
        while(itUsers.hasNext())
        {
            User userprosp=itUsers.next();
            urlAction.setAction("createProspect");
            urlAction.setParameter("user", userprosp.getURI());
            if(userprosp.getPhoto()!=null) photo=userprosp.getPhoto();

            if(!flag && cont==0) {%><tr><%}
            cont++;
            %>
                <td>
                    <div class="moreUser">
                        <p class="titulo"><a href="<%=perfilPath%>?user=<%=userprosp.getEncodedURI()%>"><img src="<%=SWBPlatform.getWebWorkPath()+photo%>" valign="top" width="80" height="70"/><br><%=userprosp.getFullName()%></a></p>
                    </div>
               </td>
            <%
            if(cont==4) {
                %></tr><%
                cont=0;
                flag=false;
            }
         }
        if(flag) {%></tr><%}
     %>
     </table>
     </div>