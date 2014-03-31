package org.semanticwb.bsc.formelement;

import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.element.BSCElement;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 * Permite relacionar un objeto de tipo (BSCElement) a un riesgo. Los elementos
 * BSC estan definidos como valores en la propiedad "propSelectValues" dentro de
 * la ontología, y el valor para identificar el elemento BSC es el mismo que el
 * de la clase.
 *
 * @author martha.jimenez
 */
public class ElementBSCRelated extends org.semanticwb.bsc.formelement.base.ElementBSCRelatedBase {

    public static org.semanticwb.platform.SemanticProperty elementInstanceRelated = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#elementInstanceRelated");

    public ElementBSCRelated(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     *
     * Genera el código HTML para representar la relación con un objeto(clase) y
     * con una instancia en especifico de la clase.
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
     *
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }

        boolean DOJO = false;
        if (type.equals("dojo")) {
            DOJO = true;
        }
        StringBuffer ret = new StringBuffer();
        String name = propName;
        String label = prop.getDisplayName(lang);
        SemanticObject sobj = prop.getDisplayProperty();
        boolean required = prop.isRequired();
//        String pmsg = null;
        String imsg = null;
        String selectValues = null;
        boolean disabled = false;
        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);
//            pmsg = dobj.getPromptMessage();
            imsg = dobj.getInvalidMessage();
            selectValues = dobj.getDisplaySelectValues(lang);
            disabled = dobj.isDisabled();
        }
        if (DOJO) {
            if (imsg == null) {
                if (required) {
                    imsg = label + " es requerido.";
                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                } else {
                    imsg = "Dato invalido.";
                    if (lang.equals("en")) {
                        imsg = "Invalid data.";
                    }
                }
            }
//            if (pmsg == null) {
//                pmsg = "Captura " + label + ".";
//                if (lang.equals("en")) {
//                    pmsg = "Enter " + label + ".";
//                }
//            }
        }
        String ext = "";
        if (disabled) {
            ext += " disabled=\"disabled\"";
        }
        /*if (prop.isObjectProperty()) {
         SemanticObject val = null;
         String aux = request.getParameter(propName);
         if (aux != null) {
         val = SemanticObject.createSemanticObject(aux);
         } else {
         val = obj.getObjectProperty(prop);
         }
         String uri = "";
         String value = "";
         if (val != null) {
         uri = val.getURI();
         value = val.getDisplayName(lang);
         }
         if (mode.equals("edit") || mode.equals("create") || mode.equals("filter")) {
         if (mode.equals("create")) {
         ret.append("<select name=\"" + name + "\"");
         if (DOJO) {
         ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\""
         + imsg + "\"" + " value=\"" + uri + "\"");
         }
         if (!mode.equals("filter")) {
         if (required) {
         ret.append(" required=\"" + required + "\"");
         }
         }
         if ((mode.equals("filter") || isBlankSuport()) && ((uri == null) || (uri.length() == 0))) {
         ret.append(" displayedvalue=\"\"");
         }
         ret.append(" " + ext + ">");
         if ((mode.equals("filter") || isBlankSuport())) {
         ret.append("<option");
         ret.append(" value=\"\"></option>");
         }
         SemanticClass cls = prop.getRangeClass();
         Iterator<SemanticObject> it = null;
         if (isGlobalScope()) {
         if (cls != null) {
         it = SWBComparator.sortSemanticObjects(lang, cls.listInstances());
         } else {
         it = SWBComparator.sortSemanticObjects(lang,
         SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClassesAsSemanticObjects());
         }
         } else if (isUserRepository()) {
         SemanticModel model = getModel();
         SWBModel m = (SWBModel) model.getModelObject().createGenericInstance();
         if (m instanceof WebSite) {
         m = ((WebSite) m).getUserRepository();
         model = m.getSemanticObject().getModel();
         }
         it = SWBComparator.sortSemanticObjects(lang, model.listInstancesOfClass(cls));
         } else {
         SemanticModel model = getModel();
         SWBModel m = (SWBModel) model.getModelObject().createGenericInstance();
         if (m.getParentWebSite() != null) {
         m = m.getParentWebSite();
         }
         model = m.getSemanticModel();
         it = SWBComparator.sortSemanticObjects(lang, model.listInstancesOfClass(cls));
         }
         if (it != null) {
         while (it.hasNext()) {
         SemanticObject sob = it.next();
         boolean deleted = false;
         if (sob.instanceOf(Trashable.swb_Trashable)) {
         deleted = sob.getBooleanProperty(Trashable.swb_deleted);
         }
         if (filterObject(request, sobj, sob, prop, propName, type, mode, lang)) {
         continue;
         }
         if (!deleted) {
         if (sob.getURI() != null) {
         ret.append("<option value=\"" + sob.getURI() + "\" ");
         if (sob.getURI().equals(uri)) {
         ret.append("selected=\"selected\"");
         }
         ret.append(">" + sob.getDisplayName(lang) + "</option>");
         }
         }
         }
         }
         ret.append("</select>");
         } else if (mode.equals("edit")) {
         if (val != null) {

         }
         }
         } else if (mode.equals("view")) {
         ret.append("<span _id=\"" + name + "\" name=\"" + name + "\">" + value + "</span>");
         }*/
