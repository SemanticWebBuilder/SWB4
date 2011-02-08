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
 * AdmTopics.java
 *
 * Created on 19 de abril de 2004, 01:08 PM
 */

package com.infotec.wb.resources.repository;


import com.infotec.appfw.exception.*;
import com.infotec.appfw.util.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import com.infotec.wb.util.*;
import com.infotec.topicmaps.*;
import com.infotec.wb.lib.*;
import com.infotec.topicmaps.bean.*;
import com.infotec.wb.core.*;
import java.util.*;
/** Administraci�n de las carpetas del repositorio, agrega sub carpetas,
 * actualiza su nombre y nombre de ordenamiento, as� como, elimina la sub
 * carpeta seleccionada.
 *
 * Repository folders administration, add sub folders, updates name and order
 * name, also erase a selected sub folder.
 *
 * @author Javier Solis Gonzalez
 */
public class AdmTopics {
    Resource base=null;
    ResourceMgr Rmgr;


    /** Creates a new instance of AdmTopics */
    public AdmTopics() {
    }

    /**
     * Load the resource information
     * @param base Resource object
     */
    public void setResourceBase(Resource base) {
        this.base=base;
    }

    /**
     * Shows the form for creates a new folder
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user User object in session
     * @param topic Topic object
     * @param arguments A list of arguments
     * @param dir A topic object that represent a directory
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An exception of type AF (Applicatioon Framework)
     * @return a create form to show to the user
     */
    public String create(HttpServletRequest request, HttpServletResponse response, WBUser user, Topic topic, HashMap arguments, Topic dir,WBParamRequest paramsRequest) throws AFException {
        StringBuffer ret = new StringBuffer();
        String path = WBUtils.getInstance().getWebPath()+"wbadmin/resources/Repository/images/";
        WBResourceURL url = paramsRequest.getRenderUrl();
        url.setParameter("repobj","AdmTopics");
        url.setParameter("repacc","createUpd");
        url.setParameter("reptp",dir.getId());
        ret.append("<b>");
        ret.append(paramsRequest.getLocaleString("msgCreateDirectory"));
        ret.append("</b>\n<BR>");
        if(user.isLoged()) ret.append("<form action=\""+url+"\" method=\"post\">\n");
        ret.append("<table border=0 width=\"100%\">\n");
        ret.append("<tr><td colspan=2>");
        ret.append("<IMG src=\""+path+"line.gif\" width=\"100%\" height=\"5\">");
        ret.append("</td>");
        ret.append("</tr>");
        ret.append("<tr><td width=200>");
        ret.append("<FONT face=\"Verdana, Arial, Helvetica, sans-serif\">"+paramsRequest.getLocaleString("msgDirectoryName")+":</FONT>");
        ret.append("</td><td  align=left>");
        ret.append("<input type=\"text\" name=\"name\" value=\"\">\n");
        ret.append("</td></tr>");
        ret.append("<tr><td width=200>");
        ret.append("<FONT face=\"Verdana, Arial, Helvetica, sans-serif\">"+paramsRequest.getLocaleString("msgOrderName")+":</FONT>");
        ret.append("</td><td  align=left>");
        ret.append("<input type=\"text\" name=\"orderid\" value=\"\">\n");
        ret.append("</td></tr>");
        ret.append("<tr><td colspan=2>");
        ret.append("<IMG src=\""+path+"line.gif\" width=\"100%\" height=\"5\">");
        ret.append("</td>");
        ret.append("</tr>");
        ret.append("<tr><td colspan=2 align=center>\n");
        WBResourceURL urlBack = paramsRequest.getRenderUrl();
        urlBack.setParameter("reptp",dir.getId());
        if(user.isLoged())  ret.append("<input type=\"submit\" name=\"accept\" value=\""+paramsRequest.getLocaleString("msgBTNAccept")+"\" >\n");
        if(user.isLoged())  ret.append("<input type=\"button\" name=\"cancel\" value=\""+paramsRequest.getLocaleString("msgBTNCancel")+"\" onclick=\"javascript:window.location='"+urlBack.toString()+"';\" >\n");
        ret.append("</td></tr>\n");
        ret.append("</table>\n");
        if(user.isLoged())  ret.append("</form>\n");
        return ret.toString();
    }

