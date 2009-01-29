<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.portal.admin.resources.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%
    String uri=request.getParameter("suri");
    SemanticObject obj=SemanticObject.createSemanticObject(uri);
    if(obj!=null)
    {
        User user=SWBPortal.getSessionUser();
        out.println("<div class=\"applet\">");
        SWBAEditor.getTemplateApplet(new java.io.PrintWriter(out), obj.getModel().getName(), obj.getId(), 1, user);
        out.println("</div>");
        //System.out.println("Template");
        //java.io.PrintWriter p=new java.io.PrintWriter(System.out,true);
        //SWBAEditor.getTemplateApplet(p, obj.getModel().getName(), obj.getId(), 1, user);
    }
%>