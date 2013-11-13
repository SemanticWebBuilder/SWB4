package org.semanticwb.bsc.formelement;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * Utilizado en la generación de vistas resumen y detalle 
   */
public class TextAreaElement extends org.semanticwb.bsc.formelement.base.TextAreaElementBase {
    
    
    public TextAreaElement(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String type, String mode, String lang) {
        return renderElement(request, obj, prop, prop.getName(), type, mode, lang);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode,
            String lang) {
        
        String toReturn = null;
        
        if (mode == null) {
            mode = "view";
        }
        if (mode.equals("edit") || mode.equals("create")) {
            toReturn = super.renderElement(request, obj, prop, propName, type, mode, lang);
        } else if (mode.equals("view")) {
            toReturn = obj.getProperty(prop);
        }
        
        if (toReturn == null) {
            toReturn = "";
        }
        return toReturn;
    }
    
}
