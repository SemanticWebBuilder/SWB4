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


package com.infotec.wb.core;

/**
 *
 * Usuario en WebBuilder
 * WebBuilder User 
 *
 * @author JAJSoftware(2004)
 * @version
 * @author Sergio Martínez Rediseño Nov 2004
 */

import com.infotec.appfw.exception.AFException;
import com.infotec.appfw.lib.AFObserver;
import com.infotec.appfw.util.AFUtils;
import com.infotec.topicmaps.Occurrence;
import com.infotec.topicmaps.Topic;
import com.infotec.topicmaps.TopicMap;
import com.infotec.topicmaps.bean.TopicMgr;
import com.infotec.wb.core.db.DBUser;
import com.infotec.wb.core.db.RecUser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/**
 * Objeto user: Este objeto se encarga de administrar los datos de un
 * usuario final que entra en sesion.
 * <p/>
 * Object to user: This object is in charge to administer the data of an end user
 * who enters in session.
 */
public class WBUser implements HttpSessionBindingListener, AFObserver, java.io.Serializable {
    private RecUser recUser = null;
    private String device;
    private String languageU;
    private String navegador;
    private String ip;
    private boolean isloged;
    private boolean isregistered;
    //private WBUserMgr usm;
    private String sesid = null;
    private Document dom;
    private String tipousu = null;
    private String repository = null;
    private Subject authSubject = null;

    public WBUser(String repository) {
        device = null;
        navegador = null;
        isloged = false;
        //this.usm = null;
        this.sesid = null;
        this.repository = repository;
        this.setRecUser(DBUser.getInstance(repository).getNewRecUser());
    }

    public WBUser(RecUser recuser) {
        device = null;
        navegador = null;
        isloged = false;
        //this.usm = null;
        this.sesid = null;
        this.setRecUser(recuser);
        repository = recuser.getRepository();
    }


    /**
     * Metodo que se encarga de crear un usuario final, en caso de estar
     * registrado carga todos sus datos, de lo contrario, solo crea el objeto
     *
     * @param usm     <PRE>Este es un objeto referencia de la clase <I>WBUserMgr</I></PRE>
     * @param request <PRE>Número de sesion</PRE>
     */
    public WBUser(String sessid, String repository) {
        device = null;
        navegador = null;
        isloged = false;
        //this.usm = usm;
        this.sesid = sessid;
        this.repository = repository;
        this.setRecUser(DBUser.getInstance(repository).getNewRecUser());
    }

    /**
     * Metodo que se encarga de crear un usuario final, en caso de estar
     * registrado carga todos sus datos, de lo contrario, solo crea el objeto
     *
     * @param usm     <PRE>Este es un objeto referencia de la clase <I>WBUserMgr</I></PRE>
     * @param request <PRE>Número de sesion</PRE>
     */
    public WBUser(HttpServletRequest request, String repository) {
        device = null;
        navegador = null;
        isloged = false;
        //this.usm = usm;
        this.sesid = request.getSession().getId();
        this.repository = repository;
        this.setRecUser(DBUser.getInstance(repository).getNewRecUser(request));
    }


    /**
     * Metodo que regresa el tipo de usuario final
     *
     * @return <PRE>Regresa Nombre de usuario de sesion</PRE>
     */
    public String getUserType() {
        return tipousu;
    }

    public String getName() {
        return recUser.getName();
    }

    /**
     * Metodo que regresa el nombre del usuario final
     *
     * @return <PRE>Regresa Nombre de usuario de sesion</PRE>
     */
    public String getFirstName() {
        return recUser.getFirstName();
    }

    /**
     * Metodo que regresa el email de un usuario final
     *
     * @return <PRE>Regresa Email de usuario de sesion</PRE>
     */
    public String getEmail() {
        return recUser.getEmail();
    }

    /**
     * Metodo que regresa el appellido materno de un usuario final
     *
     * @return <PRE>Regresa Apellido materno de Usuario de Sesion</PRE>
     */
    public String getMiddleName() {
        return recUser.getMiddleName();
    }

    /**
     * Metodo que regresa el apellido paterno de un usuario final
     *
     * @return <PRE>Regresa Apellido paterno de Usuario de sesion</PRE>
     */
    public String getLastName() {
        return recUser.getLastName();
    }

    public String getLogin() {
        return recUser.getLogin();
    }

    public String getId() {
        return recUser.getId() + "_" + repository;
    }

    public String getPassword() {
        return recUser.getPassword();
    }


    /**
     * Metodo que adiere un lenguage a un usuario final
     *
     * @param language :language de usuario
     */
    public void setLanguage(String language) {
        setAttribute("LANGUAGE", language);
        languageU = language;
        recUser.setLanguage(language);
    }

