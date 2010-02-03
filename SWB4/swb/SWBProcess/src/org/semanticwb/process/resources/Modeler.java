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
        //out.println("<h1>Process Modeler</h1>");

//        out.println("<div dojoType=\"dijit.layout.ContentPane\" postCreate=\"");
//        //out.println("<script type=\"dojo/connect\">");
//        out.println("    alert('hola');");
//        out.println("    javafx(");
//        out.println("      {");
//        out.println("        archive: '"+ SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAppBPMNModeler.jar',");
//        out.println("        draggable: true,");
//        out.println("        width: 800,");
//        out.println("        height: 600,");
//        out.println("        code: 'org.semanticwb.process.modeler.Main',");
//        out.println("        name: 'SWBAppBPMNModeler'");
//        out.println("      }");
//        out.println("   );");
//        //out.println("</script>");
//        out.println("\" />");

/*
    case"Mac":
        standardArchives.push("javafx-rt-macosx-universal");
        break;
    case"Windows":
        standardArchives.push("javafx-rt-windows-i586");
        break;
    case"Linux":
        standardArchives.push("javafx-rt-linux-i586");
        break;
    case"SunOS":
        standardArchives.push("javafx-rt-solaris-i586");
        break;

    var versionNumber="1.2.3_b36";
 */

        out.println("<div id=\"SWBAppBPMNModeler\" class=\"applet\">");
        out.println("<applet height=\"500\" width=\"100%\" archive=\""+ SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAppBPMNModeler.jar,http://dl.javafx.com/applet-launcher__V1.2.3_b36.jar,http://dl.javafx.com/javafx-rt-windows-i586__V1.2.3_b36.jar,http://dl.javafx.com/emptyJarFile-1265174763440__V1.2.3_b36.jar\" code=\"org.jdesktop.applet.util.JNLPAppletLauncher\" mayscript=\"\"/>");
        out.println("<param name=\"subapplet.classname\" value=\"com.sun.javafx.runtime.adapter.Applet\">");
        out.println("<param name=\"MainJavaFXScript\" value=\"org.semanticwb.process.modeler.Main\">");
        out.println("</div>");

    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        super.processAction(request, response);
    }
}
