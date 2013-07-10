<%-- 
    Document   : postViewFiles
    Created on : 05-jul-2013, 17:23:22
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    if(request.getAttribute("imageList")!=null && request.getAttribute("semObj")!=null)
    {
        SemanticObject semObj=(SemanticObject)request.getAttribute("semObj");
        
        Iterator<File> imageList=(Iterator)request.getAttribute("imageList");
        
        while(imageList.hasNext())
        {
            File file=imageList.next();
            System.out.println("file:"+SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file.getName());
            %>
                 <img src="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file.getName()%>"/>
            <%
        }
        
        
    }

%>