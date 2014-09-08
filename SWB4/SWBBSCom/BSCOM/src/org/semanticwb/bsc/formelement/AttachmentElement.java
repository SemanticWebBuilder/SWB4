package org.semanticwb.bsc.formelement;

import java.net.URLDecoder;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.Attachmentable;
import org.semanticwb.bsc.catalogs.Attachment;
import org.semanticwb.model.FormElementURL;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;

/**
 * Form Element que presentar&aacute; la vista para administrar archivos
 * adjuntos (Creaci&oacute;n, Edici&oacute;n y Eliminaci&oacute;n)
 *
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class AttachmentElement extends org.semanticwb.bsc.formelement.base.AttachmentElementBase {

    /**
     * Realiza operaciones en la bitacora de eventos.
     */
    private static Logger log = SWBUtils.getLogger(AttachmentElement.class);
    /**
     * La constante Mode_VIEW.
     */
    private static final String Mode_VIEW = "view";
    /**
     * La constante Mode_EDIT.
     */
    private static final String Mode_EDIT = "edit";
    /**
     * La constante Mode_ADD.
     */
    private static final String Mode_ADD = "add";
    /**
     * La constante Mode_RELOAD.
     */
    private static final String Mode_RELOAD = "reload";
    /**
     * La constante Action_ADD.
     */
    private static final String Action_ADD = "aAdd";
    /**
     * La constante Action_EDIT.
     */
    private static final String Action_EDIT = "aEdit";
    /**
     * La constante Action_REMOVE.
     */
    private static final String Action_REMOVE = "aDelete";

    /**
     * Crea instancias de esta clase a partir de un objeto sem&aacute;ntico
     *
     * @param base el objeto sem&aacute;ntico a utilizar para crear la nueva
     * instancia
     */
    public AttachmentElement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Genera el c&oacute;digo HTML para representar la administraci&oacute;n de
     * los Archivos adjuntos de los elementos del BSC.
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son:
     * {@literal edit}, {@literal create}, {@literal filter} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {
        StringBuilder toReturn = new StringBuilder();
        String modeTmp = request.getParameter("modeTmp");
        String downloadEle = request.getAttribute("downloadEle") != null
                ? request.getAttribute("downloadEle").toString() : null;
        if (modeTmp == null && mode.equals(SWBFormMgr.MODE_VIEW) && downloadEle == null) {
            toReturn.append(renderModeView(request, obj, prop, propName, type, mode, lang));
        } else if (modeTmp == null || (modeTmp != null && Mode_VIEW.equals(modeTmp))) {
            toReturn.append(renderElementView(request, obj, prop, propName, type, mode, lang));
        } else if (Mode_ADD.equals(modeTmp)) {
            toReturn.append(renderElementAdd(request, obj, prop, propName, type, mode, lang));
        } else if (Mode_EDIT.equals(modeTmp)) {
            toReturn.append(renderElementEdit(request, obj, prop, propName, type, mode, lang));
        } else if (Mode_RELOAD.equals(modeTmp)) {
            toReturn.append(renderElementReload(request, obj, prop, type, mode, lang));
        }
        return toReturn.toString();
    }

    /**
     * Procesa la informaci&oacute;n enviada por el elemento de forma, para
     * almacenarla en la propiedad del objeto indicado.
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     */
    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) {
        final String action = request.getParameter("_action");
        String usrWithGrants = request.getParameter("usrWithGrants");
        WebSite scorecard = (WebSite) obj.getModel().getModelObject().getGenericInstance();
        final User user = SWBContext.getSessionUser(scorecard.getUserRepository().getId());

        if (Action_ADD.equals(action)) {
            SWBFormMgr mgr = new SWBFormMgr(Attachment.sclass, scorecard.getSemanticObject(), null);
            String sref = request.getParameter("sref");
            SemanticObject semObjRef = SemanticObject.createSemanticObject(sref);
            Attachmentable attachmentable = null;
            if (mgr != null && (semObjRef != null) && semObjRef.createGenericInstance() instanceof Attachmentable) {
                attachmentable = (Attachmentable) semObjRef.createGenericInstance();
                mgr.setFilterRequired(false);
                try {
                    SemanticObject sobj = mgr.processForm(request);
                    Attachment attach = (Attachment) sobj.createGenericInstance();
                    attach.setCreator(user);
                    attachmentable.addAttachments(attach);
                } catch (FormValidateException ex) {
                    log.error(ex);
                }
            }
        } else if (Action_EDIT.equals(action)) {
            String suri = request.getParameter("suri");
            SemanticObject semObj = SemanticObject.getSemanticObject(suri);
            if (semObj != null) {
                SWBFormMgr mgr = new SWBFormMgr(semObj, null, SWBFormMgr.MODE_EDIT);
                try {
                    mgr.processForm(request);
                } catch (FormValidateException ex) {
                    log.error("Error in: " + ex);
                }
            }
        } else if (Action_REMOVE.equals(action)) {
            String suri = request.getParameter("suriAttach");
            String sobj = request.getParameter("obj");
            SemanticObject semObj = SemanticObject.getSemanticObject(suri);
            SemanticObject semObjAtta = SemanticObject.getSemanticObject(sobj);
            if (semObjAtta.createGenericInstance() instanceof Attachmentable) {
                Attachmentable objectAttach = (Attachmentable) semObjAtta.createGenericInstance();
                Attachment attachment = (Attachment) semObj.createGenericInstance();
                objectAttach.removeAttachments(attachment);
                attachment.remove();
            }
            renderElementReload(request, obj, prop, FormElementURL.URLTYPE_RENDER, null, "es");
        }
        getRenderURL(obj, prop, FormElementURL.URLTYPE_RENDER, null, "es").
                setParameter("usrWithGrants", usrWithGrants);
    }

    /**
     * Genera el c&oacute;digo HTML para representar la edici&oacute;n de un
     * archivo adjunto.
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son:
     * {@literal edit}, {@literal create}, {@literal filter} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     */
    public String renderElementEdit(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {
        StringBuilder toReturn = new StringBuilder();
        String usrWithGrants = (String) request.getAttribute("usrWithGrants");

        SemanticObject semObject = SemanticObject.createSemanticObject(URLDecoder.
                decode(request.getParameter("suriAttach")));
        SWBFormMgr formMgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        formMgr.clearProperties();
        formMgr.addProperty(Attachment.swb_title);
        formMgr.addProperty(Attachment.swb_description);
        formMgr.addProperty(Attachment.bsc_file);
        formMgr.setType(SWBFormMgr.TYPE_XHTML);
        formMgr.setSubmitByAjax(true);
        formMgr.setMode(SWBFormMgr.MODE_EDIT);

        FormElementURL urlp = getProcessURL(obj, prop);
        urlp.setParameter("_action", Action_EDIT);
        urlp.setParameter("usrWithGrants", usrWithGrants);
        String url = urlp.toString();
        String onsubmit = " onsubmit=\"if(validateData(this.id, 'edit')){mySubmitForm(this.id)}\"";
        toReturn.append("<div id=\"frmAdd\">");
        toReturn.append("<form id=\"");
        toReturn.append(formMgr.getFormName());
        toReturn.append("\" class=\"form-horizontal\" action=\"");
        toReturn.append(url);
        toReturn.append("\"");
        toReturn.append(onsubmit);
        toReturn.append(" method=\"post\">\n");
        toReturn.append(formMgr.getFormHiddens());

        Iterator<SemanticProperty> it = SWBComparator.sortSortableObject(formMgr.
                getProperties().iterator());
        while (it.hasNext()) {
            SemanticProperty prop1 = it.next();
            toReturn.append("<div class=\"form-group\">");
            String reqtxt = " &nbsp;";
            if (prop1.isRequired()) {
                reqtxt = " <em>*</em>";
            }
            toReturn.append("<label for=\"");
            toReturn.append(prop1.getName());
            toReturn.append("\" class=\"col-lg-2 control-label\">");
            toReturn.append(prop1.getDisplayName(lang));
            toReturn.append(reqtxt);
            toReturn.append("</label>");

            toReturn.append("<div class=\"col-lg-10\">");
            toReturn.append(
                    formMgr.getFormElement(prop1).renderElement(
                    request, semObject, prop1,
                    prop1.getName(), "XHTML", SWBFormMgr.MODE_EDIT, lang));
            toReturn.append("</div>");
            toReturn.append("</div>");
        }
        toReturn.append("<div class=\"btn-group col-lg-12 col-md-12 pull-right\">");
        toReturn.append("          <button dojoType=\"dijit.form.Button\" class=\"pull-right swb-boton-cancelar\" ");
        toReturn.append("onclick=\"dijit.byId('swbDialog').hide()\">");
        toReturn.append(getLocaleString("cancel", lang));
        toReturn.append("</button>");
        toReturn.append("          <button dojoType=\"dijit.form.Button\" class=\"pull-right swb-boton-enviar\" type=\"submit\" ");
        toReturn.append("name=\"enviar\" >");
        toReturn.append(getLocaleString("send", lang));
        toReturn.append("</button>");
        toReturn.append("</div>");
        toReturn.append("</form>\n");
        toReturn.append("</div>");
        return toReturn.toString();
    }

    /**
     * Presenta la vista de los archivos adjuntos asociados al elemento BSC,
     * aqu&iacute; se getiona los permisos para los usuarios
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son:
     * {@literal edit}, {@literal create}, {@literal filter} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     */
    public String renderElementView(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {
        StringBuilder toReturn = new StringBuilder();
        String suri = (String) request.getParameter("suri");

        String usrWithGrants = (String) request.getAttribute("usrWithGrants") == null
                ? (String) request.getParameter("usrWithGrants")
                : (String) request.getAttribute("usrWithGrants");

        if (suri != null) {
            SemanticObject semObj = SemanticObject.getSemanticObject(URLDecoder.decode(suri));
            Attachmentable element = null;
            if (semObj != null && semObj.createGenericInstance() instanceof Attachmentable) {
                element = (Attachmentable) semObj.createGenericInstance();
                Iterator<Attachment> itAttachments = element.listAttachmentses();
                FormElementURL urlAddFE = getRenderURL(obj, prop, type, mode, lang);
                urlAddFE.setParameter("modeTmp", Mode_ADD);
                urlAddFE.setParameter("usrWithGrants", usrWithGrants);
                FormElementURL urlFE = getRenderURL(obj, prop, type, mode, lang);
                urlFE.setParameter("modeTmp", Mode_RELOAD);
                urlFE.setParameter("suri", suri);
                urlFE.setParameter("usrWithGrants", usrWithGrants);
                toReturn.append("\n<script type=\"text/javascript\">");
                toReturn.append("\n  dojo.require(\"dijit.Dialog\");");
                toReturn.append("\n  dojo.require(\"dijit.form.TextBox\");");
                toReturn.append("\n  dojo.require(\"dijit.form.ValidationTextBox\");");
                toReturn.append("\n  dojo.require(\"dijit.form.Button\");");
                toReturn.append("\n  dojo.require(\"dojo.parser\");");
                toReturn.append("\n  dojo.require(\"dojox.layout.ContentPane\");");
                toReturn.append("\n  dojo.require(\"dijit.form.CheckBox\");");

                toReturn.append("\n  function mySubmitForm(formid){");
                toReturn.append("\n     var obj = dojo.byId(formid);");
                toReturn.append("\n     var objd = dijit.byId(formid);");
                toReturn.append("\n     var fid = formid;");
                toReturn.append("\n     if (!obj && objd) {//si la forma esta dentro de un dialog");
                toReturn.append("\n         obj = objd.domNode;");
                toReturn.append("\n         fid = obj;");
                toReturn.append("\n      }");
                toReturn.append("\n      if (!objd || objd.isValid()) {");
                toReturn.append("\n         try { ");
                toReturn.append("\n             dojo.fx.wipeOut({node: formid, duration: 500})");
                toReturn.append(".play();");
                toReturn.append("\n         } catch (noe) {}");
                toReturn.append("\n         try {");
                toReturn.append("\n             var framesNames = \"\";");
                toReturn.append("\n             for (var i = 0; i < window.frames.length; i++) {");
                toReturn.append("\n                 try { ");
                toReturn.append("\n                     framesNames += window.frames[i].name;");
                toReturn.append("\n                     if (framesNames && framesNames.");
                toReturn.append("indexOf(\"_editArea\") != -1) {");
                toReturn.append("\n                         area = window.frames[framesNames].");
                toReturn.append("editArea;");
                toReturn.append("\n                         id = framesNames.substr(6);");
                toReturn.append("\n                         document.getElementById(id).value = ");
                toReturn.append("area.textarea.value;");
                toReturn.append("\n                     }");
                toReturn.append("\n                 }catch(e){}");
                toReturn.append("\n             }");
                toReturn.append("\n         } catch (ex) {}");
                toReturn.append("\n         dojo.xhrPost({");
                toReturn.append("\n            contentType: \"application/x-www-form-urlencoded; ");
                toReturn.append("charset=utf-8\",");
                toReturn.append("\n             url: obj.action,");
                toReturn.append("\n             form: fid,");
                toReturn.append("\n             load: function(data) {");
                toReturn.append("\n                 dijit.byId('swbDialog').hide();");
                toReturn.append("\n                 processUrl('");
                toReturn.append(urlFE.setContentType("text/html; charset=UTF-8"));
                toReturn.append("', 'swbform');");
                toReturn.append("\n                 dijit.byId('swbDialog2').show();");
                toReturn.append("\n             },");
                toReturn.append("\n             error: function(error) {");
                toReturn.append("\n                 alert('Error: ', error);");
                toReturn.append("\n             }");
                toReturn.append("\n         });");
                toReturn.append("\n     } else {");
                toReturn.append("\n         alert(\"");
                toReturn.append(getLocaleString("invalidData", lang));
                toReturn.append("\");");
                toReturn.append("\n     }");
                toReturn.append("\n  }");

                toReturn.append("\nfunction processUrl(url,id)");
                toReturn.append("\n{");
                toReturn.append("\n    dojo.xhrPost({");
                toReturn.append("\n        url: url,");
                toReturn.append("\n        load: function(response)");
                toReturn.append("\n        {");
                toReturn.append("\n             if(id != null){");
                toReturn.append("\n               document.getElementById(id).innerHTML ");
                toReturn.append("= response;");
                toReturn.append("\n             } else {");
                toReturn.append("\n               processUrl('");
                toReturn.append(urlFE.setContentType("text/html; charset=UTF-8"));
                toReturn.append("', 'swbform');");
                toReturn.append("\n                 dijit.byId('swbDialog2').show();");
                toReturn.append("\n             }");
                toReturn.append("\n        },");
                toReturn.append("\n        error: function(response)");
                toReturn.append("\n        {");
                toReturn.append("\n            return response;");
                toReturn.append("\n        },");
                toReturn.append("\n        handleAs: \"text\"");
                toReturn.append("\n    });");
                toReturn.append("\n}");

                String valueStr = Attachment.swb_title.getDisplayName(lang) == null
                        ? Attachment.swb_title.getDisplayName()
                        : Attachment.swb_title.getDisplayName(lang);
                String valueStr2 = Attachment.bsc_file.getDisplayName(lang) == null
                        ? Attachment.bsc_file.getDisplayName()
                        : Attachment.bsc_file.getDisplayName(lang);
                toReturn.append("\n    function validateData(form, action) {");
                toReturn.append("\n        var obj = dojo.byId(form);");
                toReturn.append("\n        if(obj.");
                toReturn.append(Attachment.swb_title.getName());
                toReturn.append(".value == ''){");
                toReturn.append("\n         alert('");
                toReturn.append(getLocaleString("lb_required", lang));
                toReturn.append(" ");
                toReturn.append(valueStr);
                toReturn.append("');");
                toReturn.append("\n         obj.");
                toReturn.append(Attachment.swb_title.getName());
                toReturn.append(".focus(); return false; }");
                toReturn.append("\n        if(dojo.byId('selectFile').innerText =='' && ");
                toReturn.append("action == 'add'){");
                toReturn.append("\n         alert('");
                toReturn.append(getLocaleString("lb_required", lang));
                toReturn.append(" ");
                toReturn.append(valueStr2);
                toReturn.append("');");
                toReturn.append("return false;}");
                toReturn.append("\n        return true;");
                toReturn.append("\n    }");
                toReturn.append("\n</script>");

                toReturn.append("\n<div dojoType=\"dijit.Dialog\" class=\"clsDialog col-lg-6 col-lg-offset-3 co-md-8 col-sm-8 col-sm-offset-2 col-xs-12 swb-ventana-dialogo \" ");//class=\"soria\"
                toReturn.append("id=\"swbDialog\" title=\"Agregar\" >\n");
                toReturn.append("\n<div class=\"panelDialog panelDialog-default\">");
                toReturn.append("\n<div class=\"swb-panel-cuerpo\">");
                toReturn.append("  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" ");
                toReturn.append("id=\"swbDialogImp\" executeScripts=\"true\">\n");
                toReturn.append("    Cargando...\n");
                toReturn.append("  </div>\n");
                toReturn.append("  </div>\n");
                toReturn.append("  </div>\n");
                toReturn.append("</div>\n");

                toReturn.append("\n<div dojoType=\"dijit.Dialog\" class=\"clsDialog2 col-lg-2 col-lg-offset-5 co-md-8 col-sm-8 col-sm-offset-2 col-xs-12 swb-ventana-dialogo\" "); //soria
                toReturn.append("id=\"swbDialog2\" title=\"");
                toReturn.append(getLocaleString("successfulOperation", lang));
                toReturn.append("");
                toReturn.append("\" >\n");
                toReturn.append("\n<div class=\"panelDialog panelDialog-default\">");
                toReturn.append("\n<div class=\"swb-panel-cuerpo\">");
                toReturn.append("  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" ");
                toReturn.append("id=\"swbDialogImp2\" executeScripts=\"true\">\n");
                toReturn.append("          <p class=\"text-center bold\"><strong>");
                toReturn.append(getLocaleString("successfulOperation", lang));
                toReturn.append("</strong></p>\n");
                toReturn.append("<div class=\"btn-group col-lg-2 col-lg-offset-3 col-md-12 \">");
                toReturn.append("<button dojoType=\"dijit.form.Button\" class=\"swb-boton-enviar\" ");//btn btn-default btn-lg btn-block 
                toReturn.append("onclick=\"dijit.byId('swbDialog2').hide()\">");
                toReturn.append(getLocaleString("success", lang));
                toReturn.append("</button>");
                toReturn.append("  </div>\n");

                toReturn.append("  </div>\n");
                toReturn.append("  </div>\n");
                toReturn.append("  </div>\n");
                toReturn.append("</div>\n");

                if ("true".equals(usrWithGrants)) {
                    toReturn.append("<a href=\"#\" class=\"swb-url-lista detalle-archivos\" onclick=\"showDialog('");
                    toReturn.append(urlAddFE.setContentType("text/html; charset=UTF-8"));
                    toReturn.append("', '");
                    toReturn.append(Attachment.sclass.getDisplayName(lang));
                    toReturn.append("');\">");
                    toReturn.append(getLocaleString("add", lang));
                    toReturn.append(" ");
                    toReturn.append(Attachment.sclass.getDisplayName(lang));
                    toReturn.append("\n</a>");                    
                }
                toReturn.append("<br/>");
                toReturn.append("\n<div class=\"table-responsive\" id=\"swbform\">");
                if (itAttachments.hasNext()) {
                    toReturn.append(listAttachment(itAttachments, suri, obj, prop, type,
                            mode, lang, usrWithGrants));
                }
                toReturn.append("\n</div>");
            }
        }
        return toReturn.toString();
    }

    /**
     * Genera el c&oacute;digo HTML para representar la vista para agregar un
     * nuevo archivo adjunto
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son:
     * {@literal edit}, {@literal create}, {@literal filter} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     */
    public String renderElementAdd(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {
        StringBuilder toReturn = new StringBuilder();
        SWBFormMgr formMgr = new SWBFormMgr(Attachment.bsc_Attachment,
                obj, null);
        formMgr.clearProperties();
        formMgr.addProperty(Attachment.swb_title);
        formMgr.addProperty(Attachment.swb_description);
        formMgr.addProperty(Attachment.bsc_file);
        formMgr.setType(SWBFormMgr.TYPE_DOJO);
        formMgr.setSubmitByAjax(true);
        formMgr.setMode(SWBFormMgr.MODE_CREATE);

        String url = getProcessURL(obj, prop).setParameter("_action", Action_ADD).toString();
        formMgr.setAction(url);
        toReturn.append("<div id=\"frmAdd\" >");
        
        SemanticObject semObj = new SemanticObject(obj.getModel(), Attachment.bsc_Attachment);
        String onsubmit = " onsubmit=\"if(validateData(this.id, 'add')){mySubmitForm(this.id);} else {return false;}\"";
        toReturn.append("<form id=\"");
        toReturn.append(formMgr.getFormName());
        toReturn.append("\" class=\"form-horizontal\" action=\"");
        toReturn.append(url);
        toReturn.append("\"");
        toReturn.append(onsubmit);
        toReturn.append(" method=\"post\" dojoType=\"dijit.form.Form\">\n");
        toReturn.append(formMgr.getFormHiddens());

        Iterator<SemanticProperty> it = SWBComparator.sortSortableObject(formMgr.
                getProperties().iterator());
        while (it.hasNext()) {
            SemanticProperty prop1 = it.next();
            toReturn.append("<div class=\"form-group\">");
            String reqtxt = " &nbsp;";
            if (prop1.isRequired()) {
                reqtxt = " <em>*</em>";
            }
            toReturn.append("<label for=\"");
            toReturn.append(prop1.getName());
            toReturn.append("\" class=\"col-lg-2 control-label\">");
            toReturn.append(prop1.getDisplayName(lang));
            toReturn.append(reqtxt);
            toReturn.append("</label>");

            toReturn.append("<div class=\"col-lg-10\">");
            toReturn.append(
                    formMgr.getFormElement(prop1).renderElement(
                    request, semObj, prop1,
                    prop1.getName(), "XHTML", SWBFormMgr.MODE_CREATE, lang));
            toReturn.append("</div>");
            toReturn.append("</div>");
        }
        
        toReturn.append("<div class=\"btn-group col-lg-12 col-md-12 pull-right\">");
        toReturn.append("          <button dojoType=\"dijit.form.Button\" class=\"pull-right swb-boton-cancelar\" ");
        toReturn.append("onclick=\"dijit.byId('swbDialog').hide()\">");
        toReturn.append(getLocaleString("cancel", lang));
        toReturn.append("</button>");
        
        toReturn.append("          <button dojoType=\"dijit.form.Button\" type=\"submit\" class=\"pull-right swb-boton-enviar\" ");//btn btn-default pull-right swb-boton-enviar
        toReturn.append("name=\"enviar\" >");
        toReturn.append(getLocaleString("send", lang));
        toReturn.append("</button>");

        toReturn.append("</div>");
        toReturn.append("</form>");
        toReturn.append("</div>");
        return toReturn.toString();
    }

    /**
     * Vista utilizada para recargar la vista despu&eacute;s de ejecutar una
     * acci&oacute;n, tal como Agregar, Editar o Eliminar
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son:
     * {@literal edit}, {@literal create}, {@literal filter} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     */
    private String renderElementReload(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String type, String mode, String lang) {
        StringBuilder toReturn = new StringBuilder();
        String suri = (String) request.getParameter("suri");
        String usrWithGrants = (String) request.getAttribute("usrWithGrants") == null
                ? (String) request.getParameter("usrWithGrants")
                : (String) request.getAttribute("usrWithGrants");
        if (suri != null) {
            SemanticObject semObj = SemanticObject.getSemanticObject(URLDecoder.decode(suri));
            Attachmentable element = null;
            if (semObj != null && semObj.createGenericInstance() instanceof Attachmentable) {
                element = (Attachmentable) semObj.createGenericInstance();
                Iterator<Attachment> itAttachments = element.listAttachmentses();
                toReturn.append(listAttachment(itAttachments, suri, obj, prop, type, mode,
                        lang, usrWithGrants));
            }
        }
        return toReturn.toString();
    }

    /**
     * Lista en HTML los archivos adjuntos.
     *
     * @param itAttachments el iterador con los archivos adjuntos
     * @param suri el objeto que contiene el formElement
     * @param obj la propiedad asociada a este FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son:
     * {@literal edit}, {@literal create}, {@literal filter} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @param usrWithGrants define si existen permisos para editar o eliminar
     * los archivos adjuntos.
     * @return el objeto String que representa el c&oacute;digo HTML con el
     * conjunto de archivos adjuntos.
     */
    private String listAttachment(Iterator<Attachment> itAttachments, String suri,
            SemanticObject obj, SemanticProperty prop, String type, String mode, String lang,
            String usrWithGrants) {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("\n<table class=\"tabla-detalle table\">");
        itAttachments = SWBComparator.sortByCreated(itAttachments, false);
        toReturn.append("<tbody>");
        while (itAttachments.hasNext()) {
            Attachment attachment = itAttachments.next();
            toReturn.append("\n<tr>");
            toReturn.append("\n<td>");
            toReturn.append("\n<a href=\"");
            toReturn.append(SWBPlatform.getContextPath());
            toReturn.append("/work");
            toReturn.append(attachment.getWorkPath());
            toReturn.append("/");
            toReturn.append(attachment.getFile());
            toReturn.append("\" target='_blank' class=\"swb-url-lista detalle-archivos\">");
            toReturn.append(attachment.getTitle() == null ? "" : attachment.getTitle());
            toReturn.append("\n</a>");
            toReturn.append("\n</td>");

            toReturn.append("\n<td>");
            toReturn.append(attachment.getCreated() == null ? ""
                    : SWBUtils.TEXT.getStrDate(attachment.getCreated(), "es", "dd/mm/yyyy"));
            toReturn.append("\n</td>");

            if ("true".equals(usrWithGrants)) {
                FormElementURL urlEdit = getRenderURL(obj, prop, type, mode, lang);
                urlEdit.setParameter("modeTmp", Mode_EDIT);
                urlEdit.setParameter("suriAttach", attachment.getURI());
                urlEdit.setParameter("svalAttach", attachment.getId());
                urlEdit.setParameter("usrWithGrants", usrWithGrants);

                FormElementURL urlFE = getRenderURL(obj, prop, type, mode, lang);
                urlFE.setParameter("modeTmp", Mode_RELOAD);
                urlFE.setParameter("suri", suri);
                urlFE.setParameter("usrWithGrants", usrWithGrants);

                FormElementURL urlRemove = getProcessURL(obj, prop);
                urlRemove.setParameter("_action", Action_REMOVE);
                urlRemove.setParameter("suriAttach", attachment.getURI());
                urlRemove.setParameter("obj", suri);
                urlRemove.setParameter("usrWithGrants", usrWithGrants);

                toReturn.append("\n<td class=\"swb-td-accion\">");
                toReturn.append("\n<a href=\"#\" onclick=\"showDialog('");
                toReturn.append(urlEdit.setContentType("text/html; charset=UTF-8"));
                toReturn.append("', '");
                toReturn.append(Attachment.sclass.getDisplayName(lang));
                toReturn.append("');\">");
                
                //toReturn.append("<i class=\"fa fa-pencil fa-lg swb-boton-accion\" title=\"Editar\"></i>");
                toReturn.append("<span class=\"glyphicon glyphicon-pencil\"></span>");
                toReturn.append("\n</a>");
                toReturn.append("\n<a href=\"#\" onclick=\"if(confirm(\'");
                toReturn.append("¿");
                toReturn.append(getLocaleString("alertDeleteElement", lang));
                toReturn.append(" \\'");
                toReturn.append(attachment.getTitle());
                toReturn.append("\\' ?\')){ ");
                toReturn.append("processUrl('");
                toReturn.append(urlRemove);
                toReturn.append("',null); ");
                toReturn.append("} else { return false;}");
                toReturn.append("\">");
                toReturn.append("<span class=\"glyphicon glyphicon-trash\"></span>");
//                toReturn.append("<i class=\"fa fa-trash-o fa-lg swb-boton-accion\" title=\"Eliminar\"></i>");
                toReturn.append("\n</a>");
                toReturn.append("\n</td>");

            }
            toReturn.append("\n</tr>");
        }
        toReturn.append("</tbody>");
        toReturn.append("\n</table>");
        return toReturn.toString();
    }

    /**
     * Genera la vista view del elemento de forma.
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son:
     * {@literal edit}, {@literal create}, {@literal filter} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     */
    public String renderModeView(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {
        StringBuilder toReturn = new StringBuilder();
        String suri = (String) request.getParameter("suri");
        if (suri == null) {
            suri = (String) request.getAttribute("suri");
        }
        if (suri != null) {
            SemanticObject semObj = SemanticObject.getSemanticObject(URLDecoder.decode(suri));
            Attachmentable element = null;
            if (semObj != null && semObj.createGenericInstance() instanceof Attachmentable) {
                element = (Attachmentable) semObj.createGenericInstance();
                Iterator<Attachment> itAttachments = element.listAttachmentses();
                toReturn.append("\n<table width=\"98%\">");
                itAttachments = SWBComparator.sortByCreated(itAttachments, false);

                while (itAttachments.hasNext()) {
                    Attachment attachment = itAttachments.next();
                    toReturn.append("\n<tr>");
                    toReturn.append("\n<td>");
                    toReturn.append("\n<div>");
                    toReturn.append(attachment.getTitle() == null ? "" : attachment.getTitle());
                    toReturn.append("\n</div>");
                    toReturn.append("\n</td>");

                    toReturn.append("\n<td>");
                    toReturn.append(attachment.getCreated() == null ? ""
                            : SWBUtils.TEXT.getStrDate(attachment.getCreated(), "es", "dd/mm/yyyy"));
                    toReturn.append("\n</td>");
                    /*if ("true".equals(usrWithGrants)) {
                     toReturn.append("\n<td>");
                     toReturn.append("\n<img src=\"");
                     toReturn.append(SWBPlatform.getContextPath());
                     toReturn.append("/swbadmin/icons/editar_1.gif\" alt=\"");
                     toReturn.append(getLocaleString("edit", lang));
                     toReturn.append("\"/>");
                     toReturn.append("\n</td>");

                     toReturn.append("\n<td>");
                     toReturn.append("\n<img src=\"");
                     toReturn.append(SWBPlatform.getContextPath());
                     toReturn.append("/swbadmin/icons/iconelim.png\" alt=\"");
                     toReturn.append(getLocaleString("delete", lang));
                     toReturn.append("\"/>");
                     toReturn.append("\n</td>");
                     }*/
                    toReturn.append("</tr>");
                }
                toReturn.append("\n</table>");
            }
        }
        return toReturn.toString();
    }
}