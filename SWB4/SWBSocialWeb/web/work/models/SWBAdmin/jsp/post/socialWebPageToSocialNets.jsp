<%-- 
    Document   : socialWebPageToSocialNets
    Created on : 06-ago-2013, 17:01:25
    Author     : jorge.jimenez
--%>


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
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<!--%@page import="gui.ava.html.image.generator.*"%-->
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

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


<div class="swbform"  region="center" id="publish">
    <div id="pub-detalle">
        <form name="formSocialNetwork" id="formSocialNetwork">    

            <table width="100%" >
                <tr>
                    <td colspan="3" style="text-align: left;" class="titulo">Publicación de página hacia cuentas de redes sociales:</td>        
                </tr>
                <tr>
                    <td colspan="3" style="text-align: center;">&nbsp;</td>        
                </tr>
                <%
                    while (i.hasNext()) {
                        SocialNetwork s = (SocialNetwork) i.next();
                %>
                <tr>
                    <td style="text-align: left;">
                        <%=s.getTitle()%>
                    <td>
                </tr>
                <%}%>
            </table>
        </form>  
    </div>
</div>
<div id="socialNetwork" dojoType="dijit.layout.ContentPane">
</div>
</fieldset>


<fieldset>
    <div class="swbform">
        <div id="pub-detalle">
            <form name="formLanguage<%=swb.getId()%>" id="formLanguage<%=swb.getId()%>">    
                <table>
                    <tr>
                        <td colspan="3" style="text-align: center;" class="titulo">Seleccione un idioma:</td>        
                    </tr>
                    <tr>
                        <td colspan="3" style="text-align: center;">&nbsp;</td>        
                    </tr>
                    <tr>
                        <td style="text-align: left;">
                            <select name="selectLanguage<%=swb.getId()%>" id="selectLanguage<%=swb.getId()%>" onchange="javascript:postHtml('<%=url.setMode("preview")%>?suri='+escape(document.formLanguage<%=swb.getId()%>.selectLanguage<%=swb.getId()%>[document.formLanguage<%=swb.getId()%>.selectLanguage<%=swb.getId()%>.selectedIndex].value)+'&suriSite=<%=URLEncoder.encode(suri) %>', 'selectlanguage<%=swb.getId()%>');"> 
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

                        </td>
                    </tr>
                </table>
            </form>  
        </div>
    </div>
    <div id="selectlanguage<%=swb.getId()%>" dojoType="dijit.layout.ContentPane">
    </div>
</fieldset>


</div>


