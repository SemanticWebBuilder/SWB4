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
    if(request.getAttribute("postUri")==null) return;
    
    SemanticObject semObjPost=(SemanticObject)request.getAttribute("postUri");
    
    WebSite wsite=WebSite.ClassMgr.getWebSite(semObjPost.getModel().getName());
    if(wsite==null) return; 
    
    PostIn postIn=(PostIn)semObjPost.getGenericInstance();
    
    //SocialTopic socialTopic=postIn.getSocialTopic();
    
    //User user=paramRequest.getUser(); 
    
    SWBResourceURL actionUrl = paramRequest.getActionUrl();
    actionUrl.setAction("reValue");
    actionUrl.setParameter("postUri", postIn.getURI());
    %>
    <div class="swbform">
    <fieldset>
      <table width="98%" >
        <thead>
        <th>
            <%=paramRequest.getLocaleString("post")%>
        </th>
        <th>
            <%=paramRequest.getLocaleString("sentiment")%>
        </th>
        <th>
            <%=paramRequest.getLocaleString("intensity")%>
        </th>
        </thead>
        <tbody>
        
        <tr>
        <td align="center">
           <%=postIn.getMsg_Text()%> 
        </td>
       
        <!--Sentiment-->
        <td align="center">
        <%    
        if(postIn.getPostSentimentalType()==0)
        {
        %>
            ---
        <%    
        }else if(postIn.getPostSentimentalType()==1)
        {
        %>    
            <img src="<%=SWBPortal.getContextPath()%>/swbadmin/images/feelpos.png">
        <%    
        }else if(postIn.getPostSentimentalType()==2)
        {
        %>
            <img src="<%=SWBPortal.getContextPath()%>/swbadmin/images/feelneg.png">
        <%    
        }
        %>
        </td>

        <!--Intensity-->
        <td>
        <%=postIn.getPostIntesityType()==0?paramRequest.getLocaleString("low"):postIn.getPostSentimentalType()==1?paramRequest.getLocaleString("medium"):postIn.getPostSentimentalType()==2?paramRequest.getLocaleString("high"):"---"%>
        </td> 
        
        </tr>
      </tbody>
      </table>
    </fieldset>
        
    <fieldset>
      <form id="<%=semObjPost.getSemanticClass().getClassId()%>/classbTopicForm" dojoType="dijit.form.Form" class="swbform" method="post" action="<%=actionUrl%>" method="post" onsubmit="submitForm('<%=semObjPost.getSemanticClass().getClassId()%>/classbTopicForm');return false;"> 
      <table width="98%" >
        <thead>
        <th>
            <%=paramRequest.getLocaleString("classifyMsg")%>
        </th>
        </thead>
        <tbody>
        
        <tr>
        <td>
            <label for="fw"><em>*</em><%=paramRequest.getLocaleString("phrases")%></label>
        </td>
        <td colspan="2">
            <textarea name="fw" id="fw" rows="2" cols="28" trim="true"></textarea>
        </td>
        </tr>
        <tr>
        <td>
               <label for="nv"><em>*</em><%=paramRequest.getLocaleString("sentimentType")%></label>
        </td>
        <td colspan="2">
               <select name="nv" id="nv" value="0">
                   <option value="0" selected="selected"><%=paramRequest.getLocaleString("neutral")%></option>
                   <option value="1"><%=paramRequest.getLocaleString("possitive")%></option>
                   <option value="2"><%=paramRequest.getLocaleString("negative")%></option>
               </select>
        </td>
        </tr>
        <tr>
        <td>
               <label for="dpth"><em>*</em><%=paramRequest.getLocaleString("intensity")%></label>
        </td>
        <td colspan="2">
               <select name="dpth" id="dpth" value="0">
                   <option value="0" selected="selected"><%=paramRequest.getLocaleString("low")%></option>
                   <option value="1"><%=paramRequest.getLocaleString("medium")%></option>
                   <option value="2"><%=paramRequest.getLocaleString("high")%></option>
               </select>
        </td>
        </tr>    
        
        <tr>
            <td>
                <button dojoType="dijit.form.Button" type="submit" ><%=paramRequest.getLocaleString("btnSend")%></button>
                <button dojoType="dijit.form.Button" onclick="hideDialog(); return false;"><%=paramRequest.getLocaleString("btnCancel")%></button>
            </td>
        </tr>
        </tbody>
      </table>
     </form>
    </fieldset>
  </div>
 

