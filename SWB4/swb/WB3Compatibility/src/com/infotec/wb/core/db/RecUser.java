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
 * RecUser.java
 *
 * Created on 3 de junio de 2002, 12:29
 */

package com.infotec.wb.core.db;

import java.sql.*;


import java.util.*;

import com.infotec.appfw.exception.*;

import com.infotec.appfw.lib.AFObserver;
import com.infotec.wb.lib.WBDBRecord;
import com.infotec.appfw.util.AFUtils;
import com.infotec.topicmaps.bean.*;

import com.infotec.appfw.util.db.ObjectDecoder;
import com.infotec.appfw.util.db.ObjectEncoder;
import com.infotec.wb.core.WBLoader;


/**
 * objeto: cache de registro de base de datos de la tabla wbuser
 * <p/>
 * object: record cache of the data base of the wbuser table
 *
 * @author JAJSofteare(2004)
 * @author Sergio Martinez (2005)
 *
 */
public abstract class RecUser implements WBDBRecord, java.io.Serializable {

    


    private ArrayList observers = new ArrayList();
    private ArrayList notifys = new ArrayList();
    public static String DEFLOGIN="nologin";

    private long id = 0;
    private String login = DEFLOGIN;
    String repository = null;
    private String password = "nopassword";
    private String firstName = null;
    private String lastName = null;
    private String middleName = null;
    private String language = "es";
    private String email = null;
    private int active = 0;
    private Timestamp lastLogin = null;
    private Timestamp passwordChanged = null;
    private String confirmValue = null;
    private Timestamp created = null;
    private String home = null;
    private String xml = null;
    public Timestamp lastupdate = null;
    
    private boolean rolesLoaded = true;
    private boolean admFiltersLoaded = true;

    private ArrayList userroles = new ArrayList();
    private HashMap admfilters = new HashMap();

    org.semanticwb.model.User usr = null;

    public RecUser(org.semanticwb.model.User user)
    {
        usr = user;
    }

    public org.semanticwb.model.User getNative()
    {
        return usr;
    }


    /**
     * Creates new RecUser
     */
    public RecUser() {
    }

    /**
     * Creates new RecUser
     */
    public RecUser(String repository) {
        this.repository = repository;
        if(WBLoader.getInstance().haveDBTables())
        {
            //registerObserver(DBUser.getInstance(repository));
            if ("true".equalsIgnoreCase(DBUser.getInstance(repository).getProperty("active", "true")))
                this.active = 1;
        }
    }

    /**
     * @param login
     */
    public RecUser(String repository, String login) {
        this(repository);
        this.login = login;
        rolesLoaded = false;
        admFiltersLoaded = false;        
    }

    /**
     * Creates new RecUser
     *
     * @param firstName
     * @param lastName
     * @param middleName
     * @param language
     * @param email
     * @param password
     * @param xml
     * @param lastupdate
     */
    public RecUser(String repository, long id, String login, String password, String firstName, String lastName, String middleName, String language, String email, int active, Timestamp lastLogin, Timestamp passwordChanged, String confirmValue, Timestamp created, String home, String xml, Timestamp lastupdate) {
        this(repository);
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.language = language;
        this.email = email;
        this.active = active;
        this.lastLogin = lastLogin;
        this.passwordChanged = passwordChanged;
        this.confirmValue = confirmValue;
        this.created = created;
        this.home = home;
        this.xml = xml;
        this.lastupdate = lastupdate;
        //System.out.println("RecUser:"+id);

        rolesLoaded = false;
        admFiltersLoaded = false;        
//        try {
//            loadRoles();
//        } catch (Exception e) {
//            AFUtils.log(e);
//        }
//        try {
//            loadAdmFilters();
//        } catch (Exception e) {
//            AFUtils.log(e);
//        }
    }

    public RecUser(ObjectDecoder dec) {
        setDecoderValues(dec);
    }

