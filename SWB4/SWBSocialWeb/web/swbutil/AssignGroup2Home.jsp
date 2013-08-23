<%-- 
    Document   : AssignGroup2Home
    Created on : 05-jul-2013, 18:29:52
    Author     : jorge.jimenez
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
<%@page import="java.io.File"%>

<%
   UserGroup userGrpAdmin=null; 
   WebSite wsite=SWBContext.getAdminWebSite();
   Iterator <UserGroup> ituserGrps=wsite.getUserRepository().listUserGroups();
   while(ituserGrps.hasNext())
   {
       UserGroup userGrp=ituserGrps.next();  
       System.out.println("userGrp:"+userGrp);
       if(userGrp.getURI().equals("http://www.semanticwb.org/uradm#swb_UserGroup:admin"))
       {
           userGrpAdmin=userGrp;
       }
   }
   
   if(userGrpAdmin!=null)
   {
       System.out.println("userGrpAdmin-1:"+userGrpAdmin);
       UserGroupRef userGrpRef=UserGroupRef.ClassMgr.createUserGroupRef(wsite);
       userGrpRef.setUserGroup(userGrpAdmin);
       userGrpRef.setActive(true);
       wsite.getHomePage().addUserGroupRef(userGrpRef);
       System.out.println("Asignado...");
   }

%>