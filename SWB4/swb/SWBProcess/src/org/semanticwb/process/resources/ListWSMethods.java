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
public class ListWSMethods extends GenericResource {

    private Logger log = SWBUtils.getLogger(ListWSMethods.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        User usr = paramRequest.getUser();

        String suri = request.getParameter("suri");

        //System.out.println("suri: " + suri);

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
            out.println("Id");
            out.println("</th>");
            out.println("<th>");
            out.println("Parámetros");
            out.println("</th>");
            out.println("<th>");
            out.println("P. Requeridos");
            out.println("</th>");
            out.println("<th>");
            out.println("P. Opcionales");
            out.println("</th>");
            out.println("</tr>");
            out.println("</thead>");


            String urlwsrv = wsrv.getUrl();
            //System.out.println("WebService: " + wsrv.getDisplayTitle(usr.getLanguage()) + " url: " + urlwsrv);
            if (urlwsrv != null) {
                try {
                    RestSource source = new RestSource(new URL(urlwsrv));
                    if (source != null) {
//                        System.out.println("Source: " + source.toString());
                        ServiceInfo info = source.getServiceInfo();

                        out.println("<tbody>");
                        Method[] methods = info.getMethods();
//                        System.out.println("getMethods( "+methods.length+" )");
                        for (Method m : methods) {
//                            System.out.println("method " + m.getId());

                            out.println("<tr>");

                            Method minfo = m; //info.getMethod("swbcomm_EventElementlistEventElements");
                            out.println("<td valign=\"top\">");
                            out.println(minfo.getId());
                            out.println("</td>");

                            out.println("<td valign=\"top\">");
                            Parameter[] params = minfo.getAllParameters();
                            int c = 0;
                            if (params.length > 0) {
                                out.println("<ul>");
                                for (Parameter allparam : params) {
                                    c++;
                                    out.println("<li>"+allparam.getName()+"</li>");
//                                    if (c < params.length) {
//                                        out.print("<br>");
//                                    }
                                }
                                out.println("</ul>");
                            } else {
                                out.println("---");
                            }
                            out.println("</td>");
                            out.println("<td valign=\"top\">");
                            params = minfo.getRequiredParameters();
                            c = 0;
                            if (params.length > 0) {
                                out.println("<ul>");
                                for (Parameter allparam : params) {
                                    c++;
                                    out.println("<li>"+allparam.getName()+"</li>");
//                                    if (c < params.length) {
//                                        out.print("<br>");
//                                    }
                                }
                                out.println("</ul>");
                            } else {
                                out.println("---");
                            }
                            out.println("</td>");
                            out.println("<td valign=\"top\">");
                            params = minfo.getOptionalParameters();
                            c = 0;
                            if (params.length > 0) {
                                out.println("<ul>");
                                for (Parameter allparam : params) {
                                    c++;
                                    out.println("<li>"+allparam.getName()+"</li>");
//                                    if (c < params.length) {
//                                        out.print("<br>");
//                                    }
                                }
                                out.println("</ul>");
                            } else {
                                out.println("---");
                            }
                            out.println("</td>");

//                            minfo.
//
//                            ArrayList<ParameterValue> values = new ArrayList<ParameterValue>();
//                            RepresentationResponse resp = minfo.request(values);
//                            ParameterDefinition[] parameters = resp.getParameterDefinitions();
//                            if (resp instanceof XmlResponse) {
//                                for (ParameterDefinition parameter : parameters) {
//                                    XmlResponse xmlResponse = (XmlResponse) resp;
//                                    URL[] objs = xmlResponse.getLinks(parameter);
//                                    for (URL obj : objs) {
//                                        System.out.println("obj: " + obj);
//                                    }
//                                }
//                            }
                            out.println("</tr>");
                        }
                        out.println("</tbody>");


                    } else {
                        log.error("Configuración de URL del WebService inválido.");
                        out.println("<fieldset>");
                        out.println("<h2>URL del WebService inválido.</h2>");
                        out.println("</fieldset>");
                    }


                } catch (Exception e) {
                    log.debug(e);
                }
                out.println("</table>");
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
