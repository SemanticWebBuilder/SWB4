package org.semanticwb.bsc.formelement;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.bsc.element.Deliverable;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * Muestra en el modo vista, una liga hacia la página que contenga la información
   * del tipo de objeto correspondiente, para bsc:Objective, bsc:Indicator, bsc:Initiative y bsc:Deliverable 
   */
public class TextField extends org.semanticwb.bsc.formelement.base.TextFieldBase {
    
    
    public TextField(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String type, String mode, String lang) {
        return renderElement(request, obj, prop, prop.getName(), type, mode, lang);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj,
            SemanticProperty prop, String propName, String type, String mode, String lang) {
        
        String toReturn = null;
        
        if (mode == null) {
            mode = "view";
        }
        if (mode.equals("edit") || mode.equals("create")) {
            toReturn = super.renderElement(request, obj, prop, propName, type, mode, lang);
        } else if (mode.equals("view")) {
            boolean showLink = false;
            StringBuilder viewString = new StringBuilder(128);
            
            GenericObject genObject = obj.createGenericInstance();
            if (genObject instanceof Objective || genObject instanceof Indicator ||
                    genObject instanceof Initiative || genObject instanceof Deliverable) {
                showLink = true;
                viewString.append("<a href=\"");
                viewString.append(genObject.getClass().getSimpleName());
                viewString.append("?suri=");
                viewString.append(obj.getEncodedURI());
                viewString.append("\" >");
            }
            viewString.append(obj.getProperty(prop));
            if (showLink) {
                viewString.append("</a>");
            }
            toReturn = viewString.toString();
        }
        if (toReturn == null) {
            toReturn = "";
        }
        return toReturn;
    }
    
}
