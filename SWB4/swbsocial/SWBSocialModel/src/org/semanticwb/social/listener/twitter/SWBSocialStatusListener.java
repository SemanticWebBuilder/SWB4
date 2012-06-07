/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.listener.twitter;

import java.util.Date;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBModel;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialNetworkUser;
import org.semanticwb.social.listener.Classifier;
import org.semanticwb.social.util.SendPostThread;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;

/**
 *
 * @author jorge.jimenez
 */
public class SWBSocialStatusListener implements twitter4j.StatusListener {

    private static Logger log = SWBUtils.getLogger(SWBSocialStatusListener.class);
    SWBModel model=null;
    SocialNetwork socialNetwork=null;

    public SWBSocialStatusListener(SWBModel model, SocialNetwork socialNetwork)
    {
        this.model=model;
        this.socialNetwork=socialNetwork;
    }

    @Override
    public void onStatus(Status status) {
        try
        {
            //if (status.getGeoLocation() != null)

            Place place=status.getPlace();
            //Investigar cuales son los 4 puntos cardinales del bounding Box de México y los tweets que lleguen no deben ser mayores
            //a esos 4 float que lleguen, esto con los números que nos llegan en la parte de código que se encuentra comentada aquí abajo.
            /*
            if (null != place.getBoundingBoxCoordinates() && place.getBoundingBoxCoordinates().length > 0) {
                    GeoLocation[] boundingBox = place.getBoundingBoxCoordinates()[0];
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("[" + boundingBox[0].getLatitude() + ", " + boundingBox[0].getLongitude() + "], ");
                    buffer.append("[" + boundingBox[1].getLatitude() + ", " + boundingBox[1].getLongitude() + "], ");
                    buffer.append("[" + boundingBox[2].getLatitude() + ", " + boundingBox[2].getLongitude() + "], ");
                    buffer.append("[" + boundingBox[3].getLatitude() + ", " + boundingBox[3].getLongitude() + "]");
                    System.out.println("bufferJ:"+buffer.toString());
                    System.out.println("boundingBoxType:"+place.getBoundingBoxType());
            }
            */
            System.out.println("Cuenta en la que estoy:"+socialNetwork.getTitle());
            //if(place!=null) System.out.println("Country:"+place.getCountry()+",CountryCode:"+place.getCountryCode()+", PlaceName:"+place.getName()+", PlaceFullName:"+place.getFullName()+", PlaceStreetAdress:"+place.getStreetAddress());
            //else System.out.println("place es NULO...");
            //if(place!=null && place.getCountryCode().equals("MX")) //&& place.getName().equals("Cuernavaca"))
            {

                System.out.println();
                System.out.println(status.getUser().getName() + " : " + status.getText() + " : " + status.getGeoLocation());
                System.out.println(status.getCreatedAt());
                MediaEntity[] medianEntities=status.getMediaEntities();
                if(medianEntities!=null)
                {
                    for(int i=0;i<medianEntities.length;i++)
                    {
                        MediaEntity mediaEntity=medianEntities[i];
                        System.out.println("Media DisplayUrl:"+mediaEntity.getDisplayURL());
                        System.out.println("Media ExpandedUrl:"+mediaEntity.getExpandedURL());
                        System.out.println("Media MediaURLFile:"+mediaEntity.getMediaURL().getFile());
                        System.out.println("Media URLString:"+mediaEntity.getURL().toString());
                    }
                }
                //Persistencia del mensaje
                MessageIn mesagge=MessageIn.ClassMgr.createMessageIn(String.valueOf(status.getId()), model);
                mesagge.setMsg_Text(status.getText());
                mesagge.setPostInSocialNetwork(socialNetwork);
                if(status.getUser()!=null)
                {
                    long userId=status.getUser().getId();
                    String name=status.getUser().getScreenName();
                    Date userNetworkCreatedDate=status.getUser().getCreatedAt();
                    int followers=status.getUser().getFollowersCount();
                    int friends=status.getUser().getFriendsCount();
                    SocialNetworkUser socialNetUser=SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(""+userId, socialNetwork, model);
                    if(socialNetUser==null)//
                    {
                        //Si no existe el id del usuario para esa red social, lo crea.
                        socialNetUser=SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);
                        socialNetUser.setSnu_id(""+userId);
                        socialNetUser.setSnu_name(name);
                        socialNetUser.setSnu_SocialNetwork(socialNetwork);
                        socialNetUser.setCreated(userNetworkCreatedDate);
                        //System.out.println("SocialNetworkUser Creado:"+socialNetUser.getSnu_id());
                    }else{
                        //System.out.println("SocialNetworkUser Actualizado:"+socialNetUser.getSnu_id());
                        socialNetUser.setUpdated(new Date());
                    }
                    socialNetUser.setFollowers(followers);
                    socialNetUser.setFriends(friends);
                    //int listedCount=status.getUser().getListedCount();
                    //System.out.println("userId:"+userId+", created:"+socialNetUser.getCreated()+", followers:"+socialNetUser.getFollowers()+", friends:"+socialNetUser.getFriends()+", name:"+socialNetUser.getSnu_name());
                    if(socialNetUser!=null)
                    {
                        mesagge.setPostInSocialNetworkUser(socialNetUser);
                    }
                }
                socialNetwork.addReceivedPost(mesagge, String.valueOf(status.getId()), socialNetwork);
                new Classifier(mesagge);
            }
      }catch(Exception e){
        e.printStackTrace();
        log.error(e);
      }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice sdn) {
    }

    @Override
    public void onTrackLimitationNotice(int i) {
    }

    @Override
    public void onScrubGeo(long l, long l1) {
    }

    @Override
    public void onException(Exception excptn) {
    }

}
