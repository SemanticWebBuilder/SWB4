/**
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
**/

package com.infotec.wb.resources.survey.db;

import java.util.*;
import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * Esta clase crea un objeto para el manejo de la base de datos
 *
 * This class create an object to manage database table
 *
 * User: Juan Antonio Fernández Arias
 * INFOTEC 
 */
public class RecSurvey {

    private static Logger log = SWBUtils.getLogger(RecSurvey.class);
    private long surveyid;
    private String idtm;
    private String res_id;
    private Timestamp created;
    private Timestamp lastupdate;
    private int typeid;
    private float min_score;
    private int max_answer;
    private int time_answer;

    /**
     * Constructor
     */
    public RecSurvey() {
        this.surveyid = 0;
        this.idtm = null;
        this.res_id = null;
        this.created = null;
        this.lastupdate = null;
        this.typeid = 0;
        this.min_score = 0;
        this.max_answer = 0;
        this.time_answer = 0;
    }

    /**
     * Load information from database
     * @return a boolean value
     */
    public boolean load() {
        // se carga de la bd Survey con id == psurveyid
        // acceso a la bd, se carga el registro

        // se actualizan los valores obtenidos de la BD a los atributos locales
        //this();
        boolean ret = false;

        if ((res_id != null || surveyid != 0) && null != idtm) {
            Connection con = null;
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                con = SWBUtils.DB.getDefaultConnection("Recurso Formulario.RecSurvey.load()");
                String strSql = new String("select surveyid, res_id, typeid, min_score, max_answer, time_answer, created, lastupdate from sr_survey where surveyid = ? and idtm=?");

                if (surveyid == 0) {
                    strSql = "select surveyid, res_id, typeid, min_score, max_answer, time_answer, created, lastupdate from sr_survey where res_id=? and idtm=?";
                }

                st = con.prepareStatement(strSql);

                if (surveyid == 0) {
                    st.setString(1, res_id);
                } else {
                    st.setLong(1, surveyid);
                }

                st.setString(2, idtm);
                rs = st.executeQuery();
                if (rs.next()) {
                    surveyid = rs.getLong("surveyid");
                    res_id = rs.getString("res_id");
                    typeid = rs.getInt("typeid");
                    min_score = rs.getFloat("min_score");
                    max_answer = rs.getInt("max_answer");
                    time_answer = rs.getInt("time_answer");
                    created = rs.getTimestamp("created");
                    lastupdate = rs.getTimestamp("lastupdate");
                }
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
                ret = true;
            } catch (Exception e) {
                log.error("Error al cargar el registro RecSurvey", e);
            } finally {
                rs = null;
                st = null;
                con = null;
            }
        }
        return ret;
    }

    /**
     * Gets surveyid value
     * @return a long value
     */
    public long getSurveyID() {
        return this.surveyid;
    }

    /**
     * Set surveyid value
     * @param surveyID input long value
     */
    public void setSurveyID(long surveyID) {
        this.surveyid = surveyID;
    }

    /**
     * Gets resid value
     * @return a long value
     */
    public String getResID() {
        return this.res_id;
    }

    /**
     * Set resid value
     * @param presid input long value
     */
    public void setResID(String presid) {
        this.res_id = presid;
    }

    /**
     * Set typeid value
     * @param ptypeid input int value
     */
    public void setTypeID(int ptypeid) {
        this.typeid = ptypeid;
    }

    /**
     * Gets typeid value
     * @return an int value
     */
    public int getTypeID() {
        return typeid;
    }

    /**
     * Gets minscore value
     * @return a float value
     */
    public float getMinScore() {

        return min_score;
    }

    /**
     * Set minscore value
     * @param pcalificacion input float value
     */
    public void setMinScore(float pcalificacion) {
        this.min_score = pcalificacion;
    }

    /**
     * Gets maxanswer value
     * @return an int value
     */
    public int getMaxAnswer() {

        return max_answer;
    }

    /**
     * Set maxanswer value
     * @param ponceanswered input int value
     */
    public void setMaxAnswer(int ponceanswered) {
        this.max_answer = ponceanswered;
    }

    /**
     * Gets a list of types values
     * @param ptipoID input long value
     * @param evento input string value
     * @return a string value
     */
    public String getTypeList(long ptipoID, String evento) {
        if (Integer.toString((int) ptipoID).equals(null)) {
            ptipoID = 0;
        }
        if (evento.equals(null)) {
            evento = " ";
        }
        StringBuffer ret1 = new StringBuffer();
        String select = "";
        ret1.append("<select name=\"tipo\" " + evento + ">");
        if (ptipoID == 1) {
            select = "selected";
        }
        ret1.append("<option value=\"1\" " + select + ">Opinion</option>\n");
        select = "";
        if (ptipoID == 2) {
            select = "selected";
        }
        ret1.append("<option value=\"2\" " + select + ">Evaluation</option>\n");
        ret1.append("</select>");
        return ret1.toString();
    }

    /**
     * Gets a list of subjects values
     * @param psubjectID input long value
     * @param pevento input string value
     * @return a string value
     */
    public String getSubjectList(long psubjectID, String pevento, String idtm) {
        if (Long.toString(psubjectID).equals(null)) {
            psubjectID = 0;
        }
        if (pevento.equals(null)) {
            pevento = " ";
        }
        StringBuffer ret1 = new StringBuffer();
        Connection conexion = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            ret1.append("<select name=\"seccion\" " + pevento + "   >");
            conexion = SWBUtils.DB.getDefaultConnection("Recurso Formulario.RecSurvey.getSubjectList()");
            if (conexion != null) {
                st = conexion.prepareStatement("select title,subjectid from sr_subject where idtm=?");
                st.setString(1, idtm);
                rs = st.executeQuery();
                while (rs.next()) {
                    String titulo = "";
                    if (rs.getString("title").length() > 60) {
                        titulo = rs.getString("title").substring(0, 57) + " ... ";
                    } else {
                        titulo = rs.getString("title");
                    }
                    long tipoID = rs.getLong("subjectid");
                    String seleccionado = "";
                    if (psubjectID == tipoID) {
                        seleccionado = "selected";
                    }
                    ret1.append("<option value=\"" + tipoID + "\" " + seleccionado + ">" + titulo + "</option>\n");
                }
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            }
            ret1.append("</select>");
        } catch (Exception e) {
            log.error("Error al consultar DB - temas", e);
        } finally {
            rs = null;
            st = null;
            conexion = null;
        }
        return ret1.toString();
    }

    /**
     * Gets subjects according to a database query
     * @param pquery input string value
     * @return a hashtable object
     */
    public Hashtable getSubjects(String pquery) {
        Hashtable ht = new Hashtable();
        StringBuffer strsql = new StringBuffer();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            strsql.append("select sr_orderquestion.subjectid from sr_orderquestion where surveyid = ? and idtm=? ");
            if (!pquery.equalsIgnoreCase("all")) {
                strsql.append(pquery);
            }
            strsql.append(" group by sr_orderquestion.subjectid order by sr_orderquestion.subjectid");
            con = SWBUtils.DB.getDefaultConnection("Recurso Formulario.RecSurvey.getSubjects()");
            st = con.prepareStatement(strsql.toString());
            st.setLong(1, surveyid);
            st.setString(2, idtm);
            rs = st.executeQuery();
            int isec = 0;
            while (rs.next()) {
                int subjectId = rs.getInt("subjectid");
                ht.put(Integer.toString(isec), Integer.toString(subjectId));
                isec++;
            }
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            log.error("Error al consultar DB - Tipo de Encuestas", e);
        } finally {
            rs = null;
            st = null;
            con = null;
        }
        return ht;
    }

