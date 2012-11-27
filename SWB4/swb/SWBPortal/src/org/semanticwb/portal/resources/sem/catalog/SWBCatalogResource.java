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
package org.semanticwb.portal.resources.sem.catalog;

import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import org.semanticwb.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.model.base.FormElementBase;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.SWBForms;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

public class SWBCatalogResource extends org.semanticwb.portal.resources.sem.catalog.base.SWBCatalogResourceBase {

    /** The log. */
    private Logger log = SWBUtils.getLogger(SWBCatalogResource.class);
    /** The MOD e_ form. */
    private String MODE_FORM = "FORM";
    /** The hm form ele. */
    private HashMap<String, SemanticObject> hmFormEle = null;
    private Resource base = null;
    private static String COLL_ID = "collid";
    static final String FE_MODE_VIEW = "view";
    static final String FE_MODE_EDIT = "edit";
    static final String FE_DEFAULT = "generico";
    private static int counter = 0;

    public SWBCatalogResource() {
    }

    /**
     * Constructs a SWBCatalogResource with a SemanticObject
     * @param base The SemanticObject with the properties for the SWBCatalogResource
     */
    public SWBCatalogResource(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Process the mode request by the session user.
     *
     * @param request , this holds the parameters
     * @param response , an answer to the user request
     * @param paramRequest , a list of objects like user, webpage, Resource, ...
     * @throws SWBResourceException, a Resource Exception
     * @throws IOException, an In Out Exception
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        if (paramRequest.getMode().equals(MODE_FORM)) {
            doForm(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

        base = getResourceBase();
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        User user = paramsRequest.getUser();
        String id = null;
        id = getCatalogClass().getId();

        String page = request.getParameter("page");

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject semobj = getCatalogClass();

        String action = request.getParameter("act");
        if (action == null) {
            action = "";
        }

        String busqueda = request.getParameter("search");
        if (null == busqueda) {
            busqueda = "";
        }
        busqueda = busqueda.trim();
        HashMap<String, SemanticProperty> hmConfcol = new HashMap();
        HashMap<String, String> hmConfcolFE = new HashMap();
        HashMap<String, SemanticProperty> hmConfbus = new HashMap();
        HashMap<String, SemanticProperty> hmConfbusOrder = new HashMap();
        HashMap<String, SemanticObject> hmfiltro = new HashMap();
        HashMap<String, SemanticObject> hmSearchParam = new HashMap();
        HashMap<String, String> hmSearchParamBoo = new HashMap();

        if (semobj != null) {
            SemanticClass sccol = getCatalogClass().transformToSemanticClass();
            SWBModel colmodel = getCatalogModel();
            SemanticModel semmodel = colmodel.getSemanticObject().getModel();
            SemanticProperty semProphm = null;
            Iterator<String> its = listListPropertieses();
            while (its.hasNext()) {
                String spropname = its.next();
                StringTokenizer stoken = new StringTokenizer(spropname, "|");
                if (stoken != null) {
                    String suri = stoken.nextToken();
                    String surife = stoken.nextToken();
                    String suriorder = stoken.nextToken();
                    semProphm = ont.getSemanticProperty(suri);
                    if (semProphm != null) {
                        hmConfcol.put(suriorder, semProphm);
                        hmConfcolFE.put(suriorder, surife);
                    }
                } else {
                    semProphm = ont.getSemanticProperty(spropname);
                    if (semProphm != null) {
                        hmConfcol.put(spropname, semProphm);
                    }
                }
            }

            ArrayList list = new ArrayList(hmConfcol.keySet());
            Collections.sort(list);

            semProphm = null;
            its = listSearchPropertieses();
            while (its.hasNext()) {
                String spropname = its.next();
                StringTokenizer stoken = new StringTokenizer(spropname, "|");
                if (stoken != null) {
                    String suri = stoken.nextToken();
                    String surife = stoken.nextToken();
                    String suriorder = stoken.nextToken();
                    semProphm = ont.getSemanticProperty(suri);
                    if (semProphm != null) {
                        hmConfbus.put(suri, semProphm);  //suri
                        hmConfbusOrder.put(suriorder, semProphm);
                    }
                }
            }

            if (hmConfcol.size() > 0) {

                String idform = "" + System.currentTimeMillis();

                out.println("    <script type=\"text/javascript\">");
                out.println("      dojo.require(\"dojo.parser\");");
                out.println("      dojo.require(\"dijit.TitlePane\");");
                out.println("      dojo.require(\"dijit.form.Form\");");
                out.println("      dojo.require(\"dijit.form.CheckBox\");");
                out.println("      dojo.require(\"dijit.form.Textarea\");");
                out.println("      dojo.require(\"dijit.form.FilteringSelect\");");
                out.println("      dojo.require(\"dijit.form.TextBox\");");
                out.println("      dojo.require(\"dijit.form.DateTextBox\");");
                out.println("      dojo.require(\"dijit.form.Button\");");
                out.println("      dojo.require(\"dijit.form.NumberSpinner\");");
                out.println("      dojo.require(\"dijit.form.Slider\");");
                out.println("      dojo.require(\"dojox.form.BusyButton\");");
                out.println("      dojo.require(\"dojox.form.TimeSpinner\");");
                out.println("    </script>");

                //Armado de tabla
                out.println("<div >");
                out.println("<div id=\"filterprop/" + idform + "\" dojoType=\"dijit.TitlePane\" title=\"Búsqueda y Filtrado de elementos\" class=\"admViewProperties\" open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
                SWBResourceURL urls = paramsRequest.getRenderUrl();
                urls.setParameter("act", "stpBusqueda");
                urls.setParameter("act2", "ssearch");
                out.println("<form id=\"" + idform + "/fsearch\" name=\"" + idform + "/fsearch\" method=\"post\" action=\"" + urls + "\" >"); //onsubmit=\"submitForm('" + id + "/fsearch');return false;\"
                out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
                out.println("<fieldset>");
                out.println("<table width=\"100%\" >");
                out.println("<tr><td colspan=\"2\">&nbsp;</td></tr>");
                out.println("<tr><td align=\"right\" width=\"200\">");
                out.println("<label for=\"" + idform + "_search\">" + paramsRequest.getLocaleString("searchInProperties") + ": </label>");
                out.println("</td><td>");
                out.println("<input type=\"text\" name=\"search\" id=\"" + idform + "_search\" value=\"" + busqueda + "\">");
                out.println("</td><tr>");

                ////////////////////////////////////////////////////////////////////////////////////
                //////////////
                //////////////     GENERACION DE CONTROLES PARA FILTRADO
                //////////////
                ////////////////////////////////////////////////////////////////////////////////////

                ArrayList<String> listfilters = new ArrayList(hmConfbusOrder.keySet());
                Collections.sort(listfilters);

                Iterator<String> itsprops = null; //hmConfbus.values().iterator();
                itsprops = listfilters.iterator();


                while (itsprops.hasNext()) {
                    String strOrderKey = itsprops.next();
                    SemanticProperty semanticProperty = hmConfbusOrder.get(strOrderKey); //itsprops.next();
                    String bpropName = semanticProperty.getName();
                    String paramsearch = null;
                    if (request.getParameter("search_" + bpropName) != null && request.getParameter("search_" + bpropName).trim().length() > 0) {
                        paramsearch = request.getParameter("search_" + bpropName);

                        if (semanticProperty.isDataTypeProperty() && semanticProperty.isBoolean()) {
                            hmSearchParamBoo.put("search_" + bpropName, paramsearch);
                        }
                        hmSearchParam.put("search_" + bpropName, ont.getSemanticObject(paramsearch));

                    }

                    if (request.getParameter(semanticProperty.getName()) != null && request.getParameter(semanticProperty.getName()).trim().length() > 0) {
                        hmSearchParam.put("search_" + semanticProperty.getName(), ont.getSemanticObject(request.getParameter(semanticProperty.getName())));
                    }
                    if (semanticProperty.isDataTypeProperty()) {

                        /////////////////////////////////////////////////////
                        ////////////
                        ////////////   GENERACION DE CONTROL BOOLEAN PROPERTY
                        ////////////
                        /////////////////////////////////////////////////////


                        if (semanticProperty.isBoolean()) {
                            out.println("<tr><td align=\"right\" width=\"200\">");
                            out.println("<label>" + semanticProperty.getDisplayName(user.getLanguage()) + ": </label>");
                            out.println("</td><td>");
                            out.println("<label for=\"" + idform + "_" + bpropName + "_si\">" + paramsRequest.getLocaleString("booleanYes") + " </label><input type=\"radio\" name=\"search_" + bpropName + "\" id=\"" + idform + "_" + bpropName + "_si\" value=\"" + paramsRequest.getLocaleString("booleanYes") + "\" " + (paramsearch != null && paramsearch.equals(paramsRequest.getLocaleString("booleanYes")) ? "checked" : "") + ">");
                            out.println("<label for=\"" + idform + "_" + bpropName + "_no\">" + paramsRequest.getLocaleString("booleanNo") + " </label><input type=\"radio\" name=\"search_" + bpropName + "\" id=\"" + idform + "_" + bpropName + "_no\" value=\"" + paramsRequest.getLocaleString("booleanNo") + "\" " + (paramsearch != null && paramsearch.equals(paramsRequest.getLocaleString("booleanNo")) ? "checked" : "") + ">");
                            out.println("<label for=\"" + idform + "_" + bpropName + "_todos\">" + paramsRequest.getLocaleString("booleanAll") + "</label><input type=\"radio\" name=\"search_" + bpropName + "\" id=\"" + idform + "_" + bpropName + "_todos\" value=\"\" " + (paramsearch == null ? "checked" : "") + ">");
                            out.println("</td><tr>");
                        }

                        ////////////////////////////////////////////////////////

                    } else if (semanticProperty.isObjectProperty()) {
                        SemanticClass sc = semanticProperty.getRangeClass();

                        /////////////////////////////////////////////////////
                        ////////////
                        ////////////   GENERACION DE CONTROL OBJECT PROPERTY
                        ////////////
                        /////////////////////////////////////////////////////

                        out.println(objectPropertyControl(request, semanticProperty, idform, paramsearch, user, sc, semmodel));

                        ///////////////////////////////////////////////////////

                    }

                }

                //
                // REVISION DE OBJECT-PROPERTIES PARA OPCIONES DE FILTRADO
                //

                out.println("<tr><td colspn=\"2\">&nbsp;</td></tr>");
                out.println("</table>");
                out.println("</fieldset>");
                out.println("<fieldset>");
                out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramsRequest.getLocaleString("btnSearch") + "</button>"); //
                out.println("</fieldset>");
                out.println("</form>");

                out.println("    <script type=\"dojo/method\" event=\"postCreate\" args=\"\" >");
                out.println("        if(!this.open){");
                out.println("            this.hideNode.style.display=this.wipeNode.style.display=\"none\";");
                out.println("        }");
                out.println("        this._setCss();");
                out.println("        dojo.setSelectable(this.titleNode,false);");
                out.println("        dijit.setWaiState(this.containerNode,\"labelledby\",this.titleNode.id);");
                out.println("        dijit.setWaiState(this.focusNode,\"haspopup\",\"true\");");
                out.println("        var _1=this.hideNode,_2=this.wipeNode;");
                out.println("        this._wipeIn=dojo.fx.wipeIn({");
                out.println("            node:this.wipeNode,");
                out.println("            duration:this.duration,");
                out.println("            beforeBegin:function(){");
                out.println("                _1.style.display=\"\";");
                out.println("            },");
                out.println("            onEnd:function(){");
                out.println("                //alert(\"open\");");
                out.println("                dijit.byId(\"leftSplit\").layout();");
                out.println("            }");
                out.println("            });");
                out.println("        this._wipeOut=dojo.fx.wipeOut({");
                out.println("            node:this.wipeNode,");
                out.println("            duration:this.duration,");
                out.println("            onEnd:function(){");
                out.println("                _1.style.display=\"none\";");
                out.println("                //alert(\"close\");");
                out.println("                dijit.byId(\"leftSplit\").layout();");
                out.println("            }");
                out.println("            });");
                out.println("    </script>");
                out.println("</div>");

                out.println("<fieldset>");
                out.println("<table width=\"100%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>");
                out.println("&nbsp;");
                out.println("</th>");

                Iterator<String> itcol = list.iterator();
                while (itcol.hasNext()) {
                    String key = itcol.next();
                    SemanticProperty scol = hmConfcol.get(key);
                    out.println("<th>");
                    out.println(scol.getDisplayName(user.getLanguage()));
                    out.println("</th>");
                }
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");


                ///////////////////////////////////////////////////////////////////////
                //////
                //////    FILTRADO DE RESULTADOS
                //////
                ///////////////////////////////////////////////////////////////////////

                SemanticObject semO = null;
                Iterator<SemanticObject> itso = semmodel.listInstancesOfClass(sccol); //gobj.getSemanticObject().getModel().listInstancesOfClass(sccol); //sccol.listInstances();

                itso = getResultFilter(hmSearchParam, hmSearchParamBoo, hmConfcol, hmConfbus, busqueda, semmodel, paramsRequest);

                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                //PAGINACION

                List<SemanticObject> cplist = SWBUtils.Collections.copyIterator(itso);
                Set<SemanticObject> setso = null;
                if (sccol.isSubClass(Traceable.swb_Traceable)) {
                    if (cplist.size() > 1) {
                        setso = SWBComparator.sortByCreatedSet(cplist.iterator(), false);
                        itso = setso.iterator();
                    } else {
                        itso = cplist.iterator();
                    }
                } else {
                    itso = cplist.iterator();
                }
                int ps = 10;
                int l = cplist.size();
                int p = 0;
                if (page != null) {
                    p = Integer.parseInt(page);
                }
                int x = 0;
                if (l == 0) {
                    out.println("<tr><td colspan=\"" + list.size() + "\">");
                    out.println("No se encontraron registros.");
                    out.println("</td></tr>");
                } else {
                    while (itso.hasNext()) {

                        semO = itso.next();

                        //PAGINACION ////////////////////
                        if (x < p * ps) {
                            x++;
                            continue;
                        }
                        if (x == (p * ps + ps) || x == l) {
                            break;
                        }
                        x++;
                        /////////////////////////////////

                        out.println("<tr>");
                        out.println("<td>");

                        SWBResourceURL urlr = paramsRequest.getActionUrl();
                        urlr.setParameter("suri", id);
                        urlr.setParameter("sval", semO.getURI());
                        urlr.setParameter("ract", action);
                        //urlr.setParameter("page", ""+p);
                        urlr.setAction("removeso");
                        out.println("<a href=\"#\" title=\"" + paramsRequest.getLocaleString("remove") + "\" onclick=\"if(confirm('" + paramsRequest.getLocaleString("confirm_remove") + " " + SWBUtils.TEXT.scape4Script(semO.getDisplayName(user.getLanguage())) + "?')){ window.location='" + urlr + "'; } else { return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("remove") + "\"></a>");
                        SWBResourceURL urlform = paramsRequest.getRenderUrl();
                        urlform.setMode(MODE_FORM);
                        urlform.setParameter("suri", id);
                        urlform.setParameter("clsuri", getCatalogClass().transformToSemanticClass().getURI());
                        urlform.setParameter("sval", semO.getURI());
                        urlform.setParameter("ract", action);
                        urlform.setParameter("act", "edit");
                        out.println("<a href=\"#\"  title=\"" + paramsRequest.getLocaleString("documentAdmin") + "\" onclick=\"window.location='" + urlform + "';\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("documentAdmin") + "\"></a>");
                        out.println("</td>");
                        itcol = list.iterator();
                        while (itcol.hasNext()) {
                            String scol = itcol.next();
                            if (hmConfcol.get(scol) != null) {
                                out.println("<td>");
                                out.println(reviewSemProp(hmConfcol.get(scol), semO, paramsRequest));
                                out.println("</td>");
                            }
                        }
                        out.println("</tr>");

                    }
                }
                out.println("</tbody>");

                //boton para agregar instancia
                out.println("</table>");
                out.println("</fieldset>");

                if (p > 0 || x < l) //Requiere paginacion
                {
                    out.println("<fieldset>");
                    out.println("<center>");

                    int pages = (int) (l / ps);
                    if ((l % ps) > 0) {
                        pages++;
                    }

                    int inicia = 0;
                    int finaliza = pages;


                    int rangoinicial = p - 5;
                    int rangofinal = p + 5;
                    if (pages <= 10) {
                        inicia = 0;
                        finaliza = pages;
                    } else {
                        if (rangoinicial < 0) {
                            inicia = 0;
                            finaliza = Math.abs(rangoinicial) + rangofinal;
                        } else if (rangofinal > pages) {
                            inicia = pages - 10;
                            finaliza = pages;
                        } else {
                            inicia = rangoinicial;
                            finaliza = rangofinal;
                        }
                    }

                    if (pages > 10) {
                        SWBResourceURL urlIni = paramsRequest.getRenderUrl();
                        urlIni.setParameter("suri", id);
                        urlIni.setParameter("page", "" + 0);
                        urlIni.setParameter("act", "stpBusqueda");
                        urlIni.setParameter("search", busqueda);
                        urlIni.setParameter("clsuri", sccol.getURI());
                        out.println("<a href=\"#\" onclick=\"window.location='" + urlIni + "';\">Ir al inicio</a> ");
                    }

                    for (int z = inicia; z < finaliza; z++) {
                        SWBResourceURL urlNew = paramsRequest.getRenderUrl();
                        urlNew.setParameter("suri", id);
                        urlNew.setParameter("page", "" + z);
                        urlNew.setParameter("act", "stpBusqueda");
                        urlNew.setParameter("search", busqueda);
                        urlNew.setParameter("clsuri", sccol.getURI());

                        if (z != p) {
                            out.println("<a href=\"#\" onclick=\"window.location='" + urlNew + "';\">" + (z + 1) + "</a> ");
                        } else {
                            out.println((z + 1) + " ");
                        }
                    }
                    if (pages > 10) {
                        SWBResourceURL urlIni = paramsRequest.getRenderUrl();
                        urlIni.setParameter("suri", id);
                        urlIni.setParameter("page", "" + (pages - 1));
                        urlIni.setParameter("act", "stpBusqueda");
                        urlIni.setParameter("search", busqueda);
                        urlIni.setParameter("clsuri", sccol.getURI());
                        out.println("<a href=\"#\" onclick=\"window.location='" + urlIni + "';\">Ir al final</a> ");
                    }
                    out.println("</center>");
                    out.println("</fieldset>");
                }


                SWBResourceURL url = paramsRequest.getRenderUrl();
                url.setParameter("act", "new");
                url.setParameter("suri", id);
                url.setParameter("clsuri", sccol.getURI());
                url.setMode(MODE_FORM);
                out.println("<fieldset>");
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"window.location='" + url + "';\">" + paramsRequest.getLocaleString("Add_Instance") + " " + base.getTitle() + "</button>"); //
                out.println("</fieldset>");
                out.println("</div>");
            } else {
                out.println("<div class=\"swbform\">");
                out.println("<fieldset>");
                out.println("<legend>" + paramsRequest.getLocaleString("errorMsgConfig") + " \"" + base.getDisplayTitle(user.getLanguage()) + "\" </legend>");
                out.println("<ul style=\"list-style:none;\">");
                out.println("<li>");
                out.println(paramsRequest.getLocaleString("msgMissingConfigProperties"));
                out.println("</li>");
                out.println("</ul>");
                out.println("</fieldset>");
                SWBResourceURL urlreload = paramsRequest.getRenderUrl();
                urlreload.setParameter("act", "config");
                urlreload.setParameter("suri", id);
                out.println("<fieldset>");
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlreload + "',this.domNode); return false;\">" + paramsRequest.getLocaleString("btnReload") + "</button>"); //
                out.println("</fieldset>");
                out.println("</div>");

            }

        }

    }

