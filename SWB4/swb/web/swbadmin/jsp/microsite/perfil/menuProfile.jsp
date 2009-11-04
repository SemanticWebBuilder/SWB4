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
    %>
    <li><a <%=classPerfil%> href="{topic@getUrl}/../perfil" >Principal</a></li>
    <li><a <%=classamigos%> href="{topic@getUrl}/../Amigos" >Mis amigos</a></li>
    <li><a <%=classeventos%> href="{topic@getUrl}/../Mis_Eventos" >Mis eventos</a></li>
    <li><a <%=classfaviritos%> href="{topic@getUrl}/../Mis_favoritos" >Mis favoritos</a></li>
    <li><a <%=classtwiter%> href="{topic@getUrl}/../Twitter" >Twitter</a></li>
</ul>