<%-- 
    Document   : reValue
    Created on : 28-may-2013, 18:24:48
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
    System.out.println("ReValue.jsp-1:"+request.getAttribute("postUri"));
    if(request.getAttribute("postUri")==null) return;
    
    SemanticObject semObjPost=(SemanticObject)request.getAttribute("postUri");
    
    WebSite wsite=WebSite.ClassMgr.getWebSite(semObjPost.getModel().getName());
    if(wsite==null) return; 
    
    PostIn postIn=(PostIn)semObjPost.getGenericInstance();
    System.out.println("ReValue.jsp-2:"+postIn);
       
    //SocialTopic socialTopic=postIn.getSocialTopic();
    
    //User user=paramRequest.getUser(); 
    
    SWBResourceURL actionUrl = paramRequest.getActionUrl();
    actionUrl.setAction("reValue");
    actionUrl.setParameter("postUri", postIn.getURI());
    System.out.println("ReValue.jsp-3");
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
            {%>
                ---
            <%}else if(postIn.getPostSentimentalType()==1)
            {%>
                <img src="<%=SWBPortal.getContextPath()%>/swbadmin/images/feelpos.png">
            <%         
            }else if(postIn.getPostSentimentalType()==2)
            {
            %>   
                <img src="<%=SWBPortal.getContextPath()%>/swbadmin/images/feelneg.png">
            <%    
            }else 
            {
            %>
                XXX
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
      <form id="<%=semObjPost.getSemanticClass().getClassId()%>/classbReValue" dojoType="dijit.form.Form" class="swbform" method="post" action="<%=actionUrl%>" method="post" onsubmit="submitForm('<%=semObjPost.getSemanticClass().getClassId()%>/classbReValue');return false;"> 
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
            <!--input type="text" name="fw" id="fw" dojoType="dijit.form.ValidationTextBox" value="" required="true" promptMessage="Lista de frases separadas por punto y coma" invalidMessage="Palabra incorrecta" trim="true" /-->
            <textarea name="fw" id="fw" rows="2" cols="28" ></textarea>
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
               <select name="dpth" id="dpth" value="0" required="true">
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