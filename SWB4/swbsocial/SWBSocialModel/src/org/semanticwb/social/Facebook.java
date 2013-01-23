package org.semanticwb.social;


import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SFBase64;
import org.semanticwb.io.SWBFile;
import org.semanticwb.io.SWBFileInputStream;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.listener.Classifier;


public class Facebook extends org.semanticwb.social.base.FacebookBase {
    
    
    private static final String PassPhrase = "f:,+#u4w=EkJ0R[";
    
    private static final String CRLF = "\r\n";    
    
    private static final String PREF = "--";    
    
    private static final String FACEBOOKGRAPH = "https://graph.facebook.com/";
    
    private static final short QUERYLIMIT = 100;
    
    private Logger log = SWBUtils.getLogger(Facebook.class);
    
    
    public Facebook(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void listen(Stream stream) {
        HashMap<String, String> params = new HashMap<String, String>(2);
//        String access_token = this.getAccessToken();
        params.put("access_token", this.getAccessToken());
//        JSONArray nextQueries = null;
        JSONArray batchQueries = null;
        JSONObject queries = new JSONObject();
        boolean canGetMoreResults = true;
        
        try {
            
            //Como Facebook proporciona los datos de un conjunto definido de 
            //mensajes a la vez, se necesita pedir esta información por bloques
            do {
                //Genera todas las consultas a ejecutar en Facebook, una por frase almacenada
                generateBatchQuery(queries, stream);
                
                if (queries.has("batch")) {
                    batchQueries = queries.getJSONArray("batch");
                    
                    params.put("batch", batchQueries.toString());
                    String fbResponse = postRequest(params, Facebook.FACEBOOKGRAPH,
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");

                    //Se analiza la respuesta de Facebook y se extraen los datos de los mensajes
                    //---System.out.println("Analizando respuesta de FB");
                    canGetMoreResults = parseResponse(fbResponse, queries, stream);
                    //---System.out.println("canGetMoreResults: " + canGetMoreResults);
                    
                /*} else {
                    //System.out.println("queries no tiene batch");*/
                }
                
            } while (canGetMoreResults);
            
            //Almacena los nuevos limites para las busquedas posteriores en Facebook
            storeSearchLimits(queries, stream);

        } catch (JSONException jsone) {
            log.error("JSON al usar queries construidos", jsone);
            jsone.printStackTrace();
        } catch (IOException ioe) {
            log.error("IO, al recibir informacion/generar archivo", ioe);
            ioe.printStackTrace();
        } catch (Exception e) {
            log.error("Exception, al ejecutar generateBatchQuery", e);
            e.printStackTrace();
        }
        
    }

    /**
     * Almacena los nuevos l&iacute;mites para las b&uacute;squedas posteriores en Facebook
     * @param queries contiene tanto los queryStrings a ejecutar en FB, como los
     *        url's devueltos por FB para las siguientes consultas
     * @param stream objeto en el que se almacenará la fecha del último post extraído de Facebook
     */
    private void storeSearchLimits(JSONObject queries, Stream stream) {
        
        StringBuilder limits = new StringBuilder(56);
        try {
            for (int i = 0; i < queries.getJSONArray("batch").length(); i++) {
                JSONObject batchQuery = queries.getJSONArray("batch").getJSONObject(i);
                String newQuery = queries.getJSONArray("searchPhrase").getJSONObject(i).getString("nextQuery");
                String[] params = null;
                
                if (i > 0) {
                    limits.append(":");  //se agrega separador entre valores
                }
                //Si se obtuvieron mensajes, hay un nuevo valor para el último mensaje leído, indicado en "nextQuery"
                if (newQuery.indexOf("search?") > 0) {
                    //Para consultas en toda la red social
                    params = newQuery.substring(newQuery.indexOf("?") + 1).split("&");
                    for (int j = 0; j < params.length; j++) {
                        if (params[j].startsWith("until=")) {
                            limits.append("search_");
                            limits.append(queries.getJSONArray("searchPhrase").getJSONObject(i).getString("phrase"));
                            limits.append("=");
                            limits.append(params[j].split("=")[1]);
                        }
                    }
                } else if (newQuery.indexOf("feed?") > 0) {
                    //Para consultas en el muro
                    params = newQuery.substring(newQuery.indexOf("?") + 1).split("&");
                    for (int j = 0; j < params.length; j++) {
                        if (params[j].startsWith("until=")) {
                            limits.append("feed_");
                            limits.append(queries.getJSONArray("searchPhrase").getJSONObject(i).getString("phrase"));
                            limits.append("=");
                            limits.append(params[j].split("=")[1]);
                        }
                    }
                }
            }
            if (limits.length() > 0) {
                this.setNextDatetoSearch(limits.toString());
                //System.out.println("Limites almacenados: " + limits.toString());
            }
        } catch (JSONException jsone) {
            log.error("Al formar URL de siguiente consulta a ejecutar en Facebook", jsone);
        }
    }
    
    /**
     * Genera las url's de las consultas a ejecutar en Facebook.
     * @param queries contiene tanto los queryStrings a ejecutar en FB, como los
     *        url's devueltos por FB para las siguientes consultas
     * @param streamobjeto del que se extrae la fecha del último post obtenido de Facebook
     */
    private void generateBatchQuery(JSONObject queries, Stream stream) throws Exception {
        
        JSONArray batch = new JSONArray();
        JSONArray searchPhrase = new JSONArray();
        //TODO: Acordar que las frases almacenadas se separen por pipes y que no contengan el símbolo =.
        String[] phrase = stream.getPhrase().split(":");
        HashMap<String, String> searchLimits = new HashMap<String, String>(phrase.length);
        
        if (!queries.has("batch")) {
            try {
            //Se obtienen los límites desde los cuales se harán consultas en Facebook
                //System.out.println("Nuevos límites: " + this.getNextDatetoSearch());
            if (this.getNextDatetoSearch() != null && !"".equals(this.getNextDatetoSearch())) {
                String[] phraseElements = this.getNextDatetoSearch().split(":");
                for (int i = 0; i < phrase.length; i++) {
                    String[] pair = phraseElements[i].split("=");
                    //Se quita de la frase el sufijo: search_ o feed_ correspondiente a cada tipo de busqueda
                    String key = null;
                    if (pair[0].startsWith("search_")) {
                        key = pair[0].substring(7);
                    /*} else if (pair[0].startsWith("feed_")) {
                        key = pair[0].substring(5);*/
                    }
                    String value = pair[1];
                    searchLimits.put(key, value);
                }
            }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e.getCause());
            }
            
            for (int i = 0; i < phrase.length; i++) {
                //Se crean consultas a Facebook por cada frase capturada
                JSONObject request = new JSONObject();
                //JSONObject wallRequest = new JSONObject();
                JSONObject jsonPhrase = new JSONObject();
                String publicQuery = null;
                //String wallQuery = null;
                String until = null;
                try {
                    request.put("method", "GET");
                    
                    if (searchLimits.containsKey(phrase[i]) && searchLimits.get(phrase[i]) != null) {
                        until = searchLimits.get(phrase[i]);
                    }
                    //Para la primera ejecución de este proceso se crean las url de las consultas públicas
                    publicQuery = "search?q=" + phrase[i] + "&type=post&limit="
                            + Facebook.QUERYLIMIT + (until != null ? "&until=" + until : "");
                    // Y las url de las consultas privadas
                    /*wallQuery = "me/feed?q=" + phrase[i] + "&type=post&limit="
                            + Facebook.QUERYLIMIT + (until != null ? "&until=" + until : "");*/
                    
                    request.put("relative_url", publicQuery);
                    jsonPhrase.put("phrase", phrase[i]);
                    /*wallRequest.put("method", "GET");
                    wallRequest.put("relative_url", wallQuery);*/
                    
                    batch.put(request); //consulta a la información pública de Facebook
                    searchPhrase.put(jsonPhrase);
                    /*batch.put(wallRequest); //consulta a la información privada de Facebook
                    searchPhrase.put(jsonPhrase);*/
                } catch (JSONException jsone) {}
            }
            
            try {
                queries.put("batch", batch);
                queries.put("searchPhrase", searchPhrase);
            } catch (JSONException jsone) {
                log.error("Al integrar consulta batch con siguiente consulta", jsone);
            }
            //System.out.println("queries 1a vez: " + queries.toString());
        } else {
            //En las segundas y posteriores iteraciones de este proceso, se usan las consultas "next" 
            //que se extraen de las respuestas obtenidas de Facebook:
            try {
                for (int i = 0; i < queries.getJSONArray("batch").length(); i++) {
                    JSONObject batchQuery = queries.getJSONArray("batch").getJSONObject(i);
                    String newQuery = queries.getJSONArray("searchPhrase").getJSONObject(i).getString("nextQuery");
                    int msgsObtained = queries.getJSONArray("searchPhrase").getJSONObject(i).getInt("msgCounted");
                    if (msgsObtained > 0) {
                        //Si se obtuvieron mensajes, hay un nuevo valor para el último mensaje leído, indicado en "nextQuery"
                        
                        //System.out.println("Se va a sustituir:\n" + batchQuery.getString("relative_url") + "\npor:\n ");
                        
                        if (newQuery.indexOf("search?") > 0) {
                            batchQuery.put("relative_url", newQuery.substring(newQuery.indexOf("search?") - 1));
                        /*} else if (newQuery.indexOf("me/feed?") > 0) {
                            batchQuery.put("relative_url", newQuery.substring(newQuery.indexOf("me/feed?") - 1));*/
                        }
                        
                        //System.out.println(batchQuery.getString("relative_url"));
                        
                    /*} else {
                        //Si no hubo resultados, se usará a continuación la misma consulta ejecutada anteriormente
                        System.out.println("Se vuelve a usar la misma query:\n" + batchQuery.getString("relative_url"));
                        */
                    }
                }
            } catch (JSONException jsone) {
                log.error("Al formar URL de siguiente consulta a ejecutar en Facebook", jsone);
            }
        }
        //System.out.println("queries armadas: \n" + queries.toString(3));
    }
    
    /**
     * Analiza la respuesta de Facebook y obtiene la información de los mensajes publicados en la red social.
     * En base a la estructura del objeto JSON devuelto por Facebook, se revisa que se haya podido responder
     * la petición, se obtiene la información de los mensajes publicados y se obtiene las url's de las 
     * siguientes consultas a ejecutar.
     * @param response
     * @param queries
     * @param stream
     * @return un booleano que indica si es posible que haya más mensajes publicados en la red al momento
     *         en que se contestó la petición, lo que indica que se tiene que realizar otra iteración
     */
    private boolean parseResponse(String response, JSONObject queries, Stream stream) {
        
        boolean isThereMoreMsgs = false;
        
        try {
            JSONArray mainObject = new JSONArray(response);
            if (mainObject != null) {
                
                for (int j = 0; j < mainObject.length(); j++) {
                    if (mainObject.getJSONObject(j) != null) {
                        
                        boolean isResponseEmpty = false;
                        JSONObject phraseResp = mainObject.getJSONObject(j);
                        if (phraseResp.getInt("code") != 200) {
                            //Si hubo un problema con la consulta, se extrae la descripción del problema
                            StringBuilder errorMsg = new StringBuilder(128);
                            errorMsg.append("Error en la extracción de datos de Facebook (");
                            errorMsg.append(queries.getJSONArray("searchPhrase").getJSONObject(j).getString("phrase"));
                            errorMsg.append("): ");
                            
                            JSONObject jsonError = new JSONObject(phraseResp.getString("body"));
                            
                            errorMsg.append(jsonError.getJSONObject("error").getString("type"));
                            errorMsg.append(".- ");
                            errorMsg.append(jsonError.getJSONObject("error").getString("message"));
                            
                            log.error(errorMsg.toString());
                            continue;
                        } else if (phraseResp.getString("body").length() > 9) {
                            //Se extraen todos los mensajes obtenidos en la respuesta de Facebook
                            int cont = 0;
                            JSONObject dataOnBody = new JSONObject(phraseResp.getString("body"));
                            JSONArray postsData = dataOnBody.getJSONArray("data");
                            for (int k = 0; k < postsData.length(); k++) {
                                cont++;
                                ExternalPost external = new ExternalPost();
                                external.setPostId(postsData.getJSONObject(k).getString("id"));
                                external.setCreatorId(postsData.getJSONObject(k).getJSONObject("from").getString("id"));
                                external.setCreatorName(postsData.getJSONObject(k).getJSONObject("from").getString("name"));
                                external.setCreationTime(postsData.getJSONObject(k).getString("created_time"));
                                external.setUpdateTime(postsData.getJSONObject(k).getString("updated_time"));
                                if (postsData.getJSONObject(k).has("message")) {
                                    external.setMessage(postsData.getJSONObject(k).getString("message"));
                                }
                                if (postsData.getJSONObject(k).has("description")) {
                                    external.setDescription(postsData.getJSONObject(k).getString("description"));
                                }
                                if (postsData.getJSONObject(k).has("icon")) {
                                    external.setIcon(postsData.getJSONObject(k).getString("icon"));
                                }
                                if (postsData.getJSONObject(k).has("link")) {
                                    external.setLink(postsData.getJSONObject(k).getString("link"));
                                }
                                if (postsData.getJSONObject(k).has("picture")) {
                                    external.setPicture(postsData.getJSONObject(k).getString("picture"));
                                }
                                if (postsData.getJSONObject(k).has("name")) {
                                    external.setPostName(postsData.getJSONObject(k).getString("name"));
                                }
                                //TODO: Enviar external al clasificador en cada iteracion.
                                //System.out.println("Mensaje " + cont + ": " + external.getMessage());
                                new Classifier(external, stream, this);
                                
                            }
                            if (cont == Facebook.QUERYLIMIT) {
                                isThereMoreMsgs = true;  //Esto indica la posibilidad de que en una consulta siguiente, se obtengan más mensajes
                            } else if (cont == 0) {
                                isResponseEmpty = true;
                            }
                            //System.out.println("Hubo " + cont + " comentarios para la frase " + queries.getJSONArray("searchPhrase").getJSONObject(j).getString("phrase"));
                            
                            //Se obtiene la url de la siguiente consulta a realizar para la frase correspondiente
                            String nextPage = null;
                            if (dataOnBody.has("paging")) {
                                nextPage = dataOnBody.getJSONObject("paging").getString("next");
                            } else if (isResponseEmpty) {
                                nextPage = queries.getJSONArray("batch").getJSONObject(j).getString("relative_url");
                                if (!nextPage.startsWith(Facebook.FACEBOOKGRAPH)) {
                                    nextPage = Facebook.FACEBOOKGRAPH + nextPage;
                                }
                            }
                            
                            JSONObject phrase = queries.getJSONArray("searchPhrase").getJSONObject(j);
                            phrase.put("nextQuery", nextPage);
                            phrase.put("msgCounted", cont);
                            //System.out.println("Mensajes para " + phrase.getString("phrase") + " = " + cont);
                            //TODO: Mostrar valores de nextPage y cont
                            //System.out.println("Queries después del parse: \n" + queries.toString(3));
                        }
                    }
                }
            }
        } catch (JSONException jsone) {
            log.error("Problemas al parsear respuesta de Facebook", jsone);
        }
        
        return isThereMoreMsgs;
    }
    
    public void postMsg(Message message, HttpServletRequest request,
                        SWBActionResponse response) {
        
        Map<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", this.getAccessToken());
        params.put("message", message.getMsg_Text());
        String url = Facebook.FACEBOOKGRAPH + this.getFacebookUserId() + "/feed";
        JSONObject jsonResponse = null;
        String facebookResponse = "";
        
        try {
            facebookResponse = postRequest(params, url, request.getHeader(""), "POST");
            jsonResponse = new JSONObject(facebookResponse);
            if (jsonResponse != null && jsonResponse.get("id") != null) {
                //message.setSocialNetPostId(jsonResponse.getString("id"));
                //addPost(message);
                addSentPost(message, jsonResponse.getString("id"), this);
                //addPost(message, "IDpuestoxFacebook", this);
                //this.msg = message;
            }
        } catch (IOException ioe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Problemas con el envio/recepcion de la peticion/respuesta, detail: "
                        + ioe.getMessage() + "\"}");
            } catch (JSONException jsone2) {}
            log.error("Problemas con el envio/recepcion de la peticion/respuesta con Facebook", ioe);
        } catch (JSONException jsone) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"La operacion no se pudo realizar, detail: "
                        + jsone.getMessage() + "\"}");
            } catch (JSONException jsone2) {}
            log.error("La operacion no se pudo realizar, objeto JSON mal formado debido a la respuesta de Facebook: "
                    + facebookResponse, jsone);
        }
    }

    public void postPhoto(Photo photo, HttpServletRequest request,
                          SWBActionResponse response) {
        
        Map<String, String> params = new HashMap<String, String>(2);
        if (this.getAccessToken() != null) {
            params.put("access_token", this.getAccessToken());
        }
        if (photo.getDescription() != null) {
            params.put("message", photo.getDescription());
        }
        String url = Facebook.FACEBOOKGRAPH + this.getFacebookUserId() + "/photos";
        JSONObject jsonResponse = null;
        
        try {
            String photoPath = SWBPortal.getWorkPath() + photo.getWorkPath()
                    + "/" + Photo.social_photo.getName() + "_" + photo.getId() 
                    + "_" + photo.getPhoto();
            SWBFile photoFile = new SWBFile(photoPath);
            
            if (photoFile.exists()) {
                SWBFileInputStream fileStream = new SWBFileInputStream(photoFile);
                String facebookResponse = postFileRequest(params, url,
                        photo.getPhoto(), fileStream, "POST", "photo");
                jsonResponse = new JSONObject(facebookResponse);
            }
            if (jsonResponse != null && jsonResponse.get("id") != null) {
                //photo.setSocialNetPostId(jsonResponse.getString("id"));
                //this.addPost(photo);
                addSentPost(photo, jsonResponse.getString("id"), this);
                //this.photo = photo;
            }
        } catch (FileNotFoundException fnfe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Archivo no encontrado\"}");
            } catch (JSONException jsone) {}
        } catch (JSONException jsone) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"La operacion no se pudo realizar\"}");
            } catch (JSONException jsone2) {}
        } catch (IOException ioe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Problemas con el envio/recepcion de la peticion/respuesta\"}");
            } catch (JSONException jsone2) {}
        }
    }

    public void postVideo(Video video, HttpServletRequest request, SWBActionResponse response) {
        
        Map<String, String> params = new HashMap<String, String>(3);
        if (this.getAccessToken() != null) {
            params.put("access_token", this.getAccessToken());
        }
        if (video.getTitle() != null) {
            params.put("title", video.getTitle());
        }
        if (video.getDescription() != null) {
            params.put("description", video.getDescription());
        }
        String url = Facebook.FACEBOOKGRAPH + this.getFacebookUserId() + "/videos";
        JSONObject jsonResponse = null;
        
        try {
            String videoPath = SWBPortal.getWorkPath() + video.getWorkPath() + "/" + video.getVideo();
            SWBFile videoFile = new SWBFile(videoPath);
            
            if (videoFile.exists()) {
                SWBFileInputStream fileStream = new SWBFileInputStream(videoFile);
                String facebookResponse = postFileRequest(params, url,
                        video.getVideo(), fileStream, "POST", "video");
                jsonResponse = new JSONObject(facebookResponse);
            }
            if (jsonResponse != null && jsonResponse.get("id") != null) {
                //video.setSocialNetPostId(jsonResponse.getString("id"));
                //this.addPost(video);
                addSentPost(video, jsonResponse.getString("id"), this);
            }
        } catch (FileNotFoundException fnfe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Archivo no encontrado\"}");
            } catch (JSONException jsone) {}
        } catch (JSONException jsone) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"La operacion no se pudo realizar\"}");
            } catch (JSONException jsone2) {}
        } catch (IOException ioe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Problemas con el envio/recepcion de la peticion/respuesta\"}");
            } catch (JSONException jsone2) {}
        }
    }
    
    /**
     * Realiza una petici&oacute;n al grafo de Facebook, de acuerdo a la url recibida
     * @param urlString especifica el objeto del grafo de Facebook con el que se desea interactuar
     * @param userAgent indica el navegador de Internet utilizado en la petici&oacute;n a realizar
     * @return un {@code String} que representa la respuesta generada por el grafo de Facebook
     * @throws java.net.MalformedURLException si la url especificada a trav&eacute;s
     *          de {@literal urlString} no est&aacute; correctamente formada
     * @throws java.io.IOException en caso de que se produzca un error al generar la petici&oacute;n
     *          o recibir la respuesta del grafo de Facebook
     */
    public static String graphRequest(String urlString, String userAgent)
            throws java.net.MalformedURLException, java.io.IOException {
        
        URL pagina = new URL(urlString);
        URLConnection conex = null;
        String answer = null;
        
        try {
            //Se realiza la peticion a la página externa
            conex = pagina.openConnection();
            if (userAgent != null) {
                conex.setRequestProperty("user-agent", userAgent);
            }
            if (pagina.getHost() != null) {
                conex.setRequestProperty("host", pagina.getHost());
            }
            conex.setDoOutput(true);
            conex.setConnectTimeout(5000);
        } catch (Exception nexc) {
            conex = null;
        }

        //Analizar la respuesta a la peticion y obtener el access token
        if (conex != null) {
            answer = getResponse(conex.getInputStream());
        }
        return answer;
    }

    /**
     * Realiza peticiones al grafo de Facebook que deban ser enviadas por alg&uacute;n m&eacute;todo en particular
     * @param params contiene los par&aacute;metros a enviar a Facebook para realizar la operaci&oacute;n deseada
     * @param url especifica el objeto del grafo de Facebook con el que se desea interactuar
     * @param userAgent indica el navegador de Internet utilizado en la petici&oacute;n a realizar
     * @param method indica el m&eacute;todo de la petici&oacute; HTTP requerido por Facebook para realizar
     *          una operaci&oacute;n, como: {@literal POST} o {@literal DELETE}
     * @return un {@code String} que representa la respuesta generada por el grafo de Facebook
     * @throws IOException en caso de que se produzca un error al generar la petici&oacute;n
     *          o recibir la respuesta del grafo de Facebook
     */
    private String postRequest(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {

        URL serverUrl = new URL(url);
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);

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
            out = conex.getOutputStream();
            out.write(paramString.toString().getBytes("UTF-8"));
            in = conex.getInputStream();
            response = getResponse(in);
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        } finally {
            close(in);
            close(out);
            if (conex != null) {
                conex.disconnect();
            }
        }
        if (response == null) {
            response = "";
        }
        return response;
    }

    /**
     * Realiza peticiones al grafo de Facebook que deban ser enviadas por alg&uacute;n m&eacute;todo
     * en particular, en las que adem&aacute;, es necesaria la inclusi&oacute;n de un archivo
     * @param params contiene los par&aacute;metros a enviar a Facebook para realizar la operaci&oacute;n deseada
     * @param url especifica el objeto del grafo de Facebook con el que se desea interactuar
     * @param fileName indica el nombre del archivo a incluir en la petici&oacute; a Facebook
     * @param fileStream representa el contenido del archivo, para incluirlo en la petici&oacute; a Facebook
     * @param method indica el m&eacute;todo de la petici&oacute; HTTP requerido por Facebook para realizar
     *          una operaci&oacute;n, como: {@literal POST}
     * @param itemToPost indica si el elemento a publicar en Facebook es una foto o un video.
     *          Los valores aceptados por este parametro son: {@literal photo} o {@literal video}
     * @return un {@code String} que representa la respuesta generada por el grafo de Facebook, o la 
     *          representaci&oacute;n de un objeto JSON con el resultado de la ejecuci&oacute;n del metodo.
     * @throws IOException en caso de que se produzca un error al generar la petici&oacute;n
     *          o recibir la respuesta del grafo de Facebook
     */
    private String postFileRequest(Map<String, String> params, String url, String fileName,
            InputStream fileStream, String method, String itemToPost) {

        HttpURLConnection conex = null;
        OutputStream urlOut = null;
        InputStream in = null;
        URL serverUrl = null;
        String facebookResponse = "{\"response\" : \"Sin procesar\"}";

        if (method == null) {
            method = "POST";
        }

        try {
            serverUrl = new URL(url);
            String boundary = "---MyFacebookFormBoundary" + Long.toString(System.currentTimeMillis(), 16);
            conex = (HttpURLConnection) serverUrl.openConnection();
            conex.setConnectTimeout(30000);
            conex.setReadTimeout(itemToPost.equals("photo") ? 60000 : 3600000);
            conex.setRequestMethod(method);
            conex.setDoInput(true);
            conex.setDoOutput(true);
            conex.setUseCaches(false);
            conex.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conex.setRequestProperty("MIME-version", "1.0");

            conex.connect();
            urlOut = conex.getOutputStream();
            DataOutputStream out = new DataOutputStream(urlOut);
            
            for (Map.Entry<String, String> entry : params.entrySet()) {
                out.writeBytes(PREF + boundary + CRLF);
                out.writeBytes("Content-Type: text/plain;charset=UTF-8" + CRLF);
                out.writeBytes("Content-disposition: form-data; name=\"" + entry.getKey() + "\"" + CRLF);
                out.writeBytes(CRLF);
                byte[] valueBytes = entry.getValue().toString().getBytes("UTF-8");
                out.write(valueBytes);
                out.writeBytes(CRLF);
            }

            out.writeBytes(PREF + boundary + CRLF);
            out.writeBytes("Content-Type: " + (itemToPost.equals("photo") ? "image" : "file") + CRLF);
            out.writeBytes("Content-disposition: form-data; filename=\"" + fileName + "\"" + CRLF);

            // Write the file
            out.writeBytes(CRLF);
            byte buf[] = new byte[1024];
            int len = 0;
            while (len >= 0) {
                out.write(buf, 0, len);
                len = fileStream.read(buf);
            }

            out.writeBytes(CRLF + PREF + boundary + PREF + CRLF);
            out.flush();
            in = conex.getInputStream();
            facebookResponse = getResponse(in);
        } catch (IOException ioe) {
            facebookResponse = "{\"errorMsg\":\"Ocurrio un problema en la peticion a Facebook "
                    + ioe.getMessage() + "\"}";
        } finally {
            close(urlOut);
            close(in);
            if (conex != null) {
                conex.disconnect();
            }
        }
        return facebookResponse;
    }

    /**
     * En base al contenido de la colecci&oacute;n recibida, arma una secuencia de caracteres compuesta de los pares:
     * <p>{@code Entry.getKey()} {@code equals} {@code Entry.getKey()} </p>
     * Si en la colecci&oacute;n hay m&aacute;s de una entrada, los pares (como el anterior), se separan por {@code delimiter}.
     * @param entries la colecci&oacute;n con la que se van a formar los pares
     * @param delimiter representa el valor con que se van a separar los pares a representar
     * @param equals representa el valor con el que se van a relacionar los elementos de cada par a representar
     * @param doEncode indica si el valor representado en cada par, debe ser codificado (UTF-8) o no
     * @return la secuencia de caracteres que representa el conjunto de pares
     * @throws UnsupportedEncodingException en caso de ocurrir algun problema en la codificaci&oacute;n a UTF-8 del valor
     *      de alg&uacute;n par, si as&iacute; se indica en {@code doEncode}
     */
    private CharSequence delimit(Collection<Map.Entry<String, String>> entries,
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
    private String encode(CharSequence target) throws UnsupportedEncodingException {

        String result = "";
        if (target != null) {
            result = target.toString();
            result = URLEncoder.encode(result, "UTF8");
        }
        return result;
    }

    /**
     * Lee un flujo de datos y lo convierte en un {@code String} con su contenido codificado en UTF-8
     * @param data el flujo de datos a convertir
     * @return un {@code String} que representa el contenido del flujo de datos especificado, codificado en UTF-8
     * @throws IOException si ocurre un problema en la lectura del flujo de datos
     */
    private static String getResponse(InputStream data) throws IOException {

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

    /**
     * Cierra el objeto recibido
     * @param c cualquier objeto que tenga la factultad de cerrarse
     */
    private void close( Closeable c ) {
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
    
    @Override
    public void authenticate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
//        try {
//            state = new String(SWBUtils.CryptoWrapper.PBEAES128Decipher(PassPhrase, SFBase64.decode(state)));
//        }catch(GeneralSecurityException gse) {
//System.out.println("----GeneralSecurityException");
//        }catch(Exception e) {
//System.out.println("----Exception");        
//        }
        String error = request.getParameter("error");
        HttpSession session = request.getSession(true);
System.out.println("Facebook.autenticate.............");
        if(code==null)
        {
System.out.println("paso 1");
            String url = doRequestPermissions(request, paramRequest);
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println(" function ioauth() {");
            out.println("  mywin = window.open("+url+",'_blank','width=840,height=680',true);");
            out.println("  mywin.focus();");
            out.println(" }");
            out.println(" if(confirm('autenticar la cuenta en facebook?')) {");
            out.println("  ioauth();");
            out.println(" }");
            out.println("</script>");
        }
        else if( state!=null && state.equals(session.getAttribute("state")) && error==null  )
        {
System.out.println("2");
            session.removeAttribute("state");
            String accessToken = null;
            long secsToExpiration = 0L;
            String token_url = "https://graph.facebook.com/oauth/access_token?" + "client_id="+getAppKey()+"&redirect_uri=" + URLEncoder.encode(getRedirectUrl(request, paramRequest), "utf-8") + "&client_secret="+getSecretKey()+"&code=" + code;

            if(accessToken == null) {
System.out.println("2.1");
                URL pagina = new URL(token_url);
                URLConnection conex = null;
                try {
                    //Se realiza la peticion a la página externa
                    conex = pagina.openConnection();
                    if(request.getHeader("user-agent")!=null) {
                        conex.setRequestProperty("user-agent", request.getHeader("user-agent"));
                    }
                    if(pagina.getHost()!=null) {
                        conex.setRequestProperty("host", pagina.getHost());
                    }
                    conex.setConnectTimeout(5000);
                }catch(Exception nexc) {
                    conex = null;
//System.out.println("error........"+nexc);
                }
                if (conex != null) {
                    String answer = SWBUtils.IO.readInputStream(conex.getInputStream());
                    String aux = null;
                    if (answer.indexOf("&") > 0) {
                        aux = answer.split("&")[0];
                        if (aux.indexOf("=") > 0) {
                            accessToken = aux.split("=")[1];
                        }
                        aux = answer.split("&")[1];
                        if (aux.indexOf("=") > 0) {
//                            secsToExpiration = Long.parseLong(aux.split("=")[1]);
                        }
                    }
                }
            }
            if(accessToken != null) {
System.out.println("2.2");
                String graph_url = "https://graph.facebook.com/me?access_token=" + accessToken;
                String me = Facebook.graphRequest(graph_url, request.getHeader("user-agent"));
                try {
                    JSONObject userData = new JSONObject(me);
                    String name = (String)userData.getString("name");
                    setActive(true);
                    setAccessToken(accessToken);
                    request.setAttribute("msg", name);
                    PrintWriter out = response.getWriter();
                    out.println("<script type=\"text/javascript\">");
                    out.println("  window.close();");
                    out.println("</script>");
                }catch(JSONException e) {
                    log.error(e);
                    request.setAttribute("msg", "hubo problemas. contacta a tu administrador para revisar el visor de errores");
                    PrintWriter out = response.getWriter();
                    out.println("<script type=\"text/javascript\">");
                    out.println("  window.close();");
                    out.println("</script>");
                }
            }
        }
        else
        {
System.out.println("problemas");
            session.removeAttribute("state");
            //System.out.println("ERROR:Se ha encontrado un problema con la respuesta obtenida, se considera no aut&eacute;ntica.");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("  window.close();");
            out.println("</script>");
            request.setAttribute("msg", "Se ha encontrado un problema con la respuesta obtenida, tu cuenta se considera no aut&eacute;ntica.");
        }
    }
    
    private String getRedirectUrl(HttpServletRequest request, SWBParamRequest paramRequest)
    {
        StringBuilder address = new StringBuilder(128);
        address.append("http://").append(request.getServerName()).append(":").append(request.getServerPort()).append("/").append(paramRequest.getUser().getLanguage()).append("/").append(paramRequest.getResourceBase().getWebSiteId()).append("/"+paramRequest.getWebPage().getId()+"/_rid/").append(paramRequest.getResourceBase().getId()).append("/_mod/").append(paramRequest.getMode()).append("/_lang/").append(paramRequest.getUser().getLanguage());
System.out.println("url de regreso: "+address);
        return address.toString();
    }
    
    public String doRequestPermissions(HttpServletRequest request, SWBParamRequest paramRequest )
    {
        StringBuilder url = new StringBuilder(128);
        String encodedURI;
        try {
            encodedURI = SFBase64.encodeBytes(SWBUtils.CryptoWrapper.PBEAES128Cipher(PassPhrase, getURI().getBytes()));
        }catch(GeneralSecurityException e) {
            encodedURI = getURI();
        }
        request.getSession(true).setAttribute("state", "123");
//System.out.println("encodedURI="+encodedURI);
                
        url.append("'https://www.facebook.com/dialog/oauth?'+");
        url.append("'client_id='+");
        url.append("'").append(getAppKey()).append("'+");
        url.append("'&redirect_uri='+");
        url.append("encodeURI('").append(getRedirectUrl(request, paramRequest)).append("')+");
        url.append("'&scope='+");
        url.append("encodeURIComponent('publish_stream,read_stream')+");
        url.append("'&state='+");        
        url.append("'123'");
        return url.toString();
        //heroku pass admin12345678
    }
    
    @Override
    public String doRequestPermissions() {
//System.out.println("Facebook.doRequestPermissions()");
        return null;
    }
    
    @Override
    public String doRequestAccess() {
        return null;
    }
    
}
