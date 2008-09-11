/*
 * WBXformsContainer.java
 *
 * Created on 1 de julio de 2008, 05:45 PM
 */

package org.semanticwb.portal.xforms.lib;

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.xforms.lib.XformsBase;
import org.semanticwb.xforms.lib.XformsBaseImp;

/**
 *
 * @author  jorge.jimenez
 */

public abstract class WBXformsContainer extends XformsBaseImp
{
    protected ArrayList formelements;
    
    public WBXformsContainer() {
        formelements = new ArrayList();
    }
    
    public void add(Object obj) {
        formelements.add(obj);
    }
    
    public String getXform() {
        StringBuffer strb = new StringBuffer();
        Iterator iobj = formelements.iterator();
        while(iobj.hasNext())
        {
            if(!iobj.hasNext()){
                break;
            }
            Object obj = iobj.next();
            if(obj instanceof XformsBase) {
                XformsBase wbXformE = (XformsBase)obj;
                strb.append(wbXformE.getXml());
            }
        }
        return strb.toString();
    }
    
     public String getXformBinds() {
        StringBuffer strb = new StringBuffer();
        Iterator iobj = formelements.iterator();
        do {
            if(!iobj.hasNext()){
                break;
            }
            Object obj = iobj.next();
            if(obj instanceof XformsBase) {
                XformsBase wbXformE = (XformsBase)obj;
                String xmlBind=wbXformE.getXmlBind();
                if(xmlBind!=null && xmlBind.trim().length()>0)
                {
                    strb.append(xmlBind);
                }
            }
        } while(true);
        return strb.toString();
    }
        
    public String show() {
        return getXform();
    }
    
    public String showBinds() {
        return getXformBinds();
    }
    
}
