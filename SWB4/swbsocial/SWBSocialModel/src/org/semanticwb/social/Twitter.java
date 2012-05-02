package org.semanticwb.social;

import java.io.File;
import java.io.UnsupportedEncodingException;
import javaQuery.j2ee.tinyURL;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.SWBActionResponse;
import twitter4j.Status;
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
        User user = response.getUser();

        if (photo != null && photo.getPhoto() != null && photo.getPhoto().trim().length() > 1) {
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(this.getAppKey(), this.getSecretKey());
            AccessToken accessToken = new AccessToken(this.getAccessToken(), this.getAccessTokenSecret());
            String description = photo.getDescription(user.getLanguage()) == null ? (photo.getDescription() == null ? "" : photo.getDescription()) : photo.getDescription(user.getLanguage());
            twitter.setOAuthAccessToken(accessToken);
            String photoSend = SWBPortal.getWorkPath() + photo.getWorkPath() + "/" + Photo.social_photo.getName() +
                     "_" + photo.getId() + "_" + photo.getPhoto();
            try {
                twitter.setOAuthAccessToken(accessToken);
                //StatusUpdate sup = new StatusUpdate(new String(photo.getComment().getBytes(), "utf-8"));
                StatusUpdate sup = new StatusUpdate(new String(shortUrl(description).getBytes(), "ISO-8859-1"));
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