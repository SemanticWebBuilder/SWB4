<%-- 
    Document   : adminUserHistory
    Created on : 11-sep-2013, 18:29:14
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>


<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");

    if (request.getAttribute("swbAdminUser") == null) {
        return;
    }

    SemanticObject semObj = (SemanticObject) request.getAttribute("swbAdminUser");
    if (semObj == null) {
        return;
    }

    User userCreator = (User) semObj.getGenericInstance(); 
    //Un mensaje de entrada siempre debe estar atachado a un usuario de la red social de la que proviene, de esta manera, es como desde swbsocial
    //se respondería a un mensaje
    if (userCreator == null) {
        return;
    }

    String userPhoto = userCreator.getPhoto();
    if (userPhoto == null) {
        userPhoto = "/swbadmin/css/images/profileDefImg.jpg";
    } else {
        userPhoto = SWBPortal.getWebWorkPath() + userCreator.getWorkPath() + "/" + userPhoto;
    }

%>

<div class="swbform swbpopup usr-pop">
    <div class="perfilgral">
        <div class="perfil">
            <img style="width:150px" src="<%=userPhoto%>"/>        
            <p align="center">
                <%=SWBUtils.TEXT.encode(userCreator.getFullName(), "utf-8")%>
            </p>
        </div>
        <div class="clear"></div>
    </div>
</div>
