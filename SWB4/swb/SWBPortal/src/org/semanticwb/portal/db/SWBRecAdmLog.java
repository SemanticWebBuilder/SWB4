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
 * SWBRecAdmLog.java
 *
 * Created on 23 de septiembre de 2002, 12:29
 */

package org.semanticwb.portal.db;

import java.sql.*;

//import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.model.SWBContext;



/** objeto: cache de registro de base de datos de la tabla wbadmlog
 *
 * object: record cache of data base from the wbadmlog table
 *
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class SWBRecAdmLog //implements WBDBRecord
{

//    private ArrayList observers = new ArrayList();
//    private ArrayList notifys = new ArrayList();

    private String user;
    private String action;
    private String objuri;
    //private long dbobjid;
    private String modelid;
    //private String description;
    private Timestamp date;

    private Logger log = SWBUtils.getLogger(SWBRecAdmLog.class);

    /** Creates new RecAdmLog */
    public SWBRecAdmLog()
    {
        this.user = "_";
        this.action = "_";
        this.objuri = "_";
        this.modelid = null;
        this.date = null;
//        if(WBLoader.getInstance().haveDBTables())
//        {
//            registerObserver(SWBDBAdmLog.getInstance());
//            //registerObserver(PFlowMgr.getInstance());
//        }
    }

    /**
     * @param rs  */
    public SWBRecAdmLog(ResultSet rs)
    {
        this();
        try
        {
            this.user = rs.getString("log_user");
            this.action = rs.getString("log_action");
            this.objuri = rs.getString("log_objuri");
            this.modelid = rs.getString("log_modelid");
            this.date = rs.getTimestamp("log_date");
        } catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_RecAdmLog_RecAdmLog_registryError"), e);
        }
    }

    /** Creates new RecAdmLog
     * @param user
     * @param action
     * @param dbobject
     * @param dbobjid
     * @param topicmapid
     * @param topicid
     * @param description
     * @param date  */
    public SWBRecAdmLog(String user, String action, String objuri, String modelid, Timestamp date)
    {
        this();
        this.user = user;
        this.action = action;
        this.objuri = objuri;
        this.modelid = modelid;
        this.date = date;
    }

    /** Getter for property user.
     * @return Value of property user.
     */
    public String getUser()
    {
        return user;
    }

    /** Setter for property user.
     * @param user New value of property user.
     */
    public void setUser(String user)
    {
        this.user = user;
    }

    /** Getter for property action.
     * @return Value of property action.
     */
    public java.lang.String getAction()
    {
        return action;
    }

    /** Setter for property action.
     * @param action New value of property action.
     */
    public void setAction(java.lang.String action)
    {
        this.action = action;
    }

    /** Getter for property dbobject.
     * @return Value of property dbobject.
     */
    public java.lang.String getDbobject()
    {
        return objuri;
    }

    /** Setter for property dbobject.
     * @param dbobject New value of property dbobject.
     */
    public void setDbobject(java.lang.String objuri)
    {
        this.objuri = objuri;
    }

    /** Getter for property tpicmapid.
     * @return Value of property tpicmapid.
     */
    public java.lang.String getModelId()
    {
        return modelid;
    }

    /** Setter for property tpicmapid.
     * @param topicmapid  */
    public void setModelId(java.lang.String modelid)
    {
        this.modelid = modelid;
    }

    /** Getter for property date.
     * @return Value of property date.
     */
    public java.sql.Timestamp getDate()
    {
        return date;
    }

    /** Setter for property date.
     * @param date New value of property date.
     */
    public void setDate(java.sql.Timestamp date)
    {
        this.date = date;
    }

    /** registra el objeto observador para que pueda recibir notoficaciones de cambios
     * @param obs  */
//    public void registerObserver(AFObserver obs)
//    {
//        if (!observers.contains(obs)) observers.add(obs);
//    }

    /** Elimina el registro de la base de datos asi como todopublic void remove() throws AFException
     * @throws com.infotec.appfw.exception.AFException  */
    public void remove() throws SWBException
    {
        
        Connection con=null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection("RecAdmLog.remove()");
            String query = "delete from swb_admlog where log_user=? and log_action=? and log_date=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, user);
            st.setString(2, action);
            st.setTimestamp(3, date);
            st.executeUpdate();
            st.close();
            con.close();
//            Iterator it = observers.iterator();
//            while (it.hasNext())
//            {
//                ((AFObserver) it.next()).sendDBNotify("remove", this);
//            }

        } catch (Exception e)
        {
            throw new SWBException("No fue posible borrar el elemento... SWBRecAdmLog:remove()", e);
        } finally
        {
            try{
            if (con != null) con.close();
            }catch(Exception ex){}
        }
    }

    /** actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void update() throws SWBException
    {

    }

    /** crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void create() throws SWBException
    {
        Connection con=null;
        try
        {
            if (date == null) date = new Timestamp(new java.util.Date().getTime());
            con = SWBUtils.DB.getDefaultConnection("RecAdmLog.create()");
            String query = "insert into swb_admlog (log_user,log_action,log_objuri,log_modelid,log_date) values (?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, user);
            st.setString(2, action);
            st.setString(3, objuri);
            st.setString(4, modelid);
            st.setTimestamp(5, date);
            st.executeUpdate();
            st.close();
            con.close();
//            Iterator it = observers.iterator();
//            while (it.hasNext())
//            {
//                ((AFObserver) it.next()).sendDBNotify("create", this);
//            }


            if (SWBDBAdmLog.getInstance().admlogEmail != null)
            {
                String desc = SWBUtils.TEXT.getLocaleString("locale_core", "email_RecAdmLog_create_user") + SWBContext.getWebSite(modelid).getUserRepository().getUser(user).getName();
                desc += desc = " " + SWBUtils.TEXT.getLocaleString("locale_core", "email_RecAdmLog_create_action") ;
                SWBMail mail = new SWBMail();
                mail.addAddress(SWBDBAdmLog.getInstance().admlogEmail);
                mail.setSubject(SWBUtils.TEXT.getLocaleString("locale_core", "email_RecAdmLog_create_change"));
                mail.setData(desc);
                SWBUtils.EMAIL.sendBGEmail(mail); //SWBDBAdmLog.getInstance().admlogEmail, SWBUtils.TEXT.getLocaleString("locale_core", "email_RecAdmLog_create_change"), desc);
            }

        } catch (Exception e)
        {
            throw new SWBException(SWBUtils.TEXT.getLocaleString("locale_core", "error_RecAdmLog_create_createelementerror")+" SWBRecAdmLog:create()", e);
        } finally
        {
            try{
            if (con != null) con.close();
            }
            catch(Exception ex){}
        }
    }

    /** refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria
     * @throws com.infotec.appfw.exception.AFException  */
    public void load() throws SWBException
    {

    }

//    public void sendNotify()
//    {
//        Iterator nt = notifys.iterator();
//        while (nt.hasNext())
//        {
//            String message = (String) nt.next();
//            Iterator it = observers.iterator();
//            while (it.hasNext())
//            {
//                ((AFObserver) it.next()).sendDBNotify(message, this);
//            }
//            nt.remove();
//        }
//    }

    /**
     * @param message  */
//    public void sendNotify(String message)
//    {
//        Iterator it = observers.iterator();
//        while (it.hasNext())
//        {
//            ((AFObserver) it.next()).sendDBNotify(message, this);
//        }
//    }

}
