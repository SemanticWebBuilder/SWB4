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
 * RepositoryFile.java
 *
 * Created on 19 de abril de 2004, 12:40 PM
 */
package com.infotec.wb.resources.repository;

import com.infotec.topicmaps.*;
import com.infotec.wb.lib.WBResourceURL;
import com.infotec.wb.core.*;
import com.infotec.appfw.exception.*;
import com.infotec.appfw.util.*;
import com.infotec.wb.lib.WBParamRequest;
import com.infotec.wb.util.WBUtils;
import com.infotec.appfw.util.FileUpload;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.sql.*;
import java.text.*;
import com.infotec.wb.core.db.*;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/** Muestra la lista de los documentos existentes dentro del repositorio. Se pueden
 * agregar documentos, mostrar su informaci�n detallada, el historial de cada
 * documento, vista preliminar, borrar el documento del repositorio, sacar el
 * documento del repositorio para su revisi�n o modificaci�n y actualizar el
 * documento.
 *
 * Show the repository list of the existing documents. It can add new documents,
 * show document detail, document history, document preview, erase document from
 * the repository, make a check out for review or for a modification, check in with
 * the updated document.
 * @author Victor Lorenzana
 */
public class RepositoryFile {

    protected org.semanticwb.model.Resource resource;
    String path = "";
    String[] values;
    protected Notification notification = new Notification();
    protected TreeRepHtml dirs = new TreeRepHtml();

    /** Creates a new instance of RepositoryFile */
    public RepositoryFile() {
    }

    /** User html view of the repository resource files
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An Application Framework exception
     * @return The html code of existing files in the repository
     */
    public String getHtml(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        path = SWBPortal.getWebWorkPath() + resource.getWorkPath();
        int level = getLevelUser(user, topic.getWebSiteId());
        return getHtml(request, response, user, topic, hashMap, topic, level, paramsRequest);
    }

    /** Gets the user level into the repository
     * @param user WBUser object to get the access level
     * @param tmid Topic map identifier
     * @return An integer value of the user level
     */
    public int getLevelUser(User user, String tmid) {


        int level=0;
        String adm=resource.getAttribute("admin");
        User usr = user;
        UserRepository usrRep = usr.getUserRepository();
        Role rol = null;
        if(adm!=null)
        {
            rol = usrRep.getRole(adm);
            //int r=Integer.parseInt(adm);
            if(user.hasRole(rol)) level=3;
        }
        else level=3;

        if(level==0)
        {
            String mdy=resource.getAttribute("modify");
            if(mdy!=null)
            {
                rol = usrRep.getRole(mdy);
                //int r=Integer.parseInt(mdy);
                if(user.hasRole(rol)) level=2;
            }
            else level=2;
        }

        if(level==0)
        {
            String viw=resource.getAttribute("view");
            if(viw!=null)
            {
                rol = usrRep.getRole(viw);
                //int r=Integer.parseInt(viw);
                 if(user.hasRole(rol)) level=1;
            }
            else level=1;
        }
        return level;

    }

    /**
     * User html view of the repository resource files
     * @return The html code of existing files in the repository
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param dir A topic object that represent a directory
     * @param nivel The user level into the repository
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An Application Framework exception
     */
    public String getHtml(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, int nivel, SWBParamRequest paramsRequest) throws  SWBResourceException, IOException {

        String accept = request.getHeader("accept");
        if (null != accept) {
            values = accept.split(",");
        }
        SWBResourceURL url1 = paramsRequest.getRenderUrl();
        url1.setMode(url1.Mode_VIEW);
        StringBuffer ret = new StringBuffer();
        FileUpload fup = new FileUpload();
        try {
            fup.getFiles(request, response);
            String op = fup.getValue("repfop");

            if (op == null) {
                op = request.getParameter("repfop");
            }

            if (paramsRequest.getCallMethod() == paramsRequest.Call_DIRECT && op == null) {
                op = "view";
            }
            //System.out.println("repfop:"+op);
            if (op != null) {
                if (op.equals("delete")) {
                    delete(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest, fup);

                    ret.append("<script language='JavaScript'> \r\n");
                    ret.append("document.location='" + url1.toString() + "?reptp=" + dir.getId() + "';\r\n");
                    ret.append("</script>\r\n");
                } else if (op.equals("add")) {
                    ret.append(create(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest));
                } else if (op.equals("insert")) {
                    ret.append(insert(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest, fup));
                    ret.append("<meta http-equiv=\"refresh\" content=\"0;url=" + url1.toString() + "?reptp=" + dir.getId() + "\"> ");

                } else if (op.equals("history")) {
                    ret.append(history(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest));
                } else if (op.equals("info")) {
                    ret.append(info(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest));
                } else if (op.equals("checkout")) {
                    long id = Integer.parseInt(request.getParameter("repfiddoc"));
                    checkout(request, response, user, topic, hashMap, dir, this.resource, nivel);

                    ret.append(showfiles(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest));

                    url1.setCallMethod(url1.Call_DIRECT);
                    ret.append("<meta http-equiv=\"refresh\" content=\"0;url=" + url1.toString() + "?reptp=" + dir.getId() + "&repfop=view&repfiddoc=" + id + "\"> ");
                } else if (op.equals("checkin")) {
                    ret.append(this.checkin(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest));
                } else if (op.equals("view")) {
                    this.view(request, response, user, topic, hashMap, dir, this.resource);
                    return "empty";
                } else if (op.equals("undocheckuout")) {

                    this.undocheckout(request, response, user, topic, hashMap, dir, this.resource, nivel);
                    ret.append("<script language='JavaScript'>\r\n");
                    ret.append("window.location='" + url1.toString() + "?reptp=" + dir.getId() + "';\r\n");
                    ret.append("</script>\r\n");
                } else if (op.equals("updatetitle")) {
                    long id = Long.parseLong(request.getParameter("repfiddoc"));
                    this.updatetitle(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest);
                    // indexar cambio


                    ret.append("<form name=\"frmupdatetitle\" action=\"" + url1.toString() + "\" method=\"post\">");
                    ret.append("<input type=\"hidden\" name=\"repfop\" value=\"info\"><input type=\"hidden\" name=\"updated\" value=\"1\"><input type=\"hidden\" name=\"reptp\" value=\"" + dir.getId() + "\"><input type=\"hidden\" name=\"repfiddoc\" value=\"" + id + "\">");
                    ret.append("</form>");
                    ret.append("<script language='JavaScript'>\r\n");
                    ret.append("window.document.frmupdatetitle.submit();");
                    ret.append("</script>\r\n");
                } else if (op.equals("mod")) {
                    long id = Integer.parseInt(fup.getValue("repfiddoc"));
                    this.newversion(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest, fup);
                    ret.append("<form name=\"frmnewversion\" action=\"" + url1.toString() + "\" method=\"post\">");
                    ret.append("<input type=\"hidden\" name=\"repfop\" value=\"history\"><input type=\"hidden\" name=\"reptp\" value=\"" + dir.getId() + "\"><input type=\"hidden\" name=\"repfiddoc\" value=\"" + id + "\">");
                    ret.append("</form>");
                    ret.append("<script language='JavaScript'>\r\n");
                    ret.append("window.document.frmnewversion.submit();");
                    ret.append("</script>\r\n");
                } else if (op.equals("movedoctodir")) {
                    long id = Long.parseLong(request.getParameter("repiddoc"));
                    String origen = request.getParameter("reptp_original");
                    String destino = request.getParameter("reptp");
                    WebPage fromDir = dir.getWebSite().getWebPage(origen);
                    WebPage toDir = dir.getWebSite().getWebPage(destino);
                    //System.out.println("movedoctodir");
                    if (!moveDoc2Dir(id, fromDir, toDir, user)) {
                        AFUtils.log("Error while trying to move the document to another directory. RepositoryFile.getHtml.movedoctodir", true);
                    }
                    ret.append("<script language='JavaScript'>\r\n");
                    ret.append("window.location='" + url1.toString() + "?reptp=" + toDir.getId() + "';\r\n");
                    ret.append("</script>\r\n");
                } else {
                    ret.append(showfiles(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest));
                }
            } else {
                ret.append(showfiles(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest));
            }

        } catch (IOException ioe) {
            AFUtils.log(ioe, "Error in RepositoryFile.getHTML");
        }

        return ret.toString();
    }

    /** Load the Resource object information
     * @param resource The Resource object
     */
    public void setResourceBase(org.semanticwb.model.Resource resource) {
        try {
            this.resource = resource;
            path = WBUtils.getInstance().getWebPath() + "wbadmin/resources/Repository/images/";

        } catch (Exception e) {
            AFUtils.log(e, "", true);
        }
    }

