/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.WebService;
import org.semanticwb.process.model.WebServiceInvoker;
import org.semanticwb.process.model.WebServiceParameter;
import org.semanticwb.webservices.Operation;
import org.semanticwb.webservices.ParameterDefinition;
import org.semanticwb.webservices.Service;
import org.semanticwb.webservices.ServiceInfo;

import org.w3c.dom.Document;

/**
 *
 * @author juan.fernandez
 */
public class WSOperationParameterConfig extends GenericResource {

    private Logger log = SWBUtils.getLogger(WSOperationParameterConfig.class);

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

        WebServiceInvoker wsrvin = null;

        if (gobj instanceof WebServiceInvoker) {
            wsrvin = (WebServiceInvoker) gobj;
            String method = wsrvin.getMethod();
            if (method == null) {
                method = "";
            }

            Operation opersel = null;

            WebService wsrv = wsrvin.getWebService();
//            String wstype = getWSType(wsrv);
            if (wsrv != null) {
                out.println("<div class=\"swbform\">");

                SWBResourceURL urlupd = paramRequest.getActionUrl();
                urlupd.setAction("updMethod");

                long idform = System.currentTimeMillis();
                out.println("<form id=\"" + idform + "_wsrvin\" method=\"post\" action=\"" + urlupd + "\" onsubmit=\"submitForm('" + idform + "_wsrvin');return false;\">");
                out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\">");
                out.println("<fieldset>");
                out.println("<legend>Lista de operaciones definidas en el WebService</legend>");

                //Revisando tipo de web-service WADL o WSDL

                String urlwsrv = wsrv.getUrl();
                //System.out.println("WebService: " + wsrv.getDisplayTitle(usr.getLanguage()) + " url: " + urlwsrv);

                if (urlwsrv != null) {

                    try {
                        ServiceInfo sinfo = org.semanticwb.webservices.WebService.getServiceinfo(new URL(urlwsrv));

                        if (sinfo != null) {
                            out.println("<ul style=\"list-style-type:none;\">");
                            out.println("<li>");
                            out.println("<label for=\"idmethod\">Oparaciones: </label><select name=\"idmethod\">");

                            for (Service service : sinfo.getServices()) {

                                out.println("<optgroup>" + service.getId());

                                for (Operation op : service.getOperations()) {

                                    Operation opinfo = op;
                                    if (opinfo.getName().equals(method)) {
                                        opersel = opinfo;
                                    }
                                    out.println("<option value=\"" + opinfo.getName() + "\" " + (opinfo.getName().equals(method) ? "selected" : "") + ">");
                                    out.println(opinfo.getName());
                                    out.println("</option>");
                                }
                                out.println("</optgroup>");
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
            } else {

                log.error("Falta seleccionar el WebService");
                out.println("<fieldset>");
                out.println("<h2>Falta seleccionar el WebService.</h2>");
                out.println("</fieldset>");
            }

            if (!method.equals("") && method.length() > 0) {


                //System.out.println("Se tiene configurado una operación: " + method);

                long idform = System.currentTimeMillis();
                //Se tiene configurado un método, se muestran los parámetros correspondientes:
                if (wsrv != null) {

                    HashMap<String, String> hm = new HashMap<String, String>();
                    Iterator<WebServiceParameter> itwsp = wsrvin.listWebServiceParameters();
                    while (itwsp.hasNext()) {
                        WebServiceParameter webServiceParameter = itwsp.next();
                        hm.put(webServiceParameter.getParameterName(), webServiceParameter.getParameterValue());
                    }
                    String urlwsrv = wsrv.getUrl();
                    if (urlwsrv != null) {
                        try {
                            ServiceInfo info = org.semanticwb.webservices.WebService.getServiceinfo(new URL(urlwsrv));

                            SWBResourceURL urlupd = paramRequest.getActionUrl();
                            urlupd.setAction("updParameters");
                            out.println("<div class=\"swbform\">");
                            out.println("<form id=\"" + idform + "_wsrvin\" method=\"post\" action=\"" + urlupd + "\" onsubmit=\"submitForm('" + idform + "_wsrvin'); return false;\">");
                            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\">");
                            out.println("<input type=\"hidden\" name=\"idmethod\" value=\"" + method + "\">");

                            out.println("<fieldset>");
                            out.println("<legend>Lista de parámetros</legend>");
                            out.println("<table>");
                            out.println("<tbody>");

                            Operation operinfo = null;

                            for (Service service : info.getServices()) {
                                for (Operation operation : service.getOperations()) {
                                    if (operation.getName().equals(method)) {
                                        operinfo = operation;
                                        break;
                                    }
                                }
                            }

                            /// Mostrar lista de parámetros
                            ParameterDefinition[] params = operinfo.getInput().getDefinitions();

                            if (params.length > 0) {
                                //boolean isPREQ = Boolean.TRUE; //FALSE;
                                String isType = "";
                                for (ParameterDefinition allparam : params) {

                                    isType = "";
                                    isType = allparam.isBasic() ? "B" : "-";
                                    isType += allparam.isMultiple() ? "M" : "-";
                                    isType += allparam.isRequired() ? "R" : "-";
                                    out.println("<tr><td align=\"right\" valign=\"top\" width=\"200px\">");
                                    out.println("<label for=\"p_" + allparam.getName() + "\">");
                                    out.println(allparam.getName() + "[" + allparam.getDefinitionType() + "]&nbsp;(" + isType + "):");
                                    out.println("</label>");
                                    out.println("</td>");
                                    out.println("<td>");
                                    ///////////////////////////////////////////////////////
                                    if (!allparam.isBasic()) {
                                        out.println("<table>");
                                        for (ParameterDefinition paramdef : allparam.getProperties()) {
                                            isType = "";
                                            isType = paramdef.isBasic() ? "B" : "-";
                                            isType += paramdef.isMultiple() ? "M" : "-";
                                            isType += paramdef.isRequired() ? "R" : "-";
                                            String pvalue = hm.get(allparam.getName() + "." + paramdef.getName()) != null ? hm.get(allparam.getName() + "." + paramdef.getName()) : "";
                                            out.println("<tr>");
                                            out.println("<td>" + paramdef.getName() + " [" + paramdef.getDefinitionType() + "](" + isType + ")</td>");
                                            out.println("<td><input dojoType=\"dijit.form.TextBox\" name=\"p_" + allparam.getName() + "_" + paramdef.getName() + "\" type=\"text\" " + (paramdef.isRequired() ? "required=\"true\" invalidMessage=\"Valor del parámetro requerido.\" " : "") + " value=\"" + pvalue + "\"></td>");
                                            out.println("</tr>");
                                        }
                                        out.println("</table>");
                                    } else {
                                        ////////////////////////////////////////////////////////
                                        String pvalue = hm.get(allparam.getName()) != null ? hm.get(allparam.getName()) : "";
                                        out.println("<input dojoType=\"dijit.form.TextBox\" name=\"p_" + allparam.getName() + "\" type=\"text\" " + (allparam.isRequired() ? "required=\"true\" invalidMessage=\"Valor del parámetro requerido.\" " : "") + " value=\"" + pvalue + "\">");
                                    }
                                    out.println("</td>");
                                    out.println("</tr>");
                                }

                            }

                            out.println("</tbody>");
                            out.println("</table>");
                            out.println("</fieldset>");
                            out.println("<fieldset>");
                            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" >Guardar</button>");
                            out.println("</fieldset>");
                            out.println("<fieldset>");
                            out.println("* Notación: [Tipo definición] (B:Básico, M:Múltiple, R:Requerido)");
                            out.println("</fieldset>");
                            out.println("</form>");
                            out.println("</div>");

                        } catch (Exception e) {
                            log.debug(e);
                        }
                    }
                } else {
                    log.error("Falta seleccionar el WebService");
                    out.println("<fieldset>");
                    out.println("<h2>Falta seleccionar el WebService.</h2>");
                    out.println("</fieldset>");
                }
            }
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {

        String act = response.getAction();
        if (act == null) {
            act = "";
        }

        WebSite ws = response.getWebPage().getWebSite();
        User usr = response.getUser();

        String suri = request.getParameter("suri");

        if (suri == null) {
            return;
        }

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = ont.getGenericObject(suri);

        WebServiceInvoker wsrvin = null;

        if (act.equals("updMethod")) {

            if (gobj != null) {
                if (gobj instanceof WebServiceInvoker) {
                    wsrvin = (WebServiceInvoker) gobj;
                    String idMethod = request.getParameter("idmethod");
                    wsrvin.setMethod(idMethod);
                }
            }
        } else if (act.equals("updParameters")) {

            if (gobj != null) {
                if (gobj instanceof WebServiceInvoker) {
                    wsrvin = (WebServiceInvoker) gobj;

                    String idMethod = request.getParameter("idmethod");

                    if (idMethod != null && !idMethod.equals(wsrvin.getMethod())) {
                        // borramos parametros existentes
                        wsrvin.removeAllWebServiceParameter();
                    }

                    WebService wsrv = wsrvin.getWebService();

                    String urlwsrv = wsrv.getUrl();

                    HashMap<String, WebServiceParameter> hm = new HashMap<String, WebServiceParameter>();
                    Iterator<WebServiceParameter> itwsp = wsrvin.listWebServiceParameters();
                    while (itwsp.hasNext()) {
                        WebServiceParameter webServiceParameter = itwsp.next();
                        hm.put(webServiceParameter.getParameterName(), webServiceParameter);
//                        System.out.println("processaction ite:" + webServiceParameter.getParameterName());
                    }

                    try {
                        ServiceInfo info = org.semanticwb.webservices.WebService.getServiceinfo(new URL(urlwsrv));
                        if (info != null) {

                            Operation operinfo = null;

                            for (Service service : info.getServices()) {
                                for (Operation operation : service.getOperations()) {
                                    if (operation.getName().equals(idMethod)) {
                                        operinfo = operation;
                                        break;
                                    }
                                }
                            }

                            /// Mostrar lista de parámetros
                            ParameterDefinition[] params = operinfo.getInput().getDefinitions();
                            if (params.length > 0) {

                                for (ParameterDefinition allparam : params) {

                                    String paramName = allparam.getName();
                                    String paramVal = request.getParameter("p_" + paramName);

                                    if (allparam.isBasic()) {
                                        try {
                                            WebServiceParameter wsp = hm.get(paramName);

                                            if (wsp == null) {
                                                wsp = WebServiceParameter.ClassMgr.createWebServiceParameter(wsrvin.getProcessSite());
                                                wsp.setParameterName(paramName);
                                                wsrvin.addWebServiceParameter(wsp);
                                            }
                                            if (paramVal != null && paramVal.trim().length() > 0) {
                                                wsp.setParameterValue(paramVal);
                                            } else {
                                                wsrvin.removeWebServiceParameter(wsp);
                                                wsp.remove();
                                            }
                                        } catch (Exception ein) {
                                            log.error("Error al crear WebServiceParameter", ein);
                                        }
                                    } else {


                                        for (ParameterDefinition paramdef : allparam.getProperties()) {
                                            paramName = allparam.getName() + "." + paramdef.getName();
                                            paramVal = request.getParameter("p_" + allparam.getName() + "_" + paramdef.getName());

                                            try {
                                                WebServiceParameter wsp = hm.get(paramName);

                                                if (wsp == null) {
                                                    wsp = WebServiceParameter.ClassMgr.createWebServiceParameter(wsrvin.getProcessSite());
                                                    wsp.setParameterName(paramName);
                                                    wsrvin.addWebServiceParameter(wsp);
                                                }
                                                if (paramVal != null && paramVal.trim().length() > 0) {
                                                    wsp.setParameterValue(paramVal);
                                                } else {
                                                    wsrvin.removeWebServiceParameter(wsp);
                                                    wsp.remove();
                                                }
                                            } catch (Exception ein) {
                                                log.error("Error al crear WebServiceParameter", ein);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            }
        }
        if (suri != null) {
            response.setRenderParameter("suri", suri);
        }
        response.setAction("");
        response.setMode(SWBResourceURL.Mode_VIEW);
    }
}
