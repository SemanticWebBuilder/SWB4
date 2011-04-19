package org.semanticwb.model;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class IDTextElement extends org.semanticwb.model.base.IDTextElementBase 
{
     /** The log. */
    private static Logger log = SWBUtils.getLogger(IDTextElement.class);

    public IDTextElement(org.semanticwb.platform.SemanticObject base)
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

        if(value!=null&&value.indexOf(" ")>=0)
        {
            throw new FormValidateException("No se permiten espacios en blanco.");
        }

        if(value!=null)
        {

            int l = value.length();
            for (int x = 0; x < l; x++)
            {
                char ch = value.charAt(x);
                if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z')
                        || (ch >= 'A' && ch <= 'Z') || ch == '_')
                {
                    //pasa caracter
                } else
                {
                    throw new FormValidateException("No es permitido la letra " + ch +" en el Id del elemento.");
                }
            }

        }

    }


}
