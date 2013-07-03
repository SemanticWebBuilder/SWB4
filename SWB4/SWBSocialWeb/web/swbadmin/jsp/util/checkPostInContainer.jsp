<%-- 
    Document   : checkPostInContainer
    Created on : 02-jul-2013, 18:38:21
    Author     : jorge.jimenez
--%>

<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%> 
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

<%
    Iterator <SocialSite> itWebSites=SocialSite.ClassMgr.listSocialSites();
    while(itWebSites.hasNext())
    {
        SocialSite wsite=itWebSites.next();
        out.println("postInContainer Site:"+wsite+", hay:"+PostInContainer.ClassMgr.listPostInContainers(wsite).hasNext()); 
        Iterator <PostInContainer> itPostInCont=PostInContainer.ClassMgr.listPostInContainers(wsite);
        while(itPostInCont.hasNext())
        {
            PostInContainer postInContainer=itPostInCont.next();
            out.println("postInContainer existente:"+postInContainer);
        }
        
        out.println("\n\n\n\n\n postOutContainer Site:"+wsite+", hay:"+PostOutContainer.ClassMgr.listPostOutContainers(wsite).hasNext()); 
        Iterator <PostOutContainer> itPostOutCont=PostOutContainer.ClassMgr.listPostOutContainers(wsite);
        while(itPostOutCont.hasNext())
        {
            PostOutContainer postOutContainer=itPostOutCont.next();
            out.println("postOutContainer existente:"+postOutContainer);
        }
        
        out.println("\n\n\n\n\n postOutNet Site:"+wsite+", hay:"+PostOutNet.ClassMgr.listPostOutNets(wsite).hasNext());  
        Iterator <PostOutNet> itPostOutNets=PostOutNet.ClassMgr.listPostOutNets(wsite);
        while(itPostOutNets.hasNext())
        {
            PostOutNet postOutNet=itPostOutNets.next();
            out.println("postOutNet existente:"+postOutNet);
        }
    }
%>
