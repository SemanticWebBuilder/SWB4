/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.omg.CORBA.portable.ApplicationException;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author jorge.jimenez
 */
public class SWBModelAdmin extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SWBImportWebSite.class);

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("viewmodel")) {
            doViewModell(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("installmodel")) {
            doInstallModell(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("uploadmodel")) {
            doUploadModell(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            System.out.println("entra a SWBModelAdmin/doView");
            PrintWriter out = response.getWriter();
            SWBResourceURL url = paramRequest.getRenderUrl();
            StringBuffer strbf = new StringBuffer();
            File file = new File(SWBPlatform.getWorkPath() + "/sitetemplates/");
            File[] files = file.listFiles();
            out.println("<div class=\"swbform\">");
            out.println("<table>");
            out.println("<form action=\"" + url.setAction("uploadmodel").toString() + "\">");
            out.println("<tr>");
            out.println("<td colspan=\"2\">");
            out.println("<fieldset>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td>Template</td>");
            out.println("<td>Tamaño</td>");
            out.println("<td>Instalar</td>");
            out.println("<td>Descargar</td>");
            out.println("<td>Eliminar</td>");
            out.println("</tr>");
            for (int i = 0; i < files.length; i++) {
                File filex = files[i];
                String fileName = filex.getName();
                if (filex.isFile() && fileName.endsWith(".zip")) {
                    int pos = fileName.lastIndexOf(".");
                    if (pos > -1) {
                        fileName = fileName.substring(0, pos);
                    }
                    out.println("<tr><td>");
                    url.setParameter("zipName", fileName);
                    url.setAction("viewmodel");
                    out.println("<a href=\"" + url.toString() + "\" onclick=\"submitUrl('" + url.toString() + "',this);return false;\">" + fileName + "</a>");
                    out.println("</td><td>");
                    out.println(filex.length() + " bytes");
                    out.println("</td>");
                    url.setAction("installmodel");
                    out.println("<td>Instalar</td>");
                    url.setAction("downloadmodel");
                    out.println("<td>Descargar</td>");
                    url.setAction("deletemodel");
                    out.println("<td>Eliminar</td>");
                    out.println("</tr>");
                }
            }
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</td></tr>");
            out.println("<tr><td><input type=\"file\" value=\"Nuevo\"/></td></tr>");
            out.println("</form>");
            out.println("</table>");
            out.println("</div>");
            out.println(strbf.toString());
        } catch (Exception e) {
            log.debug(e);
        }
    }

    public void doViewModell(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        out.println("<table>");
        try {
            ZipFile zf = null;
            try {
                zf = new ZipFile(request.getParameter("zipName"));
                java.util.Enumeration enu = zf.entries();
                while (enu.hasMoreElements()) {
                    ZipEntry zen = (ZipEntry)enu.nextElement();
                    if (zen.isDirectory()) {
                        out.println("<tr><td>"+zen.getName()+"/Directorio</td></tr>");
                    }else{
                        out.println("<tr><td>"+zen.getName()+"/Archivo</td></tr>");
                    }
                }
                out.println("<tr>");
            } catch (Exception ex) {
                log.equals(ex);
            } finally {
                if (zf != null) {
                    zf.close();
                }
            }
        } catch (Exception e) {
            log.debug(e);
        }
    }

    public void doInstallModell(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
        } catch (Exception e) {
            log.debug(e);
        }
    }

    public void doUploadModell(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
        } catch (Exception e) {
            log.debug(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
    }
}
