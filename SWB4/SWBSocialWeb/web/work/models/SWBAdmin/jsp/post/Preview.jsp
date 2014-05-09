<%-- 
    Document   : Preview
    Created on : 14/08/2013, 12:22:59 PM
    Author     : gabriela.rosales
--%>

<%@page import="org.semanticwb.social.Photo"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="java.lang.StringBuilder"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.model.Language"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.semanticwb.social.SocialWebPage"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.social.SocialTopic"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    String suri = request.getParameter("suri");
    String suriSite = request.getParameter("suriSite");
    SocialWebPage swb = (SocialWebPage) SemanticObject.createSemanticObject(suriSite).createGenericInstance();
    Language l = (Language) SemanticObject.createSemanticObject(suri).createGenericInstance();
    String description = swb.getDescription(l.getId()) == null ? "" : swb.getDescription(l.getId());
    StringBuilder address = new StringBuilder(128);
    address.append("http://").append(request.getServerName()).append(":").append(request.getServerPort()).append(SWBPortal.getWebWorkPath()).append("/").append("models").append("/").append(swb.getWebSite().getId()).append("/").append("WebPage").append("/").append(swb.getId()).append("/").append(swb.getSocialwpPhoto());

    SWBResourceURL urlAction = paramRequest.getActionUrl();
    urlAction.setParameter("toPost", "photo");
    WebSite wsite = paramRequest.getWebPage().getWebSite();
    urlAction.setParameter("wsite", wsite.getSemanticObject().getModel().getName());
    String sphoto = swb.getSocialwpPhoto();

%>

<div class="preview">
    <div>
        <p>Contenido a publicar: </p>
        <br><br>
        <%=description%>
        <br><br>
        <img width="300" height="300" src="<%=SWBPortal.getWebWorkPath()%><%=swb.getWorkPath()%>/<%=sphoto%>">
        <br><br>
        <a href="<%=address%>"><%=address%></a>
        <br><br><br><br>
        <center><form dojoType="dijit.form.Form" id="frmUploadPhoto" action="<%=urlAction.setAction("uploadPhoto").setParameter("swb", URLEncoder.encode(swb.getURI())).setParameter("suri", suri)%>" method="post" onsubmit="submitForm('frmUploadPhoto'); return false;">
                <button class="submit" type="" onclick="">Publicar</button>
            </form>
        </center>
    </div>
</div>