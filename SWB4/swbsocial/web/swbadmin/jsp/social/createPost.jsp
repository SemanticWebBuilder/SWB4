<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.social.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>
<%
//tipos de scope: aplicaciÛn, p·gina y peticiÛn
    String objUri = (String) request.getParameter("objUri");
    SWBResourceURL urlAction = paramRequest.getActionUrl();
    SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
    SocialTopic socialN = (SocialTopic) semanticObject.createGenericInstance();
    String marca = (String) request.getParameter("wsite");
    WebSite wsite = WebSite.ClassMgr.getWebSite(marca);
    String toPost = request.getParameter("toPost");
    SWBResourceURL url = paramRequest.getRenderUrl();
    String action = paramRequest.getAction();
    Map argsM = new HashMap();
    argsM.put("wsite", wsite.getId().toString());
    argsM.put("objUri", objUri);
    argsM.put("valor", "postMessage");
    argsM.put("action", action);

    Map argsP = new HashMap();
    argsP.put("wsite", wsite.getId().toString());
    argsP.put("objUri", objUri);
    argsP.put("valor", "uploadPhoto");
    argsP.put("action", action);

    Map argsV = new HashMap();
    argsV.put("wsite", wsite.getId().toString());
    argsV.put("objUri", objUri);
    argsV.put("valor", "uploadVideo");
    argsV.put("action", action);
%>
Que quieres Postear?
<script>
    function postHtml(url, tagid) {
        dojo.xhrPost({
            url: url,
            load: function(response) {
                var tag=dojo.byId(tagid);
                if(tag){
                    var pan=dijit.byId(tagid);
                    //alert("-"+tagid+"-"+tag+"-"+pan+"-");
                    if(pan && pan.attr) {
                        pan.attr('content',response);
                    }else {
                        tag.innerHTML = response;
                    }
                }else {
                    alert("No existe ning√∫n elemento con id " + tagid);
                }
                return response;
            },
            error: function(response) {
                if(dojo.byId(tagid)) {
                    dojo.byId(tagid).innerHTML = "<p>Ocurri√≥ un error con respuesta:<br />" + response + "</p>";
                } else {
                    alert("No existe ning√∫n elemento con id " + tagid);
                }
                return response;
            },
            handleAs: "text"
        });
    }
</script>
<a style="cursor: pointer;" title="Mensaje" onclick="postHtml('<%=url.setMode("post").setParameters(argsM)%>', 'respuesta')"><img src="/swbadmin/images/text.png"/></a>
<a style="cursor: pointer;" title="Foto" onclick="postHtml('<%=url.setMode("post").setParameters(argsP)%>', 'respuesta')"><img src="/swbadmin/images/photo.png"/></a>
<a style="cursor: pointer;" title="Video" onclick="postHtml('<%=url.setMode("post").setParameters(argsV)%>', 'respuesta')"><img src="/swbadmin/images/video.png"/></a>
<br/><br/>
<br/><br/>
<div id="respuesta" dojoType="dijit.layout.ContentPane">
</div>
