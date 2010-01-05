/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * RecTopicMap.java
 *
 * Created on 5 de julio de 2002, 12:29
 */

package com.infotec.topicmaps.db;

import java.sql.*;
import java.io.*;

import com.infotec.wb.lib.*;

import java.util.*;

import com.infotec.appfw.exception.*;
import com.infotec.appfw.lib.DBPool.DBConnectionManager;
import com.infotec.wb.core.db.DBDbSync;
import com.infotec.appfw.lib.AFObserver;
import com.infotec.wb.lib.WBDBRecord;
import com.infotec.appfw.util.AFUtils;
import com.infotec.wb.util.*;
import com.infotec.wb.core.*;
import com.infotec.wb.core.db.*;
import com.infotec.topicmaps.bean.*;
import com.infotec.appfw.util.db.ObjectDecoder;
import com.infotec.appfw.util.db.ObjectEncoder;

import java.io.*;

/** objeto: referencia al registro de la base de datos de la table wbtopicmap
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class RecTopicMap implements WBDBRecord
{

    org.semanticwb.model.WebSite ws = null;

    public RecTopicMap(org.semanticwb.model.WebSite website)
    {
        ws = website;
    }

    public org.semanticwb.model.WebSite getNative()
    {
        return ws;
    }

    private ArrayList observers = new ArrayList();
    private ArrayList notifys = new ArrayList();

    private String id;
    private String idadm;
    private String title;
    private String home;
    private String lang;
    private String description;
    private int active;
    private String xml;
    private Timestamp created;
    private Timestamp lastupdate;
    private int deleted;
    private int system;
    private String repository;
    private String indexer;
    
    private boolean virtual=false;    

    /** Creates new RecTopicMap */
    public RecTopicMap()
    {
        this.id = null;
        this.idadm = "_";
        this.title = null;
        this.home = null;
        this.lang = null;
        this.description = null;
        this.active = 0;
        this.xml = null;
        this.created = null;
        this.lastupdate = null;
        this.deleted = 0;
        this.system=0;
        this.repository="wb";
        this.indexer=null;
        if(WBLoader.getInstance().haveDBTables())
        {
            registerObserver(DBTopicMap.getInstance());
            //registerObserver(DBVgContent.getInstance());
            //registerObserver(TopicMgr.getInstance());
        }
    }

    public RecTopicMap(String id)
    {
        this();
        this.id = id;
    }
    
    public RecTopicMap(String id, String idadm, String title, String home, String lang, String description, int active, String xml, Timestamp created, Timestamp lastupdate, int deleted, int system, String repository)
    {
        this(id,idadm,title,home,lang,description,active,xml,created,lastupdate,deleted,system,repository,"");
    }

    /** Creates new RecTopicMap */
    public RecTopicMap(String id, String idadm, String title, String home, String lang, String description, int active, String xml, Timestamp created, Timestamp lastupdate, int deleted, int system, String repository,String indexer)
    {
        this();
        this.id = id;
        this.idadm = idadm;
        this.title = title;
        this.home = home;
        this.lang = lang;
        this.description = description;
        this.active = active;
        this.xml = xml;
        this.created = created;
        this.lastupdate = lastupdate;
        this.deleted = deleted;
        this.system=system;
        this.repository=repository;
        this.indexer=indexer;
    }
    
    public RecTopicMap(ObjectDecoder dec)
    {
        this();
        this.id = dec.getString(0);
        this.idadm = dec.getString(1);
        this.title = dec.getString(2);
        this.home = dec.getString(3);
        this.lang = dec.getString(4);
        this.description = dec.getString(5);
        this.active = dec.getInt(6);
        this.xml = dec.getString(7);
        this.created = dec.getTimeStamp(8);
        this.lastupdate = dec.getTimeStamp(9);
        this.deleted = dec.getInt(10);
        this.system = dec.getInt(11);
        this.repository=dec.getString(12);
        if(dec.getSize()>13)
        {
            this.indexer=dec.getString(13);
        }        
        
    }
    
    public ObjectEncoder getEncoder()
    {
        ObjectEncoder enc=new ObjectEncoder("RecTopicMap");
        enc.addString(id);
        enc.addString(idadm);
        enc.addString(title);
        enc.addString(home);
        enc.addString(lang);
        enc.addString(description);
        enc.addInt(active);
        enc.addString(xml);
        enc.addTimestamp(created);
        enc.addTimestamp(lastupdate);
        enc.addInt(deleted);
        enc.addInt(system);
        enc.addString(repository);
        enc.addString(indexer);
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

    /** Getter for property idadm.
     * @return Value of property idadm.
     */
    public String getIdAdm()
    {
        return idadm;
    }

    /** Setter for property idadm.
     * @param idadm New value of property idadm.
     */
    public void setIdAdm(String idadm)
    {
        this.idadm = idadm;
    }

    /** Getter for property title.
     * @return Value of property title.
     */
    public java.lang.String getTitle()
    {
        return title;
    }

    /** Setter for property title.
     * @param title New value of property title.
     */
    public void setTitle(java.lang.String title)
    {
        this.title = title;
    }

    /** Getter for property home.
     * @return Value of property home.
     */
    public java.lang.String getHome()
    {
        return home;
    }

    /** Setter for property home.
     * @param home New value of property home.
     */
    public void setHome(java.lang.String home)
    {
        this.home = home;
    }

    /** Getter for property lang.
     * @return Value of property lang.
     */
    public java.lang.String getLang()
    {
        return lang;
    }

    /** Setter for property lang.
     * @param lang New value of property lang.
     */
    public void setLang(java.lang.String lang)
    {
        this.lang = lang;
    }

    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription()
    {
        return description;
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description)
    {
        this.description = description;
    }

    /** Getter for property active.
     * @return Value of property active.
     */
    public int getActive()
    {
        return active;
    }

    /** Setter for property active.
     * @param active New value of property active.
     */
    public void setActive(int active)
    {
        this.active = active;
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

    /** Getter for property created.
     * @return Value of property created.
     */
    public java.sql.Timestamp getCreated()
    {
        return created;
    }

    /** Setter for property created.
     * @param created New value of property created.
     */
    public void setCreated(java.sql.Timestamp created)
    {
        this.created = created;
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

    /** Getter for property deleted.
     * @return Value of property deleted.
     */
    public int getDeleted()
    {
        return deleted;
    }
    
    /** Setter for property deleted.
     * @param deleted New value of property deleted.
     */
    public void setDeleted(int deleted)
    {
        this.deleted = deleted;
    }

    /** Getter for property system.
     * @return Value of property system.
     *
     */
    public int getSystem()
    {
        return system;
    }
    
    /** Setter for property system.
     * @param system New value of property system.
     *
     */
    public void setSystem(int system)
    {
        this.system = system;
    }
    
    /** Getter for property repository.
     * @return Value of property repository.
     *
     */
    public java.lang.String getRepository()
    {
        return repository;
    }
    
    /** Setter for property repository.
     * @param repository New value of property repository.
     *
     */
    public void setRepository(java.lang.String repository)
    {
        this.repository = repository;
    }
    
    /** Getter for property indexer.
     * @return Value of property indexer.
     *
     */
    public java.lang.String getIndexer()
    {
        return indexer;
    }
    
    /** Setter for property indexer.
     * @param repository New value of property indexer.
     *
     */
    public void setIndexer(java.lang.String indexer)
    {
        this.indexer = indexer;
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
                String query = "delete from wbtopicmap where id=?";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, id);
                st.executeUpdate();
                st.close();
                con.close();
                DBDbSync.getInstance().saveChange("wbtopicmap", "remove", 0, id, null);
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("remove", this);
            }

        } catch (Exception e)
        {
            throw new AFException("No fue posible borrar el elemento...\n" + e.getMessage(), "RecTopicMap:remove()");
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
                String query = "update wbtopicmap set idadm=?,title=?,home=?,lang=?,description=?,active=?,xml=?,created=?,lastupdate=?,deleted=?,system=?,repository=?,indexer=? where id=?";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, idadm);
                st.setString(2, title);
                st.setString(3, home);
                st.setString(4, lang);
                st.setString(5, description);
                st.setInt(6, active);
                if (xml == null)
                    st.setString(7, null);
                else
                    st.setAsciiStream(7, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
                st.setTimestamp(8, created);
                st.setTimestamp(9, lastupdate);
                st.setInt(10, deleted);
                st.setInt(11, system);
                st.setString(12, repository);
                st.setString(13, indexer);
                st.setString(14, id);
                st.executeUpdate();
                st.close();
                con.close();
                DBDbSync.getInstance().saveChange("wbtopicmap", "update", 0, id, lastupdate);
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("update", this);
            }

        } catch (Exception e)
        {
            throw new AFException("No fue posible actualizar el elemento...\n" + e.getMessage(), "RecTopicMap:update()");
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
            created = lastupdate;
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"));
            String query = "insert into wbtopicmap (idadm,title,home,lang,description,active,xml,created,lastupdate,deleted,system,repository,indexer,id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, idadm);
            st.setString(2, title);
            st.setString(3, home);
            st.setString(4, lang);
            st.setString(5, description);
            st.setInt(6, active);
            if (xml == null)
                st.setString(7, null);
            else
                st.setAsciiStream(7, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
            st.setTimestamp(8, created);
            st.setTimestamp(9, lastupdate);
            st.setInt(10, deleted);
            st.setInt(11, system);
            st.setString(12, repository);
            st.setString(13, indexer);
            st.setString(14, id);
            st.executeUpdate();
            st.close();
            con.close();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("create", this);
            }
            DBDbSync.getInstance().saveChange("wbtopicmap", "create", 0, id, lastupdate);

        } catch (Exception e)
        {
            throw new AFException("No fue posible crear el elemento...\n" + e.getMessage(), "RecTopicMap:create()");
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
            String query = "select * from wbtopicmap where id=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next())
            {
                idadm = rs.getString("idadm");
                title = rs.getString("title");
                home = rs.getString("home");
                lang = rs.getString("lang");
                description = rs.getString("description");
                active = rs.getInt("active");
                xml = com.infotec.appfw.util.AFUtils.getInstance().readInputStream(rs.getAsciiStream("xml"));
                created = rs.getTimestamp("created");
                lastupdate = rs.getTimestamp("lastupdate");
                deleted = rs.getInt("deleted");
                system = rs.getInt("system");
                repository = rs.getString("repository");
                indexer = rs.getString("indexer");
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
            throw new AFException("No fue posible cargar el elemento...\n" + e.getMessage(), "RecTopicMap:load()");
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
        if (getLastupdate() == null || (getLastupdate() != null && getLastupdate().before(date)))
        {
            if (action.equals("remove"))
            {
                sendNotify(action);
                TopicMgr.getInstance().syncFromExternalAction(action, this);
            } else if (action.equals("create") || action.equals("update"))
            {
                String oldxml = getXml();
                load();
                if (action.equals("create") || (action.equals("update") && (oldxml == null || !oldxml.equals(getXml()))))
                    TopicMgr.getInstance().syncFromExternalAction(action, this);
            }
        }
    }
    
}