    /**
     * Metodo que obtine el lenguage de un usuario final
     *
     * @return <PRE>Regresa el lenguaje del Usuario de Sesion</PRE>
     */
    public String getLanguage() {
        return languageU;
    }


    /**
     * Metodo que adiere un dispositivo a un usuario final
     *
     * @param device :device de un usuario final
     */
    public void setDevice(String device) {
        if (device != null) {
            this.device = device;
            NodeList tmp = dom.getElementsByTagName("device");
            if (null != tmp) {
                int lgt = tmp.getLength();
                for (int i = 0; i < lgt; i++)
                    try {
                        dom.getChildNodes().item(0).removeChild(tmp.item(i));
                    } catch (Exception ignore) {
                    }
            }
            Element el = dom.createElement("device");
            el.appendChild(dom.createTextNode(device));
            if (getNavegador() != null) {
                String navwith = getNavegador();
                el.setAttribute("NavWith", navwith);
            }
            dom.getChildNodes().item(0).appendChild(el);
        }

        String val = "false";
        if (isregistered) val = "true";
        setAttribute("isregistered", val);

        if (isloged) {
            val = "true";
        } else {
            val = "false";
        }
        setAttribute("isloged", val);
    }

    /**
     * Metodo que obtine el dispositivo que este utilizando un usuario final
     *
     * @return <PRE>Regresa el dispositivo que esta utilizando el usuario de la
     *         sesion para ver el sitio en ese momento</PRE>
     */
    public String getDevice() {
        return device;
    }

    /**
     * Metodo que agrega el navegador que esta utilizando el usuario
     * final a memoria.
     *
     * @param navegador <PRE>Graba en el objeto el Navegador que esta siendo utilizado
     *                  por el usuario de la sesion, esto, en caso de estar utilizando
     *                  algun navegador de PC</PRE>
     * @deprecated el uso del device debería ser más que suficiente
     */
    public void setNavegador(String navegador) {
        this.navegador = navegador;
    }

    /**
     * Meto que obtiene de memoria el navegador que este utilizando un
     * usuario final.
     *
     * @return <PRE>Regresa el Navegador que esta siendo utilizado por el usuario de
     *         la sesion, en caso de estar en una pc.
     *         </PRE>
     * @deprecated el uso del device debería ser más que suficiente
     */
    public String getNavegador() {
        return navegador;
    }

    /**
     * Metodo que obtiene el estatus de logeado o no logeado de un
     * usuario final
     *
     * @return <PRE>Regresa true si el usuario esta logeado, de lo contrario regresa false</PRE>
     */
    public boolean isLoged() {
        if (authSubject != null && authSubject.getPrincipals().contains(this.getLogin())) {
            return true;
        }
        return isloged;
    }

    /**
     * Metodo que desconecta o deslogea a un usuario final
     */
    public void logout() {
        isloged = false;
        setAttribute("isloged", "false");
    }

    /**
     * Metodo que desconecta o deslogea a un usuario final
     */
    public void closeSession(HttpServletRequest request) {
        isloged = false;
        isregistered = false;
        setAttribute("isloged", "false");
        request.getSession().removeAttribute(WBUserMgr.UserAtt + "-" + getRepository());
        WBUserMgr.getInstance().removeUser(request.getSession().getId(), getRepository());
        setRecUser(DBUser.getInstance(getRepository()).getNewRecUser());
    }

    /**
     * Metodo que loguea a un usuario final
     *
     * @param login      <PRE>Recibe login que desea logearse</PRE>
     * @param credential <PRE>Recibe password de usuario para poder autentificarlo en caso
     *                   de que ya este registrado</PRE>
     * @param request    <PRE>Recibe request, el cual sera utilizado para realizar busqueda
     *                   de cookies</PRE>
     * @param response   <PRE>Recibe response para realizar borrado de cookies en caso de
     *                   requerirse</PRE>
     * @return <PRE>Regresa true en caso de haberse logeado satisfactoriamente el usuario, o recibe
     *         false en lo contrario</PRE>
     */

    public void sendLoginLog(HttpServletRequest request) {
        //User session log
        {
            StringBuffer logbuf = new StringBuffer();
            logbuf.append("lgn|");
            logbuf.append(request.getRemoteAddr());
            logbuf.append("|");
            logbuf.append(WBMessageCenter.getInstance().getAddress());
            logbuf.append("|");
            logbuf.append(this.getRepository());
            logbuf.append("|");
            logbuf.append(this.getLogin());
            logbuf.append("|");
            logbuf.append(""+sesid.hashCode());
            WBMessageCenter.getInstance().sendMessage(logbuf.toString());
        }
    }


