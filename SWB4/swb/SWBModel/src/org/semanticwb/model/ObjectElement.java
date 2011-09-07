package org.semanticwb.model;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * Elemento que sirve para crear instancias de objetos y relacionarlos al actual 
   */
public class ObjectElement extends org.semanticwb.model.base.ObjectElementBase 
{
    public ObjectElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

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
                                String mode, String lang)
    {
        if (obj == null) {
            obj = new SemanticObject();
        }

        //boolean IPHONE = false;
        //boolean XHTML  = false;
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

        SemanticClass cls=obj.getSemanticClass();

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);

            pmsg         = dobj.getPromptMessage();
            imsg         = dobj.getInvalidMessage();
            selectValues = dobj.getDisplaySelectValues(lang);
            disabled     = dobj.isDisabled();
        }

        if (prop.isObjectProperty())
        {
            if(prop.getCardinality()!=1)
            {
                SemanticClass cls2=prop.getRangeClass();
                Iterator<SemanticObject> it=obj.listObjectProperties(prop);
                ret.append("<span>");

                String url="/swbadmin/jsp/SemObjectEditor.jsp";
                url+="?scls="+cls2.getEncodedURI()+"&sref="+obj.getEncodedURI()+"&sprop="+prop.getEncodedURI()+"&chcls=true";

                ret.append("<a href=\"" + url + "\" onclick=\"javascript:showDialog('" + url
                           + "', '" + getLocaleString("add",lang)+" "+cls2.getDisplayName(lang) + "');return false;\">"
                           + getLocaleString("add",lang)+" "+cls2.getDisplayName(lang) + "</a>");
                ret.append("<br/>");

                while (it.hasNext())
                {
                    SemanticObject value = it.next();
                    ret.append("<div style=\"background-color:#FFFFFF; cursor:text; font:11px; width:400px; border:#A0A0FF solid 1px;\">");
                    ret.append("<a href=\"?suri=" + value.getEncodedURI() + "\" onclick=\"addNewTab('" + value.getURI()
                               + "', null, '" + value.getDisplayName(lang) + "');return false;\">"
                               + value.getDisplayName() + "</a>");
                    ret.append("</div>");
                    ret.append("<br/>");
                }
                ret.append("</span>");
            }else
            {
                SemanticObject value=obj.getObjectProperty(prop);

                if(value!=null)
                {
                    ret.append("<span>");

                    ret.append("<a href=\"?suri=" + value.getEncodedURI() + "\" onclick=\"addNewTab('" + value.getURI()
                               + "', null, '" + value.getDisplayName(lang) + "');return false;\">"
                               + value.getDisplayName() + "</a>");

                    ret.append("</span>");
                }else
                {
                    SemanticClass cls2=prop.getRangeClass();
                    
                    String url="/swbadmin/jsp/SemObjectEditor.jsp";
                    url+="?scls="+cls2.getEncodedURI()+"&sref="+obj.getEncodedURI()+"&sprop="+prop.getEncodedURI()+"&chcls=true";

                    ret.append("<a href=\"" + url + "\" onclick=\"javascript:showDialog('" + url
                               + "', '" + getLocaleString("add",lang)+" "+cls2.getDisplayName(lang) + "');return false;\">"
                               + getLocaleString("add",lang)+" "+cls2.getDisplayName(lang) + "</a>");
                    ret.append("<br/>");
                }
            }
        }

        // System.out.println("ret:"+ret);
        return ret.toString();
    }

}
