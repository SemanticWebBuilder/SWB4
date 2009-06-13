package org.semanticwb.resources.filerepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.jcr.Credentials;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.version.Version;
import javax.jcr.version.VersionIterator;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
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

public class SemanticRepositoryFile extends org.semanticwb.resources.filerepository.base.SemanticRepositoryFileBase {

    private static SWBRepository rep = null;
    private static Logger log = SWBUtils.getLogger(SemanticRepositoryFile.class);
    private static final String MODE_FILES = "showfiles";
    private static final String MODE_DIRS = "showdirs";
    private static final String MODE_ADDFORM = "getAddForm";
    private static final String MODE_GETFILE = "getFile";
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
            doAddForm(request, response, paramRequest);//getAddForm
        } else if (paramRequest.getMode().equals(MODE_GETFILE)) {
            doGetFile(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();

        WebPage wp = paramRequest.getTopic();
        IDREP = docRepNS(request, response, paramRequest);

        if (resUUID == null) {
            loadResUUID(wp);
        }

        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";

        out.println("<div style=\"font-family:verdana; font-size:10px;\">"); //class=\"SemanticRepositoryFile\"
        out.println("<fieldset>");
        out.println("<legend> <img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + getTitle(request, paramRequest) + "</legend>");
        out.println("");
        if (!isUseFolders()) {
            out.println(doShowFiles(request, response, paramRequest));
        } else {
            out.println(doShowDirs(request, response, paramRequest));
        }
        out.println("</fieldset>");
        out.println("</div>");
    }

    private void loadResUUID(WebPage wp) {
        Session session = null;
        resUUID = getResourceBase().getAttribute("uuid");

        if (resUUID == null) {
            try {
                User usrcreator = getResourceBase().getCreator();
                session = rep.login(new SWBCredentials(usrcreator), IDREP);
                Node root = null;
                Node nodeRep = null;
                Node nodePage = null;

                root = session.getRootNode();
                if (!root.hasNode(getResourceBase().getId())) {
                    nodeRep = root.addNode(getResourceBase().getId(), REP_NODE);
                } else {
                    nodeRep = root.getNode(getResourceBase().getId());
                }

                //Revisando el nodo asociado a la página
                if (nodeRep != null && !nodeRep.hasNode(wp.getId())) {
                    nodePage = nodeRep.addNode(wp.getId(), REP_FOLDER);
                    nodePage.setProperty("swb:title", wp.getDisplayName());
                    nodePage.setProperty("swbfilerep:userid", usrcreator.getId());
                    nodePage.setProperty("swb:description", "");
                    nodePage.setProperty("swbfilerep:deleted", Boolean.FALSE);
                }
                root.save();
                resUUID = nodePage.getUUID();
                getResourceBase().setAttribute("uuid", resUUID);
                getResourceBase().updateAttributesToDB();

            } catch (Exception e) {
                log.error("Error al revisar el nodo raíz del Rep. de Documentos. SemanticRepositoryFile.checkResUDDI()", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }

        }
    }

    public String doShowFiles(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer("");
        User user = paramRequest.getUser();

        Session session = null;

        String parentUUID = request.getParameter("parentUUID");
        IDREP = docRepNS(request, response, paramRequest);

        if (resUUID == null) {
            loadResUUID(paramRequest.getTopic());
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

            ret.append("\n<div style=\"float:left; margin-right:10px; width:600px; border:solid 1px;\">");
            ret.append("\n<table width=\"100%\">");
            ret.append("\n<thead style=\"color:darkblue;\">");

            ret.append("\n<tr >");
            ret.append("\n<th colspan=\"2\">");
            ret.append("\n" + nodePage.getProperty("swb:title").getString());
            ret.append("\n</th>");
            ret.append("\n<th colspan=\"2\">");
            ret.append("\n<img src=\"" + path + "suscribe.gif\" border=\"0\"/>Suscribirse a directorio");
            ret.append("\n</th>");
            ret.append("\n<th colspan=\"2\" align=\"right\">");
            SWBResourceURL urladd = paramRequest.getRenderUrl();
            urladd.setAction("addfile");
            urladd.setParameter("parentUUID", parentUUID);
            urladd.setMode(MODE_ADDFORM);
            ret.append("\n<a href=\"" + urladd + "\">");
            ret.append("\n<img src=\"" + path + "add.gif\" border=\"0\"/>");
            ret.append("\nAgregar archivo");
            ret.append("\n</a>");
            ret.append("\n</th>");
            ret.append("\n</tr>");

            ret.append("\n<tr style=\"background-color:lightgray;\">");
            ret.append("\n<th >");
            ret.append("\nInfo");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\nIcono");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\nTítulo");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\nFecha");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\nMarcar");
            ret.append("\n</th>");
            ret.append("\n<th>");
            ret.append("\nAcción");
            ret.append("\n</th>");
            ret.append("\n</tr>");
            ret.append("\n</thead>");

            int nfiles = 0;
            if (nit.hasNext()) {
                ret.append("\n<tbody>");
                while (nit.hasNext()) {

                    Node nodo = nit.nextNode();

//                    if (nodo.isCheckedOut() && !nodo.isLocked()) {
//                        Node nc = nodo.getNode(JCR_CONTENT);
//                        nc.checkin();
//                        nc.save();
//                    }
                    if (nodo.getPrimaryNodeType().getName().equals(REP_FILE)) {
                        boolean isdeleted = false;
                        try {
                            isdeleted = nodo.getProperty("swbfilerep:deleted").getBoolean();
                        } catch (Exception e) {
                            log.event("Error al revisar la propiedad Deleted del repositorio de documentos.", e);
                        }

                        if (nodo != null && !isdeleted) {
                            nfiles++;
                            UUID = nodo.getUUID();
                            ret.append("\n<tr>");
                            ret.append("\n<td>");
                            SWBResourceURL urldetail = paramRequest.getRenderUrl();
                            urldetail.setMode(MODE_ADDFORM);
                            urldetail.setParameter("uuid", nodo.getUUID());
                            urldetail.setParameter("repNS", IDREP);
                            urldetail.setAction(FILE_DETAIL);
                            ret.append("\n<a href=\"#\" onclick=\"window.location='" + urldetail + "';\"><img src=\"" + path + "info.gif\" border=\"0\"/></a>");
                            ret.append("\n</td>");
                            ret.append("\n<td>");
                            String file = nodo.getName();
                            String type = getFileName(file);

                            SWBResourceURL urlgetfile = paramRequest.getRenderUrl();
                            urlgetfile.setMode(MODE_GETFILE);
                            urlgetfile.setParameter("uuid", nodo.getUUID());
                            urlgetfile.setParameter("repNS", IDREP);
                            urlgetfile.setAction("inline");
                            urlgetfile.setCallMethod(SWBResourceURL.Call_DIRECT);
                            urlgetfile.setWindowState(SWBResourceURL.WinState_MAXIMIZED);


                            ret.append("\n<a href=\"#\" onclick=\"window.location='" + urlgetfile + "';\" alt=\"\"><IMG border=0 src='" + path + "" + type + "' alt=\"" + getFileType(file) + "\"></a>");
                            ret.append("\n</td>");
                            ret.append("\n<td>");
                            ret.append("\n" + nodo.getProperty("swb:title").getString());
                            ret.append("\n</td>");
                            ret.append("\n<td>");
                            ret.append("\n" + nodo.getProperty("jcr:created").getString());
                            ret.append("\n</td>");
                            ret.append("\n<td>");
                            SWBResourceURL uout = paramRequest.getActionUrl();
                            uout.setParameter("uuid", UUID);
                            uout.setParameter("repNS", IDREP);

//                            if (!nodo.isCheckedOut()) {
                            uout.setParameter("faction", "out");
                            uout.setAction("check");
                            ret.append("\n<a href=\"#\" onclick=\"window.location='" + uout + "';\">");
                            ret.append("\n<img src=\"" + path + "out.gif\" border=\"0\"/>");
                            ret.append("\n</a>");
//                            }
//                            else {
//                                ret.append("\n<img src=\"" + path + "reserved.gif\" border=\"0\"/>");
//                                uout.setParameter("faction", "in");
//                                uout.setAction("check");
//                                ret.append("\n<a href=\"#\" onclick=\"window.location='" + uout + "';\">");
//                                ret.append("\n<img src=\"" + path + "in.gif\" border=\"0\"/>");
//                                ret.append("\n</a>");
//                            }
                            ret.append("\n</td>");
                            ret.append("\n<td>");
                            SWBResourceURL usus = paramRequest.getRenderUrl();
                            usus.setParameter("uuid", UUID);
                            usus.setParameter("repNS", IDREP);
                            SWBResourceURL uview = paramRequest.getRenderUrl();
                            uview.setParameter("uuid", UUID);
                            uview.setParameter("repNS", IDREP);
                            ret.append("\n<img src=\"" + path + "suscribe.gif\" border=\"0\"/>");
                            ret.append("\n<a href=\"#\" onclick=\"window.location='" + urlgetfile + "';\" alt=\"\"><img src=\"" + path + "preview.gif\" border=\"0\" alt=\"ver archivo\"/></a>");
                            SWBResourceURL udel = paramRequest.getActionUrl();
                            udel.setParameter("uuid", UUID);
                            udel.setParameter("repNS", IDREP);
                            udel.setAction("removefile");
                            ret.append("\n<a href=\"#\" onclick=\"if(confirm('¿Estás seguro de querer eliminar el archivo " + nodo.getName() + "?')){ window.location='" + udel + "';} else { return false; }\">");
                            ret.append("\n<img src=\"" + path + "delete.gif\" border=\"0\"/>");
                            ret.append("\n</a>");

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

                ret.append("\n<tbody>");
            }
            ret.append("\n<tfoot style=\"color:darkblue;\">");
            ret.append("\n<tr>");
            ret.append("\n<th colspan=\"3\">");
            ret.append("\n(Activos:" + nfiles + ",Totales:" + (nit.getSize() != -1 ? nit.getSize() : 0) + ") archivos");
            ret.append("\n</th>");
            ret.append("\n<th colspan=\"3\" align=\"right\">");
            ret.append("\n<a href=\"" + urladd + "\">");
            ret.append("\n<img src=\"" + path + "add.gif\" border=\"0\"/>");
            ret.append("\nAgregar archivo");
            ret.append("\n</a>");
            ret.append("\n</th>");
            ret.append("\n</tr>");
            ret.append("\n</tfoot>");

            ret.append("\n</table>");
            ret.append("\n</div>");

        } catch (Exception e) {
            log.error("Error al traer los archivos de folder del repositorio de documentos", e);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
        return ret.toString();
    }

    public String doSelectFolders(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer("");
        ret.append("\n<div style=\"float:left; margin-right:10px; width:200px; border:solid 1px;\">");
        ret.append("\n<ul>");
        ret.append("\n</ul>");
        ret.append("\n</div>");
        ret.append(doShowFiles(request, response, paramRequest));
        return ret.toString();
    }

    public String doShowDirs(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer ret = new StringBuffer("");
        User user = paramRequest.getUser();
        WebPage wp = paramRequest.getTopic();
        Session session = null;
        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";
        IDREP = docRepNS(request, response, paramRequest);
        if (resUUID == null) {
            loadResUUID(wp);
        }
        ret.append("\n<script>include_dom('" + SWBPlatform.getContextPath() + "/swbadmin/js/repositoryfile.js');</script>");
        ret.append("\n<div style=\"float:left; margin-right:10px; width:200px; border:solid 1px;\">");
        SWBResourceURL urlfol = paramRequest.getRenderUrl();
        //urlfol.setAction("addfolder");
        urlfol.setMode(SWBResourceURL.Mode_VIEW);
        String formid = "repAddFolder" + getResourceBase().getId();

        try {
            ret.append("\n<fieldset>");

            ret.append("\n<ul>");

            session = rep.login(new SWBCredentials(user), IDREP);
            Node nodePage = session.getNodeByUUID(resUUID);
            if (user != null && user.isSigned()) {
                ret.append("<li><a href=\"#\" onclick=\"javascript:setParentUUID(document." + formid + ",'" + resUUID + "'); window.location='" + urlfol + "';\"><img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + nodePage.getProperty("swb:title").getString() + "</a></li>");
            } else {
                ret.append("<li><a href=\"#\"><img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/>" + nodePage.getProperty("swb:title").getString() + "</a></li>");
            }
            NodeIterator nit = nodePage.getNodes(REP_FOLDER);
            if (nit.hasNext()) {
                ret.append("\n<ul>");
                while (nit.hasNext()) {
                    Node nodofolder = nit.nextNode();
                    if (user != null && user.isSigned()) {
                        ret.append("<li><a href=\"#\" onclick=\"javascript:setParentUUID(document." + formid + ",'" + nodofolder.getUUID() + "'); window.location='" + urlfol + "?parentUUID=" + nodofolder.getUUID() + "';\"><img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/>" + nodofolder.getProperty("swb:title").getString() + "</a></li>");
                    } else {
                        ret.append("<li><a href=\"#\"><img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/>" + nodofolder.getProperty("swb:title").getString() + "</a></li>");
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

        if (user != null && user.isSigned()) {
            ret.append("\n<fieldset>");
            SWBResourceURL urladd = paramRequest.getRenderUrl();
            urladd.setAction("addfolder");
            urladd.setMode(MODE_ADDFORM);
            ret.append("\n<form name=\"" + formid + "\" action=\"" + urladd + "\" method=\"post\">");
            ret.append("\n<input type=\"hidden\" name=\"parentUUID\" value=\"" + resUUID + "\">");
            ret.append("\n<input type=\"submit\" value=\"crear carpeta\">");
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
        String action = paramRequest.getAction();
        if (action == null) {
            action = "";
        }
        IDREP = docRepNS(request, response, paramRequest);
        WebPage dir = paramRequest.getTopic();
        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";
        User user = paramRequest.getUser();
        Credentials credentials = new SWBCredentials(user);
        SWBResourceURL url = paramRequest.getActionUrl();
        if (parentUUID == null) {
            parentUUID = resUUID;
        }

        if (getLevelUser(user) < 2) {
            return;
        }

        ret.append("\n<script>include_dom('" + SWBPlatform.getContextPath() + "/swbadmin/js/repositoryfile.js');</script>");

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
            ret.append("\n<fieldset>");
            ret.append("\n<legend>");
            ret.append("\n<img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + folderTitle);
            ret.append("\n</legend>");
            ret.append("\n<form name='frmnewdoc' method='POST' enctype='multipart/form-data' action='" + url.toString() + "'>");
            ret.append("\n<input type=\"hidden\" name=\"repNS\" value=\"" + IDREP + "\">");
            ret.append("\n<input type=\"hidden\" name=\"parentUUID\" value=\"" + parentUUID + "\">");
            ret.append("\n<table width='100%'  border='0' cellspacing='0' cellpadding='1'>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgTitleDocument"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<input  type='text' maxlength='99' name='repftitle'>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgDescription"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<textarea rows='5' name='repfdescription' cols='20' onKeyDown='textCounter(this.form.repfdescription,255);' onKeyUp='textCounter(this.form.repfdescription,255);'></textarea>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgFile"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<input type='file'  name='repfdoc'>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n</table>");
            ret.append("\n</fieldset>");
            ret.append("\n<fieldset>");
            ret.append("\n<input type='button'  name='s' value='" + paramRequest.getLocaleString("msgBTNSave") + "' onclick='javascript:valida();'>\r\n");
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setMode(SWBResourceURL.Mode_VIEW);
            ret.append("\n<input type='button'  name='cancel' value='" + paramRequest.getLocaleString("msgBTNCancel") + "' onclick=\"window.location='" + urlb.toString() + "'\">\r\n");
            ret.append("\n</form>");
            ret.append("\n</fieldset>");
        } else if (action.equals("addfolder")) {
            url.setAction("addfolder");
            ret.append("\n<fieldset>");
            ret.append("\n<legend>");
            ret.append("\n<img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + dir.getDisplayName());
            ret.append("\n</legend>");
            ret.append("\n<form name='frmnewfolder' method='POST' action='" + url.toString() + "'>");
            ret.append("\n<input type=\"hidden\" name=\"repNS\" value=\"" + IDREP + "\">");
            ret.append("\n<input type=\"hidden\" name=\"parentUUID\" value=\"" + parentUUID + "\">");
            ret.append("\n<table width='100%'  border='0' cellspacing='0' cellpadding='1'>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\nNombre de la carpeta");
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<input  type='text' maxlength='99' name='repftitle'>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgDescription"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<textarea rows='5' name='repfdescription' cols='20' onKeyDown='textCounter(this.form.repfdescription,255);' onKeyUp='textCounter(this.form.repfdescription,255);'></textarea>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n</table>");
            ret.append("\n</fieldset>");
            ret.append("\n<fieldset>");
            ret.append("\n<input type='button'  name='s' value='" + paramRequest.getLocaleString("msgBTNSave") + "' onclick='javascript:validateAddFolder();'>\r\n");
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setMode(SWBResourceURL.Mode_VIEW);
            ret.append("\n<input type='button'  name='cancel' value='" + paramRequest.getLocaleString("msgBTNCancel") + "' onclick=\"window.location='" + urlb.toString() + "'\">\r\n");
            ret.append("\n</form>");
            ret.append("\n</fieldset>");
        } else if (action.equals(FILE_DETAIL)) {

            String folderTitle = dir.getDisplayName();
            String UUID = request.getParameter("uuid");

            Node nodo = null;
            Node nf = null;

            //System.out.println("Detalle ---- UUID: "+UUID+", REP:"+IDREP);
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
            ret.append("\n<fieldset>");
            ret.append("\n<legend>");
            ret.append("\n<img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + folderTitle);
            ret.append("\n</legend>");
            ret.append("\n<form name='frmnewdoc' method='POST' enctype='multipart/form-data' action='" + url.toString() + "'>");
            ret.append("\n<input type=\"hidden\" name=\"repNS\" value=\"" + IDREP + "\">");
            ret.append("\n<input type=\"hidden\" name=\"parentUUID\" value=\"" + parentUUID + "\">");
            ret.append("\n<input type=\"hidden\" name=\"uuid\" value=\"" + UUID + "\">");
            ret.append("\n<table width='100%'  border='0' cellspacing='0' cellpadding='1'>");
            try {

                session = rep.login(credentials, IDREP);
                nodo = session.getNodeByUUID(UUID);

                if (nodo.getPrimaryNodeType().getName().equals(REP_FILE)) {
                    Node nc = nodo.getNode(JCR_CONTENT);
                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\nTítulo:");
                    ret.append("\n</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + nodo.getProperty("swb:title").getString());
                    ret.append("\n</td>");
                    ret.append("\n</tr>");
                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\nDescripción:");
                    ret.append("\n</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + nodo.getProperty("swb:description").getString());
                    ret.append("\n</td>");
                    ret.append("\n</tr>");


                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\nÚltima actualización:");
                    ret.append("\n</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + nc.getProperty("jcr:lastModified").getString());
                    ret.append("\n</td>");
                    ret.append("\n</tr>");


                    String vn = getLastVersionOfcontent(session, "", UUID);
                    Version version = nodo.getBaseVersion();

                    User autor = user.getUserRepository().getUser(nc.getProperty("swbfilerep:userid").getString());

                    String fullname = "Anónimo";
                    if (autor != null) {
                        fullname = autor.getFullName();
                    }

                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\nAutor:");
                    ret.append("\n</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + fullname);
                    ret.append("\n</td>");
                    ret.append("\n</tr>");



                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\nVersión Actual:");
                    ret.append("\n</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + vn);
                    ret.append("\n</td>");
                    ret.append("\n</tr>");


                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    String file = nodo.getName();
                    String type = getFileName(file);
                    ret.append("\nNombre del archivo:");
                    ret.append("\n</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + file);
                    ret.append("\n</td>");
                    ret.append("\n</tr>");

                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\nTipo de archivo:");
                    ret.append("\n</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + getFileType(file));
                    ret.append("\n</td>");
                    ret.append("\n</tr>");

                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    ret.append("\nTamaño:");
                    ret.append("\n</td>");
                    ret.append("\n<td>");
                    ret.append("\n" + nc.getProperty("swbfilerep:filesize").getString() + "kb");
                    ret.append("\n</td>");
                    ret.append("\n</tr>");

                    ret.append("\n<tr>");
                    ret.append("\n<td>");
                    SWBResourceURL usus = paramRequest.getRenderUrl();
                    usus.setParameter("uuid", UUID);
                    usus.setParameter("repNS", IDREP);
                    SWBResourceURL uview = paramRequest.getRenderUrl();
                    uview.setParameter("uuid", UUID);
                    uview.setParameter("repNS", IDREP);
                    ret.append("\n<img src=\"" + path + "suscribe.gif\" border=\"0\"/>");
                    ret.append("\n<img src=\"" + path + "preview.gif\" border=\"0\"/>");
                    SWBResourceURL udel = paramRequest.getActionUrl();
                    udel.setParameter("uuid", UUID);
                    udel.setParameter("repNS", IDREP);
                    udel.setAction("removefile");
                    ret.append("\n<a href=\"#\" onclick=\"if(confirm('¿Estás seguro de querer eliminar el archivo " + nodo.getName() + "?')){ window.location='" + udel + "';} else { return false; }\">");
                    ret.append("\n<img src=\"" + path + "delete.gif\" border=\"0\"/>");
                    ret.append("\n</a>");
                    if (isUseFolders()) {
                        SWBResourceURL umove = paramRequest.getRenderUrl();
                        umove.setParameter("uuid", UUID);
                        umove.setParameter("parent_wp", parentUUID);
                        umove.setAction("movefolder");
                        umove.setMode("selectFolder");
                        umove.setParameter("repNS", IDREP);
                        ret.append("<a href=\"" + umove + "\">");
                        ret.append("\n<img src=\"" + path + "folder.gif\" border=\"0\"/>");
                        ret.append("</a>");
                    }
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
            ret.append("\n<input type='button'  name='s' value='" + paramRequest.getLocaleString("msgBTNSave") + "' onclick='javascript:valida();'>\r\n");
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setMode(SWBResourceURL.Mode_VIEW);
            ret.append("\n<input type='button'  name='cancel' value='" + paramRequest.getLocaleString("msgBTNCancel") + "' onclick=\"window.location='" + urlb.toString() + "'\">\r\n");
            ret.append("\n</form>");
            ret.append("\n</fieldset>");

        } else if (action.equals("showfileversions")) {

            String folderTitle = dir.getDisplayName();
            if (!resUUID.equals(parentUUID)) {
                try {

                    session = rep.login(new SWBCredentials(user), IDREP);
                    Node nf = session.getNodeByUUID(parentUUID);
                    if (null != nf) {
                        folderTitle = folderTitle + " - " + nf.getProperty("swb:title").getString();
                    }
                } catch (Exception e) {
                    log.error("Error al tratar de cargar el nodo de la carpeta con UUID: " + parentUUID, e);
                }
            }
            url.setAction("updatefile");
            ret.append("\n<fieldset>");
            ret.append("\n<legend>");
            ret.append("\n<img src=\"" + path + "icon-foldera.gif\" border=\"0\" alt=\"\"/> " + folderTitle);
            ret.append("\n</legend>");
            ret.append("\n<form name='frmnewdoc' method='POST' enctype='multipart/form-data' action='" + url.toString() + "'>");
            ret.append("\n<input type=\"hidden\" name=\"repNS\" value=\"" + IDREP + "\">");
            ret.append("\n<input type=\"hidden\" name=\"parentUUID\" value=\"" + parentUUID + "\">");
            ret.append("\n<table width='100%'  border='0' cellspacing='0' cellpadding='1'>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgTitleDocument"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<input  type='text' maxlength='99' name='repftitle'>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgDescription"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<textarea rows='5' name='repfdescription' cols='20' onKeyDown='textCounter(this.form.repfdescription,255);' onKeyUp='textCounter(this.form.repfdescription,255);'></textarea>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td width=\"200\">");
            ret.append("\n" + paramRequest.getLocaleString("msgFile"));
            ret.append("\n</td>");
            ret.append("\n<td>");
            ret.append("\n<input type='file'  name='repfdoc'>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n</table>");
            ret.append("\n</fieldset>");
            ret.append("\n<fieldset>");
            ret.append("\n<input type='button'  name='s' value='" + paramRequest.getLocaleString("msgBTNSave") + "' onclick='javascript:valida();'>\r\n");
            SWBResourceURL urlb = paramRequest.getRenderUrl();
            urlb.setMode(SWBResourceURL.Mode_VIEW);
            ret.append("\n<input type='button'  name='cancel' value='" + paramRequest.getLocaleString("msgBTNCancel") + "' onclick=\"window.location='" + urlb.toString() + "'\">\r\n");
            ret.append("\n</form>");
            ret.append("\n</fieldset>");

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

        System.out.println("WP-Admin: " + paramRequest.getTopic().getTitle());

        PrintWriter out = response.getWriter();
        String accion = paramRequest.getAction();
        if (accion == null) {
            accion = "";
        }
        User user = paramRequest.getUser();

        WebPage wpage = paramRequest.getTopic();
        WebSite wsite = wpage.getWebSite();

        IDREP = docRepNS(request, response, paramRequest);
        if (resUUID == null) {
            loadResUUID(wpage);
        }

        long id = 0;
        boolean flag = false;

        try {
            if (getSee() != null) {
                strView = getSee();
                flag = true;
            }
            if (getModify() != null) {
                strModify = getModify();
                flag = true;
            }
            if (getAdmin() != null) {
                strAdmin = getAdmin();
                flag = true;
            }
        } catch (Exception e) {
            log.error("Error al revisar la configuración inicial del repositorio de documentos.");
        }

        if (isUseFolders()) {
            strCheck = "checked";
            flag = true;
        } else {
            strCheck = "";
            flag = true;
        }

        out.println("<div class=\"swbform\">");

        if (!accion.equals("")) {
            SWBResourceURL urlAddRule = paramRequest.getActionUrl();
            urlAddRule.setAction("admin_update");

            out.println("<form dojoType=\"dijit.form.Form\" id=\"" + getResourceBase().getId() + "/repfile\" name=\"myForm\" onsubmit=\"submitForm('" + getResourceBase().getId() + "/repfile'); return false;\" action=\"" + urlAddRule.toString() + "\" method=\"POST\">");

            out.println("<fieldset>");
            out.println("<legend>");
            out.println("Repository Resource");
            out.println("</legend>");


            out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"10\">");

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            String strTemp = "<option value=\"-1\">" + paramRequest.getLocaleString("msgNoRolesAvailable") + "</option>";
            Iterator<Role> iRoles = wsite.getUserRepository().listRoles(); //DBRole.getInstance().getRoles(topicmap.getDbdata().getRepository());
            StringBuffer strRules = new StringBuffer("");
            strRules.append("\n<option value=\"0\">" + paramRequest.getLocaleString("msgSelNone") + "</option>");
            strRules.append("\n<optgroup label=\"Roles\">");
            while (iRoles.hasNext()) {
                Role oRole = iRoles.next();
                strRules.append("\n<option value=\"" + oRole.getURI() + "\">" + oRole.getDisplayTitle(user.getLanguage()) + "</option>");
            }
            strRules.append("\n</optgroup>");

            strRules.append("\n<optgroup label=\"User Groups\">");
            Iterator<UserGroup> iugroups = wsite.getUserRepository().listUserGroups();
            while (iugroups.hasNext()) {
                UserGroup oUG = iugroups.next();
                strRules.append("\n<option value=\"" + oUG.getURI() + "\">" + oUG.getDisplayTitle(user.getLanguage()) + "</option>");
            }
            strRules.append("\n</optgroup>");
            if (strRules.toString().length() > 0) {
                strTemp = strRules.toString();
            }

            out.println("<tr><td colspan=\"2\"><B>" + paramRequest.getLocaleString("msgRolesDefinitionLevel") + "</b></td></tr>");
            out.println("<tr><td align=right width=150>" + paramRequest.getLocaleString("msgView") + ":</td>");
            out.println("<td><select name=\"ver\">" + strTemp + "</select></td></tr>");
            out.println("<tr><td align=right width=150>" + paramRequest.getLocaleString("msgModify") + ":</td>");
            out.println("<td><select name=\"modificar\">" + strTemp + "</select></td></tr>");
            out.println("<tr><td align=right  width=150>" + paramRequest.getLocaleString("msgAdministrate") + ":</td>");
            out.println("<td><select name=\"administrar\">" + strTemp + "</select></td></tr>");
            out.println("<tr><td align=right  width=150>" + paramRequest.getLocaleString("msgShowSubDirs") + ":</td>");
            out.println("<td><input type=\"checkbox\" name=\"showdirectory\" value=\"1\" " + strCheck + " " + strEnable + ">");
            out.println("</td></tr>");

            out.println("<tr><td align=right width=150>" + paramRequest.getLocaleString("msgCreateNotificationMessage") + ":</td>");

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
            out.println("<tr><td align=right width=150>" + paramRequest.getLocaleString("msgUpdNotificationMessage") + ":</td>");

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
            out.println("<tr><td align=right width=150>" + paramRequest.getLocaleString("msgRemoveNotificationMessage") + ":</td>");

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

            out.println("<td><textarea name=\"notificationremove\" cols=60 rows=10>" + strNotify + "</textarea></td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btn\" >" + paramRequest.getLocaleString("msgBTNAccept") + "</button>");

            SWBResourceURL urlold = paramRequest.getRenderUrl();
            urlold.setAction("showold");
            urlold.setParameter("", strTemp);

            out.println("<button dojoType=\"dijit.form.Button\" name=\"btnold\" onclick=\"submitUrl('" + urlold + "',this.domNode)\" >" + paramRequest.getLocaleString("msgBTNViewOldFiles") + "</button>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<br>* " + paramRequest.getLocaleString("msgNotificationKeys") + ":");
            out.println("<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getUserName}   " + paramRequest.getLocaleString("msgCompleteUserName"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getUserEmail}  " + paramRequest.getLocaleString("msgUserEmail"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDirectoryName} " + paramRequest.getLocaleString("msgDirName"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getFileName}   " + paramRequest.getLocaleString("msgFileName"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocTitle}   " + paramRequest.getLocaleString("msgDocTitle"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocDesc}    " + paramRequest.getLocaleString("msgDocDescription"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocVersion} " + paramRequest.getLocaleString("msgDocVersion"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getDocLink} " + paramRequest.getLocaleString("msgDocLink"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getLastUpdate} " + paramRequest.getLocaleString("msgLastDateModification"));
            out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{getComments}   " + paramRequest.getLocaleString("msgModificationComment"));
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
                out.println("<script language=javascript>");
                out.println("      document.myForm.ver.value=\"" + strView + "\"");
                out.println("      document.myForm.modificar.value=\"" + strModify + "\"");
                out.println("      document.myForm.administrar.value=\"" + strAdmin + "\"");
                out.println("</script>");
            }
        } else if (accion.equals("showold")) {
            out.println("");
//            try {
//
//                //out.println(paramRequest.getLocaleString("msgFileRemovePermanently") + ":</td>");
//
//                SWBResourceURL urlShowOld = paramRequest.getActionUrl();
//                urlShowOld.setMode(paramRequest.Mode_ADMIN);
//                urlShowOld.setAction("ShowOld");
//
//                out.println("<form action=\"" + urlShowOld.toString() + "\" name=\"frmdelfile\" method=POST><input type=\"hidden\" name=\"cmd\" value=\"del_file\">");
//                out.println("<input type=\"hidden\" name=\"tm\" value=\"" + strTopicMap + "\"><input type=\"hidden\" name=\"topic\" value=\"" + strTopic + "\"><input type=\"hidden\" name=\"ifile\" value=\"\">");
//                out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
//                out.println("<tr class=tabla><td>" + paramRequest.getLocaleString("msgTHFolder") + "</td><td>" + paramRequest.getLocaleString("msgTHFileName") + "</td><td>" + paramRequest.getLocaleString("msgTHCreated") + "</td><td>" + paramRequest.getLocaleString("msgTHEmialUser") + "</td><td>" + paramRequest.getLocaleString("msgTHAction") + "</td></tr>");
//                String rowColor = "";
//                boolean cambiaColor = true;
//                while (rs.next()) {
//                    String str_doc = rs.getString("rep_docId");
//                    String str_topic = rs.getString("topic");
//                    String str_tm = rs.getString("idtm");
//                    String repemail = rs.getString("rep_email");
//                    String reptitle = rs.getString("rep_title");
//                    Timestamp repcreate = rs.getTimestamp("rep_create");
//
//                    rowColor = "#EFEDEC";
//                    if (!cambiaColor) {
//                        rowColor = "#FFFFFF";
//                    }
//                    cambiaColor = !(cambiaColor);
//                    out.println("<tr bgcolor=\"" + rowColor + "\">");
//                    String temp = str_topic;
//                    String tmsid = str_tm;
//                    String tpsid = str_topic;
//                    String nameFolder = "";
//                    String folderborrado = "";
//                    String idSub = "";
//                    int intDeleted = 0;
//                    TopicMap tpMAP = TopicMgr.getInstance().getTopicMap(tmsid);
//
//                    Topic tpSubDir = tpMAP.getTopic(tpsid, true);
//                    if (tpSubDir != null) {
//                        if (tpSubDir.getDbdata().getDeleted() == 1) {
//                            folderborrado = "<font size=1 color=red>&lt;folder eliminado&gt;</font>";
//                            intDeleted = 1;
//                            idSub = tpSubDir.getId();
//                        }
//                        nameFolder = tpSubDir.getDisplayName(user.getLanguage()) + folderborrado;
//                    } else {
//                        tpSubDir = tpMAP.getTopic(tpsid, false);
//                        nameFolder = tpSubDir.getDisplayName(user.getLanguage());
//                    }
//
//                    out.println("<td class=datos>" + nameFolder + "</td>");
//                    out.println("<td class=datos>" + reptitle + "</td>");
//                    out.println("<td class=datos>" + WBUtils.dateFormat(repcreate) + "</td>");
//                    out.println("<td class=datos><a href=\"mailto:" + DBUser.getInstance().getUserById(repemail).getEmail() + "\">" + DBUser.getInstance().getUserById(repemail).getEmail() + "</a></td>");
//
//                    out.println("<td class=datos>");
//                    if (canRecover(str_tm, str_topic)) {
//                        out.println("<a class=link href=\"javascript: DoRecover(" + str_doc + "," + intDeleted + ",'" + idSub + "');\">" + "<img src=\"" + WBUtils.getInstance().getWebPath() + "wbadmin/images/recover.gif\" border=0 title=\"" + paramRequest.getLocaleString("msgAltRestore") + "\" >" + "</a>");
//                    } else {
//                        out.println("<img src=\"" + WBUtils.getInstance().getWebPath() + "wbadmin/images/recover.gif\" border=0 title=\"" + paramRequest.getLocaleString("msgAltRestore") + "\" >");
//                    }
//                    out.println("&nbsp;<a class=link href=\"javascript: DoConfirm(" + str_doc + ");\">" + "<img src=\"" + WBUtils.getInstance().getWebPath() + "wbadmin/images/trash_vacio.gif\" border=0 title=\"" + paramRequest.getLocaleString("msgAltEliminate") + "\" >" + "</a>");
//                    out.println("</tr>");
//                }
//
//                out.println("<tr>");
//                out.println("<td colspan=\"5\" align=\"right\"><HR size=1 noshade> ");
//                out.println("<input type=button class=boton name=btnCancel onClick=\"javascript: DoRedir();\" value=" + paramRequest.getLocaleString("msgBTNCancel") + " >");
//                out.println("</td>");
//                out.println("</tr>");
//                out.println("</table>");
//                out.println("</form>");
//                SWBResourceURL urlEdit = paramRequest.getRenderUrl();
//                urlEdit.setMode(paramRequest.Mode_ADMIN);
//                urlEdit.setAction("edit");
//
//                out.println("<form name=\"frmredir\" action=\"" + urlEdit.toString() + "\" method=\"post\">");
//                out.println("<input type=\"hidden\" name=\"tm\" value=\"" + strTopicMap + "\"><input type=\"hidden\" name=\"topic\" value=\"" + strTopic + "\">");
//                out.println("</form>");
//                out.println("<form name=\"frmrestore\" action=\"" + urlShowOld.toString() + "\" method=\"post\">");
//                out.println("<input type=\"hidden\" name=\"tm\" value=\"" + strTopicMap + "\"><input type=\"hidden\" name=\"topic\" value=\"" + strTopic + "\"><input type=\"hidden\" name=\"docid\" value=\"\"><input type=\"hidden\" name=\"resdir\" value=\"\">");
//                out.println("<input type=\"hidden\" name=\"idSub\" value=\"\">");
//                out.println("</form>");
//                out.println("<script language=javascript>");
//                out.println("  function DoRedir(){");
//                out.println("      window.document.frmredir.submit();");
//                out.println("  }");
//                out.println("  function DoConfirm(erase){");
//                out.println("      if(confirm('" + paramRequest.getLocaleString("msgAlertSelFilesRemoved") + "!')){");
//                out.println("          window.document.frmdelfile.ifile.value=erase;");
//                out.println("          window.document.frmdelfile.submit();");
//                out.println("      }");
//                out.println("  }");
//                out.println("  function DoRecover(ivar,f_erased,id_sub){");
//
//                out.println("      if(f_erased=='1') ");
//                out.println("       {");
//                out.println("           if(confirm('" + "Si recuperas este archivo se recuperara el folder. \\n \\n ¿Estás seguro de querer hacerlo?" + "')) ");
//                out.println("             {");
//                out.println("                  window.document.frmrestore.resdir.value = 1;");
//                out.println("                  window.document.frmrestore.idSub.value = id_sub;");
//                out.println("                  window.document.frmrestore.docid.value = ivar;");
//                out.println("                  window.document.frmrestore.submit();");
//                out.println("             }");
//                out.println("           else");
//                out.println("            {");
//                out.println("                  return;");
//                out.println("            }");
//                out.println("       }");
//                out.println("      else");
//                out.println("       {");
//                out.println("           window.document.frmrestore.resdir.value = 0;");
//                out.println("           window.document.frmrestore.idSub.value = 0;");
//                out.println("           window.document.frmrestore.docid.value = ivar;");
//                out.println("           window.document.frmrestore.submit();");
//                out.println("        }");
//                out.println("      ");
//                out.println("      ");
//                out.println("  }");
//                out.println("</script>");
//
//            } catch (Exception e) {
//                log.error("Error in method Repository.doAdmin() when action is ShowOld, resource is " + getResourceBase().getId(), e);
//            }
        }
        out.println("</div>");
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        //Resource base = getResourceBase();
        String id = request.getParameter("suri");
        String repNS = request.getParameter("repNS");
        if (null == action) {
            action = "";
        }
        WebSite ws = response.getTopic().getWebSite();
        User user = response.getUser();

        Session session = null;
        Credentials credentials = new SWBCredentials(user);
        System.out.println("ProcessAction:" + action);
        if ("admin_update".equals(action)) {
            String viewrole = request.getParameter("ver");
            String modifyrole = request.getParameter("modificar");
            String adminrole = request.getParameter("administrar");
            String notifynew = request.getParameter("notificationcreate");
            String nofiryupdate = request.getParameter("notificationupdate");
            String notifyremove = request.getParameter("notificationremove");
            boolean usefolders = request.getParameter("showdirectory") != null && request.getParameter("showdirectory").equals("1") ? true : false;

            setSee(viewrole);
            setModify(modifyrole);
            setAdmin(adminrole);
            setMsgcrated(notifynew);
            setMsgupdated(nofiryupdate);
            setMsgdeleted(notifyremove);
            setUseFolders(usefolders);

            response.setMode(response.Mode_ADMIN);

        } else if ("admin_recover".equals(action)) //recuperación de archivo eliminado
        {
            response.setMode(response.Mode_ADMIN);
            response.setAction("recover");
        } else if ("admin_delete".equals(action)) //eliminación completa del archivo previa/ marcado como eliminado
        {
            response.setMode(response.Mode_ADMIN);
            response.setAction("recover");
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
                Node nodePage = session.getNodeByUUID(parentUUID);//nodeRep.getNode(response.getTopic().getId());//nombre id_pagina

                //Agregando nodo de tipo file
                Node nodeFile = nodePage.addNode(filename, REP_FILE);
                nodeFile.setProperty("swb:title", title);
                nodeFile.setProperty("swb:description", description);
                nodeFile.setProperty("swbfilerep:deleted", Boolean.FALSE);

                Node nodeRes = nodeFile.addNode(JCR_CONTENT, "swbfilerep:RepositoryResource");
                nodeRes.setProperty("jcr:data", new ByteArrayInputStream(bcont));
                nodeRes.addMixin("mix:versionable");
                nodeRes.setProperty("jcr:encoding", "");
                nodeRes.setProperty("jcr:lastModified", cal);
                nodeRes.setProperty("jcr:mimeType", fup.getContentType());
                nodeRes.setProperty("swbfilerep:comment", "Nuevo");
                nodeRes.setProperty("swbfilerep:userid", user.getId());
                nodeRes.setProperty("swbfilerep:filesize", bcont.length);

                nodePage.save();

                Version version = nodeRes.checkin();

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
            System.out.println("User id: " + (user != null ? user.getId() : "anónimo"));
            String pUUID = request.getParameter("parentUUID");
            if (pUUID == null) {
                pUUID = resUUID;
            }
            try {
                session = rep.login(credentials, repNS);
                Node nodePage = session.getNodeByUUID(pUUID); //nodeRep.getNode(response.getTopic().getId());//nombre id_pagina
                Node nodeFolder = nodePage.addNode(REP_FOLDER, REP_FOLDER);
                nodeFolder.setProperty("swb:title", strTitle);
                nodeFolder.setProperty("swb:description", strDescription);
                nodeFolder.setProperty("swbfilerep:userid", user.getId());
                nodeFolder.setProperty("swbfilerep:deleted", Boolean.FALSE);
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
            System.out.println("inicio:" + System.currentTimeMillis());
            String UUID = request.getParameter("uuid");
            try {
                session = rep.login(credentials, repNS);
                Node fnode = session.getNodeByUUID(UUID);
                fnode.setProperty("swbfilerep:deleted", Boolean.TRUE);
                fnode.save();
            } catch (Exception e) {
                log.error("Error al eliminar el archivo processAction.removeFile", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }
            response.setMode(SWBActionResponse.Mode_VIEW);
            System.out.println("fin:" + System.currentTimeMillis());

        } else if ("removefolder".equals(action)) // marcar folder como eliminado
        {
            String UUID = request.getParameter("uuid");
            try {
                session = rep.login(credentials, repNS);
                Node fnode = session.getNodeByUUID(UUID);
                fnode.setProperty("swbfilerep:deleted", Boolean.TRUE);
                NodeIterator nit = fnode.getNodes();
                while (nit.hasNext()) {
                    Node nodo = nit.nextNode();
                    nodo.setProperty("swbfilerep:deleted", Boolean.TRUE);
                }
                fnode.save();
            } catch (Exception e) {
                log.error("Error al eliminar el folder processAction.removeFolder", e);
            } finally {
                if (session != null) {
                    session.logout();
                }
            }
        } else if ("updatefile".equals(action)) {
        } else if ("updatefolder".equals(action)) {
        }
        if (null != id) {
            response.setRenderParameter("suri", id);
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        super.doEdit(request, response, paramRequest);
    }

    public void doGetFile(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        User user = paramRequest.getUser();
        String uuid = request.getParameter("uuid");
        String ns = request.getParameter("repNS");
        String action = paramRequest.getAction();
        Credentials credentials = new SWBCredentials(user);
        Session session = null;
        try {

            session = rep.login(credentials, ns);
            Node nodo = session.getNodeByUUID(uuid);
            String filename = nodo.getName();
            String vernum = getLastVersionOfcontent(session, ns, uuid);
            String str_file = getContentFile(session, ns, uuid, vernum);
            Node nc = nodo.getNode(JCR_CONTENT);
            String mime = null;
            if (nc != null) {
                try {
                    mime = nc.getProperty("jcr:mimeType").getString();
                } catch (Exception e) {
                    mime = DEFAULT_MIME_TYPE;
                }
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

        if (level == 0) {
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
        }

        if (level == 0) {
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
        WebSite ws = paramRequest.getTopic().getWebSite();
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
            if (version.equals("*")) {
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
}


