package org.semanticwb.bsc.formelement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class Periodicity extends org.semanticwb.bsc.formelement.base.PeriodicityBase 
{
    private static Logger log = SWBUtils.getLogger(Periodicity.class);
    private static final String formatPattern = "yyyy-MM-dd";
    private static SimpleDateFormat format = new SimpleDateFormat(formatPattern);
    
    public Periodicity(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
        System.out.println("\n\nPeriodicity...........");
    }
    
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang)
    {
        if (obj == null) {
            obj = new SemanticObject();
        }

        // boolean IPHONE=false;
        // boolean XHTML=false;
        boolean DOJO = false;

        // if(type.equals("iphone"))IPHONE=true;
        // else if(type.equals("xhtml"))XHTML=true;
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
            if (imsg == null) {
                if (required) {
                    imsg = label + " es requerido.";

                    if (lang.equals("en")) {
                        imsg = label + " is required.";
                    }
                } else {
                    imsg = "Formato invalido.";

                    if (lang.equals("en")) {
                        imsg = "Invalid Format.";
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

        String value = request.getParameter(propName);
        if (value == null) {
            Date dt = obj.getDateProperty(prop);

            if (dt != null) {
                value = format.format(dt);
            }
        }

        if (value == null) {
            value = "";
        }
        
if(type.equals("dojo")) {
    setAttribute("isValid",
                 "return validateElement('" + propName + "','" + getValidateURL(obj, prop)
                 + "',this.textbox.value);");
}else {
    setAttribute("isValid", null);
}

        if (mode.equals("edit") || mode.equals("create")) {
            ret.append("<input name=\"" + name + "\" value=\"" + value + "\"");

            if (DOJO) {
                ret.append(" dojoType=\"dijit.form.DateTextBox\"");
                ret.append(" required=\"" + required + "\"");
                ret.append(" promptMessage=\"" + pmsg + "\"");
                ret.append(" invalidMessage=\"" + imsg + "\"");

                if (getConstraints() != null) {
                    ret.append(" constraints=\"" + getConstraints() + "\"");
                }
                
                if(getDateId()!=null) ret.append(" id=\"" + getDateId() + "\"");
                if(getDateOnChange()!=null) ret.append(" onchange=\"" + getDateOnChange() + "\"");
            }

            ret.append(" " + getAttributes());

            if (DOJO) {
                ret.append(" trim=\"true\"");
            }

            ret.append(ext);
            ret.append("/>");
        } else if (mode.equals("view")) {
            ret.append("<span name=\"" + name + "\">" + value + "</span>");
        }
//System.out.println("\n\n"+ret);
        return ret.toString();
    }
    
    @Override
    public void validate(javax.servlet.http.HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) throws FormValidateException
    {
System.out.print("validate_...");
System.out.println(", obj="+obj+", id="+obj.getId());
        String value = request.getParameter(propName);
//System.out.println("value="+value);
        Date date = null;
        try {
            date = format.parse(value);
        }catch(ParseException pe) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                date = format.parse(value);
            }catch(ParseException p) {
                throw new FormValidateException("El valor es incorrecto");
            }
        }

//        System.out.print(" obj="+obj);
//        System.out.print(" prop="+prop);
//        System.out.println(", propName="+propName);
//        System.out.println(", semanticClass()="+obj.getSemanticClass());
//        System.out.println(", model="+(SWBModel)obj.getModel().getModelObject().createGenericInstance());
        SWBModel model = (SWBModel)obj.getModel().getModelObject().createGenericInstance();
        Iterator<Period> iperiods = Period.ClassMgr.listPeriods(model);
        while(iperiods.hasNext()) {
            Period p = iperiods.next();
//System.out.println("p="+p);
            if( obj.equals(p) ) {
                continue;
            }
            if(p.getStart().before(date) && p.getEnd().after(date)) {
                throw new FormValidateException("fecha invalida");
            }
        }
//System.out.println("al parece todo salio bien");
    }
    
    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName)
    {
//System.out.println("\n\nprocess........................");
//        try
//        {
            //validate(request, obj, prop, propName);
            String value = request.getParameter(propName);
            Date fvalue = null;
            try
            {
                fvalue = format.parse(value);                    
                obj.setDateProperty(prop, fvalue);
            }
            catch(Exception e)
            {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    fvalue = format.parse(value);                    
                    obj.setDateProperty(prop, fvalue);
                }catch(Exception p) {
                }
            }
//        }
//        catch(Exception e)
//        {
//            String value = request.getParameter(propName);
//            value = "";
//System.out.println("value "+value+" deberia ser blanco");
//        }
    }
    
    @Override
    public String getConstraints() {
        String ret = super.getConstraints();

        if (ret != null) {
            ret = SWBUtils.TEXT.replaceAll(ret, "{today}", SWBUtils.TEXT.getStrDate(new Date(), "es", "yyyy-mm-dd"));    // 2006-12-31
        }
        return ret;
    }
}
