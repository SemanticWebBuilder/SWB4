<%-- 
    Document   : streamAdvclassbyTopic
    Created on : 12-sep-2013, 11:10:18
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    if (request.getAttribute("stream") == null) {
        return;
    }

    SemanticObject semObjPost = (SemanticObject) request.getAttribute("stream");

    WebSite wsite = WebSite.ClassMgr.getWebSite(semObjPost.getModel().getName());
    if (wsite == null) {
        return;
    }

    Stream stream = (Stream) semObjPost.getGenericInstance();

    User user = paramRequest.getUser();

    SWBResourceURL actionUrl = paramRequest.getActionUrl();
    actionUrl.setAction("AdvReClassbyTopic");
    actionUrl.setParameter("stream", stream.getURI());
    actionUrl.setParameter("wsite", wsite.getURI());
%>

<div class="swbform swbpopup retema-pop">   
    <p><h1>Stream a Reclasificar: <b><%=stream.getTitle()%></b></h1></p>
    <p class="chooseHowtoClassify"><%=paramRequest.getLocaleString("chooseHowtoClassify")%>: </p>
    <form id="<%=semObjPost.getSemanticClass().getClassId()%>/advClassbTopicForm" dojoType="dijit.form.Form" class="swbform" method="post" action="<%=actionUrl%>" method="post" onsubmit="submitForm('<%=semObjPost.getSemanticClass().getClassId()%>/advClassbTopicForm');return false;"> 
        <p>
            <input type="radio" name="advClassChoose" value="WithOut"> <%=paramRequest.getLocaleString("withOutTopic")%><br>
            <input type="radio" name="advClassChoose" value="All"> <%=paramRequest.getLocaleString("all")%><br>
        </p>
        <button dojoType="dijit.form.Button" type="submit" ><%=paramRequest.getLocaleString("btnSend")%></button>
        <button dojoType="dijit.form.Button" onclick="hideDialog(); return false;"><%=paramRequest.getLocaleString("btnCancel")%></button>
    </form>
</div>