    /** Cancel the file reservation
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param dir A topic object that represent a directory
     * @param resource A Resource object
     * @param nivel The user level into the repository
     * @throws AFException An Application Framework exception
     */
    public void undocheckout(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource, int nivel) throws SWBResourceException, IOException {
        if (nivel < 2) {
            return;
        }
        try {
            long id = Long.parseLong(request.getParameter("repfiddoc"));
            Connection con = null;
            try {
                con = WBUtils.getDBConnection();
                boolean cancheckin = false;
                if (nivel == 3) {
                    cancheckin = true;
                } else {
                    PreparedStatement psuser = con.prepareStatement("select rep_emailCheckOut from resrepository where rep_docId=? and idtm=?");
                    psuser.setLong(1, id);
                    psuser.setString(2, dir.getWebSiteId());
                    ResultSet rsuser = psuser.executeQuery();

                    if (rsuser.next()) {
                        if (user.getId() != null) {
                            if (rsuser.getString("rep_emailCheckOut").equals(user.getId())) {
                                cancheckin = true;
                            }
                        }
                    }
                    rsuser.close();
                    psuser.close();
                }

                if (nivel == 3) {
                    cancheckin = true;
                }
                if (!cancheckin) {
                    return;
                }
                PreparedStatement psstatus = con.prepareStatement("select rep_status, rep_title from resrepository where rep_docId=? and idtm=?");
                psstatus.setLong(1, id);
                psstatus.setString(2, dir.getWebSiteId());
                ResultSet rsstatus = psstatus.executeQuery();
                boolean mod = false;
                if (rsstatus.next()) {
                    if (rsstatus.getInt("rep_status") == 1) {
                        mod = true;
                    }
                }
                rsstatus.close();
                psstatus.close();
                if (mod) {
                    PreparedStatement ps = con.prepareStatement("update resrepository set rep_status=?,rep_emailCheckOut=? where rep_docId=? and idtm=?");
                    ps.setInt(1, 0);
                    ps.setString(2, null);
                    ps.setLong(3, id);
                    ps.setString(4, dir.getWebSiteId());
                    ps.execute();
                    ps.close();
                    saveLog("undocheckout", user, id, dir, "This user made checkout", 1);
                }
            } catch (SQLException e) {
                AFUtils.log(e, "Error in undocheckout", true);
                return;
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        AFUtils.log(e, "Error in undocheckout", true);
                    }
                }
            }
        } catch (Exception e) {
            AFUtils.log(e, "Error in undocheckout", true);
        }
        return;
    }

    /**
     * Get the file to user view from the repository
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param dir A topic object that represent a directory
     * @param resource A Resource object
     * @throws AFException An Application Framework exception
     */
    public void view(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource) throws SWBResourceException, IOException {

        //System.out.println("TOPICO: " + topic.getId()+", DIR:"+dir.getId());
        String p_repinline = request.getParameter("repinline");
        int levelUser = getLevelUser(user, topic.getWebSiteId());
        WebPage tmpTp = topic;
        String subdir = resource.getAttribute("showdirectory");
        if (subdir != null && "true".equals(subdir)) {
            tmpTp = dir;
        }



        if (!user.haveAccess(tmpTp) || levelUser == 0) {
            try {
                response.sendError(403);

            } catch (Exception e) {
            }
            return;
        }

        try {
            Connection con = null;
            long id = 0;
            String fileName = null;
            if (request.getParameter("repfiddoc") == null) {
                PreparedStatement pstID = null;
                ResultSet rsID = null;
                try {
                    String p_reptp = request.getParameter("reptp");
                    String p_repfiddoc = request.getParameter("repfiddoc");

                    //long idRes = resource.getId();
                    String idRes = resource.getId();

                    if (p_reptp == null && p_repfiddoc == null && p_repinline == null) {
                        fileName = request.getRequestURL().toString();///MapaSitio.xslt?repfop=view&reptp=CNFWB_Rep11&repfiddoc=1&repinline=true
                        if (null != fileName) {
                            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
                            con = WBUtils.getDBConnection();
                            String sqlID = "select rrep.rep_docId as idDoc, rver.rep_fileName as fileName from resrepository rrep, resrepositoryversions rver "
                                    + " where rrep.topic=? and rrep.resId = ? and rrep.rep_docId = rver.rep_docId and rver.rep_fileName = ?";
                            pstID = con.prepareStatement(sqlID);
                            pstID.setString(1, tmpTp.getId());
                            pstID.setString(2, idRes);
                            pstID.setString(3, fileName);
                            rsID = pstID.executeQuery();
                            if (rsID.next()) {
                                //System.out.println("Entrando al ResultSet");
                                id = rsID.getLong(1);
                                p_repinline = "true";
                            }
                            rsID.close();
                            pstID.close();
                            con.close();
                        }
                        //System.out.println("ID Archivo: " + id);
                        //System.out.println("In Line: " + p_repinline);


                    }

                } catch (Exception e) {
                    AFUtils.log(e, "Error al buscar el archivo por nombre: " + fileName, true);
                } finally {
                    if (con != null) {
                        try {
                            con.close();
                            con = null;
                        } catch (Exception e) {
                            AFUtils.log(e, "Error in RepositoryFile.view() -- 1.1 --", true);
                        }
                    }
                    if (rsID != null) {
                        try {
                            rsID.close();
                            rsID = null;
                        } catch (Exception e) {
                            AFUtils.log(e, "Error in RepositoryFile.view() -- 1.1 --", true);
                        }
                    }
                    if (pstID != null) {
                        try {
                            pstID.close();
                            pstID = null;
                        } catch (Exception e) {
                            AFUtils.log(e, "Error in RepositoryFile.view() -- 1.1 --", true);
                        }

                    }
                }
            } else {
                id = Long.parseLong(request.getParameter("repfiddoc"));
            }
            String sversion = request.getParameter("repfversion");
            int version = 1;
            try {
                con = WBUtils.getDBConnection();
                if (sversion == null) {
                    PreparedStatement psversion = con.prepareStatement("select rep_lastVersion from resrepository where rep_docId=? and idtm=?");
                    psversion.setLong(1, id);
                    psversion.setString(2, tmpTp.getWebSiteId());
                    ResultSet rs = psversion.executeQuery();
                    if (rs.next()) {
                        version = rs.getInt("rep_lastVersion");
                    }
                    rs.close();
                } else {
                    version = Integer.parseInt(sversion);
                }
                PreparedStatement ps = con.prepareStatement("select * from  resrepositoryversions where rep_docId=? and rep_fileVersion=? and idtm=?");
                ps.setLong(1, id);
                ps.setInt(2, version);
                ps.setString(3, tmpTp.getWebSiteId());
                ResultSet rsversion = ps.executeQuery();
                String filename = null, rep_fileType = null;
                if (rsversion.next()) {
                    filename = rsversion.getString("rep_fileName");
                    rep_fileType = rsversion.getString("rep_fileType");
                }
                rsversion.close();
                ps.close();
                if (filename != null && rep_fileType != null) {
                    response.setContentType(rep_fileType);

                    String ext = "";
                    int pos = filename.lastIndexOf('.');
                    if (pos != -1) {
                        ext = filename.substring(pos);
                    } else {
                        ext = "";
                    }
                    String sfile = id + "_" + version + ext;

                    if (p_repinline != null) {
                        if (p_repinline.equals("true")) {
                            response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\";");
                        } else {
                            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\";");
                        }
                    } else {
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\";");
                    }

                    OutputStream out = response.getOutputStream();
                    InputStream in = SWBPortal.getFileFromWorkPath(resource.getWorkPath() + "/" + sfile);
                    AFUtils.copyStream(in, out);
                }
            } catch (SQLException e) {
                AFUtils.log(e, "Error in RepositoryFile.view() -- 1 -- ", true);
                return;
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        AFUtils.log(e, "Error in RepositoryFile.view() -- 1.1 --", true);
                    }
                }
            }
        } catch (Exception e) {
            AFUtils.log(e, "Error in RepositoryFile.view()", true);
        }
    }

    /** Updates the file information
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param dir A topic object that represent a directory
     * @param resource A Resource object
     * @param nivel The user level into the repository
     * @throws AFException An Application Framework exception
     */
    public synchronized void updatetitle(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource, int nivel, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if (nivel < 2) {
            return;
        }
        String filename = null;
        int version = 0;
        String title = request.getParameter("repftitle");
        String description = request.getParameter("repfdescription");

        try {
            long id = Long.parseLong(request.getParameter("repfiddoc"));
            Connection con = null;
            try {
                con = WBUtils.getDBConnection();
                PreparedStatement ps = con.prepareStatement("select rep_email from resrepository where rep_docId=? and idtm=?");
                ps.setLong(1, id);
                ps.setString(2, dir.getWebSiteId());
                boolean canupdate = false;
                if (nivel == 3) {
                    canupdate = true;
                } else {
                    if (user.getId() != null) {
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            if (rs.getString("rep_email") != null) {
                                if (rs.getString("rep_email").equals(user.getId())) {
                                    canupdate = true;
                                }
                            }
                        }
                        rs.next();
                    }
                }
                ps.close();

                if (canupdate) {
                    PreparedStatement psupdate = con.prepareStatement("update resrepository set rep_title=?,rep_description=? where rep_docId=? and idtm=?");
                    psupdate.setString(1, title);
                    psupdate.setString(2, description);
                    psupdate.setLong(3, id);
                    psupdate.setString(4, dir.getWebSiteId());
                    psupdate.execute();
                    psupdate.close();
                    saveLog("update", user, id, dir, "Description", 1);
                    // indexar cambios
                    ps = con.prepareStatement("select rep_fileVersion, rep_fileName from resrepositoryversions where rep_docId=? and idtm=? order by rep_fileVersion desc");
                    ps.setLong(1, id);
                    ps.setString(2, dir.getWebSiteId());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        //obtener valores faltantes para poder realizar la re-indexacion
                        version = rs.getInt("rep_fileVersion");
                        filename = rs.getString("rep_fileName");
                    }
                    ps.close();

                    int pos = filename.lastIndexOf('.');
                    String file_db = filename;
                    if (pos != -1) {
                        file_db = id + "_" + version + file_db.substring(pos);
                    } else {
                        file_db = id + "_" + version;
                    }
//                    indexFile(file_db, file_db, id, title, dir, paramsRequest);
                }
            } catch (SQLException e) {
                AFUtils.log(e, "Error in updatetitle", true);
                return;
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        AFUtils.log(e, "Error in updatetitle", true);
                    }
                }
            }
        } catch (Exception e) {
            AFUtils.log(e, "Error in updatetitle", true);
        }
    }

