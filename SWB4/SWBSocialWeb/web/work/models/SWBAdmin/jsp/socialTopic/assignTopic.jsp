<%-- 
    Document   : assignTopic
    Created on : 11/06/2013, 12:07:34 PM
    Author     : francisco.jimenez
--%>

<%@page import="java.util.regex.Pattern"%>
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
    String objUri = (String)request.getAttribute("suri");//Suri SocialNetwork
    SocialNetwork socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
    WebSite wsite=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());   
    SWBModel model=WebSite.ClassMgr.getWebSite(wsite.getId());
    User user=paramRequest.getUser();
    SWBResourceURL actionUrl = paramRequest.getActionUrl();
    
    actionUrl.setAction("setSocialTopic");
    actionUrl.setParameter("suri", objUri);
    actionUrl.setParameter("id", request.getParameter("id"));
    actionUrl.setParameter("currentTab", request.getParameter("currentTab"));
    /*String currentTopic = "--";
    PostIn postIn = PostIn.getPostInbySocialMsgId(model, request.getParameter("id"));
    if(postIn != null && postIn.getSocialTopic()!= null){
        currentTopic = postIn.getSocialTopic().getDisplayTitle(user.getLanguage());
    }*/
    Pattern pattern = Pattern.compile("^(\\d*\\_\\d*)$");
    String id = request.getParameter("id");
    boolean isIdFromFB = pattern.matcher(id).matches();    
    PostOut postOut = PostOut.getPostOutbySocialMsgId(model, request.getParameter("id"));
    if(postOut == null && isIdFromFB){//for Facebook sometimes returns the single id of post 'idUser_idPost'
                                      //Some other times only returns the 'idPost'    
        postOut = PostOut.getPostOutbySocialMsgId(model, id.substring(id.indexOf("_") + 1));
    }
    %>
    <div class="swbform">
<%if(postOut != null && postOut.getSocialTopic() != null){%>
<fieldset>
    <table width="98%" >
        <thead>
        <th>
            Enviado desde SWBSocial.
        </th>
        </thead>
        <tbody>
        <tr>
            <td align="center">
                Desde el tema: <%= postOut.getSocialTopic().getTitle()%>
            </td>        
        </tr>
        </tbody>
      </table>
</fieldset>
<%}%>
    <fieldset>
      <form id="<%="id"%>/assignTopicForm" dojoType="dijit.form.Form" class="swbform" method="post" action="<%=actionUrl%>" method="post" onsubmit="submitForm('<%="id"%>/assignTopicForm'); return false;"> 
      <table width="98%" >
        <thead>
        <th>
            <%=paramRequest.getLocaleString("chooseTopic")%>
        </th>
        </thead>
        <tbody>
        
        <tr>    
            <td align="center">
                <select name="newSocialTopic">
                    <!--<option value="none"><%//=paramRequest.getLocaleString("none")%>Ninguno</option>-->
                   <%
                        Iterator<SocialTopic> itSocialTopics=SocialTopic.ClassMgr.listSocialTopics(wsite);
                        while(itSocialTopics.hasNext())
                        {
                           SocialTopic siteSocialTopic=itSocialTopics.next();    
                            if(siteSocialTopic.isActive() && !siteSocialTopic.isDeleted()){
                               %>
                                    <option value="<%=siteSocialTopic.getURI()%>"><%=siteSocialTopic.getDisplayTitle(user.getLanguage())%></option>
                               <%
                            }
                        }
                   %> 
                </select>
            </td> 
        </tr>
        <tr>
            <td align="center">
                <button dojoType="dijit.form.Button" type="submit" ><%=paramRequest.getLocaleString("btnSend")%></button>
                <button dojoType="dijit.form.Button" onclick="hideDialog(); return false;"><%=paramRequest.getLocaleString("btnCancel")%></button>
            </td>
        </tr>
        </tbody>
      </table>
     </form>
    </fieldset>
  </div>