    public Iterator<SemanticObject> getResultFilter(HashMap<String, SemanticObject> hmSearchParam, HashMap<String, String> hmSearchParamBoo, HashMap<String, SemanticProperty> hmConfcol, HashMap<String, SemanticProperty> hmConfbus, String busqueda, SemanticModel semmodel, SWBParamRequest paramsRequest) {


        SemanticObject semO = null;
        Iterator<String> itcol = null;

        HashMap<String, SemanticObject> hmfiltro = new HashMap();

        SemanticClass sccol = getCatalogClass().transformToSemanticClass();
        Iterator<SemanticObject> itso = semmodel.listInstancesOfClass(sccol);
        ;



        String urikey = null;
        SemanticProperty semOProp = null;
        if (!busqueda.trim().equals("")) {
            while (itso.hasNext()) {
                semO = itso.next();
                itcol = hmConfcol.keySet().iterator();
                String occ = "";
                while (itcol.hasNext()) {
                    String sprop = itcol.next();
                    semOProp = hmConfcol.get(sprop);
                    urikey = semOProp.getURI();
                    if (hmConfbus.get(urikey) != null) {
                        occ = occ + reviewSemProp(hmConfbus.get(urikey), semO, paramsRequest);
                    }
                }
                occ = occ.toLowerCase();
                if (occ.indexOf(busqueda.trim().toLowerCase()) > -1) {
                    hmfiltro.put(semO.getURI(), semO);
                }
            }
        }

        //Llenado de la tabla

        if (!busqueda.equals("") || !hmSearchParam.isEmpty()) {

            HashMap<String, SemanticObject> hmResults = new HashMap();
            HashMap<String, SemanticObject> hmRemove = new HashMap();
            Iterator<SemanticObject> itsprop2 = null;
            if (!busqueda.equals("") || (!busqueda.equals("") && !hmSearchParam.isEmpty())) {
                itsprop2 = hmfiltro.values().iterator();
            } else if (busqueda.equals("") && !hmSearchParam.isEmpty()) {
                itsprop2 = semmodel.listInstancesOfClass(sccol);
            }

            if (!hmSearchParam.isEmpty()) {
                while (itsprop2.hasNext()) {

                    SemanticObject sofil = itsprop2.next();

                    Iterator<SemanticProperty> sempropit = hmConfbus.values().iterator();
                    while (sempropit.hasNext()) {

                        SemanticProperty semanticProp = sempropit.next();
                        if (semanticProp.isObjectProperty()) {
                            SemanticClass semclass = semanticProp.getRangeClass();
                            if (semclass != null) {
                                if (hmSearchParam.get("search_" + semclass.getClassId()) != null) {
                                    SemanticObject so = hmSearchParam.get("search_" + semclass.getClassId());
                                    String URIfilter = so.getURI();

                                    SemanticObject sotmp = sofil.getObjectProperty(semanticProp);
                                    if (sotmp != null && sotmp.getURI().equals(URIfilter)) {
                                        hmResults.put(sofil.getURI(), sofil);
                                    } else {
                                        hmRemove.put(sofil.getURI(), sofil);
                                    }
                                }
                                if (hmSearchParam.get("search_" + semanticProp.getName()) != null) {
                                    SemanticObject so = hmSearchParam.get("search_" + semanticProp.getName());
                                    String URIfilter = so.getURI();
                                    SemanticObject sotmp = sofil.getObjectProperty(semanticProp);
                                    if (sotmp != null && sotmp.getURI().equals(URIfilter)) {
                                        hmResults.put(sofil.getURI(), sofil);
                                    } else {
                                        hmRemove.put(sofil.getURI(), sofil);
                                    }
                                }
                            }
                        } else if (semanticProp.isDataTypeProperty() && semanticProp.isBoolean()) {
                            if (hmSearchParamBoo.get("search_" + semanticProp.getName()) != null) {
                                String strfilter = hmSearchParamBoo.get("search_" + semanticProp.getName());
                                String strActualValue = reviewSemProp(semanticProp, sofil, paramsRequest);
                                if (strActualValue != null && strActualValue.equals(strfilter)) {
                                    hmResults.put(sofil.getURI(), sofil);
                                } else {
                                    hmRemove.put(sofil.getURI(), sofil);
                                }
                            }
                        }
                    }
                }
                //limpiando el filtro
                hmfiltro = new HashMap();
                Iterator<SemanticObject> itresults = hmResults.values().iterator();
                while (itresults.hasNext()) {
                    SemanticObject semObject = itresults.next();
                    if (hmRemove.get(semObject.getURI()) == null) {
                        hmfiltro.put(semObject.getURI(), semObject);
                    }
                }
            }

            itso = hmfiltro.values().iterator();

        } else {
            itso = semmodel.listInstancesOfClass(sccol); //gobj.getSemanticObject().getModel().listInstancesOfClass(sccol); //sccol.listInstances();
        }

        return itso;
    }

