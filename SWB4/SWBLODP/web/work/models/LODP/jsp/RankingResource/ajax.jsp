<%-- 
    Document   : ajax
    Created on : 24/05/2013, 01:05:13 PM
    Author     : rene.jara
--%><jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" /><%
    String rank=request.getParameter("rank");
    if(rank!=null){
%><ul><li><%=rank.equals("1")?"1":"-"%></li>
<li><%=rank.equals("2")?"2":"-"%></li>
<li><%=rank.equals("3")?"3":"-"%></li>
<li><%=rank.equals("4")?"4":"-"%></li>
<li><%=rank.equals("5")?"5":"-"%></li></ul><%}%>