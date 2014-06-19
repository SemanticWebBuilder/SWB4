package org.semanticwb.bsc.formelement;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.FormElementURL;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 * Presenta la vista para un sppiner, el cual permite
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
     * Genera el c&oacute;digo HTML para representar un sppiner cuando el {@code type} recibido es {@literal dojo}.
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
     * {@literal edit}, {@literal create}, {@literal inlineEdit} y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, 
    SemanticProperty prop, String propName, String type, String mode, String lang) {
        
        StringBuilder buffer = new StringBuilder(256);
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
        boolean isDojoType = type != null && type.equalsIgnoreCase("dojo");
        String dojoAttributes = " data-dojo-type=\"dijit.form.NumberSpinner\" data-dojo-props=\"smallDelta:" +
               increase + ", constraints:{min:" + valMin + ", max:" + valMax + ", places:0}\"";
        
        if (mode == null) {
            mode = "view";
        }
        if (mode.equals("edit") || mode.equals("create")) {
            buffer.append("<input type=\"text\" id=\"");
            buffer.append(propName);
            buffer.append("_");
            buffer.append(obj.getId());
            buffer.append("\" value=\"");
            buffer.append(value);
            buffer.append("\"");
            if (isDojoType) {
                buffer.append(dojoAttributes);
            }
            buffer.append(" name=\"");
            buffer.append(propName);
            buffer.append("\"/>");
        } else if (mode.equals("inlineEdit")) {
            /*Al utilizar este modo, se debe incluir en el HTML las instrucciones 
             * dojo.require del dijit.form.NumberSpinner, al menos*/
            FormElementURL urlProcess = getProcessURL(obj, prop);
            String objectId = obj.getSemanticClass().getClassCodeName() + obj.getId() + propName;

            buffer.append("<script type=\"text/javascript\">\n");
            buffer.append("  <!--\n");
            buffer.append("    var iledit_");
            buffer.append(objectId);
            buffer.append(";\n");
            buffer.append("    dojo.addOnLoad( function() {\n");
            buffer.append("      iledit_");
            buffer.append(objectId);
            buffer.append(" = new dijit.InlineEditBox({\n");
            buffer.append("        id: \"");
            buffer.append(objectId);
            buffer.append("\",\n");
            buffer.append("        autoSave: false,\n");
            buffer.append("        editor: \"dijit.form.NumberSpinner\",\n");
            buffer.append("        editorParams: {required: false, width: '50px', constraints: {places:0, min:");
            buffer.append(valMin);
            buffer.append(", max:");
            buffer.append(valMax);
            buffer.append("}, smallDelta: ");
            buffer.append(increase);
            buffer.append("},\n");
            buffer.append("        onChange: function(value) {\n");
            buffer.append("          getSyncHtml('");
            buffer.append(urlProcess.toString());
            buffer.append("&");
            buffer.append(prop.getName());
            buffer.append("='+value);\n");
            buffer.append("        }\n");
            buffer.append("      }, 'eb_");
            buffer.append(objectId);
            buffer.append("');\n");
            buffer.append("      iledit_");
            buffer.append(objectId);
            buffer.append(".startup();\n");
            buffer.append("    });\n");
            buffer.append("-->\n");
            buffer.append("</script>\n");
            buffer.append("<span id=\"eb_");
            buffer.append(objectId);
            buffer.append("\" class=\"swb-ile\">");
            buffer.append(value);
            buffer.append("</span>");
        } else if (mode.equals("view")) {
            buffer.append("<span id=\"");
            buffer.append(propName);
            buffer.append("_");
            buffer.append(obj.getId());
            buffer.append("\">");
            buffer.append(value);
            buffer.append("</span>");
        }
        return buffer.toString();
    }
}
