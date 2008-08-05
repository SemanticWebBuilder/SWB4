/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal;

import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;
import java.sql.*;
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

   
    
     
}
