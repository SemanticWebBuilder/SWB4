<%-- 
    Document   : messageable
    Created on : 24/04/2012, 02:06:10 PM
    Author     : jose.jimenez
--%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.social.Messageable"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
String action = paramRequest.getAction();
SWBResourceURL url = paramRequest.getRenderUrl();
SWBResourceURL urlAction = paramRequest.getActionUrl();
String socialUri = request.getParameter("socialUri");

SWBFormMgr messageFormMgr = new SWBFormMgr(Message.sclass.getSemanticObject(), null, SWBFormMgr.MODE_CREATE);
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
<div class="formularios">
    <form action="<%=urlAction.setAction("postMessage")%>" method="post">
        <%= messageFormMgr.getFormHiddens() %>
        <div class="etiqueta"><label for="title"><%=Message.social_Message.getDisplayName(lang)%>: </label></div>
        <div class="campo"><%=messageFormMgr.renderElement(request, Message.social_msg_Text, messageFormMgr.MODE_CREATE)%></div>
        <input type="hidden" name="socialUri" value="<%=socialUri%>"/>
        <input type="hidden" name="toPost" value="<%=request.getParameter("toPost")%>">
        <ul class="btns_final">
            <li>
                <label class="etiqueta"></label>
                <input type="submit" class="btn_form"  value="Guardar">
                <input type="button" class="btn_form"  value="Regresar" onclick="javascript:history.go(-1);">
            </li>
        </ul>
    </form>
</div>
<%
    if(action.equals("postMessage")) {
%>    <div>Mensaje enviado</div>
<%
    }
%>
