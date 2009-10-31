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
 * RecMDTable.java
 *
 * Created on 14 de octubre de 2004, 11:05 AM
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

/** objeto: referencia al registro de la base de datos de la tabla wbmdtable
 *
 * object: reference to a data base record of the wbmdtable table
 *
 * @author Juan Antonio Fernández Arias
 */
public class RecMDTable extends com.infotec.wb.core.db.RecSync {
    
    private int id;
    private String idtm;
    private String idadm;
    private String name;
    private String description;
    private Timestamp creation;
    private Timestamp lastupdate;
    private String topicmapid;
    
    /** Creates a new instance of RecMDTable */
    public RecMDTable() {
        
        this.id = 0;
        this.idtm="";
        this.idadm="";
        this.name = "";
        this.description = "";
        this.creation = creation;
        this.lastupdate = lastupdate;
        //this.topicmapid="";
        
    }
    
    /** Creates a new instance of RecMDTable
     * @param idtm TopicMap id
     */
    public RecMDTable(String idtm) {
        this();
        this.idtm = idtm;
    }
    
    /** Creates a new instance of RecMDTable
     * @param id Table id
     * @param idtm TopicMap id
     */
    public RecMDTable(int id, String idtm) {
        this();
        this.id = id;
        this.idtm = idtm;
    }
    
    /** Creates new RecMDTable
     * @param id Attribute id
     * @param tmnid TopicMap id
     * @param idadm Admin user id
     * @param name Table name
     * @param description Table description
     * @param creation Table creation date
     * @param lastupdate Table last modification date
     */
    public RecMDTable(int id, String idtm,String idadm,String name, String description,Timestamp creation,Timestamp lastupdate) {
        this();
        this.id = id;
        this.idtm=idtm;
        this.idadm=idadm;
        this.name = name;
        this.description = description;
        this.creation = creation;
        this.lastupdate = lastupdate;
    }
    
    public RecMDTable(ObjectDecoder dec)
    {
        this();
        this.id = dec.getInt(0);
        this.idtm = dec.getString(1);
        this.idadm = dec.getString(2);
        this.name = dec.getString(3);
        this.description = dec.getString(4);
        this.creation = dec.getTimeStamp(5);
        this.lastupdate = dec.getTimeStamp(6);
    }
    
