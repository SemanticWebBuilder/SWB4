/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.listener.twitter;

import java.io.IOException;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author jorge.jimenez
 */
public class Test {

    public static void main(String[] args) throws TwitterException, IOException {
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
                System.out.println(status.getUser().getName() + " : " + status.getText());
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }

            @Override
            public void onScrubGeo(long l, long l1) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onStallWarning(StallWarning sw) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
       
        TwitterStream twitterStream = new TwitterStreamFactory(configureOAuth().build()).getInstance();
        twitterStream.addListener(listener);
        
        FilterQuery query = new FilterQuery();
        {
            String[] tr = {"infotec"};
            query.track(tr);
        }
        twitterStream.filter(query);
        
        
        // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        //twitterStream.sample();
    }
    
    
     /**
     * Returns the configuration object with OAuth Credentials
     * Anonymous access is not allowed
     */
    private static ConfigurationBuilder configureOAuth(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("V5Xp0RYFuf3N0WsHkqSOIQ")
          .setOAuthConsumerSecret("4DZ9UrE4X5VavUjXzBcGFTvEsHVsCGOgIuLVSZMA8")
          .setOAuthAccessToken("47531700-jXeCrVfnUEksmoZCrTnYo8iFu8ZOnhMAJRZ4089pw")
          .setOAuthAccessTokenSecret("d9JSNytuJmp0ft8LPlxMrtLlpNOW0H23WFAg5ubBXsw"); 
        return cb;
    }
}
