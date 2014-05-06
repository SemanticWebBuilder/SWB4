package org.semanticwb.bsc.formelement;

import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class SelectUsers extends org.semanticwb.bsc.formelement.base.SelectUsersBase {

    public SelectUsers(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type, String mode, String lang) {
        return super.renderElement(request, obj, prop, type, mode, lang); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {        String uriClass = obj.getSemanticClass().getURI();
        uriClass = uriClass.substring(0, uriClass.indexOf("#") + 1);
        org.semanticwb.platform.SemanticProperty bsc_area = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uriClass + getAreaSelect());


        if (obj == null) {
            obj = new SemanticObject();
        }
        boolean DOJO = false;
        if (type.equals("dojo")) {
            DOJO = true;
        }

        StringBuilder ret = new StringBuilder();
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
                ret.append("<div id=\"div"+propName+"\">");
                ret.append("<select name=\"" + name + "\"");

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

                if ((mode.equals("filter") || isBlankSuport())) {
                    ret.append("<option");
                    ret.append(" value=\"\"></option>");
                }
                SemanticObject selectedArea = obj.getObjectProperty(bsc_area);
                if (selectedArea != null && selectedArea.getGenericInstance() instanceof UserGroup) {
                    Iterator<User> it = null;
                    UserGroup area = (UserGroup) selectedArea.getGenericInstance();
                    it = area.listUsers();

                    if (it != null) {
                        while (it.hasNext()) {
                            User userIt = it.next();
                            if (userIt.getURI() != null) {
                                ret.append("<option value=\"" + userIt.getURI() + "\" ");

                                if (uri != null && userIt.getURI().equals(uri)) {
                                    ret.append("selected=\"selected\"");
                                }

                                ret.append(">" + userIt.getFullName() + "</option>");
                            }

                        }
                    }
                } else {
                    ret.append("<option value=\"\" selected>Selecciona un area...</option>");
                }

                ret.append("</select>");
                ret.append("</div>");
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
