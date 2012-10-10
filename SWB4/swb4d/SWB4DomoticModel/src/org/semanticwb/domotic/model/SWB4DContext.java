/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.domotic.model;

import org.semanticwb.base.SWBAppObject;
import org.semanticwb.domotic.server.Server;
import org.semanticwb.model.SWBModel;


/**
 *
 * @author javier.solis.g
 */
public class SWB4DContext implements SWBAppObject
{
    /** The instance. */
    private static SWB4DContext instance = null;    
    private static Server server=null;
    
    
    /**
     * Instantiates a new DomContext.
     */
    private SWB4DContext() 
    {
        server=new Server();
        server.start();
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
