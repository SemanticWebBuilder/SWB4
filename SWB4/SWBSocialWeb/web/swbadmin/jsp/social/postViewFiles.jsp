<%-- 
    Document   : postViewFiles
    Created on : 01-ago-2013, 17:30:17
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

if(request.getParameter("uri") != null) 
{
    SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("uri"));
    if(semObj!=null)
    {
        ArrayList aPostImages=new ArrayList();
        ArrayList aPostVideos=new ArrayList();
        String path=SWBPortal.getWorkPath()+semObj.getWorkPath();
        File postDirectory=new File(path);
        if(postDirectory.exists() && postDirectory.isDirectory())
        {
            if(semObj.getGenericInstance() instanceof Photo){
                Photo photo = (Photo) semObj.getGenericInstance();
                Iterator<String> itPhotos = photo.listPhotos();
                while (itPhotos.hasNext()) {
                    String sphoto = itPhotos.next();
                    aPostImages.add(sphoto);
                }
            }else if(semObj.getGenericInstance() instanceof Video){
                Video video = (Video) semObj.getGenericInstance();
                if(video.getVideo() != null && !video.getVideo().isEmpty()){
                    aPostVideos.add(video.getVideo());
                }
            }
            /*File[] directoryFiles=postDirectory.listFiles();
            for(int i=0;i<directoryFiles.length;i++)
            {
                File file=directoryFiles[i];
                if(file.isFile())
                {
                    //Para Imagenes. Extensiones soportados en la ontología para subir en los Post
                    if(file.getName().toLowerCase().endsWith(".png") || file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".gif")) 
                    {
                        aPostImages.add(file);
                    }else if(file.getName().toLowerCase().endsWith(".mov") || file.getName().toLowerCase().endsWith(".avi") 
                            || file.getName().toLowerCase().endsWith(".wmv") || file.getName().toLowerCase().endsWith(".mp4")
                            || file.getName().toLowerCase().endsWith(".swf") || file.getName().toLowerCase().endsWith(".flv")
                            || file.getName().toLowerCase().endsWith(".wav")
                            )   //Ver que extensiones para video soportamos 
                    {
                        aPostVideos.add(file);
                    }
                }
            }*/
            
            //Despliegue de Imagenes
            if(aPostImages.size()>0) 
            {
                Iterator<String> imageList=aPostImages.iterator();
                while(imageList.hasNext())
                {
                    String file=imageList.next();
                    System.out.println("file:"+SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file);
                    %>
                         <img src="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>"/>
                    <%
                }
            }
            
            //Despliegue de Videos
            
            if(aPostVideos.size()>0)
            {
                Iterator<String> videoList=aPostVideos.iterator();
                while(videoList.hasNext())
                {
                    String file=videoList.next();
                    System.out.println("file:"+SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file);
                    
                    String videoFormat = "";
                    String fileext=null;
                    int pos=file.lastIndexOf(".");
                    if(pos>-1)
                    {
                        fileext=file.substring(pos);
                        int pos1=fileext.indexOf("?");
                        if(pos1>-1)
                        {
                            fileext=fileext.substring(0, pos1);
                        }
                    }
                    if (file.toLowerCase().contains("www.youtube.com")) {//show player from youtube
                        videoFormat = "youtube";
                    } else if (fileext.toLowerCase().equals(".mp4")) {
                        videoFormat = "mp4";
                    } else if (fileext.toLowerCase().equals(".swf") || fileext.toLowerCase().equals(".mov")) { 
                        videoFormat = "flash";
                    } else if (fileext.toLowerCase().equals(".flv")) {
                        videoFormat = "flv";
                    } else if (fileext.toLowerCase().equals(".wav")) {
                        videoFormat = "wav";
                    }else if (fileext.equals(".wmv")) {
                        videoFormat = "wmv";
                    }
                    %>    

                    <%
                        if (videoFormat.equals("flv")) {
                    %>

                    <%=SWBUtils.TEXT.encode(file, "utf8")%>
                    <br>
                    <object id="video" type="application/x-shockwave-flash" data="<%=SWBPlatform.getContextPath()%>/swbadmin/player_flv_maxi.swf" width="400" height="200">
                        <param name="movie" value="<%=SWBPlatform.getContextPath()%>/swbadmin/player_flv_maxi.swf" />
                        <param name="FlashVars" value="flv=<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>"/>
                    </object>
                    <%
                    } else if (videoFormat.equals("flash")) {
                    %>

                    <%=SWBUtils.TEXT.encode(file, "utf8")%>
                    <br>
                    <object width="400" height="200" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"   codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0"> 
                        <param name="SRC" value="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>">
                        <embed src="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>" width="400" height="200"></embed>
                    </object>



                    <%} else if (videoFormat.equals("mp4")) {
                    %>   
                    <%=SWBUtils.TEXT.encode(file, "utf8")%>
                    <br>
                    <video width="400" height="200" controls>
                        <source src="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>" type="video/mp4">
                        <object data="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>" width="400" height="200">
                            <embed src="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>" width="400" height="200" autostart="false">    
                        </object>
                    </video>

                    <%
                    } else if (videoFormat.equals("wav")) {
                    %>
                    <%=SWBUtils.TEXT.encode(file, "utf8")%>
                    <br>
                    <object width="400" height="200" classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" codebase="http://www.apple.com/qtactivex/qtplugin.cab">
                        <param name="src" value="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>">
                        <param name="controller" value="true">
                    </object>

                    <%
                    } else if (videoFormat.equals("wmv")) {

                    %>    

                    <%=SWBUtils.TEXT.encode(file, "utf8")%>
                    <br>
                    <object width="400" height="200" type="video/x-ms-asf" url="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>" data="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">
                        <param name="url" value="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>">
                        <param name="filename" value="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>">
                        <param name="autostart" value="1">
                        <param name="uiMode" value="full">
                        <param name="autosize" value="1">
                        <param name="playcount" value="1"> 
                        <embed type="application/x-mplayer2" src="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>" width="400" height="200" autostart="true" showcontrols="true" pluginspage="http://www.microsoft.com/Windows/MediaPlayer/"></embed>
                    </object>

                    <%
                   }
                }
            }
            
        }

    } 
}       
%>