/*
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
 */
package org.semanticwb.resources.filerepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.jcr.Credentials;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
//import javax.jcr.Property;
//import javax.jcr.PropertyIterator;
import javax.jcr.Session;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
//import org.semanticwb.jcr170.implementation.PropertyIteratorImp;
import org.semanticwb.jcr170.implementation.SWBCredentials;
import org.semanticwb.jcr170.implementation.SWBRepository;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Role;

import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.util.FileUpload;
//import org.semanticwb.repository.VersionHistory;

//org.semanticwb.resources.filerepository.SemanticRepositoryFile
public class SemanticRepositoryFile extends org.semanticwb.resources.filerepository.base.SemanticRepositoryFileBase {

    private static final String SWB_FILEREP_DELETED = "swbfilerep:deleted";
    private static final String JCR_LASTMODIFIED = "jcr:lastModified";
    private static final String SWBFILEREPFILESIZE = "swbfilerep:filesize";
    private static final String SWBFILEREPUSERID = "swbfilerep:userid";
    private static final String SWBFILEREPCOMMENT = "swbfilerep:comment";
    private static SWBRepository rep = null;
    private static Logger log = SWBUtils.getLogger(SemanticRepositoryFile.class);
    private static final String MODE_FILES = "showfiles";
    private static final String MODE_DIRS = "showdirs";
    private static final String MODE_ADDFORM = "getAddForm";
    private static final String MODE_GETFILE = "getFile";
    private static final String MODE_SELFOLDER = "selFolder";
    private static final String FILE_DETAIL = "fdetail";
    private static final String REP_FILE = "swbfilerep:RepositoryFile";
    private static final String REP_FOLDER = "swbfilerep:RepositoryFolder";
    private static final String REP_NODE = "swbfilerep:RepositoryNode";
    private static final String JCR_CONTENT = "jcr:content";
    private static final String JCR_FROZEN_NODE = "jcr:frozenNode";
    private static final String CONTENT_NOT_FOUND = "El contenido no se encontró en el repositorio.";
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    private String IDREP = null;
    private String resUUID = null;
    private static final String PARAM_UUID = "UUID";

    static {
        try {
            rep = new SWBRepository();
        } catch (Exception e) {
            log.error(e);
        }
    }

    public SemanticRepositoryFile() {
    }

    public SemanticRepositoryFile(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals(MODE_FILES)) {
            doShowFiles(request, response, paramRequest);
        } else if (paramRequest.getMode().equals(MODE_DIRS)) {
            doShowDirs(request, response, paramRequest);
        } else if (paramRequest.getMode().equals(MODE_ADDFORM)) {
            doAddForm(request, response, paramRequest);
        } else if (paramRequest.getMode().equals(MODE_GETFILE)) {
            doGetFile(request, response, paramRequest);
        } //        else if (paramRequest.getMode().equals(MODE_SELFOLDER)) {
        //            doSelectFolders(request, response, paramRequest);
        //        }
        else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();

        WebPage wp = paramRequest.getWebPage();
        IDREP = docRepNS(request, response, paramRequest);

        resUUID = getResourceBase().getAttribute(wp.getId() + "_uuid");

        if (resUUID == null) {
            loadResUUID(wp, user);
        }

        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";
        out.println("<div style=\"font-family:verdana; font-size:10px;\">"); //class=\"SemanticRepositoryFile\"
        out.println("<fieldset>");
        out.println("<legend> <img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + getTitle(request, paramRequest) + "</legend>");
        out.println("");
        if (user != null && user.isSigned()) {
            if (!isUseFolders()) {
                out.println(doShowFiles(request, response, paramRequest));
            } else {
                out.println(doShowDirs(request, response, paramRequest));
            }
        } else {
            out.println(paramRequest.getLocaleString("mustBeSigned"));
        }
        out.println("</fieldset>");
        out.println("</div>");
    }

    private void loadResUUID(WebPage wp, User user) {
        Session session = null;
        try {
            User usrcreator = getResourceBase().getCreator();
            if (usrcreator == null && user != null) {
                usrcreator = user;
            }

            session = rep.login(new SWBCredentials(usrcreator), IDREP);
            Node root = null;
            Node nodeRep = null;
            Node nodePage = null;
            root = session.getRootNode();

            //Revisando nodo base del recurso

            if (getResourceBase().getAttribute("repNode_uuid", "").equals("")) {
                nodeRep = root.addNode(getResourceBase().getId(), REP_NODE);
                getResourceBase().setAttribute("repNode_uuid", nodeRep.getUUID());
            } else {
                nodeRep = session.getNodeByUUID(getResourceBase().getAttribute("repNode_uuid"));
            }

            //Revisando el nodo asociado a la página

            if (nodeRep != null && wp != null && !nodeRep.hasNode(wp.getId())) {
                nodePage = nodeRep.addNode(wp.getId(), REP_FOLDER);
                nodePage.setProperty("swb:title", wp.getDisplayName());
                nodePage.setProperty(SWBFILEREPUSERID, usrcreator.getId());
                nodePage.setProperty("swb:description", "");
                nodePage.setProperty(SWB_FILEREP_DELETED, Boolean.FALSE);
            }

            session.save();

            resUUID = nodePage.getUUID();

            if (wp != null) {
                getResourceBase().setAttribute(wp.getId() + "_uuid", resUUID);
            }

            getResourceBase().updateAttributesToDB();

        } catch (Exception e) {
            log.error("Error al revisar el nodo raíz del Rep. de Documentos. SemanticRepositoryFile.loadResUDDI()", e);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }

    public String doShowFiles(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer("");
        User user = paramRequest.getUser();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss");
        int luser = getLevelUser(user);
        Session session = null;

        String parentUUID = request.getParameter("parentUUID");
        IDREP = docRepNS(request, response, paramRequest);
        WebPage wp = paramRequest.getWebPage();

        resUUID = getResourceBase().getAttribute(wp.getId() + "_uuid");
        if (resUUID == null) {
            loadResUUID(paramRequest.getWebPage(), user);
        }
        if (parentUUID == null) {
            parentUUID = resUUID;
        }

        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";
        String UUID = "";
        try {
            session = rep.login(new SWBCredentials(user), IDREP);

            //Nodo asociado al id de la página
            Node nodePage = session.getNodeByUUID(parentUUID);
            NodeIterator nit = nodePage.getNodes();

            ret.append(getLinkStyle());

            ret.append("\n<div style=\"position: relative; float: left;width: 600px;\">"); // class=\"swbform\"
            ret.append("\n<fieldset>");
            ret.append("\n<table width=\"100%\">");
            ret.append("\n<thead >");

            ret.append("\n<tr >");
            ret.append("\n<th colspan=\"4\">");
            ret.append("\n" + nodePage.getProperty("swb:title").getString());
            ret.append("\n</th>");
//            ret.append("\n<th colspan=\"2\">");
//            //ret.append("\n<img src=\"" + path + "suscribe.gif\" border=\"0\"/>Suscribirse a directorio");
//            ret.append("\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
//            ret.append("\n</th>");
            ret.append("\n<th colspan=\"2\" style=\"text-align:right;\">");
            SWBResourceURL urladd = paramRequest.getRenderUrl();
            urladd.setAction("addfile");
            urladd.setParameter("parentUUID", parentUUID);
            urladd.setMode(MODE_ADDFORM);

            if (user != null && luser > 1) {
                ret.append("\n<a href=\"" + urladd + "\">");
                ret.append("\n<img src=\"" + path + "add.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("addFile") + "\"/>");
                ret.append("\n" + paramRequest.getLocaleString("addFile"));
                ret.append("\n</a>");
            }
            ret.append("\n</th>");
            ret.append("\n</tr>");

            SWBResourceURL urlorder = paramRequest.getRenderUrl();
            urlorder.setParameter("parentUUID", parentUUID);


            ret.append("\n<tr bgcolor=\"#B8B8B8\">");
            ret.append("\n<th >");
            ret.append("\n" + paramRequest.getLocaleString("msgInfo"));
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\n<a href=\"" + urlorder + "&orderBy=type\" title=\"Ordenar por tipo\">" + paramRequest.getLocaleString("msgType") + "</a>");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\n<a href=\"" + urlorder + "&orderBy=title\" title=\"Ordenar por título\">" + paramRequest.getLocaleString("msgTitle") + "</a>");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\n<a href=\"" + urlorder + "&orderBy=date\" title=\"Ordenar por fecha\">" + paramRequest.getLocaleString("msgDate") + "</a>");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\n" + paramRequest.getLocaleString("msgSelect"));
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\n" + paramRequest.getLocaleString("msgAction"));
            ret.append("\n</th>");
            ret.append("\n</tr>");
            ret.append("\n</thead>");

            int nfiles = 0;
            if (nit.hasNext()) {

                String orderBy = request.getParameter("orderBy");
                if (null == orderBy) {
                    orderBy = "title";
                }


                HashMap<String, Node> hmNodes = new HashMap<String, Node>();

                while (nit.hasNext()) {
                    Node nodofolder = nit.nextNode();

                    String skey = nodofolder.getUUID();

                    if (orderBy.equals("title")) {
                        skey = nodofolder.getProperty("swb:title").getString()+ "-" +nodofolder.getUUID();

                    } else if (orderBy.equals("date")) {
                        //nodo.getProperty("jcr:created").getDate().getTime())
                        skey = nodofolder.getProperty("jcr:created").getDate().getTime() + "-" +nodofolder.getUUID();

                    } else if (orderBy.equals("type")) {
                        String file = nodofolder.getName();
                        String type = getFileName(file);

                        skey = type + "-" + nodofolder.getProperty("swb:title").getString()+ "-" +nodofolder.getUUID();
                        hmNodes.put(skey, nodofolder);
                    }
                    hmNodes.put(skey, nodofolder);
                }

                ArrayList list = new ArrayList(hmNodes.keySet());
                Collections.sort(list);

                ret.append("\n<tbody>");

                String bgcolor = "#FFFFFF";

                Iterator<String> lnit = list.iterator();

                while (lnit.hasNext()) {

                    String skey = lnit.next();
                    Node nodo = hmNodes.get(skey);

                    if (nodo.getPrimaryNodeType().getName().equals(REP_FILE)) {
                        boolean isdeleted = false;
                        try {
                            isdeleted = nodo.getProperty(SWB_FILEREP_DELETED).getBoolean();
                        } catch (Exception e) {
                            log.event("Error al revisar la propiedad Deleted del repositorio de documentos.", e);
                        }

                        if (nodo != null && !isdeleted) {
                            nfiles++;
                            UUID = nodo.getUUID();



                            ret.append("\n<tr bgcolor=\"" + bgcolor + "\">");

                            if (bgcolor.equals("#FFFFFF")) {
                                bgcolor = "#E8E8E8";
                            } else {
                                bgcolor = "#FFFFFF";
                            }

                            ret.append("\n<td>");
                            SWBResourceURL urldetail = paramRequest.getRenderUrl();
                            urldetail.setMode(MODE_ADDFORM);
                            urldetail.setParameter(PARAM_UUID, nodo.getUUID());
                            urldetail.setParameter("repNS", IDREP);
                            urldetail.setAction(FILE_DETAIL);
                            urldetail.setParameter("parentUUID", parentUUID);
                            ret.append("\n<a href=\"#\" onclick=\"window.location='" + urldetail + "';\"><img src=\"" + path + "info.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgFileDetail") + "\"/></a>");
                            ret.append("\n</td>");
                            ret.append("\n<td>");
                            String file = nodo.getName();
                            String type = getFileName(file);

                            SWBResourceURL urlgetfile = paramRequest.getRenderUrl();
                            urlgetfile.setMode(MODE_GETFILE);
                            urlgetfile.setAction("inline");
                            urlgetfile.setCallMethod(SWBResourceURL.Call_DIRECT);
                            urlgetfile.setWindowState(SWBResourceURL.WinState_MAXIMIZED);

                            ret.append("\n<a href=\"" + urlgetfile + "/" + UUID + "/" + file + "\" onclick=\"window.location='" + urlgetfile + "/" + UUID + "/" + file + "';return false;\" alt=\"\"><img border=0 src='" + path + "" + type + "' alt=\"" + getFileType(file) + "\" /></a>");
                            ret.append("\n</td>");
                            ret.append("\n<td>");
                            ret.append("\n" + nodo.getProperty("swb:title").getString());
                            ret.append("\n</td>");
                            ret.append("\n<td>");

                            ret.append("\n" + sdf.format(nodo.getProperty("jcr:created").getDate().getTime()));
                            ret.append("\n</td>");
                            ret.append("\n<td>");
                            SWBResourceURL uout = paramRequest.getActionUrl();
                            uout.setParameter(PARAM_UUID, UUID);
                            uout.setParameter("repNS", IDREP);
                            uout.setParameter("parentUUID", parentUUID);

                            Node ndata = nodo.getNode(JCR_CONTENT);
                            String s_usrID = ndata.getProperty(SWBFILEREPUSERID).getString();

                            User autor = user.getUserRepository().getUser(s_usrID);

                            String fullname = paramRequest.getLocaleString("msgUsrAnonymous");
                            if (autor != null) {
                                fullname = autor.getFullName();
                            }


                            if (!ndata.isCheckedOut() && luser > 1) {
                                SWBResourceURL duout = paramRequest.getActionUrl();
                                duout.setParameter(PARAM_UUID, UUID);
                                duout.setParameter("repNS", IDREP);
                                duout.setAction("check");
                                duout.setParameter("faction", "out");
                                duout.setParameter("parentUUID", parentUUID);

                                ret.append("\n<a href=\"#\" onclick=\"window.location='" + duout + "';\">");
                                ret.append("\n<img src=\"" + path + "out.gif\" border=\"0\"/>");
                                ret.append("\n</a>");
                            } else if (paramRequest.getUser().getId().equals(autor.getId())&&luser>1) {
                                //ret.append("\n<img src=\"" + path + "reserved.gif\" border=\"0\"/>");
                                SWBResourceURL uin = paramRequest.getRenderUrl();
                                uin.setParameter(PARAM_UUID, UUID);
                                uin.setParameter("repNS", IDREP);
                                uin.setMode(SWBResourceURL.Mode_EDIT);
                                uin.setAction("checkin");
                                uin.setParameter("parentUUID", parentUUID);
                                ret.append("\n<a href=\"#\" onclick=\"window.location='" + uin + "';\">");
                                ret.append("\n<img src=\"" + path + "in.gif\" border=\"0\"alt=\"" + paramRequest.getLocaleString("msgFileUpdate") + "\"/>");
                                ret.append("\n</a>");
                                uout.setParameter("faction", "undo");
                                uout.setAction("check");
                                ret.append("\n<a href=\"#\" onclick=\"window.location='" + uout + "';\">");
                                ret.append("\n<img src=\"" + path + "undo.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgFileUnlock") + "\"/>");
                                ret.append("\n</a>");
                            } else {
                                if (luser > 1) {
                                    ret.append("\n<img src=\"" + path + "reserved.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgFileReserved") + "\"/>");
                                }
                            }

                            if (luser == 3 && ndata.isCheckedOut() && !paramRequest.getUser().getId().equals(autor.getId())) {
                                uout.setParameter("faction", "undo");
                                uout.setAction("check");
                                ret.append("\n<a href=\"#\" onclick=\"window.location='" + uout + "';\">");
                                ret.append("\n<img src=\"" + path + "undo.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgFileUnlock") + "\"/>");
                                ret.append("\n</a>");

                            }
                            ret.append("\n</td>");
                            ret.append("\n<td>");
                            SWBResourceURL usus = paramRequest.getRenderUrl();
                            usus.setParameter(PARAM_UUID, UUID);
                            usus.setParameter("repNS", IDREP);
                            SWBResourceURL uview = paramRequest.getRenderUrl();
                            uview.setParameter(PARAM_UUID, UUID);
                            uview.setParameter("repNS", IDREP);
                            //ret.append("\n<img src=\"" + path + "suscribe.gif\" border=\"0\"/>");

