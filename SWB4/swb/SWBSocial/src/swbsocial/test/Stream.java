/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swbsocial.test;

import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author jorge.jimenez
 */
public class Stream {
    public static void main(String[] args) throws TwitterException {

    ConfigurationBuilder cb = new ConfigurationBuilder();
    /*
    cb.setDebugEnabled(true);
    cb.setOAuthConsumerKey("bbb");
    cb.setOAuthConsumerSecret("bbb");
    cb.setOAuthAccessToken("bbb");
    cb.setOAuthAccessTokenSecret("bbb");
     *
     */
    cb.setUser("Jorge24Jimenez");
    cb.setPassword("george24");

    TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    StatusListener listener = new StatusListener() {

        public void onStatus(Status status) {
            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
        }

        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
        }

        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
        }

        public void onScrubGeo(long userId, long upToStatusId) {
            System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
        }

        public void onException(Exception ex) {
            ex.printStackTrace();
        }
    };

    FilterQuery fq = new FilterQuery();
    String keywords[] = {"France", "Germany", "imagen"};

    fq.track(keywords);

    twitterStream.addListener(listener);
    twitterStream.filter(fq);
}
}
