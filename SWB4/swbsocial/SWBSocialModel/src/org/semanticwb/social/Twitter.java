package org.semanticwb.social;

import java.io.File;
import java.io.UnsupportedEncodingException;
import javaQuery.j2ee.tinyURL;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.SWBActionResponse;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Twitter extends org.semanticwb.social.base.TwitterBase {

    Logger log  = SWBUtils.getLogger(Twitter.class);

    public Twitter(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void postMsg(Message message, HttpServletRequest request, SWBActionResponse response) {
        String uri = request.getParameter("socialUri");
        Twitter twitterObj = null;
        if(uri != null && uri.trim().length() > 1) {
            twitterObj = (Twitter) SemanticObject.createSemanticObject(uri).createGenericInstance();
        }
        if (message != null && message.getMsg_Text() != null && message.getMsg_Text().trim().length() > 1 && twitterObj != null) {
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();

            try {
                twitter.setOAuthConsumer(twitterObj.getAppKey(), twitterObj.getSecretKey());
                AccessToken accessToken = new AccessToken(twitterObj.getAccessToken(), twitterObj.getAccessTokenSecret());
                twitter.setOAuthAccessToken(accessToken);
//                StatusUpdate sup = new StatusUpdate(new String(message.getMsg_Text().getBytes(), "utf-8"));
                StatusUpdate sup = new StatusUpdate(new String(shortUrl(message.getMsg_Text()).getBytes(), "utf-8"));

                twitter.updateStatus(sup);
            } catch (UnsupportedEncodingException ex) {
                log.error("Exception"+ ex);
            } catch (TwitterException ex) {
                log.error("Exception"+ ex);
                if (401 == ex.getStatusCode()) {
                    //getResourceBase().setAttribute("oauth", null);
                }
            }
        }
    }

    public void postPhoto(Photo photo, HttpServletRequest request, SWBActionResponse response) {
        String uri = request.getParameter("socialUri");
        User user = response.getUser();
        Twitter twitterObj = null;
        if(uri != null && uri.trim().length() > 1) {
            twitterObj = (Twitter) SemanticObject.createSemanticObject(uri).createGenericInstance();
        }
        if (photo != null && photo.getPhoto() != null && photo.getPhoto().trim().length() > 1 && twitterObj != null) {
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(twitterObj.getAppKey(), twitterObj.getSecretKey());
            AccessToken accessToken = new AccessToken(twitterObj.getAccessToken(), twitterObj.getAccessTokenSecret());
            String description = photo.getDescription(user.getLanguage()) == null ? (photo.getDescription() == null ? "" : photo.getDescription()) : photo.getDescription(user.getLanguage());
            //System.out.println("Mensaje de Twitter:" + description);
            twitter.setOAuthAccessToken(accessToken);
            String photoSend = SWBPortal.getWorkPath() + photo.getWorkPath() + "/" + Photo.social_photo.getName() +
                     "_" + photo.getId() + "_" + photo.getPhoto();
            try {
                twitter.setOAuthAccessToken(accessToken);
                //StatusUpdate sup = new StatusUpdate(new String(photo.getComment().getBytes(), "utf-8"));
                StatusUpdate sup = new StatusUpdate(new String(shortUrl(description).getBytes(), "utf-8"));
                sup.setMedia(new File(photoSend));
                twitter.updateStatus(sup);
            } catch (UnsupportedEncodingException ex) {
                log.error("Exception"+ ex);
            } catch (TwitterException ex) {
                log.error("Exception"+ ex);
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
    
    
}