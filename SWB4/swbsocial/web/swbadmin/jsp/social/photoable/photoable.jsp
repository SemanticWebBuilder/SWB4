<%-- 
    Document   : photoable
    Created on : 24-abr-2012, 12:49:17
    Author     : martha.jimenez
--%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.social.Photoable"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%
SWBResourceURL urlAction=paramRequest.getActionUrl();
String socialUri=request.getParameter("socialUri");

SWBFormMgr photoMgr = new SWBFormMgr(Photo.sclass,paramRequest.getWebPage().getWebSite().getSemanticObject() , null);
photoMgr.setType(SWBFormMgr.TYPE_DOJO);
photoMgr.setFilterRequired(false);
String lang="es";
if (paramRequest.getUser() != null) {
    lang = paramRequest.getUser().getLanguage();
}
photoMgr.setLang(lang);
photoMgr.addButton(SWBFormButton.newSaveButton());
photoMgr.addButton(SWBFormButton.newBackButton());
//StringBuffer ret = new StringBuffer();
SemanticObject obj2 = new SemanticObject(paramRequest.getWebPage().getWebSite().getSemanticModel(), Photo.sclass);
//photoMgr.renderProp(request, ret, Photo.social_photo, photoMgr.getFormElement(Photo.social_photo), photoMgr.MODE_CREATE);
%>
<hr/>
<div class="formularios">
    <form action="<%=urlAction.setAction("uploadPhoto")%>" method="post" id="frmUploadPhoto" name="frmUploadPhoto" dojoType="dijit.form.Form">
        <%= photoMgr.getFormHiddens() %>
        <div class="etiqueta"><label for="title"><%=Photo.swb_title.getDisplayName()%>: </label></div>
        <div class="campo"><%=photoMgr.renderElement(request, Photo.swb_title, photoMgr.MODE_CREATE)%></div>

        <div class="etiqueta"><label for="description"><%=Photo.swb_description.getDisplayName()%>:</label></div>
        <div class="campo"><%=photoMgr.renderElement(request, Photo.swb_description, photoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="keywords"><%=Photo.swb_Tagable.getDisplayName(lang)%>:</label></div>
        <div class="etiqueta"><%=photoMgr.renderElement(request, Photo.swb_tags, photoMgr.MODE_CREATE)%></div>
        
        <div class="etiqueta"><label for="photo"><%=photoMgr.renderLabel(request, Photo.social_photo, photoMgr.MODE_CREATE)%>: </label></div>
        <%=photoMgr.getFormElement(Photo.social_photo).renderElement(request, obj2, Photo.social_photo, SWBFormMgr.TYPE_DOJO, SWBFormMgr.MODE_CREATE, lang)%>
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