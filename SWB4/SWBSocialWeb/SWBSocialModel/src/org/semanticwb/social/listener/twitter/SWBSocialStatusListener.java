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
import org.semanticwb.social.Stream;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;

/**
 *
 * @author jorge.jimenez
 */

/*
 * Metodo cuya funcionalidad es la de obtener los mensajes que llegan por el listener de twitter
 * mediante la forma streaming de esta red social
 */

public class SWBSocialStatusListener implements twitter4j.StatusListener {

    private static Logger log = SWBUtils.getLogger(SWBSocialStatusListener.class);
    SWBModel model=null;
    SocialNetwork socialNetwork=null;
    Stream stream=null;

    /*
     * Metodo constructor el cual recibe como parametros
     * @param model is the SWBModel object related with the listener
     * @param socialNetwork is the SocialNetwork object related with the instance of the listener
     * @param stream is the stream configured to hear the listener.
     */
    public SWBSocialStatusListener(SWBModel model, SocialNetwork socialNetwork, Stream stream)
    {
        this.model=model;
        this.socialNetwork=socialNetwork;
        this.stream=stream;
    }

    /*
     * Metodo que obtiene todos los mensajes provenientes de twitter de un determinado listener de red social y los
     * guarda y envía a clasificar
     * @param status objeto de tipo twitter4j.Status el cual tiene toda la información de un mensaje proveniente del listener
     * de la red social de twitter
     */
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
            //System.out.println("Cuenta en la que estoy:"+socialNetwork.getTitle());
            if(place!=null) System.out.println("Country:"+place.getCountry()+",CountryCode:"+place.getCountryCode()+", PlaceName:"+place.getName()+", PlaceFullName:"+place.getFullName()+", PlaceStreetAdress:"+place.getStreetAddress());
            //else System.out.println("place es NULO...");
            //if(place!=null && place.getCountryCode().equals("MX")) //&& place.getName().equals("Cuernavaca"))
            {

                System.out.println("CUENTA EN SWBSocialStatusListener:"+socialNetwork);
                System.out.println(status.getUser().getName() + " : " + status.getText() + " : " + status.getGeoLocation());
                System.out.println(status.getCreatedAt());
               
                MediaEntity[] medianEntities=status.getMediaEntities();
                if(medianEntities!=null)
                {
                    for(int i=0;i<medianEntities.length;i++)
                    {
                        MediaEntity mediaEntity=medianEntities[i];
                        /*
                        System.out.println("Media DisplayUrl:"+mediaEntity.getDisplayURL());
                        System.out.println("Media ExpandedUrl:"+mediaEntity.getExpandedURL());
                        System.out.println("Media MediaURLFile:"+mediaEntity.getMediaURL().getFile());
                        System.out.println("Media URLString:"+mediaEntity.getURL().toString());
                         * */
                    }
                }
                //Persistencia del mensaje
                MessageIn message=MessageIn.ClassMgr.createMessageIn(String.valueOf(status.getId()), model);
                message.setMsg_Text(status.getText());
                message.setPostInSocialNetwork(socialNetwork);
                message.setPostInStream(stream);
                //System.out.println("Fuente:"+status.getSource());
                message.setPostRetweets(Integer.parseInt(""+status.getRetweetCount()));
                //System.out.println("Ya en Msg ReTweets:"+message.getPostRetweets());
                if(status.getSource()!=null)    //Dispositivo utilizado
                {
                    String source=null;
                    int pos=status.getSource().indexOf(">");
                    if(pos>-1)
                    {
                        int pos1=status.getSource().indexOf("<",pos);
                        if(pos1>-1)
                        {
                            source=status.getSource().substring(pos+1, pos1);
                        }
                    }else{
                        source=status.getSource();
                    }
                   message.setPostSource(source);
                }
                //System.out.println("Ya en Msg source:"+source);

                if(status.getUser()!=null)
                {
                    long userId=status.getUser().getId();
                    String name=status.getUser().getScreenName();
                    Date userNetworkCreatedDate=status.getUser().getCreatedAt();
                    int followers=status.getUser().getFollowersCount();
                    int friends=status.getUser().getFriendsCount();
                    if(place!=null)
                    {
                        message.setPostPlace(place.getFullName());
                    }else if(status.getUser().getLocation()!=null)
                    {
                        message.setPostPlace(status.getUser().getLocation());
                    }
                    
                    SocialNetworkUser socialNetUser=SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(""+userId, socialNetwork, model);
                    if(socialNetUser==null)//
                    {
                        //Si no existe el id del usuario para esa red social, lo crea.
                        socialNetUser=SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);
                        socialNetUser.setSnu_id(""+userId);
                        socialNetUser.setSnu_name(name);
                        //socialNetUser.setSnu_SocialNetwork(socialNetwork);
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
                        message.setPostInSocialNetworkUser(socialNetUser);
                    }
                }
                socialNetwork.addReceivedPost(message, String.valueOf(status.getId()), socialNetwork);
                //new Classifier(message);
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

    @Override
    public void onStallWarning(StallWarning sw) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
