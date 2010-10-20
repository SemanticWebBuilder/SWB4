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
public class RecResponseUser
{
    private static Logger log = SWBUtils.getLogger(RecResponseUser.class);

    private long responseid;
    private String idtm;
    private long surveyid;
    private String wbuser;
    private int currentquestion;
    private int correctanswer;
    private int wronganswer;
    private int score;
    private Timestamp created;
    private Timestamp lastupdate;
    private int review;
    private int statistic;
    private int finished;
    private String comments;
    
    /**
     * Constructor
     */    
    public RecResponseUser()
    {
        this.responseid = 0;
        this.idtm=null;
        this.surveyid = 0;
        this.wbuser = "";
        this.currentquestion = 0;
        this.correctanswer = 0;
        this.wronganswer = 0;
        this.score = 0;
        this.created = null;
        this.lastupdate = null;
        this.review = 0;
        this.statistic = 1;
        this.comments = "";
        this.finished = 0;
    }
    
    /**
     * Load information from database
     */    
    public void load()
    {
        // Se carga el objeto con los valores de la BD
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs=null;
        try
        {
            conn = SWBUtils.DB.getDefaultConnection();
            st = conn.prepareStatement("select * from sr_responseuser where responseid=? and idtm=?");
            st.setLong(1,responseid);
            st.setString(2,idtm);
            rs=st.executeQuery();
            if(rs.next())
            {
                responseid = rs.getLong("responseid");
                surveyid = rs.getLong("surveyid");
                wbuser = rs.getString("wbuser");
                currentquestion = rs.getInt("currentquestion");
                correctanswer = rs.getInt("correctanswer");
                wronganswer = rs.getInt("wronganswer");
                score = rs.getInt("score");
                review=rs.getInt("review");
                statistic = rs.getInt("statistic");
                comments = rs.getString("comments");
//                try {
//                    comments = SWBUtils.IO.readInputStream(rs.getAsciiStream("comments"));
//                } catch (Exception e) {}
                if(comments==null) comments="";
                finished = rs.getInt("finished");
                created = rs.getTimestamp("created");
                lastupdate = rs.getTimestamp("lastupdate");
            }
            if(rs!=null)rs.close();
            if(st!=null)st.close();
            if(conn!=null)conn.close();
        }
        catch (SQLException e)
        {
            log.error(e.getMessage(), e);
        }
        finally
        {
            rs=null;
            st=null;
            conn=null;
        }
    }
    
   
    /**
     * Gets survey value
     * @return a RecSurvey object
     */    
    public RecSurvey getSurvey()
    {
        RecSurvey objsurvey =null;
        try
        {
            objsurvey = new RecSurvey();
            objsurvey.setSurveyID(surveyid);
            objsurvey.setIdtm(idtm);
            objsurvey.load();
        }
        catch(Exception e)
        {
            log.error(e);
        }
        return objsurvey;
    }
    
    /**
     * Gets surveyid value
     * @return a long value
     */    
    public long getSurveyId()
    {
        return surveyid;
    }
    
    
    /**
     * Set surveyid value
     * @param psurveyid a long value
     */    
    public void setSurveyId(long psurveyid)
    {
        this.surveyid = psurveyid;
    }
    
    /**
     * Gets user value
     * @return a string value
     */    
    public String getUser()
    {
        return this.wbuser.trim();
    }
    
    /**
     * Set user value
     * @param puser input string value
     */    
    public void setUser(String puser)
    {
        this.wbuser = puser.trim();
    }
    
    /**
     * Gets currentquestion value
     * @return an int value
     */    
    public int getCurrentQuestion()
    {
        return this.currentquestion;
    }
    
    /**
     * Set currentquestion value
     * @param pcurrentquestion input int value
     */    
    public void setCurrentQuestion(int pcurrentquestion)
    {
        this.currentquestion = pcurrentquestion;
    }
    
    /**
     * Gets correctanswer value
     * @return an int value
     */    
    public int getCorrectAnswer()
    {
        return this.correctanswer;
    }
    
    /**
     * Set correctanswer value
     * @param pcorrectanswer input int value
     */    
    public void setCorrectAnswer(int pcorrectanswer)
    {
        this.correctanswer = pcorrectanswer;
    }
    
    /**
     * Gets wronganswer value
     * @return an int value
     */    
    public int getWrongAnswer()
    {
        return this.wronganswer;
    }
    
    /**
     * Set wronganswer value
     * @param pwronganswer input int value
     */    
    public void setWrongAnswer(int pwronganswer)
    {
        this.wronganswer = pwronganswer;
    }
    
    /**
     * Gets score value
     * @return an int value
     */    
    public int getScore()
    {
        return this.score;
    }
    
    /**
     * Set score value
     * @param pscore input int value
     */    
    public void setScore(int pscore)
    {
        this.score = pscore;
    }
    
    /**
     * Gets created value
     * @return a timestamp value
     */    
    public java.sql.Timestamp getCreated()
    {
        return this.created;
    }
    
    /**
     * Gets lastupdate value
     * @return a timestamp value
     */    
    public java.sql.Timestamp getLastUpdate()
    {
        return this.lastupdate;
    }
    
    /**
     * Gets review value
     * @return an int value
     */    
    public int getReview()
    {
        return this.review;
    }
    
    /**
     * Set review value
     * @param preview input int value
     */    
    public void setReview(int preview)
    {
        this.review = preview;
    }
    
