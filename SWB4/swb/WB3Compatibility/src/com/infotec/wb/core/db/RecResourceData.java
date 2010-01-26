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
 * RecResourceData.java
 *
 * Created on 19 de septiembre de 2002, 12:29
 */

package com.infotec.wb.core.db;

import java.sql.*;
import java.util.*;

import com.infotec.appfw.exception.*;
import com.infotec.appfw.lib.DBPool.DBConnectionManager;
import com.infotec.appfw.lib.AFObserver;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBResourceException;

/** objeto: cache de registro de base de datos de la tabla wbresourcedata
 *
 * * object: record cache of the data base of the wbresourcedata table
 *
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class RecResourceData //implements WBDBRecord
{

    private ArrayList observers = new ArrayList();
    private ArrayList notifys = new ArrayList();

    private long resid;
    private String residtm;
    private String topicmapid;
    private String topicid;
    private String userid;
    private String usertp;
    private String data;
    private Timestamp lastupdate;

    /** Creates new RecResourceData */
    public RecResourceData()
    {
        this.resid = 0;
        this.residtm="_";
        this.topicmapid = "_";
        this.topicid = "_";
        this.userid = "_";
        this.usertp = "_";
        this.data = null;
        this.lastupdate = null;
        //registerObserver(DBPFlow.getInstance());
        //registerObserver(PFlowMgr.getInstance());
    }
