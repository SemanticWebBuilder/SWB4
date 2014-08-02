<%-- 
    Document   : facebookInteraction
    Created on : 9/07/2014, 01:46:43 PM
    Author     : francisco.jimenez
--%>

<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Comparator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="org.semanticwb.SWBPlatform"%>
<%@page import="org.semanticwb.social.admin.resources.util.SWBSocialResUtil"%>
<%@page import="org.semanticwb.social.PostIn"%>
<%@page import="org.semanticwb.model.UserGroup"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="java.io.Closeable"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.util.Collection"%>
<%@page import="org.semanticwb.social.SocialNetwork"%>
<%@page import="org.semanticwb.social.Facebook"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.model.SWBModel"%>
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
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>


<%!
    public static String postRequest(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {

        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        //System.out.println("2URL : " + url + "?" + paramString);
        URL serverUrl = new URL(url + "?" + paramString);

        HttpURLConnection conex = null;
        OutputStream out = null;
        InputStream in = null;
        String response = null;

        if (method == null) {
            method = "POST";
        }
        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod(method);
            conex.setDoOutput(true);
            conex.connect();

            //System.out.println("CONNECT:" + conex);
            //   out = conex.getOutputStream();
            //   out.write(paramString.toString().getBytes("UTF-8"));
            in = conex.getInputStream();
            response = getResponse(in);

        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                //System.out.println("ERROR:" +   getResponse(conex.getErrorStream()));
                response = getResponse(conex.getErrorStream());
                //log.error("\n\nERROR:" + response);
            }
            //ioe.printStackTrace();
        } finally {
            close(in);
            //close(out);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }

    public static String getRequest(Map<String, String> params, String url,
            String userAgent) throws IOException {

        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url + "?" + paramString);
        HttpURLConnection conex = null;
        InputStream in = null;
        String response = null;

        try {
            conex = (HttpURLConnection) serverUrl.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(60000);
            conex.setRequestMethod("GET");
            conex.setDoOutput(true);
            conex.connect();
            in = conex.getInputStream();
            response = getResponse(in);
            //System.out.println("RESPONSE:" + response);

        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                response = getResponse(conex.getErrorStream());
                //log.error("\n\n\nERROR:" + response);
            }
            ioe.printStackTrace();
        } finally {
            close(in);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }

    public static CharSequence delimit(Collection<Map.Entry<String, String>> entries,
            String delimiter, String equals, boolean doEncode)
            throws UnsupportedEncodingException {

        if (entries == null || entries.isEmpty()) {
            return null;
        }
        StringBuilder buffer = new StringBuilder(64);
        boolean notFirst = false;
        for (Map.Entry<String, String> entry : entries) {
            if (notFirst) {
                buffer.append(delimiter);
            } else {
                notFirst = true;
            }
            CharSequence value = entry.getValue();
            buffer.append(entry.getKey());
            buffer.append(equals);
            buffer.append(doEncode ? encode(value) : value);
        }
        return buffer;
    }

   
    private static String encode(CharSequence target) throws UnsupportedEncodingException {

        String result = "";
        if (target != null) {
            result = target.toString();
            result = URLEncoder.encode(result, "UTF8");
        }
        return result;
    }

    public static String getResponse(InputStream data) throws IOException {

        Reader in = new BufferedReader(new InputStreamReader(data, "UTF-8"));
        StringBuilder response = new StringBuilder(256);
        char[] buffer = new char[1000];
        int charsRead = 0;
        while (charsRead >= 0) {
            response.append(buffer, 0, charsRead);
            charsRead = in.read(buffer);
        }
        in.close();
        return response.toString();
    }

    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ex) {
                //log.error("Error at closing object: " + c.getClass().getName(),ex);
            }
        }
    }

    public static String parseResponse(String response, Writer out, boolean includeSinceParam, HttpServletRequest request, SWBParamRequest paramRequest, SWBModel model) {

        String until = null;
        String since = "";
        String objUri = (String) request.getParameter("suri");
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook) semanticObject.createGenericInstance();

        try {
            JSONObject phraseResp = new JSONObject(response);
            int cont = 0;
            JSONArray postsData = phraseResp.getJSONArray("data");
            //System.out.println("ARREGLO DE DATOS:" + postsData.length());

            org.semanticwb.model.User user = paramRequest.getUser();
            HashMap<String, SemanticProperty> mapa = new HashMap<String, SemanticProperty>();
            Iterator<SemanticProperty> list = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialUserExtAttributes").listProperties();
            while (list.hasNext()) {
                SemanticProperty sp = list.next();
                mapa.put(sp.getName(),sp);
            }
            boolean userCanRetopicMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanReTopicMsg"))).booleanValue();                
            boolean userCanRespondMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanRespondMsg"))).booleanValue();
            boolean userCanRemoveMsg = ((Boolean)user.getExtendedAttribute(mapa.get("userCanRemoveMsg"))).booleanValue();
            UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
            //user.hasUserGroup(userSuperAdminGrp)
            //Obtener informacion del mes actual

            for (int k = 0; k < postsData.length(); k++) {
                cont++;
                //System.out.println("\n\nPost de FACEBOOOK*********: " + postsData.getJSONObject(k));
                doPrintPost(out, postsData.getJSONObject(k), request, paramRequest, facebook, model, user.hasUserGroup(userSuperAdminGrp), userCanRetopicMsg, userCanRespondMsg, userCanRemoveMsg);
            }
            //lastMonthData(out, postsData);
            if (phraseResp.has("paging")) {
                JSONObject pagingData = phraseResp.getJSONObject("paging");
                String nextPage = pagingData.getString("next"); // get until param to get OLDER posts
                Pattern pattern = Pattern.compile("until=[0-9]+");
                Matcher matcher = pattern.matcher(nextPage);
                String untilParam = "";
                if (matcher.find()) {
                    untilParam = matcher.group();
                }
                if (!untilParam.isEmpty()) {
                    until = untilParam.substring(untilParam.indexOf("=") + 1);//gets only the value of until param in paging object
                }
                if (includeSinceParam) {//Include value of since param when the tab is loaded and when GetNewPost link is clicked
                    String previousPage = pagingData.getString("previous"); // get since param to get NEWER posts
                    pattern = Pattern.compile("since=[0-9]+");
                    matcher = pattern.matcher(previousPage);
                    String sinceParam = "";

                    if (matcher.find()) {
                        sinceParam = matcher.group();
                    }
                    if (!sinceParam.isEmpty()) {
                        since = sinceParam.substring(sinceParam.indexOf("=") + 1);//gets only the value of since param in paging object
                        HttpSession session = request.getSession(true);
                        //System.out.println("\n\n\n\t\tReemplazando viejo parametro FEED:" + session.getAttribute(objUri + tabSuffix + "since"));
                        session.setAttribute(objUri + "since", since);
                    }
                }
            }
        } catch (JSONException jsone) {
            //log.error("Problemas al parsear respuesta de Facebook", jsone);
        }
        return until;
    }

    public static int[] lastMonthData(Writer writer, JSONArray postsData, int chartCurrentMonth[]) {
        LinkedHashMap<Integer, JSONArray> postsByMonth= new LinkedHashMap<Integer,JSONArray>();
                
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
        int monthOfLastPost = -1;
        try{
            if(postsData.length() > 0){
                if(!postsData.getJSONObject(0).isNull("created_time")){                    
                    Date postTime = formatter.parse(postsData.getJSONObject(0).getString("created_time"));
                    Calendar calendar = GregorianCalendar.getInstance();
                    calendar.setTime(postTime);
                    monthOfLastPost = calendar.get(Calendar.MONTH);
                    System.out.println("first created time:" + postTime + " -->" +monthOfLastPost);
                }
            }
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
        
        if(monthOfLastPost < 0)return null;
        
        JSONArray posts = new JSONArray();
        Date currentDate  = null;
        for(int i = 0; i < postsData.length(); i++){
            try{
                Date postTime = formatter.parse(postsData.getJSONObject(i).getString("created_time"));
                Calendar monthPost = GregorianCalendar.getInstance();
                monthPost.setTime(postTime);
                if(currentDate == null){
                    currentDate = postTime;
                }
                
                if((monthOfLastPost != monthPost.get(Calendar.MONTH)) || i+ 1 == postsData.length()){
                    postsByMonth.put(monthOfLastPost, posts);
                }else{
                    posts.put(postsData.getJSONObject(i));
                    writer.write("<p>" + postsData.getJSONObject(i) + "</p>");
                }
            }catch(Exception e){}
        }
        
        JSONArray currentMonthPosts = postsByMonth.get(Integer.valueOf(monthOfLastPost));
        Calendar monthPost = Calendar.getInstance();
        monthPost.setTime(currentDate);
        int totalDaysOfMonth = monthPost.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("FECHA:" + currentDate);
        System.out.println("MES:" + (monthPost.get(Calendar.MONTH)+1));
        System.out.println("totalDaysOfMonth:" + monthPost.getActualMaximum(Calendar.DAY_OF_MONTH));
        int postsByDay[] = new int[totalDaysOfMonth];
        if(chartCurrentMonth != null){
            for(int i = 0 ; i < chartCurrentMonth.length; i++){
                postsByDay[i] = chartCurrentMonth[i];
            }
        }
        try{
            for(int i = 0; i < currentMonthPosts.length(); i++){
                JSONObject currentPost = currentMonthPosts.getJSONObject(i);
                if(!currentPost.isNull("created_time")){
                    Date postTime = formatter.parse(currentPost.getString("created_time"));
                    Calendar calendar = GregorianCalendar.getInstance();
                    calendar.setTime(postTime);
                    postsByDay[calendar.get(Calendar.DAY_OF_MONTH)-1]++;
                }
            }
            writer.write("------------------------------------------");
            writer.write("------------------------------------------");
            writer.write("------------------------------------------");
            for(int i = 0; i < postsByDay.length; i++){
                //writer.write("<p>" + postsByDay[i] + "</p>");
            }
        }catch(Exception e){}
        return postsByDay;
    }
    
    public static boolean isNumeric(String number){
        try{
            Integer.parseInt(number);
            return true;
        }catch(NumberFormatException nfe){
            return false;
        }
    }
    
    public static JSONArray getAllPostFromLastMonth(Facebook facebook, JSONArray historicData) {
        HashMap<String, String> paramsFb = new HashMap<String, String>(2);
        paramsFb.put("access_token", facebook.getAccessToken());
        paramsFb.put("limit", "100");
        paramsFb.put("fields", "id,from,to,message,message_tags,story,story_tags,picture,caption,link,object_id,application,source,name,description,properties,icon,actions,privacy,type,status_type,created_time,likes.summary(true),comments.limit(5).summary(true),place,icon");

        //Genera la primera peticion de posts
        //Solo se deben obtener estadisticas del ultimo mes        
        boolean endOfMonth = false;//get all posts of the last month with activity
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
        Calendar lastPostDate = null;
        JSONArray monthlyData = new JSONArray();
        
        try{
            do{
                String fbResponse = postRequest(paramsFb, "https://graph.facebook.com/me/posts",
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
                //System.out.println("fbResponse_:_:_:_:_:_:_:" + fbResponse);
                try{
                    new JSONObject(fbResponse);
                }catch(Exception e){
                    endOfMonth = true;
                    break;
                }                
                JSONObject myPosts = new JSONObject(fbResponse);
                //System.out.println(myPosts);
                if(!myPosts.isNull("data")){
                    JSONArray postsData = myPosts.getJSONArray("data");
                    
                    //Store the month of the last post. Do this once!
                    if(postsData.length() > 0 && lastPostDate == null){
                        if(!postsData.getJSONObject(0).isNull("created_time")){
                            Date postTime = formatter.parse(postsData.getJSONObject(0).getString("created_time"));
                            Calendar calendar = GregorianCalendar.getInstance();
                            calendar.setTime(postTime);
                            lastPostDate = calendar; 
                            //monthOfLastPost = calendar.get(Calendar.MONTH);
                            //System.out.println("first created time:" + postTime + " -->" +lastPostDate.getTime());
                        }
                        
                    }
                    
                    if(postsData.length() > 0 ){
                        for(int i = 0; i < postsData.length(); i++){
                            Date postTime = formatter.parse(postsData.getJSONObject(i).getString("created_time"));
                            Calendar monthPost = GregorianCalendar.getInstance();
                            monthPost.setTime(postTime);
                            //System.out.print( monthPost.getTime() + "///");
                            //If the post was created the same year and month
                            if(monthPost.get(Calendar.YEAR) == lastPostDate.get(Calendar.YEAR)
                                    && monthPost.get(Calendar.MONTH) == lastPostDate.get(Calendar.MONTH)){
                                    //System.out.print("adding--> " + monthPost.getTime());
                                    monthlyData.put( postsData.getJSONObject(i));
                                    historicData.put( postsData.getJSONObject(i));
                            }else{//The wall is from newest to oldests
                               historicData.put( postsData.getJSONObject(i));
                               if(historicData.length() >= 100){
                                   endOfMonth = true;
                                   break;
                               }                               
                            }
                        }
                    }else{
                        endOfMonth = true;
                    }
                
                    //Get more posts because its the same month
                    
                    if(!myPosts.isNull("paging") && endOfMonth == false) {
                        if(!myPosts.getJSONObject("paging").isNull("next")){
                            String until = null;
                            try{
                                String paging = myPosts.getJSONObject("paging").getString("next");
                                //System.out.println("--->" + myPosts.getJSONObject("paging").getString("next"));

                                if(paging.contains("?")){
                                    paging = paging.split("\\?")[1];
                                    String paramsUrl[] = paging.split("&");
                                    for(int i = 0; i < paramsUrl.length; i++){
                                        if(paramsUrl[i].contains("until")){
                                            until = paramsUrl[i].substring(paramsUrl[i].lastIndexOf("=") + 1);
                                            //System.out.println("VALUE OF UNTIL::" + until);
                                            break;
                                        }
                                    }
                                }
                            }catch(Exception e){
                                System.out.println("NEXT PARAM" +e.getMessage());
                            }

                            if(until != null && isNumeric(until)){
                                paramsFb.put("until",until);
                            }else{endOfMonth = true;}
                        }
                    }
                }else{
                    endOfMonth = true;
                }
            }while(endOfMonth == false);        
        }catch(Exception e){
            System.out.println("ERROR--->:" + e.getMessage());
            e.printStackTrace();
        }
        return monthlyData;
    }
    
    
    public static int[] getChartValues(JSONArray data, Writer writer, Calendar currentCalendar){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
        int postsByDay[] = null;
        if(data.length() > 0 ){
            Calendar monthPost = GregorianCalendar.getInstance();
            try{
                Date postTime = formatter.parse(data.getJSONObject(0).getString("created_time"));                
                System.out.println("EL POST CIENTE:" + postTime);
                monthPost.setTime(postTime);  
                currentCalendar = monthPost;
                postsByDay = new int[monthPost.getActualMaximum(Calendar.DAY_OF_MONTH)];
            }catch(Exception e){}
            
            try{
                for(int i = 0; i < data.length(); i++){
                    JSONObject currentPost = data.getJSONObject(i);
                    ////writer.write("<p>" + currentPost + "</p>");
                    if(!currentPost.isNull("created_time")){
                        Date postTime = formatter.parse(currentPost.getString("created_time"));
                        Calendar calendar = GregorianCalendar.getInstance();
                        calendar.setTime(postTime);
                        postsByDay[calendar.get(Calendar.DAY_OF_MONTH)-1]++;
                    }
                }

                for(int i = 0; i < postsByDay.length; i++){
                    //writer.write("<p>" + postsByDay[i] + "</p>");
                }
            }catch(Exception e){}
        }
        
               
        
        return postsByDay;
    }
    public static void doPrintPost(Writer writer, JSONObject postsData, HttpServletRequest request, SWBParamRequest paramRequest, Facebook facebook, SWBModel model, boolean userCanDoEveryting, boolean userCanRetopicMsg, boolean userCanRespondMsg, boolean userCanRemoveMsg) {
        try {
            Date  now = new Date();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(now);
            
            int month = -1;
            
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            actionURL.setParameter("suri", request.getParameter("suri"));
            renderURL.setParameter("suri", request.getParameter("suri"));
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
            String postType = postsData.getString("type");

            String story = "";
            String message = "";
            
            boolean isFoursquare = false;
            //TODO: FALTA COMMENTED ON A PHOTO
            
            JSONObject foursquareLink = null;

            



            if (postType.equals("photo")) {
                if (!postsData.isNull("story")) {
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "";
                    if (!postsData.isNull("story_tags")) {//Users tagged in story
                        story = getTagsFromPost(postsData.getJSONObject("story_tags"), story, renderURL);
                    }
                }

                if (!postsData.isNull("message")) {
                    message = SWBSocialResUtil.Util.createHttpLink(postsData.getString("message"));
                    if (!postsData.isNull("message_tags")) {//Users tagged in story
                        message = getTagsFromPost(postsData.getJSONObject("message_tags"), message, renderURL);
                    }
                }
              
                if (postsData.has("application")) {
                    if (postsData.getJSONObject("application").getString("name").equals("Foursquare")) {
                        return;
                        /*foursquareLink = getPostFromId(postsData.getString("id"), null, facebook);
                         isFoursquare = true;
                         System.out.println("\n\n\nFOURSQUARE CREATED:" +  foursquareLink);
                         message = "Checked in";
                         */
                    }
                }
                //Story or message or both!!
                //or "status_type": "shared_story", tagged_in_photo

            } else if (postType.equals("link")) {
                //"status_type": "app_created_story",
                if (!postsData.isNull("story")) {
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "";
                    if (!postsData.isNull("story_tags")) {//Users tagged in story
                        story = getTagsFromPost(postsData.getJSONObject("story_tags"), story, renderURL);
                    }
                    if (story.contains("is going to an event") && postsData.has("link")) {//If the link is an event
                        return;
                        //message = "<a href=\"" + postsData.getString("link") + "\" target=\"_blank\">View event</a>";
                    }
                    if (story.contains("likes a photo")) {
                        return;
                    } else if (story.contains("likes a link")) {
                        return;
                    } else if (story.contains("likes a status")) {
                        return;
                    } else if (story.contains("commented on")) {
                        return;
                    } else if (story.contains("likes")) {
                        return;
                    } else if (story.contains("is going to")) {
                        return;
                    } else if (story.contains("created an event")) {
                        return;
                    }
                }
                if (!postsData.isNull("message")) {                    
                    message = SWBSocialResUtil.Util.createHttpLink(postsData.getString("message"));
                    if (!postsData.isNull("message_tags")) {//Users tagged in story
                        message = getTagsFromPost(postsData.getJSONObject("message_tags"), message, renderURL);
                    }
                }

                if (postsData.has("application")) {
                    //return;
                    /*
                     if(postsData.getJSONObject("application").getString("name").equals("Instagram")){
                     applicationCreated = getPostFromId(postsData.getString("id"), null, facebook);
                     isAppCreated = true;
                     System.out.println("\n\n\nAPPLICATION CREATED:" +  applicationCreated);
                     message = "Liked a picture in Instagram";
                     }*/
                }
            } else if (postType.equals("status")) {

                if (postsData.has("story")) {//Do not print the posts when 'User X likes a post'
                    if (postsData.getString("story").contains("likes a post")) {
                        return;
                    }
                }
                if (!postsData.isNull("status_type")) {
                    if (postsData.getString("status_type").equals("wall_post")) {
                        JSONObject toUser = null;
                        if (postsData.has("to")) {
                            toUser = postsData.getJSONObject("to").getJSONArray("data").getJSONObject(0);
                            story = " to " + "<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", toUser.getLong("id") + "") + "','" + toUser.getString("name") + "'); return false;\">" + toUser.getString("name") + "</a>";
                        }
                    }
                }
                if (!postsData.isNull("message")) {
                    message = SWBSocialResUtil.Util.createHttpLink(postsData.getString("message"));
                    if (!postsData.isNull("message_tags")) {//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = getTagsFromPost(storyTags, message, renderURL);
                    }
                } else if (!postsData.isNull("story")) {
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "";
                    if (!postsData.isNull("story_tags")) {//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("story_tags");
                        story = getTagsFromPost(storyTags, story, renderURL);
                    }
                    if (story.contains("likes a photo")) {
                        /*photoLike = getPostFromId(postsData.getString("id"), "id,from,name,name_tags,picture,source,link,tags", facebook);
                         isAPhotoLike = true;*/
                        return;
                        //System.out.println("THE RECOVERED OBJECT:" + jSONObject);
                    } else if (story.contains("likes a link")) {//Do not print the posts when 'User X likes a link'
                        /*linkLike = getPostFromId(postsData.getString("id"), "id,from,name,picture,link,tags,message", facebook);
                         System.out.println("\n\n\nLINKED LIKED:" +  linkLike);
                         isALinkLike = true;*/
                        return;
                    } else if (story.contains("likes a status")) {
                        /*
                         statusLike = getPostFromId(postsData.getString("id"), null, facebook);
                         isAStatusLike = true;
                         System.out.println("\n\n\nSTATUS LIKED:" +  statusLike);
                         if(statusLike.has("message")){
                         message = statusLike.getString("message");
                         }*/
                        return;
                    } else if (story.contains("commented on")) {
                        return;
                    } else if (story.contains("likes")) {//USER likes PAGE
                        return;
                    } else if (story.contains("is going to")) {//events
                        return;
                    } else if (story.contains("created an event")) {
                        return;
                    }
                } else {//Status must have message OR Story
                    return;
                }
            } else if (postType.equals("video")) {
                if (!postsData.isNull("message")) {
                    message = SWBSocialResUtil.Util.createHttpLink(postsData.getString("message"));
                }

                if (!postsData.isNull("story")) {
                    story = (!postsData.isNull("story")) ? postsData.getString("story").replace(postsData.getJSONObject("from").getString("name"), "") : "";
                    if (!postsData.isNull("story_tags")) {//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("story_tags");
                        story = getTagsFromPost(storyTags, story, renderURL);
                    }
                }
            } else if (postType.equals("checkin")) {

                if (!postsData.isNull("message")) {
                    message = SWBSocialResUtil.Util.createHttpLink(postsData.getString("message"));
                    if (!postsData.isNull("message_tags")) {//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = getTagsFromPost(storyTags, message, renderURL);
                    }
                } else {
                    message = postsData.getJSONObject("from").getString("name") + " checked in ";
                }
            } else if (postType.equals("swf")) {
                if (!postsData.isNull("message")) {
                    message = SWBSocialResUtil.Util.createHttpLink(postsData.getString("message"));
                    if (!postsData.isNull("message_tags")) {//Users tagged in story
                        JSONObject storyTags = postsData.getJSONObject("message_tags");
                        message = getTagsFromPost(storyTags, message, renderURL);
                    }
                }
            }

            if (postsData.has("place") && !postsData.isNull("place")) {
                if (postsData.getJSONObject("place").has("name")) {
                    message = message + " at " + "<a href=\"http://facebook.com/" + postsData.getJSONObject("place").getString("id") + "\" target=\"_blank\">" + postsData.getJSONObject("place").getString("name") + "</a>";
                }
            }

            if (isFoursquare) {
                if (foursquareLink.has("place")) {
                    if (foursquareLink.getJSONObject("place").has("name")) {
                        message = message + "by Foursquare AT " + "<a href=\"http://facebook.com/" + foursquareLink.getJSONObject("place").getString("id") + "\" target=\"_blank\">" + foursquareLink.getJSONObject("place").getString("name") + "</a>";
                    }
                }
            }

            //JSONObject profile = new JSONObject(getProfileFromId(postsData.getJSONObject("from").getString("id")+"", facebook));
            //profile = profile.getJSONArray("data").getJSONObject(0);
            writer.write("<div class=\"timeline timelinefacebook\" id=\"" + facebook.getId() + postsData.getString("id") + "\">");
            //Username and story
            /*writer.write("<p>");
            writer.write("<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", postsData.getJSONObject("from").getLong("id") + "") + "','" + postsData.getJSONObject("from").getString("name") + "'); return false;\">" + postsData.getJSONObject("from").getString("name") + "</a> " + story);
            writer.write("</p>");*/

            //User image and message
            writer.write("<div class=\"timelineusr\">");
            writer.write("<a href=\"#\" title=\"" + "Ver perfil" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", postsData.getJSONObject("from").getLong("id") + "") + "','" + postsData.getJSONObject("from").getString("name") + "'); return false;\"><img src=\"http://graph.facebook.com/" + postsData.getJSONObject("from").getLong("id") + "/picture\"/></a>");

            writer.write("<p>");
            if (message.isEmpty()) {
                writer.write("&nbsp;");
            } else {
                writer.write(message.replace("\n", "</br>"));
            }
            writer.write("</p>");
            writer.write("</div>");

            //Picture if exists, start

            //Comments,start
            if (postsData.has("comments")) {
                if (postsData.getJSONObject("comments").has("summary")) {
                    JSONObject summary = postsData.getJSONObject("comments").getJSONObject("summary");
                    writer.write("<p>Comentarios:" + summary.getInt("total_count") + "</p>");
                }
            }

            //writer.write("<span id=\"" + facebook.getId() + postsData.getString("id") + tabSuffix + "/comments\" dojoType=\"dojox.layout.ContentPane\">");
            //writer.write("</span>"); 
            //Comments, end
            writer.write("<div class=\"clear\"></div>");
            Date postTime = formatter.parse(postsData.getString("created_time"));
            Calendar monthPost = GregorianCalendar.getInstance();
            monthPost.setTime(postTime);
            if(month != monthPost.get(Calendar.MONTH)){
                writer.write("-------------------------------" + month + "-" + monthPost.get(Calendar.MONTH));
                month--;
            }
            
            writer.write("<div class=\"timelineresume\" dojoType=\"dijit.layout.ContentPane\">");
            if (!postsData.isNull("icon")) {
                //writer.write("<img src=\"" + postsData.getString("icon") + "\"/>");
            }
            writer.write("<span class=\"inline\" id=\"" + facebook.getId() + postsData.getString("id") +  "\" dojoType=\"dojox.layout.ContentPane\">");
            //writer.write("<em>" + facebookHumanFriendlyDate(postTime, paramRequest) + "</em>");
            //writer.write("<em title=\"" + postTime +"\">&nbsp;</em>");
            writer.write("time:" + postTime);
            boolean iLikedPost = false;
            //writer.write("<strong><span> Likes: </span>");
            if (postsData.has("likes")) {
                JSONArray likes = postsData.getJSONObject("likes").getJSONArray("data");
                int postLikes = 0;
                if (!postsData.getJSONObject("likes").isNull("summary")) {
                    if (!postsData.getJSONObject("likes").getJSONObject("summary").isNull("total_count")) {
                        postLikes = postsData.getJSONObject("likes").getJSONObject("summary").getInt("total_count");
                    }
                }

                writer.write("likes:" + String.valueOf(postLikes));

                for (int k = 0; k < likes.length(); k++) {
                    if (likes.getJSONObject(k).getString("id").equals(facebook.getFacebookUserId())) {
                        //My User id is in 'the likes' of this post
                        iLikedPost = true;
                    }
                }

                if ((likes.length() < postLikes) && (iLikedPost == false)) {
                    //System.out.println("Look for postLike!!!");
                    HashMap<String, String> params = new HashMap<String, String>(3);
                    params.put("q", "SELECT post_id FROM like WHERE user_id=me() AND post_id=\"" + postsData.getString("id") + "\"");
                    params.put("access_token", facebook.getAccessToken());
                    String fbLike = null;

                    try {
                        fbLike = getRequest(params, "https://graph.facebook.com/fql",
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
                        //System.out.println("fbLike:" + fbLike);
                        JSONObject likeResp = new JSONObject(fbLike);
                        if (likeResp.has("data")) {
                            JSONArray likesArray = likeResp.getJSONArray("data");

                            if (likesArray.length() == 1) {//There is one result, I liked this post
                                iLikedPost = true;
                            }
                        }
                    } catch (Exception e) {
                        //log.error("Error getting like information for Facebook post " + postsData.getString("id"), e);
                    }
                }
            } else {
                writer.write("0");
            }
            //writer.write("</strong>");
            writer.write("</span>");

            writer.write("  </div>");
            writer.write("</div>");
        } catch (Exception e) {
            //log.error("Error printing post:", e);
            e.printStackTrace();
        }
    }

    public static String getTagsFromPost(JSONObject objectTags, String postContent, SWBResourceURL renderURL) {
        String postContentWithUrl = postContent;
        Iterator<?> keyTags = objectTags.keys();
        try {
            while (keyTags.hasNext()) {
                String key = (String) keyTags.next();
                if (objectTags.get(key) instanceof JSONArray) {
                    JSONArray tag = objectTags.getJSONArray(key);
                    String userUrl = "";
                    if (tag.getJSONObject(0).has("type")) {
                        userUrl = "<a href=\"#\" title=\"" + "View profile" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", tag.getJSONObject(0).getString("type")).setParameter("id", tag.getJSONObject(0).getLong("id") + "") + "','" + tag.getJSONObject(0).getString("name") + "'); return false;\">" + tag.getJSONObject(0).getString("name") + "</a> ";
                    } else {
                        userUrl = "<a href=\"#\" title=\"" + "View profile" + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", "noType").setParameter("id", tag.getJSONObject(0).getLong("id") + "") + "','" + tag.getJSONObject(0).getString("name") + "'); return false;\">" + tag.getJSONObject(0).getString("name") + "</a> ";
                    }
                    //userUrl = "<a href=\"#\" title=\"" + paramRequest.getLocaleString("viewProfile") + "\" onclick=\"showDialog('" + renderURL.setMode("fullProfile").setParameter("type", tag.getJSONObject(0).getString("type")).setParameter("id", tag.getJSONObject(0).getLong("id")+"") + "','" + tag.getJSONObject(0).getString("name") + "'); return false;\">" + tag.getJSONObject(0).getString("name") + "</a>";
                    postContentWithUrl = postContentWithUrl.replace(tag.getJSONObject(0).getString("name"), userUrl);
                }
            }
        } catch (JSONException jSonException) {
            //log.error("Problem parsing associated users:" + objectTags + "\n\n" + postContent);
            jSonException.printStackTrace();
            return postContent;
        }
        return postContentWithUrl;
    }

    public static class OrderByLikesComparator implements Comparator<JSONObject>
    {

        @Override
        public int compare(JSONObject a, JSONObject b) {
            //throw new UnsupportedOperationException("Not supported yet.");
            int response = 0;
            try{
                int alikes = 0;
                int blikes = 0;
                if(!a.isNull("likes")){            
                    if(!a.getJSONObject("likes").isNull("summary")){
                        if(!a.getJSONObject("likes").getJSONObject("summary").isNull("total_count")){
                            alikes = a.getJSONObject("likes").getJSONObject("summary").getInt("total_count");
                        }
                    }
                }
                if(!b.isNull("likes")){            
                    if(!b.getJSONObject("likes").isNull("summary")){
                        if(!b.getJSONObject("likes").getJSONObject("summary").isNull("total_count")){
                            blikes = b.getJSONObject("likes").getJSONObject("summary").getInt("total_count");
                        }
                    }
                }
                //System.out.print("a:"+ alikes + "vs" + "b:" + blikes +"...");
                //Mos likes first
                if(alikes > blikes)
                    response = -1;
                if(alikes < blikes)
                    response =  1;
                //return 0; 
                //return a.compareTo(b);
            }catch(Exception e){
                System.out.print("e");
            }
            return response;
        }                
    }

    public static class OrderByCommentsComparator implements Comparator<JSONObject>
        {

        @Override
        public int compare(JSONObject a, JSONObject b) {
            //throw new UnsupportedOperationException("Not supported yet.");
            int response = 0;
            try{
                int acomments = 0;
                int bcomments = 0;
                if(!a.isNull("comments")){            
                    if(!a.getJSONObject("comments").isNull("summary")){
                        if(!a.getJSONObject("comments").getJSONObject("summary").isNull("total_count")){
                            acomments = a.getJSONObject("comments").getJSONObject("summary").getInt("total_count");
                        }
                    }
                }
                if(!b.isNull("comments")){            
                    if(!b.getJSONObject("comments").isNull("summary")){
                        if(!b.getJSONObject("comments").getJSONObject("summary").isNull("total_count")){
                            bcomments = b.getJSONObject("comments").getJSONObject("summary").getInt("total_count");
                        }
                    }
                }
                //System.out.print("a:"+ alikes + "vs" + "b:" + blikes +"...");
                //Mos likes first
                if(acomments > bcomments)
                    response = -1;
                if(acomments < bcomments)
                    response =  1;
                //return 0; 
                //return a.compareTo(b);
            }catch(Exception e){
                System.out.print("e");
            }
            return response;
        }
    }
%>
    
    <%
    int chartCurrentMonth[] = null;
    Calendar currentCalendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date currentDate = sdf.parse("" + currentCalendar.get(Calendar.YEAR) + "-" + (currentCalendar.get(Calendar.MONTH)) + "-" + currentCalendar.get(Calendar.DAY_OF_MONTH));
    
    try{
        String suri = request.getParameter("suri");
        SocialNetwork sn = (SocialNetwork) SemanticObject.createSemanticObject(suri).createGenericInstance();
        Facebook fb = null;
        if(!(sn instanceof Facebook)){return;}else{fb = (Facebook)sn;}
    
        JSONArray historicData = new JSONArray();
        JSONArray monthlyData = getAllPostFromLastMonth(fb, historicData);        
        chartCurrentMonth = getChartValues(monthlyData, out, currentCalendar);
        ///System.out.println("EL CALENDARIO ACTUAL: " + currentCalendar);
        //System.out.println("---->" + historicData);
        System.out.println("Entries recovered::::" + historicData.length());
        try{
        Calendar recentPost = Calendar.getInstance();
        Calendar olderPost = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));

        if(historicData.length()>0){            
            if(!historicData.getJSONObject(0).isNull("created_time")){
                Date postTime = formatter.parse(historicData.getJSONObject(0).getString("created_time"));
                recentPost.setTime(postTime);
            }
            if(!historicData.getJSONObject(historicData.length()-1).isNull("created_time")){
                Date postTime = formatter.parse(historicData.getJSONObject(historicData.length()-1).getString("created_time"));
                olderPost.setTime(postTime);
            }
            ///System.out.println("RECENTE POST::" + recentPost.getTime());
            ///System.out.println("OLDEST POST::" + olderPost.getTime());
            
            ArrayList<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for (int i = 0; i < historicData.length(); i++){
               jsonValues.add(historicData.getJSONObject(i));
            }

            Collections.sort(jsonValues, new OrderByLikesComparator());
            for(int i = 0; i < jsonValues.size(); i++){
                ///out.print("<p>");
                boolean likes = false;
                if(!jsonValues.get(i).isNull("likes")){            
                    if(!jsonValues.get(i).getJSONObject("likes").isNull("summary")){
                        if(!jsonValues.get(i).getJSONObject("likes").getJSONObject("summary").isNull("total_count")){
                            likes =true;
                            ///out.print("\nLikes:" + jsonValues.get(i).getJSONObject("likes").getJSONObject("summary").getInt("total_count") + "-->");                            
                        }
                    }
                }
                if(likes== false){
                    ///out.print("\nlikes:0 ");
                }
                ///out.print( jsonValues.get(i).getString("created_time") );
                if(!jsonValues.get(i).isNull("message")){
                    ///out.print(jsonValues.get(i).getString("message") + "</p>");
                }else{
                    ///out.print("</p>");
                }
                if(i == 9)break;
            }
            
            Collections.sort(jsonValues, new OrderByCommentsComparator());
            for(int i = 0; i < jsonValues.size(); i++){
                //out.print("<p>");
                boolean likes = false;
                if(!jsonValues.get(i).isNull("comments")){            
                    if(!jsonValues.get(i).getJSONObject("comments").isNull("summary")){
                        if(!jsonValues.get(i).getJSONObject("comments").getJSONObject("summary").isNull("total_count")){
                            likes =true;
                            //out.print("\nComments:" + jsonValues.get(i).getJSONObject("comments").getJSONObject("summary").getInt("total_count"));                            
                        }
                    }
                }
                if(likes== false){
                    //out.print("\nComments:0 ");
                }
                //out.print( jsonValues.get(i).getString("created_time") );
                if(!jsonValues.get(i).isNull("message")){
                   // out.print(jsonValues.get(i).getString("message") + "</p>");
                }else{
                    //out.print( jsonValues.get(i) +"</p>");
                }
                //if(i==0)out.print("<p>" + jsonValues.get(i) + "</p>");
                if(i == 9)break;
            }
        }
               }catch(Exception e){
               System.out.println("\n\n\n\n" + "EEEERRRRORRRR");
                   e.printStackTrace();
               }
        //if(1==1)return;
        /*String wsite = fb.getSemanticObject().getModel().getName();
        
        String username;
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", fb.getAccessToken());
        String user = postRequest(params, "https://graph.facebook.com/me",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        //System.out.println("user:"+ user);
        JSONObject userObj = new JSONObject(user);
        if(!userObj.isNull("name")){
            username = userObj.getString("name");
        }else{
            username = fb.getTitle();
        }*/
        

    %>         
<%
        
        /*SWBModel model=WebSite.ClassMgr.getWebSite(fb.getSemanticObject().getModel().getName());        
        params.put("limit", "100");
        params.put("fields", "id,from,to,message,message_tags,story,story_tags,picture,caption,link,object_id,application,source,name,description,properties,icon,actions,privacy,type,status_type,created_time,likes.summary(true),comments.limit(5).summary(true),place,icon");
        String since = (String)session.getAttribute("since");
        //System.out.println("session.getAttribute(since):" + session.getAttribute("since"));
        
        //Genera la primera peticion de posts
        //Solo se deben obtener estadisticas del ultimo mes
        
        boolean endOfMonth = false;//get all posts of the last month with activity
        do{
            String fbResponse = postRequest(params, "https://graph.facebook.com/SemanticWebBuilder/feed",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
            JSONObject myPosts = new JSONObject(fbResponse);
            if(!myPosts.isNull("data")){
                JSONArray postsData = myPosts.getJSONArray("data");

                chartCurrentMonth = lastMonthData(out, postsData, chartCurrentMonth);
            }else{
                endOfMonth = true;
            }
        }while(endOfMonth == false);

        String fbResponse = postRequest(params, "https://graph.facebook.com/SemanticWebBuilder/feed",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        */
        /*JSONObject myPosts = new JSONObject(fbResponse);
        
        if(!myPosts.isNull("data")){
            JSONArray postsData = myPosts.getJSONArray("data");
            
            chartCurrentMonth = lastMonthData(out, postsData, chartCurrentMonth);
        }
        String untilPost = parseResponse(fbResponse, out, true, request, paramRequest, model);//Gets the newest post and saves the ID of the last one
        out.println(fbResponse);*/
    
%>

    <%
        }catch(Exception e){
        out.print("Problem displaying Wall: " + e.getMessage());
    }
%>




<link href="/swbadmin/css/nv.d3.css" rel="stylesheet" type="text/css">

<style>

body {
  overflow-y:scroll;
}

text {
  font: 12px sans-serif;
}

svg {
  display: block;
}

#chart1 svg{
  height: 500px;
  min-width: 100px;
  min-height: 100px;
/*
  margin: 10px;
  Minimum height and width is a good idea to prevent negative SVG dimensions...
  For example width should be =< margin.left + margin.right + 1,
  of course 1 pixel for the entire chart would not be very useful, BUT should not have errors
*/
}

