<%-- 
    Document   : terminosCondiciones
    Created on : 9/05/2013, 05:58:08 PM
    Author     : Lennin
--%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />
<%
SWBResourceURL renderURL = paramRequest.getRenderUrl();
%>
   
        <h1><%=paramRequest.getLocaleString("lbl_appTerminosLicencia")%></h1>    
    
    <a href="<%=renderURL.setMode("ADDCOMMENT")%>">
      Regresar
    </a>
</html>
