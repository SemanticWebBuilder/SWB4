package org.semanticwb.model;

import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class Portlet extends PortletBase 
{
    private String siteid=null;
    protected int randpriority;    
    
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
        if(getPortletType().getPortletCache()>0)
        {
            ret=true;
        }
        return ret;
    }
    
//    public void refreshRandPriority()
//    {
//        if (this.getCamp() == 1)
//            randpriority = SWBPriorityCalculator.getInstance().calcPriority(0);
//        else if (this.getCamp() == 2)
//            randpriority = WBUtils.getInstance().calcPriority(6);
//        else
//            randpriority = WBUtils.getInstance().calcPriority(recResource.getPriority());
//    }   
    
    public void setRandPriority(int randpriority)
    {
        this.randpriority=randpriority;
    }
    
    /**
     * @return  */
    public int getRandPriority()
    {
        return randpriority;
    }    
}
