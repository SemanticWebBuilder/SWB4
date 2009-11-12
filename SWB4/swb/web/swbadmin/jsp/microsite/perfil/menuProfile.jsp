<%@page import="org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*" %>
<ul id="menuInterna">
    <%
            WebPage webpage = (WebPage) request.getAttribute("webpage");
            String id = webpage.getId();
            String classPerfil = "", classamigos = "", classeventos = "", classfaviritos = "", classtwiter = "";
            if (id.equals("perfil"))
            {
                classPerfil = "class=\"active\"";
            }
            if (id.equals("Amigos"))
            {
                classamigos = "class=\"active\"";
            }
            if (id.equals("Mis_Eventos"))
            {
                classeventos = "class=\"active\"";
            }
            if (id.equals("Mis_favoritos"))
            {
                classfaviritos = "class=\"active\"";
            }
            if (id.equals("Twitter"))
            {
                classtwiter = "class=\"active\"";
            }

            String parameter = request.getParameter("user");
            if (parameter == null)
            {
                parameter = "";
            }
            else
            {
                parameter = "?user=" + java.net.URLEncoder.encode(parameter);
            }
    %>
    <li><a <%=classPerfil%> href="{topic@getUrl}/../perfil<%=parameter%>" >Principal</a></li>
    <li><a <%=classamigos%> href="{topic@getUrl}/../Amigos<%=parameter%>" >Amigos</a></li>
    <li><a <%=classeventos%> href="{topic@getUrl}/../Mis_Eventos<%=parameter%>" >Eventos</a></li>
    <%
        if(request.getParameter("user")==null)
            {
            %>
            <li><a <%=classfaviritos%> href="{topic@getUrl}/../Mis_favoritos<%=parameter%>" >Favoritos</a></li>
            <%
            }
    %>
    
    <li><a <%=classtwiter%> href="{topic@getUrl}/../Twitter<%=parameter%>" >Twitter</a></li>
</ul>