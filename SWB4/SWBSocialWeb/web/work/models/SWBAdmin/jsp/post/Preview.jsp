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
       System.out.println("entro al jsp");
    WebPage ws = (WebPage) swb;
    StringBuilder address = new StringBuilder(128);
    address.append("http://").append(request.getServerName()).append(":").append(request.getServerPort()).append(SWBPortal.getWebWorkPath()).append("/").append("models").append("/").append(swb.getWebSite().getId()).append("/").append("WebPage").append("/").append(swb.getId()).append("/").append(SocialWebPage.social_socialwpPhoto.getName() + "_" + swb.getId() + "_" + swb.getSocialwpPhoto());

    SWBResourceURL urlAction = paramRequest.getActionUrl();
    urlAction.setParameter("toPost", "photo");
    WebSite wsite = paramRequest.getWebPage().getWebSite();
    urlAction.setParameter("wsite", wsite.getSemanticObject().getModel().getName());
    String sphoto = swb.getSocialwpPhoto();
    
%>

<body>
    <div id="pub-detalle">
        <span class="sel-imgdiv"></span>
        <div class="swbform">
            <form dojoType="dijit.form.Form" id="frmUploadPhoto" action="<%=urlAction.setAction("uploadPhoto").setParameter("swb", URLEncoder.encode(swb.getURI())).setParameter("suri", suri)%>" method="post" onsubmit="submitForm('frmUploadPhoto'); return false;">
                <table width="100%">
                    <tr>
                        <td colspan="3" style="text-align: left;" class="titulo">Previsualización:</td>        
                    </tr>
                    <tr>
                        <td>
                            <textarea readonly><%=description%></textarea>
                        <td>
                    </tr>

                    <tr>
                        <td>
                            <a href="<%=address%>"><%=address%></a>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <img width="400" height="400" src="<%=SWBPortal.getWebWorkPath()%><%=swb.getWorkPath()%>/<%=SocialWebPage.social_socialwpPhoto.getName()%>_<%=swb.getId()%>_<%=sphoto%>">

                        </td>
                    </tr>

                    <tr>
                        <td colspan="3" style="text-align: left;">
                            <button class="submit" type="" onclick="">Publicar</button>
                        </td>
                    </tr>
                </table>                
            </form>
        </div>
    </div>
    <div id="preview" dojoType="dijit.layout.ContentPane">
    </div>



</body>