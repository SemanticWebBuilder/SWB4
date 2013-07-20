package org.semanticwb.social;

import com.google.gdata.client.youtube.YouTubeService;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.io.SWBFile;
import org.semanticwb.model.SWBContext;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.listener.Classifier;
import org.semanticwb.social.util.SWBSocialUtil;

public class Youtube extends org.semanticwb.social.base.YoutubeBase {

    private static Logger log = SWBUtils.getLogger(Youtube.class);
    private Date lastVideoID;
    String UPLOAD_URL = "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";

    public Youtube(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /* public void postVideo(Video video) {
     System.out.println("Video K llega a Youtube:" + video);
     System.out.println("Video id:" + video.getId());
     //System.out.println("Video title:"+video.getTitle());
     //System.out.println("Video descr:"+video.getDescription());
     System.out.println("Video Tags:" + video.getTags());
     System.out.println("Video getVideo:" + video.getVideo());
     YouTubeService service = getYouTubeService();
     if (service == null) {
     return;
     }
     //String action = response.getAction();
     try {
     //if (action.equals("uploadVideo")) {

     //    WebSite wsite=response.getWebPage().getWebSite();

     VideoEntry newEntry = new VideoEntry();


     newEntry.setLocation("Mexico"); // Debe estar desde la configuración de la red social
     YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
     //http://gdata.youtube.com/schemas/2007/categories.cat-->pienso que a una cirta comunidad se le deberÃ­a asignar una categoria en especifico
     //(de las del archivo de la mencionada url, ej. Autos) y serÃ­a con la que se subieran los nuevos videos y de esta manera
     //ya no le mostrarÃ­a un combo con todas las categorias para que el usuario final escogiera, porque en realidad en una comunidad se deberian
     //de subir videos con una cierta categoria solamente, que serÃ­a que tuviera relaciÃ³n con el tipo de comunidad en la que se esta.
     //***El tÃ­tulo, la categoria y por lo menos un keyword son requeridos.

     mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, "Autos"));       // Debe estar desde la configuración de la red social
     mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "xyzzy"));  // Debe estar desde la configuración de la red social

     String title = "SWBSocial"; //TODO:Ver como aparece este título en YouTube y si lo requiere o es opcional
     if (title != null && title.trim().length() > 0) {
     mg.setTitle(new MediaTitle());
     mg.getTitle().setPlainTextContent(title);
     }
     String keywords = video.getTags();
     if (keywords != null && keywords.trim().length() > 0) {
     mg.setKeywords(new MediaKeywords());
     if (keywords.indexOf(",") > -1) {
     StringTokenizer strTokens = new StringTokenizer(keywords, ",");
     while (strTokens.hasMoreTokens()) {
     String token = strTokens.nextToken();
     mg.getKeywords().addKeyword(token);
     }
     } else {
     mg.getKeywords().addKeyword(keywords);
     }
     }
     String description = video.getMsg_Text();
     if (description != null && description.trim().length() > 0) {
     mg.setDescription(new MediaDescription());
     mg.getDescription().setPlainTextContent(description);
     }
     //mg.setPrivate(false);
     //URL uploadUrl = new URL("http://gdata.youtube.com/action/GetUploadToken");
     //FormUploadToken token = service.getFormUploadToken(uploadUrl, newEntry);

     mg.setPrivate(false);


     newEntry.setGeoCoordinates(new GeoRssWhere(37.0, -122.0));       //ver como puedo obtener estos datos (latitud y longitud) dinamicamente
     // alternatively, one could specify just a descriptive string
     // newEntry.setLocation("Mountain View, CA");

     String videoSend = SWBPortal.getWorkPath() + video.getWorkPath() + "/" + video.getVideo();
     MediaFileSource ms = new MediaFileSource(new File(videoSend), "video/quicktime");
     newEntry.setMediaSource(ms);

     VideoEntry entry = service.insert(new URL(UPLOAD_URL), newEntry);
     System.out.println("createdEntry:" + entry);
     System.out.println("createdEntry:" + entry.getId());
     System.out.println("entry sefLink:" + entry.getSelfLink());
     System.out.println("entry getEtag:" + entry.getEtag());
     System.out.println("entry getKind:" + entry.getKind());
     System.out.println("entry getVersionId:" + entry.getVersionId());

     int post = -1;
     post = entry.getId().lastIndexOf(":");
     if (post > -1) {
     String idEntry = entry.getId().substring(post + 1);
     System.out.println("idEntry********:" + idEntry);
     //SWBSocialUtil.MONITOR.persistPost2Monitor(video, idEntry, this, wsite);
     addSentPost(video, idEntry, this);
     }

     System.out.println("createdEntry:" + entry.getPublicationState().getState().name());

     //you upload a video using the direct upload method, then the Upload API response will contain a <link> tag for which the value of the rel attribute is self. To check the status of the uploaded video, send a GET request to the URL identified in this <link> tag.
     //<link rel='self' type='application/atom+xml' href='https://gdata.youtube.com/feeds/api/users/default/uploads/Video_ID'/>

     if (entry.isDraft()) {
     System.out.println("Video is not live");
     YtPublicationState pubState = entry.getPublicationState();
     if (pubState.getState() == YtPublicationState.State.PROCESSING) {
     System.out.println("Video is still being processed.");
     } else if (pubState.getState() == YtPublicationState.State.REJECTED) {
     System.out.print("Video has been rejected because: ");
     System.out.println(pubState.getDescription());
     System.out.print("For help visit: ");
     System.out.println(pubState.getHelpUrl());
     } else if (pubState.getState() == YtPublicationState.State.FAILED) {
     System.out.print("Video failed uploading because: ");
     System.out.println(pubState.getDescription());
     System.out.print("For help visit: ");
     System.out.println(pubState.getHelpUrl());
     }
     }


     //response.setRenderParameter("jspResponse", "/swbadmin/jsp/social/videoable/videoable.jsp");
     //response.setRenderParameter("videoId", newEntry.getId());
     //}
     } catch (Exception e) {
     log.error(e);
     }
     }*/
    @Override
    public void postVideo(Video video) {

        System.out.println("Entra al metodo postVideo de YouTube....");
        
        YouTubeCategory youTubeCat;
        String allCategories=video.getCategory();
        String[] arrayCat=allCategories.split(";");
        for(int i=0;i<arrayCat.length;i++)
        {
            String category=arrayCat[i];
            youTubeCat=YouTubeCategory.ClassMgr.getYouTubeCategory(category, SWBContext.getAdminWebSite());
        }
        
        
       
        //Valida que este activo el token, de lo contrario manda el token refresh
        //para que nos regrese un nuevo 
        /*
        try {
            HttpClient client = new DefaultHttpClient();
            //client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpPost post = new HttpPost("https://www.googleapis.com/oauth2/v2/tokeninfo?access_token=" + this.getAccessToken());
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = client.execute(post, responseHandler);
            System.out.println("la respuesta es: " + responseBody);
        } catch (HttpResponseException e) {
            System.out.println("Msg" + e.getMessage());
            System.out.println("Error code" + e.getStatusCode());
            if (e.getStatusCode() == 400) {
                System.out.println("entra al error 400....");
                try {
                    Map<String, String> params = new HashMap<String, String>();
                    //Temporalmente comentado por que ya se habia autenticado mi cuenta sin pedir el refresh token
                    //params.put("refresh_token", this.getAccessTokenSecret());
                    params.put("refresh_token", "1/WY53_4yVfdnoCZ9WATEjVdvt8GgZOobQ9YC5T77PjwY");
                    params.put("client_id", this.getAppKey());
                    params.put("client_secret", this.getSecretKey());
                    params.put("grant_type", "refresh_token");
                    String res = postRequest(params, "https://accounts.google.com/o/oauth2/token", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
                    System.out.println("respuesta de peticion del token nuevo" + res);
                    JSONObject userData = new JSONObject(res);
                    String tokenAccess = userData.getString("access_token");
                    setAccessToken(tokenAccess);
                } catch (IOException io) {
                    System.out.println("Error en la peticion del nuevo accessToken" + io);
                } catch (JSONException ex) {
                    System.out.println("Error en la respuesta del nuevo accessToken" + ex);
                }
            }
            e.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }

        System.out.println("el token de acceso es: " + this.getAccessToken());
        System.out.println("la developerkey es: " + this.getDeveloperKey());

        String url = UPLOAD_URL;

        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost post = new HttpPost(url);
        String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String boundary = "";
        for (int i = 0; i < 8; i++) {
            int numero = (int) (Math.random() * base.length());
            String caracter = base.substring(numero, numero + 1);
            boundary = boundary + caracter;
        }
        try {
            String videoPath = SWBPortal.getWorkPath() + video.getWorkPath() + "/" + video.getVideo();
            SWBFile fileVideo = new SWBFile(videoPath);

            System.out.println("filevideo:  " + fileVideo);
            System.out.println("nombre del video: " + video.getVideo());
            String[] arr = video.getVideo().split("\\.");
            String ext = arr[1];
            System.out.println("la extension del archivo es: " + ext);

            post.setHeader("Authorization", "Bearer " + this.getAccessToken());
            post.setHeader("GData-Version", "2");
            //post.setHeader("X-GData-Key", "key=" +this.//Falta developerkey);
            post.setHeader("X-GData-Key", "key=" + this.getDeveloperKey());
            post.setHeader("Slug", video.getVideo());
            post.setHeader("Content-Type", "multipart/related; boundary=" + boundary);
            post.setHeader("Connection", "close");
            post.setHeader("", "--" + boundary);
            post.setHeader("Content-Type", "application/atom+xml; charset=UTF-8");
            System.out.println(post.getMethod());
            StringEntity entity = new StringEntity(
                    "<?xml version=\"1.0\"?>"
                    + "<entry xmlns=\"http://www.w3.org/2005/Atom\""
                    + " xmlns:media=\"http://search.yahoo.com/mrss/\""
                    + " xmlns:yt=\"http://gdata.youtube.com/schemas/2007\">"
                    + "<media:group>"
                    + "<media:title type=\"plain\">Bad Wedding Toast</media:title>"
                    + "<media:description type=\"plain\">"
                    + "I gave a bad toast at my friend's wedding."
                    + "</media:description>"
                    + "<media:category "
                    + "scheme=\"http://gdata.youtube.com/schemas/2007/categories.cat\">People"
                    + "</media:category>"
                    + "<media:keywords>toast, wedding</media:keywords>"
                    + "</media:group>"
                    + "</entry>");
            post.setEntity(entity);
            post.setHeader("", "--" + boundary);
            post.setHeader("Content-Type", "video/" + ext);
            post.setHeader("Content-Transfer-Encoding", "binary");

            if (fileVideo.exists()) {
                //File fileVideo = new File("C:/Users/ana.garcias/Documents/Video.asf");
                byte[] archivoBytes = null;
                long tamanoArch = fileVideo.length();
                if (tamanoArch > Integer.MAX_VALUE) {
                    System.out.println("El archivo es demasiado largo.");
                } else {

                    try {
                        archivoBytes = new byte[(int) tamanoArch]; //Le damos al array el tamaño del archivo.
                        FileInputStream docu = new FileInputStream(fileVideo);
                        //Leemos los bytes del archivo y a la vez se van insertando en el array de bytes creado.
                        int numBytes = docu.read(archivoBytes);
                        System.out.println("El archivo tiene " + numBytes + " de bytes.");
                        docu.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("No se ha encontrado el archivo.");
                    } catch (IOException e) {
                        System.out.println("No se ha podido leer el archivo." + e);
                    }
                }

                if (archivoBytes != null) {
                    post.setEntity(new ByteArrayEntity(archivoBytes));
                }

                post.setHeader("", "--" + boundary + "--");
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String responseBody = client.execute(post, responseHandler);
                System.out.println("responseBody : " + responseBody);
            }
        } catch (HttpResponseException e) {
            System.out.println("Msg" + e.getMessage());
            System.out.println("Error code" + e.getStatusCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        * */
        try
        {
           System.out.println("Va a Grabar en savePostOutNetID - George/video:"+video+", this:"+this);
           SWBSocialUtil.PostOutUtil.savePostOutNetID(video, this, "12345678");
        }catch(Exception e)
        {
            
        }
        
    }

    private YouTubeService getYouTubeService() {
        //YouTubeService service = new YouTubeService("SEMANTICWEBBUILDER", "AI39si4crQ_Zn6HmLxroe0TP48ZDkOXI71uodU9xc1QRyl8Y5TaRc2OIIOKMEatsw9Amce81__JcvvwObue_8yXD2yC6bFRhXA");
        YouTubeService service = new YouTubeService(getAppKey(), getSecretKey());
        try {
            //service.setUserCredentials(getLogin(), getPassword());
        } catch (Exception e) {
            log.error("Invalid login credentials:", e);
        }
        return service;
    }

    @Override
    public String doRequestPermissions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doRequestAccess() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String postRequest2(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {

        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);

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

            System.out.println("CONNECT:" + conex);
            in = conex.getInputStream();
            response = getResponse(in);

        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                System.out.println("ERROR:" + getResponse(conex.getErrorStream()));
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

    private String getRedirectUrl(HttpServletRequest request, SWBParamRequest paramRequest) {
        //System.out.println("getRedirectUrl....");
        StringBuilder address = new StringBuilder(128);
        address.append("http://").append(request.getServerName()).append(":").append(request.getServerPort()).append("/").append(paramRequest.getUser().getLanguage()).append("/").append(paramRequest.getResourceBase().getWebSiteId()).append("/" + paramRequest.getWebPage().getId() + "/_rid/").append(paramRequest.getResourceBase().getId()).append("/_mod/").append(paramRequest.getMode()).append("/_lang/").append(paramRequest.getUser().getLanguage());
        //System.out.println("URL callback="+address);
        return address.toString();
    }

    CharSequence delimit(Collection<Map.Entry<String, String>> entries,
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

    private String encode(CharSequence target) throws UnsupportedEncodingException {

        String result = "";
        if (target != null) {
            result = target.toString();
            result = URLEncoder.encode(result, "UTF8");
        }
        return result;
    }

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

    private void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ex) {
            }
        }
    }

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

