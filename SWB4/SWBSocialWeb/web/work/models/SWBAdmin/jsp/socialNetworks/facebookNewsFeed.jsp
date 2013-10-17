<%-- 
    Document   : facebookNewsFeed
    Created on : 26/06/2013, 04:34:46 PM
    Author     : francisco.jimenez
--%>

<%@page import="org.semanticwb.social.util.SWBSocialUtil"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBModel"%>
<%@page import="org.semanticwb.social.Facebook"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.Reader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="java.io.Writer"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="java.util.HashMap"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<jsp:useBean id="facebookBean" scope="request" type="org.semanticwb.social.Facebook"/>
<%@page import="static org.semanticwb.social.admin.resources.FacebookWall.*"%>

<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>

<style type="text/css">
    div.bar{
      background-color: #F5F5F5;
      border-top: 1px solid #DDDDDD;
      box-shadow: 0 3px 8px rgba(0, 0, 0, 0.05) inset;
      cursor: pointer;
      display: block;
      font-size: 13px;
      font-weight: normal;
      padding: 10px 1px;
      position: relative;
      text-align: center;          
    }
    span.inline { display:inline; }
</style>    
<%!
/*
    public static JSONObject getPostFromId(String postId, String fields, Facebook facebook){
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", facebook.getAccessToken());
        if(fields != null){
            params.put("fields", fields);
        }
        
        JSONObject jsonObject = null;
        try{
            String fbResponse = postRequest(params, "https://graph.facebook.com/" + postId.substring(postId.lastIndexOf("_") +1),
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
            if(!fbResponse.isEmpty()){
                jsonObject = new JSONObject(fbResponse);                
            }
        }catch(IOException ieo){
            System.out.println("Error getting post that user liked:" + ieo.getMessage());
        }catch(JSONException jsone){
            System.out.println("Error parsing information from string recieved:" + jsone.getMessage());
        }
        return jsonObject;
    }

    public static String parseResponse(String response, Writer out, boolean includeSinceParam, HttpServletRequest request, SWBParamRequest paramRequest, String tabSuffix, SWBModel model) {
        
        String  until="";
        String  since="";
        String objUri = (String) request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook)semanticObject.createGenericInstance();
                
        try {
                JSONObject phraseResp = new JSONObject(response);
                System.out.println("Tamanio del arreglo:" + phraseResp.length());                       
                    int cont = 0;
                    JSONArray postsData = phraseResp.getJSONArray("data");
                    System.out.println("ARREGLO DE DATOS:" + postsData.length());
                    for (int k = 0; k < postsData.length(); k++) {
                        cont++;
                        //System.out.println("\n\nPost de FACEBOOOK*********: " + postsData.getJSONObject(k));
                        doPrintPost(out,  postsData.getJSONObject(k), request, paramRequest, tabSuffix, facebook, model);
                    }
                    if(phraseResp.has("paging")){
                        JSONObject pagingData = phraseResp.getJSONObject("paging");
                        String nextPage = pagingData.getString("next"); // get until param to get OLDER posts
                        Pattern pattern = Pattern.compile("until=[0-9]+");
                        Matcher matcher = pattern.matcher(nextPage);
                        String untilParam = "";
                        if(matcher.find()){
                            untilParam = matcher.group();
                        }
                        if(!untilParam.isEmpty()){
                            until = untilParam.substring(untilParam.indexOf("=") + 1);//gets only the value of until param in paging object
                        }
                        if(includeSinceParam){//Include value of since param when the tab is loaded and when GetNewPost link is clicked
                            String previousPage = pagingData.getString("previous"); // get since param to get NEWER posts
                            pattern = Pattern.compile("since=[0-9]+");
                            matcher = pattern.matcher(previousPage);
                            String sinceParam = "";
                            
                            if(matcher.find()){
                                sinceParam = matcher.group();
                            }
                            if(!sinceParam.isEmpty()){
                                since = sinceParam.substring(sinceParam.indexOf("=") + 1);//gets only the value of since param in paging object
                                HttpSession session = request.getSession(true);
                                System.out.println("\n\n\n\t\tReemplazando viejo parametro FEED:" + session.getAttribute(objUri + tabSuffix + "since"));
                                session.setAttribute(objUri + tabSuffix + "since", since);
                            }
                        }
                    }
        } catch (JSONException jsone) {
            
            System.out.println("Problemas al parsear respuesta de Facebook" + jsone);
        }
        return until;
    }
    
    
    public static String getTagsFromPost(JSONObject objectTags, String postContent, SWBResourceURL renderURL){
        String postContentWithUrl = postContent;
        Iterator<?> keyTags = objectTags.keys();
        try{
            while( keyTags.hasNext() ){
                String key = (String)keyTags.next();
                if( objectTags.get(key) instanceof JSONArray ){
                    JSONArray tag = objectTags.getJSONArray(key);
                    String userUrl = "";
                    if(tag.getJSONObject(0).has("type")){
                        userUrl = "<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", tag.getJSONObject(0).getString("type")).setParameter("id", tag.getJSONObject(0).getLong("id")+"") + "','" + tag.getJSONObject(0).getString("name") + "'); return false;\">" + tag.getJSONObject(0).getString("name") + "</a>";
                    }else{
                        userUrl = "<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", tag.getJSONObject(0).getLong("id")+"") + "','" + tag.getJSONObject(0).getString("name") + "'); return false;\">" + tag.getJSONObject(0).getString("name") + "</a>";
                    }
                    //userUrl = "<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", tag.getJSONObject(0).getString("type")).setParameter("id", tag.getJSONObject(0).getLong("id")+"") + "','" + tag.getJSONObject(0).getString("name") + "'); return false;\">" + tag.getJSONObject(0).getString("name") + "</a>";
                    postContentWithUrl = postContentWithUrl.replace(tag.getJSONObject(0).getString("name"), userUrl);
                }
            }
        }catch(JSONException jSonException){
            System.out.println("Problem parsing associated users:" + objectTags + "\n\n" + postContent);
            jSonException.printStackTrace();
            return postContent;
        }
        return postContentWithUrl;    
    }
    
    public static void doPrintPost(Writer writer, JSONObject postsData, HttpServletRequest request, SWBParamRequest paramRequest, String tabSuffix, Facebook facebook, SWBModel model){
        try{
            SWBResourceURL actionURL = paramRequest.getActionUrl();                        
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            actionURL.setParameter("suri",request.getParameter("suri"));
            renderURL.setParameter("suri",request.getParameter("suri"));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");    
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
            String postType = postsData.getString("type");

            String story = "";
            String message = "";
            String caption = "";
            boolean isAPhotoLike = false;
            boolean isALinkLike = false;
            boolean isAPostLike = false;
            boolean isAppCreated = false;
            boolean isAStatusLike = false;
            boolean isFoursquare = false;
            //TODO: FALTA COMMENTED ON A PHOTO
            JSONObject postLike = null;
            JSONObject photoLike = null;
            JSONObject linkLike = null;
            JSONObject applicationCreated = null;
            JSONObject foursquareLink = null;
            JSONObject statusLike = null;
            
            
            
            if(postsData.isNull("actions")){//You can't like or respond this post
                return;                     //Must NOT be shown in wall
            }
            
            //If the posts is empty and is application-created don't show it
            if(postsData.isNull("message") && postsData.isNull("story") && postsData.isNull("name") && postsData.isNull("picture") 
                    && postsData.isNull("link") && postsData.isNull("description") && !postsData.isNull("application")){
                return;
            }
            
            if(postType.equals("photo")){
                if(!postsData.isNull("story")){
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "" ;
                    if(!postsData.isNull("story_tags")){//Users tagged in story
                        story = getTagsFromPost(postsData.getJSONObject("story_tags"), story, renderURL);
                    }
                }

                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        message = getTagsFromPost(postsData.getJSONObject("message_tags"), message, renderURL);
                    }
                }
                if(!postsData.isNull("caption")){
                    caption = postsData.getString("caption").replace("\n", "</br>");
                }
                if(postsData.has("application")){
                    if(postsData.getJSONObject("application").getString("name").equals("Foursquare")){
                        return;
                        //foursquareLink = getPostFromId(postsData.getString("id"), null, facebook);
                        //isFoursquare = true;
                        //System.out.println("\n\n\nFOURSQUARE CREATED:" +  foursquareLink);
                        //message = "Checked in";
                        
                    }
                }
                //Story or message or both!!
                //or "status_type": "shared_story", tagged_in_photo
                
            }else if(postType.equals("link")){
                //"status_type": "app_created_story",
                if(!postsData.isNull("story")){
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "" ;
                    if(!postsData.isNull("story_tags")){//Users tagged in story
                        story = getTagsFromPost(postsData.getJSONObject("story_tags"), story, renderURL);
                    }
                    if(story.contains("is going to an event") && postsData.has("link")){//If the link is an event
                        return;
                        //message = "<a href=\"" + postsData.getString("link") + "\" target=\"_blank\">View event</a>";
                    }
                }
                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        message = getTagsFromPost(postsData.getJSONObject("message_tags"), message, renderURL);
                    }
                }

                if(postsData.has("application")){
                    //return;
                    
                    //if(postsData.getJSONObject("application").getString("name").equals("Instagram")){
//                        applicationCreated = getPostFromId(postsData.getString("id"), null, facebook);
////                        isAppCreated = true;
//                        System.out.println("\n\n\nAPPLICATION CREATED:" +  applicationCreated);
//                        message = "Liked a picture in Instagram";
//                    }
                }
            }else if(postType.equals("status")){
                
                if(postsData.has("story")){//Do not print the posts when 'User X likes a post'
                    if(postsData.getString("story").contains("likes a post")){
                        return;
                    }
                }
                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = getTagsFromPost(storyTags, message, renderURL);
                    }
                }else if(!postsData.isNull("story")){
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "" ;
                    if(!postsData.isNull("story_tags")){//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("story_tags");
                        story = getTagsFromPost(storyTags, story, renderURL);
                    }
                    if(story.contains("likes a photo")){
                        //photoLike = getPostFromId(postsData.getString("id"), "id,from,name,name_tags,picture,source,link,tags", facebook);
                        //isAPhotoLike = true;
                        return;
                        //System.out.println("THE RECOVERED OBJECT:" + jSONObject);
                    }else if(story.contains("likes a link")){//Do not print the posts when 'User X likes a link'
                        //linkLike = getPostFromId(postsData.getString("id"), "id,from,name,picture,link,tags,message", facebook);
                        //System.out.println("\n\n\nLINKED LIKED:" +  linkLike);
                        //isALinkLike = true;
                        return;
                    }else if(story.contains("likes a status")){
                        
                        //statusLike = getPostFromId(postsData.getString("id"), null, facebook);
                        //isAStatusLike = true;
                        //System.out.println("\n\n\nSTATUS LIKED:" +  statusLike);
                        //if(statusLike.has("message")){
//                            message = statusLike.getString("message");
                        //}
                        return;
                    }else if(story.contains("commented on")){
                        return;
                    }else if(story.contains("likes")){//USER likes PAGE
                        return;
                    }else if(story.contains("is going to")){//events
                        return;
                    }
                }else{
                     return;
                }
            }else if(postType.equals("video")){
                if(!postsData.isNull("message")){
                    message = postsData.getString("message") + " THIS IS A VIDEO!!";
                }
            }else if(postType.equals("checkin")){
                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = getTagsFromPost(storyTags, message, renderURL);
                    }
                }            
            }else if(postType.equals("swf")){
                if(!postsData.isNull("message")){
                    message = postsData.getString("message");
                    if(!postsData.isNull("message_tags")){//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = getTagsFromPost(storyTags, message, renderURL);
                    }
                }            
            }
            
            if(postsData.has("place") && !postsData.isNull("place")){
                if(postsData.getJSONObject("place").has("name")){
                message = message + " AT " + "<a href=\"http://facebook.com/" + postsData.getJSONObject("place").getString("id") + "\" target=\"_blank\">" + postsData.getJSONObject("place").getString("name") + "</a>";
                }                
            }
            
            if(isFoursquare){
                if(foursquareLink.has("place")){
                    if(foursquareLink.getJSONObject("place").has("name")){
                    message = message + "by Foursquare AT " + "<a href=\"http://facebook.com/" + foursquareLink.getJSONObject("place").getString("id") + "\" target=\"_blank\">" + foursquareLink.getJSONObject("place").getString("name") + "</a>";
                    }                
                }
            }
            
            //JSONObject profile = new JSONObject(getProfileFromId(postsData.getJSONObject("from").getString("id")+"", facebook));
            //profile = profile.getJSONArray("data").getJSONObject(0);
            writer.write("<fieldset>");
            writer.write("<table style=\"width: 100%; border: 0px\">");
            writer.write("<tr>");
            writer.write("   <td colspan=\"2\">");
            writer.write("<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" +renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", postsData.getJSONObject("from").getLong("id")+"")  + "','" + postsData.getJSONObject("from").getString("name") + "'); return false;\">" + postsData.getJSONObject("from").getString("name") + "</a> " + story);                            
            
            writer.write("   </td>");
            writer.write("</tr>");
            writer.write("<tr>");
            writer.write("   <td  width=\"10%\">");
            writer.write("<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", postsData.getJSONObject("from").getLong("id")+"") + "','" + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + postsData.getJSONObject("from").getLong("id") +"/picture\"/></a>");

            writer.write("   </td>");
            writer.write("   <td width=\"90%\">");
            //writer.write("MESSAGE:");
            if(message.isEmpty()){
                writer.write("&nbsp;");
            }else{                
                writer.write(message.replace("\n", "</br>"));
            }

            writer.write("   </td>");
            writer.write("</tr>");
            
            //if(postType.equals("link") && story.contains("like")){
//                writer.write("<tr>");
//                writer.write("<td>CUAL ES LA FUNCION DE ESTO?</td>");
//                writer.write("</tr>");
//            }
            
            //Picture if exists, start
            //if(postType.equals("link") && postsData.has("picture")){
            if(postsData.has("picture") || isAPhotoLike || isALinkLike || isAppCreated){
                String picture = "";
                if(isAPhotoLike){
                    if(photoLike.has("source")){
                        picture = photoLike.getString("source");
                    }
                }else if(isALinkLike){
                    if(linkLike.has("picture")){
                        picture = linkLike.getString("picture");
                    }
                }else if(isAPostLike){
                    if(postLike.has("picture")){
                        picture = postLike.getString("picture");
                    }
                }else if(isAppCreated){
                    if(applicationCreated.has("data")){
                        if(applicationCreated.getJSONObject("data").has("object")){
                            picture = applicationCreated.getJSONObject("data").getJSONObject("object").optString("url") + "media";
                        }
                    }
                }else{
                    picture = postsData.getString("picture").replace("_s.", "_n.");
                }
                writer.write("<tr>");
                writer.write("   <td  width=\"10%\">"); 
                writer.write("       &nbsp;");
                writer.write("   </td>");
                
                writer.write("   <td width=\"90%\">");
                writer.write("   <table>");
                writer.write("   <tr>");
                writer.write("   <td rowspan=\"3\">");
                if(postType.equals("video") || postType.equals("swf")){
                    writer.write("      <div id=\"vid" + tabSuffix + facebook.getId() + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                    writer.write("      <a href=\"#\" onclick=\"showDialog('"+ renderURL.setMode("displayVideo").setParameter("videoUrl", URLEncoder.encode(postsData.getString("source"), "UTF-8")) +
                            "','Video from " + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"" + picture + "\" style=\"position: relative;\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onload=\"imageLoad(" + "this, 'vid" + tabSuffix + facebook.getId() + postsData.getString("id") + "');\"/></a>");
                    writer.write("      </div>");
                }else{
                    if(isALinkLike){//If the post is a link -> it has link and name
                        if(linkLike.has("link") && linkLike.has("picture")){
                            writer.write("      <div id=\"img" + tabSuffix + facebook.getId() + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                            writer.write("      <a href=\"" + linkLike.getString("link") + "\" target=\"_blank\">" + "<img src=\"" + picture + "\" style=\"position: relative;\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onload=\"imageLoad(" + "this, 'img" + tabSuffix +facebook.getId() + postsData.getString("id") + "');\"/></a>");
                            writer.write("      </div>");
                        }
                    }else if(postType.equals("link")){//If the post is a link -> it has link and name
                        if(postsData.has("name") && postsData.has("link")){
                            writer.write("      <div id=\"img" + tabSuffix + facebook.getId() + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                            writer.write("      <a href=\"" + postsData.getString("link") + "\" target=\"_blank\">" + "<img src=\"" + picture + "\" style=\"position: relative;\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onload=\"imageLoad(" + "this, 'img" + tabSuffix +facebook.getId() + postsData.getString("id") + "');\"/></a>");
                            writer.write("      </div>");
                        }
                    }else{
                        writer.write("      <div id=\"img" + tabSuffix + facebook.getId() + postsData.getString("id") + "\" style=\"width: 250px; height: 250px; border: thick #666666; overflow: hidden; position: relative;\">");
                        writer.write("      <a href=\"#\" onclick=\"showDialog('" + renderURL.setMode("displayPicture").setParameter("pictureUrl", URLEncoder.encode(picture, "UTF-8")) +
                                "','Picture from " + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"" + picture + "\" style=\"position: relative;\" onerror=\"this.src ='" + picture.replace("_n.", "_s.") + "'\" onload=\"imageLoad(" + "this, 'img" + tabSuffix +facebook.getId() + postsData.getString("id") + "');\"/></a>");
                        writer.write("      </div>");
                    }
                }
                writer.write("  </td>");
                writer.write("</tr>");
                
                writer.write("<tr>");
                writer.write("  <td>");
                if(postsData.has("link") && postsData.has("name")){
                    writer.write(    "<a href=\"" + postsData.getString("link") + "\" target=\"_blank\">" +  postsData.getString("name") + "</a>");
                }else if(isALinkLike){
                    if(linkLike.has("link")){
                        writer.write(    "<a href=\"" + linkLike.getString("link") + "\" target=\"_blank\">" +  linkLike.getString("name") + "</a>");
                    }                    
                }else if(isAPostLike){
                    if(postLike.has("link")){
                        writer.write(    "<a href=\"" + postLike.getString("link") + "\" target=\"_blank\">" +  postLike.getString("name") + "</a>");
                    }                    
                }else{ 
                    writer.write("&nbsp;");
                }
                
                writer.write("  </td>");
                writer.write("</tr>");                

                writer.write("<tr>");
                writer.write("<td valign=\"top\">");
                if(isAPhotoLike){
                    writer.write(photoLike.has("name") ? photoLike.getString("name") : "&nbsp;");
                }else if(isALinkLike){
                    writer.write(linkLike.has("message") ? linkLike.getString("message") : "&nbsp;");
                }else{
                    writer.write( postsData.has("description") ?  postsData.getString("description") :"&nbsp;");
                }
                writer.write("</td>");
                writer.write("</tr>");                                
                writer.write("  </table>");
                writer.write("  </td>");
                writer.write("</tr>");
                if(!caption.isEmpty()){
                    writer.write("<tr>");
                    writer.write("   <td width=\"10%\">"); 
                    writer.write("       &nbsp;");
                    writer.write("   </td>");
                    writer.write("   <td width=\"90%\">");
                    writer.write(caption);
                    writer.write("   </td>");
                    writer.write("</tr>");
                }
            }
            //Picture if exists, end
            

            //Comments,start
            if(postsData.has("comments")){
                if(postsData.getJSONObject("comments").has("data")){
                    writer.write("<tr>");
                    writer.write("   <td  width=\"10%\">"); 
                    writer.write("      &nbsp;");
                    writer.write("   </td>");
                    writer.write("   <td width=\"90%\">");

                    JSONArray comments = postsData.getJSONObject("comments").getJSONArray("data");
                    writer.write("<table>");
                    for (int k = 0; k < comments.length(); k++){
                        if(k==5)break;
                        writer.write("<tr>");
                        writer.write("  <td rowspan=\"3\">");
                        //JSONObject commentProfile = new JSONObject(getProfileFromId(comments.getJSONObject(k).getJSONObject("from").getString("id")+"", facebook));
                        //commentProfile = commentProfile.getJSONArray("data").getJSONObject(0);
                        //writer.write("<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", commentProfile.getString("type")).setParameter("id", commentProfile.getLong("id")+"") + "','" + commentProfile.getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + commentProfile.getLong("id") +"/picture?width=30&height=30\"/></a>");
                        writer.write("<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type","noType").setParameter("id",comments.getJSONObject(k).getJSONObject("from").getLong("id")+"") + "','" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + comments.getJSONObject(k).getJSONObject("from").getLong("id") +"/picture?width=30&height=30\"/></a>");
                        
                        writer.write("  </td>");
                        writer.write("</tr>");
                        
                        writer.write("<tr>");
                        writer.write("  <td>");
                        writer.write("<b><a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type","noType").setParameter("id", comments.getJSONObject(k).getJSONObject("from").getLong("id")+"") + "','" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "'); return false;\">" + comments.getJSONObject(k).getJSONObject("from").getString("name") + "</a></b>:");
                        writer.write(       comments.getJSONObject(k).getString("message").replace("\n", "</br>") + "</br>");
                        writer.write("  </td>");
                        writer.write("</tr>");

                        Date commentTime = formatter.parse(comments.getJSONObject(k).getString("created_time"));
                        
                        writer.write("<tr>");
                        writer.write("<td>");
                        writer.write("<div id=\"" +facebook.getId() + comments.getJSONObject(k).getString("id") + "_" + postsData.getString("id") + "\" dojoType=\"dojox.layout.ContentPane\">");
                        
                        writer.write(facebookHumanFriendlyDate(commentTime));
                        if(comments.getJSONObject(k).has("like_count")){
                            writer.write(" Likes: " + comments.getJSONObject(k).getInt("like_count") );
                        }
                        writer.write("</div>");
                        writer.write("</td>");
                        writer.write("</tr>");
                    }
                    writer.write("   </table>");
                    
                    if(postsData.getJSONObject("comments").has("paging")){//Link to get more comments
                        JSONObject pagingComments = postsData.getJSONObject("comments").getJSONObject("paging");

                        if(pagingComments.has("next") && pagingComments.has("cursors")){
                            writer.write("<div align=\"left\" id=\"" + facebook.getId() + postsData.getString("id") + tabSuffix + "/comments\" dojoType=\"dojox.layout.ContentPane\">");
                            SWBResourceURL commentsURL = paramRequest.getRenderUrl().setMode("moreComments").setParameter("suri", request.getParameter("suri")).setParameter("postId", postsData.getString("id"));
                            commentsURL = commentsURL.setParameter("after", pagingComments.getJSONObject("cursors").getString("after")).setParameter("currentTab", tabSuffix);
                            writer.write("<label><a href=\"#\" onclick=\"appendHtmlAt('" + commentsURL
                                    + "','" + facebook.getId() + postsData.getString("id") + tabSuffix +"/comments', 'bottom');try{this.parentNode.parentNode.removeChild( this.parentNode );}catch(noe){}; return false;\">View more comments</a></label>");
                            writer.write("</div>");                            
                        }
                    }
                    
                    writer.write("   </td>");
                    writer.write("</tr>");
                }
            }
            //Comments, end

            writer.write("<tr>");
            writer.write("   <td colspan=\"2\">");
            ///////writer.write("<div id=\"" + postsData.getString("id") + postsData.getString("created_time") + "\" dojoType=\"dijit.layout.ContentPane\">");
            //writer.write("<div id=\"" + postsData.getString("id") + "\" dojoType=\"dijit.layout.ContentPane\">");
            Date postTime = formatter.parse(postsData.getString("created_time"));
            
            writer.write("<div dojoType=\"dijit.layout.ContentPane\">");
            
            writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + INFORMATION + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
            writer.write(facebookHumanFriendlyDate(postTime));
            boolean iLikedPost = false;
            writer.write(" Likes: <b>");
            if(postsData.has("likes")){
                JSONArray likes = postsData.getJSONObject("likes").getJSONArray("data");
                writer.write(postsData.getJSONObject("likes").getLong("count") + "");
                for (int k = 0; k < likes.length(); k++) {
                    //writer.write(likes.getJSONObject(k).getString("name") + ", ");
                    if(likes.getJSONObject(k).getString("id").equals(facebook.getFacebookUserId())){
                        //My User id is in 'the likes' of this post
                        iLikedPost = true;
                    }
                }
            }else{
                writer.write("0");
            }
            writer.write("   </b>");
            writer.write("   </span>");
            
            //Show like/unlike and reply (comment)
            JSONArray actions = postsData.has("actions") ? postsData.getJSONArray("actions") : null;
            if(actions != null && actions.length() > 0){//Available actions for the post
                for (int i = 0; i < actions.length(); i++) {
                    if(actions.getJSONObject(i).getString("name").equals("Comment")){//I can comment
                        writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + REPLY + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                            writer.write(" <a href=\"\" onclick=\"showDialog('" + renderURL.setMode("replyPost").setParameter("commentID", postsData.getString("id")) + "','Reply to " + postsData.getJSONObject("from").getString("name") + "');return false;\">Reply</a>  ");
                        writer.write("   </span>");
                        
                        if(linkLike != null){
                            writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + REPLY + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                                writer.write(" <a href=\"\" onclick=\"showDialog('" + renderURL.setMode("replyPost").setParameter("commentID", linkLike.getString("id")) + "','Reply to " + postsData.getJSONObject("from").getString("name") + "');return false;\">Reply</a>  ");
                            writer.write("   </span>");
                        }
            //**ini                                                
                        ///////////////////////If I can post I can Classify it to answer it later
                        PostIn post = PostIn.getPostInbySocialMsgId(model, postsData.getString("id"));
                        writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + TOPIC + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                        if(post != null){
                            String socialT = "";
                            if(post.getSocialTopic() != null){
                                socialT = post.getSocialTopic().getTitle();
                            }
                            SWBResourceURL clasifybyTopic = renderURL.setMode("doReclassifyTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", postsData.getString("id")).setParameter("postUri", post.getURI()).setParameter("currentTab", tabSuffix);
                                writer.write("<a href=\"#\" title=\"" + "Tema actual: " +  socialT + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                                + "Reclasificar post'); return false;\">Reclasificar</a>");
                        }else{
                            SWBResourceURL clasifybyTopic = renderURL.setMode("doShowTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", postsData.getString("id")).setParameter("currentTab", tabSuffix);
                            writer.write("<a href=\"#\" title=\"" + "Clasificar" + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                            + "Clasificar Post'); return false;\">Clasificar</a>");
                        }
                        writer.write("   </span>");
              //**fin          
                    }else if(actions.getJSONObject(i).getString("name").equals("Like")){//I can like
                        writer.write("   <span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") + LIKE + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");                        
                        if(iLikedPost){
                            writer.write(" <a href=\"\"  onclick=\"postSocialHtml('" + actionURL.setAction("doUnlike").setParameter("commentID", postsData.getString("id")).setParameter("currentTab", tabSuffix) + "','" + facebook.getId() +  postsData.getString("id") + INFORMATION + tabSuffix + "');return false;" +"\">Unlike</a>");
                        }else{
                            writer.write(" <a href=\"\"  onclick=\"postSocialHtml('" + actionURL.setAction("doLike").setParameter("commentID", postsData.getString("id")).setParameter("currentTab", tabSuffix) + "','" + facebook.getId() + postsData.getString("id") + INFORMATION + tabSuffix + "');return false;" +"\">Like</a>");
                        }
                        writer.write("   </span>");
                    }else{//Other unknown action
                        writer.write("other:" + actions.getJSONObject(i).getString("name"));
                    }
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
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                
            }            
        }
    }
*/
%>
<%
    try{
        String objUri = (String) request.getParameter("suri");
        String username;
        HashMap<String, String> params = new HashMap<String, String>(2);
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
<div align="center"><h2><%=username%> News Feed.</h2><br/></div>
<div class="bar" id="<%=objUri%>newPostsAvailable" dojoType="dojox.layout.ContentPane"></div>
<div id="<%=objUri%>facebookStream" dojoType="dojox.layout.ContentPane"></div>
<%
    SWBModel model=WebSite.ClassMgr.getWebSite(facebookBean.getSemanticObject().getModel().getName());
    
    params.put("limit", "50");
    params.put("fields", "id,from,to,message,message_tags,story,story_tags,picture,caption,link,object_id,application,source,name,description,properties,icon,actions,privacy,type,status_type,created_time,likes.summary(true),comments.limit(5).summary(true),place,icon");

    //SELECT status_id, time, source, message FROM status WHERE uid = me() Filtering status only
    //POSTS WITH LOCATION https://graph.facebook.com/me/home?with=location
    String fbResponse = postRequest(params, "https://graph.facebook.com/me/home",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
    
    String untilPost = parseResponse(fbResponse, out, true, request, paramRequest, NEWS_FEED_TAB, model);//Gets the newest post and saves the ID of the last one    
    SWBResourceURL renderURL = paramRequest.getRenderUrl().setParameter("suri", objUri).setParameter("currentTab", NEWS_FEED_TAB);
    String since = (String)session.getAttribute(objUri + NEWS_FEED_TAB + "since");
    System.out.println("\n\n\nsession.getAttribute(since):" + session.getAttribute(objUri + NEWS_FEED_TAB +  "since"));
%>

<div dojoType="dojox.layout.ContentPane">
    <script type="dojo/method">
        <%
            System.out.println("SINCE: " +  since);
            
            if(since != null){//Check what this is for
                String intervalId = (String)session.getAttribute(objUri + "pooling");
                System.out.println("\n\nSESSION VAR FOR CURRENT FACEBOOK TAB:" + intervalId);
        
                if(intervalId == null){
        %>
                    var interval = setInterval(function(){ postSocialHtmlListeners('<%=renderURL.setMode("newPostsAvailable")%>','<%=objUri%>newPostsAvailable'); },20000);
                    console.log('Value of FB Interval:' + interval);
                    saveInterval('<%=paramRequest.getRenderUrl().setMode("storeInterval").setParameter("suri", objUri)+"&interval="%>' + interval);
        <%
                }else{
        %>
                    console.log('Stoping interval for current uri ' + ':' + <%=intervalId%>);
                    clearInterval(<%=intervalId%>);
                    var interval = setInterval(function(){ postSocialHtmlListeners('<%=renderURL.setMode("newPostsAvailable")%>','<%=objUri%>newPostsAvailable'); },20000);
                    console.log('Value of a NEW FB Interval:' + interval);
                    saveInterval('<%=paramRequest.getRenderUrl().setMode("storeInterval").setParameter("suri", objUri)+"&interval="%>' + interval);
        <%
                }
                //In both cases an 'interval' variable is defined and is assigned to the onClose event
        %>
                //Change the default onClose method of the parent Tab
                var tabId =  '<%=objUri%>' + '/tab';
                var cPane = dijit.byId(tabId);        
                cPane.attr('onClose', function callStopListener(){ clearInterval(interval); return true;});
        <%
            }
        %>
   </script>
</div>
<div id="<%=objUri%>getMorePosts" dojoType="dijit.layout.ContentPane">
    <div align="center">
        <label id="<%=objUri%>morePostsLabel"><a href="#" onclick="appendHtmlAt('<%=renderURL.setMode("getMorePosts").setParameter("until", untilPost).setParameter("scope", "newsFeed")%>','<%=objUri%>getMorePosts', 'bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;">More posts</a></label>
    </div>
</div>
</div>
    <%
        }catch(Exception e){
        out.print("Problem displaying News feed: " + e.getMessage());
    }
%>