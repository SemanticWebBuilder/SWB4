/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.WebService;
import org.semanticwb.rest.consume.Method;
import org.semanticwb.rest.consume.Parameter;
import org.semanticwb.rest.consume.ParameterDefinition;
import org.semanticwb.rest.consume.ParameterValue;
import org.semanticwb.rest.consume.RepresentationResponse;
import org.semanticwb.rest.consume.RestSource;
import org.semanticwb.rest.consume.ServiceInfo;
import org.semanticwb.rest.consume.XmlResponse;

/**
 *
 * @author juan.fernandez
 */
public class WSMethodParameterConfig extends GenericResource {

    private Logger log = SWBUtils.getLogger(WSMethodParameterConfig.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        User usr = paramRequest.getUser();

        String suri = request.getParameter("suri");

        if (suri == null) {
            return;
        }

        PrintWriter out = response.getWriter();

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = ont.getGenericObject(suri);

        WebService wsrv = null;

        if (gobj instanceof WebService) {
            wsrv = (WebService) gobj;

            out.println("<div class=\"swbform\">");

            SWBResourceURL urlupd = paramRequest.getActionUrl();
            urlupd.setAction("updMethod");

            out.println("<form method=\"post\" action=\""+urlupd+"\">");
            out.println("<fieldset>");
            out.println("<legend>Lista de métodos definidos en el WebService</legend>");

            String urlwsrv = wsrv.getUrl();
            //System.out.println("WebService: " + wsrv.getDisplayTitle(usr.getLanguage()) + " url: " + urlwsrv);
            if (urlwsrv != null) {
                try {
                    RestSource source = new RestSource(new URL(urlwsrv));
                    if (source != null) {
                        ServiceInfo info = source.getServiceInfo();
                        Method[] methods = info.getMethods();

                        out.println("<ul style=\"list-style-type:none;\">");
                        out.println("<li>");
                        out.println("<laber for=\"\">Métodos: </label><select id=\"\" name=\"idmethod\">");
                        for (Method m : methods) {
                            Method minfo = m;
                            out.println("<option value=\""+minfo.getId()+"\">");
                            out.println(minfo.getId());
                            out.println("</option>");
                        }
                        out.println("</select>");
                        out.println("</li>");
                        out.println("</ul>");

                    } else {
                        log.error("Configuración de URL del WebService inválido.");
                        out.println("<fieldset>");
                        out.println("<h2>URL del WebService inválido.</h2>");
                        out.println("</fieldset>");
                    }


                } catch (Exception e) {
                    log.debug(e);
                }
                out.println("</fieldset>");
                out.println("<fieldset>");
                out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" >Guardar</button>");
                out.println("</fieldset>");
                out.println("</form>");
                out.println("</div>");
            } else {
                log.error("Falta definir URL del WebService");
                out.println("<fieldset>");
                out.println("<h2>Falta configurar el URL del WebService.</h2>");
                out.println("</fieldset>");
            }
        }


    }
}
