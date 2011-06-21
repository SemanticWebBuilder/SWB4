/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
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
import org.semanticwb.process.model.WebService;
import org.semanticwb.webservices.Operation;
import org.semanticwb.webservices.ParameterDefinition;
import org.semanticwb.webservices.Service;
import org.semanticwb.webservices.ServiceInfo;

/**
 *
 * @author juan.fernandez
 */
public class ListWSOperations extends GenericResource {

    private Logger log = SWBUtils.getLogger(ListWSOperations.class);

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
            out.println("<fieldset>");
            out.println("<legend>Lista de métodos definidos en el WebService</legend>");
            out.println("<table width=\"100%\" border=\"1\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println("Id Servicio - Operacion");
            out.println("</th>");
            out.println("<th>");
            out.println("Parámetros Entrada");
            out.println("</th>");
            out.println("<th>");
            out.println("Parámetros Salida");
            out.println("</th>");
            out.println("</tr>");
            out.println("</thead>");


            String urlwsrv = wsrv.getUrl();
            if (urlwsrv != null) {
                try {
                    ServiceInfo info = org.semanticwb.webservices.WebService.getServiceinfo(new URL(urlwsrv)); 
                    out.println("<tbody>");

                    String isType = "";

                    for (Service service : info.getServices()) {
                        for (Operation operation : service.getOperations()) {
                            out.println("<tr>");
                            out.println("<td valign=\"top\">");
                            out.println(service.getId()+" - "+operation.getName());
                            out.println("</td>");
                            out.println("<td valign=\"top\">");
                            ParameterDefinition[] params = operation.getInput().getDefinitions();
                            if (params.length > 0) {
                                out.println("<ul>");
                                for (ParameterDefinition allparam : params) {
                                    isType = "";
                                    isType = allparam.isBasic()?"B":"-";
                                    isType += allparam.isMultiple()?"M":"-";
                                    isType += allparam.isRequired()?"R":"-";

                                    out.println("<li>" + allparam.getName() + " ["+allparam.getDefinitionType()+"]("+isType+")</li>");
                                }
                                out.println("</ul>");
                            } else {
                                out.println("---");
                            }
                            out.println("</td>");
                            out.println("<td valign=\"top\">");
                            params = operation.getOutput().getDefinitions();
                            if (params.length > 0) {
                                out.println("<ul>");
                                for (ParameterDefinition allparam : params) {
                                    isType = "";
                                    isType = allparam.isBasic()?"B":"-";
                                    isType += allparam.isMultiple()?"M":"-";
                                    isType += allparam.isRequired()?"R":"-";
                                    out.println("<li>" + allparam.getName() + " ["+allparam.getDefinitionType()+"]("+isType+")</li>");
                                }
                                out.println("</ul>");
                            } else {
                                out.println("---");
                            }
                            out.println("</td>");
                            out.println("</tr>");
                        }
                    }
                    out.println("</tbody>");

                } catch (Exception e) {
                    log.debug(e);
                }
                out.println("</table>");
                out.println("</fieldset>");
                out.println("<fieldset>");
                out.println("* Notación: [Tipo definición] (B:Básico, M:Múltiple, R:Requerido)");
                out.println("</fieldset>");
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
