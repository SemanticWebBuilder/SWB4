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
public class RecSubject
{
    private static Logger log = SWBUtils.getLogger(RecSubject.class);

    private long subjectid;
    private String idtm;
    private String title;
    private String description;
    private Timestamp created;
    private Timestamp lastupdate;
    
    
    /**
     * Constructor
     */
    public RecSubject()
    {
        this.subjectid = 0;
        this.idtm=null;
        this.title = null;
        this.description = null;
        this.created = null;
        this.lastupdate = null;
    }
    
    /*Se carga el objeto con los valores de la BD*/
    /**
     * Load information from database
     */
    public void load()
    {
        
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs =null;
        if(subjectid>0 && null!=idtm)
        {
            try
            {
                con = SWBUtils.DB.getDefaultConnection();
                pst = con.prepareStatement("select * from sr_subject where subjectid = ? and idtm=?");
                pst.setLong(1,subjectid);
                pst.setString(2,idtm);
                rs = pst.executeQuery();
                if (rs.next())
                {
                    subjectid = rs.getLong("subjectid");
                    idtm= rs.getString("idtm");
                    title = rs.getString("title");
                    description = rs.getString("description");
                    created = rs.getTimestamp("created");
                    lastupdate = rs.getTimestamp("lastupdate");
                }
                if(rs!=null)rs.close();
                if(pst!=null)pst.close();
                if(con!=null)con.close();
            }
            catch(Exception e)
            {
                log.error("Error al leer el registro de sr_subject..." + subjectid, e);
            }
            finally
            {
                rs=null;
                pst=null;
                con=null;
            }
        }
    }
    
    
    
    /**
     * Gets title value
     * @return a string value
     */
    public String getTitle()
    {
        return this.title;
    }
    
    /**
     * Set title value
     * @param ptitle input string value
     */
    public void setTitle(String ptitle)
    {
        this.title = ptitle;
    }
    
    /**
     * Gets description value
     * @return a string value
     */
    public String getDescription()
    {
        return this.description;
    }
    
    /**
     * Set description value
     * @param pdescription input string value
     */
    public void setDescription(String pdescription)
    {
        this.description = pdescription;
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
    
    /**Se crea o actualiza el registro, si sectionid == 0 se crea si no se actualiza
     * Creates a record on database
     * @throws Exception when an error occurs
     * @return a boolean value
     */
    public boolean create() throws Exception
    {
        boolean res=false;
        Connection con=null;
        ResultSet rs=null;
        PreparedStatement pst = null;
        try
        {
            lastupdate=new Timestamp(new java.util.Date().getTime());
            if(subjectid==0)
            {
                created=lastupdate;
                con=SWBUtils.DB.getDefaultConnection();
                pst=con.prepareStatement("SELECT max(subjectid) as id FROM sr_subject where idtm=?");
                pst.setString(1,idtm);
                rs=pst.executeQuery();
                if(rs.next())
                {
                    subjectid=rs.getLong("id")+1;
                }else subjectid=1;
                if(rs!=null)rs.close();
                if(pst!=null)pst.close();
                pst=null;
                pst = con.prepareStatement("insert into sr_subject (subjectid, idtm, title, description, created, lastupdate) values (?,?,?,?,?,?)");
                pst.setLong(1, subjectid);
                pst.setString(2,idtm);
                pst.setString(3, title);
                pst.setString(4, description);
                pst.setTimestamp(5, created);
                pst.setTimestamp(6, lastupdate);
                pst.executeUpdate();
                res = true;
                if(pst!=null)pst.close();
                if(con!=null)con.close();
            }
        }
        catch(Exception e)
        {
            log.error("No fue posible crear el registro de DB - sr_subject",e);
        }
        finally
        {
            rs=null;
            pst=null;
            con=null;
        }
        return res;
    }
    
    /**
     * Updates information on database
     * @throws Exception when an error occurs
     * @return a boolean value
     */
    public boolean update() throws Exception
    {
        boolean res=false;
        Connection con=null;
        PreparedStatement pst = null;
        try
        {
            
            lastupdate=new Timestamp(new java.util.Date().getTime());
            con=SWBUtils.DB.getDefaultConnection();
            pst = con.prepareStatement("update sr_subject set title=?,description=?,lastupdate=?,created=? where subjectid=? and idtm=?");
            pst.setString(1, title);
            pst.setString(2, description);
            pst.setTimestamp(3, lastupdate);
            pst.setTimestamp(4, created);
            pst.setLong(5, subjectid);
            pst.setString(6,idtm);
            pst.executeUpdate();
            res = true;
            if(pst!=null)pst.close();
            if(con!=null)con.close();
        }
        
        catch(Exception e)
        {
            log.error("No fue posible actualizar el registro de DB - sr_subject",e);
        }
        finally
        {
            pst=null;
            con=null;
        }
        return res;
    }
    
    /**
     * Getter for property subjectid.
     * @return Value of property subjectid.
     */
    public long getSubjectid()
    {
        return subjectid;
    }
    
    /**
     * Setter for property subjectid.
     * @param subjectid New value of property subjectid.
     */
    public void setSubjectid(long subjectid)
    {
        this.subjectid = subjectid;
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
            String query = "delete from sr_subject where subjectid=? and idtm=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setLong(1,subjectid);
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
            log.error("Error while trying to remove a subject",e);
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
