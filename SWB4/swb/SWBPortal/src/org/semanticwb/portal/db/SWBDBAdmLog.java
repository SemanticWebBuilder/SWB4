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
 * SWBDBAdmLog.java
 *
 * Created on 13 de enero de 2009, 11:20
 */
package org.semanticwb.portal.db;

import java.sql.*;
import java.util.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticOntology;

/** Objeto: Almacena a la base de datos los cambios desde la administracion.
 *
 * Object: Save to a data base all the changes from the administration.
 *
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class SWBDBAdmLog {

    public String admlogEmail = null;
    static private SWBDBAdmLog instance;       // The single instance
    private Logger log = SWBUtils.getLogger(SWBDBAdmLog.class);

    /** Creates new DBUser */
    private SWBDBAdmLog() {
        log.event(SWBUtils.TEXT.getLocaleString("locale_core", "log_DBAdmLog_DBAdmLog_init"));
        admlogEmail = (String) SWBPlatform.getEnv("wb/admlogEmail");
    }

    public void destroy() {
        instance = null;
        log.event(SWBUtils.TEXT.getLocaleString("locale_core", "log_DBAdmLog_destroy_finalized"));
    }

    /**
     * @return  */
    static public SWBDBAdmLog getInstance() {
        if (instance == null) {
            makeInstance();
        }
        return instance;
    }

    static private synchronized void makeInstance() {
        if (instance == null) {
            instance = new SWBDBAdmLog();
            instance.init();
        }
    }


    /**
     * @param userid
     * @param objuri
     * @param accion  */
    public void saveAdmLog(String userid, String objuri, String accion) {
        try {
            SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
            String modelid = ont.getSemanticObject(objuri).getModel().getModelObject().getURI();

            SWBRecAdmLog rec = new SWBRecAdmLog(userid, accion, objuri, modelid, null);
            rec.create();
        } catch (Exception e) {
            log.error(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_SaveContentLog_contentlogerror"), e);
        }
    }

    public void init() {
    }

    /**
     * @return  */
    public Iterator getAdmLog() {
        return executeQuery("select * from swb_admlog order by log_date");
    }

    /**
     * @param obj
     * @param id
     * @return  */
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
     * @param user
     * @param object
     * @return  */
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
     * @param dbobjid
     * @return  */
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
     * @param dbobjid
     * @return  */
    public Iterator getBitaTopic(String modelid, String objuri) {
        Iterator ret = new ArrayList().iterator();
        try {
            Connection con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.getUserObjectLog()");
            if (con != null) {
                try {
                    String query = "select * from swb_admlog where log_modelid=? and log_objuri=? order by log_date";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setString(1, modelid);
                    st.setString(2, objuri);
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
     * @param user
     * @param action
     * @param object
     * @return  */
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
     * @param action
     * @param object
     * @return  */
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
     * @param user
     * @param action
     * @return  */
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

    /** Elimina todos los registros de la base de datos que cumplan con el Admuserid  throws AFException
     * @throws WBException  */
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

    /** Elimina todos los registros de la base de datos que cumplan con el topicid  throws AFException
     * @throws WBException  */
    public void removeLogByTopicId(String modelid, String objuri, Timestamp lastupdate) throws SWBException {

        Connection con = null;
        try {
            con = SWBUtils.DB.getDefaultConnection("DBAdmLog.removeLogByTopicId()");
            String query = "delete from swb_admlog where log_modelid=? and log_objuri=? and log_date<?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, modelid);
            st.setString(2, objuri);
            st.setTimestamp(3, lastupdate);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e) {
            throw new SWBException(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_removeLogByAdmuserId_cannotremoveelements") + e.getMessage() + " SWBDBAdmLog:removeLogByTopicId()", e);
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

    /** Elimina todos los registros de la base de datos que cumplan con el topicmapid  throws AFException
     * @throws WBException  */
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

    /** Elimina todos los registros de la base de datos que cumplan con el DBObject(para lenguaje y usuarios finales)  throws AFException
     * @throws WBException  */
    public void removeLogByDBObject(String objuri, String modelid, Timestamp lastupdate) throws SWBException {

        Connection con = null;
        try {
            con = SWBUtils.DB.getDefaultConnection("SWBDBAdmLog.removeLogByDBObject()");
            String query = "delete from swb_admlog where log_objuri=? and log_modelid=? and log_date<?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, objuri);
            st.setString(2, modelid);
            st.setTimestamp(3, lastupdate);
            st.executeUpdate();
            st.close();
            con.close();
        } catch (Exception e) {
            throw new SWBException(SWBUtils.TEXT.getLocaleString("locale_core", "error_DBAdmLog_removeLogByAdmuserId_cannotremoveelements") + e.getMessage() + " SWBDBAdmLog:removeLogByDBObject()", e);
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

    /** Elimina todos los registros de la base de datos que cumplan con el DBObject y   throws AFException
     * @throws WBException  */
    public void removeLogByDBObjectAndDBObjId(String objuri, String modelid, Timestamp lastupdate) throws SWBException {

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
     * @param user
     * @return  */
    public Iterator getUserLog(String user) {
        return executeQuery("select * from swb_admlog where log_user='" + user + "' order by log_date");
    }

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

    public void refresh() {
    }

//    /** Avisa al observador de que se ha producido un cambio.
//     * @param s
//     * @param obj  */
//    public void sendDBNotify(String s, Object obj) {
//        if (s.equals("remove")) {
//        }
//        if (s.equals("create")) {
//        }
//    }
}
