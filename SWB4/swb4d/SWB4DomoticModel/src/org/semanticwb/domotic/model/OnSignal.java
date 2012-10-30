package org.semanticwb.domotic.model;


public class OnSignal extends org.semanticwb.domotic.model.base.OnSignalBase 
{
    public OnSignal(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public void onEventImp(String stat)
    {
        System.out.println("OnSignal:onEvent:"+stat);                
        if(stat.equals(getSignal()))
        {
            doActions();        
        }
    }
    
}
