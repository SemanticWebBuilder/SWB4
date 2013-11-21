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
        } else if (mode.equals("inlineEdit")) {
            /*Al utilizar este modo, se debe incluir en el HTML las instrucciones 
             * dojo.require del InkineEditBox y dijit.form.ValidationTextBox, al menos*/
            StringBuilder viewString = new StringBuilder(128);
            String value = obj.getProperty(prop);
            String objectId = obj.getSemanticClass().getClassCodeName() + obj.getId() +
                    propName;
            String url = (String) request.getAttribute("urlRequest");
            
            viewString.append("<script type=\"text/javascript\">\n");
            viewString.append("  <!--\n");
            viewString.append("    var iledit_");
            viewString.append(objectId);
            viewString.append(";");
            viewString.append("    dojo.addOnLoad( function() {");
            viewString.append("      iledit_");
            viewString.append(objectId);
            viewString.append(" = new dijit.InlineEditBox({");
            viewString.append("        id: \"ile_");
            viewString.append(objectId);
            viewString.append("\",");
            viewString.append("        autoSave: false,");
            viewString.append("        editor: \"dijit.form.ValidationTextBox\",");
            viewString.append("        editorParams: {trim:true, required:true},");
            viewString.append("        width: '80px',");
            viewString.append("        onChange: function(value) {");
            viewString.append("          getSyncHtml('");
            viewString.append(url);
            viewString.append("&propUri=");
            viewString.append(prop.getEncodedURI());
            viewString.append("&value='+value);\n");
            viewString.append("        }");
            viewString.append("      }, 'eb_");
            viewString.append(objectId);
            viewString.append("');");
            viewString.append("    }); iledit_");
            viewString.append(objectId);
            viewString.append(".startup();\n");
            viewString.append("-->\n");
            viewString.append("</script>\n");
            viewString.append("<span id=\"eb_");
            viewString.append(objectId);
            viewString.append("\" class=\"swb-ile\">");
            viewString.append(value);
            viewString.append("</span>");
            toReturn = viewString.toString();
        }
        if (toReturn == null) {
            toReturn = "";
        }
        return toReturn;
    }
    
}
