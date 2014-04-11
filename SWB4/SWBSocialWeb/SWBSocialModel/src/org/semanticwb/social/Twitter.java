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
import java.util.HashMap;
import java.util.Iterator;
import javaQuery.j2ee.tinyURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.routines.UrlValidator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Language;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.social.listener.Classifier;
import org.semanticwb.social.listener.twitter.SWBSocialStatusListener;
import org.semanticwb.social.util.SWBSocialUtil;
import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.GeoLocation;
import twitter4j.StatusListener;
import twitter4j.TwitterStreamFactory;


public class Twitter extends org.semanticwb.social.base.TwitterBase {

    private static Logger log = SWBUtils.getLogger(Twitter.class);
    
    private static HashMap<String, TwitterStream> ListenAlives=new HashMap();
    
    //private Long lastTweetID; // get tweets since the tweet with ID 'lastTweetID' until now
                                // In the first request the lastTweetID value is 0. This value MUST be updated in further executions
    //private int tweetsReceived;
    
    //public List<Status> twitterResults;
    
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
    static {
        Twitter.social_Twitter.registerObserver(new SocialNetSemanticObserver());
    }

    @Override
    public void postMsg(Message message) {        
        if(!isSn_authenticated() || getAccessToken() == null ){
            log.error("Not authenticated network: " + getTitle() + ". Unable to post Message");
            return;
        }
        
        if (message != null && message.getMsg_Text() != null && message.getMsg_Text().trim().length() > 1) {
            String messageText = this.shortMsgText(message);
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();
            try {
                twitter.setOAuthConsumer(this.getAppKey(), this.getSecretKey());
                AccessToken accessToken = new AccessToken(this.getAccessToken(), this.getAccessTokenSecret());
                twitter.setOAuthAccessToken(accessToken);
                //StatusUpdate sup = new StatusUpdate(new String(message.getMsg_Text().getBytes(), "utf-8"));
                //StatusUpdate sup = new StatusUpdate(new String(shortUrl(message.getMsg_Text()).getBytes(), "ISO-8859-1"));
                Status sup = null;
                String urlLocalPost = "";
                Iterator<String> files = message.listFiles();
                if(files.hasNext()){//If at least one file found
                    String absolutePath = SWBPortal.getEnv("wb/absolutePath") == null ? "" : SWBPortal.getEnv("wb/absolutePath");
                    urlLocalPost = absolutePath + "/es/SWBAdmin/ViewPostFiles?uri=" + message.getEncodedURI() + "&neturi=" + this.getEncodedURI();
                    urlLocalPost = SWBSocialUtil.Util.shortSingleUrl(urlLocalPost);
                }                
                if(message.getPostInSource()!=null && message.getPostInSource().getSocialNetMsgId()!=null)
                {
                    //System.out.println("Twitter Msg PRIMERA OPCION...:"+message.getPostInSource().getPostInSocialNetworkUser().getSnu_name());
                    if(!urlLocalPost.isEmpty()){
                        sup = twitter.updateStatus(new StatusUpdate(new String((message.getPostInSource().getPostInSocialNetworkUser().getSnu_name()+" " + messageText + " " + urlLocalPost).getBytes(), "ISO-8859-1")).inReplyToStatusId(Long.parseLong(message.getPostInSource().getSocialNetMsgId())));
                    }else{
                        sup = twitter.updateStatus(new StatusUpdate(new String((message.getPostInSource().getPostInSocialNetworkUser().getSnu_name()+" " + messageText).getBytes(), "ISO-8859-1")).inReplyToStatusId(Long.parseLong(message.getPostInSource().getSocialNetMsgId())));
                    }
                }else{
                    //System.out.println("Twitter Msg SEGUNDA OPCION...");
                    if(!urlLocalPost.isEmpty()){
                        sup = twitter.updateStatus(new StatusUpdate(new String((messageText + " " + urlLocalPost).getBytes(), "ISO-8859-1")));
                    }else{
                        sup = twitter.updateStatus(new StatusUpdate(new String((messageText).getBytes(), "ISO-8859-1")));
                    }
                }
                //Status stat = twitter.updateStatus(sup);
                long longStat = sup.getId();
                
                if(longStat>0)
                {
                    SWBSocialUtil.PostOutUtil.savePostOutNetID(message, this, String.valueOf(longStat), null);
                }else{
                    SWBSocialUtil.PostOutUtil.savePostOutNetID(message, this, null, "Problem encountered posting twitter message");
                }
                
                // System.out.println("longStat: " + longStat + " texto: " + stat.getText());
                //getPostContainer().getPost().setSocialNetPostId("");
            }catch (TwitterException te) {
                SWBSocialUtil.PostOutUtil.savePostOutNetID(message, this, null, te.getErrorMessage());
                log.error("Twitter exception posting twitter message", te);
            }catch(Exception e ){
                SWBSocialUtil.PostOutUtil.savePostOutNetID(message, this, null, e.getMessage());
                log.error("Exception posting twitter message", e);
            }
        }
    }

