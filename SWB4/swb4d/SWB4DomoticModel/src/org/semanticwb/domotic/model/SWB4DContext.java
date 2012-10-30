/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.domotic.model;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.domotic.server.Server;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.WebSite;


/**
 *
 * @author javier.solis.g
 */
public class SWB4DContext implements SWBAppObject
{
    /** The instance. */
    private static SWB4DContext instance = null;    
    private static Server server=null;
    
    private static Timer timer=null;
    
    
    /**
     * Instantiates a new DomContext.
     */
    private SWB4DContext() 
    {
        server=new Server();
        server.start();
        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run()
            {
                chechOnSchedules();
            }
        }, 60000L, 60000L);
    }
    
    
    public void chechOnSchedules()
    {
        System.out.println("Run...");
        Iterator<WebSite> it=SWBContext.listWebSites(false);
        while (it.hasNext())
        {
            WebSite webSite = it.next();
            if(webSite instanceof DomiticSite)
            {
                DomiticSite dom=(DomiticSite)webSite;
                Iterator<OnSchedule> it2=dom.listOnSchedules();
                while (it2.hasNext())
                {
                    OnSchedule onSchedule = it2.next();
                    onSchedule.chechSchedule();
                }
            }
        }
    }
    
    /**
     * Instantiates a new DomContext.
     */
    private SWB4DContext(SWBModel model) 
    {
        server=new Server(model);
        server.start();
    }
    
    /**
     * Creates the instance.
     * 
     * @return the DomContext
     */
    static public synchronized SWB4DContext getInstance() {
        if (instance == null) {
            instance = new SWB4DContext();
        }
        return instance;
    }    

    /**
     * Creates the instance.
     * 
     * @return the DomContext
     */
    static public synchronized SWB4DContext getInstance(SWBModel model) {
        if (instance == null) {
            instance = new SWB4DContext(model);
        }
        return instance;
    }

    public static Server getServer()
    {
        return server;
    }

    @Override
    public void init()
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void destroy()
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refresh()
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
