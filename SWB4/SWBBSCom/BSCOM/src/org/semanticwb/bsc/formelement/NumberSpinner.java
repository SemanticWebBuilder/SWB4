package org.semanticwb.bsc.formelement;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 * Form Element que presentará la vista para un sppiner, el cual permitirá
 * ajustar un valor dentro de un cuadro de texto.
 * 
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class NumberSpinner extends org.semanticwb.bsc.formelement.base.NumberSpinnerBase {

    /**
     * 
     * Crea instancias de esta clase a partir de un objeto semantico
     * @param base el objeto semántico a utilizar para crear la nueva instancia
     */
    public NumberSpinner(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * 
     * Genera el código HTML para representar un sppiner.
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
    public String renderElement(HttpServletRequest request, SemanticObject obj, 
    SemanticProperty prop, String propName, String type, String mode, String lang) {
        StringBuilder buffer = new StringBuilder();
        String value = null;
        if (obj.getProperty(prop) != null) {
            value = obj.getProperty(prop);
        } else {
            value = request.getParameter(propName) == null ? "0" : 
                    request.getParameter(propName);
        }
        int valMax = getValueMax();
        int valMin = getValueMin();
        int increase = getIncrease();
        buffer.append("<input data-dojo-type=\"dijit.form.NumberSpinner\" ");
        buffer.append("id=\"");
        buffer.append(propName);
        buffer.append("_");
        buffer.append(obj.getId());
        buffer.append("\"");
        buffer.append(" value=\"");
        buffer.append(value);
        buffer.append("\"");
        buffer.append(" data-dojo-props=\"smallDelta:");
        buffer.append(increase);
        buffer.append(", constraints:{");
        buffer.append("min:");
        buffer.append(valMin);
        buffer.append(",max:");
        buffer.append(valMax);
        buffer.append(",places:0}\"");
        buffer.append(" name=\"");
        buffer.append(propName);
        buffer.append("\"/>");
        return buffer.toString();
    }
}
