/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FileUpload;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.RepositoryDirectory;
import org.semanticwb.process.model.RepositoryFile;

/**
 *
 * @author juan.fernandez
 */
public class ProcessFileRepository extends GenericResource {

    private Logger log = SWBUtils.getLogger(ProcessFileRepository.class);
    private SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yy hh:mm");
    private static final String MODE_GETFILE = "getFile";
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";

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

        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        User usr = paramRequest.getUser();

        RepositoryDirectory repoDir = (RepositoryDirectory) paramRequest.getWebPage();

        String action = request.getParameter("act");
        if (null == action) {
            action = "";
        }

        if ("".equals(action)) {

            out.println("<div>");
            out.println("<table width=\"100%\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println("Id");
            out.println("</th>");
            out.println("<th>");
            out.println("Tipo");
            out.println("</th>");
            out.println("<th>");
            out.println("Nombre");
            out.println("</th>");
            out.println("<th>");
            out.println("Modificado");
            out.println("</th>");
            out.println("<th>");
            out.println("Modificado por");
            out.println("</th>");
            out.println("<th>");
            out.println("Acción");
            out.println("</th>");
            out.println("</tr>");
            out.println("</thead>");

            out.println("<tbody>");
            Iterator<RepositoryFile> itrf = repoDir.listRepositoryFiles();
            while (itrf.hasNext()) {
                RepositoryFile repositoryFile = itrf.next();
                out.println("<tr>");
                out.println("<td>");
                String fid = repositoryFile.getId();
                out.println(fid);
                out.println("</td>");

                out.println("<td>");
                SWBResourceURL urldetail = paramRequest.getRenderUrl();
                urldetail.setParameter("act", "detail");
                urldetail.setParameter("fid", fid);

                VersionInfo vi = repositoryFile.getLastVersion();

                out.println("<a href=\"" + urldetail + "\">");
                out.println(vi != null && vi.getVersionFile() != null ? vi.getVersionFile() : "--");
                out.println("</a>");
                out.println("</td>");
                out.println("<td>");
                out.println(repositoryFile.getDisplayTitle(usr.getLanguage()));
                out.println("</td>");
                out.println("<td align=\"center\">");
                out.println(vi != null && vi.getUpdated() != null ? format.format(vi.getUpdated()) : "--");
                out.println("</td>");
                out.println("<td>");
                out.println(vi != null && vi.getModifiedBy() != null && vi.getModifiedBy().getFullName() != null ? vi.getModifiedBy().getFullName() : "--");
                out.println("</td>");

                out.println("<td>");
                SWBResourceURL urlremove = paramRequest.getActionUrl();
                urlremove.setAction("removefile");
                urlremove.setParameter("act", "remove");
                urlremove.setParameter("fid", fid);
                out.println("<a href=\"" + urlremove + "\">" + "eliminar" + "</a>");

                out.println("</td>");

                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("<tfoot>");
            out.println("<tr>");
            out.println("<td colspan=\"5\">");
            SWBResourceURL urlnew = paramRequest.getRenderUrl();
            urlnew.setParameter("act", "new");
            out.println("<button onclick=\"window.location='" + urlnew + "';\">" + "Agregar archivo" + "</button>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tfoot>");
            out.println("</table>");
            out.println("</div>");

        } else if ("detail".equals(action)) {
            String fid = request.getParameter("fid");
            RepositoryFile repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getProcessSite());
            out.println("<div>");
            out.println("<table>");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Título:");
            out.println("</td>");
            out.println("<td>");
            out.println(repoFile.getDisplayTitle(usr.getLanguage()));
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Descripción:");
            out.println("</td>");
            out.println("<td>");
            out.println(repoFile.getDisplayDescription(usr.getLanguage()));
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Archivo:");
            out.println("</td>");
            out.println("<td>");
            VersionInfo vl = repoFile.getLastVersion();
            out.println(vl.getVersionFile());
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Versión:");
            out.println("</td>");
            out.println("<td>");
            out.println(vl.getVersionValue());

            SWBResourceURL urlverhistoy = paramRequest.getRenderUrl();
            urlverhistoy.setParameter("act", "history");
            urlverhistoy.setParameter("fid", fid);

            out.println("(<a href=\"" + urlverhistoy + "\">versiones</a>)");
            out.println("</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Agregar nueva Versión:");
            out.println("</td>");

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
            out.println("<select name=\"newVersion\" onsubmit=\"validaVersion(this);\">");
            out.println("<option value=\"0\">--</option>");

            float fver = Float.parseFloat(vl.getVersionValue());
            fver = fver + 0.1F;
            
            int iver = (int) fver;
            iver = iver + 1;

            out.println("<option value=\"fraction\">" + fver + "</option>");
            out.println("<option value=\"nextInt\">" + (float)iver + "</option>");


            out.println("</select>");
            out.println("<button type=\"submit\">Agregar</button>");
            out.println("</form>");
            out.println("</td>");
            out.println("</tr>");

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
            out.println("<tfoot>");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"right\">");
            SWBResourceURL urlbck = paramRequest.getRenderUrl();
            urlbck.setParameter("act", "");
            out.println("<button onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
        } else if ("history".equals(action)) {
            String fid = request.getParameter("fid");
            RepositoryFile repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getProcessSite());
            VersionInfo ver = null;
            VersionInfo vl = repoFile.getLastVersion();
            if (null != vl) {
                ver = vl;
                while (ver.getPreviousVersion() != null) { //
                    ver = ver.getPreviousVersion();
                }
            }
            if (ver != null) {
                out.println("<div>");
                out.println("<table width=\"100%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Título:");
                out.println("</td>");
                out.println("<td>");
                out.println(repoFile.getDisplayTitle(usr.getLanguage()));
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Descripción:");
                out.println("</td>");
                out.println("<td>");
                out.println(repoFile.getDisplayDescription(usr.getLanguage()));
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td align=\"right\">");
                out.println("Archivo:");
                out.println("</td>");
                out.println("<td>");
                out.println(ver.getVersionFile());
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<th>");
                out.println("&nbsp;");// espacio para liga ver archivo
                out.println("</th>");
                out.println("<th>");
                out.println("Versión");
                out.println("</th>");
                out.println("<th>");
                out.println("Fecha versión");
                out.println("</th>");
                out.println("<th>");
                out.println("Creado por");
                out.println("</th>");
                out.println("<th>");
                out.println("Comentario");
                out.println("</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
                while (ver != null) {
                    //lista de las versiones del archivo

                    out.println("<tr>");
                    out.println("<td align=\"center\" >");

                    SWBResourceURL urlview = paramRequest.getRenderUrl();
                    urlview.setCallMethod(SWBResourceURL.Call_DIRECT);
                    urlview.setParameter("fid", fid);
                    urlview.setMode(MODE_GETFILE);
                    urlview.setParameter("verNum", "" + ver.getVersionNumber());

                    out.println("<a href=\"" + urlview + "\">ver</a>");
                    out.println("</td>");
                    out.println("<td align=\"center\">");
                    out.println(ver.getVersionValue());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(ver.getCreated() != null ? format.format(ver.getCreated()) : "--");
                    out.println("</td>");
                    out.println("<td>");
                    out.println(ver.getCreator() != null ? ver.getCreator().getFullName() : "--");

                    out.println("</td>");
                    out.println("<td>");
                    out.println(ver.getVersionComment() != null ? ver.getVersionComment() : "--");

                    out.println("</td>");
                    out.println("</tr>");

                    ver = ver.getNextVersion();

                }
                out.println("</tbody>");
                out.println("<tfoot>");
                out.println("<tr>");
                out.println("<td colspan=\"2\" align=\"right\">");
                SWBResourceURL urlbck = paramRequest.getRenderUrl();
                urlbck.setParameter("act", "detail");
                urlbck.setParameter("fid", fid);
                out.println("<button onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</tfoot>");
                out.println("</table>");
                out.println("</div>");
            }



        } else if ("new".equals(action)) {

            SWBResourceURL urlnew = paramRequest.getActionUrl();
            urlnew.setAction("newfile");
            urlnew.setParameter("act", "newfile");

            String fid = request.getParameter("fid");
            String newVersion = request.getParameter("newVersion");
            String sNxtVersion = "";
            RepositoryFile repoFile = null;
            String stitle = "";
            String sdescription = "";
            if (null != fid && null != newVersion) {
                boolean incremento = Boolean.FALSE;
                if (newVersion.equals("nextInt")) {
                    incremento = Boolean.TRUE;
                }

                repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getProcessSite());

                stitle=repoFile.getDisplayTitle(usr.getLanguage());
                sdescription=repoFile.getDisplayDescription(usr.getLanguage());

                VersionInfo vl = repoFile.getLastVersion();
                float fver = Float.parseFloat(vl.getVersionValue());
                fver = fver + 0.1F;

                int iver = (int) fver;
                iver = iver + 1;

                sNxtVersion = "" + fver;

                if (incremento) {
                    sNxtVersion = "" + (float)iver;
                }
            }

