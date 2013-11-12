<%-- 
    Document   : youtubeEditVideo
    Created on : 29/09/2013, 09:58:56 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.json.JSONArray"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.platform.SemanticClass"%>
<%@page import="org.semanticwb.social.PostOutPrivacy"%>
<%@page import="org.semanticwb.SWBUtils"%>
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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    //out.println("userprofile:" + video);    
    //out.print("video:" + video);
    //response.getWriter().write("video:" + video);
    
    JSONObject usrResp = new JSONObject(video);
    
    
    String title = "";
    String description = "";
    String category = "";
    String keywords = "";
    String privacy = "";
    JSONObject information = usrResp.getJSONObject("entry");
    JSONArray accessControl = null;
    
    if(!information.isNull("yt$accessControl")){
        accessControl = information.getJSONArray("yt$accessControl");
    }
    
    if(!information.isNull("title")){
        title = information.getJSONObject("title").getString("$t");
        title = SWBUtils.TEXT.encode(title, "UTF-8");
    }
    if(!information.isNull("media$group")){
        if(!information.getJSONObject("media$group").isNull("media$description")){
            if(!information.getJSONObject("media$group").getJSONObject("media$description").isNull("$t")){
                description = information.getJSONObject("media$group").getJSONObject("media$description").getString("$t");                
                description = SWBUtils.TEXT.encode(description, "UTF-8");
            }
        }
        
        if(!information.getJSONObject("media$group").isNull("media$category")){
            if(!information.getJSONObject("media$group").getJSONArray("media$category").getJSONObject(0).isNull("$t")){
                category = information.getJSONObject("media$group").getJSONArray("media$category").getJSONObject(0).getString("$t");
                category = SWBUtils.TEXT.encode(category, "UTF-8");
            }
        }
        
        if(!information.getJSONObject("media$group").isNull("media$keywords")){
            if(!information.getJSONObject("media$group").getJSONObject("media$keywords").isNull("$t")){
                keywords = information.getJSONObject("media$group").getJSONObject("media$keywords").getString("$t");
                keywords = SWBUtils.TEXT.encode(keywords, "UTF-8");
            }
        }
        if(!information.getJSONObject("media$group").isNull("yt$private")){//If video is private
            privacy = "PRIVATE";
        }else{//If is not private check if public or unlisted
            for(int i = 0; i < accessControl.length(); i++){
                JSONObject control = accessControl.getJSONObject(i);
                if(!control.isNull("action") && !control.isNull("permission")){
                    if(control.getString("action").equalsIgnoreCase("list")){
                        if(control.getString("permission").equalsIgnoreCase("allowed")){
                            privacy = "PUBLIC";
                        }else if(control.getString("permission").equalsIgnoreCase("denied")){
                            privacy = "NOT_LISTED";
                        }
                    }
                }
            }
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
        <legend><%=SWBUtils.TEXT.encode(paramRequest.getLocaleString("title"), "UTF-8")%>:</legend>
        <div align="left">
            <input type="text" required="true" onblur="this.value=dojo.trim(this.value);" name="title" size="67" value="<%=title%>"/>
        </div>
    </fieldset>
    
    <fieldset>
        <legend><%=SWBUtils.TEXT.encode(paramRequest.getLocaleString("description"), "UTF-8")%>:</legend>
        <div align="left">
            <textarea rows="5" cols="50" name="description"><%=description%></textarea>
        </div>
    </fieldset>
    
    <fieldset>
        <legend><%=SWBUtils.TEXT.encode(paramRequest.getLocaleString("videoTags"), "UTF-8")%>:</legend>
        <div align="left">
            <input type="text" name="keywords" size="67" value="<%=keywords%>"/>
        </div>
    </fieldset>
    
    <fieldset>
        <legend><%=SWBUtils.TEXT.encode(paramRequest.getLocaleString("category"), "UTF-8")%>:</legend>
        <div align="left">
            <select name="category">
                <%
                        SWBModel model = WebSite.ClassMgr.getWebSite(paramRequest.getWebPage().getWebSiteId());
                        Iterator<YouTubeCategory> itYtube = YouTubeCategory.ClassMgr.listYouTubeCategories(model);
                        while (itYtube.hasNext()) {
                            YouTubeCategory socialCategory = (YouTubeCategory) itYtube.next();
                %>
                    <option <%=category.equals(socialCategory.getId()) ? " selected " : ""%>value="<%=socialCategory.getId()%>"><%=SWBUtils.TEXT.encode(socialCategory.getTitle(), "UTF-8")%></option>
                <%
                    }
                %>
            </select>
        </div>
    </fieldset>
    <fieldset>
        <legend><%=SWBUtils.TEXT.encode(paramRequest.getLocaleString("privacy"), "UTF-8")%>:</legend>
        <div align="left">
            <select name="privacy">
                <%
                    Iterator <PostOutPrivacy> postOutPs = PostOutPrivacy.ClassMgr.listPostOutPrivacies();
                    while(postOutPs.hasNext()){
                        PostOutPrivacy postOutP = postOutPs.next();
                        Iterator<SemanticObject> nets = postOutP.listNetworkTypes();
                        while(nets.hasNext()){
                            SemanticObject semObjNetw=nets.next(); 
                            SemanticClass sClass=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(semObjNetw.getURI());
                            if(sClass.equals(Youtube.social_Youtube)){
                                %>
                                <option value="<%=postOutP.getId()%>" <%=postOutP.getId().equals(privacy) ? "selected" : ""%>><%=postOutP.getTitle()%></option>
                                <%
                            }
                        }
                    }
                %>
            </select>
        </div>
    </fieldset>
            <div align="center"><button dojoType="dijit.form.Button" type="submit"><%=paramRequest.getLocaleString("update")%></button></div>
    </form>
</div>

<%  
%>