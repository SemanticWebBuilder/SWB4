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
            viewString.append("    var iledit_" + objectId + ";");
            viewString.append("    dojo.addOnLoad( function() {");
            viewString.append("      iledit_" + objectId + " = new dijit.InlineEditBox({");
            viewString.append("        id: \"ile_" + objectId + "\",");
            viewString.append("        autoSave: false,");
            viewString.append("        editor: \"dijit.form.Textarea\",");
            viewString.append("        editorParams: {trim:true, required:true},");
            viewString.append("        width: '80px',");
            viewString.append("        onChange: function(value) {");
            viewString.append("          var xmlhttp;\n");
            viewString.append("          if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari\n");
            viewString.append("            xmlhttp=new XMLHttpRequest();\n");
            viewString.append("          } else {// code for IE6, IE5\n");
            viewString.append("            xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");\n");
            viewString.append("          }\n");
            viewString.append("          xmlhttp.onreadystatechange=function() {\n");
            viewString.append("            if (xmlhttp.readyState==4 && xmlhttp.status==200) {\n");
            viewString.append("              location.reload(true);\n");
            viewString.append("            }\n");
            viewString.append("          }\n");
            viewString.append("          xmlhttp.open(\"GET\", \"" + url + "&propUri=" + prop.getEncodedURI() + "&value=\"+value, true);\n");
            viewString.append("          xmlhttp.send();\n");
            viewString.append("        }");
            viewString.append("      }, 'eb_" + objectId + "');");
            viewString.append("    }); iledit_" + objectId + ".startup();\n");
            viewString.append("-->\n");
            viewString.append("</script>\n");
            viewString.append("<span id=\"eb_" + objectId + "\" class=\"swb-ile\">" + value + "</span>");
            toReturn = viewString.toString();
        }
        
        if (toReturn == null) {
            toReturn = "";
        }
        return toReturn;
    }
    
}
