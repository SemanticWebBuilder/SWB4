/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author jorge.jimenez
 */
public class SWBImportWebSite extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(SWBImportWebSite.class);

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String action = paramRequest.getAction();
        SWBResourceURL url = paramRequest.getRenderUrl();
        if (action != null && action.trim().equals("step2") && request.getParameter("wstype") != null) {
            String wstype = request.getParameter("wstype");
            if (wstype.equals("1")) { //creación de sitio nuevo
                WebSite site = SWBContext.createWebSite(request.getParameter("wsname"), request.getParameter("wsns"));
                site.setCreated(new java.util.Date(System.currentTimeMillis()));
                site.setTitle(request.getParameter("wstitle"));
            } else { //creación de sitio mediante template                
                out.println(directoryList(paramRequest, request.getParameter("wsname"), request.getParameter("wsns"), request.getParameter("wstitle")));
            }
        } else if (action != null && action.trim().equals("step3")) { //creación de sitio mediante template
            if (createWebSite(response, request.getParameter("zipName"), request.getParameter("wsname"), request.getParameter("wsns"))) {
                out.println(paramRequest.getLocaleLogString("sitecreated"));
            } else {
                out.println(paramRequest.getLocaleLogString("sitenotcreated"));
            }
        } else { //Forma de entrada(Datos iniciales)
            url.setAction("step2");
            getStep1(out, url, paramRequest);
        }
    }

    private String directoryList(SWBParamRequest paramRequest, String wsname, String wsns, String wstitle) {
        StringBuffer strbf = new StringBuffer();
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setAction("step3");
        url.setParameter("wsname", wsname);
        url.setParameter("wsns", wsns);
        url.setParameter("wstitle", wstitle);
        File file = new File(SWBPlatform.getWorkPath()+"/sitetemplates/");
        File[] files = file.listFiles();
        strbf.append("<div class=\"swbform\">");
        strbf.append("<table width=\"100%\">");
        strbf.append("<form>");
        strbf.append("<tr>");
        strbf.append("<td colspan=\"2\">");
        strbf.append("<fieldset>");
        strbf.append("<table>");
        strbf.append("<tr>");
        strbf.append("<td>Template</td>");
        strbf.append("<td>Tamaño</td>");
        strbf.append("</tr>");
        for (int i = 0; i < files.length; i++) {
            File filex = files[i];
            String fileName = filex.getName();
            if (filex.isFile() && fileName.endsWith(".zip")) {
                int pos = fileName.lastIndexOf(".");
                if (pos > -1) {
                    fileName = fileName.substring(0, pos);
                }
                strbf.append("<tr><td>");
                url.setParameter("zipName", fileName);
                strbf.append("<a href=\"" + url.toString() + "\">" + fileName + "</a>");
                strbf.append("</td><td>");
                strbf.append(filex.length() + " bytes");
                strbf.append("</td></tr>");
            }
        }
        strbf.append("</table>");
        strbf.append("</fieldset>");
        strbf.append("</td></tr>");
        strbf.append("</form>");
        strbf.append("</table>");
        strbf.append("</div>");
        return strbf.toString();
    }

    private boolean createWebSite(HttpServletResponse response, String name, String newName, String newNS) {
        try {
            //Substituir x ruta dinamica
            String path = SWBPlatform.getWorkPath()+"/";
            String zipdirectory = path + "sitetemplates/";
            File zip = new File(zipdirectory + name + ".zip");
            java.io.File extractTo = new File(path + newName);
            //Descomprimir zip
            org.semanticwb.SWBUtils.IO.unzip(zip, extractTo);

            //Tomar rdf ya descomprimido para parsearlo y crear nuevo sitio   
            FileInputStream rdf = new FileInputStream(path + newName + "/" + name + ".rdf");
            String content = SWBUtils.IO.readInputStream(rdf);
            String oldNameSpace = null;
            int pos1 = content.indexOf("|ns|");
            int pos2 = content.indexOf("|ns|", pos1 + 1);
            int pos3 = content.indexOf("-->", pos2);
            if (pos1 > -1 && pos2 > -1) {
                oldNameSpace = content.substring(pos1 + 4, pos2);
                content = content.substring(pos3 + 3);
            }
            //Parseo de nombre de NameSpace anteriores por nuevos
            content = content.replaceAll(oldNameSpace, newNS); //Reempplazar namespace anterior x nuevo
            content = SWBUtils.TEXT.replaceAllIgnoreCase(content, name, newName); //Reemplazar nombre anterior x nuevo nombre
            //Mediante inputStream creado, generar sitio con metodo de jei, el cual no recuerdo su nombre :)
            InputStream io = SWBUtils.IO.getStreamFromString(content);
            SWBPlatform.getSemanticMgr().createModelByRDF(newName, newNS, io);
            //Eliminar archivo rdf
            new File(path + newName + "/" + name + ".rdf").delete();
            return true;
        } catch (Exception e) {
            log.debug(e);
        }
        return false;
    }

    private void getStep1(PrintWriter out, SWBResourceURL url, SWBParamRequest paramRequest) {
        try {
            out.println("<div class=\"swbform\">");
            out.println("<table width=\"100%\">");
            out.println("<form name=\"frm1\" action=\"" + url.toString() + "\" method=\"post\">");
            out.append("<tr>");
            out.append("<td colspan=\"2\">");
            out.println("<fieldset>");
            out.println("<table>");
            out.append("<tr>");
            out.append("<td>");
            out.println(paramRequest.getLocaleLogString("msgwsName"));
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" name=\"wsname\" size=\"30\">");
            out.println("</td>");
            out.append("</tr>");

            out.append("<tr><td>");
            out.println(paramRequest.getLocaleLogString("msgwsNameSpace"));
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" name=\"wsns\" size=\"30\">");
            out.println("</td>");
            out.append("</tr>");

            out.append("<tr><td>");
            out.println(paramRequest.getLocaleLogString("msgwsTitle"));
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" name=\"wstitle\" size=\"30\">");
            out.println("</td>");
            out.append("</tr>");

            out.append("<tr><td>");
            out.println(paramRequest.getLocaleLogString("msgwstype"));
            out.println("</td><td>");
            out.println("<select name=\"wstype\">");
            out.println("<option value=\"1\">" + paramRequest.getLocaleLogString("msgwstype1") + "</option>");
            out.println("<option value=\"2\">" + paramRequest.getLocaleLogString("msgwstype2") + "</option>");
            out.println("</select>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</td></tr>");
            out.append("<tr><td colspan=\"2\">");
            out.println("<fieldset>");
            out.println("<table>");
            out.println("<tr><td><input type=\"submit\" value=\"" + paramRequest.getLocaleLogString("msgsend") + "\"></td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</td></tr>");
            out.println("</form>");
            out.println("</table>");
            out.println("</DIV>");
        } catch (Exception e) {
            log.debug(e);
        }
    }
}
