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
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>

<%
    org.semanticwb.model.User user=paramRequest.getUser(); 
    if(request.getAttribute("postOut")==null) return;
    
    SemanticObject semObj=(SemanticObject)request.getAttribute("postOut");
    if(semObj==null) return; 
    WebSite wsite=WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    if(wsite==null) return;
    
    PostOut postOut=(PostOut)semObj.getGenericInstance();
    //Un mensaje de entrada siempre debe estar atachado a un usuario de la red social de la que proviene, de esta manera, es como desde swbsocial
    //se respondería a un mensaje
    
    User userCreator=postOut.getCreator(); 
    System.out.println("userCreator George:"+userCreator);
    String userPhoto=userCreator.getPhoto(); 
    if(userPhoto==null) userPhoto="/swbadmin/css/images/profileDefImg.jpg";
    else{
        userPhoto=SWBPortal.getWebWorkPath()+userCreator.getWorkPath()+"/"+User.swb_usrPhoto.getName()+"_"+userCreator.getId()+"_"+userPhoto;
    }
    //Un mensaje de entrada siempre debe estar atachado a un usuar
    PostIn postInSource=null;
    if(postOut.getPostInSource()!=null)
    {
        postInSource=postOut.getPostInSource();
    }    
 %>


<div class="swbform" style="width: 500px">
    <div align="center"><img src="<%=userPhoto%>" width="150" height="150"/></div>
    <table style="width: 100%">
        <tr>
            <td align="center" colspan="5">
                <%=userCreator.getFullName()%> 
            </td>
        </tr>
        <tr>
            <td colspan="5">&nbsp;</td>
        </tr>
        
        <tr>
            <td colspan="5"><hr><hr></td>
        </tr>
   
        <tr>
            <td colspan="5" align="center">
                <b><u><%=SWBSocialUtil.Util.getStringFromGenericLocale("msg", user.getLanguage())%></u></b>
            </td>
        </tr>
        <%
        if(postInSource!=null)
        {
        %>    
        <tr>
          <td align="center">
              <%=SWBSocialUtil.Util.getStringFromGenericLocale("sentiment", user.getLanguage())%>:
              <%
              if(postInSource.getPostSentimentalType()==0)
              {
                  %>
                      ---
                  <%
              }else if(postInSource.getPostSentimentalType()==1)
              {
                 %>
                      <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/feelpos.png">
                 <%
              }else if(postInSource.getPostSentimentalType()==2) 
              {
                  %>
                      <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/feelneg.png">
                  <%
              }
              %>
          </td>
          <td align="center">
              <%=SWBSocialUtil.Util.getStringFromGenericLocale("intensity", user.getLanguage())%>:<%=postInSource.getPostIntesityType()==0?paramRequest.getLocaleString("low"):postInSource.getPostIntesityType()==1?paramRequest.getLocaleString("medium"):postInSource.getPostIntesityType()==2?paramRequest.getLocaleString("high"):"---"%>
          </td>
          <td align="center">
              <%=SWBSocialUtil.Util.getStringFromGenericLocale("replies", user.getLanguage())%>:<%=postInSource.getPostShared()%>
          </td>
          <td align="center">
              <%=SWBSocialUtil.Util.getStringFromGenericLocale("place", user.getLanguage())%>:<%=postInSource.getPostPlace() == null ? "---" : postInSource.getPostPlace()%>
          </td> 
          <td align="center">
              <%=SWBSocialUtil.Util.getStringFromGenericLocale("priority", user.getLanguage())%>:<%=postInSource.isIsPrioritary() ? SWBSocialUtil.Util.getStringFromGenericLocale("yes", user.getLanguage()) : SWBSocialUtil.Util.getStringFromGenericLocale("not", user.getLanguage())%>
          </td> 
        </tr>
        <%
        }
        %>
        <tr>
            <td colspan="5"><hr><hr></td>
        </tr>
        
    <tr>
    
<%    
   
    if(semObj.getGenericInstance() instanceof Message) 
    {
        Message message=(Message)semObj.getGenericInstance();
        %>
        <td colspan="5"><%=SWBUtils.TEXT.encode(message.getMsg_Text(), "utf8")%>
        </td>
    <%    
    }else if(semObj.getGenericInstance() instanceof Photo)
    {
        Photo photo=(Photo)semObj.getGenericInstance(); 
        //System.out.println("Name:"+Photo.social_Photo.getName()); 
        //System.out.println("ClassID:"+Photo.social_Photo.getClassId()); 
        //System.out.println("Canonical:"+Photo.social_Photo.getCanonicalName());
         //Puse ese tolowercase porque el nombre de la propiedad lo pone en mayuscula, quien sabe porque, si esta en minuscula
        %>
        <td colspan="5">
        <%
        Iterator<String> itPhotos=photo.listPhotos();
        while(itPhotos.hasNext())
        {
            String sphoto=itPhotos.next();
        %>
        <img src="<%=SWBPortal.getWebWorkPath()%><%=photo.getWorkPath()%>/<%=PostImageable.social_hasPhoto.getName()%>_<%=photo.getId()%>_<%=sphoto%>">
        <%
        }
        %>
        <br><br><br><%=SWBUtils.TEXT.encode(photo.getMsg_Text(), "utf8")%>
        </td>
    <%    
    }else if(semObj.getGenericInstance() instanceof Video)
    {
        Video video=(Video)semObj.getGenericInstance(); 
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

