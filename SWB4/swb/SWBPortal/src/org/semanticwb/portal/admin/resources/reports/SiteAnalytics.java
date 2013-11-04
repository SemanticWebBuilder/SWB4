/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources.reports;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.access.LinkedPageCounter;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author carlos.alvarez
 */
public class SiteAnalytics extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SiteAnalytics.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        //TODO: Desplegar editor
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode("viewAnalytics");
        url.setCallMethod(SWBResourceURL.Call_DIRECT);
        url.setParameter("suri", request.getParameter("suri"));
        out.println("<iframe src=\"" + url + "\" style=\"width:100%; height:100%;\" frameborder=\"0\"></iframe>");
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if ("doLoad".equals(paramRequest.getMode())) {
            doStart(request, response, paramRequest);
        } else if (mode.equals("viewAnalytics")) {
            doviewAnalytics(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public void doviewAnalytics(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = SWBPlatform.getContextPath() + "/swbadmin/jsp/reports/siteAnalytics.jsp";
        request.setAttribute("paramRequest", paramRequest);
        request.setAttribute("suri", request.getParameter("suri"));
        request.setAttribute("list", getLogs());
        request.setAttribute("log", request.getParameter("log") != null ? request.getParameter("log") : "");
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            rd.include(request, response);
        } catch (Exception e) {
            log.error("Error on doviewAnalytics, " + path + ", " + e.getCause());
        }
    }

    public void doStart(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if (paramsRequest.getCallMethod() == SWBParamRequest.Call_DIRECT) {
            String logg = request.getParameter("log") != null ? request.getParameter("log") : "";
            if (!logg.equals("")) {
                LinkedPageCounter.reset();
                LinkedPageCounter.loadFile(logg);
            }
        }
    }

    public ArrayList getLogs() {
        ArrayList array = new ArrayList();
        File dir = new File(SWBPortal.getWorkPath() + "/logs");
        File files[] = dir.listFiles();
        for (int x = 0; x < files.length; x++) {
            File file = files[x];
            if (file.getName().indexOf("_acc.") > 0) {
                array.add(file.getPath() + "|" + file.getName());
            }
        }
        return array;
    }
}
