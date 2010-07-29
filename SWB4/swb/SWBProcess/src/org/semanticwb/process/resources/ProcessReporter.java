/**
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
 **/

package org.semanticwb.process.resources;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.SWBException;

/*import org.semanticwb.process.Process;
import org.semanticwb.process.Activity;
import org.semanticwb.process.ProcessSite;
import org.semanticwb.process.ProcessObject;
import org.semanticwb.process.ProcessInstance;
import org.semanticwb.process.FlowObjectInstance;
import org.semanticwb.process.bpms.CaseCountSys;
import org.semanticwb.process.bpms.BPMSProcessInstance;
import org.semanticwb.process.bpms.CaseProcessInstance;*/

import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.ProcessObject;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.SubProcessInstance;
import org.semanticwb.process.kpi.CaseCountSys;
import org.semanticwb.process.kpi.KProcessInstance;
import org.semanticwb.process.kpi.CaseProcessInstance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import org.semanticwb.process.utils.Ajax;
import org.semanticwb.process.utils.Restriction;
import org.semanticwb.process.utils.DateInterval;
import org.semanticwb.process.utils.JasperTemplate;
import org.semanticwb.process.utils.JRProcessObject;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRResource;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRPdfResource;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRRtfResource;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable;

/**
 *
 * @author Sergio Téllez
 */
public class ProcessReporter extends GenericResource {

    /** Log */
    static Logger log = SWBUtils.getLogger(ProcessReporter.class);

