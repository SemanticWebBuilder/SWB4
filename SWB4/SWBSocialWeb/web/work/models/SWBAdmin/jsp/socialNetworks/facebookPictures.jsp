<%-- 
    Document   : facebookPictures
    Created on : 7/06/2013, 06:54:00 PM
    Author     : francisco.jimenez
--%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="org.semanticwb.social.Facebook"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="java.io.Writer"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>

<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<jsp:useBean id="facebookBean" scope="request" type="org.semanticwb.social.Facebook"/>
<%@page import="static org.semanticwb.social.admin.resources.FacebookWall.*"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<!DOCTYPE html>

<%!
/*
    public static String getTagsFromPost(JSONObject objectTags, String postContent, SWBResourceURL renderURL){
        String postContentWithUrl = postContent;
        Iterator<?> keyTags = objectTags.keys();
        try{
            while( keyTags.hasNext() ){
                String key = (String)keyTags.next();
                if( objectTags.get(key) instanceof JSONArray ){
                    JSONArray tag = objectTags.getJSONArray(key);
                    String userUrl = "";
                    userUrl = "<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", tag.getJSONObject(0).getString("type")).setParameter("id", tag.getJSONObject(0).getLong("id")+"") + "','" + tag.getJSONObject(0).getString("name") + "'); return false;\">" + tag.getJSONObject(0).getString("name") + "</a>";
                    postContentWithUrl = postContentWithUrl.replace(tag.getJSONObject(0).getString("name"), userUrl);
                }
            }
        }catch(JSONException jSonException){
            System.out.println("Problem parsing associated users");
        }
        return postContentWithUrl;    
    }
    
    public static String getTagsFromPostArray(JSONObject objectTags, String postContent, SWBResourceURL renderURL){
        String postContentWithUrl = postContent;
        try{
                    String userUrl = "";
                    userUrl = "<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", objectTags.getString("type")).setParameter("id", objectTags.getLong("id")+"") + "','" + objectTags.getString("name") + "'); return false;\">" + objectTags.getString("name") + "</a>";
                    postContentWithUrl = postContentWithUrl.replace(objectTags.getString("name"), userUrl);

        }catch(JSONException jSonException){
            System.out.println("Problem parsing associated users");
        }
        return postContentWithUrl;    
    }

    public static String picture(String response, Writer out, boolean includeSinceParam, HttpServletRequest request, SWBParamRequest paramRequest, SWBModel model) {
        
        String  createdTime="";
        
        String objUri = (String) request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook)semanticObject.createGenericInstance();
        
        try {
            JSONObject phraseResp = new JSONObject(response);
            System.out.println("Tamanio del arreglo:" + phraseResp.length());                       
            int cont = 0;
                        
            JSONArray postsData = null;
            JSONArray userData = null;
            JSONArray pageData = null;

            for(int i = 0; i < phraseResp.getJSONArray("data").length(); i++){
                if(phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("pictures")){//All the posts
                    postsData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                }else if(phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("pages")){//All the pages
                    pageData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                }else if(phraseResp.getJSONArray("data").getJSONObject(i).getString("name").equals("usernames")){//All the users
                    userData = phraseResp.getJSONArray("data").getJSONObject(i).getJSONArray("fql_result_set");
                }
            }

            for (int k = 0; k < postsData.length(); k++) {
                cont++;
                JSONObject profileID = null;
                JSONObject postComments = null;
                for(int userCount = 0 ; userCount < userData.length(); userCount++){
                    if(userData.getJSONObject(userCount).getLong("uid") == postsData.getJSONObject(k).getLong("actor_id")){
                        profileID = userData.getJSONObject(userCount);                        
                        break;
                    }
                }
                if(profileID == null){ //If the 'id' was not found inside the Users, checkPages
                    for(int pageCount = 0 ; pageCount < pageData.length(); pageCount++){
                        if(pageData.getJSONObject(pageCount).getLong("page_id") == postsData.getJSONObject(k).getLong("actor_id")){
                            profileID = pageData.getJSONObject(pageCount);
                            break;
                        }
                    }
                }
                if(profileID == null){
                    System.out.println("\n\n\n\nTHIS IS NOT SUPOSSED TO HAPPEN!!!!!!!!!!!!!!!!!" + postsData.getJSONObject(k).getLong("actor_id")  );
                    return null;
                }
                
                if(postsData.getJSONObject(k).has("comment_info")){
                    if(postsData.getJSONObject(k).getJSONObject("comment_info").has("comment_count")){
                        if(postsData.getJSONObject(k).getJSONObject("comment_info").getLong("comment_count") > 0){//If post has are comments
                            //make te request for comments of the current post
                            HashMap<String, String> params = new HashMap<String, String>(3);
                            params.put("access_token", facebook.getAccessToken());
                            params.put("limit", "5");
                            try{
                                String fbResponse = postRequest(params, "https://graph.facebook.com/" + postsData.getJSONObject(k).getString("post_id") +"/comments",
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
                                postComments = new JSONObject(fbResponse);
                            }catch(Exception e){
                                System.out.println("Error getting comments of post "+ e);
                            }
                        }
                    }
                }
                
                createdTime = printPicture(out,  postsData.getJSONObject(k), postComments, profileID, request, paramRequest, MEDIA_TAB, facebook, model);
                
                //Only include the param in session when the page loads the first time and when 
                if(includeSinceParam && k==0){//Only save the most recent picture id(the first), then use this id to ask if new pictures available
                    HttpSession session = request.getSession(true);
                    System.out.println("\n\n\n\t\tReemplazando viejo parametro PICTURE:" + session.getAttribute(objUri + MEDIA_TAB + "since"));
                    session.setAttribute(objUri + MEDIA_TAB + "since", createdTime);
                }
            }
                        
            System.out.println("TOTAL PICTURE POSTS RECEIVED:" + postsData.length());
        } catch (Exception jsone) {
            System.out.println("Problemas al parsear respuesta de Facebook" + jsone);
        }
        return createdTime;
    }

    public static String printPicture(Writer writer, JSONObject postsData, JSONObject commentsData, JSONObject profileData, HttpServletRequest request, SWBParamRequest paramRequest, String tabSuffix, Facebook facebook, SWBModel model){
        String createdTime="";
        
        try{
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            actionURL.setParameter("suri",request.getParameter("suri"));
            
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            renderURL.setParameter("suri",request.getParameter("suri"));
            
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");    
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
            long id=0L;
            if(profileData.has("uid")){
                id = profileData.getLong("uid");
            }else if(profileData.has("page_id")){
                id = profileData.getLong("page_id");
            }else{
                return null;
            }
            
            //TODO: id = A 64-bit int representing the user, group, page, event, or application ID
            JSONArray media = postsData.getJSONObject("attachment").getJSONArray("media");
            
            writer.write("<fieldset>");
            writer.write("<table style=\"width: 100%; border: 0px\">");
            writer.write("<tr>");
            writer.write("   <td colspan=\"2\">");
            writer.write("      <a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", id+"") + "','" + profileData.getString("name") + "'); return false;\">" + profileData.getString("name") + "</a>");
            writer.write("   </td>");
            writer.write("</tr>");

            writer.write("<tr>");
            writer.write("   <td  width=\"10%\">"); 
            writer.write("      <a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", id+"") + "','" + profileData.getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + id +"/picture\"/></a>");
            writer.write("   </td>");
            writer.write("   <td width=\"90%\">");
            if(postsData.getInt("type") == 247){//Photo posted
                writer.write(postsData.getString("message"));
                //writer.write(":Photos posted");
            }else if(postsData.getInt("type") == 65){//Tagged photo
                if(!postsData.isNull("description_tags")){//Users tagged in story
                    JSONObject descriptionData = postsData.optJSONObject("description_tags");
                    if(descriptionData != null){
                        writer.write(getTagsFromPost(postsData.getJSONObject("description_tags"), postsData.getString("description"), renderURL));
                    }else{
                        writer.write(getTagsFromPostArray(postsData.getJSONArray("description_tags").getJSONArray(0).getJSONObject(0), postsData.getString("description"), renderURL));
                    }
                }
                //writer.write(":Tagged photo");
            }else if(postsData.getInt("type") == 373){//Cover update                
                writer.write(profileData.getString("name") + " has updated cover photo");
                //writer.write(":updated cover photo");
            }else{
                writer.write("&nbsp;");
            }
            
            if(!postsData.getJSONObject("attachment").isNull("fb_object_type") && postsData.getJSONObject("attachment").getString("fb_object_type").equals("album")){
                //writer.write(postsData.getString("description"));
                if(!postsData.getJSONObject("attachment").getString("name").isEmpty())
                    writer.write(profileData.getString("name") + " has added " + media.length() + " photos to the album " + postsData.getJSONObject("attachment").getString("name") );
            }
            
            writer.write("  </td>");
            writer.write("</tr>");
            //White space at left
            writer.write("<tr>");
            writer.write("   <td  width=\"10%\">"); 
            writer.write("  &nbsp;");
            writer.write("   </td>");
            
            writer.write("   <td  width=\"90%\">"); 
            writer.write("   <table>");
            writer.write("   <tr>");
            writer.write("   <td rowspan=\"3\">");                       
            //for (int k = 0; k < media.length(); k++) {
            for (int k = 0; k < 1; k++) {
                //writer.write("   <td  width=\"5%\">"); 
                writer.write("      <div id=\"img" + facebook.getId() + media.getJSONObject(k).getString("src") + "\" style=\"width: 150px; height: 150px; border: thick #666666; overflow: hidden; position: relative;\">");
                writer.write("      <a href=\"#\" onclick=\"showDialog('" + renderURL.setMode("displayPicture").setParameter("pictureUrl", media.getJSONObject(k).getString("src").replace("_s.", "_n.")) +
                        "','Picture from " + "" + "'); return false;\"><img src=\"" + media.getJSONObject(k).getString("src") + "\" style=\"position: relative;\" onload=\"imageLoad(" + "this, 'img" + facebook.getId() + media.getJSONObject(k).getString("src") + "');\"/></a>");
                writer.write("      </div>");
                //writer.write("   </td>"); 
                //writer.write("<img src=\"" + media.getJSONObject(k).get("src") + "\"/>");                
            }
            writer.write("   </td>");
            writer.write("</tr>");
            
            writer.write("<tr>");
            writer.write("  <td>");
            writer.write(" &nbsp;");
            writer.write("  </td>");
            writer.write("</tr>"); 
            
            writer.write("<tr>");
            writer.write("  <td>");
            writer.write(postsData.isNull("description") ? "" : postsData.getString("description"));
            writer.write("  </td>");
            writer.write("</tr>"); 
                
            writer.write("</table>");
            writer.write("   </td>");
            writer.write("</tr>");
            
            
            //Comments,start
            if(commentsData != null){
                if(commentsData.has("data") && commentsData.getJSONArray("data").length() > 0 ){
                    writer.write("<tr>");
                    writer.write("   <td  width=\"10%\">"); 
                    writer.write("      &nbsp;");
                    writer.write("   </td>");
                    writer.write("   <td width=\"90%\">");

                    JSONArray comments = commentsData.getJSONArray("data");
                    if(comments.length()>0)
                    writer.write("<table>");
                    for (int k = 0; k < comments.length(); k++){
                        writer.write("<tr>");
                        writer.write("  <td rowspan=\"3\">");
                        JSONObject commentProfile = comments.getJSONObject(k).getJSONObject("from");
                        writer.write("      <a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", commentProfile.getLong("id")+"") + "','" + commentProfile.getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + commentProfile.getLong("id") +"/picture?width=30&height=30\"/></a>");
                        writer.write("  </td>");
                        writer.write("</tr>");
                        
                        writer.write("<tr>");
                        writer.write("  <td>");
                        writer.write("      <b><a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", commentProfile.getLong("id")+"") + "','" + commentProfile.getString("name") + "'); return false;\">" + commentProfile.getString("name") + "</a></b>:");
                        writer.write(       comments.getJSONObject(k).getString("message").replace("\n", "</br>") + "</br>");
                        writer.write("  </td>");
                        writer.write("</tr>");

                        Date commentTime = formatter.parse(comments.getJSONObject(k).getString("created_time"));
                        
                        writer.write("<tr>");
                        writer.write("<td>");
                        writer.write("<div id=\"" + comments.getJSONObject(k).getString("id") + "\" dojoType=\"dojox.layout.ContentPane\">");
                        writer.write(facebookHumanFriendlyDate(commentTime));
                        //writer.write("<a href=\"\" onMouseOver=\"dijit.Tooltip.defaultPosition=['above', 'below']\" id=\"TooltipButton\" onclick=\"return false;\"> LIKE/UNLIKE</a>");
                        //writer.write("<div class=\"dijitHidden\"><span data-dojo-type=\"dijit.Tooltip\" data-dojo-props=\"connectId:'TooltipButton'\">I am <strong>above</strong> the button</span></div>");
                        if(comments.getJSONObject(k).has("like_count")){
                            writer.write(" Likes: " + comments.getJSONObject(k).getInt("like_count") );
                        }
                        writer.write("</div>");
                        //writer.write("<div dojoType=\"dijit.Tooltip\" connectId=\"ss\" position=\"below\">");
                        writer.write("</td>");
                        writer.write("</tr>");
                        //writer.write("<div style=\"float:left\"><b>" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "</b>: </div>");
                        //writer.write(comments.getJSONObject(k).getString("message").replace("\n", "</br>") + "</br><span>Hora y Fecha</span></br></br>");                        
                    }
                    if(comments.length()>0)
                    writer.write("   </table>");

                    if(commentsData.has("paging")){//Link to get more comments
                    JSONObject pagingComments = commentsData.getJSONObject("paging");

                    if(pagingComments.has("next") && pagingComments.has("cursors")){
                        writer.write("<div align=\"left\" id=\"" + facebook.getId() + postsData.getString("post_id") + tabSuffix + "/comments\" dojoType=\"dojox.layout.ContentPane\">");
                        SWBResourceURL commentsURL = paramRequest.getRenderUrl().setMode("moreComments").setParameter("suri", request.getParameter("suri")).setParameter("postId", postsData.getString("post_id"));
                        commentsURL = commentsURL.setParameter("after", pagingComments.getJSONObject("cursors").getString("after")).setParameter("currentTab", tabSuffix);
                        writer.write("<label><a href=\"#\" onclick=\"appendHtmlAt('" + commentsURL
                                + "','" + facebook.getId() +  postsData.getString("post_id") + tabSuffix +"/comments', 'bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;\">View more comments</a></label>");
                        writer.write("</div>");                            
                    }
                    }
                    writer.write("   </td>");
                    writer.write("</tr>");
                }
            }
            
            
            
            writer.write("<tr>");
            writer.write("   <td colspan=\"2\">");
            ///////writer.write("<div id=\"" + postsData.getString("id") + postsData.getString("created_time") + "\" dojoType=\"dijit.layout.ContentPane\">");
            //writer.write("<div id=\"" + postsData.getString("id") + "\" dojoType=\"dijit.layout.ContentPane\">");
            
            Date postTime = new java.util.Date((long)postsData.getLong("created_time")*1000);
            createdTime = String.valueOf(postsData.getLong("created_time"));
            writer.write("<div dojoType=\"dijit.layout.ContentPane\">");
            
            writer.write("   <span class=\"inline\" id=\"" + facebook.getId() +  postsData.getString("post_id") + INFORMATION + MEDIA_TAB + "\" dojoType=\"dojox.layout.ContentPane\">");
            writer.write(facebookHumanFriendlyDate(postTime) + "->" +postsData.getLong("created_time"));

            if(postsData.has("like_info")){
                JSONObject likeInfo = postsData.getJSONObject("like_info");
                writer.write(" Likes: <b>");
                writer.write(likeInfo.getLong("like_count")+"");
                writer.write("</b>");
                writer.write("   </span>");
                
                writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("post_id") + LIKE + MEDIA_TAB + "\" dojoType=\"dojox.layout.ContentPane\">");                        
                if(likeInfo.getBoolean("can_like")){
                    if(likeInfo.getBoolean("user_likes")){
                        writer.write(" <a href=\"\"  onclick=\"postSocialHtml('" + actionURL.setAction("doUnlike").setParameter("commentID", postsData.getString("post_id")).setParameter("currentTab", MEDIA_TAB) + "','" + facebook.getId() + postsData.getString("post_id") + INFORMATION + MEDIA_TAB + "');return false;" +"\">Unlike</a>");
                    }else{
                        writer.write(" <a href=\"\"  onclick=\"postSocialHtml('" + actionURL.setAction("doLike").setParameter("commentID", postsData.getString("post_id")).setParameter("currentTab", MEDIA_TAB) + "','" + facebook.getId() + postsData.getString("post_id") + INFORMATION + MEDIA_TAB + "');return false;" +"\">Like</a>");
                    }
                }
                writer.write("   </span>");
            }else{
                writer.write("   </span>");
            }
            
            if(postsData.has("comment_info")){
                JSONObject comments = postsData.getJSONObject("comment_info");
                
                if(comments.getBoolean("can_comment")){
                    writer.write("   <span class=\"inline\" id=\"" + postsData.getString("post_id") + REPLY + MEDIA_TAB + "\" dojoType=\"dojox.layout.ContentPane\">");
                        writer.write(" <a href=\"\" onclick=\"showDialog('" + renderURL.setMode("replyPost").setParameter("commentID", postsData.getString("post_id")) + "','Reply to " + "" + "');return false;\">Reply</a>  ");
                    writer.write("   </span>");

                    ///////////////////////If I can post I can Classify it to answer it later
                    PostIn post = PostIn.getPostInbySocialMsgId(model, postsData.getString("post_id"));
                    writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("post_id") + TOPIC + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                    if(post != null){
                        SWBResourceURL clasifybyTopic = renderURL.setMode("doReclassifyTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", postsData.getString("post_id")).setParameter("postUri", post.getURI()).setParameter("currentTab", tabSuffix);
                            writer.write("<a href=\"#\" title=\"" + "Tema actual: " +  post.getSocialTopic().getTitle() + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                            + "Reclasificar post'); return false;\">Reclasificar(" + post.getSocialTopic().getTitle()+ ")</a>");
                    }else{
                        SWBResourceURL clasifybyTopic = renderURL.setMode("doShowTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", postsData.getString("post_id")).setParameter("currentTab", tabSuffix);
                        writer.write("<a href=\"#\" title=\"" + "Clasificar" + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                        + "Clasificar Post'); return false;\">Clasificar</a>");
                    }
                    writer.write("   </span>");
                    
                }
            }
                   
            writer.write("   </div>");
            writer.write("   </td>");

            writer.write("</tr>");
            writer.write("</table>");
            writer.write("</fieldset>");               
        }catch(Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
        return createdTime;
    }
    
    public static String facebookHumanFriendlyDate(Date created){
        Date today = new Date();
        Long duration = today.getTime() - created.getTime();

        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;
        String date = "";

        if (duration < second * 7) {//Less than 7 seconds
            date = "right now";
        }else if (duration < minute) {
            int n = (int) Math.floor(duration / second);
            date = n + " seconds ago";
        }else if (duration < minute * 2) {//Less than 2 minutes
            date = "about 1 minute ago";
        }else if (duration < hour) {
            int n = (int) Math.floor(duration / minute);
            date = n + " minutes ago";
        }else if (duration < hour * 2) {//Less than 1 hour
            date = "about 1 hour ago";
        }else if (duration < day) {
            int n = (int) Math.floor(duration / hour);
            date = n + " hours ago";
        }else  if (duration > day && duration < day * 2) {
            date = "yesterday";
        }else{
            int n = (int) Math.floor(duration / day);
            date = n + " days ago";
        }
        return date;
    }
    
    //Only need the id, name and user type from 'id' 
    //The ID of the user, page, group, or event that published the post
    public static String getProfileFromId(String id, Facebook facebook){
        HashMap<String, String> params1 = new HashMap<String, String>(3);    
        params1.put("q", "SELECT id, name, type FROM profile where id = " + id);        
        params1.put("access_token", facebook.getAccessToken());
    
        String fbResponse = null;
        try{
         fbResponse = getRequest(params1, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

        }catch(Exception e){
            System.out.println("Error getting user information"  + e.getMessage());
        }
        return fbResponse;
    }
*/
%>

