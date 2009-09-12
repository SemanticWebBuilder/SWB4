<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="com.google.gdata.util.AuthenticationException"%>
<%@page import="com.google.gdata.client.*"%>
<%@page import="com.google.gdata.client.youtube.*"%>
<%@page import="com.google.gdata.data.*"%>
<%@page import="com.google.gdata.data.geo.impl.*"%>
<%@page import="com.google.gdata.data.youtube.*"%>
<%@page import="com.google.gdata.data.extensions.*"%>
<%@page import="com.google.gdata.util.*"%>
<%@page import="com.google.gdata.data.media.*"%>
<%@page import="com.google.gdata.data.media.mediarss.*"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Date"%>
<%@page import="java.net.URL"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>

<div class="miembros">
<%
String action=paramRequest.getAction();
SWBResourceURL url=paramRequest.getRenderUrl();
SWBResourceURL urlAction=paramRequest.getActionUrl();
YouTubeService service = (YouTubeService)request.getAttribute("service");
if(action.equals("comment") || action.equals("spam")){
    String entryUrl=request.getParameter("entryUrl");
    urlAction.setAction(action);
    urlAction.setParameter("entryUrl", entryUrl);
    %>
        <form action="<%=urlAction%>" method="post">
            <fieldset>
            <legend>Comentario</legend>
            <ul>
                <p>Comentario:<input type="text" name="comment"/></p>
                <p><input type="submit" value="enviar"/></p>
            </ul>
            </fieldset>
        </form>
    <%
}else if(action.equals("detail")){
     String entryUrl=request.getParameter("entryUrl");
     VideoEntry videoEntry = service.getEntry(new URL(entryUrl), VideoEntry.class);

}else if(action.equals("showAll")){
    try{
     String feedUrl = "http://gdata.youtube.com/feeds/api/playlists/D2C26D097ECEAB44?v=2";
     PlaylistFeed feed = service.getFeed(new URL(feedUrl), PlaylistFeed.class);
     for(PlaylistEntry entry : feed.getEntries()) {
     String videoId=entry.getHtmlLink().getHref();
     int pos=videoId.indexOf("v=");
     if(pos>-1) videoId=videoId.substring(pos+2);
     %>
        <div class="moreUser">
            <object width="225" height="155">
            <param name="movie" value="http://www.youtube.com/v/<%=videoId%>&rel=1&color1=0x2b405b&color2=0x6b8ab6&border=1"></param>
            <param name="wmode" value="transparent"></param>
            <embed src="http://www.youtube.com/v/<%=videoId%>&rel=1&color1=0x2b405b&color2=0x6b8ab6&border=1" type="application/x-shockwave-flash" wmode="transparent" width="425" height="355"></embed>
            </object>
            <p><%=entry.getTitle().getPlainText()%></p>
            <p><%=entry.getMediaGroup().getDescription().getPlainTextContent()%></p>
            <p><%=entry.getPublished()%></p>
            <p><%=entry.getUpdated()%></p>

            <%url.setParameter("entryUrl", "http://gdata.youtube.com/feeds/api/videos/"+videoId);url.setAction("detail");%><p><a href="<%=url%>">detalle</a> |
            <%url.setAction("comment");%><a href="<%=url%>">comentar</a> |
            <%url.setAction("spam");%><a href="<%=url%>">spam</a> |
            <%urlAction.setParameter("entryUrl", "http://gdata.youtube.com/feeds/api/videos/"+videoId);urlAction.setAction("favorite");%><a href="<%=urlAction%>">favorito</a> |
            <%urlAction.setAction("delete");%><a href="<%=urlAction%>">eliminar</a>
            <%
             //Recuperar los comentarios de un video
            String commentUrl = entry.getComments().getFeedLink().getHref();
            CommentFeed commentFeed = service.getFeed(new URL(commentUrl), CommentFeed.class);
            for(CommentEntry comment : commentFeed.getEntries()) {
              %><p><%=comment.getPlainTextContent()%></p><%
            }
            %>
        </div>
<%
    }
   }catch(Exception e){e.printStackTrace();}
}else {
try{
     String feedUrl = "http://gdata.youtube.com/feeds/api/users/default/favorites";
     VideoFeed videoFeed = service.getFeed(new URL(feedUrl), VideoFeed.class);
     for(VideoEntry entry : videoFeed.getEntries()) {
     String videoId=entry.getHtmlLink().getHref();
     int pos=videoId.indexOf("v=");
     if(pos>-1) videoId=videoId.substring(pos+2);
     %>
            <div class="moreUser">
                <object width="225" height="155">
                <param name="movie" value="http://www.youtube.com/v/<%=videoId%>&rel=1&color1=0x2b405b&color2=0x6b8ab6&border=1"></param>
                <param name="wmode" value="transparent"></param>
                <embed src="http://www.youtube.com/v/<%=videoId%>&rel=1&color1=0x2b405b&color2=0x6b8ab6&border=1" type="application/x-shockwave-flash" wmode="transparent" width="425" height="355"></embed>
                </object>
                <p><%=entry.getTitle().getPlainText()%></p>
                <p><%=entry.getMediaGroup().getDescription().getPlainTextContent()%></p>
                <p><%=entry.getPublished()%></p>
                <p><%=entry.getUpdated()%></p>
                
                <%url.setParameter("entryUrl", "http://gdata.youtube.com/feeds/api/videos/"+videoId);url.setAction("detail");%><p><a href="<%=url%>">detalle</a> |
                <%url.setAction("comment");%><a href="<%=url%>">comentar</a> |
                <%url.setAction("spam");%><a href="<%=url%>">spam</a> |
                <%urlAction.setParameter("entryUrl", "http://gdata.youtube.com/feeds/api/videos/"+videoId);urlAction.setAction("favorite");%><a href="<%=urlAction%>">favorito</a> |
                <%urlAction.setParameter("entryUrl", "http://gdata.youtube.com/feeds/api/users/default/favorites/"+videoId);urlAction.setAction("unfavorite");%><a href="<%=urlAction%>">Eliminar de los favoritos</a>
                <%urlAction.setParameter("entryUrl", "http://gdata.youtube.com/feeds/api/videos/"+videoId);urlAction.setAction("delete");%><a href="<%=urlAction%>">eliminar</a>
                <%
                 //Recuperar los comentarios de un video
                String commentUrl = entry.getComments().getFeedLink().getHref();
                CommentFeed commentFeed = service.getFeed(new URL(commentUrl), CommentFeed.class);
                for(CommentEntry comment : commentFeed.getEntries()) {
                  %><p><%=comment.getPlainTextContent()%></p><%
                }
                %>
            </div>
<%
    }
}catch(Exception e){e.printStackTrace();}
%>
<a href="<%=url.setAction("showAll")%>">Ver todos</a>
<%
}
%>
</div>
