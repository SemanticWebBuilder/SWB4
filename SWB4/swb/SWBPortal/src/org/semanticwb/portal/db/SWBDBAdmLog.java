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
import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

// TODO: Auto-generated Javadoc
/** Objeto: Almacena a la base de datos los cambios desde la administracion.
 *
 * Object: Save to a data base all the changes from the administration.
 *
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class SWBDBAdmLog {

    /** The admlog email. */
    public String admlogEmail = null;
    
    /** The instance. */
    static private SWBDBAdmLog instance;       // The single instance
    
    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBDBAdmLog.class);

    /**
     * Creates new DBUser.
     */
    public SWBDBAdmLog() {
        log.event(SWBUtils.TEXT.getLocaleString("locale_core", "log_DBAdmLog_DBAdmLog_init"));
        admlogEmail = (String) SWBPlatform.getEnv("wb/admlogEmail");
    }

    /**
     * Destroy.
     */
    public void destroy() {
        instance = null;
        log.event(SWBUtils.TEXT.getLocaleString("locale_core", "log_DBAdmLog_destroy_finalized"));
    }

    /**
     * Save adm log.
     * 
     * @param user the user
     * @param obj the obj
     * @param prop the prop
     * @param accion the accion
     */
    public void saveAdmLog(User user, SemanticObject obj, Object prop, String accion)
    {
        try {
            String modelid = obj.getModel().getName();
            String userid=user!=null?user.getURI():"_";
            if(userid==null)userid="_";
            String propid="_";
            if(prop!=null)
            {
                if(prop instanceof SemanticProperty)
                {
                    propid=((SemanticProperty)prop).getURI();
                }else if(prop instanceof SemanticClass)
                {
                    propid=((SemanticClass)prop).getURI();
                }
            }
            SWBRecAdmLog rec = new SWBRecAdmLog(userid, modelid, obj.getURI(),propid, accion, null);
            rec.create();
        } catch (Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_SaveContentLog_contentlogerror"), e);
        }
    }

    /**
     * Inits the.
     */
    public void init()
    {
    }

    /**
     * Gets the adm log.
     * 
     * @return the adm log
     * @return
     */
    public Iterator getAdmLog() {
        return executeQuery("select * from swb_admlog order by log_date");
    }

    /**
     * Gets the object log.
     * 
     * @param objuri the objuri
     * @return the object log
     * @return
     */
    public Iterator getObjectLog(String objuri) {
        Iterator ret = new ArrayList().iterator();
        try {
            Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getObjectLog()");
            if (con != null) {
                try {
                    String query = "select * from swb_admlog where log_objuri=? order by log_date";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setString(1, objuri);
                    ResultSet rs = st.executeQuery();
                    ret = new SWBIterAdmLog(con, st, rs);
                } catch (Exception e) {
                    log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_getObjectLog_getLogerror"), e);
                }
            } else {
                //tira una exception
            }
            con.close();
        } catch (Exception e) {
            log.error(e);
        }
        return ret;
    }

    /**
     * Obtiene los registros de un determinado usuario y un tipo de objeto.
     * 
     * @param user the user
     * @param objuri the objuri
     * @return the user object log
     * @return
     */
    public Iterator getUserObjectLog(String user, String objuri) {
        Iterator ret = new ArrayList().iterator();
        try {
            Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getUserObjectLog()");
            if (con != null) {
                try {
                    String query = "select * from swb_admlog where log_user=? and log_objuri=? order by log_date";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setString(1, user);
                    st.setString(2, objuri);
                    ResultSet rs = st.executeQuery();
                    ret = new SWBIterAdmLog(con, st, rs);
                } catch (Exception e) {
                    log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_getUserObjectLog_getUserLogerror"), e);
                }
            } else {
                //tira una exception
            }

            con.close();
        } catch (Exception e1) {
            log.error(e1);
        }
        return ret;
    }

    /**
     * Gets the bita content.
     * 
     * @param objuri the objuri
     * @return the bita content
     * @return
     */
    public Iterator getBitaContent(String objuri) {
        Iterator ret = new ArrayList().iterator();
        try {
            Connection con = SWBUtils.DB.getDefaultConnection("SWDBAdmLog.getBitaContent()");
            if (con != null) {
                try {
                    String query = "select * from swb_admlog where log_objuri=? order by log_date"; //"select * from swb_admlog where (log_objuri=?) and ((dbobject=?) or (dbobject=?) or (dbobject=?)) order by log_date";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setString(1, objuri);
//                    st.setString(2, "Content");
//                    st.setString(3, "Portlet");
//                    st.setString(4, "Topic");
                    ResultSet rs = st.executeQuery();
                    ret = new SWBIterAdmLog(con, st, rs);
                } catch (Exception e) {
                    log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_getBitaContent_getBitaLogerror"), e);
                }
            } else {
                //tira una exception
            }
            con.close();
        } catch (Exception e1) {
            log.error(e1);
        }
        return ret;
    }

    /**
     * Gets the bita obj uri.
     * 
     * @param modelid the modelid
     * @param objuri the objuri
     * @return the bita obj uri
     * @return
     */
    public Iterator getBitaObjURI(String modelid, String objuri) {
        Iterator ret = new ArrayList().iterator();
        try {
            Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getUserObjectLog()");
            if (con != null) {
                try {
                    String query = "select * from swb_admlog where log_modelid=? and log_objuri=? order by log_date desc";
                    if(null==objuri)
                    {
                        query = "select * from swb_admlog where log_modelid=? order by log_date desc";
                    }
                    PreparedStatement st = con.prepareStatement(query);
                    st.setString(1, modelid);
                    if(null!=objuri)
                    {
                        st.setString(2, objuri);
                    }
                    ResultSet rs = st.executeQuery();
                    ret = new SWBIterAdmLog(con, st, rs);
                } catch (Exception e) {
                    log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_getBitaTopic_getBitaTopicerror"), e);
                }
            } else {
                //tira una exception
            }
            con.close();
        } catch (Exception e1) {
            log.error(e1);
        }
        return ret;
    }

    /**
     * Gets the user action object log.
     * 
     * @param user the user
     * @param action the action
     * @param objuri the objuri
     * @return the user action object log
     * @return
     */
    public Iterator getUserActionObjectLog(String user, String action, String objuri) {
        Iterator ret = new ArrayList().iterator();
        try {
            Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getUserActionObjectLog()");
            if (con != null) {
                try {
                    String query = "select * from wbadmlog where log_user=? and log_objuri=? and log_action=? order by log_date";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setString(1, user);
                    st.setString(2, objuri);
                    st.setString(3, action);
                    ResultSet rs = st.executeQuery();
                    ret = new SWBIterAdmLog(con, st, rs);
                } catch (Exception e) {
                    log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_getUserActionObjectLog_getUserActionObjectLogerror"), e);
                }
            } else {
                //tira una exception
            }
            con.close();
        } catch (Exception e1) {
            log.error(e1);
        }
        return ret;
    }

    /**
     * Obtiene los registros de objUri y un tipo de accion.
     * 
     * @param action the action
     * @param objuri the objuri
     * @return the action object log
     * @return
     */
    public Iterator getActionObjectLog(String action, String objuri) {
        Iterator ret = new ArrayList().iterator();

        try {
            Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getActionObjectLog()");
            if (con != null) {
                try {
                    String query = "select * from swb_admlog where log_objuri=? and log_action=? order by log_date";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setString(1, objuri);
                    st.setString(2, action);
                    ResultSet rs = st.executeQuery();
                    ret = new SWBIterAdmLog(con, st, rs);
                } catch (Exception e) {
                    log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_getActionObjectLog_getActionObjectLogerror"), e);
                }
            } else {
                //tira una exception
            }
            con.close();
        } catch (Exception e1) {
            log.error(e1);
        }
        return ret;
    }

    /**
     * Obtiene los registros de determinado usuario y un tipo de accion.
     * 
     * @param user the user
     * @param action the action
     * @return the user action log
     * @return
     */
    public Iterator getUserActionLog(String user, String action) {
        Iterator ret = new ArrayList().iterator();
        try {
            Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getUserActionLog()");
            if (con != null) {
                try {
                    String query = "select * from swb_admlog where log_user=? and log_action=? order by log_date";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setString(1, user);
                    st.setString(2, action);
                    ResultSet rs = st.executeQuery();
                    ret = new SWBIterAdmLog(con, st, rs);
                } catch (Exception e) {
                    log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_getUserActionLog_getUserActionLogerror"), e);
                }
            } else {
                //tira una exception
            }
            con.close();
        } catch (Exception e1) {
            log.error(e1);
        }
        return ret;
    }

    /**
     * Elimina todos los registros de la base de datos que cumplan con el UserId y menores a la Fecha  throws AFException.
     * 
     * @param Admuserid the admuserid
     * @param lastupdate the lastupdate
     * @throws SWBException the sWB exception
     */
    public void removeLogByAdmuserId(String Admuserid, Timestamp lastupdate) throws SWBException {
        Connection con = null;
        try {
            con = SWBUtils.DB.getDefaultConnection("DBAdmLog.removeLogByAdmuserId()");
            String query = "delete from swb_admlog where log_user=? and log_date<?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, Admuserid);
            st.setTimestamp(2, lastupdate);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e) {
            throw new SWBException(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_removeLogByAdmuserId_removeLogByAdmuserIderror") + e.getMessage() + " SWBDBAdmLog:removeLogByAdmuserId() ", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                log.error(ex);
            }
        }
    }

    /**
     * Elimina todos los registros de la base de datos que cumplan con el modelid y menores a la Fecha throws AFException.
     * 
     * @param modelid the modelid
     * @param lastupdate the lastupdate
     * @throws SWBException the sWB exception
     */
    public void removeLogByModelId(String modelid, Timestamp lastupdate) throws SWBException {

        Connection con = null;
        try {
            con = SWBUtils.DB.getDefaultConnection("DBAdmLog.removeLogByTopicMapId()");
            String query = "delete from swb_admlog where log_modelid=? and log_date<?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, modelid);
            st.setTimestamp(2, lastupdate);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e) {
            throw new SWBException(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_removeLogByAdmuserId_cannotremoveelements") + e.getMessage() + " SWBDBAdmLog:removeLogByTopicMapId()", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                log.error(ex);
            }
        }
    }

    /**
     * Elimina todos los registros de la base de datos que cumplan con el ObjURI,ModelId y menores a Fecha throws AFException.
     * 
     * @param objuri the objuri
     * @param modelid the modelid
     * @param lastupdate the lastupdate
     * @throws SWBException the sWB exception
     */
    public void removeLogByDBObjUriAndModelId(String objuri, String modelid, Timestamp lastupdate) throws SWBException {

        Connection con = null;
        try {
            con = SWBUtils.DB.getDefaultConnection("DBAdmLog.removeLogByDBObjectAndDBObjId()");
            String query = "delete from swb_admlog where log_objuri=? and log_date<? and log_modelid=?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, objuri);
            st.setTimestamp(2, lastupdate);
            st.setString(3, modelid);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e) {
            throw new SWBException(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_removeLogByAdmuserId_cannotremoveelements") + e.getMessage() + " SWBDBAdmLog:removeLogByDBObjectAndDBObjId()", e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                log.error(ex);
            }
        }
    }

    /**
     * Gets the user log.
     * 
     * @param user the user
     * @return the user log
     * @return
     */
    public Iterator getUserLog(String user) {
        return executeQuery("select * from swb_admlog where log_user='" + user + "' order by log_date");
    }

    /**
     * Execute query.
     * 
     * @param sql the sql
     * @return the iterator
     */
    private Iterator executeQuery(String sql) {
        Iterator ret = new ArrayList().iterator();
        try {
            Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.executeQuery()");
            if (con != null) {
                try {
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    ret = new SWBIterAdmLog(con, st, rs);
                } catch (Exception e) {
                    log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_executeQuery_getLogError"), e);
                }
            } else {
                //tira una exception
            }
            con.close();
        } catch (Exception ex) {
            log.error(ex);
        }
        return ret;
    }

    /**
     * Gets the changes.
     * 
     * @param lastupdate the lastupdate
     * @return the changes
     */
    public Iterator getChanges(Timestamp lastupdate) {
        Iterator ret = new ArrayList().iterator();
        try {
            Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getChanges()");
            if (con != null) {
                try {
                    String query = "select * from swb_admlog where log_date>? order by log_date";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setTimestamp(1, lastupdate);
                    ResultSet rs = st.executeQuery();
                    ret = new SWBIterAdmLog(con, st, rs);
                //System.out.println("select * from wbdbsync where date>"+lastupdate+" order by date");
                } catch (Exception e) {
                    log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_getChanges_getLogError"), e);
                }
            } else {
                //tira una exception
            }
            con.close();
        } catch (Exception ex) {
            log.error(ex);
        }
        return ret;
    }

    /**
     * Refresh.
     */
    public void refresh() {
    }

}