/*
    public RecResourceData(long resid) {
        this();
        this.resid=resid;
    }
*/
    /** Creates new RecResourceData
     * @param resid
     * @param topicmapid
     * @param topicid
     * @param userid
     * @param usertp
     * @param data
     * @param lastupdate  */
    public RecResourceData(long resid, String residtm, String topicmapid, String topicid, String userid, String usertp, String data, Timestamp lastupdate)
    {
        this();
        this.resid = resid;
        this.residtm=residtm;
        this.topicmapid = topicmapid;
        this.topicid = topicid;
        this.userid = userid;
        this.data = data;
        this.lastupdate = lastupdate;
    }

    /** Getter for property resid.
     * @return Value of property resid.
     */
    public long getResid()
    {
        return resid;
    }

    /** Setter for property resid.
     * @param resid New value of property resid.
     */
    public void setResid(long resid)
    {
        this.resid = resid;
    }
    
    /**
     * Getter for property residtm.
     * @return Value of property residtm.
     */
    public java.lang.String getResidtm()
    {
        return residtm;
    }
    
    /**
     * Setter for property residtm.
     * @param residtm New value of property residtm.
     */
    public void setResidtm(java.lang.String residtm)
    {
        this.residtm = residtm;
    }    

    /** Getter for property topicmapid.
     * @return Value of property topicmapid.
     */
    public java.lang.String getTopicmapid()
    {
        return topicmapid;
    }

    /** Setter for property topicmapid.
     * @param topicmapid New value of property topicmapid.
     */
    public void setTopicmapid(java.lang.String topicmapid)
    {
        this.topicmapid = topicmapid;
    }

    /** Getter for property topicid.
     * @return Value of property topicid.
     */
    public java.lang.String getTopicid()
    {
        return topicid;
    }

    /** Setter for property topicid.
     * @param topicid New value of property topicid.
     */
    public void setTopicid(java.lang.String topicid)
    {
        this.topicid = topicid;
    }

    /** Getter for property userid.
     * @return Value of property userid.
     */
    public java.lang.String getUserid()
    {
        return userid;
    }

    /** Setter for property userid.
     * @param userid New value of property userid.
     */
    public void setUserid(java.lang.String userid)
    {
        this.userid = userid;
    }

    /** Getter for property usertp.
     * @return Value of property usertp.
     */
    public java.lang.String getUsertp()
    {
        return usertp;
    }

    /** Setter for property usertp.
     * @param usertp New value of property usertp.
     */
    public void setUsertp(java.lang.String usertp)
    {
        this.usertp = usertp;
    }

    /** Getter for property data.
     * @return Value of property data.
     */
    public java.lang.String getData()
    {
        return data;
    }

    /** Setter for property data.
     * @param data New value of property data.
     */
    public void setData(java.lang.String data)
    {
        this.data = data;
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

    /** registra el objeto observador para que pueda recibir notoficaciones de cambios
     * @param obs  */
    public void registerObserver(AFObserver obs)
    {
        if (!observers.contains(obs)) observers.add(obs);
    }

    /** Elimina el registro de la base de datos asi como todopublic void remove() throws AFException
     * @throws com.infotec.appfw.exception.AFException  */
    public void remove() throws SWBResourceException
    {
        DBConnectionManager mgr = null;
        Connection con;
        try
        {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecResourceData.remove()");
            String query = "delete from wbresourcedata where resid=? and residtm=? and topicmapid=? and topicid=? and userid=? and usertp=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setLong(1, resid);
            st.setString(2, residtm);
            st.setString(3, topicmapid);
            st.setString(4, topicid);
            st.setString(5, userid);
            st.setString(6, usertp);
            st.executeUpdate();
            st.close();
            con.close();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("remove", this);
            }
            //DBDbSync.getInstance().saveChange("wbresourcedata","remove",id,null,null);

        } catch (Exception e)
        {
            throw new SWBResourceException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_RecResourceData_remove_removeElementError") + "...RecResourceData:remove()", e);
        } finally
        {
            if (mgr != null) mgr.release();
        }
    }

    /** actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void update() throws SWBResourceException
    {
        DBConnectionManager mgr = null;
        Connection con;
        try
        {
            lastupdate = new Timestamp(new java.util.Date().getTime());
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecResourceData.update()");
            String query = "update wbresourcedata set data=?, lastupdate=? where resid=? and residtm=? and topicmapid=? and topicid=? and userid=? and usertp=?";
            PreparedStatement st = con.prepareStatement(query);
            if (data == null)
                st.setString(1, null);
            else
                st.setAsciiStream(1, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(data), data.length());
            st.setTimestamp(2, lastupdate);
            st.setLong(3, resid);
            st.setString(4, residtm);
            st.setString(5, topicmapid);
            st.setString(6, topicid);
            st.setString(7, userid);
            st.setString(8, usertp);
            st.executeUpdate();
            st.close();
            con.close();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("update", this);
            }
            //DBDbSync.getInstance().saveChange("wbresourcedata","update",id,null,lastupdate);

        } catch (Exception e)
        {
            //AFUtils.log(e,"No fue posible actualizar el elemento...",true);
            throw new SWBResourceException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_RecResourceData_update_updateElementError") + "...RecResourceData:update()", e);
        } finally
        {
            if (mgr != null) mgr.release();
        }
    }

    /** crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void create() throws SWBResourceException
    {
        DBConnectionManager mgr = null;
        Connection con;
        try
        {
            lastupdate = new Timestamp(new java.util.Date().getTime());
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecResourceData.create()");
/*
            if(id==0)
            {
                Statement st=con.createStatement();
                ResultSet rs=st.executeQuery("SELECT max(id) FROM wbresourcedata");
                if(rs.next())
                {
                    id=rs.getInt(1)+1;
                }else id=1;
                rs.close();
                st.close();
            }
*/
            String query = "insert into wbresourcedata (resid,residtm,topicmapid,topicid,userid,usertp,data,lastupdate) values (?,?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setLong(1, resid);
            st.setString(2, residtm);
            st.setString(3, topicmapid);
            st.setString(4, topicid);
            st.setString(5, userid);
            st.setString(6, usertp);
            if (data == null)
                st.setString(7, null);
            else
                st.setAsciiStream(7, com.infotec.appfw.util.AFUtils.getInstance().getStreamFromString(data), data.length());
            st.setTimestamp(8, lastupdate);
            st.executeUpdate();
            st.close();
            con.close();
            Iterator it = observers.iterator();
            while (it.hasNext())
            {
                ((AFObserver) it.next()).sendDBNotify("create", this);
            }
            //DBDbSync.getInstance().saveChange("wbresourcedata","create",id,null,lastupdate);

        } catch (Exception e)
        {
            throw new SWBResourceException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_RecResourceData_create_createElementError") + "...RecResourceData:create()", e);
        } finally
        {
            if (mgr != null) mgr.release();
        }
    }

    /** refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void load() throws SWBResourceException
    {
        DBConnectionManager mgr = null;
        Connection con;
        try
        {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecResourceData.load()");
            String query = "select * from wbresourcedata where resid=? and residtm=? and topicmapid=? and topicid=? and userid=? and usertp=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setLong(1, resid);
            st.setString(2, residtm);
            st.setString(3, topicmapid);
            st.setString(4, topicid);
            st.setString(5, userid);
            st.setString(6, usertp);
            ResultSet rs = st.executeQuery();
            if (rs.next())
            {
                //resid=rs.getLong("resid");
                //topicmapid=rs.getString("topicmapid");
                //topicid=rs.getString("topicid");
                //userid=rs.getString("userid");
                //usertp=rs.getString("usertp");
                data = com.infotec.appfw.util.AFUtils.getInstance().readInputStream(rs.getAsciiStream("data"));
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
            throw new SWBResourceException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_RecResourceData_load_loadElementError") + "...RecResourceData:load()", e);
        } finally
        {
            if (mgr != null) mgr.release();
        }
    }

    /** refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     * @throws com.infotec.appfw.exception.AFException
     * @return  */
    public boolean exist() throws SWBResourceException
    {
        boolean ret = false;
        DBConnectionManager mgr = null;
        Connection con;
        try
        {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecResourceData.exist()");
            String query = "select * from wbresourcedata where resid=? and residtm=? and topicmapid=? and topicid=? and userid=? and usertp=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setLong(1, resid);
            st.setString(2, residtm);
            st.setString(3, topicmapid);
            st.setString(4, topicid);
            st.setString(5, userid);
            st.setString(6, usertp);
            ResultSet rs = st.executeQuery();
            if (rs.next())
            {
                ret = true;
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e)
        {
            throw new SWBResourceException(SWBUtils.TEXT.getLocaleString("locale_core", "error_RecResourceData_load_loadElementError") + "...RecResourceData:load()", e);
        } finally
        {
            if (mgr != null) mgr.release();
        }
        return ret;
    }

/*
    public void sendNotify()
    {
        Iterator nt=notifys.iterator();
        while(nt.hasNext())
        {
            String message=(String)nt.next();
            Iterator it=observers.iterator();
            while(it.hasNext())
            {
                ((AFObserver)it.next()).sendDBNotify(message,this);
            }
            nt.remove();
        }
    }
*/
    /**
     * @param message  */
    public void sendNotify(String message)
    {
//        Iterator it = observers.iterator();
//        while (it.hasNext())
//        {
//            ((AFObserver) it.next()).sendDBNotify(message, this);
//        }
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
//            } else if (action.equals("create") || action.equals("update"))
//            {
//                load();
//            }
//        }
    }


    
}
