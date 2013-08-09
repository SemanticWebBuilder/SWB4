<%-- 
    Document   : socialWebPageToSocialNets
    Created on : 06-ago-2013, 17:01:25
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.model.base.WebPageBase"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.SocialTopic"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="gui.ava.html.image.generator.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    String suri=request.getParameter("suri");
    if(suri==null) return;
    SemanticObject semObj=SemanticObject.createSemanticObject(suri);
    if(semObj==null) return;
    WebPage webPage=(WebPage)semObj.getGenericInstance(); 
    WebSite wsite=webPage.getWebSite();
    
    System.out.println("WebPage Url:"+webPage.getUrl());
    
    //http://do.convertapi.com/web2image?curl=http://www.google.com
    
    //request.getLocalAddr();
    //int port=request.getLocalPort(); 
   
    File imageFile=SWBSocialUtil.Util.downloadWebFile("http://do.convertapi.com/web2image?curl=http://localhost:8080/",SWBPortal.getWorkPath()+"/models/"+wsite.getId()+"/tmp/imagen.png");      
%>