<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
    String valor = (String) request.getAttribute("valor");
    String action = request.getParameter("action");
    SWBResourceURL urlAction = paramRequest.getActionUrl();
    String objUri = request.getParameter("objUri");
    String marca = (String) request.getParameter("wsite");
    WebSite wsite = WebSite.ClassMgr.getWebSite(marca);
    if (valor.equals("postMessage")) {
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
        <%= messageFormMgr.getFormHiddens()%>
        <ul><b>Seleccione las redes sociales a las cuales desea enviar el post</b></ul>
        <%
            Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
            while (it.hasNext()) {
                SocialNetwork socialNetwork = (SocialNetwork) it.next();
                if (socialNetwork instanceof Messageable) {
        %>
        <li>
            <input type="checkbox" name="<%=socialNetwork.getURI()%>"/><%=socialNetwork.getTitle()%>
        </li>
        <%
                }
            }%>
        <div class="etiqueta"><label for="title"><%=Message.social_Message.getDisplayName(lang)%>: </label></div>
        <div class="campo"><%=messageFormMgr.renderElement(request, Message.social_msg_Text, messageFormMgr.MODE_CREATE)%></div>
        <input type="hidden" name="toPost" value="<%="msg"%>">
        <input type="hidden" name="objUri" value="<%=objUri%>">
        <input type="hidden" name="wsite" value="<%=marca%>">
        <input type="hidden" name="action" value="<%=action%>">
        <ul class="btns_final">
            <li>
                <label class="etiqueta"></label>
                <input type="submit" class="btn_form"  value="Enviar">
            </li>
        </ul>
    </form>
</div> 
<%} else if (valor.equals("uploadPhoto")) {
    SWBFormMgr photoMgr = new SWBFormMgr(Photo.sclass, paramRequest.getWebPage().getWebSite().getSemanticObject(), null);
    photoMgr.setType(SWBFormMgr.TYPE_DOJO);
    photoMgr.setFilterRequired(false);
    String lang = "es";
    if (paramRequest.getUser() != null) {
        lang = paramRequest.getUser().getLanguage();
    }
    photoMgr.setLang(lang);
    photoMgr.addButton(SWBFormButton.newSaveButton());
    photoMgr.addButton(SWBFormButton.newBackButton());
//StringBuffer ret = new StringBuffer();
    SemanticObject obj2 = new SemanticObject(paramRequest.getWebPage().getWebSite().getSemanticModel(), Photo.sclass);
%>
<div class="formularios">
    <form action="<%=urlAction.setAction("uploadPhoto")%>" method="post" id="frmUploadPhoto" name="frmUploadPhoto" dojoType="dijit.form.Form">
        <%= photoMgr.getFormHiddens()%>
        <ul><b>Seleccione las redes sociales a las cuales desea enviar el post</b></ul>
        <%
            Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
            while (it.hasNext()) {
                SocialNetwork socialNetwork = (SocialNetwork) it.next();
                if (socialNetwork instanceof Photoable) {
        %>
        <li>
            <input type="checkbox" name="<%=socialNetwork.getURI()%>"/><%=socialNetwork.getTitle()%>
        </li>
        <%
                }
            }%>
        <div class="etiqueta"><label for="title"><%=Photo.swb_title.getDisplayName()%>: </label></div>
        <div class="campo"><%=photoMgr.renderElement(request, Photo.swb_title, photoMgr.MODE_CREATE)%></div>

        <div class="etiqueta"><label for="description"><%=Photo.swb_description.getDisplayName()%>:</label></div>
        <div class="campo"><%=photoMgr.renderElement(request, Photo.swb_description, photoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="keywords"><%=Photo.swb_Tagable.getDisplayName(lang)%>:</label></div>
        <div class="etiqueta"><%=photoMgr.renderElement(request, Photo.swb_tags, photoMgr.MODE_CREATE)%></div>

        <div class="etiqueta"><label for="photo"><%=photoMgr.renderLabel(request, Photo.social_photo, photoMgr.MODE_CREATE)%>: </label></div>
        <%=photoMgr.getFormElement(Photo.social_photo).renderElement(request, obj2, Photo.social_photo, SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang)%>
        <input type="hidden" name="toPost" value="photo">
        <input type="hidden" name="objUri" value="<%=objUri%>">
        <input type="hidden" name="wsite" value="<%=marca%>">
        <input type="hidden" name="action" value="<%=action%>">
        <ul class="btns_final">
            <li>
                <label class="etiqueta"></label>
                <input type="submit" class="btn_form"  value="Enviar">
                <input type="button" class="btn_form"  value="Regresar" onclick="javascript:history.go(-1);">
            </li>
        </ul>
    </form>
</div>

<%} else if (valor.equals("uploadVideo")) {
    SWBFormMgr videoMgr = new SWBFormMgr(Video.sclass, paramRequest.getWebPage().getWebSite().getSemanticObject(), null);
    videoMgr.setType(SWBFormMgr.TYPE_DOJO);
    videoMgr.setFilterRequired(false);
    String lang = "";
    if (paramRequest.getUser() != null) {
        lang = paramRequest.getUser().getLanguage();
    }
    videoMgr.setLang(lang);
    videoMgr.addButton(SWBFormButton.newSaveButton());
    videoMgr.addButton(SWBFormButton.newBackButton());
    SemanticObject videoSemObj = new SemanticObject(paramRequest.getWebPage().getWebSite().getSemanticModel(), Video.sclass);
%>
<div class="formularios">
    <form action="<%=urlAction.setAction("uploadVideo")%>" method="post" id="frmUploadVideo" name="frmUploadVideo" dojoType="dijit.form.Form">
        <%= videoMgr.getFormHiddens()%>
        <ul><b>Seleccione las redes sociales a las cuales desea enviar el post</b></ul>
        <%
            Iterator<SocialNetwork> it = SocialNetwork.ClassMgr.listSocialNetworks(wsite);
            while (it.hasNext()) {
                SocialNetwork socialNetwork = (SocialNetwork) it.next();
                if (socialNetwork instanceof Videoable) {
        %>
        <li>
            <input type="checkbox" name="<%=socialNetwork.getURI()%>"/><%=socialNetwork.getTitle()%>
        </li>
        <%
                }
            }%>
        <div class="etiqueta"><label for="title"><%=Video.swb_title.getDisplayName()%>: </label></div>
        <div class="campo"><%=videoMgr.renderElement(request, Video.swb_title, videoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="description"><%=Video.swb_description.getDisplayName()%>:</label></div>
        <div class="campo"><%=videoMgr.renderElement(request, Video.swb_description, videoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="keywords"><%=Video.swb_Tagable.getDisplayName(lang)%>:</label></div>
        <div class="campo"><%=videoMgr.renderElement(request, Video.swb_tags, videoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="title"><%=videoMgr.renderLabel(request, Video.social_video, videoMgr.MODE_CREATE)%>: </label></div>
        <div class="campo"><%=videoMgr.getFormElement(Video.social_video).renderElement(request, videoSemObj, Video.social_video, SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang)%></div>
        <input type="hidden" name="toPost" value="<%="video"%>">
        <input type="hidden" name="objUri" value="<%=objUri%>">
        <input type="hidden" name="wsite" value="<%=marca%>">
        <input type="hidden" name="action" value="<%=action%>">
        <ul class="btns_final">
            <li>
                <label class="etiqueta"></label>
                <input type="submit" class="btn_form"  value="Enviar">
            </li>
        </ul>
    </form>
</div>
<%    }
%>