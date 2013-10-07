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
 * Repository.java
 *
 * Created on 19 de abril de 2004, 01:13 PM
 */
package com.infotec.wb.resources.repository;

import com.infotec.topicmaps.BaseName;
import javax.servlet.http.*;
import javax.xml.transform.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.indexer.FileSearchWrapper;
import org.semanticwb.portal.indexer.SWBIndexer;
import org.semanticwb.portal.util.FileUpload;


import org.w3c.dom.*;

/**
 * Control de documentos, en este recurso se pueden agregar documentos, sacarlos
 * para su revisi�n � modificaci�n, subir archivos actualizados, eliminarlos del
 * repositorio, teniendo la posibilidad de recuperarlos a trav�s del
 * administrador, cuenta con historial de las diferentes versiones de cada
 * documento. Cuenta con vista preliminar del documento. En la parte
 * administrativa se maneja que el usuario cumpla con diferentes roles para ver,
 * modificar o borrar elementos del repositorio. Se cuenta tambi�n con la
 * configuraci�n de mensajes de notificaci�n.
 *
 * A document control, in this resource can add new documents, make check out
 * for a document revision or document modification, upload updated document,
 * erase from the repository, and have the posibility to restore eraded
 * documents via an administrator user. Also have a document preview. In the
 * administration side, the administrator define the user roles to perform a
 * view, modify or delete repository elements. Also have the configuration of
 * the message notification.
 *
 * @author Javier Solis Gonzalez
 */
public class Repository extends org.semanticwb.portal.api.GenericResource {

    public static final Logger log = SWBUtils.getLogger(Repository.class);
    org.semanticwb.model.Resource base = null;
    TreeRepHtml tree = new TreeRepHtml();
    RepositoryFile files = new RepositoryFile();
    AdmTopics admtp = new AdmTopics();
    Notification notify = new Notification();
    Templates plt = null;
    Transformer trans = null;
    String strRscType = "Repository";

    /**
     * Creates a new instance of Repository
     */
    public Repository() {
    }

