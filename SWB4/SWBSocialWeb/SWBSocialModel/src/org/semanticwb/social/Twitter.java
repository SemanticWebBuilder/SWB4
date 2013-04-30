package org.semanticwb.social;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javaQuery.j2ee.tinyURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.listener.Classifier;
import org.semanticwb.social.listener.twitter.SWBSocialStatusListener;
import org.semanticwb.social.util.SWBSocialUtil;
import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.StatusListener;
import twitter4j.StatusUpdate;
//import twitter4j.Tweet;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


public class Twitter extends org.semanticwb.social.base.TwitterBase {

    private static Logger log = SWBUtils.getLogger(Twitter.class);
    
    TwitterStream trial=null;
    
    private Long lastTweetID; // get tweets since the tweet with ID 'lastTweetID' until now
                              // In the first request the lastTweetID value is 0. This value MUST be updated in further executions
    private int tweetsReceived;
    
    public List<Status> twitterResults;
    
    private RequestToken requestToken;
    
    public void setRequestToken(RequestToken requestToken) {
        this.requestToken = requestToken;
    }
    
    public RequestToken getRequestToken() {
        return requestToken;
    }

    public Twitter(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void postMsg(Message message, HttpServletRequest request, SWBActionResponse response) {
        if (message != null && message.getMsg_Text() != null && message.getMsg_Text().trim().length() > 1) {
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();
            try {
                twitter.setOAuthConsumer(this.getAppKey(), this.getSecretKey());
                AccessToken accessToken = new AccessToken(this.getAccessToken(), this.getAccessTokenSecret());
                twitter.setOAuthAccessToken(accessToken);
//                StatusUpdate sup = new StatusUpdate(new String(message.getMsg_Text().getBytes(), "utf-8"));
                StatusUpdate sup = new StatusUpdate(new String(shortUrl(message.getMsg_Text()).getBytes(), "ISO-8859-1"));
                
                Status stat = twitter.updateStatus(sup);
                Long longStat = stat.getId();
                // System.out.println("longStat: " + longStat + " texto: " + stat.getText());
                //getPostContainer().getPost().setSocialNetPostId("");
            } catch (UnsupportedEncodingException ex) {
                log.error("Exception" + ex);
            } catch (TwitterException ex) {
                log.error("Exception" + ex);
                if (401 == ex.getStatusCode()) {
                    //getResourceBase().setAttribute("oauth", null);
                }
            }
        }
    }

    public void postPhoto(Photo photo, HttpServletRequest request, SWBActionResponse response) {
        User user = response.getUser();

        if (photo != null && photo.getPhoto() != null && photo.getPhoto().trim().length() > 1) {
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(this.getAppKey(), this.getSecretKey());
            AccessToken accessToken = new AccessToken(this.getAccessToken(), this.getAccessTokenSecret());
            String description = photo.getDescription(user.getLanguage()) == null ? (photo.getDescription() == null ? "" : photo.getDescription()) : photo.getDescription(user.getLanguage());
            twitter.setOAuthAccessToken(accessToken);
            String photoSend = SWBPortal.getWorkPath() + photo.getWorkPath() + "/" + Photo.social_photo.getName()
                    + "_" + photo.getId() + "_" + photo.getPhoto();
            try {
                twitter.setOAuthAccessToken(accessToken);
                //StatusUpdate sup = new StatusUpdate(new String(photo.getComment().getBytes(), "utf-8"));
                StatusUpdate sup = new StatusUpdate(new String(shortUrl(description).getBytes(), "ISO-8859-1"));
                sup.setMedia(new File(photoSend));
                twitter.updateStatus(sup);
            } catch (UnsupportedEncodingException ex) {
                log.error("Exception" + ex);
            } catch (TwitterException ex) {
                log.error("Exception" + ex);
                if (401 == ex.getStatusCode()) {
                    //getResourceBase().setAttribute("oauth", null);
                }
            }
            //System.out.println("Mensaje de Photo de Twitter:" + description);
            //System.out.println("Photo de Twitter:" + photoSend);
        }
    }

    private String shortUrl(String urlLong) {
        String mensajeNuevo = "";
        if (urlLong != null && !urlLong.equals("")) {
            String delimiter = " ";
            String[] temp = urlLong.split(delimiter);
            for (int i = 0; i < temp.length; i++) {
                if ((temp[i].startsWith("http://") || temp[i].startsWith("https://")) && ((temp[i].length() > 9))) {
                    tinyURL tU = new tinyURL();
                    temp[i] = tU.getTinyURL(temp[i]);
                }
                mensajeNuevo += temp[i] + " ";
            }
        }
        return mensajeNuevo;
    }
    
    /**
     * Returns the configuration object with OAuth Credentials
     * Anonymous access is not allowed
     */
    private ConfigurationBuilder configureOAuth(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        //cb.setDebugEnabled(true)
        //  .setOAuthConsumerKey("V5Xp0RYFuf3N0WsHkqSOIQ")
        //  .setOAuthConsumerSecret("4DZ9UrE4X5VavUjXzBcGFTvEsHVsCGOgIuLVSZMA8")
        //  .setOAuthAccessToken("1137512760-v65LXmL07hgaOzZPGN6xlSiJGPNCx3BkipAuvnZ")
        //  .setOAuthAccessTokenSecret("F4H9ruXp8YReBG28OTQyeEkHkHudm7IzMIbP8Ep8bzw");
        
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(getAppKey())
          .setOAuthConsumerSecret(getSecretKey())
          .setOAuthAccessToken(getAccessToken())
          .setOAuthAccessTokenSecret(getAccessTokenSecret());
        
        /*
         * When a twitter object is given use:
         * Consumer Key: getAppKey()
         * Consumer Secret: getSecretKey()
         * Access token: getAccessToken()
         * Access token Secret: getAccessTokenSecret()
         */
        return cb;
    }
    /**
     * Gets the value in NextDatetoSearch and verifies is a Long number.
     */
    private void getLastTweetID(Stream stream){
        SocialNetStreamSearch socialStreamSerch=SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
        if(socialStreamSerch!=null && socialStreamSerch.getNextDatetoSearch()!= null){
            try{
                lastTweetID = Long.parseLong(socialStreamSerch.getNextDatetoSearch());
                //System.out.println("RECOVERING NEXTDATETOSEARCH: " + socialStreamSerch.getNextDatetoSearch());
            }catch(NumberFormatException nfe){
                lastTweetID = 0L;
                log.error("Error in getLastTweetID():"  + nfe);
                //System.out.println("Invalid value found in NextDatetoSearch(). Set to 0");
            }
        }else{
            lastTweetID=0L;
        }
    }
    
    /**
     * Sets the value to NextDatetoSearch. Verifies whether the passed value is greater than the stored or not.
     */
    private void setLastTweetID(Long tweetID, Stream stream){        
        try{
            Long storedValue=0L;
            SocialNetStreamSearch socialStreamSerch=SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
            if(socialStreamSerch!=null && socialStreamSerch.getNextDatetoSearch()!=null) storedValue = Long.parseLong(socialStreamSerch.getNextDatetoSearch());
            
            if(tweetID > storedValue){ //Only stores tweetID if it's greater than the current stored value
                //System.out.println("EL VALOR ALMACENADO ES:" +  tweetID.toString());
                socialStreamSerch.setNextDatetoSearch(tweetID.toString());
            }else{
                //System.out.println("NO ESTÁ GUARDANDO NADA PORQUE EL VALOR ALMACENADO YA ES IGUAL O MAYOR AL ACTUAL");
            }
        }catch(NumberFormatException nfe){
            log.error("Error in setLastTweetID():"  +nfe);
            //System.out.println("Problem Storing NextDatetoSearch");
        }
    }
    
    /**
     * Formats phrases according to Query requirements.
     * Replaces pipes by OR and encloses phrases with more than one word in double quotes.
     * @param stream - phrases to search delimited by pipe
     * @return Formated phrases.
     */
    private String getPhrases(String stream){
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
    public void listen(Stream stream) {
        
        WebSite wsite = WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        //System.out.println("Red SocialID:"+this.getId()+", Red Title:"+this.getTitle()+", sitio:"+wsite.getId());
        //System.out.println("Creador:" + this.getCreator());
        twitterResults = new ArrayList<Status>();
        tweetsReceived = 0;
        ArrayList <ExternalPost> aListExternalPost=new ArrayList();
        
        try{            
            getLastTweetID(stream); //gets the value stored in NextDatetoSearch
            twitter4j.Twitter twitter = new TwitterFactory(configureOAuth().build()).getInstance();            
            String searchPhrases = getPhrases(stream.getPhrase());
            twitter4j.Query query = new Query(searchPhrases);
            query.setCount(100); //max tweets by request
            
            boolean canGetMoreTweets = true;
            int iteration = 1;
            long currentTweetID = 0L;
            
            do{
                try{
                    //System.out.println("QUERY: " + query);
                    twitter4j.QueryResult result = twitter.search(query);
                    int noOfTweets = result.getTweets().size();
                    //System.out.println("\ntweets by request: " + noOfTweets);                    
                    //System.out.println("Iteracion: " + iteration);
                    
                    if(noOfTweets == 0){
                        //System.out.println("No more tweets available for the current query!!");
                        canGetMoreTweets = false;
                    }else{
                        for(Status status : result.getTweets()){
                            if(status.getId() <= lastTweetID){ //If value is ZERO then get all tweets available,
                                canGetMoreTweets = false;      //If it's greater than ZERO, get tweets posted since the tweet with id lastTweetID
                                //System.out.println("SINCEID LIMIT REACHED!!!");
                                break;
                            }
                                
                                if(!status.isRetweet())
                                {
                                    ExternalPost external = new ExternalPost();
                                    external.setPostId(String.valueOf(status.getId())); 
                                    external.setCreatorId(String.valueOf(status.getUser().getId()));
                                    external.setCreatorName("@"+status.getUser().getScreenName());
                                    external.setCreationTime(""+status.getCreatedAt());
                                    external.setDevice(status.getSource());
                                    if (status.getText()!=null) {
                                       external.setMessage(status.getText());
                                    }                            
                                    external.setFollowers(status.getUser().getFollowersCount());
                                    external.setFriendsNumber(status.getUser().getFriendsCount());
                                    if(status.getPlace()!=null)
                                    {
                                        external.setPlace(status.getPlace().getFullName());
                                    }
                                    
                                    aListExternalPost.add(external);

                                    twitterResults.add(status);
                                    //System.out.println("User: @" + status.getUser().getScreenName() + "\tID:" + status.getId() + "\tTime:" + status.getCreatedAt() + "\tText:" + status.getText());
                                    currentTweetID = status.getId();
                                    tweetsReceived++;
                                }
                        }

                        if(iteration == 1){
                            //System.out.println("MaxTweetID:" + result.getMaxId());
                            setLastTweetID(result.getMaxId(), stream);//Save ID of the most recent Tweet
                        }

                        query.setMaxId(currentTweetID -1L); //Get the next batch of 100 tweets
                        iteration ++;
                        //System.out.println("\n\n------");
                    }
                }catch(TwitterException te){
                    if(te.getErrorCode() == 88){
                        //System.out.println("getSecondsUntilReset: " + te.getRateLimitStatus().getSecondsUntilReset());
                        canGetMoreTweets = false;
                        //System.out.println("\n\n\n RATE LIMIT EXCCEDED!!!");
                    }
                    log.error("Error getting tweets:"  + te );
                }
                
            }while(canGetMoreTweets && tweetsReceived <17000);  //Maximo permitido para extraer de twitter c/15 minutos
            
            //System.out.println("TOTAL TWEETS RECEIVED:" + tweetsReceived);
            if(aListExternalPost.size()>0)
            {
                new Classifier(aListExternalPost, stream, this);
            }
        }catch(Exception e){            
            log.error("Error in listen():"  + e );
        }
        
        /*
        //WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        //System.out.println("Red SocialID:"+this.getId()+", Red Title:"+this.getTitle()+", sitio:"+wsite.getId());
        ArrayList <ExternalPost> aListExternalPost=new ArrayList();
        try {
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();
            Query query = new Query(stream.getPhrase());
            QueryResult result=twitter.search(query);
            List<Status> tweets = result.getTweets();
            for (Status tweet : tweets) {
                ExternalPost external = new ExternalPost();
                external.setPostId(String.valueOf(tweet.getId())); 
                external.setCreatorId(String.valueOf(tweet.getUser().getScreenName()));
                external.setCreatorName("@"+tweet.getUser().getScreenName());
                external.setCreationTime(""+tweet.getCreatedAt());
                //external.setUpdateTime();
                if (tweet.getText()!=null) {
                    external.setMessage(tweet.getText());
                }
                //if (tweet.getAnnotations().) { TODO: Ver si en las anotaciones, se encuentra una descripción del tweet
                //    external.setDescription(postsData.getJSONObject(k).getString("description"));
                //}
                aListExternalPost.add(external);
            }
            //Si el ArrayList tiene tamaño mayor a 0, entonces es que existen mensajes para enviar al clasificador
            if(aListExternalPost.size()>0)
            {
                new Classifier(aListExternalPost, stream, this);
            }
        } catch (Exception te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
        */

    }


    //@Override
    public void listenAlive(SWBModel model) {
        try {
            StatusListener listener = new SWBSocialStatusListener(model, this, null);
            /*create filterQuery*/
            FilterQuery query = new FilterQuery();
            //NOTE: format of values: {minLongitude, minLatitude}, {...}
            //double[][] loc = {{-118, 37}, {-86, 33}}; //Bounding Box de San Francisco
            //double[][] loc = {{37.78452999999, -122.39532395324}, {37.78452999998, -122.39532395323}}; //Bounding Box de San Francisco
            //double[][] loc = {{32.718620, -86.703392}, {14.532850, -118.867172}}; //Bounding Box de México (País) Encontrado en http://isithackday.com/geoplanet-explorer/index.php?woeid=23424900
            //query.locations(loc);

            //Palabras a monitorear
            String words2Monitor=SWBSocialUtil.words2Monitor.getWords2Monitor(",", model);
            //System.out.println("words2MonitorGeorge:"+words2Monitor+", en cta:"+this);
            if(words2Monitor!=null && words2Monitor.trim().length()>0)
            {
                String[] tr = {words2Monitor};
                query.track(tr);
            }


            //System.out.println(query.toString());

            //Autenticación con Oath, comentado, para despues utilizarlo
        /*
            TwitterStream twitterStream = new
            TwitterStreamFactory(listener).getInstance();
            twitterStream.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
            twitterStream.setOAuthAccessToken(new AccessToken(TOKEN,
            TOKEN_SECRET));
            FilterQuery filterQuery = new FilterQuery();
            filterQuery.track(new String[] {"is a"});
            twitterStream.filter(filterQuery);
             *
             */

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true);
            //simple http form based authentication, you can use oAuth if you have one, check Twitter4j documentation
            //cb.setUser(this.getLogin());
            //cb.setPassword(this.getPassword());
            cb.setUser("George24Mx");
            cb.setPassword("george24");
            // creating the twitter listener


            Configuration cfg = cb.build();
            trial = new TwitterStreamFactory(cfg).getInstance();

            trial.addListener(listener);


            trial.filter(query);

            //System.out.println(" here is stuff : " + trial.getFilterStream(query));


            //trial.sample();
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            log.error(e);
        }
    }

    //@Override
    public void stopListenAlive() {
        if(trial!=null)
        {
            trial.cleanUp();
            trial.shutdown();
            //System.out.println("DETUVO LA CONEXION EN:"+this.getId());
        }
    }

    @Override
    public void authenticate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        //System.out.println("Twitter.autenticate.............");
        String verifier = request.getParameter("oauth_verifier");
        if(verifier==null)
        {
            //System.out.println("Twitter----paso 1");
            twitter4j.Twitter twitterFI = new TwitterFactory().getInstance();
            twitterFI.setOAuthConsumer(getAppKey(), getSecretKey());
            try {
                requestToken = twitterFI.getOAuthRequestToken(getRedirectUrl(request, paramRequest));
                //response.sendRedirect(requestToken.getAuthorizationURL());
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println(" function ioauth() {");
                out.println("  mywin = window.open('"+requestToken.getAuthorizationURL()+"','_blank','width=840,height=680',true);");
                out.println("  mywin.focus();");
                out.println(" }");
                out.println(" if(confirm('¿Autenticar la cuenta en Twitter?')) {");
                out.println("  ioauth();");
                out.println(" }");
                out.println("</script>");
            }catch(TwitterException te) {
                throw new IOException(te.getMessage());
            }
        }
        else
        {
            //System.out.println("Twitter----2");
            twitter4j.Twitter twitterFI = new TwitterFactory().getInstance();
            twitterFI.setOAuthConsumer(getAppKey(), getSecretKey());
            try {
                AccessToken accessToken = twitterFI.getOAuthAccessToken(requestToken, verifier);
                setAccessToken(accessToken.getToken());
                setAccessTokenSecret(accessToken.getTokenSecret());
                setSn_authenticated(true);
                //SWBResourceURL posted = paramRequest.getRenderUrl().setMode("view").setParameter("objUri", getEncodedURI());
                //response.sendRedirect(request.getContextPath() + posted);
            }catch(TwitterException te) {
                te.printStackTrace(System.out);
                throw new IOException(te.getMessage());
            }catch(Exception e) {
                e.printStackTrace(System.out);
                throw new IOException(e.getMessage());
            }finally {
                PrintWriter out = response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("  window.close();");
                out.println("</script>");
            }
        }
    }
    
