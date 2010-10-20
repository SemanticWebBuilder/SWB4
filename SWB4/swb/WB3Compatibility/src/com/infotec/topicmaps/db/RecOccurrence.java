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
 * RecOccurrence.java
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

/** objeto: referencia al registro de la base de datos de la table wboccurrence
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class RecOccurrence implements WBDBRecord
{

    private ArrayList observers = new ArrayList();
    private ArrayList notifys = new ArrayList();

    private String id;
    private String idtm;
    private String idtp;
    private int active;
    private String xml;
    private Timestamp lastupdate;
    private int deleted;
    private String flow;
    private int status;
    private String step;
    private Timestamp flowtime;
    private int priority;

    private boolean virtual=false;    

    /** Creates new RecOccurrence */
    public RecOccurrence()
    {
        this.id = null;
        this.idtm = null;
        this.idtp = null;
        this.active = 0;
        this.xml = null;
        this.lastupdate = null;
        this.deleted = 0;
        this.flow = null;
        this.status = 0;                                  //0=revision, 1=rechazado
        this.step = null;
        this.flowtime=null;
        this.priority = 3;
//        if(WBLoader.getInstance().haveDBTables())
        {
//            registerObserver(DBTopicMap.getInstance());
            //registerObserver(UsrMgr.getInstance());
        }
    }

    public RecOccurrence(String id)
    {
        this();
        this.id = id;
    }

    /** Creates new RecOccurrence */
    public RecOccurrence(String id, String idtm, String idtp, int active, String xml, Timestamp lastupdate, int deleted, String flow, int status, String step, Timestamp flowtime)
    {
        this(id,idtm,idtp,active,xml,lastupdate,deleted,flow,status,step,flowtime,3);
    }

    /** Creates new RecOccurrence */
    public RecOccurrence(String id, String idtm, String idtp, int active, String xml, Timestamp lastupdate, int deleted, String flow, int status, String step, Timestamp flowtime,int priority)
    {
        this();
        this.id = id;
        this.idtm = idtm;
        this.idtp = idtp;
        this.active = active;
        this.xml = xml;
        this.lastupdate = lastupdate;
        this.deleted = deleted;
        this.flow = flow;
        this.status = status;
        this.step = step;
        this.flowtime=flowtime;
        this.priority = priority;
    }
    
    public RecOccurrence(ObjectDecoder dec)
    {
        this();
        this.id = dec.getString(0);
        this.idtm = dec.getString(1);
        this.idtp = dec.getString(2);
        this.active = dec.getInt(3);
        this.xml = dec.getString(4);
        this.lastupdate = dec.getTimeStamp(5);
        this.deleted = dec.getInt(6);
        this.flow = dec.getString(7);
        this.status = dec.getInt(8);
        this.step= dec.getString(9);
        this.flowtime = dec.getTimeStamp(10);
        if(dec.getSize()>11)
        {
            this.priority = dec.getInt(11);
        }  
    }
    
    public ObjectEncoder getEncoder()
    {
        ObjectEncoder enc=new ObjectEncoder("RecOccurrence");
        enc.addString(id);
        enc.addString(idtm);
        enc.addString(idtp);
        enc.addInt(active);
        enc.addString(xml);
        enc.addTimestamp(lastupdate);
        enc.addInt(deleted);
        enc.addString(flow);
        enc.addInt(status);
        enc.addString(step);
        enc.addTimestamp(flowtime);
        enc.addInt(priority);
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

    /** Getter for property idtm.
     * @return Value of property idtm.
     */
    public java.lang.String getIdtp()
    {
        return idtp;
    }

    /** Setter for property idtm.
     * @param idtm New value of property idtm.
     */
    public void setIdtp(java.lang.String idtp)
    {
        this.idtp = idtp;
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
        if(deleted==1)
        {
            status=0;
            step=null;
            flow=null;
            flowtime=null;
        }
    }

    /** Getter for property flow.
     * @return Value of property flow.
     */
    public String getFlow()
    {
        return flow;
    }

    /** Setter for property flow.
     * @param flow New value of property flow.
     */
    public void setFlow(String flow)
    {
        this.flow = flow;
    }

    /** Getter for property status.
     * 0=revision, 1=rechazado
     * @return Value of property status.
     */
    public int getStatus()
    {
        return status;
    }

    /** Setter for property status.
     * @param status New value of property status.
     */
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    /**
     * Getter for property step.
     * @return Value of property step.
     */
    public java.lang.String getStep() {
        return step;
    }
    
    /**
     * Setter for property step.
     * @param step New value of property step.
     */
    public void setStep(java.lang.String step) {
        this.step = step;
    }    
    
    /**
     * Getter for property flowtime.
     * @return Value of property flowtime.
     */
    public java.sql.Timestamp getFlowtime() {
        return flowtime;
    }
    
    /**
     * Setter for property flowtime.
     * @param flowtime New value of property flowtime.
     */
    public void setFlowtime(java.sql.Timestamp flowtime) {
        this.flowtime = flowtime;
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
                String query = "delete from wboccurrence where id=? and idtm=? and idtp=?";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, id);
                st.setString(2, idtm);
                st.setString(3, idtp);
                st.executeUpdate();
                st.close();
                con.close();
                //DBDbSync.getInstance().saveChange("wboccurrence", "remove", 0, id + " " + idtm + " " + idtp, null);
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("remove", this);
            }

        } catch (Exception e)
        {
            throw new AFException("No fue posible borrar el elemento...\n" + e.getMessage(), "RecOccurrence:remove()");
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
                String query = "update wboccurrence set active=?,xml=?,lastupdate=?,deleted=?,flow=?,status=?,step=?,flowtime=?,priority=? where id=? and idtm=? and idtp=?";
                PreparedStatement st = con.prepareStatement(query);
                st.setInt(1, active);
                if (xml == null)
                    st.setString(2, null);
                else
                    st.setAsciiStream(2, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
                st.setTimestamp(3, lastupdate);
                st.setInt(4, deleted);
                st.setString(5, flow);
                st.setInt(6, status);
                st.setString(7, step);
                if(flowtime==null)flowtime=new Timestamp(new java.util.Date().getTime());
                st.setTimestamp(8, flowtime);
                st.setInt(9, priority);
                st.setString(10, id);
                st.setString(11, idtm);
                st.setString(12, idtp);
                st.executeUpdate();
                st.close();
                con.close();
                //DBDbSync.getInstance().saveChange("wboccurrence", "update", 0, id + " " + idtm + " " + idtp, lastupdate);
            }
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("update", this);
            }

        } catch (Exception e)
        {
            throw new AFException("No fue posible actualizar el elemento...\n" + e.getMessage(), "RecOccurrence:update()");
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
            flowtime=lastupdate;
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"));
            String query = "insert into wboccurrence (active,xml,lastupdate,deleted,flow,status,step,flowtime,id,idtm,idtp,priority) values (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, active);
            if (xml == null)
                st.setString(2, null);
            else
                st.setAsciiStream(2, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(xml), xml.length());
            st.setTimestamp(3, lastupdate);
            st.setInt(4, deleted);
            st.setString(5, flow);
            st.setInt(6, status);
            st.setString(7, step);
            st.setTimestamp(8,flowtime);
            st.setString(9, id);
            st.setString(10, idtm);
            st.setString(11, idtp);
            st.setInt(12, priority);
            st.executeUpdate();
            st.close();            
            con.close();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("create", this);
            }
            //DBDbSync.getInstance().saveChange("wboccurrence", "create", 0, id + " " + idtm + " " + idtp, lastupdate);
        } catch (Exception e)
        {
            throw new AFException("No fue posible crear el elemento...\n" + e.getMessage(), "RecOccurrence:create()");
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
            String query = "select * from wboccurrence where id=? and idtm=? and idtp=?";
            //System.out.println("RecOccurrence-->select * from wboccurrence where id="+id+" and idtm="+idtm+" and idtp="+idtp);
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, id);
            st.setString(2, idtm);
            st.setString(3, idtp);
            ResultSet rs = st.executeQuery();
            if (rs.next())
            {
                active = rs.getInt("active");
                xml = com.infotec.appfw.util.AFUtils.getInstance().readInputStream(rs.getAsciiStream("xml"));
                lastupdate = rs.getTimestamp("lastupdate");
                deleted = rs.getInt("deleted");
                flow = rs.getString("flow");
                status = rs.getInt("status");
                step = rs.getString("step");
                priority = rs.getInt("priority");
                try
                { 
                    flowtime= rs.getTimestamp("flowtime");
                }catch(SQLException e){flowtime=null;}
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
            throw new AFException("No fue posible cargar el elemento...\n" + e.getMessage(), "RecOccurrence:load()");
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
        //System.out.println("syncFromExternalAction:RecOccurrence-->"+action+" "+date);
//        if (getLastupdate() == null || (getLastupdate() != null && getLastupdate().before(date)))
//        {
//            //System.out.println("syncFromExternalAction:"+action);
//
//            if (action.equals("remove"))
//            {
//                sendNotify(action);
//                TopicMgr.getInstance().syncFromExternalAction(action, this);
//            } else if (action.equals("create") || action.equals("update"))
//            {
//                String oldxml = getXml();
//                load();
//                //System.out.println("getXml:"+getXml());
//                if (action.equals("create") || (action.equals("update") && (oldxml == null || !oldxml.equals(getXml()))))
//                    TopicMgr.getInstance().syncFromExternalAction(action, this);
//            }
//        }
    }
}