    public void setDecoderValues(ObjectDecoder dec) {
        setRepository(dec.getString(0));
        this.id = dec.getLong(1);
        this.login = dec.getString(2);
        this.password = dec.getString(3);
        this.firstName = dec.getString(4);
        this.lastName = dec.getString(5);
        this.middleName = dec.getString(6);
        this.language = dec.getString(7);
        this.email = dec.getString(8);
        this.active = dec.getInt(9);
        this.lastLogin = dec.getTimeStamp(10);
        this.passwordChanged = dec.getTimeStamp(11);
        this.confirmValue = dec.getString(12);
        this.created = dec.getTimeStamp(13);
        this.home = dec.getString(14);
        this.xml = dec.getString(15);
        this.lastupdate = dec.getTimeStamp(16);
        
        rolesLoaded = false;
        admFiltersLoaded = false;        
        
//        try {
//            loadRoles();
//        } catch (Exception e) {
//            AFUtils.log(e);
//        }
//        try {
//            loadAdmFilters();
//        } catch (Exception e) {
//            AFUtils.log(e);
//        }
    }

    public ObjectEncoder getEncoder() {
        ObjectEncoder enc = new ObjectEncoder("RecUser");
        enc.addString(repository);
        enc.addLong(id);
        enc.addString(login);
        enc.addString(password);
        enc.addString(firstName);
        enc.addString(lastName);
        enc.addString(middleName);
        enc.addString(language);
        enc.addString(email);
        enc.addInt(active);
        enc.addTimestamp(lastLogin);
        enc.addTimestamp(passwordChanged);
        enc.addString(confirmValue);
        enc.addTimestamp(created);
        enc.addString(home);
        enc.addString(xml);
        enc.addTimestamp(lastupdate);
        return enc;
    }


    /**
     * Getter for property id.
     *
     * @return Value of property id.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for property id.
     *
     * @param id New value of property id.
     */
    public void setId(long id) {
        this.id = id;
        rolesLoaded = false;
        admFiltersLoaded = false;        
    }

    /**
     * getter for property rollid vector
     */
    public Iterator getRoles() {
        if (!rolesLoaded) try {
            loadRoles();
        } catch (AFException e) {
            AFUtils.log(e, "Problem loading roles for user " + login);
        }
        return userroles.iterator();
    }
    
    public List getRolesList() {
        if (!rolesLoaded) try {
            loadRoles();
        } catch (AFException e) {
            AFUtils.log(e, "Problem loading roles for user " + login);
        }
        return userroles;
    }    

    /*/
    *check if the user have roles asigned
    */
    public boolean haveRoles() {
        if (!rolesLoaded) try {
            loadRoles();
        } catch (AFException e) {
            AFUtils.log(e, "Problem loading roles for user " + login);
        }
        if (userroles.size() > 0) return true;
        else return false;
    }

    /*/
    *check if the user have roles asigned
    */
    public boolean haveRole(int roleid) {
        if (!rolesLoaded) try {
            loadRoles();
        } catch (AFException e) {
            AFUtils.log(e, "Problem loading roles for user " + login);
        }
        //System.out.println("haveRole:"+roleid+" "+userroles.size());
        return userroles.contains(new Integer(roleid));
    }

    /**
     * getter for property rollid vector
     */
    public Iterator getAdmFilters(String topicmap) {
        if (!admFiltersLoaded) try {
            loadAdmFilters();
        } catch (AFException e) {
            AFUtils.log(e,"Problem loading filters for user "+ login);
        }
        ArrayList map = (ArrayList) admfilters.get(topicmap);
        if (map == null) return new Vector().iterator();
        return map.iterator();
    }

    /*/
    *check if the user have roles asigned
    */
    public boolean haveAdmFilters(String topicmap) {
        if (!admFiltersLoaded) try {
            loadAdmFilters();
        } catch (AFException e) {
            AFUtils.log(e,"Problem loading filters for user "+ login);
        }
        ArrayList map = (ArrayList) admfilters.get(topicmap);
        if (map != null && map.size() > 0) return true;
        else return false;
    }

