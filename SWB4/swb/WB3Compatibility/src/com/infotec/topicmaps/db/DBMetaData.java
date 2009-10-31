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
 * DBMetaData.java
 *
 * Created on 28 de septiembre de 2004, 05:52 PM
 */

package com.infotec.topicmaps.db;

import com.infotec.wb.core.*;
import com.infotec.appfw.util.*;
import com.infotec.appfw.lib.DBPool.DBConnectionManager;
import com.infotec.wb.core.db.*;
import com.infotec.appfw.lib.*;
import com.infotec.appfw.exception.*;
import com.infotec.topicmaps.bean.TopicMgr;

import java.sql.*;
import java.util.*;

import com.infotec.wb.util.*;
import com.infotec.wb.lib.*;

/** Utilerías para meta datos, obtiene la lista de tablas de metadatos de un sitio
 * específico, así como sus atributos, valores; agrega valores de opciones cuando
 * son de tipo catálogo. También sirve para eliminar tablas de metadatos con todos
 * sus atributos y valores correspondientes.
 *
 * Meta Data utilities, get a list of meta data tables of an specific site, also
 * get all attributes, values; it add option values to a catalog. Eliminate a meta
 * data table with all corresponding attributes and values.
 * @author Juan Antonio Fernández Arias
 */
public class DBMetaData  implements AFAppObject, AFObserver {
    static private DBMetaData instance;          //The single instance
    private Hashtable topicmaps = null;              //indexado por id
    private Hashtable topics = null;        //indexado por nombre
    private boolean debug_var =true;
    
    
    /** Creates a new instance of DBMetaData */
    public DBMetaData() {
    }
    
    /** Destroy a DBMetaData instance */    
    public void destroy() {
        instance=null;
    }
    
    /** Get a DBMetaData instance 
     * @return DBMetaData instance
     */    
    static synchronized public DBMetaData getInstance() {
        
        if (instance == null) {
            instance = new DBMetaData();
            instance.init();
        }
        return instance;
    }
    
    /** Init of DBMetaData instance */    
    public void init() {
        
    }
    
    /** Refresh of DBMetaData instance */   
    public void refresh() {
    }
    
    /** Send a DB notifycation
     * @param s string
     * @param obj object
     */    
    public void sendDBNotify(String s, Object obj) {
    }
    
    /** Getter of a list of Table Attributes
     * @return An Iterator of existing attributes related to the Table
     * @param idtm
     */
    public java.util.Iterator getMDTables(String idtm) {
        return getMDTables(idtm,false);
    }
    
