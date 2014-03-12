<%-- 
    Document   : facebookgc
    Created on : 12-mar-2014, 12:33:59
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Language"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.semanticwb.social.FacebookGC"%> 
<%@page import="org.semanticwb.social.SocialAdmin"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    SocialAdmin socialAdm=(SocialAdmin)SWBContext.getAdminWebSite();
    FacebookGC facebookGC=socialAdm.getAdm_facebookgc(); 
    String appKey=null, secretKey=null;
    if(facebookGC!=null)
    {
        appKey=facebookGC.getAppKey();
        secretKey=facebookGC.getSecretKey();
    }
    SWBResourceURL acc_url = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_EDIT);        
%>

<br/><br/><br/><br/>
    <form id="form_<%=socialAdm.getId()%>_facebookGC" dojoType="dijit.form.Form" class="swbform" action="<%=acc_url%>" method="post" onsubmit="submitForm('form_<%=socialAdm.getId()%>_facebookGC'); return false;"> 
    <%if(facebookGC!=null){%>    
        <input type="hidden" name="facebookGCid" value="<%=facebookGC.getId()%>">    
    <%}%>
    <div id="socialNetCF">
        <label for="appKey"><span>&nbsp;</span>Llave de aplicaci&oacute;n:</label>
        <input type="textbox" name="appKey" value="<%=appKey!=null?appKey:""%>"/> 
        <br/> 
        <label for="secretKey"><span>&nbsp;</span>Llave secreta:</label>
        <input type="textbox" name="secretKey" value="<%=secretKey!=null?secretKey:""%>"/> 
        <br/> 
        <br/>
        <div class="clear">&nbsp;</div>
    </div>
    <div class="clear">&nbsp;</div>
   <input type="submit" value="Guardar" class="btn-registro">&nbsp;&nbsp;&nbsp;&nbsp;
   <input type="reset" value="Cancelar" class="btn-registro"/>
  </form>


