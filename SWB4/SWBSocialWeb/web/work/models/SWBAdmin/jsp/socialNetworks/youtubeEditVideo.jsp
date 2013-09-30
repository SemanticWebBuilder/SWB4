<%-- 
    Document   : youtubeEditVideo
    Created on : 29/09/2013, 09:58:56 PM
    Author     : francisco.jimenez
--%>

<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.social.YouTubeCategory"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.social.Youtube"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.HashMap"%>
<%@page import="static org.semanticwb.social.admin.resources.YoutubeWall.*"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<%!      
    public static String getFullVideoFromId(String id, String accessToken){
        HashMap<String, String> params = new HashMap<String, String>(3);    
        params.put("v", "2");
        params.put("alt","json");
    
        String response = null;
        try{
         response = getRequest(params, "https://gdata.youtube.com/feeds/api/videos/" + id,
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", accessToken);

        }catch(Exception e){
            System.out.println("Error getting user information"  + e.getMessage());
        }
        return response;
    }
%>

<%
    String videoId = (String) request.getParameter("videoId");
    String objUri = (String)request.getParameter("suri");
    if(videoId == null || objUri == null){
        return;
    }
    
    SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
    Youtube semanticYoutube = (Youtube) semanticObject.createGenericInstance();
    
    if(!semanticYoutube.validateToken()){//If was unable to refresh the token
        out.println("Problem refreshing access token");
        return;
    }
     
    
    String video = getFullVideoFromId(videoId, semanticYoutube.getAccessToken());
    //System.out.println("userprofile:" + video);    
    //out.print("video:" + video);
    JSONObject usrResp = new JSONObject(video);
    
    String sex = "";
    String name = "";
    String birthday="";
    String locationName= "";
    String locationCoordinates="";
    String locationId = "";
    
    String description = "";
    String category = "";
    String keywords = "";
    
    
    String picture = "";
    String profileUrl = "";
    String subscribers = "";
    int friendsCount = 0;
    int mutualFriendsCount = 0;
    
    
    System.out.println("Displaying user information!!");
    JSONObject information = usrResp.getJSONObject("entry");      

    
    if(!information.isNull("title")){
        name = information.getJSONObject("title").getString("$t");
    }
    if(!information.isNull("media$group")){
        if(!information.getJSONObject("media$group").isNull("media$description")){
            if(!information.getJSONObject("media$group").getJSONObject("media$description").isNull("$t")){
                description = information.getJSONObject("media$group").getJSONObject("media$description").getString("$t");
            }
        }
        
        if(!information.getJSONObject("media$group").isNull("media$category")){
            if(!information.getJSONObject("media$group").getJSONArray("media$category").getJSONObject(0).isNull("$t")){
                category = information.getJSONObject("media$group").getJSONArray("media$category").getJSONObject(0).getString("$t");
            }
        }
        
        if(!information.getJSONObject("media$group").isNull("media$keywords")){
            if(!information.getJSONObject("media$group").getJSONObject("media$keywords").isNull("$t")){
                keywords = information.getJSONObject("media$group").getJSONObject("media$keywords").getString("$t");
            }
        }
    }

    if(!information.isNull("media$thumbnail")){
        picture = information.getJSONObject("media$thumbnail").getString("url");
    }

    if(!information.isNull("yt$location")){
        locationName = information.getJSONObject("yt$location").getString("$t");
    }
    if(!information.isNull("birthday_date")){
        birthday = information.getString("birthday_date");
    }
    if(!information.isNull("yt$statistics")){
        if(!information.getJSONObject("yt$statistics").isNull("subscriberCount")){
            subscribers = information.getJSONObject("yt$statistics").getString("subscriberCount");
        }
    }
%>

<div class="swbform" style="width: 500px">
    <fieldset>
        <div align="center">
            <embed src="<%=BASE_VIDEO_URL + videoId%>" width="250" height="195" autostart="false" type="application/x-shockwave-flash">
        </div>
    </fieldset>
        
    <form type="dijit.form.Form" id="editedVideo" action="<%=paramRequest.getActionUrl().setAction("doUpdateVideo").setParameter("suri", objUri).setParameter("videoId", videoId)%>" method="post" onsubmit="submitForm('editedVideo'); try{document.getElementById('csLoading').style.display='inline';}catch(noe){}; return false;">
    <fieldset>
        <legend>Title:</legend>
        <div align="left">
            <input type="text" name="title" size="67" value="<%=name%>"/>
        </div>
    </fieldset>
    
    <fieldset>
        <legend>Description:</legend>
        <div align="left">
            <textarea rows="5" cols="50" name="description"><%=description%></textarea>
        </div>
    </fieldset>
    
    <fieldset>
        <legend>Video tags:</legend>
        <div align="left">
            <input type="text" name="keywords" size="67" value="<%=keywords%>"/>
        </div>
    </fieldset>
    
    <fieldset>
        <legend>Category:</legend>
        <div align="left">
            <select name="category">
                <%
                        SWBModel model = WebSite.ClassMgr.getWebSite(paramRequest.getWebPage().getWebSiteId());
                        Iterator<YouTubeCategory> itYtube = YouTubeCategory.ClassMgr.listYouTubeCategories(model);
                        while (itYtube.hasNext()) {
                            YouTubeCategory socialCategory = (YouTubeCategory) itYtube.next();
                %>
                    <option <%=category.equals(socialCategory.getId()) ? " selected " : ""%>value="<%=socialCategory.getId()%>"><%=socialCategory.getTitle()%></option>
                <%
                    }
                %>
            </select>
        </div>
    </fieldset>
            <div align="center"><input type="submit" value="Actualizar"/></div>
    </form>

<%
    if(description.isEmpty()){
%>
    <fieldset>
        <legend>Description:</legend>
        <div align="left"><%=description%></div>
    </fieldset>
<%
    }
%>






</div>

<%  
%>