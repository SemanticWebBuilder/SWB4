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
    org.semanticwb.model.User user = paramRequest.getUser();
    System.out.println("postInJsp:" + request.getAttribute("postIn"));
    if (request.getAttribute("postIn") == null) {
        return;
    }

    SemanticObject semObj = (SemanticObject) request.getAttribute("postIn");
    if (semObj == null) {
        return;
    }

    WebSite wsite = WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    if (wsite == null) {
        return;
    }

    PostIn postIn = (PostIn) semObj.getGenericInstance();
    //Un mensaje de entrada siempre debe estar atachado a un usuario de la red social de la que proviene, de esta manera, es como desde swbsocial
    //se respondería a un mensaje
    if (postIn.getPostInSocialNetworkUser() == null) {
        return;
    }

    SocialNetworkUser socialNetworkUser = postIn.getPostInSocialNetworkUser();
    String userPhoto = socialNetworkUser.getSnu_photoUrl(); //Sacar la foto de la redSocial;
    if(userPhoto == null || userPhoto.isEmpty()){
        userPhoto = "/swbadmin/css/images/profileDefImg.jpg";
    }    
%>
             

<div class="swbform swbpopup usr-pop">
    <div class="perfilgral">
        <div class="perfil">           
            <img src="<%=userPhoto%>"/>
            <p><%=socialNetworkUser.getSnu_name()%></p>
        </div>
        <p><strong><%=socialNetworkUser.getFollowers()%></strong> <%=SWBSocialUtil.Util.getStringFromGenericLocale("followers", user.getLanguage())%></p>
        <p><strong><%=socialNetworkUser.getFriends()%></strong> <%=SWBSocialUtil.Util.getStringFromGenericLocale("friends", user.getLanguage())%></p>
        <p><strong><%=socialNetworkUser.getSnu_klout()%></strong> Klout</p>

        <div class="clear"></div>
    </div>
    <jsp:include page="/work/models/SWBAdmin/jsp/post/postActions.jsp">
        <jsp:param name="postUri" value="<%=postIn.getURI()%>" />
    </jsp:include>
    <table class="tabla1">
        <thead>
            <tr>               
                <th>
                    <span><%=paramRequest.getLocaleString("post")%></span>
                </th>
                <th>
                    <span><%=paramRequest.getLocaleString("sentiment")%></span>
                </th>
                <th>
                    <span><%=paramRequest.getLocaleString("intensity")%></span>
                </th>
                <th>
                    <span><%=paramRequest.getLocaleString("replies")%></span>
                </th>
                <th>
                    <span><%=paramRequest.getLocaleString("place")%></span>
                </th>
                <th>
                    <span><%=paramRequest.getLocaleString("prioritary")%></span>
                </th>
            </tr>
        </thead>


        <tbody>
            <tr>

                <%
                    if (semObj.getGenericInstance() instanceof MessageIn) {
                        MessageIn message = (MessageIn) semObj.getGenericInstance();
                %>
                <td><span><%=SWBUtils.TEXT.encode(message.getMsg_Text(), "utf8")%></span>
                </td>
                <%
                } else if (semObj.getGenericInstance() instanceof PhotoIn) {
                    PhotoIn photo = (PhotoIn) semObj.getGenericInstance();
                    //System.out.println("Name:"+Photo.social_Photo.getName()); 
                    //System.out.println("ClassID:"+Photo.social_Photo.getClassId()); 
                    //System.out.println("Canonical:"+Photo.social_Photo.getCanonicalName());
                    //Puse ese tolowercase porque el nombre de la propiedad lo pone en mayuscula, quien sabe porque, si esta en minuscula 
%>
                <td>
                    <%
                        Iterator<String> itPhotos = photo.listPhotos();
                        while (itPhotos.hasNext()) {
                            String sphoto = itPhotos.next();
                    %>   

                    <img src="<%=sphoto%>">
                    <%
                        }
                    %>     
                    <%=SWBUtils.TEXT.encode(photo.getMsg_Text(), "utf8")%>
                </td>
                <%
                } else if (semObj.getGenericInstance() instanceof VideoIn) {
                    VideoIn video = (VideoIn) semObj.getGenericInstance();
                   
                    out.print("<td>");
                    if(video.getVideo().contains("youtube")){
                    %>
                        <iframe id="ytplayer" type="text/html" width="250" height="195"
                        src="<%=video.getVideo()%>" frameborder="0"/>
                <%
                    }else if(video.getVideo().contains(".swf")){
                %>
                        <embed src="<%=video.getVideo()%>" width="250" height="195" autostart="false" type="application/x-shockwave-flash">
                <%
                    }else{
                %>
                            <video width="250" height="195" controls autoplay>
                              <source src="<%=video.getVideo()%>" type="video/mp4">
                              <object data="<%=video.getVideo()%>" width="250" height="195">
                                <embed width="250" height="195" src="<%=video.getVideo()%>">
                              </object>
                            </video>
                <%
                    }
                %>
                    <br/><br/><%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
                </td>

                <%
                    }
                %>
                <td>

                    <%
                        if (postIn.getPostSentimentalType() == 0) {
                    %>
                    ---
                    <% } else if (postIn.getPostSentimentalType() == 1) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/pos.png">
                    <%
                    } else if (postIn.getPostSentimentalType() == 2) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/neg.png">
                    <%
                        }
                    %>
                </td>
                <td>
                    <!--<%=postIn.getPostIntesityType() == 0 ? paramRequest.getLocaleString("low") : postIn.getPostSentimentalType() == 1 ? paramRequest.getLocaleString("medium") : postIn.getPostSentimentalType() == 2 ? paramRequest.getLocaleString("high") : "---"%>-->

                    <%
                        if (postIn.getPostIntesityType() == 0) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/ibaja.png" width="25" height="25" alt="<%=paramRequest.getLocaleString("low")%>">
                    <%        } else if (postIn.getPostIntesityType() == 1) {
                    %>    
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/imedia.png" width="25" height="25" alt="<%=paramRequest.getLocaleString("medium")%>">
                    <%
                    } else if (postIn.getPostIntesityType() == 2) {
                    %>
                    <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/ialta.png" width="25" height="25" alt="<%=paramRequest.getLocaleString("high")%>">
                    <%
                    } else {
                    %>
                    ----
                    <%}%>
                </td> 
                <td align="center">
                    <%=postIn.getPostShared()%>
                </td>
                <td align="center">
                    <%=postIn.getPostPlace() == null ? "---" : postIn.getPostPlace()%>
                </td> 
                <td align="center">
                    <%=postIn.isIsPrioritary() ? SWBSocialUtil.Util.getStringFromGenericLocale("yes", user.getLanguage()) : SWBSocialUtil.Util.getStringFromGenericLocale("not", user.getLanguage())%>
                </td> 
            </tr>

        </tbody>
    </table>
</div>

