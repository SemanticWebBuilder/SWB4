package org.semanticwb.bsc.admin.resources;


import com.arthurdo.parser.HtmlException;
import com.arthurdo.parser.HtmlStreamTokenizer;
import com.arthurdo.parser.HtmlTag;
import com.hp.hpl.jena.rdf.model.Statement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import javax.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.*;
import org.semanticwb.bsc.formelement.TextAreaElement;
import org.semanticwb.bsc.tracing.Measure;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.bsc.utils.DetailView;
import org.semanticwb.bsc.utils.PropertiesComparator;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Text;
import org.semanticwb.model.TextArea;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;


/**
 * Administra instancias disponibles de tipo {@code DetailView} cuya propiedad {@code objectsType} tenga el mismo valor.
 * También define cual de las instancias de {@code DetailView} ser&aacute; la utilizada por el sistema para presentar los
 * datos de los objetos del BSC.
 * @author jose.jimenez
 */
public class DetailViewManager extends org.semanticwb.bsc.admin.resources.base.DetailViewManagerBase {

    /** Realiza operaciones en la bitacora de eventos. */
    private static Logger log = SWBUtils.getLogger(GenericSemResource.class);

    /** Representa el nombre del archivo asociado a las plantillas creadas con este recurso */
    private static final String TEMPLATE_FILENAME = "/templateContent.html";
    
    /**
     * Genera una instancia de este recurso
     */
    public DetailViewManager() {
    }

    /**
     * Constructs a DetailViewManager with a SemanticObject
     * @param base The SemanticObject with the properties for the DetailViewManager
     */
    public DetailViewManager(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Eval&uacute;a la configuraci&oacute;n hecha al recurso y si es v&aacute;lida, permite el despliegue 
     * del listado de instancias disponibles de las vistas detalle cuya propiedad {@code objectsType}
     * coincide con la configuraci&oacute;n del recurso.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        boolean workClassIsValid = false;
        if (this.getWorkClass() != null) {
            SemanticClass semWorkClass = this.getWorkClass().transformToSemanticClass();
            workClassIsValid = semWorkClass.getRootClass().equals(BSCElement.bsc_BSCElement);
        }
        if (workClassIsValid) {
            //Una vez que se especifica la clase de objetos, se puede administrar las instancias
            doShowListing(request, response, paramRequest);
        } else {
            super.doAdmin(request, response, paramRequest);
        }
    }
    
