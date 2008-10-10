/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


package org.semanticwb.portal.admin.admresources.util.JSValidations;

import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.lib.WBJsValidationsFEAbs;

/**
 * Objeto que valida si se capturan o no caracteres validos o invalidos en un campo html con JavaScript en la apiadmin
 * <p>
 * Object that handles if a field has some valid or invalid characters with JavaScript in the apiadmin
 * @author  Jorge Alberto Jim�nez
 */

public class Js_ConfValFE extends WBJsValidationsFEAbs
{
    private String validchars;
    private String invalidchars;
    private boolean isshowchars;
    
    public Js_ConfValFE(String field, int minsize)
    {
        validchars = null;
        invalidchars = null;
        isshowchars = false;
        this.field = field;
        if(minsize > 1)
            this.minsize = minsize;
    }

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

    public void setConfChars(boolean valid, String confchars)
    {
        if(valid)
            validchars = confchars;
        else
            invalidchars = confchars;
    }

    public void showChars(boolean isshowchars)
    {
        this.isshowchars = isshowchars;
    }

    public String getValidChars()
    {
        return validchars;
    }

    public String getInvalidValidChars()
    {
        return invalidchars;
    }

    public boolean showChars()
    {
        return isshowchars;
    }

    /**
    * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
    * obtains the final xml element to show in the resource admin
    */     
    public String getHtml(java.util.Locale locale)
    {
        StringBuffer strb = new StringBuffer();
        String bundle=getClass().getName();
        if(field != null)
            if(validchars != null)
            {
                strb.append("\n   pCaracter=");
                if(formfeName != null)
                    strb.append(formfeName + ".");
                strb.append(field + ".value;");
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
                if(formfeName != null) strb.append("\n     " + formfeName + ".");
                strb.append(field + ".focus();");
                strb.append("\n     return false;");
                strb.append("\n } ");
            } else
            if(invalidchars != null)
            {
                strb.append("\n   pCaracter=");
                if(formfeName != null)
                    strb.append(formfeName + ".");
                strb.append(field + ".value");
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
                if(formfeName != null)
                    strb.append("\n     " + formfeName + ".");
                strb.append(field + ".focus();");
                strb.append("\n     return false;");
                strb.append("\n } ");
            }
        return strb.toString();
    }

}