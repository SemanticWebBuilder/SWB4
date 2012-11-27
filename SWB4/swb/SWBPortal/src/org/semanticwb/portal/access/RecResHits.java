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

import java.sql.*;

import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBObserver;
import org.semanticwb.util.ObjectDecoder;
import org.semanticwb.util.ObjectEncoder;


// TODO: Auto-generated Javadoc
/**
 * objeto: cache de registro de base de datos de la tabla swb_reshits
 * 
 * object: record cache of the data base of the swb_reshits table.
 * 
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class RecResHits //implements WBDBRecord
{
    
    /** The log. */
    public static Logger log = SWBUtils.getLogger(RecResHits.class);

    /** The observers. */
    private ArrayList observers = new ArrayList();
    
    /** The notifys. */
    private ArrayList notifys = new ArrayList();

    /** The hits_date. */
    private Timestamp hits_date;
    
    /** The hits_modelid. */
    private String hits_modelid;
    
    /** The hits_objid. */
    private String hits_objid;
    
    /** The hits_type. */
    private int hits_type;
    
    /** The hits. */
    private long hits;

    /**
     * Creates new RecResHits.
     */
    public RecResHits()
    {
        this.hits_date = null;
        this.hits_modelid = "";
        this.hits_objid = "";
        this.hits_type = 0;
        this.hits = 0;
    }

    /**
     * Instantiates a new rec res hits.
     * 
     * @param hits_date the hits_date
     * @param hits_modelid the hits_modelid
     * @param hits_objid the hits_objid
     * @param hits_type the hits_type
     * @param hits the hits
     */
    public RecResHits(Timestamp hits_date, String hits_modelid, String hits_objid, int hits_type, long hits)
    {
        this();
        this.hits_date = hits_date;
        this.hits_modelid = hits_modelid;
        this.hits_objid = hits_objid;
        this.hits_type = hits_type;
        this.hits = hits;
    }
    
    /**
     * Instantiates a new rec res hits.
     * 
     * @param dec the dec
     */
    public RecResHits(ObjectDecoder dec)
    {
        this.hits_date = dec.getTimeStamp(0);
        this.hits_modelid = dec.getString(1);
        this.hits_objid = dec.getString(2);
        this.hits_type= dec.getInt(3);
        this.hits = dec.getLong(4);
    }
    
    /**
     * Gets the encoder.
     * 
     * @return the encoder
     */
    public ObjectEncoder getEncoder()
    {
        ObjectEncoder enc=new ObjectEncoder("RecResHits");
        enc.addTimestamp(hits_date);
        enc.addString(hits_modelid);
        enc.addString(hits_objid);
        enc.addInt(hits_type);
        enc.addLong(hits);
        return enc;
    }
    
    /**
     * Instantiates a new rec res hits.
     * 
     * @param rs the rs
     */
    public RecResHits(ResultSet rs)
    {
        this(rs,true);
    }

    /**
     * Instantiates a new rec res hits.
     * 
     * @param rs the rs
     * @param hasWBDate the has wb date
     */
    public RecResHits(ResultSet rs, boolean hasWBDate)
    {
        this();
        try
        {
            if(hasWBDate)this.hits_date = rs.getTimestamp("hits_date");
            this.hits_modelid = rs.getString("hits_modelid");
            this.hits_objid = rs.getString("hits_objid");
            this.hits_type = rs.getInt("hits_type");
            this.hits = rs.getLong("hits");
        } catch (Exception e)
        {
            log.error(e);
        }
    }


    /** Getter for property hits_date.
     * @return Value of property hits_date.
     */
    public Timestamp getdate()
    {
        return hits_date;
    }

    /** Setter for property hits_date.
     * @param hits_date New value of property hits_date.
     */
    public void setDate(Timestamp hits_date)
    {
        this.hits_date = hits_date;
    }

    /** Getter for property hits_modelid.
     * @return Value of property hits_modelid.
     */
    public java.lang.String getModelId()
    {
        return hits_modelid;
    }

    /**
     * Setter for property hits_modelid.
     * 
     * @param hits_modelid the new model id
     */
    public void setModelId(java.lang.String hits_modelid)
    {
        this.hits_modelid = hits_modelid;
    }

    /** Getter for property hits_objid.
     * @return Value of property hits_objid.
     */
    public java.lang.String getObjectId()
    {
        return hits_objid;
    }

    /** Setter for property hits_objid.
     * @param hits_objid New value of property hits_objid.
     */
    public void setObjectId(java.lang.String hits_objid)
    {
        this.hits_objid = hits_objid;
    }

    /** Getter for property hits_type.
     * @return Value of property hits_type.
     */
    public int getType()
    {
        return hits_type;
    }

    /**
     * Setter for property hits_type.
     * 
     * @param hits_type the new type
     */
    public void setType(int hits_type)
    {
        this.hits_type = hits_type;
    }


    /** Getter for property hits.
     * @return Value of property hits.
     */
    public long getHits()
    {
        return hits;
    }

    /**
     * Setter for property hits.
     * 
     * @param hits the new hits
     */
    public void setHits(int hits)
    {
        this.hits = hits;
    }

    /**
     * registra el objeto observador para que pueda recibir notoficaciones de cambios.
     * 
     * @param obs the obs
     */
    public void registerObserver(SWBObserver obs)
    {
        if (!observers.contains(obs)) observers.add(obs);
    }

    /**
     * Elimina el registro de la base de datos asi como todopublic vodate remove() throws AFException.
     * 
     * @param user the user
     * @param comment the comment
     * @throws SWBException the sWB exception
     */
    public void remove(long user, String comment) throws SWBException
    {

    }

    /**
     * Elimina el registro de la base de datos asi como todopublic vodate remove() throws AFException.
     * 
     * @throws SWBException the sWB exception
     */
    public void remove() throws SWBException
    {

    }


    /**
     * actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria.
     * 
     * @throws SWBException the sWB exception
     */
    public void update() throws SWBException
    {
        Connection con=null;
        try
        {
            //System.out.println("update swb_reshits set hits="+hits+" where hits_date="+hits_date+" and hits_modelid="+hits_modelid+" and hits_objid="+hits_objid+" and hits_type="+hits_type);
            con = SWBUtils.DB.getDefaultConnection();
            String query = "update swb_reshits set hits=?, hits_date=? where hits_date=? and hits_modelid=? and hits_objid=? and hits_type=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setLong(1, hits);
            st.setTimestamp(2, hits_date);
            st.setTimestamp(3, hits_date);
            st.setString(4, hits_modelid);
            st.setString(5, hits_objid);
            st.setInt(6, hits_type);
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
            throw new SWBException("RecResHits Update Element Error" + "...", e);
        } 
    }

    /**
     * crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria.
     * 
     * @throws SWBException the sWB exception
     */
    public void create() throws SWBException
    {
        Connection con=null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();

            String query = "insert into swb_reshits (hits_modelid,hits_objid,hits_type,hits,hits_date) values (?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, hits_modelid);
            st.setString(2, hits_objid);
            st.setInt(3, hits_type);
            st.setLong(4, hits);
            st.setTimestamp(5, hits_date);
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

    /**
     * refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria.
     * 
     * @throws SWBException the sWB exception
     */
    public void load() throws SWBException
    {
        Connection con=null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            String query = "select * from swb_reshits where hits_date=? and hits_modelid=? and hits_objid=? and hits_type=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setTimestamp(1, hits_date);
            st.setString(2, hits_modelid);
            st.setString(3, hits_objid);
            st.setInt(4, hits_type);
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


    /**
     * refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria.
     * 
     * @return true, if successful
     * @throws SWBException the sWB exception
     * @return
     */
    public boolean exist() throws SWBException
    {
        boolean ret = false;
        Connection con=null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            String query = "select * from swb_reshits where hits_date=? and hits_modelid=? and hits_objid=? and hits_type=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setTimestamp(1, hits_date);
            st.setString(2, hits_modelid);
            st.setString(3, hits_objid);
            st.setInt(4, hits_type);
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


    /**
     * Send notify.
     */
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
     * Send notify.
     * 
     * @param message the message
     */
    public void sendNotify(String message)
    {
        Iterator it = observers.iterator();
        while (it.hasNext())
        {
            ((SWBObserver) it.next()).sendDBNotify(message, this);
        }
    }


}