    /**
     * Eval&uacute;a la configuraci&oacute;n hecha al recurso y si es v&aacute;lida, permite el despliegue 
     * del listado de instancias disponibles de las vistas detalle cuya propiedad {@code objectsType}
     * coincide con la configuraci&oacute;n del recurso.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
     */
    public void doShowListing(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        StringBuilder listCode = new StringBuilder(256);
        String lang = paramRequest.getUser().getLanguage();
        BSC bsc = (BSC) this.getResource().getWebSite();
        SemanticClass workClassSC = this.getWorkClass() != null
                ? this.getWorkClass().transformToSemanticClass()
                : null;
        String statusMsg = request.getParameter("statusMsg");
        String statusErr = request.getParameter("statusErr");
        String objectsType = workClassSC != null ? workClassSC.getURI() : null;
        Resource base = getResourceBase();

        if (bsc != null && objectsType != null) {
            SWBResourceURL urlCreate = paramRequest.getRenderUrl();
            urlCreate.setParameter("operation", "add");
            urlCreate.setMode("editTemplate");

            SWBResourceURL url = paramRequest.getActionUrl();
            url.setAction("setPerm");
            //Se genera el código HTML del encabezado de la tabla que muestra el listado de vistas
            listCode.append("<div class=\"swbform\">\n");
            
            listCode.append("<form dojoType=\"dijit.form.Form\" name=\"permissionsForm");
            listCode.append(this.getId());
            listCode.append("\" ");
            listCode.append("id=\"permissionsForm");
            listCode.append(this.getId());
            listCode.append("\" class=\"swbform\" onsubmit=\"submitForm('permissionsForm");
            listCode.append(this.getId());
            listCode.append("');return false;\" method=\"post\" action=\"");
            listCode.append(url.toString());
            listCode.append("\">\n");
            listCode.append("  <fieldset>\n");
            listCode.append("    <legend>");
            listCode.append(paramRequest.getLocaleString("lbl_grantsSelection"));
            listCode.append("</legend>\n");
            
            //Para mostrar la seleccion de otorgamiento de permisos
            String roleSelected = base.getAttribute("editRole", "");
            StringBuilder strRules = new StringBuilder(256);
            strRules.append("        <optgroup label=\"");
            strRules.append(paramRequest.getLocaleString("lbl_roles"));
            strRules.append("\">\n");
            Iterator<Role> iRoles = this.getResource().getWebSite().getUserRepository().listRoles();
            while (iRoles.hasNext()) {
                Role oRole = iRoles.next();
                strRules.append("        <option value=\"");
                strRules.append(oRole.getURI());
                strRules.append("\"");
                strRules.append(roleSelected.equals(oRole.getURI()) ? " selected=\"selected\" " : "");
                strRules.append(">");
                strRules.append(oRole.getDisplayTitle(lang));
                strRules.append("</option>\n");
            }
            strRules.append("        </optgroup>\n");
            strRules.append("        <optgroup label=\"");
            strRules.append(paramRequest.getLocaleString("lbl_userGroups"));
            strRules.append("\">\n");
            Iterator<UserGroup> iugroups = this.getResource().getWebSite().getUserRepository().listUserGroups();
            while (iugroups.hasNext()) {
                UserGroup oUG = iugroups.next();
                strRules.append("        <option value=\"");
                strRules.append(oUG.getURI());
                strRules.append("\"");
                strRules.append(roleSelected.equals(oUG.getURI()) ? " selected=\"selected\" " : "");
                strRules.append(">");
                strRules.append(oUG.getDisplayTitle(lang) == null ? oUG.getTitle() : oUG.getDisplayTitle(lang));
                strRules.append("</option>\n");
            }
            strRules.append("        </optgroup>\n");
            
            listCode.append("  <ul class=\"swbform-ul\">\n");
            listCode.append("    <li class=\"swbform-li\">\n");
            listCode.append("      <label for=\"updatePermit");
            listCode.append(this.getId());
            listCode.append("\" class=\"swbform-label\">");
            listCode.append(paramRequest.getLocaleString("lbl_permissionsSelect"));
            listCode.append("</label>\n");
            listCode.append("      <select name=\"updatePermit");
            listCode.append(this.getId());
            listCode.append("\">");
            listCode.append(strRules);
            listCode.append("       </select>\n");
            listCode.append("     </li>\n");
            listCode.append("   <li class=\"swbform-li\">");
            listCode.append("       <label for=\"before\" class=\"swbform-label\">");
            listCode.append(paramRequest.getLocaleString("lbl_timeBefore"));
            listCode.append("</label>");
            listCode.append("       <input type=\"text\" name=\"before\" id=\"before\" maxlength=\"2\" value=\"");
            listCode.append(base.getAttribute("before", "7"));
            listCode.append("\" regExp=\"\\d{1,2}\" dojoType=\"dijit.form.ValidationTextBox\" invalidMessage=\"");
            listCode.append(paramRequest.getLocaleString("err_incorrectAmount"));
            listCode.append("\"  promptMessage=\"");
            listCode.append(paramRequest.getLocaleString("msg_promptTimeBefore"));
            listCode.append("\" />");
            listCode.append("     </li>");
            listCode.append("   <li class=\"swbform-li\">");
            listCode.append("       <label for=\"after\" class=\"swbform-label\">");
            listCode.append(paramRequest.getLocaleString("lbl_timeAfter"));
            listCode.append("</label>");
            listCode.append("       <input type=\"text\" name=\"after\" id=\"after\" maxlength=\"2\" value=\"");
            listCode.append(base.getAttribute("after", "7"));
            listCode.append("\" regExp=\"\\d{1,2}\" dojoType=\"dijit.form.ValidationTextBox\" invalidMessage=\"");
            listCode.append(paramRequest.getLocaleString("err_incorrectAmount"));
            listCode.append("\"  promptMessage=\"");
            listCode.append(paramRequest.getLocaleString("msg_promptTimeAfter"));
            listCode.append("\" />");
            listCode.append("     </li>");
            listCode.append("   <li class=\"swbform-li\">");
            listCode.append("       <label for=\"timeUnit\" class=\"swbform-label\">");
            listCode.append(paramRequest.getLocaleString("lbl_unitOfTime"));
            listCode.append("</label>");
            listCode.append("       <select id=\"timeUnit\" name=\"timeUnit\" dojoType=\"dijit.form.FilteringSelect\">");
            listCode.append("         <option");
            listCode.append(base.getAttribute("unitTime", Integer.toString(Calendar.DATE)).equals(Integer.toString(Calendar.DATE)) ? " selected=\"selected\"" : "");
            listCode.append(" value=\"");
            listCode.append(Calendar.DATE);
            listCode.append("\">");
            listCode.append(paramRequest.getLocaleString("lbl_dayMeasure"));
            listCode.append("</option>");
            listCode.append("         <option ");
            listCode.append(base.getAttribute("unitTime", "").equals(Integer.toString(Calendar.WEEK_OF_YEAR)) ? "selected=\"selected\"" : "");
            listCode.append(" value=\"");
            listCode.append(Calendar.WEEK_OF_YEAR);
            listCode.append("\">");
            listCode.append(paramRequest.getLocaleString("lbl_weekMeasure"));
            listCode.append("</option>");
            listCode.append("         <option");
            listCode.append(base.getAttribute("unitTime", "").equals(Integer.toString(Calendar.MONTH)) ? " selected=\"selected\"" : "");
            listCode.append(" value=\"");
            listCode.append(Calendar.MONTH);
            listCode.append("\">");
            listCode.append(paramRequest.getLocaleString("lbl_monthMeasure"));
            listCode.append("</option>");
            listCode.append("         <option ");
            listCode.append(base.getAttribute("unitTime", "").equals(Integer.toString(Calendar.YEAR)) ? "selected=\"selected\"" : "");
            listCode.append(" value=\"");
            listCode.append(Calendar.YEAR);
            listCode.append("\">");
            listCode.append(paramRequest.getLocaleString("lbl_yearMeasure"));
            listCode.append("</option>");
            listCode.append("       </select>");
            listCode.append("     </li>");
            listCode.append("  </ul>\n");
            listCode.append("  </fieldset>\n");
            listCode.append("  <fieldset>\n");
            listCode.append("    <button dojoType=\"dijit.form.Button\" id=\"btnSavePerm");
            listCode.append(this.getId());
            listCode.append("\" type=\"button\">");
            listCode.append(paramRequest.getLocaleString("lbl_btnSubmit"));
            listCode.append("      <script type=\"dojo/method\" event=\"onClick\" args=\"evt\">\n");
            listCode.append("          submitForm(\"permissionsForm");
            listCode.append(this.getId());
            listCode.append("\");\n");
            listCode.append("          return false;\n");
            listCode.append("      </script>\n");
            listCode.append("    </button>\n");
            listCode.append("  </fieldset>\n");
            listCode.append("</form>\n");
            
            listCode.append("  <fieldset>\n");
            listCode.append("    <legend>");
            listCode.append(paramRequest.getLocaleString("lbl_objectsType"));
            listCode.append(workClassSC.getName());
            listCode.append("</legend>");
            listCode.append("    <table width=\"98%\" >\n");
            listCode.append("      <thead>\n");
            listCode.append("        <tr>\n");
            listCode.append("          <th>");
            listCode.append(paramRequest.getLocaleString("th_action"));
            listCode.append("</th>\n");
            listCode.append("          <th>");
            listCode.append(DetailView.swb_title.getDisplayName(lang));
            listCode.append("</th>\n");
            listCode.append("          <th>");
            listCode.append(DetailView.swb_modifiedBy.getDisplayName(lang));
            listCode.append("</th>\n");
            listCode.append("          <th>");
            listCode.append(DetailView.swb_updated.getDisplayName(lang));
            listCode.append("</th>\n");
            listCode.append("          <th>");
            listCode.append(paramRequest.getLocaleString("th_assigned"));
            listCode.append("</th>\n");
            listCode.append("        </tr>\n");
            listCode.append("      </thead>\n");
            listCode.append("      <tbody>\n");
                
            Iterator listOfViews = DetailView.ClassMgr.listDetailViews(bsc);

            if (listOfViews != null && listOfViews.hasNext()) {
                ArrayList<DetailView> viewsList = new ArrayList<DetailView>(32);
                while (listOfViews.hasNext()) {
                    DetailView view2Include = (DetailView) listOfViews.next();
                    if (view2Include.getObjectsType() != null
                            && view2Include.getObjectsType().equals(objectsType)) {
                        viewsList.add(view2Include);
                    }
                }
                listOfViews = viewsList.iterator();
            }
            if (listOfViews != null && listOfViews.hasNext()) {
                listOfViews = SWBComparator.sortByDisplayName(listOfViews, lang);
                
                try {
                    //Se agrega al listado cada vista creada
                    while (listOfViews.hasNext()) {
                        DetailView view = (DetailView) listOfViews.next();

                        SWBResourceURL urlEdit = paramRequest.getRenderUrl();
                        urlEdit.setParameter("operation", "edit");
                        urlEdit.setParameter("viewUri", view.getURI());
                        urlEdit.setMode("editTemplate");

                        SWBResourceURL urlDelete = paramRequest.getActionUrl();
                        urlDelete.setAction("deleteView");
                        urlDelete.setParameter("svUri", view.getURI());

                        SWBResourceURL urlMakeActive = paramRequest.getActionUrl();
                        urlMakeActive.setAction("makeActive");
                        urlMakeActive.setParameter("svUri", view.getURI());

                        //Se genera el HTML para las opciones de edición, eliminación
                        listCode.append("        <tr>\n");
                        listCode.append("          <td>\n");
                        listCode.append("            <a href=\"void(0);\" title=\"");
                        listCode.append(paramRequest.getLocaleString("ttl_lnkEdit"));
                        listCode.append("\" onclick=\"submitUrl('");
                        listCode.append(urlEdit);
                        listCode.append("', this);return false;\"><img src=\"");
                        listCode.append(SWBPlatform.getContextPath());
                        listCode.append("/swbadmin/icons/editar_1.gif\" border=\"0\" alt=\"");
                        listCode.append(paramRequest.getLocaleString("ttl_lnkEdit"));
                        listCode.append("\"></a>\n");
                        listCode.append("            <a href=\"void(0);\" title=\"");
                        listCode.append(paramRequest.getLocaleString("ttl_lnkDelete"));
                        listCode.append("\" onclick=\"if (confirm('");
                        listCode.append(paramRequest.getLocaleString("msg_confirmRemove"));
                        listCode.append(SWBUtils.TEXT.scape4Script(view.getTitle() != null ? view.getTitle() : "xxx"));
                        listCode.append("?')){submitUrl('");
                        listCode.append(urlDelete);
                        listCode.append("', this);}return false;\"><img src=\"");
                        listCode.append(SWBPlatform.getContextPath());
                        listCode.append("/swbadmin/images/delete.gif\" border=\"0\" alt=\"");
                        listCode.append(paramRequest.getLocaleString("ttl_lnkDelete"));
                        listCode.append("\"></a>\n");
                        listCode.append("          </td>\n");
                        listCode.append("          <td>");
                        listCode.append("            <a href=\"void(0);\" title=\"");
                        listCode.append(paramRequest.getLocaleString("ttl_lnkEdit"));
                        listCode.append("\" onclick=\"submitUrl('");
                        listCode.append(urlEdit);
                        listCode.append("', this);return false;\">");
                        listCode.append(view.getTitle() != null ? view.getTitle() : "xxx");
                        listCode.append("</a>\n");
                        listCode.append("          </td>\n");
                        listCode.append("          <td>");
                        listCode.append(view.getModifiedBy() != null ? view.getModifiedBy().getFullName() : "xxx");
                        listCode.append("          </td>\n");
                        listCode.append("          <td>");
                        listCode.append(view.getUpdated() != null ? view.getUpdated() : "xxx");
                        listCode.append("          </td>\n");
                        listCode.append("          <td align=\"center\">");

                        if (this.getActiveDetailView() == null || (this.getActiveDetailView() != null
                                && !this.getActiveDetailView().getURI().equals(view.getURI()))) {
                            //Código HTML para asignación como contenido
                            listCode.append("          <input type=\"radio\" dojoType=\"dijit.form.RadioButton\" ");
                            listCode.append("name=\"asContent\" id=\"asContent");
                            listCode.append(view.getId());
                            listCode.append("\" value=\"");
                            listCode.append(view.getId());
                            listCode.append("\" onclick=\"submitUrl('");
                            listCode.append(urlMakeActive);
                            listCode.append("', this.domNode);return false;\">");
                        } else if (this.getActiveDetailView() != null
                                && this.getActiveDetailView().getURI().equals(view.getURI())) {
                            listCode.append("          <input type=\"radio\" dojoType=\"dijit.form.RadioButton\" ");
                            listCode.append("name=\"asContent\" id=\"asContent");
                            listCode.append(view.getId());
                            listCode.append("\" value=\"");
                            listCode.append(view.getId());
                            listCode.append("\" checked=\"checked\" onclick=\"submitUrl('");
                            listCode.append(urlMakeActive);
                            listCode.append("', this.domNode);return false;\">");
                        }
                        listCode.append("          </td>\n");
                        listCode.append("        </tr>\n");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            listCode.append("      </tbody>\n");
            listCode.append("    </table>\n");
            listCode.append("  </fieldset>\n");
            listCode.append("  <fieldset>\n");
            listCode.append("    <button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('");
            listCode.append(urlCreate);
            listCode.append("', this.domNode); return false;\">");
            listCode.append(paramRequest.getLocaleString("btn_add"));
            listCode.append("</button>\n");
            listCode.append("  </fieldset>\n");
            listCode.append("</div>\n");
        } else {
            DetailViewManager.log.error(new SWBResourceException("Propiedad DetailViewManager.workClass es nula"));
        }

        //Muestra mensaje sobre resultado de la operacion realizada
        if ((statusMsg != null && !statusMsg.isEmpty()) ||
                (statusErr != null && !statusErr.isEmpty())) {
            listCode.append("<div dojoType=\"dojox.layout.ContentPane\">\n");
            listCode.append("    <script type=\"dojo/method\">\n");
            if (statusMsg != null) {
                statusMsg = paramRequest.getLocaleString(statusMsg);
                listCode.append("        showStatus('" + statusMsg + "');\n");
            } else if (statusErr != null) {
                statusErr = paramRequest.getLocaleString(statusErr);
                listCode.append("        showError('" + statusErr + "');\n");
            }
            listCode.append("    </script>\n");
            listCode.append("</div>\n");
        }

        out.println(listCode.toString());

    }
    
    /**
     * Muestra la interface para captura de los datos de las vistas detalle, dentro de los cuales
     * se define la configuraci&oacute;n del despliegue de informaci&oacute;n de los objetos del 
     * tipo especificado en la configuraci&oacute;n del recurso.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
     */
    public void doEditTemplate(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(512);
        String templateContent = new String();
        String viewUri = request.getParameter("viewUri") != null
                        ? request.getParameter("viewUri") : "";
        String propertiesUrl = paramRequest.getRenderUrl().setMode("getPropertiesInfo"
                ).setCallMethod(SWBResourceURLImp.Call_DIRECT).toString();
        String operation = request.getParameter("operation") == null
                        ? "add" : request.getParameter("operation");
        String statusMsg = request.getParameter("statusMsg");
        String statusErr = request.getParameter("statusErr");
        
        String lang = paramRequest.getUser().getLanguage();
        SWBFormMgr formMgr = null;
        String modeUsed = null;
        SWBResourceURL url = paramRequest.getActionUrl();
        DetailView viewSemObject = !viewUri.isEmpty()
                ? (DetailView) SemanticObject.getSemanticObject(viewUri).createGenericInstance()
                : null;
        
        if (operation.equals("edit")) {
            if (viewSemObject == null) {
                throw new SWBResourceException("View URI is null while editing");
            }
            modeUsed = SWBFormMgr.MODE_EDIT;
            formMgr = new SWBFormMgr(DetailView.bsc_DetailView,
                        viewSemObject.getSemanticObject(), modeUsed);
            url.setAction("editView");
            String filePath = SWBPortal.getWorkPath() + 
                    viewSemObject.getWorkPath() + DetailViewManager.TEMPLATE_FILENAME;
            templateContent = SWBUtils.IO.getFileFromPath(filePath);
        } else if (operation.equals("add")) {
            modeUsed = SWBFormMgr.MODE_CREATE;
            formMgr = new SWBFormMgr(DetailView.sclass, null, modeUsed);
            url.setAction("addView");
        }
        
        //Se genera el código HTML para presentar la forma en que se capturan los datos de la vista detalle
        //para los dos modos: creación y edición
        output.append("        <div>\n");
        output.append("            <form dojoType=\"dijit.form.Form\" name=\"detailViewForm");
        output.append(this.getId());
        output.append("\" ");
        output.append("id=\"detailViewForm");
        output.append(this.getId());
        output.append("\" class=\"swbform\" onsubmit=\"submitForm('detailViewForm");
        output.append(this.getId());
        output.append("');return false;\" method=\"post\" action=\"");
        output.append(url.toString());
        output.append("\">\n");
        output.append("                <input type=\"hidden\" id=\"urlForProperties\" name=\"urlForProperties\" value=\"");
        output.append(propertiesUrl);
        output.append("\">\n");
                
        if (operation.equals("edit")) {
            output.append("                <input type=\"hidden\" id=\"svUri");
            output.append(this.getId());
            output.append("\" name=\"svUri\" value=\"" + viewUri + "\">\n");
        }
        output.append("<fieldset>\n");
        output.append("    <legend>");
        output.append(paramRequest.getLocaleString("lbl_pageTitle"));
        output.append("</legend>\n");
        output.append("    <table>\n");
        output.append("        <tbody>\n");
        output.append("            <tr>\n");
        output.append("                <td width=\"200px\" align=\"right\">\n");
        output.append("                    ");
        output.append(formMgr.renderLabel(request, DetailView.swb_title, DetailView.swb_title.getName(), modeUsed));
        output.append("                </td>\n");
        output.append("                <td>\n");
        output.append("                    ");
        if (operation.equals("edit") && viewSemObject != null) {
            output.append(
                    formMgr.getFormElement(DetailView.swb_title).renderElement(
                            request, viewSemObject.getSemanticObject(), DetailView.swb_title,
                            DetailView.swb_title.getName(), "dojo", modeUsed, lang)
                );
        } else if (operation.equals("add")) {
            output.append(
                    formMgr.getFormElement(DetailView.swb_title).renderElement(
                            request, null, DetailView.swb_title, DetailView.swb_title.getName(),
                            "dojo", modeUsed, lang)
                );
        }
        output.append("                </td>\n");
        output.append("            </tr>\n");
        output.append("            <tr>\n");
        output.append("                <td width=\"200px\" align=\"right\">\n");
        output.append("                    ");
        output.append(formMgr.renderLabel(request, DetailView.swb_description, modeUsed));
        output.append("                </td>\n");
        output.append("                <td>\n");
        output.append("                    ");
        if (operation.equals("edit") && viewSemObject != null) {
            output.append(
                    formMgr.getFormElement(DetailView.swb_description).renderElement(
                            request, viewSemObject.getSemanticObject(), DetailView.swb_description,
                            DetailView.swb_description.getName(), "dojo", modeUsed, lang)
                    );
        } else if (operation.equals("add")) {
            output.append(
                    formMgr.getFormElement(DetailView.swb_description).renderElement(
                            request, null, DetailView.swb_description, DetailView.swb_description.getName(),
                            "dojo", modeUsed, lang)
                    );
        }
        output.append("                </td>\n");
        output.append("            </tr>\n");
        output.append("            <tr>\n");
        output.append("                <td align=\"right\">\n");
        output.append("                  <label for=\"FCKeditorDetailView");
        output.append(this.getId());
        output.append("\">\n");
        output.append(paramRequest.getLocaleString("lbl_template"));
        output.append("&nbsp; \n");
        output.append("                  </label>\n");
        output.append("                </td>\n");
        output.append("                <td align=\"left\" width=\"850px\" height=\"600px\">\n");
        output.append("                  <textarea name=\"FCKeditorDetailView");
        output.append(this.getId());
        output.append("\" id=\"FCKeditorDetailView");
        output.append(this.getId());
        output.append("\">");
        output.append(templateContent);
        output.append("</textarea>\n");
        output.append("");
        output.append("<div dojoType=\"dojox.layout.ContentPane\">\n");
        output.append("    <script type=\"dojo/method\">\n");
        output.append("      var oFCKeditor = new FCKeditor( 'FCKeditorDetailView");
        output.append(this.getId());
        output.append("', '100%', '100%' ) ;\n");
        output.append("      oFCKeditor.BasePath = '");
        output.append(SWBPlatform.getContextPath());
        output.append("/swbadmin/js/fckeditor/';\n");
        output.append("      oFCKeditor.Config['CustomConfigurationsPath'] = ");
        output.append(SWBPlatform.getContextPath());
        output.append("'/swbadmin/js/fckeditor/fckconfig_inserter.js';\n");
        output.append("      oFCKeditor.ReplaceTextarea();\n");
        output.append("      oFCKeditor.urlInsertHtmlData = '");
        output.append(SWBUtils.TEXT.scape4Script(propertiesUrl));
        output.append("' ;\n");
        output.append("    </script>\n");
        output.append("</div>\n");
        output.append("                </td>\n");
        output.append("            </tr>\n");
        output.append("        </tbody>\n");
        output.append("    </table>\n");
        output.append("</fieldset>\n");
        //despliegue de botones
        output.append("<fieldset>\n");
        output.append("    <button dojoType=\"dijit.form.Button\" id=\"btnSave");
        output.append(this.getId());
        output.append("\" type=\"button\">");
        output.append(paramRequest.getLocaleString("lbl_btnSubmit"));
        output.append("      <script type=\"dojo/method\" event=\"onClick\" args=\"evt\">\n");
        output.append("        var oEditor = FCKeditorAPI.GetInstance('FCKeditorDetailView");
        output.append(this.getId());
        output.append("') ;\n");
        output.append("        document.getElementById('FCKeditorDetailView");
        output.append(this.getId());
        output.append("').value = oEditor.GetData();\n");
        output.append("        submitForm(\"detailViewForm");
        output.append(this.getId());
        output.append("\");\n");
        output.append("        return false;\n");
        output.append("      </script>\n");
        output.append("    </button>\n");
        
        SWBResourceURL urlCancel = paramRequest.getRenderUrl();
        urlCancel.setMode("showListing");
        output.append("    <button dojoType=\"dijit.form.Button\" ");
        output.append("type=\"button\" onClick=\"reloadTab('");
        output.append(this.getResource().getURI());
        output.append("');\">\n");
        output.append(paramRequest.getLocaleString("lbl_btnCancel"));
        output.append("    </button>\n");
        
        output.append("</fieldset>\n");
        output.append("            </form>\n");
        output.append("        </div>\n");
        
        //Muestra mensaje sobre resultado de la operacion realizada
        if ((statusMsg != null && !statusMsg.isEmpty()) ||
                (statusErr != null && !statusErr.isEmpty())) {
            
            output.append("<div dojoType=\"dojox.layout.ContentPane\">\n");
            output.append("    <script type=\"dojo/method\">\n");
            if (statusMsg != null) {
                statusMsg = paramRequest.getLocaleString(statusMsg);
                output.append("        showStatus('" + statusMsg + "');\n");
            } else if (statusErr != null) {
                statusErr = paramRequest.getLocaleString(statusErr);
                output.append("        showError('" + statusErr + "');\n");
            }
            output.append("    </script>\n");
            output.append("</div>\n");
        }

        out.println(output);
    }
    
    /**
     * Genera el despliegue de los datos asociados a los elementos del BSC, de acuerdo 
     * a la configuración hecha para la vista detalle asignada como contenido.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(256);
        String message = validateInput(request, paramRequest);

        output.append("<div id=\"detalle\" class=\"detalleObjetivo\">\n");

        if (message == null) {
            FileReader reader = retrieveTemplate();
            String suri = request.getParameter("suri");
            SemanticObject semObj = SemanticObject.getSemanticObject(suri);
            //Declarar variable para el per&iacte;odo, obteniendo el valor del request
            String modelName = paramRequest.getWebPage().getWebSiteId();
            String periodId = request.getSession().getAttribute(modelName) != null
                                ? (String) request.getSession().getAttribute(modelName)
                                : null;
            //Si no hay sesión, la petición puede ser directa (una liga en un correo). Crear sesión y atributo:
            if (periodId == null) {
                periodId = request.getParameter(modelName) != null ? request.getParameter(modelName) : null;
                if (periodId != null) {
                    request.getSession(true).setAttribute(modelName, periodId);
                }
            }
            Period period = Period.ClassMgr.getPeriod(periodId, paramRequest.getWebPage().getWebSite());
            PeriodStatus periodStatus = null;
            
            UserGroup collaboration = null;
            
            //Si el semObj es hijo de PeriodStatusAssignable se debe:
            GenericObject generic = semObj.createGenericInstance();
            if (generic != null && generic instanceof Objective) {
                Objective objective = (Objective) generic;
                periodStatus = objective.getPeriodStatus(period);
                collaboration = objective.getSponsor().getUserRepository().getUserGroup("Sponsors");
            } else if (generic != null && generic instanceof Indicator) {
                Indicator indicator = (Indicator) generic;
                Measure measure = indicator != null && indicator.getStar() != null ? indicator.getStar().getMeasure(period) : null;
                if (measure != null && measure.getEvaluation() != null) {
                    periodStatus = measure.getEvaluation();
                }
                collaboration = indicator.getChampion().getUserRepository().getUserGroup("Champions");
            }
            
            //-Agrega encabezado al cuerpo de la vista detalle, en el que se muestre el estado del objeto
            // para el per&iacte;odo especificado y el t&iacte;tulo del objeto, para lo que:
            //    - Se pide el listado de objetos PeriodStatus asociado al semObj
            //    - Se recorre uno por uno los PeriodStatus relacionados
            //    - Cuando el per&iacte;odo del PeriodStatus = per&iacte;odo del request:
            //        - Se obtiene el status correspondiente y su &iacte;cono relacionado
            //        - Se agrega el &iacte;cono al encabezado y el t&iacte;tulo del objeto semObj
            output.append("<h2");
            output.append(" class=\"");
            if (periodStatus != null && periodStatus.getStatus() != null && 
                    periodStatus.getStatus().getIconClass() != null) {
                output.append(periodStatus.getStatus().getIconClass());
            } else {
                output.append("indefinido");
            }
            output.append("\"");
            output.append(">");
            output.append(semObj.getDisplayName());
            output.append("</h2>\n");

            if (reader != null) {
                output.append(generateDisplay(request, paramRequest, reader, semObj, collaboration));
            } else {
                output.append(paramRequest.getLocaleString("fileNotRead"));
            }
        } else { //Si la información de entrada no es válida
            output.append(paramRequest.getLocaleString(message));
        }

        output.append("</div>\n");
        out.println(output.toString());
    }

    /**
     * Genera una estructura de objetos JSON con los datos de las propiedades 
     * pertenecientes a la clase configurada en el recurso.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
     */
    public void doGetPropertiesInfo(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        response.setContentType("application/json; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        SemanticClass semWorkClass = this.getWorkClass().transformToSemanticClass();
        ArrayList<SemanticProperty> propsList = 
                (ArrayList<SemanticProperty>) SWBUtils.Collections.copyIterator(semWorkClass.listSortProperties());
        Collections.sort(propsList, new PropertiesComparator());
        Iterator<SemanticProperty> basePropertiesList = propsList.iterator();
        JSONArray array = new JSONArray();
        String structure = null;

        if (semWorkClass != null) {
            try {
                while (basePropertiesList.hasNext()) {
                    SemanticProperty prop = basePropertiesList.next();
                    if (prop.getDisplayProperty() != null || displayElementExists(prop)) {
                        //Se crea una estructura de JSON con los datos de las propiedades de la clase en la variable array
                        JSONObject object = new JSONObject();
                        object.put("name", prop.getName());
                        object.put("label", prop.getDisplayName());
                        object.put("uri", prop.getURI());
                        array.put(object);
                    }
                }
                structure = array.toString(2);
            } catch (JSONException jsone) {
                structure = "";
                DetailViewManager.log.error("Al generar estructura de JSON con propiedades semanticas", jsone);
            }
        }
        //Se regresa como respuesta la estructura de JSON creada
        out.println(structure);
    }
    
    /**
     * Determina el modo a ejecutar, en base a los parámetros recibidos en la petici&oacute;n del cliente
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        if (paramRequest.getMode().equals("showListing")) {
            doShowListing(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("editTemplate")) {
            doEditTemplate(request, response, paramRequest);
        } else if (paramRequest.getMode().equals("getPropertiesInfo")) {
            doGetPropertiesInfo(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    /**
     * Realiza las operaciones de almacenamiento o eliminaci&oacute;n de informaci&oacute;n de las 
     * vistas detalle administradas, asi como la asignaci&oacute;n de una vista detalle seleccionada
     * como contenido.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response)
            throws SWBResourceException, IOException {
        
        String action = response.getAction();
        String title = request.getParameter("title");
        String descrip = request.getParameter("description");
        String configuration = request.getParameter("FCKeditorDetailView" + this.getId()) != null
                ? request.getParameter("FCKeditorDetailView" + this.getId())
                : "";
        String detailViewUri = request.getParameter("svUri");
        String statusMsg = null;
        String errorMsg = null;
        String lang = response.getUser().getLanguage();
        String viewFilePath = null;
        DetailView detailView = null;
        BSC bscModel = (BSC) this.getResource().getWebSite();
        boolean listingRedirect = false;
        boolean formRedirect = false;
        boolean storePath = false;
        if (!"addView".equals(action)) {
            detailView = SemanticObject.getSemanticObject(detailViewUri) != null
                    ? ((DetailView) SemanticObject.getSemanticObject(
                    detailViewUri).createGenericInstance())
                    : null;
        }
        if ("addView".equals(action)
                || "editView".equals(action)) {

            if ("addView".equals(action)) {
                SemanticClass semWorkClass = this.getWorkClass().transformToSemanticClass();

                if (semWorkClass != null) {
                    detailView = DetailView.ClassMgr.createDetailView(bscModel);
                    detailView.setObjectsType(semWorkClass.getURI());

                    statusMsg = "msg_ViewCreated";
                }
            } else if ("editView".equals(action)) {

                statusMsg = "msg_ViewUpdated";
            }
            if (detailView != null) {

                if (!configuration.isEmpty()) {
                    //Almacenar en el archivo asociado el contenido de la configuración
                    viewFilePath = SWBPortal.getWorkPath() + detailView.getWorkPath();
                    try {
                        File filePath = new File(viewFilePath);
                        if (!filePath.exists()) {
                            filePath.mkdirs();
                        }
                        viewFilePath += DetailViewManager.TEMPLATE_FILENAME;
                        File file = new File(viewFilePath);
                        if (detailView.getViewFilePath() == null) {
                            storePath = true;
                        }
                        FileWriter writer = new FileWriter(file);
                        writer.write(configuration);
                        writer.flush();
                        writer.close();
                    } catch (IOException ioe) {
                        DetailViewManager.log.error("Al escribir el contenido de la plantilla", ioe);
                        errorMsg = "msg_FileNotWriten";
                    }
                    if (storePath) {
                        detailView.setViewFilePath(viewFilePath);
                    }
                }
                //Se actualiza informacion de Traceable
                SWBPortal.getServiceMgr().updateTraceable(detailView.getSemanticObject(), response.getUser());
                detailView.setTitle(title, lang);
                detailView.setTitle(title);
                detailView.setDescription(descrip, lang);
                detailView.setDescription(descrip);
                response.setRenderParameter("viewUri", detailView.getURI());
                response.setRenderParameter("operation", "edit");
            }
            formRedirect = true;

        } else if ("deleteView".equals(action)) {

            if (detailView != null) {
                try {
                    if (this.getActiveDetailView() != null && this.getActiveDetailView() == detailView) {
                        this.setActiveDetailView(null);
                    }
                    //Se debe eliminar el archivo asociado a la vista detalle a eliminar
                    if (detailView.getViewFilePath() != null) {
                        File toDelete = new File(detailView.getViewFilePath());
                        if (toDelete.canWrite()) {
                            toDelete.delete();
                        }
                    }
                    DetailView.ClassMgr.removeDetailView(detailView.getId(), bscModel);
                    statusMsg = "msg_ViewDeleted";
                } catch (Exception e) {
                    errorMsg = "msg_ViewNotDeleted";
                    DetailViewManager.log.error("Al eliminar una vista detalle", e);
                }
            }
            listingRedirect = true;
        } else if (action.equalsIgnoreCase("makeActive") && detailView != null) {
            //asignar a DetailViewAdm la instancia de DetailView correspondiente al uri recibido en request
            this.setActiveDetailView(detailView);
            statusMsg = "msg_ContentViewAssigned";
            listingRedirect = true;
        } else if (action.equalsIgnoreCase("setPerm")) {
            Resource base = getResourceBase();
            
            String userType = request.getParameter("updatePermit" + this.getId());
            String atribBefore = request.getParameter("before");
            String atribAfter = request.getParameter("after");
            String propTimeUnit = request.getParameter("timeUnit");
            if (userType != null && !userType.isEmpty()) {
                base.setAttribute("editRole", userType);
            } else {
                base.removeAttribute("editRole");
            }
            if (atribBefore != null && !atribBefore.isEmpty()) {
                base.setAttribute("before", atribBefore);
            } else {
                base.removeAttribute("before");
            }
            if (atribAfter != null && !atribAfter.isEmpty()) {
                base.setAttribute("after", atribAfter);
            } else {
                base.removeAttribute("after");
            }
            if (propTimeUnit != null && !propTimeUnit.isEmpty()) { 
                base.setAttribute("unitTime", propTimeUnit);
            } else {
                base.removeAttribute("unitTime");
            }
            statusMsg = "msg_PermissionAssigned";
            listingRedirect = true;
            try {
                base.updateAttributesToDB();
            } catch (SWBException swbe) {
                DetailViewManager.log.error("Al asignar permisos", swbe);
            }
        } else if ("updateProp".equals(action)) {
            String objectUri = request.getParameter("suri");
            String propUri = request.getParameter("propUri");
            String propValue = request.getParameter("value");
            
            SemanticObject semanticObject = objectUri != null ? SemanticObject.getSemanticObject(objectUri) : null;
            SemanticProperty semProp = org.semanticwb.SWBPlatform.getSemanticMgr(
                    ).getVocabulary().getSemanticProperty(propUri);
            
            if (semanticObject != null && propUri != null && propValue != null) {
                String value = SWBUtils.XML.replaceXMLTags(propValue);
                semanticObject.setProperty(semProp, value);
            }
        } else {
            super.processAction(request, response);
        }

        if (statusMsg != null) {
            response.setRenderParameter("statusMsg", statusMsg);
        }
        if (errorMsg != null) {
            response.setRenderParameter("errorMsg", errorMsg);
        }
        
        if (listingRedirect) {
            response.setMode("showListing");
        } else if (formRedirect) {
            response.setMode("editTemplate");
        }
    }
    
    /**
     * Realiza validaciones a los datos de entrada para el despliegue de informaci&oacute;n
     * @param request petici&oacute;n HTTP realizada por el cliente
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @return un String que representa una llave del archivo de propiedades que corresponde
     *         al mensaje de error que describe el problema encontrado en los datos, null de lo contrario.
     */
    private String validateInput(HttpServletRequest request, SWBParamRequest paramRequest) {
        
        String suri = request.getParameter("suri");
        SemanticObject semObj = SemanticObject.getSemanticObject(suri);
        GenericObject genericObject = semObj != null ? semObj.createGenericInstance() : null;
        SemanticClass workClassSC = this.getWorkClass() != null
                ? this.getWorkClass().transformToSemanticClass()
                : null;
        String messageType = null;
        
        //Variable para evaluar al periodo especificado
        String modelName = paramRequest.getWebPage().getWebSiteId();
        String periodId = request.getSession().getAttribute(modelName) != null
                            ? (String) request.getSession().getAttribute(modelName)
                            : null;
        if (periodId == null) {
            periodId = request.getParameter(modelName) != null ? request.getParameter(modelName) : null;
        }
        
        //Revisa configuracion del recurso
        if (workClassSC == null) {
            messageType = "workClassNotConfigured";
        }

        //Revisa datos recibidos en request
        if (messageType == null && genericObject == null) {
            messageType = "uriNotValid";
        }
        if (messageType == null && (genericObject instanceof Objective && workClassSC != Objective.sclass)) {
            messageType = "objectTypeMissmatch";
        } else if (messageType == null && (genericObject instanceof Indicator && workClassSC != Indicator.sclass)) {
            messageType = "objectTypeMissmatch";
        } else if (messageType == null && (genericObject instanceof Initiative && workClassSC != Initiative.sclass)) {
            messageType = "objectTypeMissmatch";
        } else if (messageType == null && (genericObject instanceof Deliverable && workClassSC != Deliverable.sclass)) {
            messageType = "objectTypeMissmatch";
        } else if (messageType == null && (genericObject instanceof Perspective && workClassSC != Perspective.sclass)) {
            messageType = "objectTypeMissmatch";
        } else if (messageType == null && (genericObject instanceof Theme && workClassSC != Theme.sclass)) {
            messageType = "objectTypeMissmatch";
        }

        //Revisa vista asignada como contenido
        if (messageType == null && this.getActiveDetailView() == null) {
            messageType = "withNoActiveView";
        }
        /*
        if (messageType == null && this.getActiveDetailView() != null &&
                this.getActiveDetailView().getViewFilePath() == null) {
            messageType = "activeViewWithNoFile";
        }*/
        if (messageType == null && this.getActiveDetailView() != null &&
                this.getActiveDetailView().getViewFilePath() != null) {
            //Revisar que al menos exista un archivo con la ruta y nombre almacenados en this.getActiveDetailView().getViewFilePath()
            File templateFile = new File(SWBPortal.getWorkPath() + 
                    this.getActiveDetailView().getWorkPath() +
                    DetailViewManager.TEMPLATE_FILENAME);
            //si no es el caso, asignar el tipo de mensaje:
            if (!templateFile.exists()) {
                messageType = "activeViewWithNoFile";
            }
        }

        //Revisa existencia de un periodo con el identificador recibido en la sesion
        //Si no existe el periodo, asignar el tipo de mensaje a "periodNotExistent"
        if (periodId != null && !Period.ClassMgr.hasPeriod(periodId, paramRequest.getWebPage().getWebSite())) {
            messageType = "periodNotExistent";
        }

        return messageType;
    }
    
    /**
     * Lee el archivo asociado a la vista detalle asignada como contenido.
     * @return un objeto {@code FileReader} con el contenido del archivo asociado a la vista detalle asignada como contenido.
     */
    private FileReader retrieveTemplate() {
        
        String filePath = SWBPortal.getWorkPath() + 
                    this.getActiveDetailView().getWorkPath() + DetailViewManager.TEMPLATE_FILENAME;
        FileReader reader = null;
        try {
            reader = new FileReader(filePath);
        } catch (FileNotFoundException fnfe) {
            DetailViewManager.log.error("Al leer plantilla de vista detalle con Id: "
                    + this.getActiveDetailView().getId(), fnfe);
        }
        return reader;
    }
    
    /**
     * Interpreta el contenido de la plantilla de la vista detalle asignada como contenido, 
     * sustituyendo los tags que representan las propiedades de los objetos, por los despliegues
     * de valores de esas propiedades.
     * @param request petici&oacute;n HTTP realizada por el cliente
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @param template el contenido de la vista detalle asignada como contenido
     * @param elementBSC representa el objeto del cual se desea extraer la informaci&oacute;n
     * @return un {@code String} que representa el contenido de la plantilla de la vista detalle
     *         correspondiente con el despliegue de los valores de las propiedades configuradas.
     * @throws IOException en caso de que se presente alg&uacute;n problema en el parseo del contenido de la plantilla
     */
    private String generateDisplay(HttpServletRequest request, SWBParamRequest paramRequest,
            FileReader template, SemanticObject elementBSC, final UserGroup collaboration) throws IOException {
        
        StringBuilder view = new StringBuilder(256);
        HtmlStreamTokenizer tok = new HtmlStreamTokenizer(template);
        HtmlTag tag = new HtmlTag();
        String lang = paramRequest.getUser().getLanguage();
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction("updateProp");
        url.setParameter("suri", request.getParameter("suri"));
        request.setAttribute("urlRequest", url.toString());

        String modelName = paramRequest.getWebPage().getWebSiteId();
        String periodId = request.getSession().getAttribute(modelName) != null
                            ? (String) request.getSession().getAttribute(modelName)
                            : null;
        Period period = Period.ClassMgr.getPeriod(periodId, paramRequest.getWebPage().getWebSite());
        
        while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
            int ttype = tok.getTokenType();

            if (ttype == HtmlStreamTokenizer.TT_TAG) {

                try {
                    //si no es un tag de imagen, que continue con el siguiente
                    tok.parseTag(tok.getStringValue(), tag);
                } catch (HtmlException htmle) {
                    DetailViewManager.log.error("Al parsear plantilla vista detalle, " + 
                            this.getActiveDetailView().getId(), htmle);
                    view = new StringBuilder(16);
                }
                if (!tag.getTagString().toLowerCase().equals("img")) {
                    view.append(tag.toString());
                } else if (tag.getTagString().toLowerCase().equals("img") && !tag.hasParam("tagProp")) {
                    view.append(tag.toString());
                }  else if (tag.getTagString().toLowerCase().equals("img") && tag.hasParam("tagProp")) {
                /*
                 Si es un tag de imagen y tiene el atributo tagProp
                 obtener el valor del atributo tagProp que contiene el uri de la propiedad
                 */
                    String propUri = tag.getParam("tagProp");
                    if (propUri != null) {
                        view.append(renderPropertyValue(request, elementBSC, propUri, lang, period, collaboration));
                    }
                }
            } else if (ttype == HtmlStreamTokenizer.TT_TEXT) {
                view.append(tok.getStringValue());
            } else if (ttype == HtmlStreamTokenizer.TT_COMMENT) {
                view.append("<!--" + tok.getStringValue() + "-->");
            }
            view.append("\n");
        }

        return view.toString();
    }
    
    /**
     * Devuelve el despliegue correspondiente al valor de la propiedad especificada, del objeto indicado.
     * @param request petici&oacute;n HTTP realizada por el cliente
     * @param elementBSC representa el objeto del cual se desea extraer la informaci&oacute;n
     * @param propUri representa la uri de la propiedad semantica de la que se desea obtener su valor
     * @param lang representa el lenguaje en que se desea mostrar el valor de la propiedad indicada
     * @return el despliegue del valor almacenado para la propiedad indicada
     */
    private String renderPropertyValue(HttpServletRequest request, SemanticObject elementBSC,
            String propUri, String lang, Period period, final UserGroup collaboration) {
        
        String ret = null;
        SWBFormMgr formMgr = new SWBFormMgr(elementBSC, null, SWBFormMgr.MODE_VIEW);
        SemanticProperty semProp = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(propUri);
        
        //Codigo para obtener el displayElement
        Statement st = semProp.getRDFProperty().getProperty(
                SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(
                "http://www.semanticwebbuilder.org/swb4/bsc#displayElement"));
        if (st != null) {
            //Se obtiene: SemanticProperty: displayElement de la propiedad en cuestion (prop)
            SemanticObject soDisplayElement = SemanticObject.createSemanticObject(st.getResource());
            if (soDisplayElement != null) {
                SemanticObject formElement = soDisplayElement.getObjectProperty(
                        org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(
                        "http://www.semanticwebbuilder.org/swb4/xforms/ontology#formElement"));
                if (formElement != null) {
                    FormElement fe = (FormElement) formElement.createGenericInstance();
                    boolean applyInlineEdit = false;
                    if( (userCanEdit()&&isInMeasurementTime(period)&&isEditable(formElement)) || (userCanCollaborate(collaboration)&&isEditable(formElement)) ) {
                        applyInlineEdit = true;
                    }
                    
                    if (fe != null) {
                        if (formMgr.getSemanticObject() != null) {
                            fe.setModel(formMgr.getSemanticObject().getModel());
                        }
                        ret = fe.renderElement(request, elementBSC, semProp, semProp.getName(),
                                SWBFormMgr.TYPE_XHTML,
                                applyInlineEdit ? "inlineEdit" : SWBFormMgr.MODE_VIEW,
                                lang);
                    }
                }
            }
        }
        
        if (ret == null) {
            FormElement formElement = formMgr.getFormElement(semProp);
            if (formElement != null) {
                ret = formElement.renderElement(request, elementBSC, semProp, semProp.getName(),
                        SWBFormMgr.TYPE_XHTML, SWBFormMgr.MODE_VIEW, lang);
            }
        }
        return ret != null ? ret : "";
    }
    
    /**
     * Verifica la existencia de un valor para el displayElement de una propiedad en la ontolog&iacute;a
     * @param property la propiedad sem&aacute;ntica de la que se requiere el displayElement
     * @return {@literal true} si existe un valor para el displayElement en la ontolog&iacute;a, {@literal false} de lo contrario
     */
    private boolean displayElementExists(SemanticProperty property) {
        
        boolean exists = false;
        
        Statement st = property.getRDFProperty().getProperty(
                SWBPlatform.getSemanticMgr().getSchema().getRDFOntModel().getProperty(
                "http://www.semanticwebbuilder.org/swb4/bsc#displayElement"));
        if (st != null) {
            exists = true;
        }
        return exists;
    }
    
    /**
     * Determina si el usuario en sesi&oacute;n tiene los permisos necesarios para hacer 
     * actualizaciones en la informaci&oacute;n del elemento presentado en la vista detalle.
     * @return un boleano que representa la posibilidad del usuario de hacer actualizaciones ({@code true}),
     *          de lo contrario devuelve {@code false}.
     */
    private boolean userCanEdit() {
        boolean access = false;
        String str_role = getResourceBase().getAttribute("editRole", null);
        final User user = SWBContext.getSessionUser();
        
        if(user != null)
        {
            if(str_role != null)
            {
                SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
                GenericObject gobj = null;
                try {
                    gobj = ont.getGenericObject(str_role);
                } catch (Exception e) {
                    DetailViewManager.log.error("Error InlineEdit.userCanEdit()", e);
                    return Boolean.FALSE;
                }

                UserGroup ugrp = null;
                Role urole = null;

                if (gobj != null) {
                    if (gobj instanceof UserGroup) {
                        ugrp = (UserGroup) gobj;
                        if (user.hasUserGroup(ugrp)) {
                            access = true;
                        }
                    } else if (gobj instanceof Role) {
                        urole = (Role) gobj;
                        if (user.hasRole(urole)) {
                            access = true;
                        }
                    }
                } else {
                    access = false;
                }
            }
        }
        return access;
    }
    
    /**
     * Determina si la fecha actual pertenece al periodo de captura permitido para 
     * el periodo indicado como argumento
     * @param period periodo a evaluar contra la fecha actual
     * @return un boleano que representa la validez de la fecha actual contra el periodo
     *          de captura para el periodo indicado {@code true}, o {@code false} de lo contrario.
     */
    private boolean isInMeasurementTime(final Period period) {
        Resource base = getResourceBase();
        int timeBefore = 0;
        int timeAfter = 0;
        int timeUnit = 0;
        try {
            timeBefore = Integer.parseInt(base.getAttribute("before", "7"));
        } catch (NumberFormatException e) {
            timeBefore = 0;
        }
        try {
            timeAfter = Integer.parseInt(base.getAttribute("after", "7"));
        } catch (NumberFormatException e) {
            timeAfter = 0;
        }
        
        try {
            timeUnit = Integer.parseInt(base.getAttribute("unitTime",
                    Integer.toString(Calendar.DATE)));
        } catch (NumberFormatException nfe) {
            timeUnit = Calendar.DATE;
        }
        
        GregorianCalendar current = new GregorianCalendar();
        Date end = period.getEnd();
        if (end == null) {
            return false;
        }
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.setTime(end);
        startDate.add(timeUnit, -timeBefore);
        GregorianCalendar endDate = new GregorianCalendar();
        endDate.setTime(end);
        endDate.add(timeUnit, timeAfter);
        return current.compareTo(startDate) >= 0 && current.compareTo(endDate) <= 0;
    }
    
    private boolean isEditable(SemanticObject formElement) {        
        return formElement.getProperty(TextAreaElement.bsc_editable)==null?false:formElement.getBooleanProperty(TextAreaElement.bsc_editable);
    }
    
    private boolean userCanCollaborate(final UserGroup collaboration) {
        final User user = SWBContext.getSessionUser();
        return collaboration.hasUser(user);
    }
}
