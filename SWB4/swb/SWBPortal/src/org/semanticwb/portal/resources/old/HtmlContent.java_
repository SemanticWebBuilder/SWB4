/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.util.WBFileUpload;

/**
 *
 * @author jorge.jimenez
 */
public class HtmlContent extends GenericResource {

    private static Logger log = SWBUtils.getLogger(HtmlContent.class);
    WBAdmResourceUtils admResUtils = new WBAdmResourceUtils();

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals("upload")) {
            doFileUpload(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            System.out.println("entra j");
            request.setAttribute("paramRequest", paramRequest);
            RequestDispatcher rd=request.getRequestDispatcher("/resources/jsp/content/contentAdmin.jsp");
            rd.include(request, response);         
        } catch (Exception e) {
            log.debug(e);
        }
    }

    public void doFileUpload(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Portlet portlet = paramRequest.getResourceBase();
        if (request.getHeader("content-type") != null && (request.getHeader("content-type").indexOf("multipart/form-data")) > -1) { // utilizar fileupload
            WBFileUpload fUpload = new WBFileUpload();
            fUpload.getFiles(request);
            String filename = null, strClientPath="";
            filename = fUpload.getFileName("filecontent");
            filename=filename.replace('\\','/');
            int i = filename.lastIndexOf("/");
            if (i > -1) {
                strClientPath=filename.substring(0, i+1);
                filename = filename.substring(i + 1);
            }
            i = filename.lastIndexOf("/");
            if (i != -1) {
                filename = filename.substring(i + 1);
            }
            VersionInfo version=new VersionInfo(portlet.getSemanticObject());
            version.setVersionFile(filename);
            version.setVersionNumber(1);
            portlet.setActualVersion(version);
            portlet.setLastVersion(version);
            
            String portletWorkPath=SWBPlatform.getWorkPath()+portlet.getWorkPath()+"/"+version.getVersionNumber()+"/";
            File file=new File(portletWorkPath);
            if (!file.exists()) file.mkdirs();

            fUpload.saveFile("filecontent", portletWorkPath);

            String strAttaches = fUpload.FindAttaches("filecontent");

            file = new File(portletWorkPath + "images/");
            if (!file.exists()) file.mkdir();
            if (file.exists() && file.isDirectory())
            {
                PrintWriter out = response.getWriter();
                out.println("\n");
                out.println("<APPLET  WIDTH=100% HEIGHT=100% CODE=\"applets.dragdrop.DragDrop.class\" codebase=\""+SWBPlatform.getContextPath()+"\" archive=\"swbadmin/lib/DragDrop.jar, swbadmin/lib/WBCommons.jar\" border=0>");
                out.println("<PARAM NAME=\"webpath\" VALUE=\""+SWBPlatform.getContextPath()+"/\">\n");
                out.println("<PARAM NAME=\"foreground\" VALUE=\"000000\">\n");
                out.println("<PARAM NAME=\"background\" VALUE=\"979FC3\">\n");
                out.println("<PARAM NAME=\"foregroundSelection\" VALUE=\"ffffff\">\n");
                out.println("<PARAM NAME=\"backgroundSelection\" VALUE=\"666699\">\n");
                out.println("<PARAM NAME=\"path\" value=\"" + portletWorkPath + "images/" + "\">\n");
                out.println("<PARAM NAME=\"clientpath\" value=\"" + strClientPath + "\">\n");
                out.println("<PARAM NAME=\"files\" value=\"" + strAttaches + "\">\n");
                out.println("</APPLET>\n");
            }
        } 
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
    }
}
