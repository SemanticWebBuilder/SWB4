/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swbsocial.test;

import java.util.Date;
import java.util.List;
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 *
 * @author jorge.jimenez
 */
public class SearchTest {

    public static void main(String[] args) {
/*
		String token = "277181746-i5plkIhZlZfszXdNnVFtZ2X8fB2duyBn79eDHTUM";
		String tokenSecret = "yGVTgEBNwCbMtGkbi2Qz1dR51mZLsKzPmJJOUUrYvDU";
                Twitter twitter = new TwitterFactory().getInstance();

		//TwitterService ts = new TwitterService(token, tokenSecret);
                
		try {
                        Query query=new Query("Semantic");
                        query.setRpp(20);
                        query.setPage(1);

                        QueryResult result = twitter.search(query);
                        
			List<Tweet> tweets = result.getTweets();

			for (Tweet tweet : tweets) {

				// System.out.println("@" + tweet.getFromUser() + " - "
				// + tweet.getText());

				System.out.print("!!! Twit !!! ");

				Date createdAt = tweet.getCreatedAt();
				String suser = tweet.getFromUser();
				long userId = tweet.getFromUserId();

				double latitud = -1, longitud = -1;
				try {
					GeoLocation geoLocation = tweet.getGeoLocation();
					latitud = geoLocation.getLatitude();
					longitud = geoLocation.getLongitude();
				} catch (Exception e) {
				}

				long id = tweet.getId();
				String isoLanguageCode = tweet.getIsoLanguageCode();
				String location = tweet.getLocation();

				Place place = tweet.getPlace();

				Place[] containedWithIn = null;
				String country = null;
				String url = null;
				String fullName = null;
				GeoLocation[][] boundingBoxCoordinates = null;
				String geometryType = null;
				String idPlace = null;
				String name = null;
				String placeType = null;
				String streetAddress = null;
				String urlPlace = null;

				try {
					containedWithIn = place.getContainedWithIn();
					country = place.getCountry();
					url = place.getCountryCode();
					fullName = place.getFullName();
					boundingBoxCoordinates = place.getBoundingBoxCoordinates();
					geometryType = place.getGeometryType();
					idPlace = place.getId();
					name = place.getName();
					placeType = place.getPlaceType();
					streetAddress = place.getStreetAddress();
					urlPlace = place.getURL();
				} catch (Exception e) {
				}

				String profileImageUrl = tweet.getProfileImageUrl();
				String text = tweet.getText();
				String source = tweet.getSource();
				String toUser = tweet.getToUser();
				long toUserId = tweet.getToUserId();

                                int followerUsers=0;
                                try{
                                    System.out.println("toUserId Solicitado:"+userId);
                                    User user=twitter.showUser(userId);
                                    followerUsers=user.getFollowersCount();
                                    System.out.println("Nombre de usuario:"+user.getName());
                                    System.out.println("Following:"+user.getFriendsCount());
                                    System.out.println("followerUsers:"+followerUsers);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }


				System.out.println("createdAt: " + createdAt + ", " + "user: "
						+ suser + "," + "userId: " + userId + "," + "latitud: "
						+ latitud + "," + "longitud: " + longitud + ","
						+ "id: " + id + "," + "isoLanguageCode: "
						+ isoLanguageCode + "," + "location: " + location + ","
						+ "place: " + place + "," + "containedWithIn: "
						+ containedWithIn + "," + "country: " + country + ","
						+ "url: " + url + "," + "fullName: " + fullName + ","
						+ "boundingBoxCoordinates: " + boundingBoxCoordinates
						+ "," + "geometryType: " + geometryType + ","
						+ "idPlace: " + idPlace + "," + "name: " + name + ","
						+ "placeType: " + placeType + "," + "streetAddress: "
						+ streetAddress + "," + "urlPlace: " + urlPlace + ","
						+ "profileImageUrl: " + profileImageUrl + ","
						+ "text: " + text + "," + "source: " + source + ","
						+ "toUser: " + toUser + "," + "toUserId: " + toUserId+", followerUsers:"+followerUsers);

                                //
                     
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}

}