    /**
     * Process request.
     *
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        if(paramsRequest.getMode().equalsIgnoreCase("fillgridmtr"))
            doFilterCases(response, paramsRequest);
        else if(paramsRequest.getMode().equalsIgnoreCase("xml"))
            doFilterCasesXml(request,response,paramsRequest);
        else if(paramsRequest.getMode().equalsIgnoreCase("xls"))
            doFilterCasesXls(request,response,paramsRequest);
        else if(paramsRequest.getMode().equalsIgnoreCase("pdf"))
            doFilterCasesPdf(request,response,paramsRequest);
        else if(paramsRequest.getMode().equalsIgnoreCase("rtf"))
            doFilterCasesRtf(request,response,paramsRequest);
        else
            super.processRequest(request, response, paramsRequest);
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if ("add".equals(paramRequest.getAction()) || "edit".equals(paramRequest.getAction()))
            doAdminProcessCase(response, paramRequest);
        else if ("properties".equals(paramRequest.getAction()))
            doAdminProcessObjects(request, response, paramRequest);
        else
            doAdminResume(request, response, paramRequest);
    }

    /* (non-Javadoc)
    * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
    */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String field = "";
        PrintWriter out = response.getWriter();
        HashMap fields = getFields(paramRequest);
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setCallMethod(url.Call_DIRECT);
        url.setMode(paramRequest.Mode_EDIT);
        url.setAction(url.Action_EDIT);
        out.println("<script type=\"text/javascript\">");
        out.println("  dojo.require(\"dojox.grid.DataGrid\");");
        out.println("  dojo.require(\"dojo.data.ItemFileReadStore\");");
        out.println("  function fillGrid(grid, uri, mode) {");
        out.println("      grid.store = new dojo.data.ItemFileReadStore({url: uri+'/_mod/'+mode});");
        out.println("      grid._refresh();");
        out.println("  }");
        out.println("  var layout= null;");
        out.println("  var gridMaster = null;");
        out.println("  dojo.addOnLoad(function() {");
        out.println("      layout= [");
        Iterator<String> keys = fields.keySet().iterator();
        while (keys.hasNext()) {
            field = (String)fields.get(keys.next());
            out.println("          { field:\"" + field + "\", width:\"100px\", name:\"" + field + "\" },");
        }
        out.println("      ];");
        out.println("      gridMaster = new dojox.grid.DataGrid({");
        out.println("          id: \"gridMaster\",");
        out.println("          structure: layout,");
        out.println("          rowSelector: \"10px\",");
        out.println("          rowsPerPage: \"15\"");
        out.println("      }, \"gridMaster\");");
        out.println("      gridMaster.startup();");
        out.println("      var grid = dijit.byId('gridMaster');");
        out.println("      fillGrid(grid, '"+url.setMode("view")+"', 'fillgridmtr');");
        out.println("  });");
        out.println("  function doXml(size) { ");
        out.println("      window.open('" + url.setMode("xml") + "',\'graphWindow\',size);");
        out.println("  }");
        out.println("  function doExcel(size) { ");
        out.println("      window.open('" + url.setMode("xls") + "',\'graphWindow\',size);");
        out.println("  }");
        out.println("  function doPdf(size) { ");
        out.println("      window.open('" + url.setMode("pdf") + "',\'graphWindow\',size);");
        out.println("  }");
        out.println("  function doRtf(size) { ");
         out.println("      window.open('" + url.setMode("rtf") + "',\'graphWindow\',size);");
         out.println("  }");
        out.println("</script>");
        out.print("      <fieldset>\n");
        out.print("        <table border=\"0\" width=\"70%\">\n");
        out.print("            <tr>\n");
        out.print("                <td>&nbsp;&nbsp;&nbsp;\n");
        out.print("                    <button dojoType=\"dijit.form.Button\" onClick=\"doXml('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">XML</button>&nbsp;\n");
        out.print("                    <button dojoType=\"dijit.form.Button\" onClick=\"doExcel('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">" + paramRequest.getLocaleString("spread_sheet") + "</button>&nbsp;\n");
        out.print("                    <button dojoType=\"dijit.form.Button\" onClick=\"doPdf('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">PDF</button>&nbsp;\n");
        out.print("                    <button dojoType=\"dijit.form.Button\" onClick=\"doRtf('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">RTF</button>&nbsp;\n");
        out.print("                </td>\n");
        out.print("            </tr>\n");
        out.print("        </table>\n");
        out.print("      </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("    <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("        <tr>\n");
        out.print("            <td>\n");
        out.print("                <div id=\"ctnergrid\" style=\"height:250px; width:98%; margin: 1px; padding: 0px; border: 1px solid #DAE1FE;\">\n");
        out.print("                    <div id=\"gridMaster\"></div>\n");
        out.print("                </div>\n");
        out.print("            </td>\n");
        out.print("        </tr>\n");
        out.print("    </table>\n");
        out.print("  </fieldset>\n");
    }

    /**
     * Do fill report.
     *
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void doFilterCases(HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        HashMap fields = getFields(paramRequest);
        response.setContentType("text/json;charset=iso-8859-1");
        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            jobj.put("label", "cases");
            jobj.put("items", jarr);
        }catch (JSONException jse) { jse.printStackTrace(); }
        try {
            Iterator<HashMap> it = results();
            while (it.hasNext()) {
                JSONObject obj = new JSONObject();
                HashMap map = it.next();
                Iterator<String> keys = fields.keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next();
                    obj.put((String)fields.get(key), Ajax.notNull((String)map.get(key)));
                }
                jarr.put(obj);
            }
        }catch (JSONException jsone) { jsone.printStackTrace(); }
        response.getOutputStream().println(jobj.toString());
    }

    /**
     * Do filter cases xml.
     *
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doFilterCasesXml(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        HashMap fields = getFields(paramsRequest);
        response.setContentType("text/xml;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        Document dom = SWBUtils.XML.getNewDocument();
        Element filter = dom.createElement("Report");
        dom.appendChild(filter);
        Iterator<HashMap> it = results();
        while (it.hasNext()) {
            HashMap map = it.next();
            Element cases = dom.createElement("cases");
            cases.appendChild(dom.createTextNode(""));
            filter.appendChild(cases);
            Iterator<String> keys = fields.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                Element instance = dom.createElement((String)fields.get(key));
                instance.appendChild(dom.createTextNode(Ajax.notNull((String)map.get(key))));
                cases.appendChild(instance);
            }
        }
        out.print(SWBUtils.XML.domToXml(dom));
        out.flush();
        out.close();
    }

    /**
     * Do filter cases excel.
     *
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doFilterCasesXls(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        HashMap fields = getFields(paramsRequest);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=\"ic.xls\"");
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("   <head>");
        out.println("       <title>" + paramsRequest.getLocaleString("title") + "</title>");
        out.println("   </head>");
        out.println("   <body>");
        out.println("       <table border=\"0\" width=\"95%\">");
        out.println("           <tr>");
        Iterator<String> keys = fields.keySet().iterator();
        while (keys.hasNext()) {
            out.println("               <th>" + (String)fields.get(keys.next()) + "</th>");
        }
        out.println("           </tr>");
        Iterator<HashMap> it = results();
        while (it.hasNext()) {
            HashMap map = it.next();
            keys = fields.keySet().iterator();
            out.println("       <tr>");
            while (keys.hasNext()) {
                String key = keys.next();
                out.println("           <td>" + Ajax.notNull((String)map.get(key)) + "</td>");
            }
            out.println("       </tr>");
        }
        out.println("       </table>");
        out.println("   </body>");
        out.println("</html>");
        out.flush();
        out.close();
    }

    /**
     * Do filter cases pdf.
     *
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doFilterCasesPdf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/pdf");
        try {
            JRDataSourceable datasource = new JRProcessObject(results(), getFields(paramsRequest));
            JasperTemplate jasperTemplate = JasperTemplate.PROCESS_OBJECTS;
            HashMap<String,String> params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
            params.put("title", paramsRequest.getLocaleString("title"));
            params.put("elements", getProcessTitle(getResourceBase().getAttribute("process","")));
            JRResource jrResource = new JRPdfResource(jasperTemplate.getTemplatePath(), params, datasource.orderJRReport());
            jrResource.prepareReport();
            jrResource.exportReport(response);
        }catch (Exception e){
            log.error("Error on method doFilterCasesPdf with id" + " " + getResourceBase().getId(), e);
        }
    }

    /**
     * Do filter cases rtf.
     *
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doFilterCasesRtf(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("application/rtf");
        response.setHeader("Content-Disposition", "inline; filename=\"scr.rtf\"");
        try {
            JRDataSourceable datasource = new JRProcessObject(results(), getFields(paramsRequest));
            JasperTemplate jasperTemplate = JasperTemplate.PROCESS_OBJECTS;
            HashMap<String,String> params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
            params.put("title", paramsRequest.getLocaleString("title"));
            params.put("elements", getProcessTitle(getResourceBase().getAttribute("process","")));
            JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), params, datasource.orderJRReport());
            jrResource.prepareReport();
            jrResource.exportReport(response);
        }catch (Exception e){
            log.error("Error on method doFilterCasesRtf with id" + " " + getResourceBase().getId(), e);
        }
    }

    private Iterator applyRestrictions() {
        CaseCountSys ccs = new CaseCountSys();
        if (!"".equals(getResourceBase().getAttribute("process","")))
            ccs.addRestriction(new Restriction(CaseCountSys.PROCESS,getProcess(getResourceBase().getAttribute("process","")).getURI(),null));
        if (!"".equals(getResourceBase().getAttribute("status","")))
            ccs.addRestriction(new Restriction(CaseCountSys.STATUS,getResourceBase().getAttribute("status",""),null));
        if (/*!"".equals(request.getParameter("permission")) &&*/ !"".equals(getResourceBase().getAttribute("permission_value",""))) {
            //if (Integer.parseInt(request.getParameter("permission")) == USER)
                ccs.addRestriction(new Restriction(CaseCountSys.USER,getResourceBase().getAttribute("permission_value"),null));
        }
        if (!"".equals(getResourceBase().getAttribute("starteda","")))
            ccs.addRestriction(new Restriction(String.valueOf(Instance.STATUS_INIT),new DateInterval(getResourceBase().getAttribute("starteda"),getResourceBase().getAttribute("startedto")),null));
            //ccs.addRestriction(new Restriction(String.valueOf(Activity.STATUS_INIT),new DateInterval(getResourceBase().getAttribute("starteda"),getResourceBase().getAttribute("startedto")),null));
        if (!"".equals(getResourceBase().getAttribute("closeda","")))
            ccs.addRestriction(new Restriction(String.valueOf(Instance.STATUS_CLOSED),new DateInterval(getResourceBase().getAttribute("closeda"),getResourceBase().getAttribute("closedto")),null));
            //ccs.addRestriction(new Restriction(String.valueOf(Activity.STATUS_CLOSED),new DateInterval(getResourceBase().getAttribute("closeda"),getResourceBase().getAttribute("closedto")),null));
        ccs.addRestriction(new Restriction(CaseCountSys.ARTIFACT,getRestrictions(),null));
        return ccs.listProcessInstance().iterator();
    }

    private Iterator<HashMap> results() {
        ArrayList results = new ArrayList();
        Iterator it = applyRestrictions();
        while (it.hasNext()) {
            HashMap map = new HashMap();
            ArrayList pobjs = new ArrayList();
            ProcessInstance pinst = (ProcessInstance)it.next();
            getObjectsFromInstance(pinst, pobjs);
            Iterator<ProcessObject> objit = pobjs.iterator();
            while(objit.hasNext()) {
                ProcessObject pobj =  objit.next();
                Iterator<SemanticProperty> spit = pobj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    //System.out.println("sp.getName(): " + sp.getName() + " " + BPMSProcessInstance.getPropertyValue(pobj.getSemanticObject(), sp));
                    if (!"".equals(getResourceBase().getAttribute(sp.getName(),"")))
                        //map.put(sp.getName(), BPMSProcessInstance.getPropertyValue(pobj.getSemanticObject(), sp));
                        map.put(sp.getName(), KProcessInstance.getPropertyValue(pobj.getSemanticObject(), sp));
                }
            }
            if (!map.isEmpty())
                results.add(map);
        }
        return results.iterator();
    }

    private void doAdminProcessCase(HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode(paramRequest.Mode_ADMIN);
        url.setAction("properties");
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("title"));
        out.print("  </fieldset>\n");
        out.print("  <form id=\"case\" name=\"case\" action=" + url.toString() + " method=\"post\">\n");
        out.print("      <fieldset>\n");
        out.print("          <legend>" + paramRequest.getLocaleString("filter") + "</legend>\n");
        out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
        out.print("              <tr>\n");
        out.print("                  <td width=\"40%\">" + paramRequest.getLocaleString("process") + ":</td>\n");
        out.print("                  <td width=\"60%\" align=\"left\">\n");
        out.print("                      <select id=\"process\" name=\"process\">\n");
        //out.print("                          <option value=\"\">&nbsp;</option>\n");
        ProcessSite site = (ProcessSite)paramRequest.getWebPage().getWebSite();
        /*java.util.Vector processdefs = BPMSProcessInstance.ClassMgr.getAllProcessDefinitions(site);
        java.util.Enumeration eprocess = processdefs.elements();
        while (eprocess.hasMoreElements()) {
            Process process = (Process)eprocess.nextElement();*/
        Iterator<Process> itprocess = site.listProcesses();
        while (itprocess.hasNext()) {
            Process process = itprocess.next();
            out.print("                   <option value=" +  process.getId() + " " + (getResourceBase().getAttribute("process","").equalsIgnoreCase(process.getId()) ? " selected" : "") + ">" + process.getTitle() + "</option>\n");
        }
        out.print("                      </select>\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + paramRequest.getLocaleString("status") + ":</td>\n");
        out.print("                  <td>\n");
        out.print("                      <select id=\"status\" name=\"status\">\n");
        out.print("                          <option value=\"\">&nbsp;</option>\n");
        /*out.print("                          <option value=\"" + Activity.STATUS_PROCESSING + "\"" + " " + (getResourceBase().getAttribute("status","").equalsIgnoreCase(String.valueOf(Activity.STATUS_PROCESSING)) ? " selected" : "") + ">" + paramRequest.getLocaleString("STATUS_PROCESSING") + "</option>\n");
        out.print("                          <option value=\"" + Activity.STATUS_CLOSED + "\"" + " " + (getResourceBase().getAttribute("status","").equalsIgnoreCase(String.valueOf(Activity.STATUS_CLOSED)) ? " selected" : "") + ">" + paramRequest.getLocaleString("STATUS_CLOSED") + "</option>\n");
        out.print("                          <option value=\"" + Activity.STATUS_ABORTED + "\"" + " " + (getResourceBase().getAttribute("status","").equalsIgnoreCase(String.valueOf(Activity.STATUS_ABORTED)) ? " selected" : "") + ">" + paramRequest.getLocaleString("STATUS_ABORTED") + "</option>\n");*/
        out.print("                          <option value=\"" + Instance.STATUS_PROCESSING + "\">" + paramRequest.getLocaleString("STATUS_PROCESSING") + "</option>\n");
        out.print("                          <option value=\"" + Instance.STATUS_CLOSED + "\">" + paramRequest.getLocaleString("STATUS_CLOSED") + "</option>\n");
        out.print("                          <option value=\"" + Instance.STATUS_ABORTED + "\">" + paramRequest.getLocaleString("STATUS_ABORTED") + "</option>\n");
        out.print("                      </select>\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        //out.print("                  <td>" + paramRequest.getLocaleString("permission") + "</td>\n");
        out.print("                  <td>" + paramRequest.getLocaleString("USER") + "</td>\n");
        out.print("                  <td>\n");
        /*out.print("                      <select dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" id=\"permission\" name=\"permission\">\n");
        out.print("                          <option value=\"\">&nbsp;</option>\n");
        out.print("                          <option value=\"" + USER + "\">" + paramRequest.getLocaleString("USER") + "</option>\n");
        out.print("                          <option value=\"" + ROLE + "\">" + paramRequest.getLocaleString("ROLE") + "</option>\n");
        out.print("                          <option value=\"" + GROUP + "\">" + paramRequest.getLocaleString("GROUP") + "</option>\n");
        out.print("                      </select>\n");*/
        out.print("                      <select id=\"permission_value\" name=\"permission_value\">\n");
        out.print("                        <option value=\"\">&nbsp;</option>\n");
        getSelectUsers(paramRequest.getWebPage().getWebSiteId(),out);
        out.print("                      </select>\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + paramRequest.getLocaleString("started") + ":</td>\n");
        out.print("                  <td>\n");
        out.print("                      <input type=\"text\" name=\"starteda\" value=" + getResourceBase().getAttribute("starteda", "") + " onblur=\"if(!this.value){this.focus();}\" id=\"starteda\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\">");
        out.print("                      &nbsp;<input type=\"text\" name=\"startedto\" value=" + getResourceBase().getAttribute("startedto", "") + " onblur=\"if(!this.value){this.focus();}\" id=\"startedto\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\">\n");
        out.print("                  </td>");
        out.print("              </tr>\n");
        out.print("              <tr>\n");
        out.print("                  <td>" + paramRequest.getLocaleString("closed") + ":</td>\n");
        out.print("                  <td>\n");
        out.print("                      <input type=\"text\" name=\"closeda\" value=" + getResourceBase().getAttribute("closeda", "") + " onblur=\"if(!this.value){this.focus();}\" id=\"closeda\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\">");
        out.print("                      &nbsp;<input type=\"text\" name=\"closedto\" value=" + getResourceBase().getAttribute("closedto", "") + " onblur=\"if(!this.value){this.focus();}\" id=\"closedto\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\">\n");
        out.print("                  </td>\n");
        out.print("              </tr>\n");
        out.print("          </table>\n");
        out.print("      </fieldset>\n");
        out.print("        <fieldset>");
        out.print("            <span align=\"center\">");
        out.print("                <span widgetid=\"dijit_form_Button_0\" class=\"dijit dijitReset dijitLeft dijitInline dijitButton\" dojoattachevent=\"ondijitclick:_onButtonClick,onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse\">");
        out.print("                    <span class=\"dijitReset dijitRight dijitInline\">");
        out.print("                        <span class=\"dijitReset dijitInline dijitButtonNode\">");
        out.print("                            <button style=\"-moz-user-select: none;\" tabindex=\"0\" id=\"dijit_form_Button_0\" aria-labelledby=\"dijit_form_Button_0_label\" role=\"button\" class=\"dijitReset dijitStretch dijitButtonContents\" dojoattachpoint=\"titleNode,focusNode\" type=\"submit\" value=\"\" wairole=\"button\" waistate=\"labelledby-dijit_form_Button_0_label\">");
        out.print("                                <span class=\"dijitReset dijitInline\" dojoattachpoint=\"iconNode\">");
        out.print("                                    <span class=\"dijitReset dijitToggleButtonIconChar\">✓</span>");
        out.print("                                </span>");
        out.print("                                <span class=\"dijitReset dijitInline dijitButtonText\" id=\"dijit_form_Button_0_label\" dojoattachpoint=\"containerNode\">" + paramRequest.getLocaleString("apply")+"</span>");
        out.print("                            </button>");
        out.print("                        </span>");
        out.print("                    </span>");
        out.print("                </span>");
        out.print("             </span>");
        out.print("    </fieldset>");
        out.print("  </form>\n");
        out.print("</div>\n");
    }

    public void doAdminProcessObjects(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        ArrayList pobjs = new ArrayList();
        updateAttributes(request, paramRequest);
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> itprocess = site.listProcesses();
            while (itprocess.hasNext()) {
                Process process = itprocess.next();
                if(request.getParameter("process").equals(process.getId())) {
                    ProcessInstance pinst = CaseProcessInstance.pop(process);
                    getObjectsFromInstance(pinst, pobjs);
                }
            }
        }
        printProcessObjetcs(request, pobjs, response.getWriter(), paramRequest);
    }

    private void printProcessObjetcs(HttpServletRequest request, ArrayList pobjs, PrintWriter out, SWBParamRequest paramRequest) throws SWBResourceException {
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("edit"));
        out.print("  </fieldset>\n");
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setMode(paramRequest.Mode_ADMIN);
        url.setAction("resume");
        out.print("  <form id=\"case\" name=\"case\" action=" + url.toString() + " method=\"post\">\n");
        out.print("     <fieldset>\n");
        out.print("         <legend>" + paramRequest.getLocaleString("advanced") + "</legend>\n");
        out.print("         <table border=\"0\" width=\"100%\" align=\"left\">\n");
        Iterator<ProcessObject> objit = pobjs.iterator();
        if (objit.hasNext()) {
            while (objit.hasNext()) {
                String dao = "";
                ProcessObject obj =  objit.next();
                SemanticObject sob = SemanticObject.getSemanticObject(obj.getURI());
                SemanticClass cls = sob.getSemanticClass();
                //System.out.println("ProcessObject: " + obj.getURI() + " " + cls.getRootClass().getName() + " " + cls.getRootClass().getLabel(paramRequest.getUser().getLanguage()));
                if (null!=cls.getRootClass().getLabel(paramRequest.getUser().getLanguage()))
                    dao = cls.getRootClass().getLabel(paramRequest.getUser().getLanguage());
                else
                    dao = cls.getRootClass().getName();
                out.print(" <tr>\n");
                out.print("     <td colspan=\"4\"><b>" + Ajax.specialChars(dao) + "<b></td>\n");
                out.print(" </tr>\n");
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    //out.print(" <tr>\n" + "<input name=\"key\" type=\"hidden\" value=\"" + sp.getName() + "\"/>");
                    out.print("     <td width=\"5%\"><input id=\"" + sp.getName() + "_active\" type=\"checkbox\" name=\"" + sp.getName() + "_active\" value=\"1\"" + (!"".equalsIgnoreCase(getResourceBase().getAttribute(sp.getName(),"")) ? " checked" : "") + "></td>\n");
                    out.print("     <td width=\"25%\">" + Ajax.specialChars(sp.getDisplayName()) + "</td>\n");
                    out.print("     <td width=\"25%\">\n");
                    out.print("         <select id=\"" + sp.getName() + "_operator\" name=\"" + sp.getName() + "_operator\">\n");
                    out.print("             <option value=\"\">&nbsp;</option>\n");
                    out.print("             <option value=\"" + Restriction.EQUALS + "\" " + (Restriction.EQUALS == getOperator(getResourceBase().getAttribute(sp.getName(),"")) ? " selected" : "") + ">=</option>\n");
                    out.print("             <option value=\"" + Restriction.GREATER_THAT + "\" " + (Restriction.GREATER_THAT == getOperator(getResourceBase().getAttribute(sp.getName(),"")) ? " selected" : "") + ">></option>\n");
                    out.print("             <option value=\"" + Restriction.SMALLER_THAT + "\" " + (Restriction.SMALLER_THAT == getOperator(getResourceBase().getAttribute(sp.getName(),"")) ? " selected" : "") + "><</option>\n");
                    out.print("             <option value=\"" + Restriction.GREATER_EQUAL + "\" " + (Restriction.GREATER_EQUAL == getOperator(getResourceBase().getAttribute(sp.getName(),"")) ? " selected" : "") + ">>=</option>\n");
                    out.print("             <option value=\"" + Restriction.SMALLER_EQUAL + "\" " + (Restriction.SMALLER_EQUAL == getOperator(getResourceBase().getAttribute(sp.getName(),"")) ? " selected" : "") + "><=</option>\n");
                    out.print("             <option value=\"" + Restriction.EQUALS_TO + "\" " + (Restriction.EQUALS_TO == getOperator(getResourceBase().getAttribute(sp.getName(),"")) ? " selected" : "") + ">" + paramRequest.getLocaleString("EQUALS_TO") + "</option>\n");
                    out.print("             <option value=\"" + Restriction.EQUALS_IGNORE_CASE + "\" " + (Restriction.EQUALS_IGNORE_CASE == getOperator(getResourceBase().getAttribute(sp.getName(),"")) ? " selected" : "") + ">" + paramRequest.getLocaleString("EQUALS_IGNORE_CASE") + "</option>\n");
                    out.print("             <option value=\"" + Restriction.STARTS_WITH + "\" " + (Restriction.STARTS_WITH == getOperator(getResourceBase().getAttribute(sp.getName(),"")) ? " selected" : "") + ">" + paramRequest.getLocaleString("STARTS_WITH") + "</option>\n");
                    out.print("             <option value=\"" + Restriction.ENDS_WITH + "\" " + (Restriction.ENDS_WITH == getOperator(getResourceBase().getAttribute(sp.getName(),"")) ? " selected" : "") + ">" + paramRequest.getLocaleString("ENDS_WITH") + "</option>\n");
                    out.print("             <option value=\"" + Restriction.LIKES + "\" " + (Restriction.LIKES == getOperator(getResourceBase().getAttribute(sp.getName(),"")) ? " selected" : "") + ">" + paramRequest.getLocaleString("LIKES") + "</option>\n");
                    out.print("         </select>\n");
                    out.print("     </td>");
                    out.print("     <td width=\"45%\" align=\"left\"><input id=\"" + sp.getName() + "_filter\" name=\"" + sp.getName() + "_filter\" type=\"text\" value=\"" + getcriteria(getResourceBase().getAttribute(sp.getName(),"")) + "\"/></td>\n");
                    out.print(" </tr>\n");
                }
            }
        }else {
            out.print("         <tr>\n");
            out.print("             <td colspan=\"4\"><b>" + paramRequest.getLocaleString("NOT_ARTIFACTS_ASSIGNED") + "<b></td>\n");
            out.print("         </tr>\n");
        }
        out.print("         </table>\n");
        out.print("     </fieldset>\n");
        out.print("     <fieldset>\n");
        out.print("         <button  dojoType=\"dijit.form.Button\" type=\"submit\" >"+paramRequest.getLocaleString("apply")+"</button>");
        url.setAction("edit");
        out.print("         <button dojoType=\"dijit.form.Button\" onClick=location='" + url + "'>"+paramRequest.getLocaleString("return")+"</button>");
        out.print("     </fieldset>\n");
        out.print(" </form>\n");
        out.print("</div>\n");
    }

