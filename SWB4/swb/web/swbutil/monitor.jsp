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
    </body>
</html>
