<%-- 
    Document   : reValue
    Created on : 28-may-2013, 19:57:15
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    if (request.getAttribute("postUri") == null) {
        return;
    }

    SemanticObject semObjPost = (SemanticObject) request.getAttribute("postUri");

    WebSite wsite = WebSite.ClassMgr.getWebSite(semObjPost.getModel().getName());
    if (wsite == null) {
        return;
    }

    PostIn postIn = (PostIn) semObjPost.getGenericInstance();

    //SocialTopic socialTopic=postIn.getSocialTopic();

    //User user=paramRequest.getUser(); 

    SWBResourceURL actionUrl = paramRequest.getActionUrl();
    actionUrl.setAction("reValue");
    actionUrl.setParameter("postUri", postIn.getURI());
%>
<div class="swbform swbpopup reevaluar-pop">

    <table class= "tabla1">
        <thead>
            <tr>
                <th>
                    <span><%=paramRequest.getLocaleString("post")%></span>
                </th>
                <th>
                    <span><%=paramRequest.getLocaleString("sentiment")%></span>
                </th>
                <th>
                    <span><%=paramRequest.getLocaleString("intensity")%></span>
                </th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <%=postIn.getMsg_Text()%> 
                </td>

                <!--Sentiment-->
                <td>
                    <%
                        if (postIn.getPostSentimentalType() == 0) {
                    %>
                    ---
                    <%        } else if (postIn.getPostSentimentalType() == 1) {
                    %>    
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/pos.png" width="25" height="25" title="Sentimiento positivo">
                    <%
                    } else if (postIn.getPostSentimentalType() == 2) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/neg.png" width="25" height="25" title="Sentimiento negativo">
                    <%
                        }
                    %>
                </td>

                <!--Intensity-->
                <td>
                    <!--<%=postIn.getPostIntesityType() == 0 ? paramRequest.getLocaleString("low") : postIn.getPostSentimentalType() == 1 ? paramRequest.getLocaleString("medium") : postIn.getPostSentimentalType() == 2 ? paramRequest.getLocaleString("high") : "---"%>-->

                    <%
                        if (postIn.getPostIntesityType() == 0) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/ibaja.png" width="25" height="25" alt="<%=paramRequest.getLocaleString("low")%>">
                    <%        } else if (postIn.getPostIntesityType() == 1) {
                    %>    
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/imedia.png" width="25" height="25" alt="<%=paramRequest.getLocaleString("medium")%>">
                    <%
                    } else if (postIn.getPostIntesityType() == 2) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/ialta.png" width="25" height="25" alt="<%=paramRequest.getLocaleString("high")%>">
                    <%
                    } else {
                    %>
                    ----
                    <%}%>
                </td> 

            </tr>
        </tbody>
    </table>

    <p class="tit"><%=paramRequest.getLocaleString("classifyMsg")%></p>


    <form id="<%=semObjPost.getSemanticClass().getClassId()%>/classbTopicForm" dojoType="dijit.form.Form" class="swbform" method="post" action="<%=actionUrl%>" method="post" onsubmit="submitForm('<%=semObjPost.getSemanticClass().getClassId()%>/classbTopicForm');return false;"> 
        <p>
            <label>        
                <em>
                    *
                </em>
                <%=paramRequest.getLocaleString("phrases")%>
            </label>    
            <textarea name="fw" id="fw" rows="2" cols="28" trim="true" required="true"></textarea>
        </p>      

        <p>
            <label for="nv">
                <em>*</em>
                <%=paramRequest.getLocaleString("sentimentType")%>
            </label>


            <select name="nv" id="nv" value="0">
                <option value="0" selected="selected"><%=paramRequest.getLocaleString("neutral")%></option>
                <option value="1"><%=paramRequest.getLocaleString("possitive")%></option>
                <option value="2"><%=paramRequest.getLocaleString("negative")%></option>
            </select>
        </p>


        <p>
            <label for="dpth">
                <em>*</em>
                <%=paramRequest.getLocaleString("intensity")%>
            </label>

            <select name="dpth" id="dpth" value="0">
                <option value="0" selected="selected"><%=paramRequest.getLocaleString("low")%></option>
                <option value="1"><%=paramRequest.getLocaleString("medium")%></option>
                <option value="2"><%=paramRequest.getLocaleString("high")%></option>
            </select>
        </p>
        <p>
            <button dojoType="dijit.form.Button" type="submit"><%=paramRequest.getLocaleString("btnSend")%></button>
            <button dojoType="dijit.form.Button" onclick="hideDialog(); return false;"><%=paramRequest.getLocaleString("btnCancel")%></button>
        </p>
    </form>

</div>


