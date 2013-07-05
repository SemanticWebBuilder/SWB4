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
<script type="text/javascript" id="<%=objUri%>appends">
    changeClassT = function() { 
       if(document.getElementById('Txt').className ='sel-txt'){
            document.getElementById('Txt').className ='sel-txt2';
            document.getElementById('Img').className ='sel-img';
            document.getElementById('Video').className ='sel-vid';
        }
    }
     changeClassI = function() { 
       if(document.getElementById('Img').className ='sel-img'){
            document.getElementById('Img').className ='sel-img2';
            document.getElementById('Txt').className ='sel-txt';
            document.getElementById('Video').className ='sel-vid';
        }
     }
     changeClassV = function() { 
       if(document.getElementById('Video').className ='sel-vid'){
            document.getElementById('Video').className ='sel-vid2';
            document.getElementById('Txt').className ='sel-txt';
            document.getElementById('Img').className ='sel-img';
        }
    }
</script>
<div dojoType="dojox.layout.ContentPane">
    <script type="dojo/method">
         eval(document.getElementById("<%=objUri%>appends").innerHTML);
   </script>
</div> 
<body>
<div class="swbform" id="publicar" >
        <p><strong>¿Qué deseas publicar?</strong></p>
        <div class="sel-media">
            <a id="Txt" title="Publicar un mensaje de texto" class="sel-txt" onclick="changeClassT();postHtml('<%=url.setMode("post").setParameters(argsM)%>', '<%=objUri%>postType')"><span>Publicar un mensaje de texto</span></a>
            <a id="Img" title="Publicar una imagen" class="sel-img" onclick="changeClassI();postHtml('<%=url.setMode("post").setParameters(argsP)%>', '<%=objUri%>postType')"><span>Publicar una imagen</span></a>
            <a id="Video" title="Publicar un video" class="sel-vid" onclick="changeClassV();postHtml('<%=url.setMode("post").setParameters(argsV)%>', '<%=objUri%>postType')"><span>Publicar un video</span></a>
	</div>
        
</div>
        <div id="<%=objUri%>postType" dojoType="dijit.layout.ContentPane">
</div>
</body>