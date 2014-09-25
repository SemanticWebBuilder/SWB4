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

import java.io.IOException;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author jorge.jimenez
 */
public class Test {

    public static void main(String[] args) throws TwitterException, IOException {
        //El resultado sera en la misma unidad de medida que sea parado el Radio
        /*
        double EARTRADIUS_KM = 6371.01;   //KM
        double EARTRADIUS_MI = 3958.762079;
        GeoLocation myLocation = GeoLocation.fromDegrees(19.319901, -99.152130);
        double distance = 50;   //El radio que le estamos enviando, este es variable
        GeoLocation[] boundingCoordinates =myLocation.boundingCoordinates(distance, EARTRADIUS_KM);
        System.out.println("boundingCoordinates:"+boundingCoordinates);
      
        //NE
        System.out.println("N:"+boundingCoordinates[1].getLatitudeInDegrees());
        System.out.println("E:"+boundingCoordinates[1].getLongitudeInDegrees());
        
        //SW
        System.out.println("S:"+boundingCoordinates[0].getLatitudeInDegrees());
        System.out.println("W:"+boundingCoordinates[0].getLongitudeInDegrees());
        
        */
        
        
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
                System.out.println(status.getUser().getName() + " : " + status.getText());
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

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
       
        TwitterStream twitterStream = new TwitterStreamFactory(configureOAuth().build()).getInstance();
        twitterStream.addListener(listener);
        
        
        FilterQuery query = new FilterQuery();
        {
            //String[] tr = {"infotec"};
            //query.track(tr);
        }
        
        //double[][] boundingBox2FindTweets = {{19.048220, -99.364067}, 
        //    {19.591579, -98.940193}};
        
        //query.locations(boundingBox2FindTweets);
        
        twitterStream.filter(query);
        
        
        // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.sample();
       
    }
    
    
     /**
     * Returns the configuration object with OAuth Credentials
     * Anonymous access is not allowed
     */
    private static ConfigurationBuilder configureOAuth(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("V5Xp0RYFuf3N0WsHkqSOIQ")
          .setOAuthConsumerSecret("4DZ9UrE4X5VavUjXzBcGFTvEsHVsCGOgIuLVSZMA8")
          .setOAuthAccessToken("47531700-lyEHlP3671bb3IcFKKc5dueGmJTmnIZ4ZrMkD8RY")
          .setOAuthAccessTokenSecret("8je7NslBcJyV3OW9SBzaM3yslAPjPCSVAN1PLafqc"); 
        return cb;
    }
}