//        } else {
        if (selectValues != null) {
            String value = request.getParameter(propName);
            if (value == null) {
                value = obj.getProperty(prop);
            }
            if (mode.equals("edit") || mode.equals("create")) {
                if (mode.equals("create")) {
                    ret.append("<select name=\"");
                    ret.append(name);
                    ret.append("\"");
                    if (DOJO) {
                        ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"");
                        ret.append(imsg);
                        ret.append("\"");
                    }
                    ret.append(" ");
                    ret.append(ext);
                    ret.append(">");
                    StringTokenizer st = new StringTokenizer(selectValues, "|");
                    while (st.hasMoreTokens()) {
                        String tok = st.nextToken();
                        int ind = tok.indexOf(':');
                        String id = tok;
                        String val = tok;
                        if (ind > 0) {
                            id = tok.substring(0, ind);
                            val = tok.substring(ind + 1);
                        }
                        ret.append("<option value=\"");
                        ret.append(id);
                        ret.append("\" ");
                        if (id.equals(value)) {
                            ret.append("selected");
                        }
                        ret.append(">");
                        ret.append(val);
                        ret.append("</option>");
                    }
                    ret.append("</select>");
                } else if (mode.equals("edit")) {
                    if (value != null && !value.equals("none")) {
                        StringTokenizer st = new StringTokenizer(selectValues, "|");
                        while (st.hasMoreTokens()) {
                            String tok = st.nextToken();
                            int ind = tok.indexOf(':');
                            String id = tok;
                            String val = tok;
                            if (ind > 0) {
                                id = tok.substring(0, ind);
                                val = tok.substring(ind + 1);
                            }
                            if (id.equals(value)) {
                                ret.append("<input _id=\"");
                                ret.append(name);
                                ret.append("\" name=\"");
                                ret.append(name);
                                ret.append("\" type=\"text\" disabled value=\"");
                                ret.append(val);
                                ret.append("\" dojoType=\"dijit.form.ValidationTextBox\">");
                                break;
                            }
                        }
                        SemanticModel model = getModel();
                        SWBModel m = (SWBModel) model.getModelObject().createGenericInstance();
                        Iterator it = null;
                        String value2 = obj.getSemanticClass().getPrefix() + ":" + value.trim();
                        SemanticClass classList = SWBPlatform.getSemanticMgr().getVocabulary().
                                getSemanticClassById(value2);
                        if (classList != null) {
                            it = m.getSemanticObject().getModel().listInstancesOfClass(classList);
                        }
                        if (it != null) {
                            it = SWBComparator.sortByDisplayName(it, lang);
                        }
                        ret.append("<p>");
                        ret.append("<select name=\"");
                        ret.append(elementInstanceRelated.getName());
                        ret.append("\"");
                        if (DOJO) {
                            ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"");
                            ret.append(imsg);
                            ret.append("\"");
                            ret.append("");
                        }
                        ret.append(" ");
                        ret.append(ext);
                        ret.append(">");
                        SemanticObject uriElSave = obj.getObjectProperty(elementInstanceRelated);
                        while (it != null && it.hasNext()) {
                            SemanticObject semObjInst = (SemanticObject) it.next();
                            BSCElement object = (BSCElement) semObjInst.createGenericInstance();
                            if (object.getURI() != null) {
                                ret.append("<option value=\"");
                                ret.append(object.getURI());
                                ret.append("\" ");
                                if (uriElSave != null && object.getURI().equals(uriElSave.getURI())) {
                                    ret.append("selected=\"selected\"");
                                }
                                ret.append(">");
                                ret.append(object.getDisplayTitle(lang));
                                ret.append("</option>");
                            }
                        }
                        ret.append("</select></p>");
                    }
                }
            } else if (mode.equals("view")) {
                if (value != null || !value.equals("none")) {
                    StringTokenizer st = new StringTokenizer(selectValues, "|");
                    while (st.hasMoreTokens()) {
                        String tok = st.nextToken();
                        int ind = tok.indexOf(':');
                        String id = tok;
                        String val = tok;
                        if (ind > 0) {
                            id = tok.substring(0, ind);
                            val = tok.substring(ind + 1);
                        }
                        if (id.equals(value)) {
                            ret.append("<span _id=\"");
                            ret.append(name);
                            ret.append("\" name=\"");
                            ret.append(name);
                            ret.append("\">");
                            ret.append(val);
                            ret.append("</span>");
                            break;
                        }
                    }
                }
            }
        }
//        }
        return ret.toString();
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
        if ("edit".equals(request.getParameter("smode"))) {
            String elementRelated = request.getParameter(elementInstanceRelated.getName());
            String suri = request.getParameter("suri");
            SemanticObject semObj = SemanticObject.getSemanticObject(suri);
            SemanticObject semObjInstRel = SemanticObject.getSemanticObject(elementRelated);
            if (semObj != null && semObjInstRel != null) {
                semObj.setObjectProperty(elementInstanceRelated, semObjInstRel);
            }
        } else {
            super.process(request, obj, prop, propName);
        }
    }
}
