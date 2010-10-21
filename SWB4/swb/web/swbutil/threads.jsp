<%-- 
    Document   : threads
    Created on : 28/06/2010, 08:12:15 PM
    Author     : jei
--%>

<%@page import="org.semanticwb.portal.monitor.SWBThreadDumper"%>
<%@page import="java.lang.management.ManagementFactory"%>
<%@page import="java.lang.management.ThreadMXBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Threads List</title>
    </head>
    <body>
        <%
            ThreadMXBean threads=ManagementFactory.getThreadMXBean();
            long t[]=threads.getAllThreadIds();
            out.println("<h1>Threads:"+t.length+"</h1>");
/*
            for(int x=0;x<t.length;x++)
            {
                out.println("Thread:"+t[x]+" "+(threads.getThreadCpuTime(t[x])/(1000*1000*60)));
                out.println("<br>");
            }
*/
        %>
        <h1>Threads BLOCKED</h1>
        <pre><%=SWBThreadDumper.dumpBLOCKEDThreadWithStackTrace()%></pre>

        <h1>All Threads</h1>
        <pre><%=SWBThreadDumper.dumpThreadWithStackTrace()%></pre>
    </body>
</html>
