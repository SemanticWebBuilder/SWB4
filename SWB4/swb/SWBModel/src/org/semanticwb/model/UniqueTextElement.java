/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
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

//        if (value != null && value.indexOf(" ") >= 0)
//        {
//            throw new FormValidateException("No se permiten espacios:" + value);
//        }

        System.out.println("obj:"+obj.getURI()+" prop:"+prop+" propName:"+propName+" value:"+value);
        
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
            Iterator<SemanticObject> itso = obj.getModel().listSubjects(prop, value);
            
            System.out.println("sclass:"+sclass.getURI()+" model1:"+obj.getModel().getName()+" model2:"+getModel().getName());
            
            while (itso.hasNext())
            {
                SemanticObject sObj = itso.next(); 
                System.out.println("sObj:"+sObj);
                if(!obj.getSemanticClass().equals(sObj.getSemanticClass()))
                {
                    continue;
                }
                if (obj != null && sObj.equals(obj))
                {
                    continue;
                }                
                System.out.println("Ya existe:"+sObj);
                //existe texto igual
                throw new FormValidateException("Ya existe Texto(URL):" + value);
            }
        }

    }
}