    @Override
    public void authenticate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String code = request.getParameter("code");
        System.out.println("Entra al metodo authenticate ...codigo" + code);
        PrintWriter out = response.getWriter();
        String clientId = getAppKey();
        String clientSecret = getSecretKey();
        String developerKey = getDeveloperKey();
        String uri = getRedirectUrl(request, paramRequest);
        //YouTube no permite enviarle una url dinamica por lo cual se envia a un jsp y nuevamnete se redirecciona
        String uriTemp = "http://localhost:8080/work/SWBAdmin/jsp/oauth/callback.jsp";
        //Se crea una variable de sesion para recuperar en el jsp la url dinamica
        HttpSession session = request.getSession(true);
        session.setAttribute("redirectYouTube", uri);


        if (code == null) {
            out.println("<script type=\"text/javascript\">");
            out.println(" function ioauth() {");
            out.println("  mywin = window.open('https://accounts.google.com/o/oauth2/auth?client_id=" + clientId + "&redirect_uri=" + uriTemp + "&response_type=code&scope=https://gdata.youtube.com&access_type=offline','_blank','width=840,height=680',true);");
            out.println("  mywin.focus();");
            out.println(" }");
            out.println(" if(confirm('¿Autenticar la cuenta en YouTube?')) {");
            out.println("  ioauth();");
            out.println(" }");
            out.println("</script>");
        } else {
            Map<String, String> params = new HashMap<String, String>();
            params.put("code", code);
            params.put("client_id", clientId);
            params.put("client_secret", clientSecret);
            params.put("redirect_uri", uriTemp);
            params.put("grant_type", "authorization_code");
            //params.put("access_type", "offline");
            try {
                String res = postRequest(params, "https://accounts.google.com/o/oauth2/token", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
                System.out.println("respuesta" + res);

                JSONObject userData = new JSONObject(res);
                String tokenAccess = userData.getString("access_token");
                String token_type = userData.getString("token_type");
                String refresh_token = userData.getString("refresh_token");


                setAccessToken(tokenAccess);
                //Temporalmente guardando el token Refresh:
                //setRefreshToken(code);
                setAccessTokenSecret(refresh_token);
                setSn_authenticated(true);
                System.out.println("refresh token: " + refresh_token);
                System.out.println("token access:  " + tokenAccess);
                System.out.println("tipo de token: " + token_type);
                System.out.println("developer key: " + developerKey);

            } catch (Exception ex) {
                System.out.println("Error en la autenticacion: " + ex);
            } finally {
                out.println("<script type=\"text/javascript\">");
                out.println("  window.close();");
                out.println("</script>");
            }
        }
    }

