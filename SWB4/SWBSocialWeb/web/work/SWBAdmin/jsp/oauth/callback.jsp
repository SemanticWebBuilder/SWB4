<%-- 
    Document   : callback
    Created on : 11/06/2013, 12:11:45 PM
    Author     : ana.garcias
--%>

<%@page import="org.semanticwb.portal.resources.SendRedirect"%>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>
<%
        //Se recupera la variable de sesion y el code que regresa YouTube y se redirecciona
        session.getAttribute("redirectYouTube");
        response.sendRedirect(session.getAttribute("redirectYouTube") + "?code=" + request.getParameter("code"));
%> 
