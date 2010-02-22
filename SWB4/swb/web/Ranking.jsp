<%-- 
    Document   : Ranking
    Created on : 19/02/2010, 01:01:20 AM
    Author     : carlos.ramos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.platform.SemanticObject,org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*,org.semanticwb.pymtur.MicroSitePyme,org.semanticwb.pymtur.ServiceProvider" %>

<%
    SemanticObject sobj = null;
    WebPage community = null;
    WebPage currentpage = (WebPage) request.getAttribute("webpage");

    if (currentpage instanceof MicroSitePyme) {
        community = currentpage;
    }else {
        community = currentpage.getParent();
    }

    if (community != null) {
        MicroSitePyme ms=(MicroSitePyme)community;
        String uri = ms.getURI();
        if (null != uri) {
            sobj = SemanticObject.createSemanticObject(uri);
        }
        if (sobj!=null && sobj.getGenericInstance() instanceof MicroSitePyme) {
        }

        ServiceProvider sprovider=ms.getServiceProvider();
        String webpath=SWBPortal.getWebWorkPath() + "/" + sprovider.getWorkPath() + "/";
    }
%>