    public boolean loginUser(String login, Object credential, HttpServletRequest request, HttpServletResponse response, String repository) {
        RecUser recordUser = null;
        try {
            recordUser = DBUser.getInstance(repository).getLoginUser(login, credential);
        }
        catch (AFException e) {
            //The user wasn't authenticated so we just pass along the exception
        }
/*      if(usm.getUser(this,request, response,login,credential,repository)!=null){
            isloged = true;
            isregistered = true;
            setAttribute("isloged","true");
            setAttribute("isregistered","true");
            return true;
        }
 */
        if (null != recordUser) {

            if (1 == recordUser.getActive()) {
                this.setRecUser(recordUser);
                isloged = true;
                isregistered = true;
                setAttribute("isloged", "true");
                setAttribute("isregistered", "true");
                sendLoginLog(request);
                //System.out.println("reset:WBCookieMgr");
                request.getSession().removeAttribute("WBCookieMgr");
                return true;
            } else return false;
        }
        return false;
    }

    /**
     * Lee un atributo del DOM del Usuario por medio del template
     * parametros:
     * <b>name</b> -> nombre del atributo
     * <b>defaultvalue</b> -> valor por defecto
     * Si el atributo no esta declarado regresa el valor por defecto defvalue.
     */
    public String getAttribute(java.util.HashMap params) {
        String name = (String) params.get("name");
        String defvalue = (String) params.get("defaultvalue");
        String ret = getAttribute(name);
        if (ret == null) ret = defvalue;
        return ret;
    }

    /**
     * Lee un atributo del DOM del Usuario
     * Si el atributo no esta declarado regresa el valor por defecto defvalue.
     */
    public String getAttribute(String name, String defvalue) {
        String ret = getAttribute(name);
        if (ret == null) ret = defvalue;
        return ret;
    }

    /**
     * Lee un atributo del DOM del Usuario
     * Si el atributo no esta declarado regresa null.
     */
    public String getAttribute(String name) {
        String ret = null;
        try {
            NodeList data = dom.getElementsByTagName(name);
            if (data.getLength() > 0) {
                Node txt = data.item(0).getFirstChild();
                if (txt != null) ret = txt.getNodeValue();
            }
        }
        catch (Exception e) {
            AFUtils.log(e, "Error reading:" + name, true);
        }
        return ret;
    }

    /**
     * Asigna un atributo al DOM del usuario.
     * Si no existe el atributo, lo crea y si existe lo modifica
     *
     * @param name  String nombre del atributo
     * @param value String valor del atributo
     */
    public void setAttribute(String name, String value) {
        if ("_wb_token".equals(name)) {
            if ("true".equals(value) && 1 == this.getRecUser().getActive()) {
                isloged = true;
                isregistered = true;
                setAttribute("isloged", "true");
                setAttribute("isregistered", "true");
            } else {
                isloged = false;
            }
        }
        try {
            AFUtils.setDomAttribute(dom, name, value);
        }
        catch (Exception e) {
            AFUtils.log(e, "Error while setting attribute to the user" + " " + name + " ->WBUser:setAttribute " + getId(), true);
        }
    }


    /**
     * Metodo que obtiene el valor de una propiedad del xml de configuración
     * de un usuario final
     *
     * @param propertyname <PRE>Paso un nombre de propiedad a ser buscado por este metodo
     *                     en el DOM</PRE>
     * @return <PRE>Regresa un Enumeration de valores de la propiedad solicitada</PRE>
     */
    public Enumeration getPropertyValue(String propertyname) {
        if ("_userType".equals(propertyname)) propertyname = "USERTYPE";
        Vector values = new Vector();
        String valor = null;
        NodeList propvalue = dom.getElementsByTagName(propertyname);
        boolean isnull = true;
        for (int x = 0; x < propvalue.getLength(); x++) {
            isnull = false;
            if (propvalue.item(x).getChildNodes().item(0) != null) {
                valor = propvalue.item(x).getChildNodes().item(0).getNodeValue();
                values.addElement(valor);
            }
        }
        if (isnull) return null;
        return values.elements();
    }

    /**
     * Metodo que obtine los nombres de los tags de propiedades de el
     * xml de configuración de un usuario final.
     *
     * @return <PRE>Regresa Enumeration con los nombres de todas
     *         las propiedades contenidadas en el DOM</PRE>
     */
    public Enumeration getPropertyNames() {
        Vector en = new Vector();
        NodeList nodeL = dom.getChildNodes().item(0).getChildNodes();
        for (int x = 0; x < nodeL.getLength(); x++) {
            String nodeP = nodeL.item(x).getNodeName();
            String nodeName = null;
            if (nodeP != null && !nodeP.equals("#text")) {
                nodeName = nodeL.item(x).getNodeName().trim();
                if (!en.contains(nodeName)) {
                    en.addElement(nodeName);
                }
            }
        }
        return en.elements();
    }

