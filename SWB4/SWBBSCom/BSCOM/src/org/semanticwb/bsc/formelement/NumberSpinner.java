package org.semanticwb.bsc.formelement;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 * Form Element que presentará la vista para un sppiner, el cual permitirá
 * ajustar un valor dentro de un cuadro de texto.
 */
public class NumberSpinner extends org.semanticwb.bsc.formelement.base.NumberSpinnerBase {

    public NumberSpinner(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

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
