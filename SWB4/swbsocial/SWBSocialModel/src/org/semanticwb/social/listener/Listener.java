/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.listener;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialSite;
import org.semanticwb.social.Stream;

/**
 *
 * @author jorge.jimenez
 */

/*
 *  Clase cuya funcionalidad es la de generar un timer por cada uno de los streams existentes y activos en todas las Marcas
 *  de una instancia de la herramienta swbsocial.
 *  Esta clase es llamada cuando se levanta el appserver mediante el archivo startup.properties o mediante la creación y/o edición
 *  de streams.
 */
public class Listener implements SWBAppObject {

    private static Logger log = SWBUtils.getLogger(Listener.class);
    //Timer timer;
    static Hashtable<String, Timer> htTimers = new Hashtable();
    static private Listener instance;
    static final int MILISEG_IN_SEGUNDO=1000;
    static ReBindThread rbThread=null;

    /**
     * Retrieves a reference to the only one existing object of this class.
     * <p>Obtiene una referencia al &uacute;nico objeto existente de esta clase.</p>
     * @return a reference to the only one existing object of this class
     */
    static public synchronized Listener createInstance() {
        if (Listener.instance == null) {
            Listener.instance = new Listener();
        }
        return Listener.instance;
    }

    /*
     * Metodo constructor que levanta listener de cada uno de los streams de cada sitio de tipo SWBSocial
     */
    public Listener() {
        try {
            Iterator<WebSite> itWebSites = SWBContext.listWebSites(false);
            while (itWebSites.hasNext()) {
                WebSite wsite = itWebSites.next();
                if (wsite instanceof SocialSite) {
                    Iterator<Stream> itStreams = Stream.ClassMgr.listStreams(wsite);
                    while (itStreams.hasNext()) {
                        Stream stream = itStreams.next();
                        if (createTimer(stream))
                        {
                            int periodTime = stream.getPoolTime()*MILISEG_IN_SEGUNDO;
                            Timer timer = new Timer();
                            timer.schedule(new ListenerTask(stream), 0,periodTime);
                            htTimers.put(stream.getURI(), timer);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    /*
     * Metodo que crea y actualiza timers
     */
    public static boolean createUpdateTimers(Stream stream)
    {
        //System.out.println("rbThread:"+rbThread);
        try
        {
            //synchronized(stream)
            {
                if(rbThread==null)  //TODO:Ver que hago cuando puedan llegar algunos otros stream que se modifiquen cuando aun no haya terminado el thread.
                {
                    //System.out.println("Entra a Enviar a ReBind...");
                    rbThread=new ReBindThread(stream);
                    rbThread.sleep(2*MILISEG_IN_SEGUNDO);
                    rbThread.start();
                    rbThread=null;
                    return true;
                }
            }
        }catch(Exception e)
        {
            log.error(e);
        }
        return false;
    }

    /*
     * Metodo que regenera un determinado thread de un determinado stream
     */
    static class ReBindThread extends Thread {
        Stream stream=null;
        public ReBindThread(Stream stream) {
            this.stream=stream;
        }
        public void run() {
            //sleep(2 * 1000);
            //System.out.println("Entra a run...");
            createUpdateTimersReBind(stream);
            //System.out.println("DONE! " + getName());
        }
    }

    /*
     * Metodo que crea o actualiza un thread de un determinado stream
     */
    private static boolean createUpdateTimersReBind(Stream stream)
    {
        //System.out.println("Entra a Listener/createUpdateTimersReBind-1");
        /*
        Iterator<SocialNetwork> itNets=stream.listSocialNetworks();
        while(itNets.hasNext())
        {
            SocialNetwork socialNet=itNets.next();
            System.out.println("SocialNetwork que tiene el stream:"+socialNet.getURI());
        }*/
        if(htTimers.get(stream.getURI())!=null)
        {
            try
            {
                //System.out.println("Entra a Listener/createUpdateTimers-2:"+stream.getURI());
                if(!createTimer(stream))
                {
                    //System.out.println("Entra a Listener/createUpdateTimers-3:"+stream.getURI());
                    removeTimer(stream);
                    System.out.println("Elimino timer k");
                }else
                {
                    //System.out.println("Entra a Listener/createUpdateTimers-4:"+stream.getURI());
                    Timer timer=removeTimer(stream);
                    timer=new Timer();
                    timer.schedule(new ListenerTask(stream), 0,stream.getPoolTime()*MILISEG_IN_SEGUNDO);
                    htTimers.put(stream.getURI(), timer);
                }
                //System.out.println("Entra a Listener/createUpdateTimers-5:"+stream.getURI());
                return true;
            }catch(Exception e)
            {
                //System.out.println("Error:"+e.getMessage());
                log.error(e);
            }
        }else
        {
            if(createTimer(stream))
            {
                //Se arranca un timer que se ejecutara cada tantos segundos configurados en el stream
                System.out.println("Levanta Timer");
                Timer timer = new Timer();
                timer.schedule(new ListenerTask(stream), 0,stream.getPoolTime()*MILISEG_IN_SEGUNDO); 
                htTimers.put(stream.getURI(), timer);
                return true;
            }
        }
        return false;
    }
     

    /*
     * Metodo que elimina un thread de un stream
     */
     public static Timer removeTimer(Stream stream)
     {
        try
        {
            if(htTimers.get(stream.getURI())!=null)
            {
                System.out.println("Entra a removeTimer de stream:"+stream.getURI());
                Timer timer=htTimers.get(stream.getURI());
                htTimers.remove(stream.getURI());
                timer.cancel();
                timer.purge();
                timer=null;
                return timer;
            }
         }catch(Exception e)
         {
           //System.out.println("Error:"+e.getMessage());
           log.error(e);
         }
        return null;
     }

    /**
     * Clase de tipo Timer, hecha a andar las redes sociales adjudicadas a un stream.
     */ 
    private static class ListenerTask extends TimerTask
    {
        Stream stream=null;

        public ListenerTask(Stream stream)
        {
            this.stream=stream;
        }

        public void run() {
            System.out.println("Ejecuta Timer");
            Iterator<SocialNetwork> itSocialNets=stream.listSocialNetworks();
            while(itSocialNets.hasNext())
            {
                SocialNetwork socialNet=itSocialNets.next();
                System.out.println("Ejecuta Red Social/Listen:"+socialNet.getId());
                socialNet.listen(stream);
            }
        }
     }

    /*
     * Metodo cuya funcionalidad es la de verificar si se podría crear un thread de acuerdo a los datos que trae un stream dado.
     */
    private static boolean createTimer(Stream stream)
    {
        if(stream.isActive() && stream.getPoolTime() > 0 && stream.getPhrase()!=null && stream.getPhrase().trim().length()>0 && stream.listSocialNetworks().hasNext())
        {
            return true;
        }
        return false;
    }


    @Override
    public void init() {
        //new Listener();
    }

    @Override
    public void destroy() {
        htTimers.clear();
    }

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /*
     * Metodo de prueba
     */
    public static void main(String args[]) {
        System.out.println("About to schedule task.");
        new Listener(); //C/cuantos segundos se ejecutara la tarea
        System.out.println("Task scheduled.");
    }
}