    public ObjectEncoder getEncoder()
    {
        ObjectEncoder enc=new ObjectEncoder("RecMDTable");
        enc.addInt(id);
        enc.addString(idtm);
        enc.addString(idadm);
        enc.addString(name);
        enc.addString(description);
        enc.addTimestamp(creation);
        enc.addTimestamp(lastupdate);
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
    
    /** Getter for property tmnid.
     * @return Value of property tmnid.
     */
    public String getTopicMapId() {
        return idtm;
    }
    
    /** Setter for property tmnid.
     * @param tmnid TopicMap id
     */
    public void setTopicMapId(String idtm) {
        this.idtm = idtm;
    }
    
    
   
    /** Getter for property idadm. (user id)
     * @return Value of property idadm. (user id)
     */
    public String getAdmUserId() {
        return idadm;
    }
    
    /** Setter for property idadm. (user id)
     * @param idadm Admin user id
     */
    public void setAdmUserId(String idadm) {
        this.idadm = idadm;
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
    
    /** Getter for property description.
     * @return Value of property type.String query = "insert into wbmdattribute (idatt,name,type,opcional,lastupdate,tableid) values (?,?,?,?,?,?)";
     */
    public String getDescription() {
        return description;
    }
    
    /** Setter for property Desription.
     * @param description table description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    /** Getter for property creation.
     * @return Value of property creation.
     */
    public java.sql.Timestamp getCreationDate() {
        return creation;
    }
    
    /** Setter for property creation.
     * @param creation New value of property creation.
     */
    public void setCreationDate(java.sql.Timestamp creation) {
        this.creation = creation;
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
    
    /** Setter for property RecID
     * @param id Rec id
     */
    protected void setRecID(long id) {
        setId(Integer.parseInt(Long.toString(id)));
    }
    
    /** Setter for property SID1, used in logs
     * @param sid topicmap id column in logs
     */
    protected void setRecSID1(String sid) {
        
        
    }
    
    /** Setter for property SID2, used in logs
     * @param sid topic id column in logs
     */
    protected void setRecSID2(String sid) {
        
    }
    
    /** Add new record in DB, and new object in memory
     * @throws com.infotec.appfw.exception.AFException an AF Exception
     */
    public void createImp() throws AFException {
        DBConnectionManager mgr = null;
        Connection con;
        try {
            lastupdate = new Timestamp(new java.util.Date().getTime());
            setCreationDate(lastupdate);
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecMDTAble.create()");
            if (id == 0) {
                PreparedStatement st = con.prepareStatement("SELECT max(tableid) FROM wbmdtable where idtm=?");
                st.setString(1,idtm);
                ResultSet rs = st.executeQuery();
                //Statement st = con.createStatement();
                //ResultSet rs = st.executeQuery("SELECT max(tableid) FROM wbmdtable where idtm=?");
                if (rs.next()) {
                    id = rs.getInt(1) + 1;
                } else
                    id = 1;
                rs.close();
                st.close();
            }
            String query = "insert into wbmdtable (tableid,idtm,idadm,name,description,creation,lastupdate) values (?,?,?,?,?,?,?) ";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.setString(2, idtm);
            pst.setString(3, idadm);
            pst.setString(4, name);
            pst.setString(5, description);
            pst.setTimestamp(6, creation);
            pst.setTimestamp(7, lastupdate);
            pst.executeUpdate();
            pst.close();
            con.close();
            
        } catch (Exception e) {
            throw new AFException("Error while trying to create RecMDTable element...", "RecMDTable:create()", e);
        } finally {
            if (mgr != null) mgr.release();
        }
    }
    
    /** Getter for property Log name
     * @return Value of log name
     */
    protected String getLogName() {
        return "MDTable";
    }
    
    /** Getter for property RecID
     * @return Value of Red ID
     */
    protected long getRecID() {
        return id;
    }
    
    /** Getter for property SID1, used in logs
     * @return Value of SID1, topicmap id value for logs
     */
    protected String getRecSID1() {
        return getTopicMapId();
    }
    
    /** Getter for property SID2, used in logs
     * @return Value used in topic id column in logs.
     */
    protected String getRecSID2() {
        return null;
    }
    
    /** Getter for property Table name
     * @return Value of table name
     */
    protected String getTableName() {
        return "wbmdtable";
    }
    
    /** Getter for property Type Id
     * @return Value of type id
     */
    protected int getIDType(){
        return IDTYPE_LONG_STRING;
    }
    
    /** Init RecMDTable object with a Resulset
     * @param set Resulset with MDTable values
     */
    public void init(ResultSet set) {
        try{
            this.id = set.getInt("tableid");
            this.idtm=set.getString("idtm");
            this.idadm=set.getString("idadm");
            this.name = set.getString("name");
            this.description = set.getString("description");
            this.creation = set.getTimestamp("creation");
            this.lastupdate = set.getTimestamp("lastupdate");
        }
        catch(Exception e){ AFUtils.log(e,"Error in resulset parameter. RecMDTable:init()");}
    }
    
    /** Refresh RecMDAttribute object with the DB information
     * @throws com.infotec.appfw.exception.AFException an AF Exception
     */
    public void loadImp() throws AFException {
        DBConnectionManager mgr = null;
        Connection con;
        try {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecMDTable.load()");
            String query = "select * from wbmdtable where tableid=? and idtm=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, id);
            st.setString(2, idtm);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                idtm=rs.getString("idtm");
                idadm=rs.getString("idadm");
                name = rs.getString("name");
                description = rs.getString("description");
                creation = rs.getTimestamp("creation");
                lastupdate = rs.getTimestamp("lastupdate");
            }
            rs.close();
            st.close();
            con.close();
            
        } catch (Exception e) {
            throw new AFException("Error while trying to load a RecMDTable element", "RecMDTable:load()", e);
        } finally {
            if (mgr != null) mgr.release();
        }
    }
    
    
    /** Remove the MDTable record from DB
     * @throws com.infotec.appfw.exception.AFException an AF Exception
     */
    public void removeImp() throws AFException {
        DBConnectionManager mgr = null;
        Connection con;
        try {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecMDTable.remove()");
            String query = "delete from wbmdtopicvalue where tableid=? and idtm=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, id);
            st.setString(2,idtm);
            st.executeUpdate();
            st.close();
            
            query = "select idatt from wbmdattribute where tableid=? and idtm=?";
            PreparedStatement st2 = con.prepareStatement(query);
            st2.setInt(1, id);
            st2.setString(2,idtm);
            ResultSet rs = st2.executeQuery();
            while(rs.next()){
                query = "delete from wbmdattvalue where idatt=? and idtm=?";
                PreparedStatement st3 = con.prepareStatement(query);
                st3.setInt(1, rs.getInt("idatt"));
                st3.setString(2,idtm);
                st3.close();
            }
            st2.close();
            
            query = "delete from wbmdattribute where tableid=? and idtm=?";
            PreparedStatement st4 = con.prepareStatement(query);
            st4.setInt(1, id);
            st4.setString(2, idtm);
            st4.executeUpdate();
            st4.close();
            
            query = "delete from wbmdtable where tableid=? and idtm=?";
            PreparedStatement st5 = con.prepareStatement(query);
            st5.setInt(1, id);
            st5.setString(2, idtm);
            st5.executeUpdate();
            st5.close();
            
        } catch (Exception e) {
            throw new AFException("Error while trying to remove RecMDTable element from the DB", "RecMDTable:remove()", e);
        } finally {
            if (mgr != null) mgr.release();
        }
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
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "RecMDTable.update()");
            String query = "update wbmdtable set idadm=?,name=?,description=?,lastupdate=? where tableid=? and idtm=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, idadm);
            st.setString(2, name);
            st.setString(3, description);
            st.setTimestamp(4, lastupdate);
            st.setInt(5, id);
            st.setString(6, idtm);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e) {
            throw new AFException("Error while trying to update a RecMDTable element", "RecMDTable:update()", e);
        } finally {
            if (mgr != null) mgr.release();
        }
    }
    
}
