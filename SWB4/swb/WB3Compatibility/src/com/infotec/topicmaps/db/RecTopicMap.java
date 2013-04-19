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
 * RecTopicMap.java
 *
 * Created on 5 de julio de 2002, 12:29
 */

package com.infotec.topicmaps.db;

import java.sql.*;
import java.util.*;

import com.infotec.appfw.exception.*;
import com.infotec.appfw.lib.AFObserver;
import com.infotec.wb.lib.WBDBRecord;
import com.infotec.appfw.util.db.ObjectDecoder;
import com.infotec.appfw.util.db.ObjectEncoder;

import org.semanticwb.SWBPortal;
import org.semanticwb.model.Language;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.indexer.SWBIndexer;

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
        id=ws.getId();
        if(ws.getUserRepository().getCreator()!=null)idadm = ws.getUserRepository().getCreator().getId();
        title = ws.getTitle();
        home = ws.getHomePage().getId();
        if(ws.getLanguage()!=null)lang = ws.getLanguage().getId();
        description = ws.getDescription();
        active = ws.isActive()?1:0;
        if(ws.getCreated()!=null)created = new Timestamp(ws.getCreated().getTime());
        if(ws.getUpdated()!=null)lastupdate = new Timestamp(ws.getUpdated().getTime());
        deleted = ws.isDeleted()?1:0;
        repository = ws.getUserRepository().getId();
        indexer = SWBPortal.getIndexMgr().getModelIndexer(ws).getName();        
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
//        if(WBLoader.getInstance().haveDBTables())
//        {
//            registerObserver(DBTopicMap.getInstance());
//            registerObserver(DBVgContent.getInstance());
//            registerObserver(TopicMgr.getInstance());
//        }
    }

    public RecTopicMap(String id)
    {
        this(SWBContext.getWebSite(id));
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
        try
        {
            if(!virtual)
            {
                ws.remove();
                //DBDbSync.getInstance().saveChange("wbtopicmap", "remove", 0, id, null);
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("remove", this);
            }

        } catch (Exception e)
        {
            throw new AFException("No fue posible borrar el elemento...\n" + e.getMessage(), "RecTopicMap:remove()");
        } 
    }

    /** actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     */
    public void update() throws AFException
    {
        try
        {
            lastupdate = new Timestamp(new java.util.Date().getTime());
            if(!virtual)
            {
                UserRepository urep = UserRepository.ClassMgr.getUserRepository(repository);
                ws.setModifiedBy(urep.getUser(idadm));
                ws.setTitle(title);
//                WebPage wp = ws.createWebPage(home);
//                wp.setTitle("home");
//                ws.setHomePage(wp);
                Language language = ws.getLanguage(lang);
                ws.setLanguage(language);
                ws.setDescription(description);
                ws.setActive(active==1?true:false);
                ws.setUserRepository(urep);
//                if (xml == null)
//                    st.setString(7, null);
//                else
//                    st.setAsciiStream(7, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
                ws.setUpdated(new java.util.Date(lastupdate.getTime()));
                ws.setDeleted(deleted==1?true:false);
                SWBIndexer ind = SWBPortal.getIndexMgr().getIndexer(indexer);
                ind.indexModel(id);
//                if (xml == null)
//                    st.setString(7, null);
//                else
//                    st.setAsciiStream(7, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
                //st.setInt(11, system);
                //DBDbSync.getInstance().saveChange("wbtopicmap", "update", 0, id, lastupdate);
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("update", this);
            }
        } catch (Exception e)
        {
            throw new AFException("No fue posible actualizar el elemento...\n" + e.getMessage(), "RecTopicMap:update()");
        } 
    }

    /** crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     */
    public void create() throws AFException
    {
        try
        {
            lastupdate = new Timestamp(new java.util.Date().getTime());
            created = lastupdate;
            if(null!=id)
                ws = WebSite.ClassMgr.createWebSite(id, "http://www."+id+".swb");
            if(null!=ws)
            {
                UserRepository urep = UserRepository.ClassMgr.getUserRepository(repository);
                ws.setCreator(urep.getUser(idadm));
                ws.setTitle(title);
                WebPage wp = ws.createWebPage(home);
                wp.setTitle("home");
                ws.setHomePage(wp);
                Language language = ws.createLanguage(lang);
                language.setTitle(lang);
                ws.setLanguage(language);
                ws.setDescription(description);
                ws.setActive(active==1?true:false);
                ws.setUserRepository(urep);

//                if (xml == null)
//                    st.setString(7, null);
//                else
//                    st.setAsciiStream(7, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
                ws.setCreated(new java.util.Date(lastupdate.getTime()));
                ws.setDeleted(deleted==1?true:false);


                SWBIndexer ind = SWBPortal.getIndexMgr().getIndexer(indexer);
                ind.indexModel(id);
                //TODO:
                //st.setInt(11, system);
                

                Iterator it = observers.iterator();
                while (it.hasNext())
                {
                    ((AFObserver) it.next()).sendDBNotify("create", this);
                }
            //DBDbSync.getInstance().saveChange("wbtopicmap", "create", 0, id, lastupdate);
            }

        } catch (Exception e)
        {
            throw new AFException("No fue posible crear el elemento...\n" + e.getMessage(), "RecTopicMap:create()");
        } 
    }

    /** refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     */
    public void load() throws AFException
    {
        try
        {
            if(id!=null)
                ws = SWBContext.getWebSite(id);
            if(null!=ws)
            {
                if(ws.getUserRepository().getCreator()!=null)idadm = ws.getUserRepository().getCreator().getId();
                title = ws.getTitle();
                home = ws.getHomePage().getId();
                if(ws.getLanguage()!=null)lang = ws.getLanguage().getId();
                description = ws.getDescription();
                active = ws.isActive()?1:0;
                if(ws.getCreated()!=null)created = new Timestamp(ws.getCreated().getTime());
                if(ws.getUpdated()!=null)lastupdate = new Timestamp(ws.getUpdated().getTime());
                deleted = ws.isDeleted()?1:0;
                repository = ws.getUserRepository().getId();
                indexer = SWBPortal.getIndexMgr().getModelIndexer(ws).getName();       
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("load", this);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new AFException("No fue posible cargar el elemento...\n" + e.getMessage(), "RecTopicMap:load()",e);
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