    /**
     * gets statistic value
     * @return an int value
     */    
    public int getStatistic()
    {
        return this.statistic;
    }
    
    /**
     * Set statistic value
     * @param pstatistic input int value
     */    
    public void setStatistic(int pstatistic)
    {
        this.statistic = pstatistic;
    }
    
    /**
     * Gets comments value
     * @return a string value
     */    
    public String getComments()
    {
        return this.comments.trim();
    }
    
    /**
     * Set comments value
     * @param pcomments input string value
     */    
    public void setComments(String pcomments)
    {
        this.comments = pcomments.trim();
    }
    
    /**
     * Gets finished value
     * @return an int value
     */    
    public int getFinished()
    {
        return this.finished;
    }
    
    /**
     * Set finished value
     * @param pfinished input int value
     */    
    public void setFinished(int pfinished)
    {
        this.finished = pfinished;
    }
    
    /**
     * Creates a record on database
     * @return a boolean value
     */    
    public boolean create()
    {
        boolean res=false;
        String query=null;
        Connection con=null;
        PreparedStatement stmt=null;
        ResultSet rs=null;
        PreparedStatement st=null;
        try
        {
            lastupdate=new Timestamp(new java.util.Date().getTime());
            con=SWBUtils.DB.getDefaultConnection();
            if(responseid==0)
            {
                created=lastupdate;
                stmt=con.prepareStatement("SELECT max(responseid) as id FROM sr_responseuser where idtm=?");
                stmt.setString(1,idtm);
                rs=stmt.executeQuery();
                if(rs.next())
                {
                    responseid=rs.getLong("id")+1;
                }else responseid=1;
                if(rs!=null)rs.close();
                if(stmt!=null)stmt.close();
                // inserta los valores en srresponseuser
                query="insert into sr_responseuser (responseid, idtm, surveyid, wbuser,currentquestion,correctanswer,wronganswer,score,created,lastupdate,review,statistic,comments,finished) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                st=con.prepareStatement(query);
                st.setLong(1,responseid);
                st.setString(2,idtm);
                st.setLong(3,surveyid);
                st.setString(4,wbuser);
                st.setInt(5,currentquestion);
                st.setInt(6,correctanswer);
                st.setInt(7,wronganswer);
                st.setLong(8,score);
                st.setTimestamp(9, created);
                st.setTimestamp(10, lastupdate);
                st.setInt(11,review);
                st.setInt(12,statistic);
                st.setString(13,comments);
//                if(comments==null)
//                    st.setString(13,null);
//                else
//                    st.setAsciiStream(13,SWBUtils.IO.getStreamFromString(comments),comments.length());
                st.setInt(14,finished);
                st.executeUpdate();
                if(st!=null)st.close();
                if(con!=null)con.close();
                res=true;
            }
            
        }catch(Exception e)
        {
            log.error("No fue posible crear el elemento RecResponseUser:setResponseUser(), responseuserid=" + responseid,e);
        }
        finally
        {
            rs=null;
            stmt=null;
            st=null;
            con=null;
        }
        return res;
    }
    
    /**
     * Updates information on database
     * @return a boolean value
     */    
    public boolean update()
    {
        boolean res=false;
        String query=null;
        Connection con=null;
        PreparedStatement st=null;
        try
        {
            con = SWBUtils.DB.getDefaultConnection();
            lastupdate = new Timestamp(new java.util.Date().getTime());
            query="update sr_responseuser set currentquestion = ?, correctanswer = ?, wronganswer = ?, score = ?, review=?, statistic=?, comments=?, finished=?, lastupdate = ?, created=? where responseid=? and idtm=?";
            st=con.prepareStatement(query);
            st.setInt(1,currentquestion);
            st.setInt(2,correctanswer);
            st.setInt(3,wronganswer);
            st.setFloat(4,score);
            st.setInt(5,review);
            st.setInt(6,statistic);
            st.setString(7,comments);
//            if(comments==null)
//                    st.setString(7,null);
//                else
//                    st.setAsciiStream(7,SWBUtils.IO.getStreamFromString(comments),comments.length());
            st.setInt(8,finished);
            st.setTimestamp(9, lastupdate);
            st.setTimestamp(10,created);
            st.setLong(11,responseid);
            st.setString(12,idtm);
            st.executeUpdate();
            if(st!=null)st.close();
            if(con!=null)con.close();
            res=true;
            
        }
        catch(Exception e)
        {
            log.error("No fue posible crear el elemento RecResponseUser:setResponseUser(), responseuserid=" + responseid,e);
        }
        finally
        {
            st=null;
            con=null;
        }
        return res;
    }
    
    /**
     * Removes data from database
     * @return a boolean value
     */    
    public boolean remove()
    {
        boolean deleted=false;
        try
        {
            Connection conn = SWBUtils.DB.getDefaultConnection();
            String query = "delete from sr_responseuser where responseid=? and idtm=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setLong(1,responseid);
            pst.setString(2,idtm);
            pst.executeUpdate();
            pst.close();
            pst=null;
            conn.close();
            conn=null;
            deleted=true;
        }
        
        catch (Exception e)
        {
            log.error("Error while trying to remove a response user",e);
        }
        return deleted;
    }
    
    /**
     * Getter for property responseid.
     * @return Value of property responseid.
     */
    public long getResponseID()
    {
        return responseid;
    }
    
    /**
     * Setter for property responseid.
     * @param responseid New value of property responseid.
     */
    public void setResponseID(long responseid)
    {
        this.responseid = responseid;
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
