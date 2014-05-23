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

import com.infotec.appfw.exception.AFException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.sql.*;
import java.text.*;
import com.infotec.wb.core.db.*;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.indexer.FileSearchWrapper;
import org.semanticwb.portal.indexer.SWBIndexer;
import org.semanticwb.portal.util.FileUpload;

/**
 * Muestra la lista de los documentos existentes dentro del repositorio. Se
 * pueden agregar documentos, mostrar su informaci�n detallada, el historial de
 * cada documento, vista preliminar, borrar el documento del repositorio, sacar
 * el documento del repositorio para su revisi�n o modificaci�n y actualizar el
 * documento.
 *
 * Show the repository list of the existing documents. It can add new documents,
 * show document detail, document history, document preview, erase document from
 * the repository, make a check out for review or for a modification, check in
 * with the updated document.
 *
 * @author Victor Lorenzana
 */
public class RepositoryFile {

    protected org.semanticwb.model.Resource resource;
    String path = "";
    String[] values;
    protected Notification notification = new Notification();
    protected TreeRepHtml dirs = new TreeRepHtml();
    protected boolean hideSuscriptions=true;

    /**
     * Creates a new instance of RepositoryFile
     */
    public RepositoryFile() {
    }

    /**
     * User html view of the repository resource files
     *
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

    /**
     * Gets the user level into the repository
     *
     * @param user WBUser object to get the access level
     * @param tmid Topic map identifier
     * @return An integer value of the user level
     */
    public int getLevelUser(User user, String tmid) {


        int level = 0;
        String adm = resource.getAttribute("admin");
        User usr = user;
        UserRepository usrRep = usr.getUserRepository();
        Role rol = null;
        if (adm != null) {
            rol = usrRep.getRole(adm);
            //int r=Integer.parseInt(adm);
            if (user.hasRole(rol)) {
                level = 3;
            }
        } else {
            level = 3;
        }

        if (level == 0) {
            String mdy = resource.getAttribute("modify");
            if (mdy != null) {
                rol = usrRep.getRole(mdy);
                //int r=Integer.parseInt(mdy);
                if (user.hasRole(rol)) {
                    level = 2;
                }
            } else {
                level = 2;
            }
        }

        if (level == 0) {
            String viw = resource.getAttribute("view");
            if (viw != null) {
                rol = usrRep.getRole(viw);
                //int r=Integer.parseInt(viw);
                if (user.hasRole(rol)) {
                    level = 1;
                }
            } else {
                level = 1;
            }
        }
        return level;

    }

    /**
     * User html view of the repository resource files
     *
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
    public String getHtml(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, int nivel, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

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

                    ret.append("<script type='text/javascript'> \r\n");
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
                    ret.append("<script type='text/javascript'>\r\n");
                    ret.append("window.location='" + url1.toString() + "?reptp=" + dir.getId() + "';\r\n");
                    ret.append("</script>\r\n");
                } else if (op.equals("updatetitle")) {
                    long id = Long.parseLong(request.getParameter("repfiddoc"));
                    this.updatetitle(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest);
                    // indexar cambio


                    ret.append("<form class=\"oculto\" name=\"frmupdatetitle\" action=\"" + url1.toString() + "\" method=\"post\">");
                    ret.append("<input type=\"hidden\" name=\"repfop\" value=\"info\"><input type=\"hidden\" name=\"updated\" value=\"1\"><input type=\"hidden\" name=\"reptp\" value=\"" + dir.getId() + "\"><input type=\"hidden\" name=\"repfiddoc\" value=\"" + id + "\">");
                    ret.append("</form>");
                    ret.append("<script type='text/javascript'>\r\n");
                    ret.append("window.document.frmupdatetitle.submit();");
                    ret.append("</script>\r\n");
                } else if (op.equals("mod")) {
                    long id = Integer.parseInt(fup.getValue("repfiddoc"));
                    this.newversion(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest, fup);
                    ret.append("<form class=\"oculto\" name=\"frmnewversion\" action=\"" + url1.toString() + "\" method=\"post\">");
                    ret.append("<input type=\"hidden\" name=\"repfop\" value=\"history\" /><input type=\"hidden\" name=\"reptp\" value=\"" + dir.getId() + "\" /><input type=\"hidden\" name=\"repfiddoc\" value=\"" + id + "\" />");
                    ret.append("</form>");
                    ret.append("<script type='text/javascript'>\r\n");
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
                        Repository.log.error("Error while trying to move the document to another directory. RepositoryFile.getHtml.movedoctodir");
                    }
                    ret.append("<script type='text/javascript'>\r\n");
                    ret.append("window.location='" + url1.toString() + "?reptp=" + toDir.getId() + "';\r\n");
                    ret.append("</script>\r\n");
                } else {
                    ret.append(showfiles(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest));
                }
            } else {
                ret.append(showfiles(request, response, user, topic, hashMap, dir, this.resource, nivel, paramsRequest));
            }

        } catch (IOException ioe) {
            Repository.log.error("Error in RepositoryFile.getHTML", ioe);
        }

        return ret.toString();
    }

    /**
     * Load the Resource object information
     *
     * @param resource The Resource object
     */
    public void setResourceBase(org.semanticwb.model.Resource resource) {
        try {
            this.resource = resource;
            path = SWBPlatform.getContextPath() + "/swbadmin/images/Repository/";

        } catch (Exception e) {
            Repository.log.error(e);
        }
    }

