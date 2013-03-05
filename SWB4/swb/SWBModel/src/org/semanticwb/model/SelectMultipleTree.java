package org.semanticwb.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * SelectMultipleTree 
   */
public class SelectMultipleTree extends org.semanticwb.model.base.SelectMultipleTreeBase 
{
    public SelectMultipleTree(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
/*
    private String addPage(WebPage page, String selected, String lang, String separator)
    {
        String ret="<option value=\""+page.getURI()+"\" ";
        if(page.getURI().equals(selected))ret+="selected";
        ret+=">"+separator+page.getDisplayName(lang)+"</option>";

        if(separator.length()==0)separator=">";

        Iterator<WebPage> it=page.listVisibleChilds(lang);
        while(it.hasNext())
        {
            WebPage child=it.next();
            ret+=addPage(child,selected,lang,"--"+separator);
        }
        return ret;
    }
*/
    

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
    
    
    /**
 * Adds the object.
 * 
 * @param obj the obj
 * @param selected the selected
 * @param lang the lang
 * @param separator the separator
 * @return the string
 */
private String addObject(SemanticObject obj, ArrayList<String> vals, String lang, String separator) {
        String ret = "<option value=\"" + obj.getURI() + "\" ";

        if (vals.contains(obj.getURI())) {
            ret += "selected";
        }

        ret += ">" + separator + obj.getDisplayName(lang) + "</option>";

        if (separator.length() == 0) {
            separator = ">";
        }

        Iterator<SemanticObject> it = obj.listHerarquicalChilds();

        while (it.hasNext()) {
            SemanticObject child = it.next();

            ret += addObject(child, vals, lang, "--" + separator);
        }

        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.SelectOne#renderElement(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type,
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
            if (required && imsg == null) {
                imsg = label + " es requerido.";

                if (lang.equals("en")) {
                    imsg = label + " is required.";
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

            String value = obj.getDisplayName(lang);

            if (mode.equals("edit") || mode.equals("create")) {
                ret.append("<select name=\"" + name + "\""+" multiple=\"true\"");
                ret.append(" style=\"width:300px;\"");

                if (DOJO) {
                //    ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\""+ imsg + "\"");
                }

                ret.append(" required=\"" + required + "\"");

//                if (isNullSupport() && ((uri == null) || (uri.length() == 0))) {
//                    ret.append(" displayedvalue=\"\"");
//                }

                ret.append(" " + ext + ">");

                // onChange="dojo.byId('oc1').value=arguments[0]"
//                if (isNullSupport()) {
//                    ret.append("<option value=\"\"></option>");
//                }

                SemanticClass            cls = prop.getRangeClass();
                Iterator<SemanticObject> it  = null;

                if (cls != WebPage.sclass) {
                    if (isGlobalScope()) {
                        if (cls != null) {
                            it = SWBComparator.sortSemanticObjects(lang, cls.listInstances());
                        } else {
                            it = SWBComparator.sortSemanticObjects(
                                lang,
                                SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClassesAsSemanticObjects());
                        }
                    } else {
                        it = SWBComparator.sortSemanticObjects(lang, getModel().listInstancesOfClass(cls));
                    }

                    boolean hp = false;

                    if (cls != null) {
                        hp = cls.hasHerarquicalProperties();
                    }

                    // System.out.println("cls:"+cls+" hp:"+hp+" "+cls.hasHerarquicalProperties()+" "+cls.hasInverseHerarquicalProperties());
                    while (it.hasNext()) {
                        SemanticObject sob = it.next();
                        
                        boolean deleted=false;
                        if(sob.instanceOf(Trashable.swb_Trashable))
                        {
                            deleted=sob.getBooleanProperty(Trashable.swb_deleted);
                        }

                        if(!deleted)
                        {                        

                            if (hp) {
                                if (!sob.hasHerarquicalParents()) {
                                    ret.append(addObject(sob, vals, lang, ""));
                                }
                            } else {
                                ret.append("<option value=\"" + sob.getURI() + "\" ");

                                if (vals.contains(sob.getURI())) {
                                    ret.append("selected=\"selected\"");
                                }

                                ret.append(">" + sob.getDisplayName(lang) + "</option>");
                            }
                        }
                    }
                } else {
                    WebSite site = SWBContext.getWebSite(getModel().getName());

                    if (site != null) {
                        WebPage home = site.getHomePage();

                        ret.append(addObject(home.getSemanticObject(), vals, lang, ""));
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

                ret.append("<select name=\"" + name + "\""+" multiple=\"true\"");
                ret.append(" style=\"width:300px;\"");

                if (DOJO) {
                //    ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"" + imsg + "\"");
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
            }
        }

        return ret.toString();
    }    
    
}
