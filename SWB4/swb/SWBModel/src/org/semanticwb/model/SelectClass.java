package org.semanticwb.model;

import java.util.Comparator;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class SelectClass extends org.semanticwb.model.base.SelectClassBase 
{
    public SelectClass(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String type,
                                String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }

//        boolean IPHONE = false;
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

        StringBuffer   ret          = new StringBuffer();
        String         name         = prop.getName();
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

/*
        System.out.println("prop:"+prop);
        System.out.println("sobj:"+sobj);
        System.out.println("selectValues:"+selectValues);
*/
        if (DOJO)
        {
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

        if (prop.isObjectProperty())
        {
            SemanticObject val = null;
            String         aux = request.getParameter(prop.getName());

            if (aux != null) {
                val = SemanticObject.createSemanticObject(aux);
            } else {
                val = obj.getObjectProperty(prop);
            }

            String uri   = "";
            String value = "";

            if (val != null) {
                uri   = val.getURI();
                value = obj.getResId();
            }

            if (mode.equals("edit") || mode.equals("create")) {
                ret.append("<select name=\"" + name + "\"");

                if (DOJO) {
                    ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\""
                               + imsg + "\"");
                }

                ret.append(" required=\"" + required + "\"");

                if (((uri == null) || (uri.length() == 0))) {
                    ret.append(" displayedvalue=\"\"");
                }

                ret.append(" " + ext + ">");

                ret.append("<option");
                ret.append(" value=\"\"></option>");

                SemanticClass            cls = prop.getRangeClass();
                Iterator<SemanticObject> it  = null;

                Comparator comp=new Comparator<SemanticObject>()
                {
                    public int compare(SemanticObject o1, SemanticObject o2)
                    {
                        //System.out.println(o1+" "+o2);
                        String s1=o1.getResId();
                        String s2=o2.getResId();
                        return s1.compareTo(s2);
                    }
                };

                if (cls != null) {
                    it = SWBComparator.sortSermanticObjects(comp, cls.listInstances());
                } else {
                    it = SWBComparator.sortSermanticObjects(comp,SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClassesAsSemanticObjects());
                }

                if (it != null) {
                    while (it.hasNext()) {
                        SemanticObject sob = it.next();

                        // System.out.println("display:"+sob.getDisplayName(lang));
                        if (sob.getURI() != null) {
                            ret.append("<option value=\"" + sob.getURI() + "\" ");

                            if (sob.getURI().equals(uri)) {
                                ret.append("selected");
                            }

                            ret.append(">" + sob.getResId() + "</option>");
                        }
                    }
                }

                ret.append("</select>");
            } else if (mode.equals("view")) {
                ret.append("<span _id=\"" + name + "\" name=\"" + name + "\">" + value + "</span>");
            }
        }else
        {
            ret.append("Error: This mode is not supported...");
        }

        return ret.toString();
    }

}
