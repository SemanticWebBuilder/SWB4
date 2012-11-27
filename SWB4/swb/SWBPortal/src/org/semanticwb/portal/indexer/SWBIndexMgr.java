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
package org.semanticwb.portal.indexer;

import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.model.Indexable;
import org.semanticwb.model.SWBModel;

// TODO: Auto-generated Javadoc
/**
 * Manager for the {@link SWBIndexer}s used by Semantic WebBuilder.
 * <p>
 * Administrador para los indexadores ({@link SWBIndexers}) usados por
 * Semantic WebBuilder.
 * 
 * @author Javier Solis Gonzalez
 */
public class SWBIndexMgr implements SWBAppObject
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SWBIndexMgr.class);

    /** The indexers. */
    private HashMap<String, SWBIndexer> indexers=new HashMap();
    
    /** The prop. */
    private Properties prop;   
    
    /** The default indexer. */
    private String defaultIndexer="swb";
    
    /** The timer. */
    private Timer timer = null;
    
    /** The lastupdate. */
    private Timestamp lastupdate;

    
    /**
     * Creates a new instance of SWBIndexMgr.
     * <p>
     * Crea una nueva instancia de SWBIndexMgr.
     */
    public SWBIndexMgr()
    {
        this.lastupdate = new Timestamp(new java.util.Date().getTime());
//        if(!SWBPortal.isClient())
//        {
            log.event("Initializing SWBIndexMgr");
//        }else
//        {
//            log.info("WBIndexMgr can not be Initialized (isClient)...");
//        }
        //init();
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.base.SWBAppObject#destroy()
     */
    /**
     * Destroy.
     */
    public void destroy()
    {
        timer.cancel();
        log.event("Webbuilder SWBIndexMgr Stoped");
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.base.SWBAppObject#init()
     */
    /**
     * Inits the.
     */
    public void init()
    {
//        if(SWBPortal.isClient())return;
        
        prop=SWBUtils.TEXT.getPropertyFile("/indexer.properties");
        Enumeration propNames = prop.propertyNames();
        while (propNames.hasMoreElements())
        {
            String name = (String) propNames.nextElement();
            //System.out.println("name:"+name);
            if (name.endsWith(".class"))
            {
                String pname = name.substring(0, name.lastIndexOf("."));
                String clsname = prop.getProperty(pname + ".class");
                log.info("Initializing Indexer: "+pname);
                
                try
                {
                    Class cls = Class.forName(clsname);
                    SWBIndexer ind = (SWBIndexer)cls.newInstance();
                    if(ind!=null)
                    {
                        indexers.put(pname,ind);
                        if(SWBPortal.isClient())
                        {
                            ind.init(pname);
                        }else
                        {
                            ind.init(pname, prop);
                        }
                    }
                }catch(Exception e)
                {
                    log.error(e);
                }
                
            }
        }
       
        defaultIndexer=prop.getProperty("defaultIndexer","swb");

        //Timer
//        int delays=Integer.parseInt(prop.getProperty("delay","30"));
//        TimerTask t=new TimerTask()
//        {
//            public void run()
//            {
//                _run();
//            }
//        };
//        timer = new Timer();
//        timer.schedule(t, delays*1000, delays*1000);
     
    }
    
    /** Get Instance. */

    public void _run()
    {
        //System.out.println("indexMgr run()");
        //if (indexer == null || indexer.indexer == null || !indexer.indexer.isAlive())
        {
            //System.out.println("IndexMgr->Indexando cambios... "+lastupdate);
            try
            {
                //SELECT log_modelid, log_objuri, log_date FROM swb_admlog where log_date>"2009-04-25 22:59:10" group by log_modelid, log_objuri, log_date order by log_date
                //TODO:
                /*
                Iterator iter = DBDbSync.getInstance().getChanges(lastupdate);
                while (iter.hasNext())
                {
                    RecDbSync rec = (RecDbSync) iter.next();
                    String table = rec.getDbTable();
                    String action = rec.getAction();
                    long id = rec.getIdint();
                    String ids = rec.getIdstr();
                    Timestamp date = rec.getDate();

                    //System.out.println("WBIndexMgr-->rec:"+table+" "+action+" "+id+" "+ids);
                    if (date.after(lastupdate)) lastupdate = date;
                    try
                    {
                        if (table.equals("wboccurrence"))
                        {
                            if (!action.equals("load"))
                            {
                                StringTokenizer st = new StringTokenizer(ids, " ");
                                if (st.hasMoreTokens())
                                {
                                    String occid = st.nextToken();
                                    String tmid = st.nextToken();
                                    SWBIndexer indexer=getTopicMapIndexer(tmid);
                                    if(indexer!=null)
                                    {
                                        String tpid = st.nextToken();

                                        WebSite tm = SWBContext.getWebSite(tmid);
                                        if (tm != null)
                                        {
                                            WebPage tp = tm.getWebPage(tpid);
                                            if (tp != null)
                                            {
                                                Occurrence occ = tp.getOccurrence(occid);
                                                if (occ != null)
                                                {
                                                    WebPage ttype = occ.getInstanceOf().getTopicRef();
                                                    if (ttype != null && ttype.getId().equals("REC_WBContent"))
                                                    {
                                                        long rid=Long.parseLong(occ.getResourceData());
                                                        //System.out.println("remove:"+rid);
                                                        indexer.removeContent(rid, tp.getMap().getId());
                                                        if(occ.isActive())
                                                        {
                                                            //System.out.println("index:"+rid);
                                                            indexer.indexContent(rid, tp);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (table.equals("wbresource"))
                        {
                            if (!action.equals("load"))
                            {
                                SWBIndexer indexer=getTopicMapIndexer(ids);
                                if(indexer!=null)
                                {
                                    Iterator it=indexer.search4Id(ids+" "+id).iterator();
                                    if(it.hasNext())
                                    {
                                        SWBIndexObj obj=(SWBIndexObj)it.next();
                                        indexer.removeContent(id, ids);
                                        WebSite map=SWBContext.getWebSite(obj.getTopicMapID());
                                        if(map!=null)
                                        {
                                            WebPage tpi=map.getWebPage(obj.getTopicID());
                                            if(tpi!=null)
                                            {
                                                indexer.indexContent(id, tpi);
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (table.equals("wbtopic"))
                        {
                            if (!action.equals("load"))
                            {
                                StringTokenizer st = new StringTokenizer(ids, " ");
                                if (st.hasMoreTokens())
                                {
                                    String tpid = st.nextToken();
                                    String tmid = st.nextToken();
                                    SWBIndexer indexer=getTopicMapIndexer(tmid);
                                    if(indexer!=null)
                                    {
                                        if (action.equals("remove"))
                                        {
                                            indexer.removeTopic(tmid,tpid,true);
                                        } else
                                        {
                                            indexer.removeTopic(tmid,tpid,true);
                                            WebSite map=SWBContext.getWebSite(tmid);
                                            if(map!=null)
                                            {
                                                WebPage tpi=map.getWebPage(tpid);
                                                if(tpi!=null)
                                                {
                                                    indexer.indexWebPage(tpi,true);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e)
                    {
                        log.error(e);
                    }
                }
                */
            } catch (Exception e)
            {
                log.error(e);
            }
        }
    }    
    
    
    /* (non-Javadoc)
     * @see org.semanticwb.base.SWBAppObject#refresh()
     */
    /**
     * Refresh.
     */
    public void refresh()
    {
    }
    
    /**
     * Gets all the {@link SWBIndexer}s registered in the Manager.
     * <p>
     * Obtiene todos los indexadores registrados en el administrador.
     *
     * @return  Map with the registered {@link SWBIndexer}s. Mapa con los
     *          {@link SWBIndexer}s registrados.
     */
    public java.util.HashMap getIndexers()
    {
        return indexers;
    }
    
    /**
     * Gets a specific {@link SWBIndexer} with the given name.
     * <p>
     * Obtiene un {@link SWBIndexer} con el nombre especificado.
     * 
     * @param name  the name of the requested {@link SWBIndexer}. Nombre del
     * @return      the {@link SWBIndexer}.
     * {@link SWBIndexer} requerido.
     */
    public SWBIndexer getIndexer(String name)
    {
        return (SWBIndexer)indexers.get(name);
    }    
    
    /**
     * Gets the {@link SWBIndexer} for the specified {@link SWBModel}.
     * <p>
     * Obtiene el {@link SWBIndexer} para el modelo especificado.
     * 
     * @param model the model
     * @return  the model indexer.
     */
    public SWBIndexer getModelIndexer(SWBModel model)
    {
        SWBIndexer ret=null;
        //System.out.println("getTopicMapIndexer:"+tmid);
        if(model!=null)
        {
            //System.out.println("isIndeable:"+tm.isIndexable());
            //TODO:Agregar indexador al modelo
            //return (SWBIndexer)indexers.get(tm.getIndexer());
            if(model instanceof Indexable)
            {
                if(((Indexable)model).isIndexable())
                {
                    ret=getDefaultIndexer();
                }
            }
        }
        return ret;
    }        

    /**
     * Gets the default {@link SWBIndexer} for Semantic WebBuilder.
     * <p>
     * Obtiene el {@link SWBIndexer} definido como indexador predeterminado para
     * Semantic WebBuilder.
     * 
     * @return the default indexer.
     */
    public SWBIndexer getDefaultIndexer()
    {
        return (SWBIndexer)indexers.get(defaultIndexer);
    }
}
