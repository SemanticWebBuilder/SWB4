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

            in = conex.getInputStream();
            response = getResponse(in);

        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                //System.out.println("ERROR:" +   getResponse(conex.getErrorStream()));
                response = getResponse(conex.getErrorStream());
            }
            //ioe.printStackTrace();
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
                            //System.out.println("\n\n\n*****first created time:" + postTime + " -->" +lastPostDate.getTime());
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
                                    //System.out.print("adding--> " + monthPost.getTime() + "---->");
                                    /*if(!postsData.getJSONObject(i).isNull("message")){
                                        System.out.println(postsData.getJSONObject(i).getString("message"));
                                    }else{
                                        System.out.println(postsData.getJSONObject(i).getString("story"));
                                    }*/
                                    monthlyData.put( postsData.getJSONObject(i));
                                    historicData.put( postsData.getJSONObject(i));
                            }else{//The wall is from newest to oldests
                               historicData.put( postsData.getJSONObject(i));
                               if(historicData.length() >= 200){
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
                                //System.out.println("NEXT PARAM" +e.getMessage());
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
                monthPost.setTime(postTime);  
                currentCalendar.setTime(postTime); ;
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
    //System.out.println("\n\n**currentCalendar:" + currentCalendar.getTime() + "\n**");
    Date currentDate = null;
    JSONArray historicData = new JSONArray();
    try{
        String suri = request.getParameter("suri");
        SocialNetwork sn = (SocialNetwork) SemanticObject.createSemanticObject(suri).createGenericInstance();
        Facebook fb = null;
        if(!(sn instanceof Facebook)){return;}else{fb = (Facebook)sn;}
        if(!fb.isSn_authenticated() || fb.getAccessToken() == null){
            out.println("<link href=\"/swbadmin/css/swbsocial.css\" rel=\"stylesheet\" type=\"text/css\">");
            out.println("<div id=\"configuracion_redes\">");
            out.println("<div id=\"autenticacion\">");
            out.println("<p>      La cuenta no ha sido autenticada correctamente</p>");
            out.println("</div>");
            out.println("</div>");
            return;
        }
        
        JSONArray monthlyData = getAllPostFromLastMonth(fb, historicData);        
        chartCurrentMonth = getChartValues(monthlyData, out, currentCalendar);
        currentDate = currentCalendar.getTime();//sdf.parse("" + currentCalendar.get(Calendar.YEAR) + "-" + (currentCalendar.get(Calendar.MONTH)+1) + "-" + currentCalendar.get(Calendar.DAY_OF_MONTH));
        //System.out.println("EL CALENDARIO ACTUAL: " + currentCalendar.getTime());
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
        }
               }catch(Exception e){
               System.out.println("\n\n\n\n" + "EEEERRRRORRRR");
                   e.printStackTrace();
               }
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

        for(int i = 0; i <chartCurrentMonth.length; i++ ){
            Calendar byDay = Calendar.getInstance();
            //Dia del mes actual
            Date date = sdf.parse("" + currentCalendar.get(Calendar.YEAR) + "-" + (currentCalendar.get(Calendar.MONTH)+1) + "-" + (i+1));
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
    <p>TOP TEN DE MENSAJES POR LIKES Y COMENTARIOS.</p>
</div>
<div class="clear"></div>

<%
            ArrayList<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for (int i = 0; i < historicData.length(); i++){
               jsonValues.add(historicData.getJSONObject(i));
            }

            Collections.sort(jsonValues, new OrderByLikesComparator());
            out.println("<div style=\"padding: 10px; float: left; width: 45%; text-align: justify;\">");
            out.println("<table width=\"100%\">"+
                "<tr bgcolor=\"#ffc46a\">" +
                    "<th style=\"color: #cc6600;\">Posici&oacute;n</th>" +
                    "<th style=\"color: #cc6600;\">Mensaje</th>" +
                    "<th style=\"color: #cc6600;\">Likes</th>" +
                "</tr>");
            
            for(int i = 0; i < jsonValues.size(); i++){
                if(!jsonValues.get(i).isNull("likes")){            
                    if(!jsonValues.get(i).getJSONObject("likes").isNull("summary")){
                        if(!jsonValues.get(i).getJSONObject("likes").getJSONObject("summary").isNull("total_count")){
                            String msgText = "";
                            
                            if (!jsonValues.get(i).isNull("story")) {
                                msgText = jsonValues.get(i).getString("story");
                            }else if (!jsonValues.get(i).isNull("message")) {
                                msgText = jsonValues.get(i).getString("message");
                            }
                            
                            if(msgText.length() > 200){
                                msgText = msgText.substring(0, 200) + "...";
                            }
                            out.println("<tr" + (i%2==0 ? " bgcolor=\"#fff7e2\"": "") + ">");
                            out.println("    <td>" + (i+1) +"</td>");
                            out.println("    <td>" + "<a href=\"" + getLinkFromId(jsonValues.get(i).getString("id")) + "\" target=\"_blank\">" + msgText + "</a>" + "</td>");
                            out.println("   <td>" + jsonValues.get(i).getJSONObject("likes").getJSONObject("summary").getInt("total_count") + "</td>");
                            out.println("</tr>");
                        }
                    }
                }
                if(i == 9)break;
            }
            out.println("  </table>");
            out.println("</div>");
            
            out.println("<div style=\"padding: 10px; float: right; width: 45%; text-align: justify;\">");
            out.println("<table width=\"100%\">"+
                "<tr bgcolor=\"#ffc46a\">" +
                    "<th style=\"color: #cc6600;\">Posici&oacute;n</th>" +
                    "<th style=\"color: #cc6600;\">Mensaje</th>" +
                    "<th style=\"color: #cc6600;\">Comentarios</th>" +
                "</tr>");
            
            Collections.sort(jsonValues, new OrderByCommentsComparator());
            for(int i = 0; i < jsonValues.size(); i++){
                if(!jsonValues.get(i).isNull("comments")){
                    if(!jsonValues.get(i).getJSONObject("comments").isNull("summary")){
                        if(!jsonValues.get(i).getJSONObject("comments").getJSONObject("summary").isNull("total_count")){
                            String msgText = "";
                            
                            if (!jsonValues.get(i).isNull("story")) {
                                msgText = jsonValues.get(i).getString("story");
                            }else if (!jsonValues.get(i).isNull("message")) {
                                msgText = jsonValues.get(i).getString("message");
                            }
                            
                            if(msgText.length() > 200){
                                msgText = msgText.substring(0, 200) +"...";
                            }
                            out.println("<tr" + (i%2==0 ? " bgcolor=\"#fff7e2\"": "") + ">");
                            out.println("    <td>" + (i+1) +"</td>");
                            out.println("    <td>" + "<a href=\"" + getLinkFromId(jsonValues.get(i).getString("id")) + "\" target=\"_blank\">" + msgText + "</a>" + "</td>");
                            out.println("   <td>" + jsonValues.get(i).getJSONObject("comments").getJSONObject("summary").getInt("total_count") + "</td>");
                            out.println("</tr>");
                        }
                    }
                }
                if(i == 9)break;
            }
            out.println("  </table>");
            out.println("</div>");
%>

<%!
    public static String getLinkFromId(String id){
        String link = "#";
            if(id.contains("_")){
                String user = id.split("_")[0];
                String post = id.split("_")[1];
                link = "https://facebook.com/" + user + "/posts/" + post;
            }
        return link;
    }
%>