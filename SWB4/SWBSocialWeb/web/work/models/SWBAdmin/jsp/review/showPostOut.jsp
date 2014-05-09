<%-- 
    Document   : showPostIn
    Created on : 03-jun-2013, 13:01:48
    Author     : jorge.jimenez
--%>

<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
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
            
            @import "/swbadmin/css/swbsocial.css";          
            html, body, #main{
                overflow: auto;
            }
</style>
    
<%
    org.semanticwb.model.User user = paramRequest.getUser();
    if (request.getAttribute("postOut") == null) {
        return;
    }

    SemanticObject semObj = (SemanticObject) request.getAttribute("postOut");
    if (semObj == null) {
        return;
    }
    WebSite wsite = WebSite.ClassMgr.getWebSite(semObj.getModel().getName());
    if (wsite == null) {
        return;
    }

    PostOut postOut = (PostOut) semObj.getGenericInstance();
    //Un mensaje de entrada siempre debe estar atachado a un usuario de la red social de la que proviene, de esta manera, es como desde swbsocial
    //se respondería a un mensaje

    User userCreator = postOut.getCreator();
    System.out.println("userCreator George:" + userCreator);
    String userPhoto = userCreator.getPhoto();
    if (userPhoto == null) {
        userPhoto = "/swbadmin/css/images/profileDefImg.jpg";
    } else {
        userPhoto = SWBPortal.getWebWorkPath() + userCreator.getWorkPath() + "/" + userPhoto;
    }