    /**
     * Metodo que obtiene el xml de configuarción de un usuario final.
     *
     * @return <PRE>Regresa string de xml</PRE>
     */
    public String getXML() {
        String ret = null;
        try {
            ret = com.infotec.appfw.util.AFUtils.getInstance().DomtoXml(dom);
        }
        catch (Exception e) {
            AFUtils.log(e, "Error while getting Xml from user" + recUser.getEmail(), true);
        }
        return ret;
    }

    /**
     * Metodo a agrega la ip de un usuario final a memoria.
     *
     * @param ip :ip de usuario final
     */
    public void setIP(String ip) {
        this.ip = ip;
    }

    /**
     * Metodo que obtiene la ip de un usuario final que este en sesion
     * en ese momento.
     *
     * @return :Regresa la ip de un usuario final que este en ese momento
     *         en sesion.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Metodo que inserta o remplaza el dom de configuración de un
     * usuario en especifico que este en sesion.
     *
     * @param doc <PRE>Recibe un DOM, el cual remplazara al del usuario de
     *            la sesion de ese momento</PRE>
     */
    public void setDom(Document doc) {
        dom = doc;
    }

    /**
     * Metodo que obtiene el dom de configuración de un usuario final que
     * este em sesion.
     *
     * @return <PRE>Regresa el DOM del usuario que este en la sesion</PRE>
     */
    public Document getDom() {
        return dom;
    }

    /**
     * @param e <PRE>Recibe el evento de terminación de la sesion</PRE>
     */
    public void valueUnbound(HttpSessionBindingEvent e) {
        WBUserMgr.getInstance().removeUser(sesid, repository);
        AFUtils.debug(this + " logged out or timed out.");
    }

    /**
     * @param e <PRE>Recibe evento de inicio de sesion</PRE>
     */
    public void valueBound(HttpSessionBindingEvent e) {
        AFUtils.debug("logged in.");
    }

    /**
     * return user roles Iterator of Integer (Roles IDs)
     */
    public Iterator getRoles() {
        return recUser.getRoles();
    }

    /**
     * adds a role to the user
     *
     public void addRole(int rolid) throws AFException {
     recUser.addRole(rolid);
     }*/

    /**
     * adds a role to the user
     * <p/>
     * public void removeRole(int rolid) throws AFException {
     * recUser.removeRole(rolid);
     * }
     */

    /*/
    *check if the user have roles asigned
    */
    public boolean haveRoles() {
        return recUser.haveRoles();
    }

    /*/
    *check if the user have roles asigned
    */
    public boolean haveRole(int role) {
        return recUser.haveRole(role);
    }

    /**
     * return Integer AdmFilter iterator
     */
    public Iterator getAdmFilters(String topicmap) {
        return recUser.getAdmFilters(topicmap);
    }

    /**
     * adds a role to the user
     *
     public void addAdmFilter(int filterid) throws AFException {
     recUser.addAdmFilter(filterid);
     }*/

    /**
     * adds a role to the user
     * <p/>
     * public void removeAdmFilter(int filterid) throws AFException {
     * recUser.removeAdmFilter(filterid);
     * }
     */

    /*
    *check if the user have roles asigned
    */
    public boolean haveAdmFilters(String topicmap) {
        return recUser.haveAdmFilters(topicmap);
    }

    /*
    *check if the user have roles asigned
    */
    public boolean haveAdmFilter(String topicmap, int filter) {
        return recUser.haveAdmFilter(topicmap, filter);
    }

    public boolean havePermission(Topic permission) {
        Iterator it = getRoles();
        while (it.hasNext()) {
            Integer val = (Integer) it.next();
            Role role = RoleMgr.getInstance().getRole(getRepository(), val.intValue());
            if (role != null && role.havePermission(permission)) return true;
        }
        return false;
    }

    /**
     * @param iRule <PRE>Evalua una regla para ver si es compatible con
     * el usuario de la sesion</PRE>
     * @return <PRE>Regresa true si la regla aplica para el usuario de la sesion, de lo contrario regresa false</PRE>
     */
    /*
    public boolean eval(int iRule) {
       boolean bRule=RuleMgr.getInstance().eval(this,iRule);
       return bRule;
    }
    */

    /**
     * @param request
     * @param response
     * @param slanguage
     * @return
     */
    public boolean icooklanguage(HttpServletRequest request, HttpServletResponse response, String slanguage) {
        boolean regresa = false;
        if (WBUserMgr.getInstance().addcooklang(request, response, slanguage)) ;
        regresa = true;
        return regresa;
    }

