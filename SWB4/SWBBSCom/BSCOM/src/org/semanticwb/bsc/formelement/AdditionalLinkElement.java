package org.semanticwb.bsc.formelement;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.SWBClass;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 * Form Element que presentar&aacute; el enlace a un elemento complementario.
 *
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class AdditionalLinkElement extends org.semanticwb.bsc.formelement.base.AdditionalLinkElementBase {

    /**
     * Crea instancias de esta clase a partir de un objeto sem&aacute;ntico
     *
     * @param base el objeto sem&aacute;ntico a utilizar para crear la nueva
     * instancia
     */
    public AdditionalLinkElement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Genera el c&oacute;digo HTML para representar el enlace a un elemento
     * complementario del BSC.
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son: {@literal pdf}, {@literal inline}
     * y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     *
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String type, String mode, String lang) {
        return super.renderElement(request, obj, prop, type, mode, lang); 
    }

    /**
     * Genera el c&oacute;digo HTML para representar el enlace a un elemento
     * complementario del BSC.
     *
     * @param request la petici&oacute;n HTTP hecha por el cliente
     * @param obj el objeto a quien pertenece la propiedad asociada a este
     * FormElement
     * @param prop la propiedad asociada a este FormElement
     * @param propName el nombre de la propiedad asociada a este FormElement
     * @param type el tipo de despliegue a generar. Actualmente solo se acepta
     * el valor {@code dojo}
     * @param mode el modo en que se presentar&aacute; el despliegue del
     * FormElement. Los modos soportados son: {@literal pdf}, {@literal inline}
     * y {@literal view}
     * @param lang el lenguaje utilizado en la generaci&oacute;n del
     * c&oacute;digo HTML a regresar
     * @return el objeto String que representa el c&oacute;digo HTML con la
     * vista correspondiente al modo especificado para este elemento de forma.
     *
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {
        String toReturn = null;

        if (mode == null) {
            mode = "view";
        }
        String pdf = request.getAttribute("pdf") == null ? ""
                : request.getAttribute("pdf").toString();
        if (mode.equals("view") || mode.equals("inlineEdit")) {
            StringBuilder viewString = new StringBuilder(128);
            SemanticClass rangeClass = prop.getRangeClass();

            viewString.append("<ul>");

            if (prop.isObjectProperty()) {
                Iterator<SemanticObject> values = obj.listObjectProperties(prop);
                while (values.hasNext()) {
                    SemanticObject semanticObject = values.next();
                    if (semanticObject.getSemanticClass().isSWBClass()) {
                        SWBClass swbclass = (SWBClass) semanticObject.createGenericInstance();
                        if (!swbclass.isValid()) {
                            continue;
                        }
                    }
                    viewString.append("  <li>");
                    if (pdf.equals("")) {
                        viewString.append("<a href=\"");
                        viewString.append(rangeClass.getName());
                        viewString.append("?suri=");
                        viewString.append(semanticObject.getEncodedURI());
                        viewString.append("\" >");
                    }
                    viewString.append(semanticObject.getDisplayName(lang));
                    if (pdf.equals("")) {
                        viewString.append("</a>");
                    }
                    viewString.append("</li>");
                }
            }
            viewString.append("</ul>");
            toReturn = viewString.toString();
        }
        if (toReturn == null) {
            toReturn = "";
        }
        return toReturn;
    }

}
