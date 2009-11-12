<%@page import="org.semanticwb.process.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.portal.*"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    WebPage topic=paramRequest.getWebPage();
    String url=paramRequest.getRenderUrl().setParameter("act", "cpi").toString();
    org.semanticwb.process.Process process=SWBProcessMgr.getProcess(topic);
%>
        <a href="<%=url%>">Crear Instancia de Proceso</a>
<%
        String act=request.getParameter("act");
        if(act!=null)
        {
            if(act.equals("cpi"))
            {
                SWBProcessMgr.createProcessInstance((ProcessSite)topic.getWebSite(), process, user);
            }
        }
%>