package org.semanticwb.domotic.model;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


public class StartTimerAction extends org.semanticwb.domotic.model.base.StartTimerActionBase 
{
    private Timer timer=null;
    
    public StartTimerAction(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doActionImp()
    {
        if(timer!=null)timer.cancel();
        System.out.println("StartTimerAction:doAction");
        timer=new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                System.out.println("StartTimerAction:run");
                doActions();
                timer.cancel();
            }
            
        }, getDelay()*1000);
    }
    
    /**
     * Ejecutar las Acciones relacionadas al evento
     */
    
    public void doActions()
    {        
        DomContext c=getDomContext();
        if(c!=null && !c.isActive())return;
        Iterator<DomAction> it=listDomActions();
        while (it.hasNext())
        {
            DomAction domAction = it.next();
            domAction.doAction();
        }           
    }    
    
    public DomContext getDomContext()
    {
        if(getDomEvent()!=null)return getDomEvent().getDomContext();
        return getGetStartTimerAction().getDomContext();
    }
        
    
    
}
