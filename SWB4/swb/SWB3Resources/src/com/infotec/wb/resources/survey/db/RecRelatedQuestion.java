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
 * Created by
 * User: Juan Antonio Fernández Arias
 * INFOTEC 
 * 
 */
public class RecRelatedQuestion
{
    private static Logger log = SWBUtils.getLogger(RecRelatedQuestion.class);

    private long parentid;
    private long sonid;
    private long surveyid;
    private String idtm;
    private Timestamp lastupdate;
    private int bandera;
    
    /**
     * Constructor
     */    
    public RecRelatedQuestion()
    {
        parentid=0;
        sonid=0;
        surveyid=0;
        idtm=null;
        lastupdate=null;
        bandera = 0;
    }
    
    /**
     * Load information from database
     */    
    public void load()
    {
        Connection con=null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try
        {
            con=SWBUtils.DB.getDefaultConnection();
            pst = con.prepareStatement("" +
            "select * " +
            "from sr_relatedquestion " +
            "where " +
            "parentquestion = ? and " +
            "sonquestion = ? and " +
            "surveyid = ? and idtm=?" );
            pst.setLong(1,parentid);
            pst.setLong(2,sonid);
            pst.setLong(3,surveyid);
            pst.setString(4,idtm);
            rs = pst.executeQuery();
            if (rs.next())
            {
                surveyid = rs.getLong("surveyid");
                parentid = rs.getLong("parentquestion");
                sonid = rs.getLong("sonquestion");
                lastupdate = rs.getTimestamp("lastupdate");
            }
            if(rs!=null)rs.close();
            if(pst!=null)pst.close();
            if(con!=null)con.close();
            bandera=1;
        }
        catch(Exception e)
        {
            log.error("Error al cargar el registro de sr_orderquestion", e);
        }
        finally
        {
            rs=null;
            pst=null;
            con=null;
        }
    }
    
    /**
     * Gets parent value
     * @return a long value
     */    
    public long getParent()
    {
        return parentid;
    }
    
    /**
     * Set parent value
     * @param parent input long value
     */    
    public void setParent(long parent)
    {
        parentid = parent;
    }
    
    /**
     * Gets son value
     * @return a long value
     */    
    public long getSon()
    {
        return sonid;
    }
    
    /**
     * Set son value
     * @param son input long value
     */    
    public void setSon(long son)
    {
        sonid = son;
    }
    /**
     * Gets survey value
     * @return a RecSurvey object
     */    
    public RecSurvey getSurvey()
    {
        RecSurvey oRS = null;
        try
        {
            oRS = new RecSurvey();
            oRS.setSurveyID(surveyid);
            oRS.setIdtm(idtm);
            oRS.load();
        }
        catch(Exception e)
        {
            log.error("No se carg� el registro de survey",e);
        }
        return oRS;
    }
    
    /**
     * Set surveyid value
     * @param surveyid input long value
     */    
    public void setSurveyId(long surveyid)
    {
        this.surveyid = surveyid;
    }
    
    /**
     * Gets lastupdate value
     * @return a timestamp value
     */    
    public Timestamp getLastUpdate()
    {
        return lastupdate;
    }
    
    /**
     * Creates a record on database
     * @return a boolean value
     */    
    public boolean create()
    {
        boolean res=false;
        lastupdate=new Timestamp(new java.util.Date().getTime());
        Connection con=null;
        PreparedStatement pst=null;
        if (bandera==0)
        {
            try
            {
                con=SWBUtils.DB.getDefaultConnection();
                pst=con.prepareStatement("" +
                "insert into sr_relatedquestion " +
                "(parentquestion,sonquestion,surveyid,idtm,lastupdate) values (?,?,?,?,?)");
                pst.setLong(1,parentid);
                pst.setLong(2,sonid);
                pst.setLong(3,surveyid);
                pst.setString(4,idtm);
                pst.setTimestamp(5,lastupdate);
                pst.executeUpdate();
                if(pst!=null)pst.close();
                if(con!=null)con.close();
                res = true;
            }
            catch(Exception e)
            { log.error("Error al insertar en DB - sr_relatedquestion",e); }
            finally
            {
                pst=null;
                con=null;
            }
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
        lastupdate=new Timestamp(new java.util.Date().getTime());
        Connection con=null;
        PreparedStatement pst=null;
        
        try
        {
            con=SWBUtils.DB.getDefaultConnection();
            pst=con.prepareStatement("" +
            "update sr_relatedquestion " +
            "set lastupdate = ? " +
            "where " +
            "surveyid = ? and " +
            "parentquestion = ? and " +
            "sonquestion = ? and idtm=?");
            pst.setTimestamp(1,lastupdate);
            pst.setLong(2,surveyid);
            pst.setLong(3,parentid);
            pst.setLong(4,sonid);
            pst.setString(5,idtm);
            pst.executeUpdate();
            if(pst!=null)pst.close();
            if(con!=null)con.close();
            res = true;
        }
        catch(Exception e)
        {
            log.error("Error al actualizar el registro en DB - sr_relatedquestion", e);
        }
        finally
        {
            pst=null;
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
            String query = "delete from sr_relatedquestion where parentquestion=? and sonquestion=? and surveyid=? and idtm=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setLong(1,parentid);
            pst.setLong(2,sonid);
            pst.setLong(3,surveyid);
            pst.setString(4,idtm);
            pst.executeUpdate();
            pst.close();
            pst=null;
            conn.close();
            conn=null;
            deleted=true;
        }
        
        catch (Exception e)
        {
            log.error("Error while trying to remove a Related question",e);
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
