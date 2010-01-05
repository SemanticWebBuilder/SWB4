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
 * RecResourceType.java
 *
 * Created on 25 de junio de 2002, 12:29
 */

package com.infotec.wb.core.db;

import java.sql.*;

import com.infotec.wb.lib.*;

import java.util.*;

import com.infotec.appfw.exception.*;
import com.infotec.appfw.lib.DBPool.DBConnectionManager;
import com.infotec.wb.core.db.DBDbSync;
import com.infotec.wb.core.db.RecAdmLog;
import com.infotec.appfw.lib.AFObserver;
import com.infotec.wb.lib.WBDBRecord;
import com.infotec.appfw.util.AFUtils;
import com.infotec.wb.util.*;
import com.infotec.wb.core.*;
import org.w3c.dom.*;

import com.infotec.appfw.util.db.ObjectDecoder;
import com.infotec.appfw.util.db.ObjectEncoder;

import java.io.*;

/** objeto: cache de registro de base de datos de la tabla wbresourcetype
 *
 * * object: record cache of the data base of the wbresourcetype table
 *
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class RecResourceType implements WBDBRecord
{
    org.semanticwb.model.ResourceType res = null;

    public RecResourceType(org.semanticwb.model.ResourceType resource)
    {
        res = resource;
    }

    public org.semanticwb.model.ResourceType getNative()
    {
        return res;
    }

    private ArrayList observers = new ArrayList();
    private ArrayList notifys = new ArrayList();

    private int id;
    private String idtm;
    private String name;
    private String displayname;
    private String objclass;
    private String description;
    private String xml;
    private int type;
    private String bundle;
    private int cache;
    private Timestamp lastupdate;
    
    protected Document dom;
    private int versionControl=0;
    private int haveXSL=0;
    private int creationMode=0;
    
    private boolean virtual=false;      

    /** Creates new RecResourceType */
    public RecResourceType(String idtm)
    {
        this.id = 0;
        this.idtm=idtm;
        this.name = "noname";
        this.displayname="noname";
        this.objclass = "noclass";
        this.description = null;
        this.xml=null;
        this.type = 0;
        this.bundle = null;
        this.cache = 0;
        this.lastupdate = null;
        if(WBLoader.getInstance().haveDBTables())
        {
            registerObserver(DBResourceType.getInstance());
            //registerObserver(ObjectMgr.getInstance());
        }
    }

    /**
     * @param id  */
    public RecResourceType(int id, String idtm)
    {
        this(idtm);
        this.id = id;
        this.idtm=idtm;
    }

    /** Creates new RecResourceType
     * @param id
     * @param name
     * @param objclass
     * @param description
     * @param lastupdate
     * @param type
     * @param cache  */
    public RecResourceType(int id, String idtm, String name, String displayname, String objclass, String description, String xml, int type, String bundle, int cache, Timestamp lastupdate)
    {
        this(idtm);
        this.id = id;
        this.idtm = idtm;
        this.name = name;
        this.displayname = displayname;
        this.objclass = objclass;
        this.description = description;
        this.xml = xml;
        this.type = type;
        this.bundle = bundle;
        if(bundle!=null && bundle.trim().length()==0)this.bundle=null;
        this.cache = cache;
        this.lastupdate = lastupdate;
        //this.admxml=xml;
    }
    
    public RecResourceType(ObjectDecoder dec)
    {
        this(dec.getString(1));
        this.id = dec.getInt(0);
        this.idtm = dec.getString(1);
        this.name = dec.getString(2);
        this.displayname = dec.getString(3);
        this.objclass = dec.getString(4);
        this.description = dec.getString(5);
        this.xml = dec.getString(6);
        this.type = dec.getInt(7);
        this.bundle = dec.getString(8);
        this.cache = dec.getInt(9);
        this.lastupdate = dec.getTimeStamp(10);
    }
    
    public ObjectEncoder getEncoder()
    {
        ObjectEncoder enc=new ObjectEncoder("RecResourceType");
        enc.addLong(id);
        enc.addString(idtm);
        enc.addString(name);
        enc.addString(displayname);
        enc.addString(objclass);
        enc.addString(description);
        enc.addString(xml);
        enc.addInt(type);
        enc.addString(bundle);
        enc.addInt(cache);
        enc.addTimestamp(lastupdate);
        return enc;
    }    

    /** Getter for property id.
     * @return Value of property id.
     */
    public int getId()
    {
        return id;
    }

    /** Setter for property id.
     * @param id New value of property id.
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getTopicMapId()
    {
        return idtm;
    }
    
    public void setTopicMapId(String idtm)
    {
        this.idtm=idtm;
    }  
    
    /** Getter for property objclass.
     * @return Value of property objclass.
     */
    public java.lang.String getObjclass()
    {
        return objclass;
    }

    /** Setter for property objclass.
     * @param objclass New value of property objclass.
     */
    public void setObjclass(java.lang.String objclass)
    {
        this.objclass = objclass;
    }

    /** Getter for property name.
     * @return Value of property name.
     */
    public java.lang.String getName()
    {
        return name;
    }

    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(java.lang.String name)
    {
        this.name = name;
    }
    
    /** Getter for property name.
     * @return Value of property name.
     */
    public java.lang.String getDisplayName()
    {
        return displayname;
    }

    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setDisplayName(java.lang.String displayname)
    {
        this.displayname = displayname;
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
    
    /** Getter for property bundle.
     * @return Value of property bundle.
     *
     */
    public java.lang.String getBundle()
    {
        return bundle;
    }
    
    /** Setter for property bundle.
     * @param bundle New value of property bundle.
     *
     */
    public void setBundle(java.lang.String bundle)
    {
        this.bundle = bundle;
    }
    
    /** Getter for property cache.
     * @return Value of property cache.
     */
    public int getCache()
    {
        return cache;
    }

    /** Setter for property cache.
     * @param cache New value of property cache.
     */
    public void setCache(int cache)
    {
        this.cache = cache;
    }
    
    public String getXml(){
        return xml;
    }

    public void setXml(String xml){
        this.xml=xml;
        dom=null;
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
     * @param obs  */
    public void registerObserver(AFObserver obs)
    {
        if (!observers.contains(obs)) observers.add(obs);
    }

    /** Elimina el registro de la base de datos asi como todopublic void remove() throws AFException
     * @param user
     * @param comment
     * @throws com.infotec.appfw.exception.AFException  */
    public void remove(String user, String comment) throws AFException
    {
        remove();
        if(!virtual)
        {
            RecAdmLog rec = new RecAdmLog(user, "remove", "ResourceType", getId(), getTopicMapId(), null, comment, lastupdate);
            rec.create();
        }
    }

    /** Elimina el registro de la base de datos asi como todopublic void remove() throws AFException
     * @throws com.infotec.appfw.exception.AFException  */
    public void remove() throws AFException
    {
        DBConnectionManager mgr = null;
        Connection con;
        try
        {
            if(!virtual)
            {            
                mgr = DBConnectionManager.getInstance();
                con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecResourceType.remove()");
                String query = "delete from wbresourcetype where id=? and idtm=?";
                PreparedStatement st = con.prepareStatement(query);
                st.setInt(1, id);
                st.setString(2, idtm);
                st.executeUpdate();
                st.close();
                con.close();
                DBDbSync.getInstance().saveChange("wbresourcetype", "remove", id, idtm, null);
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("remove", this);
            }

        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_RecObject_remove_removeElementError") + "...", "RecResourceType:remove()", e);
        } finally
        {
            if (mgr != null) mgr.release();
        }
    }

    /** actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     * @param user
     * @param comment
     * @throws com.infotec.appfw.exception.AFException  */
    public void update(String user, String comment) throws AFException
    {
        update();
        if(!virtual)
        {
            RecAdmLog rec = new RecAdmLog(user, "update", "ResourceType", getId(), getTopicMapId(), null, comment, lastupdate);
            rec.create();
        }
    }

    /** actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     * @throws com.infotec.appfw.exception.AFException  */
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
                con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecResourceType.update()");
                String query = "update wbresourcetype set name=?,displayname=?,objclass=?,description=?,xml=?,type=?,bundle=?,cache=?,lastupdate=? where id=? and idtm=?";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, name);
                st.setString(2, displayname);
                st.setString(3, objclass);
                st.setString(4, description);
                if (xml == null)
                    st.setString(5, null);
                else
                    st.setAsciiStream(5, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
                st.setInt(6, type);
                st.setString(7, bundle);
                st.setInt(8, cache);
                st.setTimestamp(9, lastupdate);
                st.setInt(10, id);
                st.setString(11, idtm);
                st.executeUpdate();
                st.close();
                con.close();
                DBDbSync.getInstance().saveChange("wbresourcetype", "update", id, idtm, lastupdate);
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("update", this);
            }

        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_RecObject_update_updateElementError") + "...", "RecResourceType:update()", e);
        } finally
        {
            if (mgr != null) mgr.release();
        }
    }

    /** crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     * @param user
     * @param comment
     * @throws com.infotec.appfw.exception.AFException  */
    public void create(String user, String comment) throws AFException
    {
        create();
        RecAdmLog rec = new RecAdmLog(user, "create", "ResourceType", getId(), getTopicMapId(), null, comment, lastupdate);
        rec.create();
    }

    /** crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void create() throws AFException
    {
        DBConnectionManager mgr = null;
        Connection con;
        try
        {
            lastupdate = new Timestamp(new java.util.Date().getTime());
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecResourceType.create()");
            if (id == 0)
            {
                PreparedStatement st = con.prepareStatement("SELECT max(id) FROM wbresourcetype where idtm=?");
                st.setString(1, idtm);
                ResultSet rs = st.executeQuery();
                if (rs.next())
                {
                    id = rs.getInt(1) + 1;
                } else
                    id = 1;
                rs.close();
                st.close();
                if(id<1000 && !AFUtils.getEnv("wb/adminDev","false").equalsIgnoreCase("true") && idtm.equals(com.infotec.topicmaps.bean.TopicMgr.TM_ADMIN))
                {
                    id=1000;
                }                
            }
            String query = "insert into wbresourcetype (name,displayname,objclass,description,xml,type,bundle,cache,lastupdate,id,idtm) values (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, name);
            st.setString(2, displayname);
            st.setString(3, objclass);
            st.setString(4, description);
            if (xml == null)
                st.setString(5, null);
            else
                st.setAsciiStream(5, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
            st.setInt(6, type);
            st.setString(7, bundle);
            st.setInt(8, cache);
            st.setTimestamp(9, lastupdate);
            st.setInt(10, id);
            st.setString(11, idtm);
            st.executeUpdate();
            st.close();
            con.close();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("create", this);
            }
            DBDbSync.getInstance().saveChange("wbresourcetype", "create", id, idtm, lastupdate);

        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_RecObject_create_createElementError") + "...", "RecResourceType:create()", e);
        } finally
        {
            if (mgr != null) mgr.release();
        }
    }

    /** refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void load() throws AFException
    {
        DBConnectionManager mgr = null;
        Connection con;
        try
        {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecResourceType.load()");
            String query = "select * from wbresourcetype where id=? and idtm=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, id);
            st.setString(2, idtm);
            ResultSet rs = st.executeQuery();
            if (rs.next())
            {
                name = rs.getString("name");
                displayname = rs.getString("displayname");
                objclass = rs.getString("objclass");
                description = rs.getString("description");
                xml = com.infotec.appfw.util.AFUtils.getInstance().readInputStream(rs.getAsciiStream("xml"));
                dom=null;
                type = rs.getInt("type");
                bundle = rs.getString("bundle");
                cache = rs.getInt("cache");
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
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_RecObject_load_loadElementError") + "...", "RecResourceType:load()", e);
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

    /**
     * @param message  */
    public void sendNotify(String message)
    {
        Iterator it = observers.iterator();
        while (it.hasNext())
        {
            ((AFObserver) it.next()).sendDBNotify(message, this);
        }
    }

    /**
     * @param action
     * @param date
     * @throws com.infotec.appfw.exception.AFException  */
    public void syncFromExternalAction(String action, Timestamp date) throws AFException
    {
        if (getLastupdate() == null || (getLastupdate() != null && getLastupdate().before(date)))
        {
            if (action.equals("remove"))
            {
                sendNotify(action);
            } else if (action.equals("create") || action.equals("update"))
            {
                load();
            }
        }
    }
    
    /**
     * Getter for property versionControl.
     * @return Value of property versionControl.
     */
    public int getVersionControl()
    {
        return versionControl;
    }    
    
    /**
     * Setter for property versionControl.
     * @param versionControl New value of property versionControl.
     */
    public void setVersionControl(int versionControl)
    {
        this.versionControl = versionControl;
    }    

    /**
     * Getter for property haveXSL.
     * @return Value of property haveXSL.
     */
    public int getHaveXSL()
    {
        return haveXSL;
    }    

    /**
     * Setter for property haveXSL.
     * @param haveXSL New value of property haveXSL.
     */
    public void setHaveXSL(int haveXSL)
    {
        this.haveXSL = haveXSL;
    }    
    
    /**
     * Getter for property creationMode.
     * @return Value of property creationMode.
     */
    public int getCreationMode()
    {
        return creationMode;
    }
    
    /**
     * Setter for property creationMode.
     * @param creationMode New value of property creationMode.
     */
    public void setCreationMode(int creationMode)
    {
        this.creationMode = creationMode;
    }
    
    private Document getDom()
    {
        if(dom==null)
        {
            dom=AFUtils.getInstance().XmltoDom(xml);
            try
            {
                if(dom==null)dom=AFUtils.getInstance().getNewDocument();
            }catch(Exception e){AFUtils.log(e);}
        }
        return dom;
    }
    
    /** Asigna un atributo al DOM del recurso.
     * Si no existe el atributo, lo crea y si existe lo modifica
     * @param name String nombre del atributo
     * @param value String valor del atributo
     */
    public void setAttribute(String name, String value)
    {
        try
        {
            AFUtils.setDomAttribute(getDom(), name, value);
        } catch (Exception e)
        {
            AFUtils.log(e, "Error ResourceType.setAttribute(" + name + ", " +value+"): "+ getId(),true);
        }
    }
    

    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa el valor por defecto defvalue.
     */
    public String getAttribute(String name, String defvalue)
    {
        return AFUtils.getDomAttribute(getDom(), name, defvalue);
    }


    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa null.
     */
    public String getAttribute(String name)
    {
        return AFUtils.getDomAttribute(getDom(), name);
    }    
    
}
