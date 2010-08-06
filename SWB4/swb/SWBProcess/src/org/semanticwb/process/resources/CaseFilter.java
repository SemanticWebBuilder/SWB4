/**
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integraciÃ³n,
 * colaboraciÃ³n y conocimiento, que gracias al uso de tecnologÃ­a semÃ¡ntica puede generar contextos de
 * informaciÃ³n alrededor de algÃºn tema de interÃ©s o bien integrar informaciÃ³n y aplicaciones de diferentes
 * fuentes, donde a la informaciÃ³n se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creaciÃ³n original del Fondo de InformaciÃ³n y DocumentaciÃ³n
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trÃ¡mite.
 *
 * INFOTEC pone a su disposiciÃ³n la herramienta SemanticWebBuilder a travÃ©s de su licenciamiento abierto al pÃºblico (â€˜open sourceâ€™),
 * en virtud del cual, usted podrÃ¡ usarlo en las mismas condiciones con que INFOTEC lo ha diseÃ±ado y puesto a su disposiciÃ³n;
 * aprender de Ã©l; distribuirlo a terceros; acceder a su cÃ³digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los tÃ©rminos y condiciones de la LICENCIA ABIERTA AL PÃšBLICO que otorga INFOTEC para la utilizaciÃ³n
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantÃ­a sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implÃ­cita ni explÃ­cita,
 * siendo usted completamente responsable de la utilizaciÃ³n que le dÃ© y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposiciÃ³n la siguiente
 * direcciÃ³n electrÃ³nica:
 *  http://www.semanticwebbuilder.org
 **/
package org.semanticwb.process.resources;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.ProcessObject;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.SubProcessInstance;

import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

import org.semanticwb.process.kpi.CaseCountSys;
import org.semanticwb.process.kpi.KProcessInstance;
import org.semanticwb.process.kpi.CaseProcessInstance;

import org.semanticwb.process.utils.Ajax;
import org.semanticwb.process.utils.Restriction;
import org.semanticwb.process.utils.TimeInterval;
import org.semanticwb.process.utils.DateInterval;
import org.semanticwb.process.utils.JasperTemplate;

import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

import org.semanticwb.model.SWBContext;

import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.process.utils.JRCaseDetail;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRResource;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRPdfResource;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRRtfResource;
import org.semanticwb.portal.admin.resources.reports.jrresources.JRDataSourceable;

/**
 *
 * @author Sergio TÃ©llez
 */
public class CaseFilter extends GenericResource {

    /** Log */
    static Logger log = SWBUtils.getLogger(CaseFilter.class);