</style>
<div>
    <div align="center" style="width:100%">
        ACTIVIDAD DEL &Uacute;LTIMO MES EN LA RED SOCIAL.
    </div>
  <div id="chart1">
    <svg></svg>
  </div>
</div>

<div class="clear"></div>

<script src="/work/models/SWBAdmin/js/d3.v3.js"></script>
<script src="/work/models/SWBAdmin/js/nv.d3.js"></script>
<script>





historicalBarChart = [ 
  {
    //key is year_month(1-12)
    key: "<%=currentCalendar.get(Calendar.YEAR) +"_" + (currentCalendar.get(Calendar.MONTH)) %>",
    values: [
        <%
        SimpleDateFormat month = new SimpleDateFormat("MMMM", new Locale("es", "MX"));
        try{
            System.out.println("chartCurrentMonth.length_____::" + chartCurrentMonth.length);
        }catch(Exception e){
            System.out.println("ERRRORR OHOHOHO");
            e.printStackTrace();
        }
        for(int i = 0; i <chartCurrentMonth.length; i++ ){
            Calendar byDay = Calendar.getInstance();
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");            
            //Dia del mes actual
            Date date = sdf.parse("" + currentCalendar.get(Calendar.YEAR) + "-" + (currentCalendar.get(Calendar.MONTH)) + "-" + (i+1));
            byDay.setTime(date);
            //String dayStr = getDayOfWeek(byDay.get(Calendar.DAY_OF_WEEK));
            SimpleDateFormat output = new SimpleDateFormat("EEEE dd 'de' MMMM 'de' yyyy", new Locale("es", "MX"));
            %>
        {
            "label": "<%=i+1%>",
            "value": <%=chartCurrentMonth[i]%>,
            "dayOfMonth": "<%=output.format(date)%>"
        }
            <%
            if(i < (chartCurrentMonth.length-1))out.println(",");
        }%>      
    ]
  }
];




