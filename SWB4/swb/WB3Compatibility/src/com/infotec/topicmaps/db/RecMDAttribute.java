/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * RecMDAttribute.java
 *
 * Created on 28 de julio de 2004, 04:17 PM
 */

package com.infotec.topicmaps.db;


import java.sql.*;

import com.infotec.wb.lib.*;

import java.util.*;

import com.infotec.appfw.exception.*;
import com.infotec.appfw.lib.DBPool.DBConnectionManager;
import com.infotec.appfw.lib.AFObserver;
import com.infotec.appfw.util.AFUtils;
import com.infotec.wb.core.db.*;
import com.infotec.appfw.util.db.ObjectDecoder;
import com.infotec.appfw.util.db.ObjectEncoder;

/** objeto: referencia al registro de la base de datos de la tabla wbmdattribute
 *
 * object: reference to a data base record of the wbmdattribute table
 *
 * @author Juan Antonio Fernández Arias
 */
public class RecMDAttribute extends com.infotec.wb.core.db.RecSync {
    
    private int id;
    private String name;
    private String type;
    private int opcional;
    private Timestamp lastupdate;
    private int tableid;
    private String idtm;
    
    /** Creates a new instance of RecMDAttribute */
    public RecMDAttribute() {
        this.id = 0;
        this.name = "";
        this.type = "";
        this.opcional = 0;
        this.lastupdate = lastupdate;
        this.tableid=0;
        this.idtm="";
    }

    
    /** Creates a new instance of RecMDAttribute
     * @param idtm TopicMap id
     */
    public RecMDAttribute(String idtm) {
        this();
        this.idtm = idtm;
    }
    
    
    /** Creates a new instance of RecMDAttribute
     * @param id Attribute id
     * @param idtm TopicMap id
     */
    public RecMDAttribute(int id,String idtm) {
        this();
        this.id = id;
        this.idtm = idtm;
    }
    
    /** Creates new RecMDAttribute
     * @param tableid Table id
     * @param id Attribute id
     * @param name Attribute name
     * @param type Attribute type
     * @param opcional An optional value (1 or 0)
     * @param lastupdate Attribute last modification
     * @param idtm TopicMap id
     */
    public RecMDAttribute(int id, String name, String type, int opcional, Timestamp lastupdate, int tableid, String idtm) {
        this();
        this.id = id;
        this.name=name;
        this.type = type;
        this.opcional = opcional;
        this.lastupdate = lastupdate;
        this.tableid=tableid;
        this.idtm=idtm;
    }
    
    public RecMDAttribute(ObjectDecoder dec)
    {
        this();
        this.id = dec.getInt(0);
        this.name = dec.getString(1);
        this.type = dec.getString(2);
        this.opcional = dec.getInt(3);
        this.lastupdate = dec.getTimeStamp(4);
        this.tableid = dec.getInt(5);
        this.idtm = dec.getString(6);
    }
    
    public ObjectEncoder getEncoder()
    {
        ObjectEncoder enc=new ObjectEncoder("RecMDAttribute");
        enc.addInt(id);
        enc.addString(name);
        enc.addString(type);
        enc.addInt(opcional);
        enc.addTimestamp(lastupdate);
        enc.addInt(tableid);
        enc.addString(idtm);
        return enc;
    }      
    
    
    /** Getter for property id.
     * @return Value of property id.
     */
    public int getId() {
        return id;
    }
    
    /** Setter for property id.
     * @param id New value of property id.
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /** Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /** Getter for property type.
     * @return Value of property type.
     */
    public String getType() {
        return type;
    }
    
    /** Setter for property type.
     * @param type New value of property type.
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /** Getter for property opcional.
     * @return Value of property opcional.
     */
    public int getOpcional() {
        return opcional;
    }
    
    /** Setter for property opcional.
     * @param opcional New value of property opcional.
     */
    public void setOpcional(int opcional) {
        this.opcional = opcional;
    }
    
