package org.semanticwb.bsc.formelement;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.Activeable;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.Trashable;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


/**
* Este form element muestra un componente de selección, o lista de selección,
 * para escoger una instancia del mismo nivel jerarquico, en el árbol,  excluyendo
 * la instancia actual.
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
        
        boolean dojo = type.equalsIgnoreCase("dojo");

        StringBuilder  ret      = new StringBuilder();
        String         name     = propName;
        String         label    = prop.getDisplayName(lang);
        SemanticObject sobj     = prop.getDisplayProperty();
        boolean        required = prop.isRequired();
        String         pmsg     = null;
        String         imsg     = null;
        boolean        disabled = false;
        
//System.out.println("\n\n SelectOneExclusive...");
//System.out.println("obj="+obj);
//System.out.println("prop="+prop);
//System.out.println("propName="+propName);
//System.out.println("type="+type);
//System.out.println("mode="+mode);
//System.out.println("lang="+lang);
//System.out.println("sobj="+sobj);

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);
            pmsg         = dobj.getPromptMessage(lang);
            imsg         = dobj.getInvalidMessage(lang);
            disabled     = dobj.isDisabled();
        }

        if(dojo) {
            if(imsg == null) {
                if(required) {
                    imsg = label + ("en".equals(lang)?" is required.":" is required.");
                }else {
                    imsg = "en".equals(lang)?"Invalid data.":"Dato incorrecto.";
                }
            }
            if(pmsg == null) {
                pmsg = ("en".equals(lang)?"Enter ":"Captura ") + label + ".";
            }
        }
        
        if(prop.isObjectProperty())
        {
            SemanticObject val = null;
            String aux = request.getParameter(propName);
            if(aux != null) {
                val = SemanticObject.createSemanticObject(aux);
            }else {
                val = obj.getObjectProperty(prop);
            }

            String uri   = "";
            String value = "";

            if(val != null) {
                uri   = val.getURI();
                value = val.getDisplayName(lang);
            }

            if(mode.equals("edit") || mode.equals("create") || mode.equals("filter"))
            {
                ret.append("<select name=\"" + name + "\"");

                if(dojo) {
                    ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" promptMessage=\""+pmsg+"\" invalidMessage=\"" + imsg + "\"" + " value=\""+uri+"\"");
                }
                if(!mode.equals("filter")) {
                    ret.append(" required=\"" + required + "\"");
                }
                if((mode.equals("filter") || isBlankSuport()) && ((uri == null) || (uri.length() == 0))) {
                    ret.append(" displayedvalue=\"\"");
                }
                if(disabled) {
                    ret.append(" disabled=\"disabled\"");
                }
                ret.append(">");                
                if((mode.equals("filter") || isBlankSuport())) {
                    ret.append("<option");
                    ret.append(" value=\"\"></option>");
                }
                
                SemanticClass cls = prop.getRangeClass();
                Iterator<SemanticObject> it = null;
                if(isGlobalScope())
                {
                    if (cls != null) {
                        it = SWBComparator.sortSemanticObjects(lang, cls.listInstances());
                    } else {
                        it = SWBComparator.sortSemanticObjects(lang,
                                SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClassesAsSemanticObjects());
                    }
                }
                else if (isUserRepository())
                {
                    SemanticModel model = getModel();
                    SWBModel m = (SWBModel) model.getModelObject().createGenericInstance();

                    if (m instanceof WebSite) {
                        m = ((WebSite) m).getUserRepository();
                        model = m.getSemanticObject().getModel();
                    }

                    it = SWBComparator.sortSemanticObjects(lang, model.listInstancesOfClass(cls));
                }
                else
                {
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

                if(it != null)
                {
                    while (it.hasNext()) {
                        SemanticObject sob = it.next();
                        if(filterObject(request, obj, sob, prop, propName, type, mode, lang)) {
                            continue;
                        }
//                        if(sob.getURI() != null) {
                        ret.append("<option value=\"" + sob.getURI() + "\" ");
                        if(sob.getURI().equals(uri)) {
                            ret.append("selected=\"selected\"");
                        }
                        ret.append(">" + sob.getDisplayName(lang) + "</option>");
//                        }
                    }
                }
                ret.append("</select>");
            }
            else if (mode.equals("view"))
            {
                ret.append("<span name=\"" + name + "\">" + value + "</span>");
            }
        }
        return ret.toString();
    }

    @Override
    public boolean filterObject(HttpServletRequest request, SemanticObject base_obj, SemanticObject filter_obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
        boolean exclude = true;
        if(filter_obj!=null) {
            GenericObject gobj = filter_obj.createGenericInstance();
            if(gobj instanceof SWBClass) {
                exclude = !((SWBClass)filter_obj.createGenericInstance()).isValid();
            }else {
                if(gobj instanceof Activeable) {
                    exclude = !((Activeable)gobj).isActive();
                }
                if(!exclude && (gobj instanceof Trashable)) {
                    exclude = ((Trashable) gobj).isDeleted();
                }
            }
            exclude =  exclude || base_obj.equals(filter_obj);
        }
//        if(!exclude) {
//            SemanticProperty prp;
//            SemanticClass cls = prop.getRangeClass();
//            Iterator<SemanticProperty> it = base_obj.getSemanticClass().listProperties();
//            while(it.hasNext() && !exclude) {
//                prp = it.next();
//                if(prp.isObjectProperty() && prp.getRangeClass().equals(cls) && base_obj.getObjectProperty(prp)!=null) {       
//                    exclude =  base_obj.getObjectProperty(prp).equals(filter_obj);
//                }
//            }
//        }
        return exclude;
    }
}