    /*/
    *check if the user have roles asigned
    */
    public boolean haveAdmFilter(String topicmap, int filterid) {
        if (!admFiltersLoaded) try {
            loadAdmFilters();
        } catch (AFException e) {
            AFUtils.log(e,"Problem loading filters for user "+ login);
        }
        ArrayList map = (ArrayList) admfilters.get(topicmap);
        if (map == null) return false;
        return map.contains(new Integer(filterid));
    }

    /**
     * Getter for property login.
     *
     * @return Value of property login.
     */
    public java.lang.String getLogin() {
        return login;
    }

    /**
     * Setter for property login.
     *
     * @param login New value of property login.
     */
    public void setLogin(java.lang.String login) {
        //System.out.println("setLogin:"+this.login+"-->"+login);
//        if(!this.login.equals(DEFLOGIN) && !this.login.equals(login))
//        {
//            DBUser.getInstance(getRepository()).removeUserFromCache(this);
//        }
        this.login = login;
        rolesLoaded = false;
        admFiltersLoaded = false;        
    }

    /**
     * Getter for property password.
     *
     * @return Value of property password.
     */
    public java.lang.String getPassword() {
        return password;
    }

    /**
     * Setter for property password.
     *
     * @param password New value of property password.
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    /**
     * Getter for property firstName.
     *
     * @return Value of property firstName.
     */
    public java.lang.String getFirstName() {
        return firstName;
    }

    /**
     * Setter for property firstName.
     *
     * @param firstName New value of property firstName.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for property lastName.
     *
     * @return Value of property lastName.
     */
    public java.lang.String getLastName() {
        return lastName;
    }

    /**
     * Setter for property lastName.
     *
     * @param lastName New value of property lastName.
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for property middleName.
     *
     * @return Value of property middleName.
     */
    public java.lang.String getMiddleName() {
        return middleName;
    }

    /**
     * Setter for property middleName.
     *
     * @param middleName New value of property middleName.
     */
    public void setMiddleName(java.lang.String middleName) {
        this.middleName = middleName;
    }

    /**
     * Getter for property language.
     *
     * @return Value of property language.
     */
    public java.lang.String getLanguage() {
        return language;
    }

    /**
     * Setter for property language.
     *
     * @param language New value of property language.
     */
    public void setLanguage(java.lang.String language) {
        //AFUtils.log(new Exception());
        this.language = language;
    }

    /**
     * Getter for property email.
     *
     * @return Value of property email.
     */
    public java.lang.String getEmail() {
        return email;
    }

    /**
     * Setter for property email.
     *
     * @param email New value of property email.
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    /**
     * Getter for property active.
     *
     * @return Value of property active.
     */
    public int getActive() {
        return active;
    }

    /**
     * Setter for property active.
     *
     * @param active New value of property active.
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     * Getter for property lastLogin.
     *
     * @return Value of property lastLogin.
     */
    public java.sql.Timestamp getLastLogin() {
        return lastLogin;
    }

