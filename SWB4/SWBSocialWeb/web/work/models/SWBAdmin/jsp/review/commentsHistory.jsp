<%-- 
    Document   : commentsHistory
    Created on : 16/12/2013, 11:55:34 AM
    Author     : francisco.jimenez
--%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.social.util.*"%>
<%@page import="java.util.*"%>

<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<style type="text/css">
    span.inline { display:inline; }
</style>
<%
    String postUri = (request.getAttribute("postOut") != null ?(String)request.getAttribute("postOut") : null );
    if(postUri == null){
        return;
    }
    
    SemanticObject sobj = SemanticObject.createSemanticObject(postUri);    
    PostOut postOut = (PostOut) sobj.createGenericInstance(); 
    WebSite wsite = WebSite.ClassMgr.getWebSite(postOut.getSemanticObject().getModel().getName());
    
    if(postOut == null){//|| !postOut.isPublished()){
        return;
    }
    
    String loading="<BR/><center><img src='"+SWBPlatform.getContextPath()+"/swbadmin/css/images/loading.gif'/><center>";
    
    System.out.println("--------------------");
    ArrayList postOutSocialNets = SWBSocialUtil.sparql.getPostOutSocialNetwors(postOut);
    for(int i= 0; i < postOutSocialNets.size(); i++){
        System.out.println("->" + postOutSocialNets.get(i));
    }
    
    System.out.println("--------------------");
    /*ArrayList postOutSocialOUTNETS = SWBSocialUtil.sparql.getPostOutNetsPostOut(postOut, (SocialNetwork)SemanticObject.createSemanticObject("http://www.NewBrandOne.swb#social_Facebook:1").createGenericInstance());
    for(int i= 0; i < postOutSocialOUTNETS.size(); i++){
        System.out.println("-------->" + postOutSocialOUTNETS.get(i));
    }*/
    System.out.println("--------------------");
    System.out.println("POST OUT:" + postOut + "->" + postOut.getMsg_Text() + "</br>");
    //out.println("POST OUT:" + postOut + "->" + postOut.getMsg_Text() + "</br>");    
    //Iterator<PostOutNet> itPostOutNets = PostOutNet.ClassMgr.listPostOutNetBySocialPost(postOut, wsite);    
    
    
%>

<!-- this div is only for documentation purpose, in real development environments, just take it out -->
<!--<div style="height: 300px; width: 500px">-->
<div class="swbform swbpopup retema-pop">
    <div dojoType="dijit.layout.TabContainer" style="width: 100%;" doLayout="false">
        <%
        for(int i=0; i< postOutSocialNets.size(); i++){
            SocialNetwork socialNetwork = (SocialNetwork)((SemanticObject)postOutSocialNets.get(i)).createGenericInstance();
    
            //out.println("  <option value=\"" + topic.getURI() +  "\">" + topic.getDisplayTitle(user.getLanguage()) + "</option>");
            ///out.println("-->" + outNet.getURI());
            ///out.println("-->" + outNet.getSocialPost().getSocialTopic());
            ///out.println("-->" + outNet.getSocialNetwork().getURI());
            //if(outNet.getStatus() == 1){//Fue publicado correctamente            
            //if(!(socialNetwork instanceof Twitter) && !(socialNetwork instanceof Facebook)){
            if(!(socialNetwork instanceof Twitter)){
            %>
            <div style="height: 300px;" id="<%=socialNetwork.getURI()%>" dojoType="dijit.layout.ContentPane" href="<%=paramRequest.getRenderUrl().setMode("recoverComments").setParameter("postOutUri", postOut.getURI()).setParameter("postOutNetNetwork", socialNetwork.getURI()) %>" title="<%=socialNetwork.getDisplayTitle("es")%>" _loadingMessage="<%=loading%>">
                <div class="swbform">
                <%
                    //out.println("&nbsp;&nbsp;&nbsp;&nbsp;POST OUT NET:" + outNet + "->" + outNet.getSocialNetwork().getTitle() + "</br>");
                %>
                </div>
            </div>
        <%
            }
            //}
        }
        postOut.setNumTotNewResponses(0);
        %>
    </div>
</div>