                            ret.append("\n<a href=\"" + urlgetfile + "/" + UUID + "/" + file + "\" onclick=\"window.location='" + urlgetfile + "/" + UUID + "/" + file + "'; return false;\" alt=\"\"><img src=\"" + path + "preview.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgViewFile") + "\"/></a>");


                            if ((user != null && user.getId().equals(autor.getId())) || luser == 3) {
                                SWBResourceURL udel = paramRequest.getActionUrl();
                                udel.setParameter(PARAM_UUID, UUID);
                                udel.setParameter("repNS", IDREP);
                                udel.setAction("removefile");
                                if(null!=parentUUID) udel.setParameter("parentUUID", parentUUID);
                                ret.append("\n<a href=\"#\" onclick=\"if(confirm('" + paramRequest.getLocaleString("msgAlertConfirmRemoveFile") + " " + nodo.getName() + "?')){ window.location='" + udel + "';} else { return false; }\">");
                                ret.append("\n<img src=\"" + path + "delete.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgALTDelete") + "\"/>");
                                ret.append("\n</a>");
                            }

                            //TODO: Para mover archivos de carpetas
//                            if (isUseFolders()) {
//                                SWBResourceURL umove = paramRequest.getRenderUrl();
//                                umove.setParameter("uuid", UUID);
//                                umove.setParameter("parent_wp", nodePage.getUUID());
//                                umove.setAction("movefolder");
//                                umove.setMode("selectFolder");
//                                umove.setParameter("repNS", IDREP);
//                                ret.append("<a href=\"" + umove + "\">");
//                                ret.append("\n<img src=\"" + path + "folder.gif\" border=\"0\"/>");
//                                ret.append("</a>");
//                            }

                            ret.append("\n</td>");
                            ret.append("\n</tr>");
                        }
                    }
                }

                ret.append("\n</tbody>");
            }
            ret.append("\n<tfoot>");
            ret.append("\n<tr>");
            ret.append("\n<th colspan=\"2\">");
            ret.append("\n" + nfiles + " " + paramRequest.getLocaleString("msgFiles"));
            ret.append("\n</th>");
            ret.append("\n<th colspan=\"2\">");
            ret.append("\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            ret.append("\n</th>");
            ret.append("\n<th colspan=\"2\" style=\"text-align:right;\">");
            if (user != null && luser > 1) {
                ret.append("\n<a style=\"text-decoration:none;\" href=\"" + urladd + "\">");
                ret.append("\n<img src=\"" + path + "add.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgAltAddFile") + "\"/>");
                ret.append("\n" + paramRequest.getLocaleString("msgALTAddFile"));
                ret.append("\n</a>");
            }

            ret.append("\n</th>");
            ret.append("\n</tr>");
            ret.append("\n</tfoot>");

            ret.append("\n</table>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");

        } catch (Exception e) {
            log.error("Error al traer los archivos de folder del repositorio de documentos", e);
        } finally {
            if (session != null) {
                session.logout();
            }
        }

        if (request.getParameter("getfile") != null && request.getParameter("getfile").equals("true")) {

            SWBResourceURL urlgetfile = paramRequest.getRenderUrl();
            urlgetfile.setMode(MODE_GETFILE);
            urlgetfile.setParameter(PARAM_UUID, request.getParameter("fuuid"));
            urlgetfile.setParameter("repNS", request.getParameter("repNS"));
            urlgetfile.setAction("inline");
            urlgetfile.setCallMethod(SWBResourceURL.Call_DIRECT);
            urlgetfile.setWindowState(SWBResourceURL.WinState_MAXIMIZED);
            ret.append("<div style=\"diplay:none;\"><iframe src=\"" + urlgetfile + "\" style=\"display: none;\"/></div>");
        }


        return ret.toString();
    }

