/*
 * XformsBaseImp.java
 *
 * Created on 1 de julio de 2008, 05:15 PM
 */

package org.semanticwb.portal.xforms.lib;

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
