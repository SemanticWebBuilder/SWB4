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
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;

/**
 *
 * @author jorge.jimenez
 */
public class SWBDBAdmLog {

    private static Logger log = SWBUtils.getLogger(SWBDBAdmLog.class);
    private String user;
    private String action;
    private String dbobject;
    private String uri;
    private String description;
    private Timestamp date;
    public String admlogEmail = SWBPlatform.getEnv("wb/admlogEmail");

    /** Creates new RecAdmLog */
    public SWBDBAdmLog() {
        this.user = "_";
        this.action = "_";
        this.dbobject = "_";
        this.uri = null;
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
            this.dbobject = rs.getString("object");
            this.uri = rs.getString("uri");
            this.description = rs.getString("description");
            this.date = rs.getTimestamp("wbdate");
        } catch (Exception e) {
            log.error(e);
        }

    }

    public SWBDBAdmLog(String user, String action, String dbobject, String uri, String description, Timestamp date) {
        this();
        this.user = user;
        this.action = action;
        this.dbobject = dbobject;
        this.uri = uri;
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

    /** Getter for property tpicmapid.
     * @return Value of property tpicmapid.
     */
    public java.lang.String getUri() {
        return uri;
    }

    /** Setter for property tpicmapid.
     * @param topicmapid  */
    public void setUri(java.lang.String uri) {
        this.uri = uri;
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
    public void create() throws SWBException {
        Connection con;
        try {
            if (date == null) {
                date = new Timestamp(new java.util.Date().getTime());
            }
            con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.create()");
            String query = "insert into swb_admlog (uri,action,object,user,description,date) values (?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);

            String descri = description;
            if (descri != null && descri.length() > 255) {
                descri = descri.substring(0, 255);
            }

            st.setString(1, uri);
            st.setString(2, action);
            st.setString(3, dbobject);
            st.setString(4, user);
            st.setString(5, descri);
            st.setTimestamp(6, date);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (SQLException e) {
            log.error(e);
            throw new SWBException("Error in SWBDBAdmLog:create", e);
        }

        if (admlogEmail != null) { //TODO
//                String desc = com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "email_RecAdmLog_create_user") + DBUser.getInstance().getUserById(user).getName();
//                desc += desc = " " + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "email_RecAdmLog_create_action") + description;
//                com.infotec.appfw.util.AFUtils.getInstance().sendBGEmail(DBAdmLog.getInstance().admlogEmail, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "email_RecAdmLog_create_change"), desc);
            }

    }

    /** Elimina el registro de la base de datos asi como todopublic void remove() throws AFException
     * @throws com.infotec.appfw.exception.AFException  */
    public void remove() throws SWBException {
        Connection con = null;
        try {
            con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.remove()");
            String query = "delete from swb_admlog where wbuser=? and action=? and wbdate=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, user);
            st.setString(2, action);
            st.setTimestamp(3, date);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (SQLException e) {
            log.error(e);
            throw new SWBException("Error in SWBDBAdmLog:create", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new SWBException("Can´t close connection SWBDBAdmLog:remove", e);
                }
            }
        }
    }

    public Iterator getObjectLog(String obj) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getObjectLog()");
        if (con != null) {
            try {
                String query = "select * from swb_admlog where object=? order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, obj);
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

    public Iterator getUserObjectLog(String user, String object) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getUserObjectLog()");
        if (con != null) {
            try {
                String query = "select * from swb_admlog where user=? and object=? order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, user);
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

    public Iterator getActionObjectLog(String action, String object) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getActionObjectLog");
        if (con != null) {
            try {
                String query = "select * from swb_admlog where object=? and action=? order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, object);
                st.setString(2, action);
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

    public Iterator getUserActionLog(String user, String action) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getUserActionLog");
        if (con != null) {
            try {
                String query = "select * from swb_admlog where user=? and action=? order by wbdate";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, user);
                st.setString(2, action);
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

    /** Elimina todos los registros de la base de datos que cumplan con el Admuserid  throws AFException
     * @throws WBException  */
    public void removeLogByAdmuserId(String user, Timestamp lastupdate) throws SWBException {
        Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.removeLogByAdmuserId");
        try {
            String query = "delete from swb_admlog where user=? and date<?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, user);
            st.setTimestamp(2, lastupdate);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e) {
            throw new SWBException("SWBDBAdmLog:removeLogByAdmuserId", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new SWBException("Can´t close connection SWBDBAdmLog:removeLogByAdmuserId", e);
                }
            }
        }
    }

    /** Elimina todos los registros de la base de datos que cumplan con el topicid  throws AFException
     * @throws WBException  */
    public void removeLogByUri(String uri, Timestamp date) throws SWBException {
        Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.removeLogByTopicId");
        try {
            String query = "delete from swb_admlog where uri=? and date<?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, uri);
            st.setTimestamp(2, date);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e) {
            throw new SWBException("SWBDBAdmLog:removeLogByUri", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new SWBException("Can´t close connection SWBDBAdmLog:removeLogByUri", e);
                }
            }
        }
    }

    /** Elimina todos los registros de la base de datos que cumplan con el DBObject(para lenguaje y usuarios finales)  throws AFException
     * @throws WBException  */
    public void removeLogByDBObject(String object, String uri, Timestamp lastupdate) throws SWBException {
        Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.removeLogByDBObject");
        try {
            String query = "delete from swb_admlog where object=? and uri=? and date<?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, object);
            st.setString(2, uri);
            st.setTimestamp(3, lastupdate);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e) {
            throw new SWBException("SWBDBAdmLog:removeLogByDBObject", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new SWBException("Can´t close connection SWBDBAdmLog:removeLogByDBObject", e);
                }
            }
        }
    }

    /** Elimina todos los registros de la base de datos que cumplan con el DBObject y   throws AFException
     * @throws WBException  */
    public void removeLogByDBObjectAndDBObjId(String object, String topicmap, Timestamp lastupdate) throws Exception {
        Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.removeLogByDBObjectAndDBObjId");
        try {
            String query = "delete from swb_admlog where object=? and date<? and uri=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, object);
            st.setTimestamp(2, lastupdate);
            st.setString(3, uri);
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

    public Iterator getUserLog(long user) {
        return executeQuery("select * from swb_admlog where user=" + user + " order by wbdate");
    }

    private Iterator executeQuery(String sql) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.executeQuery");
        if (con != null) {
            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                ret = new SWBIterAdmLog(con, st, rs);
            } catch (Exception e) {
                log.error(e);
            }
        } else {
            //tira una exception
        }
        return ret;
    }

    public Iterator getChanges(Timestamp lastupdate) {
        Iterator ret = new ArrayList().iterator();
        Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getChanges");
        if (con != null) {
            try {
                String query = "select * from swb_admlog where date>? order by date";
                PreparedStatement st = con.prepareStatement(query);
                st.setTimestamp(1, lastupdate);
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
}
