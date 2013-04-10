/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.ExternalPost;
import org.semanticwb.social.Facebook;
import org.semanticwb.social.Stream;
import org.semanticwb.social.listener.Classifier;
import twitter4j.TwitterStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBResourceURL;
import twitter4j.Status;
import twitter4j.TwitterException;
/**
 *
 * @author francisco.jimenez
 */
public class FacebookWall extends GenericResource {
    private static Logger log = SWBUtils.getLogger(FacebookWall.class);
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String objUri = (String) request.getParameter("suri");
        System.out.println("SURI:" + objUri);
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook)semanticObject.createGenericInstance();
        String jspResponse = SWBPlatform.getContextPath() +"/work/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/wall.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("facebookBean", facebook);
            dis.include(request, response);
        } catch (Exception e) {
            log.error("Error in doView() for requestDispatcher" , e);
        }        
        
        /*
        String objUri = (String) request.getParameter("suri");
        System.out.println("SURI:" + objUri);
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Facebook facebook = (Facebook)semanticObject.createGenericInstance();
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", facebook.getAccessToken());
        params.put("limit", "3");
        //params.put("callback", "?");
        String fbResponse = postRequest(params, "https://graph.facebook.com/" + facebook.getFacebookUserId() + "/feed",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
        parseResponse(fbResponse);
        */
        /*
        //Start the listener
        TwitterStream twitterStream = null;             //The stream
        SocialUserStreamListener tweetsListener = null; //The listener reads tweets received in the home timeline
        HttpSession session = request.getSession(true);

        if((session.getAttribute("twitterStream") == null && session.getAttribute("tweetsListener") == null) ||
                ((SocialUserStreamListener)session.getAttribute("tweetsListener")).streamActive == false){ //If no stream found for the current session, create one.            
            System.out.println("Listener started!");
        }else{
             if(session.getAttribute("tweetsListener") != null){//If the tab is refreshed, clean all 'new' statuses in ArrayList
                 ((SocialUserStreamListener)session.getAttribute("tweetsListener")).socialStatus.clear();
                 ((SocialUserStreamListener)session.getAttribute("tweetsListener")).startTime = System.currentTimeMillis();
             }
        }
        
        String jspResponse = SWBPlatform.getContextPath() +"/work/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/timeline.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        try {
            request.setAttribute("paramRequest", paramRequest);
            //request.setAttribute("twitterBean", twitter);
            dis.include(request, response);
        } catch (Exception e) {
            log.error("Error in doView() for requestDispatcher" , e);
        }         
        */
    }        
    
    private static String postRequest(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {
        
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        //URL serverUrl = new URL(url + "?" +  paramString);
        URL serverUrl = new URL(url);
        System.out.println("URL:" +  serverUrl);
        System.out.println("paramString:" + paramString);
        
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
                out = conex.getOutputStream();
                out.write(paramString.toString().getBytes("UTF-8"));
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
    
    private static String getRequest(Map<String, String> params, String url,
            String userAgent) throws IOException {
        
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = new URL(url + "?" +  paramString);       
        System.out.println("URL:" +  serverUrl);
        
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
            System.out.println("CONNECT:" + conex);
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
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }
    
    /*
    public void listen(Stream stream) {
        
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", this.getAccessToken());
        boolean canGetMoreResults = true;
        String phrasesInStream = stream.getPhrase() != null ? stream.getPhrase() : "";
        int queriesNumber = phrasesInStream.split("\\|").length * 2;
        HashMap<String, String>[] queriesArray = new HashMap[queriesNumber];
        
        try {
            //Como Facebook proporciona los datos de un conjunto definido de 
            //mensajes a la vez, se necesita pedir esta información por bloques
            do {
                //Genera todas las consultas a ejecutar en Facebook, una por frase almacenada en el Stream
                generateBatchQuery(phrasesInStream, queriesArray, stream);
                
                //printMap(queriesArray);
                if (queriesArray != null && queriesArray[0].containsKey("relative_url")) {
                    
                    params.put("batch", renderFacebookQueries(queriesArray));
                    String fbResponse = postRequest(params, Facebook.FACEBOOKGRAPH,
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");

                    //Se analiza la respuesta de Facebook y se extraen los datos de los mensajes
                    canGetMoreResults = parseResponse(fbResponse, stream, queriesArray);
                }
                
            } while (canGetMoreResults);
            
            //Almacena los nuevos limites para las busquedas posteriores en Facebook
            storeSearchLimits(queriesArray, stream);

        } catch (Exception jsone) {
            log.error("JSON al usar queries construidos", jsone);
            jsone.printStackTrace();
        }
    }
    */
    
    public static CharSequence delimit(Collection<Map.Entry<String, String>> entries,
            String delimiter, String equals, boolean doEncode)
            throws UnsupportedEncodingException {

        if (entries == null || entries.isEmpty()) {
            return null;
        }
        StringBuilder buffer
                = new StringBuilder(64);
	boolean notFirst = false;
        for (Map.Entry<String, String> entry : entries ) {
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

    /**
     * Codifica el valor de {@code target} de acuerdo al c&oacute;digo de caracteres UTF-8
     * @param target representa el texto a codificar
     * @return un {@code String} que representa el valor de {@code target} de acuerdo al c&oacute;digo de caracteres UTF-8
     * @throws UnsupportedEncodingException en caso de ocurrir algun problema en la codificaci&oacute;n a UTF-8
     */
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
    
    public static void close( Closeable c ) {
        if ( c != null ) {
            try {
                c.close();
            }
            catch ( IOException ex ) {
                log.error("Error at closing object: " + c.getClass().getName(),
                        ex);
            }
        }
    }
    
//    public static boolean parseResponse(String response) {
//        
//        boolean isThereMoreMsgs = false;
//        
//        try {
//            //JSONArray mainObject = new JSONArray(response);
//            //if (mainObject != null) {
//                //for (int j = 0; j < mainObject.length(); j++) {
//                  //  if (mainObject.getJSONObject(j) != null) {
//                        boolean isResponseEmpty = false;
//                        //JSONObject phraseResp = mainObject.getJSONObject(j);
//                        JSONObject phraseResp = new JSONObject(response);
//                        System.out.println("Tamanio del arreglo:" + phraseResp.length());
//                        if(1==1)return false;
//                        //if (phraseResp.getInt("code") != 200) {
//                            //Si hubo un problema con la consulta, se extrae la descripción del problema
//                            /*StringBuilder errorMsg = new StringBuilder(128);
//                            errorMsg.append("Error en la extracción de datos de Facebook (");
////                            errorMsg.append(queriesArray[j].get("phrase"));
//                            errorMsg.append("): ");
//                            
//                            JSONObject jsonError = new JSONObject(phraseResp.getString("body"));
//                            
//                            errorMsg.append(jsonError.getJSONObject("error").getString("type"));
//                            errorMsg.append(".- ");
//                            errorMsg.append(jsonError.getJSONObject("error").getString("message"));
//                            
//                            log.error(errorMsg.toString());
//                            continue;*/
//                        //} else
//                            if (phraseResp.getString("body").length() > 9) {
//                            //Se extraen todos los mensajes obtenidos en la respuesta de Facebook
//                            int cont = 0;
//                            JSONObject dataOnBody = new JSONObject(phraseResp.getString("body"));
//                            JSONArray postsData = dataOnBody.getJSONArray("data");
//                            ArrayList <ExternalPost> aListExternalPost=new ArrayList();
//                            for (int k = 0; k < postsData.length(); k++) {
//                                cont++;
//                                ExternalPost external = new ExternalPost();
//                                System.out.println("ID:" + postsData.getJSONObject(k).getString("id"));
//                                System.out.println("created_time:" + postsData.getJSONObject(k).getString("created_time"));
//                                external.setPostId(postsData.getJSONObject(k).getString("id"));
//                                external.setCreatorId(postsData.getJSONObject(k).getJSONObject("from").getString("id"));
//                                external.setCreatorName(postsData.getJSONObject(k).getJSONObject("from").getString("name"));
//                                external.setCreationTime(postsData.getJSONObject(k).getString("created_time"));
//                                external.setUpdateTime(postsData.getJSONObject(k).getString("updated_time"));
//                                if (postsData.getJSONObject(k).has("message")) {
//                                    external.setMessage(postsData.getJSONObject(k).getString("message"));
//                                }
//                                if (postsData.getJSONObject(k).has("description")) {
//                                    external.setDescription(postsData.getJSONObject(k).getString("description"));
//                                }
//                                if (postsData.getJSONObject(k).has("icon")) {
//                                    external.setIcon(postsData.getJSONObject(k).getString("icon"));
//                                }
//                                if (postsData.getJSONObject(k).has("link")) {
//                                    external.setLink(postsData.getJSONObject(k).getString("link"));
//                                }
//                                if (postsData.getJSONObject(k).has("picture")) {
//                                    external.setPicture(postsData.getJSONObject(k).getString("picture"));
//                                }
//                                if (postsData.getJSONObject(k).has("name")) {
//                                    external.setPostName(postsData.getJSONObject(k).getString("name"));
//                                }
//                                if (postsData.getJSONObject(k).has("type")) {
//                                    external.setPostType(postsData.getJSONObject(k).getString("type"));
//                                }
//                                aListExternalPost.add(external);
//                            }
////                            //Si el ArrayList tiene tamaño mayor a 0, entonces es que existen mensajes para enviar al clasificador
////                            if(aListExternalPost.size()>0)
////                            {
////                                new Classifier(aListExternalPost, stream, this);
////
//                        //}
////                            if (cont == Facebook.QUERYLIMIT) {
////                                isThereMoreMsgs = true;  //Esto indica la posibilidad de que en una consulta siguiente, se obtengan más mensajes
////                            } else if (cont == 0) {
////                                isResponseEmpty = true;
////                            }
//                            
//                            //Se obtiene la url de la siguiente consulta a realizar para la frase correspondiente
//                            /*
//                            String nextPage = null;
//                            if (dataOnBody.has("paging")) {
//                                nextPage = dataOnBody.getJSONObject("paging").getString("next");
//                            } else if (isResponseEmpty) {
//                                //si la busqueda no tuvo resultados, se repetira para la proxima ejecucion
//                                nextPage = queriesArray[j].get("relative_url");
//                                if (!nextPage.startsWith(Facebook.FACEBOOKGRAPH)) {
//                                    nextPage = Facebook.FACEBOOKGRAPH + nextPage;
//                                }
//                            }
//                            
//                            queriesArray[j].put("nextQuery", nextPage);
//                            queriesArray[j].put("msgCounted", Integer.toString(cont));
//                            * */
//                        }
//                    //}
//                //}
//            //}
//        } catch (JSONException jsone) {
//            log.error("Problemas al parsear respuesta de Facebook", jsone);
//        }
//        
//        return isThereMoreMsgs;
//    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        if(action.equals("doLike")){
            System.out.println("LIKE-Start");
            String objUri = (String) request.getParameter("suri");
            String commentID = (String)request.getParameter("commentID");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Facebook facebook = (Facebook)semanticObject.createGenericInstance();
            HashMap<String, String> params = new HashMap<String, String>(2);
            params.put("access_token", facebook.getAccessToken());
            String fbResponse = postRequest(params, "https://graph.facebook.com/" + commentID + "/likes",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
            System.out.println("Response: " + fbResponse);
            response.setRenderParameter("commentID", commentID);
            response.setRenderParameter("suri", objUri);
            response.setRenderParameter("liked", "ok");
            response.setRenderParameter("access_token", facebook.getAccessToken());
            System.out.println("LIKE-End");
            response.setMode("likeSent"); //show RT Message and update div
        }else if(action.equals("doUnlike")){
            System.out.println("UNLIKE-Start");
            String objUri = (String) request.getParameter("suri");
            String commentID = (String)request.getParameter("commentID");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Facebook facebook = (Facebook)semanticObject.createGenericInstance();
            HashMap<String, String> params = new HashMap<String, String>(2);
            params.put("access_token", facebook.getAccessToken());
            String fbResponse = postRequest(params, "https://graph.facebook.com/" + commentID + "/likes",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "DELETE");
            System.out.println("Response: " + fbResponse);
            response.setRenderParameter("commentID", commentID);
            response.setRenderParameter("suri", objUri);
            response.setRenderParameter("unliked", "ok");
            response.setRenderParameter("access_token", facebook.getAccessToken());
            System.out.println("UNLIKE-End");
            response.setMode("unlikeSent"); //show RT Message and update div
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        actionURL.setParameter("suri", request.getParameter("suri"));        
        PrintWriter out = response.getWriter();
                
        if(mode!= null && mode.equals("likeSent") || mode.equals("unlikeSent")){//Displays updated data of liked status
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            String commentID = request.getParameter("commentID");
            String accessToken = request.getParameter("access_token");
            
            try {
                HashMap<String, String> params = new HashMap<String, String>(2);
                params.put("access_token", accessToken);
                String fbResponse = getRequest(params, "https://graph.facebook.com/" + commentID,
                                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            
                System.out.println("Facebook response:" + fbResponse);
                JSONObject likeResp = new JSONObject(fbResponse);
                String facebookDate = likeResp.getString("created_time");
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSz");    
                formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));  
                Date localDateTime = formatter.parse(facebookDate);

                long minutes = (long)(new Date().getTime()/60000) - (localDateTime.getTime()/60000);
                out.write("Created:<b>" + (int)minutes + "</b> minutes");

                boolean iLikedPost = false;
                if(likeResp.has("likes")){
                    out.write(" Likes: <b>");
                    JSONArray likes = likeResp.getJSONObject("likes").getJSONArray("data");
                    for (int k = 0; k < likes.length(); k++) {
                        out.write(likes.getJSONObject(k).getString("name") + ", ");
                        if(likes.getJSONObject(k).getString("id").equals("100000536460020")){
                            //My User id is in 'the likes' of this post
                            iLikedPost = true;
                        }
                    }
                    out.write("</b>");
                }
                if(iLikedPost){
                    out.write(" <a href=\"\"  onclick=\"submitUrl('" + actionURL.setAction("doUnlike").setParameter("commentID", likeResp.getString("id")).toString() + "',this);return false;" +"\">Unlike</a>");
                }else{
                    out.write(" <a href=\"\"  onclick=\"submitUrl('" + actionURL.setAction("doLike").setParameter("commentID", likeResp.getString("id")).toString() + "',this);return false;" +"\">Like</a>");
                }
                
                if(request.getParameter("liked") != null && request.getParameter("liked").equals("ok")){
                    out.println("<script type=\"text/javascript\">");
                    out.println("   showStatus('Post liked successfully');");
                    out.println("</script>");
                }else if(request.getParameter("unliked") != null && request.getParameter("unliked").equals("ok")){
                    out.println("<script type=\"text/javascript\">");
                    out.println("   showStatus('Post unliked successfully');");
                    out.println("</script>");
                }                
            } catch (Exception ex) {
                log.error("Error when trying to retweet ", ex);
            }
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }        
}