    /**
     * Cancel the file reservation
     *
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
                String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
                con = SWBUtils.DB.getConnection(tmp_conn, "RepositoryFile.undocheckout()");
                
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
                Repository.log.error("Error in undocheckout", e);
                return;
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        Repository.log.error("Error in undocheckout", e);
                    }
                }
            }
        } catch (Exception e) {
            Repository.log.error("Error in undocheckout", e);
        }
        return;
    }

    /**
     * Get the file to user view from the repository
     *
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
                            String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
                            con = SWBUtils.DB.getConnection(tmp_conn, "RepositoryFile.view()");
                            
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
                    Repository.log.error("Error al buscar el archivo por nombre: " + fileName, e);
                } finally {
                    if (con != null) {
                        try {
                            con.close();
                            con = null;
                        } catch (Exception e) {
                            Repository.log.error("Error in RepositoryFile.view() -- 1.1 --", e);
                        }
                    }
                    if (rsID != null) {
                        try {
                            rsID.close();
                            rsID = null;
                        } catch (Exception e) {
                            Repository.log.error("Error in RepositoryFile.view() -- 1.1 --", e);
                        }
                    }
                    if (pstID != null) {
                        try {
                            pstID.close();
                            pstID = null;
                        } catch (Exception e) {
                            Repository.log.error("Error in RepositoryFile.view() -- 1.1 --", e);
                        }

                    }
                }
            } else {
                id = Long.parseLong(request.getParameter("repfiddoc"));
            }
            String sversion = request.getParameter("repfversion");
            int version = 1;
            try {
                String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
                con = SWBUtils.DB.getConnection(tmp_conn, "Repository.view() -2-");
                
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
                    SWBUtils.IO.copyStream(in, out);
                }
            } catch (SQLException e) {
                Repository.log.error("Error in RepositoryFile.view() -- 1 -- ", e);
                return;
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        Repository.log.error("Error in RepositoryFile.view() -- 1.1 --", e);
                    }
                }
            }
        } catch (Exception e) {
            Repository.log.error("Error in RepositoryFile.view()", e);
        }
    }

    /**
     * Updates the file information
     *
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
                String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
                con = SWBUtils.DB.getConnection(tmp_conn, "Repository.view -3-()");
                
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
                    indexFile(file_db, file_db, id, title, description, dir, paramsRequest);
                }
            } catch (SQLException e) {
                Repository.log.error("Error in updatetitle", e);
                return;
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        Repository.log.error("Error in updatetitle", e);
                    }
                }
            }
        } catch (Exception e) {
            Repository.log.error("Error in updatetitle", e);
        }
    }

    private void indexFile(String file_remove, String file_index, long f_id, String title,String description, WebPage dir, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        
        //System.out.println("Indexando archivo....");
            File f_last = new File(SWBPortal.getWorkPath() + "/" + resource.getWorkPath() + "/" + file_remove);
            if (f_last.exists()) {
                    try {
                        //System.out.println("Quitando archivo del indice...");
                        SWBPortal.getIndexMgr().getDefaultIndexer().removeSearchable("file:"+f_last.getAbsolutePath());
                    } catch (Exception ex) {
                        Repository.log.error("Error while trying to remove a file from index.", ex);
                    }
            }

            File f = new File(SWBPortal.getWorkPath() + "/" + resource.getWorkPath() + "/" + file_index);
            if (f.exists()) {
                try {
                    //System.out.println("Poniendo archivo en indice....");
                    String type = f.getName();
                    SWBResourceURL urllineA = paramsRequest.getRenderUrl();
                    urllineA.setCallMethod(SWBResourceURL.Call_DIRECT);
                    String url = "" + urllineA + "/" + type + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + Long.toString(f_id) + "&repinline=true";
                    FileSearchWrapper fsw = new FileSearchWrapper(f, title, description, null, url, resource);
                    SWBPortal.getIndexMgr().getDefaultIndexer().indexSerchable(fsw);
                } catch (Exception ex_ind) {
                    Repository.log.error("Error while trying to index file.", ex_ind);
                }
            }
    }  
    
    /**
     * Generates a new version of the file
     *
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
        //System.out.println("RepositoryFile.newversion");
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
            String description = "";
            byte[] bcont = fup.getFileData("repfdoc");
            try {
                String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
                con = SWBUtils.DB.getConnection(tmp_conn, "RepositoryFile.newversion()");
                
                boolean cancheckin = false;
                PreparedStatement psuser = con.prepareStatement("select rep_title,rep_emailCheckOut, rep_description from resrepository where rep_docId=? and idtm=?");
                psuser.setLong(1, id);
                psuser.setString(2, dir.getWebSiteId());
                ResultSet rsuser = psuser.executeQuery();
                if (rsuser.next()) {
                    title = rsuser.getString("rep_title");
                    description = rsuser.getString("rep_description");
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
                PreparedStatement psmaxversion = con.prepareStatement("select MAX(rep_fileVersion) as maximo from resrepositoryversions where rep_docId=? and idtm=?");
                psmaxversion.setLong(1, id);
                psmaxversion.setString(2, dir.getWebSiteId());
                ResultSet rsmax = psmaxversion.executeQuery();
                if (rsmax.next()) {
                    version = rsmax.getInt("maximo");
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

                indexFile(file_last, file_db, id, title, description, dir, paramsRequest);

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
                    description = rsdata.getString("rep_description");
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
                Repository.log.error("Error in newversion", e);
                return;
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        Repository.log.error("Error in newversion", e);
                    }
                }
            }
        } catch (Exception e) {
            Repository.log.error("Error in newversion", e);
        }
    }

    /**
     * Creates a new version form of file
     *
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
                String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
                con = SWBUtils.DB.getConnection(tmp_conn, "RepositoryFile.checkin()");
                
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

                    ret.append("\n<p>" + paramsRequest.getLocaleString("msgEditFileStatus") + ": " + title + "</p>");
                    ret.append("\n<div id=\"checkin\">");
                    ret.append("<form name='frmnewdoc' method='POST' enctype='multipart/form-data'>");
                    ret.append("<input type='hidden' name='repfop' value='mod'>");
                    ret.append("<input type='hidden' name='repfiddoc' value='" + id + "'>");

                    String tp = dir.getId();
                    ret.append("<input type='hidden' name='reptp' value='" + tp + "'>");

                    ret.append("\n<p><label for=\"checkcom\">" + paramsRequest.getLocaleString("msgComments") + ": </label>");
                    ret.append("<textarea name='repfdescription' cols='50' rows='3' onKeyDown='textCounter(this.form.repfdescription,255);' onKeyUp='textCounter(this.form.repfdescription,255);'  id=\"checkcom\"></textarea>");
                    ret.append("</p>\n");

                    ret.append("<p><label for=\"agregafile\">Archivo: </label>");
                    ret.append("<input type='file' name='repfdoc' id=\"agregafile\"  />");
                    ret.append("<input type='hidden' name='repfdocOri' value=\"" + fileName + "\" />");
                    ret.append("</p>");
                    ret.append("<p class=\"botones\">");

                    ret.append("<input type='button'  class=\"aceptar\" name='Submit3' value='" + paramsRequest.getLocaleString("msgBTNSave") + "' onclick='javascript:valida();' />");
                    ret.append("&nbsp;");
                    ret.append("<input type='button'  class=\"cancelar\" name='Submit32' value='" + paramsRequest.getLocaleString("msgBTNCancel") + "' onclick='javascript:init();' />");
                    ret.append("</p>");
                    ret.append("</form>");
                    ret.append("\n</div>");

                    ret.append("<script type='text/javascript'>\r\n");
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
                Repository.log.error("Error in checkin", e);
                return ret.toString();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        Repository.log.error("Error in checkin", e);
                    }
                }
            }
        } catch (Exception e) {
            Repository.log.error("Error in checkin", e);
        }
        return ret.toString();
    }

    /**
     * User's file reservation for modification
     *
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
                String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
                con = SWBUtils.DB.getConnection(tmp_conn, "RepositoryFile.checkout()");
                
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
                Repository.log.error("Error on checkout", e);
                return;
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        Repository.log.error("Error in checkout", e);
                    }
                }
            }
        } catch (Exception e) {
            Repository.log.error("Error in checkout", e);
        }
    }

    /**
     * Eliminates the selected file from repository
     *
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
    public synchronized void delete(HttpServletRequest request, HttpServletResponse response, User user, WebPage topic, HashMap hashMap, WebPage dir, org.semanticwb.model.Resource resource, int nivel, SWBParamRequest paramsRequest, FileUpload fup) throws SWBResourceException, IOException {

        if (nivel == 0) {
            return;
        }

        try {
            long id = Long.parseLong(request.getParameter("repfiddoc"));
            Connection con = null;
            try {
                String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
                con = SWBUtils.DB.getConnection(tmp_conn, "RepositoryFile.delete()");
                
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
                int i_lastVer = 1;
                if (rsdata.next()) {
                    String title = rsdata.getString("rep_title");
                    String description = rsdata.getString("rep_description");
                    String comment = fup.getValue("repfdescription");
                    i_lastVer =  rsdata.getInt("rep_lastVersion");
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
                
                ////// Empieza quitar del indice documento eliminado
                //System.out.println("Eliminando indice...version file "+i_lastVer);
                SWBIndexer w_indx = SWBPortal.getIndexMgr().getDefaultIndexer();
                PreparedStatement pst1 = con.prepareStatement("select rep_fileName from resrepositoryversions where rep_docId=? and rep_fileVersion=? and idtm=?");
                pst1.setLong(1, id);
                pst1.setInt(2, i_lastVer);
                pst1.setString(3, dir.getWebSiteId());
                ResultSet rs1 = pst1.executeQuery();
                 while (rs1.next()) {
                     
                    String file_name = rs1.getString("rep_fileName");
                    //System.out.println("Se encontró el documento a eliminar...."+file_name);
                    int pos = file_name.lastIndexOf('.');
                    if (pos != -1) {
                        String file_db = id + "_" + i_lastVer + file_name.substring(pos);;
                        File f = new File(SWBPortal.getWorkPath() + "/" + resource.getWorkPath() + "/" + file_db);
                        if (f.exists()) {
                            try {
                                //System.out.println("Se encontró INDICE a eliminar....");
                                w_indx.removeSearchable("file:" + f.getAbsolutePath());
                            } catch (Exception ex) {
                                Repository.log.error("Error while trying to remove a file from index. RepositoryFile.delete()", ex);
                            } 
                        }
                    }
                }
                rs1.close();
                pst1.close();
                ////////////////////////////////////////////////////////  Termina quitar del indice el documento eliminado 
                
                PreparedStatement ps = con.prepareStatement("update resrepository set rep_deleted = 1 where rep_docId=? and idtm=?");
                ps.setLong(1, id);
                ps.setString(2, dir.getWebSiteId());
                int count = ps.executeUpdate();
                ps.close();

                return;
            } catch (SQLException e) {
                Repository.log.error("Error on delete", e);
                return;
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        Repository.log.error("Error on delete", e);
                    }
                }
            }
        } catch (Exception e) {
            Repository.log.error("Error on delete", e);
        }
    }

    /**
     * Shows the file information
     *
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
            ret.append("\n<script type='text/javascript'>");
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

            ret.append("\n<form class=\"oculto\" name=\"frmparameter\" method=\"post\" action=\"" + url.toString() + "\" >");
            ret.append("\n<input type=\"hidden\" name=\"repfop\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repacc\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"reptp\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repobj\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repdocid\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repfiddoc\" value=\"\">");
            ret.append("\n</form>");

            ret.append("\n<p>Directorio: " + dir.getDisplayName() + "</p>");
            ret.append("\n<div id=\"informacion\">");
            if (user.isSigned()) {
                ret.append("<form name='frmnewdoc' method='GET' action='" + url.toString() + "'>");
            }
            ret.append("<input type='hidden' name='repfop' value='updatetitle' />");
            ret.append("<input type='hidden' name='repfiddoc' value='" + id + "' />");
            ret.append("<input type='hidden' name='reptp' value='" + tp + "' />");

            String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
            con = SWBUtils.DB.getConnection(tmp_conn, "RepositoryFile.info()");
            
            ps = con.prepareStatement("select rep_docId, resId, rep_email, rep_title, rep_description, rep_lastVersion, rep_status, rep_emailCheckOut, rep_xml from resrepository where rep_docId=? and idtm=?");
            ps.setLong(1, id);
            ps.setString(2, dir.getWebSiteId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                long repdocid = rs.getLong("rep_docId");
                String represid = rs.getString("resId");
                s_author = rs.getString("rep_email");
                String reptitle = rs.getString("rep_title");
                String repdescription = rs.getString("rep_description");
                int replastversion = rs.getInt("rep_lastVersion");
                int repstatus = rs.getInt("rep_status");
                String repemailCOut = rs.getString("rep_emailCheckOut");

                String repxml = rs.getAsciiStream("rep_xml") != null ? SWBUtils.IO.readInputStream(rs.getAsciiStream("rep_xml")) : null;

                ret.append("<p><label for=\"infotit\">" + paramsRequest.getLocaleString("msgTitle") + ": </label>");

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
                    ret.append("<input  type='text' maxlength='99' name='repftitle' value='" + reptitle + "' id=\"infotit\" />");
                } else {
                    ret.append(reptitle);
                }
                ret.append("</p>");

                ret.append("<p><label for=\"desctit\">" + paramsRequest.getLocaleString("msgDescription") + ": </label>");
                if (canupdate) {
                    ret.append("<textarea name='repfdescription' cols='50' rows='3' onKeyDown='textCounter(this.form.repfdescription,255);' onKeyUp='textCounter(this.form.repfdescription,255);' id=\"desctit\" >" + repdescription + "</textarea>");
                } else {
                    ret.append(repdescription);
                }
                ret.append("</p>");

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
                    ret.append("<p><strong>" + paramsRequest.getLocaleString("msgLastUpdate") + ": </strong>");
                    ret.append( date + "</p>");

                    ret.append("<p><strong>" + paramsRequest.getLocaleString("msgAuthor") + ": </strong>");
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
                            Repository.log.error("Error on method info class RepositoryFile trying to create new user docId" + ": " + id, e);
                        }
                    }
                    ret.append(s_author + "</p>");

                    ret.append("<p><strong>" + paramsRequest.getLocaleString("msgCurrentVersion") + ": </strong>");
                    ret.append("<a href=\"javascript: doShowHistory(" + repdocid + ");\">" + replastversion + "</a></p>");

                    //////////////////////////////////////
                    ret.append("<p><strong>" + paramsRequest.getLocaleString("msgFileName") + ": </strong>");
                    ret.append( rsversions.getString("rep_fileName") + "</p>");
                    /////////////////////////////////////

                    ret.append("<p><strong>" + paramsRequest.getLocaleString("msgFileType") + ": </strong>");
                    ret.append(getFileType(rsversions.getString("rep_fileName")) + "</p>");

                    ret.append("<p><strong>" + paramsRequest.getLocaleString("msgSize") + ": </strong>");
                    int size = rsversions.getInt("rep_fileSize") / 1024;
                    if (size == 0) {
                        size = 1;
                    }
                    ret.append( size + "k</p>");

                    if (repstatus == 1 && repemailCOut != null) {
                        ret.append("<p><strong>" + paramsRequest.getLocaleString("msgReservedBy") + ": </strong>");
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

                        ret.append(usercheckout + checkOutDate + "</p>");;
                    }

                    if (nivel >= 2 && user.isSigned()) {

                        ret.append("<p class=\"acciones\">");
                        if (repstatus == 0) {
                            ret.append("<a class=\"out\" href=\"javascript: doCheckout(" + i_log + "," + repdocid + ");\"><span>out</span></a>");
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
                                ret.append("<a  class=\"in\" href=\"javascript: doCheckin(" + i_log + "," + repdocid + ");\"><span>in</span></a>");
                                ret.append("&nbsp;<a  class=\"undo\" href=\"javascript: doUndocheckout(" + i_log + "," + repdocid + ");\"><span>undo</span></a>");
                            } else {
                                if (nivel == 3) {
                                    ret.append("<a  class=\"undo\"  href=\"javascript: doUndocheckout(" + i_log + "," + repdocid + ");\"><span>undo</span></a>");
                                } else {
                                    ret.append("<a href=\"#\"  class=\"reser\"  title=\"" + paramsRequest.getLocaleString("msgReserved") + "\" ><span>reservado</span></a>");
                                }
                            }
                        }
                    }
                    if (!hideSuscriptions && user.getId() != null && user.isSigned()) {
                        if (!subcriptions.contains(new Long(0))) { //dir

                            if (!subcriptions.contains(new Long(repdocid))) {
                                ret.append("<a  class=\"suscribir\"  href=\"javascript: doSuscribeDoc(" + i_log + "," + repdocid + ");\" title=\"" + paramsRequest.getLocaleString("msgSuscribe") + "\"><span>suscribir</span></a>");
                            } else {
                                ret.append("<a  class=\"nosuscribir\"  href=\"javascript: doUnsuscribeDoc(" + i_log + "," + repdocid + ");\" title=\"" + paramsRequest.getLocaleString("msgUnSuscribe") + "\" ><span>no suscribir</span></a>");
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
                                ret.append("<a  class=\"mover\"  href=\"javascript: doMoveDocDir(" + i_log + "," + repdocid + ");\" title=\"" + paramsRequest.getLocaleString("msgALTMove") + "\" ><span>Mover</span></a>");
                            }
                            ret.append("<a  class=\"eliminar\"  href=\"javascript: doDelete(" + i_log + "," + repdocid + ");\" title=\"" + paramsRequest.getLocaleString("msgAltDelete") + "\" ><span>Eliminar</span></a>");
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
                            ret.append("<a class=\"ver\" href=\"javascript: doViewInLine(" + tmp + "," + repdocid + ");\" title=\"" + paramsRequest.getLocaleString("msgAltPreview") + "\" ><span>Ver</span></a>");
                        } else {
                            ret.append("<a class=\"ver\" href=\"javascript: doView(" + tmp + "," + repdocid + ");\" title=\"" + paramsRequest.getLocaleString("msgAltPreview") + "\" ><span>Ver</span></a>");
                        }
                    }
                    ret.append("</p>");

                    ret.append("<p class=\"botones\">");
                    if (canupdate && user.isSigned()) {
                        ret.append("<input  class=\"aceptar\" type='button' name='s' value='" + paramsRequest.getLocaleString("msgBTNSubmit") + "' onClick='javascript:valida(" + i_log + ")' />&nbsp;");
                    }
                    ret.append("<input  class=\"aceptar\" type='button' name='n' value='" + paramsRequest.getLocaleString("msgBTNViewAllFiles") + "' onClick='javascript:init()' />&nbsp;");
                    if (canupdate && user.isSigned()) {
                        ret.append("<input  class=\"cancelar\" type='button' name='c' value='" + paramsRequest.getLocaleString("msgBTNCancel") + "' onClick='javascript:init()'>");
                    }
                    ret.append("</p>");

                }
            }
            ps.close();

            if (user.isSigned()) {
                ret.append("</form>");
            }
            
            ret.append("\n</div>");
            
            ret.append("<script type='text/javascript'>\r\n");
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
            Repository.log.error("Error on info", e);
            return ret.toString();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    Repository.log.error("Error on info", e);
                }
            }
        }
        return ret.toString();

    }

    /**
     * Shows a list of existing files in the directory
     *
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
            String topicid = dir != null ? dir.getId() : "";
            String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
            con = SWBUtils.DB.getConnection(tmp_conn, "RepositoryFile.showFiles()");
            
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
            ret.append("\n<script type='text/javascript'>");
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

            ret.append("\n<form class=\"oculto\" name=\"frmparameter\" method=\"post\" action=\"" + url.toString() + "\">");

            ret.append("\n<input type=\"hidden\" name=\"repfop\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"reptp\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repacc\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repobj\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repfiddoc\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repdocid\" value=\"\">");
            ret.append("\n<input type=\"hidden\" name=\"repordid\" value=\"\">");
            ret.append("\n</form>");
            ret.append("\n<table>");
            ret.append("\n<caption>");
            ret.append("\n<span>" + foldername+"</span> ");
            if (!hideSuscriptions && user.getId() != null && user.isSigned()) {
                if (!subcriptions.contains(new Long(0))) {
                    ret.append("<a  class=\"suscribir\" href=\"javascript: doSuscribe(" + i_log + ");\" title=\"" + paramsRequest.getLocaleString("msgSuscribeDirectory") + "\"><span>" + paramsRequest.getLocaleString("msgSuscribeDirectory") + "</span></a>");
                } else {
                    ret.append("<a class=\"nosuscribir\" href=\"javascript: doUnsuscribe(" + i_log + ");\" title=\"" + paramsRequest.getLocaleString("msgUnsuscribeDirectory") + "\" ><span>" + paramsRequest.getLocaleString("msgUnsuscribeDirectory") + "</span></a>");
                }
            }
            if (nivel >= 2 && user.isSigned()) {
                ret.append("\n<a class=\"agregar\" href=\"javascript: doAddFile(" + i_log + ");\" title=\"" + paramsRequest.getLocaleString("msgAltAdd") + "\"><span>" + paramsRequest.getLocaleString("msgALTAddFile") + "</span></a>");
            }
            ret.append("\n</caption>");

            ret.append("\n<thead>");
            ret.append("\n<tr>");
            ret.append("\n<th class=\"info\">Información</th>");
            ret.append("\n<th class=\"archivo\"><a href=\"#\" onclick=\"javascript: doResOrderTitle();\" >" + paramsRequest.getLocaleString("msgTitle") + "</a></th>");
            ret.append("\n<th class=\"fecha\"><a  href=\"#\" onclick=\"javascript: doResOrderDate();\">" + paramsRequest.getLocaleString("msgDate") + "</a></th>");
             if (nivel >= 2) {
                 ret.append("\n<th class=\"marcar\">" + paramsRequest.getLocaleString("msgCheck") + "</th>");
            } else {
                 ret.append("\n<th class=\"marcar\">&nbsp;</th>");
            }
            ret.append("\n<th class=\"accion\">" + paramsRequest.getLocaleString("msgAction") + "</th>");
            ret.append("\n</tr>");
            ret.append("\n</thead>");

            ret.append("\n<tbody>");
            
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

                    ret.append("\n<td class=\"info\"><a href=\"#\" onclick=\"javascript: doInfo(" + repdocid + ");\" title=\""+paramsRequest.getLocaleString("msgInfo")+"\">" + "<span>Más información" + "</span></a></td>");
                    
                    String file = "default";
                    String type = "";
                    String classStyle = "";
                    if (repfiletype != null) {
                        type = rsversions.getString("rep_fileName");
                        file = getFileName(type);
                        classStyle = getFileStyleClass(type);
                    }

                    SWBResourceURL urllineA = paramsRequest.getRenderUrl();
                    urllineA.setCallMethod(urllineA.Call_DIRECT);
                    
                    ret.append("\n<td class=\"archivo\">");
                    if (i_log == 1 || supportGuestUser() == 1) {
                        ret.append("<a  class=\""+classStyle+"\" target='_new' href='" + urllineA + "/" + type + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + Long.toString(repdocid) + "&repinline=true' title=\"" + getFileType(type) + "\"><span>" + reptitle+"</span></a>");
                    } else {
                        ret.append("\n<a class=\""+classStyle+"\" href=\"#\" onclick=\"javascript:alert('Debes estar firmado para poder ver este archivo.');\" title=\"" + getFileType(type) + "\"><span>" + reptitle+"</span></a>");
                    }

                    ret.append("\n</td>");

                    Timestamp repcreate = rsversions.getTimestamp("rep_create");
                    String date = repcreate.toString();
                    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new java.util.Locale("es"));
                    date = df.format(new Date(repcreate.getTime()));
                    ret.append("\n<td  class=\"fecha\">" + date + "</td>");

                    ret.append("<td class=\"marcar\">");
                    if (nivel >= 2 && user.isSigned()) {
                        if (repstatus == 0) {
                            
                            ret.append("<a  class=\"out\" href=\"#\" onclick=\"javascript: doCheckout(" + i_log + "," + repdocid + ");\" title='" + paramsRequest.getLocaleString("msgCOut") + "'><span>out</span></a>");

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
                            String strReservado = ""; 

                            strReservado =  "<a href=\"#\" class=\"reser\"><span>"+paramsRequest.getLocaleString("img_Reserved") +"</span></a> ";
                            if (cancheckin) {
                                ret.append("\n<a  class=\"in\" href=\"#\" onclick=\"javascript: doCheckin(" + i_log + "," + repdocid + ");\" title'" + paramsRequest.getLocaleString("msgCIn") + "'> ");
                                ret.append("\n<span>in</span></a>");
                                ret.append("<a class=\"undo\" href=\"#\" onclick=\"javascript: doUndocheckout(" + i_log + "," + repdocid + ");\" title='" + paramsRequest.getLocaleString("msgUndoCOut") + "' ><span>undo</span></a>");
                            } else {
                                if (nivel == 3) {
                                    ret.append(strReservado);
                                    ret.append("<a class=\"undo\" href=\"#\" onclick=\"javascript: doUndocheckout(" + i_log + "," + repdocid + ");\" title='" + paramsRequest.getLocaleString("msgUndoCOut") + "' ><span>undo</span></a>");
                                } else {
                                    ret.append(strReservado);
                                }
                            }
                        }
                    }
                    ret.append("\n</td>");
                    ret.append("\n<td class=\"accion\">");
                    if (!hideSuscriptions && user.getId() != null && user.isSigned()) {
                        if (!subcriptions.contains(new Long(0))) //dir
                        {
                            if (!subcriptions.contains(new Long(repdocid))) {
                                ret.append("<a href=\"#\" class=\"suscribir\" onclick=\"javascript: doSuscribeDoc(" + i_log + "," + repdocid + ");\" title=\"" + paramsRequest.getLocaleString("msgSuscribe") + "\"><span>Susribir</span></a>");
                            } else {
                                ret.append("<a href=\"#\" class=\"nosuscribir\" onclick=\"javascript: doUnsuscribeDoc(" + i_log + "," + repdocid + ");\" title='" + paramsRequest.getLocaleString("msgUnSuscribe") + "' ><span>Cancelar susribir</span></a>");
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
                                ret.append("<a href=\"#\" class=\"mover\" title=\"" + paramsRequest.getLocaleString("msgALTMove") + "\" onclick=\"javascript: doMoveDocDir(" + i_log + "," + repdocid + ");\"><span>Mover</span></a>");
                            }
                            ret.append("<a href=\"#\" class=\"eliminar\" title=\"" + paramsRequest.getLocaleString("msgALTDelete") + "\" onclick=\"javascript: doDelete(" + i_log + "," + repdocid + ");\"><span>Eliminar</span></a>");
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

                            if (i_log == 1 || supportGuestUser() == 1) {
                                ret.append("<a  class=\"ver\" title=\"" + paramsRequest.getLocaleString("msgALTPreview") + "\" target='_new' href='" + urlline + "/" + type + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + Long.toString(repdocid) + "&repinline=true'><span>Ver</span></a>");
                            } else {
                                ret.append("<a href=\"#\" class=\"ver\" title=\"'" + paramsRequest.getLocaleString("msgALTPreview") + "\" onclick='javascript:alert(\"Debes estar firmado para poder ver este archivo.\")'><span>Ver</span></a>");
                            }
                        } else {
                            int tmp = i_log;
                            if (supportGuestUser() == 1) {
                                tmp = 1;
                            }
                            ret.append("<a href=\"#\" class=\"ver\" title=\"ver\" onclick=\"javascript: doView(" + tmp + "," + repdocid + ",'" + type + "');\"><span>Ver</span></a>");
                        }
                    }
                    ret.append("</td>");
                    
                }
                rsversions.close();
                ps2.close();
                ret.append("</tr>");
            }
            

            ret.append("\n</tbody>");
            ret.append("\n<tfoot>");
            ret.append("\n<tr>");
            ret.append("\n<td colspan=\"5\">" + i_tot + " file(s)</td>");
            ret.append("\n</tr>");
            ret.append("\n</tfoot>");

            rs.close();
            ps.close();
            ret.append("</table>");
        } catch (SQLException e) {
            Repository.log.error("Error on showfiles", e);
            return ret.toString();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    Repository.log.error("Error on showfiles", e);
                }
            }
        }
        return ret.toString();
    }

    /**
     * Get the file name icon to represents the file type
     *
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
        } else if (type.indexOf(".doc") != -1 || type.indexOf(".docx") != -1 || type.indexOf(".rtf") != -1) {
            file = "ico_word.gif";
        } else if (type.indexOf(".xml") != -1 || type.indexOf(".xsl") != -1 || type.indexOf(".xslt") != -1) {
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
    
        public String getFileStyleClass(String filename) {
        String file = "default";
        String type = filename.toLowerCase();
        if (type.indexOf(".bmp") != -1 || type.indexOf(".gif") != -1 || type.indexOf(".img") != -1|| type.indexOf(".jpg") != -1|| type.indexOf(".jpeg") != -1|| type.indexOf(".tif") != -1|| type.indexOf(".tiff") != -1|| type.indexOf(".png") != -1) {
            file = "img";
        } else if (type.indexOf(".pdf") != -1) {
            file = "pdf";
        } else if (type.indexOf(".xls") != -1 || type.indexOf(".xlsx") != -1) {
            file = "xls";
        } else if (type.indexOf(".html") != -1 || type.indexOf(".htm") != -1) {
            file = "htm";
        } else if (type.indexOf("vsd") != -1 || type.indexOf("vsdx") != -1) {
            file = "vsd";
        } else if (type.indexOf(".ppt") != -1 || type.indexOf(".pptx") != -1) {
            file = "ppt";
        } else if (type.indexOf(".exe") != -1) {
            file = "exe";
        } else if (type.indexOf(".txt") != -1 || type.indexOf(".properties") != -1) {
            file = "txt";
        } else if (type.indexOf(".doc") != -1 || type.indexOf(".docx") != -1 || type.indexOf(".rtf") != -1) {
            file = "doc";
        } else if (type.indexOf(".xml") != -1 || type.indexOf(".xsl") != -1 || type.indexOf(".xslt") != -1) {
            file = "xml";
        } else if (type.indexOf(".mmap") != -1 || type.indexOf(".mm") != -1 || type.indexOf(".xmind") != -1) {
            file = "mmap";
        } else if (type.indexOf(".mpp") != -1 ) {
            file = "mpp";
        } else if (type.indexOf(".avi") != -1 || type.indexOf(".mpeg") != -1  || type.indexOf(".mov") != -1  || type.indexOf(".mts") != -1  ) {
            file = "avi";
        } else if (type.indexOf(".mp3") != -1 || type.indexOf(".mp4") != -1  || type.indexOf(".wma") != -1  || type.indexOf(".aac") != -1 || type.indexOf(".wav") != -1 ) {
            file = "mp3";
        } else if (type.indexOf(".zip") != -1 || type.indexOf(".rar") != -1) {
            file = "zip";
        }
        return file;
    }
        
    /**
     * Get the file type
     *
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
        } else if (type.indexOf(".doc") != -1 || type.indexOf(".docx") != -1 || type.indexOf(".rtf") != -1) {
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
        } else if (type.indexOf(".xsl") != -1 || type.indexOf(".xslt") != -1) {
            file = "XSLT file";
        }
        return file;
    }

    /**
     * Get the existing versions of the file
     *
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

            String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
            con = SWBUtils.DB.getConnection(tmp_conn, "RepositoryFile.history()");
            
            ps = con.prepareStatement("select * from resrepositoryversions where rep_docId=? and idtm=?");
            ps.setLong(1, id);
            ps.setString(2, dir.getWebSiteId());
            ResultSet rs = ps.executeQuery();

            int numcols=7;
            String tp = dir.getId();
            ret.append("\n<div id=\"file\">");
            ret.append("\n<table>");
            ret.append("\n<caption>");
            ret.append("\n<span>" + dir.getDisplayName() + "</span>");
            ret.append("\n</caption>");
             ret.append("\n<thead>");
            ret.append("\n<tr>");
            ret.append("\n<th class=\"accion\">&nbsp;</th>");
            ret.append("\n<th class=\"info\">" + paramsRequest.getLocaleString("msgVersion") + "</th>");
            ret.append("\n<th class=\"info\">" + paramsRequest.getLocaleString("msgAuthor") + "</th>");
            ret.append("\n<th class=\"info\">" + paramsRequest.getLocaleString("msgComments") + "</th>");
            ret.append("\n<th class=\"archivo\">" + paramsRequest.getLocaleString("msgFileName") + "</th>");
            ret.append("\n<th class=\"fecha\">" + paramsRequest.getLocaleString("msgLastUpdate") + "</th>");
            ret.append("\n<th class=\"info\">" + paramsRequest.getLocaleString("msgSize") + "</th>");
            if (nivel >= 1) {
                ret.append("\n<th class=\"accion\">" + paramsRequest.getLocaleString("msgView") + "</th>");
                numcols=8;
            }
            ret.append("\n</tr>");
            ret.append("\n</thead>");
            ret.append("\n<tbody>");

            while (rs.next()) {
                file = "file.gif";

                String repemail = rs.getString("rep_email");
                String reptype = rs.getString("rep_fileType");
                if (reptype != null) {
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
                        Repository.log.error("Error on method history class RepositoryFile trying to create new user with email" + ": " + dir.getWebSite().getUserRepository().getUser(repemail).getEmail(), e);
                    }
                }
                SWBResourceURL urlrecdoc = paramsRequest.getRenderUrl();
                urlrecdoc.setCallMethod(urlrecdoc.Call_DIRECT);
                ret.append("\n<tr>");
                ret.append("\n<td class=\"archivo\"><a class=\""+getFileStyleClass(type)+"\" href='" + urlrecdoc.toString() + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + rs.getString("rep_docId") + "&repfversion=" + rs.getString("rep_fileVersion") + "'><span>&nbsp;</span></a></td>");
                ret.append("\n<td class=\"archivo\"><span>" + rs.getString("rep_fileVersion") + "</span></td>");
                ret.append("\n<td class=\"archivo\"><span>" + s_author + "</span></td>");
                ret.append("\n<td class=\"archivo\"><span>" + rs.getString("rep_comment") + "</span></td>");
                ret.append("\n<td class=\"archivo\"><span>" + rs.getString("rep_fileName") + "</span></td>");
                Timestamp repcreate = rs.getTimestamp("rep_create");
                date = repcreate.toString();
                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new java.util.Locale("es"));
                date = df.format(new Date(repcreate.getTime()));

                ret.append("\n<td class=\"fecha\">" + date + "</td>");


                int size = rs.getInt("rep_fileSize") / 1024;
                if (size == 0) {
                    size = 1;
                }
                ret.append("\n<td class=\"archivo\"><span>" + size + " Kb</span></td>");

                if (nivel >= 1) {
                    ret.append("<td class=\"accion\">");
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
                        ret.append("\n<a class=\"ver\" target='_new' href='" + urlrec.toString() + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + rs.getString("rep_docId") + "&repfversion=" + rs.getString("rep_fileVersion") + "&repinline=true' title='" + paramsRequest.getLocaleString("msgALTPreview") + "'><span>" + paramsRequest.getLocaleString("msgALTPreview") + "</span></a>");
                    } else {
                        SWBResourceURL urlrec = paramsRequest.getRenderUrl();
                        urlrec.setCallMethod(urlrec.Call_DIRECT);
                        ret.append("\n<a class=\"ver\" href='" + urlrec.toString() + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + rs.getString("rep_docId") + "&repfversion=" + rs.getString("rep_fileVersion") + "' title='" + paramsRequest.getLocaleString("msgALTPreview") + "'><span>" + paramsRequest.getLocaleString("msgALTPreview") + "</span></a>");
                    }
                    ret.append("\n</td>");
                }
                ret.append("\n</tr>");
                
            }
            
            rs.close();
            ps.close();
             ret.append("\n</tbody>");
            ret.append("\n</table>");
            
            ret.append("\n<p class=\"botones\"><input  class=\"aceptar\" type='button' name='n' value='" + paramsRequest.getLocaleString("msgBTNViewAllFiles") + "' onClick='javascript:init()' /></p>");
           
            ret.append("\n</div>");
            ret.append("<script type='text/javascript'>\r\n");
            ret.append("function textCounter(field,  maxlimit) {\r\n");
            ret.append("if (field.value.length > maxlimit)\r\n");
            ret.append("field.value = field.value.substring(0, maxlimit);\r\n");
            ret.append("}\r\n");
            ret.append("function init(){\r\n");
            ret.append("document.location='" + url.toString() + "?reptp=" + dir.getId() + "';\r\n");
            ret.append("}\r\n");
            ret.append("</script>\r\n");
        } catch (SQLException e) {
            Repository.log.error("Error on history", e);
            return ret.toString();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    Repository.log.error("Error on history", e);
                }
            }
        }
        return ret.toString();
    }

    /**
     * Add new file into the repository
     *
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
            Repository.log.error("Error on insert", ioe);
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

            String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
            con = SWBUtils.DB.getConnection(tmp_conn, "RepositoryFile.insert()");
            
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


            filename = SWBUtils.TEXT.replaceSpecialCharacters(strfileName, true) + strFileExt;

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

            //  INDEXAR ARCHIVO

            File f = new File(SWBPortal.getWorkPath() + "/" + resource.getWorkPath() + "/" + file_db);

            if (f.exists()) {
                try {
                    String type = f.getName();
                    SWBResourceURL urllineA = paramsRequest.getRenderUrl();
                    urllineA.setCallMethod(SWBResourceURL.Call_DIRECT);
                    String url = "" + urllineA + "/" + type + "?repfop=view&reptp=" + dir.getId() + "&repfiddoc=" + Long.toString(docid) + "&repinline=true";
                    FileSearchWrapper fsw = new FileSearchWrapper(f, title, description, null, url, resource);
                    SWBPortal.getIndexMgr().getDefaultIndexer().indexSerchable(fsw);
                } catch (Exception ex_ind) {
                    Repository.log.error("Error while trying to index file.", ex_ind);
                }
            }

            //  TERMINA INDEXAR ARCHIVO

        } catch (Exception e) {
            if (filename != null) {
                File fdoc = new File(SWBPortal.getWebWorkPath() + "/" + filename);
                if (fdoc.exists()) {
                    fdoc.delete();
                }
            }
            Repository.log.error("Error on insert", e);
            return ret.toString();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    Repository.log.error("Error on insert", e);
                }
            }
        }
        return ret.toString();
    }

    /**
     * Add file form to upload a new file into the repository
     *
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
        
        ret.append("\n<p>Subir archivo a " + dir.getDisplayName() + "</p>");
        ret.append("\n<div id=\"agregar\">");
        ret.append("\n");
        ret.append("\n<form name='frmnewdoc' method='POST' enctype='multipart/form-data' action='" + url.toString() + "'>");
        ret.append("\n<input type='hidden' name='repfop' value='insert'>");
        
        ret.append("\n<p><label for=\"agregatit\">" + paramsRequest.getLocaleString("msgTitleDocument") + ": </label>");
        ret.append("\n<input  type='text' maxlength='99' name='repftitle' id=\"agregatit\" />");
        ret.append("\n</p>");

        ret.append("\n<p><label for=\"agregades\">" + paramsRequest.getLocaleString("msgDescription") + ": </label>");
        ret.append("\n<textarea id=\"agregades\" rows='5' name='repfdescription' cols='20' onKeyDown='textCounter(this.form.repfdescription,255);' onKeyUp='textCounter(this.form.repfdescription,255);'></textarea>");
        ret.append("\n</p>");

        ret.append("\n<p><label for=\"agregafile\">Archivo:</label>" + paramsRequest.getLocaleString("msgFile") + ": </label>");
        ret.append("\n<input type='file'  name='repfdoc' id=\"agregafile\" />");
        ret.append("\n</p>");

        ret.append("\n<p class=\"botones\">");
        ret.append("\n<input class=\"aceptar\" type='button'  name='s' value='" + paramsRequest.getLocaleString("msgBTNSave") + "' onclick='javascript:valida();' />\r\n");
        ret.append("\n<input class=\"cancelar\"  type='button'  name='cancel' value='" + paramsRequest.getLocaleString("msgBTNCancel") + "' onclick='javascript:init();' />\r\n");
        ret.append("\n</p>");

        ret.append("\n</form>");
        ret.append("<script type='text/javascript'>\r\n");
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
     *
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
            String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
            con = SWBUtils.DB.getConnection(tmp_conn, "RepositoryFile.saveLog()");
            
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
            Repository.log.error("Error while trying to create a resource record at RepositoryFile:saveLog", e);
        } finally {
            ps = null;
            con = null;
        }
    }

    /**
     * Move a file from one folder to another
     *
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
                String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
                conn = SWBUtils.DB.getConnection(tmp_conn, "RepositoryFile.moveDoc2Dir()");
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
                Repository.log.error("Error while trying to move document with id: " + id + " from directory " + fromDir.getId() + " to directory " + toDir.getId(), e);
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
