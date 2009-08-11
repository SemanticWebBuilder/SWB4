/**  
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
**/ 
 


package org.semanticwb.portal.admin.admresources.lib;

import org.semanticwb.portal.admin.admresources.util.JSValidations.*;

/**
 * Esta es una clase abstracta que extiende de  WBAdmResourceAbs e implementar los metodos genericos para los objetos Input que utilizan JavaScript 
 * <p>
 * This is an abstract class that extends from WBAdmResourceAbs and implements the generic methods for the Input object that JavaScript 
 * @author  Jorge Alberto Jim�nez
 * @version 1.0
 */

public abstract class WBJsInputFEAbs extends WBAdmResourceAbs implements WBJsInputFE
{

    protected String js_validation;
    protected String js_patron;
    protected String js_filetype;
    protected boolean js_isrequired;
    protected Js_AlphabeticValFE jsObj_alphabetic;
    protected Js_NumbersValFE jsObj_numbers;
    protected Js_EmailValFE jsObj_email;
    protected Js_RequiredValFE jsObj_required;
    protected Js_ConfValFE jsObj_conf;
    protected Js_PatronFE jsObj_patron;
    protected Js_FileTypeFE jsObj_filetype;
    public String JS_REQUIRED="js_required";
    public String JS_ALPHABETIC="js_alphabetic";
    public String JS_NUMBERS="js_numbers";
    public String JS_EMAIL="js_email";
    public int minsize;
    
    
    public WBJsInputFEAbs()
    {
        js_validation = null;
        js_isrequired = false;
        jsObj_alphabetic = null;
        jsObj_numbers = null;
        jsObj_email = null;
        jsObj_required = null;
        jsObj_conf = null;
        minsize=0;
    }
    
    public void setJsMinSize(int minsize){
        this.minsize=minsize;
    }
    
    public int getJsMinSize(){
        return minsize;
    }

    public void setJsIsRequired(boolean isjs_required)
    {
        if(isjs_required) {
            setJsValType("js_required");
        }
    }
    
    public void setJsValType(String js_validation)
    {
        this.js_validation = js_validation;
        if(js_validation.equalsIgnoreCase("js_alphabetic")){
            jsObj_alphabetic = new Js_AlphabeticValFE(getName(), minsize);
        }
        else
        if(js_validation.equalsIgnoreCase("js_numbers")){
            jsObj_numbers = new Js_NumbersValFE(getName(), minsize);
        }
        else
        if(js_validation.equalsIgnoreCase("js_email")){
            jsObj_email = new Js_EmailValFE(getName(), minsize);
        }
        else
        if(js_validation.equalsIgnoreCase("js_required")){
            jsObj_required = new Js_RequiredValFE(getName(), minsize);
        }
    }

    public void setJsValTypeFlags(boolean isjs_alphabetic, boolean isjs_numbers, boolean isjs_email, boolean isjs_isrequired)
    {
        if(isjs_alphabetic){
            setJsValType("js_alphabetic");
        }
        else
        if(isjs_numbers){
            setJsValType("js_numbers");
        }
        else
        if(isjs_email){
            setJsValType("js_email");
        }
        else
        if(isjs_isrequired){
            setJsValType("js_required");
        }
    }

    public void setJsValidChars(boolean valid, String confchars, boolean isshowchars)
    {
        jsObj_conf = new Js_ConfValFE(getName(), minsize, valid, confchars, isshowchars);
        js_validation = "js_confchars";
    }

  
    public Object[] getJsValObj()
    {
        Object JsObjs[]=new Object[7];
        JsObjs[0]=jsObj_alphabetic;
        JsObjs[1]=jsObj_numbers;
        JsObjs[2]=jsObj_email;
        JsObjs[3]=jsObj_required;
        JsObjs[4]=jsObj_conf;
        JsObjs[5]=jsObj_patron;
        JsObjs[6]=jsObj_filetype;
        
        return JsObjs;
    }
    
    public void setJsPatron(String patron, boolean isshowpatron)
    {
        jsObj_patron = new Js_PatronFE(getName(),patron, isshowpatron);
        js_patron = patron;
    }
    
    public void setJsPatron(String patron)
    {
        jsObj_patron = new Js_PatronFE(getName(),patron, true);
        js_patron = patron;
    }
    
    public void setJsFileType(String filetype, boolean isshowfiletype)
    {
        jsObj_filetype = new Js_FileTypeFE(getName(),filetype, isshowfiletype);
        js_filetype = filetype;
    }
    
    public void setJsFileType(String filetype)
    {
        jsObj_filetype = new Js_FileTypeFE(getName(),filetype, true);
        js_filetype = filetype;
    }

}