//    private void indexFile(String file_remove, String file_index, long f_id, String title, Topic dir, WBParamRequest paramsRequest) throws AFException {
//        //if (indxRemove) {
//            File f_last = new File(WBUtils.getInstance().getWorkPath() + "/" + resource.getResourceWorkPath() + "/" + file_remove);
//            if (f_last.exists()) {
//                WBIndexer w_indx_last = WBIndexMgr.getInstance().getTopicMapIndexer(dir.getMap().getId());
//                if (w_indx_last != null) {
//                    try {
//                        w_indx_last.removeFile(f_last);  // Para eliminar un archivo del index
//
//                    } catch (Exception ex) {
//                        AFUtils.log(ex, "Error while trying to remove a file from index.", true);
//                    }
//                }
//            }
//        //}
//
//        File f = new File(WBUtils.getInstance().getWorkPath() + "/" + resource.getResourceWorkPath() + "/" + file_index);
//        if (f.exists()) {
//            WBIndexer w_indx = WBIndexMgr.getInstance().getTopicMapIndexer(dir.getMap().getId());
//            if (w_indx != null) {
//                try {
//                    String type = f.getName();
//                    WBResourceURL urllineA = paramsRequest.getRenderUrl();
//                    urllineA.setCallMethod(urllineA.Call_DIRECT);
//                    String url = "" + urllineA + "/" + type + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + Long.toString(f_id) + "&repinline=true";
//
//                    w_indx.indexFile(f, title, url, dir, resource.getId());
//                } catch (Exception ex) {
//                    AFUtils.log(ex, "Error while trying to add a file to index.", true);
//                }
//            }
//        }
//    }

    /**
     * Generates a new version of the file
     * @param fup File upload object
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param dir A topic object that represent a directory
     * @param resource A Resource object
     * @param nivel The user level into the repository
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An Application Framework exception
     */
    public synchronized void newversion(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource, int nivel, SWBParamRequest paramsRequest, FileUpload fup) throws SWBResourceException, IOException {
        System.out.println("RepositoryFile.newversion");
        if (nivel < 2) {
            return;
        }
        try {
            long id = Long.parseLong(fup.getValue("repfiddoc"));
            Connection con = null;
            String filename = fup.getFileName("repfdoc");
            if (filename.lastIndexOf('/') != -1) {
                int pos = filename.lastIndexOf('/');
                filename = filename.substring(pos + 1);
            }
            if (filename.lastIndexOf('\\') != -1) {
                int pos = filename.lastIndexOf('\\');
                filename = filename.substring(pos + 1);
            }

            String title = "";
            byte[] bcont = fup.getFileData("repfdoc");
            try {
                con = WBUtils.getDBConnection();
                boolean cancheckin = false;
                PreparedStatement psuser = con.prepareStatement("select rep_title,rep_emailCheckOut from resrepository where rep_docId=? and idtm=?");
                psuser.setLong(1, id);
                psuser.setString(2, dir.getWebSiteId());
                ResultSet rsuser = psuser.executeQuery();
                if (rsuser.next()) {
                    title = rsuser.getString("rep_title");
                    if (user.getId() != null) {
                        if (rsuser.getString("rep_emailCheckOut").equals(user.getId())) {
                            cancheckin = true;
                        }
                    }
                }
                rsuser.close();
                psuser.close();
                if (!cancheckin) {
                    return;
                }
                int version = 1;
                int last_ver = 1;
                PreparedStatement psmaxversion = con.prepareStatement("select max(rep_fileVersion) as max from resrepositoryversions where rep_docId=? and idtm=?");
                psmaxversion.setLong(1, id);
                psmaxversion.setString(2, dir.getWebSiteId());
                ResultSet rsmax = psmaxversion.executeQuery();
                if (rsmax.next()) {
                    version = rsmax.getInt("max");
                    last_ver = version;
                }
                rsmax.close();
                psmaxversion.close();
                version++;

                int pos = filename.lastIndexOf('.');
                String file_db = filename;
                String file_last = filename;
                if (pos != -1) {
                    file_db = id + "_" + version + file_db.substring(pos);
                    file_last = id + "_" + last_ver + file_last.substring(pos);
                } else {
                    file_db = id + "_" + version;
                    file_last = id + "_" + last_ver;
                }

                SWBPortal.writeFileToWorkPath(resource.getWorkPath() + "/" + file_db, new ByteArrayInputStream(bcont), user);

                //indexFile(file_last, file_db, id, title, dir, paramsRequest);


//                if (version != last_ver) {
//                    File f_last = new File(WBUtils.getInstance().getWorkPath() + "/" + resource.getResourceWorkPath() + "/" + file_last);
////                    if (f_last.exists()) {
////                        WBIndexer w_indx_last = WBIndexMgr.getInstance().getTopicMapIndexer(dir.getMap().getId());
////                        if (w_indx_last != null) {
////                            try {
////                                w_indx_last.removeFile(f_last);  // Para eliminar un archivo del index
////
////                            } catch (Exception ex) {
////                                AFUtils.log(ex, "Error while trying to remove a file from index.", true);
////                            }
////                        }
////                    }
//                }

//                File f = new File(WBUtils.getInstance().getWorkPath() + "/" + resource.getResourceWorkPath() + "/" + file_db);
//                if (f.exists()) {
//                    WBIndexer w_indx = WBIndexMgr.getInstance().getTopicMapIndexer(dir.getMap().getId());
//                    if (w_indx != null) {
//                        try {
//                            String type = f.getName();
//                            WBResourceURL urllineA = paramsRequest.getRenderUrl();
//                            urllineA.setCallMethod(urllineA.Call_DIRECT);
//                            String url = "" + urllineA + "/" + type + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + Long.toString(id) + "&repinline=true";
//
//                            w_indx.indexFile(f, title, url, dir, resource.getId());
//                        } catch (Exception ex) {
//                            AFUtils.log(ex, "Error while trying to add a file to index.", true);
//                        }
//                    }
//                }

//                WBUtils.getInstance().writeFileToWorkPath(resource.getResourceWorkPath()+"/"+file_db, new ByteArrayInputStream(bcont), user.getId());

                PreparedStatement ps = con.prepareStatement("update resrepository set rep_lastVersion=?,rep_status=?,rep_emailCheckOut=? where rep_docId=? and idtm=?");
                ps.setInt(1, version);
                ps.setInt(2, 0);
                ps.setString(3, user.getId());
                ps.setLong(4, id);
                ps.setString(5, dir.getWebSiteId());
                int num_reg = ps.executeUpdate();
                ps.close();
                PreparedStatement ps2 = con.prepareStatement("insert into "
                        + "resrepositoryversions(rep_docId,rep_fileVersion,idtm,rep_email,rep_fileName,rep_fileSize,rep_fileDate,rep_comment,rep_create,rep_fileType) "
                        + "values(?,?,?,?,?,?,?,?,?,?)");
                ps2.setLong(1, id);
                ps2.setInt(2, version);
                ps2.setString(3, dir.getWebSiteId());
                ps2.setString(4, user.getId());
                ps2.setString(5, filename);
                ps2.setInt(6, bcont.length);
                ps2.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                ps2.setString(8, fup.getValue("repfdescription"));
                ps2.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
                ps2.setString(10, fup.getContentType("repfdoc"));
                num_reg = ps2.executeUpdate();
                ps2.close();
                PreparedStatement psdata = con.prepareStatement("select * from resrepository where rep_docId=? and idtm=?");
                psdata.setLong(1, id);
                psdata.setString(2, dir.getWebSiteId());
                ResultSet rsdata = psdata.executeQuery();
                if (rsdata.next()) {
                    title = rsdata.getString("rep_title");
                    String description = rsdata.getString("rep_description");
                    String comment = fup.getValue("repfdescription");

                    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new java.util.Locale("es"));
                    String fileDate = df.format(new Date(System.currentTimeMillis()));

                    String action = "update";
                    notification.sendNotification(user, resource, id, title, description, filename, comment, fileDate, version, action, dir, paramsRequest, request);
                    saveLog("checkin", user, id, dir, "Description", 1);
                }
                rsdata.close();
                psdata.close();
            } catch (SQLException e) {
                AFUtils.log(e, "Error in newversion", true);
                return;
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        AFUtils.log(e, "Error in newversion", true);
                    }
                }
            }
        } catch (Exception e) {
            AFUtils.log(e, "Error in newversion", true);
        }
    }

    /** Creates a new version form of file
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param dir A topic object that represent a directory
     * @param resource A Resource object
     * @param nivel The user level into the repository
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An Application Framework exception
     * @return the form to add comments and add new version of file
     */
    public String checkin(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource, int nivel, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer();
        if (nivel < 2) {
            return "";
        }

        try {
            long id = Long.parseLong(request.getParameter("repfiddoc"));
            Connection con = null;
            try {
                con = WBUtils.getDBConnection();
                boolean cancheckin = false;
                PreparedStatement psuser = con.prepareStatement("select rep_emailCheckOut from resrepository where rep_docId=? and idtm=?");
                psuser.setLong(1, id);
                psuser.setString(2, dir.getWebSiteId());
                ResultSet rsuser = psuser.executeQuery();
                if (rsuser.next()) {
                    if (user.getId() != null) {
                        if (rsuser.getString("rep_emailCheckOut").equals(user.getId())) {
                            cancheckin = true;
                        }
                    }
                }
                rsuser.close();
                psuser.close();
                if (!cancheckin) {
                    return "";
                }
                PreparedStatement psstatus = con.prepareStatement("select rep_status from resrepository where rep_docId=? and idtm=?");
                psstatus.setLong(1, id);
                psstatus.setString(2, dir.getWebSiteId());
                ResultSet rsstatus = psstatus.executeQuery();
                boolean mod = false;
                if (rsstatus.next()) {
                    if (rsstatus.getInt("rep_status") == 1) {
                        mod = true;
                    }
                }
                rsstatus.close();
                psstatus.close();
                if (mod) {
                    String title = "";
                    PreparedStatement ps = con.prepareStatement("select rep_title from resrepository where rep_docId=? and idtm=?");
                    ps.setLong(1, id);
                    ps.setString(2, dir.getWebSiteId());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        title = rs.getString("rep_title");
                    }
                    rs.close();
                    ps.close();

                    String fileName = "";
                    PreparedStatement psFileName = con.prepareStatement("select rep_fileName from resrepositoryversions where rep_docId=? and rep_fileVersion=? and idtm=?");
                    psFileName.setLong(1, id);
                    psFileName.setInt(2, 1);
                    psFileName.setString(3, dir.getWebSiteId());
                    ResultSet rsFileName = psFileName.executeQuery();
                    if (rsFileName.next()) {
                        fileName = rsFileName.getString("rep_fileName");
                    }
                    rsFileName.close();
                    psFileName.close();

                    ret.append("<form name='frmnewdoc' method='POST' enctype='multipart/form-data'>");
                    ret.append("<input type='hidden' name='repfop' value='mod'>");
                    ret.append("<input type='hidden' name='repfiddoc' value='" + id + "'>");

                    String tp = dir.getId();
                    ret.append("<input type='hidden' name='reptp' value='" + tp + "'>");

                    ret.append("<table width='100%'  border='0' cellspacing='0' cellpadding='1'>");
                    ret.append("<TR>");
                    ret.append("<TD>&nbsp;&nbsp;&nbsp;&nbsp; <B><FONT face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgEditFileStatus") + "...</FONT></B></TD>");
                    ret.append("</TR>");
                    ret.append("<TR>");

                    ret.append("<TD bgcolor='#999933'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'><IMG src='" + path + "file.gif' width='20' height='14'>" + title + "</FONT></TD>");
                    ret.append("</TR>");

                    ret.append("<TR>");
                    ret.append("<TD align='center'>");
                    ret.append("<FONT face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgComments") + "</FONT><BR>");
                    ret.append("<TEXTAREA name='repfdescription' cols='50' rows='3' onKeyDown='textCounter(this.form.repfdescription,255);' onKeyUp='textCounter(this.form.repfdescription,255);'></TEXTAREA>");
                    ret.append("</TD>");
                    ret.append("</TR>");

                    ret.append("<TR>");
                    ret.append("<TD align='center'>");
                    ret.append("<INPUT type='file' name='repfdoc'>");
                    ret.append("<INPUT type='hidden' name='repfdocOri' value=\"" + fileName + "\">");
                    ret.append("</TD>");
                    ret.append("</TR>");
                    ret.append("<TR>");
                    ret.append("<TD><IMG src='" + path + "line.gif' width='100%' height='5'></TD>");
                    ret.append("</TR>");
                    ret.append("<TR align='center'>");
                    ret.append("<TD>");
                    ret.append("<INPUT type='button'  name='Submit3' value='" + paramsRequest.getLocaleString("msgBTNSave") + "' onclick='javascript:valida();'>");
                    ret.append("&nbsp;");
                    ret.append("<INPUT type='button'  name='Submit32' value='" + paramsRequest.getLocaleString("msgBTNCancel") + "' onclick='javascript:init();'>");

                    ret.append("</TD>");
                    ret.append("</TR>");
                    ret.append("</table>");
                    ret.append("</form>");

                    ret.append("<script language='JavaScript'>\r\n");
                    ret.append("function textCounter(field,  maxlimit) {\r\n");
                    ret.append("if (field.value.length > maxlimit)\r\n");
                    ret.append("field.value = field.value.substring(0, maxlimit);\r\n");
                    ret.append("}\r\n");
                    ret.append("function submitform()\r\n");
                    ret.append("{\r\n");
                    ret.append("\r\ndocument.frmnewdoc.submit();\r\n");
                    ret.append("}\r\n");
                    ret.append("function valida(){\r\n");
                    ret.append("if(document.frmnewdoc.repfdescription.value==\"\")\r\n");
                    ret.append("{\r\n");
                    ret.append("alert(\"" + paramsRequest.getLocaleString("msgDefineComment") + "\");\r\n");
                    ret.append("return;\r\n");
                    ret.append("}\r\n");
                    ret.append("if(document.frmnewdoc.repfdoc.value==\"\")\r\n");
                    ret.append("{\r\n");
                    ret.append("alert(\"" + paramsRequest.getLocaleString("msgDefineFile") + "\");\r\n");
                    ret.append("return;\r\n");
                    ret.append("}\r\n");



                    ret.append("\r\n var strfile = document.frmnewdoc.repfdoc.value;");
                    ret.append("\r\n var strOri = document.frmnewdoc.repfdocOri.value;");
                    ret.append("if(strfile.indexOf(strOri)==-1)\r\n");
                    ret.append("{\r\n");
                    ret.append("alert(\"" + paramsRequest.getLocaleString("msgAlertIncorrectFile") + ": \"+strOri);\r\n");
                    ret.append("return;\r\n");
                    ret.append("}\r\n");



                    ret.append("submitform();\r\n");
                    ret.append("}\r\n");
                    ret.append("function init(){\r\n");
                    SWBResourceURL url = paramsRequest.getRenderUrl();

                    ret.append("document.location='" + url.toString() + "?reptp=" + dir.getId() + "';\r\n");
                    ret.append("}\r\n");
                    ret.append("</script>\r\n");
                }
            } catch (SQLException e) {
                AFUtils.log(e, "Error in checkin", true);
                return ret.toString();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        AFUtils.log(e, "Error in checkin", true);
                    }
                }
            }
        } catch (Exception e) {
            AFUtils.log(e, "Error in checkin", true);
        }
        return ret.toString();
    }

    /**
     * User's file reservation for modification
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param dir A topic object that represent a directory
     * @param resource A Resource object
     * @param nivel The user level into the repository
     * @throws AFException An Application Framework exception
     */
    public synchronized void checkout(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource, int nivel) throws SWBResourceException, IOException {
        if (nivel < 2) {
            return;
        }

        try {
            long id = Long.parseLong(request.getParameter("repfiddoc"));
            Connection con = null;
            try {
                con = WBUtils.getDBConnection();
                PreparedStatement psstatus = con.prepareStatement("select rep_status from resrepository where rep_docId=? and idtm=?");
                psstatus.setLong(1, id);
                psstatus.setString(2, dir.getWebSiteId());
                ResultSet rsstatus = psstatus.executeQuery();
                boolean mod = false;
                if (rsstatus.next()) {
                    if (rsstatus.getInt("rep_status") == 0) {
                        mod = true;
                    }
                }
                rsstatus.close();
                psstatus.close();
                if (mod) {
                    PreparedStatement ps = con.prepareStatement("update resrepository set rep_status=?,rep_emailCheckOut=? where rep_docId=? and idtm=?");
                    ps.setInt(1, 1);
                    ps.setString(2, user.getId());
                    ps.setLong(3, id);
                    ps.setString(4, dir.getWebSiteId());
                    int num_reg = ps.executeUpdate();
                    ps.close();

                    saveLog("checkout", user, id, dir, "Description", 1);
                }
            } catch (SQLException e) {
                AFUtils.log(e, "Error on checkout", true);
                return;
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        AFUtils.log(e, "Error in checkout", true);
                    }
                }
            }
        } catch (Exception e) {
            AFUtils.log(e, "Error in checkout", true);
        }
    }

    /**
     * Eliminates the selected file from repository
     * @param fup File upload object
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param dir A topic object that represent a directory
     * @param resource A Resource object
     * @param nivel The user level into the repository
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An exception of type AF (Applicatioon Framework)
     */
    public synchronized void delete(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource, int nivel, SWBParamRequest paramsRequest, FileUpload fup) throws  SWBResourceException, IOException {

        if (nivel == 0) {
            return;
        }

        try {
            long id = Long.parseLong(request.getParameter("repfiddoc"));
            Connection con = null;
            try {
                con = WBUtils.getDBConnection();
                if (nivel >= 2) {
                    boolean candelete = false;
                    if (nivel == 3) {
                        candelete = true;
                    } else {
                        PreparedStatement psuser = con.prepareStatement("select rep_email from resrepository where rep_docId=? and idtm=?");
                        psuser.setLong(1, id);
                        psuser.setString(2, dir.getWebSiteId());
                        ResultSet rsuser = psuser.executeQuery();
                        if (rsuser.next()) {
                            if (user.getId() != null) {
                                if (rsuser.getString("rep_email").equals(user.getId())) {
                                    candelete = true;
                                }
                            }
                        }
                    }
                    if (!candelete) {
                        return;
                    }
                }
                PreparedStatement psdata = con.prepareStatement("select * from resrepository where rep_docId=? and idtm=?");
                psdata.setLong(1, id);
                psdata.setString(2, dir.getWebSiteId());
                ResultSet rsdata = psdata.executeQuery();
                if (rsdata.next()) {
                    String title = rsdata.getString("rep_title");
                    String description = rsdata.getString("rep_description");
                    String comment = fup.getValue("repfdescription");

                    String fileDate = "";

                    String action = "remove";
                    int version = 0;
                    String filename = "";
                    try {
                        notification.sendNotification(user, resource, id, title, description, filename, comment, fileDate, version, action, dir, paramsRequest, request);
                    } catch (Exception e) {
                    }

                    saveLog("delete", user, id, dir, "Description", 1);
                }
                PreparedStatement ps = con.prepareStatement("update resrepository set rep_deleted = 1 where rep_docId=? and idtm=?");
                ps.setLong(1, id);
                ps.setString(2, dir.getWebSiteId());
                int count = ps.executeUpdate();
                ps.close();

                return;
            } catch (SQLException e) {
                AFUtils.log(e, "Error on delete", true);
                return;
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        AFUtils.log(e, "Error on delete", true);
                    }
                }
            }
        } catch (Exception e) {
            AFUtils.log(e, "Error on delete", true);
        }
    }

    /** Shows the file information
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param dir A topic object that represent a directory
     * @param resource A Resource object
     * @param nivel The user level into the repository
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An exception of type AF (Applicatioon Framework)
     * @return The file information
     */
    public String info(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource, int nivel, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer();
        ArrayList subcriptions = new ArrayList();
        User usr = null;
        Connection con = null;
        PreparedStatement ps = null;
        boolean canupdate = false;
        String s_author = null;
        String s_message = null;
        String tp = null;
        String date = null;
        long id = 0;
        int i_log = 0;

        SWBResourceURL url = paramsRequest.getRenderUrl();
        url.setCallMethod(url.Call_CONTENT);

        if (user.getId() != null) {
            subcriptions = notification.getSubscriptions(user, dir);
        }
        // Exit from method
        if (nivel == 0) {
            return "";
        }

        if (user.isSigned()) {
            i_log = 1;
        }
        //i_log = 1;
        try {
            id = Long.parseLong(request.getParameter("repfiddoc"));

            String topicid = dir.getId();

            tp = dir.getId();
            s_message = paramsRequest.getLocaleString("msgMustBeLogged");
            ret.append("\n<script language='JavaScript'>");
            if (request.getParameter("updated") != null && request.getParameter("updated").equals("1")) {
                ret.append("\n  alert('" + paramsRequest.getLocaleString("msgFileInfoUpdated") + ".');");
            }
            ret.append("\n  function doShowHistory(idoc){");
            ret.append("\n          window.document.frmparameter.action= '" + url.toString() + "';");
            ret.append("\n      window.document.frmparameter.repfop.value = 'history';");
            ret.append("\n      window.document.frmparameter.repfiddoc.value = idoc;");
            ret.append("\n      window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n      window.document.frmparameter.submit();");
            ret.append("\n  }");

            ret.append("\n  function doCheckout(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            SWBResourceURL urlOUT = paramsRequest.getRenderUrl();

            ret.append("\n          window.document.frmparameter.action= '" + urlOUT.toString() + "';");
            ret.append("\n          window.document.frmparameter.repfop.value = 'checkout';");
            ret.append("\n          window.document.frmparameter.repfiddoc.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doCheckin(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            ret.append("\n          window.document.frmparameter.action= '" + url.toString() + "';");
            ret.append("\n          window.document.frmparameter.repfop.value = 'checkin';");
            ret.append("\n          window.document.frmparameter.repfiddoc.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");

            ret.append("\n  function doUndocheckout(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            ret.append("\n          window.document.frmparameter.action= '" + url.toString() + "';");
            ret.append("\n          window.document.frmparameter.repfop.value = 'undocheckuout';");
            ret.append("\n          window.document.frmparameter.repfiddoc.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");

            ret.append("\n  function doDelete(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            ret.append("\n          if(confirm('" + paramsRequest.getLocaleString("msgSureDelete") + "?')){");
            SWBResourceURL urlDel = paramsRequest.getRenderUrl();
            ret.append("\n              window.document.frmparameter.action = '" + urlDel.toString() + "';");
            ret.append("\n              window.document.frmparameter.repfop.value = 'delete';");
            ret.append("\n              window.document.frmparameter.repfiddoc.value = idoc;");
            ret.append("\n              window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n              window.document.frmparameter.submit();");
            ret.append("\n          }");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doView(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            SWBResourceURL urlView = paramsRequest.getRenderUrl();
            urlView.setCallMethod(urlView.Call_DIRECT);
            ret.append("\n          window.document.frmparameter.action = '" + urlView.toString() + "';");
            ret.append("\n          window.document.frmparameter.repfop.value = 'view';");
            ret.append("\n          window.document.frmparameter.repfiddoc.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");

            ret.append("\n  function doViewInLine(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");

            SWBResourceURL urlPreView = paramsRequest.getRenderUrl();
            urlPreView.setCallMethod(paramsRequest.Call_DIRECT);

            ret.append("\n          window.open(\"" + urlPreView.toString() + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=\" + idoc + \"&repinline=true \",\"\",'width=650, height=520');    ");
            ret.append("\n      }");
            ret.append("\n  }");

            ret.append("\n  function doSuscribeDoc(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            SWBResourceURL urlSusDoc = paramsRequest.getRenderUrl();
            ret.append("\n          window.document.frmparameter.action = '" + urlSusDoc.toString() + "';");
            ret.append("\n          window.document.frmparameter.repacc.value = 'addtodoc';");
            ret.append("\n          window.document.frmparameter.repobj.value = 'Notification';");
            ret.append("\n          window.document.frmparameter.repdocid.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doUnsuscribeDoc(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            ret.append("\n          window.document.frmparameter.action = '" + urlSusDoc.toString() + "';");
            ret.append("\n          window.document.frmparameter.repacc.value = 'rmtodoc';");
            ret.append("\n          window.document.frmparameter.repobj.value = 'Notification';");
            ret.append("\n          window.document.frmparameter.repdocid.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doMoveDocDir(ivar,idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            SWBResourceURL urlMove = paramsRequest.getRenderUrl();
            urlMove.setCallMethod(urlMove.Call_DIRECT);
            urlMove.setParameter("repfiddoc", "movedoctodir");
            urlMove.setParameter("reptp", dir.getId());
            urlMove.setParameter("reptp_original", dir.getId());
            urlMove.setParameter("repobj", "MoveDoc");
            ret.append("\n          var url1 = '" + urlMove.toString() + "&repiddoc='+idoc;");
            ret.append("\n          window.open(url1,'MoveDir','width=400, height=350, scrollbars, resizable, alwaysRaised');");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n</script>");

            ret.append("\n<form name=\"frmparameter\" method=\"post\" action=\"" + url.toString() + "\" >");
            ret.append("\n<input type=\"hidden\" name=\"repfop\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repacc\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"reptp\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repobj\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repdocid\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repfiddoc\" value=\"\">");
            ret.append("\n</form>");

            if (user.isSigned()) {
                ret.append("<form name='frmnewdoc' method='GET' action='" + url.toString() + "'>");
            }
            ret.append("<input type='hidden' name='repfop' value='updatetitle'>");
            ret.append("<input type='hidden' name='repfiddoc' value='" + id + "'>");
            ret.append("<input type='hidden' name='reptp' value='" + tp + "'>");

            ret.append("<TABLE width='100%'  border='0' cellspacing='0' cellpadding='1'>");
            ret.append("<TR>");
            ret.append("<TD colspan='10'><IMG src='" + path + "openfolder.gif' width='20' height='20'> <B><FONT face='Verdana, Arial, Helvetica, sans-serif' size=\"2\">" + dir.getDisplayName() + "</FONT></B></TD>");
            ret.append("</TR>");
            ret.append("<TR>");
            ret.append("<TD colspan='10'><IMG src='" + path + "line.gif' width='100%' height='5'></TD>");
            ret.append("</TR>");

            con = WBUtils.getDBConnection();
            ps = con.prepareStatement("select rep_docId, resId, rep_email, rep_title, rep_description, rep_lastVersion, rep_status, rep_emailCheckOut, rep_xml from resrepository where rep_docId=? and idtm=?");
            ps.setLong(1, id);
            ps.setString(2, dir.getWebSiteId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                long repdocid = rs.getLong("rep_docId");
                long represid = rs.getLong("resId");
                s_author = rs.getString("rep_email");
                String reptitle = rs.getString("rep_title");
                String repdescription = rs.getString("rep_description");
                int replastversion = rs.getInt("rep_lastVersion");
                int repstatus = rs.getInt("rep_status");
                String repemailCOut = rs.getString("rep_emailCheckOut");
                
                String repxml =rs.getAsciiStream("rep_xml")!=null?SWBUtils.IO.readInputStream(rs.getAsciiStream("rep_xml")):null;
                //String repxml = com.infotec.appfw.util.AFUtils.getInstance().readInputStream(rs.getAsciiStream("rep_xml")); //rs.getString("rep_xml");

                ret.append("<TR>");
                ret.append("<TD bgcolor='#FFFFFF' width='150'><FONT color='#000000' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgTitle") + ":</FONT></TD>");
                ret.append("</Td>");
                ret.append("<Td>");
                if (nivel == 3) {
                    canupdate = true;
                } else {
                    if (user.getId() != null) {
                        if (s_author != null) {
                            if (s_author.equals(user.getId())) {
                                canupdate = true;
                            }
                        }
                    }
                }

                if (canupdate) {
                    ret.append("<input  type='text' maxlength='99' name='repftitle' value='" + reptitle + "'>");
                } else {
                    ret.append("<FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + reptitle + "</FONT>");
                }
                ret.append("</Td>");
                ret.append("</TR>");

                ret.append("<TR>");
                ret.append("<TD bgcolor='#FFFFFF' width='150'><FONT color='#000000' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgDescription") + ":</FONT></TD>");
                ret.append("</Td>");
                ret.append("<Td>");
                if (canupdate) {
                    ret.append("<TEXTAREA name='repfdescription' cols='50' rows='3' onKeyDown='textCounter(this.form.repfdescription,255);' onKeyUp='textCounter(this.form.repfdescription,255);'>" + repdescription + "</TEXTAREA>");
                } else {
                    ret.append("<FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + repdescription + "</FONT>");
                }
                ret.append("</Td>");
                ret.append("</TR>");

                PreparedStatement ps2 = con.prepareStatement("select * from resrepositoryversions where rep_docId=? and rep_fileVersion=? and idtm=?");
                ps2.setLong(1, repdocid);
                ps2.setInt(2, replastversion);
                ps2.setString(3, dir.getWebSiteId());
                ResultSet rsversions = ps2.executeQuery();
                if (rsversions.next()) {
                    Timestamp repcreate = rsversions.getTimestamp("rep_create");
                    date = repcreate.toString();

                    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, Locale.getDefault());
                    date = df.format(new Date(repcreate.getTime()));
                    ret.append("<TR>");
                    ret.append("<TD bgcolor='#FFFFFF' width='150'><FONT color='#000000' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgLastUpdate") + ":</FONT></TD>");
                    ret.append("</Td>");
                    ret.append("<Td>");
                    ret.append("<FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + date + "</FONT>");
                    ret.append("</Td>");
                    ret.append("</TR>");

                    ret.append("<TR>");
                    ret.append("<TD bgcolor='#FFFFFF' width='150'><FONT color='#000000' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgAuthor") + ":</FONT></TD>");
                    ret.append("</Td>");
                    ret.append("<Td>");
                    if (s_author == null) {
                        s_author = "&nbsp;";
                    } else {
                        try {
                            //usr = DBUser.getInstance().getUserById(s_author);
                            usr = dir.getWebSite().getUserRepository().getUser(s_author);

                            if (usr.getFirstName() != null) {
                                s_author = usr.getFirstName();
                            }
                            if (usr.getLastName() != null) {
                                s_author = s_author + " " + usr.getLastName();
                            }
                            s_author = s_author + " &lt;" + usr.getEmail() + "&gt;";
                        } catch (Exception e) {

                            s_author = "&nbsp;";
                            AFUtils.log(e, "Error on method info class RepositoryFile trying to create new user docId" + ": " + id, true);
                        }
                    }
                    ret.append("<FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + s_author + "</FONT>");
                    ret.append("</Td>");
                    ret.append("</TR>");

                    ret.append("<TR>");

                    ret.append("<TD bgcolor='#FFFFFF' width='150'><FONT color='#000000' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgCurrentVersion") + ":</FONT></TD>");
                    ret.append("</Td>");
                    ret.append("<Td>");

                    ret.append("<FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'><a href=\"javascript: doShowHistory(" + repdocid + ");\">" + replastversion + "</a></FONT>");
                    ret.append("</Td>");
                    ret.append("</TR>");
                    //////////////////////////////////////
                    ret.append("<TR>");

                    ret.append("<TD bgcolor='#FFFFFF' width='150'><FONT color='#000000' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgFileName") + ":</FONT></TD>");
                    ret.append("<Td>");
                    ret.append("<FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + rsversions.getString("rep_fileName") + "</FONT>");
                    ret.append("</Td>");
                    ret.append("</TR>");

                    /////////////////////////////////////


                    ret.append("<TR>");

                    ret.append("<TD bgcolor='#FFFFFF' width='150'><FONT color='#000000' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgFileType") + ":</FONT></TD>");
                    ret.append("<Td>");
                    ret.append("<FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + getFileType(rsversions.getString("rep_fileName")) + "</FONT>");
                    ret.append("</Td>");
                    ret.append("</TR>");
                    ret.append("<TR>");

                    ret.append("<TD bgcolor='#FFFFFF' width='150'><FONT color='#000000' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgSize") + ":</FONT></TD>");
                    ret.append("</Td>");
                    ret.append("<Td>");
                    int size = rsversions.getInt("rep_fileSize") / 1024;
                    if (size == 0) {
                        size = 1;
                    }
                    ret.append("<FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + size + "k</FONT>");
                    ret.append("</Td>");
                    ret.append("</TR>");


                    if (repstatus == 1 && repemailCOut != null) {
                        ret.append("<TR>");
                        ret.append("<TD bgcolor='#FFFFFF' width='150'><FONT color='#000000' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgReservedBy") + ":</FONT></TD>");
                        ret.append("</Td>");
                        ret.append("<Td>");
                        String usercheckout = repemailCOut;

                        if (DBUser.getInstance().getUserById(usercheckout) != null) {
                            usercheckout = DBUser.getInstance().getUserById(usercheckout).getFirstName() + " " + DBUser.getInstance().getUserById(usercheckout).getLastName();
                        }

                        PreparedStatement ps3 = con.prepareStatement("select rep_dateaction from resrepositorylog where rep_docId=? and rep_email=? and rep_action=? and rep_topicmapid=? order by rep_dateaction desc");
                        ps3.setLong(1, id);
                        ps3.setString(2, repemailCOut);
                        ps3.setString(3, "checkout");
                        ps3.setString(4, dir.getWebSiteId());
                        ResultSet rs3 = ps3.executeQuery();

                        String checkOutDate = "";
                        if (rs3.next()) {
                            String mydate = rs3.getTimestamp("rep_dateaction").toString();

                            DateFormat df2 = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, Locale.getDefault());
                            mydate = df2.format(new Date(rs3.getTimestamp("rep_dateaction").getTime()));
                            checkOutDate = " - " + mydate;
                        }
                        rs3.close();
                        ps3.close();

                        ret.append("<FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + usercheckout + checkOutDate + "</FONT>");
                        ret.append("</Td>");
                        ret.append("</TR>");
                    }
                    ret.append("<TR>");
                    ret.append("<TD colspan='2' align='center'>");

                    if (nivel >= 2 && user.isSigned()) {

                        if (repstatus == 0) {
                            ret.append("<A href=\"javascript: doCheckout(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "out.gif' height='14' width='25' alt='" + paramsRequest.getLocaleString("msgCOut") + "' border='0'>");
                        } else {
                            boolean cancheckin = false;
                            PreparedStatement psuser = con.prepareStatement("select rep_emailCheckOut from resrepository where rep_docId=? and idtm=?");
                            psuser.setLong(1, repdocid);
                            psuser.setString(2, dir.getWebSiteId());
                            ResultSet rsuser = psuser.executeQuery();
                            if (rsuser.next()) {
                                if (user != null) {
                                    String repusercout = rsuser.getString("rep_emailCheckOut");
                                    if (repusercout != null && repusercout.equals(user.getId())) {
                                        cancheckin = true;
                                    }
                                }
                            }

                            rsuser.close();
                            psuser.close();

                            rsuser = null;
                            psuser = null;

                            if (cancheckin) {
                                ret.append("<A href=\"javascript: doCheckin(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "in.gif' alt='" + paramsRequest.getLocaleString("msgCIn") + "' height='14' width='25' border='0' /></A>");
                                ret.append("&nbsp;<A href=\"javascript: doUndocheckout(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "undo.gif' alt='" + paramsRequest.getLocaleString("msgUndoCOut") + "' height='14' width='45' border='0' /></A>");
                            } else {
                                if (nivel == 3) {
                                    ret.append("<a href=\"javascript: doUndocheckout(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "undo.gif' alt='" + paramsRequest.getLocaleString("msgUndoCOut") + "' height='14' width='45' border='0'></a>");
                                } else {
                                    ret.append("<IMG src='" + path + paramsRequest.getLocaleString("img_Reserved") + ".gif' alt='" + paramsRequest.getLocaleString("msgReserved") + "' border='0'>");
                                }
                            }
                        }
                    }
                    if (user.getId() != null && user.isSigned()) {
                        if (!subcriptions.contains(new Long(0))) { //dir

                            if (!subcriptions.contains(new Long(repdocid))) {
                                ret.append("<a href=\"javascript: doSuscribeDoc(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "suscribe.gif' alt='" + paramsRequest.getLocaleString("msgSuscribe") + "' border='0'></a>");
                            } else {
                                ret.append("<a href=\"javascript: doUnsuscribeDoc(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "unsuscribe.gif' alt='" + paramsRequest.getLocaleString("msgUnSuscribe") + "' border='0'></a>");
                            }
                        }
                    }

                    boolean candelete = false;
                    if (nivel == 3) {
                        candelete = true;
                    } else {
                        PreparedStatement psuser = con.prepareStatement("select rep_email from resrepository where rep_docId=? and idtm=?");
                        psuser.setLong(1, repdocid);
                        psuser.setString(2, dir.getWebSiteId());
                        ResultSet rsuser = psuser.executeQuery();
                        if (rsuser.next()) {
                            if (user.getId() != null) {
                                String repemail = rsuser.getString("rep_email");
                                if (repemail.equals(user.getId())) {
                                    candelete = true;
                                }
                            }
                        }
                    }
                    if (candelete && user.isSigned()) {
                        if (repstatus == 0) {
                            if (resource.getAttribute("showdirectory", "true").equals("true")) {
                                ret.append("<a href=\"javascript: doMoveDocDir(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "folder.gif' alt='" + paramsRequest.getLocaleString("msgALTMove") + "' border='0'></a>");
                            }
                            ret.append("<a href=\"javascript: doDelete(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "delete.gif' alt='" + paramsRequest.getLocaleString("msgAltDelete") + "' border='0'></a>");
                        }
                    }
                    if (nivel >= 1) {
                        boolean inline = false;
                        if (rsversions.getString("rep_fileType") != null) {
                            for (int i = 0; i < values.length; i++) {
                                if (values[i].equals("*/*")) {
                                    inline = true;
                                    break;
                                } else {
                                    if (values[i].equalsIgnoreCase(rsversions.getString("rep_fileType"))) {
                                        inline = true;
                                        break;
                                    }
                                }
                            }
                        }

                        int tmp = i_log;
                        ;
                        if (supportGuestUser() == 1) {
                            tmp = 1;
                        }
                        if (inline) {
                            ret.append("<a href=\"javascript: doViewInLine(" + tmp + "," + repdocid + ");\"><IMG src='" + path + "preview.gif' alt='" + paramsRequest.getLocaleString("msgAltPreview") + "' border='0'></a>");
                        } else {
                            ret.append("<a href=\"javascript: doView(" + tmp + "," + repdocid + ");\"><IMG src='" + path + "preview.gif' alt='" + paramsRequest.getLocaleString("msgAltPreview") + "' border='0'></a>");
                        }
                    }
                    ret.append("</TD>");
                    ret.append("</TR>");

                    ret.append("<TR>");
                    ret.append("<Td colspan='2'>");
                    ret.append("<img width=\"100%\" src=\"" + path + "line.gif\" height=5>");
                    ret.append("</Td>");
                    ret.append("</TR>");
                    ret.append("<TR>");
                    ret.append("<TD colspan='2' align='center'>");
                    if (canupdate && user.isSigned()) {
                        ret.append("<input  type='button' name='s' value='" + paramsRequest.getLocaleString("msgBTNSubmit") + "' onClick='javascript:valida(" + i_log + ")'>&nbsp;&nbsp;&nbsp;");
                    }
                    ret.append("<input  type='button' name='n' value='" + paramsRequest.getLocaleString("msgBTNViewAllFiles") + "' onClick='javascript:init()'>&nbsp;&nbsp;&nbsp;");
                    if (canupdate && user.isSigned()) {
                        ret.append("<input  type='button' name='c' value='" + paramsRequest.getLocaleString("msgBTNCancel") + "' onClick='javascript:init()'>");
                    }
                    ret.append("</TD>");
                    ret.append("</TR>");
                }
            }
            ps.close();

            ret.append("</table>");
            if (user.isSigned()) {
                ret.append("</form>");
            }
            ret.append("<script language='JavaScript'>\r\n");
            ret.append("function textCounter(field,  maxlimit) {\r\n");
            ret.append("if (field.value.length > maxlimit)\r\n");
            ret.append("field.value = field.value.substring(0, maxlimit);\r\n");
            ret.append("}\r\n");
            ret.append("function valida(ivar){\r\n");
            ret.append("");
            ret.append("    if(ivar==0){");
            ret.append("        alert('" + s_message + "');");
            ret.append("        return;");
            ret.append("    }");
            ret.append("    else{");


            ret.append("if(document.frmnewdoc.repftitle.value==\"\")\r\n");
            ret.append("{\r\n");
            ret.append("alert(\"" + paramsRequest.getLocaleString("msgAlertDefineTitle") + "\");\r\n");
            ret.append("return;\r\n");
            ret.append("}\r\n");
            ret.append("if(document.frmnewdoc.repfdescription.value==\"\")\r\n");
            ret.append("{\r\n");
            ret.append("alert(\"" + paramsRequest.getLocaleString("msgAlertDefineDescription") + "\");\r\n");
            ret.append("return;\r\n");
            ret.append("}\r\n");

            ret.append("document.frmnewdoc.submit();\r\n");
            ret.append("    }");
            ret.append("}\r\n");

            ret.append("function init(){\r\n");

            ret.append("document.location='" + url.toString() + "?reptp=" + dir.getId() + "';\r\n");
            ret.append("}\r\n");
            ret.append("</script>\r\n");


        } catch (SQLException e) {
            AFUtils.log(e, "Error on info", true);
            return ret.toString();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    AFUtils.log(e, "Error on info", true);
                }
            }
        }
        return ret.toString();

    }

    /**
     * Shows a list of existing files in the directory
     * @param request the input parameters
     * @param response the answerd to the request
     * @param user A WBUser object
     * @param topic A topic object
     * @param hashMap A list of arguments
     * @param dir Topic object that represents a folder
     * @param resource The Resource object
     * @param nivel The user level
     * @param paramsRequest A list of objects (topic, user, action, ...)
     * @throws AFException An Application Framework exception
     * @return A list of existing files
     */
    public String showfiles(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource, int nivel, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

        ArrayList subcriptions = new ArrayList();
        StringBuffer ret = new StringBuffer();
        Connection con = null;
        PreparedStatement ps = null;
        int i_log = 0;
        int i_tot = 0;
        String s_message = null;
        String s_sql = null;

        if (user.getId() != null) {
            subcriptions = notification.getSubscriptions(user, dir);
        }

        if (nivel == 0) {
            return "";
        }

        if (user.isSigned()) {
            i_log = 1;
        }
        //i_log = 1;
        try {
            String topicid = dir!=null?dir.getId():"";
            con = WBUtils.getDBConnection();
            s_sql = "select * from resrepository where idtm=? and topic=? and resId=? and rep_deleted = 0";
            // Display results sorted by title or date
            if (request.getParameter("repordid") != null) {
                if (request.getParameter("repordid").equals("title")) {
                    s_sql = s_sql + " order by rep_title";
                } else {
                    s_sql = s_sql + " order by rep_create";
                }
            }
            ps = con.prepareStatement(s_sql);
            ps.setString(1, dir.getWebSiteId());
            ps.setString(2, topicid);
            ps.setString(3, resource.getId());
            ResultSet rs = ps.executeQuery();

            String tp = dir.getId();
            s_message = paramsRequest.getLocaleString("msgMustBeLogged");
            String foldername = dir.getDisplayName();
            SWBResourceURL url = paramsRequest.getRenderUrl();
            ret.append("\n<script language='JavaScript'>");
            ret.append("\n  function doSuscribe(ivar){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            ret.append("\n          window.document.frmparameter.action= '" + url.toString() + "';");
            ret.append("\n          window.document.frmparameter.repobj.value = 'Notification';");
            ret.append("\n          window.document.frmparameter.repacc.value = 'addtodir';");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doUnsuscribe(ivar){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            ret.append("\n          window.document.frmparameter.action= '" + url.toString() + "';");
            ret.append("\n          window.document.frmparameter.repobj.value = 'Notification';");
            ret.append("\n          window.document.frmparameter.repacc.value = 'rmtodir';");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doAddFile(ivar){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            SWBResourceURL urlAdd = paramsRequest.getRenderUrl();
            ret.append("\n          window.document.frmparameter.action= '" + urlAdd.toString() + "';");
            ret.append("\n          window.document.frmparameter.repfop.value = 'add';");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doCheckout(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            SWBResourceURL urlOUT = paramsRequest.getRenderUrl();
            ret.append("\n          window.document.frmparameter.action= '" + urlOUT.toString() + "';");
            ret.append("\n          window.document.frmparameter.repfop.value = 'checkout';");
            ret.append("\n          window.document.frmparameter.repfiddoc.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doCheckin(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            ret.append("\n          window.document.frmparameter.action= '" + url.toString() + "';");
            ret.append("\n          window.document.frmparameter.repfop.value = 'checkin';");
            ret.append("\n          window.document.frmparameter.repfiddoc.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doUndocheckout(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            ret.append("\n          window.document.frmparameter.action= '" + url.toString() + "';");
            ret.append("\n          window.document.frmparameter.repfop.value = 'undocheckuout';");
            ret.append("\n          window.document.frmparameter.repfiddoc.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doDelete(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            ret.append("\n          if(confirm('" + paramsRequest.getLocaleString("msgSureDelete") + "?')){");
            SWBResourceURL urlDel = paramsRequest.getRenderUrl();
            ret.append("\n              window.document.frmparameter.action = '" + urlDel.toString() + "';");
            ret.append("\n              window.document.frmparameter.repfop.value = 'delete';");
            ret.append("\n              window.document.frmparameter.repfiddoc.value = idoc;");
            ret.append("\n              window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n              window.document.frmparameter.submit();");
            ret.append("\n          }");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doView(ivar, idoc,iname){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            SWBResourceURL urlView = paramsRequest.getRenderUrl();
            urlView.setCallMethod(urlView.Call_DIRECT);
            ret.append("\n          window.document.frmparameter.action = '" + urlView.toString() + "/'+iname;");
            ret.append("\n          window.document.frmparameter.repfop.value = 'view';");
            ret.append("\n          window.document.frmparameter.repfiddoc.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n      return;");
            ret.append("\n  }");
            ret.append("\n  function doSuscribeDoc(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            SWBResourceURL urlSusDoc = paramsRequest.getRenderUrl();
            ret.append("\n          window.document.frmparameter.action = '" + urlSusDoc.toString() + "';");
            ret.append("\n          window.document.frmparameter.repacc.value = 'addtodoc';");
            ret.append("\n          window.document.frmparameter.repobj.value = 'Notification';");
            ret.append("\n          window.document.frmparameter.repdocid.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doUnsuscribeDoc(ivar, idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            ret.append("\n          window.document.frmparameter.action = '" + urlSusDoc.toString() + "';");
            ret.append("\n          window.document.frmparameter.repacc.value = 'rmtodoc';");
            ret.append("\n          window.document.frmparameter.repobj.value = 'Notification';");
            ret.append("\n          window.document.frmparameter.repdocid.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n  function doInfo(idoc){");
            ret.append("\n          window.document.frmparameter.action= '" + url.toString() + "';");
            ret.append("\n          window.document.frmparameter.repfop.value = 'info';");
            ret.append("\n          window.document.frmparameter.repfiddoc.value = idoc;");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n  }");
            ret.append("\n  function doResOrderTitle(){");
            ret.append("\n          window.document.frmparameter.action= '" + url.toString() + "';");
            ret.append("\n          window.document.frmparameter.repordid.value = 'title';");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n  }");
            ret.append("\n  function doResOrderDate(){");
            ret.append("\n          window.document.frmparameter.action= '" + url.toString() + "';");
            ret.append("\n          window.document.frmparameter.repordid.value = 'date';");
            ret.append("\n          window.document.frmparameter.reptp.value = '" + dir.getId() + "';");
            ret.append("\n          window.document.frmparameter.submit();");
            ret.append("\n  }");
            ret.append("\n  function doMoveDocDir(ivar,idoc){");
            ret.append("\n      if( ivar == 0){");
            ret.append("\n          alert('" + s_message + "');");
            ret.append("\n      }");
            ret.append("\n      else{");
            SWBResourceURL urlMove = paramsRequest.getRenderUrl();
            urlMove.setCallMethod(urlMove.Call_DIRECT);
            urlMove.setParameter("repfiddoc", "movedoctodir");
            urlMove.setParameter("reptp", dir.getId());
            urlMove.setParameter("reptp_original", dir.getId());
            urlMove.setParameter("repobj", "MoveDoc");
            ret.append("\n          var url1 = '" + urlMove.toString() + "&repiddoc='+idoc;");
            //ret.append("\n          alert(url1+'('+ivar+','+idoc+')');");
            ret.append("\n          window.open(url1,'MoveDir','width=400, height=350, scrollbars, resizable, alwaysRaised');");
            ret.append("\n      }");
            ret.append("\n  }");
            ret.append("\n</script>");

            ret.append("\n<form name=\"frmparameter\" method=\"post\" action=\"" + url.toString() + "\">");

            ret.append("\n<input type=\"hidden\" name=\"repfop\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"reptp\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repacc\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repobj\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repfiddoc\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repdocid\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repordid\" value=\"\">");
            ret.append("\n</form>");
            ret.append("\n<TABLE width='100%'  border='0' cellspacing='0' cellpadding='1'>");
            ret.append("\n<TR>");
            ret.append("\n<TD colspan='10'>");
            ret.append("\n<TABLE width='100%'  border='0' cellspacing='0' cellpadding='0'>");
            ret.append("\n<TR>");
            ret.append("\n<TD colspan='10' width='50%'><IMG src='" + path + "openfolder.gif' width='20' height='20'> <B class=\"Estilo6\"><FONT face=\"Verdana, Arial, Helvetica, sans-serif\">" + foldername);
            if (user.getId() != null && user.isSigned()) {
                if (!subcriptions.contains(new Long(0))) {
                    ret.append("&nbsp;&nbsp;&nbsp;<a href=\"javascript: doSuscribe(" + i_log + ");\"><FONT color='#666666' size='1' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgSuscribeDirectory") + "</font></a>");
                } else {
                    ret.append("&nbsp;&nbsp;&nbsp;<a href=\"javascript: doUnsuscribe(" + i_log + ");\"><FONT color='#666666' size='1' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgUnsuscribeDirectory") + "</font></a>");
                }
            }
            ret.append("</FONT></B></TD>");
            if (nivel >= 2 && user.isSigned()) {
                ret.append("\n<TD width='50%' align='right'><A href=\"javascript: doAddFile(" + i_log + ");\"><IMG src='" + path + "add.gif' alt='" + paramsRequest.getLocaleString("msgAltAdd") + "' width='20' height='18' border='0'></A><FONT size='1' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgALTAddFile") + "</FONT> </TD>");
            }
            ret.append("\n</TR>");
            ret.append("\n</TABLE>");
            ret.append("\n</TD>");
            ret.append("\n</TR>");

            ret.append("\n<TR bgcolor='#589942'>");
            ret.append("\n<TD width='20'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'>&nbsp;</FONT></TD>");
            ret.append("\n<TD width='20'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'>&nbsp;</FONT></TD>");
            ret.append("\n<TD width='150'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'><a style=\"text-decoration:none; color:#FFFFFF;\" href=\"javascript: doResOrderTitle();\" >" + paramsRequest.getLocaleString("msgTitle") + "</a></FONT></TD>");
            ret.append("\n<TD width='150' align='center'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'><a style=\"text-decoration:none; color:#FFFFFF;\" href=\"javascript: doResOrderDate();\">" + paramsRequest.getLocaleString("msgDate") + "</a></FONT></TD>");

            if (nivel >= 2) {
                ret.append("\n<TD width='140' align='center'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgCheck") + "</FONT></TD>");
            } else {
                ret.append("\n<td></td>");
            }
            ret.append("\n<TD width='80' align='center'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgAction") + "</FONT></TD>");

            ret.append("\n</TR>");
            ret.append("\n<TR>");
            ret.append("\n<TD colspan='10'><IMG src='" + path + "line.gif' width='100%' height='5'></TD>");
            ret.append("\n</TR>");

            while (rs.next()) {
                long repdocid = rs.getLong("rep_docId");
                String reptitle = rs.getString("rep_title");
                int lastversion = rs.getInt("rep_lastVersion");
                int repstatus = rs.getInt("rep_status");
                i_tot++;
                //System.out.println("Files: "+i_tot+" docid: "+repdocid+" titulo: "+reptitle);
                PreparedStatement ps2 = con.prepareStatement("select * from resrepositoryversions where rep_docId=? and rep_fileVersion=? and idtm=?");
                ps2.setLong(1, repdocid);
                ps2.setInt(2, lastversion);
                ps2.setString(3, dir.getWebSiteId());
                ResultSet rsversions = ps2.executeQuery();
                if (rsversions.next()) {
                    String repfiletype = rsversions.getString("rep_fileType");
                    ret.append("<tr>");

                    ret.append("\n<TD width='20'><a href=\"javascript: doInfo(" + repdocid + ");\">" + "<IMG src='" + path + "info.gif' width='20' height='14' border='0'>" + "</a></TD>"); //paramsRequest.getLocaleString("msgInfo")

                    String file = "ico_default2.gif";
                    String type = "";
                    if (repfiletype != null) {
                        type = rsversions.getString("rep_fileName");
                        file = getFileName(type);
                    }

                    //ret.append("\n<TD width='20'><a href=\"javascript: doView(" + i_log + ","+ repdocid+",'"+type+"');\" ><IMG border=0 src='"+ path +""+ file +"' alt=\""+getFileType(type)+"\"></a></TD>");

                    SWBResourceURL urllineA = paramsRequest.getRenderUrl();
                    urllineA.setCallMethod(urllineA.Call_DIRECT);
                    ret.append("\n<TD width='20'>");//"<a href=\"javascript: doView(" + i_log + ","+ repdocid+",'"+type+"');\" ></a></TD>");
                    if (i_log == 1 || supportGuestUser() == 1) {
                        ret.append("<a target='_new' href='" + urllineA + "/" + type + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + Long.toString(repdocid) + "&repinline=true'><IMG border=0 src='" + path + "" + file + "' alt=\"" + getFileType(type) + "\"></a>");
                    } else {
                        ret.append("<a onclick='javascript:alert(\"Debes estar firmado para poder ver este archivo.\")'><IMG border=0 src='" + path + "" + file + "' alt=\"" + getFileType(type) + "\"></a>");
                    }
                    ret.append("</TD>");


                    ret.append("\n<TD width='150'><FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + reptitle);
                    ret.append("\n</FONT></TD>");

                    Timestamp repcreate = rsversions.getTimestamp("rep_create");
                    String date = repcreate.toString();
                    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new java.util.Locale("es"));
                    date = df.format(new Date(repcreate.getTime()));
                    ret.append("\n<TD width='150'><FONT size='1' face='Verdana, Arial, Helvetica, sans-serif'>" + date + "</FONT></TD>");

                    if (nivel >= 2 && user.isSigned()) {
                        if (repstatus == 0) {
                            ret.append("\n<TD width='140' align=\"center\">");
                            ret.append("<A href=\"javascript: doCheckout(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "out.gif' height='14' width='25' alt='" + paramsRequest.getLocaleString("msgCOut") + "' border='0'></A>");
                            ret.append("</TD>");
                        } else {
                            boolean cancheckin = false;
                            PreparedStatement psuser = con.prepareStatement("select rep_emailCheckOut from resrepository where rep_docId=? and idtm=?");
                            psuser.setLong(1, repdocid);
                            psuser.setString(2, dir.getWebSiteId());
                            ResultSet rsuser = psuser.executeQuery();
                            if (rsuser.next()) {
                                String repemailcout = rsuser.getString("rep_emailCheckOut");
                                if (user != null) {
                                    if (repemailcout != null && repemailcout.equals(user.getId())) {
                                        cancheckin = true;
                                    }
                                }
                            }

                            rsuser.close();
                            psuser.close();
                            String strReservado = ""; //<font size='-3' face='Verdana, Arial, Helvetica, sans-serif'>&lt;reservado&gt;</font>";  // despues cambiar por la imagen <IMG src='"+ path +"undo.gif' alt='"+paramsRequest.getLocaleString("msgReserved)+"' height='14' width='45' border='0' />

                            strReservado = "<IMG src=\"" + path + paramsRequest.getLocaleString("img_Reserved") + ".gif\" alt=\"" + paramsRequest.getLocaleString("msgReserved") + "\" height=\"14\" width=\"45\" border=\"0\" />&nbsp;";
                            if (cancheckin) {
                                ret.append("\n<TD width='140' align=\"center\">\r\n<A href=\"javascript: doCheckin(" + i_log + "," + repdocid + ");\">");
                                ret.append("\n<IMG src='" + path + "in.gif' alt='" + paramsRequest.getLocaleString("msgCIn") + "' height='14' width='25' border='0' /></A>");
                                ret.append("&nbsp;<A href=\"javascript: doUndocheckout(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "undo.gif' alt='" + paramsRequest.getLocaleString("msgUndoCOut") + "' height='14' width='45' border='0' /></A>");
                                ret.append("\n</TD>");
                            } else {
                                if (nivel == 3) {
                                    ret.append("\n<TD width='140' align=\"center\">");
                                    ret.append(strReservado);
                                    ret.append("<a href=\"javascript: doUndocheckout(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "undo.gif' alt='" + paramsRequest.getLocaleString("msgUndoCOut") + "' height='14' width='45' border='0'></a>");
                                    ret.append("\n</TD>");
                                } else {
                                    ret.append("\n<TD width='140' align='center'>");
                                    ret.append(strReservado);
                                    ret.append("\n</TD>");
                                }

                            }

                        }
                    }
                    ret.append("<TD width='80' align='center'>");
                    if (user.getId() != null && user.isSigned()) {
                        if (!subcriptions.contains(new Long(0))) //dir
                        {
                            if (!subcriptions.contains(new Long(repdocid))) {
                                ret.append("<a href=\"javascript: doSuscribeDoc(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "suscribe.gif' alt='" + paramsRequest.getLocaleString("msgSuscribe") + "' border='0'></a>");
                            } else {
                                ret.append("<a href=\"javascript: doUnsuscribeDoc(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "unsuscribe.gif' alt='" + paramsRequest.getLocaleString("msgUnSuscribe") + "' border='0'></a>");
                            }
                        }
                    }

                    boolean candelete = false;
                    if (nivel == 3) {
                        candelete = true;
                    } else {
                        PreparedStatement psuser = con.prepareStatement("select rep_email from resrepository where rep_docId=? and idtm=?");
                        psuser.setLong(1, repdocid);
                        psuser.setString(2, dir.getWebSiteId());
                        ResultSet rsuser = psuser.executeQuery();
                        if (rsuser.next()) {
                            if (user.getId() != null) {
                                if (rsuser.getString("rep_email").equals(user.getId())) {
                                    candelete = true;
                                }
                            }
                        }
                    }
                    if (candelete && user.isSigned()) {
                        if (repstatus == 0) {
                            if (resource.getAttribute("showdirectory", "true").equals("true")) {
                                ret.append("<a href=\"javascript: doMoveDocDir(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "folder.gif' alt='" + paramsRequest.getLocaleString("msgALTMove") + "' border='0'></a>");
                            }
                            ret.append("<a href=\"javascript: doDelete(" + i_log + "," + repdocid + ");\"><IMG src='" + path + "delete.gif' alt='" + paramsRequest.getLocaleString("msgALTDelete") + "' border='0'></a>");
                        }
                    }
                    if (nivel >= 1) {
                        boolean inline = false;
                        if (repfiletype != null) {
                            for (int i = 0; i < values.length; i++) {
                                if (values[i].equals("*/*")) {
                                    inline = true;
                                    break;
                                } else {
                                    if (values[i].equalsIgnoreCase(repfiletype)) {
                                        inline = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (inline) {
                            SWBResourceURL urlline = paramsRequest.getRenderUrl();
                            urlline.setCallMethod(urlline.Call_DIRECT);
//                            urlline.setParameter("repfop","view");
//                            urlline.setParameter("reptp", dir.getId());
//                            urlline.setParameter("repfiddoc",Long.toString(repdocid));
//                            urlline.setParameter("repinline","true");
                            if (i_log == 1 || supportGuestUser() == 1) {
                                ret.append("<a target='_new' href='" + urlline + "/" + type + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + Long.toString(repdocid) + "&repinline=true'><IMG src='" + path + "preview.gif' alt='" + paramsRequest.getLocaleString("msgALTPreview") + "' border='0'></a>");
                            } else {
                                ret.append("<a onclick='javascript:alert(\"Debes estar firmado para poder ver este archivo.\")'><IMG src='" + path + "preview.gif' alt='" + paramsRequest.getLocaleString("msgALTPreview") + "' border='0'></a>");
                            }
                        } else {
                            int tmp = i_log;
                            if (supportGuestUser() == 1) {
                                tmp = 1;
                            }
                            ret.append("<a href=\"javascript: doView(" + tmp + "," + repdocid + ",'" + type + "');\"><IMG src='" + path + "preview.gif' alt='" + paramsRequest.getLocaleString("msgALTPreview") + "' border='0'></a>");
                        }
                    }
                    ret.append("</TD>");
                    ret.append("</tr>");
                    ret.append("<TR><TD colspan='10'><IMG src='" + path + "line.gif' width='100%' height='5'></TD></TR>");
                }
                rsversions.close();
                ps2.close();
            }

            ret.append("<TR>");
            ret.append("<TD  align='left' colspan='3'><FONT size='1' face='Verdana, Arial, Helvetica, sans-serif'><b>" + i_tot + "</b> " + "file(s)" + "</FONT></TD>");
            ret.append("<TD  align='right' colspan='7'>");
            if (nivel >= 2 && user.isSigned()) {
                ret.append("<A href=\"javascript: doAddFile(" + i_log + ");\"><IMG src='" + path + "add.gif' alt='" + paramsRequest.getLocaleString("msgAdd") + "' width='20' height='18' border='0'></A><FONT size='1' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgAddFile") + "</FONT>");
            }
            ret.append("&nbsp;</TD>");
            ret.append("</TR>");

            rs.close();
            ps.close();
            ret.append("</table>");
        } catch (SQLException e) {
            AFUtils.log(e, "Error on showfiles", true);
            return ret.toString();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    AFUtils.log(e, "Error on showfiles", true);
                }
            }
        }
        return ret.toString();
    }

    /** Get the file name icon to represents the file type
     * @param filename The file's name
     * @return The name of the icon thats represents the file type
     */
    public String getFileName(String filename) {
        String file = "ico_default2.gif";
        String type = filename.toLowerCase();
        if (type.indexOf(".bmp") != -1) {
            file = "ico_bmp.gif";
        } else if (type.indexOf(".pdf") != -1) {
            file = "ico_acrobat.gif";
        } else if (type.indexOf(".xls") != -1 || type.indexOf(".xlsx") != -1) {
            file = "ico_excel.gif";
        } else if (type.indexOf(".html") != -1 || type.indexOf(".htm") != -1) {
            file = "ico_html.gif";
        } else if (type.indexOf("jpg") != -1 || type.indexOf("jpeg") != -1) {
            file = "ico_jpeg.gif";
        } else if (type.indexOf(".ppt") != -1 || type.indexOf(".pptx") != -1) {
            file = "ico_powerpoint.gif";
        } else if (type.indexOf(".exe") != -1) {
            file = "ico_program.gif";
        } else if (type.indexOf(".txt") != -1 || type.indexOf(".properties") != -1) {
            file = "ico_text.gif";
        } else if (type.indexOf(".doc") != -1 || type.indexOf(".docx") != -1) {
            file = "ico_word.gif";
        } else if (type.indexOf(".xml") != -1 || type.indexOf(".xsl") != -1) {
            file = "ico_xml.gif";
        } else if (type.indexOf(".mmap") != -1) {
            file = "ico_mindmanager.GIF";
        } else if (type.indexOf(".gif") != -1) {
            file = "ico_gif.gif";
        } else if (type.indexOf(".avi") != -1) {
            file = "ico_video.gif";
        } else if (type.indexOf(".mp3") != -1) {
            file = "ico_audio.gif";
        } else if (type.indexOf(".wav") != -1) {
            file = "ico_audio.gif";
        }
        return file;
    }

    /** Get the file type
     * @param filename The name of the file
     * @return The file type
     */
    public String getFileType(String filename) {
        String file = "Document";
        String type = filename.toLowerCase();
        if (type.indexOf(".bmp") != -1) {
            file = "Image";
        } else if (type.indexOf(".pdf") != -1) {
            file = "Adobe Acrobat";
        } else if (type.indexOf(".xls") != -1 || type.indexOf(".xlsx") != -1) {
            file = "Microsoft Excel";
        } else if (type.indexOf(".html") != -1 || type.indexOf(".htm") != -1) {
            file = "HTML file";
        } else if (type.indexOf("jpg") != -1 || type.indexOf("jpeg") != -1) {
            file = "Image";
        } else if (type.indexOf(".ppt") != -1 || type.indexOf(".pptx") != -1) {
            file = "Microsoft Power Point";
        } else if (type.indexOf(".vsd") != -1) {
            file = "Microsoft Visio";
        } else if (type.indexOf(".mpp") != -1) {
            file = "Microsoft Project";
        } else if (type.indexOf(".mmap") != -1) {
            file = "Mind Manager";
        } else if (type.indexOf(".exe") != -1) {
            file = "Application";
        } else if (type.indexOf(".txt") != -1) {
            file = "Text file";
        } else if (type.indexOf(".properties") != -1) {
            file = "Properties file";
        } else if (type.indexOf(".doc") != -1 || type.indexOf(".docx") != -1) {
            file = "Microsoft Word";
        } else if (type.indexOf(".xml") != -1) {
            file = "XML file";
        } else if (type.indexOf(".gif") != -1 || type.indexOf(".png") != -1) {
            file = "Image";
        } else if (type.indexOf(".avi") != -1) {
            file = "Media file";
        } else if (type.indexOf(".mp3") != -1) {
            file = "Audio file";
        } else if (type.indexOf(".wav") != -1) {
            file = "Audio file";
        } else if (type.indexOf(".xsl") != -1) {
            file = "XSLT file";
        }
        return file;
    }

    /** Get the existing versions of the file
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param dir A topic object that represent a directory
     * @param resource A Resource object
     * @param nivel The user level into the repository
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An exception of type AF (Applicatioon Framework)
     * @return The history of the file changes
     */
    public String history(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource, int nivel, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer();
        Connection con = null;
        PreparedStatement ps = null;
        String type = null;
        String file = null;
        String s_author = null;
        String date = null;
        long id = 0;
        if (nivel == 0) {
            return "";
        }

        SWBResourceURL url = paramsRequest.getRenderUrl();

        try {
            id = Long.parseLong(request.getParameter("repfiddoc"));
            String topicid = dir.getId();

            con = WBUtils.getDBConnection();
            ps = con.prepareStatement("select * from resrepositoryversions where rep_docId=? and idtm=?");
            ps.setLong(1, id);
            ps.setString(2, dir.getWebSiteId());
            ResultSet rs = ps.executeQuery();

            String tp = dir.getId();
            ret.append("\n<TABLE width='100%'  border='0' cellspacing='0' cellpadding='1'>");
            ret.append("\n<TR>");
            ret.append("\n<TD colspan='10'><IMG src='" + path + "openfolder.gif' width='20' height='20'> <B><FONT face='Verdana, Arial, Helvetica, sans-serif' size=\"2\">" + dir.getDisplayName() + "</FONT></B></TD>");
            ret.append("</TR>");
            ret.append("\n<TR bgcolor='#589942'>");
            ret.append("\n<TD width='20'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'>&nbsp;</FONT></TD>");
            ret.append("\n<TD width='55'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgVersion") + "</FONT></TD>");
            ret.append("\n<TD><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgAuthor") + "</FONT></TD>");
            ret.append("\n<TD width='90'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgComments") + "</FONT></TD>");
            ret.append("\n<TD width='100'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgFileName") + "</FONT></TD>");
            ret.append("\n<TD width='100'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgLastUpdate") + "</FONT></TD>");
            ret.append("\n<TD width='55'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgSize") + "</FONT></TD>");
            if (nivel >= 1) {
                ret.append("\n<TD width='55' align='center'><FONT color='#FFFFFF' size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgView") + "</FONT></TD>");
            }
            ret.append("\n</TR>");
            ret.append("\n<TR><TD colspan='10'><IMG src='" + path + "line.gif' width='100%' height='5'></TD></TR>");

            while (rs.next()) {
                file = "file.gif";

                String repemail = rs.getString("rep_email");

                if (rs.getString("rep_fileType") != null) {
                    type = rs.getString("rep_fileName");
                    file = this.getFileName(type);
                }
                if (repemail == null) {
                    s_author = "&nbsp;";
                } else {
                    try {
                        User usr = null;
                        usr = dir.getWebSite().getUserRepository().getUser(repemail);
                        if (usr.getFirstName() != null) {
                            s_author = usr.getFirstName();
                        }
                        if (usr.getLastName() != null) {
                            s_author = s_author + " " + usr.getLastName();
                        }
                    } catch (Exception e) {
                        s_author = "&nbsp;";
                        AFUtils.log(e, "Error on method history class RepositoryFile trying to create new user with email" + ": " + dir.getWebSite().getUserRepository().getUser(repemail).getEmail(), true);
                    }
                }
                SWBResourceURL urlrecdoc = paramsRequest.getRenderUrl();
                urlrecdoc.setCallMethod(urlrecdoc.Call_DIRECT);
                ret.append("\n<tr>");
                ret.append("\n<TD width='20'><a href='" + urlrecdoc.toString() + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + rs.getString("rep_docId") + "&repfversion=" + rs.getString("rep_fileVersion") + "'><IMG src='" + path + "" + file + "' border=0></a></TD>");
                ret.append("\n<TD align='center' width='55'><FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + rs.getString("rep_fileVersion") + "</FONT></TD>");
                ret.append("\n<TD width='55'><FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + s_author + "</FONT></TD>");
                ret.append("\n<TD width='55'><FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + rs.getString("rep_comment") + "</FONT></TD>");
                ret.append("\n<TD width='55'><FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + rs.getString("rep_fileName") + "</FONT></TD>");
                Timestamp repcreate = rs.getTimestamp("rep_create");
                date = repcreate.toString();
                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new java.util.Locale("es"));
                date = df.format(new Date(repcreate.getTime()));

                ret.append("\n<TD width='55'><FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + date + "</FONT></TD>");


                int size = rs.getInt("rep_fileSize") / 1024;
                if (size == 0) {
                    size = 1;
                }
                ret.append("\n<TD width='55'><FONT size='2' face='Verdana, Arial, Helvetica, sans-serif'>" + size + " Kb</FONT></TD>");

                if (nivel >= 1) {
                    ret.append("<TD width='55' align='center'>");
                    boolean inline = false;
                    if (rs.getString("rep_fileType") != null) {
                        for (int i = 0; i < values.length; i++) {
                            if (values[i].equals("*/*")) {
                                inline = true;
                                break;
                            } else {
                                if (values[i].equalsIgnoreCase(rs.getString("rep_fileType"))) {
                                    inline = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (inline) {
                        SWBResourceURL urlrec = paramsRequest.getRenderUrl();
                        urlrec.setCallMethod(urlrec.Call_DIRECT);
                        ret.append("\n<a target='_new' href='" + urlrec.toString() + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + rs.getString("rep_docId") + "&repfversion=" + rs.getString("rep_fileVersion") + "&repinline=true'><IMG src='" + path + "preview.gif' alt='" + paramsRequest.getLocaleString("msgALTPreview") + "' border='0'></a>");
                    } else {
                        SWBResourceURL urlrec = paramsRequest.getRenderUrl();
                        urlrec.setCallMethod(urlrec.Call_DIRECT);
                        ret.append("\n<a href='" + urlrec.toString() + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + rs.getString("rep_docId") + "&repfversion=" + rs.getString("rep_fileVersion") + "'><IMG src='" + path + "preview.gif' alt='" + paramsRequest.getLocaleString("msgALTPreview") + "' border='0'></a>");
                    }
                    ret.append("\n</TD>");
                }
                ret.append("\n</tr>");
                ret.append("\n<TR><TD colspan='10'><IMG src='" + path + "line.gif' width='100%' height='5'></TD></TR>");
            }
            rs.close();
            ps.close();
            ret.append("\n<TR><TD colspan='10' align='center'><input  type='button' name='n' value='" + paramsRequest.getLocaleString("msgBTNViewAllFiles") + "' onClick='javascript:init()'></TD></TR>");
            ret.append("\n</table>");
            ret.append("<script language='JavaScript'>\r\n");
            ret.append("function textCounter(field,  maxlimit) {\r\n");
            ret.append("if (field.value.length > maxlimit)\r\n");
            ret.append("field.value = field.value.substring(0, maxlimit);\r\n");
            ret.append("}\r\n");
            ret.append("function init(){\r\n");
            ret.append("document.location='" + url.toString() + "?reptp=" + dir.getId() + "';\r\n");
            ret.append("}\r\n");
            ret.append("</script>\r\n");
        } catch (SQLException e) {
            AFUtils.log(e, "Error on history", true);
            return ret.toString();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    AFUtils.log(e, "Error on history", true);
                }
            }
        }
        return ret.toString();
    }

    /**
     * Add new file into the repository
     * @return The action for generates a new file into the repository
     * @param fup A File upload object
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param dir A topic object that represent a directory
     * @param resource A Resource object
     * @param nivel The user level into the repository
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An exception of type AF (Applicatioon Framework)
     */
    public synchronized String insert(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource, int nivel, SWBParamRequest paramsRequest, FileUpload fup) throws SWBResourceException, IOException {
        //System.out.println("RepositoryFile.insert");
        StringBuffer ret = new StringBuffer();
        String title = null;
        String filename = null;
        Connection con = null;

        if (nivel < 2) {
            return "";
        }

        try {
            title = fup.getValue("repftitle");
        } catch (IOException ioe) {
            AFUtils.log(ioe, "Error on insert");
        }

        try {
            filename = fup.getFileName("repfdoc");

            if (filename.lastIndexOf('/') != -1) {
                int pos = filename.lastIndexOf('/');
                filename = filename.substring(pos + 1);
            }
            if (filename.lastIndexOf('\\') != -1) {
                int pos = filename.lastIndexOf('\\');
                filename = filename.substring(pos + 1);
            }
            byte[] bcont = fup.getFileData("repfdoc");

            con = WBUtils.getDBConnection();
            if (con == null) {
                return ret.toString();
            }
            PreparedStatement psmaxid = con.prepareStatement("select max(rep_docId) as id from resrepository where idtm=?");
            psmaxid.setString(1, dir.getWebSiteId());
            ResultSet rsmax = psmaxid.executeQuery();
            long docid = 0;
            if (rsmax.next()) {
                docid = rsmax.getInt("id");
            }
            rsmax.close();
            psmaxid.close();
            docid++;
            int pos = filename.lastIndexOf('.');
            String file_db = filename;
            if (pos != -1) {
                file_db = docid + "_" + "1" + file_db.substring(pos);
            } else {
                file_db = docid + "_" + "1";
            }
            File repositorydir = new File(SWBPortal.getWorkPath() + "/" + resource.getWorkPath() + "/");
            if (!repositorydir.exists()) {
                repositorydir.mkdirs();
            }

            SWBPortal.writeFileToWorkPath(resource.getWorkPath() + "/" + file_db, new ByteArrayInputStream(bcont), user);

            PreparedStatement ps = con.prepareStatement("insert into resrepository "
                    + "(rep_docId,resId,idtm,topic,rep_email,rep_title,rep_description,rep_lastVersion,rep_status, rep_deleted, rep_create) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?)");
            ps.setLong(1, docid);
            ps.setString(2, resource.getId());
            String topicid = dir.getId();
            ps.setString(3, dir.getWebSiteId());
            ps.setString(4, topicid);
            ps.setString(5, user.getId());
            ps.setString(6, title);
            ps.setString(7, fup.getValue("repfdescription"));
            ps.setInt(8, 1);
            ps.setInt(9, 0);
            ps.setInt(10, 0);  // insert 0 means new file, also means not deleted

            ps.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
            int num_reg = ps.executeUpdate();
            ps.close();

            // eliminando del nombre del archivo los caracteres especiales: vocales acentuadas, cambiando espacions por "_", � por n, etc...
            String strfileName = "";
            String strFileExt = "";
            if (filename.lastIndexOf(".") != -1) {
                strfileName = filename.substring(0, filename.lastIndexOf("."));
                strFileExt = filename.substring(filename.lastIndexOf("."));
            } else {
                strfileName = filename;
                strFileExt = "";
            }


            filename = AFUtils.replaceSpecialCharacters(strfileName, true) + strFileExt;

            PreparedStatement ps2 = con.prepareStatement("insert into "
                    + "resrepositoryversions(rep_docId,idtm,rep_fileVersion,rep_email,rep_fileName,rep_fileSize,rep_fileDate,rep_comment,rep_create,rep_fileType) "
                    + "values(?,?,?,?,?,?,?,?,?,?)");
            ps2.setLong(1, docid);
            ps2.setString(2, dir.getWebSiteId());
            ps2.setInt(3, 1);
            ps2.setString(4, user.getId());
            ps2.setString(5, filename);
            ps2.setInt(6, bcont.length);
            ps2.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            ps2.setString(8, fup.getValue("repfdescription"));
            ps2.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

            //System.out.println("ContentType: " + fup.getContentType("repfdoc"));

            ps2.setString(10, fup.getContentType("repfdoc"));
            num_reg = ps2.executeUpdate();
            ps2.close();

            String description = fup.getValue("repfdescription");
            String comment = fup.getValue("repfdescription");

            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new java.util.Locale("es"));
            String fileDate = df.format(new Date(System.currentTimeMillis()));

            String action = "create";
            int version = 1;
            notification.sendNotification(user, resource, docid, title, description, filename, comment, fileDate, version, action, dir, paramsRequest, request);
            saveLog("upload", user, docid, dir, "Description", 1);

//            File f = new File(WBUtils.getInstance().getWorkPath() + "/" + resource.getResourceWorkPath() + "/" + file_db);
//
//            if (f.exists()) {
//                WBIndexer w_indx = WBIndexMgr.getInstance().getTopicMapIndexer(dir.getMap().getId());
//                if (w_indx != null) {
//                    try {
//                        String type = f.getName();
//                        WBResourceURL urllineA = paramsRequest.getRenderUrl();
//                        urllineA.setCallMethod(urllineA.Call_DIRECT);
//                        String url = "" + urllineA + "/" + type + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + Long.toString(docid) + "&repinline=true";
//                        w_indx.indexFile(f, title, url, dir, resource.getId());
//                    } catch (Exception ex_ind) {
//                        AFUtils.log(ex_ind, "Error while trying to index file.", true);
//                    }
//                }
//            }

        } catch (Exception e) {
            if (filename != null) {
                File fdoc = new File(WBUtils.getInstance().getWebWorkPath() + "/" + filename);
                if (fdoc.exists()) {
                    fdoc.delete();
                }
            }
            AFUtils.log(e, "Error on insert", true);
            return ret.toString();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    AFUtils.log(e, "Error on insert", true);
                }
            }
        }
        return ret.toString();
    }

    /** Add file form to upload a new file into the repository
     * @param request Input parameters
     * @param response the answer to the user request
     * @param user WBUser object in session
     * @param topic Topic object
     * @param hashMap A list of arguments
     * @param dir A topic object that represent a directory
     * @param resource A Resource object
     * @param nivel The user level into the repository
     * @param paramsRequest a list of objects (topic, user, action, ...)
     * @throws AFException An exception of type AF (Applicatioon Framework)
     * @return The create form
     */
    public String create(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource, int nivel, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer();
        String reptp = null;

        SWBResourceURL url = paramsRequest.getRenderUrl();
        reptp = request.getParameter("reptp");
        url.setParameter("reptp", reptp);
        if (nivel < 2) {
            return "";
        }
        ret.append("\n<form name='frmnewdoc' method='POST' enctype='multipart/form-data' action='" + url.toString() + "'>");
        ret.append("\n<input type='hidden' name='repfop' value='insert'>");
        ret.append("\n<TABLE width='100%'  border='0' cellspacing='0' cellpadding='1'>");
        ret.append("\n<TR>");
        ret.append("\n<TD colspan='2'><IMG src='" + path + "openfolder.gif' width='20' height='20'> <B><FONT face='Verdana, Arial, Helvetica, sans-serif' size=\"2\">" + dir.getDisplayName() + "</FONT></B></TD>");
        ret.append("\n</TR>");
        ret.append("\n<TR>");
        ret.append("\n<TD colspan='10'><IMG src='" + path + "line.gif' width='100%' height='5'></TD>");
        ret.append("\n</TR>");
        ret.append("\n<tr>");
        ret.append("\n<td width=\"200\">");
        ret.append("\n<FONT face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgTitleDocument") + "</font>");
        ret.append("\n</td>");
        ret.append("\n<td>");
        ret.append("\n<input  type='text' maxlength='99' name='repftitle'>");
        ret.append("\n</td>");
        ret.append("\n</tr>");
        ret.append("\n<tr>");
        ret.append("\n<td width=\"200\">");
        ret.append("\n<FONT face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgDescription") + "</font>");
        ret.append("\n</td>");
        ret.append("\n<td>");
        ret.append("\n<textarea rows='5' name='repfdescription' cols='20' onKeyDown='textCounter(this.form.repfdescription,255);' onKeyUp='textCounter(this.form.repfdescription,255);'></textarea>");
        ret.append("\n</td>");
        ret.append("\n</tr>");
        ret.append("\n<tr>");
        ret.append("\n<td width=\"200\">");
        ret.append("\n<FONT face='Verdana, Arial, Helvetica, sans-serif'>" + paramsRequest.getLocaleString("msgFile") + "</font>");
        ret.append("\n</td>");
        ret.append("\n<td>");
        ret.append("\n<input type='file'  name='repfdoc'>");
        ret.append("\n</td>");
        ret.append("\n</tr>");
        ret.append("\n<TR>");
        ret.append("\n<TD colspan='2'><IMG src='" + path + "line.gif' width='100%' height='5'></TD>");
        ret.append("\n</TR>");
        ret.append("\n<tr>");
        ret.append("\n<td colspan='2' align='center'>");
        ret.append("\n<input type='button'  name='s' value='" + paramsRequest.getLocaleString("msgBTNSave") + "' onclick='javascript:valida();'>\r\n");
        ret.append("\n<input type='button'  name='cancel' value='" + paramsRequest.getLocaleString("msgBTNCancel") + "' onclick='javascript:init();'>\r\n");
        ret.append("\n</td>");
        ret.append("\n</tr>");
        ret.append("\n</table>");
        ret.append("\n</form>");
        ret.append("<script language='JavaScript'>\r\n");
        ret.append("function init(){\r\n");
        ret.append("document.location='" + url.toString() + "';\r\n");
        ret.append("}\r\n");
        ret.append("function textCounter(field,  maxlimit) {\r\n");
        ret.append("if (field.value.length > maxlimit)\r\n");
        ret.append("field.value = field.value.substring(0, maxlimit);\r\n");
        ret.append("}\r\n");
        ret.append("function submitform()\r\n");
        ret.append("{\r\n");
        ret.append("\r\ndocument.frmnewdoc.submit();\r\n");
        ret.append("}\r\n");
        ret.append("function valida(){\r\n");
        ret.append("if(document.frmnewdoc.repftitle.value==\"\")\r\n");
        ret.append("{\r\n");
        ret.append("alert(\"" + paramsRequest.getLocaleString("msgAlertDefineTitle") + "\");\r\n");
        ret.append("return;\r\n");
        ret.append("}\r\n");
        ret.append("if(document.frmnewdoc.repfdescription.value==\"\")\r\n");
        ret.append("{\r\n");
        ret.append("alert(\"" + paramsRequest.getLocaleString("msgAlertDefineDescription") + "\");\r\n");
        ret.append("return;\r\n");
        ret.append("}\r\n");
        ret.append("if(document.frmnewdoc.repfdoc.value==\"\")\r\n");
        ret.append("{\r\n");
        ret.append("alert(\"" + paramsRequest.getLocaleString("msgAlertDefineFile") + "\");\r\n");
        ret.append("return;\r\n");
        ret.append("}\r\n");
        ret.append(" \r\n");
        ret.append("\r\n");
        ret.append("\r\n");
        ret.append("submitform();\r\n");
        ret.append("}\r\n");
        ret.append("</script>\r\n");

        return ret.toString();
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
    public void saveLog(String p_action, User user, long p_fileid, WebPage p_topic, String p_description, int p_isfile) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String str_name = null;
        String str_title = "";
        String str_topicmapid = null;
        String str_topicid = null;
        try {
            con = WBUtils.getDBConnection();
            if (p_isfile == 1) {
                ps = con.prepareStatement("select rep_title from resrepository where rep_docId=? and idtm=?");
                ps.setLong(1, p_fileid);
                ps.setString(2, p_topic.getWebSiteId());
                rs = ps.executeQuery();
                if (rs.next()) {
                    str_title = rs.getString("rep_title");
                }
            } else {
                str_title = "";
            }
            if (user.getFirstName() != null) {
                str_name = user.getFirstName();
            }
            if (user.getLastName() != null) {
                str_name = str_name + user.getLastName();
            }
            str_topicid = p_topic.getId();
            str_topicmapid = p_topic.getWebSiteId();

            ps = con.prepareStatement("insert into resrepositorylog "
                    + "(rep_email,rep_user,rep_docId,rep_action,rep_name,rep_topicid,rep_topicmapid,rep_objectid,rep_description,rep_dateaction,rep_isfile,rep_ipuser) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, user.getId());
            ps.setString(2, str_name);
            ps.setLong(3, p_fileid);
            ps.setString(4, p_action);
            ps.setString(5, str_name);
            ps.setString(6, str_topicid);
            ps.setString(7, str_topicmapid);
            ps.setString(8, resource.getId());
            ps.setString(9, p_description);
            ps.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
            ps.setInt(11, p_isfile);
            ps.setString(12, user.getIp());
            ps.execute();
            ps.close();
            con.close();
        } catch (Exception e) {
            AFUtils.log(e, "Error while trying to create a resource record at RepositoryFile:saveLog", true);
        } finally {
            ps = null;
            con = null;
        }
    }

    /**
     * Move a file from one folder to another
     * @param id File identifier
     * @param fromDir Topic that represents the directory source
     * @param toDir Topic that represents the directory destination
     * @param user WBUser object that represents the user who moves the file
     * @return True or false if the action was success
     */
    public boolean moveDoc2Dir(long id, WebPage fromDir, WebPage toDir, User user) {
        boolean ret = false;
        Connection conn = null;
        PreparedStatement ps = null;
        if (!fromDir.getId().equals(toDir.getId())) {
            try {
                conn = WBUtils.getDBConnection();
                ps = conn.prepareStatement("update resrepository set topic=? where rep_docId=? and topic=? and idtm=?");
                ps.setString(1, toDir.getId());
                ps.setLong(2, id);
                ps.setString(3, fromDir.getId());
                ps.setString(4, fromDir.getWebSiteId());
                ps.executeUpdate();
                ret = true;
                ps.close();
                conn.close();

                this.saveLog("Move", user, id, toDir, "The document was moved from  " + fromDir.getDisplayName(user.getLanguage()) + " to " + toDir.getDisplayName(user.getLanguage()) + " directory.", 1);
            } catch (Exception e) {
                AFUtils.log(e, "Error while trying to move document with id: " + id + " from directory " + fromDir.getId() + " to directory " + toDir.getId(), true);
            }
        }

        return ret;
    }

    public int supportGuestUser() {

        int level = 0;
        String viw = resource.getAttribute("view");
        if (viw == null) // quiere decir que view es ninguno
        {
            String guest = resource.getAttribute("guest");
            if (guest != null && guest.equals("true")) {
                level = 1;
            }
        }
        return level;
    }
}