//    public String doSelectFolders(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        StringBuffer ret = new StringBuffer("");
//        ret.append("\n<div >");
//        ret.append("\n<ul>");
//        ret.append("\n</ul>");
//        ret.append("\n</div>");
//        ret.append(doShowFiles(request, response, paramRequest));
//        return ret.toString();
//    }
    public String doShowDirs(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer("");
        User user = paramRequest.getUser();
        int luser = getLevelUser(user);
        WebPage wp = paramRequest.getWebPage();
        Session session = null;
        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";
        IDREP = docRepNS(request, response, paramRequest);
        if (resUUID == null) {
            loadResUUID(wp, user);
        }
        ret.append("\n<script>include_dom('" + SWBPlatform.getContextPath() + "/swbadmin/js/repositoryfile.js');</script>");
        ret.append(getLinkStyle());
        ret.append("\n<div style=\"position: relative; float: left;width: 240px;\">");
        SWBResourceURL urlfol = paramRequest.getRenderUrl();
        //urlfol.setAction("addfolder");
        urlfol.setMode(SWBResourceURL.Mode_VIEW);
        String formid = "repAddFolder" + getResourceBase().getId();

        try {
            ret.append("\n<fieldset>");

            ret.append("\n<ul style=\"list-style-type:none; text-decoration:none;\">");

            session = rep.login(new SWBCredentials(user), IDREP);
            Node nodePage = session.getNodeByUUID(resUUID);

            if (user != null && user.isSigned()) {

                SWBResourceURL urledit = paramRequest.getRenderUrl();
                urledit.setAction("editfolder");
                urledit.setMode(MODE_ADDFORM);
                urledit.setParameter("parentUUID", resUUID);
                urledit.setParameter(PARAM_UUID, resUUID);

                ret.append("\n<li style=\"list-style-type:none; text-decoration:none;\">");
                ret.append("\n<a href=\"#\" onclick=\"window.location='" + urledit + "';\"><img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/></a>");
                ret.append("\n<a href=\"#\" onclick=\"javascript:setParentUUID(document." + formid + ",'" + resUUID + "'); window.location='" + urlfol + "';\">" + nodePage.getProperty("swb:title").getString() + "</a></li>");
                //ret.append("\n<a href=\"#\" onclick=\"window.location='" + urledit + "';\"><img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"edit folder\"/></a>");
            } else {
                ret.append("<li style=\"list-style-type:none;\"><a href=\"#\"><img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/>" + nodePage.getProperty("swb:title").getString() + "</a></li>");
            }
            NodeIterator nit = nodePage.getNodes(REP_FOLDER);
            if (nit.hasNext()) {

                HashMap<String, Node> hmNodes = new HashMap<String, Node>();

                while (nit.hasNext()) {
                    Node nodofolder = nit.nextNode();

                    boolean isDeleted = Boolean.FALSE;
                    if (nodofolder.getProperty(SWB_FILEREP_DELETED) != null) {
                        //System.out.println("folder borrado: "+nodofolder.getProperty(SWB_FILEREP_DELETED).getBoolean());
                        isDeleted = nodofolder.getProperty(SWB_FILEREP_DELETED).getBoolean();
                    }

                    String skey = nodofolder.getProperty("swb:title").getString();
                    if (!isDeleted) {
                        hmNodes.put(skey, nodofolder);
                    }
                }

                ArrayList list = new ArrayList(hmNodes.keySet());
                Collections.sort(list);

                ret.append("\n<ul>");
                Iterator<String> itdis = list.iterator();
                while (itdis.hasNext()) {

                    String key = itdis.next();

                    Node nodofolder = hmNodes.get(key);

                    SWBResourceURL urledit = paramRequest.getRenderUrl();
                    urledit.setAction("editfolder");
                    urledit.setMode(MODE_ADDFORM);
                    urledit.setParameter("parentUUID", resUUID);
                    urledit.setParameter(PARAM_UUID, nodofolder.getUUID());

                    if (user != null && user.isSigned()) {
                        ret.append("\n<li style=\"list-style-type:none;\">");
                        ret.append("\n<a href=\"#\" onclick=\"window.location='" + urledit + "';\"><img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"edit folder\"/></a>");
                        ret.append("\n<a href=\"#\" onclick=\"javascript:setParentUUID(document." + formid + ",'" + nodofolder.getUUID() + "'); window.location='" + urlfol + "?parentUUID=" + nodofolder.getUUID() + "';\">" + nodofolder.getProperty("swb:title").getString() + "</a></li>");
                    } else {
                        ret.append("<li style=\"list-style-type:none;\"><a href=\"#\"><img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/>" + nodofolder.getProperty("swb:title").getString() + "</a></li>");
                    }
                }
                ret.append("\n</ul>");
            }
        } catch (Exception e) {
        } finally {
            if (session != null) {
                session.logout();
            }
        }
        ret.append("\n</ul>");
        ret.append("\n</fieldset>");

        if (user != null && user.isSigned() && luser>2) {
            ret.append("\n<fieldset>");
            SWBResourceURL urladd = paramRequest.getRenderUrl();
            urladd.setAction("addfolder");
            urladd.setMode(MODE_ADDFORM);
            ret.append("\n<form name=\"" + formid + "\" action=\"" + urladd + "\" method=\"post\">");
            ret.append("\n<input type=\"hidden\" name=\"parentUUID\" value=\"" + resUUID + "\">");
            ret.append("\n<input type=\"submit\" value=\"" + paramRequest.getLocaleString("msgBtnAddFolder") + "\">");
            ret.append("\n</form>");
            ret.append("\n</fieldset>");
        }
        ret.append("\n</div>");

        ret.append(doShowFiles(request, response, paramRequest));
        return ret.toString();
    }

    public void doAddForm(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer ret = new StringBuffer();

        Session session = null;
        String parentUUID = request.getParameter("parentUUID");
        String fUUID = request.getParameter(PARAM_UUID);
        String action = paramRequest.getAction();
        if (action == null) {
            action = "";
        }
        IDREP = docRepNS(request, response, paramRequest);
        WebPage dir = paramRequest.getWebPage();
        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";
        User user = paramRequest.getUser();
        Credentials credentials = new SWBCredentials(user);
        SWBResourceURL url = paramRequest.getActionUrl();
        if (parentUUID == null) {
            parentUUID = resUUID;
        }

        int luser = getLevelUser(user);
        if (luser < 1) {
            return;
        }

        ret.append("\n<script>include_dom('" + SWBPlatform.getContextPath() + "/swbadmin/js/repositoryfile.js');</script>");

        ret.append(getLinkStyle());

        if (action.equals("addfile")) {

            String folderTitle = dir.getDisplayName();
            if (!resUUID.equals(parentUUID)) {
                try {

                    session = rep.login(credentials, IDREP);
                    Node nf = session.getNodeByUUID(parentUUID);
                    if (null != nf) {
                        folderTitle = folderTitle + " - " + nf.getProperty("swb:title").getString();
                    }
                } catch (Exception e) {
                    log.error("Error al tratar de cargar el nodo de la carpeta con UUID: " + parentUUID, e);
                } finally {
                    if (session != null) {
                        session.logout();
                    }
                }
            }
            url.setAction("addfile");
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<form name=\"frmnewdoc\" method=\"post\" enctype=\"multipart/form-data\" action=\"" + url.toString() + "\">");
            ret.append("\n<fieldset>");
            ret.append("\n<legend>");
            ret.append("\n<img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + folderTitle);
            ret.append("\n</legend>");
            //ret.append("\n<form name=\"frmnewdoc\" method=\"post\" enctype=\"multipart/form-data\" action=\"" + url.toString() + "\">");
            ret.append("\n<input type=\"hidden\" name=\"repNS\" value=\"" + IDREP + "\">");
            ret.append("\n<input type=\"hidden\" name=\"parentUUID\" value=\"" + parentUUID + "\">");
            ret.append("\n<table width=\"100%\"  border=\"0\">");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgTitleDocument"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<input  type=\"text\" maxlength=\"99\" name=\"repftitle\">");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgDescription"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<textarea rows=\"5\" name=\"repfdescription\" cols=\"20\" onKeyDown=\"textCounter(this.form.repfdescription,255);\" onKeyUp=\"textCounter(this.form.repfdescription,255);\"></textarea>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgFile"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"file\"  name=\"repfdoc\">");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n</table>");
            ret.append("\n</fieldset>");
            ret.append("\n<fieldset>");
            ret.append("\n<input type=\"button\"  name=\"s\" value=\"" + paramRequest.getLocaleString("msgBTNSave") + "\" onclick=\"javascript:valida();\" />\r\n");
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setMode(SWBResourceURL.Mode_VIEW);
            urlb.setParameter("parentUUID", parentUUID);
            ret.append("\n<input type=\"button\"  name=\"cancel\" value=\"" + paramRequest.getLocaleString("msgBTNCancel") + "\" onclick=\"window.location='" + urlb.toString() + "';\" />\r\n");
            //ret.append("\n</form>");
            ret.append("\n</fieldset>");
            ret.append("\n</form>");
            ret.append("\n</div>");
        } //        else if (action.equals("editfile")) {
        //
        //            String folderTitle = dir.getDisplayName();
        //            if (!resUUID.equals(parentUUID)) {
        //                try {
        //
        //                    session = rep.login(credentials, IDREP);
        //                    Node nf = session.getNodeByUUID(parentUUID);
        //                    if (null != nf) {
        //                        folderTitle = folderTitle + " - " + nf.getProperty("swb:title").getString();
        //                    }
        //                } catch (Exception e) {
        //                    log.error("Error al tratar de cargar el nodo de la carpeta con UUID: " + parentUUID, e);
        //                } finally {
        //                    if (session != null) {
        //                        session.logout();
        //                    }
        //                }
        //            }
        //            url.setAction("updatefile");
        //            ret.append("\n<div class=\"swbform\">");
        //            ret.append("\n<form name=\"frmnewdoc\" method=\"post\"  action=\"" + url.toString() + "\">"); //enctype=\"multipart/form-data\"
        //            ret.append("\n<fieldset>");
        //            ret.append("\n<legend>");
        //            ret.append("\n<img src=\"" + path + "icon-foldera.gif\" border=\"0\" title=\"" + folderTitle+"\"/> " + folderTitle);
        //            ret.append("\n</legend>");
        //            //ret.append("\n<form name=\"frmnewdoc\" method=\"post\" enctype=\"multipart/form-data\" action=\"" + url.toString() + "\">");
        //            ret.append("\n<input type=\"hidden\" name=\"repNS\" value=\"" + IDREP + "\">");
        //            ret.append("\n<input type=\"hidden\" name=\"parentUUID\" value=\"" + parentUUID + "\">");
        //            ret.append("\n<input type=\"hidden\" name=\"UUID\" value=\"" + UUID + "\">"); //uuid file
        //            ret.append("\n<table width=\"100%\"  border=\"0\">");
        //            ret.append("\n<tr>");
        //            ret.append("\n<td width=\"200\">");
        //            ret.append("\n" + paramRequest.getLocaleString("msgTitleDocument"));
        //            ret.append("\n</td>");
        //            ret.append("\n<td>");
        //            ret.append("\n<input  type=\"text\" maxlength=\"99\" name=\"repftitle\" value=\""+stitle+"\">");
        //            ret.append("\n</td>");
        //            ret.append("\n</tr>");
        //            ret.append("\n<tr>");
        //            ret.append("\n<td width=\"200\">");
        //            ret.append("\n" + paramRequest.getLocaleString("msgDescription"));
        //            ret.append("\n</td>");
        //            ret.append("\n<td>");
        //            ret.append("\n<textarea rows=\"5\" name=\"repfdescription\" cols=\"20\" onKeyDown=\"textCounter(this.form.repfdescription,255);\" onKeyUp=\"textCounter(this.form.repfdescription,255);\">"+sdescription+"</textarea>");
        //            ret.append("\n</td>");
        //            ret.append("\n</tr>");
        ////            ret.append("\n<tr>");
        ////            ret.append("\n<td width=\"200\">");
        ////            ret.append("\n" + paramRequest.getLocaleString("msgFile"));
        ////            ret.append("\n</td>");
        ////            ret.append("\n<td>");
        ////            ret.append("\n<input type=\"file\"  name=\"repfdoc\">");
        ////            ret.append("\n</td>");
        ////            ret.append("\n</tr>");
        //            ret.append("\n</table>");
        //            ret.append("\n</fieldset>");
        //            ret.append("\n<fieldset>");
        //            ret.append("\n<input type=\"button\"  name=\"s\" value=\"" + paramRequest.getLocaleString("msgBTNSave") + "\" onclick=\"javascript:valida();\" />\r\n");
        //            SWBResourceURL urlb = paramRequest.getRenderUrl();
        //            urlb.setMode(SWBResourceURL.Mode_VIEW);
        //            ret.append("\n<input type=\"button\"  name=\"cancel\" value=\"" + paramRequest.getLocaleString("msgBTNCancel") + "\" onclick=\"window.location='" + urlb.toString() + "';\" />\r\n");
        //            //ret.append("\n</form>");
        //            ret.append("\n</fieldset>");
        //            ret.append("\n</form>");
        //            ret.append("\n</div>");
        //        }
        else if (action.equals("addfolder")) {
            url.setAction("addfolder");
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<form name=\"frmnewfolder\" method=\"post\" action=\"" + url.toString() + "\">");
            ret.append("\n<fieldset>");
            ret.append("\n<legend>");
            ret.append("\n<img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + dir.getDisplayName());
            ret.append("\n</legend>");
            //ret.append("\n<form name=\"frmnewfolder\" method=\"post\" action=\"" + url.toString() + "\">");
            ret.append("\n<input type=\"hidden\" name=\"repNS\" value=\"" + IDREP + "\">");
            ret.append("\n<input type=\"hidden\" name=\"parentUUID\" value=\"" + parentUUID + "\">");
            ret.append("\n<table width=\"100%\"  border=\"0\">");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgFolderName"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<input  type=\"text\" maxlength=\"99\" name=\"repftitle\">");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgDescription"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<textarea rows=\"5\" name=\"repfdescription\" cols=\"20\" onKeyDown=\"textCounter(this.form.repfdescription,255);\" onKeyUp=\"textCounter(this.form.repfdescription,255);\"></textarea>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n</table>");
            ret.append("\n</fieldset>");
            ret.append("\n<fieldset>");
            ret.append("\n<input type=\"button\"  name=\"s\" value=\"" + paramRequest.getLocaleString("msgBTNSave") + "\" onclick=\"javascript:validateAddFolder();\" />\r\n");
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setMode(SWBResourceURL.Mode_VIEW);
            urlb.setParameter("parentUUID", parentUUID);
            ret.append("\n<input type=\"button\"  name=\"cancel\" value=\"" + paramRequest.getLocaleString("msgBTNCancel") + "\" onclick=\"window.location='" + urlb.toString() + "';\" />\r\n");
            //ret.append("\n</form>");
            ret.append("\n</fieldset>");
            ret.append("\n</form>");
            ret.append("\n</div>");
        } else if (action.equals("editfolder")) {


            String ftitle = "";
            String fdescription = "";

            try {
                session = rep.login(credentials, IDREP);
                Node fnode = session.getNodeByUUID(fUUID);
                ftitle = fnode.getProperty("swb:title").getString();
                fdescription = fnode.getProperty("swb:description").getString();

            } catch (Exception e) {
                log.error("Error al cargar la información de la carpeta para editarla", e);
            }



            url.setAction("updatefolder");
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<form name=\"frmnewfolder\" method=\"post\" action=\"" + url.toString() + "\">");
            ret.append("\n<fieldset>");
            ret.append("\n<legend>");
            ret.append("\n<img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + dir.getDisplayName());
            ret.append("\n</legend>");
            //ret.append("\n<form name=\"frmnewfolder\" method=\"post\" action=\"" + url.toString() + "\">");
            ret.append("\n<input type=\"hidden\" name=\"repNS\" value=\"" + IDREP + "\">");
            ret.append("\n<input type=\"hidden\" name=\"parentUUID\" value=\"" + parentUUID + "\">");
            ret.append("\n<input type=\"hidden\" name=\"" + PARAM_UUID + "\" value=\"" + fUUID + "\">");
            ret.append("\n<table width=\"100%\"  border=\"0\">");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgFolderName"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<input  type=\"text\" maxlength=\"99\" name=\"repftitle\" value=\"" + ftitle + "\">");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgDescription"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<textarea rows=\"5\" name=\"repfdescription\" cols=\"20\" onKeyDown=\"textCounter(this.form.repfdescription,255);\" onKeyUp=\"textCounter(this.form.repfdescription,255);\">" + fdescription + "</textarea>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n</table>");
            ret.append("\n</fieldset>");
            ret.append("\n<fieldset>");

            if(luser>2)
            {
                ret.append("\n<input type=\"button\"  name=\"s\" value=\"" + paramRequest.getLocaleString("msgBTNSave") + "\" onclick=\"javascript:validateAddFolder();\" />\r\n");
            }
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setMode(SWBResourceURL.Mode_VIEW);
            urlb.setParameter("parentUUID", parentUUID);
            ret.append("\n<input type=\"button\"  name=\"cancel\" value=\"" + paramRequest.getLocaleString("msgBTNCancel") + "\" onclick=\"window.location='" + urlb.toString() + "';\" />\r\n");

            if (!parentUUID.equals(fUUID) && luser>2) {
                SWBResourceURL urlrem = paramRequest.getActionUrl();
                urlrem.setMode(SWBResourceURL.Mode_VIEW);
                urlrem.setAction("removefolder");
                urlrem.setParameter("repNS", IDREP);
                urlrem.setParameter("parentUUID", parentUUID);
                urlrem.setParameter(PARAM_UUID, fUUID);
                ret.append("\n<input type=\"button\"  name=\"btnremove\" value=\"Eliminar carpeta\" onclick=\"if(confirm('Eliminar carpeta y todos los archivos contenidos. ¿Estás seguro de eliminar la carpeta?')){window.location='" + urlrem.toString() + "'} else { return false;};\" />\r\n");
            }
            ret.append("\n</fieldset>");
            ret.append("\n</form>");
            ret.append("\n</div>");
        } else if (action.equals(FILE_DETAIL)) {

            String folderTitle = dir.getDisplayName();
            String UUID = request.getParameter(PARAM_UUID);

            Node nodo = null;
            Node nf = null;

            if (!resUUID.equals(parentUUID)) {
                try {

                    session = rep.login(credentials, IDREP);
                    nf = session.getNodeByUUID(parentUUID);
                    if (null != nf) {
                        folderTitle = folderTitle + " - " + nf.getProperty("swb:title").getString();
                    }
                } catch (Exception e) {
                    log.error("Error al tratar de cargar el nodo de la carpeta con UUID: " + parentUUID, e);
                } finally {
                    if (session != null) {
                        session.logout();
                    }
                }
            }
            url.setAction("updatefile");
            ret.append("\n<div class=\"swbform\">");
            ret.append("\n<form name=\"frmnewdoc\" method=\"post\"  action=\"" + url.toString() + "\">"); //enctype=\"multipart/form-data\"
            ret.append("\n<fieldset>");
            ret.append("\n<legend>");
            ret.append("\n<img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + folderTitle);
            ret.append("\n</legend>");
            //ret.append("\n<form name=\"frmnewdoc\" method=\"post\" enctype=\"multipart/form-data\" action=\"" + url.toString() + "\">");
            ret.append("\n<input type=\"hidden\" name=\"repNS\" value=\"" + IDREP + "\" />");
            ret.append("\n<input type=\"hidden\" name=\"parentUUID\" value=\"" + parentUUID + "\" />");
            ret.append("\n<input type=\"hidden\" name=\"" + PARAM_UUID + "\" value=\"" + UUID + "\" />");
            ret.append("\n<table width=\"100%\"  border=\"0\" >");
            boolean editFile = Boolean.FALSE;
            try {

               
                session = rep.login(credentials, IDREP);
                nodo = session.getNodeByUUID(UUID);

                if (nodo.getPrimaryNodeType().getName().equals(REP_FILE)) {
                    Node nc = nodo.getNode(JCR_CONTENT);
                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\n" + paramRequest.getLocaleString("msgTitle"));
                    ret.append("\n:</td>");
                    ret.append("\n<td>");

                    String vn = getLastVersionOfcontent(session, "", UUID);

                    User autor = user.getUserRepository().getUser(nc.getProperty(SWBFILEREPUSERID).getString());

                    String fullname = paramRequest.getLocaleString("msgUsrAnonymous");
                    if (autor != null) {
                        fullname = autor.getFullName();
                    }

                    if (luser == 3 || paramRequest.getUser().getId().equals(autor.getId())) {

                        editFile = Boolean.TRUE;

                    }

                    if (editFile) {
                        ret.append("\n<input type=\"text\" maxlength=\"99\" name=\"repftitle\" value=\"" + nodo.getProperty("swb:title").getString() + "\">");
                    } else {
                        ret.append("\n" + nodo.getProperty("swb:title").getString());
                    }

                    ret.append("\n</td>");
                    ret.append("\n</tr>");
                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\n" + paramRequest.getLocaleString("msgDescription"));
                    ret.append("\n:</td>");
                    ret.append("\n<td>");

                    if (editFile) {
                        ret.append("\n<textarea rows=\"5\" name=\"repfdescription\" cols=\"20\" onKeyDown=\"textCounter(this.form.repfdescription,255);\" onKeyUp=\"textCounter(this.form.repfdescription,255);\">" + nodo.getProperty("swb:description").getString() + "</textarea>");

                    } else {
                        ret.append("\n" + nodo.getProperty("swb:description").getString());
                    }

                    ret.append("\n</td>");
                    ret.append("\n</tr>");


                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\n" + paramRequest.getLocaleString("msgLastUpdate"));
                    ret.append("\n:</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + nc.getProperty(JCR_LASTMODIFIED).getString());
                    ret.append("\n</td>");
                    ret.append("\n</tr>");



                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\n" + paramRequest.getLocaleString("msgAutor"));
                    ret.append("\n:</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + fullname);
                    ret.append("\n</td>");
                    ret.append("\n</tr>");

                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\n" + paramRequest.getLocaleString("msgActualVersion"));
                    ret.append("\n:</td>");
                    ret.append("\n<td>");
                    SWBResourceURL urlv = paramRequest.getRenderUrl();

                    urlv.setAction("showfileversions");
                    urlv.setParameter(PARAM_UUID, UUID);
                    urlv.setParameter("parentUUID", parentUUID);
                    urlv.setParameter("repNS", IDREP);

                    ret.append("\n" + vn + " (<a href=\"" + urlv + "\">" + paramRequest.getLocaleString("msgViewVersionHistory") + "</a>)");
                    ret.append("\n</td>");
                    ret.append("\n</tr>");

                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    String file = nodo.getName();
                    String type = getFileName(file);
                    ret.append("\n" + paramRequest.getLocaleString("msgFileName"));
                    ret.append("\n:</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + file);
                    ret.append("\n</td>");
                    ret.append("\n</tr>");

                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\n" + paramRequest.getLocaleString("msgFileType"));
                    ret.append("\n:</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + getFileType(file));
                    ret.append("\n</td>");
                    ret.append("\n</tr>");

                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\n" + paramRequest.getLocaleString("msgFileSize"));
                    ret.append("\n:</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + nc.getProperty(SWBFILEREPFILESIZE).getString() + " bytes");
                    ret.append("\n</td>");
                    ret.append("\n</tr>");

                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    SWBResourceURL usus = paramRequest.getRenderUrl();
                    usus.setParameter(PARAM_UUID, UUID);
                    usus.setParameter("repNS", IDREP);
                    usus.setParameter("parentUUID", parentUUID);


                    SWBResourceURL urlgetfile = paramRequest.getRenderUrl();
                    urlgetfile.setMode(MODE_GETFILE);
                    urlgetfile.setParameter(PARAM_UUID, UUID);
                    urlgetfile.setParameter("repNS", IDREP);
                    urlgetfile.setAction("inline");
                    urlgetfile.setCallMethod(SWBResourceURL.Call_DIRECT);
                    urlgetfile.setWindowState(SWBResourceURL.WinState_MAXIMIZED);
                    urlgetfile.setParameter("parentUUID", parentUUID);
                    ret.append("\n<a href=\"#\" onclick=\"window.location='" + urlgetfile + "';\" title=\"" + paramRequest.getLocaleString("msgViewFile") + "\"><img border=\"0\" src=\"" + path + "preview.gif\" alt=\"\" /></a>");

                    if (luser == 3 || paramRequest.getUser().getId().equals(autor.getId())) {
                        SWBResourceURL udel = paramRequest.getActionUrl();
                        udel.setParameter(PARAM_UUID, UUID);
                        udel.setParameter("parentUUID", parentUUID);
                        udel.setParameter("repNS", IDREP);
                        udel.setAction("removefile");
                        ret.append("\n<a href=\"#\" onclick=\"if(confirm('" + paramRequest.getLocaleString("msgAlertConfirmRemoveFile") + " " + nodo.getName() + "?')){ window.location='" + udel + "';} else { return false; }\">");
                        ret.append("\n<img src=\"" + path + "delete.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgAltDelete") + "\"/>");
                        ret.append("\n</a>");
                    }
//                    if (isUseFolders()) {
//                        SWBResourceURL umove = paramRequest.getRenderUrl();
//                        umove.setParameter("uuid", UUID);
//                        umove.setParameter("parent_wp", parentUUID);
//                        umove.setAction("movefolder");
//                        umove.setMode("selectFolder");
//                        umove.setParameter("repNS", IDREP);
//                        ret.append("<a href=\"" + umove + "\">");
//                        ret.append("\n<img src=\"" + path + "folder.gif\" border=\"0\" alt=\"" + paramRequest.getLocaleString("msgALTMove") + "\"/>");
//                        ret.append("</a>");
//                    }
                    ret.append("\n</td>");
                    ret.append("\n</tr>");

                }
            } catch (Exception e) {
                log.error("Error detalle archivo.", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }

//            ret.append("\n<tr>");
//            ret.append("\n<td width=\"200\">");
//            ret.append("\n" + paramRequest.getLocaleString("msgTitleDocument"));
//            ret.append("\n</td>");
//            ret.append("\n<td>");
//            ret.append("\n<input  type='text' maxlength='99' name='repftitle'>");
//            ret.append("\n</td>");
//            ret.append("\n</tr>");
//            ret.append("\n<tr>");
//            ret.append("\n<td width=\"200\">");
//            ret.append("\n" + paramRequest.getLocaleString("msgDescription"));
//            ret.append("\n</td>");
//            ret.append("\n<td>");
//            ret.append("\n<textarea rows='5' name='repfdescription' cols='20' onKeyDown='textCounter(this.form.repfdescription,255);' onKeyUp='textCounter(this.form.repfdescription,255);'></textarea>");
//            ret.append("\n</td>");
//            ret.append("\n</tr>");
//            ret.append("\n<tr>");
//            ret.append("\n<td width=\"200\">");
//            ret.append("\n" + paramRequest.getLocaleString("msgFile"));
//            ret.append("\n</td>");
//            ret.append("\n<td>");
//            ret.append("\n<input type='file'  name='repfdoc'>");
//            ret.append("\n</td>");
//            ret.append("\n</tr>");
            ret.append("\n</table>");
            ret.append("\n</fieldset>");
            ret.append("\n<fieldset>");
            if(editFile)
            {
                ret.append("\n<input type=\"button\"  name=\"s\" value=\"" + paramRequest.getLocaleString("msgBTNSave") + "\" onclick=\"javascript:validateEditFile();\" />\r\n");
            }
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setMode(SWBResourceURL.Mode_VIEW);
            urlb.setParameter("parentUUID", parentUUID);
            urlb.setAction(FILE_DETAIL);
            urlb.setParameter(PARAM_UUID, UUID);
            ret.append("\n<input type=\"button\"  name=\"cancel\" value=\"" + paramRequest.getLocaleString("msgBTNCancel") + "\" onclick=\"window.location='" + urlb.toString() + "';\" />\r\n");
            //ret.append("\n</form>");
            ret.append("\n</fieldset>");
            ret.append("\n</form>");
            ret.append("\n</div>");

        } else if (action.equals("showfileversions")) {

            String folderTitle = dir.getDisplayName();
            String UUID = request.getParameter(PARAM_UUID);
            String repNS = request.getParameter("repNS");
            Node nf = null;
            if (null != UUID) {
                try {

                    session = rep.login(new SWBCredentials(user), repNS);
                    nf = session.getNodeByUUID(UUID);
                } catch (Exception e) {
                    log.error("Error al tratar de cargar el nodo de la carpeta con UUID: " + parentUUID, e);
                }
            }
            if (nf != null) {
                try {
                    ret.append("\n<div class=\"swbform\">");
                    ret.append("\n<fieldset>");
                    ret.append("\n<legend>");
                    ret.append("\n<img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + folderTitle);
                    ret.append("\n</legend>");
                    ret.append("\n<table width=\"100%\"  border=\"0\">");
                    ret.append("\n<thead>");
                    ret.append("\n<tr>");
                    ret.append("\n<th width=\"20\">");
                    ret.append("\n");
                    ret.append("\n</th>");
                    ret.append("\n<th width=\"20\">");
                    ret.append("\n" + paramRequest.getLocaleString("msgVersion"));
                    ret.append("\n</th>");
                    ret.append("\n<th>");
                    ret.append("\n" + paramRequest.getLocaleString("msgComments"));
                    ret.append("\n</th>");
                    ret.append("\n<th>");
                    ret.append("\n" + paramRequest.getLocaleString("msgDate"));
                    ret.append("\n</th>");
                    ret.append("\n<th>");
                    ret.append("\n" + paramRequest.getLocaleString("msgVersionUser"));
                    ret.append("\n</th>");
                    ret.append("\n<th >");
                    ret.append("\n" + paramRequest.getLocaleString("msgView"));
                    ret.append("\n</th>");
                    ret.append("\n</tr>");
                    ret.append("\n</thead>");

                    String file = nf.getName();
                    String type = getFileName(file);

                    Node ndata = nf.getNode(JCR_CONTENT);


                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss");

                    VersionHistory vhis = ndata.getVersionHistory();
                    VersionIterator it = vhis.getAllVersions();

                    ret.append("\n<tbody>");
                    while (it.hasNext()) {
                        Version version = it.nextVersion();
                        
                        if (!version.getName().equals("jcr:rootVersion")) {

                            String numversion = version.getName();

                            String[] lblvers = vhis.getVersionLabels(version);
                            if(lblvers==null||lblvers.length==0)
                            {
                                numversion = version.getName();
                            } else {
                                numversion = lblvers[0];
                            }

                            Node fnode = version.getNode(JCR_FROZEN_NODE);
                            String comment = fnode.getProperty(SWBFILEREPCOMMENT).getString();
                            
                            String lastModified = sdf.format(version.getProperty("jcr:created").getDate().getTime());
                            User autor = user.getUserRepository().getUser(fnode.getProperty(SWBFILEREPUSERID).getString());

                            String fullname = "Anónimo";
                            if (autor != null) {
                                fullname = autor.getFullName();
                            }

                            ret.append("\n<tr>");
                            ret.append("\n<td width=\"20\">");
                            ret.append("\n<img border=\"0\" src=\"" + path + "" + type + "\" alt=\"" + getFileType(file) + "\" />");
                            ret.append("\n</td>");
                            ret.append("\n<td width=\"20\">");
                            ret.append("\n" + numversion);
                            ret.append("\n</td>");
                            ret.append("\n<td>");
                            ret.append("\n" + comment);
                            ret.append("\n</td>");
                            ret.append("\n<td>");
                            ret.append("\n" + lastModified);
                            ret.append("\n</td>");
                            ret.append("\n<td>");
                            ret.append("\n" + fullname);
                            ret.append("\n</td>");

                            SWBResourceURL urlgetfile = paramRequest.getRenderUrl();
                            urlgetfile.setMode(MODE_GETFILE);
                            urlgetfile.setParameter(PARAM_UUID, UUID);
                            urlgetfile.setParameter("repNS", IDREP);
                            urlgetfile.setParameter("version", numversion);
                            urlgetfile.setAction("inline");
                            urlgetfile.setCallMethod(SWBResourceURL.Call_DIRECT);
                            urlgetfile.setWindowState(SWBResourceURL.WinState_MAXIMIZED);
                            urlgetfile.setParameter("parentUUID", parentUUID);

                            ret.append("\n<td>");
                            ret.append("\n<a href=\"#\" onclick=\"window.location='" + urlgetfile + "';\" title=\"" + paramRequest.getLocaleString("msgViewFile") + "\"><img border=\"0\" src=\"" + path + "preview.gif\" alt=\"" + paramRequest.getLocaleString("msgAltViewVersion") + "\" /></a>");
                            ret.append("\n</td>");
                            ret.append("\n</tr>");
                        }
                    }
                    ret.append("\n</tbody>");
                    ret.append("\n</table>");
                    ret.append("\n</fieldset>");
                } catch (Exception e) {
                    log.error("Error al traer la lista de versiones.doAddForm.showFileVersions", e);
                }
            }
            ret.append("\n<fieldset>");
            //ret.append("\n<input type='button'  name='s' value='" + paramRequest.getLocaleString("msgBTNSave") + "' onclick='javascript:valida();'>\r\n");
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setMode(SWBResourceURL.Mode_VIEW);
            urlb.setParameter("parentUUID", parentUUID);
            urlb.setParameter(PARAM_UUID, UUID);
            ret.append("\n<input type=\"button\"  name=\"cancel\" value=\"" + paramRequest.getLocaleString("msgBTNCancel") + "\" onclick=\"window.location='" + urlb.toString() + "'\" />\r\n");
            //ret.append("\n</form>");
            ret.append("\n</fieldset>");
            ret.append("\n</div>");
        }
        out.println(ret.toString());
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        String strView = "-1";
        String strModify = "-1";
        String strAdmin = "-1";
        String strNotify = "";
        String strCheck = "checked";
        String strEnable = "";

        String id = getResourceBase().getId();
        //System.out.println("id:"+id);

        PrintWriter out = response.getWriter();
        String accion = paramRequest.getAction();
        if (accion == null) {
            accion = "";
        }
        User user = paramRequest.getUser();

        WebPage wpage = paramRequest.getWebPage();
        WebSite wsite = getResourceBase().getWebSite(); //wpage.getWebSite();

        IDREP = docRepNS(request, response, paramRequest);

        boolean flag = false;

        try {
            if (this.getSee() != null) {
                strView = this.getSee();
                flag = true;
            }
            if (this.getModify() != null) {
                strModify = this.getModify();
                flag = true;
            }
            if (this.getAdmin() != null) {
                strAdmin = this.getAdmin();
                flag = true;
            }
        } catch (Exception e) {
            log.error("Error al revisar la configuración inicial del repositorio de documentos.", e);
        }

        if (isUseFolders()) {
            strCheck = "checked";
            flag = true;
        } else {
            strCheck = "";
            flag = true;
        }

        out.println("<div class=\"swbform\">");

        if (accion.equals("edit")) {

            SWBResourceURL urlA = paramRequest.getActionUrl();
            urlA.setAction("admin_update");

            out.println("<form id=\"" + id + "_myform_repfile\"  name=\"" + id + "_myform_repfile\" action=\"" + urlA.toString() + "\" method=\"post\" onsubmit=\"submitForm('" + id + "_myform_repfile');return false;\">");

            out.println("<fieldset>");
            out.println("<legend>");
            out.println(paramRequest.getLocaleString("msgFileRepositoryRes"));
            out.println("</legend>");


            out.println("<table width=\"100%\" border=\"0\" >");

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//            String strTemp = "<option value=\"-1\">" + paramRequest.getLocaleString("msgNoRolesAvailable") + "</option>";
//            Iterator<Role> iRoles = wsite.getUserRepository().listRoles(); //DBRole.getInstance().getRoles(topicmap.getDbdata().getRepository());
//            StringBuffer strRules = new StringBuffer("");
//            strRules.append("\n<option value=\"0\">" + paramRequest.getLocaleString("msgSelNone") + "</option>");
//            strRules.append("\n<optgroup label=\"Roles\">");
//            while (iRoles.hasNext()) {
//                Role oRole = iRoles.next();
//                strRules.append("\n<option value=\"" + oRole.getURI() + "\">" + oRole.getDisplayTitle(user.getLanguage()) + "</option>");
//            }
//            strRules.append("\n</optgroup>");
//
//            strRules.append("\n<optgroup label=\"User Groups\">");
//            Iterator<UserGroup> iugroups = wsite.getUserRepository().listUserGroups();
//            while (iugroups.hasNext()) {
//                UserGroup oUG = iugroups.next();
//                strRules.append("\n<option value=\"" + oUG.getURI() + "\">" + oUG.getDisplayTitle(user.getLanguage()) + "</option>");
//            }
//            strRules.append("\n</optgroup>");
//            if (strRules.toString().length() > 0) {
//                strTemp = strRules.toString();
//            }

            out.println("<tr><td colspan=\"2\"><B>" + paramRequest.getLocaleString("msgRolesDefinitionLevel") + "</b></td></tr>");
            out.println("<tr><td align=\"right\" width=150>" + paramRequest.getLocaleString("msgView") + ":</td>");
            out.println("<td><select name=\"ver\">" + getSelectOptions("ver", wsite, paramRequest) + "</select></td></tr>");
            out.println("<tr><td align=\"right\" width=150>" + paramRequest.getLocaleString("msgModify") + ":</td>");
            out.println("<td><select name=\"modificar\">" + getSelectOptions("modificar", wsite, paramRequest) + "</select></td></tr>");
            out.println("<tr><td align=\"right\"  width=150>" + paramRequest.getLocaleString("msgAdministrate") + ":</td>");
            out.println("<td><select name=\"administrar\">" + getSelectOptions("administrar", wsite, paramRequest) + "</select></td></tr>");
            out.println("<tr><td align=\"right\"  width=150>" + paramRequest.getLocaleString("msgShowSubDirs") + ":</td>");
            out.println("<td><input type=\"checkbox\" name=\"showdirectory\" value=\"1\" " + strCheck + " " + strEnable + ">");
            out.println("</td></tr>");

            /*

            out.println("<tr><td align=\"right\" width=150>" + paramRequest.getLocaleString("msgCreateNotificationMessage") + ":</td>");

            strNotify = "";
            if (getMsgcrated() != null) {
            strNotify = getMsgcrated();
            flag = true;
            } else {
            strNotify = null;
            StringBuffer strMsg = new StringBuffer(paramRequest.getLocaleString("msgCreateNotificationMessage") + ":");
            strMsg.append("\n" + paramRequest.getLocaleString("msgTheDoc") + " {getDocTitle} " + paramRequest.getLocaleString("msgWasCreated") + ".\n");
            strMsg.append("\n   " + paramRequest.getLocaleString("msgFileName") + ":       {getFileName}");
            strMsg.append("\n   " + paramRequest.getLocaleString("msgCreationDate") + ":   {getLastUpdate}");
            strMsg.append("\n   " + paramRequest.getLocaleString("msgReviewFile") + ":   {getDocLink}");
            strMsg.append("\n   " + paramRequest.getLocaleString("msgCreationUser") + ":   {getUserName} <{getUserEmail}>");
            strNotify = strMsg.toString();
            }

            out.println("<td><textarea name=\"notificationcreate\" cols=60 rows=10>" + strNotify + "</textarea></td></tr>");
            out.println("<tr><td align=\"right\" width=\"150\">" + paramRequest.getLocaleString("msgUpdNotificationMessage") + ":</td>");

            strNotify = "";
            if (getMsgupdated() != null) {
            strNotify = getMsgupdated();
            flag = true;
            } else {
            strNotify = null;
            StringBuffer strMsg = new StringBuffer(paramRequest.getLocaleString("msgUpdNotificationMessage") + ":");
            strMsg.append("\n" + paramRequest.getLocaleString("msgTheDoc") + " {getDocTitle} " + paramRequest.getLocaleString("msgWasUpd") + ".\n");
            strMsg.append("\n   " + paramRequest.getLocaleString("msgFileName") + ":       {getFileName}");
            strMsg.append("\n   " + paramRequest.getLocaleString("msgReviewFile") + ":   {getDocLink}");
            strMsg.append("\n   " + paramRequest.getLocaleString("msgLastUpd") + ":     {getLastUpdate}");
            strMsg.append("\n   " + paramRequest.getLocaleString("msgVersionUser") + ":    {getUserName} <{getUserEmail}>");
            strMsg.append("\n   " + paramRequest.getLocaleString("msgVersionComments") + ":");
            strMsg.append("\n        {getComments}");
            strNotify = strMsg.toString();
            }

            out.println("<td><textarea name=\"notificationupdate\" cols=60 rows=10>" + strNotify + "</textarea></td></tr>");
            out.println("<tr><td align=\"right\" width=150>" + paramRequest.getLocaleString("msgRemoveNotificationMessage") + ":</td>");

            strNotify = "";
            if (getMsgdeleted() != null) {
            strNotify = getMsgdeleted();
            flag = true;
            } else {
            strNotify = null;
            StringBuffer strMsg = new StringBuffer(paramRequest.getLocaleString("msgRemoveNotificationMessage") + ":");
            strMsg.append("\n" + paramRequest.getLocaleString("msgTheDoc") + " {getDocTitle} " + paramRequest.getLocaleString("msgWasRemoved") + ".\n");
            strMsg.append("\n   " + paramRequest.getLocaleString("msgDirName") + ":  {getDirectoryName}");
            strMsg.append("\n   " + paramRequest.getLocaleString("msgUserName") + ":       {getUserName} <{getUserEmail}>");
            strNotify = strMsg.toString();
            }

            out.println("<td><textarea name=\"notificationremove\" cols=\"60\" rows=\"10\">" + strNotify + "</textarea></td></tr>");
             *
             */
            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" id=\"" + id + "btn\" name=\"btn\" >" + paramRequest.getLocaleString("msgBTNAccept"));
            out.println("</button>");
//            out.println("<script type=\"dojo/method\" event=\"onClick\" >");
//            out.println(" submitForm('"+id+"_myform_repfile'); ");
//            out.println(" return false; ");
//            out.println("</script>");



            SWBResourceURL urlold = paramRequest.getRenderUrl();
            urlold.setAction("showold");

            out.println("<button dojoType=\"dijit.form.Button\" type=\"button\" name=\"btnold\" onclick=\"submitUrl('" + urlold + "',this.domNode); return false;\" >" + paramRequest.getLocaleString("msgBTNViewOldFiles") + "</button>");
            out.println("</fieldset>");
            out.println("<fieldset>");
//            out.println("<br>* " + paramRequest.getLocaleString("msgNotificationKeys") + ":");
//            out.println("<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getUserName}   " + paramRequest.getLocaleString("msgCompleteUserName"));
//            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getUserEmail}  " + paramRequest.getLocaleString("msgUserEmail"));
//            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDirectoryName} " + paramRequest.getLocaleString("msgDirName"));
//            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getFileName}   " + paramRequest.getLocaleString("msgFileName"));
//            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocTitle}   " + paramRequest.getLocaleString("msgDocTitle"));
//            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocDesc}    " + paramRequest.getLocaleString("msgDocDescription"));
//            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocVersion} " + paramRequest.getLocaleString("msgDocVersion"));
//            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocLink} " + paramRequest.getLocaleString("msgDocLink"));
//            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getLastUpdate} " + paramRequest.getLocaleString("msgLastDateModification"));
//            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getComments}   " + paramRequest.getLocaleString("msgModificationComment"));
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            out.println("<br> * " + paramRequest.getLocaleString("msgNote") + ": " + paramRequest.getLocaleString("msgRolesDependent"));

            out.println("</fieldset>");
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
            }
//            out.println("<script type=\"text/javascript\">");
//            out.println("      document." + id + "_myform_repfile.ver.value='" + strView + "';");
//            out.println("      document." + id + "_myform_repfile.modificar.value='" + strModify + "';");
//            out.println("      document." + id + "_myform_repfile.administrar.value='" + strAdmin + "';");
//            out.println("</script>");

        } else if (accion.equals("showold")) {

            SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss", new Locale(paramRequest.getUser().getLanguage()));
            String ns = docRepNS(request, response, paramRequest);
            Node nodeRep = null;
            Node nf = null;
            Node nfi = null;
            Session session = null;
            try {
                session = rep.login(new SWBCredentials(user), ns);
                String swbresUUID = getResourceBase().getAttribute("repNode_uuid", "");

                out.println("<div class=\"swform\">");
                out.println("<fieldset>");
                out.println("<legend>");
                out.println(paramRequest.getLocaleString("msgFileRemovePermanently") + ":</td>");
                out.println("</legend>");
                SWBResourceURL urlShowOld = paramRequest.getActionUrl();
                urlShowOld.setMode(paramRequest.Mode_ADMIN);
                urlShowOld.setAction("ShowOld");

                out.println("<form action=\"" + urlShowOld.toString() + "\" name=\"frmdelfile\" method=\"post\">");
                out.println("<table width=\"100%\" border=\"0\">");
                out.println("<tr><th>" + paramRequest.getLocaleString("msgTHFolder") + "</th><th>" + paramRequest.getLocaleString("msgTHFileName") + "</th><th>" + paramRequest.getLocaleString("msgTHCreated") + "</th><th>" + paramRequest.getLocaleString("msgTHEmialUser") + "</th><th>" + paramRequest.getLocaleString("msgTHAction") + "</th></tr>");

                //System.out.println("Resource UUID: "+swbresUUID);

                if (!swbresUUID.equals("")) {
                    nodeRep = session.getNodeByUUID(swbresUUID);
                }

                //System.out.println("Nodo repositorio: "+nodeRep.getNodes().nextNode().getProperty("swb:title").getString());

                if (nodeRep != null) {
                    String rowColor = "";
                    boolean cambiaColor = true;
                    NodeIterator itn = nodeRep.getNodes().nextNode().getNodes();
                    while (itn.hasNext()) {
                        nf = itn.nextNode();
                        if (nf.getPrimaryNodeType().getName().equals(REP_FOLDER)) {
                            rowColor = "bgcolor=\"#EFEDEC\"";
                            if (!cambiaColor) {
                                rowColor = "";
                            }
                            cambiaColor = !(cambiaColor);
                            boolean fdel = nf.getProperty(SWB_FILEREP_DELETED).getBoolean();
                            if (fdel) {
                                //TODO: Mostrar información del folder eliminado
                                out.println("<tr " + rowColor + ">");
                                out.println("<td>" + nf.getName() + "</td>");
                                out.println("<td>" + "folder" + "</td>");
                                out.println("<td>" + df.format(nf.getProperty("jcr:created").getDate().getTime()) + "</td>");
                                User usrcreator = paramRequest.getUser().getUserRepository().getUser(getResourceBase().getCreator().getId());
                                out.println("<td><a href=\"mailto:" + usrcreator.getEmail() + "\">" + usrcreator.getEmail() + "</a></td>");
                                out.println("<td>");
                                SWBResourceURL urlrecover = paramRequest.getActionUrl();
                                urlrecover.setParameter(PARAM_UUID, nf.getUUID());
                                urlrecover.setParameter("repNS", ns);
                                urlrecover.setAction("admin_recover_folder");
                                SWBResourceURL urldel = paramRequest.getActionUrl();
                                urldel.setParameter(PARAM_UUID, nf.getUUID());
                                urldel.setParameter("repNS", ns);
                                urldel.setAction("admin_delete");
                                out.println("<a href=\"#\" onclick=\"submitUrl('" + urlrecover + "',this); return false;\">" + "<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/recover.gif\" border=\"0\" title=\"" + paramRequest.getLocaleString("msgAltRestore") + "\" >" + "</a>");
                                out.println("&nbsp;<a href=\"#\" onclick=\"submitUrl('" + urldel + "',this); return false;\">" + "<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/trash_vacio.gif\" border=\"0\" title=\"" + paramRequest.getLocaleString("msgAltEliminate") + "\" ></a>");
                                out.println("</td>");
                                out.println("</tr>");
                            }
                            // Listado de archivos asociados al folder
                            NodeIterator itf = nf.getNodes();
                            while (itf.hasNext()) {
                                nfi = itf.nextNode();
                                if (nfi.getPrimaryNodeType().getName().equals(REP_FILE)) {
                                    boolean fidel = false;
                                    fidel = nfi.getProperty(SWB_FILEREP_DELETED).getBoolean();
                                    if (fidel) {
                                        rowColor = "bgcolor=\"#EFEDEC\"";
                                        if (!cambiaColor) {
                                            rowColor = "";
                                        }
                                        cambiaColor = !(cambiaColor);
                                        out.println("<tr " + rowColor + ">");

                                        out.println("<td>" + nf.getName() + "</td>"); //nombre del folder
                                        out.println("<td>" + nfi.getName() + "</td>"); //nombre del evento
                                        Node ndata = nfi.getNode(JCR_CONTENT);
                                        String s_usrID = ndata.getProperty(SWBFILEREPUSERID).getString();
                                        //System.out.println("USRID:"+s_usrID);
                                        out.println("<td>" + df.format(ndata.getProperty(JCR_LASTMODIFIED).getDate().getTime()) + "</td>");
                                        //User usrcreator = paramRequest.getUser().getUserRepository().getUser(s_usrID);
                                        User usrcreator = paramRequest.getWebPage().getWebSite().getUserRepository().getUser(s_usrID);
                                        //System.out.println("UsrCreator: "+usrcreator+", UsrWS:"+usrws);
                                        out.println("<td><a href=\"mailto:" + usrcreator.getEmail() + "\">" + usrcreator.getEmail() + "</a></td>");
                                        out.println("<td>");
                                        SWBResourceURL urlrecover = paramRequest.getActionUrl();
                                        urlrecover.setParameter(PARAM_UUID, nfi.getUUID());
                                        urlrecover.setParameter("repNS", ns);
                                        urlrecover.setAction("admin_recover");
                                        SWBResourceURL urldel = paramRequest.getActionUrl();
                                        urldel.setParameter(PARAM_UUID, nfi.getUUID());
                                        urldel.setParameter("repNS", ns);
                                        urldel.setAction("admin_delete");
                                        out.println("<a href=\"#\" onclick=\"submitUrl('" + urlrecover + "',this); return false;\">" + "<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/recover.gif\" border=\"0\" title=\"" + paramRequest.getLocaleString("msgAltRestore") + "\" >" + "</a>");
                                        out.println("&nbsp;<a href=\"#\" onclick=\"submitUrl('" + urldel + "',this); return false;\">" + "<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/trash_vacio.gif\" border=\"0\" title=\"" + paramRequest.getLocaleString("msgAltEliminate") + "\" >" + "</a>");
                                        out.println("</td>");
                                        out.println("</tr>");
                                    }
                                }
                            }
                        }
                    }
                }
                out.println("</table>");
                out.println("</form>");
                out.println("</fieldset>");
                out.println("<fieldset>");
                SWBResourceURL ub = paramRequest.getRenderUrl();
                ub.setAction("edit");
                out.println("<button dojoType=\"dijit.form.Button\" type=\"button\" name=\"btn_back\" onclick=\"submitUrl('" + ub + "',this.domNode); return false;\">" + paramRequest.getLocaleString("btnBack") + "</button>");
                out.println("</fieldset>");
            } catch (Exception e) {
                log.error("Error al traer los folders/archivos eliminados. doAdmin.showold", e);
            }
        }
        out.println("</div>");
    }

    public String getSelectOptions(String type, WebSite wsite, SWBParamRequest paramRequest) {
        String strTemp = "";
        try {

            User user = paramRequest.getUser();

            String selectedItem = "";
            if (type.equals("ver")) {
                selectedItem = this.getSee();
            } else if (type.equals("modificar")) {
                selectedItem = this.getModify();

            } else if (type.equals("administrar")) {
                selectedItem = this.getAdmin();
            }

            strTemp = "<option value=\"-1\">" + paramRequest.getLocaleString("msgNoRolesAvailable") + "</option>";


            Iterator<Role> iRoles = wsite.getUserRepository().listRoles(); //DBRole.getInstance().getRoles(topicmap.getDbdata().getRepository());
            StringBuffer strRules = new StringBuffer("");
            strRules.append("\n<option value=\"0\">" + paramRequest.getLocaleString("msgSelNone") + "</option>");
            strRules.append("\n<optgroup label=\"Roles\">");

            String slang = user.getLanguage();
            String stitle = "";
            while (iRoles.hasNext()) {
                Role oRole = iRoles.next();
                if(null!=oRole&&selectedItem!=null)
                {
                    stitle = "";
                    if(oRole.getDisplayTitle(slang)!=null) stitle = oRole.getDisplayTitle(slang);
                    else if(oRole.getTitle(slang)!=null) stitle = oRole.getTitle(slang);
                    else if(oRole.getTitle()!=null) stitle = oRole.getTitle();
                    else stitle = oRole.getId();

                    strRules.append("\n<option value=\"" + oRole.getURI() + "\" " + (selectedItem.equals(oRole.getURI()) ? "selected" : "") + ">" + stitle + "</option>");
                }
            }
            strRules.append("\n</optgroup>");

            strRules.append("\n<optgroup label=\"User Groups\">");
            Iterator<UserGroup> iugroups = wsite.getUserRepository().listUserGroups();
            while (iugroups.hasNext()) {
                UserGroup oUG = iugroups.next();
                strRules.append("\n<option value=\"" + oUG.getURI() + "\" " + (selectedItem.equals(oUG.getURI()) ? "selected" : "") + ">" + oUG.getDisplayTitle(user.getLanguage()) + "</option>");
            }
            strRules.append("\n</optgroup>");
            if (strRules.toString().length() > 0) {
                strTemp = strRules.toString();
            }

        } catch (Exception e) { log.error("Error al cargar los roles del repositorio de usuarios.",e);
        }



        return strTemp;
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        String id = request.getParameter("suri");
        String repNS = request.getParameter("repNS");
        if (null == action) {
            action = "";
        }

        //System.out.println("Action:" + action);

        User user = response.getUser();
        Session session = null;
        Credentials credentials = new SWBCredentials(user);
        if ("admin_update".equals(action)) {

            //System.out.println("Actualizando....");

            String viewrole = request.getParameter("ver");
            String modifyrole = request.getParameter("modificar");
            String adminrole = request.getParameter("administrar");
            String notifynew = request.getParameter("notificationcreate");
            String nofiryupdate = request.getParameter("notificationupdate");
            String notifyremove = request.getParameter("notificationremove");
            boolean usefolders = request.getParameter("showdirectory") != null && request.getParameter("showdirectory").equals("1") ? true : false;

            this.setSee(viewrole);
            this.setModify(modifyrole);
            this.setAdmin(adminrole);
            this.setMsgcrated(notifynew);
            this.setMsgupdated(nofiryupdate);
            this.setMsgdeleted(notifyremove);
            this.setUseFolders(usefolders);

            try {
                getResourceBase().updateAttributesToDB();
            } catch (Exception e) {
            }

            response.setMode(SWBActionResponse.Mode_ADMIN);
            response.setAction("edit");

        }  else if ("admin_recover_folder".equals(action)) //recuperación de carpeta
        {

            String UUID = request.getParameter(PARAM_UUID);
            try {
                session = rep.login(credentials, repNS);
                Node nodofolder = session.getNodeByUUID(UUID);

                // recuperando folder eliminado

                if(nodofolder!=null)
                {
                    boolean isDeleted = Boolean.FALSE;
                    if (nodofolder.getProperty(SWB_FILEREP_DELETED) != null) {
                        isDeleted = nodofolder.getProperty(SWB_FILEREP_DELETED).getBoolean();
                    }
                    if(isDeleted)
                    {
                        nodofolder.setProperty(SWB_FILEREP_DELETED, Boolean.FALSE);
                        nodofolder.save();
                    }
                }

            } catch (Exception e) {
                log.error("Error al hacer el Folder Recover processAction.admin_recover_folder", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }
            response.setMode(SWBActionResponse.Mode_ADMIN);
            response.setAction("showold");
        } else if ("admin_recover".equals(action)) //recuperación de archivo eliminado
        {

            String UUID = request.getParameter(PARAM_UUID);
            try {
                session = rep.login(credentials, repNS);
                Node fnode = session.getNodeByUUID(UUID);

                // recuperando folder eliminado
                Node nodofolder = fnode.getParent();

                if(nodofolder!=null)
                {
                    boolean isDeleted = Boolean.FALSE;
                    if (nodofolder.getProperty(SWB_FILEREP_DELETED) != null) {
                        isDeleted = nodofolder.getProperty(SWB_FILEREP_DELETED).getBoolean();
                    }
                    if(isDeleted)
                    {
                        nodofolder.setProperty(SWB_FILEREP_DELETED, Boolean.FALSE);
                        nodofolder.save();
                    }
                }

                if (!nodofolder.isCheckedOut()) {
                    fnode.checkout();
                }
                fnode.checkout();
                //fnode.save();

                fnode.setProperty(SWB_FILEREP_DELETED, Boolean.FALSE);
                Node contenido = fnode.getNode(JCR_CONTENT);

                if(!contenido.isCheckedOut())
                {
                    contenido.checkout();
                    contenido.save();
                }

                contenido.restore(contenido.getBaseVersion(), false);
                contenido.save();
                fnode.save();
                Version ver = contenido.checkin();
                VersionHistory history = ver.getContainingHistory();
                ver.remove();
                history.save();
            } catch (Exception e) {
                log.error("Error al hacer el File Recover processAction.admin_recover", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }
            response.setMode(SWBActionResponse.Mode_ADMIN);
            response.setAction("showold");
        } else if ("admin_delete".equals(action)) //eliminación completa del archivo previa/ marcado como eliminado
        {
            String UUID = request.getParameter(PARAM_UUID);
            try {
                session = rep.login(credentials, repNS);
                Node fnode = session.getNodeByUUID(UUID);
                fnode.remove();
                session.save();
            } catch (Exception e) {
                log.error("Error al eliminar el archivo processAction.admin_delete", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }
            response.setMode(SWBActionResponse.Mode_ADMIN);
            response.setAction("showold");
        } else if ("addfile".equals(action)) //agregar nuevo archivo al repositorio
        {
            String filename = null;

            String parentUUID = null;
            try {

                FileUpload fup = new FileUpload();
                fup.getFiles(request, null);

                repNS = fup.getValue("repNS");

                filename = fup.getFileName("repfdoc");
                String title = fup.getValue("repftitle");
                String description = fup.getValue("repfdescription");

                parentUUID = fup.getValue("parentUUID");
                if (parentUUID == null) {
                    parentUUID = resUUID;
                }
                if (filename.lastIndexOf('/') != -1) {
                    int pos = filename.lastIndexOf('/');
                    filename = filename.substring(pos + 1);
                }
                if (filename.lastIndexOf('\\') != -1) {
                    int pos = filename.lastIndexOf('\\');
                    filename = filename.substring(pos + 1);
                }

                byte[] bcont = fup.getFileData("repfdoc");

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());

                session = rep.login(credentials, repNS);
                Node nodePage = session.getNodeByUUID(parentUUID);//nodeRep.getNode(response.getWebPage().getId());//nombre id_pagina

                //Agregando nodo de tipo file
                Node nodeFile = nodePage.addNode(filename, REP_FILE);
                nodeFile.setProperty("swb:title", title);
                nodeFile.setProperty("swb:description", description);
                nodeFile.setProperty(SWB_FILEREP_DELETED, Boolean.FALSE);

                Node nodeRes = nodeFile.addNode(JCR_CONTENT, "swbfilerep:RepositoryResource");
                nodeRes.setProperty("jcr:data", new ByteArrayInputStream(bcont));
                nodeRes.addMixin("mix:versionable");
                nodeRes.setProperty("jcr:encoding", "");
                nodeRes.setProperty(JCR_LASTMODIFIED, cal);
                nodeRes.setProperty("jcr:mimeType", fup.getContentType());
                nodeRes.setProperty(SWBFILEREPCOMMENT, "Nuevo");
                nodeRes.setProperty(SWBFILEREPUSERID, user.getId());
                nodeRes.setProperty(SWBFILEREPFILESIZE, bcont.length);
                nodePage.save();

                Version version = nodeRes.checkin();
                nodeFile.checkin();

                log.debug("Version created with number " + version.getName());

                session.save();
            } catch (Exception e) {
                log.error("ERROR al agregar un archivo al repositorio de documentos.", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }
            response.setRenderParameter("parentUUID", parentUUID);
            response.setMode(SWBActionResponse.Mode_VIEW);
        } else if ("addfolder".equals(action)) //agregar nuevo folder al repositorio
        {
            String strTitle = request.getParameter("repftitle");
            String strDescription = request.getParameter("repfdescription");
            String pUUID = request.getParameter("parentUUID");
            if (pUUID == null) {
                pUUID = resUUID;
            }
            try {
                session = rep.login(credentials, repNS);
                Node nodePage = session.getNodeByUUID(pUUID); //nodeRep.getNode(response.getWebPage().getId());//nombre id_pagina
                Node nodeFolder = nodePage.addNode(REP_FOLDER, REP_FOLDER);
                nodeFolder.setProperty("swb:title", strTitle);
                nodeFolder.setProperty("swb:description", strDescription);
                nodeFolder.setProperty(SWBFILEREPUSERID, user.getId());
                nodeFolder.setProperty(SWB_FILEREP_DELETED, Boolean.FALSE);
                nodePage.save();
            } catch (Exception e) {
                log.error("Error al crear folder processAction.addFolder", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }
            response.setRenderParameter("parentUUID", pUUID);
            response.setMode(SWBActionResponse.Mode_VIEW);
        } else if ("removefile".equals(action)) // marcar archivo como eliminado lógico
        {

            String pUUID = request.getParameter("parentUUID");
            if (pUUID == null) {
                pUUID = resUUID;
            }
            String UUID = request.getParameter(PARAM_UUID);
            try {
                session = rep.login(credentials, repNS);
                Node fnode = session.getNodeByUUID(UUID);
                Node fc = fnode.getNode(JCR_CONTENT);
                if (!fnode.isCheckedOut()) {
                    fc.checkout();
                }
                fnode.checkout();
                fnode.setProperty(SWB_FILEREP_DELETED, Boolean.TRUE);
                fnode.save();
            } catch (Exception e) {
                log.error("Error al eliminar el archivo processAction.removeFile", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }
            if(null!=pUUID) response.setRenderParameter("parentUUID", pUUID);
            response.setRenderParameter(PARAM_UUID, UUID);
            response.setMode(SWBActionResponse.Mode_VIEW);
        } else if ("removefolder".equals(action)) // marcar folder como eliminado
        {
            String pUUID = request.getParameter("´parentUUID");
            String fUUID = request.getParameter(PARAM_UUID);
            try {
                session = rep.login(credentials, repNS);
                Node fnode = session.getNodeByUUID(fUUID);
                fnode.setProperty(SWB_FILEREP_DELETED, Boolean.TRUE);
                NodeIterator nit = fnode.getNodes();
                while (nit.hasNext()) {
                    Node nodo = nit.nextNode();
                    Node fc = nodo.getNode(JCR_CONTENT);
                    if (!nodo.isCheckedOut()) {
                        fc.checkout();
                    }
                    nodo.checkout();
                    nodo.setProperty(SWB_FILEREP_DELETED, Boolean.TRUE);
                    nodo.save();
                }
                fnode.save();
            } catch (Exception e) {
                log.error("Error al eliminar el folder processAction.removeFolder", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }
            response.setRenderParameter("parentUUID", pUUID);
            response.setRenderParameter(PARAM_UUID, fUUID);
            response.setMode(SWBActionResponse.Mode_VIEW);
        } else if ("updatefile".equals(action)) {

            boolean isChecked = false;
            String strTitle = request.getParameter("repftitle");
            String strDescription = request.getParameter("repfdescription");
            String pUUID = request.getParameter("parentUUID");
            String UUID = request.getParameter(PARAM_UUID);
            if (pUUID == null) {
                pUUID = resUUID;
            }
            try {

                session = rep.login(credentials, repNS);
                Node fnode = session.getNodeByUUID(UUID);
                Node contenido = fnode.getNode(JCR_CONTENT);

                if (!contenido.isCheckedOut()) {
                    contenido.checkout();
                    contenido.setProperty(SWBFILEREPUSERID, user.getId());
                    contenido.save();
                    fnode.checkout();
                    fnode.save();
                    isChecked = true;
                }

                fnode.setProperty("swb:title", strTitle);
                fnode.setProperty("swb:description", strDescription);

                if (isChecked) {
                    contenido.restore(contenido.getBaseVersion(), false);
                    contenido.save();
                    Version ver = contenido.checkin();
                    VersionHistory history = ver.getContainingHistory();
                    ver.remove();
                    history.save();
                    //fnode.checkin();
                    fnode.save();
                }



            } catch (Exception e) {
                log.error("Error al crear folder processAction.updateFile", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }
            response.setRenderParameter("parentUUID", pUUID);
            response.setRenderParameter(PARAM_UUID, UUID);
            response.setMode(SWBActionResponse.Mode_VIEW);



        } else if ("updatefolder".equals(action)) {

            String strTitle = request.getParameter("repftitle");
            String strDescription = request.getParameter("repfdescription");
            String pUUID = request.getParameter("parentUUID");
            String UUID = request.getParameter(PARAM_UUID);
            if (pUUID == null) {
                pUUID = resUUID;
            }
            try {

                session = rep.login(credentials, repNS);
                Node fnode = session.getNodeByUUID(UUID);

                fnode.setProperty("swb:title", strTitle);
                fnode.setProperty("swb:description", strDescription);
                fnode.save();

            } catch (Exception e) {
                log.error("Error al crear folder processAction.updateFolder", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }
            response.setRenderParameter("parentUUID", pUUID);
            response.setMode(SWBActionResponse.Mode_VIEW);

        } else if ("check".equals(action)) {
            String faction = "";
            if (request.getParameter("faction") != null && request.getParameter("faction").trim().length() > 0) {
                faction = request.getParameter("faction");
            }
            if ("in".equals(faction)) {
                String filename = null;

                //String lblVersion = null;
                String parentUUID = null;
                try {

                    FileUpload fup = new FileUpload();
                    fup.getFiles(request, null);

                    repNS = fup.getValue("repNS");
                    //lblVersion = fup.getValue("version_number");

                    filename = fup.getFileName("fileversion");
                    String comentario = fup.getValue("commentversion");

                    if (comentario.trim().length() == 0) {
                        comentario = "Nueva versión";
                    }
                    parentUUID = fup.getValue(PARAM_UUID);
                    if (parentUUID == null) {
                        parentUUID = resUUID;
                    }
                    if (filename.lastIndexOf('/') != -1) {
                        int pos = filename.lastIndexOf('/');
                        filename = filename.substring(pos + 1);
                    }
                    if (filename.lastIndexOf('\\') != -1) {
                        int pos = filename.lastIndexOf('\\');
                        filename = filename.substring(pos + 1);
                    }

                    byte[] bcont = fup.getFileData("fileversion");

                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(System.currentTimeMillis());

                    session = rep.login(credentials, repNS);
                    Node nodeFile = session.getNodeByUUID(parentUUID);//nodeRep.getNode(response.getWebPage().getId());//nombre id_pagina

                    //Agregando nodo de tipo nueva version JCR_CONTENT

                    Node nodeRes = nodeFile.getNode(JCR_CONTENT);
                    nodeRes.setProperty("jcr:data", new ByteArrayInputStream(bcont));
                    nodeRes.setProperty("jcr:encoding", "");
                    nodeRes.setProperty(JCR_LASTMODIFIED, cal);
                    nodeRes.setProperty("jcr:mimeType", fup.getContentType());
                    nodeRes.setProperty(SWBFILEREPCOMMENT, comentario);
                    nodeRes.setProperty(SWBFILEREPUSERID, user.getId());
                    nodeRes.setProperty(SWBFILEREPFILESIZE, bcont.length);

//                    VersionHistory vhis = nodeRes.getVersionHistory();
//                    vhis.addVersionLabel(nodeRes.getName(), lblVersion, true);
//                    vhis.save();

                    nodeFile.save();
                    nodeRes.save();

                    Version version = nodeRes.checkin();


                    log.debug("Version created with number " + version.getName());

                } catch (Exception e) {
                    log.error("ERROR al agregar un archivo al repositorio de documentos.", e);
                } finally {
                    if (session != null) {
                        session.logout();
                    }
                }
                if (request.getParameter("parentUUID") != null) {
                    response.setRenderParameter("parentUUID", request.getParameter("parentUUID"));
                }
                response.setMode(SWBActionResponse.Mode_VIEW);

            } else if ("undo".equals(faction)) {

                String UUID = request.getParameter(PARAM_UUID);
                try {
                    session = rep.login(credentials, repNS);
                    Node fnode = session.getNodeByUUID(UUID);
                    Node contenido = fnode.getNode(JCR_CONTENT);
                    contenido.restore(contenido.getBaseVersion(), false);
                    contenido.save();
                    Version ver = contenido.checkin();
                    VersionHistory history = ver.getContainingHistory();
                    ver.remove();
                   history.save();

                } catch (Exception e) {
                    log.error("Error al hacer el UndoCheckOut processAction.undo", e);
                } finally {
                    if (session != null) {
                        session.logout();
                    }
                }

                if (request.getParameter("parentUUID") != null) {
                    response.setRenderParameter("parentUUID", request.getParameter("parentUUID"));
                }

            } else if ("out".equals(faction)) {
                String UUID = request.getParameter(PARAM_UUID);
                try {
                    session = rep.login(credentials, repNS);
                    Node fnode = session.getNodeByUUID(UUID);
                    Node contenido = fnode.getNode(JCR_CONTENT);
                    contenido.checkout();
                    contenido.setProperty(SWBFILEREPUSERID, user.getId());
                    contenido.save();
                } catch (Exception e) {
                    log.error("Error al hacer check out del archivo. processAction.checkOut()", e);
                }
                response.setRenderParameter("fuuid", UUID);
                response.setRenderParameter("repNS", repNS);
                response.setRenderParameter("getfile", "true");
                response.setMode(SWBActionResponse.Mode_VIEW);
                if (request.getParameter("parentUUID") != null) {
                    response.setRenderParameter("parentUUID", request.getParameter("parentUUID"));
                }

            }
        }
        if (null != id) {
            response.setRenderParameter("suri", id);
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String uuid = request.getParameter(PARAM_UUID);
        String ns = request.getParameter("repNS");
        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";
        String action = paramRequest.getAction();
        Credentials credentials = new SWBCredentials(user);
        Session session = null;
        boolean checked = false;
        if ("checkin".equals(action)) {
            out.println("<script>include_dom('" + SWBPlatform.getContextPath() + "/swbadmin/js/repositoryfile.js');</script>");

            try {

                session = rep.login(credentials, ns);
                Node nodo = session.getNodeByUUID(uuid);
                String filename = nodo.getName();
                String vernum = getLastVersionOfcontent(session, ns, uuid);

                Node nc = nodo.getNode(JCR_CONTENT);

                VersionHistory verhis = nodo.getVersionHistory();
                
//                Version version = null;
//                if(verhis.hasVersionLabel(vernum))
//                {
//                    version = verhis.getVersionByLabel(vernum);
//                }

//                if(version==null) version = nodo.getVersionHistory().getVersion(vernum);

                float fvnum = Float.parseFloat(vernum);
                float fvnum_short = fvnum+0.10F;
                float fvnum_big = (int)fvnum_short+1;

                //System.out.println("fvnum:"+fvnum+" nxt-short:"+fvnum_short+" nxt-big:"+fvnum_big);

                SWBResourceURL urla = paramRequest.getActionUrl();
                urla.setParameter("faction", "in");
                urla.setAction("check");
                out.println("<div >");
                out.println("<form method=\"post\" action=\"" + urla + "\" enctype=\"multipart/form-data\" >");
                out.println("<input type=\"hidden\" name=\"" + PARAM_UUID + "\" value=\"" + uuid + "\" />");
                out.println("<input type=\"hidden\" name=\"repNS\" value=\"" + ns + "\" />");
                out.println("<fieldset>");
                out.println("<legend>" + paramRequest.getLocaleString("msgVersionUpdate") + "</legend>");

                out.println("<table width=\"100%\" border=\"0\">");
                out.println("<tr>");
                out.println("<td>" + paramRequest.getLocaleString("msgTitle") + ":</td>");
                out.println("<td>" + nodo.getProperty("swb:title").getString() + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>" + paramRequest.getLocaleString("msgDescription") + ":</td>");
                out.println("<td>" + nodo.getProperty("swb:description").getString() + "</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>" + paramRequest.getLocaleString("msgFile") + ":</td>");
                out.println("<td>" + filename + " ver. " + vernum +"</td>");
                out.println("</tr>");
//                out.println("<tr>");
//                out.println("<td>" + paramRequest.getLocaleString("msgNextVersion") + ":</td>");
//                out.println("<td><select name=\"version_number\"><option value=\"short\">"+fvnum_short+"</option><option value=\"big\">"+fvnum_big+"</option></select></td>");
//                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>" + paramRequest.getLocaleString("msgFileNewVerComm") + ":</td>");
                out.println("<td><textarea id=\"commentversion\" name=\"commentversion\" rows=\"5\" cols=\"20\"></textarea></td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>" + paramRequest.getLocaleString("msgUpdatedFile") + ":</td>");
                out.println("<td><input type=\"file\" id=\"fileversion\" name=\"fileversion\" /></td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</fieldset>");
                out.println("<fieldset>");
                SWBResourceURL urlb = paramRequest.getRenderUrl();
                urlb.setMode(SWBResourceURL.Mode_VIEW);
                out.println("<input type=\"submit\" name=\"Actualizar\" value=\"" + paramRequest.getLocaleString("msgBTNUpdate") + "\"/>");
                out.println("<input type=\"button\" name=\"Cancelar\" onclick=\"window.location='" + urlb + "';\" value=\"" + paramRequest.getLocaleString("msgBTNCancel") + "\" />");
                out.println("</fieldset>");
                out.println("</form>");
                out.println("</div>");
            } catch (Exception e) {
                log.error("Error ", e);
            }
        }
    }

    public void doGetFile(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        User user = paramRequest.getUser();
        String uuid = request.getParameter(PARAM_UUID);
        String uuidtoken = null;
        String ns = request.getParameter("repNS");
        String versionfile = request.getParameter("version");

        String filename = null;
        String qs = request.getRequestURI();

        if (uuid == null) {
            StringTokenizer stoken = new StringTokenizer(qs, "/");
            int c = stoken.countTokens();
            int contador = 0;
            while (stoken.hasMoreTokens()) {
                contador++;
                uuidtoken = stoken.nextToken();
                if (contador == (c - 1)) {
                    break;
                }
            }

            if (stoken.hasMoreTokens()) {
                filename = stoken.nextToken(); //obteniendo el nombre del archivo
            }
            if (filename.indexOf(".") > 0) {
                uuid = uuidtoken;
            }
        }

        if (ns == null) {
            ns = docRepNS(request, response, paramRequest);
        }


        String action = paramRequest.getAction();
        Credentials credentials = new SWBCredentials(user);
        Session session = null;
        try {

            session = rep.login(credentials, ns);
            Node nodo = session.getNodeByUUID(uuid);

            String faction = "";
            if (request.getParameter("faction") != null && request.getParameter("faction").trim().length() > 0) {
                faction = request.getParameter("faction");
            }
            filename = nodo.getName();
            String vernum = getLastVersionOfcontent(session, ns, uuid);
            if (versionfile != null && versionfile.trim().length() > 0) {
                vernum = versionfile;
            }
            String str_file = getContentFile(session, ns, uuid, vernum);
            Node nc = nodo.getNode(JCR_CONTENT);
//            if ("checkout".equals(faction)) {
//                try {
//                    nc.checkout();
//                    nc.setProperty(SWBFILEREPUSERID, user.getId());
//                    nc.save();
//                } catch (Exception e) {
//                    log.error("Error al hacer check out del archivo. doGetFile.", e);
//                }
//            }
            String mime = null;
            if (nc != null) {
                try {
                    mime = nc.getProperty("jcr:mimeType").getString();
                } catch (Exception e) {
                    mime = DEFAULT_MIME_TYPE;
                }
                //response.setHeader("Content-type", mime + ";");
            }
            if (action != null && "inline".equals(action)) {
                response.setContentType(mime);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\";");
            }
            OutputStream out = response.getOutputStream();
            SWBUtils.IO.copyStream(SWBUtils.IO.getStreamFromString(str_file), out);
        } catch (Exception e) {
            log.error("Error al obtener el archivo del Repositorio de documentos.", e);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }

    public int getLevelUser(User user) {
        int level = 0;

        if (null == user) {
            return level;
        }

        String uriView = getSee() != null ? getSee() : "0";
        String uriModify = getModify() != null ? getModify() : "0";
        String uriAdmin = getAdmin() != null ? getAdmin() : "0";

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = null;
        try {
            gobj = ont.getGenericObject(uriAdmin);
        } catch (Exception e) {
            //log.error("Errror getLevelUser()",e);
        }

        UserGroup ugrp = null;
        Role urole = null;

        if (!uriAdmin.equals("0")) {
            if (gobj != null) {
                if (gobj instanceof UserGroup) {
                    ugrp = (UserGroup) gobj;
                    if (user.hasUserGroup(ugrp)) {
                        level = 3;
                    }
                } else if (gobj instanceof Role) {
                    urole = (Role) gobj;
                    if (user.hasRole(urole)) {
                        level = 3;
                    }
                }
            } else {
                level = 3;
            }
        } else {
            level = 3;
        }

        if (level == 0) {
            if (!uriModify.equals("0")) {
                gobj = ont.getGenericObject(uriModify);
                if (gobj != null) {
                    if (gobj instanceof UserGroup) {
                        ugrp = (UserGroup) gobj;
                        if (user.hasUserGroup(ugrp)) {
                            level = 2;
                        }
                    } else if (gobj instanceof Role) {
                        urole = (Role) gobj;
                        if (user.hasRole(urole)) {
                            level = 2;
                        }
                    }
                } else {
                    level = 2;
                }
            } else {
                level = 2;
            }
        }

        if (level == 0) {
            if (!uriView.equals("0")) {
                gobj = ont.getGenericObject(uriView);
                if (gobj != null) {
                    if (gobj instanceof UserGroup) {
                        ugrp = (UserGroup) gobj;
                        if (user.hasUserGroup(ugrp)) {
                            level = 1;
                        }
                    } else if (gobj instanceof Role) {
                        urole = (Role) gobj;
                        if (user.hasRole(urole)) {
                            level = 1;
                        }
                    }
                } else {
                    level = 1;
                }
            } else {
                level = 1;
            }
        }

        return level;
    }

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

    public String docRepNS(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) {
        String NS = IDREP;
        WebSite ws = paramRequest.getWebPage().getWebSite();
        try {

            String[] lws = rep.listWorkspaces();
            for (int i = 0; i < lws.length; i++) {
                if (lws[i].endsWith(ws.getId() + "_rep")) {
                    IDREP = lws[i];
                    NS = IDREP;
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Error al obtener el NameSpace del Repositorio de docuentos.", e);
        }
        return NS;
    }

    private String getLastVersionOfcontent(Session session, String repositoryName, String contentId) throws Exception {
        String getLastVersionOfcontent = null;
        ArrayList<Version> versions = new ArrayList<Version>();
        try {
            Node nodeContent = session.getNodeByUUID(contentId);
            Node resContent = nodeContent.getNode(JCR_CONTENT);
            VersionIterator it = resContent.getVersionHistory().getAllVersions();
            while (it.hasNext()) {
                Version version = it.nextVersion();
                if (!version.getName().equals("jcr:rootVersion")) {
                    versions.add(version);
                }
            }
            for (Version version : versions) {
                if (getLastVersionOfcontent == null) {
                    getLastVersionOfcontent = version.getName();
                } else {
                    try {
                        float currentVersion = Float.parseFloat(version.getName());
                        if (Float.parseFloat(getLastVersionOfcontent) < currentVersion) {
                            getLastVersionOfcontent = version.getName();
                        }
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            }

        } catch (ItemNotFoundException infe) {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
        return getLastVersionOfcontent;
    }

    public String getContentFile(Session session, String repositoryName, String contentId, String version) throws Exception {
        try {
            Node nodeContent = session.getNodeByUUID(contentId);
            //String cm_file = loader.getOfficeManager(repositoryName).getPropertyFileType();
            Node resContent = nodeContent.getNode(JCR_CONTENT);
            if (version != null && version.equals("*")) {
                String lastVersion = getLastVersionOfcontent(session, repositoryName, contentId);
                Version versionNode = resContent.getVersionHistory().getVersion(lastVersion);
                if (versionNode != null) {
                    Node frozenNode = versionNode.getNode(JCR_FROZEN_NODE);
                    return frozenNode.getProperty("jcr:data").getString();
                } else {
                    return null;
                }

            } else {
                Version versionNode = resContent.getVersionHistory().getVersion(version);
                if (versionNode != null) {
                    Node frozenNode = versionNode.getNode(JCR_FROZEN_NODE);
                    return frozenNode.getProperty("jcr:data").getString();
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }

    private String getLinkStyle() {
        StringBuilder ret = new StringBuilder();
        ret.append("\n<style type=\"text/css\">");
        ret.append("\n a:link {text-decoration: none}");
        ret.append("\n a:visited {text-decoration: none}");
        ret.append("\n a:active {text-decoration: none}");
        ret.append("\n a:hover {text-decoration: none}");
        ret.append("\n</style>");

        return ret.toString();
    }
}