    /**
     * Changes the folder information
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user User object in session
     * @param topic Topic object
     * @param arguments A list of arguments
     * @param dir A topic object that represent a directory
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An exception of type AF (Applicatioon Framework)
     * @return a edit form to change the name of the folder
     */
    public String changeName(HttpServletRequest request, HttpServletResponse response, WBUser user, Topic topic, HashMap arguments, Topic dir, WBParamRequest paramsRequest) throws AFException {
        StringBuffer ret = new StringBuffer();
        String path = WBUtils.getInstance().getWebPath()+"wbadmin/resources/Repository/images/";
        WBResourceURL url = paramsRequest.getRenderUrl();
        ret.append("<b>");
        ret.append(paramsRequest.getLocaleString("msgRenameDirectory"));
        ret.append("</b>\n<BR>");
        if(user.isLoged())  ret.append("<form action=\""+url+"?repobj=AdmTopics&repacc=changeNameUpd&reptp="+dir.getId()+"\" method=\"post\" >\n");
        ret.append("<table border=0 width=\"100%\">\n");
        ret.append("<tr><td colspan=2>");
        ret.append("<IMG src=\""+path+"line.gif\" width=\"100%\" height=\"5\">");
        ret.append("</td>");
        ret.append("</tr>");
        ret.append("<tr><td width=200>");
        ret.append("<FONT face=\"Verdana, Arial, Helvetica, sans-serif\">"+paramsRequest.getLocaleString("msgDirectoryName")+":</FONT>");
        ret.append("</td><td align=left>");
        ret.append("<input type=\"text\" name=\"name\" value=\""+dir.getDisplayName(dir.getMap().getlang())+"\">\n");
        ret.append("</td></tr>");
        ret.append("<tr><td width=200>");
        ret.append("<FONT face=\"Verdana, Arial, Helvetica, sans-serif\">"+paramsRequest.getLocaleString("msgOrderName")+":</FONT>");
        ret.append("</td><td  align=left>");
        ret.append("<input type=\"text\" name=\"orderid\" value=\"" + dir.getSortName() +"\">\n");
        ret.append("</td></tr>");
        ret.append("<tr><td colspan=2>");
        ret.append("<IMG src=\""+path+"line.gif\" width=\"100%\" height=\"5\">");
        ret.append("</td>");
        ret.append("</tr>");
        ret.append("<tr><td colspan=2 align=center>");
        WBResourceURL urlBack = paramsRequest.getRenderUrl();
        urlBack.setParameter("reptp",dir.getId());
        if(user.isLoged())  ret.append("<input type=\"submit\" name=\"accept\" value=\""+paramsRequest.getLocaleString("msgBTNAccept")+"\"  >\n");
        if(user.isLoged())  ret.append("<input type=\"button\" name=\"cancel\" value=\""+paramsRequest.getLocaleString("msgBTNCancel")+"\" onclick=\"javascript:window.location='"+urlBack.toString()+"';\" >\n");
        ret.append("</td></tr>\n");
        ret.append("</table>\n");
        if(user.isLoged()) ret.append("</form>\n");
        return ret.toString();
    }