            out.println("<div>");
            out.println("<form method=\"post\" action=\"" + urlnew + "\" >"); 

            if (null != fid && null != newVersion) {
                out.println("<input type=\"hidden\" name=\"newVersion\" value=\"" + newVersion + "\">");
                out.println("<input type=\"hidden\" name=\"fid\" value=\"" + fid + "\">");
            }


            out.println("<table>");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Título:");
            out.println("</td>");
            out.println("<td>");
            out.println("<input type=\"text\" name=\"ftitle\" value=\""+stitle+"\">");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Descripción:");
            out.println("</td>");
            out.println("<td>");
            out.println("<input type=\"text\" name=\"fdescription\" value=\""+sdescription+"\">");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Comentario:");
            out.println("</td>");
            out.println("<td>");
            out.println("<input type=\"text\" name=\"fcomment\">");
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


            out.println("<tr>");
            out.println("<td align=\"right\">");
            out.println("Archivo:");
            out.println("</td>");
            out.println("<td>");

            out.println("<input type=\"file\" name=\"ffile\">");
            out.println("</td>");
            out.println("</tr>");

            out.println("</tbody>");
            out.println("<tfoot>");
            out.println("<tr>");
            out.println("<td colspan=\"2\" align=\"right\">");
            out.println("<button type=\"submit\" >Agregar</button>");
            SWBResourceURL urlbck = paramRequest.getRenderUrl();
            urlbck.setParameter("act", "");
            out.println("<button type=\"button\" onclick=\"window.location='" + urlbck + "';\">Regresar</button>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tbody>");
            out.println("</table>");
            out.println("</form>");
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
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        if (action == null) {
            action = "";
        }

