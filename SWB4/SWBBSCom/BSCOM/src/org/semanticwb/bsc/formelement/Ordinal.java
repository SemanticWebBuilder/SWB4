package org.semanticwb.bsc.formelement;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.Sortable;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 * Ordinal es un FormElement para manejar valores ordinales para las
 * instancias de una misma clase semántica.
 * Los valores ordinales para las instancias de una misma clase nos únicos e
 * irrepetibles. Es decir, dos instancias de una misma clase no pueden tener
 * como valor ordinal el mismo número.
 * Las instancias de cada clase semántica maneja su propio orden de valores.
 * Así, las instancias de clases difenrentes manejan su propia lista de
 * valores ordinales.
 * 
 * @author      Carlos Ramos Incháustegui
 * @version     %I%, %G%
 * @since       1.0
 */
public class Ordinal extends org.semanticwb.bsc.formelement.base.OrdinalBase 
{
    private static Logger log = SWBUtils.getLogger(Ordinal.class);
    
    public Ordinal(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
        if (obj == null) {
            obj = new SemanticObject();
        }
        
        boolean dojo = type.equals("dojo");

        StringBuilder  ret      = new StringBuilder();
        String         name     = propName;
        String         label    = prop.getDisplayName(lang);
        SemanticObject sobj     = prop.getDisplayProperty();
        boolean        required = prop.isRequired();
        String         pmsg     = null;
        String         imsg     = null;
        boolean        disabled = false;

        if (sobj != null) {
            DisplayProperty dobj = new DisplayProperty(sobj);
            pmsg = dobj.getPromptMessage(lang);
            imsg = dobj.getInvalidMessage(lang);
            disabled = dobj.isDisabled();
        }
        
        if (dojo) {
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
        
        String value = null;
        if(obj.getProperty(prop)!=null) {
            value = obj.getProperty(prop);
        }else {
            value = request.getParameter(propName)==null?"":request.getParameter(propName);
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
            if(dojo)
            {
                ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
                ret.append(" required=\"").append(required).append("\"");
                ret.append(" promptMessage=\"").append(pmsg).append("\"");
                ret.append((getRegExp() != null ? (" regExp=\"" + getRegExp() + "\"") : ""));
                ret.append(" invalidMessage=\"").append(imsg).append("\"");
                ret.append(" trim=\"true\"");
                ret.append(" ").append(getAttributes());
                ret.append(" style=\"width:300px;\"");
                ret.append(ext);
                
            }
            if(getMaxLength()>0) {
                ret.append(" maxlength=\"").append(getMaxLength()).append("\"");
            }
            ret.append("/>");
            
            if(!mode.equals("create") && prop.isLocaleable() && !obj.isVirtual()) {
                ret.append(" <a href=\"#\" onClick=\"javascript:showDialog('").append(SWBPlatform.getContextPath()).append("/swbadmin/jsp/propLocaleEdit.jsp?suri=").append(obj.getEncodedURI()).append("&prop=").append(prop.getEncodedURI()).append("','Idiomas de la Propiedad ").append(prop.getDisplayName(lang)).append("');\">locale</a>");
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
        org.semanticwb.bsc.Sortable sortable = (Sortable) obj.getGenericInstance();
        sortable.validOrder(request, prop, propName);
//        int ordinal;
//        try {
//            String value = request.getParameter(propName);
//            ordinal = Integer.parseInt(value);
//        }catch(NumberFormatException pe) {            
//            throw new FormValidateException("El valor debe ser numérico y no debe repetirse");
//        }
//        
//        SWBModel model = (SWBModel)obj.getModel().getModelObject().createGenericInstance();
//        Iterator<SemanticObject> it = model.getSemanticModel().listInstancesOfClass(obj.getSemanticClass());
//        while(it.hasNext()) {
//            SemanticObject so = it.next();
//            if( obj.equals(so) ) {
//                continue;
//            }
//            if(ordinal == so.getIntProperty(prop))
//            {
//                throw new FormValidateException("El valor debe ser numérico y no puede repetirse");
//            }
//        }        
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName)
    {
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
}