    /**
     * Creates new folder folder
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user User object in session
     * @param topic Topic object
     * @param arguments A list of arguments
     * @param dir A topic object that represent a directory
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An exception of type AF (Applicatioon Framework)
     * @return a create folder action
     */
    public String createUpd(HttpServletRequest request, HttpServletResponse response, WBUser user, Topic topic, HashMap arguments, Topic dir, WBParamRequest paramsRequest) throws AFException{
        StringBuffer ret = null;
        String name = null;
        String s_id = null;
        String msg = "";
        Topic rd = null;

        rd = dir;
        name = request.getParameter("name");
        s_id = request.getParameter("orderid");
        if(name!=null){
            try{

                Topic aux=new Topic(dir.getMap());
                aux.setId(TopicMgr.getInstance().getIdGenerator().getID(name, dir.getMap().getId()));

                aux.setSubjectIdentity(new SubjectIdentity(paramsRequest.getTopic().getUrl(aux)));

                BaseName bn=new BaseName(name);
                aux.addBaseName(bn);
                bn.setScope(new Scope(dir.getMap().getlang()));
                aux.addType(dir);
                if(s_id != null){
                    BaseName sn=new BaseName(s_id);
                    sn.setScope(new Scope(aux.getMap().getTopic("CNF_WBSortName")));
                    aux.addBaseName(sn);
                }
                aux.setActive(1);
                aux.setIndexable(0);
                dir.getMap().addTopic(aux);
                dir.getMap().update2DB();
                rd=aux;
                saveLog("create",user,0,dir,"Create a directory",0);
                msg=paramsRequest.getLocaleString("msgDirectoryCreatedSuccessfully")+"...";
            }
            catch(Exception e){
                msg=paramsRequest.getLocaleString("msgErrorCreatingDirectory")+"...";
            }
        }
        else{
            msg=paramsRequest.getLocaleString("msgErrorCreatingDirectoryWrongName")+"...";
        }

        ret = new StringBuffer();
        ret.append("<script>");
        ret.append("alert('");
        ret.append(msg);
        ret.append("');");
        WBResourceURL url = paramsRequest.getRenderUrl();
        ret.append("window.location='"+url+"';");
        ret.append("</script>\n");
        return ret.toString();
    }


    /**
     * Update the folder information
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user User object in session
     * @param topic Topic object
     * @param arguments A list of arguments
     * @param dir A topic object that represent a directory
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An exception of type AF (Applicatioon Framework)
     * @return a update action for the folder information
     */
    public String changeNameUpd(HttpServletRequest request, HttpServletResponse response, WBUser user, Topic topic, HashMap arguments, Topic dir, WBParamRequest paramsRequest) throws AFException{
        String s_id = request.getParameter("orderid");
        String name=request.getParameter("name");
        String msg="";
        Topic rd=dir;
        if(name!=null){
            try{
                dir.getBaseNames().clear();
                BaseName bn=new BaseName(name);
                dir.addBaseName(bn);
                bn.setScope(new Scope(dir.getMap().getlang()));
                if(s_id != null){
                    BaseName sn=new BaseName(s_id);
                    sn.setScope(new Scope(dir.getMap().getTopic("CNF_WBSortName")));
                    dir.addBaseName(sn);
                }
                dir.getMap().update2DB();
                saveLog("rename",user,0,dir,"Rename a directory a directory",0);
                msg=paramsRequest.getLocaleString("msgDirectoryCreatedSuccessfully")+"...";
            }catch(Exception e) {
                msg=paramsRequest.getLocaleString("msgErrorChangingNameDirectory")+"...";
            }
        }else {
            msg=paramsRequest.getLocaleString("msgErrorChangingNameDirectoryWrongName")+"...";
        }

        StringBuffer ret = new StringBuffer();
        ret.append("<script>");
        ret.append("alert('");
        ret.append(msg);
        ret.append("');");
       WBResourceURL url = paramsRequest.getRenderUrl();
        ret.append("window.location='"+url+"';");
        ret.append("</script>\n");
        return ret.toString();
    }


