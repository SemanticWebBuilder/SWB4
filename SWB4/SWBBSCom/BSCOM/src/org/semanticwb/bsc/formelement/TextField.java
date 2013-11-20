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
            viewString.append("    var iledit_" + objectId + ";");
            viewString.append("    dojo.addOnLoad( function() {");
            viewString.append("      iledit_" + objectId + " = new dijit.InlineEditBox({");
            viewString.append("        id: \"ile_" + objectId + "\",");
            viewString.append("        autoSave: false,");
            viewString.append("        editor: \"dijit.form.ValidationTextBox\",");
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
