package org.semanticwb.bsc.formelement;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.bsc.element.BSCElement;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticClass;
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
        String websiteId = ((SWBModel) obj.getModel().getModelObject().getGenericInstance()).getId();
        //(String) request.getAttribute("websiteId"); se cambio por la linea anterior
        
        if (mode == null) {
            mode = "view";
        }
        if (mode.equals("edit") || mode.equals("create")) {
            toReturn = super.renderElement(request, obj, prop, propName, type, mode, lang);
        } else if (mode.equals("view")) {
            boolean showLink = false;
            StringBuilder viewString = new StringBuilder(128);
            String baseUrl = null;
            
            GenericObject genObject = obj.createGenericInstance();
            SemanticClass rootClass = obj.getSemanticClass().getRootClass();
            if (rootClass.equals(BSCElement.bsc_BSCElement)) {
                if (request.getAttribute("pdf") == null) {
                showLink = true;
                viewString.append("<a href=\"");
                if (websiteId != null && request.getRequestURI().contains(websiteId)) {
                    baseUrl = request.getRequestURI().substring(0,
                            request.getRequestURI().indexOf(websiteId) + websiteId.length() + 1);
                }
                if (baseUrl != null) {
                    viewString.append(baseUrl);
                }
                viewString.append(genObject.getClass().getSimpleName());
                viewString.append("?suri=");
                viewString.append(obj.getEncodedURI());
                viewString.append("\" >");
                }
                
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
            viewString.append(";\n");
            viewString.append("    dojo.addOnLoad( function() {\n");
            viewString.append("      iledit_");
            viewString.append(objectId);
            viewString.append(" = new dijit.InlineEditBox({\n");
            viewString.append("        id: \"ile_");
            viewString.append(objectId);
            viewString.append("\",\n");
            viewString.append("        autoSave: false,\n");
            viewString.append("        editor: \"dijit.form.ValidationTextBox\",\n");
            viewString.append("        editorParams: {trim:true, required:true},\n");
            viewString.append("        width: '80px',\n");
            viewString.append("        onChange: function(value) {\n");
            viewString.append("          getSyncHtml('");
            viewString.append(url);
            viewString.append("&propUri=");
            viewString.append(prop.getEncodedURI());
            viewString.append("&value='+value);\n");
            viewString.append("        }\n");
            viewString.append("      }, 'eb_");
            viewString.append(objectId);
            viewString.append("');");
            viewString.append(" iledit_");
            viewString.append(objectId);
            viewString.append(".startup();\n");
            viewString.append("    });\n");
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
