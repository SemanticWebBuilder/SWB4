package org.semanticwb.process.model;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;

public class ProcessObserver extends org.semanticwb.process.model.base.ProcessObserverBase implements SemanticObserver
{
    private static Logger log=SWBUtils.getLogger(ProcessObserver.class);

    /** The timer. */
    private transient Timer timer;

    /** The delays. */
    private transient int delays = 60;

    /** The t. */
    private transient TimerTask t=null;

    /**
     * Inits the.
     */
    public void init()
    {
        t=new TimerTask(){
            public void run()
            {
                try {
                    checkTimer();
                } catch (Exception e){
                    log.error(e);
                }
            }
        };
        timer = new Timer("ProcessObserver("+delays+"s)", true);
        timer.scheduleAtFixedRate(t, delays*1000, delays*1000);
        log.event("Initializing ProcessObserver("+delays+"s)...");

        SWBPlatform.getSemanticMgr().registerObserver(this);
    }

    public ProcessObserver(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
        init();
    }

    public void sendSignal(FlowNodeInstance instance)
    {
        Iterator<FlowNodeInstance> it=listSignalObserverInstances();
        while (it.hasNext())
        {
            FlowNodeInstance flowNodeInstance = it.next();
            String c1=((Event)flowNodeInstance.getFlowNodeType()).getActionCode();
            String c2=((Event)instance.getFlowNodeType()).getActionCode();
            if((c1!=null && c1.equals(c2)) || c1==null && c2==null)
            {
                try
                {
                    flowNodeInstance.notifyEvent(instance);
                }catch(Exception e){log.error(e);}
            }
        }
    }

    public void sendSignal(FlowNode node)
    {

    }

    private void checkTimer()
    {
        System.out.println("checkTimer...");
        Iterator<FlowNodeInstance> it=listTimeObserverInstances();
        while (it.hasNext())
        {
            FlowNodeInstance flowNodeInstance = it.next();
            ProcessPeriodRefable pr=((ProcessPeriodRefable)flowNodeInstance.getFlowNodeType());
            Iterator<ProcessPeriodRef> it2=pr.listProcessPeriodRefs();
            while (it2.hasNext()) {
                ProcessPeriodRef ppr = it2.next();
                if(ppr.isActive())
                {
                    System.out.println("checking:"+ppr.getProcessPeriod());
                    if(ppr.getProcessPeriod().isOnSchedule())
                    {
                        try
                        {
                            System.out.println("ok...");
                            flowNodeInstance.notifyEvent(null);
                        }catch(Exception e){log.error(e);}
                    }
                }
            }
        }

        Iterator<StartEvent> sit=listTimeObserverNodes();
        while (sit.hasNext())
        {
            StartEvent startEvent = sit.next();
            Containerable cont=startEvent.getContainer();
            if(cont!=null && cont instanceof Process && ((Process)cont).isActive())
            {
                ProcessPeriodRefable pr=((ProcessPeriodRefable)startEvent);
                Iterator<ProcessPeriodRef> it2=pr.listProcessPeriodRefs();
                while (it2.hasNext()) {
                    ProcessPeriodRef ppr = it2.next();
                    if(ppr.isActive())
                    {
                        System.out.println("checking:"+ppr.getProcessPeriod());
                        if(ppr.getProcessPeriod().isOnSchedule())
                        {
                            try
                            {
                                System.out.println("ok...");
                                ProcessInstance inst=((Process)cont).createInstance();
                                inst.start(null,startEvent);
                            }catch(Exception e){log.error(e);}
                        }
                    }
                }
            }
        }

    }

    public void notify(SemanticObject obj, Object prop, String lang, String action)
    {
        if(SemanticObject.ACT_CREATE.equals(action))
        {
            if(obj.instanceOf(TimerStartEvent.sclass))
            {
                addTimeObserverNode((StartEvent)obj.createGenericInstance());
            }
        }
    }
}