//    public String getDisplayList(int pshowdisplay, String evento )
//    {
//        if (Integer.toString(pshowdisplay).equals(null)) pshowdisplay=0;
//        if(evento.equals(null))evento="";
//        StringBuffer ret1=new StringBuffer();
//        ret1.append("<select name=\"showdisplay\" "+evento+">");
//        String selectA ="";
//        String selectB ="";
//        String selectC ="";
//        if (pshowdisplay==1) selectA ="selected";
//        if (pshowdisplay==2) selectB ="selected";
//        if (pshowdisplay==3) selectC ="selected";
//        ret1.append("<option value=\"1\" "+selectA+">"+AFUtils.getLocaleString("locale_wb2_resources","usrmsg_RecSurvey_getAdmHtml_msgPregunta")+"</option>\n");   // Por pregunta
//        ret1.append("<option value=\"2\" "+selectB+">"+AFUtils.getLocaleString("locale_wb2_resources","usrmsg_RecSurvey_getAdmHtml_msgSeccion")+"</option>\n");   // Por Secci�n
//        ret1.append("<option value=\"3\" "+selectC+">"+AFUtils.getLocaleString("locale_wb2_resources","usrmsg_RecSurvey_getAdmHtml_msgEncuesta")+"</option>\n");   // Toda la Encuesta
//        ret1.append("</select>");
//        return ret1.toString();
//    }
//    
//    public String getStatusList(int pstatus)
//    {
//        if (Integer.toString(pstatus).equals(null)) pstatus=-1;
//        StringBuffer ret1=new StringBuffer();
//        ret1.append("<select name=\"estatus\">");
//        String selectA ="";
//        String selectB ="";
//        if (pstatus==0) selectA ="selected";
//        if (pstatus==1) selectB ="selected";
//        ret1.append("<option value=\"0\" "+selectA+">"+AFUtils.getLocaleString("locale_wb2_resources","usrmsg_RecSurvey_getAdmHtml_msgDesarrollo")+"</option>\n");
//        ret1.append("<option value=\"1\" "+selectB+">"+AFUtils.getLocaleString("locale_wb2_resources","usrmsg_RecSurvey_getAdmHtml_msgActiva")+"</option>\n");
//        ret1.append("</select>");
//        return ret1.toString();
//    }
    /**
     * Creates a record on database
     * @return a boolean value
     */
    public boolean create() {
        Connection conn = null;
        Statement sst = null;
        ResultSet rs = null;
        PreparedStatement st = null;
        boolean ret = false;
        if (surveyid == 0 && null != idtm) {
            try {
                conn = SWBUtils.DB.getDefaultConnection("Recurso Formulario.RecSurvey.create()");
                lastupdate = new Timestamp(new java.util.Date().getTime());
                created = lastupdate;
                st = conn.prepareStatement("SELECT max(surveyid) FROM sr_survey where idtm=?");
                st.setString(1, idtm);
                rs = st.executeQuery();
                if (rs.next()) {
                    surveyid = rs.getLong(1) + 1;
                } else {
                    surveyid = 1;
                }
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                st = conn.prepareStatement("insert into sr_survey (surveyid, idtm,  typeid, created, lastupdate, res_id, min_score, max_answer, time_answer) values (?,?,?,?,?,?,?,?,?)");
                st.setLong(1, surveyid);
                st.setString(2, idtm);
                st.setInt(3, typeid);
                st.setTimestamp(4, created);
                st.setTimestamp(5, lastupdate);
                st.setString(6, res_id);
                st.setFloat(7, min_score);
                st.setInt(8, max_answer);
                st.setInt(9, time_answer);
                st.executeUpdate();
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
                ret = true;
            } catch (Exception e) {
                log.error("Error al insertar en DB - sr_survey", e);
            } finally {
                rs = null;
                sst = null;
                st = null;
                conn = null;
            }
        }
        return ret;
    }

    /**
     * Updates information on database
     * @return a boolean value
     */
    public boolean update() {
        Connection conn = null;
        PreparedStatement st = null;
        Timestamp tempLast = new Timestamp(new java.util.Date().getTime());
        boolean ret = false;
        try {
            conn = SWBUtils.DB.getDefaultConnection("Recurso Formulario.RecSurvey.update()");
            st = conn.prepareStatement("" +
                    "update sr_survey set " +
                    "typeid=?, " +
                    "created=?, " +
                    "lastupdate=?, " +
                    "min_score=?, " +
                    "max_answer=?, " +
                    "time_answer=? " +
                    "where surveyid=? and idtm=?");

            st.setInt(1, typeid);
            st.setTimestamp(2, created);
            st.setTimestamp(3, lastupdate);

            st.setFloat(4, min_score);
            st.setInt(5, max_answer);
            st.setInt(6, time_answer);
            st.setLong(7, surveyid);
            st.setString(8, idtm);
            st.executeUpdate();
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
            lastupdate = tempLast;
            ret = true;
        } catch (Exception e) {
            log.error("Error while trying to update RecSurvey record.", e);
        } finally {
            st = null;
            conn = null;
        }
        return ret;
    }

    /**
     * Removes data from database
     * @return a boolean value
     */
    public boolean remove() {
        boolean deleted = false;
        try {
            Connection conn = SWBUtils.DB.getDefaultConnection("Recurso Formulario.RecSurvey.remove()");
            String query = "delete from sr_survey where surveyid=? and idtm=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setLong(1, surveyid);
            pst.setString(2, idtm);
            pst.executeUpdate();
            pst.close();
            pst = null;
            conn.close();
            conn = null;
            deleted = true;
        } catch (Exception e) {
            log.error("Error while trying to remove a Survey", e);
        }
        return deleted;
    }

    /**
     * Getter for property time_answer.
     * @return Value of property time_answer.
     */
    public int getTimeAnswer() {
        return time_answer;
    }

    /**
     * Setter for property time_answer.
     * @param time_answer New value of property time_answer.
     */
    public void setTimeAnswer(int time_answer) {
        this.time_answer = time_answer;
    }

    /**
     * Getter for property idtm.
     * @return Value of property idtm.
     */
    public java.lang.String getIdtm() {
        return idtm;
    }

    /**
     * Setter for property idtm.
     * @param idtm New value of property idtm.
     */
    public void setIdtm(java.lang.String idtm) {
        this.idtm = idtm;
    }
}
