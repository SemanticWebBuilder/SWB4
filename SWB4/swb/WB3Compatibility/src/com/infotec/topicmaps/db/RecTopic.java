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
 * RecTopic.java
 *
 * Created on 5 de julio de 2002, 12:29
 */

package com.infotec.topicmaps.db;

import java.sql.*;

//import com.infotec.wb.lib.*;

import java.util.*;

import com.infotec.appfw.exception.*;
import com.infotec.appfw.lib.DBPool.DBConnectionManager;
//import com.infotec.wb.core.db.DBDbSync;
import com.infotec.appfw.lib.AFObserver;
import com.infotec.wb.lib.WBDBRecord;
import com.infotec.appfw.util.AFUtils;
//import com.infotec.wb.util.*;
//import com.infotec.wb.core.*;
//import com.infotec.wb.core.db.*;
//import com.infotec.topicmaps.bean.*;

import com.infotec.appfw.util.db.ObjectDecoder;
import com.infotec.appfw.util.db.ObjectEncoder;


import org.xml.sax.*;
import org.w3c.dom.*;
//import org.apache.xerces.parsers.*;

import java.io.*;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;

/** objeto: referencia al registro de la base de datos de la table wbtopic
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class RecTopic implements WBDBRecord
{

    org.semanticwb.model.WebPage wp = null;

    public RecTopic(org.semanticwb.model.WebPage webpage)
    {
        wp = webpage;
    }

    public org.semanticwb.model.WebPage getNative()
    {
        return wp;
    }

    private ArrayList observers = new ArrayList();
    private ArrayList notifys = new ArrayList();

    private String id;
    private String idtm;
    private String idadm;
    private int active;
    private String xml;
    private String xmlconf;
    private Document config=null;
    private Timestamp created;
    private int system;
    private Timestamp lastupdate;
    private int deleted;
    private long views;

    private long timer;                     //valores de sincronizacion de views, hits
    private long time;                      //tiempo en milisegundos por cada actualizacion
    private boolean viewed = false;
    private int indexable;
    private int hidden;
    
    private boolean virtual=false;    

    /** Creates new RecTopic */
    public RecTopic()
    {
        this.id = null;
        this.idtm = null;
        this.idadm = "_";
        this.active = 0;
        this.xml = null;
        this.xmlconf = null;
        this.created = null;
        this.system = 0;
        this.lastupdate = null;
        this.deleted = 0;
        this.views = 0;
        this.indexable = 1;
        this.hidden = 0;
//        if(WBLoader.getInstance().haveDBTables())
//        {
//            registerObserver(DBTopicMap.getInstance());
//            //registerObserver(DBVgContent.getInstance());
//            //registerObserver(UsrMgr.getInstance());
//        }
        timer = System.currentTimeMillis();
        time = 600000L;
        try
        {
            time = 1000L * Long.parseLong((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/accessLogTime"));
        } catch (Exception e)
        {
            AFUtils.log(e, "Error al leer accessLogTime...");
        }
    }

    public RecTopic(String id)
    {
        this();
        this.id = id;
    }
    
    /** Creates new RecTopic */
    public RecTopic(String id, String idtm, String idadm, int active, String xml, String xmlconf, Timestamp created, int system, Timestamp lastupdate, int deleted, long views)
    {
        this(id,idtm,idadm,active,xml,xmlconf,created,system,lastupdate,deleted,views,1,0);
    }

    /** Creates new RecTopic */
    public RecTopic(String id, String idtm, String idadm, int active, String xml, String xmlconf, Timestamp created, int system, Timestamp lastupdate, int deleted, long views,int indexable,int hidden)
    {
        this();
        this.id = id;
        this.idtm = idtm;
        this.idadm = idadm;
        this.active = active;
        this.xml = xml;
        this.xmlconf = xmlconf;
        this.created = created;
        this.system = system;
        this.lastupdate = lastupdate;
        this.deleted = deleted;
        this.views = views;
        this.indexable = indexable;
        this.hidden = hidden;
        config=AFUtils.getInstance().XmltoDom(xmlconf);
    }
    
    public RecTopic(ObjectDecoder dec)
    {
        this();
        this.id = dec.getString(0);
        this.idtm = dec.getString(1);
        this.idadm = dec.getString(2);
        this.active = dec.getInt(3);
        this.xml = dec.getString(4);
        this.xmlconf = dec.getString(5);
        this.created = dec.getTimeStamp(6);
        this.system = dec.getInt(7);
        this.lastupdate = dec.getTimeStamp(8);
        this.deleted = dec.getInt(9);
        this.views=dec.getLong(10);
        if(dec.getSize()>11)
        {
            this.indexable=dec.getInt(11);
            this.hidden=dec.getInt(12);
        }
    }
    
    public ObjectEncoder getEncoder()
    {
        ObjectEncoder enc=new ObjectEncoder("RecTopic");
        enc.addString(id);
        enc.addString(idtm);
        enc.addString(idadm);
        enc.addInt(active);
        enc.addString(xml);
        enc.addString(xmlconf);
        enc.addTimestamp(created);
        enc.addInt(system);
        enc.addTimestamp(lastupdate);
        enc.addInt(deleted);
        enc.addLong(views);
        enc.addInt(indexable);
        enc.addInt(hidden);
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

    /** Getter for property xmlconf.
     * @return Value of property xmlconf.
     */
    public java.lang.String getXmlconf()
    {
        return xmlconf;
    }

    /** Setter for property xmlconf.
     * @param xmlconf New value of property xmlconf.
     */
    public void setXmlconf(java.lang.String xmlconf)
    {
        this.xmlconf = xmlconf;
        //config=AFUtils.getInstance().XmltoDom(xmlconf);
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
    
    /** Getter for property system.
     * @return Value of property system.
     */
    public int getSystem()
    {
        return system;
    }

    /** Setter for property system.
     * @param system New value of property system.
     */
    public void setSystem(int system)
    {
        this.system = system;
    }    
    
    /** Getter for property lastupdate.
     * @return Value of property lastupdate.
     */
    public java.sql.Timestamp getLastupdate()
    {
        return lastupdate;
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
        viewed = true;
        this.views = views;
    }
    
    /** Getter for property indexable.
     * @return Value of property indexable.
     */
    public int getIndexable()
    {
        return indexable;
    }
    
    /** Setter for property indexable.
     *  @param indexable New value of property indexable.
     */
    public void setIndexable(int indexable)
    {
        this.indexable=indexable;
    }
    
    
    /** Getter for property Hidden.
     * @return Value of property Hidden.
     */
    public int getHidden()
    {
        return hidden;
    }
    
    
    /** Setter for property Hidden.
     * @param hidden New value of property hidden.
     */
    public void setHidden(int hidden)
    {
        this.hidden=hidden;
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
       try
        {
            if(!virtual)
            {
                wp.remove();
                //DBDbSync.getInstance().saveChange("wbtopic", "remove", 0, id + " " + idtm, null);
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("remove", this);
            }
        } catch (Exception e)
        {
            throw new AFException("No fue posible borrar el elemento...\n" + e.getMessage(), "RecTopic:remove()");
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

                wp.setActive(active==1?true:false);
                //TODO:
//                if (xml == null)
//                    st.setString(3, null);
//                else
//                    st.setAsciiStream(3, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
//                st.setString(4,xmlconf);
//                if (DBTopicMap.getInstance().xmlconftp == 1)
//                {
//                    st.setBytes(4, xmlconf.getBytes());
//                } else if (DBTopicMap.getInstance().xmlconftp == 2)
//                {
//                    st.setString(4, xmlconf);
//                } else //if (DBTopicMap.getInstance().xmlconftp == 3)
//                {
//                    if (xmlconf == null)
//                        st.setString(4, null);
//                    else
//                        st.setAsciiStream(4, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xmlconf), xmlconf.length());
//                }
                //st.setInt(6, system);
                wp.setUpdated(new java.util.Date(lastupdate.getTime()));
                wp.setDeleted(deleted==1?true:false);
                wp.setViews(views);
                wp.setIndexable(indexable==1?true:false);
                wp.setHidden(hidden==1?true:false);
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("update", this);
            }
            config=AFUtils.getInstance().XmltoDom(xmlconf);
        } catch (Exception e)
        {
            throw new AFException("No fue posible actualizar el elemento...\n" + e.getMessage(), "RecTopic:update()");
        } 
    }


    public boolean incViews()
    {

        viewed = true;
        views++;
//        long t = System.currentTimeMillis() - timer;
//        if (t > time || t < -time)
//        {
//            return true;
//        }
        return wp.incViews();
    }

    /** actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     */
    public void updateViews() throws AFException
    {
        if (viewed)
        {
            wp.updateViews();
            viewed = false;
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
            if(!virtual)
            {
                WebSite ws = SWBContext.getWebSite(idtm);
                if(id!=null)
                {
                    wp = ws.createWebPage(id);
                    wp.setCreator(ws.getUserRepository().getUser(idadm));
                    wp.setActive(active==1?true:false);
                    //TODO:
//                    if (xml == null)
//                        st.setString(3, null);
//                    else
//                        st.setAsciiStream(3, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
                    //st.setString(4,xmlconf);
                    //System.out.println("conftp:"+DBTopicMap.getInstance().xmlconftp);
//                    if (DBTopicMap.getInstance().xmlconftp == 1)
//                    {
//                        st.setBytes(4, xmlconf.getBytes());
//                    } else if (DBTopicMap.getInstance().xmlconftp == 2)
//                    {
//                        st.setString(4, xmlconf);
//                    } else //if (DBTopicMap.getInstance().xmlconftp == 3)
//                    {
//                        if (xmlconf == null)
//                            st.setString(4, null);
//                        else
//                            st.setAsciiStream(4, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xmlconf), xmlconf.length());
//                    }
                    wp.setCreated(new java.util.Date(created.getTime()));
                    wp.setUpdated(new java.util.Date(lastupdate.getTime()));
                    wp.setDeleted(deleted==1?true:false);
                    wp.setViews(views);
                    wp.setIndexable(indexable==1?true:false);
                    wp.setHidden(hidden==1?true:false);
                    //st.setInt(6, system);
                }
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("create", this);
            }

            if(null!=xmlconf)config=AFUtils.getInstance().XmltoDom(xmlconf);
        } catch (Exception e)
        {
            com.infotec.appfw.util.AFUtils.log(e);
            throw new AFException("No fue posible crear el elemento...\n" + e.getMessage(), "RecTopic:create()");
        } 
    }

    /** refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     */
    public void load() throws AFException
    {

        if(idtm!=null && id !=null)
        {
            WebSite ws = SWBContext.getWebSite(idtm);
            wp = ws.getWebPage(id);
            if (null!=wp)
            {
                idadm = wp.getCreator().getId();
                active = wp.isActive()?1:0;
                //TODO:
                //xml = com.infotec.appfw.util.AFUtils.getInstance().readInputStream(rs.getAsciiStream("xml"));
//                if (DBTopicMap.getInstance().xmlconftp == 2)
//                {
//                    xmlconf = rs.getString("xmlconf");
//                }else
//                {
//                    xmlconf = com.infotec.appfw.util.AFUtils.getInstance().readInputStream(rs.getAsciiStream("xmlconf"));
//                }
                created = new Timestamp(wp.getCreated().getTime());
                //system = rs.getInt("system");
                lastupdate = new Timestamp(wp.getUpdated().getTime());
                deleted = wp.isDeleted()?1:0;
                views = wp.getViews();
                indexable = wp.isIndexable()?1:0;
                hidden = wp.isHidden()?1:0;
            }

            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("load", this);
            }
            if(null!=xmlconf) config=AFUtils.getInstance().XmltoDom(xmlconf);
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
        //System.out.println("syncFromExternalAction:"+action);
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

    /**
     * Getter for property config.
     * @return Value of property config.
     */
    public Document getConfig()
    {
        return config;
    }
    
    /**
     * Setter for property config.
     * @param config New value of property config.
     */
//    public void setConfig(Document config)
//    {
//        this.config = config;
//    }
    
    /** Getter for property interval.
     * @return Value of property interval.
     */
    public org.w3c.dom.NodeList getInterval()
    {
        if(config==null)return null;
        return config.getElementsByTagName("interval");
    }    
    
}
