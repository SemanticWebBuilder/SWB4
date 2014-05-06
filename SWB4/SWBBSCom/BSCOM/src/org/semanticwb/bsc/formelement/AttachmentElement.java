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
 * Form Element que presentará la vista para administrar archivos adjuntos
 * (Creación, Edición y Eliminación)
 *
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class AttachmentElement extends org.semanticwb.bsc.formelement.base.AttachmentElementBase {

    private static Logger log = SWBUtils.getLogger(AttachmentElement.class);
    private static final String Mode_VIEW = "view";
    private static final String Mode_EDIT = "edit";
    private static final String Mode_ADD = "add";
    private static final String Mode_RELOAD = "reload";
    private static final String Action_ADD = "aAdd";
    private static final String Action_EDIT = "aEdit";
    private static final String Action_REMOVE = "aDelete";

    public AttachmentElement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String type, String mode, String lang) {
        return super.renderElement(request, obj, prop, type, mode, lang);
    }

    /**
     * Genera el código HTML para representar la administración de los Archivos
     * adjuntos de los elementos del BSC.
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

        if (modeTmp == null && mode.equals(SWBFormMgr.MODE_VIEW)) {
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

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop) {
        super.process(request, obj, prop);
    }

    /**
     * Procesa la información enviada por el elemento de forma, para almacenarla
     * en la propiedad del objeto indicado.
     *
     * @param request la petici&oacute; HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     */
    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop,
            String propName) {
        String action = request.getParameter("_action");
        String usrWithGrants = request.getParameter("usrWithGrants");
        WebSite site = SWBContext.getWebSite(getModel().getName());
        User user = SWBContext.getSessionUser();
        if (Action_ADD.equals(action)) {
            SWBFormMgr mgr = new SWBFormMgr(Attachment.sclass, site.getSemanticObject(), null);
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
     * Genera el código HTML para representar la edición de un archivo adjunto.
     *
     * @param request la petici&oacute; HTTP hecha por el cliente
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
        formMgr.setOnSubmit("mySubmitForm(this.id)");
        formMgr.setMode(SWBFormMgr.MODE_EDIT);

        FormElementURL urlp = getProcessURL(obj, prop);
        urlp.setParameter("_action", Action_EDIT);
        urlp.setParameter("usrWithGrants", usrWithGrants);
        String url = urlp.toString();
        String onsubmit = " onsubmit=\"if(validateData(this.id, 'edit')){mySubmitForm(this.id)}\"";
        toReturn.append("<div id=\"frmEdit\">");
        toReturn.append("<form id=\"");
        toReturn.append(formMgr.getFormName());
        toReturn.append("\" class=\"swbform\" action=\"");
        toReturn.append(url);
        toReturn.append("\"");
        toReturn.append(onsubmit);
        toReturn.append(" method=\"post\">\n");
        toReturn.append(formMgr.getFormHiddens());
        toReturn.append("	<fieldset>\n");
        toReturn.append("	    <table>\n");
        toReturn.append("           <tbody>\n");

        Iterator<SemanticProperty> it = SWBComparator.sortSortableObject(formMgr.
                getProperties().iterator());
        while (it.hasNext()) {
            SemanticProperty prop1 = it.next();
            toReturn.append("            <tr>\n");
            toReturn.append("                <td align=\"right\">\n");
            toReturn.append("                    ");
            toReturn.append(formMgr.renderLabel(request, prop1, prop1.getName(),
                    SWBFormMgr.MODE_EDIT));
            toReturn.append("                </td>\n");
            toReturn.append("                <td>\n");

            toReturn.append(
                    formMgr.getFormElement(prop1).renderElement(
                    request, semObject, prop1,
                    prop1.getName(), "XHTML", SWBFormMgr.MODE_EDIT, lang));
            toReturn.append("                </td>\n");
            toReturn.append("             </tr>\n");
        }
        toReturn.append("	    </tbody>\n");
        toReturn.append("<tr>");
        toReturn.append("<td align=\"center\" colspan=\"2\">");
        toReturn.append("          <button dojoType=\"dijit.form.Button\" type=\"submit\" ");
        toReturn.append("name=\"enviar\" >");
        toReturn.append(getLocaleString("send", lang));
        toReturn.append("</button>");
        toReturn.append("          <button dojoType=\"dijit.form.Button\" ");
        toReturn.append("onclick=\"dijit.byId('swbDialog').hide()\">");
        toReturn.append(getLocaleString("cancel", lang));
        toReturn.append("</button>");
        toReturn.append("</td>");
        toReturn.append("</tr>");
        toReturn.append("	    </table>\n");
        toReturn.append("	</fieldset>\n");
        toReturn.append("</form>\n");
        toReturn.append("</div>");

        return toReturn.toString();
    }

    /**
     * Presenta la vista de los archivos adjuntos asociados al elemento BSC,
     * aquí se getiona los permisos para los usuarios
     *
     * @param request la petici&oacute; HTTP hecha por el cliente
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
                toReturn.append("\n         dijit.byId('");
                toReturn.append(Attachment.bsc_file.getName());
                toReturn.append("_new_defaultAuto').focus(); return false;}");
                toReturn.append("\n        return true;");
                toReturn.append("\n    }");
                toReturn.append("\n</script>");

                toReturn.append("\n<div dojoType=\"dijit.Dialog\" class=\"soria\" ");
                toReturn.append("style=\"width:450px;height:225px;\" ");
                toReturn.append("id=\"swbDialog\" title=\"Agregar\" >\n");
                toReturn.append("  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" ");
                toReturn.append("style=\"width:450px;height:225px;\" ");
                toReturn.append("id=\"swbDialogImp\" executeScripts=\"true\">\n");
                toReturn.append("    Cargando...\n");
                toReturn.append("  </div>\n");
                toReturn.append("</div>\n");

                toReturn.append("\n<div dojoType=\"dijit.Dialog\" class=\"soria\" ");
                toReturn.append("style=\"height:125px;\" ");
                toReturn.append("id=\"swbDialog2\" title=\"");
                toReturn.append(getLocaleString("successfulOperation", lang));
                toReturn.append("");
                toReturn.append("\" >\n");
                toReturn.append("  <div dojoType=\"dojox.layout.ContentPane\" class=\"soria\" ");
                toReturn.append("style=\"height:125px;width:250px;text-align:center;\" ");
                toReturn.append("id=\"swbDialogImp2\" executeScripts=\"true\">\n");
                toReturn.append("          <p style=\"align:center\">");
                toReturn.append(getLocaleString("successfulOperation", lang));
                toReturn.append("</p>\n");
                toReturn.append("          <p style=\"vertical-align: middle\">");
                toReturn.append("<button dojoType=\"dijit.form.Button\" ");
                toReturn.append("onclick=\"dijit.byId('swbDialog2').hide()\">");
                toReturn.append(getLocaleString("success", lang));
                toReturn.append("</button></p>");
                toReturn.append("  </div>\n");
                toReturn.append("</div>\n");

                if ("true".equals(usrWithGrants)) {
                    toReturn.append("<a href=\"#\" onclick=\"showDialog('");
                    toReturn.append(urlAddFE.setContentType("text/html; charset=UTF-8"));
                    toReturn.append("', '");
                    toReturn.append(Attachment.sclass.getDisplayName(lang));
                    toReturn.append("');\">");
                    toReturn.append(getLocaleString("add", lang));
                    toReturn.append(" ");
                    toReturn.append(Attachment.sclass.getDisplayName(lang));
                    toReturn.append("\n</a>");
                }

                toReturn.append("\n</div>");

                toReturn.append("<br/>");
                if (itAttachments.hasNext()) {
                    toReturn.append("\n<div class=\"swbform\" id=\"swbform\">");
                    toReturn.append(listAttachment(itAttachments, suri, obj, prop, type,
                            mode, lang, usrWithGrants));
                    toReturn.append("\n</div>");
                }
            }
        }
        return toReturn.toString();
    }

    /**
     * Genera el código HTML para representar la vista para agregar un nuevo
     * archivo adjunto
     *
     * @param request la petici&oacute; HTTP hecha por el cliente
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
        formMgr.setType(SWBFormMgr.TYPE_XHTML);
        formMgr.setSubmitByAjax(true);
        formMgr.setOnSubmit("if(validateData(this.id, 'add')) {mySubmitForm(this.id)}");
        formMgr.setMode(SWBFormMgr.MODE_CREATE);

        String url = getProcessURL(obj, prop).setParameter("_action", Action_ADD).toString();
        formMgr.addButton("          <button dojoType=\"dijit.form.Button\" type=\"submit\" "
                + "name=\"enviar\" >" + getLocaleString("send", lang) + "</button>");
        formMgr.addButton("          <button dojoType=\"dijit.form.Button\" "
                + "onclick=\"dijit.byId('swbDialog').hide()\">"
                + getLocaleString("cancel", lang) + "</button>");
        formMgr.setAction(url);
        toReturn.append("<div id=\"frmAdd\">");
        toReturn.append(formMgr.renderForm(request));
        toReturn.append("</div>");
        return toReturn.toString();
    }

    /**
     *
     * @param request la petici&oacute; HTTP hecha por el cliente
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
     * @param usrWithGrants define si existen permisos para editar o eliminar los
     * archivos adjuntos.
     * @return el objeto String que representa el c&oacute;digo HTML con el
     * conjunto de archivos adjuntos.
     */
    private String listAttachment(Iterator<Attachment> itAttachments, String suri,
            SemanticObject obj, SemanticProperty prop, String type, String mode, String lang,
            String usrWithGrants) {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("\n<table width=\"98%\">");
        itAttachments = SWBComparator.sortByCreated(itAttachments, false);

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
            toReturn.append("\" target='_blank'>");
            toReturn.append(attachment.getTitle() == null ? "" : attachment.getTitle());
            toReturn.append("\n</a>");
            toReturn.append("\n</div>");
            toReturn.append("\n</td>");

            toReturn.append("\n<td>");
            toReturn.append(attachment.getCreated() == null ? ""
                    : SWBUtils.TEXT.getStrDate(attachment.getCreated(), "es", "dd/mm/yyyy"));
            toReturn.append("\n</td>");
//            User user = SWBContext.getSessionUser();
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

                toReturn.append("\n<td>");
//                if (user.equals(attachment.getCreator())) {
                toReturn.append("\n<a href=\"#\" onclick=\"showDialog('");
                toReturn.append(urlEdit.setContentType("text/html; charset=UTF-8"));
                toReturn.append("', '");
                toReturn.append(Attachment.sclass.getDisplayName(lang));
                toReturn.append("');\">");
                toReturn.append("\n<img src=\"");
                toReturn.append(SWBPlatform.getContextPath());
                toReturn.append("/swbadmin/icons/editar_1.gif\" alt=\"");
                toReturn.append(getLocaleString("edit", lang));
                toReturn.append("\"/>");
                toReturn.append("\n</a>");
//                }
                toReturn.append("\n</td>");

                toReturn.append("\n<td>");
//                if (user.equals(attachment.getCreator())) {
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
                toReturn.append("\n<img src=\"");
                toReturn.append(SWBPlatform.getContextPath());
                toReturn.append("/swbadmin/icons/iconelim.png\" alt=\"");
                toReturn.append(getLocaleString("delete", lang));
                toReturn.append("\"/>");
                toReturn.append("\n</a>");
//                }
                toReturn.append("\n</td>");


            }
        }
        toReturn.append("\n</table>");
        return toReturn.toString();
    }

    /**
     * Genera la vista view del elemento de forma.
     * 
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
//        String usrWithGrants = (String) request.getAttribute("usrWithGrants") == null
//                ? (String) request.getParameter("usrWithGrants")
//                : (String) request.getAttribute("usrWithGrants");
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