    private String getRedirectUrl(HttpServletRequest request, SWBParamRequest paramRequest)
    {
        //System.out.println("getRedirectUrl....");
        StringBuilder address = new StringBuilder(128);
        address.append("http://").append(request.getServerName()).append(":").append(request.getServerPort()).append("/").append(paramRequest.getUser().getLanguage()).append("/").append(paramRequest.getResourceBase().getWebSiteId()).append("/"+paramRequest.getWebPage().getId()+"/_rid/").append(paramRequest.getResourceBase().getId()).append("/_mod/").append(paramRequest.getMode()).append("/_lang/").append(paramRequest.getUser().getLanguage());
        //System.out.println("URL callback="+address);
        return address.toString();
    }

    @Override
    public String doRequestPermissions() {
        return null;        
    }

    @Override
    public String doRequestAccess() {
        return null;
    }
    
    
    public double getUserKlout(String twitterUserID)
    {
        String url_1="http://api.klout.com/v2/identity.json/tw/"+twitterUserID;
        String kloutJsonResponse_1=getData(url_1);
        System.out.println("kloutResult step-1:"+kloutJsonResponse_1);
        
        //Obtener id de json
        try
        {
            if(kloutJsonResponse_1!=null)
            {
                JSONObject userData = new JSONObject(kloutJsonResponse_1);
                String kloutUserId = userData != null && userData.get("id") != null ? (String) userData.get("id") : "";
                System.out.println("kloutId de Resultado en Json:"+kloutUserId);
            
                //Segunda llamada a la red social Klout, para obtener Json de Score del usuario (kloutUserId) encontrado
                if(kloutUserId!=null)
                {
                    String url_2="http://api.klout.com/v2/user.json/"+kloutUserId+"/score";
                    String kloutJsonResponse_2=getData(url_2);
                    System.out.println("kloutResult step-2-Json:"+kloutJsonResponse_2);

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
        return 0;
    }
    
    private static String getData(String url)
    {
        String answer = null;
        //String key=SWBContext.getAdminWebSite().getProperty("kloutKey");    //TODO:Ver con Jei x que no funciona esto...
        String key=SWBSocialUtil.Util.getModelPropertyValue(SWBContext.getAdminWebSite(), "kloutKey");
        //if(key==null) key="8fkzgz7ngf7bth3nk94gnxkd";   //Solo para fines de pruebas, quitar despues y dejar línea anterior.
        System.out.println("key para KLOUT--Gg:"+key);
        if(key!=null)
        {
            url=url+"?key="+key;
            URLConnection conex = null;
            try {
                System.out.println("Url a enviar a Klout:"+url);
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

                conex.setConnectTimeout(5000);
            } catch (Exception nexc) {
                System.out.println("nexc Error:"+nexc.getMessage());
                conex = null;
            }
        
            //Analizar la respuesta a la peticion y obtener el access token
            if (conex != null) {
                try
                {
                    answer = getResponse(conex.getInputStream());
                }catch(Exception e)
                {
                    log.error(e);
                }
            }
        }
        return answer;
    }
    
    
     /**
     * Lee un flujo de datos y lo convierte en un {@code String} con su contenido codificado en UTF-8
     * @param data el flujo de datos a convertir
     * @return un {@code String} que representa el contenido del flujo de datos especificado, codificado en UTF-8
     * @throws IOException si ocurre un problema en la lectura del flujo de datos
     */
    private static String getResponse(InputStream data) throws IOException {
        
        StringBuilder response = new StringBuilder(256);
        try
        {
            Reader in = new BufferedReader(new InputStreamReader(data, "UTF-8"));
            char[] buffer = new char[1000];
            int charsRead = 0;
            while (charsRead >= 0) {
                response.append(buffer, 0, charsRead);
                charsRead = in.read(buffer);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return response.toString();
    }

}
