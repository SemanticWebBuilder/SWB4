/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.util.WBFileUpload;

/**
 *
 * @author jorge.jimenez
 */
public class SWBWebSiteTpls extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SWBImportWebSite.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String action = paramRequest.getAction();
        if (action != null && action.equals("step2")) {
            String sdo = request.getParameter("do");
            if (sdo != null) {
                if (sdo.equals("1")) { //Importar template mediante archivo
                    getTplFile(response, paramRequest, sdo);
                } else if (sdo.equals("2")) { //Importar template de sitio de comunidad
                } else if (sdo.equals("3")) { //Eliminar template
                    doListTplDirectory(response, paramRequest, sdo); 
                } else if (sdo.equals("4")) { //Enviar template a sitio de comunidad
                }
            }
        } else { //Pinta opciones iniciales para realizar (menú de acciones)
            getStep1(response, paramRequest);
        }
    }

    private void getTplFile(HttpServletResponse response, SWBParamRequest paramRequest, String sdo) {
        try {
            PrintWriter out = response.getWriter();
            SWBResourceURL url = paramRequest.getActionUrl();
             
            doListTplDirectory(response, paramRequest, sdo);            
            
            url.setAction("step3");
            out.println("<div class=\"swbform\">");
            out.println("<table width=\"100%\">");
            out.println("<form method=\"post\" enctype=\"multipart/form-data\" action=\"" + url.toString() + "\">");
            out.println("<tr><td>");
            out.println("<fieldset>");
            out.println("<legend>"+paramRequest.getLocaleLogString("newzip")+"</legend>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td>" + paramRequest.getLocaleLogString("zipfile") + "</td>");
            out.println("<td><input type=\"file\" name=\"zipfile\"/></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</td></tr>");
            out.println("<tr><td>");
            out.println("<fieldset>");
            out.println("<input type=\"submit\" value=\"" + paramRequest.getLocaleLogString("sendform") + "\"/>");
            out.println("</fieldset>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</form>");
            out.println("</div>");          
        } catch (Exception e) {
            log.debug(e);
        }
    }

    private void getStep1(HttpServletResponse response, SWBParamRequest paramRequest) {
        try {
            PrintWriter out = response.getWriter();
            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setAction("step2");
            out.println("<div class=\"swbform\">");
            out.println("<table width=\"100%\">");
            out.println("<tr><td>");
            out.println("<fieldset>");
            out.println("<legend>"+paramRequest.getLocaleLogString("tplsites")+"</legend>");
            out.println("<table>");
            out.println("<tr><td>");
            url.setParameter("do", "1");
            out.println("<a href=\"" + url.toString() + "\">" + paramRequest.getLocaleLogString("tplfile") + "</a>");
            out.println("</td></tr>");
            url.setParameter("do", "2");
            out.println("<tr><td>");
            out.println("<a href=\"" + url.toString() + "\">" + paramRequest.getLocaleLogString("comtpl") + "</a>");
            out.println("</td></tr>");
            out.println("<tr><td>");
            url.setParameter("do", "3");
            out.println("<a href=\"" + url.toString() + "\">" + paramRequest.getLocaleLogString("remtpl") + "</a>");
            out.println("</td></tr>");
            out.println("<tr><td>");
            url.setParameter("do", "4");
            out.println("<a href=\"" + url.toString() + "\">" + paramRequest.getLocaleLogString("sendtpl") + "</a>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</div>");
        } catch (Exception e) {
            log.debug(e);
        }
    }

    public void doListTplDirectory(HttpServletResponse response, SWBParamRequest paramRequest, String sdo) throws SWBResourceException, IOException {
        try {
            PrintWriter out = response.getWriter();
            StringBuffer strbf = new StringBuffer();
            SWBResourceURL url = paramRequest.getRenderUrl();
            SWBResourceURL actionurl = paramRequest.getActionUrl();
            File file = new File(SWBPlatform.getWorkPath() + "/sitetemplates/");
            File[] files = file.listFiles();
            strbf.append("<div class=\"swbform\">");
            strbf.append("<table width=\"100%\">");
            strbf.append("<form>");
            strbf.append("<tr>");
            strbf.append("<td colspan=\"2\">");
            strbf.append("<fieldset>");
            strbf.append("<legend>"+paramRequest.getLocaleLogString("zipexist")+"</legend>");
            strbf.append("<table>");
            strbf.append("<tr>");
            strbf.append("<td>Template</td>");
            strbf.append("<td>Tamaño</td>");
            strbf.append("</tr>");
            actionurl.setAction("remove");
            actionurl.setParameter("do", sdo);
            actionurl.setParameter("action", paramRequest.getAction());
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
                    strbf.append("</td><td>");
                    actionurl.setParameter("zipname", fileName);
                    strbf.append("<a href=\""+actionurl.toString() + "\" onclick=\"if(confirm('"+paramRequest.getLocaleString("msgsureremove")+"?')) { return(true);} else {return(false);}\">" + paramRequest.getLocaleLogString("remove") + "</a>");
                    strbf.append("</td></tr>");
                }
            }
            strbf.append("</table>");
            strbf.append("</fieldset>");
            strbf.append("</td></tr>");
            strbf.append("</form>");
            strbf.append("</table>");
            strbf.append("</div>");
            out.println(strbf.toString());
        } catch (Exception e) {
            log.debug(e);
        }
    }
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        String action=response.getAction();
        if(action!=null){
            if(action.equals("upload")){
                String path = SWBPlatform.getWorkPath() + "/";
                String zipdirectory = path + "sitetemplates/";
                WBFileUpload fUpload = new WBFileUpload();
                fUpload.getFiles(request);
                fUpload.saveFile("zipfile", zipdirectory);
            }
            if(action.equals("remove")){
                String zipname=request.getParameter("zipname");
                File file = new File(SWBPlatform.getWorkPath() + "/sitetemplates/"+zipname+".zip");
                file.delete();
                response.setRenderParameter("do", request.getParameter("do"));
                response.setAction(request.getParameter("action"));            
            }
       }
    }

}
