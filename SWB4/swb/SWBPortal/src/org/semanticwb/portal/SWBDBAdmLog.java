/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal;

import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.SWBInstance;

/**
 *
 * @author jorge.jimenez
 */
public class SWBDBAdmLog {

    private static Logger log = SWBUtils.getLogger(SWBDBAdmLog.class);
    private String user;
    private String action;
    private String dbobject;
    private long dbobjid;
    private String topicmapid;
    private String topicid;
    private String description;
    private Timestamp date;
    public String admlogEmail = SWBInstance.getEnv("wb/admlogEmail");

    /** Creates new RecAdmLog */
    public SWBDBAdmLog() {
        this.user = "_";
        this.action = "_";
        this.dbobject = "_";
        this.dbobjid = 0;
        this.topicmapid = null;
        this.topicid = null;
        this.description = null;
        this.date = null;
    }

    /**
     * @param rs  */
    public SWBDBAdmLog(ResultSet rs) {
        this();
        try {
            this.user = rs.getString("wbuser");
            this.action = rs.getString("action");
            this.dbobject = rs.getString("dbobject");
            this.dbobjid = rs.getInt("dbobjid");
            this.topicmapid = rs.getString("topicmapid");
            this.topicid = rs.getString("topicid");
            this.description = rs.getString("description");
            this.date = rs.getTimestamp("wbdate");
        } catch (Exception e) {
            log.error(e);
        }
    }

    public SWBDBAdmLog(String user, String action, String dbobject, long dbobjid, String topicmapid, String topicid, String description, Timestamp date) {
        this();
        this.user = user;
        this.action = action;
        this.dbobject = dbobject;
        this.dbobjid = dbobjid;
        this.topicmapid = topicmapid;
        this.topicid = topicid;
        this.description = description;
        this.date = date;
    }

    /** Getter for property user.
     * @return Value of property user.
     */
    public String getUser() {
        return user;
    }

    /** Setter for property user.
     * @param user New value of property user.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /** Getter for property action.
     * @return Value of property action.
     */
    public java.lang.String getAction() {
        return action;
    }

    /** Setter for property action.
     * @param action New value of property action.
     */
    public void setAction(java.lang.String action) {
        this.action = action;
    }

    /** Getter for property dbobject.
     * @return Value of property dbobject.
     */
    public java.lang.String getDbobject() {
        return dbobject;
    }

    /** Setter for property dbobject.
     * @param dbobject New value of property dbobject.
     */
    public void setDbobject(java.lang.String dbobject) {
        this.dbobject = dbobject;
    }

    /** Getter for property dbobjid.
     * @return Value of property dbobjid.
     */
    public long getDbObjId() {
        return dbobjid;
    }

    /** Setter for property dbobjid.
     * @param dbobjid New value of property dbobjid.
     */
    public void setDbObjId(long dbobjid) {
        this.dbobjid = dbobjid;
    }

    /** Getter for property tpicmapid.
     * @return Value of property tpicmapid.
     */
    public java.lang.String getTopicMapId() {
        return topicmapid;
    }

    /** Setter for property tpicmapid.
     * @param topicmapid  */
    public void setTopicMapId(java.lang.String topicmapid) {
        this.topicmapid = topicmapid;
    }

    /** Getter for property tpicid.
     * @return Value of property tpicid.
     */
    public java.lang.String getTopicId() {
        return topicid;
    }

