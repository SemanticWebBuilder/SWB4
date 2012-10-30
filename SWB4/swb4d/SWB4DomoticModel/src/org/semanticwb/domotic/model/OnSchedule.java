package org.semanticwb.domotic.model;

import java.util.Iterator;


public class OnSchedule extends org.semanticwb.domotic.model.base.OnScheduleBase 
{
    boolean ini=false;
    boolean oldValue=false;
    
    public OnSchedule(org.semanticwb.platform.SemanticObject base)
    {
        super(base);        
    }
    
    @Override
    public void onEventImp(String stat)
    {
        System.out.println("OnSchedule:onEvent:"+stat);
        if(getScheduleStat().equals(stat))
        {
            doActions();
        }
    }
    
    public void chechSchedule()
    {        
        if(getCalendar()==null)return;
        if(getDomContext()!=null && !getDomContext().isActive())return;
        if(!ini)
        {
            ini=true;
            oldValue=getCalendar().isOnSchedule();
        }
        boolean sch=getCalendar().isOnSchedule();
        if(oldValue!=sch)
        {
            if(sch)onEvent("true");
            else onEvent("false");
        }
    }
    
}
