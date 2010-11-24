<%@page import="org.semanticwb.portal.community.EventElement"%><%@page import="org.semanticwb.platform.SemanticClass"%><%@page import="org.semanticwb.rest.RestPublish"%><%@page import="java.util.*"%><%@page import="org.w3c.dom.Document"%><%
        
        HashSet<SemanticClass> classes=new HashSet<SemanticClass>();
        classes.add(EventElement.sclass);
        RestPublish rp=new RestPublish(classes);
        rp.service(request, response);
%>
