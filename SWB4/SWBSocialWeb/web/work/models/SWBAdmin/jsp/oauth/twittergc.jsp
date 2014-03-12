<%-- 
    Document   : twittergc
    Created on : 12-mar-2014, 11:21:55
    Author     : jorge.jimenez
--%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.Language"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="org.semanticwb.social.TwitterGC"%>
<%@page import="org.semanticwb.social.SocialAdmin"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    SocialAdmin socialAdm=(SocialAdmin)SWBContext.getAdminWebSite();
    TwitterGC twitterGC=socialAdm.getAdm_twittergc();
    String appKey=null, secretKey=null;
    if(twitterGC!=null)
    {
        appKey=twitterGC.getAppKey();
        secretKey=twitterGC.getSecretKey();
    }
    SWBResourceURL acc_url = paramRequest.getActionUrl().setCallMethod(SWBResourceURL.Call_DIRECT).setAction(SWBResourceURL.Action_EDIT);        
%>

<br/><br/><br/><br/>
    <form id="form_<%=socialAdm.getId()%>_twitterGC" dojoType="dijit.form.Form" class="swbform" action="<%=acc_url%>" method="post" onsubmit="submitForm('form_<%=socialAdm.getId()%>_twitterGC'); return false;"> 
    <%if(twitterGC!=null){%>    
        <input type="hidden" name="twitterGCid" value="<%=twitterGC.getId()%>">    
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


