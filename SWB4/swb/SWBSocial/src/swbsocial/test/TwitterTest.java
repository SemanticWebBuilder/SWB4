/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swbsocial.test;

/*
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
 * 
 */
import java.io.IOException;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 *
 * @author jorge.jimenez
 */
public class TwitterTest {
   
 //static EPServiceProvider epService;

 public static void main(String[] args) throws TwitterException, IOException {

  // Creating and registering the CEP listener

 /*
  com.espertech.esper.client.Configuration config1 = new com.espertech.esper.client.Configuration();
  config1.addEventType("HappyMessage", HappyMessage.class.getName());
  epService = EPServiceProviderManager.getDefaultProvider(config1);
  String expression = "select user, sum(ctr) from swbsocial.test.HappyMessage.win:time(10 seconds) having sum(ctr) > 2";

  EPStatement statement = epService.getEPAdministrator().createEPL(expression);
  HappyEventListener happyListener = new HappyEventListener();
  statement.addListener(happyListener);
  *
  */

  ConfigurationBuilder cb = new ConfigurationBuilder();
  cb.setDebugEnabled(true);
  //simple http form based authentication, you can use oAuth if you have one, check Twitter4j documentation
  cb.setUser("Jorge24Jimenez");
  cb.setPassword("george24");

  // creating the twitter listener

  Configuration cfg = cb.build();
  TwitterStream twitterStream = new TwitterStreamFactory(cfg).getInstance();

  //Configuración de filtro
  //FilterQuery filterQuery=new FilterQuery(int 0, long[] follow, java.lang.String[] track, double[][] locations);
  FilterQuery filterQuery=new FilterQuery();
  final String[] words2track={"copu","df","azul"};
  filterQuery.track(words2track);
  //Termina filtro

  StatusListener listener = new StatusListener() {
   public void onStatus(Status status) {

    //if (status.getText().indexOf("lol") > 0) {
    //if (status.getText().indexOf("menso") > 0) {
     System.out.println("********* Semantic found *************:"+status.getText());
     //raiseEvent(epService, status.getUser().getScreenName(), status);
     raiseEvent(status.getUser().getScreenName(), status);
    //}
   }

   public void onDeletionNotice(
     StatusDeletionNotice statusDeletionNotice) {
     //System.out.println("Jorge Got a status deletion notice id:"
      //+ statusDeletionNotice.getStatusId());
   }

   public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
    System.out.println("Jorge Got track limitation notice:"
      + numberOfLimitedStatuses);
   }

   public void onScrubGeo(long userId, long upToStatusId) {
    System.out.println("Jorge Got scrub_geo event userId:" + userId
      + " upToStatusId:" + upToStatusId);
   }

   public void onException(Exception ex) {
    ex.printStackTrace();
   }
  };
  //twitterStream.addListener(listener);
  //twitterStream.filter(filterQuery);

  //
  //twitterStream.sample();

 }

 private static void raiseEvent(String name, Status status) {
  //HappyMessage msg = new HappyMessage();
  //msg.setUser(status.getUser().getScreenName());
  //epService.getEPRuntime().sendEvent(msg);
 }

/*
 private static void raiseEvent(EPServiceProvider epService, String name,
   Status status) {
  HappyMessage msg = new HappyMessage();
  msg.setUser(status.getUser().getScreenName());
  //epService.getEPRuntime().sendEvent(msg);
 }
*/

}
