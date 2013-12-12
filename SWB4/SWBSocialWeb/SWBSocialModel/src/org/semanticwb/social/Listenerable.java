package org.semanticwb.social;

import org.json.JSONObject;

   /**
   * Esta interfaz se le agrega a las clases de tipo SocialNetwork que se va ha desear que escuchen en las redes sociales, lo cual en teoría deberían de ser a todas, pero por si en un futuro de desea que no sea así. 
   */
public interface Listenerable extends org.semanticwb.social.base.ListenerableBase
{
     public void listen(Stream stream);
     
     public void stopListen(Stream stream);
     
     public JSONObject getUserInfobyId(String userId);
   
}
