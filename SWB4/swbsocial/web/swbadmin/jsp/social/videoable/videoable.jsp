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

SWBFormMgr videoMgr = new SWBFormMgr(Video.sclass.getSemanticObject(), null, SWBFormMgr.MODE_CREATE);
videoMgr.setType(SWBFormMgr.TYPE_DOJO);
videoMgr.setFilterRequired(false);
String lang="";
if (paramRequest.getUser() != null) {
    lang = paramRequest.getUser().getLanguage();
}
videoMgr.setLang(lang);
videoMgr.addButton(SWBFormButton.newSaveButton());
videoMgr.addButton(SWBFormButton.newBackButton());
%>
<div class="formularios">
    <form action="<%=urlAction.setAction("uploadVideo")%>" method="post">
        <%= videoMgr.getFormHiddens() %>
        <div class="etiqueta"><label for="title"><%=Video.swb_title.getDisplayName()%>: </label></div>
        <div class="campo"><%=videoMgr.renderElement(request, Video.swb_title, videoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="description"><%=Video.swb_description.getDisplayName()%>:</label></div>
        <div class="campo"><%=videoMgr.renderElement(request, Video.swb_description, videoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="keywords"><%=Video.swb_Tagable.getDisplayName(lang)%>:</label></div>
        <div class="etiqueta"><%=videoMgr.renderElement(request, Video.swb_tags, videoMgr.MODE_CREATE)%></div>
        <div class="etiqueta"><label for="title"><%=videoMgr.renderLabel(request, Video.social_video, videoMgr.MODE_CREATE)%>: </label></div>
        <div class="etiqueta"><%=videoMgr.renderElement(request, Video.social_video, videoMgr.MODE_CREATE)%></div>
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
    //urlAction.setParameter("entryUrl", "http://gdata.youtube.com/feeds/api/videos/"+videoId);
    String tokenUrl=request.getParameter("tokenUrl");
    String token=request.getParameter("token");
    if(tokenUrl!=null && token!=null)
    {
     %>
        <form action="<%=tokenUrl%>?nexturl=http://localhost:8080<%=urlAction%>" method ="post" enctype="multipart/form-data">
            <ul>
            <input type="file" name="file"/>
            <input type="hidden" name="token" value="<%=token%>"/>
            <input type="submit" value="subir a youtube" />
            </ul>
        </form>
     <%
    }
}
%>