    /**
     * Do form.
     * 
     * @param request the request
     * @param response the response
     * @param paramRequest the param request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doForm(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");
        String cid = request.getParameter("clsuri");
        String act = request.getParameter("act");
        String sval = request.getParameter("sval");

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = null;

        if (sval != null) {
            obj = ont.getSemanticObject(sval);
        } else {
            obj = getCatalogClass();
        }

        SemanticClass sclass = obj.transformToSemanticClass();

        SWBResourceURL urlPA = paramRequest.getActionUrl();

        SWBFormMgr fmgr = null;
        String modeform = null;
        HashMap<String, SemanticProperty> hmProps = new HashMap<String, SemanticProperty>();
        HashMap<String, String> hmDetail = new HashMap<String, String>();
        HashMap<String, SemanticProperty> hmDetailOrder = new HashMap<String, SemanticProperty>();
        HashMap<SemanticProperty, String> hmDetailFEMode = new HashMap<SemanticProperty, String>();

        //Agregando las propiedades seleccionadas para la edición
        Iterator<String> itdetProp = listDetailPropertieses();
        while (itdetProp.hasNext()) {
            String spropname = itdetProp.next();

            StringTokenizer stoken = new StringTokenizer(spropname, "|");
            if (stoken != null) {
                String suri = stoken.nextToken();
                String surife = stoken.nextToken();
                String suriorder = stoken.nextToken();
                String modo = stoken.nextToken();
                String propCreate = stoken.nextToken();

                SemanticProperty semProphm = ont.getSemanticProperty(suri);
                hmDetail.put(suri, spropname);
                hmDetailOrder.put(suriorder, semProphm);
                hmDetailFEMode.put(semProphm, surife + "|" + modo + "|" + propCreate);
            }
        }

        ArrayList list = new ArrayList(hmDetailOrder.keySet());
        Collections.sort(list);

        SWBResourceURL urlback = paramRequest.getRenderUrl();
        urlback.setMode(SWBResourceURL.Mode_VIEW);
        urlback.setParameter("suri", id);
        if (null != cid) {
            urlback.setParameter("clsuri", cid);
        }
        if (null != act) {
            urlback.setParameter("act", act);
        }
        counter++;
        out.println(SWBFormMgr.DOJO_REQUIRED);
        StringBuffer sbForm = new StringBuffer("");
        if (act != null && act.equals("new")) {

            fmgr = new SWBFormMgr(sclass, getCatalogModel().getSemanticObject(), SWBFormMgr.MODE_CREATE);
            fmgr.setType(SWBFormMgr.TYPE_DOJO);
            urlPA.setAction("new");
            sbForm.append("\n<form id=\"Cat" + counter + "\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\"" + urlPA + "\"  method=\"post\"> ");
            sbForm.append("\n<input type=\"hidden\" name=\"suri\" value=\"" + id + "\"/>");
            sbForm.append("\n<input type=\"hidden\" name=\"scls\" value=\"" + cid + "\"/>");
            sbForm.append("\n<input type=\"hidden\" name=\"smode\" value=\"create\"/>");
            sbForm.append("\n<input type=\"hidden\" name=\"act\" value=\"new\"/>");
            sbForm.append("\n<input type=\"hidden\" name=\"ract\" value=\"" + act + "\"/>");
            sbForm.append("\n<input type=\"hidden\" name=\"clsuri\" value=\"" + cid + "\"/>");
            sbForm.append("\n<fieldset><legend>Agregar " + getCatalogClass().transformToSemanticClass().getName() + "</legend><table>");
            
            if(!sclass.isAutogenId())
            {
                sbForm.append(fmgr.getIdentifierElement());
            }
            
            //Agregando las propiedades seleccionadas para la creación
            Iterator<String> itdis = list.iterator();
            while (itdis.hasNext()) {
                String key = itdis.next();//order
                SemanticProperty sPro = hmDetailOrder.get(key); //propiedad
                String feMode = hmDetailFEMode.get(sPro); //combinacion fe|modo|usarCreacion
                String feuri = null;
                String modo = null;
                String useCreate = null;
                if (feMode != null) {
                    StringTokenizer stoken = new StringTokenizer(feMode, "|");
                    feuri = stoken.nextToken();
                    modo = stoken.nextToken();
                    useCreate = stoken.nextToken();

                    if (useCreate != null && useCreate.equals("true") ) { //&& hmProps.get(sPro.getURI())!=null
                        
                        GenericObject sofe = null;

                        //Juan esto esta mal llega generico o " "
                        if (!FE_DEFAULT.equals(feuri) && !" ".equals(feuri)) {
                            sofe = ont.getGenericObject(feuri);
                        }

                        FormElement fe = (FormElement) sofe;
                        if (null != fe && sPro != null) {
                            try {
                                fe.setModel(getCatalogModel().getSemanticModel());
                                ((FormElementBase) fe).setFilterHTMLTags(fmgr.isFilterHTMLTags());

                                fmgr.renderProp(request, sbForm, sPro, fe, SWBFormMgr.MODE_CREATE);
                            } catch (Exception e) {
                                log.error("Error al procesat lapropiedad con el formelement seleccionado. ---- " + sPro.getName() + " ----- " + feuri);
                            }

                        } else if (sPro != null) {
                            fe = fmgr.getFormElement(sPro);
                            fmgr.renderProp(request, sbForm, sPro, fe, SWBFormMgr.MODE_CREATE);
                        }
                    }
                }

            }

            sbForm.append("\n</table>");
            sbForm.append("\n</fieldset>");
            sbForm.append("\n<fieldset><span align=\"center\">");
            sbForm.append("\n<button dojoType=\"dijit.form.Button\" type=\"submit\">Guardar");
            sbForm.append("\n                <script type=\"dojo/method\" event=\"startup\">");
            sbForm.append("\n                    var form = dijit.byId(\"Cat" + counter + "\");");
            sbForm.append("\n                    this.attr(\"disabled\", !form.isValid());");
            sbForm.append("\n                    this.connect(form, \"onValidStateChange\", function(state)");
            sbForm.append("\n                    {");
            sbForm.append("\n                    this.attr(\"disabled\", !state);");
            sbForm.append("\n                    });");
            sbForm.append("\n                </script>");
            sbForm.append("\n</button>");
            sbForm.append("\n<button dojoType=\"dijit.form.Button\" onclick=\"window.location = '" + urlback + "';return false;\">Cancelar</button>");
            sbForm.append("\n</span></fieldset>");
            sbForm.append("\n</form>");
            sbForm.append("\n<fieldset>");
            sbForm.append("\n<div> * Datos requeridos. </div>");
            sbForm.append("\n</fieldset>");

        } else if (act != null && act.equals("edit")) {
            fmgr = new SWBFormMgr(obj, null, SWBFormMgr.MODE_EDIT);
            fmgr.setType(SWBFormMgr.TYPE_DOJO);
            urlPA.setAction("updateform");
            sbForm.append("\n<form id=\"Cat" + counter + "\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\"" + urlPA + "\"  method=\"post\"> ");
            sbForm.append("\n<input type=\"hidden\" name=\"suri\" value=\"" + id + "\"/>");
            sbForm.append("\n<input type=\"hidden\" name=\"scls\" value=\"" + cid + "\"/>");
            sbForm.append("\n<input type=\"hidden\" name=\"smode\" value=\"edit\"/>");
            //sbForm.append("\n<input type=\"hidden\" name=\"suri\" value=\"User\"/>");
            sbForm.append("\n<input type=\"hidden\" name=\"sval\" value=\"" + sval + "\"/>");
            sbForm.append("\n<input type=\"hidden\" name=\"act\" value=\"update\"/>");
            sbForm.append("\n<input type=\"hidden\" name=\"ract\" value=\"" + act + "\"/>");
            sbForm.append("\n<input type=\"hidden\" name=\"clsuri\" value=\"" + cid + "\"/>");
            sbForm.append("\n<fieldset><legend>Detalle " + getCatalogClass().transformToSemanticClass().getName() + "</legend><table>");

            Iterator<String> itdis = list.iterator();
            while (itdis.hasNext()) {
                String key = itdis.next();
                SemanticProperty sPro = hmDetailOrder.get(key);
                String feMode = hmDetailFEMode.get(sPro);
                String feuri = null;
                String modo = null;
                String useCreate = null;
                if (feMode != null) {
                    StringTokenizer stoken = new StringTokenizer(feMode, "|");
                    feuri = stoken.nextToken();
                    modo = stoken.nextToken();
                    useCreate = stoken.nextToken();

                    GenericObject sofe = null;

                    //Juan esto esta mal llega generico o " "
                    if (!FE_DEFAULT.equals(feuri) && !" ".equals(feuri)) {
                        sofe = ont.getGenericObject(feuri);
                    }

                    FormElement fe = (FormElement) sofe;
                    if (null != fe && sPro != null) {
                        try {
                            fe.setModel(getCatalogModel().getSemanticModel());
                            ((FormElementBase) fe).setFilterHTMLTags(fmgr.isFilterHTMLTags());

                            fmgr.renderProp(request, sbForm, sPro, fe, modo);
                        } catch (Exception e) {
                            log.error("Error al procesat lapropiedad con el formelement seleccionado. ---- " + sPro.getName() + " ----- " + feuri);
                        }

                    } else if (sPro != null) {
                        fe = fmgr.getFormElement(sPro);
                        fmgr.renderProp(request, sbForm, sPro, fe, modo);
                    }
                }

            }

            sbForm.append("\n</table>");
            sbForm.append("\n</fieldset>");
            sbForm.append("\n<fieldset><span align=\"center\">");
            sbForm.append("\n<button dojoType=\"dijit.form.Button\" type=\"submit\">Guardar");
            sbForm.append("\n                <script type=\"dojo/method\" event=\"startup\">");
            sbForm.append("\n                    var form = dijit.byId(\"Cat" + counter + "\");");
            sbForm.append("\n                    this.attr(\"disabled\", !form.isValid());");
            sbForm.append("\n                    this.connect(form, \"onValidStateChange\", function(state)");
            sbForm.append("\n                    {");
            sbForm.append("\n                    this.attr(\"disabled\", !state);");
            sbForm.append("\n                    });");
            sbForm.append("\n                </script>");
            sbForm.append("\n</button>");
            sbForm.append("\n<button dojoType=\"dijit.form.Button\" onclick=\"window.location = '" + urlback + "';return false;\">Cancelar</button>");
            sbForm.append("\n</span></fieldset>");
            sbForm.append("\n</form>");
            sbForm.append("\n<fieldset>");
            sbForm.append("\n<div> * Datos requeridos. </div>");
            sbForm.append("\n</fieldset>");

        }

        out.println(sbForm.toString());

    }

    /**
     * Review sem prop.
     * 
     * @param prop the prop
     * @param obj the obj
     * @param paramsRequest the params request
     * @return the string
     */
    public String reviewSemProp(SemanticProperty prop, SemanticObject obj, SWBParamRequest paramsRequest) {
        String ret = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm:ss", new Locale(paramsRequest.getUser().getLanguage()));
            if (prop.isDataTypeProperty()) {
                if (prop.isBoolean()) {
                    boolean bvalue = obj.getBooleanProperty(prop);
                    if (bvalue) {
                        ret = paramsRequest.getLocaleString("booleanYes");
                    } else {
                        ret = paramsRequest.getLocaleString("booleanNo");
                    }

                }
                if (prop.isInt() || prop.isDouble() || prop.isLong()) {
                    ret = Long.toString(obj.getLongProperty(prop));
                }
                if (prop.isString()) {
                    ret = obj.getProperty(prop);
                }
                if (prop.isFloat()) {
                    ret = Float.toString(obj.getFloatProperty(prop));
                }
                if (prop.isDate() || prop.isDateTime()) {
                    ret = sdf.format(obj.getDateTimeProperty(prop));
                }
            } else if (prop.isObjectProperty()) {
                SemanticObject so = obj.getObjectProperty(prop);
                if (null != so) {
                    ret = so.getDisplayName(paramsRequest.getUser().getLanguage());
                }
            }
            if (null == ret || (ret != null && ret.trim().equals("null"))) {
                ret = "";
            }

        } catch (Exception e) {
        }
        if (ret == null || (ret != null && ret.trim().equals("null"))) {
            ret = "";
        }
        return ret;
    }

    public String getFESelect(String FEsel, SWBParamRequest paramRequest, SemanticProperty sprop) {

        User usr = paramRequest.getUser();
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getSchema();
        SemanticVocabulary sv = SWBPlatform.getSemanticMgr().getVocabulary();
        StringBuilder ret = new StringBuilder();
        ret.append("\n<optgroup label=\"Genérico\">");
        ret.append("\n<option value=\"generico\" selected >GenericFormElement</option>");
        ret.append("\n</optgroup>");

        HashMap<String, SemanticClass> hmscfe = new HashMap<String, SemanticClass>();
        HashMap<String, SemanticObject> hmso = null;

        //Property prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_ANNOT_FORMELEMENTRANGE).getRDFProperty();
        SemanticProperty pro = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_ANNOT_FORMELEMENTRANGE);
        //System.out.println("prop:" + pro.getName());


        Iterator<SemanticClass> itsub = sv.getSemanticClass(sv.SWB_SWBFORMELEMENT).listSubClasses();
        while (itsub.hasNext()) {
            SemanticClass scobj = itsub.next();

            if (pro == null || sprop == null) {
                continue;
            }
            NodeIterator it = scobj.getOntClass().listPropertyValues(pro.getRDFProperty());

            while (it.hasNext()) {
                RDFNode node = it.next();
                //System.out.println("node:" + node + " " + sprop.getRange());
                if (node != null) {
                    if (sprop.getRange() != null && sprop.getRange().getURI().equals(node.asResource().getURI())) {
                        hmscfe.put(scobj.getDisplayName(usr.getLanguage()), scobj);
                    } else if (sprop.getRangeClass() != null && node.isResource()) {
                        SemanticClass cls = sv.getSemanticClass(node.asResource().getURI());
                        if (cls != null) {
                            if (sprop.getRangeClass() != null && sprop.getRangeClass().isSubClass(cls)) {
                                hmscfe.put(scobj.getDisplayName(usr.getLanguage()), scobj);
                            }
                        }
                    }
                }
            }
        }

        ArrayList list = new ArrayList(hmscfe.keySet());
        Collections.sort(list);

        Iterator<String> itsc = list.iterator();
        while (itsc.hasNext()) {

            String key = itsc.next();
            SemanticClass scfe = hmscfe.get(key);

            ret.append("\n<optgroup label=\"");
            ret.append(scfe.getDisplayName(paramRequest.getUser().getLanguage()));
            ret.append("\">");
            hmso = new HashMap<String, SemanticObject>();

            Iterator<SemanticObject> itsco = ont.listInstancesOfClass(scfe);
            while (itsco.hasNext()) {
                SemanticObject semObj = itsco.next();
                hmso.put(semObj.getDisplayName(usr.getLanguage()), semObj);
            }

            list = new ArrayList(hmso.keySet());
            Collections.sort(list);

            Iterator<String> itsoo = list.iterator();
            while (itsoo.hasNext()) {
                key = itsoo.next();
                SemanticObject sofe = hmso.get(key);
                ret.append("\n<option value=\"");
                ret.append(sofe.getURI());
                ret.append("\"");
                String stmp = FEsel + "edit|" + sofe.getURI();
                String stmp2 = FEsel + "view|" + sofe.getURI();
                String data = paramRequest.getResourceBase().getData(paramRequest.getWebPage());
                if (FEsel != null && !FEsel.equals("") && (data != null && (data.indexOf(stmp) > -1 || data.indexOf(stmp2) > -1) || FEsel.equals(sofe.getURI()))) {
                    ret.append(" selected ");
                }
                ret.append(">");
                ret.append(sofe.getDisplayName(paramRequest.getUser().getLanguage()));
                ret.append("\n</option>");
            }
            ret.append("\n</optgroup>");
        }

        return ret.toString();
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

        //System.out.println("doAdmin()");
        base = getResourceBase();
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        User user = paramsRequest.getUser();
        String id = request.getParameter("suri");
        String page = request.getParameter("page");

        // Para filtrar el tipo de funcionamiento del despliegue.
        String collectiontype = base.getAttribute("collectype", "config");

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = ont.getGenericObject(id);

        if (hmFormEle == null) {
            hmFormEle = new HashMap<String, SemanticObject>();
            SemanticOntology sont = SWBPlatform.getSemanticMgr().getSchema();
            SemanticVocabulary sv = SWBPlatform.getSemanticMgr().getVocabulary();
            Iterator<SemanticObject> itfe = sont.listInstancesOfClass(sv.getSemanticClass(sv.SWB_FORMELEMENT));
            while (itfe.hasNext()) {
                SemanticObject sofe = itfe.next();
                hmFormEle.put(sofe.getURI(), sofe);
            }
        }
        String action = request.getParameter("act");
        if (action == null) {
            action = "";
        }

        if (request.getParameter("dialog") != null && request.getParameter("dialog").equals("close")) {
            out.println("<script type=\"javascript\">");
            out.println(" hideDialog(); ");
            out.println(" reloadTab('" + id + "'); ");
            out.println("</script>");
            return;
        }

        out.println("<script type=\"text/javascript\">");
        if (request.getParameter("nsuri") != null && request.getParameter("nsuri").trim().length() > 0) {
            SemanticObject snobj = ont.getSemanticObject(request.getParameter("nsuri"));
            if (snobj != null) {
                log.debug("addNewTab");
                out.println("  addNewTab('" + snobj.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + SWBUtils.TEXT.scape4Script(snobj.getDisplayName()) + "');");
            }
        }

        if (request.getParameter("statmsg") != null && request.getParameter("statmsg").trim().length() > 0) {
            log.debug("showStatus");
            //System.out.println("showstatus...." + request.getParameter("statmsg"));
            out.println("   showStatus('" + request.getParameter("statmsg") + "');");
        }

        if (request.getParameter("closetab") != null && request.getParameter("closetab").trim().length() > 0) {
            log.debug("closeTab..." + request.getParameter("closetab"));
            out.println("   closeTab('" + request.getParameter("closetab") + "');");
        }
        out.println("</script>");

        if (collectiontype.equals("config")) {

            if (getCatalogClass() != null) {
                SemanticClass sccol = getCatalogClass().transformToSemanticClass();
                SWBModel colmodel = getCatalogModel();
                //if(colmodel==null) colmodel = col.getWebSite().getSubModel();

                HashMap<String, String> hmcol = new HashMap();
                HashMap<String, String> hmbus = new HashMap();
                HashMap<String, String> hmFE = new HashMap();
                HashMap<String, String> hmdet = new HashMap();
                HashMap<String, String> hmFEdet = new HashMap();

                Iterator<String> its = listListPropertieses();
                while (its.hasNext()) {
                    String spropname = its.next();
                    StringTokenizer stoken = new StringTokenizer(spropname, "|");
                    String suriprop = stoken.nextToken();
                    String surife = stoken.nextToken();
                    String sindice = stoken.nextToken();

                    if (stoken != null) {
                        hmcol.put(suriprop, sindice);
                    } else {
                        hmcol.put(spropname, spropname);
                    }
                }
                its = listSearchPropertieses();
                while (its.hasNext()) {
                    String spropname = its.next();
                    StringTokenizer stoken = new StringTokenizer(spropname, "|");

                    if (spropname.indexOf("|") != -1 && stoken != null) {
                        //System.out.println("tokens: "+spropname);
                        String spropuri = stoken.nextToken();
                        String propFE = stoken.nextToken();
                        String colOrder = stoken.nextToken();
                        hmbus.put(spropuri, colOrder);
                        hmFE.put(spropuri, propFE);
                    } else {
                        hmbus.put(spropname, spropname);
                    }
                }
                its = listDetailPropertieses();
                while (its.hasNext()) {
                    String spropname = its.next();
                    StringTokenizer stoken = new StringTokenizer(spropname, "|");

                    if (spropname.indexOf("|") != -1 && stoken != null) {
                        //System.out.println("tokens: "+spropname);
                        try {
                            String spropuri = stoken.nextToken();
                            String propFE = stoken.nextToken();
                            String colOrder = stoken.nextToken();
                            hmdet.put(spropuri, colOrder);
                            hmFEdet.put(spropuri, propFE);
                        } catch (Exception e) {
                            removeDetailProperties(spropname);
                        }


                    } else {
                        hmdet.put(spropname, spropname);
                    }
                }


                SWBResourceURL urlconf = paramsRequest.getActionUrl();
                urlconf.setAction("updconfig");
                urlconf.setParameter("ract", "config");


                String idform = "" + System.currentTimeMillis();

                out.println("<div class=\"swbform\">");

                out.println("<form type=\"dijit.form.Form\" id=\"" + idform + "/collectionconfig\" name=\"" + idform + "/collectionconfig\" action=\"" + urlconf + "\" method=\"post\" onsubmit=\"submitForm('" + idform + "/collectionconfig'); return false;\"  >"); //
                out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
                out.println("<input type=\"hidden\" name=\"act\" value=\"\">");
                out.println("<input type=\"hidden\" id=\"" + idform + "_actbutton\" name=\"actbutton\" value=\"\">");

                out.println("<fieldset>");
                out.println("<legend>" + "Configuración de catálogo" + " " + base.getDisplayTitle(user.getLanguage()) + "</legend>");
                out.println("<ul style=\"list-style:none;\">");
                out.println("<li>");
                out.println("<label for=\"" + idform + "_collclass\">" + "Clase asociada" + "</label>");
                out.println("<select id=\"" + idform + "_collclass\" name=\"collclass\">");
                //out.println("<input type=\"text\" name=\"classname\" value=\"" + col.getCollectionClass().getDisplayName(user.getLanguage()) + "\" readonly >");

                Iterator<SemanticObject> itsemcls = null; //col.getSemanticObject().getModel().listModelClasses();
                itsemcls = SWBPlatform.getSemanticMgr().getSchema().listInstancesOfClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(SWBPlatform.getSemanticMgr().getVocabulary().SWB_CLASS));
                while (itsemcls.hasNext()) {
                    SemanticObject semobj = itsemcls.next();
                    //System.out.println(semobj+" "+semobj.getURI()+" "+semobj.transformToSemanticClass());
                    SemanticClass semClass = semobj.transformToSemanticClass();
                    out.println("<option value=\"" + semClass.getURI() + "\" ");
                    if (sccol.getURI().equals(semClass.getURI())) {
                        out.println(" selected ");
                    }
                    out.println(">");
                    out.println(semClass.getClassId());
                    out.println("</option>");
                }
                out.println("</select>");
                out.println("</li>");
                out.println("</ul>");
                out.println("</fieldset>");

                WebSite wb = base.getWebSite();

                out.println("<fieldset>");
                out.println("<legend>" + "Tipo de modelo a utilizar" + "</legend>");
                out.println("<ul style=\"list-style:none;\">");
                out.println("<li>");
                out.println("<input type=\"radio\" id=\"" + idform + "_radiomodel1\" name=\"usemodel\" value=\"" + wb.getURI() + "\" " + (colmodel.getURI().equals(wb.getURI()) ? "checked" : "") + ">");
                out.println("<label for=\"" + idform + "_radiomodel1\">" + "Sitio " + wb.getDisplayTitle(user.getLanguage()) + "</label>");
                out.println("</li>");
                out.println("<li>");
                UserRepository usrrep = wb.getUserRepository();
                out.println("<input type=\"radio\" id=\"" + idform + "_radiomodel2\" name=\"usemodel\" value=\"" + usrrep.getURI() + "\" " + (colmodel.getURI().equals(usrrep.getURI()) ? "checked" : "") + " >");
                out.println("<label for=\"" + idform + "_radiomodel2\">" + usrrep.getDisplayTitle(user.getLanguage()) + "</label>");
                out.println("</li>");
                out.println("<li>");
                Iterator<SWBModel> itmodel = wb.listSubModels();
                while (itmodel.hasNext()) {
                    SWBModel model = itmodel.next();
                    if (!wb.getURI().equals(model.getURI()) && !usrrep.getURI().equals(model.getURI())) {
                        out.println("<input type=\"radio\" id=\"" + idform + "_radiomodel3\" name=\"usemodel\" value=\"" + model.getURI() + "\" " + (colmodel.getURI().equals(model.getURI()) ? "checked" : "") + " >");
                        out.println("<label for=\"" + idform + "_radiomodel3\">" + "Repositorio de documentos " + model.getId() + "</label>");
                        break;
                    }
                }
                out.println("</li>");
                out.println("</ul>");
                out.println("</fieldset>");
                out.println("<fieldset>");
                out.println("<button dojoType=\"dijit.form.Button\" _type=\"button\"  id=\"" + idform + "_btnClass\">" + paramsRequest.getLocaleString("btn_updt"));
                out.println("<script type=\"dojo/method\" event=\"onClick\" >");
                //out.println(" var miform = dojo.byId('"+ id + "/collectionconfig'); ");
                out.println(" var actbut = dojo.byId('" + idform + "_actbutton'); ");
                out.println(" actbut.value='updtclass'; ");
                out.println(" submitForm('" + idform + "/collectionconfig'); ");
                out.println(" return false; ");
                out.println("</script>");
                out.println("</button>");
                out.println("</fieldset>");
                out.println("</form>");

                //////////////////////////////////////////////////////////////////////////////////////////

                String strproptype = request.getParameter("proptype");
                if (null == strproptype) {
                    strproptype = "display";
                }

                HashMap<String, String> hmsc = new HashMap();
                HashMap<String, SemanticProperty> hmprops = new HashMap();
                HashMap<String, SemanticProperty> hmselected = new HashMap();

                SemanticClass cls = getCatalogClass().transformToSemanticClass();
                String clsName = "";
                if (cls != null) {
                    clsName = cls.getName();
                    Iterator<SemanticProperty> itp = cls.listProperties();
                    while (itp.hasNext()) {
                        SemanticProperty prop = itp.next();
                        //System.out.println("PropURI: "+prop.getURI());
                        String name = cls.getName() + "|" + prop.getURI();
                        hmsc.put(name, prop.getPropertyCodeName());
                        hmprops.put(name, prop);
                    }
                }


                String chkproptype = request.getParameter("proptype");
                if (chkproptype == null) {
                    chkproptype = "display";
                }

                hmselected = getExistentes(hmprops, chkproptype);

                SWBResourceURL urladd = paramsRequest.getActionUrl();
                urladd.setAction("addprops");

                //long idform = System.currentTimeMillis();

                out.println("<form type=\"dijit.form.Form\" action=\"" + urladd + "\" id=\"" + idform + "/forma\" method=\"post\" onsubmit=\"if(enviatodos('" + idform + "/existentes')) { submitForm('" + idform + "/forma'); return false; } else { return false;}\">");
                out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
                out.println("<fieldset>");
                out.println("<legend>" + "Configuración" + "</legend>");
                out.println("<table border=\"0\" width=\"100%\" >");
                out.println("<tr>");
                out.println("<th>Propiedades");
                out.println("</th>");
                out.println("<th>");
                out.println("</th>");
                out.println("<th>Seleccionadas");
                out.println("</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>");

                ArrayList list = new ArrayList(hmprops.keySet());
                Collections.sort(list);

                // select con la lista de propiedades existentes
                out.println("<select size=\"10\" name=\"propiedades\" id=\"" + idform + "/propiedades\" multiple style=\"width: 100%;\">");
                Iterator<String> itss = list.iterator();
                while (itss.hasNext()) {

                    String str = itss.next();
                    String varName = "";
                    String propid = "";
                    //System.out.println("props keyset: " + str);
                    StringTokenizer stoken = new StringTokenizer(str, "|");
                    if (stoken.hasMoreTokens()) {
                        varName = stoken.nextToken();
                        propid = stoken.nextToken();
                    }
                    if (hmselected.get(str) == null) {
                        SemanticProperty sp = hmprops.get(clsName + "|" + propid);
                        out.println("<option value=\"" + sp.getURI() + "\">");
                        out.println(varName + "." + sp.getPropertyCodeName());
                        out.println("</option>");
                    }
                }
                out.println("</select>");
                out.println("</td>");
                out.println("<td valign=\"middle\" width=\"25px\">");
                // botones
                out.println("<button dojoType=\"dijit.form.Button\" type = \"button\" style=\"width: 25px;\" id = \"" + idform + "btnMoveLeft\" onclick = \"MoveItems('" + idform + "/existentes','" + idform + "/propiedades');\"><-</button><br>");
                out.println("<button dojoType=\"dijit.form.Button\" type = \"button\" style=\"width: 25px;\" id = \"" + idform + "btnMoveRight\" onclick = \"MoveItems('" + idform + "/propiedades','" + idform + "/existentes');\">-></button>");
                out.println("</td>");
                out.println("<td>");
                // select con la lista de propiedades seleccionadas
                out.println("<select size=\"10\" name=\"existentes\" id=\"" + idform + "/existentes\" multiple style=\"width: 100%;\">");
                its = hmselected.keySet().iterator();
                while (its.hasNext()) {
                    String str = its.next();
                    String varName = "";
                    String propid = "";
                    StringTokenizer stoken = new StringTokenizer(str, "|");
                    if (stoken.hasMoreTokens()) {
                        varName = stoken.nextToken();
                        propid = stoken.nextToken();
                    }


                    SemanticProperty sp = hmprops.get(clsName + "|" + propid);
                    if (sp != null) {
                        out.println("<option value=\"" + sp.getURI() + "\">"); //str
                        out.println(varName + "." + sp.getPropertyCodeName());
                        out.println("</option>");
                    }

                }
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");

                //botones para despliegue en forma básica
                SWBResourceURL urlreload = paramsRequest.getRenderUrl();
                out.println("<tr>");
                out.println("<td>");
                out.println("<input type=\"radio\"  dojoType=\"dijit.form.RadioButton\" name=\"proptype\" id=\"" + idform + "_display\" value=\"display\" " + (strproptype.equals("display") ? "checked" : "") + " onclick=\"submitUrl('" + urlreload + "?proptype=display',this.domNode); return false;\">");

                out.println("<label for=\"" + idform + "_display\">Propiedad de despliegue</label>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<input type=\"radio\" dojoType=\"dijit.form.RadioButton\" name=\"proptype\" id=\"" + idform + "_search\" value=\"search\" " + (strproptype.equals("search") ? "checked" : "") + " onclick=\"submitUrl('" + urlreload + "?proptype=search',this.domNode); return false;\">");

                out.println("<label for=\"" + idform + "_search\">Propiedad de busqueda</label>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<input type=\"radio\" dojoType=\"dijit.form.RadioButton\" name=\"proptype\" id=\"" + idform + "_detail\" value=\"detail\" " + (strproptype.equals("detail") ? "checked" : "") + " onclick=\"submitUrl('" + urlreload + "?proptype=detail',this.domNode); return false;\">");

                out.println("<label for=\"" + idform + "_detail\">Propiedad de detalle</label>");
                out.println("</td>");
                out.println("</tr>");

                out.println("<tr>");
                out.println("<td colspan=\"3\" >");
                // botones para guadar cambios
                out.println("<button  dojoType=\"dijit.form.Button\" type=\"submit\">Guardar</button>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</fieldset>");
                out.println("</form>");

                /////////////////////////////////////////////////////////////////////////



                out.println("<div id=\"configcol/" + idform + "\" dojoType=\"dijit.TitlePane\" title=\"Configuración despliegue\" class=\"admViewProperties\" open=\"true\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
                out.println("<fieldset>");

                out.println("<p align=\"center\">");
                out.println("<table width=\"90%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th width=\"70\">");
                out.println("&nbsp;"); //paramsRequest.getLocaleString("th_action")
                out.println("</th>");
                out.println("<th>");
                out.println(paramsRequest.getLocaleString("th_property"));
                out.println("</th>");

                out.println("</tr>");
                out.println("</thead>");

                out.println("<tbody>");

                String semprop = null;
                String sempropFE = null;
                String sorder = null;
                int min = 0, max = 0, indice = 0;

                HashMap<String, String> hmlistprop = new HashMap();

                int numlistprop = 0;
                Iterator<String> itdis = listListPropertieses();
                while (itdis.hasNext()) {
                    String slistprop = itdis.next();
                    numlistprop++;
                    try {
                        StringTokenizer stoken = new StringTokenizer(slistprop, "|");
                        semprop = stoken.nextToken();
                        sempropFE = stoken.nextToken();
                        sorder = stoken.nextToken();
                        if (sorder != null) {
                            indice = Integer.parseInt(sorder);
                        }

                        if (min == 0 && max == 0) {
                            min = indice;
                            max = indice;
                        }

                        if (indice < min) {
                            min = indice;
                        }
                        if (indice > max) {
                            max = indice;
                        }
                        hmlistprop.put(sorder, slistprop);

                    } catch (Exception e) {
                        log.error("Error in display class property.", e);
                        continue;
                    }
                }
                //System.out.println("rango( "+min+" , "+max+" )");

                ArrayList lists = new ArrayList(hmlistprop.keySet());
                Collections.sort(lists);


                int countprop = 0;
                itdis = lists.iterator(); //col.listListPropertieses();
                while (itdis.hasNext()) {
                    String key = itdis.next();
                    String lprop = hmlistprop.get(key);

                    countprop++;
                    //System.out.println("valor display ..."+lprop);
                    try {
                        StringTokenizer stoken = new StringTokenizer(lprop, "|");
                        semprop = stoken.nextToken();
                        sempropFE = stoken.nextToken();
                        sorder = stoken.nextToken();
                        indice = Integer.parseInt(sorder);
                        //System.out.println("Indice:"+indice);
                    } catch (Exception e) {
                        log.error("Error in display class property.", e);
                        //continue;
                    }
                    out.println("<tr>");
                    out.println("<td  width=\"70\" align=\"left\">");
                    SWBResourceURL urlrem = paramsRequest.getActionUrl();
                    urlrem.setAction("remove");
                    urlrem.setParameter("suri", id);
                    urlrem.setParameter("sval", lprop);
                    urlrem.setParameter("prop", "display");
                    urlrem.setParameter("ract", "config");
                    out.println("<a href=\"#\" onclick=\"submitUrl('" + urlrem + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("remove") + "\"></a>");
                    if (indice != max) {
                        SWBResourceURL urldown = paramsRequest.getActionUrl();
                        urldown.setAction("swap");
                        urldown.setParameter("suri", id);
                        urldown.setParameter("sval", lprop);
                        urldown.setParameter("sorder", sorder);
                        urldown.setParameter("prop", "display");
                        urldown.setParameter("direction", "down");
                        urldown.setParameter("ract", "config");
                        out.println("<a href=\"#\" onclick=\"submitUrl('" + urldown + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/down.jpg\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("down") + "\"></a>");
                    } else {
                        out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/space.jpg\" border=\"0\" >");
                    }

                    if (indice != min) //sorderdown>0
                    {
                        SWBResourceURL urlup = paramsRequest.getActionUrl();
                        urlup.setAction("swap");
                        urlup.setParameter("suri", id);
                        urlup.setParameter("sval", lprop);
                        urlup.setParameter("sorder", sorder);
                        urlup.setParameter("prop", "display");
                        urlup.setParameter("direction", "up");
                        urlup.setParameter("ract", "config");
                        out.println("<a href=\"#\" onclick=\"submitUrl('" + urlup + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/up.jpg\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("up") + "\"></a>");
                    }
                    out.println("</td>");
                    out.println("<td align=\"left\">");
                    out.println(ont.getSemanticProperty(semprop).getDisplayName(user.getLanguage()));
                    out.println("</td>");
                    out.println("</tr>");
                }

                out.println("</tbody>");
                out.println("</table>");


                out.println("    <script type=\"dojo/method\" event=\"postCreate\" args=\"\" >");
                out.println("        if(!this.open){");
                out.println("            this.hideNode.style.display=this.wipeNode.style.display=\"none\";");
                out.println("        }");
                out.println("        this._setCss();");
                out.println("        dojo.setSelectable(this.titleNode,false);");
                out.println("        dijit.setWaiState(this.containerNode,\"labelledby\",this.titleNode.id);");
                out.println("        dijit.setWaiState(this.focusNode,\"haspopup\",\"true\");");
                out.println("        var _1=this.hideNode,_2=this.wipeNode;");
                out.println("        this._wipeIn=dojo.fx.wipeIn({");
                out.println("            node:this.wipeNode,");
                out.println("            duration:this.duration,");
                out.println("            beforeBegin:function(){");
                out.println("                _1.style.display=\"\";");
                out.println("            },");
                out.println("            onEnd:function(){");
                out.println("                //alert(\"open\");");
                out.println("                dijit.byId(\"leftSplit\").layout();");
                out.println("            }");
                out.println("            });");
                out.println("        this._wipeOut=dojo.fx.wipeOut({");
                out.println("            node:this.wipeNode,");
                out.println("            duration:this.duration,");
                out.println("            onEnd:function(){");
                out.println("                _1.style.display=\"none\";");
                out.println("                //alert(\"close\");");
                out.println("                dijit.byId(\"leftSplit\").layout();");
                out.println("            }");
                out.println("            });");
                //this.inherited(arguments);
                out.println("    </script>");
                out.println("</p>");
                out.println("</fieldset>");
                //out.println("</fieldset>");
                out.println("</div>");


                out.println("<div id=\"configbus/" + idform + "\" dojoType=\"dijit.TitlePane\" title=\"Configuración búsqueda\" class=\"admViewProperties\" open=\"true\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
                out.println("<fieldset>");
                out.println("<p align=\"center\">");
                out.println("<table width=\"90%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th width=\"70\">");
                out.println("&nbsp;"); //paramsRequest.getLocaleString("th_action"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramsRequest.getLocaleString("th_property"));
                out.println("</th>");
                out.println("</tr>");
                out.println("</thead>");

                out.println("<tbody>");

                semprop = null;
                sempropFE = null;
                sorder = null;

                min = 0;
                max = 0;
                indice = 0;

                HashMap<String, String> hmSearchprop = new HashMap();

                int numsearchprop = 0;
                itdis = listSearchPropertieses();
                while (itdis.hasNext()) {
                    String slistprop = itdis.next();
                    numsearchprop++;
                    try {
                        StringTokenizer stoken = new StringTokenizer(slistprop, "|");
                        semprop = stoken.nextToken();
                        sempropFE = stoken.nextToken();
                        sorder = stoken.nextToken();
                        indice = Integer.parseInt(sorder);

                        if (min == 0 && max == 0) {
                            min = indice;
                            max = indice;
                        }

                        if (indice < min) {
                            min = indice;
                        }
                        if (indice > max) {
                            max = indice;
                        }
                        hmSearchprop.put(sorder, slistprop);

                    } catch (Exception e) {
                        log.error("Error in display class property.", e);
                        continue;
                    }
                }
                //System.out.println("rango( "+min+" , "+max+" )");

                list = new ArrayList(hmSearchprop.keySet());
                Collections.sort(list);

                itdis = list.iterator();
                while (itdis.hasNext()) {
                    String key = itdis.next();
                    String lprop = hmSearchprop.get(key);
                    try {
                        StringTokenizer stoken = new StringTokenizer(lprop, "|");
                        semprop = stoken.nextToken();
                        sempropFE = stoken.nextToken();
                        sorder = stoken.nextToken();
                        indice = Integer.parseInt(sorder);
                        //System.out.println("Indice:"+indice);

                    } catch (Exception e) {
                        log.error("Error in search class property.", e);
                        continue;
                    }
                    out.println("<tr>");
                    out.println("<td  width=\"70\" align=\"left\">");
                    SWBResourceURL urlrem = paramsRequest.getActionUrl();
                    urlrem.setAction("remove");
                    urlrem.setParameter("suri", id);
                    urlrem.setParameter("sval", lprop);
                    urlrem.setParameter("prop", "search");
                    urlrem.setParameter("ract", "config");
                    out.println("<a href=\"#\" onclick=\"submitUrl('" + urlrem + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("remove") + "\"></a>");
                    if (indice != max) {
                        SWBResourceURL urldown = paramsRequest.getActionUrl();
                        urldown.setAction("swap");
                        urldown.setParameter("suri", id);
                        urldown.setParameter("sval", lprop);
                        urldown.setParameter("sorder", sorder);
                        urldown.setParameter("prop", "search");
                        urldown.setParameter("direction", "down");
                        urldown.setParameter("ract", "config");

                        out.println("<a href=\"#\" onclick=\"submitUrl('" + urldown + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/down.jpg\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("down") + "\"></a>");
                    } else {
                        out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/space.jpg\" border=\"0\" >");
                    }

                    if (indice != min) //sorderdown>0
                    {
                        SWBResourceURL urlup = paramsRequest.getActionUrl();
                        urlup.setAction("swap");
                        urlup.setParameter("suri", id);
                        urlup.setParameter("sval", lprop);
                        urlup.setParameter("sorder", sorder);
                        urlup.setParameter("prop", "search");
                        urlup.setParameter("direction", "up");
                        urlup.setParameter("ract", "config");
                        out.println("<a href=\"#\" onclick=\"submitUrl('" + urlup + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/up.jpg\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("up") + "\"></a>");
                    }
                    out.println("</td>");
                    out.println("<td align=\"left\">");
                    out.println(ont.getSemanticProperty(semprop).getDisplayName(user.getLanguage()));
                    out.println("</td>");
                    out.println("</tr>");
                }

                out.println("</tbody>");
                out.println("</table>");

                out.println("    <script type=\"dojo/method\" event=\"postCreate\" args=\"\" >");
                out.println("        if(!this.open){");
                out.println("            this.hideNode.style.display=this.wipeNode.style.display=\"none\";");
                out.println("        }");
                out.println("        this._setCss();");
                out.println("        dojo.setSelectable(this.titleNode,false);");
                out.println("        dijit.setWaiState(this.containerNode,\"labelledby\",this.titleNode.id);");
                out.println("        dijit.setWaiState(this.focusNode,\"haspopup\",\"true\");");
                out.println("        var _1=this.hideNode,_2=this.wipeNode;");
                out.println("        this._wipeIn=dojo.fx.wipeIn({");
                out.println("            node:this.wipeNode,");
                out.println("            duration:this.duration,");
                out.println("            beforeBegin:function(){");
                out.println("                _1.style.display=\"\";");
                out.println("            },");
                out.println("            onEnd:function(){");
                out.println("                //alert(\"open\");");
                out.println("                dijit.byId(\"leftSplit\").layout();");
                out.println("            }");
                out.println("            });");
                out.println("        this._wipeOut=dojo.fx.wipeOut({");
                out.println("            node:this.wipeNode,");
                out.println("            duration:this.duration,");
                out.println("            onEnd:function(){");
                out.println("                _1.style.display=\"none\";");
                out.println("                //alert(\"close\");");
                out.println("                dijit.byId(\"leftSplit\").layout();");
                out.println("            }");
                out.println("            });");
                //this.inherited(arguments);
                out.println("    </script>");
                out.println("</p>");
                out.println("</fieldset>");
                //out.println("</fieldset>");
                out.println("</div>");


                //////////////////////////////////////////////////////////////////////////////
                // configuración de las propiedades a mostrar en el detalle
                //////////////////////////////////////////////////////////////////////////////

                out.println("<div id=\"configdet/" + idform + "\" dojoType=\"dijit.TitlePane\" title=\"Configuración detalle\" class=\"admViewProperties\" open=\"true\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
                out.println("<fieldset>");
                out.println("<p align=\"center\">");
                out.println("<table width=\"90%\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th width=\"70\">");
                out.println("&nbsp;"); //paramsRequest.getLocaleString("th_action"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramsRequest.getLocaleString("th_property"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramsRequest.getLocaleString("th_formelement"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramsRequest.getLocaleString("th_MODE_VIEW"));
                out.println("</th>");
                out.println("<th>");
                out.println(paramsRequest.getLocaleString("th_FOR_CREATE"));
                out.println("</th>");
                out.println("</tr>");
                out.println("</thead>");

                out.println("<tbody>");

                semprop = null;
                sempropFE = null;
                sorder = null;
                String modo = null;
                String show = null;

                min = 0;
                max = 0;
                indice = 0;

                HashMap<String, String> hmDetailprop = new HashMap();

                int numdetailprop = 0;
                itdis = listDetailPropertieses();
                while (itdis.hasNext()) {
                    String slistprop = itdis.next();
                    numsearchprop++;
                    try {
                        StringTokenizer stoken = new StringTokenizer(slistprop, "|");
                        semprop = stoken.nextToken();
                        sempropFE = stoken.nextToken();
                        sorder = stoken.nextToken();
                        indice = Integer.parseInt(sorder);

                        if (stoken.hasMoreTokens()) {
                            modo = stoken.nextToken();
                        }
                        if (stoken.hasMoreTokens()) {
                            show = stoken.nextToken();
                        }


                        if (min == 0 && max == 0) {
                            min = indice;
                            max = indice;
                        }

                        if (indice < min) {
                            min = indice;
                        }
                        if (indice > max) {
                            max = indice;
                        }
                        hmDetailprop.put(sorder, slistprop);

                    } catch (Exception e) {
                        log.error("Error in detail class property.", e);
                        continue;
                    }
                }
                //System.out.println("rango( "+min+" , "+max+" )");

                list = new ArrayList(hmDetailprop.keySet());
                Collections.sort(list);

                itdis = list.iterator();
                while (itdis.hasNext()) {
                    modo = null;
                    show = null;
                    String key = itdis.next();
                    String lprop = hmDetailprop.get(key);
                    try {
                        StringTokenizer stoken = new StringTokenizer(lprop, "|");
                        semprop = stoken.nextToken();
                        sempropFE = stoken.nextToken();
                        sorder = stoken.nextToken();
                        indice = Integer.parseInt(sorder);

                        if (stoken.hasMoreTokens()) {
                            modo = stoken.nextToken();
                        }
                        if (stoken.hasMoreTokens()) {
                            show = stoken.nextToken();
                        }
                        //System.out.println("Indice:"+indice);

                    } catch (Exception e) {
                        log.error("Error in detail class property.", e);
                        continue;
                    }
                    out.println("<tr>");
                    out.println("<td  width=\"70\" align=\"left\">");
                    SWBResourceURL urlrem = paramsRequest.getActionUrl();
                    urlrem.setAction("remove");
                    urlrem.setParameter("suri", id);
                    urlrem.setParameter("sval", lprop);
                    urlrem.setParameter("prop", "detail");
                    urlrem.setParameter("ract", "config");
                    out.println("<a href=\"#\" onclick=\"submitUrl('" + urlrem + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("remove") + "\"></a>");
                    if (indice != max) {
                        SWBResourceURL urldown = paramsRequest.getActionUrl();
                        urldown.setAction("swap");
                        urldown.setParameter("suri", id);
                        urldown.setParameter("sval", lprop);
                        urldown.setParameter("sorder", sorder);
                        urldown.setParameter("prop", "detail");
                        urldown.setParameter("direction", "down");
                        urldown.setParameter("ract", "config");

                        out.println("<a href=\"#\" onclick=\"submitUrl('" + urldown + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/down.jpg\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("down") + "\"></a>");
                    } else {
                        out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/space.jpg\" border=\"0\" >");
                    }

                    if (indice != min) //sorderdown>0
                    {
                        SWBResourceURL urlup = paramsRequest.getActionUrl();
                        urlup.setAction("swap");
                        urlup.setParameter("suri", id);
                        urlup.setParameter("sval", lprop);
                        urlup.setParameter("sorder", sorder);
                        urlup.setParameter("prop", "detail");
                        urlup.setParameter("direction", "up");
                        urlup.setParameter("ract", "config");
                        out.println("<a href=\"#\" onclick=\"submitUrl('" + urlup + "',this); return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/up.jpg\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("up") + "\"></a>");
                    }
                    out.println("</td>");
                    out.println("<td align=\"left\">");
                    out.println(ont.getSemanticProperty(semprop).getDisplayName(user.getLanguage()));
                    out.println("</td>");
                    out.println("<td align=\"left\">");
                    SWBResourceURL urlupd = paramsRequest.getActionUrl();
                    urlupd.setAction("updtItem");
                    urlupd.setParameter("prop", key);
                    urlupd.setParameter("suri", id);
                    urlupd.setParameter("sval", lprop);
                    urlupd.setParameter("proptype", "detail");

                    out.println("<select id=\"" + id + "/" + key + "/sfe\" name=\"formElement\" style=\"width:300px;\" onchange=\"updItem('" + urlupd + "','feuri',this);\" >"); //
                    out.println(getFESelect(sempropFE, paramsRequest, hmprops.get(clsName + "|" + semprop)));
                    out.println("</select>");
                    out.println("</td>");
                    out.println("<td align=\"left\">");

                    SWBResourceURL urlmod = paramsRequest.getActionUrl();

                    urlmod.setParameter("sval", lprop);
                    urlmod.setParameter("prop", "mode");
                    urlmod.setAction("updPropMode");

                    out.println("<select  id=\"" + id + "/" + base.getId() + "/" + semprop + "/elementMode\"  name=\"elementMode\"  style=\"width:100px;\" onchange=\"submitUrl('" + urlmod + "&elementMode='+this.value,this.domNode);return false;\">");  //dojoType=\"dijit.form.FilteringSelect\"  autocomplete=\"false\" hasDownArrow=\"true\"

                    if (null == modo) {
                        modo = "edit";
                    }
                    out.println("<option value=\"edit\" " + (modo.equals("edit") ? "selected" : "") + " >Edit</option>");
                    out.println("<option value=\"view\" " + (modo.equals("view") ? "selected" : "") + " >View</option>");
                    out.println("</select>");

                    out.println("</td>");
                    out.println("<td align=\"canter\">");

                    SWBResourceURL urlchk = paramsRequest.getActionUrl();
                    urlchk.setParameter("sval", lprop);
                    urlchk.setParameter("prop", "show");
                    urlchk.setAction("updPropMode");

                    out.println("<input type=\"checkbox\" " + (show != null && show.equals("true") ? "checked" : " ") + " value=\"true\" name=\"showItemCreate\" onclick=\"submitUrl('" + urlchk + "&showItemCreate='+this.checked,this.domNode);\">");
                    out.println("</td>");
                    out.println("</tr>");
                }

                out.println("</tbody>");
                out.println("</table>");

                out.println("    <script type=\"dojo/method\" event=\"postCreate\" args=\"\" >");
                out.println("        if(!this.open){");
                out.println("            this.hideNode.style.display=this.wipeNode.style.display=\"none\";");
                out.println("        }");
                out.println("        this._setCss();");
                out.println("        dojo.setSelectable(this.titleNode,false);");
                out.println("        dijit.setWaiState(this.containerNode,\"labelledby\",this.titleNode.id);");
                out.println("        dijit.setWaiState(this.focusNode,\"haspopup\",\"true\");");
                out.println("        var _1=this.hideNode,_2=this.wipeNode;");
                out.println("        this._wipeIn=dojo.fx.wipeIn({");
                out.println("            node:this.wipeNode,");
                out.println("            duration:this.duration,");
                out.println("            beforeBegin:function(){");
                out.println("                _1.style.display=\"\";");
                out.println("            },");
                out.println("            onEnd:function(){");
                out.println("                //alert(\"open\");");
                out.println("                dijit.byId(\"leftSplit\").layout();");
                out.println("            }");
                out.println("            });");
                out.println("        this._wipeOut=dojo.fx.wipeOut({");
                out.println("            node:this.wipeNode,");
                out.println("            duration:this.duration,");
                out.println("            onEnd:function(){");
                out.println("                _1.style.display=\"none\";");
                out.println("                //alert(\"close\");");
                out.println("                dijit.byId(\"leftSplit\").layout();");
                out.println("            }");
                out.println("            });");
                out.println("    </script>");
                out.println("</p>");
                out.println("</fieldset>");
                out.println("</div>");

                // out.println("</form >");
                out.println("</div >");
            } else {

                SWBResourceURL urlconf = paramsRequest.getActionUrl();
                urlconf.setAction("updconfig");
                urlconf.setParameter("ract", "config");

                out.println("<div class=\"swbform\">");
                out.println("<form type=\"dijit.form.Form\" id=\"" + id + "/collectionconfig\" name=\"" + id + "/collectionconfig\" action=\"" + urlconf + "\" method=\"post\" _onsubmit=\"submitForm('" + id + "/collectionconfig'); return false;\"  >"); //
                out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
                out.println("<input type=\"hidden\" name=\"act\" value=\"\">");
                out.println("<input type=\"hidden\" id=\"" + id + "_actbutton\" name=\"actbutton\" value=\"\">");

                out.println("<fieldset>");
                out.println("<legend>" + "Configuración de catálogo" + " " + base.getDisplayTitle(user.getLanguage()) + "</legend>");
                out.println("<ul style=\"list-style:none;\">");
                out.println("<li>");
                out.println("<label for=\"" + id + "_collclass\">" + "Clase asociada" + "</label>");
                out.println("<select id=\"" + id + "_collclass\" name=\"collclass\">");

                Iterator<SemanticObject> itsemcls = null; //col.getSemanticObject().getModel().listModelClasses();
                itsemcls = SWBPlatform.getSemanticMgr().getSchema().listInstancesOfClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(SWBPlatform.getSemanticMgr().getVocabulary().SWB_CLASS));
                while (itsemcls.hasNext()) {
                    SemanticObject semobj = itsemcls.next();
                    SemanticClass semClass = semobj.transformToSemanticClass();
                    out.println("<option value=\"" + semClass.getURI() + "\" ");
                    //if(sccol!=null&&sccol.getURI().equals(semClass.getURI())) out.println(" selected ");
                    out.println(">");
                    out.println(semClass.getClassId());
                    out.println("</option>");
                }
                out.println("</select>");
                out.println("</li>");

                out.println("</ul>");
                out.println("</fieldset>");

                WebSite wb = base.getWebSite();


                out.println("<fieldset>");
                out.println("<legend>" + "Tipo de modelo a utilizar" + "</legend>");
                out.println("<ul style=\"list-style:none;\">");
                out.println("<li>");
                out.println("<input type=\"radio\" id=\"" + id + "_radiomodel1\" name=\"usemodel\" value=\"" + wb.getURI() + "\" checked>");
                out.println("<label for=\"" + id + "_radiomodel1\">" + "Sitio " + wb.getDisplayTitle(user.getLanguage()) + "</label>");
                out.println("</li>");
                out.println("<li>");
                UserRepository usrrep = wb.getUserRepository();
                out.println("<input type=\"radio\" id=\"" + id + "_radiomodel2\" name=\"usemodel\" value=\"" + usrrep.getURI() + "\" >");
                out.println("<label for=\"" + id + "_radiomodel2\">" + usrrep.getDisplayTitle(user.getLanguage()) + "</label>");
                out.println("</li>");
                out.println("<li>");
                Iterator<SWBModel> itmodel = wb.listSubModels();
                while (itmodel.hasNext()) {
                    SWBModel model = itmodel.next();
                    if (!wb.getURI().equals(model.getURI()) && !usrrep.getURI().equals(model.getURI())) {
                        out.println("<input type=\"radio\" id=\"" + id + "_radiomodel3\" name=\"usemodel\" value=\"" + model.getURI() + "\" >");
                        out.println("<label for=\"" + id + "_radiomodel3\">" + "Repositorio de documentos " + model.getId() + "</label>");
                        break;
                    }
                }
                out.println("</li>");
                out.println("</ul>");
                out.println("</fieldset>");
                out.println("<fieldset>");
                out.println("<button dojoType=\"dijit.form.Button\" _type=\"button\"  id=\"" + id + "_btnClass\">" + paramsRequest.getLocaleString("btn_updt"));
                out.println("<script type=\"dojo/method\" event=\"onClick\" >");
                //out.println(" var miform = dojo.byId('"+ id + "/collectionconfig'); ");
                out.println(" var actbut = dojo.byId('" + id + "_actbutton'); ");
                out.println(" actbut.value='updtclass'; ");
                out.println(" submitForm('" + id + "/collectionconfig'); ");
                out.println(" return false; ");
                out.println("</script>");
                out.println("</button>");
                out.println("</fieldset>");
                out.println("</div>");

            }
            //  }

        }

    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String id = request.getParameter("suri");
        String sval = request.getParameter("sval");
        String ract = request.getParameter("ract");
        String action = response.getAction();
        if (action == null) {
            action = "";
        }
        if (action.equals("")) {
            action = request.getParameter("act");
        }
        if (action == null) {
            action = "";
        }

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();

        if ("updconfig".equals(action)) {

            String actbutton = request.getParameter("actbutton");
            //agregando propiedades de despliegue
            String semprop = request.getParameter("semprop");
            String sempropFE = request.getParameter("sempropFE");
            int semproporder = 0;
            int max = 0;
            boolean existe = false;
            if (actbutton.equals("display") || actbutton.equals("both")) {
                Iterator<String> itorder = listListPropertieses();
                while (itorder.hasNext()) {
                    String string = itorder.next();
                    StringTokenizer stoken = new StringTokenizer(string, "|");
                    String temp = stoken.nextToken();
                    if (temp != null && temp.equals(semprop)) {
                        existe = true;
                    }
                    temp = stoken.nextToken();
                    try {
                        semproporder = Integer.parseInt(stoken.nextToken());
                        if (semproporder > max) {
                            max = semproporder;
                        }

                    } catch (Exception e) {
                        log.error("Error in display order element. SWBACollectionConfig.processAction()", e);
                    }
                }
                max++;

                if (!existe && semprop != null) {
                    addListProperties(semprop + "|Text|" + max);
                    response.setRenderParameter("statmsg", "Se agrego propiedad al despliegue.");
                }

            }

            existe = false;
            //agregando propiedades de búsqueda
            if (actbutton.equals("search") || actbutton.equals("both")) {
                semprop = request.getParameter("semprop2");
                sempropFE = request.getParameter("sempropFE2");
                semproporder = 0;
                max = 0;
                Iterator<String> itorder = listSearchPropertieses();
                while (itorder.hasNext()) {
                    String string = itorder.next();
                    StringTokenizer stoken = new StringTokenizer(string, "|");
                    String temp = stoken.nextToken();
                    if (temp != null && temp.equals(semprop)) {
                        existe = true;
                    }
                    temp = stoken.nextToken();
                    try {
                        semproporder = Integer.parseInt(stoken.nextToken());
                        if (semproporder > max) {
                            max = semproporder;
                        }

                    } catch (Exception e) {
                        log.error("Error in search order element. SWBACollectionConfig.processAction()", e);
                    }
                }
                max++;

                if (!existe && semprop != null && sempropFE != null) {
                    addSearchProperties(semprop + "|" + sempropFE + "|" + max);
                    response.setRenderParameter("statmsg", "Se agrego propiedad a la busqueda.");
                }
            }

            existe = false;
            //agregando propiedades de detalle
            if (actbutton.equals("detail") || actbutton.equals("both")) {
                semprop = request.getParameter("semprop3");
                sempropFE = request.getParameter("sempropFE3");
                semproporder = 0;
                max = 0;
                Iterator<String> itorder = listDetailPropertieses();
                while (itorder.hasNext()) {
                    String string = itorder.next();
                    StringTokenizer stoken = new StringTokenizer(string, "|");
                    String temp = stoken.nextToken();
                    if (temp != null && temp.equals(semprop)) {
                        existe = true;
                    }
                    temp = stoken.nextToken();
                    try {
                        semproporder = Integer.parseInt(stoken.nextToken());
                        if (semproporder > max) {
                            max = semproporder;
                        }

                    } catch (Exception e) {
                        log.error("Error in search order element. SWBACatalogResource.processAction()", e);
                    }
                }
                max++;

                if (!existe && semprop != null && sempropFE != null) {
                    addDetailProperties(semprop + "|" + sempropFE + "|" + max + "|edit|true");
                    response.setRenderParameter("statmsg", "Se agrego propiedad al detalle.");
                }
            }

            if (actbutton.equals("updtclass")) {

                String collclass = request.getParameter("collclass");
                String usemodel = request.getParameter("usemodel");

                if (usemodel != null) {
                    GenericObject gi = ont.getSemanticObject(usemodel).getGenericInstance();

                    if (gi != null && gi instanceof SWBModel) {
                        SWBModel colmodel = (SWBModel) gi;
                        setCatalogModel(colmodel);
                    }
                }
                SemanticObject so = ont.getSemanticObject(collclass);
                System.out.println(so+" "+collclass+" "+usemodel+" "+getCatalogClass());
                if (null != so) {
                    if (getCatalogClass()!=null && !so.equals(getCatalogClass())) {
                        removeAllDetailProperties();
                        removeAllListProperties();
                        removeAllSearchProperties();
                    }
                    setCatalogClass(so);
                }

                response.setRenderParameter("statmsg", "Se actualizo configuracion de la clase y/o modelo.");

            }

        } else if ("new".equals(action)) {


            log.debug("ProcessAction(new) ");

            id = request.getParameter("suri");
            String clsuri = request.getParameter("clsuri");

            SemanticObject so = getCatalogClass();
            SemanticClass sclass = getCatalogClass().transformToSemanticClass();
            log.debug("ProcessAction(new): sobj: " + id);

            SWBFormMgr fmgr = new SWBFormMgr(sclass, getCatalogModel().getSemanticModel().getModelObject(), SWBFormMgr.MODE_CREATE);
            try {
                SemanticObject nso = fmgr.processForm(request);

                //////////////////////////////////////////////////////////////////////////////////////
                /// ACTUALIZANDO INFORMACION ADICIONAL
                //////////////////////////////////////////////////////////////////////////////////////
                SWBFormMgr fmgr2 = new SWBFormMgr(nso, null, SWBFormMgr.MODE_EDIT);

                HashMap<String, SemanticProperty> hmProps = new HashMap<String, SemanticProperty>();

                //Agregando las propiedades requeridas para la creación del elemento
                Iterator<SemanticProperty> itsempro = fmgr.getProperties().iterator();
                while (itsempro.hasNext()) {
                    SemanticProperty sPro = itsempro.next();
                    if (sPro.isRequired()) {
                        //System.out.println("..Agregando propiedad requerida..." + sPro.getName());
                        hmProps.put(sPro.getURI(), sPro);
                    }
                }

                //Actualizando las propiedades seleccionadas para la creación
                Iterator<String> itdetProp = listDetailPropertieses();
                while (itdetProp.hasNext()) {
                    String spropname = itdetProp.next();

                    StringTokenizer stoken = new StringTokenizer(spropname, "|");
                    if (stoken != null) {
                        String suri = stoken.nextToken();
                        String surife = stoken.nextToken();
                        String suriorder = stoken.nextToken();
                        String modo = stoken.nextToken();
                        String propCreate = stoken.nextToken();

                        SemanticProperty semProphm = ont.getSemanticProperty(suri);
                        if (hmProps.get(suri) == null) {
                            fmgr2.processElement(request, semProphm);
                        }
                    }
                }



                ///////////////////////////////////////////////////////////////////////////////////////
                if (clsuri != null) {
                    response.setRenderParameter("clsuri", clsuri);
                }

                response.setRenderParameter("nsuri", nso.getURI());

                response.setMode(MODE_FORM);
                response.setRenderParameter("suri", nso.getURI());
                response.setRenderParameter("sval", nso.getURI());
                response.setRenderParameter("act", "edit");
                response.setAction("edit");

            } catch (FormValidateException e) {
                log.error(e);
                throw new SWBResourceException("Error to process form...", e);
            }

            //response.setMode(response.Mode_VIEW);

        } else if ("updateform".equals(action)) {

            log.error("ProcessAction(new) ");

            id = request.getParameter("suri");
            String clsuri = request.getParameter("clsuri");
            sval = request.getParameter("sval");

            SemanticObject so = ont.getSemanticObject(sval);

            log.error("ProcessAction(updateform): sobj: " + sval + " --- " + so.getURI());

            SWBFormMgr fmgr = new SWBFormMgr(so, null, SWBFormMgr.MODE_EDIT);
            try {

                fmgr.clearProperties();

                //Agregando las propiedades seleccionadas para la creación
                Iterator<String> itdetProp = listDetailPropertieses();
                while (itdetProp.hasNext()) {
                    String spropname = itdetProp.next();

                    StringTokenizer stoken = new StringTokenizer(spropname, "|");
                    if (stoken != null) {
                        String suri = stoken.nextToken();
                        SemanticProperty semProphm = ont.getSemanticProperty(suri);
                        fmgr.processElement(request, semProphm);
                    }
                }
//                SemanticObject nso = fmgr.processForm(request);

                if (clsuri != null) {
                    response.setRenderParameter("clsuri", clsuri);
                }

                response.setRenderParameter("nsuri", so.getURI());

            } catch (Exception e) {
                log.error(e);
                throw new SWBResourceException("Error to process form...", e);
            }
            //response.setRenderParameter("statmsg", response.getLocaleString("statmsg1"));
            response.setMode(SWBActionResponse.Mode_VIEW);
        } // quita propiedad de la configuracion de la administracion
        else if ("remove".equals(action)) {
            log.debug("processAction(remove)");
            //System.out.println("sval:"+sval);
            //if (obj != null) 
            {

                String prop = request.getParameter("prop");
                //System.out.println("Eliminando ... "+prop);

                if (prop != null && prop.equals("display")) {
                    //System.out.println("quitando display");
                    removeListProperties(sval);
                    response.setRenderParameter("statmsg", "Propiedad de despliegue eliminada.");

                } else if (prop != null && prop.equals("search")) {
                    //System.out.println("quitando search");
                    removeSearchProperties(sval);
                    response.setRenderParameter("statmsg", "Propiedad de busqueda eliminanda.");

                } else if (prop != null && prop.equals("detail")) {
                    //System.out.println("quitando detail");
                    removeDetailProperties(sval);
                    response.setRenderParameter("statmsg", "Propiedad de detalle eliminada.");
                }

            }

        } else if ("removeso".equals(action)) {
            log.debug("processAction(removeso)");

            SemanticObject so = ont.getSemanticObject(sval);
            if (null != so) {
                so.remove();
            }

            log.debug("remove-closetab:" + sval);
            //response.setRenderParameter("closetab", sval);
            //response.setRenderParameter("statmsg", response.getLocaleString("statmsg2"));
            response.setMode(SWBActionResponse.Mode_VIEW);
        } else if (action.equals("updcfg")) {

            try {
                String bhtype = request.getParameter("collectype");
                //System.out.println("Config resource:"+bhtype);
                if (bhtype != null) {
                    base.setAttribute("collectype", bhtype);
                    base.updateAttributesToDB();
                }
            } catch (Exception e) {
                log.error(e);
            }
        } else if (action.equals("updPropMode")) {
            String proptype = request.getParameter("sval");  // propiedad a modificar
            String prop = request.getParameter("prop");  // mode ó show
            String newMode = request.getParameter("elementMode");  // edit ó view
            String newShow = request.getParameter("showItemCreate");  // true
            if (newShow == null) {
                newShow = "false";
            }
            String semprop = null;
            String sempropFE = null;
            String sorder = null;
            String modo = "edit";
            String itemShow = "true";

            try {
                StringTokenizer stoken = new StringTokenizer(proptype, "|");

                //System.out.println("propiedad a modificar: " + proptype);

                semprop = stoken.nextToken();
                sempropFE = stoken.nextToken();
                sorder = stoken.nextToken();
                if (stoken.hasMoreTokens()) {
                    modo = stoken.nextToken();
                }
                if (stoken.hasMoreTokens()) {
                    itemShow = stoken.nextToken();
                }
            } catch (Exception e) {
                log.error("Error in processAction.", e);
            }

            //System.out.println("Remover propiedad: " + proptype);

            removeDetailProperties(proptype);

            if ("mode".equals(prop)) {
                addDetailProperties(semprop + "|" + sempropFE + "|" + sorder + "|" + newMode + "|" + itemShow);
                response.setRenderParameter("statmsg", "Modo de la propiedad actualizada a " + newMode);
                //System.out.println("Nueva propiedad: " + semprop + "|" + sempropFE + "|" + sorder + "|" + newMode + "|" + itemShow);
            }
            if ("show".equals(prop)) {
                addDetailProperties(semprop + "|" + sempropFE + "|" + sorder + "|" + modo + "|" + newShow);
                response.setRenderParameter("statmsg", "Propiedad para creacion actualizada.");
                //System.out.println("Nueva propiedad: " + semprop + "|" + sempropFE + "|" + sorder + "|" + modo + "|" + newShow);
            }
        } else if (action.equals("swap")) {

            String proptype = request.getParameter("prop"); //search, display
            String sorder1 = request.getParameter("sorder");
            String sorder = null;
            String direct = request.getParameter("direction"); //up, down

            HashMap<String, String> hm = new HashMap<String, String>();
            Iterator<String> its = null;

            if (proptype.equals("search")) {
                its = listSearchPropertieses();
            }
            if (proptype.equals("display")) {
                its = listListPropertieses();
            }
            if (proptype.equals("detail")) {
                its = listDetailPropertieses();
            }
            String semprop = null;
            String sempropnx = null;
            String sempropFE = null;
            String sempropFEnx = null;
            String swaporder = null;
            String modo = null;
            String itemShow = null;
            String modonx = null;
            String itemShownx = null;

            while (its.hasNext()) {
                String slistprop = its.next();
                try {
                    StringTokenizer stoken = new StringTokenizer(slistprop, "|");
                    semprop = stoken.nextToken();
                    sempropFE = stoken.nextToken();
                    sorder = stoken.nextToken();

                    if (stoken.hasMoreTokens()) {
                        modo = stoken.nextToken();
                    }
                    if (stoken.hasMoreTokens()) {
                        itemShow = stoken.nextToken();
                    }

                    hm.put(sorder, slistprop);
                } catch (Exception e) {
                    log.error("Error in processAction.", e);
                }
            }

            ArrayList list = new ArrayList(hm.keySet());
            Collections.sort(list);

            String svaltempprev = null;

            its = list.iterator();
            while (its.hasNext()) {
                String key = its.next();
                String slistprop = hm.get(key);
                if (svaltempprev == null) {
                    svaltempprev = slistprop;
                }

                try {
                    StringTokenizer stoken = new StringTokenizer(slistprop, "|");
                    semprop = stoken.nextToken();
                    sempropFE = stoken.nextToken();
                    sorder = stoken.nextToken();
                    if (stoken.hasMoreTokens()) {
                        modo = stoken.nextToken();
                    }
                    if (stoken.hasMoreTokens()) {
                        itemShow = stoken.nextToken();
                    }

                } catch (Exception e) {
                    log.error("Error in processAction.", e);
                }

                if (!sorder1.equals(sorder)) {
                    svaltempprev = slistprop;
                }

                if (sval.equals(slistprop) && direct.equals("down")) {
                    String temp = null;
                    if (its.hasNext()) {
                        try {
                            key = its.next();
                            temp = hm.get(key);
                            StringTokenizer stoken = new StringTokenizer(temp, "|");
                            sempropnx = stoken.nextToken();
                            sempropFEnx = stoken.nextToken();
                            swaporder = stoken.nextToken();
                            if (stoken.hasMoreTokens()) {
                                modonx = stoken.nextToken();
                            }
                            if (stoken.hasMoreTokens()) {
                                itemShownx = stoken.nextToken();
                            }
                        } catch (Exception e) {
                            log.error("Error in processAction.", e);
                        }
                    }

                    if (proptype.equals("search")) {
                        removeSearchProperties(sval);
                        removeSearchProperties(temp);

                        addSearchProperties(semprop + "|" + sempropFE + "|" + swaporder);
                        addSearchProperties(sempropnx + "|" + sempropFEnx + "|" + sorder);
                    }
                    if (proptype.equals("display")) {
                        removeListProperties(sval);
                        removeListProperties(temp);

                        addListProperties(semprop + "|" + sempropFE + "|" + swaporder);
                        addListProperties(sempropnx + "|" + sempropFEnx + "|" + sorder);
                    }
                    if (proptype.equals("detail")) {
                        removeDetailProperties(sval);
                        removeDetailProperties(temp);

                        addDetailProperties(semprop + "|" + sempropFE + "|" + swaporder + "|" + modo + "|" + itemShow);
                        addDetailProperties(sempropnx + "|" + sempropFEnx + "|" + sorder + "|" + modonx + "|" + itemShownx);
                    }
                    break;

                }

                if (sval.equals(slistprop) && direct.equals("up")) {
                    String temp = null;
                    try {
                        temp = svaltempprev;
                        StringTokenizer stoken = new StringTokenizer(temp, "|");
                        sempropnx = stoken.nextToken();
                        sempropFEnx = stoken.nextToken();
                        swaporder = stoken.nextToken();
                        if (stoken.hasMoreTokens()) {
                            modonx = stoken.nextToken();
                        }
                        if (stoken.hasMoreTokens()) {
                            itemShownx = stoken.nextToken();
                        }
                    } catch (Exception e) {
                        log.error("Error in processAction.", e);
                    }

                    if (proptype.equals("search")) {
                        removeSearchProperties(sval);
                        removeSearchProperties(temp);

                        addSearchProperties(semprop + "|" + sempropFE + "|" + swaporder);
                        addSearchProperties(sempropnx + "|" + sempropFEnx + "|" + sorder);
                    }
                    if (proptype.equals("display")) {
                        removeListProperties(sval);
                        removeListProperties(temp);

                        addListProperties(semprop + "|" + sempropFE + "|" + swaporder);
                        addListProperties(sempropnx + "|" + sempropFEnx + "|" + sorder);
                    }
                    if (proptype.equals("detail")) {
                        removeDetailProperties(sval);
                        removeDetailProperties(temp);

                        addDetailProperties(semprop + "|" + sempropFE + "|" + swaporder + "|" + modo + "|" + itemShow);
                        addDetailProperties(sempropnx + "|" + sempropFEnx + "|" + sorder + "|" + modonx + "|" + itemShownx);
                    }
                    break;

                }

            }
            response.setRenderParameter("statmsg", "Orden de Propiedades actualizada.");

        } else if ("addprops".equals(response.getAction())) {

            String proptype_display = "display";
            String proptype_search = "search";
            String proptype_detail = "detail";

            String proptype = request.getParameter("proptype");

            String[] props = request.getParameterValues("existentes");
            HashMap<String, String> hmparam = new HashMap();
            //propiedades seleccionadas
            if (props != null && props.length > 0) {
                int j = 0;
                for (j = 0; j < props.length; j++) {
                    //System.out.println("Prop:"+props[j]);
                    hmparam.put(props[j], props[j]);
                }
            }

            Iterator<String> itstrprop = null;
            boolean isDisplayProp = false, isSearchProp = false, isDetailProp = false;
            // cargar propiedades existentes 
            // comparar nuevas con existentes, quitar las existentes que ya no vienen y agregar nuevas
            // si quedan existentes, ordenarlas, actualizar orden y al final agregar las nuevas. 
            // poner FE de defecto de cada propiedad.
            if (proptype.equals(proptype_display)) {
                itstrprop = listListPropertieses();
                isDisplayProp = true;
            } else if (proptype.equals(proptype_search)) {
                itstrprop = listSearchPropertieses();
                isSearchProp = true;
            } else if (proptype.equals(proptype_detail)) {
                itstrprop = listDetailPropertieses();
                isDetailProp = true;
            }

            if (props == null) {
                if (isDisplayProp) {
                    removeAllListProperties();
                } else if (isSearchProp) {
                    removeAllSearchProperties();
                } else if (isDetailProp) {
                    removeAllDetailProperties();
                }
            } else {


                //propiedades que se tenian guardadas
                HashMap<Integer, String> hmprops = new HashMap();
                int i = 0;
                while (itstrprop.hasNext()) {
                    String propstr = itstrprop.next();
                    String propuri = "";
                    String feuri = "";
                    String order = "";
                    String femode = "";
                    String useInCreate = "";
                    StringTokenizer stoken = new StringTokenizer(propstr, "|");

                    if (stoken.hasMoreTokens()) {
                        propuri = stoken.nextToken();
                        feuri = stoken.nextToken();
                        order = stoken.nextToken();
                    }
                    if (stoken.hasMoreTokens()) {
                        femode = stoken.nextToken();
                    }
                    if (stoken.hasMoreTokens()) {
                        useInCreate = stoken.nextToken();
                    }
                    try {
                        if (Integer.parseInt(order) > i) {
                            i = Integer.parseInt(order);
                        }
                    } catch (Exception e) {
                    }

                    // si no esta en parámetro no se agrega a las existentes
                    if (hmparam.get(propuri) != null) {
                        hmprops.put(new Integer(order), propstr);
                        hmparam.remove(propuri);
                    }

                    // se elimina propiedad existente de configuración para despues actualizar el orden
                    if (isDisplayProp) {
                        removeListProperties(propstr);
                    } else if (isSearchProp) {
                        removeSearchProperties(propstr);
                    } else if (isDetailProp) {
                        removeDetailProperties(propstr);
                    }
                }

                // en hmprops se tienen todas las propiedades configuradas, menos las que vienen en los parámetros
                // en hmparam se tienen todas las que vienen por parámetro, pueden venir también las existentes

                // Agregando las propiedades faltantes en las propiedades existentes
                Iterator<String> itparam = hmparam.keySet().iterator();
                while (itparam.hasNext()) {
                    String string = itparam.next();
                    String val = "";
                    String defaultFE = getDefaultPropFE(string);
                    //orden siguiente
                    i++;
                    if (isDisplayProp) {
                        val = string + "|Text|" + i;
                        hmprops.put(new Integer(i), val);
                    } else if (isSearchProp) {
                        val = string + "|" + defaultFE + "|" + i;
                        hmprops.put(new Integer(i), val);
                    } else if (isDetailProp) {
                        val = string + "|" + defaultFE + "|" + i + "|edit|true";
                        hmprops.put(new Integer(i), val);
                    }
                }

                // ordenando las propiedades
                ArrayList list = new ArrayList(hmprops.keySet());
                Collections.sort(list);

                //guardando las propiedades
                i = 1;
                Iterator<Integer> itprop = list.iterator();
                while (itprop.hasNext()) {
                    Integer integer = itprop.next();
                    String thisprop = hmprops.get(integer);

                    String propuri = "";
                    String feuri = "";
                    String order = "";
                    String femode = "";
                    String useInCreate = "";
                    StringTokenizer stoken = new StringTokenizer(thisprop, "|");

                    if (stoken.hasMoreTokens()) {
                        propuri = stoken.nextToken();
                        feuri = stoken.nextToken();
                        order = stoken.nextToken();
                    }
                    if (stoken.hasMoreTokens()) {
                        femode = stoken.nextToken();
                    }
                    if (stoken.hasMoreTokens()) {
                        useInCreate = stoken.nextToken();
                    }

                    String propValue = propuri + "|" + feuri + "|" + i;

                    if (isDisplayProp) {
                        addListProperties(propValue);
                    } else if (isSearchProp) {
                        addSearchProperties(propValue);
                    } else if (isDetailProp) {
                        propValue = propValue + "|" + femode + "|" + useInCreate;
                        addDetailProperties(propValue);
                    }
                    i++;
                }

            }

            if (proptype != null) {

                response.setRenderParameter("proptype", proptype);
            }

        } else if ("updtItem".equals(response.getAction())) {

            String pid = request.getParameter("sval");
            String newFEuri = request.getParameter("feuri");

            if (pid != null) {

                StringTokenizer stoken = new StringTokenizer(pid, "|");

                if (null != stoken) {

                    String propuri = "";
                    String fele = "";
                    String propOrder = "";
                    String femode = "";
                    String useInCreate = "";

                    try {
                        if (stoken.hasMoreTokens()) {
                            propuri = stoken.nextToken();
                            fele = stoken.nextToken();
                            propOrder = stoken.nextToken();
                            femode = stoken.nextToken();
                            useInCreate = stoken.nextToken();

                            String propValue = propuri + "|" + newFEuri + "|" + propOrder;
                            propValue = propValue + "|" + femode + "|" + useInCreate;
                            removeDetailProperties(pid);
                            addDetailProperties(propValue);
                        }
                    } catch (Exception e) {
                        log.error("Error al cambiar el orden de las propiedades.", e);
                    }
                }
            }
        }

