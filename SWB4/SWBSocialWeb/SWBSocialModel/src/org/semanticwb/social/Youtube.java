package org.semanticwb.social;

/*import com.google.api.client.googleapis.json.GoogleJsonResponseException;
 import com.google.api.client.http.HttpRequest;
 import com.google.api.client.http.HttpRequestInitializer;
 import com.google.api.client.http.HttpTransport;
 import com.google.api.client.http.javanet.NetHttpTransport;
 import com.google.api.client.json.JsonFactory;
 import com.google.api.client.json.jackson2.JacksonFactory;
 import com.google.api.services.youtube.YouTube;
 import com.google.api.services.youtube.model.ResourceId;
 import com.google.api.services.youtube.model.SearchListResponse;
 import com.google.api.services.youtube.model.SearchResult;
 import com.google.api.services.youtube.model.SearchResultSnippet;
 */
import com.google.gdata.client.youtube.YouTubeService;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.io.SWBFile;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.listener.Classifier;
import org.semanticwb.social.util.SWBSocialUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Youtube extends org.semanticwb.social.base.YoutubeBase {

    private static Logger log = SWBUtils.getLogger(Youtube.class);
    //private Date lastVideoID;
    static String UPLOAD_URL = "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";
    static String BASE_VIDEO_URL = "http://www.youtube.com/v/";

    public Youtube(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }
    static {
        Youtube.social_Youtube.registerObserver(new SocialNetSemanticObserver());
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
        if (video.getVideo() == null || video.getTitle() == null) {//Required fields
            return;
        }        

        //Valida que este activo el token, de lo contrario lo refresca
        if(!this.validateToken()){
            log.error("Unable to update the access token inside postVideo Youtube!");
            return;
        }

        String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String boundary = "";
        for (int i = 0; i < 8; i++) {
            int numero = (int) (Math.random() * base.length());
            String caracter = base.substring(numero, numero + 1);
            boundary = boundary + caracter;
        }
        String url1 = UPLOAD_URL;
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(url1);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Host", "uploads.gdata.youtube.com");
            conn.setRequestProperty("Authorization", "Bearer " + this.getAccessToken());
            conn.setRequestProperty("GData-Version", "2");
            //conn.setRequestProperty("X-GData-Client", clientID);
            conn.setRequestProperty("X-GData-Key", "key=" + this.getDeveloperKey());
            conn.setRequestProperty("Slug", video.getTitle());
            conn.setRequestProperty("Content-Type", "multipart/related; boundary=\"" + boundary + "\"");
            //conn.setRequestProperty("Content-Length", getLength());
            conn.setRequestProperty("Connection", "close");
            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            writer.write(("\r\n--" + boundary + "\r\n").getBytes());
            writer.write("Content-Type: application/atom+xml; charset=UTF-8\r\n\r\n".getBytes());
            String category = video.getCategory() == null || video.getCategory().isEmpty() ? "People" : video.getCategory();
            System.out.println("THE CATEGORY->" + category + "<-");
            String privacy = privacyValue(video);
            String xml = "<?xml version=\"1.0\"?>\r\n"
                    + " <entry xmlns=\"http://www.w3.org/2005/Atom\"" + "\r\n"
                    + "xmlns:media=\"http://search.yahoo.com/mrss/\"\r\n"
                    + "xmlns:yt=\"http://gdata.youtube.com/schemas/2007\"> \r\n"
                    + " <media:group> \r\n"
                    + " <media:title type=\"plain\">" + video.getTitle() + "</media:title> \r\n"
                    + " <media:description type=\"plain\"> \r\n" + (video.getMsg_Text() == null ? "" : video.getMsg_Text()) + "\r\n"
                    + " </media:description> \r\n"
                    + " <media:category\r\n"
                    + "scheme=\"http://gdata.youtube.com/schemas/2007/categories.cat\"> " + category + " \r\n"
                    + " </media:category> \r\n"
                    + " <media:keywords>" + (video.getTags() == null ? "" : video.getTags()) + "</media:keywords> \r\n"
                    + (privacy.equals("PRIVATE") ? " <yt:private/> \r\n" :"")//Ad this tag to make a video PRIVATE
                    + " </media:group> \r\n"
                    + (privacy.equals("NOT_LISTED") ? " <yt:accessControl action='list' permission='denied'/> \r\n" : "")
                    + " </entry> \r\n";
            //System.out.println("XML:" + xml);
            writer.write(xml.getBytes("UTF-8"));
            writer.write(("--" + boundary + "\r\n").getBytes());
            String[] arr = video.getVideo().split("\\.");
            String ext = "Content-Type: video/" + arr[1] + "\r\n";
            writer.write(ext.getBytes());
            writer.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());

            String videoPath = SWBPortal.getWorkPath() + video.getWorkPath() + "/" + video.getVideo();
            SWBFile fileVideo = new SWBFile(videoPath);

            FileInputStream reader = new FileInputStream(fileVideo);
            byte[] array;
            int bufferSize = Math.min(reader.available(), 2048);
            array = new byte[bufferSize];
            int read = 0;
            read = reader.read(array, 0, bufferSize);
            while (read > 0) {
                writer.write(array, 0, bufferSize);
                bufferSize = Math.min(reader.available(), 2048);
                array = new byte[bufferSize];
                read = reader.read(array, 0, bufferSize);
            }
            writer.write(("--" + boundary + "--\r\n").getBytes());
            writer.write(("--" + boundary + "--\r\n").getBytes());
            writer.flush();
            writer.close();
            reader.close();
            BufferedReader readerl = new BufferedReader(new InputStreamReader(conn.getInputStream()));            
            StringBuilder videoInfo = new StringBuilder();
            String line;
            while((line = readerl.readLine()) != null) {
               videoInfo.append(line);
            }
            line = videoInfo.toString();
            String videoId = line.substring(line.indexOf("<yt:videoid>"), line.lastIndexOf("</yt:videoid>"));
            videoId = videoId.replace("<yt:videoid>", "");
            System.out.println("videoId..." + videoId);
            //Si el videoId es diferente de null manda a preguntar por el status del video
            //de lo contrario manda el error al log
            if (videoId != null) {
                SWBSocialUtil.PostOutUtil.savePostOutNetID(video, this, videoId, null);
            }
        } catch (Exception ex) {
            try{
            log.error("PROBLEM UPLOADING VIDEO:" + conn.getResponseMessage());
            System.out.println("THE ERROR" + getResponse(conn.getErrorStream()));
            }catch(Exception e){
                System.out.println("INGNORED:" + e.getMessage());
            }
            log.error("ERROR-->",ex);
        }

        /*   try
         {
         System.out.println("Va a Grabar en savePostOutNetID - George/video:"+video+", this:"+this);
         SWBSocialUtil.PostOutUtil.savePostOutNetID(video, this, "12345678");
         }catch(Exception e)
         {
            
         }*/

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

    public String getRequest(Map<String, String> params, String url,
            String userAgent, String method) throws IOException {

        CharSequence paramString = (null == params) ? "" : delimit(params.entrySet(), "&", "=", true);
        URL serverUrl = null;
        if (params != null) {
            serverUrl = new URL(url + "?" + paramString);
        } else {
            serverUrl = new URL(url);
        }

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

            in = conex.getInputStream();
            response = getResponse(in);

        } catch (java.io.IOException ioe) {
            if (conex.getResponseCode() >= 400) {
                //System.out.println("ERROR:" + getResponse(conex.getErrorStream()));                
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
        //String uriTemp = "http://localhost:8080/work/models/SWBAdmin/jsp/oauth/callback.jsp";
        String uriTemp = "http://" + request.getServerName() + ":" + request.getServerPort() + SWBPortal.getWebWorkPath() + "/models/SWBAdmin/jsp/oauth/callback.jsp";
        //Se crea una variable de sesion para recuperar en el jsp la url dinamica
        HttpSession session = request.getSession(true);
        session.setAttribute("redirectYouTube", uri);


        if (code == null) {
            out.println("<script type=\"text/javascript\">");
            out.println(" function ioauth() {");
            //out.println("  mywin = window.open('https://accounts.google.com/o/oauth2/auth?client_id=" + clientId + "&redirect_uri=" + uriTemp + "&response_type=code&scope=https://gdata.youtube.com&access_type=offline','_blank','width=840,height=680',true);");
            out.println("  mywin = window.open('https://accounts.google.com/o/oauth2/auth?client_id=" + clientId + "&redirect_uri=" + uriTemp + "&response_type=code&scope=https://gdata.youtube.com+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile&access_type=offline&state=/profile&approval_prompt=force','_blank','width=840,height=680',true);");
            out.println("  if(mywin == null){");
            out.println("    alert('¿Tienes bloqueadas las ventajas emergentes?');");
            out.println("    return;");
            out.println("  }");
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
                String refresh_token = "";
                if (userData.has("refresh_token") && !userData.isNull("refresh_token")) {
                    refresh_token = userData.getString("refresh_token");
                }

                setAccessToken(tokenAccess);
                setAccessTokenSecret(refresh_token);
                if (!refresh_token.isEmpty()) {
                    setRefreshToken(refresh_token);
                } else {//Si ya no viene el refresh token entonces hay que validar si esa cuenta ya esta dada de alta
                    //en social. Se puede ver a quien pertenece un token usando el endpoint 'tokeninfo'
                }
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

    private Date getLastVideoID(Stream stream) {
        Date lastVideoID = new Date(0L);
        System.out.println("entrando al metodo getLastVideoID....");
        SocialNetStreamSearch socialStreamSerch = SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
        System.out.append("NDTS:" + socialStreamSerch.getNextDatetoSearch());
        //socialStreamSerch.setNextDatetoSearch("2013-06-17T15:42:09.000Z");
        //if(1==1)return;

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
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
        return lastVideoID;
    }

    private void setLastVideoID(String dateVideo, Stream stream) {
        System.out.println("entrando al metodo setLastVideoID....");
        //if(1==1)return;
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            
            // formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
            Date storedValue = new Date(0L);
            SocialNetStreamSearch socialStreamSerch = SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
            if (socialStreamSerch != null && socialStreamSerch.getNextDatetoSearch() != null) {
                storedValue = formatter.parse(socialStreamSerch.getNextDatetoSearch());
            }
            //System.out.println("Antes de validar las fechas: ");
            System.out.println("stored Value : " + storedValue + "  dateVideo:  " + formatter.parse(dateVideo));
            if (formatter.parse(dateVideo).after(storedValue)) {
                //if (storedValue.before(formatter.parse(dateVideo))) { //Only stores tweetID if it's greater than the current stored value
                socialStreamSerch.setNextDatetoSearch(dateVideo);
                //System.out.println("GUARDANDO FECHA!!:" + dateVideo);
            } else {
                //System.out.println("NO ESTÁ GUARDANDO NADA PORQUE EL VALOR ALMACENADO YA ES IGUAL O MAYOR AL ACTUAL");
            }
        } catch (NumberFormatException nfe) {
            log.error("Error in setLastVideoID():" + nfe);
        } catch (ParseException pe) {
            log.error("Error in parseDate():" + pe);
        }
    }

    /*@Override
     public void listen(Stream stream) {
     System.out.println("Listening from youtube... API V3");
     try {
     // instance of the HTTP transport.
     HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
     // instance of the JSON factory. 
     JsonFactory JSON_FACTORY = new JacksonFactory();
     // instance of the max number of videos we want returned (50 = upper limit per page). 
     long NUMBER_OF_VIDEOS_RETURNED = 50;
     //instance of Youtube object to make all API requests. 
     YouTube youtube;
            
     ArrayList<ExternalPost> aListExternalPost = new ArrayList();
     // Words from stream.
     String searchPhrases = getPhrases(stream.getPhrase());
     System.out.println("searching for phrases:" + searchPhrases);
     DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
     Date lastVideoID = getLastVideoID(stream); //gets the value stored in NextDatetoSearch
     System.out.println("storedVideoID:" + lastVideoID);

     //The YouTube object is used to make all API requests. The last argument is required, but
     //because we don't need anything initialized when the HttpRequest is initialized, we override
     //the interface and provide a no-op function.
             
     youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
     public void initialize(HttpRequest request) throws IOException {}
     }).setApplicationName("SWB Social").build();

     YouTube.Search.List search = youtube.search().list("id,snippet");
            
     //It is important to set your developer key from the Google Developer Console for
     //non-authenticated requests (found under the API Access tab at this link:
     //code.google.com/apis/). This is good practice and increased your quota.
             
     String apiKey = "AIzaSyBQ6hGagr0wcKrWqsXEfFdDad2loLclqT8";//this.getDeveloperKey();
     search.setKey(apiKey);
     search.setQ(searchPhrases);
            
     //We are only searching for videos (not playlists or channels). If we were searching for
     //more, we would add them as a string like this: "video,playlist,channel".
             
     search.setType("video");
     search.setOrder("date");
            
     //search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
     search.setFields("items(id/kind,id/videoId,snippet),nextPageToken");
     search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);            

     boolean canGetMoreVideos = false;
     int totalVideos = 0;
     int iterations = 1;
     do{//Iterate to get blocks of videos
     SearchListResponse searchResponse = search.execute();
     List<SearchResult> searchResultList = searchResponse.getItems();

     if (searchResultList != null){
     Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();
     if(!iteratorSearchResults.hasNext()) {
     System.out.println(" There aren't any results for your query.");
     canGetMoreVideos = false;
     }else{
     if(searchResultList.size() < NUMBER_OF_VIDEOS_RETURNED){//It got less videos than requested, therefore there are not more
     canGetMoreVideos = false;
     }else{
     if(searchResponse.getNextPageToken() != null){
     //System.out.println("HAY TOKEN PARA OTRA PAGINA:" + searchResponse.getNextPageToken());
     search.setPageToken(searchResponse.getNextPageToken());
     canGetMoreVideos = true;
     }else{
     canGetMoreVideos = false;
     }
     }
                      
     System.out.println("Iteracion:" + iterations + " Size of Array:" + searchResultList.size());
     while (iteratorSearchResults.hasNext()){
     SearchResult singleVideo = iteratorSearchResults.next();
     ResourceId rId = singleVideo.getId();

     // Double checks the kind is video.
     if (rId.getKind().equals("youtube#video")){
                               
     SearchResultSnippet result = singleVideo.getSnippet();
     ExternalPost external = new ExternalPost();
     Date published = formatter.parse(result.getPublishedAt().toString());
                               
     if(totalVideos == 0){//Set the date of the most recent video
     setLastVideoID(result.getPublishedAt().toString(), stream);
     }
                               
     if (published.before(lastVideoID) || published.equals(lastVideoID)) {
     System.out.println("Terminar la busqueda, limite alcanzado");
     canGetMoreVideos = false;
     break;
     }
                               
     String desc = result.getDescription();
     String title = result.getTitle();
     //Date published = formatter.parse(result.getPublishedAt().toString());
     if(desc == null || desc.trim().equals("")){
     desc = result.getTitle();
     }else{
     desc = title + " / " + desc;
     }
     external.setPostId(rId.getVideoId());
     //System.out.println("$$" + rId.getVideoId() + "$$" + result.getChannelId() + "$$" + result.getChannelTitle() + "$$" + result.getPublishedAt() );
     if(result.getChannelTitle() == null || result.getChannelTitle().isEmpty()){
     external.setCreatorId(result.getChannelId());
     external.setCreatorName(result.getChannelId());
     }else{
     external.setCreatorId(result.getChannelId());
     external.setCreatorName(result.getChannelTitle());
     }
                               
     external.setMessage(desc);
     if(result.getPublishedAt() != null){
     external.setCreationTime(result.getPublishedAt().toString());
     }
     ////////external.setCategory("");
     external.setSocialNetwork(this);
     external.setVideo(BASE_VIDEO_URL + rId.getVideoId());
     external.setPostType(SWBSocialUtil.VIDEO);
     aListExternalPost.add(external);
     totalVideos++;
     }
     }
     }
     }
     iterations++;
     }while(canGetMoreVideos);
     System.out.println("Videos totales:" +  totalVideos);
            
     if (aListExternalPost.size() > 0) {
     //new Classifier(aListExternalPost, stream, this, false);
     }            
     }catch (GoogleJsonResponseException e) {
     System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
     + e.getDetails().getMessage());
     e.printStackTrace();
     } catch (IOException e) {
     System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
     } catch (Throwable t) {
     t.printStackTrace();
     }
     }*/
    @Override
    public void listen(Stream stream) {
        //Busca en las categorias: Comedy, Film, Music, People
        //Las palabras "america+pumas"
        //https://gdata.youtube.com/feeds/api/videos?v=2&category=Comedy%7CFilm%7CMusic%7CPeople&max-results=50&alt=jsonc&q=%22america+pumas%22&orderby=published&start-index=1
        //Busca en las mismas categorias que la anterior pero con 3 diferentes palabras
        //https://gdata.youtube.com/feeds/api/videos?v=2&category=Comedy%7CFilm%7CMusic%7CPeople&max-results=50&alt=jsonc&q=horse|cat|dog&orderby=published&start-index=1


        System.out.println("Entra al metodo listen.... Youtube");
        ArrayList<ExternalPost> aListExternalPost = new ArrayList();
        String searchPhrases = getPhrases(stream.getPhrase());
        String category = "";
        
        if(searchPhrases == null || searchPhrases.isEmpty()){
            return;
        }
        
        Iterator<YouTubeCategory> it = listYoutubeCategories();
        if (it.hasNext()) {//The first category
            category = it.next().getId();
        }

        while (it.hasNext()) {//More categories
            category = category + "|" + it.next().getId();
        }

        int blockOfVideos = 0; //this is the default Value, 
        if(stream.getBlockofMsgToClassify() > 0){
            blockOfVideos = stream.getBlockofMsgToClassify();
        }
        System.out.println("Message Block Youtube:" + blockOfVideos);
        
        int limit = 500;
        int maxResults = 50;
        int totalResources = 0;
        boolean canGetMoreVideos = true;
        int iteration = 1;
        int count = 0;
        Date lastVideoID = getLastVideoID(stream); //gets the value stored in NextDatetoSearch
        //if(1==1)return;
        for (int startIndex = 1; startIndex <= limit; startIndex++) {
            String index = String.valueOf(startIndex);
            // idClave = idClave.replace("|", "/");
            Map<String, String> params = new HashMap<String, String>();
            params.put("q", searchPhrases);
            params.put("v", "2");
            params.put("start-index", index);
            params.put("max-results", String.valueOf(maxResults));
            params.put("alt", "jsonc");
            params.put("orderby", "published");
            if (!category.isEmpty()) {
                params.put("category", category);
            }

            try {
                String youtubeResponse = getRequest(params, "https://gdata.youtube.com/feeds/api/videos", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
                //Convertir la String youtubeResponse a un objeto json
                JSONObject resp = new JSONObject(youtubeResponse);
                JSONObject data = resp.getJSONObject("data");
                if (data.has("items")) {
                    JSONArray items = data.getJSONArray("items");
                    count = items.length();

                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    //formatter.setTimeZone(TimeZone.getTimeZone("GMT-6"));
                    //Date currentVideoID = new Date(0L);

                    //System.out.println("Antes de entrar al for");
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
                        } else {
                            description = title + " / " + description;
                        }
                        String categoryItem = id.getString("category");

                        Date uploaded = formatter.parse(id.getString("uploaded"));
                        if (uploaded.before(lastVideoID) || uploaded.equals(lastVideoID)) {
                            System.out.println("Terminar la busqueda, limite alcanzado");
                            canGetMoreVideos = false;
                            break;
                        } else {
                            external.setPostId(idItem);
                            external.setCreatorId(uploader);
                            external.setCreatorName(uploader);
                            external.setCreationTime(uploadedStr);
                            external.setUpdateTime(updatedItem);
                            external.setMessage(description);
                            external.setCategory(categoryItem);
                            external.setSocialNetwork(this);
                            external.setVideo(BASE_VIDEO_URL + idItem);
                            external.setPostType(SWBSocialUtil.VIDEO);
                            aListExternalPost.add(external);
                            //currentVideoID = uploaded;
                        }

                        if (iteration == 1) {
                            iteration = 0;
                            System.out.println("uploaded:" + uploadedStr + " -- " + lastVideoID);
                            System.out.println("Saving: " + uploadedStr);
                            setLastVideoID(uploadedStr, stream);//uploadedStr
                        }
                        totalResources++;
                    }
                    
                    if((blockOfVideos > 0) && (aListExternalPost.size() >= blockOfVideos)){//Classify the block of videos
                        System.out.println("CLASSIFYING:" + aListExternalPost.size());
                        new Classifier((ArrayList <ExternalPost>)aListExternalPost.clone(), stream, this, false);
                        aListExternalPost.clear();
                    }
                    if(!stream.isActive()){//If the stream has been disabled stop listening
                        canGetMoreVideos = false;
                    }
                    if (canGetMoreVideos == false) {
                        System.out.println("Terminando... " + "<=" + lastVideoID);
                        break;
                    }
                } else {//Not found JSONArray items[]                    
                    canGetMoreVideos = false;
                    break;
                }
            } catch (Exception e) {
                log.error("Error reading Youtube stream ", e);
                canGetMoreVideos = false;
                break;
            }
            startIndex = startIndex + (count - 1);
        }
        System.out.println("Total Videos in Array: " + aListExternalPost.size());

        if (aListExternalPost.size() > 0) {
            new Classifier(aListExternalPost, stream, this, false);
        }
        System.out.println("Total Videos: " + totalResources);
    }

    private String getPhrases(String stream) {
        String parsedPhrases = null; // parsed phrases 
        if (stream != null && !stream.isEmpty()) {
            String[] phrasesStream = stream.split(","); //Delimiter            
            parsedPhrases = "";
            String tmp;
            int noOfPhrases = phrasesStream.length;
            for (int i = 0; i < noOfPhrases; i++) {
                if(!phrasesStream[i].trim().isEmpty()){
                    tmp = phrasesStream[i].trim().replaceAll("\\s+", " "); //replace multiple spaces beetwen words for one only one space
                    //parsedPhrases += ((tmp.contains(" ")) ? ("\"" + tmp + "\"") : tmp); // if spaces found, it means more than one word in a phrase
                    parsedPhrases += "\"" + tmp + "\""; // if spaces found, it means more than one word in a phrase
                    if ((i + 1) < noOfPhrases) {
                        parsedPhrases += "|";
                    }
                }
            }
        }
        return parsedPhrases;
    }

    @Override
    public boolean isPublished(PostOutNet postOutNet) {
        System.out.println("Entra al metodo isPublished....");
        System.out.println("El id del video es...." + postOutNet.getPo_socialNetMsgID());

        boolean exit = false;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("https://gdata.youtube.com/feeds/api/users/default/uploads/" + postOutNet.getPo_socialNetMsgID());
            get.setHeader("Authorization", "Bearer " + this.getAccessToken());
            HttpResponse res = client.execute(get);
            BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
            //String dcxml = rd.readLine();
            StringBuilder videoInfo = new StringBuilder();
            String dcxml;
            while((dcxml = rd.readLine()) != null) {
               videoInfo.append(dcxml);
            }
            dcxml = videoInfo.toString();
            System.out.println("docxml dentro de isPublished:   " + dcxml);
            if (dcxml.contains("Video not found")) {
                //   postOutNet.setError(dcxml);
                exit = true;
            }
            Document doc = SWBUtils.XML.xmlToDom(dcxml);
            doc.getDocumentElement().normalize();
            NodeList nodosRaiz = doc.getDocumentElement().getChildNodes();
            boolean found = false;
            int setErr = 0;
            String reasonCode = "";
            String descriptionReason = "";
            for (int i = 0; i < nodosRaiz.getLength(); i++) {
                Node childNode = nodosRaiz.item(i);
                if (childNode.getNodeName().equals("app:control")) {
                    found = true;
                    System.out.println("Entra a app:control....");
                    NodeList children = childNode.getChildNodes();
                    for (int j = 0; j < children.getLength(); j++) {
                        Node children2 = children.item(j);
                        if (children2.getNodeName().equals("yt:state")) {
                            String name = children2.getAttributes().getNamedItem("name").getTextContent();
                            System.out.println("lo que trae yt:state name: " + name);
                            if (name.equals("processing")) {
                                System.out.println("Entra a la validacion de que el name es igual a processing");
                                //exit = false;
                            } else {
                                reasonCode = children2.getAttributes().getNamedItem("reasonCode").getTextContent();
                                System.out.println("lo que trae yt:state reasonCode: " + reasonCode);
                                descriptionReason = children2.getTextContent();
                                System.out.println("lo que trae yt:state: " + descriptionReason);
                                setErr = 1;
                            }
                        }
                    }
                    break;
                }
            }
            if (found == true) {
                System.out.println("La variable found es true, si encontro un tag llamado app.control");
                if (setErr == 1) {
                    postOutNet.setStatus(0);
                    postOutNet.setError(reasonCode + " : " + descriptionReason);
                    exit = true;
                } else {
                    exit = false;
                }
            } else {
                System.out.println("No encontro un tag app:control....");
                postOutNet.setStatus(1);
                exit = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exit;
    }

    @Override
    public JSONObject getUserInfobyId(String userId) {
        //Realiza la peticion a Youtube para obtener id de Google+
        HashMap<String, String> params = new HashMap<String, String>(3);
        params.put("v", "2");
        params.put("alt", "json");

        JSONObject userInfo = new JSONObject();
        String responseIdGoogle = null;
        String googlePlusUserId = "";

        try {
            responseIdGoogle = getRequest(params, "https://gdata.youtube.com/feeds/api/users/" + userId, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");

            if (responseIdGoogle.equals("")) {
                return userInfo;
            }
            JSONObject parseUsrInfYoutube = null;
            parseUsrInfYoutube = new JSONObject(responseIdGoogle);

            JSONObject information = parseUsrInfYoutube.getJSONObject("entry");
            if (information.has("yt$googlePlusUserId") && !information.isNull("yt$googlePlusUserId")) {
                googlePlusUserId = information.getJSONObject("yt$googlePlusUserId").getString("$t");
               userInfo.put("third_party_id", information.getJSONObject("yt$googlePlusUserId").getString("$t"));
            }
            if(information.has("yt$statistics") && !information.isNull("yt$statistics")){       
                userInfo.put("followers", information.getJSONObject("yt$statistics").getString("subscriberCount"));
            }
            
        } catch (Exception e) {
            System.out.println("Error getting user information" + e.getMessage());
        }

        //Se realiza la peticion API Google,para obtener los datos de usuario en google+
        if (googlePlusUserId.equals("")) {
            log.error("El usuario " + userId + " no tiene asociado un id de google");
            return userInfo;
        }

        try {
            String youtubeResponse = getRequest(null, "https://www.googleapis.com/plus/v1/people/" + googlePlusUserId + "?key=AIzaSyBEbVYqvZudUYdt-UeHkgRl-rkvNHCw4Z8", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");
            JSONObject parseUsrInf = null;
            parseUsrInf = new JSONObject(youtubeResponse);

            if (parseUsrInf.has("gender") && !parseUsrInf.isNull("gender")) {
                userInfo.put("gender", parseUsrInf.getString("gender"));
            } else {
                userInfo.put("gender", "");
            }

            if (parseUsrInf.has("relationshipStatus") && !parseUsrInf.isNull("relationshipStatus")) {
                userInfo.put("relationship_status", parseUsrInf.getString("relationshipStatus"));
            } else {
                userInfo.put("relationship_status", "");
            }

            if (parseUsrInf.has("placesLived") && !parseUsrInf.isNull("placesLived")) {
                JSONArray location = parseUsrInf.getJSONArray("placesLived");

                for (int i = 0; i < location.length(); i++) {
                    JSONObject jo = location.getJSONObject(i);

                    if (jo.has("primary") && !jo.isNull("primary")) {
                        userInfo.put("placesLived", jo.getString("value"));
                        break;
                    } else {
                        userInfo.put("placesLived", "");
                    }
                }
            } else {
                userInfo.put("placesLived", "");
            }

            if (parseUsrInf.has("birthday") && !parseUsrInf.isNull("birthday")) {
                String date = parseUsrInf.getString("birthday");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date regresa = sdf.parse(date);
                sdf = new SimpleDateFormat("MM-dd-yyyy");
                date = sdf.format(regresa);
                userInfo.put("birthday", date.replace("-", "/"));
            } else {
                userInfo.put("birthday", "");
            }

        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(Youtube.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        }

        return userInfo;
    }

    public boolean validateToken() {
        boolean refreshedToken = false;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("https://www.googleapis.com/oauth2/v2/tokeninfo?access_token=" + this.getAccessToken());
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = client.execute(post, responseHandler);
            System.out.println("la respuesta es: " + responseBody);
            refreshedToken = true;//No exception thrown, the token is fine
            //TODO: validate status of the token :)
        } catch (HttpResponseException e) {
            System.out.println("Msg" + e.getMessage());
            System.out.println("Error code" + e.getStatusCode());
            if (e.getStatusCode() == 400) {
                System.out.println("entra al error 400....");
                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("refresh_token", this.getRefreshToken());
                    params.put("client_id", this.getAppKey());
                    params.put("client_secret", this.getSecretKey());
                    params.put("grant_type", "refresh_token");
                    String res = postRequest(params, "https://accounts.google.com/o/oauth2/token", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "POST");
                    System.out.println("respuesta de peticion del token nuevo" + res);
                    JSONObject userData = new JSONObject(res);
                    String tokenAccess = userData.getString("access_token");
                    this.setAccessToken(tokenAccess);
                    refreshedToken = true;
                } catch (IOException io) {
                    System.out.println("Error en la peticion del nuevo accessToken" + io);
                } catch (JSONException ex) {
                    System.out.println("Error en la respuesta del nuevo accessToken" + ex);
                }
            }
            //e.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
        return refreshedToken;

    }

    @Override
    public void postMsg(Message message) {
        System.out.println("Posting comment to a video");
        if (message != null && message.getMsg_Text() != null && message.getMsg_Text().trim().length() > 1) {
            if(message.getPostInSource()!=null && message.getPostInSource().getSocialNetMsgId()!=null){
                System.out.println("Youtube Making comment:...:" + message.getPostInSource().getPostInSocialNetworkUser().getSnu_name());
                String videoId = message.getPostInSource().getSocialNetMsgId();
                String comment = message.getMsg_Text();
                
                if(!this.validateToken()){
                    log.error("Unable to update the access token inside post Comment!");
                    return;
                }
                
                String urlComment = "https://gdata.youtube.com/feeds/api/videos/" + videoId + "/comments";
                URL url;
                HttpURLConnection conn = null;
                try {
                    url = new URL(urlComment);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setUseCaches(false);
                    conn.setRequestProperty("Host", "gdata.youtube.com");
                    conn.setRequestProperty("Content-Type", "application/atom+xml");
                    conn.setRequestProperty("Authorization", "Bearer " + this.getAccessToken());
                    conn.setRequestProperty("GData-Version", "2");
                    conn.setRequestProperty("X-GData-Key", "key=" + this.getDeveloperKey());

                    DataOutputStream writer = new DataOutputStream(conn.getOutputStream());                        
                    String xml = "<?xml version=\"1.0\"?>"
                        + "<entry xmlns=\"http://www.w3.org/2005/Atom\""
                        + " xmlns:yt=\"http://gdata.youtube.com/schemas/2007\">"
                        + "<content>" + comment + "</content>"
                        + "</entry>";
                    writer.write(xml.getBytes("UTF-8"));
                    writer.flush();
                    writer.close();                        
                    BufferedReader readerl = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    //String docxml = readerl.readLine();
                    StringBuilder videoInfo = new StringBuilder();
                    String docxml;
                    while((docxml = readerl.readLine()) != null) {
                       videoInfo.append(docxml);
                    }
                    docxml = videoInfo.toString();

                    //SWBSocialUtil.PostOutUtil.savePostOutNetID(message, this, String.valueOf(longStat), null);
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder;
                    builder = factory.newDocumentBuilder();
                    Document xmlDoc = builder.parse(new InputSource(new StringReader(docxml)));
                    xmlDoc.getDocumentElement().normalize();
                    NodeList rootNode = xmlDoc.getDocumentElement().getChildNodes();

                    for( int tmp = 0; tmp < rootNode.getLength(); tmp++){
                        Node nNode= rootNode.item(tmp);
                        if(nNode.getNodeName().equals("id")){
                            //System.out.println("id-->" + nNode.getTextContent());
                            if(nNode.getTextContent().contains("comment:")){
                                String commentId = nNode.getTextContent().substring(nNode.getTextContent().indexOf("comment:") + 8);
                                SWBSocialUtil.PostOutUtil.savePostOutNetID(message, this, commentId, null);
                                System.out.println("ID-->" + commentId + "<--");
                                break;
                            }
                        }
                    }
                }catch(Exception ex){                    
                    SWBSocialUtil.PostOutUtil.savePostOutNetID(message, this, null, ex.getMessage());
                    log.error("Problem posting comment ", ex);
                    try{
                        System.out.println("ERROR:" + getResponse(conn.getErrorStream()));                
                        if(conn.getResponseMessage() != null){
                            log.error("Error code:" + conn.getResponseCode() + " " + conn.getErrorStream());
                        }
                    }catch(Exception e){
                    
                    }
                }
                
            }else{
                System.out.println("Youtube only allows comment to a video not POSTS!");
                log.error("Youtube only allows comment to a video not POSTS!");
            }
        }        
    }
    
    private String privacyValue(PostOut postout){
        Iterator<PostOutPrivacyRelation> privacyRelation = PostOutPrivacyRelation.ClassMgr.listPostOutPrivacyRelationByPopr_postOut(postout);        
        String privacy = "";
        try{
            while(privacyRelation.hasNext()){
                PostOutPrivacyRelation privacyR = privacyRelation.next();
                if(privacyR.getPopr_socialNetwork().getURI().equals(this.getURI())){
                    if(privacyR.getPopr_privacy() != null){
                        privacy = privacyR.getPopr_privacy().getId();
                    }
                }
            }
        }catch(Exception e){
            log.error("Problem setting privacy:", e );
        }
        System.out.println("PRIVACY:" + privacy);
        return privacy;
    }

    @Override
    public HashMap monitorPostOutResponses(PostOut postOut) {
        //throw new UnsupportedOperationException("Not supported yet.");
        HashMap hMapPostOutNets = new HashMap();
        Iterator<PostOutNet> itPostOutNets=PostOutNet.ClassMgr.listPostOutNetBySocialPost(postOut);
        
        if(!this.validateToken()){
            log.error("Unable to update the access token inside post Comment!");
            return hMapPostOutNets;
        }
        
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
    
    public long comments(String videoId){
        long totalComments = 0L;
        try{
        String video = getcommentsFromVideoId(videoId, this.getAccessToken());
        JSONObject videoResp = new JSONObject(video);

        if(!videoResp.isNull("feed")){
            if(!videoResp.getJSONObject("feed").isNull("openSearch$totalResults")){
                if(!videoResp.getJSONObject("feed").getJSONObject("openSearch$totalResults").isNull("$t")){
                    totalComments = videoResp.getJSONObject("feed").getJSONObject("openSearch$totalResults").getLong("$t");
                }
            }            
        }
        System.out.println("YOUTUBE:" + videoId + " AND THE NUMBER:" + totalComments);
        }catch(Exception e){
            log.error("Youtube: Not data found for ->" + videoId );
        }
        return totalComments;
    }
    
    public String getcommentsFromVideoId(String id, String accessToken){
        HashMap<String, String> params = new HashMap<String, String>(3);    
        params.put("v", "2");
        params.put("alt","json");//alt
        params.put("start-index","1");//alt
        params.put("max-results","1");//alt
    
        String response = null;
        try{
            //https://gdata.youtube.com/feeds/api/videos/Wwv6iOVFZvw/comments?v=2&alt=json&start-index=1&max-results=1&prettyprint=true
            response = getRequestVideo(params, "https://gdata.youtube.com/feeds/api/videos/" + id +"/comments",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", accessToken);

        }catch(Exception e){
            System.out.println("Error getting video information"  + e.getMessage());
        }
        return response;
    }
    
    public String getRequestVideo(Map<String, String> params, String url,
            String userAgent, String accessToken) throws IOException {
        
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
            ///Validate if i am looking for the default user or another
            if(accessToken != null){
                conex.setRequestProperty("Authorization", "Bearer " + accessToken);
            }
            ///
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
                System.out.println("\n\n\nERROR:" +   response);
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
    
    //Klout
    @Override
    public double getUserKlout(String youtubeUserID) {
       String userThird_party_id=null;
       WebSite wsite=WebSite.ClassMgr.getWebSite(this.getSemanticObject().getModel().getName());
       SocialNetworkUser socilNetUser=getThird_party_id(youtubeUserID, wsite);
       if(socilNetUser!=null)
       {
           if(socilNetUser.getSnu_third_party_id()!=null)
           {
               userThird_party_id=socilNetUser.getSnu_third_party_id();
           }
       }
       if(userThird_party_id==null)
       {
           //Hacer conexión vía directa a Google+ para obtener Third_party_id del usuario en cuestion.
           userThird_party_id=getYouTubeThird_party_id(youtubeUserID);
       }
        
       if(userThird_party_id!=null)
       {
            String url_1="http://api.klout.com/v2/identity.json/gp/"+userThird_party_id;
            String kloutJsonResponse_1=getData(url_1);
            //System.out.println("kloutResult step-1:"+kloutJsonResponse_1);

            //Obtener id de json
            try
            {
                if(kloutJsonResponse_1!=null)
                {
                    JSONObject userData = new JSONObject(kloutJsonResponse_1);
                    String kloutUserId = userData != null && userData.get("id") != null ? (String) userData.get("id") : "";
                    //System.out.println("kloutId de Resultado en Json:"+kloutUserId);

                    //Segunda llamada a la red social Klout, para obtener Json de Score del usuario (kloutUserId) encontrado
                    if(kloutUserId!=null)
                    {
                        String url_2="http://api.klout.com/v2/user.json/"+kloutUserId+"/score";
                        String kloutJsonResponse_2=getData(url_2);
                        //System.out.println("kloutResult step-2-Json:"+kloutJsonResponse_2);

                        if(kloutJsonResponse_2!=null)
                        {
                             JSONObject userScoreData = new JSONObject(kloutJsonResponse_2);
                             Double kloutUserScore = userScoreData != null && userScoreData.get("score") != null ? (Double) userScoreData.get("score") : 0.00;
                             return Math.rint(kloutUserScore.doubleValue());
                        }
                    }
                }
            }catch(JSONException je)
            {
                
            }
        }
        return 0;
    }
    
    private static String getData(String url)
    {
        String answer = null;
        //String key=SWBContext.getAdminWebSite().getProperty("kloutKey");    //TODO:Ver con Jei x que no funciona esto...
        String key=SWBSocialUtil.Util.getModelPropertyValue(SWBContext.getAdminWebSite(), "kloutKey");
        //if(key==null) key="8fkzgz7ngf7bth3nk94gnxkd";   //Solo para fines de pruebas, quitar despues y dejar línea anterior.
        //System.out.println("key para KLOUT--Gg:"+key);
        if(key!=null)
        {
            url=url+"?key="+key;
            URLConnection conex = null;
            try {
                //System.out.println("Url a enviar a Klout:"+url);
                URL pagina = new URL(url);

                String host = pagina.getHost();
                //Se realiza la peticion a la página externa
                conex = pagina.openConnection();
                /*
                if (userAgent != null) {
                    conex.setRequestProperty("user-agent", userAgent);
                }*/
                if (host != null) {
                    conex.setRequestProperty("host", host);
                }
                conex.setDoOutput(true);

                conex.setConnectTimeout(20000); //15 segundos maximo, si no contesta la red Klout, cortamos la conexión
            } catch (Exception nexc) {
                System.out.println("nexc Error:"+nexc.getMessage());
                conex = null;
            }
            //System.out.println("Twitter Klout/conex:"+conex);
            //Analizar la respuesta a la peticion y obtener el access token
            if (conex != null) {
                try
                {
                    //System.out.println("Va a checar esto en Klit:"+conex.getInputStream());
                    answer = getResponse(conex.getInputStream());
                }catch(Exception e)
                {
                    //log.error(e);
                }
                //System.out.println("Twitter Klout/answer-1:"+answer);
            }
        }
        //System.out.println("Twitter Klout/answer-2:"+answer);
        return answer;
    }
    
    private String getYouTubeThird_party_id(String youtubeUserId)
    {
        String idPlus = "" ; 
        HashMap<String, String> params = new HashMap<String, String>(3);
        params.put("v", "2");
        params.put("alt", "json");

        String responseIdGoogle = null;

        try {
            responseIdGoogle = getRequest(params, "https://gdata.youtube.com/feeds/api/users/" + youtubeUserId, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95", "GET");

            if (responseIdGoogle.equals("")) {
                return idPlus;
            }
            
            JSONObject parseUsrInfYoutube = new JSONObject(responseIdGoogle);
            JSONObject information = parseUsrInfYoutube.getJSONObject("entry");
            if (information.has("yt$googlePlusUserId") && !information.isNull("yt$googlePlusUserId")) {
                idPlus = information.getJSONObject("yt$googlePlusUserId").getString("$t");            
            }                  
            
        } catch (Exception e) {
            System.out.println("Error getting user information" + e.getMessage());
        }        
        return idPlus;
    }
    
    
    private SocialNetworkUser getThird_party_id(String userId, SWBModel model)
    {
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(SocialNetworkUser.social_snu_id, userId); //No encuentra
        while(it.hasNext())
        {
            SemanticObject obj=it.next();
            SocialNetworkUser socialNetUser=(SocialNetworkUser)obj.createGenericInstance();
            //System.out.println("GEOOOOORGGGEEE:socialNetUser.getSnu_SocialNetwork().getId():"+socialNetUser.getSnu_SocialNetworkObj().getId());
            //System.out.println("GEOOOOORGGGEEE:socialNetwork.getSemanticObject().getSemanticClass().getSemanticObject().getId():"+socialNetwork.getSemanticObject().getSemanticClass().getSemanticObject().getId());
            if(socialNetUser.getSnu_SocialNetworkObj()!=null && socialNetUser.getSnu_SocialNetworkObj().getId().equals(this.getSemanticObject().getSemanticClass().getSemanticObject().getId()));
            {
                  return socialNetUser;
            }
        }
        return null;
    }
}
