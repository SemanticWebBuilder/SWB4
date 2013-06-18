<%-- 
    Document   : panel
    Created on : 18/06/2013, 02:13:59 PM
    Author     : juan.fernandez
--%>

<%@page import="com.infotec.lodp.swb.Publisher"%>
<%@page import="com.infotec.lodp.swb.utils.LODPUtils"%>
<%@page import="org.semanticwb.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />
<%
User usr = paramRequest.getUser();
Publisher pub = LODPUtils.getPublisher(usr);

if(pub!=null){
%>
<a href="/swb/LODP/Panel_de_control">Panel de control</a>
<%
}
%>
