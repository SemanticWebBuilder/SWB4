/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.social.Kloutable;
import org.semanticwb.social.Message;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.Photo;
import org.semanticwb.social.Post;
import org.semanticwb.social.PostIn;
import org.semanticwb.social.PostOut;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.SocialPFlow;
import org.semanticwb.social.SocialTopic;
import org.semanticwb.social.SocialUserExtAttributes;
import org.semanticwb.social.Twitter;
import org.semanticwb.social.Video;
import org.semanticwb.social.admin.resources.util.SWBSocialResUtil;
import org.semanticwb.social.util.SWBSocialUtil;
import org.semanticwb.social.util.SocialLoader;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
/**
 *
 * @author francisco.jimenez
 */
public class Timeline extends GenericResource{
    private static Logger log = SWBUtils.getLogger(Timeline.class);
    
    /*variables used to define the id of '<div>' for the fields of information, favorite and reweet.
     Each link is in a different '<div>' and it's updated individually*/
    public static String FAVORITE = "/fav";
    public static String INFORMATION = "/inf";
    public static String RETWEET = "/rt";
    public static String TOPIC ="/topic";
    
    /*Additionally every div has a suffix to identify if the status is inside the tab of
     home, mentions, favorites or myTweets */ 
    public static String HOME_TAB = "/home";
    public static String MENTIONS_TAB = "/mentions";
    public static String FAVORITES_TAB = "/favorites";
    public static String DIRECT_MESSAGES_TAB = "/dmsgs";
    public static String USER_TWEETS_TAB = "/userTweets";
    public static String DISCOVER_TAB ="/discover";
    public static String FRIENDS_TAB ="/friends";
    public static String FOLLOWERS_TAB ="/followers";
    
    //Variable used to save the users with active session
    private HashMap<String, twitter4j.Twitter> twitterUsers = new HashMap<String,twitter4j.Twitter>();    
    
    private ConfigurationBuilder configureOAuth(org.semanticwb.social.Twitter tw){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(tw.getAppKey())
          .setOAuthConsumerSecret(tw.getSecretKey())
          .setOAuthAccessToken(tw.getAccessToken())
          .setOAuthAccessTokenSecret(tw.getAccessTokenSecret());       
        return cb;
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = (String) request.getParameter("suri");
        String contentTabId = (String) request.getParameter("contentTabId");
        HttpSession session = request.getSession(true);
        //System.out.println("suriReceived in Timeline:" + objUri);
        twitter4j.Twitter twitter = null;
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Twitter semanticTwitter = (Twitter) semanticObject.createGenericInstance();
        
        if(!semanticTwitter.isSn_authenticated() || semanticTwitter.getAccessToken() == null){
            out.println("<div id=\"configuracion_redes\">");
            out.println("<div id=\"autenticacion\">");
            out.println("<p>      La cuenta no ha sido autenticada correctamente</p>");
            out.println("</div>");
            out.println("</div>");
            return;
        }
        if(contentTabId == null){//The resource is loaded for the first time and it needs to display the tabs
            String jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/twitterTabs1.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            }catch (Exception e) {
                //System.out.println("Error loading the Twitter Tabs " + e);
                log.error("Error loading the Twitter Tabs ", e);
            }
            return;
        }
        
        //Verifies if params are properly set an if the Social Network is authenticated        
        if(semanticTwitter.getAccessToken() == null || semanticTwitter.getAccessTokenSecret() == null ||
                semanticTwitter.getAppKey() == null || semanticTwitter.getSecretKey() == null || !semanticTwitter.isSn_authenticated()){
            out.println("<div class=\"swbform\">");
            out.println("<table width=\"100%\" border=\"0px\">");            
            out.println("   <tr>");
            out.println("       <td style=\"text-align: center;\"><h3>La cuenta no está autenticada correctamente</h3></td>");
            out.println("   </tr>");
            out.println("</table>");
            out.println("</div>");
            return;
        }
        if(twitterUsers.get(objUri) == null){//If user is not logged in, create an instance and add it
            twitter = new TwitterFactory(configureOAuth(semanticTwitter).build()).getInstance();
            //System.out.println("LET'S CREATE A NEW TWITTER OBJECT: " + twitter);
            //System.out.println("objUri NEW in doView:" + objUri);
            twitterUsers.put(objUri, twitter);
        }else{//The user has been created before, use it.
            twitter = twitterUsers.get(objUri);
            //System.out.println("objUri OLD in doView:" + objUri);
            //System.out.println("DO NOT CREATE A NEW TWITTER OBJECT");
        }

        /*try{
            System.out.println("\n*\n*\n*");
            System.out.println("doView suri:" + objUri);
            System.out.println("doView id:" + twitter.getId());
            System.out.println("doView username:" + twitter.getScreenName());        
            System.out.println("\n*\n*\n*");
        }catch(Exception e){ 
            System.out.println("Error doView: " + e.getMessage());
        }*/
        
        /*//Start the listener
        TwitterStream twitterStream = null;             //The stream
        SocialUserStreamListener tweetsListener = null; //The listener reads tweets from Twitter        
        
        //Use the suri and the name of the parameter to identify multiple instances of Twitter Listeners
        if((session.getAttribute(objUri + "twitterStream") == null && session.getAttribute(objUri + "tweetsListener") == null) ||
                !((SocialUserStreamListener)session.getAttribute(objUri +"tweetsListener")).isStreamActive()){ //If no stream found for the current account/session, create one.
            twitterStream = new TwitterStreamFactory(configureOAuth(semanticTwitter).build()).getInstance();
            tweetsListener = new SocialUserStreamListener(twitterStream);
            twitterStream.addListener(tweetsListener);//Listening for statuses, mentions, favorites, DMsgs
            
            try{
                tweetsListener.setCurrentUser(twitter.getId());
            }catch(TwitterException te){
                log.error("Error asigning twitter user: " + te.getErrorMessage());
            }
            
            twitterStream.user();//This method internally starts a thread
            session.setAttribute(objUri + "tweetsListener", tweetsListener);
            session.setAttribute(objUri + "twitterStream", twitterStream);
            System.out.println("Listener started!");
        }else{
             System.out.println("Do not Start a new listener we already have one!!");
             if(session.getAttribute(objUri + "tweetsListener") != null){//If the tab is refreshed, clean all 'new' statuses in ArrayList
                 ((SocialUserStreamListener)session.getAttribute( objUri + "tweetsListener")).clearHomeStatus();
                 ((SocialUserStreamListener)session.getAttribute( objUri + "tweetsListener")).clearMentionsStatus();
                 ((SocialUserStreamListener)session.getAttribute( objUri + "tweetsListener")).clearDirectMStatus();
                 ((SocialUserStreamListener)session.getAttribute( objUri + "tweetsListener")).clearFavoritesStatus();
                 ((SocialUserStreamListener)session.getAttribute( objUri + "tweetsListener")).setStartTime( System.currentTimeMillis());
             }
        }*/
        String jspResponse = "";
        //tweetsListener = (SocialUserStreamListener)session.getAttribute(objUri +"tweetsListener");
        
        //Each one of the tabs is loaded once, so when it loads should verify (and clean its own ArrayList)
        if(contentTabId != null && contentTabId.equals(HOME_TAB)){//El listener es sólo iniciado por el tab principal HOME_TAB
            //Start the listener
            TwitterStream twitterStream = null;             //The stream
            SocialUserStreamListener tweetsListener = null; //The listener reads tweets from Twitter        

            //Use the suri and the name of the parameter to identify multiple instances of Twitter Listeners
            if((session.getAttribute(objUri + "twitterStream") == null && session.getAttribute(objUri + "tweetsListener") == null) ||
                    !((SocialUserStreamListener)session.getAttribute(objUri +"tweetsListener")).isStreamActive()){ //If no stream found for the current account/session, create one.
                twitterStream = new TwitterStreamFactory(configureOAuth(semanticTwitter).build()).getInstance();
                tweetsListener = new SocialUserStreamListener(twitterStream);
                twitterStream.addListener(tweetsListener);//Listening for statuses, mentions, favorites, DMsgs

                try{
                    tweetsListener.setCurrentUser(twitter.getId());
                }catch(TwitterException te){
                    log.error("Error asigning twitter user: " + te.getErrorMessage());
                }

                twitterStream.user();//This method internally starts a thread
                session.setAttribute(objUri + "tweetsListener", tweetsListener);
                session.setAttribute(objUri + "twitterStream", twitterStream);
                System.out.println("Listener started!");
            }else{
                 System.out.println("Do not Start a new listener we already have one!!");
                 if(session.getAttribute(objUri + "tweetsListener") != null){//If the tab is refreshed, clean all 'new' statuses in ArrayList
                     ((SocialUserStreamListener)session.getAttribute( objUri + "tweetsListener")).clearHomeStatus();
                     ((SocialUserStreamListener)session.getAttribute( objUri + "tweetsListener")).clearMentionsStatus();
                     ((SocialUserStreamListener)session.getAttribute( objUri + "tweetsListener")).clearDirectMStatus();
                     ((SocialUserStreamListener)session.getAttribute( objUri + "tweetsListener")).clearFavoritesStatus();
                     ((SocialUserStreamListener)session.getAttribute( objUri + "tweetsListener")).setStartTime( System.currentTimeMillis());
                 }
            }
            //tweetsListener.clearHomeStatus();
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/timeline.jsp";
        }else if(contentTabId != null && contentTabId.equals(MENTIONS_TAB)){
            //tweetsListener.clearMentionsStatus();
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/twitterMentions.jsp";
        }else if(contentTabId != null && contentTabId.equals(FAVORITES_TAB)){
            //tweetsListener.clearFavoritesStatus();
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/twitterFavorites.jsp";
        }else if(contentTabId != null && contentTabId.equals(DIRECT_MESSAGES_TAB)){
            //tweetsListener.clearDirectMStatus();
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/twitterDM.jsp";
        }else if(contentTabId != null && contentTabId.equals(DISCOVER_TAB)){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/twitterDiscover.jsp";
        }else if(contentTabId != null && contentTabId.equals(USER_TWEETS_TAB)){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/twitterMyTweets.jsp";
        }else if(contentTabId != null && contentTabId.equals(FRIENDS_TAB)){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/twitterConnections.jsp";
        }else if(contentTabId != null && contentTabId.equals(FOLLOWERS_TAB)){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/twitterFollowers.jsp";
        }/*else if(contentTabId != null && contentTabId.equals()){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/twitterUserProfile.jsp";
        }else if(contentTabId != null && contentTabId.equals("wordNet")){
            jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/tagCloud.jsp";
        }*/
        
        RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
        
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("twitterBean", twitter);            
            dis.include(request, response);
        } catch (Exception e) {
            log.error("Error in doView() for requestDispatcher" , e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {        
        String action = response.getAction();
        String objUri = request.getParameter("suri");
        //System.out.println("suri in processAction:" + objUri);
        twitter4j.Twitter twitter = twitterUsers.get(objUri);
        if(twitter == null && !action.equals("postMessage") && !action.equals("uploadPhoto")){//When executing an action if can't recover a valid twitter, create it
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Twitter semanticTwitter = (Twitter) semanticObject.createGenericInstance();
            twitter = new TwitterFactory(configureOAuth(semanticTwitter).build()).getInstance();            
            twitterUsers.put(objUri, twitter);
            if(twitter == null){return;}//Unable to create twitter
        }
        //System.out.println("Action:" + response.getAction());
        if(action.equals("doRT")){ //Makes the retweet
            response.setRenderParameter("suri", objUri);
            response.setRenderParameter("currentTab", request.getParameter("currentTab"));
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                //System.out.println("Tweet to RT in doRT:" + id);
                response.setRenderParameter("retweetId", twitter.retweetStatus(id).getId()+""); //When a RT is made a new id is created and it's used to undo the RT
                response.setRenderParameter("id", id+"");                                       //Id of original status
                //System.out.println("Retwit!");
                response.setMode("retweetSent"); //show RT Message and update div
            } catch (TwitterException ex) {
                log.error("Error when trying to retweet ", ex);
                response.setRenderParameter("id", request.getParameter("id"));
                response.setRenderParameter("action",action);
                response.setMode("onError");
            }
        }else if(action.equals("undoRT")){
            response.setRenderParameter("suri", objUri);
            response.setRenderParameter("currentTab", request.getParameter("currentTab"));
            try {
                //System.out.println("Undoing Retweet!!");
                Long retweetId = Long.parseLong(request.getParameter("retweetId"));
                Long originalId = Long.parseLong(request.getParameter("id"));                
                twitter.destroyStatus(retweetId); //Destroy Tweet generated when you Retweeted
                response.setRenderParameter("id", originalId+"");
                response.setMode("unretweetSent"); //show RT Message and update div                
            } catch (TwitterException ex) {
                log.error("Error when trying to undo retweet ", ex);
                response.setRenderParameter("retweetId", request.getParameter("retweetId"));
                response.setRenderParameter("id", request.getParameter("id"));
                response.setRenderParameter("action",action);
                response.setMode("onError");
            }
        }else if(action.equals("sendReply")){
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                String answer = request.getParameter("replyText");
                //System.out.println("Answer Text:" + answer);
                twitter.updateStatus(new StatusUpdate(answer).inReplyToStatusId(id));
                response.setRenderParameter("repliedTweet", "ok");
                response.setMode("tweetSent");                
            } catch (Exception ex) {
                log.error("Error when trying to reply ", ex);
            }
        }else if(action.equals("sendDM")){
            try {
                String dmText = request.getParameter("dmText");
                String idUser = request.getParameter("userId");
                //System.out.println("Answer Text:" + dmText);
                DirectMessage message = twitter.sendDirectMessage(Long.parseLong(idUser), dmText);
                response.setRenderParameter("repliedTweet", "ok");
                response.setMode("tweetSent");                
            } catch (TwitterException ex) {
                log.error("Error when trying to send DM: "+ ex.getErrorMessage(), ex);
                response.setMode("showDMError");
            }
        }else if(action.equals("doFavorite")){
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                //System.out.println("Doing FAVORITE:" + id);
                twitter.createFavorite(id); // El id de st Deberá ser guardado para poder deshacer el Favorite
                response.setRenderParameter("id", id+"");
                response.setRenderParameter("suri", objUri);
                response.setRenderParameter("currentTab", request.getParameter("currentTab"));
                //System.out.println("Favorited!");
                response.setMode("favoriteSent"); //show Favorited Message and update div
            }catch(TwitterException ex) {
                if(ex.getErrorCode() == 139){//You have already favorited this status
                    log.error("You have already FAVORITED this status");
                    response.setRenderParameter("suri", objUri);
                    response.setRenderParameter("id", request.getParameter("id"));
                    response.setMode("showError");
                }
            }
        }else if(action.equals("undoFavorite")){
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                //System.out.println("UNDOING FAVORITE:" + id);
                twitter.destroyFavorite(id);
                response.setRenderParameter("id", id+"");
                response.setRenderParameter("suri", objUri);
                response.setRenderParameter("currentTab", request.getParameter("currentTab"));
                //System.out.println("Unfavorited!");
                response.setMode("favoriteSent"); //show Favorited Message and update div
            } catch (TwitterException ex) { 
                log.error("You have already UNFAVORITED this status ", ex);
                /*if(ex.getErrorCode() == 139){//You retweeted twice
                    log.error("You have already UNFAVORITED this status ");
                    response.setRenderParameter("suri", objUri);
                    response.setRenderParameter("id", request.getParameter("id"));
                    response.setMode("showError");
                }*/
            }
        }else if(action.equals("doFollow")){
            try {
                String friendToAdd = request.getParameter("targetUser");
                twitter.createFriendship(friendToAdd);
                //System.out.println("Following user:" + friendToAdd);
                response.setRenderParameter("suri", objUri);
                response.setRenderParameter("targetUser", friendToAdd);               
                response.setMode("followSent"); //show follow message
            } catch (TwitterException ex) {
                log.error("Error when trying to do Follow ", ex);
            }
        }else if(action.equals("undoFollow")){
            try{
                String friendToDelete = request.getParameter("targetUser");
                twitter.destroyFriendship(friendToDelete);
                //System.out.println("UNFollowing user:" + friendToDelete);
                response.setRenderParameter("suri", objUri);
                response.setRenderParameter("targetUser", friendToDelete);
                response.setMode("undoFollowSent"); //show unfollow Message
            } catch (TwitterException ex) {
                log.error("Error when trying to retweet ", ex);
            }
        }else if(action.equals("setSocialTopic")){
            SocialNetwork socialNetwork = null;
            Long id = Long.parseLong(request.getParameter("id"));
            String idStatus = request.getParameter("id");            

            try {
                socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            }catch(Exception e){
                //System.out.println("Error getting the SocialNetwork " + e);
                log.error("Error getting the SocialNetwork ", e);
                return;
            }
            
            try {
                Status status = twitter.showStatus(id);
                String creatorId = status.getUser().getId() + "";
                SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
                SocialNetworkUser socialNetUser = SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(""+status.getUser().getId(), socialNetwork, model);
                
                PostIn post = PostIn.getPostInbySocialMsgId(model, status.getId()+"");
                PostIn postIn = null; //The post
                if(post == null){                                                    
                    postIn = MessageIn.ClassMgr.createMessageIn(model);
                    postIn.setSocialNetMsgId(status.getId()+"");
                    postIn.setMsg_Text(status.getText());
                    postIn.setPostInSocialNetwork(socialNetwork);
                    postIn.setPostInStream(null);
                    Calendar calendario = Calendar.getInstance();
                    postIn.setPi_created(calendario.getTime());
                    postIn.setPi_type(SWBSocialUtil.POST_TYPE_MESSAGE);

                    boolean checkKlout=false; 
                    try{
                       WebSite wsite=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName()); 
                       checkKlout=Boolean.parseBoolean(SWBSocialUtil.Util.getModelPropertyValue(wsite, "checkKlout"));
                    }catch(Exception ignored)
                    {
                       checkKlout=false;
                    }

                    if(socialNetUser != null){//User already exists
                        //System.out.println("The user already exists: " + socialNetUser.getSnu_name() + " - " + socialNetUser.getSnu_id() +"="+ status.getUser().getId());
                        int userKloutScore = 0;
                        int days=SWBSocialUtil.Util.Datediff(socialNetUser.getUpdated(), Calendar.getInstance().getTime());
                        if(days > 5 && checkKlout){  //Si ya pasaron 5 o mas días de que se actualizó la info del usuario, entonces busca su score en Klout
                            //System.out.println("YA PASARON MAS DE 5 DÍAS, BUSCAR KLOUT DE USUARIO...");
                            Kloutable socialNetKloutAble=(Kloutable) socialNetwork;
                            userKloutScore=Double.valueOf(socialNetKloutAble.getUserKlout(creatorId)).intValue(); 
                            //System.out.println("userKloutScore K TRAJO:" + userKloutScore);
                            socialNetUser.setSnu_klout(userKloutScore);
                        }

                    }else{//User does not exist, create it
                        //System.out.println("USUARIO NO EXISTE EN EL SISTEMA, REVISAR QUE KLOUT TIENE");
                        int userKloutScore = 0;
                        if(checkKlout){
                            Kloutable socialNetKloutAble=(Kloutable) socialNetwork;
                            userKloutScore = Double.valueOf(socialNetKloutAble.getUserKlout(creatorId)).intValue();
                        }
                        User twitterUser = twitter.showUser(status.getUser().getId());                    

                        socialNetUser=SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);//Create a socialNetworkUser
                        socialNetUser.setSnu_id(status.getUser().getId()+"");
                        socialNetUser.setSnu_klout(userKloutScore);
                        socialNetUser.setSnu_name("@"+status.getUser().getScreenName());
                        socialNetUser.setSnu_SocialNetworkObj(socialNetwork.getSemanticObject());
                        socialNetUser.setSnu_photoUrl(status.getUser().getBiggerProfileImageURL());
                        if(twitterUser != null){
                            socialNetUser.setCreated(twitterUser.getCreatedAt());
                            socialNetUser.setFollowers(twitterUser.getFollowersCount());
                            socialNetUser.setFriends(twitterUser.getFriendsCount());
                        }else{
                            socialNetUser.setCreated(new Date());
                            socialNetUser.setFollowers(0);
                            socialNetUser.setFriends(0);
                        }
                    }
                    //SocialNetworkUser socialNetUser=SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);//Create a socialNetworkUser

                    postIn.setPostInSocialNetworkUser(socialNetUser);
                }else{
                    postIn = post;
                }
                if(request.getParameter("newSocialTopic").equals("none"))
                {
                    postIn.setSocialTopic(null);
                }else {
                    SemanticObject semObjSocialTopic=SemanticObject.getSemanticObject(request.getParameter("newSocialTopic"));
                    if(semObjSocialTopic!=null)
                    {
                        SocialTopic socialTopic=(SocialTopic)semObjSocialTopic.createGenericInstance();
                        postIn.setSocialTopic(socialTopic);//Asigns socialTipic
                    }
                }
                response.setRenderParameter("postUri", postIn.getURI());
                //System.out.println("POST CREADO CORRECTAMENTE: " + postIn.getId() + " ** " + postIn.getSocialNetMsgId());
            }catch(Exception e){
                log.error("Error trying to setSocialTopic", e);
            }
            response.setRenderParameter("suri", socialNetwork.getURI());
            response.setRenderParameter("currentTab", request.getParameter("currentTab"));
            response.setRenderParameter("id", idStatus);            
            response.setMode("assignedPost");
        }else if(action.equals("changeSocialTopic"))
        {
            if(request.getParameter("postUri")!=null && request.getParameter("newSocialTopic")!=null){
                SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("postUri"));
                Post post=(Post)semObj.createGenericInstance();
                if(request.getParameter("newSocialTopic").equals("none")){
                    post.setSocialTopic(null);
                }else{
                    SemanticObject semObjSocialTopic=SemanticObject.getSemanticObject(request.getParameter("newSocialTopic"));
                    if(semObjSocialTopic!=null){
                        SocialTopic socialTopic=(SocialTopic)semObjSocialTopic.createGenericInstance();
                        post.setSocialTopic(socialTopic);
                    }
                }
            }
            response.setMode("reAssignedPost");
        }else if (action.equals("postMessage") || action.equals("uploadPhoto") || action.equals("uploadVideo")) {
            //System.out.println("Entra a InBox_processAction-2:"+request.getParameter("objUri"));
            if (request.getParameter("objUri") != null) {
                //System.out.println("Entra a InBox_processAction-3");
                PostIn postIn = null;
                SocialTopic socialTopic = null;
                String suri = request.getParameter("objUri");

                if(SemanticObject.getSemanticObject(suri).createGenericInstance() instanceof PostIn){//When is a response from the timeline
                    postIn = (PostIn)SemanticObject.createSemanticObject(suri).createGenericInstance();
                }else if(SemanticObject.getSemanticObject(suri).createGenericInstance() instanceof SocialTopic){//When is a tweet to some user
                    socialTopic = (SocialTopic)SemanticObject.createSemanticObject(suri).createGenericInstance();
                }
                ///
                WebSite wsite = WebSite.ClassMgr.getWebSite(request.getParameter("wsite"));
                String socialUri = "";
                int j = 0;
                Enumeration<String> enumParams = request.getParameterNames();
                while (enumParams.hasMoreElements()) {
                    String paramName = enumParams.nextElement();
                    if (paramName.startsWith("http://")) {//get param name starting with http:// -> URIs
                        if (socialUri.trim().length() > 0) {
                            socialUri += "|";
                        }
                        socialUri += paramName;
                        j++;
                    }
                }
                
                ArrayList aSocialNets = new ArrayList();//Social nets where the post will be published
                String[] socialUris = socialUri.split("\\|");  //Dividir valores
                if( j > 0 && wsite != null){
                    for (int i = 0; i < socialUris.length; i++) {
                        String tmp_socialUri = socialUris[i];
                        SemanticObject semObject = SemanticObject.createSemanticObject(tmp_socialUri, wsite.getSemanticModel());
                        SocialNetwork socialNet = (SocialNetwork) semObject.createGenericInstance();
                        //Se agrega la red social de salida al post
                        aSocialNets.add(socialNet);
                    }
                }
                                
                String toPost = request.getParameter("toPost");
                String socialFlow = request.getParameter("socialFlow");
                SocialPFlow socialPFlow = null;
                if (socialFlow != null && socialFlow.trim().length() > 0) {
                    socialPFlow = (SocialPFlow) SemanticObject.createSemanticObject(socialFlow).createGenericInstance();
                    //Revisa si el flujo de publicación soporte el tipo de postOut, de lo contrario, asinga null a spflow, para que no 
                    //asigne flujo al mensaje de salida., Esto también esta validado desde el jsp typeOfContent
                    if ((toPost.equals("msg") && !SocialLoader.getPFlowManager().isManagedByPflow(socialPFlow, Message.sclass))
                            || (toPost.equals("photo") && !SocialLoader.getPFlowManager().isManagedByPflow(socialPFlow, Photo.sclass))
                            || (toPost.equals("video") && !SocialLoader.getPFlowManager().isManagedByPflow(socialPFlow, Video.sclass))) {
                        socialPFlow = null;
                    }
                }

                //System.out.println("Entra a InBox_processAction-4");
                if(postIn != null){//When is a response from the timeline
                    SWBSocialUtil.PostOutUtil.sendNewPost(postIn, postIn.getSocialTopic(), socialPFlow, aSocialNets, wsite, toPost, request, response);
                }else if(socialTopic != null){//When is new tweet to some user
                    SWBSocialUtil.PostOutUtil.sendNewPost(null, socialTopic, socialPFlow, aSocialNets, wsite, toPost, request, response);
                }

                response.setRenderParameter("repliedTweet", "ok");
                response.setMode("tweetSent");
            }
        }
    }
    
   @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        //
        //System.out.println("\n\n+\n+\nEntering process request!");
        String mode = paramRequest.getMode();
        //System.out.println("\n\n+\n+\nModo: " + mode);
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        //System.out.println("suri in processRequest:" + objUri);
        twitter4j.Twitter twitter = twitterUsers.get(objUri);
        if(mode!= null && mode.equals("getMoreTweets")){//Gets more Tweets
            //System.out.println("brings additional TWEETS");
            doGetMoreTweets(request, response, paramRequest);
        }else if(mode!= null && mode.equals("getMoreMentions")){//Gets more Mentions
            //System.out.println("brings additional MENTIONS");
            doGetMoreMentions(request, response, paramRequest);
        }else if(mode!= null && mode.equals("getMoreFavorites")){//Gets more Favorites
            //System.out.println("brings additional FAVORITES");
            doGetMoreFavorites(request, response, paramRequest);
        }else if(mode!= null && mode.equals("getMoreDM")){//Gets more Direct Messages
            //System.out.println("brings additional DIRECT MESSAGES");
            doGetMoreDMsgs(request, response, paramRequest);
        }else if(mode!= null && mode.equals("onError")) {//Handles error
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            String action = request.getParameter("action");
            renderURL.setParameter("suri", request.getParameter("suri"));
            actionURL.setParameter("suri", request.getParameter("suri"));
            Long originalId = Long.parseLong(request.getParameter("id"));
            String currentTab = request.getParameter("currentTab");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Twitter semTwitter = (Twitter) semanticObject.createGenericInstance();
            
            try {
                Status originalStatus =  twitter.showStatus(originalId);//get the original tweet                
                org.semanticwb.model.User user = paramRequest.getUser();
                SocialUserExtAttributes socialUserExtAttr = null;
                UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
                if(user.isSigned()){
                    socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
                }
                updateStatusInformation(originalStatus, renderURL, objUri, out, socialUserExtAttr,user.hasUserGroup(userSuperAdminGrp), paramRequest);
                
                actionURL.setAction(action);
//                StringBuilder output = new StringBuilder();
                // updates only the DOM of the 'Retweet' message to change it for 'Undo retweet' and change URL also*/
                out.println("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.println("<script type=\"dojo/method\">");
                out.println("   var spanId = dijit.byId('" + semTwitter.getId() + originalId + RETWEET + currentTab +  "');");
                if(action.equals("doRT")){
                    out.println("   spanId.attr('content', '" + "<a title=\"" + paramRequest.getLocaleString("retweet") + "\" href=\"#\" class=\"retweet\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = \\'<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>\\';}catch(noe){} postSocialHtml(\\'" + actionURL.setParameter("id", originalId+"").setParameter("currentTab", currentTab) + "\\',\\'" + semTwitter.getId() + originalStatus.getId() + INFORMATION + currentTab + "\\');return false;" +"\"></a>" +"')");
                }else if(action.equals("undoRT")){
                    actionURL.setParameter("retweetId", request.getParameter("retweetId"));
                    out.println("   spanId.attr('content', '" + "<a title=\"" + paramRequest.getLocaleString("undoRetweet") + "\" href=\"#\" class=\"noretweet\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = \\'<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>\\';}catch(noe){} postSocialHtml(\\'" + actionURL.setParameter("id", originalId+"").setParameter("currentTab", currentTab) + "\\',\\'" + semTwitter.getId() + originalStatus.getId() + INFORMATION + currentTab + "\\');return false;" +"\"></a>" +"')");
                }
                out.println("   showStatus('" + paramRequest.getLocaleString("failedRequest") + "');");
                out.println("</script>");
                out.println("</span>");
//                out.println(output.toString());
            } catch (Exception ex) {
                log.error("onError ", ex);
            }
        }else if(mode!= null && mode.equals("retweetSent")){//Displays updated data of retweeted tweet
            //System.out.println("RETWEET SENT!!!");
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            renderURL.setParameter("suri", request.getParameter("suri"));
            actionURL.setParameter("suri", request.getParameter("suri"));
            Long retweetId = Long.parseLong(request.getParameter("retweetId"));
            Long originalId = Long.parseLong(request.getParameter("id"));
            String currentTab = request.getParameter("currentTab");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Twitter semTwitter = (Twitter) semanticObject.createGenericInstance();
            
            try {
                Status originalStatus =  twitter.showStatus(originalId);//get the original tweet                
                org.semanticwb.model.User user = paramRequest.getUser();
                SocialUserExtAttributes socialUserExtAttr = null;
                if(user.isSigned()){
                    socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
                }
                UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
                updateStatusInformation(originalStatus, renderURL, objUri, out, socialUserExtAttr, user.hasUserGroup(userSuperAdminGrp), paramRequest);
                
                actionURL.setAction("undoRT");
                /* updates only the DOM of the 'Retweet' message to change it for 'Undo retweet' and change URL also*/
                out.println("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.println("<script type=\"dojo/method\">");
                out.println("   var spanId = dijit.byId('" + semTwitter.getId() + originalId + RETWEET + currentTab +  "');");
                out.println("   spanId.attr('content', '" + "<a href=\"#\" title=\"" + paramRequest.getLocaleString("retweet") + "\" class=\"noretweet\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = \\'<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>\\';}catch(noe){} postSocialHtml(\\'" + actionURL.setParameter("retweetId", retweetId+"").setParameter("id", originalId+"").setParameter("currentTab", currentTab) + "\\',\\'" + semTwitter.getId() + originalStatus.getId() + INFORMATION + currentTab + "\\');return false;" +"\"></a>" +"')");
                out.println("   showStatus('" + paramRequest.getLocaleString("retweetSent") +"');");
                out.println("</script>");
                out.println("</span>");
            } catch (Exception ex) {
                log.error("Error when trying to retweet ", ex);
            }
        }else if(mode!= null && mode.equals("unretweetSent")){//Displays updated data of retweeted tweet
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            renderURL.setParameter("suri", objUri);
            actionURL.setParameter("suri", objUri);
            Long originalId = Long.parseLong(request.getParameter("id"));
            String currentTab = request.getParameter("currentTab");
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Twitter semTwitter = (Twitter) semanticObject.createGenericInstance();
            
            try {
                Status originalStatus =  twitter.showStatus(originalId);//get the original tweet
                org.semanticwb.model.User user = paramRequest.getUser();
                SocialUserExtAttributes socialUserExtAttr = null;
                if(user.isSigned()){
                    socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
                }
                UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
                updateStatusInformation(originalStatus, renderURL, objUri, out, socialUserExtAttr, user.hasUserGroup(userSuperAdminGrp), paramRequest);
                
                actionURL.setAction("doRT");
                /* updates only the DOM of the 'Undo Retweet' message to change it for 'Retweet' and change URL also*/
                out.println("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.println("<script type=\"dojo/method\">");
                out.println("   var spanId = dijit.byId('" + semTwitter.getId() + originalId + RETWEET + currentTab + "');");
                out.println("   spanId.attr('content', '" + "<a href=\"#\" class=\"retweet\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = \\'<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>\\';}catch(noe){} postSocialHtml(\\'" + actionURL.setParameter("id", originalId+"").setParameter("currentTab", currentTab) + "\\',\\'" + semTwitter.getId() + originalId + INFORMATION + currentTab + "\\');return false;" +"\"><span>" + paramRequest.getLocaleString("retweet") + "</a></span>" +"')");
                out.println("   showStatus('" + paramRequest.getLocaleString("unretweetSent") + "');");
                out.println("</script>");
                out.println("</span>");
                                
            } catch (Exception ex) {
                log.error("Error when trying to undo retweet ", ex);
            }
        }else if(mode!= null && mode.equals("tweetSent")){//Hides dialog used to create tweet and shows status
            if(request.getParameter("repliedTweet") != null && request.getParameter("repliedTweet").equals("ok")){
                out.println("<script type=\"text/javascript\">");
                out.println("   hideDialog();");
                out.println("   showStatus('Tweet sent');");
                out.println("</script>");
            }
        }else if(mode!= null && mode.equals("replyTweet")){//Displays dialog to create tweet
            //Create first the post BEGIN
            SocialNetwork socialNetwork = null;
            Long id = Long.parseLong(request.getParameter("id"));
            PostIn postIn = null; //The post
            try {
                socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            }catch(Exception e){
                //System.out.println("Error getting the SocialNetwork " + e);
                log.error("Error getting the SocialNetwork ", e);
                return;
            }
            
            try {
                Status status = twitter.showStatus(id);
                String creatorId = status.getUser().getId() + "";
                SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
                SocialNetworkUser socialNetUser = SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(""+status.getUser().getId(), socialNetwork, model);
                
                postIn = PostIn.getPostInbySocialMsgId(model, status.getId()+"");
                if(postIn == null){                    
                    postIn = MessageIn.ClassMgr.createMessageIn(model);
                    postIn.setSocialNetMsgId(status.getId()+"");
                    postIn.setMsg_Text(status.getText());
                    postIn.setPostInSocialNetwork(socialNetwork);
                    postIn.setPostInStream(null);
                    Calendar calendario = Calendar.getInstance();
                    postIn.setPi_created(calendario.getTime());
                    postIn.setPi_type(SWBSocialUtil.POST_TYPE_MESSAGE);                


                    if(socialNetUser != null){//User already exists
                        //System.out.println("The user already exists: " + socialNetUser.getSnu_name() + " - " + socialNetUser.getSnu_id() +"="+ status.getUser().getId());
                        int userKloutScore = 0;
                        int days=SWBSocialUtil.Util.Datediff(socialNetUser.getUpdated(), Calendar.getInstance().getTime());
                        if(days > 5){  //Si ya pasaron 5 o mas días de que se actualizó la info del usuario, entonces busca su score en Klout
                            //System.out.println("YA PASARON MAS DE 5 DÍAS, BUSCAR KLOUT DE USUARIO...");
                            Kloutable socialNetKloutAble=(Kloutable) socialNetwork;
                            userKloutScore=Double.valueOf(socialNetKloutAble.getUserKlout(creatorId)).intValue(); 
                            //System.out.println("userKloutScore K TRAJO:" + userKloutScore);
                            socialNetUser.setSnu_klout(userKloutScore);
                        }

                    }else{//User does not exist, create it
                        //System.out.println("USUARIO NO EXISTE EN EL SISTEMA, REVISAR QUE KLOUT TIENE");
                        int userKloutScore = 0;

                        Kloutable socialNetKloutAble=(Kloutable) socialNetwork;
                        userKloutScore = Double.valueOf(socialNetKloutAble.getUserKlout(creatorId)).intValue();
                        User twitterUser = twitter.showUser(status.getUser().getId());                    

                        socialNetUser=SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);//Create a socialNetworkUser
                        socialNetUser.setSnu_id(status.getUser().getId()+"");
                        socialNetUser.setSnu_klout(userKloutScore);
                        //System.out.println("setting user name = " + status.getUser().getScreenName());
                        socialNetUser.setSnu_name("@"+status.getUser().getScreenName());
                        socialNetUser.setSnu_SocialNetworkObj(socialNetwork.getSemanticObject());
                        socialNetUser.setSnu_photoUrl(status.getUser().getBiggerProfileImageURL());
                        if(twitterUser != null){
                            socialNetUser.setCreated(twitterUser.getCreatedAt());
                            socialNetUser.setFollowers(twitterUser.getFollowersCount());
                            socialNetUser.setFriends(twitterUser.getFriendsCount());
                        }else{
                            socialNetUser.setCreated(new Date());
                            socialNetUser.setFollowers(0);
                            socialNetUser.setFriends(0);
                        }
                    }
                    //SocialNetworkUser socialNetUser=SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);//Create a socialNetworkUser

                    postIn.setPostInSocialNetworkUser(socialNetUser);

                    SocialTopic defaultSocialTopic = SocialTopic.ClassMgr.getSocialTopic("DefaultTopic", model);
                    if(defaultSocialTopic != null){
                        postIn.setSocialTopic(defaultSocialTopic);//Asigns socialTipic
                        //System.out.println("Setting social topic:" + defaultSocialTopic);
                    }else{
                        postIn.setSocialTopic(null);
                        //System.out.println("Setting to null");
                    }
                      
                    //System.out.println("Post created ok: " + postIn.getId() + " ** " + postIn.getSocialNetMsgId());
                }else{
                    System.out.println("Creating another response");
                }
            }catch(Exception e){
                log.error("Error trying to setSocialTopic ", e);
            }
            //Create first the post END
            response.setContentType("text/html; charset=ISO-8859-1");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/postInResponse.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(path);
            if (dis != null) {
                try {
                    request.setAttribute("postUri", SemanticObject.createSemanticObject(postIn.getURI()));
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }else if(mode!= null && (mode.equals("replyDM") || mode.equals("createNewDM"))){//Displays dialog to create tweet
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            actionURL.setParameter("userId", request.getParameter("userId"));
            actionURL.setParameter("suri", request.getParameter("suri"));

            out.println("<form type=\"dijit.form.Form\" id=\"createDM\" action=\"" +  actionURL.setAction("sendDM") + "\" method=\"post\" onsubmit=\"submitForm('createDM'); try{document.getElementById('csLoading').style.display='inline';}catch(noe){}; return false;\">");            
            out.println("<fieldset>");
            out.println("<table>");
            out.println("<tr>"); 
            out.println("   <td>");
            out.println("       <textarea type=\"dijit.form.Textarea\" name=\"dmText\" id=\"dmText\" rows=\"4\" cols=\"50\"></textarea>");
            out.println("   </td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("       <td style=\"text-align: center;\"><button dojoType=\"dijit.form.Button\" type=\"submit\">Send</button></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("<span id=\"csLoading\" style=\"width: 100px; display: none\" align=\"center\">&nbsp;&nbsp;&nbsp;<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\"/></span>");
        }else if(mode.equals("doGetHomeStream")){//Displays new home statuses obtained with the stream
            //System.out.println("ProcessRequest: doGetHomeStream-> RenderURL:" + paramRequest.getRenderUrl() + " actionURL:" + paramRequest.getActionUrl());
            HttpSession session = request.getSession(true);
            if(session.getAttribute(objUri + "tweetsListener")!=null){
                SocialUserStreamListener tweetsListener = (SocialUserStreamListener)session.getAttribute(objUri + "tweetsListener");
                if(tweetsListener.getHomeStatusSize() > 0){
                    tweetsListener.setProcessing(true);
                    int i;
                    org.semanticwb.model.User user = paramRequest.getUser();
                    SocialUserExtAttributes socialUserExtAttr = null;
                    if(user.isSigned()){
                        socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
                    }
                    //Probablemente es innecesario dado que al ser mensajes nuevos, no han sido clasificados.
                    SocialNetwork  socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
                    SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
                    String postURI = null;
                    UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
                    for(i = tweetsListener.getHomeStatusSize() - 1 ; i >= 0 ; i-- ){//Most recent status first                        
                         try{
                            postURI = null;
                            PostIn post = PostIn.getPostInbySocialMsgId(model, tweetsListener.getHomeStatus(i).getId()+"");
                            if(post != null){
                                postURI = post.getURI();
                            }                                                               
                             //--doPrintTweet(request, response, paramRequest, tweetsListener.getHomeStatus(i), twitter, response.getWriter(), recoverConversations(tweetsListener.getHomeStatus(i).getId(), twitter));
                             doPrintTweet(request, response, paramRequest, tweetsListener.getHomeStatus(i), twitter, out, null,HOME_TAB, postURI, socialUserExtAttr, user.hasUserGroup(userSuperAdminGrp));
                         }catch(Exception te){
                            log.error("Error when printing tweet:" , te);
                         }
                    }
                   
                    //As user has requested the tweets in ArrayList
                    //Restart the timer to keep thread alive
                    tweetsListener.setStartTime(System.currentTimeMillis());
                    tweetsListener.clearHomeStatus();
                    tweetsListener.setProcessing(false);
                }
            }
        }else if(mode.equals("doGetFavoritesStream")){//Displays new favorites obtained with the stream
            HttpSession session = request.getSession(true);
            if(session.getAttribute(objUri + "tweetsListener")!=null){
                SocialUserStreamListener tweetsListener = (SocialUserStreamListener)session.getAttribute(objUri + "tweetsListener");
                if(tweetsListener.getFavoritesSize() > 0){
                   tweetsListener.setProcessing(true);
                   int i;
                   org.semanticwb.model.User user = paramRequest.getUser();
                   SocialUserExtAttributes socialUserExtAttr = null;
                   if(user.isSigned()){
                       socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
                   }
                   SocialNetwork  socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
                   SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
                   String postURI = null;
                   UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
                   for(i = tweetsListener.getFavoritesSize() - 1 ; i >= 0 ; i-- ){//Most recent status first
                       try{
                           postURI = null;
                           PostIn post = PostIn.getPostInbySocialMsgId(model, tweetsListener.getFavoritesStatus(i).getId()+"");
                           if(post != null){
                               postURI = post.getURI();
                           }
                           //--doPrintTweet(request, response, paramRequest, tweetsListener.getFavoritesStatus(i), twitter, response.getWriter(), recoverConversations(tweetsListener.getFavoritesStatus(i).getId(), twitter));
                           doPrintTweet(request, response, paramRequest, tweetsListener.getFavoritesStatus(i), twitter, response.getWriter(), null, FAVORITES_TAB, postURI, socialUserExtAttr,user.hasUserGroup(userSuperAdminGrp));
                       }catch(Exception te){
                           log.error("Error when printing favorite:" , te);
                       }
                   }
                   //As user has requested the tweets in ArrayList
                   //Restart the timer to keep thread alive
                   tweetsListener.setStartTime(System.currentTimeMillis());
                   tweetsListener.clearFavoritesStatus();
                   tweetsListener.setProcessing(false);
                }
            }
        }else if(mode.equals("doGetMentionsStream")){//Displays new mentions obtained with the stream
            HttpSession session = request.getSession(true);
            if(session.getAttribute(objUri + "tweetsListener")!=null){
                SocialUserStreamListener tweetsListener = (SocialUserStreamListener)session.getAttribute(objUri + "tweetsListener");
                if(tweetsListener.getMentionsSize() > 0){
                   tweetsListener.setProcessing(true);
                   int i;
                   org.semanticwb.model.User user = paramRequest.getUser();
                   SocialUserExtAttributes socialUserExtAttr = null;
                   if(user.isSigned()){
                       socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
                   }
                   
                   SocialNetwork  socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
                   SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
                   String postURI = null; //When the post has already been saved like a postIn or a postOut
                   UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");

                   for(i = tweetsListener.getMentionsSize() - 1 ; i >= 0 ; i-- ){//Most recent status first
                       try{
                           postURI = null;
                           PostIn post = PostIn.getPostInbySocialMsgId(model, tweetsListener.getMentionsStatus(i).getId()+"");
                           if(post != null){
                               postURI = post.getURI();
                           }
                           //--doPrintTweet(request, response, paramRequest, tweetsListener.getMentionsStatus(i), twitter, response.getWriter(), recoverConversations(tweetsListener.getMentionsStatus(i).getId(), twitter));
                           doPrintTweet(request, response, paramRequest, tweetsListener.getMentionsStatus(i), twitter, response.getWriter(), null,MENTIONS_TAB, postURI, socialUserExtAttr,user.hasUserGroup(userSuperAdminGrp));
                       }catch(Exception te){
                           log.error("Error when printing tweet:" , te);
                       }
                   }                   
                   //As user has requested the tweets in ArrayList
                   //Restart the timer to keep thread alive
                   tweetsListener.setStartTime(System.currentTimeMillis());
                   tweetsListener.clearMentionsStatus();
                   tweetsListener.setProcessing(false);
                }
            }
        }else if(mode.equals("doGetDMsStream")){//Displays new direct messages obtained with the stream
            HttpSession session = request.getSession(true);
            if(session.getAttribute(objUri + "tweetsListener")!=null){
                SocialUserStreamListener tweetsListener = (SocialUserStreamListener)session.getAttribute(objUri + "tweetsListener");
                if(tweetsListener.getDirectMSize() > 0){
                   tweetsListener.setProcessing(true);
                   int i;
                   for(i = tweetsListener.getDirectMSize() - 1 ; i >= 0 ; i-- ){//Most recent status first
                       try{
                           doPrintDM(request, response, paramRequest, tweetsListener.getDirectMStatus(i),twitter.getId() , out);
                       }catch(Exception te){
                           log.error("Error when printing tweet:" , te);
                       }
                   }                   
                   //As user has requested the tweets in ArrayList
                   //Restart the timer to keep thread alive
                   tweetsListener.setStartTime(System.currentTimeMillis());
                   tweetsListener.clearDirectMStatus();
                   tweetsListener.setProcessing(false);
                }
            }
        }else if(mode.equals("newTweets")){//Displays a status message when new tweets received from stream
            HttpSession session = request.getSession(true);
            if(session.getAttribute(objUri +"tweetsListener") != null){
                SocialUserStreamListener tweetsListener = (SocialUserStreamListener)session.getAttribute(objUri + "tweetsListener");
                int noOfNewTweets = tweetsListener.getHomeStatusSize();
                SWBResourceURL renderURL = paramRequest.getRenderUrl();
                if(noOfNewTweets > 0 && tweetsListener.isProcessing() == false){
                    out.println("<a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("doGetHomeStream").setParameter("suri", objUri) + "','" + objUri +"/stream','top'); try{dojo.byId(this.parentNode.id).innerHTML = '';}catch(noe){}; return false;\">" + paramRequest.getLocaleString("youHave") + " <b>" + noOfNewTweets +  "</b> " + (noOfNewTweets > 1 ? paramRequest.getLocaleString("newTweets") : paramRequest.getLocaleString("newTweet")) +  "</a>");
                }
                if(tweetsListener.getFavoritesSize() > 0){
                    //System.out.println("hay nuevos favoritos:" + tweetsListener.getFavoritesSize() );
                    out.println("<script type=\"text/javascript\">");
                    /*out.println("   var tabId = '" + objUri + FAVORITES_TAB + "';");
                    out.println("   var pane = dijit.byId(tabId);");                   
                    out.println("   try{");
                    out.println("       var aux='" + paramRequest.getLocaleString("favoritesLabel") + " (" + tweetsListener.getFavoritesSize() + ")';");
                    out.println("       pane.title = aux;");
                    out.println("       pane.controlButton.containerNode.innerHTML = aux;");
                    out.println("   }catch(noe){");
                    out.println("       alert('Error setting title: ' + noe);");
                    out.println("   }");*/
                   
                    out.println("   var favs = '" + objUri + "/newFavoritesAvailable';");
                    out.println("   var hrefVal='<a href=\"#\" onclick=\"appendHtmlAt(\\'" + renderURL.setMode("doGetFavoritesStream").setParameter("suri", objUri)
                            + "\\',\\'" + objUri +"/favoritesStream\\',\\'top\\'); try{dojo.byId(this.parentNode.id).innerHTML = \\'\\';}catch(noe){}; resetTabTitle(\\'" + objUri + "\\', \\'" + FAVORITES_TAB +  "\\', \\'" + paramRequest.getLocaleString("favoritesLabel") + "\\'); return false;\">" + paramRequest.getLocaleString("youHave") + " <b>"
                            + tweetsListener.getFavoritesSize() +  "</b> " + (tweetsListener.getFavoritesSize() > 1 ? paramRequest.getLocaleString("newFavorites") : paramRequest.getLocaleString("newFavorite")) +  "</a>';");
                    out.println("   try{");
                    out.println("      document.getElementById(favs).innerHTML = hrefVal;");
                    out.println("   }catch(noe){}");
                    out.println("</script>");
                }
                
                if(tweetsListener.getMentionsSize() >0 ){
                    //System.out.println("hay nuevas menciones:" + tweetsListener.getMentionsSize() );
                    out.println("<script type=\"text/javascript\">");
                    /*out.println("   var tabId = '" +objUri + MENTIONS_TAB + "';");
                    out.println("   var pane = dijit.byId(tabId);");                   
                    out.println("   try{");
                    out.println("       var aux='" + paramRequest.getLocaleString("mentionsLabel") + " (" + tweetsListener.getMentionsSize() + ")';");
                    out.println("       pane.title = aux;");
                    out.println("       pane.controlButton.containerNode.innerHTML = aux;");
                    out.println("   }catch(noe){");
                    out.println("       alert('Error setting title: ' + noe);");
                    out.println("   }");*/
                   
                    out.println("   var favs = '" + objUri + "/newMentionsAvailable';");
                    out.println("   var hrefVal='<a href=\"#\" onclick=\"appendHtmlAt(\\'" + renderURL.setMode("doGetMentionsStream").setParameter("suri", objUri) + "\\',\\'" + objUri +"/mentionsStream\\',\\'top\\'); try{dojo.byId(this.parentNode.id).innerHTML = \\'\\';}catch(noe){}; resetTabTitle(\\'" + objUri + "\\', \\'" + MENTIONS_TAB +"\\', \\'" + paramRequest.getLocaleString("mentionsLabel") + "\\'); return false;\">" + paramRequest.getLocaleString("youHave") + " <b>" + tweetsListener.getMentionsSize() +  "</b> " + (tweetsListener.getMentionsSize() > 1 ? paramRequest.getLocaleString("newMentions") : paramRequest.getLocaleString("newMention")) +  "</a>';");
                    out.println("   try{");
                    out.println("      document.getElementById(favs).innerHTML = hrefVal;");
                    out.println("   }catch(noe){}");
                    out.println("</script>");
                }
                
                if(tweetsListener.getDirectMSize() >0 ){
                    //System.out.println("hay nuevos direct msgs:" + tweetsListener.getDirectMSize() );
                    out.println("<script type=\"text/javascript\">");
                    /*out.println("   var tabId = '" +objUri + DIRECT_MESSAGES_TAB +"';");
                    out.println("   var pane = dijit.byId(tabId);");                   
                    out.println("   try{");
                    out.println("       var aux='" + paramRequest.getLocaleString("directMLabel") + " (" + tweetsListener.getDirectMSize() + ")';");
                    out.println("       pane.title = aux;");
                    out.println("       pane.controlButton.containerNode.innerHTML = aux;");
                    out.println("   }catch(noe){");
                    out.println("       alert('Error setting title: ' + noe);");
                    out.println("   }");*/
                   
                    out.println("   var favs = '" + objUri + "/newDirectMessagesAvailable';");
                    out.println("   var hrefVal='<a href=\"#\" onclick=\"appendHtmlAt(\\'" + renderURL.setMode("doGetDMsStream").setParameter("suri", objUri) + "\\',\\'" + objUri +"/directMessagesStream\\',\\'top\\'); try{dojo.byId(this.parentNode.id).innerHTML = \\'\\';}catch(noe){}; resetTabTitle(\\'" + objUri + "\\', \\'" + DIRECT_MESSAGES_TAB + "\\', \\'" + paramRequest.getLocaleString("directMLabel") + "\\'); return false;\">" + paramRequest.getLocaleString("youHave") + " <b>" + tweetsListener.getDirectMSize() +  "</b> " + (tweetsListener.getDirectMSize() > 1 ? paramRequest.getLocaleString("newDMs") : paramRequest.getLocaleString("newDM")) +  "</a>';");
                    out.println("   try{");
                    out.println("      document.getElementById(favs).innerHTML = hrefVal;");
                    out.println("   }catch(noe){}");
                    out.println("</script>");
                }
                
            }
        }else if(mode.equals("stopListener")){//Stops the Listener inmediatly!
            HttpSession session = request.getSession(true);
            if(session.getAttribute(objUri + "tweetsListener")!=null){
                SocialUserStreamListener tweetsListener = (SocialUserStreamListener)session.getAttribute(objUri +"tweetsListener");
                System.out.println("Stopping listener for this timeline:");
                System.out.println(tweetsListener.stopListener());
                twitterUsers.remove(objUri);
                if(session.getAttribute(objUri + "pooling") != null){
                    session.removeAttribute(objUri + "pooling");
                }
                if(session.getAttribute(objUri + "tweetsListener") != null){
                    session.removeAttribute(objUri + "tweetsListener");
                }
                if(session.getAttribute(objUri + "twitterStream") != null){
                    session.removeAttribute(objUri + "twitterStream");
                }
                
            }
        }else if(mode!= null && mode.equals("favoriteSent")){//Displays updated data of favorited tweet
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            actionURL.setParameter("suri", objUri);
            Long id = Long.parseLong(request.getParameter("id"));
            String currentTab = request.getParameter("currentTab");
            //System.out.println("Inside favorite sent: " + id);
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Twitter semTwitter = (Twitter) semanticObject.createGenericInstance();
            
            try {
                Status originalStatus = twitter.showStatus(id);
                org.semanticwb.model.User user = paramRequest.getUser();
                SocialUserExtAttributes socialUserExtAttr = null;
                if(user.isSigned()){
                    socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
                }
                UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
                updateStatusInformation(originalStatus, renderURL, objUri, out, socialUserExtAttr, user.hasUserGroup(userSuperAdminGrp) , paramRequest);
                out.println("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.println("<script type=\"dojo/method\">");
                out.println("   try{");
                out.println("   var spanId = dijit.byId('" + semTwitter.getId() +  id + FAVORITE + currentTab +  "');");
                out.println("   spanId.attr('content', '" + checkIfFavorite(originalStatus, actionURL, out, currentTab, semTwitter, paramRequest).replace("'", "\\'") +"');");
                out.println("   showStatus('" + paramRequest.getLocaleString("favoriteSent") +"');");
                out.println("   }catch(noe){alert('Error:' + noe);}");
                out.println("</script>");
                out.println("</span>");
            } catch (TwitterException ex) {
                log.error("Error when trying to show message of Favorite sent ", ex);
            }
        }else if(mode!= null && mode.equals("showError")){//Displays updated data and shows error
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            Long id = Long.parseLong(request.getParameter("id"));
            //System.out.println("Error processing action!!!");
            
            try {
                Status originalStatus = twitter.showStatus(id);
                org.semanticwb.model.User user = paramRequest.getUser();
                SocialUserExtAttributes socialUserExtAttr = null;
                if(user.isSigned()){
                    socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
                }
                UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
                updateStatusInformation(originalStatus, renderURL, objUri, out, socialUserExtAttr, user.hasUserGroup(userSuperAdminGrp), paramRequest);
                out.println("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                out.println("<script type=\"dojo/method\">");
                out.println("   showError('No fue posible procesar la solicitud');");
                out.println("</script>");
                out.println("</span>");
            } catch (TwitterException ex) {
                log.error("Error when trying to show message of Favorite sent ", ex);
            }
        }else if(mode!= null && mode.equals("showDMError")){//Displays updated data and shows error            
            //System.out.println("Error processing action!!!");
            
            out.println("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
            out.println("<script type=\"dojo/method\">");
            out.println("   hideDialog();");
            out.println("   showError('No fue posible procesar la solicitud');");
            out.println("</script>");
            out.println("</span>");
            
        }else if(mode.equals("showUserProfile")){
            RequestDispatcher dis = request.getRequestDispatcher(SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/socialNetworks/twitterUserProfile.jsp");
            try {
                request.setAttribute("paramRequest", paramRequest);
                request.setAttribute("twitterBean", twitter);            
                dis.include(request, response);
            } catch (Exception e) {
                log.error("Error in processRequest() for requestDispatcher" , e);
            }
        }else if(mode.equals("followSent")){
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            String targetUser = request.getParameter("targetUser");
            actionURL.setParameter("targetUser", targetUser);
            actionURL.setParameter("suri", request.getParameter("suri"));
            actionURL.setAction("undoFollow");
            out.println("<b><a class=\"clasifica\" href=\"#\" onclick=\"try{dojo.byId(this.parentNode.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + actionURL + "','" + targetUser + "/relStat'); return false;\">" + paramRequest.getLocaleString("unfollow") + "</a></b>");
        }else if(mode.equals("undoFollowSent")){
            SWBResourceURL actionURL = paramRequest.getActionUrl();
            String targetUser = request.getParameter("targetUser");
            actionURL.setParameter("targetUser", targetUser);
            actionURL.setParameter("suri", request.getParameter("suri"));
            actionURL.setAction("doFollow");
            out.println("<b><a class=\"clasifica\" href=\"#\" onclick=\"try{dojo.byId(this.parentNode.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + actionURL + "','" + targetUser + "/relStat'); return false;\">" + paramRequest.getLocaleString("follow") + "</a></b>");
        }else if(mode.equals("doShowTopic")){
            final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/assignTopic.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(path);
            if (dis != null) {
                try {
                    request.setAttribute("suri", objUri);
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error("Error on doShowTopic: " + e);
                }
            }
        }else if(mode.equals("doReclassifyTopic")){
            final String path = SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/socialTopic/classifybyTopic.jsp";
            RequestDispatcher dis = request.getRequestDispatcher(path);
            if (dis != null) {
                try {
                    SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("postUri"));
                    request.setAttribute("postUri", semObject);
                    request.setAttribute("paramRequest", paramRequest);
                    dis.include(request, response);
                } catch (Exception e) {
                    log.error("Error on doReclassifyTopic: " + e);
                }
            }
        }else if(mode.equals("assignedPost")){
            String id = request.getParameter("id");
            String currentTab = request.getParameter("currentTab");
            String postUri = request.getParameter("postUri");
            String suri = request.getParameter("suri");
            SWBResourceURL renderURL = paramRequest.getRenderUrl();
            SWBResourceURL clasifybyTopic = renderURL.setMode("doReclassifyTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", id).setParameter("postUri", postUri).setParameter("currentTab", currentTab).setParameter("suri",suri);
            
            SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
            Twitter semTwitter = (Twitter) semanticObject.createGenericInstance();
            
            String url= "<a href=\"#\" class=\"clasifica\" title=\"" + paramRequest.getLocaleString("reclassify") + "\" onclick=\"showDialog('" + clasifybyTopic + "','" + paramRequest.getLocaleString("reclassify") + " tweet'); return false;\"></a>";
            out.println("<span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
            out.println("<script type=\"dojo/method\">");
            out.println("   hideDialog(); ");            
            out.println("   try{");
            out.println("   var spanId = dijit.byId('" + semTwitter.getId() +  id + TOPIC + currentTab +  "');");            
            out.println("   spanId.attr('content', '" + url.replace("'", "\\'") +"');");           
            out.println("   }catch(noe){alert('Error:' + noe);}");
            out.println("   showStatus('Tema asociado correctamente');");
            out.println("</script>");
            out.println("</span>");
            //response.setRenderParameter("currentTab", request.getParameter("currentTab"));
            //response.setRenderParameter("id", idStatus);
        }else if(mode.equals("reAssignedPost")){
            out.println("<script type=\"javascript\">");
            out.println("   hideDialog(); ");
            out.println("   showStatus('El tema fue cambiado correctamente');");
            out.println("</script>");
        }else if(mode.equals("storeInterval")){//Storing the interval for the current uri
            //System.out.println("suri:" + objUri + "---interval:" + request.getParameter("interval"));
            if(request.getParameter("interval") != null){
                HttpSession session = request.getSession(true);
                session.setAttribute(objUri + "pooling", request.getParameter("interval"));
            }
        }else if (paramRequest.getMode().equals("post")) {
            doCreatePost(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("getMoreFriends")) {
            doGetMoreFriends(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("getMoreFollowers")) {
            doGetMoreFollowers(request, response, paramRequest);
        }else if (paramRequest.getMode().equals("createTweet")){
            response.setContentType("text/html; charset=ISO-8859-1");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            String jspResponse = SWBPlatform.getContextPath() +"/work/models/" + paramRequest.getWebPage().getWebSiteId() +"/jsp/post/createNewPostToUser.jsp";           
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            try {
                request.setAttribute("paramRequest", paramRequest);
                dis.include(request, response);
            } catch (Exception e) {
                log.error(e);
            }            
        }else{
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doGetMoreTweets(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");        
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        twitter4j.Twitter twitter = twitterUsers.get(objUri);
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        
        Long maxTweetID;
        try{
            maxTweetID = Long.parseLong(request.getParameter("maxTweetID"));
        }catch(NumberFormatException nfe){
            maxTweetID =0L;
        }
        
        try {
            //System.out.println("Get the next 25 tweets!!");
            Paging paging = new Paging(); //used to set maxId, count
            paging.count(20);
            if(maxTweetID >0L){
                paging.setMaxId(maxTweetID-1);
            }
            int i = 0;
            org.semanticwb.model.User user = paramRequest.getUser();
            SocialUserExtAttributes socialUserExtAttr = null;
            if(user.isSigned()){
                socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
            }
            SocialNetwork  socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
            String postURI = null;
            UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
            for (Status status : twitter.getHomeTimeline(paging)) {
                maxTweetID = status.getId();
                postURI = null;
                PostIn post = PostIn.getPostInbySocialMsgId(model, maxTweetID+"");
                if(post != null){
                    postURI = post.getURI();
                }                                

                //--doPrintTweet(request, response, paramRequest, status, twitter,response.getWriter(),recoverConversations(maxTweetID, twitter));
                doPrintTweet(request, response, paramRequest, status, twitter,response.getWriter(),null, HOME_TAB, postURI, socialUserExtAttr,user.hasUserGroup(userSuperAdminGrp));
                i++;
            }

            out.println("<div align=\"center\">");
            out.println("<label id=\"" + objUri + "/moreTwitLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreTweets").setParameter("maxTweetID", maxTweetID+"") + "','" + objUri + "/getMoreTweets','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">" + paramRequest.getLocaleString("moreTweets") + "</a></label>");
            out.println("</div>");
            //System.out.println("Total tweets:" + i);
        } catch (Exception te) {
            out.println("<div align=\"center\">");
            out.println("<label id=\"" + objUri + "/moreTwitLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreTweets").setParameter("maxTweetID", maxTweetID+"") + "','" + objUri + "/getMoreTweets','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">" + paramRequest.getLocaleString("moreTweets") + "</a></label>");
            out.println("</div>");
            log.error("Error when getting timeline: ", te);
        }
        //System.out.println("Has finised printing tweets!");
    }
    
    public void doGetMoreMentions(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        twitter4j.Twitter twitter = twitterUsers.get(objUri);
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        
        Long maxTweetID;
        try{
            maxTweetID = Long.parseLong(request.getParameter("maxTweetID"));
        }catch(NumberFormatException nfe){
            maxTweetID =0L;
        }
        
        try {
            //System.out.println("Get the next 25 tweets!!");
            Paging paging = new Paging(); //used to set maxId, count
            paging.count(20);
            if(maxTweetID >0L){
                paging.setMaxId(maxTweetID-1);
            }
            int i = 0;
            org.semanticwb.model.User user = paramRequest.getUser();
            SocialUserExtAttributes socialUserExtAttr = null;
            if(user.isSigned()){
                socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
            }
            SocialNetwork  socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
            String postURI = null;
            UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
            for (Status status : twitter.getMentionsTimeline(paging)) {
                maxTweetID = status.getId();
                postURI = null;
                PostIn post = PostIn.getPostInbySocialMsgId(model, maxTweetID+"");
                if(post != null){
                    postURI = post.getURI();
                }

                //--doPrintTweet(request, response, paramRequest, status, twitter,response.getWriter(),recoverConversations(maxTweetID, twitter));
                doPrintTweet(request, response, paramRequest, status, twitter,response.getWriter(),null,MENTIONS_TAB, postURI, socialUserExtAttr,user.hasUserGroup(userSuperAdminGrp));
                i++;
            }

            out.println("<div align=\"center\">");
            out.println("<label id=\"" + objUri + "/moreMentionLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreMentions").setParameter("maxTweetID", maxTweetID+"") + "','" + objUri + "/getMoreMentions','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">" + paramRequest.getLocaleString("moreMentions") +"</a></label>");
            out.println("</div>");
            //System.out.println("Total mentions:" + i);
        } catch (Exception te) {
            out.println("<div align=\"center\">");
            out.println("<label id=\"" + objUri + "/moreMentionLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreMentions").setParameter("maxTweetID", maxTweetID+"") + "','" + objUri + "/getMoreMentions','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">" + paramRequest.getLocaleString("moreMentions") +"</a></label>");
            out.println("</div>");
            log.error("Error when getting mentions timeline: ", te);
        }
    }
    
    public void doGetMoreFavorites(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        twitter4j.Twitter twitter = twitterUsers.get(objUri);
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        
        Long maxTweetID;
        try{
            maxTweetID = Long.parseLong(request.getParameter("maxTweetID"));
        }catch(NumberFormatException nfe){
            maxTweetID =0L;
        }
        
        try {
            //System.out.println("Get the next 25 favorites!!");
            Paging paging = new Paging(); //used to set maxId, count
            paging.count(20);
            if(maxTweetID >0L){
                paging.setMaxId(maxTweetID-1);
            }
            int i = 0;
            org.semanticwb.model.User user = paramRequest.getUser();
            SocialUserExtAttributes socialUserExtAttr = null;
            if(user.isSigned()){
                socialUserExtAttr = SocialUserExtAttributes.ClassMgr.getSocialUserExtAttributes(user.getId(), SWBContext.getAdminWebSite());
            }
            SocialNetwork  socialNetwork = (SocialNetwork)SemanticObject.getSemanticObject(objUri).getGenericInstance();
            SWBModel model=WebSite.ClassMgr.getWebSite(socialNetwork.getSemanticObject().getModel().getName());
            String postURI = null;
            UserGroup userSuperAdminGrp=SWBContext.getAdminWebSite().getUserRepository().getUserGroup("su");
            for (Status status : twitter.getFavorites(paging)) {
                maxTweetID = status.getId();
                postURI = null;
                PostIn post = PostIn.getPostInbySocialMsgId(model, maxTweetID+"");
                if(post != null){
                    postURI = post.getURI();
                }
                //--doPrintTweet(request, response, paramRequest, status, twitter,response.getWriter(),recoverConversations(maxTweetID, twitter));
                doPrintTweet(request, response, paramRequest, status, twitter,response.getWriter(), null, FAVORITES_TAB, postURI, socialUserExtAttr,user.hasUserGroup(userSuperAdminGrp));
                i++;
            }
            out.println("<div align=\"center\">");
            out.println("<label id=\"" + objUri + "/moreFavoritesLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreFavorites").setParameter("maxTweetID", maxTweetID+"") + "','" + objUri + "/getMoreFavorites','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">" + paramRequest.getLocaleString("moreFavorites") + "</a></label>");
            out.println("</div>");
            //System.out.println("Total favorites:" + i);
        } catch (Exception te) {
            out.println("<div align=\"center\">");
            out.println("<label id=\"" + objUri + "/moreFavoritesLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreFavorites").setParameter("maxTweetID", maxTweetID+"") + "','" + objUri + "/getMoreFavorites','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">" + paramRequest.getLocaleString("moreFavorites") + "</a></label>");
            out.println("</div>");
            log.error("Error when getting more favorites: ", te);
        }
    }
    
    public void doGetMoreDMsgs(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");
        twitter4j.Twitter twitter = twitterUsers.get(objUri);
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        
        Long maxTweetID;
        try{
            maxTweetID = Long.parseLong(request.getParameter("maxTweetID"));
        }catch(NumberFormatException nfe){
            maxTweetID =0L;
        }
        
        try {
            //System.out.println("Get the next 25 Direct Messages!!");
            Paging paging = new Paging(); //used to set maxId, count
            paging.count(20);
            if(maxTweetID >0L){
                paging.setMaxId(maxTweetID-1);
            }
            int i = 0;
            for (DirectMessage directMsg : twitter.getDirectMessages(paging)) {
                maxTweetID = directMsg.getId();
                doPrintDM(request, response, paramRequest, directMsg, twitter.getId(), out);
                i++;
            }
            out.println("<div align=\"center\">");
            out.println("<label id=\"" + objUri + "/moreDMLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreDM").setParameter("maxTweetID", maxTweetID+"") + "','" + objUri + "/getMoreDM','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">" + paramRequest.getLocaleString("moreDMs") + "</a></label>");
            out.println("</div>");
            //System.out.println("Total direct messages:" + i);
        } catch (Exception te) {
            log.error("Error when getting timeline: ", te);
        }
    }
    
    /**
     * Prints a tweet in the spedified output defined by the writer param.
     * @param request the request
     * @param response the response
     * @param paramRequest the paramRequest
     * @param status the status to be printed
     * @param twitter the twitter object to retrieve additional information of a status 
     * @param writer the object where the status will be printed
     * @param conversations array of conversations or null
     * @param tabSuffix id of the current tab
     * @param postURI if the post has already been classified it has an URI
     * @param socialUserExtAttr current user permissions over Timeline
     * @throws SWBResourceException
     * @throws IOException 
     */
    public static void doPrintTweet(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, Status status, 
            twitter4j.Twitter twitter, java.io.Writer writer, List<Status> conversations, String tabSuffix, String postURI, SocialUserExtAttributes socialUserExtAttr, boolean userCanDoEverything) throws SWBResourceException, IOException {
        String objUri = request.getParameter("suri");        
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        User twitterUser = status.getUser();
        
        SemanticObject semanticObject = SemanticObject.createSemanticObject(objUri);
        Twitter semTwitter = (Twitter) semanticObject.createGenericInstance();
        
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        
        if(status.isRetweet()){//This is a RT of someone else's tweet
            Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]{3,}");//Regex for username
            String rtInformation = status.getText().substring(0, status.getText().indexOf(":"));
            Matcher matcher = pattern.matcher(rtInformation);            
            if (matcher.find()) {
                try{
                    twitterUser = twitter.showUser(matcher.group(0));
                }catch(Exception e){
                    log.error("Error getting the user of RT: " + e.getMessage());
                }
            }else{
                //System.out.println("Match not found");
            }
            //Unable to undoRT created in another application
            
            //System.out.println("\n\nstatus.getCurrentUserRetweetId():" + status.getCurrentUserRetweetId());
            //System.out.println("status.getContributors():" + status.getContributors().length);
            //for(long v: status.getContributors()){
                //System.out.println("v:" + v);
            //}
            //Status originalStatus = status.getRetweetedStatus();
            
            //System.out.println("\n\nstatus.getRetweetedStatus()" + status.getRetweetedStatus());
            
            //try{
                  //if(originalStatus.getUser().getId()==0L);
            //                twitter.getRetweets(1l);
            //                System.out.println("userid:" + twitter.getId());                
            //}catch(Exception e){}
        }

        try {
                writer.write("<div class=\"timeline timelinetweeter\">");
                if(status.isRetweet()){
                    writer.write("  <p class=\"retweeted\">" + paramRequest.getLocaleString("retweetedBy") +  " ");
                    writer.write("       <a href=\"#\" onclick=\"showDialog('" + renderURL.setMode("showUserProfile").setParameter("targetUser", status.getUser().getScreenName()) + "','" + status.getUser().getName() + " - @" + status.getUser().getScreenName() + "');return false;\">@"+ status.getUser().getScreenName() + "</a>");
                    writer.write("  </p>");
                }

                writer.write("<p class=\"tweeter\">");
                writer.write(twitterUser.getName() + " <a href=\"#\" onclick=\"showDialog('" +
                    renderURL.setMode("showUserProfile").setParameter("targetUser", twitterUser.getScreenName()) + "','" +
                    twitterUser.getName() + " - @" + twitterUser.getScreenName() + "'); return false;\">" + twitterUser.getScreenName() + "</a>");
                writer.write("</p>");

                writer.write("<p class=\"tweet\">");
                writer.write("       <a href=\"#\" onclick=\"showDialog('" + renderURL.setMode("showUserProfile").setParameter("targetUser", twitterUser.getScreenName()) + "','" + twitterUser.getName() + " - @" + twitterUser.getScreenName() + "');return false;\"><img src=\"" + twitterUser.getProfileImageURL() + "\" width=\"48\" height=\"48\"/></a>");

                String statusText = status.getText();
                if(status.isRetweet()){// Remove 'RT @username: ' and show only the text when is a RT
                    statusText = statusText.substring(statusText.indexOf(":") + 2);
                }                                
                statusText = lookForEntities(statusText, renderURL, status.getURLEntities(), status.getMediaEntities(), status.getHashtagEntities(), status.getUserMentionEntities()); 
                writer.write(        statusText);
                writer.write("</p>");
                writer.write("<div class=\"timelineresume\">");
                /*creates isolated spans to identify and update only the elemente where the action affects*/
                writer.write("   <span class=\"inline\" id=\"" + semTwitter.getId() +  status.getId() + INFORMATION + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");                
                updateStatusInformation(status, renderURL, objUri, writer,socialUserExtAttr, userCanDoEverything, paramRequest);                
                writer.write("   </span>");                
                
                writer.write("   <span class=\"inline\" id=\"" + semTwitter.getId() + status.getId() + TOPIC + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                if((socialUserExtAttr != null && socialUserExtAttr.isUserCanReTopicMsg()) || userCanDoEverything){
                    if(postURI != null){//If post already exists
                        SWBResourceURL clasifybyTopic = renderURL.setMode("doReclassifyTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", status.getId()+"").setParameter("postUri", postURI).setParameter("currentTab", tabSuffix);
                        writer.write("<a href=\"#\" class=\"clasifica\" title=\"" + paramRequest.getLocaleString("reclassify") + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                            + paramRequest.getLocaleString("reclassify") +" tweet'); return false;\"></a>");
                    }else{//If posts does not exists 
                        SWBResourceURL clasifybyTopic = renderURL.setMode("doShowTopic").setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("id", status.getId()+"").setParameter("currentTab", tabSuffix);
                        writer.write("<a href=\"#\" class=\"clasifica\" title=\"" + paramRequest.getLocaleString("classify") + "\" onclick=\"showDialog('" + clasifybyTopic + "','"
                            + paramRequest.getLocaleString("classify") + " tweet'); return false;\"></a>");
                    }
                }else{
                    writer.write("&nbsp;");
                }
                writer.write("   </span>");
                
                writer.write("   <span class=\"inline\" id=\"" + semTwitter.getId() + status.getId() + FAVORITE + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                if(status.isRetweet()){//Apparently there's a bug in Twitter4j. isFavorite() is always false unless the status is obtained with showStatus()
                    try{
                        writer.write(checkIfFavorite(twitter.showStatus(status.getId()), actionURL, writer, tabSuffix, semTwitter, paramRequest));
                    }catch(Exception e){
                        log.error("Problem checking if RT is favorited ", e);
                    }
                }else{
                writer.write(checkIfFavorite(status, actionURL, writer,tabSuffix, semTwitter, paramRequest));
                }
                writer.write("   </span>");
                writer.write("   <span class=\"inline\" id=\"" + semTwitter.getId() + status.getId() + RETWEET + tabSuffix + "\" dojoType=\"dojox.layout.ContentPane\">");
                updateStatusRT(status, renderURL, actionURL, objUri, writer, twitter.getId(), tabSuffix, semTwitter, paramRequest);
                writer.write("   </span>");
                writer.write("</div>");
                
                if(conversations != null && !conversations.isEmpty()){
                    writer.write("   <span class=\"inline\" dojoType=\"dojox.layout.ContentPane\">");
                    writer.write(" <a href=\"#\"  onclick=\"showHideConversation('inReplyTo" + status.getId() + "'); return false;" +"\">Show conversation</a> ");
                    writer.write("   </span>");
                                        
                }
                
                /*if(conversations != null && !conversations.isEmpty()){
                    writer.write("<tr>");
                    writer.write("   <td width=\"9%\">&nbsp;</td>");
                    writer.write("   <td width=\"91%\">");
                    writer.write("   <table id=\"inReplyTo" + status.getId() +  "\" style=\"display: none;\">");
                    for(Status st : conversations){
                                writer.write("<tr>");
                                writer.write("   <td colspan=\"2\">");
                                writer.write("   " + st.getUser().getName() + " <b><a href=\"#\" onclick=\"showDialog('" +
                                    renderURL.setMode("showUserProfile").setParameter("targetUser", st.getUser().getScreenName()) + "','" +
                                    st.getUser().getName() + " - @" + st.getUser().getScreenName() + "'); return false;\">" + st.getUser().getScreenName() + "</a></b>");
                                writer.write("   </td>");
                                writer.write("</tr>");                                
                                writer.write("<tr>");
                                writer.write("   <td  width=\"10%\">");
                                writer.write("       <a href=\"#\" onclick=\"showDialog('" + renderURL.setMode("showUserProfile").setParameter("targetUser", st.getUser().getScreenName()) + "','" + st.getUser().getName() + " - @" + st.getUser().getScreenName() + "');return false;\"><img src=\"" + st.getUser().getProfileImageURL() + "\" width=\"48\" height=\"48\"/></a>  ");
                                writer.write("   </td>");
                                writer.write("   <td width=\"90%\">");                
                                String statusTextConv = lookForEntities(st.getText(), renderURL, st.getURLEntities(), st.getMediaEntities(), st.getHashtagEntities(),st.getUserMentionEntities());

                                writer.write(        statusTextConv);
                                writer.write("   </td>");
                                writer.write("</tr>");
                                writer.write("<tr>");
                                writer.write("   <td colspan=\"2\">");
                                
                                writer.write("   <span class=\"inline\" id=\"" + semTwitter.getId() + st.getId() + INFORMATION + "\" dojoType=\"dojox.layout.ContentPane\">");
                                updateStatusInformation(st, renderURL, objUri, writer, socialUserExtAttr, paramRequest);
                                writer.write("   </span>");
                                writer.write("   <span class=\"inline\" id=\"" + semTwitter.getId() + st.getId() + RETWEET + "\" dojoType=\"dojox.layout.ContentPane\">");
                                updateStatusRT(st, renderURL, actionURL, objUri, writer, twitter.getId(), tabSuffix, semTwitter, paramRequest);
                                writer.write("   </span>");
                                writer.write("   <span class=\"inline\" id=\"" + semTwitter.getId() + st.getId() + FAVORITE + "\" dojoType=\"dojox.layout.ContentPane\">");
                                writer.write(checkIfFavorite(st, actionURL, writer, tabSuffix, semTwitter, paramRequest));
                                writer.write("   </span>");

                                //writer.write("<div id=\"" + st.getId() + "\" dojoType=\"dijit.layout.ContentPane\">");
                                //updateTweetStatus(status, renderURL, actionURL, objUri, writer, twitter.getId());
                                writer.write("   </div>");
                                writer.write("   </td>");
                                writer.write("</tr>");
                    }
                    writer.write("   </table>");
                    writer.write("   </td>");
                    writer.write("</tr>");
                    
                }*/
                //writer.write("</table>");
                writer.write("</div>");             
        } catch (Exception te) {
            log.error("Error when getting timeline: ", te);
        }        
    }
    
    public static void doPrintDM(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest, DirectMessage dm, Long currenUser, java.io.Writer writer) throws SWBResourceException, IOException {
        String objUri = request.getParameter("suri");        
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        Long maxTweetID;
        try{
            maxTweetID = Long.parseLong(request.getParameter("maxTweetID"));
        }catch(NumberFormatException nfe){
            maxTweetID =0L;
        }
        try {
                maxTweetID = dm.getId();
                writer.write("<div class=\"timeline timelinetweeter\">");
                
                writer.write("<p class=\"tweeter\">");
                writer.write(dm.getSenderScreenName() + " <a href=\"#\" onclick=\"showDialog('" +
                    renderURL.setMode("showUserProfile").setParameter("targetUser", dm.getSenderScreenName()) + "','" +
                    dm.getSenderScreenName() + " - @" + dm.getSenderScreenName() + "'); return false;\">" + dm.getSenderScreenName() + "</a>");
                writer.write("</p>");

                writer.write("<p class=\"tweet\">");
                writer.write("       <a href=\"#\" onclick=\"showDialog('" + renderURL.setMode("showUserProfile").setParameter("targetUser", dm.getSenderScreenName()) + "','" + dm.getSenderScreenName() + " - @" + dm.getSenderScreenName() + "');return false;\"><img src=\"" + dm.getSender().getProfileImageURL() + "\" width=\"48\" height=\"48\"/></a>");

                String statusText = dm.getText();
                                            
                statusText = lookForEntities(statusText, renderURL, dm.getURLEntities(), dm.getMediaEntities(), dm.getHashtagEntities(), dm.getUserMentionEntities()); 
                writer.write(        statusText);
                writer.write("</p>");
                writer.write("<div class=\"timelineresume\">");
                /*creates isolated spans to identify and update only the elemente where the action affects*/
                writer.write("   <span class=\"inline\" id=\"" + dm.getId() + "\" dojoType=\"dojox.layout.ContentPane\">");                
                writer.write("<em>" + twitterHumanFriendlyDate(dm.getCreatedAt(), paramRequest) + "</em> ");
                writer.write("<a class=\"clasifica\" href=\"#\" onclick=\"showDialog('" + renderURL.setMode("replyDM").setParameter("userId", dm.getSenderId()+"") + "','DM to @" + dm.getSenderScreenName()+ "');return false;\"><span>Responder</span></a>  ");
                writer.write("   </span>");                
                writer.write("</div>");
                               
                writer.write("</div>");
                
                /*writer.write("<fieldset>");
                writer.write("<table style=\"width: 100%; border: 0px\">");
                writer.write("<tr>");
                writer.write("   <td colspan=\"2\">");
                writer.write("   From:" + dm.getSenderScreenName() + " To:<b>" + dm.getRecipientScreenName() + "</b>");
                writer.write("   </td>");
                writer.write("</tr>");
                writer.write("<tr>");
                writer.write("   <td  width=\"10%\">");
                writer.write("       <img src=\"" + dm.getSender().getProfileImageURL() + "\" width=\"48\" height=\"48\"/>");
                writer.write("   </td>");
                writer.write("   <td width=\"90%\">");                
                String statusText = lookForEntities(dm.getText(), renderURL, dm.getURLEntities(), dm.getMediaEntities(), dm.getHashtagEntities(),dm.getUserMentionEntities()); 
                
                writer.write(        statusText);
                writer.write("   </td>");
                writer.write("</tr>");
                writer.write("<tr>");
                writer.write("   <td colspan=\"2\">");
                
                writer.write("<div id=\"" + dm.getId() + "\" dojoType=\"dijit.layout.ContentPane\">");
                writer.write("<b>" + twitterHumanFriendlyDate(dm.getCreatedAt(), paramRequest) + "</b> ");
                writer.write("<a href=\"\" onclick=\"showDialog('" + renderURL.setMode("replyDM").setParameter("userId", dm.getSenderId()+"") + "','DM to @" + dm.getSenderScreenName()+ "');return false;\">Reply</a>  ");
                writer.write("   </div>");
                writer.write("   </td>");
                writer.write("</tr>");          
                writer.write("</table>");
                writer.write("</fieldset>");*/
        } catch (Exception te) {
            log.error("Error when getting DM timeline: ", te);
        }        
    }
    
    /**
     * Checks if the statusText param contains url, media, hashtag or user entities and adds the href code to the string.
     * @param statusText status text
     * @param urlEnts url entities of the status
     * @param mediaEnts media entities of the status
     * @param htEnts hashtag entities of the status
     * @param usrEnts user Entities of the status
     * @return the status text including the URLs for each one of the entities found
     */
    public static String lookForEntities(String statusText, SWBResourceURL renderURL, URLEntity urlEnts[],  MediaEntity mediaEnts[], HashtagEntity htEnts[], UserMentionEntity usrEnts[]){
        //Looking for URL entities
        if(urlEnts!=null && urlEnts.length >0){
            for(URLEntity urlEnt: urlEnts){                       
                statusText=statusText.replace(urlEnt.getURL(), "<a href=\"" + urlEnt.getURL() +  "\" target=\"_blank\">" + urlEnt.getURL() +"</a>");
            }
        }
        
        //Looking for multimedia entities
        if(mediaEnts!=null && mediaEnts.length >0){
            for(MediaEntity mediaEnt: mediaEnts){       
                statusText=statusText.replace(mediaEnt.getURL(), "<a href=\"" + mediaEnt.getURL() +  "\" target=\"_blank\">" + mediaEnt.getURL() +"</a>");
            }
        }
        
        //Looking for hashtag entities
        if(htEnts!=null && htEnts.length >0){
            for(HashtagEntity htEnt: htEnts){       
                statusText=statusText.replace("#" + htEnt.getText(), "<a href=\"https://twitter.com/search?q=%23" + htEnt.getText() +  "&src=hash\" target=\"_blank\">#"+ htEnt.getText() +"</a>");
            }
        }
        
        //Looking for user entities        
        if(usrEnts!=null && usrEnts.length >0){
            HashMap<String, String> hmUserEntity = new HashMap<String, String>();
            for(UserMentionEntity usrEnt: usrEnts){//In case the username appear more than once
                hmUserEntity.put(usrEnt.getScreenName(), usrEnt.getName());
            }            
            Iterator it = hmUserEntity.keySet().iterator();
            while(it.hasNext()){
                String screen = (String)it.next();//key
                String name= hmUserEntity.get(screen);//value
                statusText=statusText.replaceAll("@" + screen, "<a href=\"#\" onclick=\"showDialog('" + renderURL.setMode("showUserProfile").setParameter("targetUser", screen) + "','" + name + " - @" + screen + "'); return false;\">@"+ screen + "</a></b>");
            }                        
        }
        return statusText;
    }
    
    public static List<Status> recoverConversations(long tweetID, twitter4j.Twitter twitter){
        List<Status> conversations=null;
        /*try {
            //Show conversation information if exists
            RelatedResults results = twitter.getRelatedResults(tweetID);
            conversations = results.getTweetsWithConversation();
            Status originalStatus = twitter.showStatus(tweetID);
            if (conversations.isEmpty()) {
                conversations = results.getTweetsWithReply();
            }

            if (conversations.isEmpty()) {
                conversations = new ArrayList<Status>();
                Status status1 = originalStatus;
                while (status1.getInReplyToStatusId() > 0) {
                    status1 = twitter.showStatus(status1.getInReplyToStatusId());
                    conversations.add(status1);
                }
            }
            for(Status st : conversations){
                System.out.println("from:" + st.getUser().getScreenName() + " text:" + st.getText() );
            }
        } catch (TwitterException ex) {
            System.out.println("message" + ex.getMessage());
            ex.printStackTrace();
            java.util.logging.Logger.getLogger(Timeline.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        return conversations;
    }
    
    public static String checkIfFavorite(Status originalStatus, SWBResourceURL actionURL, java.io.Writer out, String tabSuffix, Twitter semTwitter, SWBParamRequest paramRequest){
        String favorite = "";
        try{
            if(originalStatus.isFavorited()){
                actionURL.setAction("undoFavorite");
                favorite = "<a href=\"#\" title=\"" + paramRequest.getLocaleString("undoFavorite") + "\" class=\"nofavorito\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + actionURL.setParameter("id", originalStatus.getId()+"").setParameter("currentTab", tabSuffix) + "','" + semTwitter.getId() + originalStatus.getId() + INFORMATION + tabSuffix + "'); return false;" +"\"></a> ";
            }else{
                actionURL.setAction("doFavorite");
                favorite = "<a href=\"#\" title=\"" + paramRequest.getLocaleString("favorite") + "\" class=\"favorito\" onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + actionURL.setParameter("id", originalStatus.getId()+"").setParameter("currentTab", tabSuffix) + "','" + semTwitter.getId() + originalStatus.getId() + INFORMATION + tabSuffix +  "'); return false;" +"\"></a> ";
            }
        }catch(Exception e){
            log.error("Problem checking if tweet is favorite ",  e);
        }
        return favorite;
    }
        
    /**
     * Updates the information of the div when a retweed/favorite is made
     * @param originalStatus
     * @param renderURL
     * @param actionURL
     * @param objUri
     * @param out
     * @param currentUser 
     */
    public static void updateStatusInformation(Status originalStatus, SWBResourceURL renderURL, String objUri, java.io.Writer out, SocialUserExtAttributes socialUserExtAttr, boolean userCanDoEverything, SWBParamRequest paramRequest){
        try{
            long retweets = originalStatus.getRetweetCount();
            String times = paramRequest.getLocaleString("times");
            if(retweets == 1){
                times = paramRequest.getLocaleString("rtime");
            }
            out.write("<em title=\"" + twitterHumanFriendlyDate(originalStatus.getCreatedAt(), paramRequest) + "\">&nbsp;</em> <strong><span>Retweeted: </span>" + originalStatus.getRetweetCount() + " " + times + " </strong>");
            
            if((socialUserExtAttr != null && socialUserExtAttr.isUserCanRespondMsg()) || userCanDoEverything){
            out.write("<a href=\"#\" title=\"Responder\" class=\"answ\" onclick=\"showDialog('" + renderURL.setMode("replyTweet").setParameter("id", originalStatus.getId()+"").setParameter("userName", "@" +
                    originalStatus.getUser().getScreenName()).setParameter("suri", objUri) +
                    "','Responder a @"  + originalStatus.getUser().getScreenName() + "');return false;\"></a> ");
            
            }
        }catch(Exception ex){
            //System.out.println("Error checking updating Tweet Status!" + ex.getMessage());
            log.error("Error checking updating Tweet Status!", ex);
        }
    }
    
    public static void updateStatusRT(Status originalStatus, SWBResourceURL renderURL, SWBResourceURL actionURL, String objUri, java.io.Writer out, Long currentUser, String tabSuffix, Twitter semTwitter, SWBParamRequest paramRequest){
        try{
            if(originalStatus.isRetweetedByMe()){
                //System.out.println("SI es RT by ME inside update Status!!");
                //actionURL.setAction("undoRT");
                //out.write("<a href=\"#\"  onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + actionURL.setParameter("id", originalStatus.getId()+"").setParameter("currentTab", tabSuffix) + "','" + originalStatus.getId() + INFORMATION + tabSuffix + "'); return false;" +"\">Undo Retweet</a>");
                out.write("<a title=\"" + paramRequest.getLocaleString("undoRetweet") +"\" class=\"noretweet\" href=\"#\"  onclick=\"return false;" +"\"></a>");
            }else if(originalStatus.getUser().getId() == currentUser){ //User cannot retweet its own tweet
                //System.out.println(" Is My Tweet ");
                out.write("<span></span>");
            }else{
                //System.out.println("Vamos a hacer RT");
                actionURL.setAction("doRT");
                out.write("<a title=\"" + paramRequest.getLocaleString("retweet") +"\" class=\"retweet\" href=\"#\"  onclick=\"try{dojo.byId(this.parentNode).innerHTML = '<img src=" + SWBPlatform.getContextPath() + "/swbadmin/icons/loading.gif>';}catch(noe){} postSocialHtml('" + actionURL.setParameter("id", originalStatus.getId()+"").setParameter("currentTab", tabSuffix) + "','" + semTwitter.getId() + originalStatus.getId() + INFORMATION + tabSuffix +  "'); return false;" +"\"></a> ");
            }
        }catch(Exception ex){
            //System.out.println("Error checking updating RT Status!" + ex.getMessage());
            log.error("Error checking updating RT Status!", ex);
        }
    }
    
    public static String twitterHumanFriendlyDate(Date created, SWBParamRequest paramRequest){
        Date today = new Date();
        Long duration = today.getTime() - created.getTime();

        int second = 1000;
        int minute = second * 60;
        int hour = minute * 60;
        int day = hour * 24;
        String date = "";
        try{
            if (duration < second * 7) {//Less than 7 seconds
                date = paramRequest.getLocaleString("rightNow");
            }else if (duration < minute) {
                int n = (int) Math.floor(duration / second);
                date = n + " " + paramRequest.getLocaleString("secondsAgo");
            }else if (duration < minute * 2) {//Less than 2 minutes
                date = paramRequest.getLocaleString("about") + " 1 " + paramRequest.getLocaleString("minuteAgo");
            }else if (duration < hour) {
                int n = (int) Math.floor(duration / minute);
                date = n + " " + paramRequest.getLocaleString("minutesAgo");
            }else if (duration < hour * 2) {//Less than 1 hour
                date = paramRequest.getLocaleString("about") + " 1 " + paramRequest.getLocaleString("hourAgo");
            }else if (duration < day) {
                int n = (int) Math.floor(duration / hour);
                date = n + " " + paramRequest.getLocaleString("hoursAgo");
            }else  if (duration > day && duration < day * 2) {
                date = paramRequest.getLocaleString("yesterday");
            }else{
                int n = (int) Math.floor(duration / day);
                date = n + " " + paramRequest.getLocaleString("daysAgo");
            }
        }catch(Exception e){
            log.error("Problem found computing time of post. ", e);
        }        
        return date;
    }
    
    
    public void doCreatePost(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(SWBPlatform.getContextPath() + "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/post/typeOfContent.jsp");
        request.setAttribute("contentType", request.getParameter("valor"));
        request.setAttribute("wsite", request.getParameter("wsite"));
        request.setAttribute("objUri", request.getParameter("objUri"));
        request.setAttribute("paramRequest", paramRequest);

        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            log.error("Error al enviar los datos a typeOfContent.jsp " + ex.getMessage());
        }
    }
    
    public void doGetMoreFriends(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");        
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        twitter4j.Twitter twitter = twitterUsers.get(objUri);
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        
        Long friendsCursor;
        try{
            friendsCursor = Long.parseLong(request.getParameter("friendsCursor"));
        }catch(NumberFormatException nfe){
            friendsCursor = -1L;
        }
        
        try {
            PagableResponseList<User> friends;
            //do {
            friends = twitter.getFriendsList(twitter.getId(), friendsCursor);
            if(friends.size() == 0)return;
            Twitter twitterSem = (Twitter)SemanticObject.createSemanticObject(objUri).createGenericInstance();
            SWBModel model=WebSite.ClassMgr.getWebSite(twitterSem.getSemanticObject().getModel().getName());
            SocialTopic defaultSocialTopic = SocialTopic.ClassMgr.getSocialTopic("DefaultTopic", model);

            for(int i = 0; i < friends.size(); i++){
                User user = friends.get(i);
                //out.println("THE friend:" + "<img src=\"" + user.getBiggerProfileImageURL() + "\">" + friends.get(i).getScreenName() + "</br>");
                out.println("<div class=\"timeline timelinetweeter\">");
                out.println("	<p class=\"tweeter\">");
                out.println("	<a onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("suri", objUri).setParameter("targetUser", user.getScreenName()) + "','@" + user.getScreenName() + " - " + user.getName() + "'); return false;\" href=\"#\">@" + user.getScreenName() + "</a> "  + user.getName());
                out.println("	</p>");
                out.println("	<p class=\"tweet\">");
                out.println("		<a onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("suri", objUri).setParameter("targetUser", user.getScreenName()) + "','@" + user.getScreenName() + " - " + user.getName() + "');return false;\" href=\"#\">");
                out.println("			<img width=\"48\" height=\"48\" src=\"" + user.getBiggerProfileImageURL() + "\"></a>");
                out.println(            user.getDescription());
                out.println("	</p>");
                out.println("<div class=\"timelineresume\">");
                out.println("<span class=\"inline\">");
                out.println("<a class=\"clasifica\" href=\"#\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("createTweet").setParameter("suri",defaultSocialTopic.getURI()).setParameter("netSuri",objUri).setParameter("username",user.getScreenName()) + "','Enviar mensaje a @" + user.getScreenName() + "');return false;\">Enviar Mensaje</a>");
                out.println("</span>");
                out.println("<span class=\"inline\">");
                out.println("<a class=\"clasifica\" href=\"#\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("createNewDM").setParameter("suri",objUri).setParameter("userId", user.getId()+"") + "','DM to @" + user.getScreenName() + "');return false;\">Enviar Mensaje Directo</a>");
                out.println("</span>");
                out.println("</div>");
                out.println("</div>");
            }
            friendsCursor = friends.getNextCursor();
            out.println("<div align=\"center\">");
            out.println("<label id=\"" + objUri + "/moreFriendsLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreFriends").setParameter("friendsCursor", friendsCursor+"") + "','" + objUri + "/getMoreFriends','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">" + paramRequest.getLocaleString("moreFriends") + "</a></label>");
            out.println("</div>");
        } catch (Exception te) {
            log.error("Error when getting friends: ", te);
            out.println("<div align=\"center\">");
            out.println("<label id=\"" + objUri + "/moreFriendsLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreFriends").setParameter("friendsCursor", friendsCursor+"") + "','" + objUri + "/getMoreFriends','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">" + paramRequest.getLocaleString("moreFriends") + "</a></label>");
            out.println("</div>");
        }
    }
    
    public void doGetMoreFollowers(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String objUri = request.getParameter("suri");        
        SWBResourceURL actionURL = paramRequest.getActionUrl();
        SWBResourceURL renderURL = paramRequest.getRenderUrl();
        twitter4j.Twitter twitter = twitterUsers.get(objUri);
        if(objUri!= null){
            actionURL.setParameter("suri", objUri);
            renderURL.setParameter("suri", objUri);
        }
        
        Long followersCursor;
        try{
            followersCursor = Long.parseLong(request.getParameter("followersCursor"));
        }catch(NumberFormatException nfe){
            followersCursor = -1L;
        }
        
        try {
            PagableResponseList<User> followers;
            //do {
            followers = twitter.getFollowersList(twitter.getId(), followersCursor);
            if(followers.size() == 0)return;
            Twitter twitterSem = (Twitter)SemanticObject.createSemanticObject(objUri).createGenericInstance();
            SWBModel model=WebSite.ClassMgr.getWebSite(twitterSem.getSemanticObject().getModel().getName());
            SocialTopic defaultSocialTopic = SocialTopic.ClassMgr.getSocialTopic("DefaultTopic", model);
            //out.println("-----" + "/nCursor:" + friends.getNextCursor());
            for(int i = 0; i < followers.size(); i++){
                User user = followers.get(i);
                //out.println("THE follower:" + "<img src=\"" + user.getBiggerProfileImageURL() + "\">" + followers.get(i).getScreenName() + "</br>");
                out.println("<div class=\"timeline timelinetweeter\">");
                out.println("	<p class=\"tweeter\">");
                out.println("	<a onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("suri", objUri).setParameter("targetUser", user.getScreenName()) + "','@" + user.getScreenName() + " - " + user.getName() + "'); return false;\" href=\"#\">@" + user.getScreenName() + "</a> "  + user.getName());
                out.println("	</p>");
                out.println("	<p class=\"tweet\">");
                out.println("		<a onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("showUserProfile").setParameter("suri", objUri).setParameter("targetUser", user.getScreenName()) + "','@" + user.getScreenName() + " - " + user.getName() + "');return false;\" href=\"#\">");
                out.println("			<img width=\"48\" height=\"48\" src=\"" + user.getBiggerProfileImageURL() + "\"></a>");
                out.println(            user.getDescription());
                out.println("	</p>");
                out.println("<div class=\"timelineresume\">");
                out.println("<span class=\"inline\">");
                out.println("<a class=\"clasifica\" href=\"#\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("createTweet").setParameter("suri",defaultSocialTopic.getURI()).setParameter("netSuri",objUri).setParameter("username",user.getScreenName()) + "','Enviar mensaje a @" + user.getScreenName() + "');return false;\">Enviar Mensaje</a>");
                out.println("</span>");
                out.println("<span class=\"inline\">");
                out.println("<a class=\"clasifica\" href=\"#\" onclick=\"showDialog('" + paramRequest.getRenderUrl().setMode("createNewDM").setParameter("suri",objUri).setParameter("userId", user.getId()+"") + "','DM to @" + user.getScreenName() + "');return false;\">Enviar Mensaje Directo</a>");
                out.println("</span>");
                out.println("</div>");
                out.println("</div>");                
            }
            followersCursor = followers.getNextCursor();
            out.println("<div align=\"center\">");
            out.println("<label id=\"" + objUri + "/moreFollowersLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreFollowers").setParameter("followersCursor", followersCursor+"") + "','" + objUri + "/getMoreFollowers','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">" + paramRequest.getLocaleString("moreFollowers") + "</a></label>");
            out.println("</div>");
        } catch (Exception te) {
            log.error("Error when getting followers: ", te);
            out.println("<div align=\"center\">");
            out.println("<label id=\"" + objUri + "/moreFollowersLabel\"><a href=\"#\" onclick=\"appendHtmlAt('" + renderURL.setMode("getMoreFollowers").setParameter("followersCursor", followersCursor+"") + "','" + objUri + "/getMoreFollowers','bottom');try{this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);}catch(noe){}; return false;\">" + paramRequest.getLocaleString("moreFollowers") + "</a></label>");
            out.println("</div>");
        }
    }
}