    /** Getter for property lastupdate.
     * @return Value of property lastupdate.
     */
    public java.sql.Timestamp getLastupdate() {
        return lastupdate;
    }
    
    /** Setter for property lastupdate.
     * @param lastupdate New value of property lastupdate.
     */
    public void setLastupdate(java.sql.Timestamp lastupdate) {
        this.lastupdate = lastupdate;
    }
    
    /** Getter for property tableid.
     * @return Value of property tableid.
     */
    public int getTableId() {
        return tableid;
    }
    
    /** Setter for property tableid.
     * @param tableid Table id
     */
    public void setTableId(int tableid) {
        this.tableid = tableid;
    }
    
    /** Getter for property idtm.
     * @return Value of property idtm.
     */
    public String getTopicMapId() {
        return idtm;
    }
    
    /** Setter for property idtm.
     * @param idtm New value of property idtm.
     */
    public void setTopicMapId(String idtm) {
        this.idtm = idtm;
    }    
    
    /** Get all topic values related to this attribute
     * @param topicid Topic id
     * @return List all topic values into a HashMap
     */
    public HashMap getTopicValues(String topicid, String topicmapid){
        HashMap hmret = new HashMap();
        DBConnectionManager mgr = null;
        Connection con;
        try {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecMDAttributte.getTopicValues()");
            String query = "select * from wbmdtopicvalue where topicid=? and idtm=? and topicmapid=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, topicid);
            st.setString(2,  idtm);
            st.setString(3,  topicmapid);
            ResultSet rs = st.executeQuery();
            StringBuffer sbin = new StringBuffer();
            while (rs.next()){
                sbin.append(" idvalue="+ rs.getString("idvalue")+" or");
            }
            
            rs.close();
            st.close();
            rs=null;
            st=null;
            
            String list_idvalue = sbin.toString().substring(0,sbin.toString().lastIndexOf("or"));
            st = con.prepareStatement("select idvalue,value from wbattvalue where idtm=? and ( "+list_idvalue+ " ) ");
            st.setString(1,idtm);
            rs = st.executeQuery();
            while(rs.next()) hmret.put(Integer.toString(rs.getInt("idvalue")), rs.getString("value"));
            rs.close();
            st.close();
            con.close();
            
        } catch (Exception e) {
            AFUtils.log(e,"Error while trying to load Topic values from the DB"+". RecMDAttribute:getTopicValues()",true);
        } finally {
            if (mgr != null) mgr.release();
        }
        return hmret;
    }
    
