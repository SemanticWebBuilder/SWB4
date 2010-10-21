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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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

public class RecValidateCode {

    private static Logger log = SWBUtils.getLogger(RecValidateCode.class);

    private int codeid;
    private String idtm;
    private String title;
    private String description;
    private String validationcode;
    private Timestamp lastupdate;

    /**
     * Constructor
     */
    public RecValidateCode(){
        this.codeid = 0;
        this.idtm=null;
        this.title = null;
        this.description = null;
        this.validationcode = null;
        this.lastupdate=null;
    }

    /**
     * Gets codeid value
     * @return an int value
     */
    public int getCodeid() {
        return codeid;
    }

    /**
     * Set codeid value
     * @param codeid input int value
     */
    public void setCodeid(int codeid) {
        this.codeid = codeid;
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
     * Gets validationcode value
     * @return a string value
     */
    public String getValidationcode() {
        return validationcode;
    }

    /**
     * Set validationcode value
     * @param validationcode input string value
     */
    public void setValidationcode(String validationcode) {
        this.validationcode = validationcode;
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
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            conn = SWBUtils.DB.getDefaultConnection();
            pst = conn.prepareStatement("select * from sr_validatecode where codeid = ? and idtm=?");
            pst.setInt(1,codeid);
            pst.setString(2,idtm);
            rs = pst.executeQuery();
            if (rs.next()){
                title = rs.getString("title");
                description = rs.getString("description");
                validationcode = rs.getString("validationcode"); //SWBUtils.IO.readInputStream(rs.getAsciiStream("validationcode"));
                lastupdate = rs.getTimestamp("lastupdate");
            }
            if(rs!=null)rs.close();
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
        }
        catch(Exception e){
            log.error("Error while trying to load a record from sr_validatecode",e);
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
        if(codeid == 0){
            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            String s_query = null;
            try{
                conn = SWBUtils.DB.getDefaultConnection();
                lastupdate = new Timestamp(new java.util.Date().getTime());
                pst = conn.prepareStatement("select max(codeid) from sr_validatecode where idtm=?");
                pst.setString(1,idtm);
                rs = pst.executeQuery();
                if(rs.next()){
                    codeid = rs.getInt(1) + 1;
                }
                else{
                    codeid = 1;
                }
                if(rs!=null) rs.close();
                if(pst!=null)pst.close();
                pst=null;

                s_query = "insert into sr_validatecode (codeid, idtm, title, description, validationcode, lastupdate) values (?, ?, ?, ?, ?, ?) ";
                pst = conn.prepareStatement(s_query);
                pst.setLong(1, codeid);
                pst.setString(2,idtm);
                pst.setString(3, title);
                pst.setString(4, description);

                //pst.setString(5,validationcode);
                if(validationcode==null)
                    pst.setString(5,null);
                else
                    pst.setAsciiStream(5,SWBUtils.IO.getStreamFromString(validationcode),validationcode.length());

                pst.setTimestamp(6, lastupdate);
                pst.executeUpdate();
                res = true;
                if(pst!=null)pst.close();
                if(conn!=null)conn.close();
            }
            catch(Exception e){
                log.error("Error while trying to create a record on sr_validatecode",e);
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
            s_query = "update sr_validatecode set title=?, description=?, validationcode=?, lastupdate=? where codeid=? and idtm=?";
            pst = conn.prepareStatement(s_query);
            pst.setString(1, title);
            pst.setString(2, description);

            //pst.setString(3,validationcode);
            if(validationcode==null)
                pst.setString(3,null);
            else
                pst.setAsciiStream(3,SWBUtils.IO.getStreamFromString(validationcode),validationcode.length());

            pst.setTimestamp(4, lastupdate);
            pst.setInt(5, codeid);
            pst.setString(6,idtm);
            pst.executeUpdate();
            res = true;
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
        }
        catch (Exception e){
            log.error("Error while trying to update a record on sr_validatecode",e);
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
            s_query = "delete from sr_validatecode where codeid=? and idtm=?";
            pst = conn.prepareStatement(s_query);
            pst.setInt(1, codeid);
            pst.setString(2,idtm);
            pst.executeUpdate();
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
            deleted=true;
        }
        catch (Exception e){
            log.error("Error while trying to remove a record from sr_validatecode",e);
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