    private void doAdminResume(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        ArrayList pobjs = new ArrayList();
        PrintWriter out = response.getWriter();
        updateAttributes(request, paramRequest);
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print(paramRequest.getLocaleString("title"));
        out.print("  </fieldset>\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("resume") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"100%\" align=\"left\">\n");
        out.print("         <tr>\n");
        out.print("             <td width=\"40%\">" + paramRequest.getLocaleString("process") + ":</td>\n");
        if (!"".equals(getResourceBase().getAttribute("process","")))
            out.print("             <td width=\"60%\" align=\"left\">" + getProcessTitle(getResourceBase().getAttribute("process","")) + "</td>\n");
        else
            out.print("             <td width=\"60%\" align=\"left\">" + paramRequest.getLocaleString("ALL") + "</td>\n");
        out.print("         </tr>\n");
        out.print("         <tr>\n");
        out.print("             <td width=\"40%\">" + paramRequest.getLocaleString("status") + ":</td>\n");
        if (!"".equals(getResourceBase().getAttribute("status","")))
            out.print("             <td width=\"60%\" align=\"left\">" + Ajax.statusLabel(Integer.parseInt(getResourceBase().getAttribute("status")),paramRequest) + "</td>\n");
        else
            out.print("             <td width=\"60%\" align=\"left\">" + paramRequest.getLocaleString("ALL") + "</td>\n");
        out.print("         </tr>\n");
        out.print("         <tr>\n");
        out.print("             <td width=\"40%\">" + paramRequest.getLocaleString("USER") + ":</td>\n");
        if (!"".equals(getResourceBase().getAttribute("permission_value","")))
            out.print("             <td width=\"60%\" align=\"left\">" + getUserName(paramRequest.getWebPage().getWebSiteId(), getResourceBase().getAttribute("permission_value","")) + "</td>\n");
        else
            out.print("             <td width=\"60%\" align=\"left\">" + paramRequest.getLocaleString("ALL") + "</td>\n");
        out.print("         </tr>\n");
        out.print("         <tr>\n");
        out.print("             <td width=\"40%\">" + paramRequest.getLocaleString("started") + ":</td>\n");
        if (!"".equals(getResourceBase().getAttribute("starteda",""))) {
            out.print("             <td width=\"60%\" align=\"left\">" + getResourceBase().getAttribute("starteda",""));
            if (!"".equals(getResourceBase().getAttribute("startedto","")))
                out.print(" " + paramRequest.getLocaleString("TO") + " " + getResourceBase().getAttribute("startedto",""));
            out.print("</td>\n");
        }else
            out.print("             <td width=\"60%\" align=\"left\">" + paramRequest.getLocaleString("ANY") + "</td>\n");
        out.print("         </tr>\n");
        out.print("         <tr>\n");
        out.print("             <td width=\"40%\">" + paramRequest.getLocaleString("closed") + ":</td>\n");
        if (!"".equals(getResourceBase().getAttribute("closeda",""))) {
            out.print("             <td width=\"60%\" align=\"left\">" + getResourceBase().getAttribute("closeda",""));
            if (!"".equals(getResourceBase().getAttribute("closedto","")))
                out.print(" " + paramRequest.getLocaleString("TO") + " " + getResourceBase().getAttribute("closedto",""));
            out.print("</td>\n");
        }else
            out.print("             <td width=\"60%\" align=\"left\">" + paramRequest.getLocaleString("ANY") + "</td>\n");
        out.print("         </tr>\n");
        out.print("     </table>\n");
        out.print("  </fieldset>\n");
        out.print("</div>\n");
        if (!"".equals(getResourceBase().getAttribute("process",""))) {
            Process process = getProcess(getResourceBase().getAttribute("process",""));
            if (null != process) {
                ProcessInstance pinst = CaseProcessInstance.pop(process);
                getObjectsFromInstance(pinst, pobjs);
            }
        }
        resumeProcessObjetcs(pobjs, out, paramRequest);
    }

