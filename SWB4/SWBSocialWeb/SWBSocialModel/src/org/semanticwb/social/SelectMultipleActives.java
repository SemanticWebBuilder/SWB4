/**  
* SWB Social es una plataforma que descentraliza la publicación, seguimiento y monitoreo hacia las principales redes sociales. 
* SWB Social escucha y entiende opiniones acerca de una organización, sus productos, sus servicios e inclusive de su competencia, 
* detectando en la información sentimientos, influencia, geolocalización e idioma, entre mucha más información relevante que puede ser 
* útil para la toma de decisiones. 
* 
* SWB Social, es una herramienta basada en la plataforma SemanticWebBuilder. SWB Social, como SemanticWebBuilder, es una creación original 
* del Fondo de Información y Documentación para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SWB Social a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarla en las mismas condiciones con que INFOTEC la ha diseñado y puesto a su disposición; 
* aprender de élla; distribuirla a terceros; acceder a su código fuente y modificarla, y combinarla o enlazarla con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. y SWB Social 1.0
* 
* INFOTEC no otorga garantía sobre SWB Social, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder o SWB Social, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
package org.semanticwb.social;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.Activeable;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.Trashable;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * SelectMultipleActives 
   */
public class SelectMultipleActives extends org.semanticwb.social.base.SelectMultipleActivesBase 
{
    public SelectMultipleActives(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.model.base.FormElementBase#process(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty)
     */
    /**
     * Process.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     */
    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) {
        //System.out.println("Entra a SelectMultipleActives/process-1");
        // super.process(request, obj, prop);
        // System.out.println("Process...");
        // System.out.println("Prop:"+prop);
        // System.out.println("obj:"+obj);
        String vals[] = request.getParameterValues(propName);

        if (vals == null) {
            vals = new String[0];
        }

        obj.removeProperty(prop);

        if (prop.isObjectProperty()) {
            for (int x = 0; x < vals.length; x++) {
                obj.addObjectProperty(prop, SemanticObject.createSemanticObject(vals[x]));
            }
        } else {
            for (int x = 0; x < vals.length; x++) {
                obj.addLiteralProperty(prop, new SemanticLiteral(vals[x]));

                // System.out.println("val"+x+":"+vals[x]);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.FormElementBase#renderElement(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    /**
     * Render element.
     * 
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @param type the type
     * @param mode the mode
     * @param lang the lang
     * @return the string
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type,
                                String mode, String lang) {
        //System.out.println("Entra a SelectMultipleActives/renderElement-1");
        if (obj == null) {
            obj = new SemanticObject();
        }

////        boolean IPHONE = false;
//        boolean XHTML  = false;
        boolean DOJO   = false;

//        if (type.equals("iphone")) {
//            IPHONE = true;
//        } else if (type.equals("xhtml")) {
//            XHTML = true;
//        } else
        if (type.equals("dojo")) {
            DOJO = true;
        }
        
        String frmname = null;
        if (mode.equals("create")) {
            frmname = prop.getDomainClass().getURI();
        } else {
            frmname = obj.getURI();
        }
        frmname = frmname + "/form";
        
        //System.out.println("frmname_George:"+frmname);
        
        
        String formName = (String) request.getAttribute("formName");
        //System.out.println("formName:"+formName);
                
        StringBuffer   ret          = new StringBuffer();
        String         name         = propName;
        String         label        = prop.getDisplayName(lang);
        SemanticObject sobj         = prop.getDisplayProperty();
        boolean        required     = prop.isRequired();
        String         pmsg         = null;
        String         imsg         = null;
        String         selectValues = null;
        boolean        disabled     = false;

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);

            pmsg         = dobj.getPromptMessage();
            imsg         = dobj.getInvalidMessage();
            selectValues = dobj.getDisplaySelectValues(lang);
            disabled     = dobj.isDisabled();
        }

        if (DOJO) {
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
            //System.out.println("ES OBJECTTYPE");
            ArrayList<String> vals   = new ArrayList();
            String            auxs[] = request.getParameterValues(propName);

            if (auxs == null) {
                auxs = new String[0];
            }

            for (int x = 0; x < auxs.length; x++) {
                vals.add(auxs[x]);
            }

            Iterator<SemanticObject> it2 = obj.listObjectProperties(prop);

            while (it2.hasNext()) {
                SemanticObject semanticObject = it2.next();

                // System.out.println("semanticObject:"+semanticObject+" vals:"+vals);
                vals.add(semanticObject.getURI());
            }

            //String value = obj.getDisplayName(lang);

            if (mode.equals("edit") || mode.equals("create")) {
                //System.out.println("ENTRA SELECT 1");
                //ret.append("<select name=\"" + name + "\" multiple=\"true\"");
                //ret.append(" style=\"width:300px;\"");
                
                //ret.append("\n<script type=\"text/javascript\">\n");
                //ret.append("function fieldValidation(){\n");
                //ret.append("alert(\"hola\");");
                //ret.append("return true;");
                //ret.append("}\n");
                //ret.append("\n</script>\n");
                
                
                ret.append("<div class=\"swb-redesconfig-info\">");
                ret.append("<form id=\"form1\" name=\"form1\" method=\"post\" action=\"\">");

                //ret.append(" " + ext + ">");

                // onChange="dojo.byId('oc1').value=arguments[0]"
                SemanticClass            cls = prop.getRangeClass();
                Iterator<SemanticObject> it  = null;

                /*
                if (isGlobalScope()) {
                    if (cls != null) {
                        it = SWBComparator.sortSemanticObjects(lang, cls.listInstances());
                    } else {
                        it = SWBComparator.sortSemanticObjects(lang,
                                SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClassesAsSemanticObjects());
                    }
                } else {
                * */
                    it = SWBComparator.sortSemanticObjects(lang, getModel().listInstancesOfClass(cls));
                //}

                while (it.hasNext()) {
                    SemanticObject sob = it.next();
                    boolean deleted=false;
                    boolean active=false;
                    if(sob.instanceOf(Trashable.swb_Trashable))
                    {
                        deleted=sob.getBooleanProperty(Trashable.swb_deleted);
                    }
                    if(sob.instanceOf(Activeable.swb_Activeable))
                    {
                        active=sob.getBooleanProperty(Activeable.swb_active);
                    }
                    boolean socialNetIsFanPage=false;
                    if(sob.getGenericInstance() instanceof Pageable)
                    {
                        Pageable pageAble=(Pageable)sob.getGenericInstance();
                        if(pageAble.isIsFanPage())
                        {
                            socialNetIsFanPage=true;
                        }
                    }

                    if(!deleted && active && !socialNetIsFanPage)
                    {                    

                        if (sob.getURI() != null) {
                            //ret.append("<option value=\"" + sob.getURI() + "\" ");
                            ret.append("<label><input type=\"checkbox\" value=\""+sob.getURI()+"\" name=\""+name+"\" id=\""+name+"_"+sob.getURI()+"\"");
                            
                            if(DOJO)
                            {
                                    //ret.append(" data-dojo-type=\"dijit/form/CheckBox\"");
                                    //ret.append(" onClick=\"document.getElementById('"+frmname+"').addEventListener('submit',fieldValidation());\"");                                    
                            }
                            

                            if (vals.contains(sob.getURI())) {
                                //ret.append("selected=\"selected\"");
                                ret.append(" checked");
                            }
                            ret.append("/>");
                            
                            if(sob.getGenericInstance() instanceof Twitter) ret.append("<img src=\"/swbadmin/css/images/config-tw.png\">");
                            if(sob.getGenericInstance() instanceof Facebook) ret.append("<img src=\"/swbadmin/css/images/config-fb.png\">");
                            if(sob.getGenericInstance() instanceof Youtube) ret.append("<img src=\"/swbadmin/css/images/config-yt.png\">");
                            if(sob.getGenericInstance() instanceof Instagram) ret.append("<img src=\"/swbadmin/css/images/config-ig.png\">");
                            
                            //ret.append(">" + sob.getDisplayName(lang) + "</option>");
                            //ret.append(" <label for=\""+name+"\">Want</label>");
                            ret.append("<span>"+sob.getDisplayName(lang)+"</span></label>");
                        }
                    }
                }
                
                if (DOJO) {
                    //ret.append("dojo.require(\"dojox.validate.check\");");
                    //ret.append("var profile = {");
                    //ret.append("required: [ \""+name+"\"],");
                    //ret.append("constraints: {");
                    //ret.append(name+": myValidationFunction,"); //Nombre de funcion js que valida los checkboxes
                    //ret.append("},");
                    //ret.append("}");
                }

                //ret.append("</select>");
                ret.append("</form>");
                ret.append("</div>");
            } else if (mode.equals("view")) {
                //System.out.println("ENTRA SELECT 2");
                ret.append("<select name=\"" + name + "\" multiple=\"true\"");
                ret.append(" style=\"width:300px;\"");

                if (DOJO) {
                    //ret.append(" dojoType=\"dijit.form.MultiSelect\" invalidMessage=\"" + imsg + "\"");
                }

                ret.append(" disabled=\"disabled\">");

                // onChange="dojo.byId('oc1').value=arguments[0]"
                SemanticClass            cls = prop.getRangeClass();
                Iterator<SemanticObject> it  = null;
                
                /*
                if (isGlobalScope()) {
                    if (cls != null) {
                        it = SWBComparator.sortSemanticObjects(lang, cls.listInstances());
                    } else {
                        it = SWBComparator.sortSemanticObjects(lang,
                        SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClassesAsSemanticObjects());
                    }
                } else {*/
                    it = SWBComparator.sortSemanticObjects(lang, getModel().listInstancesOfClass(cls));
                //}

                while (it.hasNext()) {
                    SemanticObject sob = it.next();
                    boolean deleted=false;
                    boolean active=false;
                    if(sob.instanceOf(Trashable.swb_Trashable))
                    {
                        deleted=sob.getBooleanProperty(Trashable.swb_deleted);
                    }
                    if(sob.instanceOf(Activeable.swb_Activeable))
                    {
                        active=sob.getBooleanProperty(Activeable.swb_active);
                    }
                    boolean socialNetIsFanPage=false;
                    if(sob.getGenericInstance() instanceof Pageable)
                    {
                        Pageable pageAble=(Pageable)sob.getGenericInstance();
                        if(pageAble.isIsFanPage())
                        {
                            socialNetIsFanPage=true;
                        }
                    }

                    if(!deleted && active && !socialNetIsFanPage)
                    {                    
                        if (sob.getURI() != null) {
                            if (vals.contains(sob.getURI())) {
                                ret.append("<option value=\"" + sob.getURI() + "\" ");
                                ret.append(">" + sob.getDisplayName(lang) + "</option>");
                            }
                        }
                    }
                }
                ret.append("</select>");
            }
        } else {
            //System.out.println("ES DATATYPE");
            if (selectValues != null) {
                //System.out.println("ENTRA SELECT DATA-1");
                ArrayList<String> vals   = new ArrayList();
                String            auxs[] = request.getParameterValues(propName);

                if (auxs == null) {
                    auxs = new String[0];
                }

                for (int x = 0; x < auxs.length; x++) {
                    vals.add(auxs[x]);
                }

                Iterator<SemanticLiteral> it2 = obj.listLiteralProperties(prop);

                while (it2.hasNext()) {
                    SemanticLiteral lit = it2.next();

                    vals.add(lit.getString());
                }

                ret.append("<select name=\"" + name + "\" multiple=\"true\"");

                if (DOJO) {
                    //ret.append(" dojoType=\"dijit.form.MultiSelect\" invalidMessage=\"" + imsg + "\"");
                }

                ret.append(" " + ext + ">");

                StringTokenizer st = new StringTokenizer(selectValues, "|");

                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    int    ind = tok.indexOf(':');
                    String id  = tok;
                    String val = tok;

                    if (ind > 0) {
                        id  = tok.substring(0, ind);
                        val = tok.substring(ind + 1);
                    }

                    ret.append("<option value=\"" + id + "\" ");

                    if (vals.contains(id)) {
                        ret.append("selected=\"selected\"");
                    }

                    ret.append(">" + val + "</option>");
                }

                ret.append("</select>");
            }
        }
        //Jorge
        //String enviar = lang.equals("en")?"You have to select at least one social network":"Debe seleccionar por lo menos una red social";
        //String enviar = "Holass";
        