    /** Permission */
    public final static int USER=0;
    public final static int ROLE=1;
    public final static int GROUP=2;

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
            doFilterCases(request, response, paramsRequest);
        else if(paramsRequest.getMode().equalsIgnoreCase("xml"))
            doFilterCasesXml(request,response,paramsRequest);
        else if(paramsRequest.getMode().equalsIgnoreCase("xls"))
            doFilterCasesXls(request,response,paramsRequest);
        else if(paramsRequest.getMode().equalsIgnoreCase("pdf"))
            doFilterCasesPdf(request,response,paramsRequest);
        else if(paramsRequest.getMode().equalsIgnoreCase("rtf"))
            doFilterCasesRtf(request,response,paramsRequest);
        else if(paramsRequest.getMode().equalsIgnoreCase("tracking"))
            this.doCaseTracking(request,response,paramsRequest);
        else
            super.processRequest(request, response, paramsRequest);
    }

   /* (non-Javadoc)
    * @see org.semanticwb.portal.api.GenericAdmResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
    */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
         PrintWriter out = response.getWriter();
         SWBResourceURL url = paramRequest.getRenderUrl();
         url.setCallMethod(url.Call_DIRECT);
         url.setMode(paramRequest.Mode_EDIT);
         url.setAction(url.Action_EDIT);
         out.println("<script type=\"text/javascript\">");
         out.println("  dojo.require(\"dojox.grid.DataGrid\");");
         out.println("  dojo.require(\"dojo.data.ItemFileReadStore\");");
         out.println("  function displayProps() { ");
         out.println("       dojo.xhrPost({");
         out.println("          url: \"" + url.toString() + "\",");
         out.println("          load: function(data, ioArgs){");
         out.println("              dojo.byId('properties').innerHTML = data;");
         out.println("          },");
         out.println("          form: 'case'");
         out.println("       });");
         out.println("  }");
         out.println("  function fillGrid(grid, uri, mode, params) {");
         out.println("      grid.store = new dojo.data.ItemFileReadStore({url: uri+'/_mod/'+mode+'?'+params});");
         out.println("      grid._refresh();");
         out.println("  }");
         out.println("  var layout= null;");
         out.println("  var gridMaster = null;");
         out.println("  dojo.addOnLoad(function() {");
         /*out.println("      var formatLink = function(result) {");
         out.println("          return typeof result != 'object' ? dojo.string.substitute(");
		 out.println("              '<a target=_blank href=' + result + '>' + result.substring(result.length-2, result.length) + '</a>',");
		 out.println("              result");
         out.println("          ) : result;");
         out.println("      }");*/
         out.println("      layout= ["); //formatter: formatLink,
         out.println("          { field:\"instance\", width:\"100px\", name:\"" + paramRequest.getLocaleString("instance") + "\" },");
         out.println("          { field:\"process\", width:\"100px\", name:\"" + paramRequest.getLocaleString("process") + "\" },");
         out.println("          { field:\"user\", width:\"100px\", name:\"" + paramRequest.getLocaleString("USER") + "\" },");
         out.println("          { field:\"started\", width:\"100px\", name:\"" + paramRequest.getLocaleString("started") + "\" },");
         out.println("          { field:\"closed\", width:\"100px\", name:\"" + paramRequest.getLocaleString("closed") + "\" },");
         out.println("          { field:\"status\", width:\"100px\", name:\"" + paramRequest.getLocaleString("status") + "\" },");
         out.println("      ];");
         out.println("      gridMaster = new dojox.grid.DataGrid({");
         out.println("          id: \"gridMaster\",");
         out.println("          structure: layout,");
         out.println("          rowSelector: \"10px\",");
         out.println("          rowsPerPage: \"15\"");

         out.println(",query:{ status: '*' } ");
         out.println(",onRowDblClick: fillTracking ");

         out.println("      }, \"gridMaster\");");
         out.println("      gridMaster.startup();");
         out.println("  });");

         out.println("function fillTracking(evt) {\n");
         out.println("      doTracking('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar',evt.grid.store.getValue(evt.grid.getItem(evt.rowIndex),'instance')); \n");
         out.println("}\n");

         out.println("function doTracking(size, key) { \n");
         out.println("   var params = '?&instance='+key;\n");
         out.println("   window.open(\""+url.setMode("tracking")+"\"+params,\"detailWindow\", size);\n");
         out.println("}\n");

         out.println("  function addProps(){");
         out.println("      var params = 'process='+dojo.byId('process').value;");
         //out.println("          params += '&permission=0';");
         out.println("          params += '&starteda=' + dojo.byId('starteda').value;");
         out.println("          params += '&startedto=' + dojo.byId('startedto').value;");
         out.println("          params += '&closeda=' + dojo.byId('closeda').value;");
         out.println("          params += '&closedto=' + dojo.byId('closedto').value;");
         out.println("          params += '&permission_value=' + dojo.byId('permission_value').value;");
         out.println("          params += '&response_oper=' + dojo.byId('response_oper').value;");
         out.println("          params += '&response_time=' + dojo.byId('response_time').value;");
         out.println("          params += '&response_unit=' + dojo.byId('response_unit').value;");
         out.println("      if(dojo.byId('status')) {");
         out.println("          params += '&status=' + dojo.byId('status').value;");
         out.println("      }");
         Iterator itIds = getProcessObjectsIds().iterator();
         while (itIds.hasNext()) {
             String id = (String)itIds.next();
             out.println("          if(dojo.byId('" + id + "_active')) {");
             out.println("              if(dojo.byId('" + id + "_active').checked) {");
             out.println("                  params += '&" + id + "_active=' + dojo.byId('" + id + "_active').value;");
             out.println("                  params += '&" + id + "_operator=' + dojo.byId('" + id + "_operator').value;");
             out.println("                  params += '&" + id + "_filter=' + dojo.byId('" + id + "_filter').value;");
             out.println("              }");
             out.println("          }");
         }
         out.println("      return params;");
         out.println("  }");
         out.println("  function doApply(){");
         out.println("      var grid = dijit.byId('gridMaster');");
         out.println("      var params = addProps();");
         //out.println("      window.open('" + url.setMode("html") + "?'+params,\'graphWindow\',size);");
         out.println("          fillGrid(grid, '"+url.setMode("view")+"', 'fillgridmtr', params);");
         out.println("  }");
         out.println("  function doXml(size) { ");
         out.println("      var params = addProps();");
         out.println("      window.open('" + url.setMode("xml") + "?'+params,\'graphWindow\',size);");
         out.println("  }");
         out.println("  function doExcel(size) { ");
         out.println("      var params = addProps();");
         out.println("      window.open('" + url.setMode("xls") + "?'+params,\'graphWindow\',size);");
         out.println("  }");

         out.println("  function doPdf(size) { ");
         out.println("      var params = addProps();");
         out.println("      window.open('" + url.setMode("pdf") + "?'+params,\'graphWindow\',size);");
         out.println("  }");

         out.println("  function doRtf(size) { ");
         out.println("      var params = addProps();");
         out.println("      window.open('" + url.setMode("rtf") + "?'+params,\'graphWindow\',size);");
         out.println("  }");

         out.println("</script>");
         out.print("<div class=\"swbform\">\n");
         out.print("  <fieldset>\n");
         out.print(paramRequest.getLocaleString("title"));
         out.print("  </fieldset>\n");
         out.print("  <form id=\"case\" name=\"case\" action=\"" + paramRequest.getRenderUrl().toString() + "\" method=\"post\">\n");
         out.print("      <fieldset>\n");
         out.print("          <legend>" + paramRequest.getLocaleString("filter") + "</legend>\n");
         out.print("          <table border=\"0\" width=\"70%\" align=\"center\">\n");
         out.print("              <tr>\n");
         out.print("                  <td width=\"40%\">" + paramRequest.getLocaleString("process") + ":</td>\n");
         out.print("                  <td width=\"60%\" align=\"left\">\n");
         out.print("                      <select id=\"process\" name=\"process\">\n");
         out.print("                          <option value=\"\">&nbsp;</option>\n");
         Iterator isites = ProcessSite.ClassMgr.listProcessSites();
         while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> itprocess = site.listProcesses();
            /*java.util.Vector processdefs = BPMSProcessInstance.ClassMgr.getAllProcessDefinitions(site);
            java.util.Enumeration eprocess = processdefs.elements();
            while (eprocess.hasMoreElements()) {*/
            while (itprocess.hasNext()) {
                //Process process = (Process)eprocess.nextElement();
                Process process = itprocess.next();
                out.print("                   <option value=" +  process.getEncodedURI() + ">" + process.getTitle() + "</option>\n");
            }
         }
         out.print("                      </select>\n");
         out.print("                  </td>");
         out.print("              </tr>\n");
         out.print("              <tr>\n");
         out.print("                  <td>" + paramRequest.getLocaleString("status") + ":</td>\n");
         out.print("                  <td>\n");
         out.print("                      <select id=\"status\" name=\"status\">\n");
         out.print("                          <option value=\"\">&nbsp;</option>\n");
         /*out.print("                          <option value=\"" + Activity.STATUS_PROCESSING + "\">" + paramRequest.getLocaleString("STATUS_PROCESSING") + "</option>\n");
         out.print("                          <option value=\"" + Activity.STATUS_CLOSED + "\">" + paramRequest.getLocaleString("STATUS_CLOSED") + "</option>\n");
         out.print("                          <option value=\"" + Activity.STATUS_ABORTED + "\">" + paramRequest.getLocaleString("STATUS_ABORTED") + "</option>\n");*/
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
         getSelectUsers(out);
         out.print("                      </select>\n");
         out.print("                  </td>");
         out.print("              </tr>\n");
         out.print("              <tr>\n");
         out.print("                  <td>" + paramRequest.getLocaleString("started") + ":</td>\n");
         out.print("                  <td>\n");
         out.print("                      <input type=\"text\" name=\"starteda\" onblur=\"if(!this.value){this.focus();}\" id=\"starteda\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\">");
         out.print("                      &nbsp;<input type=\"text\" name=\"startedto\" onblur=\"if(!this.value){this.focus();}\" id=\"startedto\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\">\n");
         out.print("                  </td>");
         out.print("              </tr>\n");
         out.print("              <tr>\n");
         out.print("                  <td>" + paramRequest.getLocaleString("closed") + ":</td>\n");
         out.print("                  <td>\n");
         out.print("                      <input type=\"text\" name=\"closeda\" onblur=\"if(!this.value){this.focus();}\" id=\"closeda\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\">");
         out.print("                      &nbsp;<input type=\"text\" name=\"closedto\" onblur=\"if(!this.value){this.focus();}\" id=\"closedto\" dojoType=\"dijit.form.DateTextBox\" size=\"11\" style=\"width:110px;\" hasDownArrow=\"true\">\n");
         out.print("                  </td>\n");
         out.print("              </tr>\n");
         out.print("             <tr>\n");
         out.print("                  <td>" + paramRequest.getLocaleString("RESPONSE_TIME") + ":</td>\n");
         out.print("                  <td>\n");
         out.print("                     <select id=\"response_oper\" name=\"response_oper\">\n");
         out.print("                         <option value=\"\">&nbsp;</option>\n");
         out.print("                         <option value=\"" + Restriction.EQUALS + "\">=</option>\n");
         out.print("                         <option value=\"" + Restriction.GREATER_THAT + "\">></option>\n");
         out.print("                         <option value=\"" + Restriction.SMALLER_THAT + "\"><</option>\n");
         out.print("                         <option value=\"" + Restriction.GREATER_EQUAL + "\">>=</option>\n");
         out.print("                         <option value=\"" + Restriction.SMALLER_EQUAL + "\"><=</option>\n");
         out.print("                     </select>\n");
         out.print("                     &nbsp;<input id=\"response_time\" name=\"response_time\" type=\"text\" style=\"width:60px;\"/>\n");
         out.print("                     &nbsp;<select id=\"response_unit\" name=\"response_unit\">\n");
         out.print("                         <option value=\"\">&nbsp;</option>\n");
         out.print("                         <option value=\"" + TimeInterval.MILISECOND + "\">" + paramRequest.getLocaleString("MILISECOND") + "</option>\n");
         out.print("                         <option value=\"" + TimeInterval.SECOND + "\">" + paramRequest.getLocaleString("SECOND") + "</option>\n");
         out.print("                         <option value=\"" + TimeInterval.MINUTE + "\">" + paramRequest.getLocaleString("MINUTE") + "</option>\n");
         out.print("                         <option value=\"" + TimeInterval.HOUR + "\">" + paramRequest.getLocaleString("HOUR") + "</option>\n");
         out.print("                         <option value=\"" + TimeInterval.DAY + "\">" + paramRequest.getLocaleString("DAY") + "</option>\n");
         out.print("                         <option value=\"" + TimeInterval.MONTH + "\">" + paramRequest.getLocaleString("MONTH") + "</option>\n");
         out.print("                         <option value=\"" + TimeInterval.YEAR + "\">" + paramRequest.getLocaleString("YEAR") + "</option>\n");
         out.print("                     </select>\n");
         out.print("                  </td>");
         out.print("             </tr>\n");
         out.print("          </table>\n");
         out.print("      </fieldset>\n");
         out.print("      <fieldset>\n");
         out.print("          <legend>" + paramRequest.getLocaleString("advanced") + "</legend>\n");
         out.print("          <table border=\"0\" width=\"70%\" align=\"center\"\n>");
         out.print("              <tr>\n");
         out.print("                  <td>\n");
         out.print("                      " + paramRequest.getLocaleString("edit") + "<img src=\"/swbadmin/icons/plus.gif\" onclick=\"javascript:displayProps();\">\n");
         out.print("                  </td>\n");
         out.print("              </tr>\n");
         out.print("              <tr>\n");
         out.print("                  <td>\n");
         out.print("                      <div id=\"properties\"></div>\n");
         out.print("                  </td>\n");
         out.print("              </tr>\n");
         out.print("          </table>\n");
         out.print("      </fieldset>\n");

         out.print("      <fieldset>\n");
         out.print("        <table border=\"0\" width=\"70%\">\n");
         out.print("            <tr>\n");
         out.print("                <td>&nbsp;&nbsp;&nbsp;\n");
         out.print("                    <button dojoType=\"dijit.form.Button\" onClick=\"doXml('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">XML</button>&nbsp;\n");
         out.print("                    <button dojoType=\"dijit.form.Button\" onClick=\"doExcel('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">" + paramRequest.getLocaleString("spread_sheet") + "</button>&nbsp;\n");
         out.print("                    <button dojoType=\"dijit.form.Button\" onClick=\"doPdf('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">PDF</button>&nbsp;\n");
         out.print("                    <button dojoType=\"dijit.form.Button\" onClick=\"doRtf('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">RTF</button>&nbsp;\n");
         out.print("                    <button dojoType=\"dijit.form.Button\" onClick=\"doApply('width=600, height=550, scrollbars, resizable, alwaysRaised, menubar')\">" + paramRequest.getLocaleString("apply") + "</button>\n");
         out.print("                </td>\n");
         out.print("            </tr>\n");
         out.print("        </table>\n");
         out.print("      </fieldset>\n");
         out.print("  </form>\n");
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
         out.print("</div>\n");
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        ArrayList pobjs = new ArrayList();
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            Iterator<Process> itprocess = site.listProcesses();
            while (itprocess.hasNext()) {
                Process process = itprocess.next();
                if(URLDecoder.decode(request.getParameter("process")).equals(process.getURI())) {
                    ProcessInstance pinst = CaseProcessInstance.pop(process);
                    getObjectsFromInstance(pinst, pobjs);
                }
            }
        }
        printProcessObjetcs(request, pobjs, response.getWriter(), paramRequest);
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
    private void doFilterCases(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        response.setContentType("text/json; charset=iso-8859-1");
        JSONObject jobj = new JSONObject();
        JSONArray jarr = new JSONArray();
        try {
            jobj.put("label", "cases");
            jobj.put("items", jarr);
        }catch (JSONException jse) { jse.printStackTrace(); }
        try {
            Iterator<ProcessInstance> itpi = applyRestrictions(request);
            while (itpi.hasNext()) {
                JSONObject obj = new JSONObject();
                ProcessInstance pinst = itpi.next();
                obj.put("instance", pinst.getId());
                obj.put("process", pinst.getProcessType().getTitle());
                obj.put("user", Ajax.getModifiedBy(pinst));
                obj.put("started", pinst.getCreated());
                obj.put("closed", pinst.getEnded());
                obj.put("status", Ajax.statusLabel(pinst.getStatus(),paramsRequest));
                jarr.put(obj);
            }
        }catch (JSONException jsone) { jsone.printStackTrace(); }
        PrintWriter out = response.getWriter();
        out.print(jobj.toString());
        out.flush();
        out.close();
    }

    private Iterator applyRestrictions(HttpServletRequest request) {
        CaseCountSys ccs = new CaseCountSys();
        if (!"".equals(request.getParameter("process")))
            ccs.addRestriction(new Restriction(CaseCountSys.PROCESS,request.getParameter("process"),null));
        if (!"".equals(request.getParameter("status")))
            ccs.addRestriction(new Restriction(CaseCountSys.STATUS,request.getParameter("status"),null));
        if (/*!"".equals(request.getParameter("permission")) &&*/ !"".equals(request.getParameter("permission_value"))) {
            //if (Integer.parseInt(request.getParameter("permission")) == USER)
                ccs.addRestriction(new Restriction(CaseCountSys.USER,request.getParameter("permission_value"),null));
        }
        if (!"".equals(request.getParameter("starteda")))
            ccs.addRestriction(new Restriction(String.valueOf(Instance.STATUS_INIT),new DateInterval(request.getParameter("starteda"),request.getParameter("startedto")),null));
        if (!"".equals(request.getParameter("closeda")))
            ccs.addRestriction(new Restriction(String.valueOf(Instance.STATUS_CLOSED),new DateInterval(request.getParameter("closeda"),request.getParameter("closedto")),null));
        if (!"".equals(request.getParameter("response_oper")) && !"".equals(request.getParameter("response_time")) && !"".equals(request.getParameter("response_unit")))
            ccs.addRestriction(new Restriction(String.valueOf(CaseCountSys.RESPONSE),new TimeInterval(Integer.parseInt(request.getParameter("response_unit")),Integer.parseInt(request.getParameter("response_oper")),Integer.parseInt(request.getParameter("response_time"))),null));
        addRestrictionsToObjects(request, ccs);
        return ccs.listProcessInstance().iterator();
    }

    private void addRestrictionsToObjects(HttpServletRequest request, CaseCountSys ccs) {
        ArrayList<Restriction> listRestrictions = new ArrayList();
        HashMap map = getParameters(request.getParameterMap());
        Iterator keys = map.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String)keys.next();
            if (key.endsWith("_active")) {
                key = key.substring(0, key.lastIndexOf("_"));
                listRestrictions.add(new Restriction(key,(String)map.get(key + "_filter"),(String)map.get(key + "_operator")));
            }
        }
        ccs.addRestriction(new Restriction(CaseCountSys.ARTIFACT,listRestrictions,null));
    }

    private java.util.HashMap getParameters(java.util.Map map) {
        java.util.HashMap attributes = new java.util.HashMap();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            java.util.Map.Entry n = (java.util.Map.Entry)iter.next();
            attributes.put(n.getKey().toString(), ((String[])n.getValue())[0].toString());
        }
        return attributes;
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

    private void printProcessObjetcs(HttpServletRequest request, ArrayList pobjs, PrintWriter out, SWBParamRequest paramRequest) throws SWBResourceException {
        out.print(" <table border=\"0\" width=\"100%\" align=\"left\"\n>");
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
                    out.print("     <td width=\"5%\"><input id=\"" + sp.getName() + "_active\" type=\"checkbox\" name=\"" + sp.getName() + "_active\" value=\"1\"" + ("1".equalsIgnoreCase(request.getParameter(sp.getName()+"_active")) ? " checked" : "") + "></td>\n");
                    out.print("     <td width=\"25%\">" + Ajax.specialChars(sp.getDisplayName()) + "</td>\n");
                    out.print("     <td width=\"25%\">\n");
                    out.print("         <select id=\"" + sp.getName() + "_operator\" name=\"" + sp.getName() + "_operator\">\n");
                    out.print("             <option value=\"" + Restriction.EQUALS + "\" " + (String.valueOf(Restriction.EQUALS).equalsIgnoreCase(request.getParameter(sp.getName()+"_operator")) ? " selected" : "") + ">=</option>\n");
                    out.print("             <option value=\"" + Restriction.GREATER_THAT + "\" " + (String.valueOf(Restriction.GREATER_THAT).equalsIgnoreCase(request.getParameter(sp.getName()+"_operator")) ? " selected" : "") + ">></option>\n");
                    out.print("             <option value=\"" + Restriction.SMALLER_THAT + "\" " + (String.valueOf(Restriction.SMALLER_THAT).equalsIgnoreCase(request.getParameter(sp.getName()+"_operator")) ? " selected" : "") + "><</option>\n");
                    out.print("             <option value=\"" + Restriction.GREATER_EQUAL + "\" " + (String.valueOf(Restriction.GREATER_EQUAL).equalsIgnoreCase(request.getParameter(sp.getName()+"_operator")) ? " selected" : "") + ">>=</option>\n");
                    out.print("             <option value=\"" + Restriction.SMALLER_EQUAL + "\" " + (String.valueOf(Restriction.SMALLER_EQUAL).equalsIgnoreCase(request.getParameter(sp.getName()+"_operator")) ? " selected" : "") + "><=</option>\n");
                    out.print("             <option value=\"" + Restriction.EQUALS_TO + "\" " + (String.valueOf(Restriction.EQUALS_TO).equalsIgnoreCase(request.getParameter(sp.getName()+"_operator")) ? " selected" : "") + ">" + paramRequest.getLocaleString("EQUALS_TO") + "</option>\n");
                    out.print("             <option value=\"" + Restriction.EQUALS_IGNORE_CASE + "\" " + (String.valueOf(Restriction.EQUALS_IGNORE_CASE).equalsIgnoreCase(request.getParameter(sp.getName()+"_operator")) ? " selected" : "") + ">" + paramRequest.getLocaleString("EQUALS_IGNORE_CASE") + "</option>\n");
                    out.print("             <option value=\"" + Restriction.STARTS_WITH + "\" " + (String.valueOf(Restriction.STARTS_WITH).equalsIgnoreCase(request.getParameter(sp.getName()+"_operator")) ? " selected" : "") + ">" + paramRequest.getLocaleString("STARTS_WITH") + "</option>\n");
                    out.print("             <option value=\"" + Restriction.ENDS_WITH + "\" " + (String.valueOf(Restriction.ENDS_WITH).equalsIgnoreCase(request.getParameter(sp.getName()+"_operator")) ? " selected" : "") + ">" + paramRequest.getLocaleString("ENDS_WITH") + "</option>\n");
                    out.print("             <option value=\"" + Restriction.LIKES + "\" " + (String.valueOf(Restriction.LIKES).equalsIgnoreCase(request.getParameter(sp.getName()+"_operator")) ? " selected" : "") + ">" + paramRequest.getLocaleString("LIKES") + "</option>\n");
                    out.print("         </select>\n");
                    out.print("     </td>");
                    out.print("     <td width=\"45%\" align=\"left\"><input id=\"" + sp.getName() + "_filter\" name=\"" + sp.getName() + "_filter\" type=\"text\" value=\"" + Ajax.notNull(request.getParameter(sp.getName()+"_filter")) + "\"/></td>\n");
                    out.print(" </tr>\n");
                }
            }
        }else {
            out.print(" <tr>\n");
            out.print("     <td colspan=\"4\"><b>" + paramRequest.getLocaleString("NOT_ARTIFACTS_ASSIGNED") + "<b></td>\n");
            out.print(" </tr>\n");
        }
        out.print(" </table>\n");
    }

    private ArrayList getProcessObjectsIds() {
        ArrayList pobjs = new ArrayList();
        Iterator isites = ProcessSite.ClassMgr.listProcessSites();
        while (isites.hasNext()) {
            ProcessSite site = (ProcessSite)isites.next();
            /*java.util.Vector processdefs = BPMSProcessInstance.ClassMgr.getAllProcessDefinitions(site);
            java.util.Enumeration eprocess = processdefs.elements();
            while (eprocess.hasMoreElements()) {
                Process process = (Process)eprocess.nextElement();*/
            Iterator<Process> itprocess = site.listProcesses();
            while (itprocess.hasNext()) {
                Process process = itprocess.next();
                ProcessInstance pinst = CaseProcessInstance.pop(process);
                if (null != pinst)
                    getObjectsFromInstanceIds(pinst, pobjs);
            }
        }
        return getObjetcsIds(pobjs);
    }

    private ArrayList getObjetcsIds(ArrayList pobjs) {
        ArrayList ids = new ArrayList();
        Iterator<ProcessObject> objit = pobjs.iterator();
        while (objit.hasNext()) {
            ProcessObject obj =  objit.next();
            Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
            while(spit.hasNext()) {
                SemanticProperty sp = spit.next();
                ids.add(sp.getName());
            }
        }
        return ids;
    }

    private void getObjectsFromInstanceIds(ProcessInstance pinst, ArrayList pobjs) {
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
                getObjectsFromInstanceIds((SubProcessInstance)flobin, pobjs);
        }
    }

    private void getObjectsFromInstanceIds(SubProcessInstance spinst, ArrayList pobjs) {
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
                getObjectsFromInstanceIds((SubProcessInstance)flobin, pobjs);
        }
    }

    /**
     * Do case tracking.
     *
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doCaseTracking(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        ArrayList pobjs = new ArrayList();
        PrintWriter out = response.getWriter();
        String idpinst = getURLInstance(request.getParameter("instance"));
        SemanticObject semObject = SemanticObject.createSemanticObject(idpinst);
        ProcessInstance pinst = (ProcessInstance)semObject.createGenericInstance();
        out.println("<h3>Propiedades</h3>");
        out.println("<ul>");
        getObjectsFromInstance(pinst, pobjs);
        Iterator<ProcessObject> objit = pobjs.iterator();
        while(objit.hasNext()) {
            ProcessObject pobj =  objit.next();
            out.println("   <li><b>" + getLabelObject(pobj, paramsRequest) + "</b></li>\n");
            Iterator<SemanticProperty> spit = pobj.getSemanticObject().listProperties();
            while(spit.hasNext()) {
                SemanticProperty sp = spit.next();
                //out.println("<li>" + Ajax.specialChars(sp.getDisplayName()) + ": " + BPMSProcessInstance.getPropertyValue(pobj.getSemanticObject(), sp) + "</li>");
                out.println("<li>" + Ajax.specialChars(sp.getDisplayName()) + ": " + KProcessInstance.getPropertyValue(pobj.getSemanticObject(), sp) + "</li>");
            }
        }
        out.println("</ul>");
        out.println("<h3>Detalle de Proceso</h3>");
        out.println("<ul>");
        //Iterator<FlowObjectInstance> flowbis = pinst.listFlowObjectInstances();
        Iterator<FlowNodeInstance> flowbis = pinst.listFlowNodeInstances();
        while(flowbis.hasNext()) {
            //FlowObjectInstance obj =  flowbis.next();
            FlowNodeInstance obj = flowbis.next();
            printActivityInstance(obj, out, paramsRequest);
        }
        out.println("</ul>");
    }

    public void printActivityInstance(FlowNodeInstance ai, PrintWriter out, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        out.println("   <li>");
        out.println("       <table>");
        out.println("           <tr>");
        out.println("               <td>");
        if ("org.semanticwb.process.model.Process".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/subtask_colapsado1.png\"/>");
        else if ("org.semanticwb.process.model.StartEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/start_2.png\"/>");
        else if ("org.semanticwb.process.model.InclusiveGateWay".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/gate_normal1.png\"/>");
        else if ("org.semanticwb.process.model.Task".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/task_normal1.png\"/>");
        else if ("org.semanticwb.process.model.UserTask".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/task_usr1.png\"/>");
        else if ("org.semanticwb.process.model.ServiceTask".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/task_servicio1.png\"/>");
        else if ("org.semanticwb.process.model.EndEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/end_2.png\"/>");
        else if ("org.semanticwb.process.model.ExclusiveGateWay".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/gate_inclusiva_1.png\"/>");
        else if ("org.semanticwb.process.model.TimerIntermediateCatchEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/inter_tmp1.png\"/>");
        else if ("org.semanticwb.process.model.SubProcess".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/subtask_2.png\"/>");
        else if ("org.semanticwb.process.model.SignalIntermediateThrowEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/inter_senal_n_1.png\"/>");
        else if ("org.semanticwb.process.model.SignalIntermediateCatchEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/inter_senal_b_1.png\"/>");
        else if ("org.semanticwb.process.model.SignalStartEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/start_senal1.png\"/>");
        else if ("org.semanticwb.process.model.TimerStartEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/start_tmp1.png\"/>");
        else if ("org.semanticwb.process.model.TerminationEndEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/end_termina1.png\"/>");
        else if ("org.semanticwb.process.model.ScriptTask".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/task_script1.png\"/>");
        else if ("org.semanticwb.process.model.LinkIntermediateThrowEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/inter_enlace_n_1.png\"/>");
        else if ("org.semanticwb.process.model.LinkIntermediateCatchEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/inter_enlace_b_1.png\"/>");
        else if ("org.semanticwb.process.model.ScalationIntermediateCatchEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/inter_escala_b_1.png\"/>");
        else if ("org.semanticwb.process.model.ScalationIntermediateThrowEvent".equalsIgnoreCase(ai.getFlowNodeType().getSemanticObject().getSemanticClass().getClassName()))
        out.println("       <img src=\"/swbutil/org/semanticwb/process/modeler/images/inter_escala_n_1.png\"/>");
        out.println("               </td>");
        out.println("               <td>");
        if (null != ai.getFlowNodeType().getTitle())
            out.println(ai.getFlowNodeType().getTitle()+"<br>");
        if (null != ai.getEnded())
            out.println(Ajax.notNull(ai.getEnded()).substring(0,16)+"<br>");
        if (null != ai.getModifiedBy())
            out.println(ai.getModifiedBy().getFullName()+"<br>");
        if (Instance.STATUS_CLOSED == ai.getStatus())
            out.println("       <img src=\"/swbadmin/icons/activa.gif\"/>");
        if (Instance.STATUS_ABORTED == ai.getStatus())
            out.println("       <img src=\"/swbadmin/images/delete.gif\"/>");
        if (Instance.STATUS_PROCESSING == ai.getStatus())
            out.println("       <img src=\"/swbadmin/icons/editar_1.gif\"/>");
        out.println("               </td>");
        out.println("           </tr>");
        out.println("       </table>");
        out.println("   </li>");
        if(ai instanceof SubProcessInstance) {
            SubProcessInstance spi = (SubProcessInstance)ai;
            Iterator<FlowNodeInstance> acit = spi.listFlowNodeInstances();
            if(acit.hasNext()) {
                out.println("<ul>");
                while(acit.hasNext()) {
                    FlowNodeInstance actinst =  acit.next();
                    printActivityInstance(actinst,out,paramRequest);
                }
                out.println("</ul>");
            }
        }
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
        response.setContentType("text/xml;charset=iso-8859-1");
        PrintWriter out = response.getWriter();
        Document dom = SWBUtils.XML.getNewDocument();
        Element filter = dom.createElement("FilteredCases");
        dom.appendChild(filter);
        Iterator<ProcessInstance> itpi = applyRestrictions(request);
        while (itpi.hasNext()) {
            ProcessInstance pinst = itpi.next();
            Element cases = dom.createElement("cases");
            cases.appendChild(dom.createTextNode(""));
            filter.appendChild(cases);
            Element instance = dom.createElement("instance");
            instance.appendChild(dom.createTextNode(pinst.getId()));
            cases.appendChild(instance);
            Element process = dom.createElement("process");
            process.appendChild(dom.createTextNode(pinst.getProcessType().getTitle()));
            cases.appendChild(process);
            Element user = dom.createElement("user");
            //user.appendChild(dom.createTextNode(pinst.getModifiedBy().getFullName()));
            user.appendChild(dom.createTextNode(Ajax.getModifiedBy(pinst)));
            cases.appendChild(user);
            Element started = dom.createElement("started");
            started.appendChild(dom.createTextNode(""+pinst.getCreated()));
            cases.appendChild(started);
            Element closed = dom.createElement("closed");
            closed.appendChild(dom.createTextNode(Ajax.notNull(pinst.getEnded())));
            cases.appendChild(closed);
            Element status = dom.createElement("status");
            status.appendChild(dom.createTextNode(Ajax.statusLabel(pinst.getStatus(),paramsRequest)));
            cases.appendChild(status);
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
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=\"ic.xls\"");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("   <head>");
        out.println("       <title>" + paramsRequest.getLocaleString("title") + "</title>");
        out.println("   </head>");
        out.println("   <body>");
        out.println("       <table border=\"0\" width=\"95%\">");
        out.println("           <tr>");
        out.println("               <th>" + paramsRequest.getLocaleString("instance") + "</th>");
        out.println("               <th>" + paramsRequest.getLocaleString("process") + "</th>");
        out.println("               <th>" + paramsRequest.getLocaleString("USER") + "</th>");
        out.println("               <th>" + paramsRequest.getLocaleString("started") + "</th>");
        out.println("               <th>" + paramsRequest.getLocaleString("closed") + "</th>");
        out.println("               <th>" + paramsRequest.getLocaleString("status") + "</th>");
        out.println("           </tr>");
        Iterator<ProcessInstance> itpi = applyRestrictions(request);
        while (itpi.hasNext()) {
            ProcessInstance pinst = itpi.next();
            out.println("       <tr>");
            out.println("           <td>" + pinst.getId() + "</td>");
            out.println("           <td>" + pinst.getProcessType().getTitle() + "</td>");
            //out.println("           <td>" + pinst.getModifiedBy().getFullName() + "</td>");
            out.println("           <td>" + Ajax.getModifiedBy(pinst) + "</td>");
            out.println("           <td>" + pinst.getCreated() + "</td>");
            out.println("           <td>" + Ajax.notNull(pinst.getEnded()) + "</td>");
            out.println("           <td>" + Ajax.statusLabel(pinst.getStatus(),paramsRequest) + "</td>");
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
            JRDataSourceable datasource = new JRCaseDetail(applyRestrictions(request), paramsRequest.getRenderUrl());
            JasperTemplate jasperTemplate = JasperTemplate.FILTER_CASES;
            HashMap<String,String> params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
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
            JRDataSourceable datasource = new JRCaseDetail(applyRestrictions(request), paramsRequest.getRenderUrl());
            JasperTemplate jasperTemplate = JasperTemplate.FILTER_CASES;
            HashMap<String,String> params = new HashMap();
            params.put("swb", SWBUtils.getApplicationPath()+"/swbadmin/images/swb-logo-hor.jpg");
            JRResource jrResource = new JRRtfResource(jasperTemplate.getTemplatePath(), params, datasource.orderJRReport());
            jrResource.prepareReport();
            jrResource.exportReport(response);
        }catch (Exception e){
            log.error("Error on method doFilterCasesRtf with id" + " " + getResourceBase().getId(), e);
        }
    }

    private void getSelectUsers(PrintWriter out) throws SWBResourceException {
        Iterator<UserRepository> listur = SWBContext.listUserRepositories();
        while (listur.hasNext()) {
            UserRepository ur = listur.next();
            Iterator<User> itusers = ur.listUsers();
            while (itusers.hasNext()) {
                User user = itusers.next();
                out.print("                      <option value=\"" + user.getLogin() + "\">" + user.getFullName() + "</option>\n");
            }
        }
    }

    private String getURLInstance(String id) {
        if (null != id)
            return "http://www.process.swb#swp_ProcessInstance:" + id;
        else
            return "";
    }

    private String getLabelObject(ProcessObject obj, SWBParamRequest paramRequest) {
        SemanticObject sob = SemanticObject.getSemanticObject(obj.getURI());
        SemanticClass cls = sob.getSemanticClass();
        if (null!=cls.getRootClass().getLabel(paramRequest.getUser().getLanguage()))
            return Ajax.specialChars(cls.getRootClass().getLabel(paramRequest.getUser().getLanguage()));
        else
            return Ajax.specialChars(cls.getRootClass().getName());
    }
}