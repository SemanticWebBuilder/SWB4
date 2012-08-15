<%-- 
    Document   : testDNS
    Created on : 27-jul-2012, 21:08:16
    Author     : javier.solis.g
--%>

<%@page import="org.semanticwb.model.Dns"%>
<%@page import="sun.security.x509.DNSName"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
<%
    Dns dns=Dns.getDns("www.medicasurlomas.com.mx");
    out.println(dns.getWebPage());
%>        
    </body>
</html>
