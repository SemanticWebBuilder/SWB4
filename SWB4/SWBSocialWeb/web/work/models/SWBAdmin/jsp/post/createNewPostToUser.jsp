<%-- 
    Document   : createNewPostToUser
    Created on : 6/01/2014, 06:41:09 PM
    Author     : francisco.jimenez
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
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    SWBResourceURL url = paramRequest.getRenderUrl();
    SemanticObject sObjSocialNet=(SemanticObject)SemanticObject.createSemanticObject(request.getParameter("netSuri"));
    String socialNetUri = sObjSocialNet.getURI();
    SocialNetwork socialNetwork=(SocialNetwork)sObjSocialNet.createGenericInstance();
    
    System.out.println("Entra a PostInResponse..:"+socialNetwork);
    
    Map args = new HashMap();
    args.put("wsite", socialNetwork.getSemanticObject().getModel().getName());
    args.put("objUri", request.getParameter("suri"));
    args.put("username", request.getParameter("username"));
    args.put("netSuri", request.getParameter("netSuri"));
    
    System.out.println("Username:" + request.getParameter("username"));
    System.out.println("netSuri" + request.getParameter("netSuri"));
       boolean isMessageAble=false;
    boolean isPhotoable=false;
    boolean isVideoable=false; 
    if(socialNetwork.getSemanticObject().getSemanticClass().isSubClass(Messageable.social_Messageable))
    {
        isMessageAble=true; 
    } 
    if(socialNetwork.getSemanticObject().getSemanticClass().isSubClass(Photoable.social_Photoable)) 
    {
        isPhotoable=true;
    }
    if(socialNetwork.getSemanticObject().getSemanticClass().isSubClass(Videoable.social_Videoable)) 
    {
        isVideoable=true; 
    } 
%>
    <div class="swbform" id="publicar">
    <div class="sel-media">

    <table width="50%" border="0px">            
       <tr>
           <td colspan="3" style="text-align: center;"><b><%=SWBUtils.TEXT.encode(paramRequest.getLocaleString("whattoPost"),"utf8")%></b></td>        
       </tr>
       <tr>
           <td colspan="3" style="text-align: center;">&nbsp;</td>        
       </tr>
       <tr>
            <%if(isMessageAble){
                args.put("valor", "postMessage");
            %>
                 <td style="text-align: center;"><a id="Txt" class="sel-txt2" style="cursor: pointer;" title="<%=paramRequest.getLocaleString("message")%>" onclick="postHtml('<%=url.setMode("post").setParameters(args)%>', '<%=socialNetwork%>postType')"></a></td>
            <%}%>
            <%if(isPhotoable){  
                args.put("valor", "uploadPhoto"); 
            %> 
                <td style="text-align: center;"><a id="Img" class="sel-img2" style="cursor: pointer;" title="<%=paramRequest.getLocaleString("photo")%>" onclick="postHtml('<%=url.setMode("post").setParameters(args)%>', '<%=socialNetwork%>postType')"></a></td>
            <%}%>
            <%//if(isVideoable){
                if(isVideoable && !(socialNetwork instanceof Youtube)){
                args.put("valor", "uploadVideo"); 
            %> 
                <td style="text-align: center;"><a id="Video" class="sel-vid2" style="cursor: pointer;" title="<%=paramRequest.getLocaleString("video")%>" onclick="postHtml('<%=url.setMode("post").setParameters(args)%>', '<%=socialNetwork%>postType')"></a></td>
            <%}%>    
       </tr>        
    </table>
</div>

</div>

<div id="<%=socialNetwork%>postType" dojoType="dijit.layout.ContentPane">
</div>