<%
    try{
        String objUri = (String) request.getParameter("suri");
        String username;
        HashMap<String, String> params = new HashMap<String, String>(3);
        params.put("access_token", facebookBean.getAccessToken());
        String user = postRequest(params, "https://graph.facebook.com/me",
                                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        System.out.println("user:"+ user);
        JSONObject userObj = new JSONObject(user);
        if(!userObj.isNull("name")){
            username = userObj.getString("name");
        }else{
            username = facebookBean.getTitle();
        }
%>
<div class="swbform">
<div align="center"><h2><%=username%> Pictures.</h2><br/></div>
<div class="bar" id="<%=objUri%>newPicturesAvailable" dojoType="dojox.layout.ContentPane"></div>
<div id="<%=objUri%>picturesStream" dojoType="dojox.layout.ContentPane"></div>
<%
    System.out.println("SURI jsp: " + paramRequest.getMode().toString());
    SWBModel model=WebSite.ClassMgr.getWebSite(facebookBean.getSemanticObject().getModel().getName());

    params.put("q", "{\"pictures\": \"SELECT actor_id, created_time, like_info, post_id, attachment, message, description, description_tags, type, comment_info FROM stream WHERE filter_key IN " + 
                "( SELECT filter_key FROM stream_filter WHERE uid = me() AND name = 'Photos') ORDER BY created_time DESC LIMIT 50\", \"usernames\": \"SELECT uid, name FROM user WHERE uid IN (SELECT actor_id FROM #pictures)\", \"pages\":\"SELECT page_id, name FROM page WHERE page_id IN (SELECT actor_id FROM #pictures)\"}");
    
    //params1.put("access_token", "CAACEdEose0cBAKyWLxR6XedK1KrfMDVmqUQshOoZA2vGCnuqIyrekZCGQ9HZBc0FWKIXfNMexJGxxinNvtcvEnHGkBLpCCmEuPVgmUAddZCxcDWc1KigZCrYaDCSSoEUHIhda1G3y4tCZBq4ripHKZAw1steVi0NGYZD");    
    String fbResponse = getRequest(params, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
    String createdTime = picture(fbResponse, out, true, request, paramRequest, model);//Gets the newest post and saves the ID of the last one
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setParameter("suri", objUri).setParameter("currentTab", MEDIA_TAB);
    
    /*
I have listed down type codes I have found:
11 - Group created.
12 - Event created.
46 - Status update.
56 - Post on Timeline from another user.
60 - changed profile picture.
65 - Tagged photo.
66 - Note created.
80 - Link posted ( shared Photo/Link).
128 - Video posted.
161 - Likes*.
245 - Likes a photo/Link*.
247 - Photos posted.
237 - App/Games story.
257 - Comment created.
272 - App story.
283 - Likes a Link*. 
285 - Checkin to a place.
295 - Post to Timeline of a friend from other friend.
308 - Post in Group.
347 - Likes a Link*.
373 - updated cover photo.
    */
%>
<div id="<%=objUri%>getMorePictures" dojoType="dijit.layout.ContentPane">
    <div align="center">
        <label id="<%=objUri%>morePicturesLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMorePictures").setParameter("createdTime", createdTime).setParameter("currentTab", MEDIA_TAB)%>','<%=objUri%>getMorePictures', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;">More pictures</a></label>
    </div>
</div>
</div>
<%
        }catch(Exception e){
        out.print("Problem displaying Facebook Pictures: " + e.getMessage());
    }
%>