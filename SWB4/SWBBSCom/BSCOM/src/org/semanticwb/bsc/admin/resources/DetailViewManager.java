package org.semanticwb.bsc.admin.resources;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.element.BSCElement;
import org.semanticwb.bsc.utils.DetailView;
import org.semanticwb.model.SWBComparator;
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

        if (bsc != null && objectsType != null) {
            SWBResourceURL urlCreate = paramRequest.getRenderUrl();
            urlCreate.setParameter("operation", "add");
            urlCreate.setMode("editTemplate");

            //Se genera el código HTML del encabezado de la tabla que muestra el listado de vistas
            listCode.append("<div class=\"swbform\">\n");
            listCode.append("  <fieldset>\n");
            listCode.append("    <legend>" + paramRequest.getLocaleString("lbl_objectsType") + workClassSC.getName() + "</legend>");
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
        if ((statusMsg != null && !statusMsg.isEmpty()) || (statusErr != null && !statusErr.isEmpty())) {
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
            formMgr = new SWBFormMgr(DetailView.bsc_DetailView, viewSemObject.getSemanticObject(), modeUsed);
            url.setAction("editView");
            if (viewSemObject.getViewFilePath() != null) {
                //String resourceWorkPath = SWBPortal.getWorkPath() + viewSemObject.getWorkPath() + "/templateContent.html";
                templateContent = SWBUtils.IO.getFileFromPath(viewSemObject.getViewFilePath());                
            }
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
        output.append("                <input type=\"hidden\" id=\"urlForProperties\" name=\"urlForProperties\" value=\"" + propertiesUrl + "\">\n");
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
        output.append("                <td align=\"left\" width=\"650px\" height=\"350px\">\n");
        output.append("                  <textarea name=\"FCKeditorDetailView");
        output.append(this.getId());
        output.append("\" id=\"FCKeditorDetailView");
        output.append(this.getId());
        output.append("\" style=\"width:500px; height:450px;\" cols=\"50\" rows=\"25\">");
        output.append(templateContent);
        output.append("</textarea>\n");
        output.append("");
        output.append("<div dojoType=\"dojox.layout.ContentPane\">\n");
        output.append("    <script type=\"dojo/method\">\n");
        output.append("      var oFCKeditor = new FCKeditor( 'FCKeditorDetailView" + this.getId() + "' ) ;\n");
        output.append("      oFCKeditor.BasePath = '" + SWBPlatform.getContextPath() + "/swbadmin/js/fckeditor/';\n");
        output.append("      oFCKeditor.Config['CustomConfigurationsPath'] = " + SWBPlatform.getContextPath() + "'/swbadmin/js/fckeditor/fckconfig_inserter.js';\n");
        output.append("      oFCKeditor.ReplaceTextarea();\n");
        output.append("      oFCKeditor.urlInsertHtmlData = '" + SWBUtils.TEXT.scape4Script(propertiesUrl) + "' ;\n");
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
        output.append("        var oEditor = FCKeditorAPI.GetInstance('FCKeditorDetailView" + this.getId() + "') ;\n");
        output.append("        document.getElementById('FCKeditorDetailView" + this.getId() + "').value = oEditor.GetData();\n");
        output.append("        submitForm(\"detailViewForm" + this.getId() + "\");\n");
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
        out.println("Hello DetailViewManager...");
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
        Iterator<SemanticProperty> basePropertiesList = semWorkClass.listSortProperties();
        JSONArray array = new JSONArray();
        String structure = null;

        if (semWorkClass != null) {
            try {
                while (basePropertiesList.hasNext()) {
                    SemanticProperty prop = basePropertiesList.next();
                    //Se crea una estructura de JSON con los datos de las propiedades de la clase en la variable array
                    JSONObject object = new JSONObject();
                    object.put("name", prop.getName());
                    object.put("label", prop.getDisplayName());
                    object.put("uri", prop.getURI());
                    array.put(object);
                }
                structure = array.toString(2);
            } catch (JSONException jsone) {
                structure = "";
                DetailViewManager.log.error("Al generar estructura de JSON con propiedades semánticas", jsone);
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
     * Realiza las operaciones de almacenamiento o eliminación de información de las 
     * vistas detalle administradas, asi como la asignación de una vista detalle seleccionada
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
                        viewFilePath += "/templateContent.html";
                        File file = new File(viewFilePath);
                        if (!file.exists()) {
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
    
}
