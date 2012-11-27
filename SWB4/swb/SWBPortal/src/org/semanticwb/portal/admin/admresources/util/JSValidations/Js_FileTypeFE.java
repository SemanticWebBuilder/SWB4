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
 * Objeto que valida si la entrada a un campo html es de tipo file y si cumple con las extenciones definidas en el mismo objeto
 * <p>
 * Object that verifies if a html field has a file and if those files have certains extensions.
 * @author  Jorge Alberto Jim�nez
 */

public class Js_FileTypeFE extends WBJsValidationsFEAbs {
    
    /** The patron. */
    String patron=null;
    
    /** The filetypes. */
    String filetypes=null;
    
    /** The showfiletypes. */
    boolean showfiletypes=true;
    
    /**
     * Instantiates a new js_ file type fe.
     * 
     * @param field the field
     */
    public Js_FileTypeFE(String field)
    {
        this.field = field;
    }

    /**
     * Instantiates a new js_ file type fe.
     * 
     * @param formfeName the formfe name
     * @param field the field
     */
    public Js_FileTypeFE(String formfeName, String field)
    {
        this.formfeName = formfeName;
        this.field = field;
    }
    
    /**
     * Instantiates a new js_ file type fe.
     * 
     * @param field the field
     * @param filetypes the filetypes
     * @param showfiletypes the showfiletypes
     */
    public Js_FileTypeFE(String field, String filetypes, boolean showfiletypes)
    {
        this.field = field;
        this.filetypes=filetypes;
        this.showfiletypes = showfiletypes;
    }
    
    /**
     * Instantiates a new js_ file type fe.
     * 
     * @param formfeName the formfe name
     * @param field the field
     * @param filetypes the filetypes
     * @param showfiletypes the showfiletypes
     */
    public Js_FileTypeFE(String formfeName,String field, String filetypes, boolean showfiletypes)
    {
        this.formfeName = formfeName;
        this.field = field;
        this.filetypes=filetypes;
        this.showfiletypes = showfiletypes;
    }
    
    /**
     * Sets the file types.
     * 
     * @param filetypes the new file types
     */
    public void setFileTypes(String filetypes){
        this.filetypes=filetypes;
    }
    
    /**
     * Gets the file types.
     * 
     * @return the file types
     */
    public String getFileTypes(){
        return filetypes;
    }
    
    /**
     * Sets the show filetypes.
     * 
     * @param showfiletypes the new show filetypes
     */
    public void setShowFiletypes(boolean showfiletypes){
        this.showfiletypes=showfiletypes;
    }
    
    /**
     * Gets the show file types.
     * 
     * @return the show file types
     */
    public boolean getShowFileTypes(){
        return showfiletypes;
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
            //if(formfeName != null)
            //    strb.append("document."+formfeName + ".");
            //strb.append(field + ".value;");
            strb.append("document.getElementById(\""+field+"\").value;");
            strb.append("\n var pExt=\'"+filetypes+"\';");
            strb.append("\n   if(pCaracter.length > 0)");
            strb.append("\n   {");
            strb.append("\n      var swFormat=pExt + '|';");
            strb.append("\n      sExt=pCaracter.substring(pCaracter.indexOf(\".\")).toLowerCase();");
            strb.append("\n      var sType='';");
            strb.append("\n      var flag=false;");
            strb.append("\n      while(swFormat.length > 0 )");
            strb.append("\n      {");
            strb.append("\n         sType= swFormat.substring(0, swFormat.indexOf(\"|\"));");
            strb.append("\n         if(sExt.indexOf(sType)!=-1) flag=true;");
            strb.append("\n         swFormat=swFormat.substring(swFormat.indexOf(\"|\")+1);");
            strb.append("\n      }");
            strb.append("\n      if(!flag){");
            strb.append("\n      while(pExt.indexOf(\"|\")!=-1) pExt=pExt.replace('|',',');");
            strb.append("\n      alert('"+ SWBUtils.TEXT.getLocaleString(bundle, "msgFileType", locale) +"' + pExt.replace('|',','));");
            strb.append("\n      return false;");
            strb.append("\n      }");
            strb.append("\n   }");
        }
        return strb.toString();
    }
}
