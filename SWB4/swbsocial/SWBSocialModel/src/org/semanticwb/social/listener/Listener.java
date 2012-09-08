/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.listener;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.KeepAliveListenerable;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialSite;

/**
 *
 * @author jorge.jimenez
 */
public class Listener implements SWBAppObject {

  private static Logger log = SWBUtils.getLogger(Listener.class);

  Timer timer;

  public Listener() {
    try{
        timer = new Timer();
        
        Iterator <WebSite> itWebSites=SWBContext.listWebSites(false);
        while(itWebSites.hasNext())
        {
            WebSite wsite=itWebSites.next();
            if(wsite.isActive() && wsite instanceof SocialSite)
            {
                System.out.println("wsite a monitorear en Listener:"+wsite);
                Iterator<SocialNetwork> itSocialNetWorks=SocialNetwork.ClassMgr.listSocialNetworks(wsite);
                while(itSocialNetWorks.hasNext())
                {
                    SocialNetwork socialNet=itSocialNetWorks.next();
                    int periodTime=socialNet.getPoolTime();
                    //Peridicidad de ejecución en milisegundos (3600 segundos x 1000 ms en un segundo=1 hr por defecto)
                    if(periodTime==0) periodTime=3600*1000;
                    if(socialNet instanceof KeepAliveListenerable)
                    {
                        KeepAliveListenerable keepAliveListenerable=(KeepAliveListenerable)socialNet;
                        if(keepAliveListenerable.isIsKeepingConnection()) //Tiene la propiedad de mantener la conexión en true, por lo tanto no enviar a timer
                        {
                            //System.out.println("ES KEPING ALIVE:"+socialNet.getId()+", title:"+socialNet.getTitle());
                            System.out.println("wsite a monitorear en Listener alive:"+wsite+", cuenta:"+socialNet);
                            keepAliveListenerable.listenAlive(wsite);
                        }else { //La red soporta una conexión abierta, tipo twitter con Streaming Api, pero no desean que se maneje así, quieren que sea por petición
                            //System.out.println("ES KEPING ALIVE *pero no se quiere así*:"+socialNet.getId()+", title:"+socialNet.getTitle());
                            timer.schedule(new ListenerTask(socialNet, wsite), 0, periodTime);
                        }
                    }else { //La red no es de tipo KeepAliveListenerable, es decir, no soporta una conexión abierta
                        //System.out.println("NO ES KEEPING ALIVE-Entra J1/Red ID:"+socialNet.getId()+", Title:"+socialNet.getTitle());
                        timer.schedule(new ListenerTask(socialNet, wsite), 0,periodTime);
                    }
                }
            }
        }
      }catch(Exception e){
          log.error(e);
      }
    
  }

    @Override
    public void init() {
        //new Listener();
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

  class ListenerTask extends TimerTask {

    SocialNetwork socialNet=null;
    SWBModel model=null;

    public ListenerTask(SocialNetwork socialNet, SWBModel model)
    {
        this.socialNet=socialNet;
        this.model=model;
    }

    public void run() {
      //System.out.println("NO ES KEEPING ALIVE-Entra J2/Red ID:"+socialNet.getId()+", Title:"+socialNet.getTitle());
      //Llamado a todos los metodos listener de las redes sociales, ver si c/una la meto en un thread x separado
       socialNet.listen(model);
       //System.exit(0);
    }
  }

  public static void main(String args[]) {
    System.out.println("About to schedule task.");
    new Listener(); //C/cuantos segundos se ejecutara la tarea
    System.out.println("Task scheduled.");
  }


}
