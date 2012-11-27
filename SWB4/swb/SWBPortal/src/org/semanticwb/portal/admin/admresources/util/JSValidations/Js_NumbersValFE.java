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
package org.semanticwb.portal.admin.admresources.util.JSValidations;

import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.lib.WBJsValidationsFEAbs;

// TODO: Auto-generated Javadoc
/**
 * Objeto que valida si la entrada a un campo html es de tipo n�merico con JavaScript
 * <p>
 * Object that verifies if a html field has a numeric value with JavaScript.
 * 
 * @author  Jorge Alberto Jim�nez
 */

public class Js_NumbersValFE extends WBJsValidationsFEAbs
{

    /**
     * Instantiates a new js_ numbers val fe.
     * 
     * @param field the field
     * @param minsize the minsize
     */
    public Js_NumbersValFE(String field, int minsize)
    {
        this.field = field;
        if(minsize > 1)
            this.minsize = minsize;
    }

    /**
     * Instantiates a new js_ numbers val fe.
     * 
     * @param formfeName the formfe name
     * @param field the field
     * @param minsize the minsize
     */
    public Js_NumbersValFE(String formfeName, String field, int minsize)
    {
        this.formfeName = formfeName;
        this.field = field;
        if(minsize > 1)
            this.minsize = minsize;
    }

    /**
     * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
     * obtains the final xml element to show in the resource admin.
     * 
     * @param locale the locale
     * @return the html
     */    
    public String getHtml(java.util.Locale locale)
    {
        StringBuffer strb = new StringBuffer();
        if(field != null)
        {
            String bundle=getClass().getName();
            strb.append("\n   pCaracter=");
            //if(formfeName != null) strb.append("document."+formfeName + ".");
            //strb.append(field + ".value;");
            strb.append("document.getElementById(\""+field+"\").value");
            strb.append("\n   var valid = \"0123456789\";");
            strb.append("\n   var ok = true;");
            strb.append("\n   if (pCaracter.length>0) ");
            strb.append("     { ");
            strb.append("\n      for (var i=0; i<pCaracter.length; i++)");
            strb.append("\n      { ");
            strb.append("\n          var temp = \"\" + pCaracter.substring(i, i+1); ");
            strb.append("\n          if (valid.indexOf(temp) == \"-1\") {ok = false;} ");
            strb.append("\n      } ");
            strb.append("\n      if (pCaracter.length<" + minsize + " || ok == false) ");
            strb.append("\n      { ");
            strb.append("\n         alert('"+ SWBUtils.TEXT.getLocaleString(bundle, "msgNumberRequired", locale));
            if(minsize>0) 
            {
                    strb.append(" "+ SWBUtils.TEXT.getLocaleString(bundle, "msgLengthRequired", locale) +" ");
                    strb.append(minsize);
                    strb.append(" "+ SWBUtils.TEXT.getLocaleString(bundle, "msgCharacters", locale));
            }
            strb.append(" '); ");
            //if(formfeName != null) strb.append("\n     " + "document."+formfeName + ".");
            //strb.append(field + ".focus();");
            strb.append("document.getElementById(\""+field+"\").focus();");
            strb.append("\n     return false;");
            strb.append("\n      } ");
            strb.append("\n   } ");
        }
        return strb.toString();
    }
}
