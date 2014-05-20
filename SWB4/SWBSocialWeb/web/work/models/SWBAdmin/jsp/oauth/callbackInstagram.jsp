<%-- 
    Document   : callbackInstagram
    Created on : 14/05/2014, 11:22:30 AM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.portal.resources.SendRedirect"%>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>
<%
        //Se recupera la variable de sesion y el code que regresa Instagram y se redirecciona
        System.out.println("Redirecting................");
        session.getAttribute("redirectInstagram");
        response.sendRedirect(session.getAttribute("redirectInstagram") + "?code=" + request.getParameter("code"));
%> 