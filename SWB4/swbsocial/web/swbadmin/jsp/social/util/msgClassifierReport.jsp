<%-- 
    Document   : msgClasifierReport
    Created on : 05-jun-2012, 13:47:42
    Author     : jorge.jimenez
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

<%
    response.addHeader("Content-type", "application/vnd.ms-excel");
    response.addHeader("Content-Disposition", "attachment;filename=reporte.xls");
%>

<html>
<head>
<meta http-equiv="Content-Language" content="application/vnd.ms-excel">
<meta http-equiv="Content-Type" content="application/vnd.ms-excel; charset=windows-1252">
</head>

<body>
Sentiment Type=1:Positivo|2:Negativo|0:Neutro
<table border="1">
  <tr>
    <th>Cta. de Red Social</th>
    <th>Message</th>
    <th>Sentiment Type</th>
    <th>SentimentEmoticon Type</th>
    <th>Sentiment Value</th>
    <th>Intencity Value</th>
    <th>F. creación</th>
  </tr>
<%
    int cont=0;
    WebPage wpage=paramRequest.getWebPage();
    Iterator <MessageIn> itMsgIn=SWBComparator.sortByCreated(MessageIn.ClassMgr.listMessageIns(wpage.getWebSite()), false);
    while(itMsgIn.hasNext())
    {
        MessageIn msgIn=itMsgIn.next();
        cont++;
        %>
        <tr>
            <td><%=msgIn.getPostInSocialNetwork()%></td>
            <td><%=msgIn.getMsg_Text()%></td>
            <td><%=msgIn.getPostSentimentalType()%></td>
            <td><%=msgIn.getPostSentimentalEmoticonType()%></td>
            <td><%=msgIn.getPostSentimentalValue()%></td>
            <td><%=msgIn.getPostIntensityValue()%></td>
            <td><%=msgIn.getCreated()%></td>
        </tr>
        <%
    }
%>
<tr><td colspan="7">Mensajes Totales:<%=cont%></td></tr>
</table>
</body>
</html>
