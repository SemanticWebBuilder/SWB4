package org.semanticwb.model;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class UniqueTextElement extends org.semanticwb.model.base.UniqueTextElementBase 
{
    /** The log. */
    private static Logger log = SWBUtils.getLogger(UniqueTextElement.class);

    public UniqueTextElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.Text#renderElement(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type,
                                String mode, String lang) {
        if (type.equals("dojo")) {
            setAttribute("isValid",
                         "return validateElement('" + propName + "','" + getValidateURL(obj, prop)
                         + "',this.textbox.value);");
        } else {
            setAttribute("isValid", null);
        }

        return super.renderElement(request, obj, prop, propName, type, mode, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.FormElementBase#validate(javax.servlet.http.HttpServletRequest, org.semanticwb.platform.SemanticObject, org.semanticwb.platform.SemanticProperty)
     */
    /**
     * Validate.
     *
     * @param request the request
     * @param obj the obj
     * @param prop the prop
     * @throws FormValidateException the form validate exception
     */
    @Override
    public void validate(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName)
            throws FormValidateException {
        super.validate(request, obj, prop, propName);

        String value = request.getParameter(propName);

       // System.out.println("validate:"+value);

        if(value!=null&&value.indexOf(" ")>=0)
        {
            throw new FormValidateException(getLocaleString("error", "No se permiten espacios:") + value);
        }

        if(value!=null)
        {
            SemanticClass sclass = prop.getDomainClass();
            Iterator<SemanticObject> itso = getModel().listInstancesOfClass(sclass);
            while (itso.hasNext()) {
                SemanticObject sObj = itso.next();
                if(obj!=null&&sObj.equals(obj)) continue;
                String soval = sObj.getProperty(prop);
                if(soval!=null&&value.equals(soval))
                {
                    //existe texto igual
                    throw new FormValidateException(getLocaleString("error", "Ya existe Texto(URL):") + value);
                }
            }
        }

    }


}
