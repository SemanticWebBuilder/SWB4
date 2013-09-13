package org.semanticwb.bsc.formelement;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


/**
* Elemento que muestra un componente de selección para escoger una serie designada 
* como referente para evaluar el indicador padre. 
*/
public class SeriesOfEvaluation extends org.semanticwb.bsc.formelement.base.SeriesOfEvaluationBase 
{
    public SeriesOfEvaluation(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /**
     * Elemento que muestra un componente de selección para escoger una serie 
     * designada como referente para evaluar el indicador padre.
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
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
        
System.out.println("\n\nSeriesOfEvaluation...");
System.out.println("obj="+obj);
System.out.println("prop="+prop);
System.out.println("propName="+propName);
System.out.println("mode="+mode);
        
        
        if (obj == null) {
            obj = new SemanticObject();
        }
        boolean isDojoType = false;
        if (type.equals("dojo")) {
            isDojoType = true;
        }

        StringBuilder ret = new StringBuilder(128);
        String name = propName;
        String label = prop.getDisplayName(lang);
        SemanticObject sobj = prop.getDisplayProperty();
        boolean isRequired = prop.isRequired();
        String imsg = null;
        boolean isDisabled = false;
        String parentObjUri = "";
        String displayedValue = "";
        
        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);
            imsg = dobj.getInvalidMessage();
            isDisabled = dobj.isDisabled();
        }
        
        SemanticObject value = null;
        
        if (isDojoType) {
            if (imsg == null) {
                if (isRequired) {
                    imsg = label + " es requerido.";

                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                } else {
                    imsg = "Dato invalido.";

                    if (lang.equals("en")) {
                        imsg = "Invalid datum.";
                    }
                }
            }
        }
        
        String ext = "";
        if(isDisabled) {
            ext += " disabled=\"disabled\"";
        }
        
        String valueAux = request.getParameter(propName);
        if(valueAux != null) {
            value = SemanticObject.createSemanticObject(valueAux);
        }else if(prop.isObjectProperty()) {
            value = obj.getObjectProperty(prop);
        }
        
        if(value != null) {
            parentObjUri = value.getURI();
            displayedValue = value.getDisplayName(lang);
        }
        
        if(!(obj.createGenericInstance() instanceof Indicator)) {
            return lang.equals("es")?"Clase no soportada...":"Unsupported class...";
        }
        
        if(mode.equals("edit") || mode.equals("create") || mode.equals("filter"))
        {
            ret.append("<select name=\"" + name + "\"");
            if(isDojoType) {
                ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"" + imsg + "\"" + " value=\"" + parentObjUri + "\"");
            }
            if(!mode.equals("filter")) {
                ret.append(" required=\"" + isRequired + "\"");
            }
            if((mode.equals("filter") || isBlankSuport()) && ((parentObjUri == null) || (parentObjUri.length() == 0))) {
                ret.append(" displayedvalue=\"\"");
            }
            ret.append(" " + ext + ">");
            if((mode.equals("filter") || isBlankSuport())) {
                ret.append("<option");
                ret.append(" value=\"\"></option>");
            }
            
            Indicator indicator = (Indicator) obj.createGenericInstance();
            Iterator<Series> it = null;
            
            if(indicator !=null)
            {
                it = SWBComparator.sortSemanticObjects(lang, indicator.listSerieses());
            }

            if (it != null)
            {
                while (it.hasNext())
                {
                    Series sob = it.next();
                    boolean deleted = false;
                    
                    if(filterObject(request, sobj, sob.getSemanticObject(), prop, propName, type, mode, lang)) {
                        continue;
                    }
                    if (!deleted) {
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
            }
            ret.append("</select>");
        }
        else if(mode.equals("view"))
        {
            ret.append("<span _id=\"" + name + "\" name=\"" + name + "\">");
            ret.append(displayedValue + "</span>");
        }
        return ret.toString();
    }
}
