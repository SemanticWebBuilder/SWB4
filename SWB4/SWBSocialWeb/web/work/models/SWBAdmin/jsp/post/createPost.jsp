<%-- 
    Document   : createPost
    Created on : 19/03/2013, 05:32:31 PM
    Author     : francisco.jimenez
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.social.SocialTopic"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<% 
    String objUri = (String) request.getParameter("suri");
    SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
    SocialTopic socialTopic = (SocialTopic) semanticObject.createGenericInstance();
    String brand = socialTopic.getSemanticObject().getModel().getName();    
    WebSite wsite = WebSite.ClassMgr.getWebSite(brand);
    SWBResourceURL url = paramRequest.getRenderUrl();
    //String action = paramRequest.getAction();    
    
    Map argsM = new HashMap();
    argsM.put("wsite", wsite.getId().toString());
    argsM.put("objUri", objUri);
    argsM.put("valor", "postMessage");
    //argsM.put("action", action);
    //System.out.println(wsite.getId().toString() + "--" + objUri + "--" + action );
    //System.out.println(wsite.getId().toString() + "--" + objUri);
    Map argsP = new HashMap();
    argsP.put("wsite", wsite.getId().toString());
    argsP.put("objUri", objUri);
    argsP.put("valor", "uploadPhoto");
    //argsP.put("action", action);
    //System.out.println(wsite.getId().toString() + "--" + objUri);
    Map argsV = new HashMap();
    argsV.put("wsite", wsite.getId().toString());
    argsV.put("objUri", objUri);
    argsV.put("valor", "uploadVideo");
    //argsV.put("action", action);
    //System.out.println(wsite.getId().toString() + "--" + objUri);
%>
<div class="swbform">
    <table width="50%" border="0px">            
       <tr>
           <td colspan="3" style="text-align: center;"><b>¿Qu&eacute; deseas postear?</b></td>        
       </tr>
       <tr>
           <td colspan="3" style="text-align: center;">&nbsp;</td>        
       </tr>
       <tr>
            <td style="text-align: center;"><a style="cursor: pointer;" title="Mensaje" onclick="postHtml('<%=url.setMode("post").setParameters(argsM)%>', 'postType')"><img src="/swbadmin/images/text.png"/></a></td>
            <td style="text-align: center;"><a style="cursor: pointer;" title="Foto" onclick="postHtml('<%=url.setMode("post").setParameters(argsP)%>', 'postType')"><img src="/swbadmin/images/photo.png"/></a></td>
            <td style="text-align: center;"><a style="cursor: pointer;" title="Video" onclick="postHtml('<%=url.setMode("post").setParameters(argsV)%>', 'postType')"><img src="/swbadmin/images/video.png"/></a></td>
       </tr>        
    </table>
</div>

<div id="postType" dojoType="dijit.layout.ContentPane">
</div>
