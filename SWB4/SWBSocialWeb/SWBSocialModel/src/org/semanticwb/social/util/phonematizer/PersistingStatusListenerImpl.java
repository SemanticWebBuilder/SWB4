/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.util.phonematizer;

/**
 *
 * @author jorge.jimenez
 */



import java.util.ArrayList;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Location;
import twitter4j.Place;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.internal.json.DataObjectFactoryUtil;

/**
 *
 * @author Joel M. Rives
 * Jul 28, 2011
 */

public class PersistingStatusListenerImpl {//implements PersistingStatusListener, StatusListener { //Sacado de:http://code.google.com/p/informaticslab/source/browse/PHTweet/trunk/src/org/phiresearchlab/twitter/server/PersistingStatusListenerImpl.java?spec=svn266&r=266
/*
        private static final long ONE_MINUTE = 1000 * 60;
        private static final int MAX_RETRIES = 7;

        private Logger log = SWBUtils.getLogger(PersistingStatusListenerImpl.class);

        private long receivedCount = 0L;
        private long receivedTimestamp = 0L;
        private long averageReceivedCount = 0L;

        private TwitterStream twitterStream;
        private FilterQuery filterQuery;
        private boolean twitterError = false;

        private int retryCount = 0;
        private boolean sleeping = false;

        @Autowired private HashTagDAO hashTagDAO;
        @Autowired private LocationDAO locationDAO;
        @Autowired private TwitterService twitterService;

        public boolean hasError() {
                return twitterError;
        }

        public void onException(Exception e) {
                LOG.error("Failure receiving twitter stream", e);
                twitterError = true;
                DataObjectFactoryUtil.clearThreadLocalMap();

                TwitterException te = null;
                if (e instanceof TwitterException)
                        te = (TwitterException) e;

                retryConnection(te);
        }

        public void onDeletionNotice(StatusDeletionNotice arg0) {
                LOG.info("Received status deletion notice");
                DataObjectFactoryUtil.clearThreadLocalMap();
        }

        public void onScrubGeo(long arg0, long arg1) {
                LOG.info("Received scrubb geo request");
                DataObjectFactoryUtil.clearThreadLocalMap();
        }

        public void onStatus(Status status) {
                receivedCount++;

                if (receivedTimestamp == 0)
                        receivedTimestamp = System.currentTimeMillis();

                if ((System.currentTimeMillis() - receivedTimestamp) > ONE_MINUTE) {
                        averageReceivedCount = 0 == averageReceivedCount ? receivedCount : ((averageReceivedCount + receivedCount) / 2);
                        receivedCount = 0L;
                        receivedTimestamp = System.currentTimeMillis();
                }

                try {
                        saveTweet(status);
                } catch (RuntimeException e) {
                        throw e;
                } finally {
                        DataObjectFactoryUtil.clearThreadLocalMap();
                }
        }

        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                LOG.info("Received notice of " + numberOfLimitedStatuses + " limited statuses");
                DataObjectFactoryUtil.clearThreadLocalMap();
        }

        public void startCapturing(FilterQuery filterQuery) {
                if (null != twitterStream)
                        stopCapturing();

                this.filterQuery = filterQuery;
        twitterError = false;
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(this);
        twitterStream.filter(filterQuery);
        }

        public void stopCapturing() {
                twitterStream.shutdown();
                twitterStream.cleanUp();
                twitterStream = null;
                averageReceivedCount = 0L;
        }

        private String arrayToString(String[] array) {
                if (null == array)
                        return "";

                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < array.length; i++) {
                        buffer.append(array[i]);
                        if (i < (array.length - 1))
                                buffer.append(", ");
                }
                return buffer.toString();
        }

        private void retryConnection(TwitterException caught) {
                if (null != twitterStream)
                        stopCapturing();

                if (sleeping) {
                        LOG.info("Retry is currently sleeping");
                        return;
                }

                if (++retryCount >= MAX_RETRIES)  {
                        try {
                                LOG.info("Exceeded maximum retry count of " + MAX_RETRIES + ". Going to sleep for 3 minutes");
                                sleeping = true;
                                Thread.sleep(ONE_MINUTE * 3);
                                LOG.info("Waking up from sleep");
                                sleeping = false;
                                retryCount = 0;
                                startCapturing(filterQuery);
                        } catch (InterruptedException e) {
                                LOG.info("Waking up from sleep");
                                sleeping = false;
                                retryCount = 0;
                                startCapturing(filterQuery);
                        }

                        return;
                }

                if (null == caught) {
                        startCapturing(filterQuery);
                        return;
                }

                int delay;
                try {
                        delay = caught.getRetryAfter();
                } catch (Exception e1) {
                        delay = 10 * retryCount;
                }

                if (0 == delay) {
                        startCapturing(filterQuery);
                        return;
                }

                try {
                        LOG.info("Going to sleep based on Twitter recommended delay of " + delay + " seconds");
                        sleeping = true;
                        Thread.sleep(delay * 1000);
                        LOG.info("Waking up from sleep");
                        sleeping = false;
                        retryCount = 0;
                        startCapturing(filterQuery);
                } catch (InterruptedException e) {
                        LOG.info("Waking up from sleep");
                        sleeping = false;
                        retryCount = 0;
                        startCapturing(filterQuery);
                }
        }

        private void saveTweet(final Status status) {
                if (null == status)
                        return;

                try {
                    Tweet tweet = toTweet(status);
                    twitterService.createTweet(tweet);
                } catch (TransactionException e) {
                        LOG.error("Attempt to save tweet in transaction failed", e);
                }
        }

        private List<HashTag> toHashTags(HashtagEntity[] entities) {
                List<HashTag> list = new ArrayList<HashTag>();

                if (null == entities)
                        return list;

                for (HashtagEntity entity: entities) {
                        HashTag hashTag = new HashTag();
                        hashTag.setEnd(entity.getEnd());
                        hashTag.setStart(entity.getStart());
                        hashTag.setText(entity.getText());
                        hashTagDAO.persist(hashTag);
                        list.add(hashTag);
                }

                return list;
        }

        private Location toLocation(Place place) {
                if (null == place)
                        return null;

                Location loc = locationDAO.findByPlaceId(place.getId());

                if (null != loc)
                        return loc;

                loc = new Location();

                if (null != place.getBoundingBoxCoordinates() && place.getBoundingBoxCoordinates().length > 0) {
                        GeoLocation[] boundingBox = place.getBoundingBoxCoordinates()[0];
                        StringBuffer buffer = new StringBuffer();
                        buffer.append("[" + boundingBox[0].getLatitude() + ", " + boundingBox[0].getLongitude() + "], ");
                        buffer.append("[" + boundingBox[1].getLatitude() + ", " + boundingBox[1].getLongitude() + "], ");
                        buffer.append("[" + boundingBox[2].getLatitude() + ", " + boundingBox[2].getLongitude() + "], ");
                        buffer.append("[" + boundingBox[3].getLatitude() + ", " + boundingBox[3].getLongitude() + "]");
                        loc.setBoundingBoxCoordinates(buffer.toString());
                        loc.setBoundingBoxType(place.getBoundingBoxType());
                }

                List<Location> containedWithin = new ArrayList<Location>();
                if (null != place.getContainedWithIn()) {
                        for (Place container: place.getContainedWithIn())
                                containedWithin.add(toLocation(container));
                }

                loc.setContainedWithin(containedWithin);
                loc.setCountry(place.getCountry());
                loc.setCountryCode(place.getCountryCode());
                loc.setFullName(place.getFullName());
                loc.setPlaceId(place.getId());
                loc.setName(place.getName());
                loc.setPlaceType(place.getPlaceType());
                loc.setStreetAddress(place.getStreetAddress());
                loc.setUrl(place.getURL());

                locationDAO.persist(loc);

                return loc;
        }

        private Tweet toTweet(Status status) {
            Tweet tweet = new Tweet();

            tweet.setContributors(arrayToString(status.getContributors()));
        tweet.setCreatedAt(status.getCreatedAt());
        if (null != status.getGeoLocation()) {
            tweet.setLatitude(status.getGeoLocation().getLatitude());
            tweet.setLongitude(status.getGeoLocation().getLongitude());
        }
        tweet.setHashTags(toHashTags(status.getHashtagEntities()));
        tweet.setStatusId(status.getId());
        tweet.setInReplyToStatusId(status.getInReplyToStatusId());
        tweet.setInReplyToUserId(status.getInReplyToUserId());
        tweet.setLocation(toLocation(status.getPlace()));
        tweet.setRetweetCount(status.getRetweetCount());
        tweet.setSource(status.getSource());
        tweet.setText(status.getText());
        tweet.setUserId(status.getUser().getId());
        tweet.setFavorited(status.isFavorited());
        tweet.setRetweeted(status.isRetweet());
        tweet.setTruncated(status.isTruncated());
        tweet.setAnalyzed(false);

        return tweet;
        }
*/
}