    /**
     * Get the user level
     *
     * @param user A WBUser object
     * @param tmid TopicMap identifier
     * @return The level of the user
     */
    public int getLevelUser(User user, String tmid) {
        int level = 0;
        String adm = base.getAttribute("admin");
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
            String mdy = base.getAttribute("modify");
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
            String viw = base.getAttribute("view");
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

    public int supportGuestUser() {
        int level = 0;
        String viw = base.getAttribute("view");
        if (viw == null) // quiere decir que view es ninguno
        {
            String guest = base.getAttribute("guest");
            if (guest != null && guest.equals("true")) {
                level = 1;
            }
        }
        return level;
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

        //PrintWriter out = response.getWriter();
        StringBuffer ret = new StringBuffer();
        User user = paramsRequest.getUser();
        WebPage topic = paramsRequest.getWebPage();
        WebPage dir = null;
        String s_message = null;
        String repobj = request.getParameter("repobj");
        String repacc = request.getParameter("repacc");
        String reptp = request.getParameter("reptp");
        WebSite ws = null;

        String repfop = request.getParameter("repfop");
        String home = "CNFWB_Rep" + base.getId();
        String sub = base.getAttribute("showdirectory", "true");
        HashMap arguments = null;
        int level = getLevelUser(user, paramsRequest.getWebPage().getWebSiteId());
        int i_log = 0;
        if (user.isSigned()) {
            i_log = 1;
        } else {
            i_log = supportGuestUser();
        }
        //System.out.println("Support Guest user: "+supportGuestUser());
        // quitar para quitar la validaci�n de usuario firmado
        //i_log=1;

        String path1 = "" + SWBPortal.getContextPath() + "/swbadmin/images/Repository/";

        Document dom = null;
        //xml definition
        try {
            dom = SWBUtils.XML.getNewDocument();

            Element resource = dom.createElement("resource");
            dom.appendChild(resource);
            //xml definition

            if (paramsRequest.getCallMethod() == paramsRequest.Call_STRATEGY) {
                if (reptp == null) {
                    reptp = paramsRequest.getWebPage().getId();
                }
            } else {
                if (reptp == null) {
                    if (paramsRequest.getWebPage().getId().equals(paramsRequest.getAdminTopic().getId())) {
                        reptp = home;
                    } else {
                        reptp = paramsRequest.getWebPage().getId();
                    }
                }
            }
            //System.out.println(reptp);
            try {
                dir = topic.getWebSite().getWebPage(reptp);
            } catch (Exception e) {
                log.error(e);
            }

            if (null == dir) {
                ws = topic.getWebSite();
                dir = ws.createWebPage(reptp);
                dir.setTitle(paramsRequest.getWebPage().getTitle());
                dir.setIndexable(false);
            }
            String path = SWBPlatform.getContextPath() + "/swbadmin/images/Repository/";
//            ret.append("\n<script type=\"text/javascript\" >");
//            ret.append("\n  loadjscssfile(\"" + path + "css-repositorio.css\", \"css\") ;      ");
//            ret.append("\n</script>");


            if (sub.equals("false")) {

                ret.append("\n<script type=\"text/javascript\">");
                ret.append("\n  loadjscssfile(\"" + path + "css-repositorio.css\", \"css\") ;      ");
                ret.append("\n</script>");
                ret.append("\n<div  id=\"repositorio\" >");
                ret.append("\n<div id=\"titulo\">");
                ret.append("\n<p>" + base.getTitle() + "</p>");
                ret.append("\n</div>");
                if (i_log == 1) {
                    ret.append("<div id=\"file\">");
                    if ("Notification".equals(repobj)) {
                        ret.append(notify.getHtml(request, response, user, topic, arguments, dir, paramsRequest));
                    } else {
                        ret.append(files.getHtml(request, response, user, topic, arguments, dir, level, paramsRequest));
                    }
                    ret.append("</div>");
                } else {
                    ret.append("<p>" + paramsRequest.getLocaleString("msgMostSigned") + "</p>");
                }
                ret.append("</div>");
            } else {

                String ver = repobj;
                if (ver == null) {
                    ver = "";
                }
                if ("MoveDoc".equals(ver)) {

                    ret.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
                    ret.append("<html>\n");
                    ret.append(" <head>\n");
                    ret.append("<link href=\"" + path + "css-repositorio.css\" rel=\"stylesheet\" type=\"text/css\" />");
                    ret.append("\n<style>");
                    ret.append("#dir  { width:100%; }\n");
                    ret.append("\n</style>");
                    ret.append("\n</head><body>");
                    ret.append("\n <div  id=\"repositorio\" >");
                    ret.append("\n<div id=\"titulo\">");
                    ret.append("\n<p>" + base.getTitle() + "</p>");
                    ret.append("\n</div>");
                    ret.append("<div id=\"dir\">");
                    tree.setResourceBase(getResourceBase());
                    if (i_log == 1) {
                        ret.append(tree.getDirs(request, response, user, topic, arguments, topic.getWebSite().getWebPage(home), paramsRequest));
                    }
                    ret.append("\n</div>");
                    ret.append("\n</div>");
                    ret.append("\n</body></html>");
                } else {

                    ret.append("\n<script type=\"text/javascript\">");
                    ret.append("\n  loadjscssfile(\"" + path + "css-repositorio.css\", \"css\") ;      ");
                    ret.append("\n</script>");
                    boolean salir = false;
                    //Leer archivo directo
                    if ("view".equals(repfop)) {

                        if (dir != null) {
                            if (i_log == 1) {
                                ret.append(files.getHtml(request, response, user, topic, null, dir, level, paramsRequest));
                            }
                        }
                        salir = true;
                    }
                    if (!salir) {
                        if (user.isSigned()) {
                            i_log = 1;
                        } else {
                            i_log = supportGuestUser();
                        }

                        s_message = paramsRequest.getLocaleString("msgAlertMostBeLogged");
                        ret.append("\n<script type=\"text/javascript\">");
                        ret.append("\n  function doCreateTopic(ivar){");
                        ret.append("\n      if( ivar == 0){");
                        ret.append("\n          alert('" + s_message + "');");
                        ret.append("\n      }");
                        ret.append("\n      else{");
                        ret.append("\n          window.document.frmadmtopic.repobj.value = 'AdmTopics';");
                        ret.append("\n          window.document.frmadmtopic.repacc.value = 'create';");
                        ret.append("\n          window.document.frmadmtopic.reptp.value = '" + reptp + "';");
                        ret.append("\n          window.document.frmadmtopic.submit();");
                        ret.append("\n      }");
                        ret.append("\n  }");
                        ret.append("\n  function doDeleteTopic(ivar){");
                        ret.append("\n      if( ivar == 0){");
                        ret.append("\n          alert('" + s_message + "');");
                        ret.append("\n      }");
                        ret.append("\n      else{");
                        ret.append("\n          if(confirm('" + paramsRequest.getLocaleString("msgAlertShureDelete") + "?')){");
                        ret.append("\n              window.document.frmadmtopic.repobj.value = 'AdmTopics';");
                        ret.append("\n              window.document.frmadmtopic.repacc.value = 'remove';");
                        ret.append("\n              window.document.frmadmtopic.reptp.value = '" + reptp + "';");
                        ret.append("\n              window.document.frmadmtopic.submit();");
                        ret.append("\n          }");
                        ret.append("\n      }");
                        ret.append("\n  }");
                        ret.append("\n  function doChangeTopic(ivar){");
                        ret.append("\n      if( ivar == 0){");
                        ret.append("\n          alert('" + s_message + "');");
                        ret.append("\n      }");
                        ret.append("\n      else{");
                        ret.append("\n          window.document.frmadmtopic.repobj.value = 'AdmTopics';");
                        ret.append("\n          window.document.frmadmtopic.repacc.value = 'changeName';");
                        ret.append("\n          window.document.frmadmtopic.reptp.value = '" + reptp + "';");
                        ret.append("\n          window.document.frmadmtopic.submit();");
                        ret.append("\n      }");
                        ret.append("\n  }");
                        ret.append("\n  function regresa(fiddoc,origen,destino,repobj,docid){");
                        ret.append("\n      document.frmMoveDoc.repfop.value=fiddoc;");
                        ret.append("\n      document.frmMoveDoc.reptp_original.value=origen;");
                        ret.append("\n      document.frmMoveDoc.reptp.value=destino;");
                        ret.append("\n      document.frmMoveDoc.repobj.value='0';");
                        ret.append("\n      document.frmMoveDoc.repiddoc.value=docid;");
                        ret.append("\n      document.frmMoveDoc.submit();");
                        ret.append("\n  }");
                        ret.append("\n</script>");

                        SWBResourceURL urlTopic = paramsRequest.getRenderUrl();
                        urlTopic.setMode(paramsRequest.Mode_VIEW);

                        // forma que se utiliza para cuando se quiere cambiar un archivo a un directorio diferente.
                        ret.append("\n<form class=\"oculto\" action=\"" + urlTopic.toString() + "\" method=post name=\"frmMoveDoc\">");
                        ret.append("\n<input type=hidden name=\"repfop\" value=\"\">");
                        ret.append("\n<input type=hidden name=\"reptp_original\" value=\"\">");
                        ret.append("\n<input type=hidden name=\"reptp\" value=\"\">");
                        ret.append("\n<input type=hidden name=\"repobj\" value=\"\">");
                        ret.append("\n<input type=hidden name=\"repiddoc\" value=\"\">");
                        ret.append("\n</form>");

                        ret.append("\n<form class=\"oculto\" name=\"frmadmtopic\" method=\"post\" action=\"" + urlTopic.toString() + "\">");
                        ret.append("\n<input type=\"hidden\" name=\"reptp\" value=\"\">");
                        ret.append("\n<input type=\"hidden\" name=\"repacc\" value=\"\">");
                        ret.append("\n<input type=\"hidden\" name=\"repobj\" value=\"\">");
                        ret.append("\n</form>");


                        ret.append("\n <div  id=\"repositorio\" >");
                        ret.append("\n<div id=\"titulo\">");
                        ret.append("\n<p>" + base.getTitle() + "</p>");
                        //ret.append("\n<p>" +  ((base.getDescription() != null) ? base.getDescription() : "") + "</p>");
                        ret.append("\n</div>");
                        if (i_log == 1) {
                            ret.append("\n<table class=\"dirfile\">");
                            ret.append("\n<tr>");
                            ret.append("\n<td>");
                            ret.append("<div id=\"dir\">");

                            //************************** menu directorios ********************************************
                            if (!"AdmTopics".equals(repobj)) {
                                int islog = 0;
                                if (user.isSigned()) {
                                    islog = 1;
                                }

                                ret.append("<div id=\"diropcion\">");

                                if (level > 1 && user.isSigned()) {
                                    ret.append("\n<a  class=\"crear\" href=\"#\" onclick=\"javascript: doCreateTopic(" + islog + ");\" title=\"" + paramsRequest.getLocaleString("msgAltCreateDirectory") + "\" ><span>crear</span></a>\n");
                                }
                                if (level > 2 && !home.equals(reptp)) {

                                    ret.append("\n<a class=\"eliminar\" href=\"#\" onclick=\"javascript: doDeleteTopic(" + islog + ");\" title=\"" + paramsRequest.getLocaleString("msgAltDelDirectory") + "\"><span>eliminar</span></a>\n");
                                }
                                if (level > 2 && user.isSigned()) {

                                    ret.append("\n<a class=\"editar\" href=\"#\" onclick=\"javascript: doChangeTopic(" + islog + ");\" title=\"" + paramsRequest.getLocaleString("msgAltRenDirectory") + "\"><span>editar</span></a>\n");
                                }
                                ret.append("</div>");
                            }
                            ///// directorios o carpetas
                            ret.append(tree.getHtml(request, response, user, topic, arguments, topic.getWebSite().getWebPage(home), paramsRequest));

                            ret.append("</div>");
                            ret.append("\n</td>");
                            ///////////////////////////////////////////////////  muestra archivos en el folder
                            ret.append("\n<td>");
                            ret.append("<div id=\"file\">");
                            if ("AdmTopics".equals(repobj)) {
                                if ("create".equals(repacc)) {
                                    ret.append(admtp.create(request, response, user, topic, arguments, dir, paramsRequest));
                                } else if ("createUpd".equals(repacc)) {
                                    ret.append(admtp.createUpd(request, response, user, topic, arguments, dir, paramsRequest));
                                } else if ("remove".equals(repacc)) {
                                    ret.append(admtp.remove(request, response, user, topic, arguments, dir, paramsRequest));
                                } else if ("changeName".equals(repacc)) {
                                    ret.append(admtp.changeName(request, response, user, topic, arguments, dir, paramsRequest));
                                } else if ("changeNameUpd".equals(repacc)) {
                                    ret.append(admtp.changeNameUpd(request, response, user, topic, arguments, dir, paramsRequest));
                                }
                            } else if ("Notification".equals(repobj)) {
                                ret.append(notify.getHtml(request, response, user, topic, arguments, dir, paramsRequest));
                            } else {
                                ret.append(files.getHtml(request, response, user, topic, arguments, dir, level, paramsRequest));
                            }
                            ret.append("</div>");
                            ret.append("\n</td>");
                            ret.append("\n</tr>");
                            ret.append("\n</table>");
                        } else {
                            ret.append("<p>" + paramsRequest.getLocaleString("msgMostSigned") + "</p>");
                        }
                        ret.append("</div>");
                    }
                }

            }

        } catch (Exception e) {
            log.error("Error in Repository.doView() with resouce id" + ": " + base.getId(), e);
        }

//        System.out.println("Call Method: "+(paramsRequest.getCallMethod()==paramsRequest.Call_DIRECT?"DIRECT:":"---")+paramsRequest.getCallMethod());
//        System.out.println(" -------------------------------- >>>>>>>>>>>>>>>>>");
//        Enumeration enu = request.getParameterNames();
//        while(enu.hasMoreElements())
//        {
//            String parametro = (String) enu.nextElement();
//            System.out.println(parametro+" -> "+request.getParameter(parametro));
//        }
//        System.out.println("<<<<<<<<<<<<<<<<<<<<<<");

        try {
            String param2 = request.getParameter("repobj");

            if (paramsRequest.getCallMethod() != paramsRequest.Call_DIRECT) {
                response.getWriter().println(ret.toString());
            } //            else if(paramsRequest.getCallMethod() == paramsRequest.Call_DIRECT && !sub.equals("true"))
            //            {
            //                response.getWriter().println(ret.toString());
            //            }
            else if (paramsRequest.getCallMethod() == paramsRequest.Call_DIRECT && param2 != null && param2.equals("MoveDoc")) {
                response.getWriter().println(ret.toString());
            } else {
                //System.out.println("No imprime nada...");
            }


        } catch (Exception e) {
            log.error("Error while trying to getWriter() in class Repository.doView()", e);
        }
    }

    /**
     * Load the resource information
     *
     * @param base The Resource object
     */
    public void setResourceBase(org.semanticwb.model.Resource base) {
        try {
            super.setResourceBase(base);
            this.base = base;
            tree.setResourceBase(base);
            admtp.setResourceBase(base);
            files.setResourceBase(base);
            notify.setResourceBase(base);
        } catch (Exception e) {
            log.error("Error on create DocumentBuilder method setResourceBase, resource " + strRscType + " id " + ": " + base.getId(), e);
        }
    }

    /**
     * Create the Repository resource tables in the data base
     *
     * @param recobj The Resource type object
     * @throws AFException An Application Framework exception
     */
    public void install(ResourceType recobj) {

        //Connection con = null;
        Statement st = null;
        Connection conn = null;
        StringTokenizer sto = null;
        String query = null;
        String file = null;
        String tmp_conn = null;
        int x = 0;

        // Comprobando si ya existen tablas en la DB
        boolean existe = false;
        try {
            tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
            conn = SWBUtils.DB.getConnection(tmp_conn, "Repository.install()");
            PreparedStatement pst1 = conn.prepareStatement("select * from wbresourcetype where idtm<>? and objclass=? ");
            pst1.setString(1, recobj.getWebSite().getId());
            pst1.setString(2, "com.infotec.wb.resources.repository.Repository");
            ResultSet rs1 = pst1.executeQuery();
            if (rs1.next()) {
                existe = true;
            }
            rs1.close();
            pst1.close();
            conn.close();
        } catch (Exception e) {
            existe = false;
        }

        if (!existe) {
            try {
                String dbname = SWBUtils.DB.getDatabaseName().toLowerCase();
                if (dbname.lastIndexOf("hsql") > -1) {
                    dbname = "hsql";
                }
                if (dbname.lastIndexOf("informix") > -1) {
                    dbname = "informix";
                }
                if (dbname.lastIndexOf("microsoft sql server") > -1) {
                    dbname = "sqlserver";
                }
                if (dbname.lastIndexOf("mysql") > -1) {
                    dbname = "mysql";
                }
                if (dbname.lastIndexOf("adaptive server enterprise") > -1) {
                    dbname = "sybase";
                }
                if (dbname.lastIndexOf("postgresql") > -1) {
                    dbname = "postgres";
                }
                if (dbname.lastIndexOf("oracle") > -1) {
                    dbname = "oracle";
                }
                InputStream is_filesql = SWBPortal.getAdminFileStream("/swbadmin/sql/repository_script_" + dbname + ".sql");
                file = SWBUtils.IO.readInputStream((is_filesql));
                tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
                conn = SWBUtils.DB.getConnection(tmp_conn, "Repository.install() -- !existe --");
                //conn=WBUtils.getInstance().getDBConnection();
                st = conn.createStatement();
                if (file != null) {
                    sto = new StringTokenizer(file, ";");
                    while (sto.hasMoreTokens()) {
                        query = sto.nextToken();
                        try {
                            if (query.trim().length() > 0) {
                                x = st.executeUpdate(query);
                            }
                        } catch (Exception e) {
                            log.error("Error on method install of Repository resource trying to execute next code " + ": " + query, e);
                            throw e;
                        }
                    }
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (Exception e) {
                log.error("Error on method install Repository resource", e);
            } finally {
                try {
                    if (st != null) {
                        st.close();
                    }
                    st = null;
                } catch (Exception e1) {
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                    conn = null;
                } catch (Exception e2) {
                }
            }
        }
    }

    /**
     * Eliminate the Repository resource tables from the data base
     *
     * @param recobj The Resource type object
     * @throws AFException An Application Framework exception
     */
    public void uninstall(ResourceType recobj) {
        // Comprobando si ya existen tablas en la DB
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pst1 = null;
        ResultSet rs1 = null;
        try {
            //Connection conn = null;
            String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
            conn = SWBUtils.DB.getConnection(tmp_conn, "Repository.uninstall()");
            pst1 = conn.prepareStatement("select * from wbresourcetype where idtm<>? and objclass=? ");
            pst1.setString(1, recobj.getWebSite().getId());
            pst1.setString(2, "com.infotec.wb.resources.repository.Repository");
            rs1 = pst1.executeQuery();
            if (rs1.next()) {
                existe = true;
            }
            rs1.close();
            pst1.close();
            conn.close();
        } catch (Exception e) {
            existe = false;
        } finally {
            try {
                if (rs1 != null) {
                    rs1.close();
                }
                rs1 = null;
            } catch (Exception e1) {
            }
            try {
                if (pst1 != null) {
                    pst1.close();
                }
                pst1 = null;
            } catch (Exception e2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
                conn = null;
            } catch (Exception e3) {
            }
        }

        Connection con = null;
        String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
        Statement st = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        StringTokenizer sttopic = null;
        String tablename = null;
        String strsql = null;
        String strtopic = null;
        String strpartone = null;
        String strparttwo = null;
        String lresid = "0";
        int j = 0;

        HashMap hmtables = new HashMap();
        //TopicMgr tpmgr = TopicMgr.getInstance();

        try {
            // Get the related records
            //con = WBUtils.getInstance().getDBConnection();
            con = SWBUtils.DB.getConnection(tmp_conn, "Repository.uninstall() -- repositoy topics -- ");
            pst = con.prepareStatement("select resId, topic from resrepository where idtm=? group by resId, topic");
            pst.setString(1, recobj.getWebSite().getId());
            rs = pst.executeQuery();
            while (rs.next()) {
                lresid = rs.getString("resId");

                strtopic = rs.getString("topic");
                if (strtopic != null) {
                    j = 0;
                    sttopic = new StringTokenizer(strtopic, "|");
                    while (sttopic.hasMoreTokens()) {
                        if (j == 0) {
                            strpartone = sttopic.nextToken();
                        } else {
                            strparttwo = sttopic.nextToken();
                        }
                        ++j;
                    }
                    String strTopic = "CNFWB_Rep" + lresid;
                    WebPage topic = WebSite.ClassMgr.getWebSite(strpartone).getWebPage(strTopic);
                    if (topic == null) {
                        //WebSite topicMap = topic.getWebSite();
                        topic.remove();  // elimina la sección y sus hijos
                        //topicMap.removeTopicandChild(topic.getId());
                        //topicMap.update2DB();
                    }
                }
            }
            rs.close();
            pst.close();
            con.close();
        } catch (Exception exc) {
            log.error("Error while trying to remove  Repository Topics.", exc);
            rs = null;
            pst = null;
            con = null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                rs = null;
            } catch (Exception e1) {
            }
            try {
                if (pst != null) {
                    pst.close();
                }
                pst = null;
            } catch (Exception e2) {
            }
            try {
                if (con != null) {
                    con.close();
                }
                con = null;
            } catch (Exception e3) {
            }
        }



        if (!existe) {
            try {
                //con = WBUtils.getInstance().getDBConnection();
                con = SWBUtils.DB.getConnection(tmp_conn, "Repository.uninstall() -- drop repository tables -- ");
                hmtables.put("0", "resrepository");
                hmtables.put("1", "resrepositorynotify");
                hmtables.put("2", "resrepositoryversions");
                hmtables.put("3", "resrepositorylog");
                if (con != null) {
                    st = con.createStatement();
                    for (int i = 0; i < hmtables.size(); i++) {
                        tablename = (String) hmtables.get(Integer.toString(i));
                        strsql = "drop table " + tablename;
                        st.executeUpdate(strsql);
                    }
                    if (st != null) {
                        st.close();
                    }
                    if (pst != null) {
                        pst.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                }
                // eliminar folder con su contenido dentro del folder work, sites, repositorio //REVISAR RUTA
                //WBUtils.getInstance().getWorkPath()+"/"+recobj.getTopicMapId()+"/resources/"+recobj.getName()
                SWBUtils.IO.removeDirectory(SWBUtils.getApplicationPath() + "/work/models/" + recobj.getWebSite().getId() + "/Resource/" + recobj.getId());

            } catch (Exception e) {
                log.error("Error on method uninstall of Repository resource", e);
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    rs = null;
                } catch (Exception e1) {
                }
                try {
                    if (st != null) {
                        st.close();
                    }
                    st = null;
                } catch (Exception e2) {
                }
                try {
                    if (pst != null) {
                        pst.close();
                    }
                    pst = null;
                } catch (Exception e3) {
                }
                try {
                    if (con != null) {
                        con.close();
                    }
                    con = null;
                } catch (Exception e4) {
                }
            }
        } else {
            // eliminando registros correspondientes a este TM
            Connection conn2 = null;
            PreparedStatement pst3 = null;
            PreparedStatement pst2 = null;
            try {
                conn2 = SWBUtils.DB.getConnection(tmp_conn, "Repository.uninstall() -- delete repository records -- "); //WBUtils.getInstance().getDBConnection();
                pst3 = conn2.prepareStatement("delete from resrepositoryversions where idtm = ?");
                pst3.setString(1, recobj.getWebSite().getId());
                pst3.execute();
                pst3.close();
                pst3 = null;
                pst2 = conn2.prepareStatement("delete from resrepository where idtm=?");
                pst2.setString(1, recobj.getWebSite().getId());
                pst2.executeUpdate();
                pst2.close();
                pst2 = null;
                pst2 = conn2.prepareStatement("delete from resrepositorynotify where idtm=?");
                pst2.setString(1, recobj.getWebSite().getId());
                pst2.executeUpdate();
                pst2.close();
                pst2 = null;
                pst2 = conn2.prepareStatement("delete from resrepositorylog where rep_topicmapid = ?");
                pst2.setString(1, recobj.getWebSite().getId());
                pst2.executeUpdate();
                pst2.close();
                conn2.close();

                // eliminar folder con su contenido dentro del folder work, sites, repositorio //REVISAR RUTA
                //WBUtils.getInstance().getAppPath()+"/wbadmin/resources/"+recobj.getName()
                SWBUtils.IO.removeDirectory(SWBUtils.getApplicationPath() + "/work/models/" + recobj.getWebSite().getId() + "/Resource/" + recobj.getId());
            } catch (Exception eb) {
                log.error("Error while trying to erase repository records. Repository.uninstall", eb);
            } finally {
                try {
                    if (pst2 != null) {
                        pst2.close();
                    }
                    pst2 = null;
                } catch (Exception e1) {
                }
                try {
                    if (pst3 != null) {
                        pst3.close();
                    }
                    pst3 = null;
                } catch (Exception e2) {
                }
                try {
                    if (conn2 != null) {
                        conn2.close();
                    }
                    conn2 = null;
                } catch (Exception e3) {
                }
            }

        }
    }

    /**
     * Administration view of the Repository resource
     *
     * @param request The input parameters
     * @param response The answerd to the request
     * @param paramsRequest The list of objects (topic, user, action, ...)
     * @throws AFException An Application framework exception
     * @throws IOException An Input / Output exception
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        UserRepository usrRep = getResourceBase().getWebSite().getUserRepository();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String strView = "-1";
        String strGuest = "false";
        String strModify = "-1";
        String strAdmin = "-1";
        String strNotify = "";
        String strXslFile = "";
        String strSubDir = "1";
        String strCheck = "checked";
        String strEnable = "";
        String strTopicMap = null;
        String strTopic = null;
        String s_sql = null;
        String cmd = null;
        String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "swb");

        PrintWriter out = response.getWriter();
        String accion = paramsRequest.getAction();
        User user = paramsRequest.getUser();
        String webpath = SWBPlatform.getContextPath();
        String admresprec = SWBPlatform.getEnv("wb/admresource");
        Iterator it = null;
        //TopicMap topicmap = null;
        WebPage topic = null;
        //TopicMgr tpmgr = null;
        int i_chk = 0;
        //int i_hasdata = 0;
        long id = 0;
        boolean flag = false;




        //tpmgr = TopicMgr.getInstance();

        strTopicMap = request.getParameter("tm");
        strTopic = request.getParameter("topic");
        if (strTopicMap == null) {
            strTopicMap = paramsRequest.getWebPage().getWebSiteId();
        }
        if (strTopic == null) {
            strTopic = paramsRequest.getWebPage().getId();
        }
        WebSite topicMap = getResourceBase().getWebSite();
        strTopicMap = topicMap.getId();

        if (accion.equals("add")) {

            topic = topicMap.getWebPage(strTopic);
            if (topic != null) {
                WebPage aux = WebPage.ClassMgr.createWebPage("CNFWB_Rep" + base.getId(), topicMap);
                org.semanticwb.model.Language lang = org.semanticwb.model.Language.ClassMgr.getLanguage(user.getLanguage(), topicMap);
                aux.setLanguage(lang);
                aux.setTitle(base.getTitle());
                aux.setParent(topic);
            }
        }

        // -------------------------- Inicia remove

        if (accion.equals("remove")) {
            strAdmin = "0";
            String strTMId = paramsRequest.getWebPage().getWebSiteId();
            flag = true;
            strTopic = "CNFWB_Rep" + base.getId();

            topic = topicMap.getWebPage(strTopic);
            Connection conn = null;
            PreparedStatement pst = null;
            ResultSet rsdocs = null;

            try {
                conn = SWBUtils.DB.getConnection(tmp_conn, "Repository.doAdmin() -- remove --");
                String sql = "select * from resrepository where resid = ? and idtm=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, base.getId());
                pst.setString(2, base.getWebSiteId());
                rsdocs = pst.executeQuery();

                while (rsdocs.next()) {  // borrando de la tabla de resrepositoryversions

                    PreparedStatement pstver = conn.prepareStatement("delete from resrepositoryversions where rep_docId = ? and idtm=?");
                    pstver.setLong(1, rsdocs.getLong("rep_docId"));
                    pstver.setString(2, rsdocs.getString("idtm"));
                    int borrado = pstver.executeUpdate();
                    pstver.close();
                    pstver = null;
                }

                rsdocs.close();
                pst.close();
                rsdocs = null;
                pst = null;
                conn.close();
                conn = null;

            } catch (Exception e) {
                log.error("Error while getting repository file records. Repository.doAdmin(remove)", e);
            } finally {
                try {
                    if (rsdocs != null) {
                        rsdocs.close();
                    }
                    rsdocs = null;
                } catch (Exception e1) {
                }
                try {
                    if (pst != null) {
                        pst.close();
                    }
                    pst = null;
                } catch (Exception e2) {
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                    conn = null;
                } catch (Exception e3) {
                }
            }

            if (topic != null) {
                Iterator<WebPage> itetopics = topic.listChilds();
                while (itetopics.hasNext()) {
                    WebPage tpIter = itetopics.next();
                    try {
                        
                        tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
                        conn = SWBUtils.DB.getConnection(tmp_conn, "Repository.doAdmin() -- remove --");

                        String sql = "delete from resrepository where idtm=? and topic = ?";
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, strTMId);
                        pst.setString(2, tpIter.getId());
                        int deleted = pst.executeUpdate();
                        pst.close();


                        sql = "delete from resrepositorynotify where idtm=? and topic = ?";
                        PreparedStatement pst2 = conn.prepareStatement(sql);
                        pst2.setString(1, strTMId);
                        pst2.setString(2, tpIter.getId());
                        deleted = pst2.executeUpdate();
                        pst2.close();
                        conn.close();

                        PreparedStatement pstlog = conn.prepareStatement("delete from resrepositorylog where rep_topicid = ? and rep_topicmapid = ?");
                        pstlog.setString(1, tpIter.getId());
                        pstlog.setString(2, strTMId);
                        int logborrado = pstlog.executeUpdate();
                        pstlog.close();
                        pstlog = null;
                        pst = null;
                        pst2 = null;
                        conn = null;
                    } catch (Exception e) {
                        log.error("Error while trying to erase repository records from the DB. Repository.doAdmin(remove)", e);
                    }
                }
            }


            if (topic != null) {
                Iterator<WebPage> itetopics = topic.listChilds();
                while (itetopics.hasNext()) {
                    WebPage tpIter = itetopics.next();
                    PreparedStatement pst2 = null;
                    PreparedStatement pstlog = null;
                    try {
                        conn = SWBUtils.DB.getConnection(tmp_conn, "Repository.doAdmin() -- remove 2 --");
                        String sql = "delete from resrepository where idtm=? and topic = ?";
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, strTMId);
                        pst.setString(2, tpIter.getId());
                        int deleted = pst.executeUpdate();
                        pst.close();
                        pst = null;

                        sql = "delete from resrepositorynotify where idtm=? and topic = ?";
                        pst2 = conn.prepareStatement(sql);
                        pst2.setString(1, strTMId);
                        pst2.setString(2, tpIter.getId());
                        deleted = pst2.executeUpdate();
                        pst2.close();
                        pst2 = null;

                        pstlog = conn.prepareStatement("delete from resrepositorylog where rep_topicid = ? and rep_topicmapid = ?");
                        pstlog.setString(1, tpIter.getId());
                        pstlog.setString(2, strTMId);
                        int logborrado = pstlog.executeUpdate();
                        pstlog.close();
                        pstlog = null;

                        conn.close();
                        conn = null;
                    } catch (Exception e) {
                        log.error("Error while trying to erase repository records from the DB. Repository.doAdmin(remove)", e);
                    } finally {
                        try {
                            if (rsdocs != null) {
                                rsdocs.close();
                            }
                            rsdocs = null;
                        } catch (Exception e1) {
                        }
                        try {
                            if (pst != null) {
                                pst.close();
                            }
                            pst = null;
                        } catch (Exception e2) {
                        }
                        try {
                            if (pst2 != null) {
                                pst2.close();
                            }
                            pst2 = null;
                        } catch (Exception e3) {
                        }
                        try {
                            if (pstlog != null) {
                                pstlog.close();
                            }
                            pstlog = null;
                        } catch (Exception e4) {
                        }
                        try {
                            if (conn != null) {
                                conn.close();
                            }
                            conn = null;
                        } catch (Exception e5) {
                        }
                    }
                }
            }

            PreparedStatement psttp = null;
            PreparedStatement pstnot = null;
            PreparedStatement pstlog = null;
            try {
                conn = SWBUtils.DB.getConnection(tmp_conn, "Repository.doAdmin() -- remove 3 --");
                psttp = conn.prepareStatement("delete from resrepository where idtm=? and topic = ?");
                psttp.setString(1, strTMId);
                psttp.setString(2, strTopic);
                int tpborrado = psttp.executeUpdate();
                psttp.close();
                psttp = null;

                pstnot = conn.prepareStatement("delete from resrepositorynotify where idtm=? and topic = ?");
                pstnot.setString(1, strTMId);
                pstnot.setString(2, strTopic);
                int tpnot = pstnot.executeUpdate();
                pstnot.close();
                pstnot = null;

                pstlog = conn.prepareStatement("delete from resrepositorylog where rep_topicid = ? and rep_topicmapid = ?");
                pstlog.setString(1, strTopic);
                pstlog.setString(2, strTMId);
                int logborrado = pstlog.executeUpdate();
                pstlog.close();
                pstlog = null;

                conn.close();
                conn = null;

            } catch (Exception e) {
                log.error("Error while getting repository file records. Repository.doAdmin(remove)", e);
            } finally {
                try {
                    if (psttp != null) {
                        psttp.close();
                    }
                    psttp = null;
                } catch (Exception e1) {
                }
                try {
                    if (pstnot != null) {
                        pstnot.close();
                    }
                    pstnot = null;
                } catch (Exception e2) {
                }
                try {
                    if (pstlog != null) {
                        pstlog.close();
                    }
                    pstlog = null;
                } catch (Exception e3) {
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                    conn = null;
                } catch (Exception e4) {
                }
            }


            if (topic != null) {
                topic.remove();
                SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath() + base.getWorkPath());
            }

        }  // ----------------- Termina remove

        out.println("<div class=box>");
        out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"10\">");
        out.println("<tr>");
        out.println("<td class=tabla>");
        out.println("Repository Resource" + "</td>");
        out.println("</tr>");


        if (!accion.equals("addrule")) {
            if (accion.equals("ShowOld")) {
                try {
                    con = SWBUtils.DB.getConnection(tmp_conn, "Repository.doAdmin() -- recover folder --");
                    if (null == con) {
                        con = SWBUtils.DB.getDefaultConnection();
                    }
                    cmd = request.getParameter("cmd");
                    WebSite oTM = null;
                    WebPage oRep = null;
                    WebPage oSub = null;
                    // Recover a file
                    if (request.getParameter("docid") != null) {
                        String strTMParam = request.getParameter("tm");
                        if (strTMParam == null) {
                            strTMParam = paramsRequest.getWebPage().getWebSiteId();
                        }
                        // se recuperara el folder si este est� eliminado
                        if (request.getParameter("resdir") != null && request.getParameter("resdir").equals("1")) {
                            // obtener el t�pico (sub directorio borrado) y mandarlo a la funci�n de recuperar folder � folders seg�n sea el caso
                            String strIdSub = request.getParameter("idSub");
                            oTM = WebSite.ClassMgr.getWebSite(strTMParam);
                            oRep = oTM.getWebPage("CNFWB_Rep" + base.getId());
                            oSub = oTM.getWebPage(strIdSub); //,true);
                            recuperarFolder(oSub, oRep);  // Llamada para recuperar los sub directorios eliminados.
                            //oTM.update2DB();
                            //si se pudo recuperar los t�picos (sub directorios), se recuperaran los archivos al directorio.
                        }

                        ps = con.prepareStatement("update resrepository set rep_deleted = 0 where rep_docId=? and idtm=?");
                        ps.setLong(1, Long.parseLong(request.getParameter("docid")));
                        ps.setString(2, strTMParam);
                        ps.executeUpdate();
                    }
                    //Delete comment
                    if (cmd != null) {
                        if (cmd.equals("del_file")) {
                            if (request.getParameter("ifile") != null) {
                                String[] values = request.getParameterValues("ifile");
                                for (int i = 0; i < values.length; i++) {
                                    id = Long.parseLong(values[i]);

                                    ps = con.prepareStatement("select * from resrepository where rep_docId=? and idtm=?");
                                    ps.setLong(1, id);
                                    ps.setString(2, strTopicMap);
                                    ResultSet rsCheck = ps.executeQuery();
                                    WebPage tpDir = null;
                                    if (rsCheck.next()) {
                                        String tmsid = rsCheck.getString("idtm"); //temp.substring(0,temp.lastIndexOf("|")); // topicmap del repositorio
                                        String tpsid = rsCheck.getString("topic"); //temp.substring(temp.lastIndexOf("|")+1); // folder a revisar si est� eliminado
                                        WebSite tmMain = WebSite.ClassMgr.getWebSite(tmsid);
                                        tpDir = tmMain.getWebPage(tpsid); //,true);
                                        WebPage tpRepo = tmMain.getWebPage("CNFWB_Rep" + base.getId());
                                        if (tpRepo != null) {
                                            checkDir(tmMain, tpDir, tpRepo, id);
                                        }
                                    }
                                    rsCheck.close();
                                    ps.close();

                                    ps = con.prepareStatement("delete from resrepository where rep_docId=? and idtm=?");
                                    ps.setLong(1, id);
                                    ps.setString(2, strTopicMap);
                                    ps.executeUpdate();
                                    ps.close();
                                    ps = con.prepareStatement("delete from resrepositoryversions where rep_docId=? and idtm=?");
                                    ps.setLong(1, id);
                                    ps.setString(2, strTopicMap);
                                    ps.executeUpdate();
                                    ps.close();
                                    ps = con.prepareStatement("delete from resrepositorynotify where rep_docId=? and idtm=?");
                                    ps.setLong(1, id);
                                    ps.setString(2, strTopicMap);
                                    ps.executeUpdate();
                                    ps.close();

                                    // Quitar archhivos del index

                                    File fdir = new File(SWBPortal.getWorkPath() + "/" + base.getWorkPath() + "/");
                                    String filestarts = id + "_";
                                    File[] files = fdir.listFiles();
                                    // eliminando archivos
                                    SWBIndexer swbindx = SWBPortal.getIndexMgr().getDefaultIndexer();
                                    for (int j = 0; j < files.length; j++) {
                                        File f = files[j];
                                        if (f.getName().startsWith(filestarts)) {
                                            if (f.exists() && j == (files.length - 1)) {

                                                if (swbindx != null) {
                                                    try {
                                                        swbindx.removeSearchable("file:" + f.getAbsolutePath());  // Para eliminar un archivo del index
                                                    } catch (Exception ex) {
                                                        log.error("Error while trying to remove a file from index.", ex);
                                                    }
                                                }
                                            }
                                            f.delete();
                                        }
                                    }
                                    ///  termina quitar archivos del index
                                }
                            }
                        }
                    }

                    s_sql = "select * from resrepository where resId = ? and rep_deleted = ? and idtm = ? order by rep_title";
                    ps = con.prepareStatement(s_sql);
                    ps.setString(1, base.getId());
                    ps.setInt(2, 1);
                    ps.setString(3, strTopicMap);
                    //System.out.println("select * from resrepository where resId="+base.getId()+" and rep_deleted = 1 and idtm="+strTopicMap+" order by rep_title");
                    //System.out.println("select * from resrepository where resId="+getResourceBase().getId()+" and rep_deleted = 1 and idtm="+strTopicMap+" order by rep_title");
                    rs = ps.executeQuery();
                    out.println("<tr> ");
                    out.println("<td class=valores>");
                    out.println(paramsRequest.getLocaleString("msgFileRemovePermanently") + ":</td>");
                    out.println("</tr>");
                    out.println("<tr> ");
                    out.println("<td>");
                    SWBResourceURL urlShowOld = paramsRequest.getRenderUrl();
                    urlShowOld.setMode(paramsRequest.Mode_ADMIN);
                    urlShowOld.setAction("ShowOld");

                    out.println("<form action=\"" + urlShowOld.toString() + "\" name=\"frmdelfile\" method=POST><input type=\"hidden\" name=\"cmd\" value=\"del_file\">");
                    out.println("<input type=\"hidden\" name=\"tm\" value=\"" + strTopicMap + "\"><input type=\"hidden\" name=\"topic\" value=\"" + strTopic + "\"><input type=\"hidden\" name=\"ifile\" value=\"\">");
                    out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
                    out.println("<tr class=tabla><td>" + paramsRequest.getLocaleString("msgTHFolder") + "</td><td>" + paramsRequest.getLocaleString("msgTHFileName") + "</td><td>" + paramsRequest.getLocaleString("msgTHCreated") + "</td><td>" + paramsRequest.getLocaleString("msgTHEmialUser") + "</td><td>" + paramsRequest.getLocaleString("msgTHAction") + "</td></tr>");
                    String rowColor = "";
                    boolean cambiaColor = true;
                    //System.out.println("Antes de mostrar lista de archivos eliminados...");
                    try {
                        while (rs.next()) {
                            //System.out.println("Obteniendo resultado...");
                            String str_doc = rs.getString("rep_docId");
                            String str_topic = rs.getString("topic");
                            String str_tm = rs.getString("idtm");
                            String repemail = rs.getString("rep_email");
                            String reptitle = rs.getString("rep_title");
                            Timestamp repcreate = rs.getTimestamp("rep_create");
                            //System.out.println("Despues de leer columnas...");
                            rowColor = "#EFEDEC";
                            if (!cambiaColor) {
                                rowColor = "#FFFFFF";
                            }
                            cambiaColor = !(cambiaColor);
                            out.println("<tr bgcolor=\"" + rowColor + "\">");
                            String temp = str_topic;
                            String tmsid = str_tm;
                            String tpsid = str_topic;
                            String nameFolder = "";
                            String folderborrado = "";
                            String idSub = "";
                            int intDeleted = 0;
                            WebSite tpMAP = WebSite.ClassMgr.getWebSite(tmsid);

                            WebPage tpSubDir = tpMAP.getWebPage(tpsid); //,true);
                            if (tpSubDir != null) {
                                if (tpSubDir.isDeleted()) {
                                    folderborrado = "<font size=1 color=red>&lt;folder eliminado&gt;</font>";
                                    intDeleted = 1;
                                    idSub = tpSubDir.getId();
                                }
                                nameFolder = tpSubDir.getDisplayName(user.getLanguage()) + folderborrado;
                            } else {
                                tpSubDir = tpMAP.getWebPage(tpsid); //,false);
                                if (tpSubDir != null) {
                                    nameFolder = tpSubDir.getDisplayName(user.getLanguage());
                                } else {
                                    nameFolder = "Carpeta no encontrada: (" + tpsid + ")";
                                }
                            }

                            WebSite wsite = paramsRequest.getWebPage().getWebSite();
                            String userID = repemail;
                            if (repemail.indexOf("_") != -1) {
                                userID = repemail.substring(0, repemail.indexOf("_"));
                            }

                            //String userRepoID = repemail.substring(repemail.indexOf("_"));
                            //System.out.println("usr Repo id : "+userRepoID);

                            UserRepository urepo = wsite.getUserRepository();

                            //UserRepository urepo = UserRepository.ClassMgr.getUserRepository(userRepoID);
                            // if(null==urepo) urepo = wsite.getUserRepository();

                            out.println("<td class=datos>" + nameFolder + "</td>");
                            out.println("<td class=datos>" + reptitle + "</td>");
                            out.println("<td class=datos>" + SWBUtils.TEXT.getStrDate(new java.util.Date(repcreate.getTime()), "es") + "</td>");
                            //System.out.println("email de la db: "+repemail+"-----"+userID);
                            User usrrepo = urepo.getUser(userID);
                            String usrEMAIL = null;
                            if (usrrepo != null && usrrepo.getEmail() != null) {
                                usrEMAIL = usrrepo.getEmail();
                            } else {
                                usrEMAIL = "No se encontró usuario";
                            }

                            out.println("<td class=datos><a href=\"mailto:" + usrEMAIL + "\">" + usrEMAIL + "</a></td>");

                            out.println("<td class=datos>");
                            if (canRecover(str_tm, str_topic)) {
                                out.println("<a class=link href=\"javascript: DoRecover(" + str_doc + "," + intDeleted + ",'" + idSub + "');\">" + "<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/recover.gif\" border=0 title=\"" + paramsRequest.getLocaleString("msgAltRestore") + "\" >" + "</a>");
                            } else {
                                out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/Repository/recover.gif\" border=0 title=\"" + paramsRequest.getLocaleString("msgAltRestore") + "\" >");
                            }
                            out.println("&nbsp;<a class=link href=\"javascript: DoConfirm(" + str_doc + ");\">" + "<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/trash_vacio.gif\" border=0 title=\"" + paramsRequest.getLocaleString("msgAltEliminate") + "\" >" + "</a>");
                            out.println("</td></tr>");
                        }
                    } catch (Exception e) {
                        Repository.log.error("Error al mostrar archivos eliminados...", e);
                        //System.out.println(e.getMessage());
                    }
                    con.close();
                    rs.close();
                    ps.close();
                    out.println("<tr>");
                    out.println("<td colspan=\"5\" align=\"right\"><HR size=1 noshade> ");
                    out.println("<input type=button class=boton name=btnCancel onClick=\"javascript: DoRedir();\" value=" + paramsRequest.getLocaleString("msgBTNCancel") + " >");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("</table>");
                    out.println("</form>");
                    SWBResourceURL urlEdit = paramsRequest.getRenderUrl();
                    urlEdit.setMode(paramsRequest.Mode_ADMIN);
                    urlEdit.setAction("edit");

                    out.println("<form class=\"oculto\" name=\"frmredir\" action=\"" + urlEdit.toString() + "\" method=\"post\">");
                    out.println("<input type=\"hidden\" name=\"tm\" value=\"" + strTopicMap + "\"><input type=\"hidden\" name=\"topic\" value=\"" + strTopic + "\">");
                    out.println("</form>");
                    out.println("<form class=\"oculto\" name=\"frmrestore\" action=\"" + urlShowOld.toString() + "\" method=\"post\">");
                    out.println("<input type=\"hidden\" name=\"tm\" value=\"" + strTopicMap + "\"><input type=\"hidden\" name=\"topic\" value=\"" + strTopic + "\"><input type=\"hidden\" name=\"docid\" value=\"\"><input type=\"hidden\" name=\"resdir\" value=\"\">");
                    out.println("<input type=\"hidden\" name=\"idSub\" value=\"\">");
                    out.println("</form>");
                    out.println("<script language=javascript>");
                    out.println("  function DoRedir(){");
                    out.println("      window.document.frmredir.submit();");
                    out.println("  }");
                    out.println("  function DoConfirm(erase){");
                    out.println("      if(confirm('" + paramsRequest.getLocaleString("msgAlertSelFilesRemoved") + "!')){");
                    out.println("          window.document.frmdelfile.ifile.value=erase;");
                    out.println("          window.document.frmdelfile.submit();");
                    out.println("      }");
                    out.println("  }");
                    out.println("  function DoRecover(ivar,f_erased,id_sub){");

                    out.println("      if(f_erased=='1') ");
                    out.println("       {");
                    out.println("           if(confirm('" + "Si recuperas este archivo se recuperara el folder. \\n \\n �Est�s seguro de querer hacerlo?" + "')) ");
                    out.println("             {");
                    out.println("                  window.document.frmrestore.resdir.value = 1;");
                    out.println("                  window.document.frmrestore.idSub.value = id_sub;");
                    out.println("                  window.document.frmrestore.docid.value = ivar;");
                    out.println("                  window.document.frmrestore.submit();");
                    out.println("             }");
                    out.println("           else");
                    out.println("            {");
                    out.println("                  return;");
                    out.println("            }");
                    out.println("       }");
                    out.println("      else");
                    out.println("       {");
                    out.println("           window.document.frmrestore.resdir.value = 0;");
                    out.println("           window.document.frmrestore.idSub.value = 0;");
                    out.println("           window.document.frmrestore.docid.value = ivar;");
                    out.println("           window.document.frmrestore.submit();");
                    out.println("        }");
                    out.println("      ");
                    out.println("      ");
                    out.println("  }");
                    out.println("</script>");

                } catch (Exception e) {
                    log.error("Error in method Repository.doAdmin() when action is ShowOld, resource is " + base.getId(), e);
                } finally {
                    rs = null;
                    ps = null;
                    con = null;
                }

            } else {
                if (base.getAttribute("view") != null) {
                    strView = base.getAttribute("view");
                    flag = true;
                }
                if (base.getAttribute("guest") != null) {
                    strGuest = base.getAttribute("guest");
                    flag = true;
                }
                if (base.getAttribute("modify") != null) {
                    strModify = base.getAttribute("modify");
                    flag = true;
                }
                if (base.getAttribute("admin") != null) {
                    strAdmin = base.getAttribute("admin");
                    flag = true;
                }
                if (base.getAttribute("showdirectory") != null) {
                    if (base.getAttribute("showdirectory").equals("false")) {
                        strSubDir = "0";
                        strCheck = "";
                    } else {
                        strSubDir = "1";
                        strCheck = "checked";
                        i_chk = 1;
                    }
                    flag = true;
                } else {
                    strSubDir = "1";
                    strCheck = "checked";
                    flag = true;
                }

                //topicmap=tpmgr.getTopicMap(strTopicMap);
                WebPage tp = topicMap.getWebPage("CNFWB_Rep" + base.getId());
                if (tp != null) {
                    it = tp.listChilds();
                    while (it.hasNext()) {
                        strEnable = "disabled=\"true\"";
                        break;
                    }
                }

                out.println("<tr> ");
                out.println("<td class=valores>");

                SWBResourceURL urlAddRule = paramsRequest.getRenderUrl();
                urlAddRule.setMode(paramsRequest.Mode_ADMIN);
                urlAddRule.setAction("addrule");

                out.println("<form name=\"myForm\" onSubmit=\" if(DoValidate()) return true;\" enctype=\"multipart/form-data\" action=\"" + urlAddRule.toString() + "\" method=\"POST\">");
                out.println("<table  width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"10\">");

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                String strTemp = "<option value=\"-1\">" + paramsRequest.getLocaleString("msgNoRolesAvailable") + "</option>";
                Iterator<Role> eRules = usrRep.listRoles();
                StringBuffer strRules = new StringBuffer("");
                strRules.append("\n<option value=\"0\">" + paramsRequest.getLocaleString("msgSelNone") + "</option>");
                while (eRules.hasNext()) {
                    Role oRole = eRules.next();
                    strRules.append("\n<option value=\"" + oRole.getId() + "\">" + oRole.getDisplayTitle(paramsRequest.getUser().getLanguage()) + "</option>");
                }
                if (strRules.toString().length() > 0) {
                    strTemp = strRules.toString();
                }

                out.println("<tr><td colspan=2 class=valores><B>" + paramsRequest.getLocaleString("msgRolesDefinitionLevel") + "</b></td></tr>");
                out.println("<tr><td align=right class=valores width=150>" + paramsRequest.getLocaleString("msgView") + ":</td>");
                out.println("<td><select name=\"ver\" class=campos>" + strTemp + "</select><input type=\"checkbox\" name=\"guest\" id=\"guest\" value=\"true\" " + (strGuest.equals("true") ? "checked" : "") + "><label for=\"guest\">usuario an&oacute;nimo</label></td></tr>");
                out.println("<tr><td align=right class=valores width=150>" + paramsRequest.getLocaleString("msgModify") + ":</td>");
                out.println("<td><select name=\"modificar\">" + strTemp + "</select></td></tr>");
                out.println("<tr><td align=right class=valores width=150>" + paramsRequest.getLocaleString("msgAdministrate") + ":</td>");
                out.println("<td><select name=\"administrar\">" + strTemp + "</select></td></tr>");
                out.println("<tr><td align=right class=valores width=150>" + paramsRequest.getLocaleString("msgShowSubDirs") + ":</td>");
                out.println("<td><input class=campos type=\"checkbox\" name=\"showdirectory\" value=\"1\" " + strCheck + " " + strEnable + ">");
                out.println("<input type=\"hidden\" name=\"showdirectoryaux\" value=\"" + i_chk + "\"");
                out.println("</td></tr>");

                out.println("<tr><td align=right class=valores width=150>" + paramsRequest.getLocaleString("msgCreateNotificationMessage") + ":</td>");

                strNotify = "";
                if (base.getAttribute("notifycreate") != null) {
                    strNotify = base.getAttribute("notifycreate");
                    flag = true;
                } else {
                    strNotify = null;
                    StringBuffer strMsg = new StringBuffer(paramsRequest.getLocaleString("msgCreateNotificationMessage") + ":");
                    strMsg.append("\n" + paramsRequest.getLocaleString("msgTheDoc") + " {getDocTitle} " + paramsRequest.getLocaleString("msgWasCreated") + ".\n");
                    strMsg.append("\n   " + paramsRequest.getLocaleString("msgFileName") + ":       {getFileName}");
                    strMsg.append("\n   " + paramsRequest.getLocaleString("msgCreationDate") + ":   {getLastUpdate}");
                    strMsg.append("\n   " + paramsRequest.getLocaleString("msgReviewFile") + ":   {getDocLink}");
                    strMsg.append("\n   " + paramsRequest.getLocaleString("msgCreationUser") + ":   {getUserName} <{getUserEmail}>");
                    strNotify = strMsg.toString();
                }

                out.println("<td><textarea class=campos name=\"notificationcreate\" cols=60 rows=10>" + strNotify + "</textarea></td></tr>");
                out.println("<tr><td align=right class=valores width=150>" + paramsRequest.getLocaleString("msgUpdNotificationMessage") + ":</td>");

                strNotify = "";
                if (base.getAttribute("notifyupdate") != null) {
                    strNotify = base.getAttribute("notifyupdate");
                    flag = true;
                } else {
                    strNotify = null;
                    StringBuffer strMsg = new StringBuffer(paramsRequest.getLocaleString("msgUpdNotificationMessage") + ":");
                    strMsg.append("\n" + paramsRequest.getLocaleString("msgTheDoc") + " {getDocTitle} " + paramsRequest.getLocaleString("msgWasUpd") + ".\n");
                    strMsg.append("\n   " + paramsRequest.getLocaleString("msgFileName") + ":       {getFileName}");
                    strMsg.append("\n   " + paramsRequest.getLocaleString("msgReviewFile") + ":   {getDocLink}");
                    strMsg.append("\n   " + paramsRequest.getLocaleString("msgLastUpd") + ":     {getLastUpdate}");
                    strMsg.append("\n   " + paramsRequest.getLocaleString("msgVersionUser") + ":    {getUserName} <{getUserEmail}>");
                    strMsg.append("\n   " + paramsRequest.getLocaleString("msgVersionComments") + ":");
                    strMsg.append("\n        {getComments}");
                    strNotify = strMsg.toString();
                }

                out.println("<td><textarea class=campos name=\"notificationupdate\" cols=60 rows=10>" + strNotify + "</textarea></td></tr>");
                out.println("<tr><td align=right class=valores width=150>" + paramsRequest.getLocaleString("msgRemoveNotificationMessage") + ":</td>");

                strNotify = "";
                if (base.getAttribute("notifyremove") != null) {
                    strNotify = base.getAttribute("notifyremove");
                    flag = true;
                } else {
                    strNotify = null;
                    StringBuffer strMsg = new StringBuffer(paramsRequest.getLocaleString("msgRemoveNotificationMessage") + ":");
                    strMsg.append("\n" + paramsRequest.getLocaleString("msgTheDoc") + " {getDocTitle} " + paramsRequest.getLocaleString("msgWasRemoved") + ".\n");
                    strMsg.append("\n   " + paramsRequest.getLocaleString("msgDirName") + ":  {getDirectoryName}");
                    strMsg.append("\n   " + paramsRequest.getLocaleString("msgUserName") + ":       {getUserName} <{getUserEmail}>");
                    strNotify = strMsg.toString();
                }

                out.println("<td><textarea class=campos name=\"notificationremove\" cols=60 rows=10>" + strNotify + "</textarea></td></tr>");
                out.println("<tr><td colspan=2 align=right><HR noshade size=1><INPUT type=\"submit\" class=\"boton\" value=\"" + paramsRequest.getLocaleString("msgBTNAccept") + "\" id=\"btnEnviar\" name=\"btnEnviar\" >&nbsp;<input type=button class=boton onclick=\"javascript:DoOld();\" value=\"" + paramsRequest.getLocaleString("msgBTNViewOldFiles") + "\">");
                out.println("</td></tr>");

                out.println("<tr><td colspan=2 class=valores><br>* " + paramsRequest.getLocaleString("msgNotificationKeys") + ":</td></tr>");
                out.println("<tr><td colspan=2 class=datos>");
                out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getUserName}   " + paramsRequest.getLocaleString("msgCompleteUserName"));
                out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getUserEmail}  " + paramsRequest.getLocaleString("msgUserEmail"));
                out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDirectoryName} " + paramsRequest.getLocaleString("msgDirName"));
                out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getFileName}   " + paramsRequest.getLocaleString("msgFileName"));
                out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocTitle}   " + paramsRequest.getLocaleString("msgDocTitle"));
                out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocDesc}    " + paramsRequest.getLocaleString("msgDocDescription"));
                out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocVersion} " + paramsRequest.getLocaleString("msgDocVersion"));
                out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocLink} " + paramsRequest.getLocaleString("msgDocLink"));
                out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getLastUpdate} " + paramsRequest.getLocaleString("msgLastDateModification"));
                out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getComments}   " + paramsRequest.getLocaleString("msgModificationComment"));

                out.println("</font></td></tr>");

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                out.println("</table>");
                out.println("</form>");
                out.println("<br> * " + paramsRequest.getLocaleString("msgNote") + ": " + paramsRequest.getLocaleString("msgRolesDependent") + ".</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</p>");
                out.println("<script language=javascript>");
                out.println("  function DoOld(){");

                SWBResourceURL urlShowOld = paramsRequest.getRenderUrl();
                urlShowOld.setMode(paramsRequest.Mode_ADMIN);
                urlShowOld.setAction("ShowOld");

                out.println("      window.document.frmold.action=\"" + urlShowOld.toString() + "\";");
                out.println("      window.document.frmold.submit();");
                out.println("  }");
                out.println("  function DoValidate(){");
                out.println("      if(window.document.myForm.showdirectory.checked == false){");
                out.println("          window.document.myForm.showdirectoryaux.value = 0;");
                out.println("      }");
                out.println("      return true;");
                out.println("  }");
                out.println("</script>");
                out.println("<form class=\"oculto\" action=\"\" name=\"frmold\" method=POST>");
                out.println("<input type=\"hidden\" name=\"tm\" value=\"" + strTopicMap + "\"><input type=\"hidden\" name=\"topic\" value=\"" + strTopic + "\">");
                out.println("</form>");

                if (flag) {
                    if (strView.equals("-1")) {
                        strView = "0";
                    }
                    if (strModify.equals("-1")) {
                        strModify = "0";
                    }
                    if (strAdmin.equals("-1")) {
                        strAdmin = "0";
                    }
                    out.println("<script language=javascript>");
                    out.println("      document.myForm.ver.value=\"" + strView + "\"");
                    //out.println("      document.myForm.guest.checked="+strGuest+"");
                    out.println("      document.myForm.modificar.value=\"" + strModify + "\"");
                    out.println("      document.myForm.administrar.value=\"" + strAdmin + "\"");
                    out.println("</script>");
                }
            }
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</div>");
        } else {
            try {
                FileUpload fUpload = new FileUpload();
                fUpload.getFiles(request, response);
                if (fUpload.getValue("ver") != null && !fUpload.getValue("ver").equals("-1")) {
                    if (fUpload.getValue("ver").equals("0")) {
                        base.removeAttribute("view");
                        strView = "0";
                        flag = true;
                    } else {
                        base.setAttribute("view", fUpload.getValue("ver"));
                        strView = fUpload.getValue("ver");
                        flag = true;
                    }
                }

                if (fUpload.getValue("guest") != null) //&& !fUpload.getValue("guest").equals("false")
                {
                    if (fUpload.getValue("guest").equals("true")) {
                        base.setAttribute("guest", fUpload.getValue("guest"));
                        strGuest = fUpload.getValue("guest");
                        flag = true;
                    }
                } else if (fUpload.getValue("guest") == null) {
                    base.removeAttribute("guest");
                    strGuest = "false";
                    flag = true;
                }

                if (fUpload.getValue("modificar") != null && !fUpload.getValue("modificar").equals("-1")) {
                    if (fUpload.getValue("modificar").equals("0")) {
                        base.removeAttribute("modify");
                        strModify = "0";
                        flag = true;
                    } else {
                        base.setAttribute("modify", fUpload.getValue("modificar"));
                        strModify = fUpload.getValue("modificar");
                        flag = true;
                    }
                }

                if (fUpload.getValue("administrar") != null && !fUpload.getValue("administrar").equals("-1")) {
                    if (fUpload.getValue("administrar").equals("0")) {
                        base.removeAttribute("admin");
                        strAdmin = "0";
                        flag = true;
                    } else {
                        base.setAttribute("admin", fUpload.getValue("administrar"));
                        strAdmin = fUpload.getValue("administrar");
                        flag = true;
                    }
                }

                if (fUpload.getValue("showdirectory") != null) {
                    base.setAttribute("showdirectory", "true");
                    strSubDir = "1";
                    strCheck = "checked";
                    flag = true;
                } else {
                    if (fUpload.getValue("showdirectoryaux").equals("1")) {
                        base.setAttribute("showdirectory", "true");
                        strSubDir = "1";
                        strCheck = "checked";
                        flag = true;
                    } else {
                        base.setAttribute("showdirectory", "false");
                        strSubDir = "0";
                        strCheck = "";
                        flag = true;
                    }
                }
                if (fUpload.getValue("notificationcreate").equals(null) || fUpload.getValue("notificationcreate").trim().equals("")) {
                    base.removeAttribute("notifycreate");
                    strNotify = "";
                    flag = true;
                } else {
                    base.setAttribute("notifycreate", fUpload.getValue("notificationcreate").trim());
                    strNotify = fUpload.getValue("notificationcreate");
                    flag = true;
                }
                if (fUpload.getValue("notificationupdate").equals(null) || fUpload.getValue("notificationupdate").trim().equals("")) {
                    base.removeAttribute("notifyupdate");
                    strNotify = "";
                    flag = true;
                } else {
                    base.setAttribute("notifyupdate", fUpload.getValue("notificationupdate").trim());
                    strNotify = fUpload.getValue("notificationupdate");
                    flag = true;
                }
                if (fUpload.getValue("notificationremove").equals(null) || fUpload.getValue("notificationremove").trim().equals("")) {
                    base.removeAttribute("notifyremove");
                    strNotify = "";
                    flag = true;
                } else {
                    base.setAttribute("notifyremove", fUpload.getValue("notificationremove").trim());
                    strNotify = fUpload.getValue("notificationremove");
                    flag = true;
                }
                if (flag) {
                    base.updateAttributesToDB();
                }
            } catch (Exception e) {
                log.error(" Error - Repository.doAdmin.addrule");
            }
            out.println(paramsRequest.getLocaleString("msgInfoUpdated") + "</td>");
            out.println("</tr>");
            out.println("</table>");
        }
    }

    /**
     * Indicates if the files could be recover
     *
     * @param p_topic Topic identifier
     * @return True or false if the file could be recover
     */
    public boolean canRecover(String p_tm, String p_topic) {
        boolean result = false;
        String s_topic = null;
        String s_topicmap = null;
        //TopicMgr tpmgr = TopicMgr.getInstance();
        s_topicmap = p_tm;
        s_topic = p_topic;
        WebSite topicMap = WebSite.ClassMgr.getWebSite(s_topicmap);
        WebPage topic = topicMap.getWebPage(s_topic); //,true);
        if (topic != null) {
            result = true;
        }
        return result;
    }

    /**
     * Indicates if the folder could be recover
     *
     * @param tp Topic object that represents the folder
     * @param Repository Topic object that represents the main directory of the
     * repository
     */
    public void recuperarFolder(WebPage tp, WebPage Repository) {
        try {
            tp.setDeleted(false);
            //tp.getDbdata().update();
            if (tp.getParent() != null && !Repository.getId().equals(tp.getParent().getId()) && tp.getParent().isDeleted()) {
                recuperarFolder(tp.getParent(), Repository);
            }
        } catch (Exception e) {
            log.error("Error al recuperar los sub directorios eliminados.", e);
        }
    }

    /**
     * Check the folder to find some file
     *
     * @param tm Topic map object
     * @param dir Topic object that represents the folder
     * @param repository Topic object that represents the repository
     * @param id File identifier
     */
    public void checkDir(WebSite tm, WebPage dir, WebPage repository, long id) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        if (dir != null && repository != null && !dir.getId().equals(repository.getId())) {
            try {
                String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
                conn = SWBUtils.DB.getConnection(tmp_conn, "Repository.checkDir()");
                //conn=WBUtils.getInstance().getDBConnection();
                pst = conn.prepareStatement("select * from resrepository where rep_docId<>? and  idtm=? and topic=?");
                pst.setLong(1, id);
                pst.setString(2, tm.getId());
                pst.setString(3, dir.getId());
                rs = pst.executeQuery();
                if (rs.next()) {
                    rs.close();
                    pst.close();
                    conn.close();
                    return;
                } else {
                    WebPage tptmp = dir.getParent();
                    dir.remove();
                    //tm.removeTopic(dir.getId());
                    //tm.update2DB();
                    rs.close();
                    pst.close();
                    conn.close();
                    if (tptmp != null && !repository.getId().equals(tptmp.getId()) && tptmp.isDeleted()) {
                        checkDir(tm, tptmp, repository, id);
                    }
                }
            } catch (Exception e) {
                log.error("Error al eliminar los sub folders.", e);
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    rs = null;
                } catch (Exception e1) {
                }
                try {
                    if (pst != null) {
                        pst.close();
                    }
                    pst = null;
                } catch (Exception e2) {
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                    conn = null;
                } catch (Exception e3) {
                }
            }
        }
    }

