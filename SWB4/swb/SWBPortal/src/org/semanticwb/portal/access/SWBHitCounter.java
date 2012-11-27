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
package org.semanticwb.portal.access;

import java.util.*;
import java.text.*;

import java.sql.Timestamp;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * objeto: cuenta el resumen de hits a secciones y lo almacena a DB cada "wb/accessLogTime" intervalo de tiempo, por defecto: 60 segundos.
 * 
 * @author Javier Solis Gonzalez
 */
public class SWBHitCounter
{
    
    /** The log. */
    public static Logger log = SWBUtils.getLogger(SWBHitCounter.class);

    /** The hits. */
    private long hits = 0;
    
    /** The changed. */
    private boolean changed = false;
    
    /** The date. */
    private Date date = new Date();
    
    /** The timer. */
    private long timer;               //valores de sincronizacion de views, hits
    
    /** The time. */
    private static long time;                //tiempo en milisegundos por cada actualizacion
    
    /** The patern. */
    private String patern = null;
    
    /** The map. */
    private String map = null;
    
    /** The id. */
    private String id = "_";
    
    /** The type. */
    private int type = 0;
    //private RecResHits res=null;

    static
    {
        time = 600000L;
        try
        {
            time = 1000L * Long.parseLong((String) SWBPlatform.getEnv("swb/accessLogTime"));
        } catch (Exception e)
        {
            log.error("SWBHitCounter Log Time Error...");
        }
    }

    /** The df. */
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates a new instance of SWBHitCounter.
     */
    public SWBHitCounter()
    {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        patern = df.format(date);
    }

    /**
     * Creates a new instance of SWBHitCounter.
     * 
     * @param hits the hits
     */
    public SWBHitCounter(long hits)
    {
        this();
        this.hits = hits;
    }

    /**
     * Creates a new instance of SWBHitCounter.
     * 
     * @param map the map
     * @param id the id
     * @param type the type
     * @param hits the hits
     */
    public SWBHitCounter(String map, String id, int type, long hits)
    {
        this();
        this.map = map;
        this.id = id;
        this.type = type;
        this.hits = hits;
        if (map == null) this.map = "_";
        if (id == null) this.id = "_";
    }

    /**
     * Instantiates a new sWB hit counter.
     * 
     * @param map the map
     * @param id the id
     * @param type the type
     * @param hits the hits
     * @param sdate the sdate
     */
    public SWBHitCounter(String map, String id, int type, long hits, String sdate)
    {
        this();
        log.debug("SWBHitCounter("+map+","+type+","+hits+","+sdate+")");
        //System.out.println("SWBHitCounter("+map+","+type+","+hits+","+sdate+")");
        this.map = map;
        this.id = id;
        this.type = type;
        this.hits = hits;
        patern = sdate;
        if (map == null) this.map = "_";
        if (id == null) this.id = "_";
        load(sdate);
    }

    /** Getter for property hits.
     * @return Value of property hits.
     */
    public long getHits()
    {
        return hits;
    }

    /** Setter for property hits.
     * @param hits New value of property hits.
     */
    public void setHits(long hits)
    {
        this.hits = hits;
    }

    /** Getter for property changed.
     * @return Value of property changed.
     */
    public boolean isChanged()
    {
        return changed;
    }

    /** Setter for property changed.
     * @param changed New value of property changed.
     */
    public void setChanged(boolean changed)
    {
        this.changed = changed;
    }

    /**
     * Hit.
     * 
     * @param sdate the sdate
     */
    public void hit(String sdate)
    {
        long t=System.currentTimeMillis()-timer;
        //System.out.println("hit() patern:"+patern +" date:"+sdate+" map:"+map+" id:"+id+" type:"+type+" t:"+t);
        if(sdate.equals(patern))
        {
            hits++;
            changed=true;
            if(t>time || t<-time)
            {
                reset(sdate);
            }
        }else
        {
            reset(sdate);
            hits++;
            changed=true;
        }
    }

    /** Getter for property date.
     * @return Value of property date.
     */
    public java.util.Date getDate()
    {
        return date;
    }

    /** Setter for property date.
     * @param date New value of property date.
     */
    public void setDate(java.util.Date date)
    {
        this.date = date;
    }

    /** Getter for property map.
     * @return Value of property map.
     */
    public java.lang.String getMap()
    {
        return map;
    }

    /** Setter for property map.
     * @param map New value of property map.
     */
    public void setMap(java.lang.String map)
    {
        this.map = map;
    }

    /** Getter for property id.
     * @return Value of property id.
     */
    public java.lang.String getId()
    {
        return id;
    }

    /** Setter for property id.
     * @param id New value of property id.
     */
    public void setId(java.lang.String id)
    {
        this.id = id;
    }

    /** Getter for property type.
     * @return Value of property type.
     */
    public int getType()
    {
        return type;
    }

    /** Setter for property type.
     * @param type New value of property type.
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * Reset.
     * 
     * @param sdate the sdate
     */    
    public synchronized void reset(String sdate)
    {
        //WBUtils.getInstance().debug("SWBHitCounter:reset:"+sdate+" map:"+map+" id:"+id+" type:"+type);
        //System.out.println("SWBHitCounter:reset:"+sdate+" map:"+map+" id:"+id+" type:"+type);
        if(changed)
        {
            //System.out.println("reset up_create:"+sdate+" map:"+map+" id:"+id+" type:"+type);
            try
            {
                RecResHits res=new RecResHits(new Timestamp(date.getTime()),map,id,type,hits);
                if(res.exist())
                    res.update();
                else
                    res.create();
            }catch(Exception e){
                log.error("Error updating hits..., "+map+","+id+","+type+","+hits,e);
            }
        }    
        if(!patern.equals(sdate))
        {
            //System.out.println("reset load:"+sdate+" map:"+map+" id:"+id+" type:"+type);
            hits=0;
            changed=false;
            patern=sdate;
            load(sdate);
        }
        timer=System.currentTimeMillis();
    }
    
    /**
     * Load.
     * 
     * @param sdate the sdate
     */
    private void load(String sdate)
    {
        try
        {
            date=new Date(sdate.substring(5).replace('-','/')+"/"+sdate.substring(0,4));
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            
            RecResHits res=new RecResHits(new Timestamp(date.getTime()),map,id,type,hits);
            //if(res.exist())
            res.load();
            hits=res.getHits();
            //System.out.println("reset load hits:"+hits);
        }catch(Exception e)
        {
            log.error("SWBHitCounter No Hits Error",e);
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    public void finalize()
    {
        reset(patern);
    }
    
    /**
     * Update.
     */
    public void update()
    {
        reset(patern);
    }
}