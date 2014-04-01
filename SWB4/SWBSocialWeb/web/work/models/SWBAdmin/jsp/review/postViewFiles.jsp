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
<script type="text/javascript" charset="utf-8" src="/work/models/SWBAdmin/js/swbsocial.js" ></script>
<script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false, locale: 'es'"></script>

<style>
    .box{width:275px;}
    .mediaBox .mediaInfo{background-color:#fffec3;margin:0 10px 10px 0;padding:0 8px 10px;font-size:12px;font-weight:bold;}
    .last{margin-bottom:0;}
    #bar{ background-color:#fff;border-width:1px;border-style:solid;border-color:#F0F0EB #F0F0EB #B0B0AC;padding:1px;}
    .center
    {
    margin:auto;
    width:70%;
    background-color:#F86014;
    }
    
    ul {
    font-size:25px;
    text-align:center
    }
    ul li {
        display:inline;
        zoom:1
    }
    .imgcontainer {
        max-width:750px;
        margin:0 auto
    }
</style>

<div class="center">
    <div id="bar" class="center">
        <div class="imgcontainer">
<%
    HashMap<String, String> mapIcons = new HashMap<String,String>() {{
        put(".doc", "/swbadmin/css/images/iconsDocs/doc-word-64px.jpg");
        put(".xls", "/swbadmin/css/images/iconsDocs/doc-xls-64px.jpg");
        put(".ppt", "/swbadmin/css/images/iconsDocs/doc-ppt-64px.jpg");
        put(".txt", "/swbadmin/css/images/iconsDocs/doc-txt-64px.jpg");
        put(".pdf", "/swbadmin/css/images/iconsDocs/doc-txt-64px.jpg");
        put("unknown", "/swbadmin/css/images/iconsDocs/doc-desconocido-64px.jpg");
        //put(".pdf", "/swbadmin/css/images/iconsDocs/doc-word-64px.jpg");
        put("1","/swbadmin/css/images/iconsDocs/doc-imagen-64px.jpg");//group of images
        put("2", "/swbadmin/css/images/iconsDocs/doc-video-64px.jpg");//group of videos
    }};
    
    String validExts[][]={
                        {".doc",".pdf",".xls",".ppt",".txt"},//0
                        {".jpg",".jpeg",".png", ".gif"},//1
                        {".mp4",".swf",".mov",".flv",".wav","wmv"}//2
                        };
%>

<%

if(request.getParameter("uri") != null && request.getParameter("netUri")!=null) 
{
    SemanticObject semObj=SemanticObject.createSemanticObject(request.getParameter("uri"));
    if(semObj!=null)
    {
        //Conteo de Hits de Attaches al PostOut
        PostOut postOut=(PostOut)semObj.createGenericInstance();  
        if(postOut!=null)
        {
            SocialNetwork socialNet=(SocialNetwork)SemanticObject.createSemanticObject(request.getParameter("netUri")).createGenericInstance(); 
            if(socialNet!=null)
            {
                Iterator<PostOutNet> itPostOutNets=PostOutNet.ClassMgr.listPostOutNetBySocialPost(postOut);
                while(itPostOutNets.hasNext())
                {
                    PostOutNet postOutNet=itPostOutNets.next();
                    if(postOutNet.getSocialNetwork().getURI().equals(socialNet.getURI()))
                    {
                        postOutNet.setHitsAttach(postOutNet.getHitsAttach()+1); 
                        break;
                    }
                }
            }
        }
        //Termina Conteo de Hits de Attaches al PostOut
        
        
        ArrayList aPostFiles=new ArrayList();
        ArrayList aPostImages=new ArrayList();
        ArrayList aPostVideos=new ArrayList();
        
        String path=SWBPortal.getWorkPath()+semObj.getWorkPath();
        File postDirectory=new File(path);
        
        if(postDirectory.exists() && postDirectory.isDirectory())
        {
            if(semObj.createGenericInstance() instanceof Message){
                //System.out.println("IS A MESAGE**\n");
                Message message = (Message) semObj.createGenericInstance();
                out.println("<div align=\"center\" style=\"font-size:45;\">" + message.getMsg_Text() + "</div>");
                Iterator<String> itFile = message.listFiles();
                while (itFile.hasNext()) {
                    String sFile = itFile.next();
                    //System.out.println("FILE: " +sFile);
                    
                    String fileExt = "";
                    String fileType = "";
                    for(int i=0; i < validExts.length; i++){
                        if(!fileExt.isEmpty())//If the extension was found exit the loop
                            break;
                        for(int j=0; j <validExts[i].length; j++){
                            if(sFile.toLowerCase().endsWith(validExts[i][j])){
                                fileExt = validExts[i][j];
                                //System.out.println("VALID EXT: " + fileExt);
                                fileType = String.valueOf(i);
                                break;
                            }
                        }
                    }
                    List fileEntry = new ArrayList();
                    //System.out.println("REEG:" + sFile.replaceAll("hasFile_\\d+_", ""));
                    fileEntry.add(sFile);//(0) all the filename
                    fileEntry.add(fileExt);//(1) only the extention
                    fileEntry.add(fileType);//(2) group of filetypes images/videos
                    aPostFiles.add(fileEntry);
                }
            }else if(semObj.createGenericInstance() instanceof Photo){
                //System.out.println("IS A PICTURE****\n");
                Photo photo = (Photo) semObj.createGenericInstance();
                //out.println("THE MESSAGE:" +  photo.getMsg_Text());
                out.println("<div align=\"center\" style=\"font-size:45;\">" + photo.getMsg_Text() + "</div>");
                Iterator<String> itPhotos = photo.listPhotos();
                while (itPhotos.hasNext()) {
                    String sImage = itPhotos.next();
                    //System.out.println("IMAGE: " +sImage);
                    
                    String fileExt = "";
                    String fileType = "";
                   
                    for(int j=0; j <validExts[1].length; j++){//Loop only images (row 1)
                        if(sImage.toLowerCase().endsWith(validExts[1][j])){
                            fileExt = validExts[1][j];
                            //System.out.println("VALID EXT: " + fileExt);
                            fileType = String.valueOf(1);
                            break;
                        }
                    }
                    
                    List fileEntry = new ArrayList();
                    fileEntry.add(sImage);//(0) all the filename
                    fileEntry.add(fileExt);//(1) only the extention
                    fileEntry.add(fileType);//(2) group of filetypes images/videos
                    aPostImages.add(fileEntry);                    
                }
            }else if(semObj.createGenericInstance() instanceof Video){
                System.out.println("IS A VIDEO********\n");
                Video video = (Video) semObj.createGenericInstance();
                //out.println("THE MESSAGE:" +  video.getMsg_Text());
                out.println("<div align=\"center\" style=\"font-size:45;\">" + video.getMsg_Text() + "</div>");
                if(video.getVideo() != null && !video.getVideo().isEmpty()){
                    System.out.println("VIDEO: " + video.getVideo());
                    aPostVideos.add(video.getVideo());
                }
            }
        }
        //System.out.println("*********************");        

        if(aPostFiles.size() > 0){//Displays documents
            for(int i=0; i< aPostFiles.size(); i++){
                List entry = (List)aPostFiles.get(i);
                String icon = "";
                boolean isImage = false;
                boolean downloadButton = false;
                
                //System.out.println("file 0:"+SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+entry.get(0));
                //System.out.println("file 1:"+entry.get(1));
                //System.out.println("file 2:"+entry.get(2));
                
                if(mapIcons.get(entry.get(1)) != null){//Look for single file extention(.doc, .docx, etc)
                    //System.out.println("NAME doc:" + entry.get(0) + "  ICON:" + mapIcons.get(entry.get(1)));//File extentions
                    icon = mapIcons.get(entry.get(1));                    
                    downloadButton = true;
                }                    
                else if(mapIcons.get(entry.get(2)) != null){//Look for images(1) or video(2)
                    //System.out.println("NAME img/video:" + entry.get(0) + "  ICON:" + mapIcons.get(entry.get(2)));//Group of file extentions (images, videos)
                    icon = mapIcons.get(entry.get(2));
                    isImage = entry.get(2).equals("1");
                }else{
                    //System.out.println("NAME DESCONOCIDO:" + entry.get(0) + " " + entry.get(1) + "  SIN ICON" + " " + entry.get(2));//Unknown icon
                    icon = mapIcons.get("unknown");
                    downloadButton = true;
                }
                String filename = ((String)entry.get(0)).replaceAll("hasFile_\\d+_", "");//get original filename
                %>
                    <ul>
                         <%=filename%>
                        <div>
                            <%
                                if(downloadButton){//Document
                            %>
                            <img src="<%=icon%>"/>
                            </br><a href ="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+entry.get(0)%>">DESCARGAR</a>
                            <%
                                }else{
                                    if(isImage){//Image
                            %>
                                <ul>
                                   <!--<div style="height:300px">-->
                                       <li id="img_<%=entry.get(0)%>" style="width:400px; height:200px">
                                           <img src="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+entry.get(0)%>" onload="imageLoad(this, 'img_<%=entry.get(0)%>');"/>
                                       </li>
                                       <li>
                                           </br><a href ="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+entry.get(0)%>">Ver tamaño completo</a>
                                       </li>
                                   <!--</div>-->
                                </ul>
                                
                            <%
                                    }else{//Video?
                            %>
                                    <img src="<%=icon%>"/>
                                    </br>Visualizar
                            <%
                                    }
                                }
                            %>
                        </div>
                    </ul>
                <%
            }
        }
        
        if(aPostImages.size() > 0){
            for(int i=0; i< aPostImages.size(); i++){
                List entry = (List)aPostImages.get(i);
                String icon = "";
                //System.out.println("file:"+SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+entry.get(0));
                    %>
                    <ul>
                         <%=((String)entry.get(0)).replaceAll("hasPhoto_\\d+_", "")%>
                        <div>
                           
                            <li id="img_<%=entry.get(0)%>" style="width:400px; height:200px">
                                <img src="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+entry.get(0)%>" onload="imageLoad(this, 'img_<%=entry.get(0)%>');"/>
                            </li>
                            <li>
                                </br><a href ="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+entry.get(0)%>">Ver tamaño completo</a>
                            </li>
                        </div>
                    <%
                if(mapIcons.get(entry.get(2)) != null){//Look for images or video
                    //System.out.println("NAME img/video:" + entry.get(0) + "  ICON:" + mapIcons.get(entry.get(2)));//Group of file extentions (images, videos)
                    icon = mapIcons.get(entry.get(2));
                }else{
                    //System.out.println("NAME DESCONOCIDO:" + entry.get(0) + " " + entry.get(1) + "  SIN ICON" + " " + entry.get(2));//Unknown icon
                    icon = mapIcons.get("unknown");
                }%>
                    </ul>
                <%
            }
        }
        
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
                    <ul>
                        <%=SWBUtils.TEXT.encode(file.replaceAll("video_\\d+_", ""), "utf8")%>
                        <div>
                    <%
                        if (videoFormat.equals("flv")) {
                    %>                                            
                        <br>
                        <object id="video" type="application/x-shockwave-flash" data="<%=SWBPlatform.getContextPath()%>/swbadmin/player_flv_maxi.swf" width="400" height="200">
                            <param name="movie" value="<%=SWBPlatform.getContextPath()%>/swbadmin/player_flv_maxi.swf" />
                            <param name="FlashVars" value="flv=<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>"/>
                        </object>
                    <%
                    } else if (videoFormat.equals("flash")) {
                    %>

                        <br>
                        <object width="400" height="200" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"   codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0"> 
                            <param name="SRC" value="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>">
                            <embed src="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>" width="400" height="200"></embed>
                        </object>
                    <%} else if (videoFormat.equals("mp4")) {
                    %>   
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
                        <br>
                        <object width="400" height="200" classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" codebase="http://www.apple.com/qtactivex/qtplugin.cab">
                            <param name="src" value="<%=SWBPortal.getWebWorkPath()+semObj.getWorkPath()+"/"+file%>">
                            <param name="controller" value="true">
                        </object>
                    <%
                    } else if (videoFormat.equals("wmv")) {
                    %>
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
                    %>
                        </div>
                    </ul>
                    <%
                }
            }
    }
}                                               
%>
    </div>
 </div>
</div>