    /** Setter for property tpicid.
     * @param topicid  */
    public void setTopicId(java.lang.String topicid) {
        this.topicid = topicid;
    }

    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return description;
    }

    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    /** Getter for property date.
     * @return Value of property date.
     */
    public java.sql.Timestamp getDate() {
        return date;
    }

    /** Setter for property date.
     * @param date New value of property date.
     */
    public void setDate(java.sql.Timestamp date) {
        this.date = date;
    }

    /** crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void create() throws Exception {
        Connection con;
        //try {
        if (date == null) {
            date = new Timestamp(new java.util.Date().getTime());
        }
        con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.create()");
        String query = "insert into wbadmlog (wbuser,action,dbobject,dbobjid,topicmapid,topicid,description,wbdate) values (?,?,?,?,?,?,?,?)";
        PreparedStatement st = con.prepareStatement(query);

        String descri = description;
        if (descri != null && descri.length() > 255) {
            descri = descri.substring(0, 255);
        }

        st.setString(1, user);
        st.setString(2, action);
        st.setString(3, dbobject);
        st.setLong(4, dbobjid);
        st.setString(5, topicmapid);
        st.setString(6, topicid);
        st.setString(7, descri);
        st.setTimestamp(8, date);
        st.executeUpdate();
        st.close();
        con.close();

        if (admlogEmail != null) { //TODO
//                String desc = com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "email_RecAdmLog_create_user") + DBUser.getInstance().getUserById(user).getName();
//                desc += desc = " " + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "email_RecAdmLog_create_action") + description;
//                com.infotec.appfw.util.AFUtils.getInstance().sendBGEmail(DBAdmLog.getInstance().admlogEmail, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "email_RecAdmLog_create_change"), desc);
            }

    }

    /** Elimina el registro de la base de datos asi como todopublic void remove() throws AFException
     * @throws com.infotec.appfw.exception.AFException  */
    public void remove() throws Exception {
        Connection con = null;
        try {
            con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.remove()");
            String query = "delete from wbadmlog where wbuser=? and action=? and wbdate=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, user);
            st.setString(2, action);
            st.setTimestamp(3, date);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e) {
            throw new Exception();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public void saveContentLog(String userid, String topicmapid, String topicid, String action, long contentid, String description) {
        try {
            this.user = userid;
            this.action = action;
            this.dbobject = "Content";
            this.topicmapid = topicmapid;
            this.topicid = topicid;
            this.description = description;
            create();
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void saveTopicLog(String userid, String topicmapid, String topicid, String action, long resid, String description) {
        try {
            this.user = userid;
            this.action = action;
            this.dbobject = "Topic";
            this.dbobjid = resid;
            this.topicmapid = topicmapid;
            this.topicid = topicid;
            this.description = description;
            create();
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void saveFileLog(String userid, String file, String oldfile, String action, String description) {
        try {
            this.user = userid;
            this.action = action;
            this.dbobject = "File";
            this.dbobjid = 0;
            this.topicmapid = file;
            this.topicid = oldfile;
            this.description = description;
            create();
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void saveTopicMapLog(String userid, String topicmapid, String action, String description) {
        try {
            this.user = userid;
            this.action = action;
            this.dbobject = "TopicMap";
            this.dbobjid = 0;
            this.topicmapid = topicmapid;
            this.description = description;
            create();
        } catch (Exception e) {
            log.error(e);
        }
    }

    public Iterator getObjectLog(String obj, long id) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getObjectLog()");
        if (con != null) {
            try {
                String query = "select * from wbadmlog where dbobject=? and dbobjid=? order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, obj);
                st.setLong(2, id);
                ResultSet rs = st.executeQuery();
            ret = new SWBIterAdmLog(con, st, rs);
            } catch (Exception e) {
                log.error(e);
            }
        } else {
            //tira una exception
        }
        return ret;
    }

    public Iterator getUserObjectLog(long user, String object) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getUserObjectLog()");
        if (con != null) {
            try {
                String query = "select * from wbadmlog where wbuser=? and dbobject=? order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setLong(1, user);
                st.setString(2, object);
                ResultSet rs = st.executeQuery();
            ret = new SWBIterAdmLog(con, st, rs);
            } catch (Exception e) {
                log.error(e);
            }
        } else {
            //tira una exception
        }
        return ret;
    }
    
    
    public Iterator getBitaContent(long dbobjid)
    {
        Iterator ret = new ArrayList().iterator();
        Connection con =SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getBitaContent");
        if (con != null)
        {
            try
            {
                String query = "select * from wbadmlog where (dbobjid=?) and ((dbobject=?) or (dbobject=?) or (dbobject=?)) order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setLong(1, dbobjid);
                st.setString(2, "Content");
                st.setString(3, "Resource");
                st.setString(4, "Topic");
                ResultSet rs = st.executeQuery();
                ret = new SWBIterAdmLog(con, st, rs);
            } catch (Exception e)
            {
                log.error(e);
            }
        } else
        {
            //tira una exception
        }
        return ret;
    }
    
    
     public Iterator getBitaTopic(String topicmapid, String topicid)
    {
        Iterator ret = new ArrayList().iterator();
        Connection con =SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getBitaTopic");
        if (con != null)
        {
            try
            {
                String query = "select * from wbadmlog where topicmapid=? and topicid=? order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, topicmapid);
                st.setString(2, topicid);
                ResultSet rs = st.executeQuery();
                ret = new SWBIterAdmLog(con, st, rs);
            } catch (Exception e)
            {
                log.error(e);
            }
        } else
        {
            //tira una exception
        }
        return ret;
    }
    
    public Iterator getUserActionObjectLog(long user, String action, String object)
    {
        Iterator ret = new ArrayList().iterator();
        Connection con =SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getUserActionObjectLog");
        if (con != null)
        {
            try
            {
                String query = "select * from wbadmlog where wbuser=? and dbobject=? and action=? order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setLong(1, user);
                st.setString(2, object);
                st.setString(3, action);
                ResultSet rs = st.executeQuery();
                ret = new SWBIterAdmLog(con, st, rs);
            } catch (Exception e)
            {
                log.error(e);
            }
        } else
        {
            //tira una exception
        }
        return ret;
    } 
     
    
    public Iterator getActionObjectLog(String action, String object)
    {
        Iterator ret = new ArrayList().iterator();
        Connection con =SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getActionObjectLog");
        if (con != null)
        {
            try
            {
                String query = "select * from wbadmlog where dbobject=? and action=? order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, object);
                st.setString(2, action);
                ResultSet rs = st.executeQuery();
                ret = new SWBIterAdmLog(con, st, rs);
            } catch (Exception e)
            {
                log.error(e);
            }
        } else
        {
            //tira una exception
        }
        return ret;
    }
    
     public Iterator getUserActionLog(long user, String action)
    {
        Iterator ret = new ArrayList().iterator();
        Connection con =SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getUserActionLog");
        if (con != null)
        {
            try
            {
                String query = "select * from wbadmlog where wbuser=? and action=? order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setLong(1, user);
                st.setString(2, action);
                ResultSet rs = st.executeQuery();
                ret = new SWBIterAdmLog(con, st, rs);
            } catch (Exception e)
            {
                log.error(e);
            }
        } else
        {
            //tira una exception
        }
        return ret;
    }
     
     /** Elimina todos los registros de la base de datos que cumplan con el Admuserid  throws AFException
     * @throws WBException  */
    public void removeLogByAdmuserId(long Admuserid, Timestamp lastupdate) throws com.infotec.appfw.exception.AFException
    {
        Connection con =SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.removeLogByAdmuserId");
        try
        {
            String query = "delete from wbadmlog where wbuser=? and wbdate<?";
            PreparedStatement st = con.prepareStatement(query);
            st.setLong(1, Admuserid);
            st.setTimestamp(2, lastupdate);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e)
        {
            throw new Exception();
        } finally
        {
            if (con != null) {
                con.close();
            }
        }
    }
    
    
     /** Elimina todos los registros de la base de datos que cumplan con el topicid  throws AFException
     * @throws WBException  */
    public void removeLogByTopicId(String topicmapid, String topicid, Timestamp lastupdate) throws com.infotec.appfw.exception.AFException
    {
        Connection con =SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.removeLogByTopicId");
        try
        {
            String query = "delete from wbadmlog where topicmapid=? and topicid=? and wbdate<?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, topicmapid);
            st.setString(2, topicid);
            st.setTimestamp(3, lastupdate);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e)
        {
            throw new Exception();
        } finally
        {
            if (con != null) {
                con.close();
            }
        }
    }
    
     /** Elimina todos los registros de la base de datos que cumplan con el topicmapid  throws AFException
     * @throws WBException  */
    public void removeLogByTopicMapId(String topicmapid, Timestamp lastupdate) throws com.infotec.appfw.exception.AFException
    {
        Connection con =SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.removeLogByTopicMapId");
        try
        {
            String query = "delete from wbadmlog where topicmapid=? and wbdate<?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, topicmapid);
            st.setTimestamp(2, lastupdate);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e)
        {
            throw new Exception();
        } finally
        {
            if (con != null) {
                con.close();
            }
        }
    }
    
     /** Elimina todos los registros de la base de datos que cumplan con el DBObject(para lenguaje y usuarios finales)  throws AFException
     * @throws WBException  */
    public void removeLogByDBObject(String object, String topicmapid, Timestamp lastupdate) throws com.infotec.appfw.exception.AFException
    {
        Connection con =SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.removeLogByDBObject");
        try
        {
            String query = "delete from wbadmlog where dbobject=? and topicmapid=? and wbdate<?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, object);
            st.setString(2, topicmapid);
            st.setTimestamp(3, lastupdate);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e)
        {
            throw new Exception();
        } finally
        {
            if (con != null) {
                con.close();
            }
        }
    }
    
    
     /** Elimina todos los registros de la base de datos que cumplan con el DBObject y   throws AFException
     * @throws WBException  */
    public void removeLogByDBObjectAndDBObjId(String object, String topicmap,long dbobjid, Timestamp lastupdate) throws com.infotec.appfw.exception.AFException
    {
        Connection con =SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.removeLogByDBObjectAndDBObjId");
        try
        {
            String query = "delete from wbadmlog where dbobject=? and dbobjid=? and wbdate<? and topicmapid=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, object);
            st.setLong(2, dbobjid);
            st.setTimestamp(3, lastupdate);
            st.setString(4, topicmap);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e)
        {
            throw new Exception();
        } finally
        {
            if (con != null) {
                con.close();
            }
        }
    }
    
    public Iterator getUserLog(long user)
    {
        return executeQuery("select * from wbadmlog where wbuser=" + user + " order by wbdate");
    }
    
    private Iterator executeQuery(String sql)
    {
        Iterator ret = new ArrayList().iterator();
        Connection con =SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.executeQuery");
        if (con != null)
        {
            try
            {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                ret = new SWBIterAdmLog(con, st, rs);
            } catch (Exception e)
            {
                log.error(e);
            }
        } else
        {
            //tira una exception
        }
        return ret;
    }

    public Iterator getChanges(Timestamp lastupdate)
    {
        Iterator ret = new ArrayList().iterator();
        Connection con =SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getChanges");
        if (con != null)
        {
            try
            {
                String query = "select * from wbadmlog where wbdate>? order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setTimestamp(1, lastupdate);
                ResultSet rs = st.executeQuery();
                ret = new SWBIterAdmLog(con, st, rs);
            } catch (Exception e)
            {
                log.error(e);
            }
        } else
        {
            //tira una exception
        }
        return ret;
    }
    
     
}
