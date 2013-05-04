<%-- 
    Document   : socialTopicMsgInResponse
    Created on : 03-Mayo-2013, 11:34:37
    Author     : Jorge.Jiménez
--%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="static org.semanticwb.social.admin.resources.SocialTopicMsgOut.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
 <style type="text/css">
            @import "/swbadmin/js/dojo/dojo/resources/dojo.css";
            @import "/swbadmin/css/swb.css";
 </style>
<script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false, locale: 'es'" ></script>
<script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb.js" ></script>
<script type="text/javascript" >
 dojo.require("dojox.layout.ExpandoPane");
</script>
<%
System.out.println("Entra a Jsp Response-0");
if(request.getParameter("showSource")!=null)
{
    SWBResourceURL url = paramRequest.getRenderUrl();
    System.out.println("Entra a Jsp Response");
    SemanticObject sObjPostIn=(SemanticObject)request.getAttribute("sObjPostIn");
    System.out.println("Entra a Jsp Response-1:"+sObjPostIn);
    PostIn postIn=(PostIn)sObjPostIn.createGenericInstance();
    System.out.println("Entra a Jsp Response-2:"+postIn);
    SocialNetwork postInSN=postIn.getPostInSocialNetwork();
    System.out.println("Entra a Jsp Response-3:"+postInSN);
    
    Map args = new HashMap();
    System.out.println("wsite k:"+postIn.getSemanticObject().getModel().getName());
    args.put("wsite", postIn.getSemanticObject().getModel().getName());
    System.out.println("objUri k:"+postIn.getURI());
    args.put("objUri", postIn.getURI());
    args.put("postInSN", postInSN.getURI());
    
    boolean isMessageAble=false;
    boolean isPhotoable=false;
    boolean isVideoable=false; 
    if(postInSN.getSemanticObject().getSemanticClass().isSubClass(Messageable.social_Messageable))
    {
        isMessageAble=true; 
    } 
    if(postInSN.getSemanticObject().getSemanticClass().isSubClass(Photoable.social_Photoable)) 
    {
        isPhotoable=true;
    }
    if(postInSN.getSemanticObject().getSemanticClass().isSubClass(Videoable.social_Videoable)) 
    {
        isVideoable=true; 
    } 
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
                <%if(isMessageAble){
                    args.put("valor", "postMessage");
                %>
                     <td style="text-align: center;"><a style="cursor: pointer;" title="Mensaje" onclick="postHtml('<%=url.setMode("post").setParameters(args)%>', 'postType')"><img src="/swbadmin/images/text.png"/></a></td>
                <%}%>
                <%if(isPhotoable){  
                    args.put("valor", "uploadPhoto"); 
                %> 
                    <td style="text-align: center;"><a style="cursor: pointer;" title="Foto" onclick="postHtml('<%=url.setMode("post").setParameters(args)%>', 'postType')"><img src="/swbadmin/images/photo.png"/></a></td>
                <%}%>
                <%if(isVideoable){
                    args.put("valor", "uploadVideo"); 
                %> 
                    <td style="text-align: center;"><a style="cursor: pointer;" title="Video" onclick="postHtml('<%=url.setMode("post").setParameters(args)%>', 'postType')"><img src="/swbadmin/images/video.png"/></a></td>
                <%}%>    
           </tr>        
        </table>
    </div>

    <div id="postType" dojoType="dijit.layout.ContentPane">
    </div>
       
       
       
<%
}
%>
       