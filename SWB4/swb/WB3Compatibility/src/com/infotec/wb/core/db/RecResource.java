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
 * RecResource.java
 *
 * Created on 20 de junio de 2002, 12:29
 */

package com.infotec.wb.core.db;

import java.sql.*;

//import com.infotec.wb.lib.*;

import java.util.*;

import com.infotec.appfw.exception.*;
//import com.infotec.appfw.lib.DBPool.DBConnectionManager;
//import com.infotec.wb.core.db.DBDbSync;
//import com.infotec.appfw.lib.AFObserver;
//import com.infotec.wb.lib.WBDBRecord;
//import com.infotec.appfw.util.AFUtils;
//import com.infotec.wb.util.*;
//import com.infotec.wb.core.*;
//import com.infotec.appfw.util.db.ObjectDecoder;
//import com.infotec.appfw.util.db.ObjectEncoder;

//import java.io.*;
import com.infotec.appfw.lib.AFObserver;
//import com.infotec.appfw.util.db.ObjectEncoder;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.ResourceSubType;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;

/** Objeto de referencia a un registro de la tabla wbresource de la base de datos.
 *
 * An reference object of a record of the wbresource table from the data base.
 *
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class RecResource //implements WBDBRecord
{
    /** Objeto utilizado para generacion de mensajes en el log */
    private static Logger log = SWBUtils.getLogger(RecResource.class);

    org.semanticwb.model.Resource res = null;

    public RecResource(org.semanticwb.model.Resource resource)
    {
        res = resource;
        idtm = resource.getWebSiteId();
        try
        {
            id=Long.parseLong(resource.getId());
        }catch(Exception e){}
        try {
            load();
        } catch (Exception e) {
            log.error("Error al cargar el registro de la BD..",e);
        }
        
    }

    public org.semanticwb.model.Resource getNative()
    {
        return res;
    }

    private ArrayList observers = new ArrayList();
    private ArrayList notifys = new ArrayList();

    private long id;
    private String idtm;
    private String title;
    private String description;
    private Timestamp created;
    private int active;
    private int actualversion;
    private int lastversion;
    private String xmlconf;
    private String xml;
    private int type;
    private String typemap;
    private int deleted;
    private int idcamp;
    private int idsubtype;
    private String idsubtypemap;
    private String idadm;
    private int priority;
    private long cache;
    private long views;
    private long hits;
    private Timestamp lastupdate;
    private int hitlog;

    private boolean virtual=false;

    private long timer;                     //valores de sincronizacion de views, hits
    private long time;                      //tiempo en milisegundos por cada actualizacion
    private boolean viewed = false;

    /** Creates new RecUser */
    public RecResource(String idtm)
    {
        this.id = 0;
        this.idtm = idtm;
        this.title = "notitle";
        this.description = null;
        this.created = null;
        this.active = 0;
        this.actualversion = 1;
        this.lastversion = 1;
        this.xmlconf = null;
        this.xml = null;
        this.type = 1;
        this.deleted = 0;
        this.idcamp = 0;
        this.idsubtype = 0;
        this.idsubtypemap = null;
        this.idadm = "_";
        this.priority = 3;
        this.cache = 0;
        this.views = 0;
        this.hits = 0;
        this.lastupdate = null;
        this.hitlog = 0;
        //registerObserver(DBVgContent.getInstance());
        timer = System.currentTimeMillis();
        time = 600000L;
    }

    /** Creates new RecUser
     * @param id identificador del recurso
     */
    public RecResource(long id, String idtm)
    {
        this(idtm);
        this.id = id;
    }

    /** Creates new RecUser
     * @param id Identificador del recurso
     * @param title titulo del recurso
     * @param description descripcion del recurso
     * @param created fecha de creacion del recurso
     * @param active estado del recurso del recurso
     * @param actualversion version actual del recurso
     * @param lastversion ultima version del recurso
     * @param xmlconf xml de configuracion interna del recurso
     * @param xml xml de configuracion del recurso
     * @param lastupdate fecha de ultima actualizacion del recurso
     * @param type tipo de objeto del recurso
     * @param deleted indicador si el recurso fue borarado
     * @param idcamp identificador de la campaña del recurso
     * @param idsubtype identificador del subtipo de recurso
     * @param idadm identificador del usuario administrador del recurso
     * @param priority priodad del recurso
     * @param views numero de apariciones del recurso
     * @param hits numero de hits del recurso
     */

    public RecResource(long id, String idtm, String title, String description, Timestamp created, int active, int actualversion, int lastversion, String xmlconf, String xml, int type, String typemap, int deleted, int idcamp, int idsubtype, String idsubtypemap, String idadm, int priority, long cache, long views, long hits, Timestamp lastupdate)
    {
        this(id,idtm,title,description,created,active,actualversion,lastversion,xmlconf,xml,type,typemap,deleted,idcamp,idsubtype,idsubtypemap,idadm,priority,cache,views,hits,lastupdate,0);
    }

    public RecResource(long id, String idtm, String title, String description, Timestamp created, int active, int actualversion, int lastversion, String xmlconf, String xml, int type, String typemap, int deleted, int idcamp, int idsubtype, String idsubtypemap, String idadm, int priority, long cache, long views, long hits, Timestamp lastupdate,int hitlog)
    {
        this(idtm);
        this.id = id;
        this.idtm = idtm;
        this.title = title;
        this.description = description;
        this.created = created;
        this.active = active;
        this.actualversion = actualversion;
        this.lastversion = lastversion;
        this.xmlconf = xmlconf;
        this.xml = xml;
        this.lastupdate = lastupdate;
        this.type = type;
        this.typemap=typemap;
        this.deleted = deleted;
        this.idcamp = idcamp;
        this.idsubtype = idsubtype;
        this.idsubtypemap = idsubtypemap;
        this.idadm = idadm;
        this.priority = priority;
        this.cache=cache;
        this.views = views;
        this.hits = hits;
        this.hitlog = hitlog;
      
    }

