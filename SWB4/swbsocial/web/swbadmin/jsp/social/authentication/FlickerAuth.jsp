<%-- 
    Document   : autFlickr
    Created on : 23-abr-2012, 14:00:24
    Author     : martha.jimenez
--%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="java.util.*"%>
<%
    WebPage wp = paramRequest.getWebPage();
    User user = paramRequest.getUser();
    Iterator it = Flicker.ClassMgr.listFlickers(wp.getWebSite());
    SWBResourceURL url = paramRequest.getRenderUrl().setMode("firstStepAuth");
    while(it.hasNext()){
        
        Flicker flicker = (Flicker)it.next();
        String oaTokn = flicker.getAccessToken();//flicker.getProperty("oauth_token");
        String oaTokS = flicker.getAccessTokenSecret();//flicker.getProperty("oauth_token_secret");
        String title = flicker.getTitle(user.getLanguage()) == null ? (flicker.getTitle() == null ? "" : flicker.getTitle()) : flicker.getTitle(user.getLanguage());
        %><p>Cuenta: <%=title%><%
        if(oaTokS != null && oaTokn != null) {
%>             <a href="<%=url.setParameter("uri", flicker.getEncodedURI())%>">Renovar credenciales de autenticación</a>
<%      } else {
%>             <a href="<%=url.setParameter("uri", flicker.getEncodedURI())%>">Obtener credenciales de autenticación</a>
<%      }%></p><%
    }
%>