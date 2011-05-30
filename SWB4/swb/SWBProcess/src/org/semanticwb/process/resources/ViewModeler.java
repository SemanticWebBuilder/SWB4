/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author jei
 */
public class ViewModeler extends Modeler
{
    @Override
    public void doApplet(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        String suri = request.getParameter("suri");
        String pending = request.getParameter("pending");
        String done = request.getParameter("done");
        if (pending == null)pending="";
        if (done == null)done="";
        PrintWriter out = response.getWriter();
        SWBResourceURL urlapp = paramsRequest.getRenderUrl();
        urlapp.setMode("gateway");
        urlapp.setCallMethod(urlapp.Call_DIRECT);
        urlapp.setParameter("suri", suri);

        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">");
        out.println("<script src=\"http://dl.javafx.com/1.3/dtfx.js\"></script>");
        out.println("</head>");
        out.println(" <body style=\"margin-top:0; margin-left:0;\">");
        out.println("  <div style=\"width: 100%; height: 100%\">");
        out.println("    <script>");
        out.println("    javafx(");
        out.println("        {");
        out.println("              archive: \"" + SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAppBPMNModeler.jar," + SWBPlatform.getContextPath() + "/swbadmin/lib/json.jar," + SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAplCommons.jar\",");
        out.println("              draggable: true,");
        //out.println("              width: document.body.scrollWidth,");
        //out.println("              height: document.body.scrollHeight,");
        out.println("              width: \"100%\",");
        out.println("              height: \"100%\",");
        out.println("              code: \"org.semanticwb.process.modeler.Main\",");
        out.println("              name: \"SWBAppBPMNModeler\"");
        out.println("        },");
        out.println("        {");
        out.println("              lang: \"" + paramsRequest.getUser().getLanguage() + "\",");
        out.println("              mode: \"view\",");
        out.println("              pending: \""+pending+"\",");
        out.println("              done: \""+done+"\",");
        out.println("              jsess: \"" + request.getSession().getId() + "\",");
        out.println("              cgipath: \"" + urlapp + "\"");
        out.println("        }");
        out.println("    );");
        out.println("    </script>");
        out.println("  </div>");
        out.println(" </body>");
        out.println("</html>");
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        String current = request.getParameter("current");
        if (current==null)current="";

        SWBResourceURL urlapp = paramsRequest.getRenderUrl();
        urlapp.setMode("applet");
        urlapp.setCallMethod(urlapp.Call_DIRECT);
        urlapp.setParameter("suri", suri);
        urlapp.setParameter("current", current);

        out.println("<div class=\"applet\">");
        out.println("<iframe dojoType_=\"dijit.layout.ContentPane\" src=\"" + urlapp + "\" width=\"800px\" height=\"600px\" frameborder=\"0\" scrolling=\"no\"></iframe>");
        //String idframe = "ifr_" + getResourceBase().getId();
        //out.println("<iframe alt=\"Modeler\" scrolling=\"no\" frameborder=\"0\" name==\"" + idframe + "\" id=\"" + idframe + "\" src=\"" + urlapp + "\" width=\"100%\" onload=\"this.style.height = " + idframe + ".document.body.scrollHeight + 5\" ></iframe>"); //height=\"100%\"
        out.println("</div>");
    }



}