    /** Add new record in DB, and new object in memory
     * @throws com.infotec.appfw.exception.AFException an AF Exception
     */    
    public void createImp() throws AFException {
        DBConnectionManager mgr = null;
        Connection con;
        try {
            lastupdate = new Timestamp(new java.util.Date().getTime());
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecMDAttribute.create()");
            if (id == 0) {
                PreparedStatement st = con.prepareStatement("SELECT max(idatt) FROM wbmdattribute where idtm=?");
                st.setString(1, idtm);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    id = rs.getInt(1) + 1;
                } else
                    id = 1;
                rs.close();
                st.close();
            }
            
            String query = "insert into wbmdattribute (idatt,name,type,opcional,lastupdate,tableid,idtm) values (?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, id);
            st.setString(2, name);
            st.setString(3, type);
            st.setInt(4, opcional);
            st.setTimestamp(5, lastupdate);
            st.setInt(6, tableid);
            st.setString(7, idtm);
            st.executeUpdate();
            st.close();
            con.close();

        } catch (Exception e) {
            throw new AFException("Error while trying to create a RecMDAttribute element in the DB.", "RecMDAttribute:create()", e);
        } finally {
            if (mgr != null) mgr.release();
        }
    }
    
    /** Getter for property Type of Attribute
     * @return Value of Attribute type
     */    
    protected int getIDType() {
        return IDTYPE_LONG_STRING;
    }
    
    /** NValue used in log
     * @return Object name used in logs.
     */    
    protected String getLogName() {
        return "MDAttribute";
    }
    
    /** get identifier
     * @return identifier
     */    
    protected long getRecID() {
        return id;
    }
    
    /** Getter of property SID1, used in logs
     * @return Value used in topicmap id column in logs
     */    
    protected String getRecSID1() {
        return getTopicMapId();
    }
    
    /** Getter of property SID2, used in logs
     * @return Value used in topic id column in logs
     */    
    protected String getRecSID2() {
        return null;
    }
    
    /** Getter of property table name
     * @return The name of the table used
     */    
    protected String getTableName() {
        return "wbmdattribute";
    }
    
    /** Init RecMDAttributte object use a resulset parameter
     * @param set Resulset with all RecMDAttribute values.
     */    
    public void init(ResultSet set) {
        try{
            name = set.getString("name");
            type = set.getString("type");
            opcional = set.getInt("opcional");
            lastupdate = set.getTimestamp("lastupdate");
            tableid = set.getInt("tableid");
        } catch(Exception e){ AFUtils.log(e,"Error in resulset parameter. RecMDAttribute:init()"); }
    }
    
    /** Refresh RecMDAttribute object with the DB information
     * @throws com.infotec.appfw.exception.AFException an AF Exception
     */
    public void loadImp() throws AFException {
        DBConnectionManager mgr = null;
        Connection con;
        try {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecMDAttributte.load()");
            String query = "select * from wbmdattribute where idatt=? and idtm=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, id);
            st.setString(2, idtm);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
                type = rs.getString("type");
                opcional = rs.getInt("opcional");
                lastupdate = rs.getTimestamp("lastupdate");
                tableid = rs.getInt("tableid");
            }
            rs.close();
            st.close();
            con.close();
            
        } catch (Exception e) {
            throw new AFException("Error while trying to load MDAttribute element", "RecAttribute:load()", e);
        } finally {
            if (mgr != null) mgr.release();
        }
    }
    
    /** Remove the record from DB and all related topic values
     * @throws com.infotec.appfw.exception.AFException an AF Exception
     */
    public void removeImp() throws AFException {
        DBConnectionManager mgr = null;
        Connection con;
        try {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecMDAttributte.remove()");
            String query = "delete from wbmdattribute where idatt=? and idtm=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, id);
            st.setString(2,idtm);
            st.executeUpdate();
            st.close();
            con.close();

        } catch (Exception e) {
            throw new AFException("Error while trying to remove the MDAttribute element.", "RecMDAttribute:remove()", e);
        } finally {
            if (mgr != null) mgr.release();
        }
    }
    
    /** Setter for the property ID
     * @param id attribute value
     */    
    protected void setRecID(long id) {
        setId(Integer.parseInt(Long.toString(id)));
    }
    
    /** Setter for the property SID1, used for log.
     * @param sid topicmap  id value used in log
     */    
    protected void setRecSID1(String sid) {
    }
    
    /** Setter for the property SID2, used for log.
     * @param sid topic id value used in log
     */    
    protected void setRecSID2(String sid) {
    }
    
     /** Update the Attribute information in the DB
     * @throws com.infotec.appfw.exception.AFException an AF Exception
     */    
    public void updateImp() throws AFException {
        DBConnectionManager mgr = null;
        Connection con;
        try {
            lastupdate = new Timestamp(new java.util.Date().getTime());
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecMDAttribute.update()");
            String query = "update wbmdattribute set name=?,type=?,opcional=?,lastupdate=?,tableid=? where idatt=? and idtm=?";
            PreparedStatement st = con.prepareStatement(query);
            
            st.setString(1, name);
            st.setString(2, type);
            st.setInt(3, opcional);
            st.setTimestamp(4, lastupdate);
            st.setInt(5, tableid);
            st.setInt(6, id);
            st.setString(7,idtm);
            st.executeUpdate();
            st.close();       
            con.close();

        } catch (Exception e) {
            throw new AFException("Error while trying to update MDAttribute element in the DB", "RecMDAttribute:update()", e);
        } finally {
            if (mgr != null) mgr.release();
        }
    }
}
