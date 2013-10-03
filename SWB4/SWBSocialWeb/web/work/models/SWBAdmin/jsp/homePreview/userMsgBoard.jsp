<%-- 
    Document   : userMsgBoad
    Created on : 02-oct-2013, 13:42:38
    Author     : jorge.jimenez
--%>
<%@page import="org.semanticwb.social.util.SocialLoader"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.social.SocialFlow.SocialPFlowMgr"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>


<%
    String action=paramRequest.getAction();
    User user=paramRequest.getUser();
    WebSite wsite=paramRequest.getWebPage().getWebSite();    
%>

<p>
    <div class="swbform" style="width: 500px">
        <a href="<%=paramRequest.getRenderUrl().setAction("toMe")%>">Para mi</a>
        <a href="<%=paramRequest.getRenderUrl().setAction("fromMe")%>">Mios</a>
        <a href="<%=paramRequest.getRenderUrl().setAction("sendNew")%>">Enviar nuevo 1</a>   
    </div>     
</p>

<%
    if(action.equals("editMsg"))
    {
        SemanticObject senObj=SemanticObject.getSemanticObject(request.getParameter("msgUri"));
        UserMessage userMsg=(UserMessage)senObj.getGenericInstance();  
        SWBFormMgr messageFormMgr = new SWBFormMgr(senObj, null, SWBFormMgr.MODE_EDIT);
        messageFormMgr.setType(SWBFormMgr.TYPE_DOJO);
        messageFormMgr.setFilterRequired(false);
        String lang = "";
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        messageFormMgr.setLang(lang);
        messageFormMgr.addButton(SWBFormButton.newSaveButton());
        messageFormMgr.addButton(SWBFormButton.newBackButton());
    %>
        <form dojoType="dijit.form.Form" id="<%=user.getURI()%>frmsendMsg" action="<%=paramRequest.getActionUrl().setAction("editUserMsg")%>" method="post" onsubmit="submitForm('<%=user.getURI()%>frmsendMsg'); return false;">
            <%=messageFormMgr.getFormHiddens()%>
            <input type="hidden" name="fromUser" value="<%=user.getURI()%>"/>
            <input type="hidden" name="msgUri" value="<%=userMsg.getURI()%>"/>
            <div class="etiqueta"><label for="usrMsg"><%=UserMessage.social_usrMsg.getLabel(lang)%></label></div>
            <div class="campo"><%=messageFormMgr.renderElement(request, UserMessage.social_usrMsg, messageFormMgr.MODE_EDIT)%></div>    
            
            <div class="etiqueta"><label for="userPriority"><%=UserMessage.social_userPriority.getLabel(lang)%>:</label></div> 
            <%
                String checked="";
                if(userMsg.isUserPriority()) checked="Checked";
            %>
            <div class="campo"><input type="checkbox" name="userPriority" <%=checked%>/></div>    
            
            <div class="etiqueta"><label for="hasUsers">Para Usuarios:</label></div>  
            <div class="campo">
                <select name="hasUsers" multiple size="5">
                <%
                    Iterator<User> itUsers=SWBContext.getAdminRepository().listUsers();
                    while(itUsers.hasNext())
                    {
                        User userOption=itUsers.next(); 
                        if(userOption.isActive())
                        {
                            String selected="";
                            if(userMsg.hasUsers(userOption)) selected="selected";
                        %>
                            <option value="<%=userOption.getURI()%>" <%=selected%>><%=userOption.getFullName()%></option>
                        <%
                        }
                    }
                %>
                </select>
            </div>    
            
            <button class="submit" type="submit" ><%=SWBSocialUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
        </form>    
    <%
    }else if(action.equals("viewMsg"))
    {
        if(request.getParameter("msgUri")!=null)
        {
            SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("msgUri"));
            UserMessage userMsg=(UserMessage)semObj.getGenericInstance(); 
            %>
                <p>Mensaje de:<%=userMsg.getFromUser().getFullName()%></p>
                <p>Mensaje:<%=userMsg.getUsrMsg()%></p>
                <p>Fecha:<%=userMsg.getCreated()%></p>
                <p><a href="<%=paramRequest.getRenderUrl().setAction("toMe")%>">Regresar</a></p>
            <%
        }
    }else if(action.equals("fromMe"))
    {
        %>
         <ul>
         <%
         Iterator<UserMessage> itUserMsg=UserMessage.ClassMgr.listUserMessageByFromUser(user, wsite);
         while(itUserMsg.hasNext())
         {
             UserMessage userMsg=itUserMsg.next();
             %>
             <li>
                 <a href="<%=paramRequest.getActionUrl().setAction("remMsg").setParameter("msgUri", userMsg.getURI())%>">Elim</a><a href="<%=paramRequest.getRenderUrl().setAction("editMsg").setParameter("msgUri", userMsg.getURI())%>">Edit</a><%=userMsg.getUsrMsg()%>  
             </li>
             <%
         }
         %>
         </ul>
         <%
    }else if(action.equals("sendNew"))
    {
        SWBFormMgr messageFormMgr = new SWBFormMgr(UserMessage.sclass.getSemanticObject(), null, SWBFormMgr.MODE_CREATE);
        messageFormMgr.setType(SWBFormMgr.TYPE_DOJO);
        messageFormMgr.setFilterRequired(false);
        String lang = "";
        if (paramRequest.getUser() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        messageFormMgr.setLang(lang);
        messageFormMgr.addButton(SWBFormButton.newSaveButton());
        messageFormMgr.addButton(SWBFormButton.newBackButton());
    %>
        <form dojoType="dijit.form.Form" id="<%=user.getURI()%>frmsendMsg" action="<%=paramRequest.getActionUrl().setAction("newUserMsg")%>" method="post" onsubmit="submitForm('<%=user.getURI()%>frmsendMsg'); return false;">
            <%=messageFormMgr.getFormHiddens()%>
            <input type="hidden" name="fromUser" value="<%=user.getURI()%>"/>
            <div class="etiqueta"><label for="usrMsg"><%=UserMessage.social_usrMsg.getLabel(lang)%></label></div>
            <div class="campo"><%=messageFormMgr.renderElement(request, UserMessage.social_usrMsg, messageFormMgr.MODE_CREATE)%></div>    
            
            <div class="etiqueta"><label for="userPriority"><%=UserMessage.social_userPriority.getLabel(lang)%>:</label></div> 
            <div class="campo"><input type="checkbox" name="userPriority"/></div>   
            
            <div class="etiqueta"><label for="hasUsers">Para Usuarios:</label></div>  
            <div class="campo">
                <select name="hasUsers" multiple size="5">
                <%
                    Iterator<User> itUsers=SWBContext.getAdminRepository().listUsers();
                    while(itUsers.hasNext())
                    {
                        User userOption=itUsers.next(); 
                        if(userOption.isActive())
                        {
                        %>
                            <option value="<%=userOption.getURI()%>"><%=userOption.getFullName()%></option>
                        <%
                        }
                    }
                %>
                </select>
            </div>    
            
            <button class="submit" type="submit" ><%=SWBSocialUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
        </form>    
    <%
    }else {  //Opción por defecto
         %>
         <ul>
         <%
         Iterator<UserMessage> itUserMsg=UserMessage.ClassMgr.listUserMessageByUsers(user, wsite);
         while(itUserMsg.hasNext())
         {
             UserMessage userMsg=itUserMsg.next();
             %>
             <li>
                 <a href="<%=paramRequest.getActionUrl().setAction("remMsg").setParameter("msgUri", userMsg.getURI())%>">Elim</a><a href="<%=paramRequest.getRenderUrl().setAction("viewMsg").setParameter("msgUri", userMsg.getURI())%>">View</a><%=userMsg.getUsrMsg()%>  
             </li>
             <%
         }
         %>
         </ul>
         <%
    }
%>
