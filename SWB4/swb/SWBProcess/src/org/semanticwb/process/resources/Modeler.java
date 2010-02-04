/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
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

        out.println("<div id=\"SWBAppBPMNModeler_"+getResourceBase().getId()+"\" class=\"applet\" />");
        String versionNumber="1.2.3_b36";
        out.println("<script type=\"text/javascript\">");
        out.println("var OSName=\"Unknown OS\";");
        out.println("if (navigator.appVersion.indexOf(\"Win\")!=-1) OSName=\"javafx-rt-windows-i586\";");
        out.println("if (navigator.appVersion.indexOf(\"Mac\")!=-1) OSName=\"javafx-rt-macosx-universal\";");
        out.println("if (navigator.appVersion.indexOf(\"X11\")!=-1) OSName=\"javafx-rt-solaris-i586\";");
        out.println("if (navigator.appVersion.indexOf(\"Linux\")!=-1) OSName=\"javafx-rt-linux-i586\";");
        //out.println("alert(OSName);");
        out.println("var str_applet = '<applet height=\"500\" width=\"100%\" archive=\""+ SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAppBPMNModeler.jar,http://dl.javafx.com/applet-launcher__V"+versionNumber+".jar,http://dl.javafx.com/'+OSName+'__V"+versionNumber+".jar,http://dl.javafx.com/emptyJarFile-1265174763440__V"+versionNumber+".jar\" code=\"org.jdesktop.applet.util.JNLPAppletLauncher\" mayscript=\"\"/>';");
        //out.println("<applet height=\"500\" width=\"100%\" archive=\""+ SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAppBPMNModeler.jar,http://dl.javafx.com/applet-launcher__V1.2.3_b36.jar,http://dl.javafx.com/"+jar_str+"__V"+versionNumber+".jar,http://dl.javafx.com/emptyJarFile-1265174763440__V"+versionNumber+".jar\" code=\"org.jdesktop.applet.util.JNLPAppletLauncher\" mayscript=\"\" border=\"1\"/>");
        out.println("str_applet = str_applet + ' <param name=\"subapplet.classname\" value=\"com.sun.javafx.runtime.adapter.Applet\">';");
        out.println("str_applet = str_applet + ' <param name=\"MainJavaFXScript\" value=\"org.semanticwb.process.modeler.Main\">';");
        out.println("document.getElementById(\"SWBAppBPMNModeler_"+getResourceBase().getId()+"\").innerHTML=str_applet;");
        //out.println("alert(document.getElementById(\"SWBAppBPMNModeler_"+getResourceBase().getId()+"\").innerHTML);");
        //out.println("document.write('Your OS: '+OSName);");
        out.println("</script>");



//        String os = System.getProperty("os.name").toLowerCase();
//        String jar_str = ""; // default windows
//        if(os.indexOf( "win" ) >= 0)
//        {
//            //System.out.println("OS is Windows");
//            jar_str = "javafx-rt-windows-i586";
//        }
//        else if(os.indexOf( "mac" ) >= 0)
//        {
//            //System.out.println("OS is Mac OSX");
//            jar_str = "javafx-rt-macosx-universal";
//        }
//        else if(os.indexOf( "nix")>=0 || os.indexOf( "solaris")>=0)
//        {
//            //System.out.println("OS is Unix or Solaris");
//            jar_str = "javafx-rt-solaris-i586";
//        }
//        else if(os.indexOf( "nux") >=0)
//        {
//            //System.out.println("OS is Linux");
//            jar_str = "javafx-rt-linux-i586";
//        }
//        else
//        {
//            //System.out.println("OS is not supported!!, default config: Windows.");
//            jar_str = "javafx-rt-windows-i586"; // default windows";
//        }

        //String versionNumber="1.2.3_b36";

        //out.println("<div id=\"SWBAppBPMNModeler\" class=\"applet\">");
        //out.println("<applet height=\"500\" width=\"100%\" archive=\""+ SWBPlatform.getContextPath() + "/swbadmin/lib/SWBAppBPMNModeler.jar,http://dl.javafx.com/applet-launcher__V1.2.3_b36.jar,http://dl.javafx.com/"+jar_str+"__V"+versionNumber+".jar,http://dl.javafx.com/emptyJarFile-1265174763440__V"+versionNumber+".jar\" code=\"org.jdesktop.applet.util.JNLPAppletLauncher\" mayscript=\"\" border=\"1\"/>");
        //out.println("<param name=\"subapplet.classname\" value=\"com.sun.javafx.runtime.adapter.Applet\">");
        //out.println("<param name=\"MainJavaFXScript\" value=\"org.semanticwb.process.modeler.Main\">");
        //out.println("</div>");

    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        super.processAction(request, response);
    }
}
