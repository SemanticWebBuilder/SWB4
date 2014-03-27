package org.semanticwb.bsc.formelement;

import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormElementURL;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.Trashable;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class SelectUserGroup extends org.semanticwb.bsc.formelement.base.SelectUserGroupBase {

    public SelectUserGroup(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        return super.renderElement(request, obj, prop, type, mode, lang); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
        final String Mode_RELOAD = "reload";

        FormElementURL urlFE = getRenderURL(obj, prop, type, mode, lang);
        urlFE.setParameter("modeTmp", Mode_RELOAD);

        //String uriSlave = request.getParameter("uriSlave");

        if (obj == null) {
            obj = new SemanticObject();
        }

        boolean DOJO = false;
        if (type.equals("dojo")) {
            DOJO = true;
        }

        StringBuilder ret = new StringBuilder(256);
        String name = propName;
        String label = prop.getDisplayName(lang);
        SemanticObject sobj = prop.getDisplayProperty();
        boolean required = prop.isRequired();
        String pmsg = null;
        String imsg = null;
        String selectValues = null;
        boolean disabled = false;

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);

            pmsg = dobj.getPromptMessage();
            imsg = dobj.getInvalidMessage();
            selectValues = dobj.getDisplaySelectValues(lang);
            disabled = dobj.isDisabled();
        }

        /*
         System.out.println("prop:"+prop);
         System.out.println("sobj:"+sobj);
         System.out.println("selectValues:"+selectValues);
         */
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
            SemanticObject val = null;
            String aux = request.getParameter(propName);

            if (aux != null) {
                val = SemanticObject.createSemanticObject(aux);
            } else {
                val = obj.getObjectProperty(prop);
            }

            String uri = "";
            String value = "";

            if (val != null) {
                uri = val.getURI();
                value = val.getDisplayName(lang);
            }

            if (mode.equals("edit") || mode.equals("create") || mode.equals("filter")) {



                ret.append("<select id=\"" + name + "\" name=\"" + name + "\" onChange=\"processUrl('");
                ret.append(urlFE);
                ret.append("', this);return false;\"");
                if (DOJO) {
                    ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\""
                            + imsg + "\"" + " value=\"" + uri + "\"");
                }

                if (!mode.equals("filter")) {
                    if (required) {
                        ret.append(" required=\"" + required + "\"");
                    }
                }

                if ((mode.equals("filter") || isBlankSuport()) && ((uri == null) || (uri.length() == 0))) {
                    ret.append(" displayedvalue=\"\"");
                }

                ret.append(" " + ext + ">");


                // onChange="dojo.byId('oc1').value=arguments[0]"
                if ((mode.equals("filter") || isBlankSuport())) {
                    ret.append("<option");

                    // if(uri==null || uri.length()==0)ret.append(" selected");
                    ret.append(" value=\"\"></option>");
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
                } else {
                    SemanticModel model = getModel();
                    SWBModel m = (SWBModel) model.getModelObject().createGenericInstance();
                    if (m.getParentWebSite() != null) {
                        m = m.getParentWebSite();
                    }
                    model = m.getSemanticModel();

                    it = SWBComparator.sortSemanticObjects(lang, model.listInstancesOfClass(cls));
                }

                if (it != null) {
                    while (it.hasNext()) {
                        SemanticObject sob = it.next();
                        boolean deleted = false;
                        if (sob.instanceOf(Trashable.swb_Trashable)) {
                            deleted = sob.getBooleanProperty(Trashable.swb_deleted);
                        }

                        if (filterObject(request, sobj, sob, prop, propName, type, mode, lang)) {
                            continue;
                        }

                        if (!deleted) {
                            // System.out.println("display:"+sob.getDisplayName(lang));
                            if (sob.getURI() != null) {
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
                ret.append("<script type=\"text/javascript\">\n");
                ret.append("\nfunction processUrl(url, selectArea)");
                ret.append("\n{");
                ret.append("\n if(selectArea.value != ''){");
                ret.append("\n    dojo.xhrPost({");
                ret.append("\n        url: url+'&uriSlave='+encodeURIComponent(selectArea.value),");
                ret.append("\n        id: selectArea,");
                ret.append("\n        load: function(response)");
                ret.append("\n        {");
                ret.append("\n                 document.getElementById('div" + getUsers_ById() + "').innerHTML = response;");
                ret.append("\n        },");
                ret.append("\n        error: function(response)");
                ret.append("\n        {");
                ret.append("\n            return response;");
                ret.append("\n        },");
                ret.append("\n        handleAs: \"text\"");
                ret.append("\n    });");
                ret.append("\n }");
                ret.append("\n}");
                ret.append("</script>\n");
               
                
                if (request.getParameter("modeTmp") != null && request.getParameter("uriSlave") !=null) {
                    ret = new StringBuilder(256);
                     String idGroup = request.getParameter("uriSlave");
                    
                    SemanticObject semUri = SemanticObject.createSemanticObject(idGroup);
                    GenericObject generic = semUri.createGenericInstance();
                    UserGroup userg = null;
                    if (generic instanceof UserGroup) {
                        userg = (UserGroup) generic;
                    }
                  
            String uriClass = obj.getSemanticClass().getURI();
            uriClass = uriClass.substring(0, uriClass.indexOf("#") + 1);
            org.semanticwb.platform.SemanticProperty bsc_chief = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uriClass + getUsers_ById());
                    
                   String nameProperty = bsc_chief.getName();
                    if (userg != null) {

                        Iterator<User> itUsers = userg.listUsers();
                        if (itUsers.hasNext()) {

                            ret.append("<select name=\"" + nameProperty + "\" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\""
                                    + imsg + "\">");
                            while (itUsers.hasNext()) {
                                User userD = itUsers.next();
                                ret.append("<option value=\"" + userD.getURI() + "\">" + userD.getFullName() + "</option>");
                            }
                            ret.append("</select>");
                        }
                    }
                }

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

                    if (DOJO) {
                        ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\""
                                + imsg + "\"");
                    }

                    ret.append(" " + ext + ">");

                    StringTokenizer st = new StringTokenizer(selectValues, "|");

                    while (st.hasMoreTokens()) {
                        String tok = st.nextToken();
                        int ind = tok.indexOf(':');
                        String id = tok;
                        String val = tok;

                        if (ind > 0) {
                            id = tok.substring(0, ind);
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
                        int ind = tok.indexOf(':');
                        String id = tok;
                        String val = tok;

                        if (ind > 0) {
                            id = tok.substring(0, ind);
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
        //return super.renderElement(request, obj, prop, propName, type, mode, lang); //To change body of generated methods, choose Tools | Templates.
    }
}
