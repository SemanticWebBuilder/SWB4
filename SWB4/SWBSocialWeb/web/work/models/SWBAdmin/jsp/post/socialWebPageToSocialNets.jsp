<%-- 
    Document   : socialWebPageToSocialNets
    Created on : 06-ago-2013, 17:01:25
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.portal.social.facebook.util.FacebookUtils"%>
<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
<%@page import="org.semanticwb.social.SocialWebPage"%>
<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.model.base.WebPageBase"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.SocialTopic"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.util.SocialLoader"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.json.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.social.SocialFlow.SocialPFlowMgr"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>


<%
    SWBResourceURL url = paramRequest.getRenderUrl();
    String suri = request.getParameter("suri");
    if (suri == null) {
        return;
    }
    SemanticObject semObj = SemanticObject.createSemanticObject(suri);
    if (semObj == null) {
        return;
    }
    SocialWebPage swb = (SocialWebPage) SemanticObject.createSemanticObject(suri).createGenericInstance();
    Iterator i = swb.listSocialNetses();
%>


<div id="pagSocial-publicar">
    <div class="idioma">
        <p>Publicar esta página en:<p>
        <ul>
            <%
                while (i.hasNext()) {
                    SocialNetwork s = (SocialNetwork) i.next();
                    if (s instanceof Facebook) {
            %>
            <li><img class="swbIconFacebook" src="/swbadmin/css/images/trans.png"/><%=s.getTitle()%></li>
                <%} else if (s instanceof Twitter) {
                %>
            <li><img class="swbIconTwitter" src="/swbadmin/css/images/trans.png"/><%=s.getTitle()%></li>
                <%} else if (s instanceof Youtube) {
                %>
            <li><img class="swbIconYouTube" src="/swbadmin/css/images/trans.png"/><%=s.getTitle()%></li>
                <%}%>                       
            <%}%>
        </ul>
        <form name="formLanguage<%=swb.getId()%>" id="formLanguage<%=swb.getId()%>">    
            <label>Idioma</label>
            <select name="selectLanguage<%=swb.getId()%>" id="selectLanguage<%=swb.getId()%>" onchange="javascript:postHtml('<%=url.setMode("preview")%>?suri='+escape(document.formLanguage<%=swb.getId()%>.selectLanguage<%=swb.getId()%>[document.formLanguage<%=swb.getId()%>.selectLanguage<%=swb.getId()%>.selectedIndex].value)+'&suriSite=<%=URLEncoder.encode(suri)%>', 'selectlanguage<%=swb.getId()%>');"> 
                <option value=""><---Seleccione:----></option>
                <%
                    Iterator language = swb.getWebSite().listLanguages();

                    String lenguaje = swb.getLanguage() == null ? "es" : swb.getLanguage().getId();
                    while (language.hasNext()) {
                        Language lang = (Language) language.next();
                        String titleLanguage = lang.getTitle(lenguaje);

                %>
                <option value="<%=lang.getURI()%>"><%=titleLanguage%></option>
                <%
                    }
                %>
            </select>
        </form>  
            <div>
    </div>
    </div>
    <div id="selectlanguage<%=swb.getId()%>" dojoType="dijit.layout.ContentPane">
    </div>
</div>