        ret.append("\n<script type=\"text/javascript\">\n");
        //ret.append("<!--");
        //ret.append("function loaded(){");
        //ret.append("document.getElementById(\""+frmname+"\").addEventListener(\"submit\", function(){");
        //ret.append("function prepareEventHandlers() {");
        //ret.append("alert('Entra a prepareEventHandlers...');");
        //ret.append("    document.getElementById(\""+frmname+"\").addEventListener(\"submit\", function(){");
        //ret.append("    alert(\"Hicieron Submit\");");
        //ret.append("    if(document.forms['"+frmname+"\']['"+name+"'].selectedIndex==-1){");
        //ret.append("        alert(\"Debe seleccionar por lo menos una red social a escuchar\");");
        //ret.append("    }");
        //ret.append("if (theForm.MyCheckbox.checked == false) {");
        //ret.append("if(document.forms['"+frmname+"\']['"+name+"'].checked== false){");
        //ret.append("var grupo = document.getElementById(\"form1\")."+name+";\n");
        //ret.append("alert('grupo:'+grupo);\n");
        //ret.append("if(!checkvalidate(grupo)){\n");
        //ret.append("        alert(\"Debe seleccionar por lo menos una red social a escuchar\");\n");
        //ret.append("        return false;\n");
        //ret.append("}else{\n");
        //ret.append("        return true;\n");
        //ret.append("}\n");
        
