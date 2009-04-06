
/*
 * RecResHits.java
 *
 * Created on 17 de julio de 2002, 12:29
 */

package org.semanticwb.portal;

import java.sql.*;

import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBObserver;
import org.semanticwb.util.ObjectDecoder;
import org.semanticwb.util.ObjectEncoder;


/** objeto: cache de registro de base de datos de la tabla wbreshits
 *
 * object: record cache of the data base of the wbreshits table
 *
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class RecResHits //implements WBDBRecord
{
    public static Logger log = SWBUtils.getLogger(RecResHits.class);

    private ArrayList observers = new ArrayList();
    private ArrayList notifys = new ArrayList();

    private Timestamp date;
    private String topicmap;
    private String idaux;
    private int type;
    private long hits;

    /** Creates new RecResHits */
    public RecResHits()
    {
        this.date = null;
        this.topicmap = "";
        this.idaux = "";
        this.type = 0;
        this.hits = 0;
    }

    /**
     * @param date
     * @param topicmap
     * @param idaux
     * @param type
     * @param hits  */
    public RecResHits(Timestamp date, String topicmap, String idaux, int type, long hits)
    {
        this();
        this.date = date;
        this.topicmap = topicmap;
        this.idaux = idaux;
        this.type = type;
        this.hits = hits;
    }
    
    public RecResHits(ObjectDecoder dec)
    {
        this.date = dec.getTimeStamp(0);
        this.topicmap = dec.getString(1);
        this.idaux = dec.getString(2);
        this.type= dec.getInt(3);
        this.hits = dec.getLong(4);
    }
    
    public ObjectEncoder getEncoder()
    {
        ObjectEncoder enc=new ObjectEncoder("RecResHits");
        enc.addTimestamp(date);
        enc.addString(topicmap);
        enc.addString(idaux);
        enc.addInt(type);
        enc.addLong(hits);
        return enc;
    }
    
    /**
     * @param rs  */
    public RecResHits(ResultSet rs)
    {
        this(rs,true);
    }

    /**
     * @param rs  */
    public RecResHits(ResultSet rs, boolean hasWBDate)
    {
        this();
        try
        {
            if(hasWBDate)this.date = rs.getTimestamp("wbdate");
            this.topicmap = rs.getString("topicmap");
            this.idaux = rs.getString("idaux");
            this.type = rs.getInt("type");
            this.hits = rs.getLong("hits");
        } catch (Exception e)
        {
            log.error(e);
        }
    }


    /** Getter for property date.
     * @return Value of property date.
     */
    public Timestamp getdate()
    {
        return date;
    }

    /** Setter for property date.
     * @param date New value of property date.
     */
    public void setDate(Timestamp date)
    {
        this.date = date;
    }

    /** Getter for property topicmap.
     * @return Value of property topicmap.
     */
    public java.lang.String getTopicMap()
    {
        return topicmap;
    }

    /** Setter for property topicmap.
     * @param topicmap  */
    public void setTopicMap(java.lang.String topicmap)
    {
        this.topicmap = topicmap;
    }

    /** Getter for property idaux.
     * @return Value of property idaux.
     */
    public java.lang.String getIdAux()
    {
        return idaux;
    }

    /** Setter for property idaux.
     * @param idaux New value of property idaux.
     */
    public void setIdAux(java.lang.String idaux)
    {
        this.idaux = idaux;
    }

    /** Getter for property type.
     * @return Value of property type.
     */
    public int getType()
    {
        return type;
    }

    /** Setter for property type.
     * @param type  */
    public void setType(int type)
    {
        this.type = type;
    }


    /** Getter for property hits.
     * @return Value of property hits.
     */
    public long getHits()
    {
        return hits;
    }

    /** Setter for property hits.
     * @param hits  */
    public void setHits(int hits)
    {
        this.hits = hits;
    }

    /** registra el objeto observador para que pueda recibir notoficaciones de cambios
     * @param obs  */
    public void registerObserver(SWBObserver obs)
    {
        if (!observers.contains(obs)) observers.add(obs);
    }

    /** Elimina el registro de la base de datos asi como todopublic vodate remove() throws AFException
     * @param user
     * @param comment
     * @throws com.infotec.appfw.exception.AFException  */
    public void remove(long user, String comment) throws SWBException
    {

    }

    /** Elimina el registro de la base de datos asi como todopublic vodate remove() throws AFException
     * @throws com.infotec.appfw.exception.AFException  */
    public void remove() throws SWBException
    {

    }


    /** actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void update() throws SWBException
    {
        Connection con=null;
        try
        {
            //System.out.println("update wbreshits set hits="+hits+" where date="+date+" and topicmap="+topicmap+" and idaux="+idaux+" and type="+type);
            con = SWBUtils.DB.getDefaultConnection();
            String query = "update wbreshits set hits=?, wbdate=? where wbdate=? and topicmap=? and idaux=? and type=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setLong(1, hits);
            st.setTimestamp(2, date);
            st.setTimestamp(3, date);
            st.setString(4, topicmap);
            st.setString(5, idaux);
            st.setInt(6, type);
            st.executeUpdate();
            st.close();
            con.close();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((SWBObserver) it.next()).sendDBNotify("update", this);
            }

        } catch (Exception e)
        {
            log.error(e);
            throw new SWBException("RecResHits Uupdate Element Error" + "...", e);
        } 
    }

    /** crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void create() throws SWBException
    {
        Connection con=null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();

            String query = "insert into wbreshits (topicmap,idaux,type,hits,wbdate) values (?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, topicmap);
            st.setString(2, idaux);
            st.setInt(3, type);
            st.setLong(4, hits);
            st.setTimestamp(5, date);
            st.executeUpdate();
            st.close();
            con.close();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((SWBObserver) it.next()).sendDBNotify("create", this);
            }

        } catch (Exception e)
        {
            log.error(e);
            throw new SWBException("RecResHits Create Element Error" + "...", e);
        } 
    }

    /** refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void load() throws SWBException
    {
        Connection con=null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            String query = "select * from wbreshits where wbdate=? and topicmap=? and idaux=? and type=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setTimestamp(1, date);
            st.setString(2, topicmap);
            st.setString(3, idaux);
            st.setInt(4, type);
            ResultSet rs = st.executeQuery();
            if (rs.next())
            {

                hits = rs.getLong("hits");
            }
            rs.close();
            st.close();
            con.close();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((SWBObserver) it.next()).sendDBNotify("load", this);
            }
        } catch (Exception e)
        {
            log.event(e);
            throw new SWBException("RecResHits Load Element Error" + "...", e);
        } 
    }


    /** refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     * @return
     * @throws com.infotec.appfw.exception.AFException  */
    public boolean exist() throws SWBException
    {
        boolean ret = false;
        Connection con=null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            String query = "select * from wbreshits where wbdate=? and topicmap=? and idaux=? and type=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setTimestamp(1, date);
            st.setString(2, topicmap);
            st.setString(3, idaux);
            st.setInt(4, type);
            ResultSet rs = st.executeQuery();
            if (rs.next()) ret = true;
            rs.close();
            st.close();
            con.close();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((SWBObserver) it.next()).sendDBNotify("load", this);
            }
        } catch (Exception e)
        {
            throw new SWBException("RecResHits Load Element Error" + "...", e);
        } 
        return ret;
    }


    public void sendNotify()
    {
        Iterator nt = notifys.iterator();
        while (nt.hasNext())
        {
            String message = (String) nt.next();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((SWBObserver) it.next()).sendDBNotify(message, this);
            }
            nt.remove();
        }
    }

    /**
     * @param message  */
    public void sendNotify(String message)
    {
        Iterator it = observers.iterator();
        while (it.hasNext())
        {
            ((SWBObserver) it.next()).sendDBNotify(message, this);
        }
    }


}
