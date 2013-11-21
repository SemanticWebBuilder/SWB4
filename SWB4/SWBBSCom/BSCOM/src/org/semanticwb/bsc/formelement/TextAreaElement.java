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
            toReturn = (obj.getProperty(prop) == null || 
                    (obj.getProperty(prop) == null && "null".equals(obj.getProperty(prop)))
                    ? " " : obj.getProperty(prop));
        } else if (mode.equals("inlineEdit")) {
            /*Al utilizar este modo, se debe incluir en el HTML las instrucciones 
             * dojo.require del InkineEditBox y dijit.form.Textarea, al menos*/
            StringBuilder viewString = new StringBuilder(128);
            String value = (obj.getProperty(prop) == null || 
                    (obj.getProperty(prop) == null && "null".equals(obj.getProperty(prop)))
                    ? " " : obj.getProperty(prop));
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
            viewString.append("        editor: \"dijit.form.Textarea\",");
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
