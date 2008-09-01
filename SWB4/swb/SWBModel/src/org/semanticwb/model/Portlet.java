package org.semanticwb.model;

import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class Portlet extends PortletBase 
{
    private String siteid=null;
    
    public Portlet(SemanticObject base)
    {
        super(base);
    }
    
    public String getWebSiteId()
    {
        if(siteid==null)
        {
            siteid=getWebSite().getId();
        }
        return siteid;
    }
    
    public boolean isCached()
    {
        boolean ret=false;
        if(getPortletClass().getPortletCache()>0)
        {
            ret=true;
        }
        return ret;
    }
}
