package org.semanticwb.bsc.formelement;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


   /**
   * Campo de texto para valores ordinales que no se repiten para instancias de la misma clase 
   */
public class Ordinal extends org.semanticwb.bsc.formelement.base.OrdinalBase 
{
    static Logger log = SWBUtils.getLogger(Ordinal.class);
    
    public Ordinal(org.semanticwb.platform.SemanticObject base)
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
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }
        boolean DOJO   = false;
        
        if (type.equals("dojo")) {
            DOJO = true;
        }

        StringBuilder   ret      = new StringBuilder();
        String         name     = propName;
        String         label    = prop.getDisplayName(lang);
        SemanticObject sobj     = prop.getDisplayProperty();
        boolean        required = prop.isRequired();
        String         pmsg     = null;
        String         imsg     = null;
        boolean        disabled = false;

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);

            pmsg     = dobj.getPromptMessage();
            imsg     = dobj.getInvalidMessage();
            disabled = dobj.isDisabled();
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
        
        String ext = disabled ? " disabled=\"disabled\"":"";
        
        String value = request.getParameter(propName);
        if(value == null)
        {
            value = obj.getProperty(prop);
        }
        if(value == null)
        {
            value = "";
        }
        
if(type.equals("dojo")) {
    setAttribute("isValid",
                 "return validateElement('" + propName + "','" + getValidateURL(obj, prop)
                 + "',this.textbox.value);");
}else {
    setAttribute("isValid", null);
}
        
        if(mode.equals("edit") || mode.equals("create")) {
            ret.append("<input name=\"").append(name).append("\" size=\"30\" value=\"").append(value).append("\"");

            if(DOJO)
            {
                ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
            }

            if(DOJO)
            {
                ret.append(" required=\"").append(required).append("\"");
            }

            if(DOJO)
            {
                ret.append(" promptMessage=\"").append(pmsg).append("\"");
            }
            
            if(DOJO)
            {
                ret.append(((getRegExp() != null)
                            ? (" regExp=\"" + getRegExp() + "\"")
                            : ""));
            }
            
            if(DOJO)
            {
                ret.append(" invalidMessage=\"").append(imsg).append("\"");
            }

            ret.append(" ").append(getAttributes());

            if(getMaxLength()>0)
            {
                ret.append(" maxlength=\"").append(getMaxLength()).append("\"");
            }
            
            if(DOJO)
            {
                ret.append(" trim=\"true\"");
            }

            ret.append(" style=\"width:300px;\"");
            ret.append(ext);
            ret.append("/>");

            if(DOJO)
            {
                if(!mode.equals("create") && prop.isLocaleable() && !obj.isVirtual())
                {
                    ret.append(" <a href=\"#\" onClick=\"javascript:showDialog('").append(SWBPlatform.getContextPath()).append("/swbadmin/jsp/propLocaleEdit.jsp?suri=").append(obj.getEncodedURI()).append("&prop=").append(prop.getEncodedURI()).append("','Idiomas de la Propiedad ").append(prop.getDisplayName(lang)).append("');\">locale</a>");
                }
            }
        }
        else if (mode.equals("view"))
        {
            ret.append("<span name=\"").append(name).append("\">").append(value).append("</span>");
        }
        return ret.toString();
    }
        
    @Override
    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) throws FormValidateException
    {
        int ordinal;
        try            
        {
            String value = request.getParameter(propName);
            ordinal = Integer.parseInt(value);
        }   
        catch(Exception pe)
        {            
            throw new FormValidateException("El valor debe ser numérico y no puede repetirse");
        }
        
        SWBModel model = (SWBModel)obj.getModel().getModelObject().createGenericInstance();
        Iterator<SemanticObject> it = model.getSemanticModel().listInstancesOfClass(obj.getSemanticClass());
        while(it.hasNext()) {
            SemanticObject so = it.next();
            if(ordinal == so.getIntProperty(prop))
            {
                throw new FormValidateException("El valor debe ser numérico y no puede repetirse");
            }
        }        
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName)
    {
        try
        {
            validate(request, obj, prop);
            String value = request.getParameter(propName);
            try
            {
                int fvalue = Integer.parseInt(value);
                obj.setIntProperty(prop, fvalue);
            }
            catch(Exception e)
            {
                log.error(e);
            }
        }
        catch(Exception e)
        {
            log.error(e);
        }
    }
}
