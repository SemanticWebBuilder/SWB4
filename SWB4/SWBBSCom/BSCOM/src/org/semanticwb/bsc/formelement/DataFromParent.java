package org.semanticwb.bsc.formelement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.element.Initiative;
import org.semanticwb.model.Activeable;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormElementURL;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.Trashable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * Extrae información de la propiedad indicada en parentProperty y la muestra para que el usuario seleccione un dato 
   */
public class DataFromParent extends org.semanticwb.bsc.formelement.base.DataFromParentBase  {
    
    public DataFromParent(org.semanticwb.platform.SemanticObject base) {
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
        
        if (obj == null) {
            obj = new SemanticObject();
        }

        boolean dojo = false;

        if (type.equals("dojo")) {
            dojo = true;
        }

        StringBuilder ret = new StringBuilder(256);
        String name = propName;
        String label = prop.getDisplayName(lang);
        SemanticObject sobj = prop.getDisplayProperty();
        boolean required = prop.isRequired();
        boolean disabled = false;
        String pmsg = null;
        String imsg = null;
        String selectValues = null;
        User user = SWBContext.getSessionUser();

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);
            pmsg = dobj.getPromptMessage();
            imsg = dobj.getInvalidMessage();
            selectValues = dobj.getDisplaySelectValues(lang);
            disabled = dobj.isDisabled();
        }

        if (dojo) {
            if (imsg == null) {
                if (required) {
                    imsg = label + " es requerido.";

                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                } else {
                    imsg = "Dato invalido.";
                    if (lang.equals("en")) {
                        imsg = "Invalid data.";
                    }
                }
            }

            if (pmsg == null) {
                pmsg = "Captura " + label + ".";
                if (lang.equals("en")) {
                    pmsg = "Enter " + label + ".";
                }
            }
        }

        String ext = "";

        if (disabled) {
            ext += " disabled=\"disabled\"";
        }

        if (prop.isObjectProperty()) {
            SemanticObject val = null;
            String aux = request.getParameter(propName);

            if (aux != null) {
                val = SemanticObject.createSemanticObject(aux);
            } else {
                val = obj.getObjectProperty(prop);
            }

            String uri   = "";
            String value = "";

            if (val != null) {
                uri   = val.getURI();
                value = val.getDisplayName(lang);
            }

            SemanticClass cls = prop.getRangeClass();
            Iterator<SemanticObject> it = null;

            if (isGlobalScope()) {
                if (cls != null) {
                    it = SWBComparator.sortSemanticObjects(lang, cls.listInstances());
                } else {
                    it = SWBComparator.sortSemanticObjects(lang,
                            SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClassesAsSemanticObjects());
                }
            } else if (isUserRepository()) {
                SemanticModel model = getModel();
                SWBModel m = (SWBModel) model.getModelObject().createGenericInstance();

                if (m instanceof WebSite) {
                    m = ((WebSite) m).getUserRepository();
                    model = m.getSemanticObject().getModel();
                }
                it = SWBComparator.sortSemanticObjects(lang, model.listInstancesOfClass(cls));
            } else if (this.getParentProperty() != null) {
                //Se obtienen los objetos asociados a obj a partir de parentProperty
                SemanticObject parent = obj.getHerarquicalParent();
                GenericObject thisGenericObj = obj.createGenericInstance();
                if (thisGenericObj instanceof Initiative) {
                    List<State> semObjIt = ((Initiative) thisGenericObj).listValidStates();
                    ArrayList<SemanticObject> array = new ArrayList<SemanticObject>(semObjIt.size());
                    for (State state : semObjIt) {
                        array.add(state.getSemanticObject());
                    }
                    it = array.iterator();
                } else {
                    if (parent != null) {
                        SemanticClass semClass = parent.getSemanticClass();
                        SemanticProperty property = semClass.getProperty(this.getParentProperty());
                        if (property != null && property.isObjectProperty()) {
                            it = SWBComparator.sortSemanticObjects(lang, parent.listObjectProperties(property));
                        }
                    }
                }
            } else {
                SemanticModel model = getModel();
                SWBModel m = (SWBModel) model.getModelObject().createGenericInstance();
                if (m.getParentWebSite() != null) {
                    m = m.getParentWebSite();
                }
                model = m.getSemanticModel();
                it = SWBComparator.sortSemanticObjects(lang, model.listInstancesOfClass(cls));
            }
            if (mode.equals("edit") || mode.equals("create") || mode.equals("filter")) {
                ret.append("<select name=\"");
                ret.append(name);
                ret.append("\"");

                if (dojo) {
                    ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"");
                    ret.append(imsg);
                    ret.append("\" value=\"");
                    ret.append(uri);
                    ret.append("\"");
                }

                if (!mode.equals("filter")) {
                    if (required) {
                        ret.append(" required=\"");
                        ret.append(required);
                        ret.append("\"");
                    }
                }

                if ((mode.equals("filter") || isBlankSuport()) && ((uri == null) || (uri.length() == 0))) {
                    ret.append(" displayedvalue=\"\"");
                }

                ret.append(" ");
                ret.append(ext);
                ret.append(">");

                if ((mode.equals("filter") || isBlankSuport())) {
                    ret.append("<option value=\"\"></option>");
                }

                if (it != null) {
                    while (it.hasNext()) {
                        SemanticObject sob = it.next();
                        boolean isDeleted = false;
                        boolean isActive = false;
                        if (sob.instanceOf(Trashable.swb_Trashable)) {
                            isDeleted = sob.getBooleanProperty(Trashable.swb_deleted);
                        }
                        if (sob.instanceOf(Activeable.swb_Activeable)) {
                            isActive = sob.getBooleanProperty(Activeable.swb_active);
                        }
                        
                        if (filterObject(request, sobj, sob, prop, propName, type, mode, lang)) {
                            continue;
                        }

                        if (!isDeleted && isActive && user.haveAccess(sob.createGenericInstance())) {
                            if (sob.getURI() != null) {
                                ret.append("<option value=\"");
                                ret.append(sob.getURI());
                                ret.append("\" ");
                                if (sob.getURI().equals(uri)) {
                                    ret.append("selected=\"selected\"");
                                }
                                ret.append(">");
                                ret.append(sob.getDisplayName(lang));
                                ret.append("</option>");
                            }
                        }
                    }
                }
                ret.append("</select>");
            } else if (mode.equals("inlineEdit")) {
                
                /*Al utilizar este modo, se debe incluir en el HTML las instrucciones 
                 * dojo.require del dijit.InlineEditBox y dijit.form.Select, al menos*/
                StringBuilder viewString = new StringBuilder(128);
                String objectId = obj.getSemanticClass().getClassCodeName() + obj.getId() + propName;
                String labelToShow = null;
                FormElementURL urlProcess = getProcessURL(obj, prop);

                viewString.append("<script type=\"text/javascript\">\n");
                viewString.append("  <!--\n");
                viewString.append("    var iledit_");
                viewString.append(objectId);
                viewString.append(";\n");
                viewString.append("    dojo.addOnLoad( function() {\n");
                viewString.append("      iledit_");
                viewString.append(objectId);
                viewString.append(" = new dijit.InlineEditBox({\n");
                viewString.append("        id: \"");
                viewString.append(objectId);
                viewString.append("\",\n");
                viewString.append("        autoSave: false,\n");
                viewString.append("        editor: \"dijit.form.Select\",\n");
                viewString.append("        editorParams: {required: true, width: '50px', options: [");
                if (this.isBlankSuport()) {
                    viewString.append("        { label: \" - - - \", value: \"\"}");
                    if (it != null && it.hasNext()) {
                        viewString.append(",\n");
                    }
                }
                
                int optionsCont = 0;
                while (it != null && it.hasNext()) {
                    SemanticObject sob = it.next();
                    boolean isDeleted = false;
                    boolean isActive = false;
                    if (sob.instanceOf(Trashable.swb_Trashable)) {
                        isDeleted = sob.getBooleanProperty(Trashable.swb_deleted);
                    }
                    if (sob.instanceOf(Activeable.swb_Activeable)) {
                        isActive = sob.getBooleanProperty(Activeable.swb_active);
                    }

                    if (filterObject(request, sobj, sob, prop, propName, type, mode, lang)) {
                        continue;
                    }

                    if (!isDeleted && isActive && user.haveAccess(sob.createGenericInstance())) {
                        if (sob.getURI() != null) {
                            if (optionsCont > 0) {
                                viewString.append(",\n");
                            }
                            viewString.append("        { label: \"");
                            viewString.append(sob.getDisplayName(lang));
                            viewString.append("\", value: \"");
                            viewString.append(sob.getEncodedURI());
                            viewString.append("\"");
                            if (sob.getURI().equals(uri)) {
                                viewString.append(", selected: true");
                                labelToShow = sob.getDisplayName(lang);
                            }
                            viewString.append("}");
                            optionsCont++;
                        }
                    }
                }
                
                viewString.append(" ]");
                viewString.append("},\n");
                viewString.append("        onChange: function(value) {\n");
                viewString.append("          var value2Send = '';\n");
                viewString.append("          var searchIn = iledit_");
                viewString.append(objectId);
                viewString.append(".editorParams.options;\n");
                viewString.append("          for (var i = 0; i < searchIn.length; i++) {\n");
                viewString.append("            if (searchIn[i].selected) {\n");
                viewString.append("              value2Send = searchIn[i].value;\n");
                viewString.append("            }\n");
                viewString.append("          }\n");
//                    viewString.append("          alert('A enviar: ' + value2Send);");
                viewString.append("          ");
                viewString.append("          getSyncHtml('");
                viewString.append(urlProcess.toString());
                viewString.append("&");
                viewString.append(prop.getName());
                viewString.append("='+value2Send);\n");
                viewString.append("        }\n");
                viewString.append("      }, 'eb_");
                viewString.append(objectId);
                viewString.append("');\n");
                viewString.append("      iledit_");
                viewString.append(objectId);
                viewString.append(".startup();\n");
                viewString.append("    });\n");
                viewString.append("-->\n");
                viewString.append("</script>\n");
                viewString.append("<span id=\"eb_");
                viewString.append(objectId);
                viewString.append("\" class=\"swb-ile\">");
                viewString.append(labelToShow != null ? labelToShow : "");
                viewString.append("</span>");
                ret.append(viewString.toString());
                
            } else if (mode.equals("view")) {
                ret.append("<span _id=\"");
                ret.append(name);
                ret.append("\" name=\"");
                ret.append(name);
                ret.append("\">");
                ret.append(value);
                ret.append("</span>");
            }
        } else {// isDataTypeProperty
            if (selectValues != null) {
                String value = request.getParameter(propName);

                if (value == null) {
                    value = obj.getProperty(prop);
                }
                StringTokenizer st = new StringTokenizer(selectValues, "|");
                
                if (mode.equals("edit") || mode.equals("create")) {
                    ret.append("<select name=\"");
                    ret.append(name);
                    ret.append("\"");

                    if (dojo) {
                        ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"");
                        ret.append(imsg);
                        ret.append("\"");
                    }

                    ret.append(" ");
                    ret.append(ext);
                    ret.append(">");
                    
                    if (this.isBlankSuport()) {
                        ret.append("<option value=\"\"");
                        if (value == null) {
                            ret.append(" selected=\"selected\"");
                        }
                        ret.append("></option>");
                    }
                    
                    while (st.hasMoreTokens()) {
                        String tok = st.nextToken();
                        int ind = tok.indexOf(':');
                        String id = tok;
                        String val = tok;
                        
                        if (ind > 0) {
                            id = tok.substring(0, ind).trim();
                            val = tok.substring(ind + 1);
                        }
                        
                        ret.append("<option value=\"");
                        ret.append(id);
                        ret.append("\" ");
                        
                        if (id.equals(value)) {
                            ret.append("selected");
                        }
                        
                        ret.append(">");
                        ret.append(val);
                        ret.append("</option>");
                    }
                    ret.append("</select>");
                /* ------ inlineEdit como dataTypeProperty  */
                } else if (mode.equals("inlineEdit")) {
                    /*Al utilizar este modo, se debe incluir en el HTML las instrucciones 
                     * dojo.require del dijit.InlineEditBox y dijit.form.Select, al menos*/
                    StringBuilder viewString = new StringBuilder(256);
                    String objectId = obj.getSemanticClass().getClassCodeName() + obj.getId() + propName;
                    String labelToShow = null;
                    FormElementURL urlProcess = getProcessURL(obj, prop);
                    
                    viewString.append("<script type=\"text/javascript\">\n");
                    viewString.append("  <!--\n");
                    viewString.append("    var iledit_");
                    viewString.append(objectId);
                    viewString.append(";\n");
                    viewString.append("    dojo.addOnLoad( function() {\n");
                    viewString.append("      iledit_");
                    viewString.append(objectId);
                    viewString.append(" = new dijit.InlineEditBox({\n");
                    viewString.append("        id: \"");
                    viewString.append(objectId);
                    viewString.append("\",\n");
                    viewString.append("        autoSave: false,\n");
                    viewString.append("        editor: \"dijit.form.Select\",\n");
                    viewString.append("        editorParams: {required: true, width: '50px', options: [");
                    if (this.isBlankSuport()) {
                        viewString.append("        { label: \" - - - \", value: \"\"}");
                        if (st.hasMoreTokens()) {
                            viewString.append(",\n");
                        }
                    }
                    
                    int optionsCont = 0;
                    while (st.hasMoreTokens()) {
                        String tok = st.nextToken();
                        int ind = tok.indexOf(':');
                        String id = tok;
                        String val = tok;
                        
                        if (ind > 0) {
                            id  = tok.substring(0, ind).trim();
                            val = tok.substring(ind + 1);
                        }
                        if (optionsCont > 0) {
                            viewString.append(",\n");
                        }
                        viewString.append("        { label: \"");
                        viewString.append(val);
                        viewString.append("\", value: \"");
                        viewString.append(id);
                        viewString.append("\"");
                        if (id.equals(value)) {
                            viewString.append(", selected: true");
                            labelToShow = val;
                        }
                        viewString.append("}");
                        optionsCont++;
                    }
                    
                    viewString.append(" ]");
                    viewString.append("},\n");
                    viewString.append("        onChange: function(value) {\n");
                    viewString.append("          var value2Send = '';\n");
                    viewString.append("          var searchIn = iledit_");
                    viewString.append(objectId);
                    viewString.append(".editorParams.options;\n");
                    viewString.append("          for (var i = 0; i < searchIn.length; i++) {\n");
                    viewString.append("            if (searchIn[i].selected) {\n");
                    viewString.append("              value2Send = searchIn[i].value;\n");
                    viewString.append("            }\n");
                    viewString.append("          }\n");
//                    viewString.append("          alert('A enviar: ' + value2Send);");
                    viewString.append("          ");
                    viewString.append("          getSyncHtml('");
                    viewString.append(urlProcess.toString());
                    viewString.append("&");
                    viewString.append(prop.getName());
                    viewString.append("='+value2Send);\n");
                    viewString.append("          if (reloadPage != undefined && reloadPage) {\n");
                    viewString.append("            location.reload();\n");
                    viewString.append("          }\n");
                    viewString.append("        }\n");
                    viewString.append("      }, 'eb_");
                    viewString.append(objectId);
                    viewString.append("');\n");
                    viewString.append("      iledit_");
                    viewString.append(objectId);
                    viewString.append(".startup();\n");
                    viewString.append("    });\n");
                    viewString.append("-->\n");
                    viewString.append("</script>\n");
                    viewString.append("<span id=\"eb_");
                    viewString.append(objectId);
                    viewString.append("\" class=\"swb-ile\">");
                    viewString.append(labelToShow != null ? labelToShow : "");
                    viewString.append("</span>");
                    ret.append(viewString.toString());
                } else if (mode.equals("view")) {

                    while (st.hasMoreTokens()) {
                        String tok = st.nextToken();
                        int ind = tok.indexOf(':');
                        String id  = tok;
                        String val = tok;

                        if (ind > 0) {
                            id = tok.substring(0, ind);
                            val = tok.substring(ind + 1);
                        }

                        if (id.equals(value)) {
                            ret.append("<span _id=\"");
                            ret.append(name);
                            ret.append("\" name=\"");
                            ret.append(name);
                            ret.append("\">");
                            ret.append(val);
                            ret.append("</span>");
                            break;
                        }
                    }
                }
            }
        }

        return ret.toString();
    }
    
}
