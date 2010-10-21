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

public class RecFreqAnswer {

    private static Logger log = SWBUtils.getLogger(RecFreqAnswer.class);

    private long freqanswerid;
    private String idtm;
    private String title;
    private String stringxml;
    private int groupaid;
    private int isreuse;
    private Timestamp created;
    private Timestamp lastupdate;

    /**
     * Constructor
     */    
    public RecFreqAnswer(){
        this.freqanswerid = 0;
        this.idtm=null;
        this.title = null;
        this.stringxml = null;
        this.groupaid = 0;
        this.isreuse = 0;
        this.created = null;
        this.lastupdate = null;
    }

    /**
     * Gets freqanswerid values
     * @return a long value
     */    
    public long getFreqanswerid() {
        return freqanswerid;
    }

    /**
     * Set freqanswerid value
     * @param freqanswerid input long value
     */    
    public void setFreqanswerid(long freqanswerid) {
        this.freqanswerid = freqanswerid;
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
     * Gets groupaid value
     * @return an int value
     */    
    public int getGroupaid() {
        return groupaid;
    }

    /**
     * Set groupaid value
     * @param groupaid input int value
     */    
    public void setGroupaid(int groupaid) {
        this.groupaid = groupaid;
    }

    /**
     * Gets isreuse value
     * @return an int value
     */    
    public int getIsreuse() {
        return isreuse;
    }

    /**
     * Set isreuseid value
     * @param isreuse input int value
     */    
    public void setIsreuse(int isreuse) {
        this.isreuse = isreuse;
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

    /**
     * Load information from database
     */    
    public void load(){
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            conn = SWBUtils.DB.getDefaultConnection();
            pst = conn.prepareStatement("select * from sr_freqanswer where freqanswerid=? and idtm=?");
            pst.setLong(1, freqanswerid);
            pst.setString(2,idtm);
            rs = pst.executeQuery();
            if(rs.next()){
                title = rs.getString("title");
                stringxml = SWBUtils.IO.readInputStream(rs.getAsciiStream("stringxml"));
                groupaid = rs.getInt("groupaid");
                isreuse = rs.getInt("isreuse");
                created = rs.getTimestamp("created");
                lastupdate = rs.getTimestamp("lastupdate");
            }
            if(rs!=null) rs.close();
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
        }
        catch(Exception e){
            log.error("Error while trying to load a record from sr_freqanswer",e);
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
        if(freqanswerid == 0){
            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
            String s_query = null;
            try{
                conn = SWBUtils.DB.getDefaultConnection();
                created = new Timestamp(new java.util.Date().getTime());
                setLastupdate(created);
                pst = conn.prepareStatement("select max(freqanswerid) from sr_freqanswer where idtm=?");
                pst.setString(1,idtm);
                rs = pst.executeQuery();
                if (rs.next()){
                    freqanswerid = rs.getLong(1) + 1;
                }
                else{
                    freqanswerid = 1;
                }
                if(rs!=null) rs.close();
                if(pst!=null)pst.close();
                pst=null;

                s_query = "insert into sr_freqanswer (freqanswerid, idtm, title, stringxml, groupaid, isreuse, created, lastupdate) values (?, ?, ?, ?, ?, ?, ?, ?) ";
                pst = conn.prepareStatement(s_query);
                pst.setLong(1, freqanswerid);
                pst.setString(2,idtm);
                pst.setString(3, title);

                if(stringxml==null)
                    pst.setString(4,null);
                else
                    pst.setAsciiStream(4,SWBUtils.IO.getStreamFromString(stringxml),stringxml.length());

                pst.setInt(5, groupaid);
                pst.setInt(6, isreuse);
                pst.setTimestamp(7, created);
                pst.setTimestamp(8, lastupdate);
                pst.executeUpdate();
                res = true;
                if(pst!=null)pst.close();
                if(conn!=null)conn.close();
            }
            catch(Exception e){
                log.error("Error while trying to create a record on sr_freqanswer",e);
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
            s_query = "update sr_freqanswer set title=?, stringxml=?, groupaid=?, isreuse=?, created=?, lastupdate=? where freqanswerid=? and idtm=?";
            pst = conn.prepareStatement(s_query);
            pst.setString(1, title);

            if(stringxml==null)
                pst.setString(2,null);
            else
                pst.setAsciiStream(2,SWBUtils.IO.getStreamFromString(stringxml),stringxml.length());

            pst.setInt(3, groupaid);
            pst.setInt(4, isreuse);
            pst.setTimestamp(5, created);
            pst.setTimestamp(6, lastupdate);
            pst.setLong(7, freqanswerid);
            pst.setString(8,idtm);
            pst.executeUpdate();
            res = true;
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
        }
        catch (Exception e){
            log.error("Error while trying to update a record on sr_freqanswer",e);
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
            s_query = "delete from sr_freqanswer where freqanswerid=? and idtm=?";
            pst = conn.prepareStatement(s_query);
            pst.setLong(1, freqanswerid);
            pst.setString(2,idtm);
            pst.executeUpdate();
            if(pst!=null)pst.close();
            if(conn!=null)conn.close();
            deleted=true;
        }
        catch (Exception e){
            log.error("Error while trying to remove a record from sr_freqanswer",e);
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
