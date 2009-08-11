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
 
/*
 * XformsBaseImp.java
 *
 * Created on 1 de julio de 2008, 05:15 PM
 */

package org.semanticwb.xforms.lib;

/**
 *
 * @author  jorge.jimenez
 */
public class XformsBaseImp implements XformsBase
{
    protected String label;
    protected String id;
    protected String subType;
    protected boolean isrequired;
    protected String xml;
    protected String xmlBind;
    protected String help;
    protected String hint;
        
    public void setLable(String label)
    {
        this.label=label;
    }
    
    public String getLabel()
    {
        return label;
    }
    
    public void setId(String id)
    {
        this.id=id;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setSubType(String subType)
    {
        this.subType=subType;
    }
    
    public String getSubType()
    {
        return subType;
    }
    
    public void setRequired(boolean isrequired)
    {
        this.isrequired=isrequired;
    }
   
    public boolean isRequired()
    {
        return isrequired;
    }
    
    public void setXml(String xml) {
        this.xml=xml;
    }
    
    public String getXml() {
        return xml;
    }
    
    public void setXmlBind(String xmlBind) {
        this.xmlBind=xmlBind;
    }
    
    public String getXmlBind() {
        return xmlBind;
    }
    
    public void setHelp(String help) {
        this.help=help;
    }    
    
    public String getHelp() {
        return help;
    }    
    
    public String getHint() {
        return hint;
    }
    
    public void setHint(String hint) {
        this.hint=hint;
    }
    
}
