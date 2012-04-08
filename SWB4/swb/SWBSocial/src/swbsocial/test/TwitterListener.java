/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package swbsocial.test;

import java.io.IOException;
import twitter4j.FilterQuery;
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
public class TwitterListener {

    public static void main(String[] args) throws TwitterException, IOException {

        StatusListener listener = new StatusListener() {

            public void onStatus(Status status) {
                //if (status.getGeoLocation() != null)
                //if(status.getText().indexOf("android")>-1 || status.getText().indexOf("ipad")>-1 || status.getText().indexOf("iphone")>-1 || status.getText().indexOf("tarea")>-1)
                {
                    System.out.println();
                    System.out.println("------------");
                    //System.out.println(status.getUser().getName() + " : " + status.getText() + " : " + status.getGeoLocation());
                    System.out.println(status.getCreatedAt());
                }
                System.out.print(" ");
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }

            public void onScrubGeo(long userId, long upToStatusId) {
            }
        };
        /*create filterQuery*/
        FilterQuery query = new FilterQuery();
        //NOTE: format of values: {minLongitude, minLatitude}, {...}
        double[][] loc = {{-118, 14}, {-86, 33}}; //Bounding Box de San Francisco
        //double[][] loc = {{32.718620, -86.703392}, {14.532850, -118.867172}}; //Bounding Box de México (País) Encontrado en http://isithackday.com/geoplanet-explorer/index.php?woeid=23424900
        query.locations(loc);
        String[] tr = {"kiunda"};
        query.track(tr);
        

        System.out.println(query.toString());

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
        cb.setUser("Jorge24Jimenez");
        cb.setPassword("george24");

        // creating the twitter listener

        Configuration cfg = cb.build();
        TwitterStream trial = new TwitterStreamFactory(cfg).getInstance();

        //trial.addListener(listener);

        //trial.filter(query);
        //System.out.println(" here is stuff : " + trial.getFilterStream(query));
        //trial.sample();

    }
}
