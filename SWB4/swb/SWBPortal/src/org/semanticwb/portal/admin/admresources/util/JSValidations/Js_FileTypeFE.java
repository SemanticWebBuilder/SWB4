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
 * Objeto que valida si la entrada a un campo html es de tipo file y si cumple con las extenciones definidas en el mismo objeto
 * <p>
 * Object that verifies if a html field has a file and if those files have certains extensions.
 * @author  Jorge Alberto Jim�nez
 */

public class Js_FileTypeFE extends WBJsValidationsFEAbs {
    
    String patron=null;
    String filetypes=null;
    boolean showfiletypes=true;
    
    public Js_FileTypeFE(String field)
    {
        this.field = field;
    }

    public Js_FileTypeFE(String formfeName, String field)
    {
        this.formfeName = formfeName;
        this.field = field;
    }
    
    public Js_FileTypeFE(String field, String filetypes, boolean showfiletypes)
    {
        this.field = field;
        this.filetypes=filetypes;
        this.showfiletypes = showfiletypes;
    }
    
    public Js_FileTypeFE(String formfeName,String field, String filetypes, boolean showfiletypes)
    {
        this.formfeName = formfeName;
        this.field = field;
        this.filetypes=filetypes;
        this.showfiletypes = showfiletypes;
    }
    
    public void setFileTypes(String filetypes){
        this.filetypes=filetypes;
    }
    
    public String getFileTypes(){
        return filetypes;
    }
    
    public void setShowFiletypes(boolean showfiletypes){
        this.showfiletypes=showfiletypes;
    }
    
    public boolean getShowFileTypes(){
        return showfiletypes;
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
            if(formfeName != null)
                strb.append(formfeName + ".");
            strb.append(field + ".value;");
            strb.append("\n var pExt=\'"+filetypes+"\';");
            strb.append("\n   if(pCaracter.length > 0)");
            strb.append("\n   {");
            strb.append("\n      var swFormat=pExt + '|';");
            strb.append("\n      sExt=pCaracter.substring(pCaracter.indexOf(\".\")).toLowerCase();");
            strb.append("\n      var sType='';");
            strb.append("\n      while(swFormat.length > 0 )");
            strb.append("\n      {");
            strb.append("\n         sType= swFormat.substring(0, swFormat.indexOf(\"|\"));");
            strb.append("\n         if(sExt.indexOf(sType)!=-1) return true;");
            strb.append("\n         swFormat=swFormat.substring(swFormat.indexOf(\"|\")+1);");
            strb.append("\n      }");
            strb.append("\n      while(pExt.indexOf(\"|\")!=-1) pExt=pExt.replace('|',',');");
            strb.append("\n      alert('"+ SWBUtils.TEXT.getLocaleString(bundle, "msgFileType", locale) +"' + pExt.replace('|',','));");
            strb.append("\n      return false;");
            strb.append("\n   }");
        }
        return strb.toString();
    }
}
