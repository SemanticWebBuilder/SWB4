<%@page import="org.semanticwb.portal.SWBMonitor"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.util.Vector"%>
<%@page import="org.semanticwb.portal.monitor.SWBSummary"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML><%!
static private SWBSummary swbSummary = new SWBSummary();
%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SWB Monitor Info</title>
    </head>
    <body>
        <ul>
            <li>CPU Time:<%= String.format("%1$3.6f",swbSummary.getSample().instantCPU) %></li>
            <li>Instance Name:<%= swbSummary.getSample().vmInstanceName %></li>
            <li>Commited:<%= String.format("%,d",swbSummary.getSample().currentCommited) %></li>
            <li>Heap:<%= String.format("%,d",swbSummary.getSample().currentHeap) %></li>
            <li>GC:<% for (String item:swbSummary.getSample().gcDetails) { out.print("<br>"+item);} %></li>
            <li>Threads:<%= swbSummary.getSample().startedTh %></li>
            <li>Demonios:<%= swbSummary.getSample().deamonTh %></li>
        </ul>
        
        <table border="1">
            <thead>
                <tr>
                    <th align="center">Date</th>
                    <th align="center">Used Memory</th>
                    <th align="center">Max Memory</th>
                    <th align="center">Hits</th>
                    <th align="center">Hits Time</th>
                    <th align="center">Hits/Second</th>
                    <th align="center">Users</th>
                    <th align="center">C.ResHits</th>
                    <th align="center">C.ResLoadHits</th>
                </tr>
            </thead>
            <tbody>
        
<%
        Vector<SWBMonitor.MonitorRecord> data = SWBPortal.getMonitor().getMonitorRecords();
        Enumeration<SWBMonitor.MonitorRecord> en = data.elements();
        
        long old=0;
        while (en.hasMoreElements()) 
        {
            SWBMonitor.MonitorRecord mr = en.nextElement();
            
            String date = "" + mr.getDate().getHours() + ":" + mr.getDate().getMinutes() + ":" + mr.getDate().getSeconds();
            
            if(old==0)old=mr.getHits();
%>
                <tr>
                    <td align="right"><%=date%></td>
                    <td align="right"><%=String.format("%,d", mr.getUsedMemory())%></td>
                    <td align="right"><%=String.format("%,d", mr.getMaxMemory())%></td>
                    <td align="right"><%=String.format("%,d", mr.getHits())%></td>
                    <td align="right"><%=String.format("%,d", mr.getHitsTime())%></td>
                    <td align="right"><%=String.format("%,d", (mr.getHits()-old)/SWBPortal.getMonitor().getDelay())%></td>
                    <td align="right"><%=String.format("%,d", mr.getMaxUsers())%></td>
                    <td align="right"><%=String.format("%,d", mr.getCacheResHits())%></td>
                    <td align="right"><%=String.format("%,d", mr.getCacheResLoadHits())%></td>
                </tr>
<%
            old=mr.getHits();
        }
%>        
            </tbody>
        </table>
        
    </body>
</html>
