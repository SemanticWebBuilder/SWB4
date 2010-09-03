<%@page import="org.semanticwb.platform.SemanticModel"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.platform.SWBObjectFilter"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.model.HerarquicalNode"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="java.util.Map.Entry"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Clear DB</h1>
<%
    Iterator<Entry<String, SemanticModel>> it=SWBUtils.Collections.copyIterator(SWBPlatform.getSemanticMgr().getModels().iterator()).iterator();
    while (it.hasNext())
    {
        Entry elem = it.next();
        SWBPlatform.getSemanticMgr().removeModel((String)elem.getKey());
    }
%>
    </body>
</html>
