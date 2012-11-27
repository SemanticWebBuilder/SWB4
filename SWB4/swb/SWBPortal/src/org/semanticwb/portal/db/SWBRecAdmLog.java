/*
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
 */
package org.semanticwb.portal.db;

import java.sql.*;

//import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;


// TODO: Auto-generated Javadoc
/**
 * objeto: cache de registro de base de datos de la tabla wbadmlog
 * 
 * object: record cache of data base from the wbadmlog table.
 * 
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class SWBRecAdmLog //implements WBDBRecord
{

//    private ArrayList observers = new ArrayList();
//    private ArrayList notifys = new ArrayList();

    /** The user. */
private String user;
    
    /** The modelid. */
    private String modelid;
    
    /** The objuri. */
    private String objuri;
    
    /** The propid. */
    private String propid;

    /** The action. */
    private String action;
    
    /** The date. */
    private Timestamp date;

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBRecAdmLog.class);

    /**
     * Creates new RecAdmLog.
     */
    public SWBRecAdmLog()
    {
        this.user = "_";
        this.modelid = null;
        this.objuri = "_";
        this.propid = "_";
        this.action = "_";
        this.date = null;
    }

    /**
     * Instantiates a new sWB rec adm log.
     * 
     * @param rs the rs
     */
    public SWBRecAdmLog(ResultSet rs)
    {
        this();
        try
        {
            this.user = rs.getString("log_user");
            this.modelid = rs.getString("log_modelid");
            this.objuri = rs.getString("log_objuri");
            this.propid = rs.getString("log_propid");
            this.action = rs.getString("log_action");
            this.date = rs.getTimestamp("log_date");
        } catch (Exception e)
        {
            log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_RecAdmLog_RecAdmLog_registryError"), e);
        }
    }

    /**
     * Creates new SWBRecAdmLog.
     * 
     * @param user the user
     * @param modelid the modelid
     * @param objuri the objuri
     * @param propid the propid
     * @param action the action
     * @param date the date
     */
    public SWBRecAdmLog(String user, String modelid, String objuri, String propid, String action, Timestamp date)
    {
        this();
        this.user = user;
        this.modelid = modelid;
        this.objuri = objuri;
        this.propid = propid;
        this.action = action;
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

    /**
     * Gets the prop id.
     * 
     * @return the prop id
     */
    public String getPropId() {
        return propid;
    }

    /**
     * Sets the prop id.
     * 
     * @param propid the new prop id
     */
    public void setPropId(String propid) {
        this.propid = propid;
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

    /** Getter for property objURI.
     * @return Value of property objURI.
     */
    public java.lang.String getDbobject()
    {
        return objuri;
    }

    /**
     * Setter for property dbobject.
     * 
     * @param objuri the new dbobject
     */
    public void setDbobject(java.lang.String objuri)
    {
        this.objuri = objuri;
    }

    /** Getter for property modelid.
     * @return Value of property modelid.
     */
    public java.lang.String getModelId()
    {
        return modelid;
    }

    /**
     * Setter for property tpicmapid.
     * 
     * @param modelid the new model id
     */
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


    /**
     * Elimina el registro de la base de datos asi como todopublic void remove() throws AFException.
     * 
     * @throws SWBException the sWB exception
     */
    public void remove() throws SWBException
    {
        
        Connection con=null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection("SWBRecAdmLog.remove()");
            String query = "delete from swb_admlog where log_user=? and log_modelid=? and log_objuri=? and log_propid=? and log_action=? and log_date=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, user);
            st.setString(2, modelid);
            st.setString(3, objuri);
            st.setString(4, propid);
            st.setString(5, action);
            st.setTimestamp(6, date);
            st.executeUpdate();
            st.close();
            con.close();

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

    /**
     * actualiza el objeto en la base de datos y altualiza la informacion de los objetos que esten en memoria.
     * 
     * @throws SWBException the sWB exception
     */
    public void update() throws SWBException
    {

    }

    /**
     * crea un nuevo registro en la base de datos asi como un nuevo objeto en memoria.
     * 
     * @throws SWBException the sWB exception
     */
    public void create() throws SWBException
    {
        Connection con=null;
        try
        {
            if (date == null) date = new Timestamp(new java.util.Date().getTime());
            con = SWBUtils.DB.getDefaultConnection("SWBRecAdmLog.create()");
            String query = "insert into swb_admlog (log_user,log_modelid,log_objuri,log_propid,log_action,log_date) values (?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, user);
            st.setString(2, modelid);
            st.setString(3, objuri);
            st.setString(4, propid);
            st.setString(5, action);
            st.setTimestamp(6, date);
            st.executeUpdate();
            st.close();
            con.close();

//            if (SWBPortal.getDBAdmLog().admlogEmail != null)
//            {
//                String desc = SWBUtils.TEXT.getLocaleString("locale_core", "email_RecAdmLog_create_user") + user;
//                desc += desc = " " + SWBUtils.TEXT.getLocaleString("locale_core", "email_RecAdmLog_create_action") ;
//                SWBMail mail = new SWBMail();
//                mail.addAddress(SWBPortal.getDBAdmLog().admlogEmail);
//                mail.setSubject(SWBUtils.TEXT.getLocaleString("locale_core", "email_RecAdmLog_create_change"));
//                mail.setData(desc);
//                SWBUtils.EMAIL.sendBGEmail(mail); //SWBDBAdmLog.getInstance().admlogEmail, SWBUtils.TEXT.getLocaleString("locale_core", "email_RecAdmLog_create_change"), desc);
//            }

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

    /**
     * refresca el objeto, esto es lo lee de la base de datos y actualiza los objetos que estan en la memoria.
     * 
     * @throws SWBException the sWB exception
     */
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
