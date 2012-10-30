package org.semanticwb.domotic.model;

import java.util.Iterator;


public class OnDeviceChange extends org.semanticwb.domotic.model.base.OnDeviceChangeBase 
{
    public OnDeviceChange(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void onEventImp(String stat)
    {
        System.out.println("OnDeviceChange:onEvent:"+stat);        
        int st=Integer.parseInt(stat);
        DomDevice obj=getDomDevice();
        if(getDeviceStat().equals("any") || (getDeviceStat().equals("on") && st>0) || (getDeviceStat().equals("off") && st==0))
        {
            doActions();
        }
    }
    
    
}
