/*
 * XFForm.java
 *
 * Created on 1 de julio de 2008, 05:43 PM
 */

package org.semanticwb.portal.xforms.ui.container;

import org.semanticwb.xforms.lib.WBXformsContainer;

/**
 *
 * @author  jorge.jimenez
 */
public class XFForm extends WBXformsContainer
{
    
    @Override
    public String show(){
        return getXform();
    }
    
    @Override
    public void add(Object obj){
       super.add(obj);
    }
    
    
    @Override
    public String getXml() {
        StringBuffer strb=new StringBuffer();
        strb.append(show());
        return strb.toString();
    }
    
    public String getXmlBind() {
        return showBinds();
    }
    
}
