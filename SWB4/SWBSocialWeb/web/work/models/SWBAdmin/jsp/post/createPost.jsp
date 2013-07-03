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
<body>
<div class="swbform" id="publicar" >
        <p><strong>¿Qué deseas publicar?</strong></p>
        <div class="sel-media">
		<a title="Publicar un mensaje de texto" class="sel-txt" onclick="postHtml('<%=url.setMode("post").setParameters(argsM)%>', '<%=objUri%>postType')"><span>Publicar un mensaje de texto</span></a>
		<a title="Publicar una imagen" class="sel-img2" onclick="postHtml('<%=url.setMode("post").setParameters(argsP)%>', '<%=objUri%>postType')"><span>Publicar una imagen</span></a>
		<a title="Publicar un video" class="sel-vid" onclick="postHtml('<%=url.setMode("post").setParameters(argsV)%>', '<%=objUri%>postType')"><span>Publicar un video</span></a>
	</div>
        
</div>
        <div id="<%=objUri%>postType" dojoType="dijit.layout.ContentPane">
</div>
</body>