nv.addGraph(function() {
  var chart = nv.models.discreteBarChart()
      .x(function(d) { return d.label })
      .y(function(d) { return d.value })
      .staggerLabels(true)
      .tooltips(true)
      .showValues(true)
      .transitionDuration(250)
      .margin({top: 30, right: 20, bottom: 60, left: 80})
      <%System.out.println("CURRREEEEEEENT:" + currentDate);%>
      chart.yAxis.axisLabel("Numero de posts")
      chart.xAxis.axisLabel("Actividad por dia del mes de <%=month.format(currentDate)%>")
      chart.valueFormat(d3.format('d'))
      chart.yAxis.tickFormat(d3.format('d'))
      
      d3.select('#chart1 svg')
      .datum(historicalBarChart)
      .call(chart);

  nv.utils.windowResize(chart.update);

  return chart;
});


</script>
<div class="clear"></div>
<div align="center" style="width:100%">
    <p>ACTIVIDAD RECIENTE POR LIKES Y COMENTARIOS.</p>
</div>
<div class="clear"></div>
<style type="text/css">

        @import "/swbadmin/js/dojo/dojo/resources/dojo.css";
        @import "/swbadmin/css/swbsocial.css";          
        html, body, #main{
            overflow: auto;
        }
    </style>
    <script src="http://d3js.org/d3.v3.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb.js"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb_admin.js"></script>
    <script type="text/javascript" charset="utf-8" src="/swbadmin/js/schedule.js"></script>
    <script type="text/javascript" charset="utf-8" src="/work/models/SWBAdmin/js/swbsocial.js" ></script>


