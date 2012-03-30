/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package swbsocial.test;

import java.io.IOException;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 *
 * @author jorge.jimenez
 */
public class TwitterStreamTest {

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

             public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId
                + " upToStatusId:" + upToStatusId);
             }
        };

        //TwitterStream twitterStream = new TwitterStreamFactory(listener).getInstance();
        // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        //twitterStream.sample();
    }
}
