<%-- 
    Document   : userHistory
    Created on : 26-jun-2013, 11:48:57
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
<%@page import="twitter4j.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    org.semanticwb.model.User user=paramRequest.getUser(); 
    if(request.getAttribute("swbSocialUser")==null) return; 
    
    SemanticObject semObj=(SemanticObject)request.getAttribute("swbSocialUser");
    if(semObj==null) return;
    
    WebSite wsite=WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    if(wsite==null) return;
    
    SocialNetworkUser socialNetUser=(SocialNetworkUser)semObj.getGenericInstance();
    //Un mensaje de entrada siempre debe estar atachado a un usuario de la red social de la que proviene, de esta manera, es como desde swbsocial
    //se respondería a un mensaje
    if(socialNetUser==null) return;   
    
    String userPhoto=socialNetUser.getSnu_photoUrl(); //Sacar la foto de la redSocial;
    if(userPhoto==null) userPhoto="/swbadmin/css/images/profileDefImg.jpg";
    
 %>


<div class="swbform" style="width: 500px">
    <div align="center"><img src="<%=userPhoto%>"/></div>
    <table style="width: 100%">
        <tr>
            <td align="center" colspan="5">
                <%=socialNetUser.getSnu_name()%>
            </td>
        </tr>
        <tr>
            <td align="center" colspan="5">
                <%=socialNetUser.getSnu_SocialNetworkObj()!=null?socialNetUser.getSnu_SocialNetworkObj().getId():"---"%>
            </td>
        </tr>
        <tr>
            <td colspan="5">&nbsp;</td>
        </tr>
        <tr>
            <td align="center">
                <%=SWBSocialUtil.Util.getStringFromGenericLocale("followers", user.getLanguage())%>: <b><%=socialNetUser.getFollowers()%></b>
            </td>
            <td align="center">
                <%=SWBSocialUtil.Util.getStringFromGenericLocale("friends", user.getLanguage())%>: <b><%=socialNetUser.getFriends()%></b>
            </td>
            <td align="center">
                Klout: <b><%=socialNetUser.getSnu_klout()%>
            </td>          
        </tr>
        
        <tr>
            <td colspan="5"><hr><hr></td>
        </tr>
        
        <tr>
            <%
                SWBResourceURL url=paramRequest.getRenderUrl();
                url.setMode(SWBResourceURL.Action_EDIT);
                url.setParameter("swbSocialUser", socialNetUser.getId());
                url.setParameter("dialog", "close");
                url.setParameter("suri", (String)request.getAttribute("suri")); 
                url.setParameter("reloadTap", "true");
                Iterator <PostIn> itPostIns=socialNetUser.listPostInInvs(); 
                long sizeItPostIns=SWBUtils.Collections.sizeOf(itPostIns); 
            %>
            <td>
                Mensajes de entrada:<a href="#" onclick="submitUrl('<%=url.setParameter("swbSocialUser", socialNetUser.getId())%>',this); return false;"><%=sizeItPostIns%></a>
            </td> 
        </tr>
        
        <tr>
            <td colspan="5"><hr><hr></td>
        </tr>
    
    </table>
