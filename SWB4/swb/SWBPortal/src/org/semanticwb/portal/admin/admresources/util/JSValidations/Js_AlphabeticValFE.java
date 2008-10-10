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

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.lib.WBJsValidationsFEAbs;

/**
 * Objeto que valida si un campo es de tipo alphabetico con JavaScript en la apiadmin
 * <p>
 * Object that handles if a field is of type alphabetic with JavaScript in the apiadmin
 * @author  Jorge Alberto Jim�nez
 */

public class Js_AlphabeticValFE extends WBJsValidationsFEAbs
{
    private static Logger log = SWBUtils.getLogger(Js_AlphabeticValFE.class);
    
    public Js_AlphabeticValFE(String field, int minsize)
    {
        this.field = field;
        if(minsize > 1){
            this.minsize = minsize;
        }
    }

    public Js_AlphabeticValFE(String formfeName, String field, int minsize)
    {
        this.formfeName = formfeName;
        this.field = field;
        if(minsize > 1){
            this.minsize = minsize;
        }
    }

    /**
    * Obtiene el html(xml) final del elemento para mostrar en la admin del recurso
    * obtains the final xml element to show in the resource admin
    */   
    public String getHtml(java.util.Locale locale)
    {
        StringBuffer strb = new StringBuffer();
        if(field != null)
        {
            String bundle=getClass().getName();
            strb.append("\n   pCaracter=");
            if(formfeName != null) {
                strb.append(formfeName + ".");
            }
            strb.append(field + ".value");
            strb.append("\n   var valid = \"ABCDEFGHIJKLMN\321OPQRSTUVWXYZ\301\311\315\323\332abcdefghijklmn\361opqrstuvwxyz\341\351\355\363\372 \";");
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
            strb.append("\n alert('"+ SWBUtils.TEXT.getLocaleString(bundle, "msgDataRequired", locale) +" ");
            strb.append(minsize);
            strb.append(" "+  SWBUtils.TEXT.getLocaleString(bundle, "msgCharacters", locale) +"'); ");
            if(formfeName != null) {
                strb.append("\n     " + formfeName + ".");
            }
            strb.append(field + ".focus();");
            strb.append("\n     return false;");
            strb.append("\n } ");
        }
        return strb.toString();
    }
}