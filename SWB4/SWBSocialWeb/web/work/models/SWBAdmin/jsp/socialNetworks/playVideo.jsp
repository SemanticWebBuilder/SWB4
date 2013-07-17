<%-- 
    Document   : playVideo
    Created on : 26/06/2013, 06:09:04 PM
    Author     : francisco.jimenez
--%>

<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>

<%
    String videoFormat ="";
    String videoUrl = URLDecoder.decode(request.getParameter("videoUrl"),"UTF-8");
    System.out.println("VIDEO URL:" + videoUrl);
    if(videoUrl.toLowerCase().contains("www.youtube.com")){//show player from youtube
        videoFormat = "youtube";
    }else if(videoUrl.toLowerCase().contains(".mp4")){
        videoFormat = "video/mp4";
    }else if(videoUrl.toLowerCase().contains(".3gp")){
        
    }else if(videoUrl.toLowerCase().contains(".swf")){
        videoFormat = "flash";
    }
%>
<%
    if(videoFormat.equals("youtube")){
        System.out.println("Displaing Youtube Video");
%>
    <div class="swbform">
        <fieldset>
        <iframe id="ytplayer" type="text/html" width="640" height="390"
        src="<%=videoUrl%>" frameborder="0"/>
        </fieldset>
    </div>
<%
    }else if(videoFormat.equals("flash")){
%>
    <div class="swbform">
        <fieldset>
        <object type="application/x-shockwave-flash" data="<%=videoUrl%>">
            <param name="movie" value="<%=videoUrl%>" />
        </object>
        </fieldset>
    </div>
<%
    }else{
%>
    <div class="swbform">
        <fieldset>
            <video width="640" height="390" controls autoplay>
              <source src="<%=videoUrl%>" type="video/mp4">
              <object data="<%=videoUrl%>" width="640" height="390">
                <embed width="640" height="390" src="<%=videoUrl%>">
              </object>
            </video>
        </fieldset>
    </div>
<%
    }
%>