    /**
     * Removes a folder
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user User object in session
     * @param topic Topic object
     * @param arguments A list of arguments
     * @param dir A topic object that represent a directory
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An exception of type AF (Applicatioon Framework)
     * @return the action for removes a folder
     */
    public String remove(HttpServletRequest request, HttpServletResponse response, WBUser user, Topic topic, HashMap arguments, Topic dir, WBParamRequest paramsRequest) throws AFException{
        StringBuffer ret = new StringBuffer();
        Topic rd=dir;
        Topic aux=dir.getType();
        String msg="";
        if(RemoveDirFiles(dir)){
            try{
                // se deben de revisar si los t�picos tienen ag�n archivo borrado haciendo referencia a este o alg�n otro t�pico
                Connection conn = WBUtils.getInstance().getDBConnection();
                PreparedStatement pst = conn.prepareStatement("select * from resrepository where idtm=? and topic=?");
                pst.setString(1, dir.getMap().getId());
                pst.setString(2, dir.getId());
                ResultSet rs = pst.executeQuery();
                boolean flag = false;
                if(rs.next()){
                    flag=true;
                }
                rs.close();
                pst.close();
                conn.close();
                rs=null;
                pst=null;
                conn=null;
                // si no tiene ning�n subfolder o archivo haciendo referencia a este entonces el folder (t�pico) se puede eliminar.
                if(dir.getChild().isEmpty()&&!flag){
                    dir.getMap().removeTopic(dir.getId());
                }
                //si tiene alg�n t�pico hijo o folder eliminado dentro de este s�lo se marca el t�pico como borrado
                else{
                    dir.getDbdata().setDeleted(1);
                    dir.getDbdata().update();
                }
                aux.getMap().update2DB();
                rd=aux;
                saveLog("Remove",user,0,dir,"Remove a directory",0);
                msg=paramsRequest.getLocaleString("msgDirectoryRemovedSuccessfully")+"...";
            }
            catch(Exception e){
                msg=paramsRequest.getLocaleString("msgErrorRemovingDirectory")+"...";
            }
        }
        else{
            msg=paramsRequest.getLocaleString("msgCannotRemoveDirectory")+"...";
        }
        ret.append("<script>");
        ret.append("alert('");
        ret.append(msg);
        ret.append("');");
        WBResourceURL url = paramsRequest.getRenderUrl();
        url.setParameter("reptp",aux.getId());
        ret.append("window.location='"+url+"';");
        ret.append("</script>\n");

        return ret.toString();
    }


    /**
     * Remove files from the directory
     * @param dir A topic object that represents a directory
     * @return True or false if the files was removed
     */
    private boolean RemoveDirFiles(Topic dir){
        boolean regresa=false;
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        long repdocid=-1;
        long resid=-1;
        boolean flagC=false;
        try{
            con=WBUtils.getInstance().getDBConnection();
            Iterator iteDirChilds=dir.getSortChild(false);
            // Verifies if exists subdirectory
            if(iteDirChilds.hasNext()){
                return false;
            }
            // Verifies if exists subdirectory
            while(iteDirChilds.hasNext()){
                flagC=true;
                RemoveDirFiles((Topic)iteDirChilds.next());
            }
            ps=con.prepareStatement("select rep_docid,resid from resrepository where idtm=? and topic=? and rep_deleted = 0");
            ps.setString(1,dir.getMap().getId());
            ps.setString(2,dir.getId());
            rs=ps.executeQuery();
            // Verifies if exists subdirectory
            if(rs.next()){
                return false;
            }
            while(rs.next()){
                repdocid=rs.getLong("rep_docid");
                resid=rs.getLong("resid");
                RemoveFilesFromDB(repdocid,resid,dir);
            }
            rs.close();
            ps.close();
            con.close();
            regresa=true;
        }
        catch(Exception e){
            AFUtils.log(e,"Error in AdmTopics:RemoveTopicFiles",true);
        }
        finally{
            try{
                if(rs!=null)rs.close();
                if(ps!=null)ps.close();
                if(con!=null)con.close();
            }
            catch(Exception e){
                AFUtils.log(e,"Error while trying to close connections, AdmTopics:RemoveDirFiles",true);
            }
        }
        return regresa;
    }


