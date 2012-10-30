package org.semanticwb.domotic.model;

import java.util.Iterator;


public class OnGroupChange extends org.semanticwb.domotic.model.base.OnGroupChangeBase 
{
    public OnGroupChange(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public void onEventImp(String stat)
    {
        System.out.println("OnGroupChange:onEvent:"+stat);                
        int st=Integer.parseInt(stat);
        DomGroup obj=getDomGroup();
        if(getGroupStat().equals("any") || (getGroupStat().equals("on") && st>0) || (getGroupStat().equals("off") && st==0))
        {
            doActions();
        }
    }    
}