//        if (ract != null) {
//            response.setRenderParameter("act", ract);
//        }

        if (id != null) {

            response.setRenderParameter("suri", id);
        }

        log.debug("remove-closetab:" + sval);
        //response.setMode(response.Mode_EDIT);
    }

    public HashMap<String, SemanticProperty> getExistentes(HashMap<String, SemanticProperty> hmprops, String chkproptype) {
        HashMap<String, SemanticProperty> hmselected = new HashMap<String, SemanticProperty>();

        SemanticClass cls = getCatalogClass().transformToSemanticClass();
        if (chkproptype == null) {
            chkproptype = "display";
        }

        Iterator<String> itlprop = listListPropertieses();

        if (chkproptype.equals("display")) {
            itlprop = listListPropertieses();
        } else if (chkproptype.equals("search")) {
            itlprop = listSearchPropertieses();
        } else if (chkproptype.equals("detail")) {
            itlprop = listDetailPropertieses();
        }

        while (itlprop.hasNext()) {
            String string = itlprop.next();
            //System.out.println("listProp: " + string);
            String propuri = "";
            String feuri = "";
            String order = "";
            String femode = "";
            String useInCreate = "";
            StringTokenizer stoken = new StringTokenizer(string, "|");
            if (stoken.hasMoreTokens()) {
                propuri = stoken.nextToken();
                feuri = stoken.nextToken();
                order = stoken.nextToken();
            }
            if (stoken.hasMoreTokens()) {
                femode = stoken.nextToken();
            }
            if (stoken.hasMoreTokens()) {
                useInCreate = stoken.nextToken();
            }

            String key = cls.getName() + "|" + propuri;
            hmselected.put(key, hmprops.get(key));
        }
        return hmselected;
    }

    public String getDefaultPropFE(String prop) {
        String defaultFE = FE_DEFAULT;

        SemanticProperty sempro = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(prop);
        if (sempro != null) {
            SemanticObject dp = sempro.getDisplayProperty();

            if (dp != null) {
                //tiene displayproperty
                DisplayProperty disprop = new DisplayProperty(dp);

                //FormElement por defecto
                SemanticObject semobjFE = disprop.getFormElement();
                if (semobjFE != null) {
                    defaultFE = semobjFE.getURI();
                } else {
                    defaultFE = FE_DEFAULT;
                }
            }
        } else {
            defaultFE = FE_DEFAULT;
        }

        return defaultFE;
    }

    public String objectPropertyControl(HttpServletRequest request, SemanticProperty semanticProperty, String idform, String paramsearch, User user, SemanticClass sc, SemanticModel semmodel) {

        StringBuffer ret = new StringBuffer("");

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();

        HashMap<SemanticProperty, String> hmDetailFEMode = new HashMap<SemanticProperty, String>();
        //Agregando las propiedades seleccionadas para la edición
        Iterator<String> itdetProp = listDetailPropertieses();
        while (itdetProp.hasNext()) {
            String spropname = itdetProp.next();

            StringTokenizer stoken = new StringTokenizer(spropname, "|");
            if (stoken != null) {
                String suri = stoken.nextToken();
                String surife = stoken.nextToken();
                String suriorder = stoken.nextToken();
                String modo = stoken.nextToken();
                String propCreate = stoken.nextToken();

                SemanticProperty semProphm = ont.getSemanticProperty(suri);
                hmDetailFEMode.put(semProphm, surife + "|" + modo);
            }
        }

        String uriFE = hmDetailFEMode.get(semanticProperty);

        String bpropName = semanticProperty.getName();
        if (uriFE == null) {
            ret.append("\n<tr><td align=\"right\" width=\"200\">");
            ret.append("\n<label for=\"" + idform + "_" + bpropName + "\">" + semanticProperty.getDisplayName(user.getLanguage()) + ": </label>");
            ret.append("\n</td><td>");

            ret.append("\n<select name=\"search_" + bpropName + "\" id=\"" + idform + "_" + bpropName + "\" >");
            ret.append("\n<option value=\"\">Selecciona filtro</option>");
            Iterator<SemanticObject> sobj = semmodel.listInstancesOfClass(sc); //sc.listInstances();
            //sobj = sc.listInstances();

            while (sobj.hasNext()) {
                SemanticObject semanticObject = sobj.next();
                ret.append("\n<option value=\"" + semanticObject.getURI() + "\"  " + (paramsearch != null && paramsearch.equals(semanticObject.getURI()) ? "selected" : "") + " >"); // " + (paramsearch != null && paramsearch.equals(semanticObject.getURI()) ? "selected" : "
                ret.append(semanticObject.getDisplayName(user.getLanguage()));
                ret.append("\n</option>");
            }

            ret.append("\n<option value=\"\"></option>");
            ret.append("\n</select>");

            ret.append("\n</td><tr>");
        } else {

            StringTokenizer stoken = new StringTokenizer(uriFE, "|");
            String feURI = stoken.nextToken();
            String feMode = "filter"; //stoken.nextToken();


            SWBFormMgr fmgr = new SWBFormMgr(getCatalogClass().transformToSemanticClass(), getCatalogModel().getSemanticObject(), "filter");
            fmgr.clearProperties();
            fmgr.addProperty(semanticProperty);

            GenericObject sofe = ont.getGenericObject(feURI);

            FormElement fe = (FormElement) sofe;
            if (null != fe && semanticProperty != null) {
                try {
                    fe.setModel(getCatalogModel().getSemanticModel());
                    ((FormElementBase) fe).setFilterHTMLTags(fmgr.isFilterHTMLTags());

                    fmgr.renderProp(request, ret, semanticProperty, fe, feMode);
                } catch (Exception e) {
                    log.error("Error al procesat lapropiedad con el formelement seleccionado. ---- " + semanticProperty.getName() + " ----- " + feURI);
                }

            } else if (semanticProperty != null) {
                fe = fmgr.getFormElement(semanticProperty);
                System.out.println("fe: " + (null == fe ? "null" : fe.getId()));
                fmgr.renderProp(request, ret, semanticProperty, fe, feMode);

            }
        }
        return ret.toString();
    }
}
