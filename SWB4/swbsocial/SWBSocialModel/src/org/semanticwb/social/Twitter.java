package org.semanticwb.social;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaQuery.j2ee.tinyURL;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Twitter extends org.semanticwb.social.base.TwitterBase {

    Message msg = null;
    Photo photo = null;

    public Twitter(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void postMsg(Message message, HttpServletRequest request, SWBActionResponse response) {
        addPost(message);
        this.msg = message;
        if (msg != null) {
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();
            String token = super.getSecretKey().substring(0, super.getSecretKey().indexOf("|"));
            String secretToken = super.getSecretKey().substring(super.getSecretKey().indexOf("|") + 1, super.getSecretKey().length());
            AccessToken accessToken = new AccessToken(token, secretToken);
            System.out.println("Mensaje de Twitter:" + msg.getMsg_Text());
            twitter.setOAuthAccessToken(accessToken);

            try {
                twitter.setOAuthAccessToken(accessToken);
                StatusUpdate sup = new StatusUpdate(new String(message.getMsg_Text().getBytes(), "utf-8"));

                twitter.updateStatus(sup);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Twitter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TwitterException ex) {
                if (401 == ex.getStatusCode()) {
                    //getResourceBase().setAttribute("oauth", null);
                }
            }
        }
    }

    public void postPhoto(Photo photo, HttpServletRequest request, SWBActionResponse response) {
        addPost(photo);
        System.out.println("Twitter login:" + getLogin());
        System.out.println("Twitter Passw:" + getPassword());
        System.out.println("Twitter SK:" + getSecretKey());
        this.photo = photo;
        if (photo != null) {
            twitter4j.Twitter twitter = new TwitterFactory().getInstance();
            AccessToken accessToken = new AccessToken("token", "secretToken");
            System.out.println("Mensaje de Twitter:" + msg.getMsg_Text());
            twitter.setOAuthAccessToken(accessToken);
            try {
                twitter.setOAuthAccessToken(accessToken);
                StatusUpdate sup = new StatusUpdate(new String(photo.getComment().getBytes(), "utf-8"));
                sup.setMedia(new File(photo.getPhoto()));
                twitter.updateStatus(sup);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Twitter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TwitterException ex) {
                if (401 == ex.getStatusCode()) {
                    //getResourceBase().setAttribute("oauth", null);
                }
            }
            System.out.println("Mensaje de Photo de Twitter:" + photo.getComment());
            System.out.println("Photo de Twitter:" + photo.getPhoto());
        }
    }

    private String shortUrl(String urlLong) {
        String shortUrl = "";
        if (urlLong != null && !urlLong.equals("")) {
            String delimiter = " ";
            String[] temp = urlLong.split(delimiter);
            for (int i = 0; i < temp.length; i++) {
                if ((temp[i].startsWith("http://") || temp[i].startsWith("http://")) && ((temp[i].length() > 9))) {
                    tinyURL tU = new tinyURL();
                    shortUrl = tU.getTinyURL(temp[i]);
                }
            }
        }
        return shortUrl;
    }
    
    
}