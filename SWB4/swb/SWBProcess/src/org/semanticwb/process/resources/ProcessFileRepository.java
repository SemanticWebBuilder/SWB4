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
package org.semanticwb.process.resources;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Role;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.ItemAwareStatus;
import org.semanticwb.process.model.RepositoryDirectory;
import org.semanticwb.process.model.RepositoryElement;
import org.semanticwb.process.model.RepositoryFile;
import org.semanticwb.process.model.RepositoryURL;

/**
 *
 * @author juan.fernandez
 */
public class ProcessFileRepository extends GenericResource {

    private Logger log = SWBUtils.getLogger(ProcessFileRepository.class);
    private SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yy hh:mm");
    private static final String MODE_GETFILE = "getFile";
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    private static final String LVL_VIEW = "prop_view";
    private static final String LVL_MODIFY = "prop_modify";
    private static final String LVL_ADMIN = "prop_admin";
    private static final String VALID_FILES = "prop_valid_files";

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals(MODE_GETFILE)) {
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

        String path = SWBPlatform.getContextPath() + "/swbadmin/images/repositoryfile/";
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        User usr = paramRequest.getUser();
        String lang = usr.getLanguage();
        String back=request.getParameter("back");

        int luser = getLevelUser(usr);


        WebPage wp = paramRequest.getWebPage();
        WebSite wsite = wp.getWebSite();

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        RepositoryDirectory repoDir = (RepositoryDirectory) paramRequest.getWebPage();

        String action = request.getParameter("act");
        if (null == action) {
            action = "";
        }

        if ("".equals(action)) {


            //urlorder.setParameter("parentUUID", parentUUID);

            String ugpop = "";
            String usrgpo_filter = request.getParameter("usrgpo_filter");
            SWBResourceURL urlorder = paramRequest.getRenderUrl();
            if (usrgpo_filter != null) {
                ugpop = "&usrgpo_filter=" + usrgpo_filter;
            }
            if (null != usr) {

                out.println("<div class=\"bandeja-combo\">");
                out.println("<ul>");
                out.println("<li>");
                //out.println("<form>");
                SWBResourceURL urlfilter = paramRequest.getRenderUrl();
                urlfilter.setParameter("suri", suri);

                out.println("<select id=\"usrgpo_filter\" name=\"usrgpo_filter\" onchange=\"window.location='" + urlfilter + "?usrgpo_filter='+this.value;;\">");
                out.println("<option value=\"\"></option>");
                String strSelected = "";
                Iterator<UserGroup> itug = usr.getUserRepository().listUserGroups();
                while (itug.hasNext()) {
                    UserGroup userGroup = itug.next();
                    strSelected = "";
                    if (usrgpo_filter != null && usrgpo_filter.equals(userGroup.getId())) {
                        strSelected = "selected";
                    }
                    out.println("<option value=\"" + userGroup.getId() + "\" " + strSelected + " >" + userGroup.getDisplayTitle(usr.getLanguage()) + "</option>");
                }
                out.println("</select>");
                out.println("</li>");
                out.println("<li>");
                out.println("<button type=\"button\" onclick=\"window.location='" + urlfilter + "?usrgpo_filter='+document.getElementById('usrgpo_filter').value;\">Filtrar por área</button>");
                out.println("</li>");
                out.println("</ul>");
                //out.println("</form>");
                out.println("</div>");
            }


            out.println("<div id=\"ProcessFileRepository\">");
            out.println("<table class=\"tabla-bandeja\"");
            out.println("<thead>");

            out.println("<tr>");
            out.println("<th class=\"tban-id\">");
            out.println("Id");
            out.println("</th>");
            out.println("<th class=\"tban-tarea\">");
            out.println("<a style=\"color:white; text-decoration:'none';\" href=\"" + urlorder + "?orderBy=type" + ugpop + "\" title=\"Ordenar por tipo\">" + "Tipo" + "</a>");
            out.println("</th>");
            out.println("<th class=\"tban-tarea\">");
            out.println("<a style=\"color:white; text-decoration:'none';\" href=\"" + urlorder + "?orderBy=title" + ugpop + "\" title=\"Ordenar por nombre\">" + "Nombre" + "</a>");
            out.println("</th>");
            out.println("<th class=\"tban-tarea\">");
            out.println("Versión");
            out.println("</th>");
            out.println("<th class=\"tban-tarea\">");
            out.println("<a style=\"color:white; text-decoration:'none';\" href=\"" + urlorder + "?orderBy=date" + ugpop + "\" title=\"Ordenar por fecha de modificación\">" + "Modificado" + "</a>");
            out.println("</th>");
            out.println("<th class=\"tban-tarea\">");
            out.println("<a style=\"color:white; text-decoration:'none';\" href=\"" + urlorder + "?orderBy=usr" + ugpop + "\" title=\"Ordenar por usuario que lo modificó.\">" + "Modificado por" + "</a>");
            out.println("</th>");
            out.println("<th class=\"tban-tarea\">");
            out.println("<a style=\"color:white; text-decoration:'none';\" href=\"" + urlorder + "?orderBy=gpousr" + ugpop + "\" title=\"Ordenar por área de usuario.\">" + "Área" + "</a>");
            out.println("</th>");
            out.println("<th class=\"tban-tarea\">");
            out.println("<a style=\"color:white; text-decoration:'none';\" href=\"" + urlorder + "?orderBy=status" + ugpop + "\" title=\"Ordenar por estatus.\">" + "Estatus" + "</a>");
            out.println("</th>");
            out.println("<th class=\"tban-id\">");
            out.println("Acción");
            out.println("</th>");
            out.println("</tr>");
            out.println("</thead>");

            out.println("<tbody>");
            Iterator<RepositoryFile> itrf = repoDir.listRepositoryElements();

            ///// ORDENADO DE ARCHIVOS SEGUN OPCIÓN

            String orderBy = request.getParameter("orderBy");
            if (null == orderBy) {
                orderBy = "title";
            }

            HashMap<String, RepositoryElement> hmNodes = new HashMap<String, RepositoryElement>();

            while (itrf.hasNext()) {
                RepositoryElement repoFile = itrf.next();

                VersionInfo version = repoFile.getActualVersion();
                if (version == null) {
                    continue;
                }
                String skey = repoFile.getId();

                if (orderBy.equals("title")) {
                    skey = repoFile.getDisplayTitle(lang) + " - " + repoFile.getId();

                } else if (orderBy.equals("date")) {
                    //nodo.getProperty("jcr:created").getDate().getTime())
                    skey = version.getCreated().getTime() + " - " + repoFile.getDisplayTitle(lang) + " - " + repoFile.getId();

                } else if (orderBy.equals("type")) {
                    String file = version.getVersionFile();
                    String type = getFileName(file);

                    skey = type + "-" + repoFile.getDisplayTitle(lang) + " - " + repoFile.getId();
                    //hmNodes.put(skey, repoFile);
                } else if (orderBy.equals("usr")) {
                    User usrc = version.getCreator();

                    skey = " - " + repoFile.getDisplayTitle(lang) + " - " + repoFile.getId();


                    if (usrc != null) {
                        skey = usrc.getFullName() + skey;
                    }
                } else if (orderBy.equals("gpousr")) {

                    if (repoFile.getOwnerUserGroup() == null) {
                        skey = " - " + " " + " - " + repoFile.getId();
                    } else {

                        skey = " - " + repoFile.getOwnerUserGroup().getDisplayTitle(lang) + " - " + repoFile.getId();
                    }

                } else if (orderBy.equals("status")) {

                    if (repoFile.getStatus() == null) {
                        skey = " - " + " " + " - " + repoFile.getId();
                    } else {

                        skey = " - " + repoFile.getStatus().getDisplayTitle(lang) + " - " + repoFile.getId();
                    }

                }
                hmNodes.put(skey, repoFile);
            }

            ArrayList list = new ArrayList(hmNodes.keySet());
            Collections.sort(list);


            //// TERMINA ORDENADO

            /// DESPLIEGUE DE ARCHIVOS ENCONTRADOS

            Iterator<String> lnit = list.iterator();
            while (lnit.hasNext()) {

                String skey = lnit.next();

                boolean showFile = Boolean.FALSE;
                RepositoryElement repositoryFile = hmNodes.get(skey);
                if (repositoryFile.getOwnerUserGroup() != null) {
                    UserGroup ugpo = repositoryFile.getOwnerUserGroup();
                    String ugid = null;
                    if (ugpo != null) {
                        ugid = ugpo.getId();
                    }
                    if (null != ugid && ugid.equals(usrgpo_filter) || usrgpo_filter == null || usrgpo_filter.equals("")) {
                        showFile = Boolean.TRUE;
                    }
                } else if (usrgpo_filter == null || usrgpo_filter.equals("")) {
                    showFile = Boolean.TRUE;
                }

                if (!showFile) {
                    continue;
                }
                out.println("<tr>");
                out.println("<td class=\"tban-id\">");
                String fid = repositoryFile.getId();
                out.println(fid);
                out.println("</td>");

                out.println("<td class=\"tban-tarea\">");
                SWBResourceURL urldetail = paramRequest.getRenderUrl();
                urldetail.setParameter("act", "detail");
                urldetail.setParameter("fid", repositoryFile.getURI());

                VersionInfo vi = repositoryFile.getLastVersion();

                if (vi == null) {
                    continue;
                }

                String file = "";
                String type = "";

                if (vi != null && vi.getVersionFile() != null) {
                    file = vi.getVersionFile();
                    type = getFileName(file);
                }

                if (luser > 0) {
                    if (repositoryFile instanceof RepositoryFile) {
                        SWBResourceURL urlview = paramRequest.getRenderUrl();
                        urlview.setCallMethod(SWBResourceURL.Call_DIRECT);
                        urlview.setParameter("fid", fid);
                        urlview.setMode(MODE_GETFILE);
                        urlview.setParameter("verNum", "" + vi.getVersionNumber());

                        out.println("<a href=\"" + urlview + "\">");
                        out.println("<img border=0 src='" + path + "" + type + "' alt=\"" + getFileType(file) + "\" />");
                        out.println("</a>");
                    } else if (repositoryFile instanceof RepositoryURL) {
                        out.println("<a target=\"_blank\" href=\"" + vi.getVersionFile() + "\">");
                        out.println("<img border=0 src='" + path + "" + type + "' alt=\"" + "Liga a archivo externo" + "\" />");
                        out.println("</a>");
                    }
                } else {
                    if (repositoryFile instanceof RepositoryFile) {
                        out.println("<img border=0 src='" + path + "" + type + "' alt=\"" + getFileType(file) + "\" />");
                    } else if (repositoryFile instanceof RepositoryURL) {
                        out.println("<img border=0 src='" + path + "" + type + "' alt=\"" + getFileType(vi.getVersionFile()) + "\" />");
                    }
                }

                out.println("</td>");
                out.println("<td class=\"tban-tarea\">");
                out.println(repositoryFile.getDisplayTitle(usr.getLanguage()));
                out.println("</td>");
                out.println("<td class=\"tban-tarea\">");
                out.println(vi.getVersionValue());
                out.println("</td>");
                out.println("<td class=\"tban-tarea\">");
                out.println(vi != null && vi.getUpdated() != null ? format.format(vi.getUpdated()) : "--");
                out.println("</td>");
                out.println("<td class=\"tban-tarea\">");
                out.println(vi != null && vi.getModifiedBy() != null && vi.getModifiedBy().getFullName() != null ? vi.getModifiedBy().getFullName() : "--");
                out.println("</td>");
                out.println("<td class=\"tban-tarea\">");
                out.println(repositoryFile.getOwnerUserGroup() != null ? repositoryFile.getOwnerUserGroup().getDisplayTitle(lang) : "--");
                out.println("</td>");
                out.println("<td class=\"tban-tarea\">");
                out.println(repositoryFile.getStatus() != null ? repositoryFile.getStatus().getDisplayTitle(lang) : "--");
                out.println("</td>");

                out.println("<td class=\"tban-accion\">");

                out.println("<a href=\"" + urldetail + "\"><img src=\"" + path + "info.gif\" border=\"0\" alt=\"ver detalle\"/>");

                out.println("</a>");

                if (luser == 3 || (vi.getCreator() != null && vi.getCreator().equals(usr) && luser > 1)) {
                    SWBResourceURL urlremove = paramRequest.getActionUrl();
                    urlremove.setAction("removefile");
                    urlremove.setParameter("act", "remove");
                    urlremove.setParameter("fid", repositoryFile.getURI());
                    
                    out.println("<a href=\"" + urlremove + "\" onclick=\"if (!confirm('" + paramRequest.getLocaleString("msgAlertConfirmRemoveFile") + " " + repositoryFile.getTitle() + "?')) return false;\">");
                    out.println("<img src=\"" + path + "delete.gif\" border=\"0\" alt=\"eliminar\"/>");
                    out.println("</a>");
                }

                out.println("</td>");

                out.println("</tr>");
            }
            
            out.println("</tbody>");
            out.println("<tfoot>");
            out.println("<tr>");
            out.println("<td colspan=\"9\">");

            if (luser >= 2) {
                SWBResourceURL urlnew = paramRequest.getRenderUrl();
                urlnew.setParameter("act", "new");
                out.println("<button onclick=\"window.location='" + urlnew + "';\">" + "Agregar archivo" + "</button>");
            }

            out.println("</td>");
            out.println("</tr>");
            out.println("</tfoot>");
            out.println("</table>");
            out.println("</div>");

        } else if ("detail".equals(action)) {
            String fid = request.getParameter("fid");

            RepositoryElement repoFile = null;//RepositoryElement.ClassMgr.getRepositoryElement(fid, repoDir.getProcessSite());
            GenericObject go = ont.getGenericObject(fid);

            if (go instanceof RepositoryFile) {
                repoFile = (RepositoryFile) go;
            } else {
                repoFile = (RepositoryURL) go;
            }

            out.println("<div id=\"ProcessFileRepository\">");
            out.println("<div class=\"bandeja-combo\">");
            out.println("<h2>" + repoFile.getDisplayTitle(usr.getLanguage()) + "</h2>");
            out.println("  <table class=\"tabla-bandeja\">");
            out.println("    <tbody>");
            out.println("      <tr>");
            out.println("        <td width=\"200\" align=\"right\">");
            out.println("          Descripción:");
            out.println("        </td>");
            out.println("        <td>");
            out.println(           repoFile.getDisplayDescription(usr.getLanguage()));
            out.println("        </td>");
            out.println("      </tr>");
            out.println("      <tr>");
            out.println("        <td align=\"right\">");
            out.println("          Área:");
            out.println("        </td>");
            out.println("        <td>");
            if (repoFile.getOwnerUserGroup() != null) {
                out.println(repoFile.getOwnerUserGroup().getDisplayTitle(usr.getLanguage()));
            } else {
                out.println("--");
            }
            out.println("        </td>");
            out.println("      </tr>");
            out.println("      <tr>");
            out.println("        <td align=\"right\">");
            out.println("          Estatus:");
            out.println("        </td>");
            out.println("        <td>");
            if (repoFile.getStatus() != null) {
                out.println(repoFile.getStatus().getDisplayTitle(usr.getLanguage()));
            } else {
                out.println("--");
            }
            out.println("        </td>");
            out.println("      </tr>");
            out.println("      <tr>");
            out.println("        <td align=\"right\">");
            out.println("          Archivo:");
            out.println("        </td>");
            out.println("        <td>");
            VersionInfo vl = repoFile.getLastVersion();
            if (luser > 0) {
                if (repoFile instanceof RepositoryFile) {
                    SWBResourceURL urlview = paramRequest.getRenderUrl();
                    urlview.setCallMethod(SWBResourceURL.Call_DIRECT);
                    urlview.setParameter("fid", repoFile.getId());
                    urlview.setMode(MODE_GETFILE);
                    urlview.setParameter("verNum", "" + vl.getVersionNumber());

                    out.println("<a href=\"" + urlview + "\">");
                    out.println(vl.getVersionFile());
                    out.println("</a>");
                } else if (repoFile instanceof RepositoryURL) {
                    out.println("<a target=\"_blank\" href=\"" + vl.getVersionFile() + "\">");
                    out.println("</a>");
                }
            } else {
                out.println(vl.getVersionFile());
            }
            out.println("        </td>");
            out.println("      </tr>");
            out.println("      <tr>");
            out.println("        <td align=\"right\">");
            out.println("          Versión:");
            out.println("        </td>");
            out.println("        <td>");
            out.println(vl.getVersionValue());

            SWBResourceURL urlverhistoy = paramRequest.getRenderUrl();
            urlverhistoy.setParameter("act", "history");
            urlverhistoy.setParameter("fid", repoFile.getURI());

            out.println("(<a href=\"" + urlverhistoy + "\">versiones</a>)");
            out.println("        </td>");
            out.println("      </tr>");

            if (luser == 3 || (vl.getCreator() != null && vl.getCreator().equals(usr) && luser > 1)) {
                out.println("      <tr>");
                out.println("        <td align=\"right\">");
                out.println("          Agregar nueva Versión:");
                out.println("        </td>");

                SWBResourceURL urlnewVer = paramRequest.getRenderUrl();

                out.println("<td>");
                out.println("<script type=\"text/javascript\">");
                out.println("function validaVersion(forma)");
                out.println("  {");
                out.println("       if(forma.newVersion.selectedValue!='0')");
                out.println("           return true;");
                out.println("       else return false;");
                out.println("  }");
                out.println("</script>");
                out.println("<form method=\"post\" action=\"" + urlnewVer + "\">");
                out.println("<input type=\"hidden\" name=\"act\" value=\"new\">");
                out.println("<input type=\"hidden\" name=\"fid\" value=\"" + fid + "\">");
                out.println("<select name=\"newVersion\" onsubmit=\"validaVersion(this.form);\">");
                out.println("<option value=\"0\">--</option>");

                float fver = Float.parseFloat(vl.getVersionValue());
                fver = fver + 0.1F;

                int iver = (int) fver;
                iver = iver + 1;

                out.println("<option value=\"fraction\">" + fver + "</option>");
                out.println("<option value=\"nextInt\">" + (float) iver + "</option>");


                out.println("</select>");
                out.println("<button type=\"submit\">Agregar</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");

            }
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Creado:");
            out.println("</td>");
            out.println("<td>");
            out.println(vl.getCreated() != null ? format.format(vl.getCreated()) : "--");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Usuario Creador:");
            out.println("</td>");
            out.println("<td>");
            out.println(vl.getCreator() != null ? vl.getCreator().getFullName() : "--");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tbody>");
            //out.println("<tfoot>");
            //out.println("<tr>");
            //out.println("<td colspan=\"2\" align=\"right\">");
            SWBResourceURL urlbck = paramRequest.getRenderUrl();
            urlbck.setParameter("act", "");
            
//            if (back != null && back.equals("history")) {
//                out.println("<input type=\"button\" value=\"Regresar\" onclick=\"history.go(-1)\"/>");
//            } else {
//                out.println("<button onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
//            }
            //out.println("</td>");
            //out.println("</tr>");
            out.println("</tbody>");
            out.println("</table>");
            if (back != null && back.equals("history")) {
                out.println("<br><input type=\"button\" value=\"Regresar\" onclick=\"history.go(-1)\"/>");
            } else {
                out.println("<br><button onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
            }
            out.println("</div>");
            out.println("</div>");
        } else if ("history".equals(action)) {
            String fid = request.getParameter("fid");
            RepositoryElement repoFile = null;
            GenericObject go = ont.getGenericObject(fid);

            if (go instanceof RepositoryFile) {
                repoFile = (RepositoryFile) go;
            } else {
                repoFile = (RepositoryURL) go;
            }

            VersionInfo ver = null;
            VersionInfo vl = repoFile.getLastVersion();
            if (null != vl) {
                ver = vl;
                while (ver.getPreviousVersion() != null) { //
                    ver = ver.getPreviousVersion();
                }
            }
            if (ver != null) {
                out.println("  <div id=\"ProcessFileRepository\">");
                out.println("    <div class=\"bandeja-combo\">");
                out.println("      <h2>" + repoFile.getDisplayTitle(usr.getLanguage()) + "</h2>");
                out.println("      <table width=\"100%\" class=\"tabla-bandeja\">");
                out.println("        <tbody>");
                out.println("          <tr>");
                out.println("            <td width=\"200\" align=\"right\">Descripción:</td>");
                out.println("            <td>" + repoFile.getDisplayDescription(usr.getLanguage()) + "</td>");
                out.println("          </tr>");
                out.println("          <tr>");
                out.println("            <td align=\"right\">Área:</td>");
                out.println("            <td>");
                if (repoFile.getOwnerUserGroup() != null) {
                    out.println(repoFile.getOwnerUserGroup().getDisplayTitle(usr.getLanguage()));
                } else {
                    out.println("--");
                }
                out.println("            </td>");
                out.println("          </tr>");
                out.println("          <tr>");
                out.println("            <td align=\"right\">Estatus:</td>");
                out.println("            <td>");
                if (repoFile.getStatus() != null) {
                    out.println(repoFile.getStatus().getDisplayTitle(usr.getLanguage()));
                } else {
                    out.println("--");
                }
                out.println("            </td>");
                out.println("          </tr>");
                out.println("          <tr>");
                out.println("            <td align=\"right\">Archivo:</td>");
                out.println("            <td>");
                out.println(ver.getVersionFile());
                out.println("            </td>");
                out.println("          </tr>");
                out.println("        </tbody>");
                out.println("      </table>");
                out.println("      <br/>");

                out.println("      <table class=\"tabla-bandeja\">");
                out.println("        <thead>");
                out.println("          <tr>");
                out.println("            <th>Versión</th>");
                out.println("            <th>Fecha versión</th>");
                out.println("            <th>Creado por</th>");
                out.println("            <th>Comentario</th>");
                out.println("            <th>Estatus</th>");
                out.println("          </tr>");
                out.println("        </thead>");
                out.println("        <tbody>");

                //out.println("</thead>");
                //out.println("<tbody>");
                while (ver != null) {
                    //lista de las versiones del archivo

                    out.println("          <tr>");
                    //out.println("            <td align=\"center\" >");
                    String viewLink = "";
                    if (luser > 0) {
                        if (repoFile instanceof RepositoryFile) {
                            SWBResourceURL urlview = paramRequest.getRenderUrl();
                            urlview.setCallMethod(SWBResourceURL.Call_DIRECT);
                            urlview.setParameter("fid", repoFile.getId());
                            urlview.setMode(MODE_GETFILE);
                            urlview.setParameter("verNum", "" + ver.getVersionNumber());

                            viewLink = "<a href=\"" + urlview + "\">" + ver.getVersionValue() + "</a>";
                        } else if (repoFile instanceof RepositoryURL) {
                            viewLink = "<a target=\"_blank\" href=\"" + ver.getVersionFile() + "\">" + ver.getVersionValue() + "</a>";
                        }
                    } else {
                        out.println("---");
                    }
                    //out.println("</td>");
                    out.println("            <td align=\"center\">" + viewLink +"</td>");
                    out.println("            <td align=\"center\">");
                    out.println(ver.getCreated() != null ? format.format(ver.getCreated()) : "--");
                    out.println("            </td>");
                    out.println("            <td align=\"center\">");
                    out.println(ver.getCreator() != null ? ver.getCreator().getFullName() : "--");
                    out.println("            </td>");
                    out.println("            <td align=\"center\">");
                    out.println(ver.getVersionComment() != null ? ver.getVersionComment() : "--");
                    out.println("            </td>");
                    out.println("            <td align=\"center\">");
                    String propStatus = ver.getProperty("status", "--");
                    ItemAwareStatus itemStat = ItemAwareStatus.ClassMgr.getItemAwareStatus(propStatus, wsite);
                    out.println(itemStat!=null?itemStat.getDisplayTitle(lang):"--");
                    out.println("            </td>");
                    out.println("          </tr>");

                    ver = ver.getNextVersion();

                }
                out.println("        </tbody>");
                //out.println("<tfoot>");
                //out.println("<tr>");
                //out.println("<td colspan=\"2\" align=\"right\">");
                SWBResourceURL urlbck = paramRequest.getRenderUrl();
                urlbck.setParameter("act", "detail");
                urlbck.setParameter("fid", fid);
                //out.println("<button onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
                //out.println("</td>");
                //out.println("</tr>");
                //out.println("</tfoot>");
                out.println("      </table>");
                out.println("      <br><button onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
                out.println("    </div>");
                out.println("  </div>");
            }

        } else if ("new".equals(action)) {

            SWBResourceURL urlnew = paramRequest.getActionUrl();
            urlnew.setAction("newfile");
            urlnew.setParameter("act", "newfile");

            String fid = request.getParameter("fid");
            String newVersion = request.getParameter("newVersion");
            String sNxtVersion = "";
            RepositoryElement repoFile = null;
            String stitle = "";
            String sdescription = "";
            String slink = "";
            String actualStatus = "";
            boolean isFile = false;
            if (null != fid && null != newVersion) {
                boolean incremento = Boolean.FALSE;
                if (newVersion.equals("nextInt")) {
                    incremento = Boolean.TRUE;
                }

                GenericObject go = ont.getGenericObject(fid);
                if (go instanceof RepositoryFile) {
                    repoFile = (RepositoryFile) go;
                    isFile = true;
                } else {
                    repoFile = (RepositoryURL) go;
                }

                stitle = repoFile.getDisplayTitle(usr.getLanguage());
                sdescription = repoFile.getDisplayDescription(usr.getLanguage());

                actualStatus=repoFile.getStatus()!=null?repoFile.getStatus().getDisplayTitle(lang):"";
                
                VersionInfo vl = repoFile.getLastVersion();

                slink = vl.getVersionFile();

                float fver = Float.parseFloat(vl.getVersionValue());
                fver = fver + 0.1F;

                int iver = (int) fver;
                iver = iver + 1;

                sNxtVersion = "" + fver;

                if (incremento) {
                    sNxtVersion = "" + (float) iver;
                }
            }

            String validFiles = getResourceBase().getAttribute(VALID_FILES, "");

            out.println("<script type=\"text/javascript\" >");

            out.println("function Checkfiles(pExt) ");
            out.println("{ ");
            out.println("    var ftit = document.getElementById('ftitle'); ");
            out.println("    var ftval = ftit.value; ");
            out.println("    ftval = ftval.replace(' ' , ''); ");
            out.println("    if(ftval.length==0) { ");
            out.println("        alert('Falta poner el titulo del archivo.'); ");
            out.println("        ftit.focus(); ");
            out.println("        return false; ");
            out.println("    } ");
            out.println("     ");
            out.println("    var fdesc = document.getElementById('fdescription'); ");
            out.println("    ftval = fdesc.value; ");
            out.println("    ftval = ftval.replace(' ' , ''); ");
            out.println("    if(ftval.length==0) { ");
            out.println("        alert('Falta poner la descripcion del archivo.'); ");
            out.println("        fdesc.focus(); ");
            out.println("        return false; ");
            out.println("    } ");
            out.println("    var ftype = document.getElementById('hftype'); ");
            out.println("    if(ftype.value=='url') {");
            out.println("        var urlfilec = document.getElementById('extfile');");
            out.println("        var urlfile = urlfilec.value;");
            out.println("        urlfile = urlfile.replace(' ' , ''); ");
            out.println("        if(urlfile.length==0) { ");
            out.println("            alert('Falta poner la liga del archivo externo.'); ");
            out.println("            urlfilec.focus(); ");
            out.println("            return false; ");
            out.println("        } else { return true; } ");
            out.println("    } else {");
            out.println("    var fup = document.getElementById('ffile'); ");
            out.println("    var fileName = fup.value; ");
            out.println("     ");
            out.println("    if(fileName.length == 0)  ");
            out.println("    { ");
            out.println("        alert('Falta seleccionar el archivo.'); ");
            out.println("        fup.focus(); ");
            out.println("        return false; ");
            out.println("    }  ");
            out.println("     ");
            out.println("    if(isFileType(fileName, pExt)) ");
            out.println("    { ");
            out.println("        return true; ");
            out.println("    }  ");
            out.println("    else ");
            out.println("    { ");
            out.println("        var ptemp = pExt; ");
            out.println("        ptemp = ptemp.replace('|',','); ");
            out.println("        alert('Selecciona archivos de tipo '+ptemp+' unicamente.'); ");
            out.println("        fup.focus(); ");
            out.println("        return false; ");
            out.println("    } ");
            out.println("  } ");
            out.println("} ");
            out.println(" ");
            out.println("function isFileType(pFile, pExt)  ");
            out.println("{  ");
            out.println("    if(pFile.length > 0 && pExt.length > 0)  ");
            out.println("    {  ");
            out.println("        var swFormat=pExt + '|';  ");
            out.println("        sExt=pFile.substring(pFile.indexOf('.')).toLowerCase();  ");
            out.println("        var sType='';  ");
            out.println("        while(swFormat.length > 0 )  ");
            out.println("        {  ");
            out.println("            sType= swFormat.substring(0, swFormat.indexOf('|'));  ");
            out.println("            if(sExt.indexOf(sType)!=-1) return true;  ");
            out.println("            swFormat=swFormat.substring(swFormat.indexOf('|')+1);  ");
            out.println("        }  ");
            out.println("        while(pExt.indexOf('|')!=-1) pExt=pExt.replace('|',',');  ");
            out.println("        return false;  ");
            out.println("    }  ");
            out.println("    else return true;  ");
            out.println("} ");

            out.println("function selectType(forma,val)  ");
            out.println("{  ");
            out.println("    if(val=='url')  ");
            out.println("    {  ");
            out.println("        forma.hftype.value=val;  ");
            out.println("        forma.extfile.disabled=false;  ");
            out.println("        forma.ffile.disabled=true;  ");
            out.println("    }  ");
            out.println("    else  ");
            out.println("    {  ");
            out.println("        forma.hftype.value=val;  ");
            out.println("        forma.extfile.disabled=true;  ");
            out.println("        forma.ffile.disabled=false;  ");
            out.println("    }  ");
            out.println("} ");


            out.println("</script>"); //        

            out.println("<div id=\"ProcessFileRepository\">");
            out.println("<div class=\"bandeja-combo\">");
            out.println("<h2>Agregar archivo al repositorio</h2>");
            out.println("<form dojoType=\"dijit.form.Form\" method=\"post\" action=\"" + urlnew + "\"  enctype=\"multipart/form-data\" >");

            if (null != fid && null != newVersion) {
                out.println("<input type=\"hidden\" name=\"newVersion\" value=\"" + newVersion + "\">");
                out.println("<input type=\"hidden\" name=\"fid\" value=\"" + fid + "\">");
            }

            out.println("<table class=\"tabla-bandeja\">");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<td width=\"200\" align=\"right\">");
            out.println("Título:");
            out.println("</td>");
            out.println("<td>");
            
            out.println("<input name=\"ftitle\" id=\"ftitle\" size=\"30\" value=\"" + stitle + "\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura el título.\"  trim=\"true\"  style=\"width:300px;\" />");

            //out.println("<input type=\"text\" name=\"ftitle\" id=\"ftitle\" value=\"" + stitle + "\">");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Descripción:");
            out.println("</td>");
            out.println("<td>");
            //out.println("<textarea name=\"fdescription\" id=\"fdescription\" size=\"30\" value=\"" + sdescription + "\" dojoType=\"dijit.form.ValidationTextBox\" editor=\"dijit.form.Textarea\"  required=\"true\" promptMessage=\"Captura la descripción.\"  trim=\"true\"  style=\"width:300px;\" />");
            out.println("<textarea name=\"fdescription\" id=\"fdescription\" dojoType=\"dijit.form.Textarea\" required=\"true\" promptMessage=\"Captura la descripción.\" trim=\"true\" style=\"width:300px;\">" + sdescription + "</textarea>");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Comentario:");
            out.println("</td>");
            out.println("<td>");
            //out.println("<input name=\"fcomment\" id=\"fcomment\" size=\"30\" value=\"\" dojoType=\"dijit.form.ValidationTextBox\" editor=\"dijit.form.Textarea\" promptMessage=\"Captura el comentario.\"  style=\"width:300px;\" />");
            out.println("<textarea name=\"fcomment\" dojoType=\"dijit.form.Textarea\" style=\"width:300px;\" promptMessage=\"Captura el comentario.\"></textarea>");
            out.println("</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Estatus:");
            out.println("</td>");
            out.println("<td>");

            out.println("<select name=\"itemAwStatus\" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" style=\"width:300px;\" promptMessage=\"Selecciona estatus\" >");
            out.println("<option value=\"\" "+(actualStatus.equals("")?"selected":"") +">&nbsp;</option>");
            Iterator<ItemAwareStatus> ititwstst = ItemAwareStatus.ClassMgr.listItemAwareStatuses(wsite);
            while (ititwstst.hasNext()) {
                ItemAwareStatus itemAwareStatus = ititwstst.next();
                out.println("<option value=\""+itemAwareStatus.getId()+"\" "+(actualStatus.equals(itemAwareStatus.getId())?"selected":"") +">"+itemAwareStatus.getDisplayTitle(lang) +"</option>");
            }

            out.println("</select>");
            out.println("</td>");
            out.println("</tr>");

            if (null != fid && null != newVersion) {

                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Versión del archivo a agregar:");
                out.println("</td>");
                out.println("<td>");
                out.println(sNxtVersion);
                out.println("</td>");
                out.println("</tr>");
            }

            if (null == fid && null == newVersion) {
                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Tipo de Archivo a utilizar:");
                out.println("</td>");
                out.println("<td>");
                out.println("<input id=\"hftype\" type=\"hidden\" name=\"hftype\" value=\"url\">");
                out.println("<label for=\"urlkind\">URL</label>");
                out.println("<input id=\"urlkind\" type=\"radio\" name=\"filetype\" value=\"urlkind\" onclick=\"selectType(this.form,'url');\" checked>");

                out.println("<label for=\"filekind\">Archivo</label>");
                out.println("<input id=\"filekind\" type=\"radio\" name=\"filetype\" value=\"filekind\" onclick=\"selectType(this.form,'file');\">");

                out.println("</td>");
                out.println("</tr>");
            }

            if (null != fid && null != newVersion && !isFile) {
                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Archivo externo URL:");
                out.println("</td>");
                out.println("<td>");

                out.println("<input id=\"hftype\" type=\"hidden\" name=\"hftype\" value=\"url\">");
                out.println("<input name=\"extfile\" id=\"extfile\" size=\"30\" value=\"" + slink + "\" dojoType=\"dijit.form.ValidationTextBox\" editor=\"dijit.form.Textarea\" promptMessage=\"Captura la liga al archivo.\" required=\"true\" style=\"width:300px;\" />");
                //out.println("<input id=\"extfile\" type=\"text\" name=\"extfile\" value=\"" + slink + "\" >");

                out.println("</td>");
                out.println("</tr>");
            } else if (null != fid && null != newVersion && isFile) {
                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Archivo:");
                out.println("</td>");
                out.println("<td>");
                out.println("<input id=\"hftype\" type=\"hidden\" name=\"hftype\" value=\"file\">");
                out.println("<input id=\"ffile\" type=\"file\" name=\"ffile\" value=\"\" style=\"width:300px;\">");
                out.println("</td>");
                out.println("</tr>");
            } else {
                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Archivo externo URL:");
                out.println("</td>");
                out.println("<td>");
                out.println("<input name=\"extfile\" id=\"extfile\" size=\"30\" value=\"" + slink + "\" dojoType=\"dijit.form.ValidationTextBox\" editor=\"dijit.form.Textarea\" required=\"true\" promptMessage=\"Captura la liga al archivo.\"  style=\"width:300px;\" />");
                //out.println("<input id=\"extfile\" type=\"text\" name=\"extfile\" style=\"width:300px;\" value=\"" + slink + "\" >");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Archivo:");
                out.println("</td>");
                out.println("<td>");
                out.println("<input id=\"ffile\" type=\"file\" name=\"ffile\" value=\"\" disabled style=\"width:300px;\">");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            //out.println("<tfoot>");
            //out.println("<tr>");
            //out.println("<td colspan=\"2\" align=\"right\">");
//            out.println("<button  type=\"button\" onclick=\"if(Checkfiles('" + validFiles + "')){this.form.submit();}else {return false;};\">Agregar</button>");
//            SWBResourceURL urlbck = paramRequest.getRenderUrl();
//            urlbck.setParameter("act", "");
//            out.println("<button type=\"button\" onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
            //out.println("</td>");
            //out.println("</tr>");
           // out.println("</tbody>");
            out.println("</table>");
            
            out.println("<br/><button  type=\"button\" onclick=\"if(Checkfiles('" + validFiles + "')){this.form.submit();}else {return false;};\">Agregar</button>");
            SWBResourceURL urlbck = paramRequest.getRenderUrl();
            urlbck.setParameter("act", "");
            out.println("<button type=\"button\" onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
        }
    }

    public void doGetFile(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        User user = paramRequest.getUser();
        String fid = request.getParameter("fid");
        String verNumber = request.getParameter("verNum");
        int intVer = 1;
        if (verNumber != null) {
            intVer = Integer.parseInt(verNumber);
        }
        RepositoryFile repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, paramRequest.getWebPage().getWebSite());
        VersionInfo ver = null;
        VersionInfo vl = repoFile.getLastVersion();
        if (null != vl) {
            ver = vl;
            while (ver.getPreviousVersion() != null) { //
                if (ver.getVersionNumber() == intVer) {
                    break;
                }
                ver = ver.getPreviousVersion();
            }
        }

        try {
            response.setContentType(DEFAULT_MIME_TYPE);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + ver.getVersionFile() + "\";");

            OutputStream out = response.getOutputStream();
            SWBUtils.IO.copyStream(new FileInputStream(SWBPortal.getWorkPath() + repoFile.getWorkPath() + "/" + verNumber + "/" + ver.getVersionFile()), out);
        } catch (Exception e) {
            log.error("Error al obtener el archivo del Repositorio de documentos.", e);
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        String id = getResourceBase().getId();

        PrintWriter out = response.getWriter();
        String accion = paramRequest.getAction();
        if (accion == null) {
            accion = "";
        }
        User user = paramRequest.getUser();

        WebPage wpage = paramRequest.getWebPage();
        WebSite wsite = getResourceBase().getWebSite(); //wpage.getWebSite();

        out.println("<div class=\"swbform\">");

        if (accion.equals("edit")) {

            SWBResourceURL urlA = paramRequest.getActionUrl();
            urlA.setAction("admin_update");

            out.println("<form id=\"" + id + "_myform_repfile\"  name=\"" + id + "_myform_repfile\" action=\"" + urlA.toString() + "\" method=\"post\" >"); //onsubmit=\"submitForm('" + id + "_myform_repfile');return false;\"

            out.println("<fieldset>");
            out.println("<legend>");
            out.println(paramRequest.getLocaleString("msgFileRepositoryRes"));
            out.println("</legend>");


            out.println("<table width=\"100%\" border=\"0\" >");

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            String validFiles = getResourceBase().getAttribute(VALID_FILES, "");


            out.println("<tr><td colspan=\"2\"><B>" + paramRequest.getLocaleString("msgRolesDefinitionLevel") + "</B></td></tr>");
            out.println("<tr><td align=\"right\" width=150>" + paramRequest.getLocaleString("msgView") + ":</td>");
            out.println("<td><select name=\"ver\">" + getSelectOptions("ver", wsite, paramRequest) + "</select></td></tr>");
            out.println("<tr><td align=\"right\" width=150>" + paramRequest.getLocaleString("msgModify") + ":</td>");
            out.println("<td><select name=\"modificar\">" + getSelectOptions("modificar", wsite, paramRequest) + "</select></td></tr>");
            out.println("<tr><td align=\"right\"  width=150>" + paramRequest.getLocaleString("msgAdministrate") + ":</td>");
            out.println("<td><select name=\"administrar\">" + getSelectOptions("administrar", wsite, paramRequest) + "</select></td></tr>");

            out.println("<tr><td align=\"right\"  width=150>" + paramRequest.getLocaleString("msgValidFiles") + ":</td>");
            out.println("<td><input type=\"text\" name=\"validfiles\"  value=\"" + validFiles + "\"></td></tr>");


            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" id=\"" + id + "btn\" name=\"btn\" >" + paramRequest.getLocaleString("msgBTNAccept"));
            out.println("</button>");
            out.println("</fieldset>");

            out.println("<fieldset>");
            out.println("<br> * " + paramRequest.getLocaleString("msgNote") + ": " + paramRequest.getLocaleString("msgRolesDependent"));

            out.println("</fieldset>");
            out.println("</form>");
        }

        out.println("</div>");
    }

    public String getSelectOptions(String type, WebSite wsite, SWBParamRequest paramRequest) {
        String strTemp = "";
        try {

            Resource base = getResourceBase();
            User user = paramRequest.getUser();

            String selectedItem = "";
            if (type.equals("ver")) {
                selectedItem = base.getAttribute(LVL_VIEW, "0");
            } else if (type.equals("modificar")) {
                selectedItem = base.getAttribute(LVL_MODIFY, "0");

            } else if (type.equals("administrar")) {
                selectedItem = base.getAttribute(LVL_ADMIN, "0");
            }

            strTemp = "<option value=\"-1\">" + paramRequest.getLocaleString("msgNoRolesAvailable") + "</option>";

            Iterator<Role> iRoles = wsite.getUserRepository().listRoles(); //DBRole.getInstance().getRoles(topicmap.getDbdata().getRepository());
            StringBuffer strRules = new StringBuffer("");
            strRules.append("\n<option value=\"0\">" + paramRequest.getLocaleString("msgSelNone") + "</option>");
            strRules.append("\n<optgroup label=\"Roles\">");
            while (iRoles.hasNext()) {
                Role oRole = iRoles.next();
                strRules.append("\n<option value=\"" + oRole.getURI() + "\" " + (selectedItem.equals(oRole.getURI()) ? "selected" : "") + ">" + oRole.getDisplayTitle(user.getLanguage()) + "</option>");
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

        } catch (Exception e) {
        }



        return strTemp;
    }

    public int getLevelUser(User user) {
        int level = 0;

        if (null == user) {
            return level;
        }

        Resource base = getResourceBase();

        String uriView = base.getAttribute(LVL_VIEW, "0");
        String uriModify = base.getAttribute(LVL_MODIFY, "0");
        String uriAdmin = base.getAttribute(LVL_ADMIN, "0");

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

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        if (action == null) {
            action = "";
        }

        WebSite wsite = response.getWebPage().getWebSite();
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        if ("newfile".equals(action)) {
            org.semanticwb.portal.util.FileUpload fup = new org.semanticwb.portal.util.FileUpload();
            fup.getFiles(request, null);
            String fname = fup.getFileName("ffile");
            String ftitle = fup.getValue("ftitle");
            String fdescription = fup.getValue("fdescription");
            String fcomment = fup.getValue("fcomment");
            String hftype = fup.getValue("hftype");
            String extfile = fup.getValue("extfile");

            String fid = fup.getValue("fid");
            String newVersion = fup.getValue("newVersion");
            String repoEleStat = fup.getValue("itemAwStatus");
            

            GenericObject go = null;
            RepositoryDirectory repoDir = (RepositoryDirectory) response.getWebPage();
            if (hftype != null && hftype.equals("file")) {
                byte[] bcont = fup.getFileData("ffile");

                RepositoryFile repoFile = null;
                boolean incremento = Boolean.FALSE;
                if (fid != null) {
                    go = ont.getGenericObject(fid);
                    repoFile = (RepositoryFile) go; //RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getProcessSite());
                    if (newVersion != null && newVersion.equals("nextInt")) {
                        incremento = Boolean.TRUE;
                    }
                } else {
                    repoFile = RepositoryFile.ClassMgr.createRepositoryFile(repoDir.getProcessSite());
                    repoFile.setRepositoryDirectory(repoDir);
                }
                repoFile.setTitle(ftitle);
                repoFile.setDescription(fdescription);

                User usr = response.getUser();

                repoFile.setOwnerUserGroup(usr.getUserGroup());

                //System.out.println("fname: "+fname);
                if (fname.indexOf("\\") != -1) {
                    fname = fname.substring(fname.lastIndexOf("\\") + 1);
                } else if (fname.indexOf("/") != -1) {
                    fname = fname.substring(fname.lastIndexOf("/") + 1);
                }
                //System.out.println("fname: "+fname);
                repoFile.storeFile(fname, new ByteArrayInputStream(bcont), fcomment, incremento, repoEleStat);
                
            } else {

                RepositoryURL repoUrl = null;
                boolean incremento = Boolean.FALSE;
                if (fid != null) {
                    go = ont.getGenericObject(fid);
                    repoUrl = (RepositoryURL) go;
                    if (newVersion != null && newVersion.equals("nextInt")) {
                        incremento = Boolean.TRUE;
                    }
                } else {
                    repoUrl = RepositoryURL.ClassMgr.createRepositoryURL(repoDir.getProcessSite());
                    repoUrl.setRepositoryDirectory(repoDir);
                }
                repoUrl.setTitle(ftitle);
                repoUrl.setDescription(fdescription);

                User usr = response.getUser();

                repoUrl.setOwnerUserGroup(usr.getUserGroup());
                repoUrl.storeFile(extfile, fcomment, incremento, repoEleStat);
                
            }

        } else if ("removefile".equals(action)) {
            String fid = request.getParameter("fid");

            SemanticObject so = ont.getSemanticObject(fid);
            so.remove();

        } else if ("admin_update".equals(action)) {
            String viewrole = request.getParameter("ver");
            String modifyrole = request.getParameter("modificar");
            String adminrole = request.getParameter("administrar");
            String validfiles = request.getParameter("validfiles");

            try {
                getResourceBase().setAttribute(LVL_VIEW, viewrole);
                getResourceBase().setAttribute(LVL_MODIFY, modifyrole);
                getResourceBase().setAttribute(LVL_ADMIN, adminrole);
                getResourceBase().setAttribute(VALID_FILES, validfiles);
                getResourceBase().updateAttributesToDB();

            } catch (Exception e) {
                log.error("Error al guardar configuración de niveles de usuario de ProcessFileRepository", e);
            }
            response.setMode(SWBActionResponse.Mode_ADMIN);
            response.setAction("edit");
        }
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
        } else {
            file = "URL file";
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
        } else {
            file = "ico_default2.gif";
        }
        return file;
    }
}