    /** Getter of a list of Table Attributes
     * @return An Iterator of existing attributes related to the Table
     * @param idtm
     * @param global Include in the iterator Global Tables (true or flase)
     */
    public java.util.Iterator getMDTables(String idtm, boolean global) {
        Vector ret=new Vector();
        Connection con=AFUtils.getDBConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"),"DBMetaData.getMDTables()");
        if (con != null) {
            try {
                String query="select * from wbmdtable where idtm=? order by tableid";
                if(global) query="select * from wbmdtable where idtm=? or idtm=? order by tableid";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1,idtm);
                if(global) pst.setString(2,TopicMgr.TM_GLOBAL);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    RecMDTable objRecAtt = new RecMDTable();
                    objRecAtt.init(rs);
                    ret.add(objRecAtt);
                }
                rs.close();
                pst.close();
                con.close();
            }catch(Exception e){
                AFUtils.log(e,"Error while loading Tables in TopicMap: "+idtm+" DBMetaData.getMDTables",true);
            }
        }
        return ret.iterator();
    }
    
    /** Getter of a list of Table Attributes
     * @return An Iterator of existing attributes related to the Table
     * @param idtm
     * @param tableid Table id
     */
    public RecMDTable getMDTable(int tableid, String idtm) {
        RecMDTable ret=null;
        Connection con=AFUtils.getDBConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"),"DBMetaData.getMDTable()");
        if (con != null) {
            try {
                PreparedStatement pst = con.prepareStatement("select * from wbmdtable where tableid=? and idtm=?");
                pst.setInt(1,tableid);
                pst.setString(2,idtm);
                ResultSet rs = pst.executeQuery();
                if(rs.next())
                {
                    RecMDTable objRecAtt = new RecMDTable();
                    objRecAtt.init(rs);
                    ret=objRecAtt;
                }
                rs.close();
                pst.close();
                con.close();
            }catch(Exception e){
                AFUtils.log(e,"Error while loading Table: "+tableid+" DBMetaData.getMDTables",true);
            }
        }
        return ret;
    }    
    
    
    
    /** Getter of a list of Table Attributes
     * @return An Iterator of existing attributes related to the Table
     * @param idtm
     * @param tableid Table id
     */
    public java.util.Iterator getMDTableAttributes(int tableid, String idtm) {
        Vector ret=new Vector();
        Connection con=AFUtils.getDBConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"));
        if (con != null) {
            try {
                PreparedStatement pst = con.prepareStatement("select idatt from wbmdattribute where tableid=? and idtm=? order by idatt");
                pst.setInt(1,tableid);
                pst.setString(2,idtm);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    RecMDAttribute objRecAtt = new RecMDAttribute(rs.getInt("idatt"),idtm);
                    objRecAtt.load();
                    ret.add(objRecAtt);
                }
                rs.close();
                pst.close();
                con.close();
            }catch(Exception e){
                AFUtils.log(e,"Error while loading Table attributes: "+tableid+" DBMetaData.getMDTableAttributes",true);
            }
        }
        return ret.iterator();
    }
    
    /** Get a List of Attribute Values
     * @return List of Attribute Values
     * @param idtm
     * @param idatt Attribute id
     */
    public java.util.HashMap getMDAttributeValues(int idatt, String idtm) {
        HashMap ret=new HashMap();
        Connection con=AFUtils.getDBConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"));
        if (con != null) {
            try {
                PreparedStatement pst = con.prepareStatement("select idvalue,value from wbmdattvalue where idatt=? and idtm=?");
                pst.setInt(1,idatt);
                pst.setString(2,idtm);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    ret.put(Integer.toString(rs.getInt("idvalue")),rs.getString("value"));
                }
                rs.close();
                pst.close();
                con.close();
            }catch(Exception e){
                AFUtils.log(e,"Error while loading HashMap values of attribute: "+idatt+" DBMetaData.getMDAttributeValues",true);
            }
        }
        return ret;
    }
    
    /** Get a Topic values of a Table
     * @return Existing Topic values of a Table
     * @param idtm
     * @param topicmapid
     * @param tableid Table id
     * @param topicid Topic id
     */
    public java.util.HashMap getMDTopicValues(String topicid, int tableid, String idtm, String topicmapid) {
        HashMap ret=new HashMap();
        Connection con=AFUtils.getDBConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"));
        if (con != null) {
            try {
                PreparedStatement pst = con.prepareStatement("select idvalue from wbmdtopicvalue where topicid=? and tableid=? and idtm=? and topicmapid=?");
                pst.setString(1,topicid);
                pst.setInt(2,tableid);
                pst.setString(3,idtm);
                pst.setString(4,topicmapid);
                ResultSet rs = pst.executeQuery();
                StringBuffer strbufidvalue = new StringBuffer();
                int flag=0;
                while(rs.next()){
                    strbufidvalue.append(" idvalue="+rs.getInt("idvalue")+" or");
                    flag++;
                }
                rs.close();
                pst.close();
                if(flag>0){
                    String list_idvalue = strbufidvalue.toString().substring(0,strbufidvalue.toString().lastIndexOf("or"));
                    PreparedStatement pst2 = con.prepareStatement("select * from wbmdattvalue where idtm=? and ( "+list_idvalue+" )");
                    pst2.setString(1,idtm);
                    ResultSet rs2=pst2.executeQuery();
                    while(rs2.next()) {
                        ret.put(Integer.toString(rs2.getInt("idatt")),rs2.getString("value"));
                    }
                    rs2.close();
                    pst2.close();
                }
                con.close();
            }catch(Exception e){
                AFUtils.log(e,"Error while trying to load the HashMap with the value of the Topic: "+topicid+" DBMetaData.getMDTopicValues",true);
            }
        }
        return ret;
    }
    
    /** Get the value of Topic Attribute Table
     * @return an Topic Attribute Table value
     * @param idtm
     * @param topicmapid
     * @param topicid Topic id
     * @param tableid Table id
     * @param idatt Attribute id
     */
    public String getMDTopicValue(String topicid, int tableid, int idatt, String idtm, String topicmapid) {
        String ret=null;
        Connection con=AFUtils.getDBConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"));
        if (con != null) {
            try {
                PreparedStatement pst = con.prepareStatement("select idvalue from wbmdtopicvalue where topicid=? and tableid=? and idatt=? and idtm=? and topicmapid=?");
                pst.setString(1,topicid);
                pst.setInt(2,tableid);
                pst.setInt(3, idatt);
                pst.setString(4,idtm);
                pst.setString(5,topicmapid);
                ResultSet rs = pst.executeQuery();
                int idvalue=-1;
                if(rs.next())
                {
                    idvalue=rs.getInt("idvalue");
                }
                rs.close();
                pst.close();
                if(idvalue>=0){
                    PreparedStatement pst2 = con.prepareStatement("select value from wbmdattvalue where idatt=? and idvalue=? and idtm=?");
                    pst2.setInt(1, idatt);
                    pst2.setInt(2, idvalue);
                    pst2.setString(3,idtm);
                    ResultSet rs2=pst2.executeQuery();
                    if(rs2.next()) {
                        ret=rs2.getString("value");
                    }
                    rs2.close();
                    pst2.close();
                }
                con.close();
            }catch(Exception e){
                AFUtils.log(e,"Error while loading HashMap values of Topic: "+topicid+" DBMetaData.getMDTopicValues",true);
            }
        }
        return ret;
    }    
    
    /** Getter of Attribute Types Values
     * @return An String array of Attribute Type Values
     */
    public String[] getMDAttributeValueTypes() {
        String[] ret=new String[6];
        ret[0]="Date";
        ret[1]="Number";
        ret[2]="String";
        ret[3]="Catalog Date";
        ret[4]="Catalog Number";
        ret[5]="Catalog String";
        return ret;
    }
    
    /** Add an option value of an Attribute
     * @return Value id
     * @param idtm
     * @param idatt Attribute id
     * @param value Related Atttribute value
     */    
    public int addOptionValue(int idatt,String value, String idtm){
        
        int idvalue=0;
        Timestamp lastupdate = new Timestamp(new java.util.Date().getTime());
        DBConnectionManager mgr = null;
        Connection con;
        try {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "DBMetaData.addOptionValue");
            
            PreparedStatement st1 = con.prepareStatement("SELECT max(idvalue) FROM wbmdattvalue where idtm=?");
            st1.setString(1,idtm);
            ResultSet rs1 = st1.executeQuery();
            if (rs1.next()) {
                idvalue = rs1.getInt(1) + 1;
            } else
                idvalue = 1;
            rs1.close();
            st1.close();
            
            String query = "insert into wbmdattvalue (idatt,idvalue,value,lastupdate,idtm) values (?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, idatt);
            st.setInt(2, idvalue);
            st.setString(3, value);
            st.setTimestamp(4,lastupdate);
            st.setString(5, idtm);
            st.executeUpdate();
            st.close();
            con.close();
            
        }
        catch(Exception e){AFUtils.log(e,"Error while trying to create Attribute value. "+"DBMetaData.addOptionValue",debug_var);}
        finally {
            if (mgr != null) mgr.release();
        }
        return idvalue;
    }
    
    /** Add an option value of an Attribute
     * @return Value id
     * @param idtm
     * @param topicmapid
     * @param tableid table id
     * @param idatt Attribute id
     * @param value Related Atttribute value
     * @param topicid Topic id
     */    
    public int addOptionValue(int idatt,String value, String topicid,int tableid, String idtm, String topicmapid){
        
        //revisar si existe un valor para ese tópico antes de crear uno nuevo
        int idvalue=0;
        Timestamp lastupdate = new Timestamp(new java.util.Date().getTime());
        DBConnectionManager mgr = null;
        Connection con;
        try {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "DBMetaData.addOptionValue");
            String query = "select idvalue from wbmdtopicvalue where idatt=? and topicid=? and tableid=? and idtm=? and topicmapid=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, idatt);
            st.setString(2, topicid);
            st.setInt(3, tableid);
            st.setString(4,idtm);
            st.setString(5,topicmapid);
            ResultSet rs = st.executeQuery();
            //System.out.println("DBMetaData --- valor recibido: "+value);
            if(rs.next()){
                idvalue=rs.getInt("idvalue");
                if(value!=null&&value.trim().equals("")){
                    query = "delete from wbmdtopicvalue where idvalue=? and topicid=? and idatt=? and tableid=? and idtm=? and topicmapid=?";
                    PreparedStatement st3 = con.prepareStatement(query);
                    st3.setInt(1, idvalue);
                    st3.setString(2, topicid);
                    st3.setInt(3, idatt);
                    st3.setInt(4, tableid);
                    st3.setString(5, idtm);
                    st3.setString(6, topicmapid);
                    st3.executeUpdate();
                    st3.close();
                    query = "delete from wbmdattvalue where idvalue=? and idtm=?";
                    PreparedStatement st2 = con.prepareStatement(query);
                    st2.setInt(1, idvalue);
                    st2.setString(2, idtm);
                    st2.executeUpdate();
                    st2.close();
                }
                else{
                    if(value.trim().length()>0){
                        query = "update wbmdattvalue set value=?,lastupdate=? where idvalue=? and idtm=?";
                        PreparedStatement st2 = con.prepareStatement(query);
                        st2.setString(1, value);
                        st2.setTimestamp(2,lastupdate);
                        st2.setInt(3, idvalue);
                        st2.setString(4,idtm);
                        st2.executeUpdate();
                        st2.close();
                    }
                }
            }
            else{
                idvalue= addOptionValue(idatt,value,idtm);
                addOptionValue(idatt,idvalue,topicid,tableid,idtm,topicmapid);
            }
            st.close();
            con.close();
        }
        catch(Exception e){
            AFUtils.log(e,"Error while trying to create Attribute value."+" DBMetaData.addOptionValue",debug_var);
        }
        finally {
            if (mgr != null) mgr.release();
        }
        
        return idvalue;
    }
    
     /** Remove MDTable topic values
      * @return true or false, if the action terminate succesfully or not.
      * @param idtm
      * @param topicmapid
      * @param topicid Topic id
      * @param tableid table id
      */    
    public boolean removeMDTableTopicValues(String topicid,int tableid,String idtm, String topicmapid){
        
        boolean ret = false;
        Timestamp lastupdate = new Timestamp(new java.util.Date().getTime());
        DBConnectionManager mgr = null;
        Connection con;
        try {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "DBMetaData.addOptionValue");
            String query = "select idatt,idvalue from wbmdtopicvalue where topicid=? and tableid=? and idtm=? and topicmapid=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, topicid);
            st.setInt(2, tableid);
            st.setString(3, idtm);
            st.setString(4, topicmapid);
            ResultSet rs = st.executeQuery();
            //System.out.println("DBMetaData --- valor recibido: "+value);
            while(rs.next()){
                String query2 = "select * from wbmdattribute where idatt=? and idtm=?";
                PreparedStatement st2 = con.prepareStatement(query2);
                st2.setInt(1, rs.getInt("idatt"));
                st2.setString(2,idtm);
                ResultSet rs2 = st2.executeQuery();
                if(rs2.next()){
                    if(!rs2.getString("type").startsWith("Catalog")){
                        
                        String query3 = "delete from wbmdattvalue where idvalue=? and idatt=? and idtm=?";
                        PreparedStatement st3 = con.prepareStatement(query3);
                        st3.setInt(1, rs.getInt("idvalue"));
                        st3.setInt(2, rs.getInt("idatt"));
                        st3.setString(3, idtm);
                        st3.executeUpdate();
                        st3.close();
                    
                    }
                    
                    String query4 = "delete from wbmdtopicvalue where idvalue=? and topicid=? and idatt=? and tableid=? and idtm=? and topicmapid=?";
                    PreparedStatement st4 = con.prepareStatement(query4);
                    st4.setInt(1, rs.getInt("idvalue"));
                    st4.setString(2, topicid);
                    st4.setInt(3, rs.getInt("idatt"));
                    st4.setInt(4, tableid);
                    st4.setString(5,idtm);
                    st4.setString(6, topicmapid);
                    st4.executeUpdate();
                    st4.close();
                 
                }
                
                
            }
            
            st.close();
            rs.close();
            con.close();
            ret=true;
        }
        catch(Exception e){
            AFUtils.log(e,"Error while trying to remove MDTable Topic values."+" DBMetaData.removeMDTableTopicValues",debug_var);
        }
        finally {
            if (mgr != null) mgr.release();
        }
        
        return ret;
    }
    
    
    /** Add an option value of an Attribute
     * @return True or False, if an option was added succesfully
     * @param idtm
     * @param topicmapid
     * @param tableid
     * @param idatt Attribute id
     * @param idvalue value id
     * @param topicid topic id
     */    
    public boolean addOptionValue(int idatt,int idvalue, String topicid,int tableid,String idtm, String topicmapid){
        Timestamp lastupdate = new Timestamp(new java.util.Date().getTime());
        DBConnectionManager mgr = null;
        Connection con;
        try {
            mgr = DBConnectionManager.getInstance();
            con = mgr.getConnection((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/db/nameconn"), "DBMetaData.addOptionValue");
            String query = "select * from wbmdtopicvalue where topicid=? and idatt=? and idtm=? and topicmapid=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, topicid);
            st.setInt(2, idatt);
            st.setString(3, idtm);
            st.setString(4,topicmapid);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                query = "update wbmdtopicvalue set idvalue=?,lastupdate=? where idatt=? and topicid=? and tableid=? and idtm=? and topicmapid=?";
                PreparedStatement st2 = con.prepareStatement(query);
                st2.setInt(1, idvalue);
                st2.setTimestamp(2,lastupdate);
                st2.setInt(3, idatt);
                st2.setString(4, topicid);
                st2.setInt(5, tableid);
                st2.setString(6, idtm);
                st2.setString(7, topicmapid);
                st2.executeUpdate();
                st2.close();
            }
            else{
                query = "insert into wbmdtopicvalue (idatt,idvalue,topicid,lastupdate,tableid,idtm,topicmapid) values (?,?,?,?,?,?,?)";
                PreparedStatement st1 = con.prepareStatement(query);
                st1.setInt(1, idatt);
                st1.setInt(2, idvalue);
                st1.setString(3, topicid);
                st1.setTimestamp(4,lastupdate);
                st1.setInt(5, tableid);
                st1.setString(6, idtm);
                st1.setString(7, topicmapid);
                st1.executeUpdate();
                st1.close();
            }
            con.close();
        }
        catch(Exception e){
            AFUtils.log(e,"Error while trying to create or update Attribute value."+" DBMetaData.addOptionValue",debug_var);
            return(false);
        }
        finally {
            if (mgr != null) mgr.release();
        }
        return(true);
    }
    
}
