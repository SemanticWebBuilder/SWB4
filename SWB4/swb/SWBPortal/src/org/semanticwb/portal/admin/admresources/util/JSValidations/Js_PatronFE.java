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
 * Objeto que valida si la entrada a un campo html cumple con un cierto patron de expresiones regulares mediante JavaScript.
 * <p>
 * Object that verifies if a html field conteins a especified regular expresion using JavaScript.
 * @author  Jorge Alberto Jim�nez
 */

public class Js_PatronFE extends WBJsValidationsFEAbs {
    
    /** The patron. */
    String patron=null;
    
    /** The showpatron. */
    boolean showpatron=true;
    
    /**
     * Instantiates a new js_ patron fe.
     * 
     * @param field the field
     */
    public Js_PatronFE(String field)
    {
        this.field = field;
    }

    /**
     * Instantiates a new js_ patron fe.
     * 
     * @param formfeName the formfe name
     * @param field the field
     */
    public Js_PatronFE(String formfeName, String field)
    {
        this.formfeName = formfeName;
        this.field = field;
    }
    
    /**
     * Instantiates a new js_ patron fe.
     * 
     * @param field the field
     * @param patron the patron
     * @param showpatron the showpatron
     */
    public Js_PatronFE(String field, String patron, boolean showpatron)
    {
        this.field = field;
        this.patron=patron;
        this.showpatron = showpatron;
    }
    
    /**
     * Instantiates a new js_ patron fe.
     * 
     * @param formfeName the formfe name
     * @param field the field
     * @param patron the patron
     * @param showpatron the showpatron
     */
    public Js_PatronFE(String formfeName,String field, String patron, boolean showpatron)
    {
        this.formfeName = formfeName;
        this.field = field;
        this.patron=patron;
        this.showpatron = showpatron;
    }
    
    /**
     * Sets the patron.
     */
    public void setPatron(){
        this.patron=patron;
    }
    
    /**
     * Gets the patron.
     * 
     * @return the patron
     */
    public String getPatron(){
        return patron;
    }
    
    /**
     * Sets the showpatron.
     * 
     * @param showpatron the new showpatron
     */
    public void setShowpatron(boolean showpatron){
        this.showpatron=showpatron;
    }
    
    /**
     * Gets the showpatron.
     * 
     * @return the showpatron
     */
    public boolean getShowpatron(){
        return showpatron;
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
//            strb.append(field + ".value;");
            strb.append("document.getElementById(\""+field+"\").value;");
            strb.append("var patron=/"+patron+"/;");
            strb.append("\n   if (pCaracter.length>0) ");
            strb.append("     { ");
            strb.append("\n      if (!patron.test(pCaracter)) ");
            strb.append("\n      { ");
            strb.append("\n         alert('"+ SWBUtils.TEXT.getLocaleString(bundle, "msgPatternRequired", locale));
            if (showpatron) {
                strb.append(" "+patron);
            }
            strb.append(" ');");
//            if(formfeName != null)
//                strb.append("\n     " + "document."+formfeName + ".");
//            strb.append(field + ".focus();");
            strb.append("document.getElementById(\""+field+"\").focus();");
            strb.append("\n     return false;");
            strb.append("\n      } ");
            strb.append("\n   } ");
        }
        return strb.toString();
    }
}