%>
<%
    if (!(semObj.getGenericInstance() instanceof Message)) {//It's video or image(s) set SIZE
%>
<div style="height: 450px">
    <%} else {
    %>
    <div>
        <%    }
        %>
        <div class="swbform swbpopup msj-txt">
            <div class="perfilgral">
                <div class="perfil">
                    <img src="<%=userPhoto%>" width="150" height="150"/>
                    <p><%=userCreator.getFullName()%> </p>

                </div>
                <div class="clear"></div>      
            </div>
            <div class="msj-div"></div>

            <%
                String isSentMgstoClassify = SWBSocialUtil.Util.getModelPropertyValue(wsite, SWBSocialUtil.CLASSIFYSENTMGS_PROPNAME);
                if (isSentMgstoClassify != null && isSentMgstoClassify.equalsIgnoreCase("true")) //Los mensajes de salida si se deben clasificar por sentimientos e intensidad, tal como los de entrada.
                {
            %>    

            <table class="tabla1">
                <thead>
                    <tr>
                        <th><%=SWBSocialResUtil.Util.getStringFromGenericLocale("sentiment", user.getLanguage())%></th>
                        <th><%=SWBSocialResUtil.Util.getStringFromGenericLocale("intensity", user.getLanguage())%></th>
                        <th><%=SWBSocialResUtil.Util.getStringFromGenericLocale("priority", user.getLanguage())%></th>            
                    </tr>
                </thead>
                <tr>
                    <td>                
                        <%
                            if (postOut.getPostSentimentalType() == 0) {
                        %>
                        ---
                        <%                  } else if (postOut.getPostSentimentalType() == 1) {
                        %>
                        <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/pos.png">
                        <%
                        } else if (postOut.getPostSentimentalType() == 2) {
                        %>
                        <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/neg.png">
                        <%
                            }
                        %>
                    </td>
                    <!--<td align="center">
                    <%//=SWBSocialUtil.Util.getStringFromGenericLocale("intensity", user.getLanguage())%>:<%//=postOut.getPostIntesityType() == 0 ? paramRequest.getLocaleString("low") : postOut.getPostIntesityType() == 1 ? paramRequest.getLocaleString("medium") : postOut.getPostIntesityType() == 2 ? paramRequest.getLocaleString("high") : "---"%>
                </td>-->
                    <td>
                        <%
                            if (postOut.getPostIntesityType() == 0) {
                        %>
                        <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/ibaja.png" width="25" height="25" alt="<%=SWBSocialResUtil.Util.getStringFromGenericLocale("low", user.getLanguage())%>">
                        <%        } else if (postOut.getPostIntesityType() == 1) {
                        %>    
                        <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/imedia.png" width="25" height="25" alt="<%=SWBSocialResUtil.Util.getStringFromGenericLocale("medium", user.getLanguage())%>">
                        <%
                        } else if (postOut.getPostIntesityType() == 2) {
                        %>
                        <img src="<%=SWBPortal.getContextPath()%>/swbadmin/css/images/ialta.png" width="25" height="25" alt="<%=SWBSocialResUtil.Util.getStringFromGenericLocale("high", user.getLanguage())%>">
                        <%
                        } else {
                        %>
                        ----
                        <%}%>
                    </td>
                    <td align="center">
                        <%=postOut.isIsPrioritary() ? SWBSocialResUtil.Util.getStringFromGenericLocale("yes", user.getLanguage()) : SWBSocialResUtil.Util.getStringFromGenericLocale("not", user.getLanguage())%>
                    </td> 
                </tr>
            </table>
            <%
                }
            %>

            <%

                if (semObj.getGenericInstance() instanceof Message) {
                    Message message = (Message) semObj.getGenericInstance();
                    String pname = getPropertyName(semObj, Message.social_hasFile.getName());                    
            %>
            <div class="msj-inf">
                <p>
                    <%=SWBUtils.TEXT.encode(message.getMsg_Text(), "utf8")%>
                </p>
            </div>
            <%
            if(message.listFiles().hasNext())
            {
                %>
                    <p class="docAsoc">Documentos asociados</p>
                <%
            }
            Iterator<String> itDocs = message.listFiles();
            while (itDocs.hasNext()) {
                String sdoc = itDocs.next();
                String sFileTmp=sdoc;
                if (sFileTmp.startsWith(pname)) {
                    sFileTmp = sFileTmp.substring(pname.length() + 1);
                }
                String sclass="DocFormat_undefined";
                if(sFileTmp.endsWith(".xlsx") || sFileTmp.endsWith(".xlsm") || sFileTmp.endsWith(".xltx") || sFileTmp.endsWith(".xltm") || sFileTmp.endsWith(".xlsb") || sFileTmp.endsWith(".xlam"))sclass="DocFormat_excel";
                else if(sFileTmp.endsWith(".doc") || sFileTmp.endsWith(".docx") || sFileTmp.endsWith(".docm") || sFileTmp.endsWith(".dotx") || sFileTmp.endsWith(".dotm"))sclass="DocFormat_word";
                else if(sFileTmp.endsWith(".pptx") || sFileTmp.endsWith(".pptm") || sFileTmp.endsWith(".potx") || sFileTmp.endsWith(".ppsx"))sclass="DocFormat_powerPoint";
                else if(sFileTmp.endsWith(".pdf"))sclass="DocFormat_pdf";
                else if(sFileTmp.endsWith(".zip") || sFileTmp.endsWith(".rar"))sclass="DocFormat_zip";
                else if(sFileTmp.endsWith(".mp4") || sFileTmp.endsWith(".swf") || sFileTmp.endsWith(".flv") || sFileTmp.endsWith(".wav") || sFileTmp.endsWith(".wmv") || sFileTmp.endsWith(".mov"))sclass="DocFormat_video";
                else if(sFileTmp.endsWith(".jpg") || sFileTmp.endsWith(".png") || sFileTmp.endsWith(".gif") || sFileTmp.endsWith(".jpeg") || sFileTmp.endsWith(".bmp"))sclass="DocFormat_image";
                %>
                <p class="<%=sclass%>">
                <span id="img<%=sdoc%>" style="width: 200px; height: 200px; border: thick #666666; overflow: hidden; position: relative;">
                    <a href="<%=SWBPortal.getWebWorkPath()%><%=message.getWorkPath()%>/<%=sdoc%>" target="_blank" title="View full size"><%=sFileTmp%></a>
                </span>
                </p>

                <%
                }
            } else if (semObj.getGenericInstance() instanceof Photo) {
                Photo photo = (Photo) semObj.getGenericInstance();
                //System.out.println("Name:"+Photo.social_Photo.getName()); 
                //System.out.println("ClassID:"+Photo.social_Photo.getClassId()); 
                //System.out.println("Canonical:"+Photo.social_Photo.getCanonicalName());
                //Puse ese tolowercase porque el nombre de la propiedad lo pone en mayuscula, quien sabe porque, si esta en minuscula
            %>

            <br><%=SWBUtils.TEXT.encode(photo.getMsg_Text(), "utf8")%>

            <%
                Iterator<String> itPhotos = photo.listPhotos();
                while (itPhotos.hasNext()) {
                    String sphoto = itPhotos.next();
            %>

            <span id="img<%=sphoto%>" style="width: 200px; height: 200px; border: thick #666666; overflow: hidden; position: relative;">
                <a href="<%=SWBPortal.getWebWorkPath()%><%=photo.getWorkPath()%>/<%=sphoto%>" target="_blank" title="View full size"><img src="<%=SWBPortal.getWebWorkPath()%><%=photo.getWorkPath()%>/<%=sphoto%>" style="position: relative;" onload="imageLoad(this, 'img<%=sphoto%>');"/></a>
            </span>

            <%
                }
            %>

            <%
            } else if (semObj.getGenericInstance() instanceof Video) {
                Video video = (Video) semObj.getGenericInstance();
                String videoFormat = "";
                String videoUrl = video.getVideo();
                String fileext = null;
                if (videoUrl != null) {
                    int pos = videoUrl.lastIndexOf(".");
                    if (pos > -1) {
                        fileext = videoUrl.substring(pos);
                        int pos1 = fileext.indexOf("?");
                        if (pos1 > -1) {
                            fileext = fileext.substring(0, pos1);
                        }
                    }
                    if (videoUrl.toLowerCase().contains("www.youtube.com")) {//show player from youtube
                        videoFormat = "youtube";
                    } else if (fileext.toLowerCase().equals(".mp4")) {
                        videoFormat = "mp4";
                    } else if (fileext.toLowerCase().equals(".swf") || fileext.toLowerCase().equals(".mov")) {
                        videoFormat = "flash";
                    } else if (fileext.toLowerCase().equals(".flv")) {
                        videoFormat = "flv";
                    } else if (fileext.toLowerCase().equals(".wav")) {
                        videoFormat = "wav";
                    } else if (fileext.equals(".wmv")) {
                        videoFormat = "wmv";
                    }
                } else {%>
            <p><h1>VIDEO NO DISPONIBLE</p></h1>
            <%            }
            %>    

            <%
            if(videoFormat.equals("youtube")){
            %>
                <iframe id="ytplayer" type="text/html" width="250" height="195" src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" frameborder="0"></iframe>
             <%
            }else
            if (videoFormat.equals("flv")) {
            %>

            <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
            <br>
            <object id="video" type="application/x-shockwave-flash" data="<%=SWBPlatform.getContextPath()%>/swbadmin/player_flv_maxi.swf" width="400" height="200">
                <param name="movie" value="<%=SWBPlatform.getContextPath()%>/swbadmin/player_flv_maxi.swf" />
                <param name="FlashVars" value="flv=<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>"/>
            </object>
            <%
            } else if (videoFormat.equals("flash")) {
            %>

            <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
            <br>
            <object width="400" height="200" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"   codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0"> 
                <param name="SRC" value="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>">
                <embed src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" width="400" height="200"></embed>
            </object>



            <%} else if (videoFormat.equals("mp4")) {
            %>   
            <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
            <br>
            <video width="400" height="200" controls>
                <source src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" type="video/mp4">
                <object data="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" width="400" height="200">
                    <embed src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" width="400" height="200" autostart="false">    
                </object>
            </video>

            <%
            } else if (videoFormat.equals("wav")) {
            %>
            <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
            <br>
            <object width="400" height="200" classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" codebase="http://www.apple.com/qtactivex/qtplugin.cab">
                <param name="src" value="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>">
                <param name="controller" value="true">
            </object>

            <%
            } else if (videoFormat.equals("wmv")) {

            %>    

            <%=SWBUtils.TEXT.encode(video.getMsg_Text(), "utf8")%>
            <br>
            <object width="400" height="200" type="video/x-ms-asf" url="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" data="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">
                <param name="url" value="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>">
                <param name="filename" value="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>">
                <param name="autostart" value="1">
                <param name="uiMode" value="full">
                <param name="autosize" value="1">
                <param name="playcount" value="1"> 
                <embed type="application/x-mplayer2" src="<%=SWBPortal.getWebWorkPath()%><%=video.getWorkPath()%>/<%=video.getVideo()%>" width="400" height="200" autostart="true" showcontrols="true" pluginspage="http://www.microsoft.com/Windows/MediaPlayer/"></embed>
            </object>

            <%
                }

            %>
            <%
                }
            %>
            </table>
        </div>
    </div>
            
<%!
    private String getPropertyName(SemanticObject obj, String propName) 
    {
        return propName + "_" + (obj.getId()==null?"new":obj.getId());
    }
    
%>