    /**
     * Get the Applet code
     *
     * @param tpid Topic identifier
     * @param tmid Topic map identifier
     * @param paramsRequest The list of objects (topic, user, action, ...))
     * @return A string value with the applet html call
     */
    public String tmApplet(String tpid, String tmid, SWBParamRequest paramsRequest) {

        StringBuffer sbfRet = new StringBuffer();
        sbfRet.append("<APPLET id=\"appttmadmin\" name=\"appttmadmin\" code=\"applets.mapsadm.TMWBAdmin.class\" codebase=\"" + SWBPlatform.getContextPath() + "\" ARCHIVE=\"swbadmin/lib/TMWBAdmin.jar, swbadmin/lib/WBCommons.jar\" width=\"100%\" height=\"100%\">");
        SWBResourceURL url = paramsRequest.getRenderUrl();
        url.setCallMethod(url.Call_DIRECT);
        sbfRet.append("<PARAM NAME =\"cgipath\" VALUE=\"" + url + "\">");
        sbfRet.append("<PARAM NAME=\"foreground\" VALUE=\"3f88b4\">");
        sbfRet.append("<PARAM NAME=\"background\" VALUE=\"edf2f3\">");
        sbfRet.append("<PARAM NAME=\"foregroundSelection\" VALUE=\"ffffff\">");
        sbfRet.append("<PARAM NAME=\"backgroundSelection\" VALUE=\"666699\">");
        sbfRet.append("<PARAM NAME=\"locale\" VALUE=\"" + paramsRequest.getUser().getLanguage() + "\">");
        if (tmid != null) {
            sbfRet.append("<PARAM NAME=\"TM\" VALUE=\"" + tmid + "\">"); //request.getParameter("tm")
        }
        if (tpid != null) {
            sbfRet.append("<PARAM NAME=\"TP\" VALUE=\"" + tpid + "\">"); //request.getParameter("tp")
        }
        sbfRet.append("\n</APPLET>");

        return sbfRet.toString();
    }

    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */
    @Override
    public void doIndex(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        //System.out.println("Repository.doIndex");
        Resource base = getResourceBase();
        String idtm = base.getWebSiteId();
        String resid = base.getId();
        SWBIndexer w_indx = SWBPortal.getIndexMgr().getDefaultIndexer();

        Connection conn = null;
        PreparedStatement pst = null;
        PreparedStatement pst1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        try {
            
            String tmp_conn = SWBPlatform.getEnv("wb/db/nameconn", "wb");
            conn = SWBUtils.DB.getConnection(tmp_conn, "Repository.doIndex()");

            pst = conn.prepareStatement("select topic,rep_title,rep_description,rep_docId,rep_lastVersion from resrepository where resId=? and idtm=?");
            pst.setString(1, resid);
            pst.setString(2, idtm);
            rs = pst.executeQuery();
            while (rs.next()) {
                String webpageid = rs.getString("topic");
                String title = rs.getString("rep_title");
                String description = rs.getString("rep_description");
                long docid = rs.getLong("rep_docId");
                int i_version = rs.getInt("rep_lastVersion");
                pst1 = conn.prepareStatement("select rep_fileName from resrepositoryversions where rep_docId=? and rep_fileVersion=? and idtm=?");
                pst1.setLong(1, docid);
                pst1.setInt(2, i_version);
                pst1.setString(3, idtm);
                rs1 = pst1.executeQuery();
                while (rs1.next()) {
                    String file_name = rs1.getString("rep_fileName");
                    int pos = file_name.lastIndexOf('.');
                    if (pos != -1) {
                        String file_db = docid + "_" + i_version + file_name.substring(pos);;
                        File f = new File(SWBPortal.getWorkPath() + "/" + base.getWorkPath() + "/" + file_db);
                        ////////////////// quitar del indice anterior
                        if (f.exists()) {
                            try {
                                w_indx.removeSearchable("file:" + f.getAbsolutePath());
                            } catch (Exception ex) {
                                Repository.log.error("Error while trying to remove a file from index.", ex);
                            }

                            //////////////           

                            ///// agregando archivo al nuevo indice
                            try {
                                String type = f.getName();
                                SWBResourceURL urllineA = paramRequest.getRenderUrl();
                                urllineA.setCallMethod(SWBResourceURL.Call_DIRECT);
                                urllineA.setMode(SWBResourceURL.Mode_VIEW);
                                String url = "" + urllineA + "/" + type + "?repfop=view&reptp=" + webpageid + "&repfiddoc=" + Long.toString(docid) + "&repinline=true";
                                FileSearchWrapper fsw = new FileSearchWrapper(f, title, description, null, url, base);
                                w_indx.indexSerchable(fsw);
                            } catch (Exception ex_ind) {
                                Repository.log.error("Error while trying to index file.", ex_ind);
                            }
                        }
                        //////////////////////
                    }
                }
                rs1.close();
                pst1.close();
            }
            rs.close();
            pst.close();
            conn.close();
        } catch (Exception e) {
            log.error(SWBUtils.TEXT.iso8601DateFormat(new java.util.Date(System.currentTimeMillis())) + " - Error while trying to index files of Repository resource with id:" + resid + ", from WebSite:" + idtm, e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    log.error("Error in Repository.doIndex", e);
                }
            }
        }
    }
}
