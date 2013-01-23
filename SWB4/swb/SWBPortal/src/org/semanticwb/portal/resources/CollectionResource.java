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
package org.semanticwb.portal.resources;

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
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Collection;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.PropertyGroup;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.Traceable;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author juan.fernandez
 */
public class CollectionResource extends GenericResource {

    /** The log. */
    private Logger log = SWBUtils.getLogger(CollectionResource.class);
    /** The MOD e_ form. */
    private String MODE_FORM = "FORM";
    /** The hm form ele. */
    private HashMap<String, SemanticObject> hmFormEle = null;
    private Resource base = null;
    private static String COLL_ID = "collid";

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        base = getResourceBase();
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");

        String coluri = base.getAttribute(COLL_ID, "");

        SWBResourceURL urlact = paramRequest.getActionUrl();
        urlact.setAction("updcfg");
        out.println("<div class=\"swbform\">");
        out.println("<form id=\"" + id + "/collectionAdminres\" action=\""+urlact+"\" method=\"post\" onsubmit=\"submitForm('" + id + "/collectionAdminres'); return false;\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\""+id+"\">");
        out.println("<fieldset>");
        out.println("<legend>");
        out.println(paramRequest.getLocaleString("msgResourceConfig"));
        out.println("</legend>");
        out.println("<ul style=\"list-style:none;\">");

        Iterator<Collection> itcol = paramRequest.getWebPage().getWebSite().listCollections();
        while (itcol.hasNext()) {
            Collection collection = itcol.next();
            out.println("<li>");
            out.println("<input dojoType=\"dijit.form.RadioButton\"  id=\"" + collection.getId() + "/colluri\" name=\"" + COLL_ID + "\" value=\"" + collection.getURI() + "\" " + (collection.getURI().equals(coluri) ? "checked=\"checked\"" : "") + "><label for=\"" + collection.getId() + "/colluri\">" + collection.getTitle() + "</label>");
            out.println("</li>");
        }

