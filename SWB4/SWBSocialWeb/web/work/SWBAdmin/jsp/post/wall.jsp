<%-- 
    Document   : wall
    Created on : 8/04/2013, 10:10:48 AM
    Author     : francisco.jimenez
--%>

<%@page import="java.util.TimeZone"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DateFormat"%>
<%@page import="org.semanticwb.social.ExternalPost"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.semanticwb.social.admin.resources.SocialUserStreamListener"%>
<%@page import="java.io.Writer"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.semanticwb.social.admin.resources.Timeline"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="org.semanticwb.social.Twitter"%>
<%@page import="org.semanticwb.social.base.TwitterBase"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.semanticwb.social.SocialTopic"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.text.*"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>

<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<jsp:useBean id="facebookBean" scope="request" type="org.semanticwb.social.Facebook"/>
<%@page import="static org.semanticwb.social.admin.resources.FacebookWall.*"%>

<%@page contentType="text/html" pageEncoding="x-iso-8859-11"%>
<%!
public String postRequest(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {
        
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url + "?" +  paramString);
        //URL serverUrl = new URL(url);
        //System.out.println("URL:" +  serverUrl);
        //System.out.println("paramString:" + paramString);
        
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
             
                //System.out.println("ERROR < 400:" + getResponse(conex.getInputStream()));
                System.out.println("CONNECT:" + conex);
                 //   out = conex.getOutputStream();
                 //   out.write(paramString.toString().getBytes("UTF-8"));
                in = conex.getInputStream();
                response = getResponse(in);
                System.out.println("RESPONSE:" + response);
                        
        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                System.out.println("ERROR:" +   getResponse(conex.getErrorStream()));
            }
            ioe.printStackTrace();
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

public boolean parseResponse(String response, JspWriter out, HttpServletRequest request, SWBParamRequest paramRequest) {
        
        boolean isThereMoreMsgs = false;
        
        try {           
                        boolean isResponseEmpty = false;
                        //JSONObject phraseResp = mainObject.getJSONObject(j);
                        JSONObject phraseResp = new JSONObject(response);
                        System.out.println("Tamanio del arreglo:" + phraseResp.length());                       
                        //if (phraseResp.getInt("code") != 200) {
                            //Si hubo un problema con la consulta, se extrae la descripci?n del problema
                            /*StringBuilder errorMsg = new StringBuilder(128);
                            errorMsg.append("Error en la extracci?n de datos de Facebook (");
//                            errorMsg.append(queriesArray[j].get("phrase"));
                            errorMsg.append("): ");
                            
                            JSONObject jsonError = new JSONObject(phraseResp.getString("body"));
                            
                            errorMsg.append(jsonError.getJSONObject("error").getString("type"));
                            errorMsg.append(".- ");
                            errorMsg.append(jsonError.getJSONObject("error").getString("message"));
                            
                            log.error(errorMsg.toString());
                            continue;*/
                        //} else
                            int cont = 0;
                            JSONArray postsData = phraseResp.getJSONArray("data");
                            System.out.println("ARREGLO DE DATOS:" + postsData.length());
                            for (int k = 0; k < postsData.length(); k++) {
                                cont++;
                                imprime(out,  postsData.getJSONObject(k), request, paramRequest);                                
                                //System.out.println("JSONProperty:" + postsData.getJSONObject(k).);
                            }
//                            //Si el ArrayList tiene tama?o mayor a 0, entonces es que existen mensajes para enviar al clasificador
//                            if(aListExternalPost.size()>0)
//                            {
//                                new Classifier(aListExternalPost, stream, this);
//
                        //}
//                            if (cont == Facebook.QUERYLIMIT) {
//                                isThereMoreMsgs = true;  //Esto indica la posibilidad de que en una consulta siguiente, se obtengan m?s mensajes
//                            } else if (cont == 0) {
//                                isResponseEmpty = true;
//                            }
                            
                            //Se obtiene la url de la siguiente consulta a realizar para la frase correspondiente
                            /*
                            String nextPage = null;
                            if (dataOnBody.has("paging")) {
                                nextPage = dataOnBody.getJSONObject("paging").getString("next");
                            } else if (isResponseEmpty) {
                                //si la busqueda no tuvo resultados, se repetira para la proxima ejecucion
                                nextPage = queriesArray[j].get("relative_url");
                                if (!nextPage.startsWith(Facebook.FACEBOOKGRAPH)) {
                                    nextPage = Facebook.FACEBOOKGRAPH + nextPage;
                                }
                            }
                            
                            queriesArray[j].put("nextQuery", nextPage);
                            queriesArray[j].put("msgCounted", Integer.toString(cont));
                            * */
        } catch (JSONException jsone) {
            
            System.out.println("Problemas al parsear respuesta de Facebook" + jsone);
        }
        
        return isThereMoreMsgs;
    }

