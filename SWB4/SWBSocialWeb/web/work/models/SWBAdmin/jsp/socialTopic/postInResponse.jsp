<%-- 
    Document   : postInResponse
    Created on : 27-may-2013, 17:27:34
    Author     : jorge.jimenez
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
    SemanticObject sObjPostIn=(SemanticObject)request.getAttribute("postUri");
    String postUri = (String)request.getAttribute("postUri");
    PostIn postIn=(PostIn)sObjPostIn.createGenericInstance();
    SocialNetwork postInSN=postIn.getPostInSocialNetwork();
    
    System.out.println("Entra a PostInResponse..:"+postIn);
    
    Map args = new HashMap();
    args.put("wsite", postIn.getSemanticObject().getModel().getName());
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
                 <td style="text-align: center;"><a id="Txt" class="sel-txt2" style="cursor: pointer;" title="<%=paramRequest.getLocaleString("message")%>" onclick="postHtml('<%=url.setMode("post").setParameters(args)%>', '<%=postUri%>postType')"></a></td>
            <%}%>
            <%if(isPhotoable){  
                args.put("valor", "uploadPhoto"); 
            %> 
                <td style="text-align: center;"><a id="Img" class="sel-img2" style="cursor: pointer;" title="<%=paramRequest.getLocaleString("photo")%>" onclick="postHtml('<%=url.setMode("post").setParameters(args)%>', '<%=postUri%>postType')"></a></td>
            <%}%>
            <%if(isVideoable){
                args.put("valor", "uploadVideo"); 
            %> 
                <td style="text-align: center;"><a id="Video" class="sel-vid2" style="cursor: pointer;" title="<%=paramRequest.getLocaleString("video")%>" onclick="postHtml('<%=url.setMode("post").setParameters(args)%>', '<%=postUri%>postType')"></a></td>
            <%}%>    
       </tr>        
    </table>
    </div>
</div>

<div id="<%=postUri%>postType" dojoType="dijit.layout.ContentPane">
</div>
