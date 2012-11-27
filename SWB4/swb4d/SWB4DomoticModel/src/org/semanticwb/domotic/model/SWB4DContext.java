/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
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
