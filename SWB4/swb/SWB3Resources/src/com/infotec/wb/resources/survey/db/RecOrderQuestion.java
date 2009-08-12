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

import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * Esta clase crea un objeto para el manejo de la base de datos
 *
 * This class create an object to manage database table
 *
 * User: Juan Antonio Fernández Arias
 * 
 */
public class RecOrderQuestion {

    private static Logger log = SWBUtils.getLogger(RecOrderQuestion.class);

    private long surveyid;
    private String idtm;
    private long questionid;
    private long subjectid;
    private int ordernum;
    private int isactive;
    private int isdata;
    private Timestamp lastupdate;

    /**
     * Constructor
     */    
    public RecOrderQuestion(){
        this.surveyid = 0;
        this.idtm=null;
        this.questionid = 0;
        this.subjectid = 0;
        this.ordernum = 0;
        this.isactive = 1;
        this.isdata = 0;
        this.lastupdate = null;
    }

    /**
     * Gets surveyid value
     * @return a long value
     */    
    public long getSurveyid() {
        return surveyid;
    }

    /**
     * Set surveyid value
     * @param surveyid input long value
     */    
    public void setSurveyid(long surveyid) {
        this.surveyid = surveyid;
    }

    /**
     * Gets questionid value
     * @return a long value
     */    
    public long getQuestionid() {
        return questionid;
    }

    /**
     * Set questionid value
     * @param questionid input long value
     */    
    public void setQuestionid(long questionid) {
        this.questionid = questionid;
    }

    /**
     * Gets subjectid value
     * @return a long value
     */    
    public long getSubjectid() {
        return subjectid;
    }

    /**
     * Set subjectid value
     * @param subjectid input long value
     */    
    public void setSubjectid(long subjectid) {
        this.subjectid = subjectid;
    }

    /**
     * Gets ordernum value
     * @return an int value
     */    
    public int getOrdernum() {
        return ordernum;
    }

    /**
     * Set ordernum value
     * @param ordernum input int value
     */    
    public void setOrdernum(int ordernum) {
        this.ordernum = ordernum;
    }
    
    /**
     * Gets isactive value
     * @return an int value
     */    
    public int getIsActive()
    {
        return isactive;
    }
    
    /**
     * Set isactive value
     * @param isactive input int value
     */    
    public void setIsActive(int isactive)
    {
        this.isactive = isactive;
    }
    
    /**
     * Gets isdata value
     * @return an int value
     */    
    public int getIsData()
    {
        return isdata;
    }
    
    /**
     * Set isdata value
     * @param isdata input int value
     */    
    public void setIsData(int isdata)
    {
        this.isdata = isdata;
    }

    /**
     * Gets lastupdate value
     * @return a timestamp value
     */    
    public Timestamp getLastupdate() {
        return lastupdate;
    }

    /**
     * Set lastupdate value
     * @param lastupdate input timestamp value
     */    
    public void setLastupdate(Timestamp lastupdate) {
        this.lastupdate = lastupdate;
    }


    /**
     * Load information from database
     */    
    public void load(){
        if(surveyid > 0 && questionid > 0 && null!=idtm ){
            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            try{
                conn = SWBUtils.DB.getDefaultConnection("Recurso Formulario.RecOrderQuestion.load()");
                pst = conn.prepareStatement("select * from sr_orderquestion where surveyid = ? and questionid = ? and idtm=?");
                pst.setLong(1,surveyid);
                pst.setLong(2,questionid);
                pst.setString(3,idtm);
                rs = pst.executeQuery();
                if (rs.next()){
                    subjectid = rs.getInt("subjectid");
                    ordernum = rs.getInt("ordernum");
                    isactive = rs.getInt("isactive");
                    isdata = rs.getInt("isdata");
                    lastupdate = rs.getTimestamp("lastupdate");
                }
                if(rs!=null)rs.close();
                if(pst!=null)pst.close();
                if(conn!=null)conn.close();
            }
            catch(Exception e){
                log.error("Error while trying to load a record from sr_orderquestion",e);
            }
            finally{
                rs=null;
                pst=null;
                conn=null;
             }
        }
    }


