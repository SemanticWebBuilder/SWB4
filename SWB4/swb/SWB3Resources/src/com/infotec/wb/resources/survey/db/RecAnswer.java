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


import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 * Esta clase crea un objeto para el manejo de la base de datos
 *
 * This class create an object to manage database table
 *
 * Created by Juan Antonio Fernández Arias
 * 
 */
public class RecAnswer {

    private static Logger log = SWBUtils.getLogger(RecAnswer.class);

    private long answerid;
    private String idtm;
    private long questionid;
    private long responseid;
    private String stringxml;
    private float score;
    private int mark;
    private int secuentialid;
    private Timestamp created;
    private Timestamp lastupdate;

    /*
     * Constructor
     */
    public RecAnswer(){
        this.answerid=0;
        this.idtm=null;
        this.questionid=0;
        this.responseid=0;
        this.stringxml=null;
        this.score=0;
        this.mark=0;
        this.created=null;
        this.lastupdate=null;
    }

    /**
     * Gets answerid value
     * @return a long value a long value
     */    
    public long getAnswerid() {
        return answerid;
    }

    /**
     * Set answerid value
     * @param answerid input long value
     */    
    public void setAnswerid(long answerid) {
        this.answerid = answerid;
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
     * Gets responseid value
     * @return a long value
     */    
    public long getResponseid() {
        return responseid;
    }

    /**
     * Set responseid value
     * @param responseid input long value
     */    
    public void setResponseid(long responseid) {
        this.responseid = responseid;
    }

    /**
     * Gets stringxml value
     * @return a string value
     */    
    public String getStringxml() {
        return stringxml;
    }

    /**
     * Set stringxml value
     * @param stringxml input string value
     */    
    public void setStringxml(String stringxml) {
        this.stringxml = stringxml;
    }

    /**
     * Gets score value
     * @return a float value
     */    
    public float getScore() {
        return score;
    }

    /**
     * Set score value
     * @param score input float value
     */    
    public void setScore(float score) {
        this.score = score;
    }

    /**
     * Gets mark value
     * @return an int value
     */    
    public int getMark() {
        return mark;
    }

    /**
     * Set mark value
     * @param mark input int value
     */    
    public void setMark(int mark) {
        this.mark = mark;
    }

    /**
     * Gets secuentialid value
     * @return an int value
     */    
     public int getSecuentialid() {
        return secuentialid;
    }

     /**
      * Set secuentialid value
      * @param secuentialid input int value
      */     
    public void setSecuentialid(int secuentialid) {
        this.secuentialid = secuentialid;
    }

    /**
     * Gets created value
     * @return a timestamp value
     */    
    public Timestamp getCreated() {
        return created;
    }

    /**
     * Set created value
     * @param created input timestamp value
     */    
    public void setCreated(Timestamp created) {
        this.created = created;
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

    /*
     *Load data from database
     */
    public void load(){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            conn = SWBUtils.DB.getDefaultConnection();
            pst = conn.prepareStatement("select * from sr_answer where answerid=? and idtm=?");
            pst.setLong(1, answerid);
            pst.setString(2,idtm);
            rs = pst.executeQuery();
            if(rs.next()){
                questionid = rs.getLong("questionid");
                responseid = rs.getLong("responseid");
                
//                String temp = rs.getString("stringxml");
//                if(null!=temp)
                  stringxml = rs.getString("stringxml"); //SWBUtils.IO.readInputStream(rs.getAsciiStream("stringxml"));

                score = rs.getFloat("score");
                mark = rs.getInt("mark");
                secuentialid = rs.getInt("secuentialid");
                created = rs.getTimestamp("created");
                lastupdate = rs.getTimestamp("lastupdate");

            }
            if(rs!=null) rs.close();
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
        }
        catch(Exception e){
            log.error("Error while trying to load a record from sr_answer",e);
        }
        finally{
            rs=null;
            pst=null;
            conn=null;
        }
    }


    /**
     * Creates a record on database
     * @return a boolean value
     */    
    public boolean create(){
        boolean res = false;
        if(questionid > 0 && responseid > 0 && answerid == 0 && null!=idtm){
            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            String s_query = null;
            try{
                conn = SWBUtils.DB.getDefaultConnection();
                created = new Timestamp(new java.util.Date().getTime());
                setLastupdate(created);
                pst = conn.prepareStatement("select max(answerid) from sr_answer where idtm=?");
                pst.setString(1,idtm);
                rs = pst.executeQuery();
                if (rs.next()){
                    answerid = rs.getLong(1) + 1;
                }
                else{
                    answerid = 1;
                }
                if(rs!=null) rs.close();
                if(pst!=null)pst.close();
                pst=null;

                s_query = "insert into sr_answer (answerid, idtm, questionid, responseid, stringxml, score, mark, secuentialid, created, lastupdate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
                pst = conn.prepareStatement(s_query);
                pst.setLong(1, answerid);
                pst.setString(2,idtm);
                pst.setLong(3, questionid);
                pst.setLong(4, responseid);

                pst.setString(5,stringxml);
//                if(stringxml==null)
//                    pst.setString(5,null);
//                else
//                    pst.setAsciiStream(5,SWBUtils.IO.getStreamFromString(stringxml),stringxml.length());

                pst.setFloat(6, score);
                pst.setInt(7, mark);
                pst.setInt(8, secuentialid);
                pst.setTimestamp(9, created);
                pst.setTimestamp(10, lastupdate);
                pst.executeUpdate();
                res = true;
                if(pst!=null)pst.close();
                if(conn!=null)conn.close();
            }
            catch(Exception e){
                log.error("Error while trying to create a record on sr_answer",e);
            }
            finally{
                rs=null;
                pst=null;
                conn=null;
            }
        }
        return res;
    }


    /**
     * Updates values on database
     * @return a boolean value
     */    
    public boolean update(){
        boolean res = false;
        Connection conn = null;
        PreparedStatement pst = null;
        String s_query = null;
        try{
            conn = SWBUtils.DB.getDefaultConnection();
            lastupdate = new Timestamp(new java.util.Date().getTime());
            s_query = "update sr_answer set questionid=?, responseid=?, stringxml=?, score=?, mark=?, secuentialid = ?, created=?, lastupdate=? where answerid=? and idtm=?";
            pst = conn.prepareStatement(s_query);
            pst.setLong(1, questionid);
            pst.setLong(2, responseid);

            if(stringxml==null)
                pst.setString(3,null);
            else
                pst.setAsciiStream(3,SWBUtils.IO.getStreamFromString(stringxml),stringxml.length());

            pst.setFloat(4, score);
            pst.setInt(5, mark);
            pst.setInt(6, secuentialid);
            pst.setTimestamp(7, created);
            pst.setTimestamp(8, lastupdate);

            pst.setLong(9, answerid);
            pst.setString(10,idtm);
            pst.executeUpdate();
            res = true;
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
        }
        catch (Exception e){
            log.error("Error while trying to update a record on sr_answer",e);
        }
        finally{
            pst=null;
            conn=null;
        }
        return res;
    }


    /**
     * Removes values from database
     * @return a boolean value
     */    
    public boolean remove(){
        boolean deleted=false;
        Connection conn = null;
        PreparedStatement pst = null;
        String s_query = null;
        try{
            conn = SWBUtils.DB.getDefaultConnection();
            s_query = "delete from sr_answer where answerid=? and idtm=?";
            pst = conn.prepareStatement(s_query);
            pst.setLong(1, answerid);
            pst.setString(2,idtm);
            pst.executeUpdate();
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
            deleted=true;
        }
        catch (Exception e){
            log.error("Error while trying to remove a record from sr_answer",e);
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
