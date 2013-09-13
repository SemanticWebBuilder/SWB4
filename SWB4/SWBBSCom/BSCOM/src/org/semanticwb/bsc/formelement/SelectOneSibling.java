package org.semanticwb.bsc.formelement;

import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.Trashable;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


/**
* Este form element muestra un componente de selección, o lista de selección, para escoger una instancia excluyendo la instancia actual. 
*/
public class SelectOneSibling extends org.semanticwb.bsc.formelement.base.SelectOneSiblingBase 
{
    public SelectOneSibling(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang)
    {
        if (obj == null) {
            obj = new SemanticObject();
        }

        boolean dojo   = false;
        if (type.equals("dojo")) {
            dojo = true;
        }

        StringBuilder   ret          = new StringBuilder();
        String         name         = propName;
        String         label        = prop.getDisplayName(lang);
        SemanticObject sobj         = prop.getDisplayProperty();
        boolean        required     = prop.isRequired();
        String         pmsg         = null;
        String         imsg         = null;
        String         selectValues = null;
        boolean        disabled     = false;
        
System.out.println("\n\n SelectOneExclusive...");
System.out.println("obj="+obj);
System.out.println("prop="+prop);
System.out.println("propName="+propName);
System.out.println("type="+type);
System.out.println("mode="+mode);
System.out.println("lang="+lang);
System.out.println("sobj="+sobj);

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);

            pmsg         = dobj.getPromptMessage();
            imsg         = dobj.getInvalidMessage();
            selectValues = dobj.getDisplaySelectValues(lang);
            disabled     = dobj.isDisabled();
        }

        if(dojo) {
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

            if (mode.equals("edit") || mode.equals("create") || mode.equals("filter")) {
                ret.append("<select name=\"" + name + "\"");

                if (dojo) {
                    ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"" + imsg + "\"" + " value=\""+uri+"\"");
                }

                if(!mode.equals("filter"))
                {
                    ret.append(" required=\"" + required + "\"");
                }

                if ((mode.equals("filter") || isBlankSuport()) && ((uri == null) || (uri.length() == 0))) {
                    ret.append(" displayedvalue=\"\"");
                }

                ret.append(" " + ext + ">");
                
                if ((mode.equals("filter") || isBlankSuport())) {
                    ret.append("<option");
                    ret.append(" value=\"\"></option>");
                }

                SemanticClass cls = prop.getRangeClass();
System.out.println("cls="+cls);
System.out.println("objeto padre="+obj.getHerarquicalParent());
if(obj.getHerarquicalParent()!=null) {
   Iterator<SemanticObject> i = obj.getHerarquicalParent().listHerarquicalChilds(); 
   while(i.hasNext()) {
       SemanticObject so = i.next();
       System.out.print("child="+so);
       if(so==obj) {
           System.out.print(" igual a "+obj);
       }
       System.out.print("\n");
   }
}

                Iterator<SemanticObject> it  = null;

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
                } else {
if(obj.getHerarquicalParent()!=null) {
   it = SWBComparator.sortSemanticObjects(lang, obj.getHerarquicalParent().listHerarquicalChilds());
}
else{
                    SemanticModel model = getModel();
                    SWBModel m = (SWBModel) model.getModelObject().createGenericInstance();
                    if(m.getParentWebSite()!=null) {
                        m = m.getParentWebSite();
                    }                    
                    model = m.getSemanticModel();
                    
                    it = SWBComparator.sortSemanticObjects(lang, model.listInstancesOfClass(cls));
}
                }

                if (it != null) {
                    while (it.hasNext()) {
                        SemanticObject sob = it.next();
                        boolean deleted=false;
                        if(sob.instanceOf(Trashable.swb_Trashable)) {
                            deleted=sob.getBooleanProperty(Trashable.swb_deleted);
                        }
                        
                        if(filterObject(request, sobj, sob, prop, propName, type, mode, lang)) {
                            continue;
                        }

                        if(!deleted)
                        {
                            if(sob.getURI() != null && !obj.equals(sob)) {
                                ret.append("<option value=\"" + sob.getURI() + "\" ");

                                if (sob.getURI().equals(uri)) {
                                    ret.append("selected=\"selected\"");
                                }

                                ret.append(">" + sob.getDisplayName(lang) + "</option>");
                            }
                        }
                    }
                }
                ret.append("</select>");
            } else if (mode.equals("view")) {
                ret.append("<span _id=\"" + name + "\" name=\"" + name + "\">" + value + "</span>");
            }
        } else {
            if (selectValues != null) {
                String value = request.getParameter(propName);

                if (value == null) {
                    value = obj.getProperty(prop);
                }
                
                if (mode.equals("edit") || mode.equals("create")) {
                    ret.append("<select name=\"" + name + "\"");

                    if(dojo) {
                        ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"" + imsg + "\"");
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

                        if (id.equals(value)) {
                            ret.append("selected");
                        }

                        ret.append(">" + val + "</option>");
                    }

                    ret.append("</select>");
                } else if (mode.equals("view")) {
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

                        if (id.equals(value)) {
                            ret.append("<span _id=\"" + name + "\" name=\"" + name + "\">" + val + "</span>");
                            break;
                        }
                    }
                }
            }
        }

        return ret.toString();
    }
}
