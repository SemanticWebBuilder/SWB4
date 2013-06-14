<%-- 
    Document   : socialSentPost
    Created on : 22-may-2013, 17:18:47
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
System.out.println("Entra a SocialSentPost_sObjPostOut:"+request.getAttribute("sObjPostOut"));
SemanticObject sObjPostOut=(SemanticObject)request.getAttribute("sObjPostOut");
System.out.println("Entra a SocialSentPost-3:"+sObjPostOut);
if(sObjPostOut.createGenericInstance() instanceof org.semanticwb.social.MessageIn)   
{
    System.out.println("Entra a SocialSentPost-4");
    MessageIn messageIn=(MessageIn)sObjPostOut.createGenericInstance();
    out.println(showData(messageIn, "msg", paramRequest));
}else if(sObjPostOut.createGenericInstance()  instanceof Photo)
{
    PhotoIn photoIn=(PhotoIn)sObjPostOut.createGenericInstance();
     out.println(showData(photoIn, "photo", paramRequest));
    //System.out.println("Es una foto");
}else if(sObjPostOut.createGenericInstance()  instanceof Video)
{
    VideoIn videoIn=(VideoIn)sObjPostOut.createGenericInstance();
    out.println(showData(videoIn, "video", paramRequest));
    //System.out.println("Es un Video");
}                    
%>

<%!
private String showData(PostIn postIn, String postOutType, SWBParamRequest paramRequest)
{
    if(postIn==null) return null;
    StringBuilder strb=new StringBuilder();  
    try
    {
        strb.append("<table border=\"0\" cellspacing=\"5\">");
        strb.append("<tr>");
        strb.append("<td>");
        strb.append(postOutType.equals("msg")?paramRequest.getLocaleString("message"):postOutType.equals("photo")?paramRequest.getLocaleString("photo"):postOutType.equals("video")?paramRequest.getLocaleString("video"):"---");
        strb.append("</td>");
        strb.append("</tr>");
        strb.append("<tr>");
        strb.append("<td>");
        if(postIn instanceof  MessageIn)
        {
            MessageIn msgIn=(MessageIn)postIn;
            strb.append(msgIn.getMsg_Text()!=null?msgIn.getMsg_Text():"---");
        }else if(postIn instanceof  PhotoIn)
        {
            PhotoIn photoIn=(PhotoIn)postIn;
            strb.append(photoIn.getPhoto());
            strb.append("<br>"+photoIn.getMsg_Text()!=null?photoIn.getMsg_Text():"---");
        }else if(postIn instanceof  VideoIn)
        {
            VideoIn videoIn=(VideoIn)postIn;
            strb.append(videoIn.getVideo());
            strb.append("<br>"+videoIn.getMsg_Text()!=null?videoIn.getMsg_Text():"---");
        }
        strb.append("</td>");
        strb.append("</tr>");
      strb.append("</table>");  
        
    }catch(Exception e)
    {
        //log.error(e); 
    }
    
    return strb.toString();
}

%>