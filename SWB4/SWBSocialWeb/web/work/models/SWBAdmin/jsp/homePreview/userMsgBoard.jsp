<%-- 
    Document   : userMsgBoad
    Created on : 02-oct-2013, 13:42:38
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="org.semanticwb.social.util.SocialLoader"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.social.SocialFlow.SocialPFlowMgr"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>


<%
    String action = paramRequest.getAction();
    User user = paramRequest.getUser();
    WebSite wsite = paramRequest.getWebPage().getWebSite();
%>

<p>
<div id="msj-tabs">
    <a class="recibidos-sel" title="Mensajes recibidos" href="<%=paramRequest.getRenderUrl().setAction("toMe")%>"></a>
    <a class="enviados" title="Mensajes enviados" href="<%=paramRequest.getRenderUrl().setAction("fromMe")%>"></a>
    <a class="nuevo" title="Nuevos" href="<%=paramRequest.getRenderUrl().setAction("sendNew")%>"></a>   
</div>     
</p>

<%
    if (action.equals("editMsg")) {
        SemanticObject senObj = SemanticObject.getSemanticObject(request.getParameter("msgUri"));
        UserMessage userMsg = (UserMessage) senObj.getGenericInstance();
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
<div id="msj-panel">     
    <form  class="msjnvo" dojoType="dijit.form.Form" id="<%=user.getURI()%>frmsendMsg" action="<%=paramRequest.getActionUrl().setAction("editUserMsg")%>" method="post" onsubmit="submitForm('<%=user.getURI()%>frmsendMsg'); return false;">
        <div class="msjnvo-msj">
            <%=messageFormMgr.getFormHiddens()%>
            <input type="hidden" name="fromUser" value="<%=user.getURI()%>"/>
            <input type="hidden" name="msgUri" value="<%=userMsg.getURI()%>"/>
            <label for="usrMsg"><%=UserMessage.social_usrMsg.getLabel(lang)%></label>
            <%=messageFormMgr.renderElement(request, UserMessage.social_usrMsg, messageFormMgr.MODE_EDIT)%> 
        </div>

        <div class="msjnvo-prio">
            <%
                String checked = "";
                if (userMsg.isUserPriority()) {
                    checked = "Checked";
                }
            %>
            <input id="userPriority" type="checkbox" name="userPriority" <%=checked%>/>  
            <label for="userPriority"><%=UserMessage.social_userPriority.getLabel(lang)%></label>

        </div> 
        <div class="msjnvo-usrs">
            <label for="hasUsers">Selecciona los destinatarios:</label>


            <select name="hasUsers" multiple size="5">
                <%
                    Iterator<User> itUsers = SWBContext.getAdminRepository().listUsers();
                    while (itUsers.hasNext()) {
                        User userOption = itUsers.next();
                        if (userOption.isActive()) {
                            String selected = "";
                            if (userMsg.hasUsers(userOption)) {
                                selected = "selected";
                            }
                %>
                <option value="<%=userOption.getURI()%>" <%=selected%>><%=userOption.getFullName()%></option>
                <%
                        }
                    }
                %>
            </select>
        </div>    

        <button class="submit" type="submit" ><%=SWBSocialResUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
    </form>  
</div>
<%
} else if (action.equals("viewMsg")) {
    if (request.getParameter("msgUri") != null) {
        SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("msgUri"));
        UserMessage userMsg = (UserMessage) semObj.getGenericInstance();
%>
<p>Mensaje de:<%=userMsg.getFromUser().getFullName()%></p>
<p>Mensaje:<%=userMsg.getUsrMsg()%></p>
<p>Fecha:<%=userMsg.getCreated()%></p>
<p><a href="<%=paramRequest.getRenderUrl().setAction("toMe")%>">Regresar</a></p>
<%
    }
} else if (action.equals("fromMe")) {
%>
<div id="msj-panel">          
    <%
        Iterator<UserMessage> itUserMsg = UserMessage.ClassMgr.listUserMessageByFromUser(user, wsite);
        while (itUserMsg.hasNext()) {
            UserMessage userMsg = itUserMsg.next();
            User u = userMsg.getModifiedBy();
            Date toDateD = userMsg.getCreated();
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat hours = new SimpleDateFormat("hh:mm");
            String fecha = formatoDelTexto.format(toDateD);
            String hoursS = hours.format(toDateD);
           
    %>            
    <p class="msj-fecha">
        <strong>    
            <%=fecha%>
        </strong>
    </p>
    <div class="msj-cont">
        <a class="msj-eliminar" href="<%=paramRequest.getActionUrl().setAction("remMsg").setParameter("msgUri", userMsg.getURI())%>"><span>Eliminar</span></a>
        <a class="msj-editar" href="<%=paramRequest.getRenderUrl().setAction("editMsg").setParameter("msgUri", userMsg.getURI())%>"><span>Editar</span></a>
        <p><em><%=hoursS%> - <%=u.getName()%></em><p>
        <p><%=userMsg.getUsrMsg()%></p>
    </div>

    <%
        }
    %>    

</div>
<%
} else if (action.equals("sendNew")) {
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
<div id="msj-panel">
    <form class="msjnvo" dojoType="dijit.form.Form" id="<%=user.getURI()%>frmsendMsg" action="<%=paramRequest.getActionUrl().setAction("newUserMsg")%>" method="post" onsubmit="submitForm('<%=user.getURI()%>frmsendMsg'); return false;">
        <%=messageFormMgr.getFormHiddens()%>
        <input type="hidden" name="fromUser" value="<%=user.getURI()%>"/>
        <div class="msjnvo-msj">
            <label for="usrMsg"><%=UserMessage.social_usrMsg.getLabel(lang)%></label>
            <%=messageFormMgr.renderElement(request, UserMessage.social_usrMsg, messageFormMgr.MODE_CREATE)%>   
        </div>
        <div class="msjnvo-prio">
            <input id="userPriority" type="checkbox" name="userPriority"/>
            <label for="userPriority"><%=UserMessage.social_userPriority.getLabel(lang)%></label> 
        </div>       

        <div class="msjnvo-usrs">
            <label for="hasUsers">Selecciona los destinatarios:</label>      
            <select name="hasUsers" multiple size="5">
                <%
                    Iterator<User> itUsers = SWBContext.getAdminRepository().listUsers();
                    while (itUsers.hasNext()) {
                        User userOption = itUsers.next();
                        if (userOption.isActive()) {
                %>
                <option value="<%=userOption.getURI()%>"><%=userOption.getFullName()%></option>
                <%
                        }
                    }
                %>
            </select>
        </div>    
        <button class="submit" type="submit" ><%=SWBSocialResUtil.Util.getStringFromGenericLocale("send", user.getLanguage())%></button>
    </form>    
</div>
<%
} else {  //Opción por defecto
%>
<div id="msj-panel">

    <%
        Iterator<UserMessage> itUserMsg = UserMessage.ClassMgr.listUserMessageByUsers(user, wsite);
        while (itUserMsg.hasNext()) {
            UserMessage userMsg = itUserMsg.next();
            User u = userMsg.getModifiedBy();
            Date toDateD = userMsg.getCreated();
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat hours = new SimpleDateFormat("hh:mm");
            String fecha = formatoDelTexto.format(toDateD);
            String hoursS = hours.format(toDateD);
    %>
    <p class="msj-fecha">
        <strong><%=fecha%></strong>
    </p>
    <div class="msj-cont">
        <a class="msj-eliminar" href="<%=paramRequest.getActionUrl().setAction("remMsg").setParameter("msgUri", userMsg.getURI())%>"><span>Eliminar</span></a>
        <!--<a class="" href="<%//=paramRequest.getRenderUrl().setAction("viewMsg").setParameter("msgUri", userMsg.getURI())%>"><span>Ver</span></a>-->
        <p><em><%=hoursS%>  -  <%=u.getName()%></em></p>
        <p><%=userMsg.getUsrMsg()%></p>  
    </div>
    <%
        }
    %>


</div>
<%
    }
%>
