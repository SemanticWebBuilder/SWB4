/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
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
            out.println("<legend>Lista de m&eacute;todos definidos en el WebService</legend>");
            out.println("<table width=\"100%\" border=\"1\">");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>");
            out.println("Id Servicio - Operaci&oacute;n");
            out.println("</th>");
            out.println("<th>");
            out.println("Par&aacute;metros Entrada");
            out.println("</th>");
            out.println("<th>");
            out.println("Par&aacute;metros Salida");
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

                        //System.out.println("service.getId():" + service.getId());

                        for (Operation operation : service.getOperations()) {

                            //System.out.println("operation.getName():" + operation.getName());

                            out.println("<tr>");
                            out.println("<td valign=\"top\">");
                            out.println(service.getId() + " - " + operation.getName());
                            out.println("</td>");
                            out.println("<td valign=\"top\">");
                            out.println("<ul>");
                            try {
                                for (ParameterDefinition allparam : operation.getInput().getDefinitions()) {
                                    isType = "";
                                    isType = allparam.isBasic() ? "B" : "-";
                                    isType += allparam.isMultiple() ? "M" : "-";
                                    isType += allparam.isRequired() ? "R" : "-";

                                    out.println("<li>" + allparam.getName() + " [" + allparam.getDefinitionType() + "](" + isType + ")");
                                    if(!allparam.isBasic()){
                                        out.println("<ul>");
                                        for(ParameterDefinition paramdef: allparam.getProperties()){
                                            isType = "";
                                            isType = paramdef.isBasic() ? "B" : "-";
                                            isType += paramdef.isMultiple() ? "M" : "-";
                                            isType += paramdef.isRequired() ? "R" : "-";
                                            out.println("<li>" + paramdef.getName() + " [" + paramdef.getDefinitionType() + "](" + isType + ")");
                                        }
                                        out.println("</ul>");
                                    }
                                    out.println("</li>");
                                }
                            } catch (Exception e) {
                                log.error("Error al cargar par&aacute;metros de entrada.", e);
                            }

                            out.println("</ul>");

                            out.println("</td>");
                            out.println("<td valign=\"top\">");
                            out.println("<ul>");
                            try {
                                for (ParameterDefinition allparam : operation.getOutput().getDefinitions()) {

                                    isType = "";
                                    isType = allparam.isBasic() ? "B" : "-";
                                    isType += allparam.isMultiple() ? "M" : "-";
                                    isType += allparam.isRequired() ? "R" : "-";
                                    out.println("<li>" + allparam.getName() + " [" + allparam.getDefinitionType() + "](" + isType + ")");
                                    if(!allparam.isBasic()){
                                        out.println("<ul>");
                                        for(ParameterDefinition paramdef: allparam.getProperties()){
                                            isType = "";
                                            isType = paramdef.isBasic() ? "B" : "-";
                                            isType += paramdef.isMultiple() ? "M" : "-";
                                            isType += paramdef.isRequired() ? "R" : "-";
                                            out.println("<li>" + paramdef.getName() + " [" + paramdef.getDefinitionType() + "](" + isType + ")");
                                        }
                                        out.println("</ul>");
                                    }
                                    out.println("</li>");

                                }
                            } catch (Exception e) {
                                log.error("Error al cargar par&aacute;metros de salida.", e);
                            }

                            out.println("</ul>");

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
                out.println("* Notaci&oacute;n: [Tipo definici&oacute;n] (B:B&aacute;sico, M:M&uacute;ltiple, R:Requerido)");
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