        if ("newfile".equals(action)) {

            String fid = request.getParameter("fid");
            String newVersion = request.getParameter("newVersion");
            RepositoryDirectory repoDir = (RepositoryDirectory) response.getWebPage();
            RepositoryFile repoFile = null;

            boolean incremento = Boolean.FALSE;

            if(fid!=null)
            {
                repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getProcessSite());
                if (newVersion!=null&&newVersion.equals("nextInt")) {
                    incremento = Boolean.TRUE;
                }
            } else {
                repoFile = RepositoryFile.ClassMgr.createRepositoryFile(repoDir.getProcessSite());
                repoFile.setRepositoryDirectory(repoDir);
            }

            String fname = request.getParameter("ffile");
            String ftitle = request.getParameter("ftitle");
            String fdescription = request.getParameter("fdescription");
            String fcomment = request.getParameter("fcomment");

            File f = new File(fname);
            FileInputStream in = new FileInputStream(f);
            
            repoFile.setTitle(ftitle);
            repoFile.setDescription(fdescription);
            repoFile.storeFile(f.getName(), in, fcomment, incremento);

        } else if ("removefile".equals(action)) {
            String fid = request.getParameter("fid");
            RepositoryDirectory repoDir = (RepositoryDirectory) response.getWebPage();
            RepositoryFile repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getProcessSite());
            repoFile.remove();
        }


    }
}