    /**
     * Creates a record on database
     * @return a boolean value
     */    
    public boolean create(){
        boolean res=false;
        if(surveyid > 0 && questionid > 0 && subjectid > 0 && null!=idtm){
            Connection conn = null;
            PreparedStatement pst = null;
            String s_query = null;
            try{
                conn = SWBUtils.DB.getDefaultConnection("Recurso Formulario.RecOrderQuestion.create()");
                lastupdate = new Timestamp(new java.util.Date().getTime());
                s_query = "insert into sr_orderquestion (surveyid, idtm, questionid, subjectid, ordernum, isactive, isdata, lastupdate) values (?,?, ?, ?, ?, ?, ?, ?)";
                pst = conn.prepareStatement(s_query);
                pst.setLong(1, surveyid);
                pst.setString(2,idtm);
                pst.setLong(3, questionid);
                pst.setLong(4, subjectid);
                pst.setInt(5, ordernum);
                pst.setInt(6,isactive);
                pst.setInt(7,isdata);
                pst.setTimestamp(8, lastupdate);
                pst.executeUpdate();
                if(pst!=null)pst.close();
                if(conn!=null)conn.close();
                res=true;
            }
            catch(Exception e){
                log.error("Error while trying to create a record on sr_orderquestion",e);
            }
            finally{
                pst=null;
                conn=null;
            }
        }
        return res;
    }

    /**
     * Updates information on database
     * @return a boolean value
     */    
    public boolean update(){
        boolean res=false;
        Connection conn = null;
        PreparedStatement pst = null;
        String s_query = null;
        try{
            conn = SWBUtils.DB.getDefaultConnection("Recurso Formulario.RecOrderQuestion.update()");
            lastupdate = new Timestamp(new java.util.Date().getTime());
            s_query = "update sr_orderquestion set ordernum=?, isactive=?, isdata=?, lastupdate=?, subjectid=? where surveyid = ? and questionid = ? and idtm=?";
            pst = conn.prepareStatement(s_query);
            pst.setInt(1, ordernum);
            pst.setInt(2,isactive);
            pst.setInt(3,isdata);
            pst.setTimestamp(4, lastupdate);
            pst.setLong(5, subjectid);
            pst.setLong(6, surveyid);
            pst.setLong(7, questionid);
            pst.setString(8,idtm);
            pst.executeUpdate();
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
            res=true;
        }
        catch (Exception e){
            log.error("Error while trying to update a record on sr_orderquestion",e);
        }
        finally{
            pst=null;
            conn=null;
        }
        return res;
    }


    /**
     * Removes data from database
     * @return a boolean value
     */    
    public boolean remove(){
        boolean deleted=false;
        Connection conn = null;
        PreparedStatement pst = null;
        String s_query = null;
        try{
            conn = SWBUtils.DB.getDefaultConnection("Recurso Formulario.RecOrderQuestion.remove()");
            s_query = "delete from sr_orderquestion where surveyid = ? and questionid = ? and idtm=?";
            pst = conn.prepareStatement(s_query);
            pst.setLong(1, surveyid);
            pst.setLong(2, questionid);
            pst.setString(3,idtm);
            pst.executeUpdate();
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
            deleted=true;
        }
        catch (Exception e){
            log.error("Error while trying to remove a record from sr_orderquestion",e);
        }
        finally{
            pst=null;
            conn=null;
        }
        return deleted;
    }

    /**
     * Getter for property idtm.
     * @return Value of property idtm.
     */
    public java.lang.String getIdtm()
    {
        return idtm;
    }
    
    /**
     * Setter for property idtm.
     * @param idtm New value of property idtm.
     */
    public void setIdtm(java.lang.String idtm)
    {
        this.idtm = idtm;
    }
    
}
