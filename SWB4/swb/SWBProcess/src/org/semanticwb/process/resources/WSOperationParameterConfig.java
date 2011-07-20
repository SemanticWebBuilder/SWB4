/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Enumeration;
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
                ////System.out.println("WebService: " + wsrv.getDisplayTitle(usr.getLanguage()) + " url: " + urlwsrv);

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

                int nivel = 0;
                if (null != request.getParameter("nivel")) {
                    nivel = Integer.parseInt(request.getParameter("nivel"));
                }

                String clickedObj = request.getParameter("clickedObj");

                //System.out.println("Nivel: " + nivel);

                long idform = System.currentTimeMillis();
                //Se tiene configurado un método, se muestran los parámetros correspondientes:
                if (wsrv != null) {

                    HashMap<String, String> hm = new HashMap<String, String>();
                    Iterator<WebServiceParameter> itwsp = wsrvin.listWebServiceParameters();
                    while (itwsp.hasNext()) {
                        WebServiceParameter webServiceParameter = itwsp.next();

                        //System.out.println("ws paramName: " + webServiceParameter.getParameterName() + ", " + webServiceParameter.getParameterValue());

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

                            out.println("<tr>");
                            out.println("<td align=\"left\" colspan=\"2\">");
                            SWBResourceURL url0 = paramRequest.getRenderUrl();
                            url0.setParameter("suri", suri);
                            url0.setParameter("idmethod", method);
                            url0.setParameter("nivel", "0");
                            out.println("<a href=\"#\" onclick=\"submitUrl('" + url0 + "',this);return false;\">Iniciales</a>");
                            String pnames = request.getParameter("params");
                            if (null == pnames) {
                                pnames = "";
                            }
                            String[] insideparams = pnames.split(",");
                            int cont = 1;
                            if (null != insideparams) {
                                for (String param : insideparams) {
                                    if (cont <= insideparams.length) {
                                        out.println("&nbsp;|&nbsp;");
                                    }
                                    SWBResourceURL urlp = paramRequest.getRenderUrl();
                                    urlp.setParameter("suri", suri);
                                    urlp.setParameter("idmethod", method);
                                    urlp.setParameter("nivel", "" + cont);
                                    out.println("<a href=\"#\" onclick=\"submitUrl('" + urlp + "',this);return false;\">" + param + "</a>");
                                    cont++;
                                }
                            }

                            out.println("</td>");
                            out.println("</tr>");

                            /// Mostrar lista de parámetros
                            ParameterDefinition[] params = operinfo.getInput().getDefinitions();
                            if (nivel > 0 && clickedObj != null) {
                                ParameterDefinition pdef = findParDef(operinfo, null, nivel-1, pnames, 0);
                                params = pdef.getProperties();
                                //System.out.println("Salio de findParDef..."+params.length);
                            }




                            if (params.length > 0) {

                                String isType = "";
                                out.println("<tr>");
                                out.println("<td colspan=\"2\" width=\"100%\">");
                                out.println("<table width=\"100%\">");
                                out.println("<thead>");
                                out.println("<tr>");
                                out.println("<th>");
                                out.println("Nombre");
                                out.println("</th>");
                                out.println("<th>");
                                out.println("Tipo");
                                out.println("</th>");
                                out.println("<th>");
                                out.println("Básico");
                                out.println("</th>");
                                out.println("<th>");
                                out.println("Múltiple");
                                out.println("</th>");
                                out.println("<th>");
                                out.println("Requerido");
                                out.println("</th>");
                                out.println("<th>");
                                out.println("&nbsp;");
                                out.println("</th>");
                                out.println("</tr>");
                                out.println("</thead>");
                                out.println("<tbody>");



                                for (ParameterDefinition allparam : params) {

                                    String key = (clickedObj != null ? clickedObj + "." : "");
                                    key = key.replace("_", ".");

                                    key = key + allparam.getName() + "." + allparam.getDefinitionType();

                                    //System.out.println("key: " + key);

                                    String pvalue = hm.get(key) != null ? hm.get(key) : "";
                                    out.println("<tr>");
                                    out.println("<td>" + allparam.getName() + "</td>");
                                    out.println("<td>");
                                    if (!allparam.isBasic()) {

                                        //todo: falta considerar nivel como parámetro

                                        SWBResourceURL urldetail = paramRequest.getRenderUrl();
                                        urldetail.setParameter("suri", suri);
                                        urldetail.setParameter("idmethod", method);
                                        urldetail.setParameter("params", (pnames.length() > 0 ? pnames + "," : "") + allparam.getName());
                                        urldetail.setParameter("clickedObj", allparam.getName());
                                        urldetail.setParameter("nivel", "" + (nivel + 1));
                                        out.println("<a href=\"#\" onclick=\"submitUrl('" + urldetail + "',this); return false;\">" + allparam.getDefinitionType() + "</a>");
                                    } else {
                                        out.println(allparam.getDefinitionType());
                                    }

                                    out.println("</td>");

                                    out.println("<td>" + (allparam.isBasic() ? "Sí" : "No") + "</td>");
                                    out.println("<td>" + (allparam.isMultiple() ? "Sí" : "No") + "</td>");
                                    out.println("<td>" + (allparam.isRequired() ? "Sí" : "No") + "</td>");
                                    out.println("<td>");

                                    String tmpClick = pnames.replace(" ", "");
                                    tmpClick = tmpClick.replaceAll(",", ".");


                                    if (allparam.isBasic()) {
                                        out.println("<input dojoType=\"dijit.form.TextBox\" name=\"p_" + (tmpClick != null ? tmpClick + "." : "") + allparam.getName() + "_" + allparam.getDefinitionType() + "\" type=\"text\" " + (allparam.isRequired() ? "required=\"true\" invalidMessage=\"Valor del parámetro requerido.\" " : "") + " value=\"" + pvalue + "\">");
                                    } else {
                                        out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                                    }
                                    out.println("</td>");
                                    out.println("</tr>");
                                }
                                out.println("</tbody>");
                                out.println("</table>");
                                out.println("</td>");
                                out.println("</tr>");
                            }
                            out.println("</tbody>");
                            out.println("</table>");
                            out.println("</fieldset>");
                            out.println("<fieldset>");
                            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" >Guardar</button>");
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

    private ParameterDefinition findParDef(Operation oper, ParameterDefinition paramDef, int nivel, String pnames, int currentlevel) {
        ParameterDefinition[] defs = null;
        String[] data = pnames.split(",");

        ParameterDefinition pdret = null;
        //System.out.println(" params: " + pnames);
            defs = oper.getInput().getDefinitions();
            for (ParameterDefinition pdef : defs) {
                if (pdef.getName().equals(data[currentlevel]) && currentlevel < nivel) {
                    pdret = findParDef(oper, pdef, nivel, pnames, currentlevel + 1);
                    break;
                } else if (pdef.getName().equals(data[currentlevel]) && currentlevel == nivel ) {
                    pdret = pdef;
                    break;
                }
            }
        return pdret;
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
                    String tmpMethod = wsrvin.getMethod();
                    wsrvin.setMethod(idMethod);
                    if (!idMethod.equals(tmpMethod)) {
                        wsrvin.removeAllWebServiceParameter();
                    }
                }
            }
        } else if (act.equals("updParameters")) {

            if (gobj != null) {
                if (gobj instanceof WebServiceInvoker) {
                    wsrvin = (WebServiceInvoker) gobj;

                    String idMethod = request.getParameter("idmethod");

                    WebService wsrv = wsrvin.getWebService();

                    String urlwsrv = wsrv.getUrl();

                    HashMap<String, WebServiceParameter> hm = new HashMap<String, WebServiceParameter>();
                    Iterator<WebServiceParameter> itwsp = wsrvin.listWebServiceParameters();
                    while (itwsp.hasNext()) {
                        WebServiceParameter webServiceParameter = itwsp.next();
                        hm.put(webServiceParameter.getParameterName(), webServiceParameter);
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
                            int nivel = 0;
                            try {
                                nivel = Integer.parseInt(request.getParameter("nivel"));
                            } catch (Exception e) {
                            }
                            String clickedObj = request.getParameter("clickedObj");

                            Enumeration<String> enu = request.getParameterNames();

                            while (enu.hasMoreElements()) {
                                String parametro = enu.nextElement();
                                String paramvalue = request.getParameter(parametro);
                                //System.out.println("( " + parametro + " ) ==> " + paramvalue);

                                if (parametro.startsWith("p_")) {
                                    try {
                                        String ptmp = parametro.substring(parametro.indexOf("p_") + 2);

                                        ptmp = ptmp.replace("-", ".");
                                        ptmp = ptmp.replace("_", ".");
                                        //System.out.println("parametro: " + ptmp);
                                        //ptmp = (clickedObj!=null?clickedObj+".":"");
                                        ////System.out.println("ptmp:"+clickedObj+"."+ptmp);
                                        WebServiceParameter wsp = hm.get(ptmp);

                                        if (wsp == null) {
                                            //System.out.println("wsp null => creando webServiceParameter ...");
                                            wsp = WebServiceParameter.ClassMgr.createWebServiceParameter(wsrvin.getProcessSite());
                                            wsp.setParameterName(ptmp);

                                            System.out.print("Parameter : " + ptmp);

                                            wsrvin.addWebServiceParameter(wsp);

                                        }
                                        if (paramvalue != null && paramvalue.trim().length() > 0) {
                                            wsp.setParameterValue(paramvalue);

                                            //System.out.println(", value: " + paramvalue);
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
