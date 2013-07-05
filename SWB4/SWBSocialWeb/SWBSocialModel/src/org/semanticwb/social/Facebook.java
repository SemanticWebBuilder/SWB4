package org.semanticwb.social;


import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.io.SWBFile;
import org.semanticwb.io.SWBFileInputStream;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.listener.Classifier;
import org.semanticwb.social.util.SWBSocialUtil;


public class Facebook extends org.semanticwb.social.base.FacebookBase {
    
    
    private final static String ALPHABETH = "abcdefghijklmnopqrstuvwxyz1234567890";
    
    private static final String CRLF = "\r\n";    
    
    /** Cadena utilizada en la construccion de peticiones HTTP POST al servidor de Facebook */
    private static final String PREF = "--";    
    
    /** Inicio de la URL utilizada para las peticiones de informacion a Facebook */
    //private static final String FACEBOOKGRAPH = "https://graph-video.facebook.com/";
    private static final String FACEBOOKGRAPH = "https://graph.facebook.com/";
    
     private static final String FACEBOOKGRAPH_VIDEO = "https://graph-video.facebook.com/";
    
    
    /** Indica el numero de mensajes maximo a extraer en cada busqueda */
    private static final short QUERYLIMIT = 100;
    
    private Logger log = SWBUtils.getLogger(Facebook.class);
    
    
    public Facebook(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Ejecuta las operaciones para extraer informaci&oacute;n de Facebook, 
     * en base a los criterios especificados en el stream indicado.
     * @param stream Indica el objeto {@code Stream} del que se obtienen los criterios de
     *          b&uacute;squeda de informaci&oacute;n
     */
    @Override
    public void listen(Stream stream) {
        
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", this.getAccessToken());
        boolean canGetMoreResults = true;
        String phrasesInStream = stream.getPhrase() != null ? stream.getPhrase() : "";
        int queriesNumber = phrasesInStream.split(",").length * 2;//int queriesNumber = phrasesInStream.split("\\|").length * 2;
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
     * Forma las consultas a realizar en Facebook en formato JSON, de acuerdo a
     * lo especificado en el API de Facebook.
     * @param queriesArray la coleccion que contiene los datos de las busquedas a realizar
     * @return un {@code String} que representa las busquedas a solicitar a Facebook en formato JSON.
     */
    private String renderFacebookQueries(HashMap<String, String>[] queriesArray) {
        
        JSONArray batch = new JSONArray(); //Conjunto de consultas a ejecutar como batch en Facebook
        for (int i = 0; i < queriesArray.length; i++) {
            //Se crean consultas a Facebook por cada frase capturada
            JSONObject request = new JSONObject();
            try {
                request.put("method", queriesArray[i].get("method"));
                request.put("relative_url", queriesArray[i].get("relative_url"));
                batch.put(request);
            } catch (JSONException jsone) {
                log.error("Al integrar consulta batch con siguiente consulta", jsone);
            }
        }
        
        return batch.toString();
    }
    
    /**
     * Almacena los nuevos l&iacute;mites a utilizar en las b&uacute;squedas posteriores en Facebook.
     * Para cada frase en el {@code Stream} indicado, se almacena el valor del l&iacute;mite que indica a partir
     * de qu&eacute; registro se har&aacute;n las b&uacute;squedas de nueva informaci&oacute;n en Facebook.
     * @param queriesArray contiene tanto los queryStrings a ejecutar en FB, como los
     *        url's devueltos por FB para las siguientes consultas
     */
    private void storeSearchLimits(HashMap<String, String>[] queriesArray, Stream stream) {
        
        StringBuilder limits = new StringBuilder(64);
        try {
            for (int i = 0; i < queriesArray.length; i++) {
                
                String newQuery = queriesArray[i].containsKey("nextQuery")
                                  ? queriesArray[i].get("nextQuery") : "";
                
                String[] params = null;
                
                //Si se obtuvieron mensajes, hay un nuevo valor para el último mensaje leído, indicado en "nextQuery"
                if (newQuery.indexOf("search?") > 0) {
                    //Para consultas en toda la red social
                    params = newQuery.substring(newQuery.indexOf("?") + 1).split("&");
                    for (int j = 0; j < params.length; j++) {
                        if (params[j].startsWith("until=")) {
                            if (i > 0) {
                                limits.append(":");  //se agrega separador entre valores
                            }
                            limits.append("search_");
                            limits.append(queriesArray[i].get("phrase"));
                            limits.append("=");
                            limits.append(params[j].split("=")[1]);
                        }
                    }
                } else if (newQuery.indexOf("feed?") > 0) {
                    //Para consultas en el muro
                    params = newQuery.substring(newQuery.indexOf("?") + 1).split("&");
                    for (int j = 0; j < params.length; j++) {
                        if (params[j].startsWith("until=")) {
                            if (i > 0) {
                                limits.append(":");  //se agrega separador entre valores
                            }
                            limits.append("feed_");
                            limits.append(queriesArray[i].get("phrase"));
                            limits.append("=");
                            limits.append(params[j].split("=")[1]);
                        }
                    }
                }
            }
            if (limits.length() > 0) {
                SocialNetStreamSearch socialStreamSerch=SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
                if(socialStreamSerch!=null)
                {
                    socialStreamSerch.setNextDatetoSearch(limits.toString());
                }
                //this.setNextDatetoSearch(limits.toString());
            }
        } catch (Exception e) {
            log.error("Al formar URL de siguiente consulta a ejecutar en Facebook", e);
        }
    }
    
    /**
     * Genera las url's de las siguientes consultas a ejecutar en Facebook.
     * @param streamPhrase objeto del que se extrae la fecha del &uacute;ltimo post obtenido de Facebook
     * @param queriesArray contiene tanto los queryStrings a ejecutar en FB, como los
     *        url's devueltos por FB para las siguientes consultas, as&iacute; como el n&uacute;mero 
     *        de post obtenidos en la b&uacute;squeda de la iteraci&oacute;n anterior
     */
    private void generateBatchQuery(String streamPhrase, HashMap<String,
                                    String>[] queriesArray, Stream stream) throws Exception {
        
        //TODO: Acordar que las frases almacenadas no contengan el simbolo =.
        String[] phrase = streamPhrase.split(",");//String[] phrase = streamPhrase.split("\\|");
        HashMap<String, String> searchLimits = new HashMap<String, String>((phrase.length * 2));  //es doble para busquedas globales y del muro
        
        if (queriesArray.length == 0 || (queriesArray.length > 0 && queriesArray[0] == null)) {

            try {
                //Se obtienen los limites desde los cuales se haran consultas en Facebook
                SocialNetStreamSearch socialStreamSerch=SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
                if (socialStreamSerch!=null && socialStreamSerch.getNextDatetoSearch() != null && !"".equals(socialStreamSerch.getNextDatetoSearch())) {
                    String[] phraseElements = socialStreamSerch.getNextDatetoSearch().split(":");
                    for (int i = 0; i < phrase.length; i++) {
                        String[] pair = phraseElements[i].split("=");
                        //Se quita de la frase el sufijo: search_ o feed_ correspondiente a cada tipo de busqueda
                        String key = null;
                        key = pair[0];
                        String value = pair.length > 1 ? pair[1] : "";
                        if (value != null && !"".equalsIgnoreCase(value)) {
                            searchLimits.put(key, value);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e.getCause());
            }
            
            for (int i = 0; i < phrase.length; i++) {
                //Se crean consultas a Facebook por cada frase capturada
                String publicQuery = null;
                String wallQuery = null;
                String untilPublic = null;
                String untilPrivate = null;
                HashMap<String, String> queryPublic = new HashMap<String, String>(6);
                HashMap<String, String> queryWall = new HashMap<String, String>(6);
                
                try {
                    if (searchLimits.containsKey("search_" + phrase[i]) && searchLimits.get("search_" + phrase[i]) != null) {
                        untilPublic = searchLimits.get("search_" + phrase[i]);
                    }
                    if (searchLimits.containsKey("feed_" + phrase[i]) && searchLimits.get("feed_" + phrase[i]) != null) {
                        untilPrivate = searchLimits.get("feed_" + phrase[i]);
                    }
                    //Para la primera ejecucion de este proceso se crean las url de las consultas publicas
                    publicQuery = "search?q=" + phrase[i] + "&type=post&limit="
                            + Facebook.QUERYLIMIT + (untilPublic != null ? "&until=" + untilPublic : "");
                    // Y las url de las consultas privadas
                    wallQuery = "me/feed?q=" + phrase[i] + "&type=post&limit="
                            + Facebook.QUERYLIMIT + (untilPrivate != null ? "&until=" + untilPrivate : "");
                    
                    queryPublic.put("method", "GET");
                    queryPublic.put("relative_url", publicQuery);
                    queryPublic.put("phrase", phrase[i]);
                    queriesArray[i * 2] = queryPublic;
                    queryWall.put("method", "GET");
                    queryWall.put("relative_url", wallQuery);
                    queryWall.put("phrase", phrase[i]);
                    queriesArray[(i * 2) + 1] = queryWall;
                    
                } catch (Exception e) {
                    log.error("Al integrar consulta batch con siguiente consulta", e);
                }
            }
            
        } else if (queriesArray.length > 0 && queriesArray[0] != null) {
            //En la segunda y posteriores iteraciones de este proceso, se usan las consultas "nextQuery" 
            //que se extraen de las respuestas obtenidas de Facebook:
            for (int i = 0; i < queriesArray.length; i++) {
                String newQuery = queriesArray[i].get("nextQuery");
                int msgsObtained = Integer.parseInt(queriesArray[i].get("msgCounted"));

                if (msgsObtained > 0) {
                    //Si se obtuvieron mensajes, hay un nuevo valor para el ultimo mensaje leido, indicado en "nextQuery"
                    if (newQuery.indexOf("search?") > 0) {
                        queriesArray[i].put("relative_url", newQuery.substring(newQuery.indexOf("search?") - 1));
                    }

                    //Si no hubo resultados, se usara a continuacion la misma consulta ejecutada anteriormente
                }
            }
        }
    }
    
    /**
     * Analiza la respuesta de Facebook y obtiene la informaci&oacute;n de los mensajes publicados en la red social.
     * En base a la estructura del objeto JSON devuelto por Facebook, se revisa que se haya podido responder
     * la petici&oacute;n, se obtiene la informaci&oacute;n de los mensajes publicados y se obtiene las url's de las 
     * siguientes consultas a ejecutar.
     * @param response representa la respuesta obtenida de Facebook en formato JSON
     * @param queries contiene tanto los queryStrings a ejecutar en FB, como los
     *        url's devueltos por FB para las siguientes consultas
     * @param stream objeto al que el clasificador relacionar&aacute; los mensajes obtenidos de las b&uacute;squedas ejecutadas
     * @return un booleano que indica si es posible que haya m&aacute;s mensajes publicados en la red al momento
     *         en que se contest&oacute; la petici&oacute;n, lo que indica que se tiene que realizar otra iteraci&oacute;n
     */
    private boolean parseResponse(String response, Stream stream, HashMap<String, String>[] queriesArray) {
        
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
                            errorMsg.append(queriesArray[j].get("phrase"));
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
                            ArrayList <ExternalPost> aListExternalPost=new ArrayList();
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
                                    
                                    //external.setPicture(postsData.getJSONObject(k).getString("picture"));
                                    ArrayList pictures = new ArrayList();
                                    pictures.add(postsData.getJSONObject(k).getString("picture"));
                                    external.setPictures(pictures);
                                }
                                if (postsData.getJSONObject(k).has("name")) {
                                    external.setPostName(postsData.getJSONObject(k).getString("name"));
                                }
                                if (postsData.getJSONObject(k).has("type")) {
                                    if(postsData.getJSONObject(k).getString("type").equals("status")){//Status -> message
                                        if(postsData.getJSONObject(k).has("message")){
                                            external.setPostType(SWBSocialUtil.MESSAGE);
                                        }else{
                                            continue;
                                        }                                        
                                    }else if(postsData.getJSONObject(k).getString("type").equals("photo")){//Photo
                                        if (postsData.getJSONObject(k).has("picture")) {
                                            //external.setPicture(postsData.getJSONObject(k).getString("picture"));
                                            ArrayList pictures = new ArrayList();
                                            pictures.add(postsData.getJSONObject(k).getString("picture"));
                                            external.setPictures(pictures);
                                            external.setPostType(SWBSocialUtil.PHOTO);
                                        }else{//If has message, create it as message
                                            if(postsData.getJSONObject(k).has("message")){
                                                external.setPostType(SWBSocialUtil.MESSAGE);
                                            }else{
                                                continue;
                                            }
                                        }
                                    }else if(postsData.getJSONObject(k).getString("type").equals("video")){
                                        if(postsData.getJSONObject(k).has("source")){
                                            external.setVideo(postsData.getJSONObject(k).getString("source"));
                                            external.setPostType(SWBSocialUtil.VIDEO);
                                        }else{//If has message, create it as message
                                            if(postsData.getJSONObject(k).has("message")){
                                                external.setPostType(SWBSocialUtil.MESSAGE);
                                            }else{
                                                continue;
                                            }
                                        }
                                    }else if(postsData.getJSONObject(k).getString("type").equals("link")){
                                        if(postsData.getJSONObject(k).has("message")){
                                            external.setPostType(SWBSocialUtil.MESSAGE);
                                        }else{
                                            continue;
                                        }
                                    }
                                    //external.setPostType(postsData.getJSONObject(k).getString("type"));
                                    
                                }else {//Do not process data without "type"
                                    continue;
                                }
                                aListExternalPost.add(external);
                            }
                            //Si el ArrayList tiene tamaño mayor a 0, entonces es que existen mensajes para enviar al clasificador
                            if(aListExternalPost.size()>0)
                            {
                                new Classifier(aListExternalPost, stream, this);
                            }
                            if (cont == Facebook.QUERYLIMIT) {
                                isThereMoreMsgs = true;  //Esto indica la posibilidad de que en una consulta siguiente, se obtengan más mensajes
                            } else if (cont == 0) {
                                isResponseEmpty = true;
                            }
                            
                            //Se obtiene la url de la siguiente consulta a realizar para la frase correspondiente
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
                        }
                    }
                }
            }
        } catch (JSONException jsone) {
            log.error("Problemas al parsear respuesta de Facebook", jsone);
        }
        
        return isThereMoreMsgs;
    }
    
    public void postMsg(Message message) {
        
        Map<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", this.getAccessToken());
        params.put("message", message.getMsg_Text());
        String url = Facebook.FACEBOOKGRAPH + this.getFacebookUserId() + "/feed";
        JSONObject jsonResponse = null;
        String facebookResponse = "";
        
        try {
            facebookResponse = postRequest(params, url, 
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95",
                    "POST");
            jsonResponse = new JSONObject(facebookResponse);
            if (jsonResponse != null && jsonResponse.get("id") != null) {
                //message.setSocialNetPostId(jsonResponse.getString("id"));
                //addPost(message);
                //--TODO:Ver si se agrega esta línea despues(addSentPost(message, jsonResponse.getString("id"), this); )
                //, es para agregar el Post enviado a la clase PostOutContainer
                //Para que ahi se almacenen por mes y año y despues pueda ser mas facil y optimo hacer busquedas sobre PostOuts
                //addSentPost(message, jsonResponse.getString("id"), this); 
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

    public void postPhoto(Photo photo) {
        
        Map<String, String> params = new HashMap<String, String>(2);
        if (this.getAccessToken() != null) {
            params.put("access_token", this.getAccessToken());
        }
//        if (photo.getMsg_Text() != null) {
//            params.put("message", photo.getMsg_Text());
//        }
        String url = Facebook.FACEBOOKGRAPH + this.getFacebookUserId() + "/photos";
        JSONObject jsonResponse = null;
        
        try {
            Iterator<PhotoImg> photosArray = photo.listPhotos();
            String photoToPublish="";
            String additionalPhotos="";
            int photoNumber = 0;
            while(photosArray.hasNext()){
                PhotoImg photoData = (PhotoImg)photosArray.next();
                if(++photoNumber == 1){//post the first Photo
                    photoToPublish = SWBPortal.getWorkPath() + photo.getWorkPath() + "/" + Photo.social_Photo.getName()
                        + "_" + photoData.getId() + "_" + photoData.getPhoto();
                }else{
                    additionalPhotos += SWBPortal.getWorkPath() + photo.getWorkPath() + "/" + Photo.social_Photo.getName()
                        + "_" + photoData.getId() + "_" + photoData.getPhoto() + " ";
                }                
            }
            
            if(photoNumber == 0){
                System.out.println("No Photos FOUND");
                return;
            }
            
            System.out.println("The photo to be published FACEBOOK:" + photoToPublish);
            System.out.println("Additional Photos FACEBOOK: " + additionalPhotos);
            
            if (photo.getMsg_Text() != null) {
                params.put("message", photo.getMsg_Text() + " " + additionalPhotos);
            }
            
//            String photoPath = SWBPortal.getWorkPath() + photo.getWorkPath()
//                    + "/" + Photo.social_Photo.getName() + "_" + photo.getId() 
//                    + "_" + photo.getPhoto();
            SWBFile photoFile = new SWBFile(photoToPublish);
            
            if (photoFile.exists()) {
                SWBFileInputStream fileStream = new SWBFileInputStream(photoFile);
//                String facebookResponse = postFileRequest(params, url,
//                        photo.getPhoto(), fileStream, "POST", "photo");
                String facebookResponse = postFileRequest(params, url,
                        photoToPublish, fileStream, "POST", "photo");
                jsonResponse = new JSONObject(facebookResponse);
            }
            if (jsonResponse != null && jsonResponse.get("id") != null) {
                //photo.setSocialNetPostId(jsonResponse.getString("id"));
                //this.addPost(photo);
                //--TODO:Ver si se agrega esta línea despues(addSentPost(message, jsonResponse.getString("id"), this); )
                //, es para agregar el Post enviado a la clase PostOutContainer
                //Para que ahi se almacenen por mes y año y despues pueda ser mas facil y optimo hacer busquedas sobre PostOuts
                //addSentPost(photo, jsonResponse.getString("id"), this);
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

    public void postVideo(Video video) 
    {
        Map<String, String> params = new HashMap<String, String>(3);
        if (this.getAccessToken() != null) {
            params.put("access_token", this.getAccessToken());
        }
        /*
        if (video.getTitle() != null) {
            params.put("title", video.getTitle());    //TODO:Estoy enviando esto como título a la red social, ver como lo pone en la misma
        }*/
        if (video.getMsg_Text() != null) {
            params.put("description", video.getMsg_Text());
        }
        //String url = Facebook.FACEBOOKGRAPH + this.getFacebookUserId() + "/videos";
        String url = Facebook.FACEBOOKGRAPH_VIDEO + this.getFacebookUserId() + "/videos";        
        JSONObject jsonResponse = null;
        
        try {
            String videoPath = SWBPortal.getWorkPath() + video.getWorkPath() + "/" + video.getVideo();
            SWBFile videoFile = new SWBFile(videoPath);
            
            if (videoFile.exists()) {
                SWBFileInputStream fileStream = new SWBFileInputStream(videoFile);
                String facebookResponse = postFileRequest(params, url, video.getVideo(), fileStream, "POST", "video");
                jsonResponse = new JSONObject(facebookResponse);
            }
            if (jsonResponse != null && jsonResponse.get("id") != null) {
                //video.setSocialNetPostId(jsonResponse.getString("id"));
                //this.addPost(video);
                //--TODO:Ver si se agrega esta línea despues(addSentPost(message, jsonResponse.getString("id"), this); )
                //, es para agregar el Post enviado a la clase PostOutContainer
                //Para que ahi se almacenen por mes y año y despues pueda ser mas facil y optimo hacer busquedas sobre PostOuts
                //addSentPost(video, jsonResponse.getString("id"), this);
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
        String error = request.getParameter("error");
        HttpSession session = request.getSession(true);
        //System.out.println("Facebook.autenticate.............");
        if(code==null)
        {
            //System.out.println("Facebook----paso 1");
            String url = doRequestPermissions(request, paramRequest);
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println(" function ioauth() {");
            out.println("  mywin = window.open("+url+",'_blank','width=840,height=680',true);");
            out.println("  mywin.focus();");
            out.println(" }");
            out.println(" if(confirm('¿Autenticar la cuenta en Facebook?')) {");
            out.println("  ioauth();");
            out.println(" }");
            out.println("</script>");
        }
        else if( state!=null && state.equals(session.getAttribute("state")) && error==null  )
        {
            //System.out.println("Facebook----2");
            session.removeAttribute("state");
            String accessToken = null;
//            long secsToExpiration = 0L;
            String token_url = "https://graph.facebook.com/oauth/access_token?" + "client_id="+getAppKey()+"&redirect_uri=" + URLEncoder.encode(getRedirectUrl(request, paramRequest), "utf-8") + "&client_secret="+getSecretKey()+"&code=" + code;
//            if(accessToken == null) {
            //System.out.println("Facebook----2.1");
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
            //System.out.println("error error error error error error error error 1");
            //System.out.println("error........"+nexc);
            }
            if (conex != null) {
                String answer;
                try{
                answer = SWBUtils.IO.readInputStream(conex.getInputStream());
                }catch(Exception e) {
                    //System.out.println("error error error error error error error error 3\n"+e);
                answer = "";
                }
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
//            }
            if(accessToken != null) {
                //System.out.println("Facebook----2.2");
                String graph_url = "https://graph.facebook.com/me?access_token=" + accessToken;
                String me = Facebook.graphRequest(graph_url, request.getHeader("user-agent"));
                try {
                    JSONObject userData = new JSONObject(me);
                    //System.out.println("userData="+userData);                    
                    //String username = userData.getString("username");
                    String userId = userData.getString("id");
                    setFacebookUserId(userId);
                    setAccessToken(accessToken);
                    setSn_authenticated(true);
                                        
                    request.setAttribute("msg", userId);
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
                    //System.out.println("error error error error error error error error 2");
                }
            }
        }
        else
        {
            //System.out.println("problemas");
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
        return address.toString();
    }
    
    public String doRequestPermissions(HttpServletRequest request, SWBParamRequest paramRequest )
    {
        StringBuilder url = new StringBuilder(128);
        String state = SWBPortal.UTIL.getRandString(7, ALPHABETH);
        request.getSession(true).setAttribute("state", state);
                
        url.append("'https://www.facebook.com/dialog/oauth?'+");
        url.append("'client_id='+");
        url.append("'").append(getAppKey()).append("'+");
        url.append("'&redirect_uri='+");
        url.append("encodeURI('").append(getRedirectUrl(request, paramRequest)).append("')+");
        url.append("'&scope='+");
        //url.append("encodeURIComponent('publish_stream,read_stream')+");
        url.append("encodeURIComponent('user_about_me,friends_about_me,user_activities,friends_activities,user_birthday,friends_birthday,user_checkins,friends_checkins,user_education_history,friends_education_history,user_events,friends_events,user_groups,friends_groups,user_hometown,friends_hometown,user_interests,friends_interests,user_likes,friends_likes,user_location,friends_location,user_notes,friends_notes,user_photos,friends_photos,user_relationships,friends_relationships,user_relationship_details,friends_relationship_details,user_religion_politics,friends_religion_politics,user_status,friends_status,user_videos,friends_videos,user_website,friends_website,email,manage_pages,publish_stream,read_stream,read_page_mailboxes,read_insights,ads_management')+");
        url.append("'&state='+");        
        url.append("'").append(state).append("'");
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
