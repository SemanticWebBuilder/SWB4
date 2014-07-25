
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorge.jimenez
 */
public class TwitterStreamingApiTest {
    
    public static void main(String[] args)
    {
            StatusListener listener = new StatusListener(){
                public void onStatus(Status status) {
                    System.out.println(status.getUser().getName() + " : " + status.getText());
                }
                public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
                public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
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
            TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
            FilterQuery query = new FilterQuery();
            
            String words2Monitor="cerveza botana";
            String[] tr = {words2Monitor};
            query.track(tr);
            //System.out.println("words2Monitor--1:"+words2Monitor);
            
            twitterStream.filter(query);
            
            
            twitterStream.addListener(listener);
            // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
            twitterStream.sample();
        
            ///////////
        
            //twitterStream.sample();
       
    }
    
}
