/*
 * SWBHitCounter.java
 *
 * Created on 6 de octubre de 2002, 15:03
 */

package org.semanticwb.portal.access;

import java.util.*;
import java.text.*;

import java.sql.Timestamp;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/** objeto: cuenta el resumen de hits a secciones y lo almacena a DB cada "wb/accessLogTime" intervalo de tiempo, por defecto: 60 segundos
 * @author Javier Solis Gonzalez
 */
public class SWBHitCounter
{
    public static Logger log = SWBUtils.getLogger(SWBHitCounter.class);

    private long hits = 0;
    private boolean changed = false;
    private Date date = new Date();
    private long timer;               //valores de sincronizacion de views, hits
    private static long time;                //tiempo en milisegundos por cada actualizacion
    private String patern = null;
    private String map = null;
    private String id = "_";
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

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    /** Creates a new instance of SWBHitCounter */
    public SWBHitCounter()
    {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        patern = df.format(date);
    }

    /** Creates a new instance of SWBHitCounter
     * @param hits  */
    public SWBHitCounter(long hits)
    {
        this();
        this.hits = hits;
    }

    /** Creates a new instance of SWBHitCounter
     * @param map
     * @param id
     * @param type
     * @param hits  */
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
     * @param map
     * @param id
     * @param type
     * @param hits
     * @param sdate  */
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
     * @param sdate  */
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
     * @param sdate  */    
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
    
    public void finalize()
    {
        reset(patern);
    }
    
    public void update()
    {
        reset(patern);
    }
}