        out.println("</ul>");
        out.println("</fieldset>");

        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btn_send\">" + paramRequest.getLocaleString("btnSave") + "</button>"); //
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String id = request.getParameter("suri");
        String sval = request.getParameter("sval");
        //String ract = request.getParameter("ract");
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

        //System.out.println("Accion:"+action);

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject obj = ont.getGenericObject(id);
        if (action.equals("updcfg")) {

            try {
                String bhtype = request.getParameter(COLL_ID);
                //System.out.println("Config resource:"+bhtype);
                if (bhtype != null) {
                    base.setAttribute(COLL_ID, bhtype);
                    base.updateAttributesToDB();
                }
            } catch (Exception e) {
                log.error(e);
            }
            response.setMode(SWBActionResponse.Mode_ADMIN);
        }
        else if ("new".equals(action)) {

            log.debug("ProcessAction(new) ");

            id = request.getParameter("suri");
            String clsuri = request.getParameter("clsuri");

            SemanticObject so = ont.getSemanticObject(id);
            SemanticClass sclass = ont.getSemanticObject(clsuri).transformToSemanticClass();
            log.debug("ProcessAction(new): sobj: " + id);

            SWBFormMgr fmgr = new SWBFormMgr(sclass, so.getModel().getModelObject(), SWBFormMgr.MODE_CREATE);
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
        }
        else if ("updateform".equals(action)) {

            log.debug("ProcessAction(new) ");

            id = request.getParameter("suri");
            String clsuri = request.getParameter("clsuri");
            sval =  request.getParameter("sval");

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
        } else if ("remove".equals(action))
        {
            log.debug("processAction(remove)");
            //System.out.println("sval:"+sval);
            if (obj instanceof Collection) {
                Collection col = (Collection) obj;

                String prop = request.getParameter("prop");
                //System.out.println("Eliminando ... "+prop);

                if(prop!=null&&prop.equals("display"))
                {
                    //System.out.println("quitando display");
                    col.removeListProperties(sval);

                } else if(prop!=null&&prop.equals("search"))
                {
                    //System.out.println("quitando search");
                    col.removeSearchProperties(sval);
                }

            }
        }

        if(id!=null) response.setRenderParameter("suri", id);
        if(sval!=null) response.setRenderParameter("sval", sval);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

        base = getResourceBase();
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        User user = paramsRequest.getUser();
        String id = null;//request.getParameter("suri");
        id = base.getAttribute(COLL_ID);


        //System.out.println("Collection ID:"+id);

        String page = request.getParameter("page");
        String collectiontype = "display"; 

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

        Collection col = null;
        if (collectiontype.equals("display")) { //("stpBusqueda".equals(action) || "".equals(action)) {

            //System.out.println("ID: "+id);
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
            if (gobj instanceof Collection) {
                col = (Collection) gobj;

                SemanticClass sccol = col.getCollectionClass().transformToSemanticClass();

                //System.out.println("Coleccion: "+col.getTitle());

                SWBModel colmodel = col.getCollectionModel();
                if (colmodel == null) {
                    colmodel = col.getWebSite().getSubModel();
                }

                SemanticModel semmodel = colmodel.getSemanticObject().getModel();

                SemanticProperty semProphm = null;
                Iterator<String> its = col.listListPropertieses();
                while (its.hasNext()) {
                    String spropname = its.next();
                    StringTokenizer stoken = new StringTokenizer(spropname, "|");
                    //System.out.println("spropname: "+spropname);
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
                its = col.listSearchPropertieses();
                while (its.hasNext()) {
                    String spropname = its.next();
                    StringTokenizer stoken = new StringTokenizer(spropname, "|");
                    //System.out.println("spropname: "+spropname);
                    if (stoken != null) {
                        String suri = stoken.nextToken();
                        String surife = stoken.nextToken();
                        String suriorder = stoken.nextToken();
                        semProphm = ont.getSemanticProperty(suri);
                        //System.out.println("spropnameSearch: "+suri);
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

                            SWBFormMgr fmgr = new SWBFormMgr(gobj.getSemanticObject(), SWBFormMgr.MODE_EDIT, SWBFormMgr.MODE_EDIT);

                            //TODO: Hacer el render de la propiedad y el FormElement configurado
                            SemanticObject sofe = ont.getSemanticObject(hmhasprop.get(semanticProp.getURI()));

                            FormElement fe = null;

                            if (null != sofe) {

                                if (sofe.transformToSemanticClass().isSWBFormElement()) {
                                    //System.out.println("Antes del FERender");
                                    fe = fmgr.getFormElement(semanticProp);//FormElement) sofe;
                                    //out.println(fe.renderElement(request, gobj.getSemanticObject(), semProphm, SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_EDIT, user.getLanguage()));
                                    out.println(fe.renderElement(request, gobj.getSemanticObject(), semanticProp, semanticProp.getName(), SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_EDIT, user.getLanguage()));
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
                        urlr.setAction("remove");
                        out.println("<a href=\"#\" title=\"" + paramsRequest.getLocaleString("remove") + "\" onclick=\"if(confirm('" + paramsRequest.getLocaleString("confirm_remove") + " " + SWBUtils.TEXT.scape4Script(semO.getDisplayName(user.getLanguage())) + "?')){ window.location='" + urlr + "'; } else { return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("remove") + "\"></a>");
                        SWBResourceURL urlform = paramsRequest.getRenderUrl();
                        urlform.setMode(MODE_FORM);
                        urlform.setParameter("suri", id);
                        urlform.setParameter("clsuri", col.getCollectionClass().transformToSemanticClass().getURI());
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
                    url.setParameter("act", "stpBusqueda");
                    url.setParameter("suri", id);
                    url.setParameter("clsuri", sccol.getURI());
                    url.setMode(MODE_FORM);
//                        SWBResourceURL urlback = paramsRequest.getRenderUrl();
//                        urlback.setParameter("act", "config");
//                        urlback.setParameter("suri", id);
                    out.println("<fieldset>");
                    out.println("<button dojoType=\"dijit.form.Button\" onclick=\"window.location='" + url + "';\">" + paramsRequest.getLocaleString("Add_Instance") +" "+ col.getTitle() + "</button>"); //
                    //out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlback + "',this.domNode); return false;\">" + paramsRequest.getLocaleString("btnConfig") + "</button>"); //
                    out.println("</fieldset>");
                    out.println("</div>");
                } else {
                    out.println("<div class=\"swbform\">");
                    out.println("<fieldset>");
                    out.println("<legend>" + paramsRequest.getLocaleString("errorMsgConfig") + " \"" + col.getDisplayTitle(user.getLanguage()) + "\" </legend>");
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

        //System.out.println("doForm...");

        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");
        String cid = request.getParameter("clsuri");
        String act = request.getParameter("act");
        String sval = request.getParameter("sval");

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(sval);

        GenericObject go = ont.getGenericObject(sval);
        Collection col = null;
        if(go instanceof Collection)
            col = (Collection)go;

        SemanticClass sclass = null;
        if(col!=null) sclass = col.getCollectionClass().transformToSemanticClass();
        else sclass = ont.getSemanticObject(cid).transformToSemanticClass();

        SWBResourceURL urlPA = paramRequest.getActionUrl();
        

        String modeform = null;
        if(act!=null&&act.equals("new"))
        {
            modeform = SWBFormMgr.MODE_CREATE;
            urlPA.setAction("new");
        }
        else if(act!=null&&act.equals("edit"))
        {
            modeform = SWBFormMgr.MODE_EDIT;
            urlPA.setAction("updateform");
        }

        //System.out.println("Modo: "+modeform);

        SWBFormMgr fmgr = new SWBFormMgr(obj,null,modeform);

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
        fmgr.addButton(SWBFormButton.newCancelButton().setAttribute("onclick", "window.location='" + urlback + "';"));
        fmgr.setType(SWBFormMgr.TYPE_DOJO);

        log.debug("new: suri: " + id);
        log.debug("new: clsuri: " + cid);
        log.debug("new: act: " + act);

        fmgr.addHiddenParameter("suri", id);
        fmgr.addHiddenParameter("clsuri", cid);
        fmgr.addHiddenParameter("ract", act);
        fmgr.addHiddenParameter("act", act!=null&&act.equals("new")?"new":"updateform");
        fmgr.addHiddenParameter("sval", sval);

        

        out.println(fmgr.renderForm(request));

//        Iterator<SemanticProperty> it = fmgr.getProperties().iterator();
//        while (it.hasNext()) {
//            SemanticProperty semProp = it.next();
//            if(semProp.isDataTypeProperty())
//            {
//                out.println(fmgr.getFormElement(semProp).renderElement(request, obj, semProp, act, modeform, null));
//                out.println("<br/>");
//                out.println(fmgr.renderLabel(request, semProp, modeform)+": "+fmgr.renderElement(request, semProp.getName()));
//                out.println("<br/>");
//            }
//            else if(semProp.isObjectProperty())
//            {
//               //
//
//            }
//
//        }

//        HashMap<PropertyGroup,TreeSet> hmpg = fmgr.getGroups();
//        out.println("<div>");
//        Iterator<PropertyGroup> itpg = hmpg.keySet().iterator();
//        while (itpg.hasNext()) {
//            PropertyGroup pGroup = itpg.next();
//            //out.println("groupid:"+pGroup.getId());
//            //out.println("<br/>");
//            //out.println("indice"+pGroup.getIndex());
//            //out.println("<br/>");
//
//            //out.println("label:"+pGroup.getSemanticObject().getDisplayName(user.getLanguage()));
//            //out.println("<br/>");
//            out.println("<fieldset>");
//            out.println("<legend>");
//            out.println(pGroup.getSemanticObject().getDisplayName(user.getLanguage()));
//            out.println("</legend>");
//            out.println("<table>");
//            Iterator<SemanticProperty> itsp = hmpg.get(pGroup).iterator();
//            while (itsp.hasNext()) {
//                SemanticProperty semP = itsp.next();
//
//                out.println("<tr>");
//                if(semP.isDataTypeProperty())
//                {
//                    out.println("<td>" + semP.getLabel() + ":</td><td>" + fmgr.renderElement(request, semP.getName(), modeform) + "</td>");
//                }
//                else if(semP.isObjectProperty())
//                {
//                    out.println("<td>" + semP.getLabel() + ":</td>");
//                    SemanticClass sc = semP.getRangeClass();
//
//                    Iterator<SemanticProperty> so = sc.listProperties();
//                    while (so.hasNext()) {
//                        SemanticProperty sp = so.next();
//                        if(sp.getName().equals("Estatus")||sp.getName().equals("comentario"))
//                        {
//                            out.println(fmgr.renderElement(request, sp.getName(), modeform));
//                        }
//                    }
//
//                    out.println("<td>" + fmgr.renderElement(request, semP.getName(), modeform) + "</td>");
//                }
//                out.println("</tr>");
//
//            }
//            out.println("</table>");
//            out.println("</fieldset>");
//        }
//        out.println("</div>");
        


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
}
