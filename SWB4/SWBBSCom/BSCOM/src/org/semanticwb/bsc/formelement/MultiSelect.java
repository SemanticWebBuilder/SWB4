package org.semanticwb.bsc.formelement;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.element.Deliverable;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * Para generar el contenido de las vistas resumen y detalle 
   */
public class MultiSelect extends org.semanticwb.bsc.formelement.base.MultiSelectBase {
    
    
    public MultiSelect(org.semanticwb.platform.SemanticObject base) {
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
        GenericObject genObj = obj.createGenericInstance();
        if (genObj instanceof Objective) {
            Objective objective = (Objective) genObj;
            GenericIterator<Indicator> it = objective.listIndicators();
            if (it != null) {
                while (it.hasNext()) {
                    Indicator indi = it.next();
                }
            }
        }
        String toReturn = null;
        
        if (mode == null) {
            mode = "view";
        }
        if (mode.equals("edit") || mode.equals("create")) {
            toReturn = super.renderElement(request, obj, prop, propName, type, mode, lang);
        } else if (mode.equals("view")) {
            boolean showLink = false;
            StringBuilder viewString = new StringBuilder(128);
            SemanticClass rangeClass = prop.getRangeClass();
            ArrayList<String> valuesArray = new ArrayList<String>(16);
            
            viewString.append("<ul>");
            
            if (prop.isObjectProperty()) {
                if (rangeClass.equals(Objective.bsc_Objective) ||
                        rangeClass.equals(Indicator.bsc_Indicator) ||
                        rangeClass.equals(Initiative.bsc_Initiative) ||
                        rangeClass.equals(Deliverable.bsc_Deliverable)) {
                    showLink = true;
                }
                Iterator<SemanticObject> values = obj.listObjectProperties(prop);
                
                while (values.hasNext()) {
                    SemanticObject semanticObject = values.next();
                    viewString.append("  <li>");
                    if (showLink) {
                        viewString.append("<a href=\"");
                        viewString.append(rangeClass.getName());
                        viewString.append("?suri=");
                        viewString.append(semanticObject.getEncodedURI());
                        viewString.append("\" >");
                    }
                    viewString.append(semanticObject.getDisplayName(lang));
                    if (showLink) {
                        viewString.append("</a>");
                    }
                    viewString.append("</li>");
                }
            } else {
                SemanticObject sobj = prop.getDisplayProperty();
/*                String selectValues = null;
                if (sobj != null) {
                    DisplayProperty dobj = new DisplayProperty(sobj);
                    selectValues = dobj.getDisplaySelectValues(lang);
                }
                if (selectValues != null) {*/
                Iterator<SemanticLiteral> it2 = obj.listLiteralProperties(prop);
                while (it2.hasNext()) {
                    SemanticLiteral lit = it2.next();
                    viewString.append("  <li>");
                    viewString.append(lit.getString());
                    viewString.append("</li>");
                }
//                }
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
