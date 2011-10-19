package org.semanticwb.portal.resources.sem.catalog;

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
import org.semanticwb.model.Collection;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
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
        System.out.println("doView()");
        base = getResourceBase();
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        User user = paramsRequest.getUser();
        String id = null;//request.getParameter("suri");
        id = getCatalogClass().getId();


        System.out.println("Class ID:" + id);

        String page = request.getParameter("page");
        String collectiontype = "display";

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject semobj = getCatalogClass();

        if (hmFormEle == null) {
            System.out.println("Cargando HM - Form Element");
            hmFormEle = new HashMap<String, SemanticObject>();
            SemanticOntology sont = SWBPlatform.getSemanticMgr().getSchema();
            SemanticVocabulary sv = SWBPlatform.getSemanticMgr().getVocabulary();
            Iterator<SemanticObject> itfe = sv.getSemanticClass(sv.SWB_FORMELEMENT).listInstances(false);//sont.listInstancesOfClass(sv.getSemanticClass(sv.SWB_FORMELEMENT));
            while (itfe.hasNext()) {
                SemanticObject sofe = itfe.next();
                hmFormEle.put(sofe.getURI(), sofe);
                System.out.println("formElement: " + sofe.getId());
            }
        }
        String action = request.getParameter("act");
        if (action == null) {
            action = "";
        }

        if (collectiontype.equals("display")) {

            System.out.println("ID: " + id);
            String busqueda = request.getParameter("search");
            if (null == busqueda) {
                busqueda = "";
            }
            busqueda = busqueda.trim();
            HashMap<String, String> hmhasprop = new HashMap(); // uri property, uri form element asociado
            HashMap<String, SemanticProperty> hmhasproporder = new HashMap(); // indice filtro, PROPIEDAD
            HashMap<String, SemanticProperty> hmConfcol = new HashMap();
            HashMap<String, String> hmConfcolFE = new HashMap();
            HashMap<String, SemanticProperty> hmConfbus = new HashMap();
            HashMap<String, SemanticObject> hmfiltro = new HashMap();
            HashMap<String, SemanticObject> hmSearchParam = new HashMap();
            if (semobj != null) {

                SemanticClass sccol = getCatalogClass().transformToSemanticClass();

                System.out.println("Class: " + sccol.getName());

                SWBModel colmodel = getCatalogModel();
//                if (colmodel == null) {
//                    colmodel = col.getWebSite().getSubModel();
//                }

                SemanticModel semmodel = colmodel.getSemanticObject().getModel();

                SemanticProperty semProphm = null;
                Iterator<String> its = listListPropertieses();
                while (its.hasNext()) {
                    String spropname = its.next();
                    StringTokenizer stoken = new StringTokenizer(spropname, "|");
                    System.out.println("spropname: " + spropname);
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
                    System.out.println("spropname: " + spropname);
                    if (stoken != null) {
                        String suri = stoken.nextToken();
                        String surife = stoken.nextToken();
                        String suriorder = stoken.nextToken();
                        semProphm = ont.getSemanticProperty(suri);
                        System.out.println("spropnameSearch: " + suri);
                        if (semProphm != null) {
                            hmConfbus.put(suri, semProphm);
                        }
                        if (semProphm.isObjectProperty()) {
                            hmhasprop.put(suri, surife);
                            hmhasproporder.put(suriorder, semProphm);
                        }
                    }
                }

                if (hmConfcol.size() > 0) {

                    //Armado de tabla
                    out.println("<div >"); //class=\"swbform\"
                    out.println("<div id=\"filterprop/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Búsqueda y Filtrado de elementos\" class=\"admViewProperties\" open=\"false\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
                    SWBResourceURL urls = paramsRequest.getRenderUrl();
                    urls.setParameter("act", "stpBusqueda");
                    urls.setParameter("act2", "ssearch");
                    out.println("<form id=\"" + id + "/fsearch\" name=\"" + id + "/fsearch\" method=\"post\" action=\"" + urls + "\" >"); //onsubmit=\"submitForm('" + id + "/fsearch');return false;\"
                    out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
                    out.println("<fieldset>");
                    out.println("<table width=\"100%\" >");
                    out.println("<tr><td colspan=\"2\">&nbsp;</td></tr>");
                    out.println("<tr><td align=\"right\" width=\"200\">");
                    out.println("<label for=\"" + id + "_search\">" + paramsRequest.getLocaleString("searchInProperties") + ": </label>");
                    out.println("</td><td>");
                    out.println("<input type=\"text\" name=\"search\" id=\"" + id + "_search\" value=\"" + busqueda + "\">");
                    out.println("</td><tr>");

                    HashMap<String, SemanticObject> hmFilterSearch = new HashMap();

                    //
                    // REVISION DE OBJECT-PROPERTIES PARA OPCIONES DE FILTRADO
                    //

                    // TODO: Ordenarlo en de acuerdo con el orden de agregado en la configuración

                    ArrayList listfilters = new ArrayList(hmhasproporder.keySet());
                    Collections.sort(listfilters);

                    Iterator<String> itsprop = listfilters.iterator();
                    while (itsprop.hasNext()) {
                        //System.out.println("revisando object properties");
                        String urikey = itsprop.next();
                        SemanticProperty semanticProp = hmhasproporder.get(urikey);
                        SemanticClass sc = semanticProp.getRangeClass();
                        if (sc != null) {

                            out.println("<tr>");
                            String paramsearch = null;
                            if (request.getParameter("search_" + sc.getClassId()) != null && request.getParameter("search_" + sc.getClassId()).trim().length() > 0) {
                                paramsearch = request.getParameter("search_" + sc.getClassId());
                                hmSearchParam.put("search_" + sc.getClassId(), ont.getSemanticObject(paramsearch));
                            }

                            if (request.getParameter(semanticProp.getName()) != null && request.getParameter(semanticProp.getName()).trim().length() > 0) {
                                hmSearchParam.put("search_" + semanticProp.getName(), ont.getSemanticObject(request.getParameter(semanticProp.getName())));
                            }

                            out.println("<td align=\"right\">");
                            out.println("<label for=\"" + sc.getURI() + "_search\">" + sc.getDisplayName(user.getLanguage()) + ": </label></td><td><select name=\"search_" + sc.getClassId() + "\" id=\"" + sc.getURI() + "_search\" >");
                            out.println("<option value=\"\" selected >Selecciona filtro");
                            out.println("</option>");


                            Iterator<SemanticObject> sobj = semmodel.listInstancesOfClass(sc); //sc.listInstances();
                            sobj = sc.listInstances();

                            while (sobj.hasNext()) {
                                SemanticObject semanticObject = sobj.next();
                                out.println("<option value=\"" + semanticObject.getURI() + "\" " + (paramsearch != null && paramsearch.equals(semanticObject.getURI()) ? "selected" : "") + ">");
                                out.println(semanticObject.getDisplayName(user.getLanguage()));
                                out.println("</option>");
                                if (paramsearch != null && paramsearch.equals(semanticObject.getURI())) {
                                    hmFilterSearch.put(semanticObject.getURI(), semanticObject);
                                }
                            }
                            out.println("</select>");

                            SWBFormMgr fmgr = new SWBFormMgr(semobj, SWBFormMgr.MODE_EDIT, SWBFormMgr.MODE_EDIT);

                            //TODO: Hacer el render de la propiedad y el FormElement configurado
                            SemanticObject sofe = ont.getSemanticObject(hmhasprop.get(semanticProp.getURI()));

                            FormElement fe = null;

                            if (null != sofe) {

                                if (sofe.transformToSemanticClass().isSWBFormElement()) {
                                    //System.out.println("Antes del FERender");
                                    fe = fmgr.getFormElement(semanticProp);//FormElement) sofe;
                                    //out.println(fe.renderElement(request, gobj.getSemanticObject(), semProphm, SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_EDIT, user.getLanguage()));
                                    out.println(fe.renderElement(request, semobj, semanticProp, semanticProp.getName(), SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_EDIT, user.getLanguage()));
                                }
                            }
                            //if(null!=semanticProp) out.println(fmgr.renderElement(request, semanticProp, SWBFormMgr.MODE_EDIT));

                            out.println("</td>");
                            out.println("</tr>");
                        }
                    }
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
                    //this.inherited(arguments);
                    out.println("    </script>");
                    out.println("</div>");

                    out.println("<fieldset>");
                    out.println("<table width=\"100%\">");
                    out.println("<thead>");
                    out.println("<tr>");
                    out.println("<th>");
                    out.println("&nbsp;");
                    out.println("</th>");

                    Iterator<String> itcol = list.iterator(); //hmConfcol.keySet().iterator();
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
                    SemanticObject semO = null;
                    Iterator<SemanticObject> itso = semmodel.listInstancesOfClass(sccol); //gobj.getSemanticObject().getModel().listInstancesOfClass(sccol); //sccol.listInstances();

                    String urikey = null;
                    SemanticProperty semOProp = null;
                    if (!busqueda.equals("")) {
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
                            if (occ.indexOf(busqueda.toLowerCase()) > -1) {
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
                            itsprop2 = semmodel.listInstancesOfClass(sccol); //gobj.getSemanticObject().getModel().listInstancesOfClass(sccol);
                        }

                        if (!hmSearchParam.isEmpty()) {
                            while (itsprop2.hasNext()) {
                                //System.out.println("Filtrado por tipo de elementos");
                                SemanticObject sofil = itsprop2.next();

                                Iterator<SemanticProperty> sempropit = hmConfbus.values().iterator();
                                while (sempropit.hasNext()) {

                                    SemanticProperty semanticProp = sempropit.next();
                                    //System.out.println("Revisando propiedad: "+semanticProp.getName());
                                    if (semanticProp.isObjectProperty()) {
                                        //System.out.println("Object property....");
                                        SemanticClass semclass = semanticProp.getRangeClass();
                                        if (semclass != null) {
                                            //System.out.println("class id: "+semclass.getClassId());
                                            if (hmSearchParam.get("search_" + semclass.getClassId()) != null) {
                                                //System.out.println("Aplica Filtro encontrado...");
                                                SemanticObject so = hmSearchParam.get("search_" + semclass.getClassId());
                                                String URIfilter = so.getURI();

                                                SemanticObject sotmp = sofil.getObjectProperty(semanticProp);
                                                //System.out.println("comparando "+URIfilter+ " con "+(sotmp!=null?sotmp.getURI():"nulo"));
                                                if (sotmp != null && sotmp.getURI().equals(URIfilter)) {
                                                    //System.out.println("Conservando elemento.");
                                                    hmResults.put(sofil.getURI(), sofil);
                                                } else {
                                                    //System.out.println("Quitando elemento");
                                                    hmRemove.put(sofil.getURI(), sofil);

                                                }
                                            }
                                            if (hmSearchParam.get("search_" + semanticProp.getName()) != null) {
                                                //System.out.println("Aplica Filtro encontrado...");
                                                SemanticObject so = hmSearchParam.get("search_" + semanticProp.getName());
                                                String URIfilter = so.getURI();

                                                SemanticObject sotmp = sofil.getObjectProperty(semanticProp);
                                                //System.out.println("propName comparando "+URIfilter+ " con "+(sotmp!=null?sotmp.getURI():"nulo"));
                                                if (sotmp != null && sotmp.getURI().equals(URIfilter)) {
                                                    //System.out.println("Conservando elemento.");
                                                    hmResults.put(sofil.getURI(), sofil);
                                                } else {
                                                    //System.out.println("Quitando elemento");
                                                    hmRemove.put(sofil.getURI(), sofil);

                                                }
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
                                    //pasando elementos válidos
                                    hmfiltro.put(semObject.getURI(), semObject);
                                }
                            }
                        }

                        itso = hmfiltro.values().iterator();

                    } else {
                        itso = semmodel.listInstancesOfClass(sccol); //gobj.getSemanticObject().getModel().listInstancesOfClass(sccol); //sccol.listInstances();
                    }

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
                    int ps = 20;
                    int l = cplist.size();
                    int p = 0;
                    if (page != null) {
                        p = Integer.parseInt(page);
                    }
                    int x = 0;
                    //itso=setso.iterator();
                    /////////////////////////////////

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
                        //itcol = hmConfcol.keySet().iterator();
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
                    out.println("</tbody>");

                    //boton para agregar instancia
                    out.println("</table>");
                    out.println("</fieldset>");

                    if (p > 0 || x < l) //Requiere paginacion
                    {
                        out.println("<fieldset>");
                        out.println("<center>");
                        //int pages=(int)(l+ps/2)/ps;

                        int pages = (int) (l / ps);
                        if ((l % ps) > 0) {
                            pages++;
                        }

                        for (int z = 0; z < pages; z++) {
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
                        out.println("</center>");
                        out.println("</fieldset>");
                    }


                    SWBResourceURL url = paramsRequest.getRenderUrl();
                    url.setParameter("act", "new");
                    url.setParameter("suri", id);
                    url.setParameter("clsuri", sccol.getURI());
                    url.setMode(MODE_FORM);
//                        SWBResourceURL urlback = paramsRequest.getRenderUrl();
//                        urlback.setParameter("act", "config");
//                        urlback.setParameter("suri", id);
                    out.println("<fieldset>");
                    out.println("<button dojoType=\"dijit.form.Button\" onclick=\"window.location='" + url + "';\">" + paramsRequest.getLocaleString("Add_Instance") + " " + base.getTitle() + "</button>"); //
                    //out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlback + "',this.domNode); return false;\">" + paramsRequest.getLocaleString("btnConfig") + "</button>"); //
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

        System.out.print("doForm");

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
        //urlPA.setAction("new");

        SWBFormMgr fmgr = null;
        String modeform = null;
        if (act != null && act.equals("new")) {

            System.out.print("..new");

            fmgr = new SWBFormMgr(sclass, getCatalogModel().getSemanticObject().getModel().getModelObject(), SWBFormMgr.MODE_CREATE);

            HashMap<String, SemanticProperty> hmProps = new HashMap<String, SemanticProperty>();
            HashMap<String, String> hmDetail = new HashMap<String, String>();
            HashMap<String, SemanticProperty> hmDetailOrder = new HashMap<String, SemanticProperty>();

            //Agregando las propiedades requeridas para la creación del elemento
            Iterator<SemanticProperty> itsempro = fmgr.getProperties().iterator();
            while (itsempro.hasNext()) {
                SemanticProperty sPro = itsempro.next();
                if (sPro.isRequired()) {
                    System.out.println("..Agregando propiedad requerida..." + sPro.getName());
                    hmProps.put(sPro.getURI(), sPro);
                }
            }

            fmgr.clearProperties();

            //Agregando las propiedades seleccionadas para la creación
            Iterator<String> itdetProp = listDetailPropertieses();
            while (itdetProp.hasNext()) {
                String spropname = itdetProp.next();

                StringTokenizer stoken = new StringTokenizer(spropname, "|");
                //System.out.println("spropname: " + spropname);
                if (stoken != null) {
                    String suri = stoken.nextToken();
                    String surife = stoken.nextToken();
                    String suriorder = stoken.nextToken();
                    String modo = stoken.nextToken();
                    String propCreate = stoken.nextToken();
                    
                    System.out.print("propCreate: "+propCreate);
                    
                    SemanticProperty semProphm = ont.getSemanticProperty(suri);

                    System.out.print("..."+semProphm.getName());
                    
                    if (hmProps.get(suri) != null) {
                        System.out.println("..added");
                        hmDetail.put(suri, spropname);
                        hmProps.remove(suri);
                        hmDetailOrder.put(suriorder, semProphm);
                    } else if (propCreate != null && propCreate.trim().equals("true")) {
                        hmDetail.put(suri, spropname);
                        hmDetailOrder.put(suriorder, semProphm);
                        System.out.println("..added");
                    }
                }
            }
            ArrayList list = new ArrayList(hmDetailOrder.keySet());
            Collections.sort(list);

            Iterator<SemanticProperty> itprop = hmProps.values().iterator(); 
            while (itprop.hasNext()) {
                SemanticProperty semProp = itprop.next();
                fmgr.addProperty(semProp);
            }

            Iterator<String> itdis = list.iterator(); 
            while (itdis.hasNext()) {
                String key = itdis.next();
                fmgr.addProperty(hmDetailOrder.get(key));
            }
            urlPA.setAction("new");
        } else if (act != null && act.equals("edit")) {
            fmgr = new SWBFormMgr(obj, null, SWBFormMgr.MODE_EDIT);
            urlPA.setAction("updateform");
        }

        fmgr.setLang(user.getLanguage());
        fmgr.setAction(urlPA.toString());
        fmgr.setSubmitByAjax(false);
        fmgr.addButton(SWBFormButton.newSaveButton());
        SWBResourceURL urlback = paramRequest.getRenderUrl();
        urlback.setMode(SWBResourceURL.Mode_VIEW);
        urlback.setParameter("suri", id);
        if (null != cid) {
            urlback.setParameter("clsuri", cid);
        }
        if (null != act) {
            urlback.setParameter("act", act);
        }
        fmgr.addButton(SWBFormButton.newCancelButton().setAttribute("onclick", "window.location = '" + urlback + "';return false;"));
        fmgr.setType(SWBFormMgr.TYPE_DOJO);

        log.debug("new: suri: " + id);
        log.debug("new: clsuri: " + cid);
        log.debug("new: act: " + act);

        fmgr.addHiddenParameter("suri", id);
        fmgr.addHiddenParameter("clsuri", cid);
        fmgr.addHiddenParameter("ract", act);
        if (sval != null) {
            fmgr.addHiddenParameter("sval", sval);
            fmgr.addHiddenParameter("act", "update");
        } else {
            fmgr.addHiddenParameter("act", "new");
        }

        out.println(fmgr.renderForm(request));
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

    /**
     * Gets the fE select.
     * 
     * @param hmFE the hm fe
     * @param FEsel the f esel
     * @param user the user
     * @return the fE select
     */
    public String getFESelect(HashMap<String, SemanticObject> hmFE, String FEsel, User user) {
        StringBuilder ret = new StringBuilder();
        ret.append("<option value=\" \"></option>");
        Iterator<SemanticObject> itfe = hmFE.values().iterator();
        while (itfe.hasNext()) {
            SemanticObject sofe = itfe.next();
            ret.append("<option value=\"");
            ret.append(sofe.getURI());
            ret.append("\"");
            if (FEsel.equals(sofe.getURI())) {
                ret.append(" selected ");
            }
            ret.append(">");
            ret.append(sofe.getDisplayName(user.getLanguage()));
            ret.append("</option>");
        }
        return ret.toString();
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

        System.out.println("doAdmin()");
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
            //System.out.println("Cargando HM - Form Element");
            hmFormEle = new HashMap<String, SemanticObject>();
            SemanticOntology sont = SWBPlatform.getSemanticMgr().getSchema();
            SemanticVocabulary sv = SWBPlatform.getSemanticMgr().getVocabulary();
            Iterator<SemanticObject> itfe = sv.getSemanticClass(sv.SWB_FORMELEMENT).listInstances(false);//sont.listInstancesOfClass(sv.getSemanticClass(sv.SWB_FORMELEMENT));
            while (itfe.hasNext()) {
                SemanticObject sofe = itfe.next();
                hmFormEle.put(sofe.getURI(), sofe);
                //System.out.println("formElement: "+sofe.getId());
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


                out.println("<div class=\"swbform\">");
                out.println("<form type=\"dijit.form.Form\" id=\"" + id + "/collectionconfig\" name=\"" + id + "/collectionconfig\" action=\"" + urlconf + "\" method=\"post\" onsubmit=\"submitForm('" + id + "/collectionconfig'); return false;\"  >"); //
                out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
                out.println("<input type=\"hidden\" name=\"act\" value=\"\">");
                out.println("<input type=\"hidden\" id=\"" + id + "_actbutton\" name=\"actbutton\" value=\"\">");

                out.println("<fieldset>");
                out.println("<legend>" + "Configuración de colección" + " " + base.getDisplayTitle(user.getLanguage()) + "</legend>");
                out.println("<ul style=\"list-style:none;\">");
                out.println("<li>");
                out.println("<label for=\"" + id + "_collclass\">" + "Clase asociada" + "</label>");
                out.println("<select id=\"" + id + "_collclass\" name=\"collclass\">");
                //out.println("<input type=\"text\" name=\"classname\" value=\"" + col.getCollectionClass().getDisplayName(user.getLanguage()) + "\" readonly >");

                Iterator<SemanticObject> itsemcls = null; //col.getSemanticObject().getModel().listModelClasses();
                itsemcls = SWBPlatform.getSemanticMgr().getOntology().listInstancesOfClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(SWBPlatform.getSemanticMgr().getVocabulary().SWB_CLASS));
                while (itsemcls.hasNext()) {
                    SemanticObject semobj = itsemcls.next();
                    //System.out.println(semobj+" "+semobj.getURI()+" "+semobj.transformToSemanticClass());
                    SemanticClass semClass = semobj.transformToSemanticClass();
                    out.println("<option value=\"" + semClass.getURI() + "\" ");
                    if (sccol.getURI().equals(semClass.getURI())) {
                        out.println(" selected ");
                    }
                    out.println(">");
                    out.println(semClass.getDisplayName(user.getLanguage()));
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
                out.println("<input type=\"radio\" id=\"" + id + "_radiomodel1\" name=\"usemodel\" value=\"" + wb.getURI() + "\" " + (colmodel.getURI().equals(wb.getURI()) ? "checked" : "") + ">");
                out.println("<label for=\"" + id + "_radiomodel1\">" + "Sitio " + wb.getDisplayTitle(user.getLanguage()) + "</label>");
                out.println("</li>");
                out.println("<li>");
                UserRepository usrrep = wb.getUserRepository();
                out.println("<input type=\"radio\" id=\"" + id + "_radiomodel2\" name=\"usemodel\" value=\"" + usrrep.getURI() + "\" " + (colmodel.getURI().equals(usrrep.getURI()) ? "checked" : "") + " >");
                out.println("<label for=\"" + id + "_radiomodel2\">" + usrrep.getDisplayTitle(user.getLanguage()) + "</label>");
                out.println("</li>");
                out.println("<li>");
                Iterator<SWBModel> itmodel = wb.listSubModels();
                while (itmodel.hasNext()) {
                    SWBModel model = itmodel.next();
                    if (!wb.getURI().equals(model.getURI()) && !usrrep.getURI().equals(model.getURI())) {
                        out.println("<input type=\"radio\" id=\"" + id + "_radiomodel3\" name=\"usemodel\" value=\"" + model.getURI() + "\" " + (colmodel.getURI().equals(model.getURI()) ? "checked" : "") + " >");
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

                out.println("<div id=\"configcol/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Configuración despliegue\" class=\"admViewProperties\" open=\"true\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
                out.println("<fieldset>");
                out.println("<legend>Seleccionar propiedad a utilizar para el despliegue.</legend>");
                out.println("<ul style=\"list-style:none;\">");
                out.println("<li><label for=\"" + id + "_semprop\">");
                out.println("Propiedad:");
                out.println("</label>");
                out.println("<select id=\"" + id + "_semprop\" name=\"semprop\">");
                out.println("<option value=\"\">");
                out.println("");
                out.println("</option>");
                Iterator<SemanticProperty> itsemprop = sccol.listProperties();
                while (itsemprop.hasNext()) {
                    SemanticProperty semProp = itsemprop.next();
                    out.println("<option value=\"" + semProp.getURI() + "\">");
                    out.println(semProp.getDisplayName(user.getLanguage()));
                    out.println("</option>");
                }
                out.println("</select>");

                out.println("<button dojoType=\"dijit.form.Button\" _type=\"button\"  id=\"" + id + "_btnDisp\">" + paramsRequest.getLocaleString("btnAdd2display"));
                out.println("<script type=\"dojo/method\" event=\"onClick\" >");
                //out.println(" var miform = dojo.byId('"+ id + "/collectionconfig'); ");
                out.println(" var semprop = dojo.byId('" + id + "_semprop'); ");
                out.println(" if(semprop.value=='')");
                out.println(" {");
                out.println("   alert('Falta selecionar propiedad de la lista.');");
                out.println("   semprop.focus();");
                out.println("   return false;");
                out.println(" }");
                out.println(" var actbut = dojo.byId('" + id + "_actbutton'); ");
                out.println(" actbut.value='display'; ");
                out.println(" submitForm('" + id + "/collectionconfig'); ");
                out.println(" return false; ");
                out.println("</script>");
                out.println("</button>");
                out.println("</li>");
                out.println("</ul>");
                out.println("</fieldset>");


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

                ArrayList list = new ArrayList(hmlistprop.keySet());
                Collections.sort(list);


                int countprop = 0;
                itdis = list.iterator(); //col.listListPropertieses();
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


                out.println("<div id=\"configbus/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Configuración busqueda\" class=\"admViewProperties\" open=\"true\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
                out.println("<fieldset>");
                out.println("<legend>Seleccionar propiedad y el tipo de control a utilizar para la búsqueda y filtrado.</legend>");
                out.println("<ul style=\"list-style:none;\">");
                out.println("<li><label for=\"" + id + "_semprop2\">");
                out.println("Propiedad/FormElement:");
                out.println("</label>");
                out.println("<select id=\"" + id + "_semprop2\" name=\"semprop2\">");
                out.println("<option value=\"\">");
                out.println("");
                out.println("</option>");
                itsemprop = sccol.listProperties();
                while (itsemprop.hasNext()) {
                    SemanticProperty semProp = itsemprop.next();
                    out.println("<option value=\"" + semProp.getURI() + "\">");
                    out.println(semProp.getDisplayName(user.getLanguage()));
                    out.println("</option>");
                }
                out.println("</select>");
                out.println("<select id=\"" + id + "_sempropFE2\" name=\"sempropFE2\" >");
                out.println(getFESelect(hmFormEle, "", user));
                out.println("</select>");

                out.println("<button dojoType=\"dijit.form.Button\" _type=\"submit\"  id=\"" + id + "_btnSear\">" + paramsRequest.getLocaleString("btnAdd2search"));
                out.println("<script type=\"dojo/method\" event=\"onClick\" >");
                out.println(" var semprop2 = dojo.byId('" + id + "_semprop2'); ");
                out.println(" if(semprop2.value=='')");
                out.println(" {");
                out.println("   alert('Falta selecionar propiedad de la lista.');");
                out.println("   semprop2.focus();");
                out.println("   return false;");
                out.println(" }");
                out.println(" var sempropFE2 = dojo.byId('" + id + "_sempropFE2'); ");
                out.println(" if(sempropFE2.value=='')");
                out.println(" {");
                out.println("   alert('Falta seleccionar el control a utilizar de la lista.');");
                out.println("   sempropFE2.focus();");
                out.println("   return false;");
                out.println(" }");
                out.println(" var actbut = dojo.byId('" + id + "_actbutton'); ");
                out.println(" actbut.value='search';");
                out.println(" submitForm('" + id + "/collectionconfig'); ");
                out.println(" return false; ");
                out.println("</script>");
                out.println("</button>");
                out.println("</li>");
                out.println("</ul>");

                out.println("</fieldset>");

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
                    out.println("<td align=\"left\">");
                    out.println(ont.getSemanticProperty(sempropFE).getDisplayName(user.getLanguage()));
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

                out.println("<div id=\"configdet/" + id + "\" dojoType=\"dijit.TitlePane\" title=\"Configuración detalle\" class=\"admViewProperties\" open=\"true\" duration=\"150\" minSize_=\"20\" splitter_=\"true\" region=\"bottom\">");
                out.println("<fieldset>");
                out.println("<legend>Seleccionar propiedad y el tipo de control a utilizar para el detalle.</legend>");
                out.println("<ul style=\"list-style:none;\">");
                out.println("<li><label for=\"" + id + "_semprop3\">");
                out.println("Propiedad/FormElement:");
                out.println("</label>");
                out.println("<select id=\"" + id + "_semprop3\" name=\"semprop3\">");
                out.println("<option value=\"\">");
                out.println("");
                out.println("</option>");
                itsemprop = sccol.listProperties();
                while (itsemprop.hasNext()) {
                    SemanticProperty semProp = itsemprop.next();
                    out.println("<option value=\"" + semProp.getURI() + "\">");
                    out.println(semProp.getDisplayName(user.getLanguage()));
                    out.println("</option>");
                }
                out.println("</select>");
                out.println("<select id=\"" + id + "_sempropFE3\" name=\"sempropFE3\" >");
                out.println(getFESelect(hmFormEle, "", user));
                out.println("</select>");

                out.println("<button dojoType=\"dijit.form.Button\" _type=\"submit\"  id=\"" + id + "_btnDetail\">" + paramsRequest.getLocaleString("btnAdd2detail"));
                out.println("<script type=\"dojo/method\" event=\"onClick\" >");
                //out.println("   alert('onclick agregar.');");
                out.println(" var semprop3 = dojo.byId('" + id + "_semprop3'); ");
                //derbowi out.println("   alert('antes de if1.');");
                out.println(" if(semprop3.value=='')");
                out.println(" {");
                out.println("   alert('Falta selecionar propiedad de la lista.');");
                out.println("   semprop3.focus();");
                out.println("   return false;");
                out.println(" }");

                out.println(" var sempropFE3 = dojo.byId('" + id + "_sempropFE3'); ");
                //out.println("   alert('antes de if2.');");
                out.println(" if(sempropFE3.value=='')");
                out.println(" {");
                out.println("   alert('Falta seleccionar el control a utilizar de la lista.');");
                out.println("   sempropFE3.focus();");
                out.println("   return false;");
                out.println(" }");
                //out.println("   alert('antes del submit.');");
                out.println(" var actbut = dojo.byId('" + id + "_actbutton'); ");
                out.println(" actbut.value='detail';");
                out.println(" submitForm('" + id + "/collectionconfig'); ");
                out.println(" return false; ");
                out.println("</script>");
                out.println("</button>");
                out.println("</li>");
                out.println("</ul>");

                out.println("</fieldset>");

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
                    out.println(ont.getSemanticProperty(sempropFE).getDisplayName(user.getLanguage()));
                    out.println("</td>");
                    out.println("<td align=\"left\">");

                    SWBResourceURL urlmod = paramsRequest.getActionUrl();

                    urlmod.setParameter("sval", lprop);
                    urlmod.setParameter("prop", "mode");
                    urlmod.setAction("updPropMode");

                    out.println("<select  id=\"" + id + "/" + base.getId() + "/" + semprop + "/elementMode\"  name=\"elementMode\"  dojoType=\"dijit.form.FilteringSelect\"  style=\"width:180px;\" >"); //autocomplete=\"false\" hasDownArrow=\"true\"

                    out.println("<script type=\"dojo/connect\" event=\"onChange\">");
                    out.println(" var self1=this;   ");
                    out.println(" submitUrl('" + urlmod + "&'+self1.attr(\"name\")+'='+self1.attr(\"value\"),self1.domNode);");
                    out.println("</script>");
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

                out.println("</form >");
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
                out.println("<legend>" + "Configuración de colección" + " " + base.getDisplayTitle(user.getLanguage()) + "</legend>");
                out.println("<ul style=\"list-style:none;\">");
                out.println("<li>");
                out.println("<label for=\"" + id + "_collclass\">" + "Clase asociada" + "</label>");
                out.println("<select id=\"" + id + "_collclass\" name=\"collclass\">");

                Iterator<SemanticObject> itsemcls = null; //col.getSemanticObject().getModel().listModelClasses();
                itsemcls = SWBPlatform.getSemanticMgr().getOntology().listInstancesOfClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(SWBPlatform.getSemanticMgr().getVocabulary().SWB_CLASS));
                while (itsemcls.hasNext()) {
                    SemanticObject semobj = itsemcls.next();
                    SemanticClass semClass = semobj.transformToSemanticClass();
                    out.println("<option value=\"" + semClass.getURI() + "\" ");
                    //if(sccol!=null&&sccol.getURI().equals(semClass.getURI())) out.println(" selected ");
                    out.println(">");
                    out.println(semClass.getDisplayName(user.getLanguage()));
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

        System.out.println("ProcessAccion:" + action);

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        //GenericObject obj = getCatalogClass().getGenericInstance(); //ont.getGenericObject(id);

        if ("updconfig".equals(action)) {

            String actbutton = request.getParameter("actbutton");
            //System.out.println("actbutton: "+request.getParameter("actbutton"));
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
                    addListProperties(semprop + "|Text|" + max); //col.addListProperties(semprop+"|"+sempropFE+"|"+max);
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

                //System.out.println("collclass:"+collclass+", "+so);
                if (null != so) {
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

                if (clsuri != null) {
                    response.setRenderParameter("clsuri", clsuri);
                }

                response.setRenderParameter("nsuri", nso.getURI());

            } catch (FormValidateException e) {
                throw new SWBResourceException("Error to process form...", e);
            }

            //response.setRenderParameter("statmsg", response.getLocaleString("statmsg1"));
            response.setMode(response.Mode_VIEW);

        } else if ("updateform".equals(action)) {

            log.debug("ProcessAction(new) ");

            id = request.getParameter("suri");
            String clsuri = request.getParameter("clsuri");
            sval = request.getParameter("sval");

            SemanticObject so = ont.getSemanticObject(sval);
            //SemanticClass sclass = ont.getSemanticObject(clsuri).transformToSemanticClass();
            log.debug("ProcessAction(updateform): sobj: " + sval);

            SWBFormMgr fmgr = new SWBFormMgr(so, null, SWBFormMgr.MODE_EDIT);
            try {
                SemanticObject nso = fmgr.processForm(request);

                if (clsuri != null) {
                    response.setRenderParameter("clsuri", clsuri);
                }

                response.setRenderParameter("nsuri", nso.getURI());

            } catch (FormValidateException e) {
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

            //log.debug("remove-closetab:" + sval);
            //response.setRenderParameter("closetab", sval);

            //response.setMode(SWBActionResponse.Mode_EDIT);
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
            String proptype = request.getParameter("sval");
            String prop = request.getParameter("prop");
            String newMode = request.getParameter("elementMode");
            String newShow = request.getParameter("showItemCreate");
            String semprop = null;
            String sempropFE = null;
            String sorder = null;
            String modo = "edit";
            String itemShow = "true";

            try {
                StringTokenizer stoken = new StringTokenizer(proptype, "|");
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

            removeDetailProperties(sval);

            if ("mode".equals(prop)) {
                addDetailProperties(semprop + "|" + sempropFE + "|" + sorder + "|" + newMode + "|" + itemShow);
                response.setRenderParameter("statmsg", "Modo de la propiedad actualizada a " + newMode);
            }
            if ("show".equals(prop)) {
                addDetailProperties(semprop + "|" + sempropFE + "|" + sorder + "|" + modo + "|" + newShow);
                response.setRenderParameter("statmsg", "Propiedad para creacion actualizada.");
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

        }




        if (ract != null) {
            response.setRenderParameter("act", ract);
        }

        if (id != null) {

            response.setRenderParameter("suri", id);
        }

        log.debug("remove-closetab:" + sval);
        //response.setMode(response.Mode_EDIT);
    }
}
