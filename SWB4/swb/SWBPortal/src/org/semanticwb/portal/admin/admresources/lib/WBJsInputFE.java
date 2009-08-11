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

/**
 * Esta es una clase interface que implementa la interface WBAdmResource y que es utilizada para los objetos de tipo validaci�n de JavaScript (ej. Js_AlphabeticValFE,Js_ConfValFE)
 * <p>
 * This is an interface that implements the WBAdmResource interface and its used in the JavaScript validators objetcs (ej. Js_AlphabeticValFE,Js_ConfValFE)
 * @author  Jorge Alberto Jim�nez
 * @version 1.0
 */

public interface WBJsInputFE extends WBAdmResource
{

    public abstract void setJsIsRequired(boolean flag);

    public abstract void setJsValType(String s);

    public abstract void setJsValTypeFlags(boolean flag, boolean flag1, boolean flag2, boolean flag3);

    public abstract void setJsValidChars(boolean flag, String s, boolean flag1);
    
    public abstract void setJsPatron(String patron, boolean flag);

    public abstract Object[] getJsValObj();
}
