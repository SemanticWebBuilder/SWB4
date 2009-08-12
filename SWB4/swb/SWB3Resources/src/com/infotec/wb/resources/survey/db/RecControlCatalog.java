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
public class RecControlCatalog{

    private static Logger log = SWBUtils.getLogger(RecControlCatalog.class);

    private int controlid;
    private String idtm;
    private String description;
    private String title;
    private Timestamp lastupdate;

    /**
     * Constructor
     */    
    public RecControlCatalog(){
        this.controlid = 0;
        this.idtm=null;
        this.title = null;
        this.description = null;
        this.lastupdate=null;
    }

    /**
     * Gets controlid value
     * @return an int value
     */    
    public int getControlid() {
        return controlid;
    }

    /**
     * Set controlid value
     * @param controlid input int value
     */    
    public void setControlid(int controlid) {
        this.controlid = controlid;
    }

    /**
     * Gets description value
     * @return a string value
     */    
    public String getDescription() {
        return description;
    }

    /**
     * Set description value
     * @param description input string value
     */    
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets title value
     * @return a string value
     */    
    public String getTitle() {
        return title;
    }

    /**
     * Set title value
     * @param title input string value
     */    
    public void setTitle(String title) {
        this.title = title;
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
     * Load data from database
     */    
    public void load(){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            conn = SWBUtils.DB.getDefaultConnection();
            pst = conn.prepareStatement("select * from sr_controlcatalog where controlid = ? and idtm=?");
            pst.setInt(1,controlid);
            pst.setString(2,idtm);
            rs = pst.executeQuery();
            if (rs.next()){
                title = rs.getString("title");
                description = rs.getString("description");
                lastupdate = rs.getTimestamp("lastupdate");
            }
            if(rs!=null)rs.close();
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
        }
        catch(Exception e){
            log.error("Error while trying to load a record from sr_controlcatalog",e);
        }
        finally{
            rs=null;
            pst=null;
            conn=null;
         }
    }

    /**
     * Creates an instance of this object
     * @return a boolean value
     */    
    public boolean create(){
        boolean res = false;
        if(controlid == 0){
            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            String s_query = null;
            try{
                conn = SWBUtils.DB.getDefaultConnection();
                lastupdate = new Timestamp(new java.util.Date().getTime());
                pst = conn.prepareStatement("select max(controlid) from sr_controlcatalog where idtm=?");
                pst.setString(1,idtm);
                rs = pst.executeQuery();
                if(rs.next()){
                    controlid = rs.getInt(1) + 1;
                }
                else{
                    controlid = 1;
                }
                if(rs!=null) rs.close();
                if(pst!=null)pst.close();
                pst=null;

                s_query = "insert into sr_controlcatalog (controlid, idtm, title, description, lastupdate) values (?, ?, ?, ?, ?) ";
                pst = conn.prepareStatement(s_query);
                pst.setInt(1, controlid);
                pst.setString(2,idtm);
                pst.setString(3, title);
                pst.setString(4, description);
                pst.setTimestamp(5, lastupdate);
                pst.executeUpdate();
                res = true;
                if(pst!=null)pst.close();
                if(conn!=null)conn.close();
            }
            catch(Exception e){
                log.error("Error while trying to create a record on sr_controlcatalog",e);
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
     * Updates information on database
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
            s_query = "update sr_controlcatalog set title=?, description=?, lastupdate=? where controlid=? and idtm=?";
            pst = conn.prepareStatement(s_query);
            pst.setString(1, title);
            pst.setString(2, description);
            pst.setTimestamp(3, lastupdate);
            pst.setInt(4, controlid);
            pst.setString(5,idtm);
            pst.executeUpdate();
            res = true;
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
        }
        catch (Exception e){
            log.error("Error while trying to update a record on sr_controlcatalog",e);
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
            conn = SWBUtils.DB.getDefaultConnection();
            s_query = "delete from sr_controlcatalog where controlid=? and idtm=?";
            pst = conn.prepareStatement(s_query);
            pst.setInt(1, controlid);
            pst.setString(2,idtm);
            pst.executeUpdate();
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
            deleted=true;
        }
        catch (Exception e){
            log.error("Error while trying to remove a record from sr_controlcatalog",e);
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