//    public RecResource(ObjectDecoder dec)
//    {
//        this(dec.getString(1));
//        this.id = dec.getLong(0);
//        this.idtm = dec.getString(1);
//        this.title = dec.getString(2);
//        this.description = dec.getString(3);
//        this.created = dec.getTimeStamp(4);
//        this.active = dec.getInt(5);
//        this.actualversion = dec.getInt(6);
//        this.lastversion = dec.getInt(7);
//        this.xmlconf = dec.getString(8);
//        this.xml = dec.getString(9);
//        this.type = dec.getInt(10);
//        this.typemap=dec.getString(11);
//        this.deleted = dec.getInt(12);
//        this.idcamp = dec.getInt(13);
//        this.idsubtype = dec.getInt(14);
//        this.idsubtypemap = dec.getString(15);
//        this.idadm = dec.getString(16);
//        this.priority = dec.getInt(17);
//        this.cache=dec.getLong(18);
//        this.views = dec.getLong(19);
//        this.hits = dec.getLong(20);
//        this.lastupdate = dec.getTimeStamp(21);
//        if(dec.getSize()>22)
//        {
//            this.hitlog = dec.getInt(22);
//        }
//    }
//
//    public ObjectEncoder getEncoder()
//    {
//        ObjectEncoder enc=new ObjectEncoder("RecResource");
//        enc.addLong(id);
//        enc.addString(idtm);
//        enc.addString(title);
//        enc.addString(description);
//        enc.addTimestamp(created);
//        enc.addInt(active);
//        enc.addInt(actualversion);
//        enc.addInt(lastversion);
//        enc.addString(xmlconf);
//        enc.addString(xml);
//        enc.addInt(type);
//        enc.addString(typemap);
//        enc.addInt(deleted);
//        enc.addInt(idcamp);
//        enc.addInt(idsubtype);
//        enc.addString(idsubtypemap);
//        enc.addString(idadm);
//        enc.addInt(priority);
//        enc.addLong(cache);
//        enc.addLong(views);
//        enc.addLong(hits);
//        enc.addTimestamp(lastupdate);
//        enc.addInt(hitlog);
//        return enc;
//    }

    /** Getter for property id.
     * @return Value of property id.
     */
    public long getId()
    {
        return id;
    }

    /** Setter for property id.
     * @param id New value of property id.
     */
    public void setId(long id)
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

    /** Getter for property xmlconf.
     * @return Value of property xmlconf.
     */
    public java.lang.String getXmlConf()
    {
        return xmlconf;
    }

    /** Setter for property xmlconf.
     * @param xmlconf New value of property xmlconf.
     */
    public void setXmlConf(java.lang.String xmlconf)
    {
        this.xmlconf = xmlconf;
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

    /** Getter for property actualversion.
     * @return Value of property actualversion.
     */
    public int getActualversion()
    {
        return actualversion;
    }

    /** Setter for property actualversion.
     * @param actualversion New value of property actualversion.
     */
    public void setActualversion(int actualversion)
    {
        this.actualversion = actualversion;
    }

    /** Getter for property lastversion.
     * @return Value of property lastversion.
     */
    public int getLastversion()
    {
        return lastversion;
    }

    /** Setter for property lastversion.
     * @param lastversion New value of property lastversion.
     */
    public void setLastversion(int lastversion)
    {
        this.lastversion = lastversion;
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

    /** Getter for property type.
     * @return Value of property type.
     */
    public String getTypeMap()
    {
        return typemap;
    }

    /** Setter for property type.
     * @param type New value of property type.
     */
    public void setTypeMap(String typemap)
    {
        this.typemap = typemap;
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

    /** Getter for property idcamp.
     * @return Value of property idcamp.
     */
    public int getIdCamp()
    {
        return idcamp;
    }

    /** Setter for property idcamp.
     * @param idcamp New value of property idcamp.
     */
    public void setIdCamp(int idcamp)
    {
        this.idcamp = idcamp;
    }

    /** Getter for property idsubtype.
     * @return Value of property idsubtype.
     */
    public int getIdSubType()
    {
        return idsubtype;
    }

    /** Setter for property idsubtype.
     * @param idsubtype New value of property idsubtype.
     */
    public void setIdSubType(int idsubtype)
    {
        this.idsubtype = idsubtype;
    }

    /** Getter for property idsubtypemap.
     * @return Value of property idsubtypemap.
     */
    public String getIdSubTypeMap()
    {
        return idsubtypemap;
    }

    /** Setter for property idsubtypemap.
     * @param idsubtype New value of property idsubtypemap.
     */
    public void setIdSubTypeMap(String idsubtypemap)
    {
        this.idsubtypemap = idsubtypemap;
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

    /** Getter for property priority.
     * @return Value of property priority.
     */
    public int getPriority()
    {
        return priority;
    }

    /** Setter for property priority.
     * @param priority New value of property priority.
     */
    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    /** Getter for property views.
     * @return Value of property views.
     */
    public long getCache()
    {
        return cache;
    }

    /** Setter for property views.
     * @param views New value of property views.
     */
    public void setCache(long cache)
    {
        this.cache = cache;
    }


    /** Getter for property views.
     * @return Value of property views.
     */
    public long getViews()
    {
        return views;
    }

    /** Setter for property views.
     * @param views New value of property views.
     */
    public void setViews(long views)
    {
        this.views = views;
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


     /** Getter for property hitlog.
     * @return Value of property hitlog.
     */
    public int getHitLog()
    {
        return hitlog;
    }

    /** Setter for property hitlog.
     * @param hitlog New value of property hitlog.
     */
    public void setHitLog(int hitlog)
    {
        this.hitlog = hitlog;
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
    }

    /** Elimina el registro de la base de datos asi como todopublic void remove() throws AFException
     * @throws com.infotec.appfw.exception.AFException  */
    public void remove() throws AFException
    {
        if(!virtual)
        {
            res.remove();
        }
        Iterator it = observers.iterator();
        while (it.hasNext())
        {
            ((AFObserver) it.next()).sendDBNotify("remove", this);
        }

    }

    /** actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     * @param user
     * @param comment
     * @throws com.infotec.appfw.exception.AFException  */
    public void update(String user, String comment) throws AFException
    {
        update();
    }

    /** actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     * @throws com.infotec.appfw.exception.AFException
     */
    public void update() throws AFException
    {
        lastupdate = new Timestamp(new java.util.Date().getTime());
        if(!virtual)
        {
            WebSite ws = SWBContext.getWebSite(idtm);
            
            res.setTitle(title);
            res.setDescription(description);
            res.setActive(active==1?true:false);
//            st.setInt(5, actualversion);
//            st.setInt(6, lastversion);
            res.setXmlConf(xmlconf);
            res.setXml(xml);

            ResourceType restype = ws.getResourceType(Integer.toString(type));
            res.setResourceType(restype);

            typemap = idtm;
            res.setDeleted(deleted==1?true:false);
            //st.setInt(12, idcamp);
            ResourceSubType ressubtype = ws.getResourceSubType(Integer.toString(idsubtype));
            if(ressubtype!=null) res.setResourceType(restype);
            idsubtypemap = typemap;
            res.setPriority(priority);
            //st.setLong(17, cache);
            res.setViews(views);
            res.setHits(hits);
            res.setUpdated(new java.sql.Date(lastupdate.getTime()));            
            //st.setInt(21, hitlog);
            id = Integer.parseInt(res.getId());            
        }
        Iterator it = observers.iterator();
        while (it.hasNext())
        {
            ((AFObserver) it.next()).sendDBNotify("update", this);
        }
    }

    /**
     * @return  boolean
     */
    public boolean incViews()
    {
        boolean ret=false;
        viewed = true;
        views++;
        if(res!=null)
        {
            ret=res.incViews();
        }
        return ret;
    }

    /**
     * @return  boolean
     */
    public boolean incHits()
    {
        boolean ret=false;
        viewed = true;
        hits++;
        if(res!=null)
        {
            ret=res.incHits();
        }
        return ret;
    }

    /** actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void updateViews() throws AFException
    {
        timer = System.currentTimeMillis();
        if (viewed && !virtual)
        {
            res.setViews(views);
            viewed = false;
        }
    }

    /** crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     * @param user
     * @param comment
     * @throws com.infotec.appfw.exception.AFException  */
    public void create(String user, String comment) throws AFException
    {
        create();
        //RecAdmLog rec = new RecAdmLog(user, "create", "Resource", getId(), getTopicMapId(), null, comment, lastupdate);
        //rec.create();
    }

    /** crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void create() throws AFException
    {

        try
        {
            lastupdate = new Timestamp(new java.util.Date().getTime());
            created = lastupdate;

            WebSite ws = SWBContext.getWebSite(idtm);
            res = ws.createResource();
            id = Long.parseLong(res.getId());

            res.setTitle(title);
            res.setDescription(description);
            res.setCreated(new java.util.Date(created.getTime()));
            res.setActive(active==1?true:false);

//            st.setInt(5, actualversion);
//            st.setInt(6, lastversion);

            res.setXmlConf(xmlconf);
            res.setXml(xml);

            ResourceType restype = ws.getResourceType(Integer.toString(type));
            res.setResourceType(restype);

            typemap = idtm;
            res.setDeleted(deleted==1?true:false);

            //st.setInt(12, idcamp);
            ResourceSubType ressubtype = ws.getResourceSubType(Integer.toString(idsubtype));
            if(ressubtype!=null) res.setResourceType(restype);

            idsubtypemap = typemap;
            res.setCreator(ws.getUserRepository().getUser(idadm));
            res.setPriority(priority);
            //st.setLong(17, cache);
            res.setViews(views);
            res.setHits(hits);
            res.setUpdated(new java.sql.Date(lastupdate.getTime()));

            //st.setInt(21, hitlog);

            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("create", this);
            }
            

        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_RecResource_create_createElementError") + "...", "RecResource:create()", e);
        } 
    }

    /** refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void load() throws AFException
    {
        load(true);
    }

    /** refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     * @param notify
     * @throws com.infotec.appfw.exception.AFException  */
    public void load(boolean notify) throws AFException
    {
        if(idtm!=null && id!=0)
        {
            try
            {
                WebSite ws = SWBContext.getWebSite(idtm);
                res = ws.getResource(Long.toString(id));

                typemap=idtm;
                idsubtypemap = typemap;

                if (res!=null)
                {
                    title = res.getTitle();
                    description = res.getDescription();
                    created = new Timestamp(res.getCreated().getTime());
                    active = res.isActive()?1:0;
                    actualversion = 1;
                    lastversion = 1;
                    xmlconf = res.getXmlConf();
                    xml = res.getXml();
                    try
                    {
                        type = Integer.parseInt(res.getResourceType().getId());
                    }catch(Exception e){}
                    deleted = res.isDeleted()?1:0;

                    if(res.getResourceSubType()!=null)
                        idsubtype = Integer.parseInt(res.getResourceSubType().getId());

                    idadm = res.getCreator().getId();
                    priority = res.getPriority();
                    //cache = rs.getLong("cache");
                    views = res.getViews();
                    hits = res.getHits();
                    lastupdate = new Timestamp(res.getUpdated().getTime());
                    //hitlog = rs.getInt("hitlog");
                }

                if (notify)
                {
                    Iterator it = observers.iterator();
                    while (it.hasNext())
                    {
                        ((AFObserver) it.next()).sendDBNotify("load", this);
                    }
                }
            } catch (Exception e)
            {
                throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_RecResource_load_loadElementError") + "...", "RecResource:load()", e);
            }
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
//        if (getLastupdate() == null || (getLastupdate() != null && getLastupdate().before(date)))
//        {
//            if (action.equals("remove"))
//            {
//                sendNotify(action);
//                if(AFUtils.getEnv("wb/clientServer","SASC").equalsIgnoreCase("ClientFR"))
//                {
//                    String resType=DBResourceType.getInstance().getResourceType(getTypeMap(),getType()).getName();
//                    AFUtils.removeDirectory(WBUtils.getInstance().getWorkPath()+"/sites/"+getTopicMapId()+"/resources/" + resType + "/" + getId());
//                }
//            } else if (action.equals("create") || action.equals("update"))
//            {
//                if(AFUtils.getEnv("wb/clientServer","SASC").equalsIgnoreCase("ClientFR"))
//                {
//                    load(false);
//                    DownloadDirectory downdir=new DownloadDirectory(AFUtils.getEnv("wb/serverURL"),WBUtils.getInstance().getWorkPath(),"workpath");
//                    String resType=DBResourceType.getInstance().getResourceType(getTypeMap(),getType()).getName();
//                    String lc = "LocalContent";
//                    AFUtils.removeDirectory(WBUtils.getInstance().getWorkPath()+"/sites/"+getTopicMapId()+"/resources/" + resType + "/" + getId());
//                    if (lc.equals(resType))
//                    {
//                        downdir.download("/sites/"+getTopicMapId()+"/resources/" + resType + "/" + getId()+ "/" + actualversion);
//                    } else
//                    {
//                        downdir.download("/sites/"+getTopicMapId()+"/resources/" + resType + "/" + getId());
//                    }
//                    sendNotify("load");
//                }else
//                {
//                    load();
//                }
//            }
//        }
    }

    public RecResourceType getResourceType()
    {
        return new RecResourceType(res.getResourceType());
        
        //return DBResourceType.getInstance().getResourceType(typemap, type);
    }

}