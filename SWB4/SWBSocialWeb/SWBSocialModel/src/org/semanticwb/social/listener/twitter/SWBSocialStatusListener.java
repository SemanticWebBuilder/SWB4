/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.listener.twitter;

import java.util.ArrayList;
import java.util.HashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.ExternalPost;
import org.semanticwb.social.SocialNetStreamSearch;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.Stream;
import org.semanticwb.social.listener.Classifier;
import org.semanticwb.social.util.SWBSocialUtil;
import twitter4j.GeoLocation;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterStream;

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
    SWBModel model = null;
    SocialNetwork socialNetwork = null;
    Stream stream = null;
    HashMap<String, TwitterStream> ListenAlives;
    int  tweetsReceived = 0;
    long currentTweetID = 0L;
    ArrayList aListExternalPost=new ArrayList();

    /*
     * Metodo constructor el cual recibe como parametros
     * @param model is the SWBModel object related with the listener
     * @param socialNetwork is the SocialNetwork object related with the instance of the listener
     * @param stream is the stream configured to hear the listener.
     */
    public SWBSocialStatusListener(Stream stream, SocialNetwork socialNetwork, HashMap ListenAlives) {
        if (stream == null || socialNetwork == null) {
            return;
        }
        this.socialNetwork = socialNetwork;
        this.stream = stream;
        WebSite wsite = WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        this.model = wsite;
        this.ListenAlives = ListenAlives;
        System.out.println("Entra a crear SWBSocialStatusListener...");
    }

    /*
     * Metodo que obtiene todos los mensajes provenientes de twitter de un determinado listener de red social y los
     * guarda y envía a clasificar
     * @param status objeto de tipo twitter4j.Status el cual tiene toda la información de un mensaje proveniente del listener
     * de la red social de twitter
     */
    @Override
    public void onStatus(Status status) {
        try {
            System.out.println("Entra a SWBSocialStatusListener/onStatus-0:" + socialNetwork);
            //El siguiente bloque de código (if) es NECESARIO, sirve para detener este thread de la librería twitter4J (onStatus), por si en algún momento
            //se cambia el stream en la propiedad "keepAliveManager" de true a false o si se elimina la instancia de red social la cual se esta
            //monitoreando, este thread nunca lo sabría y no se tiene manera de detenerlo desde el LitenerMgr, es por eso que se agrega aquí
            //DEBEMOS agregarlo en todos las clases de red social que soporten Listener de tipo KeepAlive (En este momento solo es Twitter).
            if ((!stream.isActive() || stream.getPhrase() == null || !stream.isKeepAliveManager() || !stream.hasSocialNetwork(socialNetwork)) && (ListenAlives != null
                    && ListenAlives.containsKey(stream.getURI() + "|" + socialNetwork.getURI()))) {
                TwitterStream twitterStream = ListenAlives.get(stream.getURI() + "|" + socialNetwork.getURI());
                if (twitterStream != null) {
                    twitterStream.cleanUp();
                    twitterStream.shutdown();
                    ListenAlives.remove(stream.getURI() + "|" + socialNetwork.getURI());
                    System.out.println("DETUVO LA CONEXION EN SWBSocialStatusListener/onStatus-de:" + stream.getURI() + "|" + socialNetwork.getURI());
                    return;
                }
            }
            //Termina Bloque de código que debemos agregar, De manera personalizada para c/red social, cuando soporten Listener de tipo KeepAlive.
            
            //Empieza a generar objetos de tipo ExternalPost con c/tweet que llega
            if(!status.isRetweet())
            {
                if(status.getUser() != null){
                    ExternalPost external = new ExternalPost();
                    external.setPostId(String.valueOf(status.getId())); 
                    external.setCreatorId(String.valueOf(status.getUser().getId()));
                    external.setCreatorName("@"+status.getUser().getScreenName());
                    external.setCreationTime(""+status.getCreatedAt());
                    external.setDevice(status.getSource());
                    if (status.getText()!=null) {
                       external.setMessage(status.getText());
                    }                            
                    external.setPostType(SWBSocialUtil.MESSAGE);   //TODO:VER SI SIEMPRE EN TWITTER LE DEBO DE PONER ESTE TIPO O TAMBIÉN LE PUDIERA PONER QUE ES DE TIPO FOTO
                    external.setFollowers(status.getUser().getFollowersCount());
                    external.setFriendsNumber(status.getUser().getFriendsCount());
                    //System.out.println("status.getUser().getLocation()----->"+status.getUser().getLocation());
                    
                    if(status.getPlace()!=null)
                    {
                        external.setPlace(status.getPlace().getFullName());
                        if(status.getPlace().getBoundingBoxCoordinates()!=null && status.getPlace().getBoundingBoxCoordinates().length > 0)
                        {
                            GeoLocation[] boundingBox = status.getPlace().getBoundingBoxCoordinates()[0];

                            double latCenterPoint=((boundingBox[3].getLatitude()-boundingBox[0].getLatitude())/2)+boundingBox[0].getLatitude();
                            double lngCenterPoint=((boundingBox[1].getLongitude()-boundingBox[0].getLongitude())/2)+boundingBox[0].getLongitude();

                            //System.out.println("Punto 5--JJ:["+latCenterPoint+"," +lngCenterPoint+"]");

                            external.setLatitude(latCenterPoint);
                            external.setLongitude(lngCenterPoint);

                            if(status.getPlace().getCountryCode()!=null)
                            {
                                external.setCountryCode(status.getPlace().getCountryCode().toUpperCase());
                            }
                        }
                    }else if(status.getUser().getLocation()!=null && !status.getUser().getLocation().isEmpty()){
                        //System.out.println("Pone en External UserGeo:"+status.getUser().getLocation());
                        external.setUserGeoLocation(status.getUser().getLocation());
                    }
                    
                    
                    external.setSocialNetwork(socialNetwork);
                    external.setPostShared((int)status.getRetweetCount());    
                    external.setCreatorPhotoUrl(status.getUser().getBiggerProfileImageURL());
                    aListExternalPost.add(external);

                    currentTweetID = status.getId();
                    tweetsReceived++;
                    //System.out.println("TweetNumber:"+tweetsReceived+",User: @" + status.getUser().getScreenName() + "\tID:" + status.getId() + "\tTime:" + status.getCreatedAt() + "\tText:" + status.getText());
                    
                    if(tweetsReceived>=1 && aListExternalPost.size()>0){
                        //System.out.println("Va a enviar a Clasificador--1");
                        new Classifier(aListExternalPost, stream, socialNetwork, true);
                        setLastTweetID(currentTweetID, stream);//Save
                        tweetsReceived=0;
                        aListExternalPost=new ArrayList();
                        //System.out.println("Va a enviar a Clasificador--2");
                    }
                }
            }
            //System.out.println("Entra a SWBSocialStatusListener/onStatus-1:" + socialNetwork);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
    }
    
    /**
     * Sets the value to NextDatetoSearch. Verifies whether the passed value is greater than the stored or not.
     */
    private void setLastTweetID(Long tweetID, Stream stream){        
        try{
            SocialNetStreamSearch socialStreamSerch=SocialNetStreamSearch.getSocialNetStreamSearchbyStreamAndSocialNetwork(stream, socialNetwork);
            socialStreamSerch.setNextDatetoSearch(tweetID.toString());
        }catch(NumberFormatException nfe){
            log.error("Error in setLastTweetID():"  +nfe);
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
