/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.listener.twitter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.social.DevicePlatform;
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
    ArrayList<DevicePlatform> devicePlatform = new ArrayList<DevicePlatform>();

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
        Iterator<DevicePlatform> dpTmp = DevicePlatform.ClassMgr.listDevicePlatforms(SWBContext.getGlobalWebSite());
        while(dpTmp.hasNext()){
            DevicePlatform dp = dpTmp.next();
            devicePlatform.add(dp);
        }
        //System.out.println("Entra a crear SWBSocialStatusListener...");
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
            //System.out.println("Entra a SWBSocialStatusListener/onStatus-0:" + socialNetwork);
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
                    //System.out.println("DETUVO LA CONEXION EN SWBSocialStatusListener/onStatus-de:" + stream.getURI() + "|" + socialNetwork.getURI());
                    return;
                }
            }
            //Termina Bloque de código que debemos agregar, De manera personalizada para c/red social, cuando soporten Listener de tipo KeepAlive.
            
            //Empieza a generar objetos de tipo ExternalPost con c/tweet que llega
            //System.out.println("Entra a SWBSocialStatusListener/onStatus-1");
            if(!status.isRetweet())
            {
                //System.out.println("Entra a SWBSocialStatusListener/onStatus-2");
                if(status.getUser() != null){
                    //System.out.println("Entra a SWBSocialStatusListener/onStatus-3");
                    ExternalPost external = new ExternalPost();
                    external.setPostId(String.valueOf(status.getId())); 
                    external.setCreatorId(String.valueOf(status.getUser().getId()));
                    external.setCreatorName("@"+status.getUser().getScreenName());
                    external.setPostUrl("https://twitter.com/" + status.getUser().getScreenName() + "/status/" + String.valueOf(status.getId()));
                    external.setUserUrl("https://twitter.com/" + status.getUser().getScreenName());
                    if(status.getCreatedAt().before(new Date())){
                        external.setCreationTime(status.getCreatedAt());
                    }else{
                        external.setCreationTime(new Date());
                    }
                    //external.setDevice(status.getSource());
                    if (status.getText()!=null) {
                       external.setMessage(status.getText());
                    }                            
                    external.setCreatorPhotoUrl(status.getUser().getBiggerProfileImageURL());
                    external.setPostType(SWBSocialUtil.MESSAGE);   //TODO:VER SI SIEMPRE EN TWITTER LE DEBO DE PONER ESTE TIPO O TAMBIÉN LE PUDIERA PONER QUE ES DE TIPO FOTO
                    external.setFollowers(status.getUser().getFollowersCount());
                    external.setFriendsNumber(status.getUser().getFriendsCount());
                    external.setDevicePlatform(getDevicePlatform(devicePlatform, status.getSource()));
                    //System.out.println("status.getUser().getLocation()----->"+status.getUser().getLocation());
                    
                    if(status.getPlace()!=null)
                    {
                        external.setPlace(status.getPlace().getFullName());
                        if(status.getPlace().getBoundingBoxCoordinates()!=null && status.getPlace().getBoundingBoxCoordinates().length > 0)
                        {
                            GeoLocation[] boundingBox = status.getPlace().getBoundingBoxCoordinates()[0];

                            double latCenterPoint=((boundingBox[3].getLatitude()-boundingBox[0].getLatitude())/2)+boundingBox[0].getLatitude();
                            double lngCenterPoint=((boundingBox[1].getLongitude()-boundingBox[0].getLongitude())/2)+boundingBox[0].getLongitude();

                            //System.out.println("Punto 5:["+latCenterPoint+"," +lngCenterPoint+"]");

                            external.setLatitude(latCenterPoint);
                            external.setLongitude(lngCenterPoint);

                            if(status.getPlace().getCountryCode()!=null)
                            {
                                external.setCountryCode(status.getPlace().getCountryCode().toUpperCase());
                            }
                        }
                    }else if(status.getUser().getLocation()!=null && !status.getUser().getLocation().isEmpty()){
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
                        //System.out.println("Entra a SWBSocialStatusListener/onStatus-4-Clasificar..");
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

    
    private DevicePlatform getDevicePlatform(ArrayList<DevicePlatform> dp, String source){
        DevicePlatform validDP = null;
        
        if(source != null && !source.trim().isEmpty()){
            source = source.toLowerCase();
            for(int i = 0; i < dp.size(); i++){
                if(validDP != null){
                    break;
                }

                String tags = dp.get(i).getTags();
                if(tags != null && !tags.trim().isEmpty()){
                    String tagsArray[] = tags.toLowerCase().split(",");
                    if(tagsArray.length > 0){
                        for(int j = 0; j < tagsArray.length; j++){
                            if(source.contains(tagsArray[j])){
                                //validDP = dp.get(i);
                                validDP = (DevicePlatform)SemanticObject.createSemanticObject(dp.get(i).getURI()).createGenericInstance();
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(validDP == null){
            validDP = DevicePlatform.ClassMgr.getDevicePlatform("other", SWBContext.getGlobalWebSite());
            validDP = (DevicePlatform)SemanticObject.createSemanticObject(validDP.getURI()).createGenericInstance();
            
        }
        if(validDP != null){
            //System.out.println("validDP:" +validDP.getId());
        }
        
        return validDP;
    }
   
}
