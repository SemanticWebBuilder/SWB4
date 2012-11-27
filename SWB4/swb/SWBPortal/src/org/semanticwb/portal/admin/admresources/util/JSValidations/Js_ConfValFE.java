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
 * Objeto que valida si se capturan o no caracteres validos o invalidos en un campo html con JavaScript en la apiadmin
 * <p>
 * Object that handles if a field has some valid or invalid characters with JavaScript in the apiadmin.
 * 
 * @author  Jorge Alberto Jim�nez
 */

public class Js_ConfValFE extends WBJsValidationsFEAbs
{
    
    /** The validchars. */
    private String validchars;
    
    /** The invalidchars. */
    private String invalidchars;
    
    /** The isshowchars. */
    private boolean isshowchars;
    
    /**
     * Instantiates a new js_ conf val fe.
     * 
     * @param field the field
     * @param minsize the minsize
     */
    public Js_ConfValFE(String field, int minsize)
    {
        validchars = null;
        invalidchars = null;
        isshowchars = false;
        this.field = field;
        if(minsize > 1)
            this.minsize = minsize;
    }

    /**
     * Instantiates a new js_ conf val fe.
     * 
     * @param formfeName the formfe name
     * @param field the field
     * @param minsize the minsize
     */
    public Js_ConfValFE(String formfeName, String field, int minsize)
    {
        validchars = null;
        invalidchars = null;
        isshowchars = false;
        this.formfeName = formfeName;
        this.field = field;
        if(minsize > 1)
            this.minsize = minsize;
    }

    /**
     * Instantiates a new js_ conf val fe.
     * 
     * @param field the field
     * @param minsize the minsize
     * @param valid the valid
     * @param confchars the confchars
     * @param isshowchars the isshowchars
     */
    public Js_ConfValFE(String field, int minsize, boolean valid, String confchars, boolean isshowchars)
    {
        validchars = null;
        invalidchars = null;
        this.isshowchars = false;
        this.field = field;
        if(minsize > 1)
            this.minsize = minsize;
        if(valid)
            validchars = confchars;
        else
            invalidchars = confchars;
        this.isshowchars = isshowchars;
    }

    /**
     * Instantiates a new js_ conf val fe.
     * 
     * @param formfeName the formfe name
     * @param field the field
     * @param minsize the minsize
     * @param valid the valid
     * @param confchars the confchars
     * @param isshowchars the isshowchars
     */
    public Js_ConfValFE(String formfeName, String field, int minsize, boolean valid, String confchars, boolean isshowchars)
    {
        validchars = null;
        invalidchars = null;
        this.isshowchars = false;
        this.formfeName = formfeName;
        this.field = field;
        if(minsize > 1)
            this.minsize = minsize;
        if(valid)
            validchars = confchars;
        else
            invalidchars = confchars;
        this.isshowchars = isshowchars;
    }

    /**
     * Sets the conf chars.
     * 
     * @param valid the valid
     * @param confchars the confchars
     */
    public void setConfChars(boolean valid, String confchars)
    {
        if(valid)
            validchars = confchars;
        else
            invalidchars = confchars;
    }

    /**
     * Show chars.
     * 
     * @param isshowchars the isshowchars
     */
    public void showChars(boolean isshowchars)
    {
        this.isshowchars = isshowchars;
    }

    /**
     * Gets the valid chars.
     * 
     * @return the valid chars
     */
    public String getValidChars()
    {
        return validchars;
    }

    /**
     * Gets the invalid valid chars.
     * 
     * @return the invalid valid chars
     */
    public String getInvalidValidChars()
    {
        return invalidchars;
    }

    /**
     * Show chars.
     * 
     * @return true, if successful
     */
    public boolean showChars()
    {
        return isshowchars;
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
        String bundle=getClass().getName();
        if(field != null)
            if(validchars != null)
            {
                strb.append("\n   pCaracter=");
//                if(formfeName != null)
//                    strb.append("document."+formfeName + ".");
//                strb.append(field + ".value;");
                strb.append("document.getElementById(\""+field+"\").value;");
                strb.append("\n   var valid = \"" + validchars + " \";");
                strb.append("\n   var ok = true;");
                strb.append("\n for (i = 0; i < pCaracter.length; i++) { ");
                strb.append("\n ch = pCaracter.charAt(i); ");
                strb.append("\n for (j = 0; j < valid.length; j++) ");
                strb.append("\n if (ch == valid.charAt(j)) ");
                strb.append("\n break; ");
                strb.append("\n if (j == valid.length) { ");
                strb.append("\n ok = false; ");
                strb.append("\n break; ");
                strb.append("\n } ");
                strb.append("\n } ");
                strb.append("\n if (!ok || pCaracter.length<" + minsize + ") { ");
                strb.append("\n alert('"+ SWBUtils.TEXT.getLocaleString(bundle, "msgFormatRequired", locale));
                if(isshowchars) strb.append(" "+ validchars);
                strb.append(SWBUtils.TEXT.getLocaleString(bundle, "msgLengthRequired", locale)+" "+ minsize + "'); ");
                //if(formfeName != null) strb.append("\n     " + "document."+formfeName + ".");
                //strb.append(field + ".focus();");
                strb.append("document.getElementById(\""+field+"\").focus();");
                strb.append("\n     return false;");
                strb.append("\n } ");
            } else
            if(invalidchars != null)
            {
//                strb.append("\n   pCaracter=");
//                if(formfeName != null)
//                    strb.append("document."+formfeName + ".");
//                strb.append(field + ".value");
                strb.append("document.getElementById(\""+field+"\").value;");
                strb.append("\n   var invalid = \"" + invalidchars + " \";");
                strb.append("\n   var ok = true;");
                strb.append("\n for (i = 0; i < pCaracter.length; i++) { ");
                strb.append("\n ch = pCaracter.charAt(i); ");
                strb.append("\n for (j = 0; j < invalid.length; j++){ ");
                strb.append("\n if (ch == invalid.charAt(j)){");
                strb.append("\n ok = false; ");
                strb.append("\n break; ");
                strb.append("\n } ");
                strb.append("\n } ");
                strb.append("\n if (!ok) break;");
                strb.append("\n } ");
                strb.append("\n if (!ok || pCaracter.length<" + minsize + ") { ");
                strb.append("\n alert('"+ SWBUtils.TEXT.getLocaleString(bundle, "msgNonvalid", locale));
                if(isshowchars)
                    strb.append(":" + invalidchars);
                strb.append(SWBUtils.TEXT.getLocaleString(bundle, "msgLengthRequired", locale)+" " + minsize + "'); ");
//                if(formfeName != null)
//                    strb.append("\n     " + "document."+formfeName + ".");
//                strb.append(field + ".focus();");
                strb.append("document.getElementById(\""+field+"\").focus();");
                strb.append("\n     return false;");
                strb.append("\n } ");
            }
        return strb.toString();
    }

}
