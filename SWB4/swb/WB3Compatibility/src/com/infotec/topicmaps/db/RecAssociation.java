/**
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
**/


/*
 * RecAssociation.java
 *
 * Created on 5 de julio de 2002, 12:29
 */

package com.infotec.topicmaps.db;

import java.sql.*;

import java.util.*;

import com.infotec.appfw.exception.*;
import com.infotec.appfw.lib.DBPool.DBConnectionManager;
import com.infotec.appfw.lib.AFObserver;
import com.infotec.wb.lib.WBDBRecord;
import com.infotec.appfw.util.db.ObjectDecoder;
import com.infotec.appfw.util.db.ObjectEncoder;

/** objeto: referencia al registro de la base de datos de la table wbassociation
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class RecAssociation implements WBDBRecord
{

    private ArrayList observers = new ArrayList();
    private ArrayList notifys = new ArrayList();

    private String id;
    private String idtm;
    private String xml;
    private Timestamp lastupdate;
    
    private boolean virtual=false;    

    /** Creates new RecAssociation */
    public RecAssociation()
    {
        this.id = null;
        this.idtm = null;
        this.xml = null;
        this.lastupdate = null;
//        if(WBLoader.getInstance().haveDBTables())
        {
            registerObserver(DBTopicMap.getInstance());
            //registerObserver(UsrMgr.getInstance());
        }
    }

    public RecAssociation(String id)
    {
        this();
        this.id = id;
    }

    /** Creates new RecAssociation */
    public RecAssociation(String id, String idtm, String xml, Timestamp lastupdate)
    {
        this();
        this.id = id;
        this.idtm = idtm;
        this.xml = xml;
        this.lastupdate = lastupdate;
    }
    
    public RecAssociation(ObjectDecoder dec)
    {
        this();
        this.id = dec.getString(0);
        this.idtm = dec.getString(1);
        this.xml = dec.getString(2);
        this.lastupdate = dec.getTimeStamp(3);
    }
    
    public ObjectEncoder getEncoder()
    {
        ObjectEncoder enc=new ObjectEncoder("RecAssociation");
        enc.addString(id);
        enc.addString(idtm);
        enc.addString(xml);
        enc.addTimestamp(lastupdate);
        return enc;
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

    /** Getter for property idtm.
     * @return Value of property idtm.
     */
    public java.lang.String getIdtm()
    {
        return idtm;
    }

    /** Setter for property idtm.
     * @param idtm New value of property idtm.
     */
    public void setIdtm(java.lang.String idtm)
    {
        this.idtm = idtm;
    }

    /** Getter for property xml.
     * @return Value of property xml.
     */
    public java.lang.String getXml()
    {
        return xml;
    }

    /** Setter for property xml.
     * @param xml New value of property xml.
     */
    public void setXml(java.lang.String xml)
    {
        this.xml = xml;
    }

    /** Getter for property lastupdate.
     * @return Value of property lastupdate.
     */
    public java.sql.Timestamp getLastupdate()
    {
        return lastupdate;
    }

    /** Setter for property lastupdate.
     * @param lastupdate New value of property lastupdate.
     */
    public void setLastupdate(java.sql.Timestamp lastupdate)
    {
        this.lastupdate = lastupdate;
    }

    /**
     * Getter for property virtual.
     * @return Value of property virtual.
     */
    public boolean isVirtual()
    {
        return virtual;
    }    
    
    /**
     * Setter for property virtual.
     * @param virtual New value of property virtual.
     */
    public void setVirtual(boolean virtual)
    {
        this.virtual = virtual;
    }    
    
    /** registra el objeto observador para que pueda recibir notoficaciones de cambios
     */
    public void registerObserver(AFObserver obs)
    {
        if (!observers.contains(obs)) observers.add(obs);
    }

    /**  Elimina el registro de la base de datos asi como todopublic void remove() throws AFException */
    public void remove() throws AFException
    {
        DBConnectionManager mgr = null;
        Connection con;
        try
        {
            if(!virtual)
            {
                mgr = DBConnectionManager.getInstance();
                con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"));
                String query = "delete from wbassociation where id=? and idtm=?";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, id);
                st.setString(2, idtm);
                st.executeUpdate();
                st.close();
                con.close();
//                DBDbSync.getInstance().saveChange("wbassociation", "remove", 0, id + " " + idtm, null);
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("remove", this);
            }

        } catch (Exception e)
        {
            throw new AFException("No fue posible borrar el elemento...\n" + e.getMessage(), "RecAssociation:remove()");
        } finally
        {
            if (mgr != null) mgr.release();
        }
    }

    /** actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     */
    public void update() throws AFException
    {
        DBConnectionManager mgr = null;
        Connection con;
        try
        {
            lastupdate = new Timestamp(new java.util.Date().getTime());
            if(!virtual)
            {
                mgr = DBConnectionManager.getInstance();
                con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"));
                String query = "update wbassociation set xml=?,lastupdate=? where id=? and idtm=?";
                PreparedStatement st = con.prepareStatement(query);
                if (xml == null)
                    st.setString(1, null);
                else
                    st.setAsciiStream(1, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
                st.setTimestamp(2, lastupdate);
                st.setString(3, id);
                st.setString(4, idtm);
                st.executeUpdate();
                st.close();
                con.close();
//                DBDbSync.getInstance().saveChange("wbassociation", "update", 0, id + " " + idtm, lastupdate);
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("update", this);
            }

        } catch (Exception e)
        {
            throw new AFException("No fue posible actualizar el elemento...\n" + e.getMessage(), "RecAssociation:update()");
        } finally
        {
            if (mgr != null) mgr.release();
        }
    }

    /** crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     */
    public void create() throws AFException
    {
        DBConnectionManager mgr = null;
        Connection con;
        try
        {
            lastupdate = new Timestamp(new java.util.Date().getTime());
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"));
            String query = "insert into wbassociation (xml,lastupdate,id,idtm) values (?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            if (xml == null)
                st.setString(1, null);
            else
                st.setAsciiStream(1, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
            st.setTimestamp(2, lastupdate);
            st.setString(3, id);
            st.setString(4, idtm);
            st.executeUpdate();
            st.close();
            con.close();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("create", this);
            }
//            DBDbSync.getInstance().saveChange("wbassociation", "create", 0, id + " " + idtm, lastupdate);

        } catch (Exception e)
        {
            throw new AFException("No fue posible crear el elemento...\n" + e.getMessage(), "RecAssociation:create()");
        } finally
        {
            if (mgr != null) mgr.release();
        }
    }

    /** refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     */
    public void load() throws AFException
    {
        DBConnectionManager mgr = null;
        Connection con;
        try
        {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"));
            String query = "select * from wbassociation where id=? and idtm=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, id);
            st.setString(2, idtm);
            ResultSet rs = st.executeQuery();
            if (rs.next())
            {
                xml = com.infotec.appfw.util.AFUtils.getInstance().readInputStream(rs.getAsciiStream("xml"));
                lastupdate = rs.getTimestamp("lastupdate");
            }
            rs.close();
            st.close();
            con.close();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("load", this);
            }
        } catch (Exception e)
        {
            throw new AFException("No fue posible cargar el elemento...\n" + e.getMessage(), "RecAssociation:load()");
        } finally
        {
            if (mgr != null) mgr.release();
        }
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
                ((AFObserver) it.next()).sendDBNotify(message, this);
            }
            nt.remove();
        }
    }

    public void sendNotify(String message)
    {
        Iterator it = observers.iterator();
        while (it.hasNext())
        {
            ((AFObserver) it.next()).sendDBNotify(message, this);
        }
    }

    public void syncFromExternalAction(String action, Timestamp date) throws AFException
    {
//        if (getLastupdate() == null || (getLastupdate() != null && getLastupdate().before(date)))
//        {
//            if (action.equals("remove"))
//            {
//                sendNotify(action);
//                TopicMgr.getInstance().syncFromExternalAction(action, this);
//            } else if (action.equals("create") || action.equals("update"))
//            {
//                String oldxml = getXml();
//                load();
//                if (action.equals("create") || (action.equals("update") && (oldxml == null || !oldxml.equals(getXml()))))
//                    TopicMgr.getInstance().syncFromExternalAction(action, this);
//            }
//        }
    }

}
