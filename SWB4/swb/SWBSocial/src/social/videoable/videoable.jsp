<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.social.Videoable"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

<%

WebSite wsite=paramRequest.getWebPage().getWebSite();
String action=paramRequest.getAction();
SWBResourceURL url=paramRequest.getRenderUrl();
SWBResourceURL urlAction=paramRequest.getActionUrl();
String socialUri=request.getParameter("socialUri");
%>

<form action="<%=urlAction.setAction("uploadVideo")%>" method="post">
    <ul>
        <li>Título:<input type="text" name="title"></li>
        <li>Descripción:<input type="text" name="description"></li>
        <li>Palabras clave:<input type="text" name="keywords"></li>
    </ul>
<input type="hidden" name="socialUri" value="<%=socialUri%>"/>
<input type="hidden" name="toPost" value="<%=request.getParameter("toPost")%>">
<li><input type="submit" value="enviar"></li>
</form>


<%
    if(action.equals("uploadVideo"))
    {
    //urlAction.setParameter("entryUrl", "http://gdata.youtube.com/feeds/api/videos/"+videoId);
    String tokenUrl=request.getParameter("tokenUrl");
    String token=request.getParameter("token");
    if(tokenUrl!=null && token!=null)
    {
     %>
        <form action="<%=tokenUrl%>?nexturl=http://localhost:8080<%=urlAction%>" method ="post" enctype="multipart/form-data">
            <ul>
            <input type="file" name="file"/>
            <input type="hidden" name="token" value="<%=token%>"/>
            <input type="submit" value="subir a youtube" />
            </ul>
        </form>
     <%
    }
}
%>