    public void postPhoto(Photo photo) {
        if(!isSn_authenticated() || getAccessToken() == null ){
            log.error("Not authenticated network: " + getTitle() + ". Unable to post Photo");
            return;
        }
        //System.out.println("Inside post photo TWITTER");
        if (photo != null) {
            String messageText = this.shortMsgText(photo);
            String photoToPublish="";
            String additionalPhotos="";
            int photoNumber = 0;
                        
            Iterator<String> photos = photo.listPhotos();
            while(photos.hasNext()){
                String sPhoto =photos.next();
                if(++photoNumber == 1){//post the first Photo
                    photoToPublish = SWBPortal.getWorkPath() + photo.getWorkPath() + "/" + sPhoto;
                }/*else{
                    additionalPhotos += SWBPortal.getWorkPath() + photo.getWorkPath() + "/" + sPhoto + " ";
                }*/
            }
            if(photoNumber == 0){
                log.error("No photo(s) found!");
                //System.out.println("No Photos FOUND");
                return;
            }else if (photoNumber > 1){
                String absolutePath = SWBPortal.getEnv("wb/absolutePath") == null ? "" : SWBPortal.getEnv("wb/absolutePath");
                additionalPhotos = absolutePath + "/es/SWBAdmin/ViewPostFiles?uri=" + photo.getEncodedURI() + "&neturi=" + this.getEncodedURI();
                additionalPhotos = SWBSocialUtil.Util.shortSingleUrl(additionalPhotos);
                //System.out.println("Path form multiple photos:" + additionalPhotos);
            }
            
            //System.out.println("The photo to be published TWITTER:" + photoToPublish);            
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(this.getAppKey(), this.getSecretKey());
            AccessToken accessToken = new AccessToken(this.getAccessToken(), this.getAccessTokenSecret());
            //String description = photo.getMsg_Text()!= null ? photo.getMsg_Text() : "";
            twitter.setOAuthAccessToken(accessToken);
            
            try {
                twitter.setOAuthAccessToken(accessToken);
                StatusUpdate sup = null;// new StatusUpdate(new String(shortUrl(description).getBytes(), "ISO-8859-1"));
                Status stat = null;
                if(photo.getPostInSource()!=null && photo.getPostInSource().getSocialNetMsgId()!=null)
                {
                    //System.out.println("Twitter Photo PRIMERA OPCION...:"+photo.getPostInSource().getPostInSocialNetworkUser().getSnu_name());
                    //sup = new StatusUpdate(new String(shortUrl(photo.getPostInSource().getPostInSocialNetworkUser().getSnu_name() + " " +description).getBytes(), "ISO-8859-1"));
                    messageText = messageText + (additionalPhotos.trim().length() > 0 ? " " + additionalPhotos : "" ); //
                    sup = new StatusUpdate(new String((photo.getPostInSource().getPostInSocialNetworkUser().getSnu_name() + " " + messageText ).getBytes(), "ISO-8859-1"));
                    //sup.setMedia(new File(photoSend));
                    sup.setMedia(new File(photoToPublish));
                    stat = twitter.updateStatus(sup.inReplyToStatusId(Long.parseLong(photo.getPostInSource().getSocialNetMsgId())));
                }else{
                    //System.out.println("Twitter Photo SEGUNDA OPCION...");
                    messageText = messageText + (additionalPhotos.trim().length() > 0 ? " " + additionalPhotos : "" ); //
                    sup = new StatusUpdate(new String((messageText).getBytes(), "ISO-8859-1"));
                    //sup.setMedia(new File(photoSend));
                    sup.setMedia(new File(photoToPublish));
                    stat = twitter.updateStatus(sup);
                }
                long longStat = stat.getId();
                
                if(longStat>0)
                {
                    SWBSocialUtil.PostOutUtil.savePostOutNetID(photo, this, String.valueOf(longStat), null);
                }else{
                    SWBSocialUtil.PostOutUtil.savePostOutNetID(photo, this, null, "Problem encountered posting twitter photo");
                }
                
                
            }catch (TwitterException te) {
                SWBSocialUtil.PostOutUtil.savePostOutNetID(photo, this, null, te.getErrorMessage());
                log.error("Twitter exception posting twitter message ", te);
            }catch(Exception e ){
                SWBSocialUtil.PostOutUtil.savePostOutNetID(photo, this, null, e.getMessage());
                log.error("Exception posting twitter message ", e);
            }
            //System.out.println("Mensaje de Photo de Twitter:" + description);
            //System.out.println("Photo de Twitter:" + photoSend);
        }
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
    private long getLastTweetID(Stream stream){
        SocialNetStreamSearch socialStreamSerch=SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, this);
        /*System.out.println("----------GET LAST TWEET ID");
        System.out.println("----------Net:" +  this.getTitle() + " Stream:" + stream.getTitle());
        System.out.println("----------SocialStreamSerch:\n\t" + socialStreamSerch );
        if(socialStreamSerch != null){
            System.out.println("Stream:\n\t" + socialStreamSerch.getStream() );
            System.out.println("Next Date:\n\t" + socialStreamSerch.getNextDatetoSearch() );
        }*/
        long lastTweetID = 0L;
        if(socialStreamSerch!=null && socialStreamSerch.getNextDatetoSearch()!= null){
            try{
                //System.out.println("RECOVERING NEXTDATETOSEARCH: " + socialStreamSerch.getNextDatetoSearch());
                lastTweetID = Long.parseLong(socialStreamSerch.getNextDatetoSearch());
                
            }catch(NumberFormatException nfe){
                lastTweetID = 0L;
                log.error("----------Error in getLastTweetID():",  nfe);
                //System.out.println("----------Invalid value found in NextDatetoSearch(). Set to 0");
            }
        }else{
            lastTweetID=0L;
        }
        return lastTweetID;
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
                //System.out.println("----------EL VALOR ALMACENADO ES:" +  tweetID.toString());
                socialStreamSerch.setNextDatetoSearch(String.valueOf(tweetID));
                //System.out.println("SET VALUE:" + socialStreamSerch.getNextDatetoSearch());
            }else{
                //System.out.println("NO ESTÁ GUARDANDO NADA PORQUE EL VALOR ALMACENADO YA ES IGUAL O MAYOR AL ACTUAL");
                log.error("Do not save the Tweet ID because stored value is equal or greater than the received");
            }
        }catch(NumberFormatException nfe){
            log.error("Error in setLastTweetID():", nfe);
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
            String[] phrasesStream = stream.split(","); //Delimiter            
            parsedPhrases = "";
            String tmp;
            int noOfPhrases = phrasesStream.length;
            for (int i = 0; i < noOfPhrases; i++) {
                if(!phrasesStream[i].trim().isEmpty()){
                    tmp = phrasesStream[i].trim().replaceAll("\\s+", " "); //replace multiple spaces beetwen words for one only one space
                    parsedPhrases += ((tmp.contains(" ")) ? ("\"" + tmp + "\"") : tmp); // if spaces found, it means more than one word in a phrase
                    if ((i + 1) < noOfPhrases) {
                        parsedPhrases += " OR ";
                    }
                }
            }
        }
        return parsedPhrases;
    }

    @Override
    public void listen(Stream stream) {
        if(!isSn_authenticated() || getAccessToken() == null ){
            log.error("Not authenticated network: " + getTitle() + "!!!");
            return;
        }

        //System.out.println("Entra a Twitter/Listen");
        //WebSite wsite = WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        //System.out.println("Red SocialID:"+this.getId()+", Red Title:"+this.getTitle()+", sitio:"+wsite.getId());
        //System.out.println("Creador:" + this.getCreator());
        //List<Status> twitterResults = new ArrayList<Status>();
        SocialSite socialSite = (SocialSite)WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());        
        int  tweetsReceived = 0;
        ArrayList <ExternalPost> aListExternalPost;
        int blockOfTweets = 500; //this is the default Value, 
        try{
            if(socialSite.getBlockofMsgToClassify() > 0){
                blockOfTweets = socialSite.getBlockofMsgToClassify();
            }
        }catch(Exception e){}
        //System.out.println("Message Block Twitter:" + blockOfTweets);
        
        try{            
            long lastTweetID = getLastTweetID(stream); //gets the value stored in NextDatetoSearch
            twitter4j.Twitter twitter = new TwitterFactory(configureOAuth().build()).getInstance();
            String searchPhrases = formatsTwitterPhrases(stream);//getPhrases(stream.getPhrase());
            if(searchPhrases == null || searchPhrases.isEmpty()){
                log.warn("\n Not a valid value to make a twitter search:" +searchPhrases);
               return; 
            }
            if(searchPhrases == null || searchPhrases.isEmpty()){
                return;
            }
            
            twitter4j.Query query = new Query(searchPhrases);
            if(stream.getGeoCenterLatitude() != 0 && stream.getGeoCenterLongitude() != 0 && stream.getGeoRadio() > 0){
                if(stream.getGeoRadio() < 50){
                    query.setGeoCode(new GeoLocation(stream.getGeoCenterLatitude(), stream.getGeoCenterLongitude()), 50, "km");//(latitude, longitude), radius, units of distance
                }else{
                    query.setGeoCode(new GeoLocation(stream.getGeoCenterLatitude(), stream.getGeoCenterLongitude()), stream.getGeoRadio(), "km");//(latitude, longitude), radius, units of distance
                }
            }
            query.setCount(100); //max tweets by request
            
            boolean canGetMoreTweets = true;
            int iteration = 1;
            long currentTweetID = 0L;
            aListExternalPost = new ArrayList();
            do{
                try{
                    //System.out.println("QUERY: " + query);
                    twitter4j.QueryResult result = twitter.search(query);
                    int noOfTweets = result.getTweets().size();
                    //System.out.println("\ntweets by request: " + noOfTweets);                    
                    
                    if(noOfTweets == 0){
                        //System.out.println("No more tweets available for the current query!!");
                        canGetMoreTweets = false;
                    }else{
                        for(Status status : result.getTweets()){
                            System.out.println("Current ID:" + status.getId() + " stored ID:" + lastTweetID);
                            if(status.getId() <= lastTweetID){ //If value is ZERO then get all tweets available,
                                canGetMoreTweets = false;      //If it's greater than ZERO, get tweets posted since the tweet with id lastTweetID
                                //System.out.println("SINCEID LIMIT REACHED!!!");
                                break;
                            }                            
                                
                            if(!status.isRetweet())
                            {
                                if(status.getUser() != null){
                                    ExternalPost external = new ExternalPost();
                                    external.setPostId(String.valueOf(status.getId())); 
                                    external.setCreatorId(String.valueOf(status.getUser().getId()));
                                    external.setCreatorName("@"+status.getUser().getScreenName());
                                    external.setCreationTime(status.getCreatedAt());
                                    external.setDevice(status.getSource());
                                    if (status.getText()!=null) {
                                       external.setMessage(status.getText());
                                    }                         
                                    external.setCreatorPhotoUrl(status.getUser().getBiggerProfileImageURL());
                                    external.setPostType(SWBSocialUtil.MESSAGE);   //TODO:VER SI SIEMPRE EN TWITTER LE DEBO DE PONER ESTE TIPO O TAMBIÉN LE PUDIERA PONER QUE ES DE TIPO FOTO
                                    external.setFollowers(status.getUser().getFollowersCount());
                                    external.setFriendsNumber(status.getUser().getFriendsCount());
                                    if(status.getPlace()!=null)
                                    {
                                        external.setPlace(status.getPlace().getFullName());
                                        if(status.getPlace().getBoundingBoxCoordinates()!=null && status.getPlace().getBoundingBoxCoordinates().length > 0)
                                        {
                                            GeoLocation[] boundingBox = status.getPlace().getBoundingBoxCoordinates()[0];
                                            
                                            double latCenterPoint=((boundingBox[3].getLatitude()-boundingBox[0].getLatitude())/2)+boundingBox[0].getLatitude();
                                            double lngCenterPoint=((boundingBox[1].getLongitude()-boundingBox[0].getLongitude())/2)+boundingBox[0].getLongitude();
                                            
                                            //System.out.println("Punto 5:["+latCenterPoint+"," +lngCenterPoint+"]");
                                            
                                            external.setLatitude(latCenterPoint);
                                            external.setLongitude(lngCenterPoint);
                                            
                                            if(status.getPlace().getCountryCode()!=null)
                                            {
                                                external.setCountryCode(status.getPlace().getCountryCode().toUpperCase());
                                            }
                                        }
                                    }else if(status.getUser().getLocation()!=null && !status.getUser().getLocation().isEmpty()){
                                        external.setUserGeoLocation(status.getUser().getLocation());
                                    }
                                    
                                    external.setSocialNetwork(this);
                                    external.setPostShared((int)status.getRetweetCount());   
                                    external.setCreatorPhotoUrl(status.getUser().getBiggerProfileImageURL());
                                    aListExternalPost.add(external);

                                    //twitterResults.add(status);
                                    //System.out.println("User: @" + status.getUser().getScreenName() + "\tID:" + status.getId() + "\tTime:" + status.getCreatedAt() + "\tText:" + status.getText());
                                    currentTweetID = status.getId();
                                    tweetsReceived++;
                                    
                                    if((blockOfTweets > 0) && (aListExternalPost.size() >= blockOfTweets)){//Classify the block of tweets
                                        //System.out.println("CLASSIFYING:" + aListExternalPost.size());
                                        new Classifier((ArrayList <ExternalPost>)aListExternalPost.clone(), stream, this, false);
                                        aListExternalPost.clear();
                                    }
                                }
                            }
                        }
                        
                        if(!stream.isActive()){//If the stream has been disabled stop listening
                            canGetMoreTweets = false;
                        }
                        //Sometimes the api gets the same tweet several times!!!
                        if(noOfTweets <= 10){//Got more messages than the requested->There are no more messages.
                            canGetMoreTweets = false;
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
                        log.error("Error getting tweets SEARCH - RATE LIMIT EXCCEDED:", te );
                    }
                    if(te.getErrorCode() == 89){
                        log.error("Error getting tweets SEARCH - AUTHENTICACION CREDENTIALS MISSING OR INCORRECT", te );
                    }
                    log.error("Error getting tweets SEARCH:", te );
                    canGetMoreTweets = false;
                }catch(Exception ex){
                    log.error("Error getting tweets SEARCH", ex);
                    canGetMoreTweets = false;
                }
            }while(canGetMoreTweets && tweetsReceived <17000);  //Maximo permitido para extraer de twitter c/15 minutos
            
            if(aListExternalPost.size()>0){
                //System.out.println("CLASSIFYING FINALLY:" + aListExternalPost.size());
                new Classifier(aListExternalPost, stream, this, false);
            }
            //System.out.println("TOTAL TWEETS RECEIVED:" + tweetsReceived);
        }catch(Exception e){            
            log.error("Error in listen():",  e );
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


    @Override
    
    public void listenAlive(Stream stream) {
        try {
            //System.out.println("Entra a Twitter listenAlive-1:"+stream.getURI()+"|"+this.getURI());
            if(ListenAlives.containsKey(stream.getURI()+"|"+this.getURI())) {
                stopListenAlive(stream);
            }
            //System.out.println("Entra a Twitter listenAlive-2:"+stream.getURI()+"|"+this.getURI());
            StatusListener listener = new SWBSocialStatusListener(stream, this, ListenAlives);
            /*
            System.out.println("AppKey:"+getAppKey());
            System.out.println("SecretKey:"+getSecretKey());
            System.out.println("AccessToken:"+getAccessToken());
            System.out.println("AccessTokenSecret:"+getAccessTokenSecret());
            */
            /*create filterQuery*/
            FilterQuery query = new FilterQuery();
            
            
            //Palabras a monitorear
            String words2Monitor=stream.getPhrase();
            if(words2Monitor!=null && words2Monitor.trim().length()>0)
            {
                String[] tr = {words2Monitor};
                query.track(tr);
                //System.out.println("words2Monitor--1:"+words2Monitor);
            }
            
            TwitterStream twitterStream = new TwitterStreamFactory(configureOAuth().build()).getInstance();
            
            //System.out.println(twitterStream);
            
            twitterStream.addListener(listener);

            twitterStream.filter(query);
            
            //System.out.println("Entra a Twitter listenAlive-3G1:"+stream.getURI()+"|"+this.getURI());
            //twitterStream.sample();
            ListenAlives.put(stream.getURI()+"|"+this.getURI(), twitterStream);
            
            //System.out.println("Entra a Twitter listenAlive-4-JorgeJJ:"+stream.getURI()+"|"+this.getURI());
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            log.error(e);
        }
    }

    //@Override
    
    public void stopListenAlive(Stream stream) {
        System.out.println("Entra a stopListenAlive");
        if(ListenAlives.containsKey(stream.getURI()+"|"+this.getURI()))
        {
            TwitterStream twitterStream=ListenAlives.get(stream.getURI()+"|"+this.getURI());
            if(twitterStream!=null)
            {
                twitterStream.cleanUp();
                //twitterStream.shutdown(); //Este tumba todos los threads y ya no vuelve a levantar otro para ninguno-->No ponerlo
                twitterStream=null;
                ListenAlives.remove(stream.getURI()+"|"+this.getURI());
                System.out.println("DETUVO LA CONEXION EN stopListenAliveGeorge:"+this.getId());
            }
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
                out.println("  if(mywin == null){");
                out.println("    alert('¿Tienes bloqueadas las ventajas emergentes?');");
                out.println("    return;");
                out.println("  }");
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
    
    @Override
    public double getUserKlout(String twitterUserID)
    {
        String url_1="http://api.klout.com/v2/identity.json/tw/"+twitterUserID;
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
                    /*
                    //TEST: OBTENCIÓN DE TOPICS DEL USUARIO
                    String url_3="http://api.klout.com/v2/user.json/"+kloutUserId+"/topics";
                    System.out.println("url_3:"+url_3);
                    String kloutJsonResponse_3=getData(url_3);
                    System.out.println("kloutJsonResponse_3-Json:"+kloutJsonResponse_3);
                    if(kloutJsonResponse_3!=null)
                    {
                        JSONObject userScoreData = new JSONObject(kloutJsonResponse_3);
                        
                        JSONArray jArrayIds=userScoreData.getJSONArray("id");
                        for (int i = 0; i < jArrayIds.length(); i++) {
                            JSONObject jsonObj = jArrayIds.getJSONObject(i);
                            System.out.println("jsonObj:"+jsonObj);
                        }
                    }
                       
                    String kloutTopicId = userScoreData != null && userScoreData.get("id") != null ? (String)userScoreData.get("id"):"";
                    String kloutTopicDisplayName  = userScoreData != null && userScoreData.get("displayName") != null ? (String)userScoreData.get("displayName"):"";
                    String kloutTopicName  = userScoreData != null && userScoreData.get("name") != null ? (String)userScoreData.get("name"):"";
                    String kloutTopicSlug  = userScoreData != null && userScoreData.get("slug") != null ? (String)userScoreData.get("slug"):"";
                    String kloutTopicImgUrl  = userScoreData != null && userScoreData.get("imageUrl") != null ? (String)userScoreData.get("imageUrl"):"";
                    String kloutTopicType  = userScoreData != null && userScoreData.get("topicType") != null ? (String)userScoreData.get("topicType"):"";
                    System.out.println("kloutTopicType:"+kloutTopicType);
                    if(kloutTopicType.equals("sub"))
                    {
                       try{ 
                           SWBUtils.IO.log2File("c://kloutTypes.txt", kloutTopicType);
                       }catch(Exception e){log.error(e);
                       }
                       System.out.println("kloutUserId:"+kloutUserId);
                       System.out.println("kloutTopicId:"+kloutTopicId);
                       System.out.println("kloutTopicDisplayName:"+kloutTopicDisplayName);
                       System.out.println("kloutTopicName:"+kloutTopicName);
                       System.out.println("kloutTopicSlug:"+kloutTopicSlug);
                       System.out.println("kloutTopicImgUrl:"+kloutTopicImgUrl);
                    }
                    */
                    //FINALIZA TEST
                   
                }
            }
        }catch(JSONException je)
        {
            log.error(je);
        }
        return 0;
    }
    
    private static String getData(String url)
    {
        String answer = null;
        //String key=SWBContext.getAdminWebSite().getProperty("kloutKey");    //TODO:Ver con Jei x que no funciona esto...
        String key=SWBSocialUtil.Util.getModelPropertyValue(SWBSocialUtil.getConfigWebSite(), "kloutKey");
        //if(key==null) key="8fkzgz7ngf7bth3nk94gnxkd";   //Solo para fines de pruebas, quitar despues y dejar línea anterior.
        //System.out.println("key para KLOUT--Gg:"+key);
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

    @Override
    public void stopListen(Stream stream) {
        System.out.println("Entra a Twitter/stopListen");
    }

    
    public void postPhotoSocialNets(Twitter t, SocialWebPage swp, Language l, StringBuilder address) {
   
        String photoToPublish = "";
        //String additionalPhotos = "";
        String urlShort = SWBSocialUtil.Util.shortUrl(address.toString());
   

        photoToPublish = SWBPortal.getWorkPath() + swp.getWorkPath() + "/" +SocialWebPage.social_socialwpPhoto.getName()+"_"+swp.getId()+"_"+ swp.getSocialwpPhoto();
        twitter4j.Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(t.getAppKey(), t.getSecretKey());
        AccessToken accessToken = new AccessToken(t.getAccessToken(), t.getAccessTokenSecret());
        String description = swp.getDescription(l.getId()) != null ? swp.getDescription(l.getId()) : "";
        twitter.setOAuthAccessToken(accessToken);

        try {
            twitter.setOAuthAccessToken(accessToken);
            StatusUpdate sup = null;// new StatusUpdate(new String(shortUrl(description).getBytes(), "ISO-8859-1"));
            Status stat = null;

            description = description+" "+urlShort; // + (additionalPhotos.trim().length() > 0 ? " " + additionalPhotos : ""); //
            sup = new StatusUpdate(new String((description).getBytes(), "ISO-8859-1"));
            //sup.setMedia(new File(photoSend));
            sup.setMedia(new File(photoToPublish));
            stat = twitter.updateStatus(sup);
       } catch (UnsupportedEncodingException ex) {
            log.error("Exception", ex);
        } catch (TwitterException ex) {
            log.error("Exception", ex);
            if (401 == ex.getStatusCode()) {
                
            }
        }
    }

    @Override 
    public JSONObject getUserInfobyId(String userId) {
        return null;
    }
    
    
   @Override
   public HashMap<String, Long> monitorPostOutResponses(PostOut postOut) 
   {
        HashMap hMapPostOutNets=new HashMap();
        Iterator<PostOutNet> itPostOutNets=PostOutNet.ClassMgr.listPostOutNetBySocialPost(postOut);
        while(itPostOutNets.hasNext())
        {
            PostOutNet postOutNet=itPostOutNets.next();
            if(postOutNet.getStatus()==1 && postOutNet.getSocialNetwork().getURI().equals(this.getURI()))
            {
                //El número que se agrega es la diferencia entre el número de respuesta encontradas en la red social - el que se encuentra en la propiedad postOutNet.getPo_numResponses()
                hMapPostOutNets.put(postOutNet.getURI(), 5);
                postOutNet.setPo_numResponses(5);
            }
        }
        return hMapPostOutNets;
    }

    @Override
    public boolean removePostOutfromSocialNet(PostOut postOut, SocialNetwork socialNet) {
        boolean removed = false;
        twitter4j.Twitter twitter = new TwitterFactory().getInstance();
        try {
            twitter.setOAuthConsumer(this.getAppKey(), this.getSecretKey());
            AccessToken accessToken = new AccessToken(this.getAccessToken(), this.getAccessTokenSecret());
            twitter.setOAuthAccessToken(accessToken);
            
            Iterator<PostOutNet> ponets = postOut.listPostOutNetInvs();
            while(ponets.hasNext()){
                PostOutNet postoutnet = ponets.next();
                if(postoutnet.getSocialNetwork().equals(socialNet)){//PostOut enviado de la red social
                    if(postoutnet.getStatus() == 1){//publicado
                        System.out.println("1va a borrar!");
                        if(postoutnet.getPo_socialNetMsgID() != null){//Tiene id
                            System.out.println("2va a borrar!");
                            Status removedSt = twitter.destroyStatus(Long.parseLong(postoutnet.getPo_socialNetMsgID()));
                            if(removedSt != null){
                                System.out.println("3va a borrar!");
                                if(removedSt.getId() == Long.parseLong(postoutnet.getPo_socialNetMsgID())){
                                    System.out.println("4va a borrar!");
                                    removed = true;
                                }
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return removed;
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    public String shortMsgText(PostOut postOut){
        SocialSite socialSite = SocialSite.ClassMgr.getSocialSite(postOut.getSemanticObject().getModel().getName());                
        String msgText = postOut.getMsg_Text();
        WebSite admin = SWBContext.getAdminWebSite();
        WebPage linksRedirector  = admin.getWebPage("linksredirector");
        String absolutePath = SWBPortal.getEnv("wb/absolutePath") == null ? "" : SWBPortal.getEnv("wb/absolutePath");
        
        Iterator<PostOutLinksHits> savedLinks = PostOutLinksHits.ClassMgr.listPostOutLinksHitsByPostOut(postOut, socialSite);
        while(savedLinks.hasNext()){
            PostOutLinksHits savedLink = savedLinks.next();
            //System.out.println("--->" + savedLink);
            if(savedLink.getSocialNet().getURI().equals(this.getURI())){//La misma red
                if(msgText.contains(savedLink.getTargetUrl())){//La url existe                    
                    String targetUrl = absolutePath + linksRedirector.getUrl() + "?uri=" + postOut.getEncodedURI() + "&code=" + savedLink.getPol_code() + "&neturi=" + this.getEncodedURI();                    
                    //System.out.println("\n\n---------------\ntarget:" + targetUrl);
                    targetUrl = SWBSocialUtil.Util.shortSingleUrl(targetUrl);                    
                    //System.out.println("shorted:" + targetUrl);
                    msgText = msgText.replace(savedLink.getTargetUrl(), targetUrl);
                    //System.out.println("msg:" + targetUrl);
                }
            }
        }
        //System.out.println("RETURNED MESSAGE:" + msgText);
        return msgText;
    }

    /**
     * Formats phrases according to Query requirements.
     * takes all the phrases from the search fields and formats the 'words' according to 
     * the twitter requirements.
     * @param stream
     * @return Formated phrases.
     */
    private String formatsTwitterPhrases(Stream stream){
        String parsedPhrases = ""; // parsed phrases 
        String exactPhrases = "";
        String orPhrases = "";
        String notPhrases = "";
        String allPhrases ="";
        String fromAccounts = "";
        if(stream.getPhrase() != null && !stream.getPhrase().trim().isEmpty()){//OR (Default)
            orPhrases = stream.getPhrase();            
            orPhrases = SWBSocialUtil.Strings.replaceSpecialCharacters(orPhrases);
            orPhrases = orPhrases.trim().replaceAll("\\s+", " "); //replace multiple spaces beetwen words for one only one space
            String words[] = orPhrases.split(" ");
            int wordsNumber = words.length;
            String tmpString = "";
            for (int i = 0; i < wordsNumber; i++) {
                if(!words[i].trim().isEmpty()){ 
                    tmpString += words[i];
                    if ((i + 1) < wordsNumber) {
                        tmpString += " OR ";
                    }
                }
            }
            orPhrases = tmpString;
        }
        if(stream.getStream_allPhrases() != null && !stream.getStream_allPhrases().trim().isEmpty()){//All phrases
            allPhrases = stream.getStream_allPhrases();
            allPhrases = SWBSocialUtil.Strings.replaceSpecialCharacters(allPhrases);
            allPhrases = allPhrases.trim().replaceAll("\\s+", " "); //replace multiple spaces beetwen words for one only one space
            String words[] = allPhrases.split(" ");
            int wordsNumber = words.length;
            String tmpString = "";
            for (int i = 0; i < wordsNumber; i++) {
                if(!words[i].trim().isEmpty()){
                    tmpString += words[i];
                    if ((i + 1) < wordsNumber) {
                        tmpString += " ";
                    }
                }
            }
            allPhrases = tmpString;
        }
        if(stream.getStream_notPhrase() != null && !stream.getStream_notPhrase().trim().isEmpty()){//Not phrases
            notPhrases = stream.getStream_notPhrase();
            notPhrases = SWBSocialUtil.Strings.replaceSpecialCharacters(notPhrases);
            notPhrases = notPhrases.trim().replaceAll("\\s+", " "); //replace multiple spaces beetwen words for one only one space
            String words[] = notPhrases.split(" ");
            int wordsNumber = words.length;
            String tmpString = "";
            for (int i = 0; i < wordsNumber; i++) {
                if(!words[i].trim().isEmpty()){
                    tmpString += ( i>0 ? " " : "") + "-" + words[i];
                }
            }
            notPhrases = tmpString;
        }
        if(stream.getStream_exactPhrase() != null && !stream.getStream_exactPhrase().trim().isEmpty()){//Exact phrase
            exactPhrases = stream.getStream_exactPhrase();
            exactPhrases = SWBSocialUtil.Strings.replaceSpecialCharacters(exactPhrases);
            exactPhrases = exactPhrases.trim().replaceAll("\\s+", " "); //replace multiple spaces beetwen words for one only one space
            exactPhrases = "\"" + exactPhrases + "\"";
        }
        
        if(stream.getStream_fromAccount() != null && !stream.getStream_fromAccount().trim().isEmpty()){//Find in certains accounts
            fromAccounts = stream.getStream_fromAccount();
            fromAccounts = SWBSocialUtil.Strings.replaceSpecialCharacters(fromAccounts);
            fromAccounts = fromAccounts.trim().replaceAll("\\s+", " "); //replace multiple spaces beetwen words for one only one space
            String words[] = fromAccounts.split(" ");
            int wordsNumber = words.length;
            String tmpString = "";
            for (int i = 0; i < wordsNumber; i++) {
                if(!words[i].trim().isEmpty()){
                    tmpString += ( i>0 ? " OR " : "") + "from:" + words[i];                    
                }                
            }
            fromAccounts = tmpString;
        }

        if(!allPhrases.isEmpty()){
            parsedPhrases += allPhrases;
        }
        if(!exactPhrases.isEmpty()){
            if(parsedPhrases.isEmpty()){
                parsedPhrases = exactPhrases;
            }else{
                parsedPhrases += " " + exactPhrases;
            }
        }
        if(!orPhrases.isEmpty()){
            if(parsedPhrases.isEmpty()){
                parsedPhrases = orPhrases;
            }else{
                parsedPhrases += " " + orPhrases;
            }
        }
        if(!fromAccounts.isEmpty()){
            if(parsedPhrases.isEmpty()){
                parsedPhrases = fromAccounts;
            }else{
                parsedPhrases += " " + fromAccounts;
            }
        }
        if(!notPhrases.isEmpty()){
            if(parsedPhrases.isEmpty()){
                parsedPhrases = notPhrases;
            }else{
                parsedPhrases += " " + notPhrases;
            }
        }
        
        System.out.println("Final String-->" + parsedPhrases + "<-");        
        return parsedPhrases;
    }
    
}
