/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Collection;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author juan.fernandez
 */
public class SWBACollectionConfig extends GenericAdmResource {

    private Logger log = SWBUtils.getLogger(SWBACollectionConfig.class);
    private String MODE_FORM = "FORM";

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
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        log.debug("doView(SWBACollectionConfig...)");
        doEdit(request, response, paramRequest);
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        User user = paramsRequest.getUser();
        String id = request.getParameter("suri");
        String page = request.getParameter("page");

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        GenericObject gobj = ont.getGenericObject(id);

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

        Collection col = null;
        if (action.equals("config")) {
            if (gobj instanceof Collection) {
                col = (Collection) gobj;

                if (col.getCollectionClass() != null) {
                    SemanticClass sccol = col.getCollectionClass().transformToSemanticClass();
                    
                    HashMap<String, String> hmcol = new HashMap();
                    HashMap<String, String> hmbus = new HashMap();

                    Iterator<String> its = col.listListPropertieses();
                    while (its.hasNext()) {
                        String spropname = its.next();
                        hmcol.put(spropname, spropname);
                    }
                    its = col.listSearchPropertieses();
                    while (its.hasNext()) {
                        String spropname = its.next();
                        hmbus.put(spropname, spropname);
                    }


                    SWBResourceURL urlconf = paramsRequest.getActionUrl();
                    urlconf.setAction("updconfig");

                    out.println("<div class=\"swbform\">");
                    out.println("<form id=\"" + id + "/collectionconfig\" name=\"" + id + "/collectionconfig\" action=\"" + urlconf + "\" method=\"post\" onsubmit=\"submitForm('" + id + "/collectionconfig'); return false;\"  >"); //
                    out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
                    out.println("<input type=\"hidden\" name=\"act\" value=\"\">");

                    out.println("<fieldset>");
                    out.println("<legend>" + "Configuración de colección" + " " + col.getDisplayTitle(user.getLanguage()) + "</legend>");
                    out.println("<ul style=\"list-style:none;\">");
                    out.println("<li>");
                    out.println("<label for=\"" + id + "_classname\">" + "Clase asociada" + "</label>");
                    out.println("<input type=\"text\" name=\"classname\" value=\"" + col.getCollectionClass().getDisplayName(user.getLanguage()) + "\" readonly >");

                    SWBResourceURL urln = paramsRequest.getRenderUrl();
                    urln.setParameter("act", "");
                    urln.setParameter("suri", id);
                    urln.setParameter("clsuri", sccol.getURI());
                    urln.setMode(MODE_FORM);

                    out.println("<button dojoType=\"dijit.form.Button\" type=\"button\" onclick=\"submitUrl('" + urln + "',this.domNode); return false;\" >" + paramsRequest.getLocaleString("Add_Instance") + "</button>");

                    out.println("</li>");
                    out.println("</ul>");
                    out.println("</fieldset>");

                    out.println("<fieldset>");
                    out.println("<legend>Configuración despliegue</legend>");
                    out.println("<ul style=\"list-style:none;\">");
                    Iterator<SemanticProperty> itsemprop = sccol.listProperties();
                    while (itsemprop.hasNext()) {
                        SemanticProperty semProp = itsemprop.next();
                        if (!semProp.getDisplayName(user.getLanguage()).endsWith("Ref") && !semProp.getDisplayName(user.getLanguage()).startsWith("has")) {
                            out.println("<li>");
                            out.println("<input id=\"" + semProp.getURI() + "_semprop\" type=\"checkbox\" name=\"semprop\" value=\"" + semProp.getName() + "\" " + (hmcol.get(semProp.getName())!= null ? "checked" : "") + ">");
                            out.println("<label for=\"" + semProp.getURI() + "_semprop\">" + semProp.getDisplayName(user.getLanguage()) + "</label>");
                            out.println("</li>");
                        }
                    }
                    out.println("</ul>");
                    out.println("</fieldset>");

                    out.println("<fieldset>");
                    out.println("<legend>Configuración busqueda</legend>");
                    out.println("<ul style=\"list-style:none;\">");
                    itsemprop = sccol.listProperties();
                    while (itsemprop.hasNext()) {
                        SemanticProperty semProp = itsemprop.next();
                        if (!semProp.getDisplayName(user.getLanguage()).endsWith("Ref") && !semProp.getDisplayName(user.getLanguage()).startsWith("has")) {
                            out.println("<li>");
                            out.println("<input id=\"" + semProp.getURI() + "_sempropSearch\" type=\"checkbox\" name=\"sempropSearch\" value=\"" + semProp.getName() + "\" " + (hmbus.get(semProp.getName()) != null ? "checked" : "") + ">");
                            out.println("<label for=\"" + semProp.getURI() + "_sempropSearch\">" + semProp.getDisplayName(user.getLanguage()) + "</label>");
                            out.println("</li>");
                        }
                    }
                    out.println("</ul>");
                    out.println("</fieldset>");

                    SWBResourceURL url = paramsRequest.getRenderUrl();
                    url.setParameter("act", "stpBusqueda");
                    url.setParameter("suri", id);
                    out.println("<fieldset>");
                    out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\"  >" + paramsRequest.getLocaleString("Save_config") + "</button>");
                    out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + url + "',this.domNode); return false;\">" + paramsRequest.getLocaleString("frmBusqueda") + "</button>"); //
                    out.println("</fieldset>");
                    out.println("</form >");
                    out.println("</div >");
                } else {
                    out.println("<div class=\"swbform\">");
                    out.println("<fieldset>");
                    out.println("<legend>" + paramsRequest.getLocaleString("erroMsgConfig") + " \"" + col.getDisplayTitle(user.getLanguage()) + "\" </legend>");
                    out.println("<ul style=\"list-style:none;\">");
                    out.println("<li>");
                    out.println(paramsRequest.getLocaleString("msgMissingConfigClass"));
                    out.println("</li>");
                    out.println("<li>");
                    out.println(paramsRequest.getLocaleString("msgConfigInstructions"));
                    out.println("</li>");
                    out.println("</ul>");
                    out.println("</fieldset>");
                    SWBResourceURL urlreload = paramsRequest.getRenderUrl();
                    urlreload.setParameter("act", "");
                    urlreload.setParameter("suri", id);
                    out.println("<fieldset>");
                    out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlreload + "',this.domNode); return false;\">" + paramsRequest.getLocaleString("btnReload") + "</button>"); //
                    out.println("</fieldset>");
                    out.println("</div>");
                }
            }

        } else if ("stpBusqueda".equals(action) || "".equals(action)) {

            String busqueda = request.getParameter("search");
            if (null == busqueda) {
                busqueda = "";
            }
            busqueda = busqueda.trim();
            HashMap<String, SemanticProperty> hmcol = new HashMap();
            HashMap<String, SemanticProperty> hmbus = new HashMap();
            HashMap<String, String> hmConfcol = new HashMap();
            HashMap<String, String> hmConfbus = new HashMap();
            HashMap<String, SemanticObject> hmfiltro = new HashMap();
            if (gobj instanceof Collection) {
                col = (Collection) gobj;
                //WebSite ws = SWBContext.getWebSite(gobj.getSemanticObject().getModel().getModelObject().getURI());
                if (col.getCollectionClass() == null) {

                    out.println("<div class=\"swbform\">");
                    out.println("<fieldset>");
                    out.println("<legend>" + paramsRequest.getLocaleString("erroMsgConfig") + " \"" + col.getDisplayTitle(user.getLanguage()) + "\" </legend>");
                    out.println("<ul style=\"list-style:none;\">");
                    out.println("<li>");
                    out.println(paramsRequest.getLocaleString("msgMissingConfigClass"));
                    out.println("</li>");
                    out.println("<li>");
                    out.println(paramsRequest.getLocaleString("msgConfigInstructions"));
                    out.println("</li>");
                    out.println("</ul>");
                    out.println("</fieldset>");
                    SWBResourceURL urlreload = paramsRequest.getRenderUrl();
                    urlreload.setParameter("act", "");
                    urlreload.setParameter("suri", id);
                    out.println("<fieldset>");
                    out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlreload + "',this.domNode); return false;\">" + paramsRequest.getLocaleString("btnReload") + "</button>"); //
                    out.println("</fieldset>");
                    out.println("</div>");
                } else {
                    SemanticClass sccol = col.getCollectionClass().transformToSemanticClass();

                    Iterator<String> its = col.listListPropertieses();
                    while (its.hasNext()) {
                        String spropname = its.next();
                        //System.out.println("spropname: "+spropname);
                        hmConfcol.put(spropname, spropname);
                    }
                    its = col.listSearchPropertieses();
                    while (its.hasNext()) {
                        String spropname = its.next();
                        //System.out.println("spropnameSearch: "+spropname);
                        hmConfbus.put(spropname, spropname);
                    }

                    Iterator<SemanticProperty> itsemprop = sccol.listProperties();
                    while (itsemprop.hasNext()) {
                        SemanticProperty semProp = itsemprop.next();
                        if (!semProp.getDisplayName(user.getLanguage()).endsWith("Ref") && !semProp.getDisplayName(user.getLanguage()).startsWith("has")) {
                            if (hmConfcol.get(semProp.getName()) != null) {
                                hmcol.put(semProp.getName(), semProp);
                            }
                            if (hmConfbus.get(semProp.getName()) != null) {
                                hmbus.put(semProp.getName(), semProp);
                            }
                        }
                    }

                    if (hmcol.size() > 0) {

                        //Armado de tabla
                        out.println("<div class=\"swbform\">");
                        out.println("<fieldset>");
                        SWBResourceURL urls = paramsRequest.getRenderUrl();
                        urls.setParameter("act", "stpBusqueda");
                        urls.setParameter("act2", "ssearch");
                        out.println("<form id=\"" + id + "/fsearch\" name=\"" + id + "/fsearch\" method=\"post\" action=\"" + urls + "\" onsubmit=\"submitForm('" + id + "/fsearch');return false;\">");
                        out.println("<div align=\"right\">");
                        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + id + "\">");
                        out.println("<label for=\"" + id + "_search\">" + paramsRequest.getLocaleString("searchInProperties") + ": </label><input type=\"text\" name=\"search\" id=\"" + id + "_search\" value=\"" + busqueda + "\">");
                        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramsRequest.getLocaleString("btnSearch") + "</button>"); //
                        out.println("</div>");
                        out.println("</form>");
                        out.println("</fieldset>");
                        out.println("<fieldset>");
                        out.println("<table width=\"100%\">");
                        out.println("<thead>");
                        out.println("<tr>");
                        out.println("<th>");
                        out.println("&nbsp;");
                        out.println("</th>");

                        Iterator<String> itcol = hmcol.keySet().iterator();
                        while (itcol.hasNext()) {
                            String scol = itcol.next();
                            out.println("<th>");
                            out.println(scol);
                            out.println("</th>");
                        }
                        out.println("</tr>");
                        out.println("</thead>");
                        out.println("<tbody>");
                        SemanticObject semO = null;
                        Iterator<SemanticObject> itso = gobj.getSemanticObject().getModel().listInstancesOfClass(sccol); //sccol.listInstances();
                        if (!busqueda.equals("")) {
                            while (itso.hasNext()) {
                                semO = itso.next();
                                itcol = hmcol.keySet().iterator();
                                String occ = "";
                                while (itcol.hasNext()) {
                                    String sprop = itcol.next();
                                    if (hmbus.get(sprop) != null) {
                                        occ = occ + reviewSemProp(hmbus.get(sprop), semO, paramsRequest);
                                    }
                                }
                                occ = occ.toLowerCase();
                                if (occ.indexOf(busqueda.toLowerCase()) > -1) {
                                    hmfiltro.put(semO.getURI(), semO);
                                }
                            }
                        }

                        //Llenado de la tabla

                        if (!busqueda.equals("")) {
                            itso = hmfiltro.values().iterator();
                        } else {
                            itso = gobj.getSemanticObject().getModel().listInstancesOfClass(sccol); //sccol.listInstances();
                        }

                        //PAGINACION
                        Set<SemanticObject> setso = SWBComparator.sortByCreatedSet(itso,false);
                        int ps=20;
                        int l=setso.size();
                        int p=0;
                        if(page!=null)p=Integer.parseInt(page);
                        int x=0;
                        itso=setso.iterator();
                        /////////////////////////////////

                        while (itso.hasNext()) {

                            semO = itso.next();

                            //PAGINACION ////////////////////
                            if(x<p*ps)
                            {
                                x++;
                                continue;
                            }
                            if(x==(p*ps+ps) || x==l)break;
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
                            out.println("<a href=\"#\" title=\"" + paramsRequest.getLocaleString("remove") + "\" onclick=\"if(confirm('" + paramsRequest.getLocaleString("confirm_remove") + " " + SWBUtils.TEXT.scape4Script(semO.getDisplayName(user.getLanguage())) + "?')){ submitUrl('" + urlr + "',this); } else { return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("remove") + "\"></a>");
                            out.println("<a href=\"#\"  title=\"" + paramsRequest.getLocaleString("documentAdmin") + "\" onclick=\"selectTab('" + semO.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + SWBUtils.TEXT.scape4Script(semO.getDisplayName()) + "','bh_AdminPorltet');return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"" + paramsRequest.getLocaleString("documentAdmin") + "\"></a>");
                            out.println("</td>");
                            itcol = hmcol.keySet().iterator();
                            while (itcol.hasNext()) {
                                String scol = itcol.next();
                                if (hmcol.get(scol) != null) {
                                    out.println("<td>");
                                    out.println(reviewSemProp(hmcol.get(scol), semO, paramsRequest));
                                    out.println("</td>");
                                }
                            }
                            out.println("</tr>");

                        }
                        out.println("</tbody>");

                        //boton para agregar instancia
                        out.println("</table>");
                        out.println("</fieldset>");

                        if(p>0 || x<l) //Requiere paginacion
                        {
                            out.println("<fieldset>");
                            out.println("<center>");
                            int pages=(int)(l+ps/2)/ps;
                            for(int z=0;z<pages;z++)
                            {
                                SWBResourceURL urlNew = paramsRequest.getRenderUrl();
                                urlNew.setParameter("suri", id);
                                urlNew.setParameter("page", ""+z);
                                urlNew.setParameter("act", "stpBusqueda");
                                urlNew.setParameter("search",busqueda);
                                urlNew.setParameter("clsuri", sccol.getURI());
                                
                                if(z!=p)
                                {
                                    out.println("<a href=\"#\" onclick=\"submitUrl('" + urlNew + "',this); return false;\">"+(z+1)+"</a> ");
                                }else
                                {
                                    out.println((z+1)+" ");
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
                        SWBResourceURL urlback = paramsRequest.getRenderUrl();
                        urlback.setParameter("act", "config");
                        urlback.setParameter("suri", id);
                        out.println("<fieldset>");
                        out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + url + "',this.domNode); return false;\">" + paramsRequest.getLocaleString("Add_Instance") + "</button>"); //
                        out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlback + "',this.domNode); return false;\">" + paramsRequest.getLocaleString("btnConfig") + "</button>"); //
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
    }

    public void doForm(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String id = request.getParameter("suri");
        String cid = request.getParameter("clsuri");
        String act = request.getParameter("act");

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(id);
        SemanticClass sclass = ont.getSemanticObject(cid).transformToSemanticClass();

        SWBResourceURL urlPA = paramRequest.getActionUrl();
        urlPA.setAction("new");

        SWBFormMgr fmgr = new SWBFormMgr(sclass, obj.getModel().getModelObject(), SWBFormMgr.MODE_CREATE);
        fmgr.setLang(user.getLanguage());
        fmgr.setAction(urlPA.toString());
        fmgr.setSubmitByAjax(true);
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
        fmgr.addButton(SWBFormButton.newCancelButton().setAttribute("onclick", "submitUrl('" + urlback + "',this.domNode);return false;"));
        fmgr.setType(SWBFormMgr.TYPE_DOJO);

        log.debug("new: suri: " + id);
        log.debug("new: clsuri: " + cid);
        log.debug("new: act: " + act);

        fmgr.addHiddenParameter("suri", id);
        fmgr.addHiddenParameter("clsuri", cid);
        fmgr.addHiddenParameter("ract", act);
        fmgr.addHiddenParameter("act", "new");
        out.println(fmgr.renderForm(request));
    }

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
        return ret;
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {

        String id = request.getParameter("suri");
        String sval = request.getParameter("sval");
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
        GenericObject obj = ont.getGenericObject(id);

        if ("updconfig".equals(action)) {
            if (obj instanceof Collection) {
                Collection col = (Collection) obj;
                
                //quitar anteriores
                col.removeAllListProperties();
                col.removeAllSearchProperties();


                //agregando propiedades de despliegue
                String[] semprop = request.getParameterValues("semprop");
                if (semprop != null) {
                    for (int i = 0; i < semprop.length; i++) {
                        //System.out.println("prop("+i+"): "+semprop[i]);
                        col.addListProperties(semprop[i]);
                    }
                }

                //agregando propiedades de búsqueda
                String[] sempropSerch = request.getParameterValues("sempropSearch");
                if (sempropSerch != null) {
                    for (int i = 0; i < sempropSerch.length; i++) {
                        //System.out.println("propSearch("+i+"): "+sempropSerch[i]);
                        col.addSearchProperties(sempropSerch[i]);
                    }
                }
            }

        } else if ("new".equals(action)) {


            log.debug("ProcessAction(new) ");

            id = request.getParameter("suri");
            String clsuri = request.getParameter("clsuri");
            String ract = request.getParameter("ract");

            SemanticObject so = ont.getSemanticObject(id);
            SemanticClass sclass = ont.getSemanticObject(clsuri).transformToSemanticClass();
            log.debug("ProcessAction(new): sobj: " + id);

            SWBFormMgr fmgr = new SWBFormMgr(sclass, so.getModel().getModelObject(), SWBFormMgr.MODE_CREATE);
            try {
                SemanticObject nso = fmgr.processForm(request);

                if (clsuri != null) {
                    response.setRenderParameter("clsuri", clsuri);
                }
                if (ract != null) {
                    response.setRenderParameter("act", ract);
                }
                response.setRenderParameter("nsuri", nso.getURI());

            } catch (FormValidateException e) {
                throw new SWBResourceException("Error to process form...", e);
            }

            response.setRenderParameter("statmsg", response.getLocaleString("statmsg1"));
            response.setMode(response.Mode_EDIT);

        } else if ("remove".equals(action)) //suri, prop
        {
            log.debug("processAction(remove)");

            String ract = request.getParameter("ract");
            sval = request.getParameter("sval");
            if (sval != null) {
                SemanticObject so = ont.getSemanticObject(sval);
                if (so != null) {
                    so.remove();
                }
            }

            if (ract != null) {
                response.setRenderParameter("act", ract);
            }

            log.debug("remove-closetab:" + sval);
            response.setRenderParameter("closetab", sval);
            response.setRenderParameter("statmsg", response.getLocaleString("statmsg2"));
            response.setMode(SWBActionResponse.Mode_EDIT);
        }

        if (id != null) {

            response.setRenderParameter("suri", id);
        }

        log.debug("remove-closetab:" + sval);
        response.setMode(response.Mode_EDIT);
    }
}
