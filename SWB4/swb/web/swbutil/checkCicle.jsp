<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
<%
    WebSite site=SWBContext.getWebSite("sectur");
    Iterator<WebPage> it=site.listWebPages();
    while (it.hasNext()) {
        WebPage elem = it.next();
        WebPage parent=elem.getParent();
        while(parent!=null)
        {
            if(elem==parent || parent.isChildof(elem))
            {
                out.print("Cicle:"+elem.getId()+" --> "+parent.getId());
                out.print("<br/>");
            }
            parent=parent.getParent();
         }
    }
%>
    </body>
</html>
