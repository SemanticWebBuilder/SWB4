/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.ProcessInstance;
import org.w3c.dom.Document;

/**
 *
 * @author jei
 */
public class ViewModeler extends Modeler
{
    private static Logger log = SWBUtils.getLogger(ViewModeler.class);

    @Override
    public void doGateway(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        ServletInputStream in = request.getInputStream();
        Document dom = SWBUtils.XML.xmlToDom(in);

        if (!dom.getFirstChild().getNodeName().equals("req")) {
            response.sendError(404, request.getRequestURI());
            return;
        }

        String cmd = null;
        if (dom.getElementsByTagName("cmd").getLength() > 0) {
            cmd = dom.getElementsByTagName("cmd").item(0).getFirstChild().getNodeValue();
        }
        if (cmd == null) {
            response.sendError(404, request.getRequestURI());
            return;
        }

        if (cmd.equals("getProcessJSON")) {
            try {
                SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                GenericObject go = ont.getGenericObject(request.getParameter("suri"));
                org.semanticwb.process.model.Process process = null;
                if (go != null && go instanceof org.semanticwb.process.model.Process) {
                    process = (org.semanticwb.process.model.Process) go;
                    String json = getProcessJSON(process).toString();
                    out.print(json);
                } else {
                    log.error("Error to create JSON: Process not found");
                    out.print("ERROR: Process not found");
                }
            } catch (Exception e) {
                log.error("Error to create JSON...", e);
                out.print("ERROR:" + e.getMessage());
            }
        } else {
            String ret;
            Document res = null;//getService(cmd, dom, paramRequest.getUser(), request, response, paramRequest);
            if (res == null) {
                ret = SWBUtils.XML.domToXml(getError(3));
            } else {
                ret = SWBUtils.XML.domToXml(res, true);
            }
            out.print(new String(ret.getBytes()));
        }
    }

    @Override
    public void doApplet(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        String suri = request.getParameter("suri");
        SemanticObject sobj=SemanticObject.createSemanticObject(suri);
        System.out.println("doApplet:"+suri+" "+sobj);
        if(sobj==null)return;

        ProcessInstance pinst=(ProcessInstance)sobj.createGenericInstance();
        org.semanticwb.process.model.Process process=pinst.getProcessType();

        String donePath="";
        String curAct="";

        //pinst.l

        PrintWriter out = response.getWriter();
        SWBResourceURL urlapp = paramsRequest.getRenderUrl();
        urlapp.setMode("gateway");
        urlapp.setCallMethod(urlapp.Call_DIRECT);
        urlapp.setParameter("suri", process.getURI());

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
        out.println("              width: \"100%\",");
        out.println("              height: \"100%\",");
        out.println("              code: \"org.semanticwb.process.modeler.Main\",");
        out.println("              name: \"SWBAppBPMNModeler\"");
        out.println("        },");
        out.println("        {");
        out.println("              lang: \"" + paramsRequest.getUser().getLanguage() + "\",");
        out.println("              jsess: \"" + request.getSession().getId() + "\",");
        out.println("              cgipath: \"" + urlapp + "\",");
        out.println("              mode: \"view\",");
        out.println("              donePath: \""+donePath+"\",");
        out.println("              currentActivities: \""+curAct+"\"");
        out.println("        }");
        out.println("    );");
        out.println("    </script>");
        out.println("  </div>");
        out.println(" </body>");
        out.println("</html>");
    }



}
