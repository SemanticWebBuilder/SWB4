<%-- 
    Document   : TwitterAuth
    Created on : 26-abr-2012, 18:56:06
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
    Iterator it = Twitter.ClassMgr.listTwitters(wp.getWebSite());
    SWBResourceURL url = paramRequest.getRenderUrl().setMode("firstStepAuth");
    while(it.hasNext()){
        Twitter twitter = (Twitter)it.next();
        String oaTokn = twitter.getAccessToken();
        String oaTokS = twitter.getAccessTokenSecret();
        String title = twitter.getTitle(user.getLanguage()) == null ? (twitter.getTitle() == null ? "" : twitter.getTitle()) : twitter.getTitle(user.getLanguage());
        %><p>Cuenta: <%=title%><%
        if(oaTokS != null && oaTokn != null) {
%>             <a href="<%=url.setParameter("uri", twitter.getEncodedURI())%>">Renovar credenciales de autenticación</a>
<%      } else {
%>             <a href="<%=url.setParameter("uri", twitter.getEncodedURI())%>">Obtener credenciales de autenticación</a>
<%      }%></p><%
    }
%>
