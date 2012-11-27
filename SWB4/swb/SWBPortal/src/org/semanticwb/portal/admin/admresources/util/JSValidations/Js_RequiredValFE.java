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
 * Objeto que valida si un cierto campo html contiene por lo menos un caracter.
 * <p>
 * Object that verifies if a html field conteins as less one character
 * @author  Jorge Alberto Jim�nez
 */

public class Js_RequiredValFE extends WBJsValidationsFEAbs
{

    /**
     * Instantiates a new js_ required val fe.
     * 
     * @param field the field
     * @param minsize the minsize
     */
    public Js_RequiredValFE(String field, int minsize)
    {
        this.field = field;
        this.minsize=1;
        if(minsize > 1) this.minsize = minsize;
    }

    /**
     * Instantiates a new js_ required val fe.
     * 
     * @param formfeName the formfe name
     * @param field the field
     * @param minsize the minsize
     */
    public Js_RequiredValFE(String formfeName, String field, int minsize)
    {
        this.formfeName = formfeName;
        this.field = field;
        this.minsize=1;
        if(minsize > 1) this.minsize = minsize;
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
//            if(formfeName != null)
//                strb.append("document."+formfeName + ".");
//            strb.append(field + ".value.replace(\" \",\"\");");
            strb.append("document.getElementById(\""+field+"\").value.replace(\" \",\"\");");
            strb.append("\n   if (pCaracter.length<" + minsize + ")");
            strb.append("\n   {");
            strb.append("\n      alert('"+ SWBUtils.TEXT.getLocaleString(bundle, "msgDataRequired", locale) +" " + minsize + " "+ SWBUtils.TEXT.getLocaleString(bundle, "msgCharacters", locale) +"'); ");
//            if(formfeName != null)
//                strb.append("\n      " + "document."+formfeName + ".");
//            strb.append(field + ".focus();");
            strb.append("document.getElementById(\""+field+"\").focus();");
            strb.append("\n      return false;");
            strb.append("\n   }");
        }
        return strb.toString();
    }
}
