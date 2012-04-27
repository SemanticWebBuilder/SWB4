<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.social.Videoable"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%
String action=paramRequest.getAction();
SWBResourceURL url=paramRequest.getRenderUrl();
SWBResourceURL urlAction=paramRequest.getActionUrl();
String socialUri=request.getParameter("socialUri");

String lang = "";
if (paramRequest.getUser() != null) {
    lang = paramRequest.getUser().getLanguage();
}
SWBFormMgr videoMgr = new SWBFormMgr(Video.sclass, paramRequest.getWebPage().getWebSite().getSemanticObject(), null);
videoMgr.setType(SWBFormMgr.TYPE_DOJO);
videoMgr.setFilterRequired(false);
videoMgr.setLang(lang);
videoMgr.addButton(SWBFormButton.newSaveButton());
videoMgr.addButton(SWBFormButton.newBackButton());
SemanticObject videoSemObj = new SemanticObject(paramRequest.getWebPage().getWebSite().getSemanticModel(), Video.sclass);
%>
<div class="formularios">
    <form action="<%=urlAction.setAction("uploadVideo")%>" method="post" id="frmUploadVideo" name="frmUploadVideo" dojoType="dijit.form.Form">
        <%= videoMgr.getFormHiddens() %>
        <div class="etiqueta"><label for="title"><%=Video.swb_title.getDisplayName()%>: </label></div>
        <div class="campo"><%=videoMgr.renderElement(request, Video.swb_title, videoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="description"><%=Video.swb_description.getDisplayName()%>:</label></div>
        <div class="campo"><%=videoMgr.renderElement(request, Video.swb_description, videoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="keywords"><%=Video.swb_Tagable.getDisplayName(lang)%>:</label></div>
        <div class="campo"><%=videoMgr.renderElement(request, Video.swb_tags, videoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="title"><%=videoMgr.renderLabel(request, Video.social_video, videoMgr.MODE_CREATE)%>: </label></div>
        <div class="campo"><%=videoMgr.getFormElement(Video.social_video).renderElement(request, videoSemObj, Video.social_video, SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang)%></div>
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
if(action.equals("uploadVideo"))
{
%>
    <div>Video enviado</div>
<%
}
%>
