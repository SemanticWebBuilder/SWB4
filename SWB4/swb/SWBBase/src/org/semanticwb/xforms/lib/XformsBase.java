/*
 * XformsBase.java
 *
 * Created on 1 de julio de 2008, 04:47 PM
 */

package org.semanticwb.xforms.lib;

/**
 *
 * @author  jorge.jimenez
 */
public interface XformsBase {
    
    public void setLable(String label);
    
    public String getLabel();
    
    public void setId(String id);
    
    public String getId();
    
    public void setSubType(String subType);
    
    public String getSubType();
    
    public void setRequired(boolean isrequired);
    
    public boolean isRequired();
    
    public void setXml(String xml);
    
    public String getXml();
    
    public void setXmlBind(String xmlBind);
    
    public String getXmlBind();
    
    public void setHelp(String help);
    
    public String getHelp();
    
    public void setHint(String hint);
    
    public String getHint();
    
  
}