    /**
     * Setter for property lastLogin.
     *
     * @param lastLogin New value of property lastLogin.
     */
    public void setLastLogin(java.sql.Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Getter for property passwordChanged.
     *
     * @return Value of property passwordChanged.
     */
    public java.sql.Timestamp getPasswordChanged() {
        return passwordChanged;
    }

    /**
     * Setter for property passwordChanged.
     *
     * @param passwordChanged New value of property passwordChanged.
     */
    public void setPasswordChanged(java.sql.Timestamp passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    /**
     * Getter for property confirmValue.
     *
     * @return Value of property confirmValue.
     */
    public java.lang.String getConfirmValue() {
        return confirmValue;
    }

    /**
     * Setter for property confirmValue.
     *
     * @param confirmValue New value of property confirmValue.
     */
    public void setConfirmValue(java.lang.String confirmValue) {
        this.confirmValue = confirmValue;
    }

    /**
     * Getter for property created.
     *
     * @return Value of property created.
     */
    public java.sql.Timestamp getCreated() {
        return created;
    }

    /**
     * Setter for property created.
     *
     * @param created New value of property created.
     */
    public void setCreated(java.sql.Timestamp created) {
        this.created = created;
    }

    /**
     * Getter for property home.
     *
     * @return Value of property home.
     */
    public java.lang.String getHome() {
        return home;
    }

    /**
     * Setter for property home.
     *
     * @param home New value of property home.
     */
    public void setHome(java.lang.String home) {
        this.home = home;
    }

    /**
     * Getter for property xml.
     *
     * @return Value of property xml.
     */
    public java.lang.String getXml() {
        return xml;
    }

    /**
     * Setter for property xml.
     *
     * @param xml New value of property xml.
     */
    public void setXml(java.lang.String xml) {
        this.xml = xml;
    }

    /**
     * Getter for property lastupdate.
     *
     * @return Value of property lastupdate.
     */
    public java.sql.Timestamp getLastupdate() {
        return lastupdate;
    }

    /**
     * Setter for property lastupdate.
     *
     * @param lastupdate New value of property lastupdate.
     */
    public void setLastupdate(java.sql.Timestamp lastupdate) {
        this.lastupdate = lastupdate;
    }

    /**
     * registra el objeto observador para que pueda recibir notoficaciones de cambios
     *
     * @param obs
     */
    public void registerObserver(AFObserver obs) {
        //System.out.println(this.getClass().getName()+"-"+this+" Registro a: "+obs.getClass().getName());
        if (obs != null && !observers.contains(obs)) observers.add(obs);
    }

    /**
     * registra el objeto observador para que pueda recibir notoficaciones de cambios
     *
     * @param obs
     */
    public void removeObserver(AFObserver obs) {
        //System.out.println(this.getClass().getName()+"-"+this+" Quito a: "+obs.getClass().getName());
        observers.remove(obs);
    }

    /**
     * Elimina el registro de la base de datos asi como todopublic void remove() throws AFException
     *
     * @param user
     * @param comment
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void remove(String user, String comment) throws AFException {
        remove();
//        RecAdmLog rec = new RecAdmLog(user, "remove", "User", getId(), getRepository(), getLogin(), comment, lastupdate);
//        rec.create();
    }

    /**
     * Elimina el registro de la base de datos asi como todopublic void remove() throws AFException
     *
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void remove() throws AFException {
        try {
            usr.remove();
            removeImp();
            sendNotify("remove");
//            DBDbSync.getInstance().saveChange("wbuser", "remove", id, id + "_" + repository, null);
//            Connection con;
//            con = AFUtils.getDBConnection(AFUtils.getEnv("wb/db/nameconn"), "RecUser.loadRoles()");
//            String query = "delete from wbuserrole where usrId=? and usrRepository=?";
//            PreparedStatement st = con.prepareStatement(query);
//            st.setLong(1, getId());
//            st.setString(2, repository);
//            st.executeUpdate();
//            st.close();
//            con.close();

        } catch (Exception e) {
            throw new AFException("Cannot delete element" + "...", "RecUser:remove()", e);
        }
    }

    public abstract void removeImp() throws Exception;

    /**
     * actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     *
     * @param user
     * @param comment
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void update(String user, String comment) throws AFException {
        update();
//        RecAdmLog rec = new RecAdmLog(user, "update", "User", getId(), getRepository(), getLogin(), comment, lastupdate);
//        rec.create();
    }

    /**
     * actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     *
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void update() throws AFException {
        try {
            lastupdate = new Timestamp(new java.util.Date().getTime());
            updateImp();
            sendNotify("update");
//            DBDbSync.getInstance().saveChange("wbuser", "update", id, id + "_" + repository, lastupdate);

        } catch (Exception e) {
            AFUtils.log(e, "Cannot update element", true);
            throw new AFException("Cannot update element" + "...", "RecUser:update()", e);
        }
    }

    public abstract void updateImp() throws Exception;

    /**
     * crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     *
     * @param user
     * @param comment
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void create(String user, String comment) throws AFException {
        create();
//        RecAdmLog rec = new RecAdmLog(user, "create", "User", getId(), getRepository(), getLogin(), comment, lastupdate);
//        rec.create();
    }

    /**
     * crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     *
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void create() throws AFException {
       // Connection con;
        try {
      //      con = AFUtils.getDBConnection(AFUtils.getEnv("wb/db/nameconn"), "RecUser.create()");

            lastupdate = new Timestamp(new java.util.Date().getTime());
            lastLogin = lastupdate;
            passwordChanged = lastupdate;
            created = lastupdate;
            if (id == 0) {
                id = DBCatalogs.getInstance().getCounter("wbuser_" + repository);
            }
            createImp();
            sendNotify("create");
//            DBDbSync.getInstance().saveChange("wbuser", "create", id, id + "_" + repository, lastupdate);

        } catch (Exception e) {
            throw new AFException("Cannot create element" + "...", "RecUser:create()", e);
        }
    }

    public abstract void createImp() throws Exception;

    /**
     * Set roles from DB to the user
     */
    public void loadRoles() throws AFException {
        //System.out.println("loadRoles:"+id);
        try {
            userroles = new ArrayList();
            Connection con;
            con = AFUtils.getDBConnection(AFUtils.getEnv("wb/db/nameconn"), "RecUser.loadRoles()");
            PreparedStatement st = null;
            if (id > 0) {
                String query = "select rolId from wbuserrole where usrId=? and usrRepository=?";
                st = con.prepareStatement(query);
                st.setLong(1, id);
                st.setString(2, repository);
            } else {
                throw new Exception();
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                if (!userroles.contains(new Integer(rs.getInt("rolId")))) {
                    //System.out.println("Add:"+rs.getInt("rolId"));
                    userroles.add(new Integer(rs.getInt("rolId")));
                }
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            throw new AFException("Error while loading roles" + "...", "RecUser:loadRoles", e);
        }
        rolesLoaded = true;
    }


    /**
     * add a role to the user in DB
     *
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void addRole(int rolid) throws AFException {
        if (!rolesLoaded) try {
            loadRoles();
        } catch (AFException e) {
            AFUtils.log(e, "Problem loading roles for user " + login);
        }
        if (!userroles.contains(Integer.valueOf(String.valueOf(rolid)))) {
            //if (!((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/enableLDAP")).equals("true"))
            {
                Connection con;
                try {
                    con = AFUtils.getDBConnection(AFUtils.getEnv("wb/db/nameconn"), "RecUser.addRole()");
                    lastupdate = new Timestamp(new java.util.Date().getTime());

                    String query = "insert into wbuserrole (usrId, usrRepository, rolId, lastupdate) values (?,?,?,?)";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setLong(1, id);
                    st.setString(2, repository);
                    st.setInt(3, rolid);
                    st.setTimestamp(4, lastupdate);
                    st.executeUpdate();
                    st.close();
                    con.close();
                    //RecAdmLog rec = new RecAdmLog(userid, "add", "role", getId(), null, null, comment, lastupdate);
                } catch (Exception e) {
                    AFUtils.log(e);
                    throw new AFException("Error while adding role to user, idrol" + ":" + rolid, "RecUser:addrole()", e);
                }
            }
            userroles.add(Integer.valueOf(String.valueOf(rolid)));
        }
    }

    /**
     * remove a role from user in DB
     *
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void removeRole(int rolid) throws AFException {
        if (!rolesLoaded) try {
            loadRoles();
        } catch (AFException e) {
            AFUtils.log(e, "Problem loading roles for user " + login);
        }
        if (userroles.contains(Integer.valueOf(String.valueOf(rolid)))) {
            //if (!((String) com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/enableLDAP")).equals("true"))
            {
                Connection con;
                try {
                    con = AFUtils.getDBConnection(AFUtils.getEnv("wb/db/nameconn"), "RecUser.addRole()");
                    lastupdate = new Timestamp(new java.util.Date().getTime());

                    String query = "delete from wbuserrole where usrId=? and usrRepository=? and rolId=?";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setLong(1, id);
                    st.setString(2, repository);
                    st.setInt(3, rolid);
                    st.executeUpdate();
                    st.close();
                    con.close();
                    //RecAdmLog rec = new RecAdmLog(userid, "remove", "role", getId(), null, null, comment, lastupdate);
                } catch (Exception e) {
                    throw new AFException("Error while removing role from user, idrol" + ":" + rolid, "RecUser:removeRole()", e);
                }
            }
            userroles.remove(Integer.valueOf(String.valueOf(rolid)));
        }
    }

    /**
     * Set roles from DB to the user
     */
    public void loadAdmFilters() throws AFException {
        try {
            admfilters = new HashMap();
            Connection con;
            con = AFUtils.getDBConnection(AFUtils.getEnv("wb/db/nameconn"), "RecUser.loadAdmFolters()");
            PreparedStatement st = null;
            if (id > 0) {
                String query = "select * from wbuserfilter where usrId=? and usrRepository=?";
                st = con.prepareStatement(query);
                st.setLong(1, id);
                st.setString(2, repository);
            } else {
                throw new Exception();
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Integer fltId = new Integer(rs.getInt("fltId"));
                String idtm = TopicMgr.TM_ADMIN;
                try {
                    idtm = rs.getString("idtm");
                } catch (Exception e) {
                    AFUtils.log(e);
                }
                ArrayList map = (ArrayList) admfilters.get(idtm);
                if (map == null) {
                    map = new ArrayList();
                    admfilters.put(idtm, map);
                }
                map.add(fltId);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            throw new AFException("Error while loading roles" + "...", "RecUser:loadAdmFolters", e);
        }
        admFiltersLoaded = true;
    }


    /**
     * add a role to the user in DB
     *
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void addAdmFilter(String topicmap, int fltid) throws AFException {
        if (!admFiltersLoaded) try {
            loadAdmFilters();
        } catch (AFException e) {
            AFUtils.log(e,"Problem loading filters for user "+ login);
        }
        if (!haveAdmFilter(topicmap, fltid)) {
            try {
                Connection con = AFUtils.getDBConnection(AFUtils.getEnv("wb/db/nameconn"), "RecUser.addAdmFilter()");
                lastupdate = new Timestamp(new java.util.Date().getTime());

                String query = "insert into wbuserfilter (usrId, usrRepository, fltId, idtm, lastupdate) values (?,?,?,?,?)";
                PreparedStatement st = con.prepareStatement(query);
                st.setLong(1, id);
                st.setString(2, repository);
                st.setInt(3, fltid);
                st.setString(4, topicmap);
                st.setTimestamp(5, lastupdate);
                st.executeUpdate();
                st.close();
                con.close();

                ArrayList map = (ArrayList) admfilters.get(topicmap);
                if (map == null) {
                    map = new ArrayList();
                    admfilters.put(topicmap, map);
                }
                map.add(new Integer(fltid));

            } catch (Exception e) {
                throw new AFException("Error while adding filter to user, filterId" + ":" + fltid, "RecUser:addAdmFilter()", e);
            }

        }
    }

    /**
     * remove a role from user in DB
     *
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void removeAdmFilter(String topicmap, int fltid) throws AFException {
        if (!admFiltersLoaded) try {
            loadAdmFilters();
        } catch (AFException e) {
            AFUtils.log(e,"Problem loading filters for user "+ login);
        }
        if (haveAdmFilter(topicmap, fltid)) {
            Connection con;
            try {
                con = AFUtils.getDBConnection(AFUtils.getEnv("wb/db/nameconn"), "RecUser.removeRole()");
                lastupdate = new Timestamp(new java.util.Date().getTime());

                String query = "delete from wbuserfilter where usrId=? and usrRepository=? and fltId=? and idtm=?";
                PreparedStatement st = con.prepareStatement(query);
                st.setLong(1, id);
                st.setString(2, repository);
                st.setInt(3, fltid);
                st.setString(4, topicmap);
                st.executeUpdate();
                st.close();
                con.close();

                ArrayList map = (ArrayList) admfilters.get(topicmap);
                if (map != null) {
                    map.remove(new Integer(fltid));
                }

            } catch (Exception e) {
                throw new AFException("Error while removing filter from user, idfilter" + ":" + fltid, "RecUser:removeRole()", e);
            }
        }
    }

    /**
     * refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     *
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void load() throws AFException {
        load(true);
    }

    /**
     * refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     *
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void load(boolean notify) throws AFException {
        try {
            userroles = new ArrayList();
            admfilters = new HashMap();
            
            loadImp();
            
            rolesLoaded = false;
            admFiltersLoaded = false;        
            // loadRoles();
            // loadAdmFilters();
            if (notify) sendNotify("load");
        } catch (Exception e) {
            throw new AFException("Error while loading user" + "...", "RecUser:load()", e);
        }
    }

    public abstract void loadImp() throws Exception;

    public String getName() {
        String n = com.infotec.appfw.util.AFUtils.stringNullValidate(firstName);
        if (n.length() > 0) n += " ";
        n += com.infotec.appfw.util.AFUtils.stringNullValidate(lastName);
        return n;
    }

    public void sendNotify() {
        Iterator nt = new ArrayList(notifys).iterator();
        while (nt.hasNext()) {
            String message = (String) nt.next();
            Iterator it = observers.iterator();
            while (it.hasNext()) {
                ((AFObserver) it.next()).sendDBNotify(message, this);
            }
            nt.remove();
        }
    }

    /**
     * @param message
     */
    public void sendNotify(String message) {
        Iterator it = new ArrayList(observers).iterator();
        while (it.hasNext()) {
            ((AFObserver) it.next()).sendDBNotify(message, this);
        }
    }

    /**
     * @param action
     * @param date
     * @throws com.infotec.appfw.exception.AFException
     *
     */
    public void syncFromExternalAction(String action, Timestamp date) throws AFException {
        if (getLastupdate() == null || (getLastupdate() != null && getLastupdate().before(date))) {
            if (action.equals("remove")) {
                sendNotify(action);
            } else if (action.equals("create") || action.equals("update")) {
                load();
            }
        }
    }

    /* revisar wb3 ******/
    public int getAdmType() {
        return 0;
    }

    /**
     * Getter for property repository.
     *
     * @return Value of property repository.
     */
    public java.lang.String getRepository() {
        return repository;
    }

    /**
     * Setter for property repository.
     */
    public void setRepository(String repository) {
        this.repository = repository;
        observers.clear();
//        registerObserver(DBUser.getInstance(repository));
    }

    /**
     * Writes this object out to a stream (i.e., serializes it).
     */
//    private synchronized void writeObject(java.io.ObjectOutputStream oos) throws java.io.IOException {
//        //System.out.println("writeObject");
//        oos.writeUTF(getEncoder().toString());
//        //oos.defaultWriteObject();
//    }
//
//    /**
//     * Reads this object from a stream (i.e., deserializes it)
//     */
//    private void readObject(java.io.ObjectInputStream s) throws
//            java.io.IOException,
//            ClassNotFoundException {
//
//        //System.out.println("readObject");
//        String str = s.readUTF();
//        if (str != null) {
//            ObjectDecoder dec = new ObjectDecoder(str);
//            setDecoderValues(dec);
//        }
//        //s.defaultReadObject();
//
//    }


}
