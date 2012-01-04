package org.semanticwb.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
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
            String mode, String lang)
    {
        if (type.equals("dojo"))
        {
            setAttribute("isValid",
                    "return validateElement('" + propName + "','" + getValidateURL(obj, prop)
                    + "',this.textbox.value);");
        } else
        {
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
            throws FormValidateException
    {
        super.validate(request, obj, prop, propName);

        String value = request.getParameter(propName);

        if (value != null && value.indexOf(" ") >= 0)
        {
            throw new FormValidateException("No se permiten espacios:" + value);
        }

        if (value != null)
        {
            int l = value.length();
            
            String regExp=getRegExp();
            
            if(regExp!=null)
            {
                if(!value.matches(regExp))
                {
                    throw new FormValidateException("String no cumple con la expresion regular");
                }
            }

            if (isRestrict4Id())
            {
                for (int x = 0; x < l; x++)
                {
                    char ch = value.charAt(x);
                    if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z')
                            || (ch >= 'A' && ch <= 'Z') || ch == '_' || ch == '/' || ch == '-')
                    {
                        //pasa caracter
                    } else
                    {
                        throw new FormValidateException("No es permitido la letra " + ch + " en el Id del elemento.");
                    }
                }
            }

            String reservedWords = getReservedWords();
            if (null != reservedWords)
            {
                HashMap<String, String> hsres = new HashMap<String, String>();
                StringTokenizer stoken = new StringTokenizer(reservedWords, ",");
                while (stoken.hasMoreTokens())
                {
                    String token = stoken.nextToken();
                    hsres.put(token, token);
                }
                if (hsres.get(value) != null) // encontro palabra reservada
                {
                    throw new FormValidateException("No se permiten es texto reservado:" + value);
                }
            }

            SemanticClass sclass = prop.getDomainClass();
            Iterator<SemanticObject> itso = getModel().listInstancesOfClass(sclass);
            while (itso.hasNext())
            {
                SemanticObject sObj = itso.next();
                if (obj != null && sObj.equals(obj))
                {
                    continue;
                }
                String soval = sObj.getProperty(prop);
                if (soval != null && value.equals(soval))
                {
                    //existe texto igual
                    throw new FormValidateException("Ya existe Texto(URL):" + value);
                }
            }
        }

    }
}