    /**
     * Getter for property recUser.
     *
     * @return <PRE>Regresa el registro completo de el usuario de la sesion</PRE>
     */
    public RecUser getRecUser() {
        return recUser;
    }

    /**
     * Setter for property recUser.
     *
     * @param recordUser <PRE>Asigna un registro a el usuario de la sesion</PRE>
     */
    public synchronized void setRecUser(RecUser recordUser) {
        this.recUser = recordUser;
        if (null == recordUser.getLogin() || "nologin".equals(recordUser.getLogin())) {
            isregistered = false;
        } else {
            isregistered = true;
        }
        try {
            dom = null;
            if (recordUser.getXml() != null && recordUser.getXml().trim().length() > 0) {
                dom = com.infotec.appfw.util.AFUtils.getInstance().XmltoDom(recordUser.getXml());
            }
        }
        catch (Exception e) {
            if (e instanceof java.io.UTFDataFormatException) {
                try {
                    dom = null;
                    if (recordUser.getXml() != null && recordUser.getXml().trim().length() > 0) {
                        dom = com.infotec.appfw.util.AFUtils.getInstance().XmltoDom(AFUtils.encode(recordUser.getXml(), "UTF-8"));
                    }
                } catch (Exception ignore) {
                    AFUtils.log(e, "Error while transforming xml from user, WBUser:setRecUser", true);
                }
            } else
                AFUtils.log(e, "Error while reading xml from user, WBUser:setRecUser", true);
        }

        if (dom == null) {
            try {
                dom = com.infotec.appfw.util.AFUtils.getInstance().getNewDocument();
                Element el = dom.createElement("user");
                dom.appendChild(el);
            }
            catch (Exception e) {
                AFUtils.log(e, "Error while creating new document to user,WBUser:setRecUser" + recUser.getEmail(), true);
            }
        }

        if (dom != null) {
            try {

                if (recordUser.getFirstName() != null) {
                    setAttribute("FNAME", recordUser.getFirstName());
                }
                if (recordUser.getLastName() != null) {
                    setAttribute("LNAME", recordUser.getLastName());
                }
                if (recordUser.getMiddleName() != null) {
                    setAttribute("MNAME", recordUser.getMiddleName());
                }
                if (recordUser.getLanguage() != null) {
                    setAttribute("LANGUAGE", recordUser.getLanguage());
                    languageU = recordUser.getLanguage();
                }
                if (recordUser.getEmail() != null) {
                    setAttribute("EMAIL", recordUser.getEmail());
                }
                if (recordUser.getLogin() != null) {
                    setAttribute("LOGIN", recordUser.getLogin());
                }
                if (recordUser.getConfirmValue() != null) {
                    setAttribute("CONFIRMVALUE", recordUser.getLogin());
                }
                if (recordUser.getHome() != null) {
                    setAttribute("HOME", recordUser.getLogin());
                }
                if (recordUser.getXml() != null) {
                    Enumeration en = null;
                    en = getPropertyValue("USERTYPE");
                    if (en != null) {
                        while (en.hasMoreElements()) {
                            tipousu = (String) en.nextElement();
                        }
                    }
                }
                setDevice(device);

            }
            catch (Exception e) {
                AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "Error while loading properties to user,WBUser:setRecUser") + recUser.getEmail(), true);
            }
        }
        //this.notify();
    }


    /**
     * Getter for property sesid.
     *
     * @return Value of property sesid.
     */
    public String getSesid() {
        return sesid;
    }

    /**
     * Avisa al observador de que se ha producido un cambio.
     *
     * @param s
     * @param obj
     */
    public void sendDBNotify(String s, Object obj) {
        RecUser user = (RecUser) obj;
        if (s.equals("update") || s.equals("load")) {
            if (recUser.getEmail().equals(user.getEmail())) {
                this.setRecUser(user);
            }
        }
    }

    /**
     * Getter for property isregistered.
     *
     * @return Value of property isregistered.
     */
    public boolean isRegistered() {
        return isregistered;
    }

    //checar p/wb3(quitar)
    public int getAdmType() {
        return 0;
    }

    public boolean isAdministrator() {
        return havePermission(TopicMgr.getInstance().getAdminTopicMap().getTopic("WBAd_per_Administrator"));
    }

    /**
     * Getter for property repository.
     *
     * @return Value of property repository.
     */
    public java.lang.String getRepository() {
        return repository;
    }

    public void setSubject(Subject subject) {
        authSubject = subject;
    }

    /**
     * Getter for property authSubject.
     *
     * @return Value of property authSubject.
     */
    public Subject getSubject() {
        return authSubject;
    }

    /**
     * Verifica si el usuario tiene permisos de acceso al template.
     *
     * @return boolean
     */
    public boolean haveAccess(Template tpl) {
        return tpl.haveAccess(this);
    }

    /**
     * Verifica si el usuario tiene permisos de acceso al recurso.
     *
     * @return boolean
     */
    public boolean haveAccess(Resource base) {
        return base.haveAccess(this);
    }

    /**
     * Verifica si el usuario tiene permisos de acceso al topico.
     *
     * @return boolean
     */
    public boolean haveAccess(Topic topic) {
        return haveAccess(topic, true);
    }

    /**
     * Verifica si el usuario tiene permisos de acceso al topico, hereda reglas o no dependiendo
     * del paremetro ruleInherit
     *
     * @return boolean
     */
    public boolean haveAccess(Topic topic, boolean ruleInherit) {
        TopicMap adm = TopicMgr.getInstance().getAdminTopicMap();
        if (topic.getMap() == adm) {
            if (topic.isChildof(adm.getTopic("WBAd_Menus"))) {
                if (!AdmFilterMgr.getInstance().haveAccess2Menu(this, topic)) return false;
            } else if (topic.isChildof(adm.getTopic("WBAd_System"))) {
                if (!AdmFilterMgr.getInstance().haveAccess2System(this, topic)) return false;
            }
        }
        return checkRoles(topic, ruleInherit)
                && checkRules(topic, ruleInherit)
                && checkPermissions(topic, ruleInherit)
                && checkInnerRules(topic, ruleInherit);
    }

    /**
     * Verifica si el usuario tiene permisos de acceso al topico, hereda reglas o no dependiendo
     * del paremetro ruleInherit
     *
     * @return boolean
     */
    public boolean checkRules(Topic topic, boolean ruleInherit) {
        boolean passrule = true;
        boolean criterial_or = true;
        com.infotec.wb.core.RuleMgr ruleMgr = com.infotec.wb.core.RuleMgr.getInstance();
        if (ruleInherit) {
            Iterator[] its = topic.getConfigOccurrencesAndRef(topic.getMap().getTopic(TopicMap.CNF_WBRule));
            Iterator itref = its[1];
            while (itref.hasNext()) {
                String raux = ((Occurrence) itref.next()).getResourceRef();
                if (raux.equals(topic.CONFIG_DATA_AND_CRITERIAL)) criterial_or = false;
            }
            Iterator reglas = its[0];
            while (reglas.hasNext()) {
                String raux = ((Occurrence) reglas.next()).getResourceData();
                try {
                    int regla = Integer.parseInt(raux);
                    if (ruleMgr.eval(this, regla, topic.getMap().getId())) {
                        passrule = true;
                        if (criterial_or) break;
                    } else {
                        passrule = false;
                        if (!criterial_or) break;
                    }
                } catch (Exception e) {
                    AFUtils.log(e);
                }
            }
        } else {
            ArrayList resu = new ArrayList();
            Iterator reglas = topic.getOccurrencesOfType(TopicMap.CNF_WBRule);
            while (reglas.hasNext()) {
                Occurrence occ = (Occurrence) reglas.next();
                if (occ.isActive() && !occ.isResourceRef()) {
                    try {
                        if (occ.isResourceRef()) {
                            if (occ.getResourceRef().equals(topic.CONFIG_DATA_AND_CRITERIAL)) criterial_or = false;
                        } else {
                            int regla = Integer.parseInt(occ.getResourceData());
                            resu.add(new Boolean(ruleMgr.eval(this, regla, topic.getMap().getId())));
                        }
                    } catch (Exception e) {
                        AFUtils.log(e);
                    }
                }
            }

            Iterator it = resu.iterator();
            while (it.hasNext()) {
                Boolean r = (Boolean) it.next();
                if (r.booleanValue()) {
                    passrule = true;
                    if (criterial_or) break;
                } else {
                    passrule = false;
                    if (!criterial_or) break;
                }
            }
        }
        //System.out.println("checkRules:"+topic+" "+passrule);
        return passrule;
    }

    /**
     * Verifica si el usuario tiene permisos de acceso al topico, hereda reglas internas o no dependiendo
     * del paremetro ruleInherit
     *
     * @return boolean
     */
    public boolean checkInnerRules(Topic topic, boolean ruleInherit) {
        boolean passrule = true;
        boolean criterial_or = true;
        com.infotec.wb.core.RuleMgr ruleMgr = com.infotec.wb.core.RuleMgr.getInstance();
        if (ruleInherit) {
            Iterator[] its = topic.getConfigOccurrencesAndRef(topic.getMap().getTopic(TopicMap.CNF_WBInnerRule));
            Iterator itref = its[1];
            while (itref.hasNext()) {
                String raux = ((Occurrence) itref.next()).getResourceRef();
                if (raux.equals(topic.CONFIG_DATA_AND_CRITERIAL)) criterial_or = false;
            }
            Iterator reglas = its[0];
            while (reglas.hasNext()) {
                Occurrence occ = (Occurrence) reglas.next();
                try {
                    if (ruleMgr.eval(this, occ)) {
                        passrule = true;
                        if (criterial_or) break;
                    } else {
                        passrule = false;
                        if (!criterial_or) break;
                    }
                } catch (Exception e) {
                    AFUtils.log(e);
                }
            }
        } else {
            ArrayList resu = new ArrayList();
            Iterator reglas = topic.getOccurrencesOfType(TopicMap.CNF_WBInnerRule);
            while (reglas.hasNext()) {
                Occurrence occ = (Occurrence) reglas.next();
                if (occ.isActive() && !occ.isResourceRef()) {
                    try {
                        if (occ.isResourceRef()) {
                            if (occ.getResourceRef().equals(topic.CONFIG_DATA_AND_CRITERIAL)) criterial_or = false;
                        } else {
                            resu.add(new Boolean(ruleMgr.eval(this, occ)));
                        }
                    } catch (Exception e) {
                        AFUtils.log(e);
                    }
                }
            }

            Iterator it = resu.iterator();
            while (it.hasNext()) {
                Boolean r = (Boolean) it.next();
                if (r.booleanValue()) {
                    passrule = true;
                    if (criterial_or) break;
                } else {
                    passrule = false;
                    if (!criterial_or) break;
                }
            }
        }
        //System.out.println("checkRules:"+topic+" "+passrule);
        return passrule;
    }


    /**
     * Verifica si el usuario tiene permisos de acceso al topico, hereda reglas o no dependiendo
     * del paremetro ruleInherit
     *
     * @return boolean
     */
    public boolean checkRoles(Topic topic, boolean roleInherit) {
        boolean passrole = true;
        boolean criterial_or = true;
        if (roleInherit) {
            Iterator[] its = topic.getConfigOccurrencesAndRef(topic.getMap().getTopic(TopicMap.CNF_WBRole));
            Iterator itref = its[1];
            while (itref.hasNext()) {
                String raux = ((Occurrence) itref.next()).getResourceRef();
                if (raux.equals(topic.CONFIG_DATA_AND_CRITERIAL)) criterial_or = false;
            }
            Iterator roles = its[0];
            while (roles.hasNext()) {
                String raux = ((Occurrence) roles.next()).getResourceData();
                try {
                    int role = Integer.parseInt(raux);
                    if (haveRole(role)) {
                        passrole = true;
                        if (criterial_or) break;
                    } else {
                        passrole = false;
                        if (!criterial_or) break;
                    }
                } catch (Exception e) {
                    AFUtils.log(e);
                }
            }
        } else {
            ArrayList resu = new ArrayList();
            Iterator roles = topic.getOccurrencesOfType(TopicMap.CNF_WBRole);
            while (roles.hasNext()) {
                Occurrence occ = (Occurrence) roles.next();
                if (occ.isActive() && !occ.isResourceRef()) {
                    try {
                        if (occ.isResourceRef()) {
                            if (occ.getResourceRef().equals(topic.CONFIG_DATA_AND_CRITERIAL)) criterial_or = false;
                        } else {
                            int role = Integer.parseInt(occ.getResourceData());
                            resu.add(new Boolean(haveRole(role)));
                        }
                    } catch (Exception e) {
                        AFUtils.log(e);
                    }
                }
            }

            Iterator it = resu.iterator();
            while (it.hasNext()) {
                Boolean r = (Boolean) it.next();
                if (r.booleanValue()) {
                    passrole = true;
                    if (criterial_or) break;
                } else {
                    passrole = false;
                    if (!criterial_or) break;
                }
            }
        }
        //System.out.println("checkRoles:"+topic+" "+passrole);
        return passrole;
    }

    /**
     * Verifica si el usuario tiene permisos de acceso al topico, hereda reglas o no dependiendo
     * del paremetro ruleInherit
     *
     * @return boolean
     */
    public boolean checkPermissions(Topic topic, boolean permInherit) {
        boolean pass = true;
        boolean criterial_or = true;
        if (permInherit) {
            Iterator[] its = topic.getConfigOccurrencesAndRef(topic.getMap().getTopic(TopicMap.CNF_WBPermission));
            Iterator itref = its[1];
            while (itref.hasNext()) {
                String raux = ((Occurrence) itref.next()).getResourceRef();
                if (raux.equals(topic.CONFIG_DATA_AND_CRITERIAL)) criterial_or = false;
            }
            Iterator perms = its[0];
            while (perms.hasNext()) {
                String raux = ((Occurrence) perms.next()).getResourceData();
                try {
                    if (havePermission(TopicMgr.getInstance().getAdminTopicMap().getTopic(raux))) {
                        pass = true;
                        if (criterial_or) break;
                    } else {
                        pass = false;
                        if (!criterial_or) break;
                    }
                } catch (Exception e) {
                    AFUtils.log(e);
                }
            }
        } else {
            ArrayList resu = new ArrayList();
            Iterator perms = topic.getOccurrencesOfType(TopicMap.CNF_WBPermission);
            while (perms.hasNext()) {
                Occurrence occ = (Occurrence) perms.next();
                if (occ.isActive() && !occ.isResourceRef()) {
                    try {
                        if (occ.isResourceRef()) {
                            if (occ.getResourceRef().equals(topic.CONFIG_DATA_AND_CRITERIAL)) criterial_or = false;
                        } else {
                            resu.add(new Boolean(havePermission(TopicMgr.getInstance().getAdminTopicMap().getTopic(occ.getResourceData()))));
                        }
                    } catch (Exception e) {
                        AFUtils.log(e);
                    }
                }
            }

            Iterator it = resu.iterator();
            while (it.hasNext()) {
                Boolean r = (Boolean) it.next();
                if (r.booleanValue()) {
                    pass = true;
                    if (criterial_or) break;
                } else {
                    pass = false;
                    if (!criterial_or) break;
                }
            }
        }
        //System.out.println("checkPermissions:"+topic+" "+pass);
        return pass;
    }

    public int getActiveStatus() {
        return recUser.getActive();
    }

    public static String getUserId(long id, String repository) {
        return "" + id + "_" + repository;
    }

    //TODO: remove the next declaration, kept for compatibility to Telemars infrastructure
    public void setLogin(boolean login) {
        isloged = login;
        setAttribute("isloged", String.valueOf(login));
    }

    /**
     * Writes this object out to a stream (i.e., serializes it).
     */
    private synchronized void writeObject(java.io.ObjectOutputStream oos) throws java.io.IOException {
        //System.out.println("writeObject");
        //oos.defaultWriteObject();
        oos.writeObject(recUser.getRepository());
        oos.writeLong(recUser.getId());
        oos.writeObject(recUser.getLogin());
        oos.writeObject(device);
        oos.writeObject(languageU);
        oos.writeObject(navegador);
        oos.writeObject(ip);
        oos.writeBoolean(isloged);
        oos.writeBoolean(isregistered);
        if (sesid != null) {
            oos.writeObject(sesid);
        } else {
            oos.writeObject("_");
        }

        if (dom == null) {
            setRecUser(recUser);
        }
        if (dom != null) {
            AFUtils.validateNullNode(dom.getFirstChild());
            oos.writeObject(AFUtils.getInstance().DomtoXml(dom));
        } else {
            oos.writeObject("_");
        }
        oos.writeObject(tipousu);
        oos.writeObject(repository);
        oos.writeObject(authSubject);
    }

    /**
     * Reads this object from a stream (i.e., deserializes it)
     */
    private void readObject(java.io.ObjectInputStream s) throws
            java.io.IOException,
            ClassNotFoundException {

        //System.out.println("readObject");
        //s.defaultReadObject();

        String rep = (String) s.readObject();
        long id = s.readLong();
        String login = (String) s.readObject();

        if (!"nologin".equals(login)) {
            recUser = DBUser.getInstance(rep).getUserByLogin(login);
        } else if (id > 0) {
            recUser = DBUser.getInstance(rep).getUserById(id);
        }

        if (recUser == null) {
            recUser = DBUser.getInstance(rep).getNewRecUser();
        }

        device = (String) s.readObject();
        languageU = (String) s.readObject();
        navegador = (String) s.readObject();
        ip = (String) s.readObject();
        isloged = s.readBoolean();
        isregistered = s.readBoolean();
        sesid = (String) s.readObject();
        if ("_".equals(sesid)) sesid = null;
        String xm = (String) s.readObject();
        if ("_".equals(xm)) {
            setRecUser(recUser);
        } else {
            dom = AFUtils.getInstance().XmltoDom(xm);
        }
        //System.out.println(dom);
        tipousu = (String) s.readObject();
        repository = (String) s.readObject();
        authSubject = (Subject) s.readObject();
    }
}