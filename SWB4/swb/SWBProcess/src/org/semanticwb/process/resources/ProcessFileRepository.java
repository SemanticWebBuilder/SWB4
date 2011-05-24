/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            out.println("<table>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println("&nbsp;");
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
            out.println("</tr>");
            out.println("</thead>");

            out.println("<tbody>");
            Iterator<RepositoryFile> itrf = repoDir.listRepositoryFiles();
            while (itrf.hasNext()) {
                RepositoryFile repositoryFile = itrf.next();
                out.println("<tr>");
                out.println("<td>");
                String fid = repositoryFile.getId();

                SWBResourceURL urldetail = paramRequest.getRenderUrl();
                urldetail.setParameter("act", "detail");
                urldetail.setParameter("fid", fid);

                out.println("<a href=\"" + urldetail + "\">" + fid + "</a>");

                out.println("</td>");
                out.println("<td>");

                VersionInfo vi = repositoryFile.getActualVersion();

                out.println(vi.getVersionFile());
                out.println("</td>");
                out.println("<td>");
                out.println(repositoryFile.getDisplayTitle(usr.getLanguage()));
                out.println("</td>");
                out.println("<td>");
                out.println(vi.getUpdated());
                out.println("</td>");
                out.println("<td>");
                out.println(vi.getModifiedBy().getFullName());
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
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println("&nbsp;");
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
            out.println("</tr>");
            out.println("</thead>");
            out.println("</table>");
            out.println("</div>");
        } else if ("history".equals(action)) {
            String fid = request.getParameter("fid");
            RepositoryFile repoFile = RepositoryFile.ClassMgr.getRepositoryFile(fid, repoDir.getProcessSite());
            VersionInfo va = repoFile.getActualVersion();
            //VersionInfo vi = getFirstVersion(va);
            VersionInfo vl = repoFile.getLastVersion();
        } else if ("new".equals(action)) {
        }



    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        super.processAction(request, response);
    }


//    private Process getProcess(String suri) {
//
//        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
//        GenericObject gobj = ont.getGenericObject(suri);
//        Process process = null;
//        if (gobj instanceof Process) {
//            process = (Process) gobj;
//        }
//
//        return process;
//    }
}