    private void getLastVideoID(Stream stream) {
        System.out.println("entrando al metodo getLastVideoID....");
        SocialNetStreamSearch socialStreamSerch = SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
        //socialStreamSerch.setNextDatetoSearch("2013-06-17T15:42:09.000Z");
        //if(1==1)return;

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        //formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
        try {
            if (socialStreamSerch != null && socialStreamSerch.getNextDatetoSearch() != null) {
                //socialStreamSerch.setNextDatetoSearch("2000-07-11T23:05:31.000Z");
                lastVideoID = formatter.parse(socialStreamSerch.getNextDatetoSearch());
                System.out.println("RECOVERING NEXTDATETOSEARCH: " + socialStreamSerch.getNextDatetoSearch());
            } else {
                lastVideoID = new Date(0L);
            }
        } catch (NumberFormatException nfe) {
            lastVideoID = new Date(0L);
            log.error("Error in getLastVideoID():" + nfe);
            System.out.println("Invalid value found in NextDatetoSearch(). Set:" + lastVideoID);
        } catch (ParseException pex) {
            log.error("Error in parseDate() in getLastVideoID:" + pex);
        }
    }

    private void setLastVideoID(String dateVideo, Stream stream) {
        System.out.println("entrando al metodo setLastVideoID....");
        //if(1==1)return;
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            // formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
            Date storedValue = new Date(0L);
            SocialNetStreamSearch socialStreamSerch = SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
            if (socialStreamSerch != null && socialStreamSerch.getNextDatetoSearch() != null) {
                storedValue = formatter.parse(socialStreamSerch.getNextDatetoSearch());
            }
            System.out.println("Antes de validar las fechas: ");
            System.out.println("stored Value : " + storedValue + "  dateVideo:  " + formatter.parse(dateVideo));
            if (formatter.parse(dateVideo).after(storedValue)) {
                //if (storedValue.before(formatter.parse(dateVideo))) { //Only stores tweetID if it's greater than the current stored value
                socialStreamSerch.setNextDatetoSearch(dateVideo);
                System.out.println("GUARDANDO FECHA!!:" + dateVideo);
            } else {
                System.out.println("NO ESTÁ GUARDANDO NADA PORQUE EL VALOR ALMACENADO YA ES IGUAL O MAYOR AL ACTUAL");
            }
        } catch (NumberFormatException nfe) {
            log.error("Error in setLastTweetID():" + nfe);
        } catch (ParseException pe) {
            log.error("Error in parseDate():" + pe);
        }
    }

    @Override
    public void listen(Stream stream) {
        System.out.println("Entra al metodo listen....");
        ArrayList<ExternalPost> aListExternalPost = new ArrayList();
        String searchPhrases = getPhrases(stream.getPhrase());

        int limit = 20;
        int maxResults = 10;
        int totalResources = 0;
        boolean canGetMoreVideos = true;
        int iteration = 1;
        int count = 0;
        getLastVideoID(stream); //gets the value stored in NextDatetoSearch
        //if(1==1)return;
        for (int starIndex = 1; starIndex <= limit; starIndex++) {
            String index = String.valueOf(starIndex);
            // idClave = idClave.replace("|", "/");
            Map<String, String> params = new HashMap<String, String>();
            params.put("q", searchPhrases);
            params.put("v", "2");
            params.put("start-index", index);
            params.put("max-results", String.valueOf(maxResults));
            params.put("alt", "jsonc");
            params.put("orderby", "published");
            System.out.println("QUe palabras va a buscar: " + searchPhrases);

            try {
                String r2 = postRequest2(params, "https://gdata.youtube.com/feeds/api/videos", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
                //Convertir la String res2 a un objeto json
                JSONObject resp = new JSONObject(r2);
                JSONObject data = resp.getJSONObject("data");
                String updated = data.getString("updated");
                JSONArray items = data.getJSONArray("items");
                count = items.length();

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                //formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
                Date currentVideoID = new Date(0L);

                System.out.println("Antes de entrar al for");
                for (int i = 0; i < count; i++) {

                    ExternalPost external = new ExternalPost();
                    JSONObject id = items.getJSONObject(i);
                    String idItem = id.getString("id");
                    String uploader = id.getString("uploader");
                    String updatedItem = id.getString("updated");
                    String title = id.getString("title");

                    String uploadedStr = id.getString("uploaded");
                    String description = id.getString("description");
                    if (description == null || description.equals("")) {
                        description = title;
                    }
                    String categoryItem = id.getString("category");
                   
                    JSONObject content = id.getJSONObject("content");
                    String url = content.getString("5");
                    System.out.println("uploaded:" + uploadedStr + " -- " + lastVideoID);
                    //Temporal
                    Date uploaded = formatter.parse(id.getString("uploaded"));
                    if (uploaded.before(lastVideoID) || uploaded.equals(lastVideoID)) {
                        System.out.println("Entra al if.... Terminar la busqueda");
                        canGetMoreVideos = false;
                        break;
                    } else {
                        System.out.println("entra al else... Guardando..");
                        external.setPostId(idItem);
                        external.setCreatorId(uploader);
                        external.setCreatorName(uploader);
                        external.setCreationTime(uploadedStr);
                        external.setUpdateTime(updatedItem);
                        external.setMessage(description);
                        external.setCategory(categoryItem);
                        external.setSocialNetwork(this);
                        external.setPostType(SWBSocialUtil.VIDEO);
                        aListExternalPost.add(external);
                        currentVideoID = uploaded;
                    }

                    if (iteration == 1) {
                        iteration = 0;
                        System.out.println("iteration es igual a 1...");
                        setLastVideoID(uploadedStr, stream);//uploadedStr
                    }
                }
                if (canGetMoreVideos == false) {
                    System.out.println("Terminando... " + "<=" + lastVideoID);
                    break;
                }
            } catch (Exception e) {
                System.out.println("entra al error: " + e);
            }
            starIndex = starIndex + (count - 1);
        }

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
        }
        if (aListExternalPost.size() > 0) {
            new Classifier(aListExternalPost, stream, this);
        }
        System.out.println("Total resources: " + totalResources);
    }

    /*
     public void listen(Stream stream) {
     System.out.println("Entra al metodo listen....");
     ArrayList<ExternalPost> aListExternalPost = new ArrayList();
     String searchPhrases = getPhrases(stream.getPhrase());

     int limit = 100;
     int starIndex = 1;
     int maxResults = 50;
     int totalResources = 0;

     for (int j = 0; j <= limit; j++) {
     String index = String.valueOf(starIndex);
     // idClave = idClave.replace("|", "/");
     Map<String, String> params = new HashMap<String, String>();
     params.put("q", searchPhrases);
     params.put("v", "2");
     params.put("start-index", index);
     params.put("max-results", String.valueOf(maxResults));
     params.put("alt", "jsonc");
     params.put("orderby", "published");
     try {
     String r2 = postRequest2(params, "https://gdata.youtube.com/feeds/api/videos", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
     //Convertir la String res2 a un objeto json
     JSONObject resp = new JSONObject(r2);
     JSONObject data = resp.getJSONObject("data");
     String updated = data.getString("updated");
     JSONArray items = data.getJSONArray("items");
     int count = items.length();

     for (int i = 0; i < count; i++) {

     ExternalPost external = new ExternalPost();
     JSONObject id = items.getJSONObject(i);
     String idItem = id.getString("id");
     String uploader = id.getString("uploader");
     String updatedItem = id.getString("updated");
     String title = id.getString("title");
     String uploaded = id.getString("uploaded");
     String description = id.getString("description");
     String categoryItem = id.getString("category");
     JSONObject content = id.getJSONObject("content");
     String url = content.getString("5");
                    
                   
     external.setPostId(idItem);
     external.setCreatorId(uploader);
     external.setCreatorName(uploader);
     external.setCreationTime(uploaded);
     external.setUpdateTime(updatedItem);


     if (description == null || description.equals("")) {
     description = title;
     }
     external.setMessage(description);
     external.setPostType(SWBSocialUtil.VIDEO);

     System.out.println("descripcion: " + description);
     aListExternalPost.add(external);
                    
     totalResources++;
     }
     } catch (Exception e) {
     System.out.println("entra al error: " + e);
     }
     starIndex = starIndex + 50;
     j = j + 50;
     }
     System.out.println("Total resources: " + totalResources);
     try {
     Thread.sleep(3000);
     } catch (Exception e) {
     }
     if (aListExternalPost.size() > 0) {
     new Classifier(aListExternalPost, stream, this);
     }
     System.out.println("Total resources: " + totalResources);
     }
     */
    private String getPhrases(String stream) {
        String parsedPhrases = null; // parsed phrases 
        if (stream != null && !stream.isEmpty()) {
            String[] phrasesStream = stream.split("\\|"); //Delimiter            
            parsedPhrases = "";
            String tmp;
            int noOfPhrases = phrasesStream.length;
            for (int i = 0; i < noOfPhrases; i++) {
                tmp = phrasesStream[i].trim().replaceAll("\\s+", " "); //replace multiple spaces beetwen words for one only one space
                parsedPhrases += ((tmp.contains(" ")) ? ("\"" + tmp + "\"") : tmp); // if spaces found, it means more than one word in a phrase
                if ((i + 1) < noOfPhrases) {
                    parsedPhrases += " OR ";
                }
            }
        }
        return parsedPhrases;
    }

    @Override
    public boolean isPublished(PostOutNet postOutNet) {
        //System.out.println("Entra a YouTube, Instancia:"+this+", PostOut:"+postOutNet.getSocialPost());
        //postOutNet.setStatus(1);      //Con esta línea le indicamos al PostOutNet que ya se ha publicado su PostOut en su red social asociada
        return false;
    }

    
}