        ret.append("function fieldValidation(){\n");
        ret.append("alert(\"hola\");");
        ret.append("}\n");
        
        
        //ret.append("function checkvalidate(checks) {\n");
        //ret.append("for (i = 0; lcheck = checks[i]; i++) {\n");
        //ret.append("    if (lcheck.checked) {\n");
        //ret.append("         return true;\n");
        //ret.append("    }\n");
        //ret.append("}\n");
        //ret.append("    return false;\n");
        //ret.append("}\n");
        
        
        //ret.append("    }, false);");
        //ret.append("//-->");
        //ret.append("}");
        //ret.append("window.setTimeOut(prepareEventHandlers,2000);");
        //ret.append("window.onload = function() {");
        //ret.append("alert(\"Entra a Onload\");prepareEventHandlers();");
        //ret.append("}");
        
        //ret.append("window.addEventListener(\"load\", loaded, false);");
        //ret.append(" //var foo; \nif (typeof(foo) == 'undefined' || foo == null){var foo = new Object();}\n");
        //ret.append("var canSubmit = true;\n");
        //ret.append("function canContinue(cad){\n");
        //ret.append("    foo[cad] = true;\n");
        //ret.append("}\n");
        //ret.append("function stopSumbit(cad){\n");
        //ret.append("    foo[cad] = false;\n");
        //ret.append("}\n");
        //ret.append("\n");
        //ret.append("var myForm = dojo.byId(\"" + frmname + "\");\n");
        //ret.append("onsubmit=function(){\n");
        //ret.append("var canSubmit=true;\n");
        //ret.append("alert('\"+enviar+\"');\\n");
        //ret.append("for (var test in foo) {\n");
        //ret.append("canSubmit = canSubmit&&foo[test];}\n");
        //ret.append(" if (!canSubmit) alert('"+enviar+"');\n");
        //ret.append("return canSubmit;};\n");
        //ret.append("\n");
        ret.append("</script>\n");
        
        //Termina Jorge

        return ret.toString();
    }
}