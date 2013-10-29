package org.semanticwb.bsc.formelement;


import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.bsc.formelement.base.ObjectiveAlignmentBase;
import org.semanticwb.model.GenericObject;


    /**
     * Muestra un select con las instancias pertenecientes al BSC padre, de la clase 
     * definida como rango de la propiedad asociada a este elemento 
     */
public class ObjectiveAlignment extends ObjectiveAlignmentBase {
    
    
    /**
     * Crea instancias de esta clase a partir de un objeto semantico
     * @param base el objeto semántico a utilizar para crear la nueva instancia
     */
    public ObjectiveAlignment(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Genera el c&oacute;digo HTML correspondiente al modo especificado, para la
     * propiedad asociada a este FormElement.
     * @param request la petici&oacute; HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del FormElement.
     *        Los modos soportados son: {@literal edit}, {@literal create}, {@literal filter} y {@literal view} 
     * @param lang el lenguaje utilizado en la generaci&oacute;n del c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la vista
     *         correspondiente al modo especificado para este elemento de forma.
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode,
            String lang) {
        
        if (obj == null) {
            obj = new SemanticObject();
        }
        
        boolean dojo = type.equalsIgnoreCase("dojo");
        
        StringBuilder  ret      = new StringBuilder();
        String         name     = propName;
        String         label    = prop.getDisplayName(lang);
        SemanticObject sobj     = prop.getDisplayProperty();
        boolean        required = prop.isRequired();
        String         pmsg     = null;
        String         imsg     = null;
        boolean        disabled = false;
        
        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);
            pmsg = dobj.getPromptMessage(lang);
            imsg = dobj.getInvalidMessage();
            disabled = dobj.isDisabled();
        }
        
        SemanticObject value = null;
        
        if(dojo) {
            if(imsg == null) {
                if(required) {
                    imsg = label + ("en".equals(lang)?" is required.":" is required.");
                }else {
                    imsg = "en".equals(lang)?"Invalid data.":"Dato incorrecto.";
                }
            }
            if(pmsg == null) {
                pmsg = ("en".equals(lang)?"Enter ":"Captura ") + label + ".";
            }
        }
        
        String ext = disabled?" disabled=\"disabled\"":"";
        
        String valueAux = request.getParameter(propName);
        if (valueAux != null) {
            value = SemanticObject.createSemanticObject(valueAux);
        } else if (prop.isObjectProperty()) {
            value = obj.getObjectProperty(prop);
        }
        
        String parentObjUri = null;
        String displayedValue = null;   
        if (value != null) {
            parentObjUri = value.getURI();
            displayedValue = value.getDisplayName(lang);
        }
        
        if (mode.equals("edit") || mode.equals("create") || mode.equals("filter")) {
            
            ret.append("<select name=\"" + name + "\"");
            if (dojo) {
                ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" promptMessage=\""+pmsg+"\" invalidMessage=\""
                           + imsg + "\"" + " value=\"" + parentObjUri + "\"");
            }
            if (!mode.equals("filter")) {
                ret.append(" required=\"" + required + "\"");
            }
            if ((mode.equals("filter") || isBlankSuport()) && 
                    ((parentObjUri == null) || (parentObjUri.length() == 0))) {
                ret.append(" displayedvalue=\"\"");
            }
            ret.append(" " + ext + ">");
            if ((mode.equals("filter") || isBlankSuport())) {
                ret.append("<option");
                ret.append(" value=\"\"></option>");
            }
            
            Objective sonObjective = (Objective) obj.createGenericInstance();
            SWBModel parent = sonObjective.getBSC().getParent();
            SemanticClass cls = prop.getRangeClass();
            Iterator<Objective> it = null;
            
            if (isGlobalScope() && parent != null) {
                if (cls != null) {
                    it = SWBComparator.sortSemanticObjects(lang, parent.listInstancesOfClass(cls));
                } else {
                    it = SWBComparator.sortSemanticObjects(lang,
                            SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClassesAsSemanticObjects());
                }
            }
            else if (parent != null) {
                it = SWBComparator.sortSemanticObjects(lang, parent.listInstancesOfClass(cls));
            }

            if (it != null) {
                while (it.hasNext()) {
                    Objective sob = it.next();
                    
                    if (filterObject(request, sobj, sob.getSemanticObject(), prop, propName, type, mode, lang)) {
                        continue;
                    }
                    if (sob.getURI() != null) {
                        ret.append("<option value=\"");
                        ret.append(sob.getURI());
                        ret.append("\" ");

                        if (sob.getURI().equals(parentObjUri)) {
                            ret.append("selected=\"selected\"");
                        }
                        ret.append(">" + sob.getDisplayTitle(lang));
                        ret.append("</option>");
                    }
                }
            }
            ret.append("</select>");

        } else if (mode.equals("view")) {
            if (value != null) {
                GenericObject genObj = value.createGenericInstance();
                String objectType = null;
                if (genObj instanceof Objective) {
                    objectType = Objective.bsc_Objective.getName();
                    //TODO: Eliminar despues la linea siguiente, porque la liga debe indicar el website correspondiente
                    objectType = null;
                }
                ret.append("<span _id=\"" + name + "\" name=\"" + name + "\">");
                if (objectType != null) {
                    ret.append("<a href=\"" + objectType + "?suri=" + genObj.getURI());
                    ret.append("\">");
                }
                ret.append(displayedValue);
                if (objectType != null) {
                    ret.append("</a>");
                }
                ret.append("</span>");
            } else {
                ret.append("<span _id=\"" + name + "\" name=\"" + name + "\"></span>");
            }
        }
        return ret.toString();
    }

    @Override
    public boolean filterObject(HttpServletRequest request, SemanticObject base_obj,
            SemanticObject filter_obj, SemanticProperty prop, String propName,
            String type, String mode, String lang) {
        
        Objective objective = null;
        if (filter_obj != null && filter_obj.createGenericInstance() instanceof Objective ) {
            objective = (Objective) filter_obj.createGenericInstance();
        }
        return objective==null?true:!objective.isValid();
    }
    
}
