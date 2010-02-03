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
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author juan.fernandez
 */
public class Modeler extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<h1>Process Modeler</h1>");
        out.println("<script src=\"http://dl.javafx.com/1.2/dtfx.js\"></script>");
        out.println("<script>");
        out.println("    javafx(");
        out.println("      {");
        out.println("        archive: \""+ SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAppBPMNModeler.jar\",");
        out.println("        draggable: true,");
        out.println("        width: 800,");
        out.println("        height: 600,");
        out.println("        code: \"org.semanticwb.process.modeler.Main\",");
        out.println("        name: \"SWBAppBPMNModeler\"");
        out.println("      }");
        out.println("   );");
        out.println("</script>");
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        super.processAction(request, response);
    }
}
