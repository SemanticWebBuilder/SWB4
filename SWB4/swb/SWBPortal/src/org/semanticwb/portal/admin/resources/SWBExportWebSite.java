/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author jorge.jimenez
 */
public class SWBExportWebSite extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SWBExportWebSite.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer strbr = new StringBuffer();       
        String action = paramRequest.getAction();
        try {
            if (action != null && action.equals("step2")) {
                String uri = request.getParameter("wsid");
                WebSite site=SWBContext.getWebSite(uri);
                String path = SWBPlatform.getWorkPath() + "/";
                String modelspath=path + "models/";
                String zipdirectory = path + "sitetemplates/";
                //---------Generación de archivo zip de carpeta work de sitio especificado-------------
                java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(new FileOutputStream(zipdirectory + uri + ".zip"));
                java.io.File directory = new File(modelspath + uri + "/");
                java.io.File base = new File(modelspath + uri);
                org.semanticwb.SWBUtils.IO.zip(directory, base, zos);
                zos.close();
                //-------------Generación de archivo rdf del sitio especificado----------------
                try {
                    WebSite ws = SWBContext.getWebSite(uri);
                    File file = new File(zipdirectory + uri + ".rdf");
                    //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    FileOutputStream out = new FileOutputStream(file);
                    ws.getSemanticObject().getModel().write(out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //----------Generación de archivo siteInfo.xml del sitio especificado-----------
                try {
                    strbr.append("<siteinfo>\n");
                    strbr.append("<name>"+site.getTitle()+"</name>\n");
                    strbr.append("<namespace>"+site.getNameSpace()+"</namespace>\n");
                    strbr.append("<title>"+site.getTitle()+"</title>\n");
                    strbr.append("<description>"+site.getDescription()+"</description>\n");
                    strbr.append("</siteinfo>");
                    File file = new File(zipdirectory + "siteInfo.xml");
                    FileOutputStream out = new FileOutputStream(file);
                    out.write(strbr.toString().getBytes("utf-8"));
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //--------------Agregar archivo rdf generado a zip generado---------------------
                File existingzip = new File(zipdirectory + uri + ".zip");
                File rdfFile = new File(zipdirectory + uri + ".rdf");
                File infoFile = new File(zipdirectory + "siteInfo.xml");
                java.io.File[] files2add = {rdfFile, infoFile};
                org.semanticwb.SWBUtils.IO.addFilesToExistingZip(existingzip, files2add);
                //Eliminar rdf y xml generados y ya agregados a zip
                rdfFile.delete();
                infoFile.delete();
                PrintWriter out=response.getWriter();
                out.println("<script type=\"text/javascript\">");
                out.println("hideDialog();");
                out.println("showStatus('Sitio Exportado');");
                out.println("</script>");               
            } else {
                strbr.append("<div class=\"swbform\">");
                strbr.append("<fieldset>");
                strbr.append("Seleccione el sitio a guardar como plantilla:");
                strbr.append("<table>");
                SWBResourceURL url = paramRequest.getRenderUrl();
                url.setAction("step2");
                Iterator<WebSite> itws = SWBContext.listWebSites();
                while (itws.hasNext()) {
                    WebSite ws = itws.next();
                    strbr.append("<tr><td>");
                    url.setParameter("wsid", ws.getId());
                    strbr.append("<a href=\"" + url.toString() + "\" onclick=\"submitUrl('" + url.toString() + "',this);return false;\">" + ws.getTitle() + "</a>");
                    strbr.append("</td></tr>");
                }
                strbr.append("</table>");
                strbr.append("</fieldset>");
                strbr.append("</div>");
                response.getWriter().println(strbr.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e);
        }
    }
}