    /**
     * Removes file from the data base
     * @param id File identifier
     * @param resid Resource identifier
     * @param dir Topic object that represent a directory or a folder
     * @return True or false if the file was removed from the data base
     */
    private boolean RemoveFilesFromDB(long id,long resid,Topic dir){
        boolean regresa=false;
        Connection con=null;
        PreparedStatement ps=null;
        Rmgr=ResourceMgr.getInstance();
        try{
            WBResource wbRes=Rmgr.getResource(dir.getMap().getId(),resid);
            Resource resource=wbRes.getResourceBase();
            con=WBUtils.getInstance().getDBConnection();
            ps=con.prepareStatement("delete from resrepository where rep_docId=? and idtm=?");
            ps.setLong(1,id);
            ps.setString(2,dir.getMap().getId());
            ps.execute();
            ps.close();
            ps=con.prepareStatement("delete from resrepositoryversions where rep_docId=? and idtm=?");
            ps.setLong(1,id);
            ps.setString(2,dir.getMap().getId());
            ps.execute();
            ps.close();
            ps=con.prepareStatement("delete from resrepositorynotify where topic=? and idtm=?");
            ps.setString(1,dir.getId());
            ps.setString(2,dir.getMap().getId());
            ps.execute();
            ps.close();
            File fdir=new File(WBUtils.getInstance().getWorkPath()+"/"+resource.getResourceWorkPath()+"/");
            String filestarts=id+"_";
            File[] files=fdir.listFiles();
            for(int i=0;i<files.length;i++){
                File f=files[i];
                if(f.getName().startsWith(filestarts)){
                    f.delete();
                }
            }
            con.close();
            regresa=true;
        }
        catch(Exception e){
            AFUtils.log(e,"Error in AdmTopics:RemoveFilesFromDB",true);
        }
        finally{
            try{
                if(ps!=null)ps.close();
                if(con!=null)con.close();
            }
            catch(Exception e){
                AFUtils.log(e,"Error while trying to close connections, AdmTopics:RemoveDirFiles",true);
            }
        }
        return regresa;
    }

    /**
     * Save the user action in the data base
     * @param p_action The user action
     * @param user User object
     * @param p_fileid File identifier
     * @param p_topic Topic object
     * @param p_description Description of the action
     * @param p_isfile A flag to indicate if is a file or not
     */
    public void saveLog(String p_action, WBUser user, long p_fileid, Topic p_topic, String p_description, int p_isfile){
        Connection con = null;
        PreparedStatement ps= null;
        ResultSet rs = null;
        String str_name = null;
        String str_title = "";
        String str_topicmapid = null;
        String str_topicid = null;
        try{
            con=WBUtils.getInstance().getDBConnection();
            if(p_isfile == 1){
                ps = con.prepareStatement("select rep_title from resrepository where rep_docId=? and idtm=?");
                ps.setLong(1, p_fileid);
                ps.setString(2,p_topic.getMap().getId());
                rs = ps.executeQuery();
                if(rs.next()){
                    str_title = rs.getString("rep_title");
                }
            }
            else{
                str_title = p_topic.getDisplayName();
            }
            if(user.getFirstName() != null) str_name = user.getFirstName();
            if(user.getLastName() != null) str_name = str_name + " " + user.getLastName();
            str_topicid = p_topic.getId();
            str_topicmapid = p_topic.getMap().getId();

            ps=con.prepareStatement("insert into resrepositorylog "+
            "(rep_email,rep_user,rep_docId,rep_action,rep_name,rep_topicid,rep_topicmapid,rep_objectid,rep_description,rep_dateaction,rep_isfile,rep_ipuser) "+
            "values(?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, user.getId());
            ps.setString(2, str_name);
            ps.setLong(3, p_fileid);
            ps.setString(4, p_action);
            ps.setString(5, str_title);
            ps.setString(6, str_topicid);
            ps.setString(7, str_topicmapid);
            ps.setLong(8, base.getId());
            ps.setString(9, p_description);
            ps.setTimestamp(10,new Timestamp(System.currentTimeMillis()));
            ps.setInt(11, 0);
            ps.setString(12, user.getIp());
            ps.execute();
            ps.close();
            con.close();
        }
        catch(Exception e){
            AFUtils.log(e,"Error while create a record of the resource in RepositoryFile:saveLog",true);
        }
        finally{
            ps = null;
            con = null;
        }
    }

}
