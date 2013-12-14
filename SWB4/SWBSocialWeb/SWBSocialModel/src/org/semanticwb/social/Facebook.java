package org.semanticwb.social;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
import org.semanticwb.model.Language;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.listener.Classifier;
import org.semanticwb.social.util.SWBSocialUtil;

public class Facebook extends org.semanticwb.social.base.FacebookBase {

    private final static String ALPHABETH = "abcdefghijklmnopqrstuvwxyz1234567890";
    private static final String CRLF = "\r\n";
    /**
     * Cadena utilizada en la construccion de peticiones HTTP POST al servidor
     * de Facebook
     */
    private static final String PREF = "--";
    /**
     * Inicio de la URL utilizada para las peticiones de informacion a Facebook
     */
    //private static final String FACEBOOKGRAPH = "https://graph-video.facebook.com/";
    private static final String FACEBOOKGRAPH = "https://graph.facebook.com/";
    private static final String FACEBOOKGRAPH_VIDEO = "https://graph-video.facebook.com/";
    /**
     * Indica el numero de mensajes maximo a extraer en cada busqueda
     */
    private static final short QUERYLIMIT = 100;
    private Logger log = SWBUtils.getLogger(Facebook.class);

    public Facebook(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }
    static {
        Facebook.social_Facebook.registerObserver(new SocialNetSemanticObserver());
    }
    
    /**
     * Formats phrases according to Query requirements.
     * Removes duplicate words and formats the string.
     * @param stream - phrases to search delimited by pipe
     * @return Formated phrases.
     */
    private String getPhrases(String phrase){
        String parsedString = null;
        HashSet<String> parsedPhrases = new HashSet<String>();
        if (phrase != null && !phrase.isEmpty()) {
            parsedString = "";
            String[] phrasesStream = phrase.split(","); //Delimiter
            String tmp;
            int noOfPhrases = phrasesStream.length;
            for (int i = 0; i < noOfPhrases; i++) {
                if(!phrasesStream[i].trim().isEmpty()){
                    tmp = phrasesStream[i].trim().replaceAll("\\s+", " "); //replace multiple spaces beetwen words for one only one space
                    parsedPhrases.add(tmp);
                }
            }
            
            Iterator<String> words = parsedPhrases.iterator();
            noOfPhrases = parsedPhrases.size();
            int i = 0;
            while(words.hasNext()){
                parsedString += words.next();
                if ((i + 1) < noOfPhrases) {
                    parsedString += ",";
                }                
                i++;
            }
        }        
        System.out.println("PARSED STRING:"  + parsedString);
        return parsedString;
    }