    private void resumeProcessObjetcs(ArrayList pobjs, PrintWriter out, SWBParamRequest paramRequest) throws SWBResourceException {
        out.print("<div class=\"swbform\">\n");
        out.print("  <fieldset>\n");
        out.print("     <legend>" + paramRequest.getLocaleString("advanced") + "</legend>\n");
        out.print("     <table border=\"0\" width=\"100%\" align=\"left\">\n");
        Iterator<ProcessObject> objit = pobjs.iterator();
        if (objit.hasNext()) {
            while (objit.hasNext()) {
                String dao = "";
                ProcessObject obj =  objit.next();
                SemanticObject sob = SemanticObject.getSemanticObject(obj.getURI());
                SemanticClass cls = sob.getSemanticClass();
                //System.out.println("ProcessObject: " + obj.getURI() + " " + cls.getRootClass().getName() + " " + cls.getRootClass().getLabel(paramRequest.getUser().getLanguage()));
                if (null!=cls.getRootClass().getLabel(paramRequest.getUser().getLanguage()))
                    dao = cls.getRootClass().getLabel(paramRequest.getUser().getLanguage());
                else
                    dao = cls.getRootClass().getName();
                out.print(" <tr>\n");
                out.print("     <td colspan=\"2\"><b>" + Ajax.specialChars(dao) + "<b></td>\n");
                out.print(" </tr>\n");
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                boolean filters = false;
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if (!"".equals(getResourceBase().getAttribute(sp.getName(),""))) {
                        filters = true;
                        out.print(" </tr>\n");
                        out.print("     <td width=\"40%\">" + Ajax.specialChars(sp.getDisplayName()) + "</td>\n");
                        out.print("     <td width=\"60%\" align=\"left\">\n");
                        Restriction restriction = new Restriction(getResourceBase().getAttribute(sp.getName(),""));
                        if (null != restriction.getOperator() && !"".equals(restriction.getOperator()))
                            out.print(" " + Ajax.operatorLabel(Integer.parseInt(restriction.getOperator()), paramRequest));
                        out.print(" " + restriction.getCriteria() + "</td>\n");
                        out.print(" </tr>\n");
                    }
                }
                if(!filters) {
                    out.print("         <tr>\n");
                    out.print("             <td colspan=\"2\">" + paramRequest.getLocaleString("NOT_ARTIFACTS_SELECTED") + "</td>\n");
                    out.print("         </tr>\n");
                }
            }
        }else {
            out.print("         <tr>\n");
            out.print("             <td colspan=\"2\"><b>" + paramRequest.getLocaleString("NOT_ARTIFACTS_ASSIGNED") + "<b></td>\n");
            out.print("         </tr>\n");
        }
        out.print("     </table>\n");
        out.print(" </fieldset>\n");
        out.print("</div>\n");
    }

    /*private void getObjectsFromInstance(ProcessInstance pinst, ArrayList pobjs) {
        Iterator<ProcessObject> objit = pinst.getAllProcessObjects().iterator();
        while(objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if (!pobjs.contains(obj))
                pobjs.add(obj);
        }
        Iterator<FlowObjectInstance> foit = pinst.listFlowObjectInstances();
        while(foit.hasNext()) {
            FlowObjectInstance flobin = foit.next();
            if (flobin instanceof ProcessInstance)
                getObjectsFromInstance((ProcessInstance)flobin, pobjs);
        }
    }*/

    private void getObjectsFromInstance(ProcessInstance pinst, ArrayList pobjs) {
        Iterator<ProcessObject> objit = pinst.listProcessObjects();
        while(objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if (!pobjs.contains(obj))
                pobjs.add(obj);
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                getObjectsFromInstance((SubProcessInstance)flobin, pobjs);
        }
    }

    private void getObjectsFromInstance(SubProcessInstance spinst, ArrayList pobjs) {
        Iterator<ProcessObject> objit = spinst.listProcessObjects();
        while(objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if (!pobjs.contains(obj))
                pobjs.add(obj);
        }
        Iterator<FlowNodeInstance> foit = spinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                getObjectsFromInstance((SubProcessInstance)flobin, pobjs);
        }
    }

    private void updateAttributes(HttpServletRequest request, SWBParamRequest paramRequest) {
        try {
            if ("properties".equalsIgnoreCase(paramRequest.getAction())) {
                getResourceBase().setAttribute("process", request.getParameter("process"));
                getResourceBase().setAttribute("status", request.getParameter("status"));
                getResourceBase().setAttribute("permission_value", request.getParameter("permission_value"));
                getResourceBase().setAttribute("starteda", formatDate(request.getParameter("starteda")));
                getResourceBase().setAttribute("startedto", formatDate(request.getParameter("startedto")));
                getResourceBase().setAttribute("closeda", formatDate(request.getParameter("closeda")));
                getResourceBase().setAttribute("closedto", formatDate(request.getParameter("closedto")));
            }else if ("resume".equalsIgnoreCase(paramRequest.getAction())) {
                HashMap map = Ajax.getParameters(request.getParameterMap());
                Iterator keys = map.keySet().iterator();
                while (keys.hasNext()) {
                    String key = (String)keys.next();
                    if (key.endsWith("_active")) {
                        key = key.substring(0, key.lastIndexOf("_"));
                        getResourceBase().setAttribute(key,new Restriction(key,map.containsKey(key + "_filter")?(String)map.get(key + "_filter"):"",map.containsKey(key + "_operator")?(String)map.get(key + "_operator"):"").toString());
                    }
                }
            }
            getResourceBase().updateAttributesToDB();
        }catch (SWBException swbe) {
            swbe.printStackTrace();
        }
    }

    private void getSelectUsers(String processSiteId, PrintWriter out) throws SWBResourceException {
        try {
            Iterator<User> itusers = ProcessSite.ClassMgr.getProcessSite(processSiteId).getUserRepository().listUsers();
            while (itusers.hasNext()) {
                User user = itusers.next();
                out.print("                      <option value=\"" + user.getLogin() + "\"" + (getResourceBase().getAttribute("permission_value","").equalsIgnoreCase(user.getLogin()) ? " selected" : "") + ">" + user.getFullName() + "</option>\n");
            }
        }catch (Exception e){ e.printStackTrace(); }
    }

    private Process getProcess(String processId) {
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> itprocess = site.listProcesses();
            while (itprocess.hasNext()) {
                Process process = itprocess.next();
                if (processId.equalsIgnoreCase(process.getId()))
                    return process;
            }
        }
        return null;
    }

    private HashMap getFields(SWBParamRequest paramRequest) {
        HashMap fields = new HashMap();
        ArrayList pobjs = new ArrayList();
        Iterator<String> it  = getResourceBase().getAttributeNames();
        while (it.hasNext()) {
            String attr = it.next();
            Restriction restriction = new Restriction(getResourceBase().getAttribute(attr,""));
            if (null != restriction.getProperty())
                fields.put(restriction.getProperty(), "");
        }
        if (!"".equals(getResourceBase().getAttribute("process",""))) {
            Process process = getProcess(getResourceBase().getAttribute("process",""));
            if (null != process) {
                ProcessInstance pinst = CaseProcessInstance.pop(process);
                getObjectsFromInstance(pinst, pobjs);
            }
        }
        Iterator<ProcessObject> objit = pobjs.iterator();
        if (objit.hasNext()) {
            while (objit.hasNext()) {
                ProcessObject obj =  objit.next();
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if (fields.containsKey(sp.getName()))
                        fields.put(sp.getName(), sp.getDisplayName(paramRequest.getUser().getLanguage()));
                }
            }
        }
        return fields;
    }

    private String getProcessTitle(String processId) {
        Process process = getProcess(processId);
        if (null != process)
            return process.getTitle();
        else
            return "";
    }

    private ArrayList<Restriction> getRestrictions() {
        ArrayList fields = new ArrayList();
        Iterator<String> it  = getResourceBase().getAttributeNames();
        while (it.hasNext()) {
            String attr = it.next();
            Restriction restriction = new Restriction(getResourceBase().getAttribute(attr,""));
            if (null != restriction.getProperty() && !"".equals(restriction.getOperator()))
                fields.add(restriction);
        }
        return fields;
    }

    private String getUserName(String processSiteId, String usrLogin) {
        String usrName = "";
        try {
            usrName = ProcessSite.ClassMgr.getProcessSite(processSiteId).getUserRepository().getUserByLogin(usrLogin).getFullName();
        }catch (Exception e) {
            log.debug("User " + usrLogin + " is not found in " + ProcessSite.ClassMgr.getProcessSite(processSiteId).getUserRepository().getTitle(),e);
        }
        return usrName;
    }

    private int getOperator(String definition){
        Restriction restriction = new Restriction(definition);
        if (null != restriction.getOperator() && !"".equals(restriction.getOperator()))
            return Integer.parseInt(restriction.getOperator());
        else
            return -1;
    }

    private String getcriteria(String definition){
        Restriction restriction = new Restriction(definition);
        if (null != restriction.getCriteria())
            return restriction.getCriteria().toString();
        else
            return "";
    }

    private String formatDate(String date) {
        if (null != date && !"".equals(date))
            return date.substring(8,10) + "-" + date.substring(5,7) + "-" + date.substring(0,4);
        else return "";
    }
}