void imprime(JspWriter writer, JSONObject postsData, HttpServletRequest request, SWBParamRequest paramRequest){
    try{
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        actionURL.setParameter("suri",request.getParameter("suri"));
        
        
        writer.write("<fieldset>");
        writer.write("<table style=\"width: 100%; border: 0px\">");
        writer.write("<tr>");
        writer.write("   <td colspan=\"2\">");
        writer.write("      <a href=\"http://facebook.com/" + postsData.getJSONObject("from").getString("id") + "\" target=\"_blank\">" + postsData.getJSONObject("from").getString("name") + "</a>  ");
        writer.write("   </td>");
        writer.write("</tr>");
        writer.write("<tr>");
        writer.write("   <td  width=\"10%\">"); 
        writer.write("       <img src=\"http://graph.facebook.com/" + postsData.getJSONObject("from").getString("id") +"/picture\"/>");
        writer.write("   </td>");
        writer.write("   <td width=\"90%\">");                
        String story = postsData.has("story") ? postsData.getString("story") : ""; // It's necessary to include the URL for media, hash tags and usernames
        String message = postsData.has("message") ? postsData.getString("message") : ""; // It's necessary to include the URL for media, hash tags and usernames
        //writer.write(        statusText);
        writer.write(story + message);        
        writer.write("   </td>");
        writer.write("</tr>");
        writer.write("<tr>");
        writer.write("   <td colspan=\"2\">");
        writer.write("<div id=\"" + postsData.getString("id") + "\" dojoType=\"dijit.layout.ContentPane\">");
        
        String facebookDate = postsData.getString("created_time");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");    
        formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));  
        Date localDateTime = formatter.parse(facebookDate);
        
        long minutes = (long)(new Date().getTime()/60000) - (localDateTime.getTime()/60000);
        writer.write("Created:<b>" + (int)minutes + "</b> minutes");

        boolean iLikedPost = false;
        if(postsData.has("likes")){
            writer.write(" Likes: <b>");
            JSONArray likes = postsData.getJSONObject("likes").getJSONArray("data");
            for (int k = 0; k < likes.length(); k++) {
                writer.write(likes.getJSONObject(k).getString("name") + ", ");
                if(likes.getJSONObject(k).getString("id").equals("100000536460020")){
                    //My User id is in 'the likes' of this post
                    iLikedPost = true;
                }
            }
            writer.write("</b>");
        }
        if(iLikedPost){
            writer.write(" <a href=\"\"  onclick=\"submitUrl('" + actionURL.setAction("doUnlike").setParameter("commentID", postsData.getString("id")).toString() + "',this);return false;" +"\">Unlike</a>");
        }else{
            writer.write(" <a href=\"\"  onclick=\"submitUrl('" + actionURL.setAction("doLike").setParameter("commentID", postsData.getString("id")).toString() + "',this);return false;" +"\">Like</a>");
        }
        
        
        /*
        writer.write("<div id=\"" + status.getId() + "\" dojoType=\"dijit.layout.ContentPane\">");
        long minutes = (long)(new Date().getTime()/60000) - (status.getCreatedAt().getTime()/60000);
        writer.write("Created:<b>" + (int)minutes + "</b> minutes ago - - Retweeted: <b>" + status.getRetweetCount() + "</b> times ");                    
        writer.write("<a href=\"\" onclick=\"showDialog('" + renderURL.setMode("replyTweet").setParameter("id", status.getId()+"").setParameter("userName", "@" + status.getUser().getScreenName()) + "','Reply to @" + status.getUser().getScreenName() + "');return false;\">Reply</a>  ");
        if(status.isRetweetedByMe()){
            actionURL.setAction("undoRT");
            //writer.write("<a href=\"\"  onclick=\"submitUrl('" + action.setParameter("id", status.getId()+"").toString() + "',this);return false;" +"\">Undo Retweet</a>");
            writer.write("Undo Retweet");
        }else if(status.getUser().getId() == currenUser){ //User cannot retweet its own tweet
            writer.write("My tweet");
        }else{
            actionURL.setAction("doRT");
            writer.write("<a href=\"#\"  onclick=\"postHtml('" + actionURL.setParameter("id", status.getId()+"") + "','" + status.getId() + "'); return false;" +"\">Retweet</a>");
        }*/
        
        writer.write("   </div>");
        writer.write("   </td>");
        
        writer.write("</tr>");          
        writer.write("</table>");
        writer.write("</fieldset>");               
       }catch(Exception e){
           System.out.println("ERROR");
           e.printStackTrace();
       }
    return;
}
%>
<%
    HashMap<String, String> params = new HashMap<String, String>(2);
    params.put("access_token", facebookBean.getAccessToken());
    params.put("limit", "6");
    System.out.println("SURI jsp: " + paramRequest.getMode().toString());
    
    //params.put("callback", "?");
    String fbResponse = postRequest(params, "https://graph.facebook.com/me/home",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
    boolean t = parseResponse(fbResponse, out, request, paramRequest);
%>