    /**
     * Ejecuta las operaciones para extraer informaci&oacute;n de Facebook, en
     * base a los criterios especificados en el stream indicado.
     *
     * @param stream Indica el objeto {@code Stream} del que se obtienen los
     * criterios de b&uacute;squeda de informaci&oacute;n
     */
    @Override
    public void listen(Stream stream) {

        System.out.println("Listening from FACEBOOK");
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", this.getAccessToken());
        boolean canGetMoreResults = true;
        String phrasesInStream = getPhrases(stream.getPhrase());
        if(phrasesInStream == null || phrasesInStream.isEmpty()){
            return;
        }
        int queriesNumber = phrasesInStream.split(",").length * 2;//int queriesNumber = phrasesInStream.split("\\|").length * 2;
        HashMap<String, String>[] queriesArray = new HashMap[queriesNumber];

        try {
            //Como Facebook proporciona los datos de un conjunto definido de 
            //mensajes a la vez, se necesita pedir esta información por bloques
            int iterations = 0;
            do {
                //Genera todas las consultas a ejecutar en Facebook, una por frase almacenada en el Stream
                generateBatchQuery(phrasesInStream, queriesArray, stream);

                //printMap(queriesArray);
                if (queriesArray != null && queriesArray[0].containsKey("relative_url")) {

                    params.put("batch", renderFacebookQueries(queriesArray));
                    String fbResponse = postRequest(params, Facebook.FACEBOOKGRAPH,
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");

                    //Se analiza la respuesta de Facebook y se extraen los datos de los mensajes
                    canGetMoreResults = parseResponse(fbResponse, stream, queriesArray, iterations++);
                }
                
                if(!stream.isActive()){//If the stream has been disabled stop listening
                    canGetMoreResults = false;
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
     *
     * @param queriesArray la coleccion que contiene los datos de las busquedas
     * a realizar
     * @return un {@code String} que representa las busquedas a solicitar a
     * Facebook en formato JSON.
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
     * Almacena los nuevos l&iacute;mites a utilizar en las b&uacute;squedas
     * posteriores en Facebook. Para cada frase en el {@code Stream} indicado,
     * se almacena el valor del l&iacute;mite que indica a partir de qu&eacute;
     * registro se har&aacute;n las b&uacute;squedas de nueva informaci&oacute;n
     * en Facebook.
     *
     * @param queriesArray contiene tanto los queryStrings a ejecutar en FB,
     * como los url's devueltos por FB para las siguientes consultas
     */
    private void storeSearchLimits(HashMap<String, String>[] queriesArray, Stream stream) {

        StringBuilder limits = new StringBuilder(64);
        try {
            for (int i = 0; i < queriesArray.length; i++) {

                /*String newQuery = queriesArray[i].containsKey("nextQuery")
                 ? queriesArray[i].get("nextQuery") : "";*/
                String newQuery = queriesArray[i].containsKey("previousQuery")
                        ? queriesArray[i].get("previousQuery") : "";

                if (newQuery == null) {
                    newQuery = "";
                }
                //System.out.println("MOSTRANDO query a Parsear storeSearchLimits:"  + newQuery);

                String[] params = null;

                //Si se obtuvieron mensajes, hay un nuevo valor para el último mensaje leído, indicado en "nextQuery"
                if (newQuery.indexOf("search?") > 0) {
                    //Para consultas en toda la red social
                    params = newQuery.substring(newQuery.indexOf("?") + 1).split("&");
                    for (int j = 0; j < params.length; j++) {
                        /*if (params[j].startsWith("until=")) {
                         if (i > 0) {
                         limits.append(":");  //se agrega separador entre valores
                         }
                         limits.append("search_");
                         limits.append(queriesArray[i].get("phrase"));
                         limits.append("=");
                         limits.append(params[j].split("=")[1]);
                         }*/
                        if (params[j].startsWith("since=")) {
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
                        if (params[j].startsWith("since=")) {
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
            /*if (limits.length() > 0) {
             SocialNetStreamSearch socialStreamSerch = SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
             if (socialStreamSerch != null) {
             socialStreamSerch.setNextDatetoSearch(limits.toString());
             }
             //this.setNextDatetoSearch(limits.toString());
             System.out.println("SAVING DATA:" + limits);
             }*/
            if (limits.length() > 0) {
                SocialNetStreamSearch socialStreamSerch = SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
                if (socialStreamSerch != null) {
                    if (socialStreamSerch.getNextDatetoSearch() != null && !socialStreamSerch.getNextDatetoSearch().isEmpty()) {
                        /*Pudo haber traido resultados para ambos casos, actualizar ambos ids
                         Pudo haber traido solo un id y si del otro no trajo nada pero se encuentra en el stream y debe de usarse el de la última vez
                         Pudo haber agregado un nuevo termino y trajo resultados, debe de guardarse
                         Pudo haberse quitado un termino del stream entonces debe de removerse del nextdatetosearch
                         */
                        //String phrasesInStream = stream.getPhrase() != null ? stream.getPhrase() : "";
                        String phrasesInStream = getPhrases(stream.getPhrase());
                        String[] phrasesArray = phrasesInStream.split(",");
                        String[] oldLimits = socialStreamSerch.getNextDatetoSearch().split(":");
                        String[] newLimits = limits.toString().split(":");
                        String finalLimits = "";


                        for (int j = 0; j < newLimits.length; j++) {
                            System.out.println("\t\tnew-->" + newLimits[j] + "<--");
                        }
                        System.out.println("\n");
                        for (int j = 0; j < oldLimits.length; j++) {
                            System.out.println("\t\told-->" + oldLimits[j] + "<--");
                        }
                        System.out.println("\n");
                        for (int j = 0; j < phrasesArray.length; j++) {
                            System.out.println("\t\tstream-->" + phrasesArray[j] + "<--");
                        }
                        System.out.println("\n");
                        for (int i = 0; i < phrasesArray.length; i++) {//Iterate the phrases in stream                            
                            //LA REFERENCIA SON LAS FRASES EN EL STREAM COMO TAL!!!
                            //para cada palabra buscarla en los nuevos limites
                            //si no la encuentra buscarla en lo que hay en el nextdatetosearch

                            //si no la encuentra no agrega nada
                            //si hay más palabras en el nextdatetosearch no las toma en cuenta porque está buscando sobre las frases del stream
                            String wordInStream = phrasesArray[i];
                            System.out.println("looking for:" + wordInStream);

                            boolean foundInNewLimits = false;
                            //Buscar primero en los limites nuevos                            
                            for (int j = 0; j < newLimits.length; j++) {
                                if (!newLimits[j].isEmpty()) {
                                    String newLimit = newLimits[j].substring(newLimits[j].indexOf("_") + 1, newLimits[j].lastIndexOf("="));
                                    System.out.println("NEW-" + newLimit + "-vs-" + wordInStream);
                                    if (wordInStream.equals(newLimit)) {//Se encontro en los limites nuevos
                                        finalLimits += newLimits[j] + ":";
                                        foundInNewLimits = true;
                                        System.out.println("FOUND IN NEW LIMITS:" + newLimits[j]);
                                        break;
                                    }
                                }
                            }

                            if (foundInNewLimits == false) {//Se va a buscar en los datos almacenados en el nextdatetosearch
                                System.out.println("NO ENCONTRADO EN LOS LIMITES NUEVOS:" + wordInStream);
                                for (int j = 0; j < oldLimits.length; j++) {
                                    if (!oldLimits[j].isEmpty()) {
                                        String oldLimit = oldLimits[j].substring(oldLimits[j].indexOf("_") + 1, oldLimits[j].lastIndexOf("="));
                                        System.out.println("old-" + oldLimit + "-vs-" + wordInStream);
                                        if (wordInStream.equals(oldLimit)) {
                                            finalLimits += oldLimits[j] + ":";
                                            System.out.println("FOUND IN OLD STORED LIMITS:" + oldLimits[j]);
                                            break;
                                        }
                                    }
                                }
                            }
                            //Si no la encontro en los limites nuevos ni en los anteriores entonces no la guarda
                        }

                        if (finalLimits.length() > 1) {
                            finalLimits = finalLimits.substring(0, finalLimits.length() - 1);
                        }
                        System.out.println("SAVING DATA:" + finalLimits);
                        socialStreamSerch.setNextDatetoSearch(finalLimits);
                    } else {
                        System.out.println("\n\n\nSAVING DATA fOR FIRST TIME!!!:" + limits.toString());
                        socialStreamSerch.setNextDatetoSearch(limits.toString());
                    }
                }
                //System.out.println("SAVING DATA:" + limits);
            }
        } catch (Exception e) {
            log.error("Al formar URL de siguiente consulta a ejecutar en Facebook", e);
        }
    }

    /**
     * Genera las url's de las siguientes consultas a ejecutar en Facebook.
     *
     * @param streamPhrase objeto del que se extrae la fecha del &uacute;ltimo
     * post obtenido de Facebook
     * @param queriesArray contiene tanto los queryStrings a ejecutar en FB,
     * como los url's devueltos por FB para las siguientes consultas, as&iacute;
     * como el n&uacute;mero de post obtenidos en la b&uacute;squeda de la
     * iteraci&oacute;n anterior
     */
    private void generateBatchQuery(String streamPhrase, HashMap<String, String>[] queriesArray, Stream stream) throws Exception {

        //TODO: Acordar que las frases almacenadas no contengan el simbolo =.
        String[] phrase = streamPhrase.split(",");//String[] phrase = streamPhrase.split("\\|");
        HashMap<String, String> searchLimits = new HashMap<String, String>((phrase.length * 2));  //es doble para busquedas globales y del muro

        if (queriesArray.length == 0 || (queriesArray.length > 0 && queriesArray[0] == null)) {

            try {
                //Se obtienen los limites desde los cuales se haran consultas en Facebook
                SocialNetStreamSearch socialStreamSerch = SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
                if (socialStreamSerch != null && socialStreamSerch.getNextDatetoSearch() != null && !"".equals(socialStreamSerch.getNextDatetoSearch())) {
                    String[] phraseElements = socialStreamSerch.getNextDatetoSearch().split(":");
                    for (int i = 0; i < phraseElements.length; i++) {
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
                            + Facebook.QUERYLIMIT + (untilPublic != null ? "&since=" + untilPublic : "");
                    // Y las url de las consultas privadas
                    wallQuery = "me/feed?q=" + phrase[i] + "&type=post&limit="
                            + Facebook.QUERYLIMIT + (untilPrivate != null ? "&since=" + untilPrivate : "");

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
     * Analiza la respuesta de Facebook y obtiene la informaci&oacute;n de los
     * mensajes publicados en la red social. En base a la estructura del objeto
     * JSON devuelto por Facebook, se revisa que se haya podido responder la
     * petici&oacute;n, se obtiene la informaci&oacute;n de los mensajes
     * publicados y se obtiene las url's de las siguientes consultas a ejecutar.
     *
     * @param response representa la respuesta obtenida de Facebook en formato
     * JSON
     * @param queries contiene tanto los queryStrings a ejecutar en FB, como los
     * url's devueltos por FB para las siguientes consultas
     * @param stream objeto al que el clasificador relacionar&aacute; los
     * mensajes obtenidos de las b&uacute;squedas ejecutadas
     * @return un booleano que indica si es posible que haya m&aacute;s mensajes
     * publicados en la red al momento en que se contest&oacute; la
     * petici&oacute;n, lo que indica que se tiene que realizar otra
     * iteraci&oacute;n
     */
    private boolean parseResponse(String response, Stream stream, HashMap<String, String>[] queriesArray, int iterations) {
        //System.out.println("Entrando a *******parseResponse*****************");
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
                            ArrayList<ExternalPost> aListExternalPost = new ArrayList();
                            for (int k = 0; k < postsData.length(); k++) {
                                cont++;
                                ExternalPost external = new ExternalPost();
                                external.setPostId(postsData.getJSONObject(k).getString("id"));
                                external.setCreatorId(postsData.getJSONObject(k).getJSONObject("from").getString("id"));
                                external.setCreatorName(postsData.getJSONObject(k).getJSONObject("from").getString("name"));
                                external.setCreationTime(postsData.getJSONObject(k).getString("created_time"));
                                external.setUpdateTime(postsData.getJSONObject(k).getString("updated_time"));
                                external.setCreatorPhotoUrl("http://graph.facebook.com/" + postsData.getJSONObject(k).getJSONObject("from").getString("id") + "/picture?width=150&height=150");
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

                                if (postsData.getJSONObject(k).has("likes")) {
                                    if (postsData.getJSONObject(k).getJSONObject("likes").has("count")) {
                                        external.setPostShared(postsData.getJSONObject(k).getJSONObject("likes").getInt("count"));
                                    }
                                }


                                if (postsData.getJSONObject(k).has("place")) {
                                    if (!postsData.getJSONObject(k).isNull("place")) {
                                        if (postsData.getJSONObject(k).getJSONObject("place").has("location")) {
                                            if (!postsData.getJSONObject(k).getJSONObject("place").isNull("location")) {
                                                String country = "";

                                                external.setLatitude(postsData.getJSONObject(k).getJSONObject("place").getJSONObject("location").getDouble("latitude"));
                                                external.setLongitude(postsData.getJSONObject(k).getJSONObject("place").getJSONObject("location").getDouble("longitude"));

                                                if (postsData.getJSONObject(k).getJSONObject("place").getJSONObject("location").has("country")) {//TODO: ver si en twitter es solo un codigo de 2 letras
                                                    if (!postsData.getJSONObject(k).getJSONObject("place").getJSONObject("location").isNull("country")) {
                                                        country = postsData.getJSONObject(k).getJSONObject("place").getJSONObject("location").getString("country");

                                                        if (country.equals("Mexico")) {
                                                            country = "MX";
                                                        }
                                                        if (country.equals("United States")) {
                                                            country = "US";
                                                        }

                                                        external.setCountryCode(country);
                                                    }
                                                    external.setPlace(country);
                                                }
                                            }
                                        }
                                    }
                                }



                                if (postsData.getJSONObject(k).has("type")) {
                                    if (postsData.getJSONObject(k).getString("type").equals("status")) {//Status -> message
                                        if (postsData.getJSONObject(k).has("message")) {
                                            external.setPostType(SWBSocialUtil.MESSAGE);
                                        } else {
                                            continue;
                                        }
                                    } else if (postsData.getJSONObject(k).getString("type").equals("photo")) {//Photo
                                        if (postsData.getJSONObject(k).has("picture")) {
                                            //external.setPicture(postsData.getJSONObject(k).getString("picture"));
                                            ArrayList pictures = new ArrayList();
                                            pictures.add(postsData.getJSONObject(k).getString("picture"));
                                            external.setPictures(pictures);
                                            external.setPostType(SWBSocialUtil.PHOTO);
                                        } else {//If has message, create it as message
                                            if (postsData.getJSONObject(k).has("message")) {
                                                external.setPostType(SWBSocialUtil.MESSAGE);
                                            } else {
                                                continue;
                                            }
                                        }
                                    } else if (postsData.getJSONObject(k).getString("type").equals("video")) {
                                        if (postsData.getJSONObject(k).has("source")) {
                                            external.setVideo(postsData.getJSONObject(k).getString("source"));
                                            external.setPostType(SWBSocialUtil.VIDEO);
                                        } else {//If has message, create it as message
                                            if (postsData.getJSONObject(k).has("message")) {
                                                external.setPostType(SWBSocialUtil.MESSAGE);
                                            } else {
                                                continue;
                                            }
                                        }
                                    } else if (postsData.getJSONObject(k).getString("type").equals("link")) {
                                        if (postsData.getJSONObject(k).has("message")) {
                                            external.setPostType(SWBSocialUtil.MESSAGE);
                                        } else {
                                            continue;
                                        }
                                    }
                                } else {//Do not process data without "type"
                                    continue;
                                }
                                external.setSocialNetwork(this);
                                aListExternalPost.add(external);
                            }
                            //Si el ArrayList tiene tamaño mayor a 0, entonces es que existen mensajes para enviar al clasificador
                            if (aListExternalPost.size() > 0) {
                                new Classifier(aListExternalPost, stream, this, true);
                            }
                            if (cont == Facebook.QUERYLIMIT) {
                                isThereMoreMsgs = true;  //Esto indica la posibilidad de que en una consulta siguiente, se obtengan más mensajes
                            } else if (cont == 0) {
                                isResponseEmpty = true;
                            }

                            //Se obtiene la url de la siguiente consulta a realizar para la frase correspondiente
                            String nextPage = null;
                            String previousPage = null;
                            if (dataOnBody.has("paging")) {
                                nextPage = dataOnBody.getJSONObject("paging").getString("next");
                                if (iterations == 0) {
                                    previousPage = dataOnBody.getJSONObject("paging").getString("previous");
                                }
                            } else if (isResponseEmpty) {
                                //si la busqueda no tuvo resultados, se repetira para la proxima ejecucion
                                nextPage = queriesArray[j].get("relative_url");
                                if (!nextPage.startsWith(Facebook.FACEBOOKGRAPH)) {
                                    nextPage = Facebook.FACEBOOKGRAPH + nextPage;
                                }
                            }

                            queriesArray[j].put("nextQuery", nextPage);
                            if (iterations == 0 && previousPage != null) {//Only the first time saves previousPage
                                //System.out.println("\t guardando previousPage:" + previousPage);
                                queriesArray[j].put("previousQuery", previousPage);
                            }
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
        if (message.getMsg_Text() == null) {
            log.error("Not message found, nothing to post");
            return;
        }
        Map<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", this.getAccessToken());
        params.put("message", message.getMsg_Text());
        params.put("privacy", "{'value':'" + privacyValue(message) + "'}");
        String url = Facebook.FACEBOOKGRAPH + this.getFacebookUserId() + "/feed";
        JSONObject jsonResponse = null;
        String facebookResponse = "";

        //TODO:REVISAR COMO HACER UN POST A ALGUNO O VARIOS USUARIOS
        try {
            if (message.getPostInSource() != null && message.getPostInSource().getSocialNetMsgId() != null) {//is a response
                System.out.println("1ST OPTION: RESPONDING TO SOMEONE:" + message.getPostInSource().getSocialNetMsgId());
                facebookResponse = postRequest(params, "https://graph.facebook.com/" + message.getPostInSource().getSocialNetMsgId() + "/comments",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
            } else {//is a single post to my wall
                System.out.println("2ND OPTION: MAKING SINGLE POST");
                facebookResponse = postRequest(params, url,
                        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95",
                        "POST");
            }
            /*
             facebookResponse = postRequest(params, url, 
             "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95",
             "POST");
             */

            jsonResponse = new JSONObject(facebookResponse);
            System.out.println("THIS IS THE RESPONSE MSG:" + jsonResponse);
            if (jsonResponse != null && jsonResponse.has("id")) {
                SWBSocialUtil.PostOutUtil.savePostOutNetID(message, this, jsonResponse.get("id").toString(), null);
                System.out.println("SAVING MSG SENT WITH POST ID: " + jsonResponse.get("id").toString());
                //message.setSocialNetPostId(jsonResponse.getString("id"));
                //addPost(message);
                //--TODO:Ver si se agrega esta línea despues(addSentPost(message, jsonResponse.getString("id"), this); )
                //, es para agregar el Post enviado a la clase PostOutContainer
                //Para que ahi se almacenen por mes y año y despues pueda ser mas facil y optimo hacer busquedas sobre PostOuts
                //addSentPost(message, jsonResponse.getString("id"), this); 
                //addPost(message, "IDpuestoxFacebook", this);
                //this.msg = message;
            } else {
                if (jsonResponse.has("error")) {
                    JSONObject error = jsonResponse.getJSONObject("error");
                    if (error.has("message")) {
                        SWBSocialUtil.PostOutUtil.savePostOutNetID(message, this, null, error.getString("message"));
                    }
                } else if (jsonResponse.has("message")) {
                    SWBSocialUtil.PostOutUtil.savePostOutNetID(message, this, null, jsonResponse.getString("message"));
                }
                log.error("Unable to post facebook message:" + jsonResponse);
            }
        } catch (IOException ioe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Problemas con el envio/recepcion de la peticion/respuesta, detail: "
                        + ioe.getMessage() + "\"}");
            } catch (JSONException jsone2) {
            }
            log.error("Problemas con el envio/recepcion de la peticion/respuesta con Facebook", ioe);
        } catch (JSONException jsone) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"La operacion no se pudo realizar, detail: "
                        + jsone.getMessage() + "\"}");
            } catch (JSONException jsone2) {
            }
            log.error("La operacion no se pudo realizar, objeto JSON mal formado debido a la respuesta de Facebook: "
                    + facebookResponse, jsone);
        }
    }

    public void postPhoto(Photo photo) {
        if (photo.listPhotos().hasNext() == false) {
            log.error("Not photos found, nothing to post");
            return;
        }

        Map<String, String> params = new HashMap<String, String>(2);
        params.put("privacy", "{'value':'" + privacyValue(photo) + "'}");
        if (this.getAccessToken() != null) {
            params.put("access_token", this.getAccessToken());
        }

        String url = Facebook.FACEBOOKGRAPH + this.getFacebookUserId() + "/photos";
        JSONObject jsonResponse = null;
        //String urlLocalPost = "http://localhost:8080/swbadmin/jsp/social/postViewFiles.jsp?uri=" + photo.getEncodedURI();
        //String uriTemp = "http://" + request.getServerName() + ":" + request.getServerPort() + SWBPortal.getWebWorkPath() + "/models/SWBAdmin/jsp/oauth/callback.jsp";
        String absolutePath = SWBPortal.getEnv("wb/absolutePath") == null ? "" : SWBPortal.getEnv("wb/absolutePath");
        String urlLocalPost = absolutePath + "/swbadmin/jsp/social/postViewFiles.jsp?uri=" + photo.getEncodedURI();
        try {
            String photoToPublish = "";
            String additionalPhotos = "";
            int photoNumber = 0;

            Iterator<String> photos = photo.listPhotos();
            while (photos.hasNext()) {
                String sPhoto = photos.next();
                if (++photoNumber == 1) {//post the first Photo
                    photoToPublish = SWBPortal.getWorkPath() + photo.getWorkPath() + "/" + sPhoto;
                }/*else{
                 additionalPhotos += SWBPortal.getWorkPath() + photo.getWorkPath() + "/" + sPhoto + " ";
                 }*/
            }
            if (photoNumber == 0) {
                log.error("No photo(s) found!");
                System.out.println("No Photos FOUND");
                return;
            } else if (photoNumber > 1) {
                additionalPhotos = urlLocalPost;
            }

            System.out.println("The photo to be published FACEBOOK:" + photoToPublish);
            System.out.println("Additional Photos FACEBOOK: " + additionalPhotos);


            //if text is not null, add it to the message. Always include additionalPhotos it may be empty if only one picture was found.
            params.put("message", (photo.getMsg_Text() == null ? "" : SWBSocialUtil.Util.shortUrl(photo.getMsg_Text() + " " + additionalPhotos)));
            /*
             if (photo.getMsg_Text() != null) {
             if(!additionalPhotos.isEmpty()){//Msg and photos
             params.put("message", photo.getMsg_Text() + " " + additionalPhotos);
             }else{//Only msg
             params.put("message", photo.getMsg_Text());
             }
             }else if(!additionalPhotos.isEmpty()){//Only photos
             params.put("message", additionalPhotos);
             }else {//Empty msg
             params.put("message", "");
             }*/

            SWBFile photoFile = new SWBFile(photoToPublish);

            if (photoFile.exists()) {
                SWBFileInputStream fileStream = new SWBFileInputStream(photoFile);
                String facebookResponse = "";

                //if it's a response to a post and a photo is included don't upload the photo, only the url to the local site
                if (photo.getPostInSource() != null && photo.getPostInSource().getSocialNetMsgId() != null) {
                    params.put("message", (photo.getMsg_Text() == null ? "" : SWBSocialUtil.Util.shortUrl(photo.getMsg_Text() + " " + urlLocalPost)));
                    facebookResponse = postRequest(params, "https://graph.facebook.com/" + photo.getPostInSource().getSocialNetMsgId() + "/comments",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
                    System.out.println("1ST OPTION: RESPONDING TO SOMEONE WITH PICTURE:" + photo.getPostInSource().getSocialNetMsgId());

                } else {//is a single post to my wall
                    facebookResponse = postFileRequest(params, url,
                            photoToPublish, fileStream, "POST", "photo");
                    System.out.println("2ND OPTION: MAKING SINGLE POST WITH PICTURE");
                }
                jsonResponse = new JSONObject(facebookResponse);
                fileStream.close();
            }
            
            System.out.println("THIS IS THE RESPONSE PHOTO:" + jsonResponse);
            if (jsonResponse != null && jsonResponse.has("id")) {
                SWBSocialUtil.PostOutUtil.savePostOutNetID(photo, this, jsonResponse.get("id").toString(), null);
                System.out.println("SAVING PHOTO SENT WITH POST ID: " + jsonResponse.get("id").toString());
                //photo.setSocialNetPostId(jsonResponse.getString("id"));
                //this.addPost(photo);
                //--TODO:Ver si se agrega esta línea despues(addSentPost(message, jsonResponse.getString("id"), this); )
                //, es para agregar el Post enviado a la clase PostOutContainer
                //Para que ahi se almacenen por mes y año y despues pueda ser mas facil y optimo hacer busquedas sobre PostOuts
                //addSentPost(photo, jsonResponse.getString("id"), this);
                //this.photo = photo;
            } else {
                if (jsonResponse.has("error")) {
                    JSONObject error = jsonResponse.getJSONObject("error");
                    if (error.has("message")) {
                        SWBSocialUtil.PostOutUtil.savePostOutNetID(photo, this, null, error.getString("message"));
                    }
                } else if (jsonResponse.has("message")) {
                    SWBSocialUtil.PostOutUtil.savePostOutNetID(photo, this, null, jsonResponse.getString("message"));
                }
                log.error("Unable to post facebook photo:" + jsonResponse);
            }
        } catch (FileNotFoundException fnfe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Archivo no encontrado\"}");
            } catch (JSONException jsone) {
            }
        } catch (JSONException jsone) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"La operacion no se pudo realizar\"}");
            } catch (JSONException jsone2) {
            }
        } catch (IOException ioe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Problemas con el envio/recepcion de la peticion/respuesta\"}");
            } catch (JSONException jsone2) {
            }
        }
    }

    public void postVideo(Video video) {
        if (video.getVideo() == null) {
            log.error("Not video found, nothing to post.");
            return;
        }
        Map<String, String> params = new HashMap<String, String>(3);
        if (this.getAccessToken() != null) {
            params.put("access_token", this.getAccessToken());
        }

        if (video.getTitle() != null) {
            params.put("title", video.getTitle());    //TODO:Estoy enviando esto como título a la red social, ver como lo pone en la misma
        }
        if (video.getMsg_Text() != null) {
            params.put("description", video.getMsg_Text());
        }
        params.put("privacy", "{'value':'" + privacyValue(video) + "'}");
        //String url = Facebook.FACEBOOKGRAPH + this.getFacebookUserId() + "/videos";
        String url = Facebook.FACEBOOKGRAPH_VIDEO + this.getFacebookUserId() + "/videos";
        JSONObject jsonResponse = null;
        //String urlLocalPost = "http://localhost:8080/swbadmin/jsp/social/postViewFiles.jsp?uri=" + video.getEncodedURI();
        String absolutePath = SWBPortal.getEnv("wb/absolutePath") == null ? "" : SWBPortal.getEnv("wb/absolutePath");
        String urlLocalPost = absolutePath + "/swbadmin/jsp/social/postViewFiles.jsp?uri=" + video.getEncodedURI();

        try {
            String videoPath = SWBPortal.getWorkPath() + video.getWorkPath() + "/" + video.getVideo();
            SWBFile videoFile = new SWBFile(videoPath);

            if (videoFile.exists()) {
                String facebookResponse;
                if (video.getPostInSource() != null && video.getPostInSource().getSocialNetMsgId() != null) {
                    params.put("message", (video.getMsg_Text() == null ? "" : SWBSocialUtil.Util.shortUrl(video.getMsg_Text() + " " + urlLocalPost)));
                    facebookResponse = postRequest(params, "https://graph.facebook.com/" + video.getPostInSource().getSocialNetMsgId() + "/comments",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
                    System.out.println("1ST OPTION: RESPONDING TO SOMEONE WITH VIDEO:" + video.getPostInSource().getSocialNetMsgId());

                } else {//is a single post to my wall
                    SWBFileInputStream fileStream = new SWBFileInputStream(videoFile);
                    facebookResponse = postFileRequest(params, url, video.getVideo(), fileStream, "POST", "video");
                    System.out.println("2ND OPTION: MAKING SINGLE POST WITH VIDEO");
                    fileStream.close();
                }
                jsonResponse = new JSONObject(facebookResponse);
            }

            System.out.println("THIS IS THE RESPONSE VIDEO:" + jsonResponse);
            if (jsonResponse != null && jsonResponse.has("id")) {
                SWBSocialUtil.PostOutUtil.savePostOutNetID(video, this, jsonResponse.get("id").toString(), null);
                System.out.println("SAVING VIDEO SENT WITH POST ID: " + jsonResponse.get("id").toString());
                //video.setSocialNetPostId(jsonResponse.getString("id"));
                //this.addPost(video);
                //--TODO:Ver si se agrega esta línea despues(addSentPost(message, jsonResponse.getString("id"), this); )
                //, es para agregar el Post enviado a la clase PostOutContainer
                //Para que ahi se almacenen por mes y año y despues pueda ser mas facil y optimo hacer busquedas sobre PostOuts
                //addSentPost(video, jsonResponse.getString("id"), this);
            } else {
                if (jsonResponse.has("error")) {
                    JSONObject error = jsonResponse.getJSONObject("error");
                    if (error.has("message")) {
                        SWBSocialUtil.PostOutUtil.savePostOutNetID(video, this, null, error.getString("message"));
                    }
                } else if (jsonResponse.has("message")) {
                    SWBSocialUtil.PostOutUtil.savePostOutNetID(video, this, null, jsonResponse.getString("message"));
                }
                log.error("Unable to post facebook video:" + jsonResponse);
            }
        } catch (FileNotFoundException fnfe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Archivo no encontrado\"}");
            } catch (JSONException jsone) {
            }
        } catch (JSONException jsone) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"La operacion no se pudo realizar\"}");
            } catch (JSONException jsone2) {
            }
        } catch (IOException ioe) {
            try {
                jsonResponse = new JSONObject("{\"errorMessage\" : \"Problemas con el envio/recepcion de la peticion/respuesta\"}");
            } catch (JSONException jsone2) {
            }
        }
    }

    /**
     * Realiza una petici&oacute;n al grafo de Facebook, de acuerdo a la url
     * recibida
     *
     * @param urlString especifica el objeto del grafo de Facebook con el que se
     * desea interactuar
     * @param userAgent indica el navegador de Internet utilizado en la
     * petici&oacute;n a realizar
     * @return un {@code String} que representa la respuesta generada por el
     * grafo de Facebook
     * @throws java.net.MalformedURLException si la url especificada a
     * trav&eacute;s de {@literal urlString} no est&aacute; correctamente
     * formada
     * @throws java.io.IOException en caso de que se produzca un error al
     * generar la petici&oacute;n o recibir la respuesta del grafo de Facebook
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
     * Realiza peticiones al grafo de Facebook que deban ser enviadas por
     * alg&uacute;n m&eacute;todo en particular
     *
     * @param params contiene los par&aacute;metros a enviar a Facebook para
     * realizar la operaci&oacute;n deseada
     * @param url especifica el objeto del grafo de Facebook con el que se desea
     * interactuar
     * @param userAgent indica el navegador de Internet utilizado en la
     * petici&oacute;n a realizar
     * @param method indica el m&eacute;todo de la petici&oacute; HTTP requerido
     * por Facebook para realizar una operaci&oacute;n, como: {@literal POST} o
     * {@literal DELETE}
     * @return un {@code String} que representa la respuesta generada por el
     * grafo de Facebook
     * @throws IOException en caso de que se produzca un error al generar la
     * petici&oacute;n o recibir la respuesta del grafo de Facebook
     */
    private String postRequest(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {

        URL serverUrl = new URL(url);
        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        //System.out.println("serverUrl: " + serverUrl);
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
            //System.out.println("params:" + paramString);
            in = conex.getInputStream();
            response = getResponse(in);
        } catch (java.io.IOException ioe) {
            response = getResponse(conex.getErrorStream());
            //ioe.printStackTrace();
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
     * Realiza peticiones al grafo de Facebook que deban ser enviadas por
     * alg&uacute;n m&eacute;todo en particular, en las que adem&aacute;, es
     * necesaria la inclusi&oacute;n de un archivo
     *
     * @param params contiene los par&aacute;metros a enviar a Facebook para
     * realizar la operaci&oacute;n deseada
     * @param url especifica el objeto del grafo de Facebook con el que se desea
     * interactuar
     * @param fileName indica el nombre del archivo a incluir en la
     * petici&oacute; a Facebook
     * @param fileStream representa el contenido del archivo, para incluirlo en
     * la petici&oacute; a Facebook
     * @param method indica el m&eacute;todo de la petici&oacute; HTTP requerido
     * por Facebook para realizar una operaci&oacute;n, como: {@literal POST}
     * @param itemToPost indica si el elemento a publicar en Facebook es una
     * foto o un video. Los valores aceptados por este parametro son:
     * {@literal photo} o {@literal video}
     * @return un {@code String} que representa la respuesta generada por el
     * grafo de Facebook, o la representaci&oacute;n de un objeto JSON con el
     * resultado de la ejecuci&oacute;n del metodo.
     * @throws IOException en caso de que se produzca un error al generar la
     * petici&oacute;n o recibir la respuesta del grafo de Facebook
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
            //facebookResponse = "{\"errorMsg\":\"Ocurrio un problema en la peticion a Facebook "
            //        + ioe.getMessage() + "\"}";
            try {
                facebookResponse = getResponse(conex.getErrorStream());
            } catch (Exception e) {
            }
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
     * En base al contenido de la colecci&oacute;n recibida, arma una secuencia
     * de caracteres compuesta de los pares:
     * <p>{@code Entry.getKey()} {@code equals} {@code Entry.getKey()} </p> Si
     * en la colecci&oacute;n hay m&aacute;s de una entrada, los pares (como el
     * anterior), se separan por {@code delimiter}.
     *
     * @param entries la colecci&oacute;n con la que se van a formar los pares
     * @param delimiter representa el valor con que se van a separar los pares a
     * representar
     * @param equals representa el valor con el que se van a relacionar los
     * elementos de cada par a representar
     * @param doEncode indica si el valor representado en cada par, debe ser
     * codificado (UTF-8) o no
     * @return la secuencia de caracteres que representa el conjunto de pares
     * @throws UnsupportedEncodingException en caso de ocurrir algun problema en
     * la codificaci&oacute;n a UTF-8 del valor de alg&uacute;n par, si
     * as&iacute; se indica en {@code doEncode}
     */
    private CharSequence delimit(Collection<Map.Entry<String, String>> entries,
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

    /**
     * Codifica el valor de {@code target} de acuerdo al c&oacute;digo de
     * caracteres UTF-8
     *
     * @param target representa el texto a codificar
     * @return un {@code String} que representa el valor de {@code target} de
     * acuerdo al c&oacute;digo de caracteres UTF-8
     * @throws UnsupportedEncodingException en caso de ocurrir algun problema en
     * la codificaci&oacute;n a UTF-8
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
     * Lee un flujo de datos y lo convierte en un {@code String} con su
     * contenido codificado en UTF-8
     *
     * @param data el flujo de datos a convertir
     * @return un {@code String} que representa el contenido del flujo de datos
     * especificado, codificado en UTF-8
     * @throws IOException si ocurre un problema en la lectura del flujo de
     * datos
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
     *
     * @param c cualquier objeto que tenga la factultad de cerrarse
     */
    private void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ex) {
                log.error("Error at closing object: " + c.getClass().getName(),
                        ex);
            }
        }
    }

    @Override
    public void authenticate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String error = request.getParameter("error");
        HttpSession session = request.getSession(true);
        //System.out.println("Facebook.autenticate.............");
        if (code == null) {
            //System.out.println("Facebook----paso 1");
            String url = doRequestPermissions(request, paramRequest);
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println(" function ioauth() {");
            out.println("  mywin = window.open(" + url + ",'_blank','width=840,height=680',true);");
            out.println("  if(mywin == null){");
            out.println("    alert('¿Tienes bloqueadas las ventajas emergentes?');");
            out.println("    return;");
            out.println("  }");
            out.println("  mywin.focus();");
            out.println(" }");
            out.println(" if(confirm('¿Autenticar la cuenta en Facebook?')) {");
            out.println("  ioauth();");
            out.println(" }");
            out.println("</script>");
        } else if (state != null && state.equals(session.getAttribute("state")) && error == null) {
            //System.out.println("Facebook----2");
            session.removeAttribute("state");
            String accessToken = null;
//            long secsToExpiration = 0L;
            String token_url = "https://graph.facebook.com/oauth/access_token?" + "client_id=" + getAppKey() + "&redirect_uri=" + URLEncoder.encode(getRedirectUrl(request, paramRequest), "utf-8") + "&client_secret=" + getSecretKey() + "&code=" + code;
//            if(accessToken == null) {
            //System.out.println("Facebook----2.1");
            URL pagina = new URL(token_url);
            URLConnection conex = null;
            try {
                //Se realiza la peticion a la página externa
                conex = pagina.openConnection();
                if (request.getHeader("user-agent") != null) {
                    conex.setRequestProperty("user-agent", request.getHeader("user-agent"));
                }
                if (pagina.getHost() != null) {
                    conex.setRequestProperty("host", pagina.getHost());
                }
                conex.setConnectTimeout(5000);
            } catch (Exception nexc) {
                conex = null;
                //System.out.println("error error error error error error error error 1");
                //System.out.println("error........"+nexc);
            }
            if (conex != null) {
                String answer;
                try {
                    answer = SWBUtils.IO.readInputStream(conex.getInputStream());
                } catch (Exception e) {
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
            if (accessToken != null) {
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
                } catch (JSONException e) {
                    log.error(e);
                    request.setAttribute("msg", "hubo problemas. contacta a tu administrador para revisar el visor de errores");
                    PrintWriter out = response.getWriter();
                    out.println("<script type=\"text/javascript\">");
                    out.println("  window.close();");
                    out.println("</script>");
                    //System.out.println("error error error error error error error error 2");
                }
            }
        } else {
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

    private String getRedirectUrl(HttpServletRequest request, SWBParamRequest paramRequest) {
        StringBuilder address = new StringBuilder(128);
        address.append("http://").append(request.getServerName()).append(":").append(request.getServerPort()).append("/").append(paramRequest.getUser().getLanguage()).append("/").append(paramRequest.getResourceBase().getWebSiteId()).append("/" + paramRequest.getWebPage().getId() + "/_rid/").append(paramRequest.getResourceBase().getId()).append("/_mod/").append(paramRequest.getMode()).append("/_lang/").append(paramRequest.getUser().getLanguage());
        return address.toString();
    }

    public String doRequestPermissions(HttpServletRequest request, SWBParamRequest paramRequest) {
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
        url.append("encodeURIComponent('user_about_me,friends_about_me,user_activities,friends_activities,user_birthday,friends_birthday,user_checkins,friends_checkins,user_education_history,friends_education_history,user_events,friends_events,user_groups,friends_groups,user_hometown,friends_hometown,user_interests,friends_interests,user_likes,friends_likes,user_location,friends_location,user_notes,friends_notes,user_photos,friends_photos,user_relationships,friends_relationships,user_relationship_details,friends_relationship_details,user_religion_politics,friends_religion_politics,user_status,friends_status,user_videos,friends_videos,user_website,friends_website,email,manage_pages,publish_stream,read_stream,read_page_mailboxes,read_insights,ads_management,user_questions,user_subscriptions,user_work_history,user_games_activity,friends_subscriptions,friends_work_history,friends_questions,friends_games_activity,publish_actions,create_note,friends_online_presence,share_item,status_update')+");
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

    public void postPhotoSocialNets(Facebook facebook, SocialWebPage swb, Language l, StringBuilder address) {

        Map<String, String> params = new HashMap<String, String>(2);
        if (facebook.getAccessToken() != null) {
            params.put("access_token", facebook.getAccessToken());
        }

        String url = "https://graph.facebook.com/" + facebook.getFacebookUserId() + "/photos";


        try {
            String photoToPublish = "";
            //String additionalPhotos = "";
            String urlShort = SWBSocialUtil.Util.shortUrl(address.toString());
            String description = swb.getDescription(l.getId()) == null ? "" : swb.getDescription(l.getId());
            params.put("message", (description) + " " + urlShort);

            photoToPublish = SWBPortal.getWorkPath() + swb.getWorkPath() + "/" + SocialWebPage.social_socialwpPhoto.getName() + "_" + swb.getId() + "_" + swb.getSocialwpPhoto();
            SWBFile photoFile = new SWBFile(photoToPublish);

            System.out.println("photoToPublish" + photoToPublish);
            if (photoFile.exists()) {
                SWBFileInputStream fileStream = new SWBFileInputStream(photoFile);
                String facebookResponse = "";
                facebookResponse = postFileRequest(params, url,
                        photoToPublish, fileStream, "POST", "photo");
            }


        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioe) {
        }
    }

    /**
     * Realiza una peticion get a la URL especificada
     *
     * @param params arreglo de nombres de parámetro-valor
     * @param url url destino
     * @param userAgent user agent
     * @return la respuesta del servidor o el mensaje de error obtenido de la
     * petición
     * @throws IOException
     */
    private String getRequest(Map<String, String> params, String url,
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

        } catch (java.io.IOException ioe) {
            response = getResponse(conex.getErrorStream());
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

    /**
     *
     * @param id identifier of the facebook user
     * @return returns a JSONObject containing the requested fields with a valid
     * value {friends, followers, latitude, longitude, country_code, place_name}
     * all the values might or might not be present in the JSONObject.
     */
    @Override
    public JSONObject getUserInfobyId(String userId) {
        HashMap<String, String> params = new HashMap<String, String>(2);
        //params.put("q", "SELECT friend_count, subscriber_count, current_location, sex, relationship_status, birthday_date, email, education, work  FROM user WHERE uid = " + userId);
        params.put("q", "{\"usuario\": \"SELECT friend_count FROM user WHERE uid = " + userId + "\", \"pagina\": \"SELECT fan_count  FROM page WHERE page_id = " + userId + "\"}");
        params.put("access_token", this.getAccessToken());

        JSONObject userInfo = new JSONObject();
        try {
            String fbResponse = getRequest(params, "https://graph.facebook.com/fql",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");
            try {
                JSONObject parseUsrInf = new JSONObject(fbResponse);
                JSONObject user = null;
                JSONObject page = null;
                
                
                System.out.println("THE RESPONSE:" + parseUsrInf);
                if (!parseUsrInf.has("data")) {
                    log.error("Not data found for id:" + userId);
                    return null;
                }
                JSONArray data = parseUsrInf.getJSONArray("data");
                for(int i = 0; i < data.length(); i++){
                    JSONObject entry = data.getJSONObject(i);
                    if(!entry.isNull("name") && entry.getString("name").equals("pagina")){
                        if(entry.getJSONArray("fql_result_set").length() == 1){
                            page = entry.getJSONArray("fql_result_set").getJSONObject(0);
                        }
                    }else if(!entry.isNull("name") && entry.getString("name").equals("usuario")){
                        if(entry.getJSONArray("fql_result_set").length() == 1){
                            user = entry.getJSONArray("fql_result_set").getJSONObject(0);
                        }
                    }
                }
                
                //JSONArray usrData = parseUsrInf.getJSONArray("data");
                if (user != null) {
                    parseUsrInf = user;

                    //Check if fields exists and if they're not null
                    //Friends
                    if (parseUsrInf.has("friend_count") && !parseUsrInf.isNull("friend_count")) {
                        userInfo.put("friends", parseUsrInf.getLong("friend_count"));
                    } else {
                        userInfo.put("friends", 0);
                    }
                    //Followers
                    if (parseUsrInf.has("subscriber_count") && !parseUsrInf.isNull("subscriber_count")) {
                        userInfo.put("followers", parseUsrInf.getLong("subscriber_count"));
                    } else {
                        userInfo.put("followers", 0);
                    }


                    if (parseUsrInf.has("current_location") && !parseUsrInf.isNull("current_location")) {
                        JSONObject location = parseUsrInf.getJSONObject("current_location");
                        /*
                         //Latitude
                         if (location.has("latitude") && !location.isNull("latitude")) {
                         userInfo.put("latitude", location.getDouble("latitude"));
                         }
                         //Longitude
                         if (location.has("longitude") && !location.isNull("longitude")) {
                         userInfo.put("longitude", location.getDouble("longitude"));
                         }
                         //Country Code
                         if (location.has("country") && !location.isNull("country")) {
                         if (location.getString("country").toLowerCase().contains("mexico")) {
                         userInfo.put("country_code", "MX");
                         } else if (location.getString("country").toLowerCase().contains("united states")) {
                         userInfo.put("country_code", "US");
                         }
                         }
                         * */
                        //Name of the place
                        if (location.has("name") && !location.isNull("name")) {
                            userInfo.put("place_name", location.getString("name"));
                        }
                    }


                    //Gender
                    if (parseUsrInf.has("sex") && !parseUsrInf.isNull("sex")) {
                        userInfo.put("gender", parseUsrInf.getString("sex"));
                    } else {
                        userInfo.put("gender", "");
                    }

                    //relationship_status
                    if (parseUsrInf.has("relationship_status") && !parseUsrInf.isNull("relationship_status")) {
                        userInfo.put("relationship_status", parseUsrInf.getString("relationship_status"));
                    } else {
                        userInfo.put("relationship_status", "");
                    }

                    //birthday
                    if (parseUsrInf.has("birthday_date") && !parseUsrInf.isNull("birthday_date")) {
                        String fecha = parseUsrInf.getString("birthday_date");
                        boolean year = false;
                        year = isDate(fecha);
                        if (year) {
                            userInfo.put("birthday", fecha);
                        } else {
                            userInfo.put("birthday", "");
                        }
                    } else {
                        userInfo.put("birthday", "");
                    }

                    //email
                    if (parseUsrInf.has("email") && !parseUsrInf.isNull("email")) {
                        userInfo.put("email", parseUsrInf.getString("email"));
                    } else {
                        userInfo.put("email", "");
                    }

                    //education
                    JSONArray jArrayEduacation = null;
                    if (parseUsrInf.has("education") && !parseUsrInf.isNull("education")) {
                        jArrayEduacation = parseUsrInf.getJSONArray("education");
                        userInfo.put("education", jArrayEduacation);

                    } else {
                        jArrayEduacation = new JSONArray();
                        userInfo.put("education", jArrayEduacation);
                    }

                    //work
                    /*
                     JSONArray jArrayWork = null;
                     if (parseUsrInf.has("work") && !parseUsrInf.isNull("work")) {
                     jArrayWork = parseUsrInf.getJSONArray("work");
                     userInfo.put("work", jArrayWork);
                     } else {
                     jArrayWork = new JSONArray();
                     userInfo.put("work", jArrayWork);
                     }*/
                }else if (page != null) {
                    //parseUsrInf = usrData.getJSONObject(0);
                    parseUsrInf = page;
                    if (parseUsrInf.has("fan_count") && !parseUsrInf.isNull("fan_count")) {
                        userInfo.put("followers", parseUsrInf.getLong("fan_count"));
                    } else {
                        userInfo.put("followers", 0);
                    }
                }
                System.out.println("parseUsrInf:" + parseUsrInf);
                //if(parseUsrInf.has(id))
            } catch (JSONException jsone) {
                log.error("Error parsing json response from facebook ", jsone);
            }

        } catch (IOException e) {
            System.out.println("Error getting user information" + e.getMessage());
        }
        return userInfo;
    }

    public boolean isDate(String date) {
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            Date f = formatDate.parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private String privacyValue(PostOut postout) {
        Iterator<PostOutPrivacyRelation> privacyRelation = PostOutPrivacyRelation.ClassMgr.listPostOutPrivacyRelationByPopr_postOut(postout);
        String privacy = "EVERYONE";//Default Value
        try {
            while (privacyRelation.hasNext()) {
                PostOutPrivacyRelation privacyR = privacyRelation.next();
                if (privacyR.getPopr_socialNetwork().getURI().equals(this.getURI())) {
                    if (privacyR.getPopr_privacy() != null) {
                        //For facebook PUBLIC->EVERYONE
                        //And PRIVATE->SELF
                        System.out.println("PRIVACY--->" + privacyR.getPopr_privacy().getId() + "<---");
                        if (privacyR.getPopr_privacy().getId().equals("PUBLIC")) {
                            privacy = "EVERYONE";
                        } else if (privacyR.getPopr_privacy().getId().equals("PRIVATE")) {
                            privacy = "SELF";
                        } else {
                            privacy = privacyR.getPopr_privacy().getId();
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Problem setting privacy:", e);
        }
        System.out.println("PRIVACY:" + privacy);
        return privacy;
    }

    @Override
    public HashMap monitorPostOutResponses(PostOut postOut) {
        HashMap hMapPostOutNets=new HashMap();
        Iterator<PostOutNet> itPostOutNets=PostOutNet.ClassMgr.listPostOutNetBySocialPost(postOut);
        while(itPostOutNets.hasNext())
        {
            PostOutNet postOutNet=itPostOutNets.next();
            if(postOutNet.getStatus()==1 && postOutNet.getSocialNetwork().getURI().equals(this.getURI()))
            {
                System.out.println("********** Monitoreando RESPUESTAS de " + postOutNet.getPo_socialNetMsgID() + "*************");
                
                long totalComments = this.comments(postOutNet.getPo_socialNetMsgID());
                //El número que se agrega es la diferencia entre el número de respuesta encontradas en la red social - el que se encuentra en la propiedad postOutNet.getPo_numResponses()
                
                if(totalComments > 0){
                    if(postOutNet.getPo_numResponses() > 0){//Si ya había respuestas
                        if(postOutNet.getPo_numResponses() < totalComments){//Si hay respuestas nuevas
                            hMapPostOutNets.put(postOutNet.getURI(), totalComments - postOutNet.getPo_numResponses());
                        }
                    }else if(postOutNet.getPo_numResponses() == 0){//Si no había respuestas
                        hMapPostOutNets.put(postOutNet.getURI(), totalComments);
                    }
                    postOutNet.setPo_numResponses((int)totalComments);
                }
            }
        }
        return hMapPostOutNets;
    }

    private long comments(String postID){
        HashMap<String, String> params = new HashMap<String, String>(2);
        params.put("access_token", this.getAccessToken());
        long totalComments  = 0;
        params.put("fields","comments.summary(true)");
        try{
            String fbResponse = getRequest(params, "https://graph.facebook.com/" + postID,
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95");

            //System.out.println("Facebook response:" + fbResponse);
            JSONObject response = new JSONObject(fbResponse);
            if(!response.isNull("comments")){
                if(!response.getJSONObject("comments").isNull("summary")){
                    if(!response.getJSONObject("comments").getJSONObject("summary").isNull("total_count")){
                        //System.out.println(response.getJSONObject("comments").getJSONObject("summary"));
                        totalComments = response.getJSONObject("comments").getJSONObject("summary").getLong("total_count");
                    }
                }
            }
            System.out.println("FACEBOOK:" + postID + " AND THE NUMBER:" + totalComments);
        }catch(Exception e){
            log.error("Facebook: Not data found for ->" + postID);
        }
        return totalComments;      
    }
}