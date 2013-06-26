<%-- 
    Document   : showPostIn
    Created on : 03-jun-2013, 13:01:48
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
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    org.semanticwb.model.User user=paramRequest.getUser(); 
    System.out.println("postInJsp:"+request.getAttribute("postIn"));
    if(request.getAttribute("postIn")==null) return;
    
    SemanticObject semObj=(SemanticObject)request.getAttribute("postIn");
    if(semObj==null) return; 
    
    WebSite wsite=WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    if(wsite==null) return;
    
    PostIn postIn=(PostIn)semObj.getGenericInstance();
    //Un mensaje de entrada siempre debe estar atachado a un usuario de la red social de la que proviene, de esta manera, es como desde swbsocial
    //se respondería a un mensaje
    if(postIn.getPostInSocialNetworkUser()==null) return;   
    
    SocialNetworkUser socialNetworkUser=postIn.getPostInSocialNetworkUser();
    String userPhoto=socialNetworkUser.getSnu_photoUrl(); //Sacar la foto de la redSocial;
    if(userPhoto==null) userPhoto="/swbadmin/images/profileDefImg.jpg";
    
 %>


<div class="swbform" style="width: 500px">
    <div align="center"><img src="<%=userPhoto%>"/></div>
    <table style="width: 100%">
        <tr>
            <td align="center" colspan="5">
                <%=socialNetworkUser.getSnu_name()%>
            </td>
        </tr>
        <tr>
            <td colspan="5">&nbsp;</td>
        </tr>
        <tr>
            <td align="center">
                <%=SWBSocialUtil.Util.getStringFromGenericLocale("followers", user.getLanguage())%>: <b><%=socialNetworkUser.getFollowers()%></b>
            </td>
            <td align="center">
                <%=SWBSocialUtil.Util.getStringFromGenericLocale("friends", user.getLanguage())%>: <b><%=socialNetworkUser.getFriends()%></b>
            </td>
            <td align="center">
                Klout: <b><%=socialNetworkUser.getSnu_klout()%>
            </td>          
        </tr>
        
        <tr>
            <td colspan="5"><hr><hr></td>
        </tr>
   
        <tr>
            <td colspan="5" align="center">
                <b><u><%=SWBSocialUtil.Util.getStringFromGenericLocale("msg", user.getLanguage())%></u></b>
            </td>
        </tr>
        
          <tr>
            <td align="center">
                <%=SWBSocialUtil.Util.getStringFromGenericLocale("sentiment", user.getLanguage())%>:
                <%
                if(postIn.getPostSentimentalType()==0)
                {
                    %>
                        ---
                    <%
                }else if(postIn.getPostSentimentalType()==1)
                {
                   %>
                        <img src="<%=SWBPortal.getContextPath()%>/swbadmin/images/feelpos.png">
                   <%
                }else if(postIn.getPostSentimentalType()==2)
                {
                    %>
                        <img src="<%=SWBPortal.getContextPath()%>/swbadmin/images/feelneg.png">
                    <%
                }
                %>
            </td>
            <td align="center">
                <%=SWBSocialUtil.Util.getStringFromGenericLocale("intensity", user.getLanguage())%>:<%=postIn.getPostIntesityType()==0?paramRequest.getLocaleString("low"):postIn.getPostIntesityType()==1?paramRequest.getLocaleString("medium"):postIn.getPostIntesityType()==2?paramRequest.getLocaleString("high"):"---"%>
            </td>
            <td align="center">
                <%=SWBSocialUtil.Util.getStringFromGenericLocale("replies", user.getLanguage())%>:<%=postIn.getPostRetweets()%>
            </td>
            <td align="center">
                <%=SWBSocialUtil.Util.getStringFromGenericLocale("place", user.getLanguage())%>:<%=postIn.getPostPlace() == null ? "---" : postIn.getPostPlace()%>
            </td> 
            <td align="center">
                <%=SWBSocialUtil.Util.getStringFromGenericLocale("priority", user.getLanguage())%>:<%=postIn.isIsPrioritary() ? SWBSocialUtil.Util.getStringFromGenericLocale("yes", user.getLanguage()) : SWBSocialUtil.Util.getStringFromGenericLocale("not", user.getLanguage())%>
            </td> 
        </tr>
        
        <tr>
            <td colspan="5"><hr><hr></td>
        </tr>
        
    <tr>
    
<%    
   
    if(semObj.getGenericInstance() instanceof MessageIn) 
    {
        MessageIn message=(MessageIn)semObj.getGenericInstance();
        %>
        <td colspan="5"><%=SWBUtils.TEXT.encode(message.getMsg_Text(), "utf8")%>
        </td>
    <%    
    }else if(semObj.getGenericInstance() instanceof PhotoIn)
    {
        PhotoIn photo=(PhotoIn)semObj.getGenericInstance(); 
        //System.out.println("Name:"+Photo.social_Photo.getName()); 
        //System.out.println("ClassID:"+Photo.social_Photo.getClassId()); 
        //System.out.println("Canonical:"+Photo.social_Photo.getCanonicalName());
         //Puse ese tolowercase porque el nombre de la propiedad lo pone en mayuscula, quien sabe porque, si esta en minuscula
        %>
        <td colspan="5">
        <img src="<%=SWBPortal.getWebWorkPath()%><%=photo.getWorkPath()%>/<%=Photo.social_Photo.getName().toLowerCase()%>_<%=photo.getId()%>_<%=photo.getPhoto()%>">
        <br><br><br><%=SWBUtils.TEXT.encode(photo.getMsg_Text(), "utf8")%>
        </td>
    <%    
    }else if(semObj.getGenericInstance() instanceof VideoIn)
    {
        VideoIn video=(VideoIn)semObj.getGenericInstance(); 
    %>    
        <td colspan="5">
        <%=video.getVideo()%>
        <br><br><br><%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
        </td>
    <%     
    }
    %>
    </tr>
    </table>

