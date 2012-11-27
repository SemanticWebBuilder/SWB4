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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.ItemAware;
import org.semanticwb.process.model.ProcessService;
import org.semanticwb.process.model.ServiceTask;
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

        Resource base = getResourceBase();

        User usr = paramRequest.getUser();

        String suri = request.getParameter("suri");

        if (suri == null) {
            return;
        }

        String pnames = request.getParameter("params");
        if (null == pnames) {
            pnames = "";
        }

        String poutnames = request.getParameter("params_out");
        if (null == poutnames) {
            poutnames = "";
        }

        PrintWriter out = response.getWriter();

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = ont.getGenericObject(suri);

        HashMap<String, String> hmsc = new HashMap();
        HashMap<String, SemanticProperty> hmprops = new HashMap();
        //HashMap<String, SemanticProperty> hmselected = new HashMap();

        ServiceTask pser = null;
        WebServiceInvoker wsrvin = null;

        if (gobj instanceof WebServiceInvoker) {
            wsrvin = (WebServiceInvoker) gobj;
            String method = wsrvin.getMethod();
            if (method == null) {
                method = "";
            }

            pser = wsrvin.getServiceTask();
            if (pser != null) {
                Iterator<ItemAware> it = pser.listHerarquicalRelatedItemAwarePlusNullOutputs().iterator(); //listHerarquicalRelatedItemAwarePlusNullOutputs()
                while (it.hasNext()) {
                    ItemAware item = it.next();
                    SemanticClass cls = item.getItemSemanticClass();
                    if (cls != null) {
                        Iterator<SemanticProperty> itp = cls.listProperties();
                        while (itp.hasNext()) {
                            SemanticProperty prop = itp.next();
                            String name = item.getName() + "|" + prop.getPropId();
                            hmsc.put(name, prop.getPropertyCodeName());
                            hmprops.put(name, prop);
                        }
                    }
                }
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

                int nivel_out = 0;
                if (null != request.getParameter("nivel_out")) {
                    nivel_out = Integer.parseInt(request.getParameter("nivel_out"));
                }

                String clickedObj = request.getParameter("clickedObj");
                String clickedObj_out = request.getParameter("clickedObj_out");

                long idform = System.currentTimeMillis();
                //Se tiene configurado un método, se muestran los parámetros correspondientes:
                if (wsrv != null) {

                    HashMap<String, String> hmin = new HashMap<String, String>();
                    Iterator<WebServiceParameter> itwsp = wsrvin.listWebServiceInputParameters();
                    while (itwsp.hasNext()) {
                        WebServiceParameter webServiceParameter = itwsp.next();
                        hmin.put(webServiceParameter.getParameterName(), webServiceParameter.getParameterValue());
                    }

                    HashMap<String, String> hmout = new HashMap<String, String>();
                    itwsp = wsrvin.listWebServiceOutputParameters();
                    while (itwsp.hasNext()) {
                        WebServiceParameter webServiceParameter = itwsp.next();
                        hmout.put(webServiceParameter.getParameterName(), webServiceParameter.getParameterValue());
                    }
                    String urlwsrv = wsrv.getUrl();
                    if (urlwsrv != null) {

                        out.println("<script type=\"text/javascript\">");

                        out.println("function reviewProp(sel,txtBox)");
                        out.println("{");
                        out.println("	var inputBox = document.getElementById(txtBox); ");
                        out.println("	if(sel.value!='0') {");
                        out.println("	inputBox.readonly = 'readonly';");
                        out.println("	inputBox.value = sel.value; ");
                        out.println("	} else {");
                        out.println("	inputBox.readOnly = false; ");
                        out.println("	inputBox.value = ''; ");
                        out.println("	}");
                        out.println("	return true;");
                        out.println("}");

                        out.println("</script>");



                        // parámetros de entrada

                        try {
                            ServiceInfo info = org.semanticwb.webservices.WebService.getServiceinfo(new URL(urlwsrv));

                            SWBResourceURL urlupd = paramRequest.getActionUrl();
                            urlupd.setAction("updParameters");
                            out.println("<div class=\"swbform\">");
                            out.println("<form id=\"" + idform + "_wsrvin\" method=\"post\" action=\"" + urlupd + "\" onsubmit=\"submitForm('" + idform + "_wsrvin'); return false;\">");
                            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\">");
                            out.println("<input type=\"hidden\" name=\"idmethod\" value=\"" + method + "\">");

                            out.println("<fieldset>");
                            out.println("<legend> Lista de parámetros de entrada </legend>");
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
                                ParameterDefinition pdef = findParDef(operinfo, null, nivel - 1, pnames, 0, "in");
                                params = pdef.getProperties();
                                //System.out.println("Salio de findParDef..."+params.length);
                            }

                            if (params.length > 0) {
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

                                    String pvalue = hmin.get(key) != null ? hmin.get(key) : "";
                                    out.println("<tr>");
                                    out.println("<td>" + allparam.getName() + "</td>");
                                    out.println("<td>");
                                    if (!allparam.isBasic()) {

                                        SWBResourceURL urldetail = paramRequest.getRenderUrl();
                                        urldetail.setParameter("suri", suri);
                                        urldetail.setParameter("idmethod", method);
                                        urldetail.setParameter("params", (pnames.length() > 0 ? pnames + "," : "") + allparam.getName());
                                        //urldetail.setParameter("params_out", poutnames);
                                        urldetail.setParameter("clickedObj", allparam.getName());
                                        urldetail.setParameter("nivel", "" + (nivel + 1));
                                        out.println("<a href=\"#\" onclick=\"submitUrl('" + urldetail + "',this); return false;\">" + allparam.getDefinitionType() + "</a>");
                                    } else {
                                        out.println(allparam.getDefinitionType());
                                    }

                                    out.println("</td>");

                                    out.println("<td align=\"center\">" + (allparam.isBasic() ? "Sí" : "No") + "</td>");
                                    out.println("<td align=\"center\">" + (allparam.isMultiple() ? "Sí" : "No") + "</td>");
                                    out.println("<td align=\"center\">" + (allparam.isRequired() ? "Sí" : "No") + "</td>");
                                    out.println("<td align=\"center\">");

                                    String tmpClick = pnames.replace(" ", "");
                                    tmpClick = tmpClick.replaceAll(",", ".");

                                    if (allparam.isBasic()) {

                                        ArrayList list = new ArrayList(hmprops.keySet());
                                        Collections.sort(list);

                                        // select con la lista de propiedades existentes
                                        out.println("<select dojoType=\"dijit.form.FilteringSelect\" name=\"sel_p_in_" + (tmpClick != null && tmpClick.length() > 0 ? tmpClick + "." : "") + allparam.getName() + "_" + allparam.getDefinitionType() + "\" id=\"" + idform + "/sel_p_in_" + (tmpClick != null && tmpClick.length() > 0 ? tmpClick + "." : "") + allparam.getName() + "_" + allparam.getDefinitionType() + "\" >");
                                        //Iterator<String> its = hmprops.keySet().iterator();
                                        boolean isSelected = false;
                                        String selected = "";
                                        Iterator<String> its = list.iterator();
                                        while (its.hasNext()) {

                                            String str = its.next();
                                            String varName = "";
                                            String propid = "";
                                            StringTokenizer stoken = new StringTokenizer(str, "|");
                                            if (stoken.hasMoreTokens()) {
                                                varName = stoken.nextToken();
                                                propid = stoken.nextToken();
                                            }
                                            selected = "";

                                            SemanticProperty sp = hmprops.get(str);
                                            if (pvalue.equals(varName + "." + sp.getPropertyCodeName())) {
                                                selected = "selected";
                                                isSelected = true;
                                            }
                                            out.println("<option value=\"" + varName + "." + sp.getPropertyCodeName() + "\" " + selected + ">");
                                            out.println(varName + "." + sp.getPropertyCodeName());
                                            out.println("</option>");

                                        }
                                        out.println("<option value=\"0\" " + (!isSelected ? "selected" : "") + " >Otro</option>");

                                        out.println("<script type=\"dojo/connect\" event=\"onChange\">");
                                        out.println(" var sel=this;   ");

                                        out.println("	var inputBox = dijit.byId('p_in_" + (tmpClick != null && tmpClick.length() > 0 ? tmpClick + "." : "") + allparam.getName() + "_" + allparam.getDefinitionType() + "'); ");
                                        out.println("	if(sel.getValue()!='0') {");

                                        out.println("	   inputBox.setValue(sel.getValue()); ");
                                        out.println("	   inputBox.readOnly=true;");
                                        out.println("	} else {");
                                        out.println("	   inputBox.readOnly=false; ");
                                        out.println("	   inputBox.setValue(''); ");
                                        out.println("	}");
                                        out.println("	return true;");

                                        out.println("</script>");


                                        out.println("</select>");



                                        out.println("<input dojoType=\"dijit.form.TextBox\" id=\"p_in_" + (tmpClick != null && tmpClick.length() > 0 ? tmpClick + "." : "") + allparam.getName() + "_" + allparam.getDefinitionType() + "\" name=\"p_in_" + (tmpClick != null && tmpClick.length() > 0 ? tmpClick + "." : "") + allparam.getName() + "_" + allparam.getDefinitionType() + "\" type=\"text\" " + (allparam.isRequired() ? "required=\"true\" invalidMessage=\"Valor del parámetro requerido.\" " : "") + " " + (isSelected ? "readonly=\"readonly\"" : "") + " value=\"" + pvalue + "\">");
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

                        // parámetros de salida


                        try {
                            ServiceInfo info = org.semanticwb.webservices.WebService.getServiceinfo(new URL(urlwsrv));

                            SWBResourceURL urlupd = paramRequest.getActionUrl();
                            urlupd.setAction("updParameters");
                            out.println("<div class=\"swbform\">");
                            out.println("<form id=\"" + idform + "_wsrvout\" method=\"post\" action=\"" + urlupd + "\" onsubmit=\"submitForm('" + idform + "_wsrvout'); return false;\">");
                            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\">");
                            out.println("<input type=\"hidden\" name=\"idmethod\" value=\"" + method + "\">");

                            out.println("<fieldset>");
                            out.println("<legend> Lista de parámetros de salida </legend>");
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
                            url0.setParameter("nivel_out", "0");
                            out.println("<a href=\"#\" onclick=\"submitUrl('" + url0 + "',this);return false;\">Iniciales</a>");

                            String[] insideparams = poutnames.split(",");
                            int cont = 1;
                            if (null != insideparams) {
                                for (String param : insideparams) {
                                    if (cont <= insideparams.length) {
                                        out.println("&nbsp;|&nbsp;");
                                    }
                                    SWBResourceURL urlp = paramRequest.getRenderUrl();
                                    urlp.setParameter("suri", suri);
                                    urlp.setParameter("idmethod", method);
                                    urlp.setParameter("nivel_out", "" + cont);
                                    out.println("<a href=\"#\" onclick=\"submitUrl('" + urlp + "',this);return false;\">" + param + "</a>");
                                    cont++;
                                }
                            }

                            out.println("</td>");
                            out.println("</tr>");

                            /// Mostrar lista de parámetros
                            ParameterDefinition[] params = operinfo.getOutput().getDefinitions();
                            if (nivel_out > 0 && clickedObj_out != null) {
                                ParameterDefinition pdef = findParDef(operinfo, null, nivel_out - 1, poutnames, 0, "out");
                                params = pdef.getProperties();
                                //System.out.println("Salio de findParDef.(OUT).."+params.length);
                            }

                            if (params.length > 0) {
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

                                    String key = (clickedObj_out != null ? clickedObj_out + "." : "");
                                    key = key.replace("_", ".");

                                    key = key + allparam.getName() + "." + allparam.getDefinitionType();

                                    String pvalue = hmout.get(key) != null ? hmout.get(key) : "";
                                    out.println("<tr>");
                                    out.println("<td>" + allparam.getName() + "</td>");
                                    out.println("<td>");
                                    if (!allparam.isBasic()) {

                                        SWBResourceURL urldetail = paramRequest.getRenderUrl();
                                        urldetail.setParameter("suri", suri);
                                        urldetail.setParameter("idmethod", method);
                                        //urldetail.setParameter("params", pnames);
                                        urldetail.setParameter("params_out", (poutnames.length() > 0 ? poutnames + "," : "") + allparam.getName());
                                        urldetail.setParameter("clickedObj_out", allparam.getName());
                                        urldetail.setParameter("nivel_out", "" + (nivel_out + 1));
                                        out.println("<a href=\"#\" onclick=\"submitUrl('" + urldetail + "',this); return false;\">" + allparam.getDefinitionType() + "</a>");
                                    } else {
                                        out.println(allparam.getDefinitionType());
                                    }

                                    out.println("</td>");

                                    out.println("<td align=\"center\">" + (allparam.isBasic() ? "Sí" : "No") + "</td>");
                                    out.println("<td align=\"center\">" + (allparam.isMultiple() ? "Sí" : "No") + "</td>");
                                    out.println("<td align=\"center\">" + (allparam.isRequired() ? "Sí" : "No") + "</td>");
                                    out.println("<td align=\"center\">");

                                    String tmpClick = poutnames.replace(" ", "");
                                    tmpClick = tmpClick.replaceAll(",", ".");


                                    if (allparam.isBasic()) {

                                        ArrayList list = new ArrayList(hmprops.keySet());
                                        Collections.sort(list);

                                        // select con la lista de propiedades existentes
                                        out.println("<select dojoType=\"dijit.form.FilteringSelect\" name=\"sel_p_out_" + (tmpClick != null && tmpClick.length() > 0 ? tmpClick + "." : "") + allparam.getName() + "_" + allparam.getDefinitionType() + "\" id=\"" + idform + "/sel_p_out_" + (tmpClick != null && tmpClick.length() > 0 ? tmpClick + "." : "") + allparam.getName() + "_" + allparam.getDefinitionType() + "\" >");
                                        //Iterator<String> its = hmprops.keySet().iterator();
                                        boolean isSelected = false;
                                        String selected = "";
                                        Iterator<String> its = list.iterator();
                                        while (its.hasNext()) {

                                            String str = its.next();
                                            String varName = "";
                                            String propid = "";
                                            StringTokenizer stoken = new StringTokenizer(str, "|");
                                            if (stoken.hasMoreTokens()) {
                                                varName = stoken.nextToken();
                                                propid = stoken.nextToken();
                                            }
                                            selected = "";

                                            SemanticProperty sp = hmprops.get(str);
                                            if (pvalue.equals(varName + "." + sp.getPropertyCodeName())) {
                                                selected = "selected";
                                                isSelected = true;
                                            }
                                            out.println("<option value=\"" + varName + "." + sp.getPropertyCodeName() + "\" " + selected + ">");
                                            out.println(varName + "." + sp.getPropertyCodeName());
                                            out.println("</option>");

                                        }
                                        out.println("<option value=\"0\" " + (!isSelected ? "selected" : "") + " >Otro</option>");

                                        out.println("<script type=\"dojo/connect\" event=\"onChange\">");
                                        out.println(" var selo=this;   ");

                                        out.println("	var outBox = dijit.byId('p_out_" + (tmpClick != null && tmpClick.length() > 0 ? tmpClick + "." : "") + allparam.getName() + "_" + allparam.getDefinitionType() + "'); ");
                                        out.println("	if(selo.getValue()!='0') {");

                                        out.println("	   outBox.setValue(selo.getValue()); ");
                                        out.println("	   outBox.readOnly=true;");
                                        out.println("	} else {");
                                        out.println("	   outBox.readOnly=false; ");
                                        out.println("	   outBox.setValue(''); ");
                                        out.println("	}");
                                        out.println("	return true;");

                                        out.println("</script>");

                                        out.println("</select>");

                                        out.println("<input dojoType=\"dijit.form.TextBox\" id=\"p_out_" + (tmpClick != null && tmpClick.length() > 0 ? tmpClick + "." : "") + allparam.getName() + "_" + allparam.getDefinitionType() + "\" name=\"p_out_" + (tmpClick != null && tmpClick.length() > 0 ? tmpClick + "." : "") + allparam.getName() + "_" + allparam.getDefinitionType() + "\" type=\"text\" " + (allparam.isRequired() ? "required=\"true\" invalidMessage=\"Valor del parámetro requerido.\" " : "") + " " + (isSelected ? "readonly=\"readonly\"" : "") + " value=\"" + pvalue + "\">");
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
                            out.println("<fieldset>");
                            SWBResourceURL urlexec = paramRequest.getRenderUrl();
                            urlexec.setMode("exec");
                            urlexec.setParameter("suri", suri);
                            out.println("<a onclick=\"submitUrl('" + urlexec + "',this); return false;\" href=\"#\" >Ejemplo ejecucion</button>");
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
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        if (paramRequest.getMode().equals("exec")) {
            execute(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    private ParameterDefinition findParDef(Operation oper, ParameterDefinition paramDef, int nivel, String pnames, int currentlevel, String inout) {
        ParameterDefinition[] defs = null;
        String[] data = pnames.split(",");

        ParameterDefinition pdret = null;
        //System.out.println(" params: " + pnames+ ", "+inout);
        if (inout != null && (inout.equals("in") || inout.equals("out"))) {
            if (inout.equals("in")) {
                defs = oper.getInput().getDefinitions();
                //System.out.println("oper_defs_in");
            }
            if (inout.equals("out")) {
                defs = oper.getOutput().getDefinitions();
                //System.out.println("oper_defs_out");
            }
        }
        if (paramDef != null && paramDef.getProperties() != null) {
            defs = paramDef.getProperties();
            //System.out.println("paramdef_props_in");
        }

        for (ParameterDefinition pdef : defs) {
            if (pdef.getName().equals(data[currentlevel]) && currentlevel < nivel) {
                pdret = findParDef(oper, pdef, nivel, pnames, currentlevel + 1, inout);
                break;
            } else if (pdef.getName().equals(data[currentlevel]) && currentlevel == nivel) {
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
                        wsrvin.removeAllWebServiceInputParameter();
                        wsrvin.removeAllWebServiceOutputParameter();
                    }
                }
            }
        } else if (act.equals("updParameters")) {

            if (gobj != null) {
                if (gobj instanceof WebServiceInvoker) {
                    wsrvin = (WebServiceInvoker) gobj;

                    WebService wsrv = wsrvin.getWebService();

                    String urlwsrv = wsrv.getUrl();

                    HashMap<String, WebServiceParameter> hmin = new HashMap<String, WebServiceParameter>();
                    Iterator<WebServiceParameter> itwsp = wsrvin.listWebServiceInputParameters();
                    while (itwsp.hasNext()) {
                        WebServiceParameter webServiceParameter = itwsp.next();
                        hmin.put(webServiceParameter.getParameterName(), webServiceParameter);
                    }

                    HashMap<String, WebServiceParameter> hmout = new HashMap<String, WebServiceParameter>();
                    itwsp = wsrvin.listWebServiceOutputParameters();
                    while (itwsp.hasNext()) {
                        WebServiceParameter webServiceParameter = itwsp.next();
                        hmout.put(webServiceParameter.getParameterName(), webServiceParameter);
                    }

                    // Para parámetros de entrada

                    try {
                        ServiceInfo info = org.semanticwb.webservices.WebService.getServiceinfo(new URL(urlwsrv));
                        if (info != null) {

                            Enumeration<String> enu = request.getParameterNames();

                            while (enu.hasMoreElements()) {
                                String parametro = enu.nextElement();
                                String paramvalue = request.getParameter(parametro);
                                if (parametro.startsWith("p_in_")) {
                                    try {
                                        String ptmp = parametro.substring(parametro.indexOf("p_in_") + 5);

                                        ptmp = ptmp.replace("-", ".");
                                        ptmp = ptmp.replace("_", ".");

                                        WebServiceParameter wsp = hmin.get(ptmp);

                                        if (wsp == null) {
                                            wsp = WebServiceParameter.ClassMgr.createWebServiceParameter(wsrvin.getProcessSite());
                                            wsp.setParameterName(ptmp);
                                            wsrvin.addWebServiceInputParameter(wsp);
                                        }
                                        if (paramvalue != null && paramvalue.trim().length() > 0) {
                                            wsp.setParameterValue(paramvalue);
                                        } else {
                                            wsrvin.removeWebServiceInputParameter(wsp);
                                            wsp.remove();
                                        }
                                    } catch (Exception ein) {
                                        log.error("Error al crear WebServiceInputParameter", ein);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error(e);
                    }

                    // Para parámetros de salida

                    try {
                        ServiceInfo info = org.semanticwb.webservices.WebService.getServiceinfo(new URL(urlwsrv));
                        if (info != null) {

                            Enumeration<String> enu = request.getParameterNames();

                            while (enu.hasMoreElements()) {
                                String parametro = enu.nextElement();
                                String paramvalue = request.getParameter(parametro);

                                if (parametro.startsWith("p_out_")) {
                                    try {
                                        String ptmp = parametro.substring(parametro.indexOf("p_out_") + 6);

                                        ptmp = ptmp.replace("-", ".");
                                        ptmp = ptmp.replace("_", ".");

                                        WebServiceParameter wsp = hmout.get(ptmp);

                                        if (wsp == null) {
                                            wsp = WebServiceParameter.ClassMgr.createWebServiceParameter(wsrvin.getProcessSite());
                                            wsp.setParameterName(ptmp);
                                            wsrvin.addWebServiceOutputParameter(wsp);
                                        }
                                        if (paramvalue != null && paramvalue.trim().length() > 0) {
                                            wsp.setParameterValue(paramvalue);
                                        } else {
                                            wsrvin.removeWebServiceOutputParameter(wsp);
                                            wsp.remove();
                                        }
                                    } catch (Exception ein) {
                                        log.error("Error al crear WebServiceOutputParameter", ein);
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

    public void execute(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String suri = request.getParameter("suri");

        if (suri == null) {
            return;
        }

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = ont.getGenericObject(suri);

        ServiceTask pser = null;
        WebServiceInvoker wsrvin = null;

        if (gobj instanceof WebServiceInvoker) {
            wsrvin = (WebServiceInvoker) gobj;
            String method = wsrvin.getMethod();
            if (method == null) {
                method = "";
            }

            Operation opersel = null;

            WebService wsrv = wsrvin.getWebService();
            try {
                ServiceInfo info = org.semanticwb.webservices.WebService.getServiceinfo(new URL(wsrv.getUrl()));
                Operation op = info.getServices()[0].getOperationByName(wsrvin.getMethod());

                JSONObject request_ws = new JSONObject();

                // para parametros de entrada
                //System.out.println("========================================================= Parametros de entrada: ");
                HashMap<String,JSONObject> hmJSON = new HashMap();
                
                Iterator<WebServiceParameter> itwsin = wsrvin.listWebServiceInputParameters();
                while (itwsin.hasNext()) {
                    WebServiceParameter wsp = itwsin.next();

                    String pName = wsp.getParameterName();
                    //System.out.println("paramName: " + pName);

                    String[] pParts = null;
                    if (pName != null) {
                        if (pName.indexOf(".") > -1) {
                            pName = pName.substring(0, pName.lastIndexOf("."));
                            //System.out.println("pName: " + pName);
                            pParts = pName.split("\\.");
                            //System.out.println("pparts.length==" + pParts.length);
                            if (pParts != null && pParts.length > 1) {
                                // se crea el JSON
                                JSONObject parameters_ws = null;
                                JSONObject pJSON = null;
                                for (int i = 0; i < pParts.length; i++) {
                                    //System.out.println("Ppart[" + i + "]:" + pParts[i]);
                                    
                                    if(hmJSON.get(pParts[i])==null){
                                        if(i==0)
                                        {
                                            hmJSON.put(pParts[i], request_ws);
                                        } else {
                                           parameters_ws = new JSONObject();
                                           hmJSON.put(pParts[i], parameters_ws);
                                        }
                                    }
                                        
                                    if(i>0)
                                    {
                                        pJSON = hmJSON.get(pParts[i-1]);
                                        parameters_ws = hmJSON.get(pParts[i]);
                                        pJSON.accumulate(pParts[i-1], parameters_ws);
                                        if((i+1==pParts.length)) parameters_ws.accumulate(pParts[i], wsp.getParameterValue());
                                    }

                                }
                            } else {
                                request_ws.accumulate(pParts[0], wsp.getParameterValue());
                            }
                        }

                    }

                }


                //System.out.println("JSON:" + request_ws.toString());
                //JSONObject getResponse_ws = op.execute(request_ws);
                //System.out.println("Retrned JSON answer:" + getResponse_ws.toString());

                //Falta alinear el JSON de respuesta del WebService a las variables

                // para parametros de salida
                //System.out.println("Parámetros de salida: ===================================================");

                HashMap<String, JSONObject> hmOut = new HashMap<String, JSONObject>();
                //HashMap<String, String> hmVals= new HashMap<String, String>();
                JSONObject response_ws = new JSONObject();
                Iterator<WebServiceParameter> itwsout = wsrvin.listWebServiceOutputParameters();
                while (itwsout.hasNext()) {
                    
                    WebServiceParameter wsp = itwsout.next();

                    String pName = wsp.getParameterName();
                    //System.out.println("paramName: " + pName);

                    String[] pParts = null;
                    if (pName != null) {
                        if (pName.indexOf(".") > -1) {
                            pName = pName.substring(0, pName.lastIndexOf("."));
                            //System.out.println("pName: " + pName);
                            pParts = pName.split("\\.");
                            //System.out.println("pparts.length==" + pParts.length);
                            if (pParts != null && pParts.length > 1) {
                                // se crea el JSON
                                JSONObject parameters_ws = null;
                                JSONObject pJSON = null;
                                for (int i = 0; i < pParts.length; i++) {
                                    //System.out.println("Ppart[" + i + "]:" + pParts[i]);
                                    
                                    if(hmOut.get(pParts[i])==null){
                                        if(i==0)
                                        {
                                            hmOut.put(pParts[i], response_ws);
                                        } else {
                                           parameters_ws = new JSONObject();
                                           hmOut.put(pParts[i], parameters_ws);
                                        }
                                    }
                                        
                                    if(i>0)
                                    {
                                        pJSON = hmOut.get(pParts[i-1]);
                                        parameters_ws = hmOut.get(pParts[i]);
                                        pJSON.accumulate(pParts[i-1], parameters_ws);
                                        if((i+1==pParts.length)) parameters_ws.accumulate(pParts[i], wsp.getParameterValue());
                                    }

                                }
                            } else {
                                response_ws.accumulate(pParts[0], wsp.getParameterValue());
                            }
                        }

                    }
                }
                //System.out.println("JSON:" + response_ws.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

            doView(request, response, paramRequest);
        }
    }

}