<%
        /*out.println("<div dojoType=\"dijit.layout.ContentPane\"/>");

        out.println("<div dojoType=\"dijit.layout.TabContainer\" region=\"center\" style_=\"border:0px; width:100%; height:100%\" id=\"tabs/twitter\" _tabPosition=\"bottom\" nested_=\"true\" _selectedChild=\"btab1\" onButtonClick_=\"alert('click');\" onLoad_=\"alert('Hola');\">");        
        //out.println("<div class=\"timelineTab-title\" style=\"width: 620px !important;\"><p style=\"width:620px\"><strong>" + "Mis Videos" + "</strong>" + semanticYoutube.getTitle() + "</p></div>");
        out.println("<div id=\"toplikes\" dojoType=\"dijit.layout.ContentPane\" title=\""+"Home"+"\" refreshOnShow=\""+"false"+"\" _loadingMessage=\"\" style_=\"border:0px; width:100%; height:100%\">");
        out.println("<div class=\"timelineTab\" style=\"padding:10px 5px 10px 5px; overflow-y: scroll; height: 400px; margin-left:25%\">");
        out.println("<div class=\"timelineTab-title\" style=\"width: 50% !important;\"><p style=\"width:50%\"><strong>" + "Top Ten Likes" + "</strong>Mensajes con m&aacute;s likes</p></div>");
        out.println("</div>");
        
        out.println("<div id=\"topComments\" dojoType=\"dijit.layout.ContentPane\" title=\""+"Home"+"\" refreshOnShow=\""+"false"+"\" _loadingMessage=\"\" style_=\"border:0px; width:100%; height:100%\">");
        out.println("<div class=\"timelineTab\" style=\"padding:10px 5px 10px 5px; overflow-y: scroll; height: 400px;\">");
        out.println("<div class=\"timelineTab-title\"><p><strong>" + "Top Ten Comments" + "</strong>Mensajes con m&aacute;s comentarios</p></div>");        
        out.println("</div>");
        
        out.println("</div><!-- end Bottom TabContainer -->");*/
%>