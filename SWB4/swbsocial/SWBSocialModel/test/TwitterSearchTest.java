
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorge.jimenez
 */
public class TwitterSearchTest
{

    public static void main(String[] args) {
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            Query query = new Query("hola");
            QueryResult result=twitter.search(query);
            List<Tweet> tweets = result.getTweets();
            for (Tweet tweet : tweets) {
                System.out.println("@" + tweet.getFromUser() + " - " + tweet.getText());
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }

}
