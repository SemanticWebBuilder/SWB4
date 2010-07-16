package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class TimerIntermediateCatchEvent extends org.semanticwb.process.model.base.TimerIntermediateCatchEventBase 
{
    public TimerIntermediateCatchEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        ProcessObserver obs=instance.getProcessSite().getProcessObserver();
        if(!obs.hasTimeObserverInstance(instance))
        {
            obs.addTimeObserverInstance(instance);
        }
    }

    @Override
    public void notifyEvent(FlowNodeInstance instance, FlowNodeInstance from)
    {
        ProcessObserver obs=instance.getProcessSite().getProcessObserver();
        obs.removeTimeObserverInstance(instance);
        if(isInterruptor())
        {
            GraphicalElement subpro=instance.getFlowNodeType().getParent();
            if(subpro!=null && subpro instanceof SubProcess)
            {
                FlowNodeInstance source=instance.getRelatedFlowNodeInstance((SubProcess)subpro);
                source.close(instance.getCreator(), Instance.STATUS_CLOSED, Instance.ACTION_EVENT, false);
            }
        }
        instance.close(instance.getCreator(),instance.ACTION_EVENT);
    }

    @Override
    public void close(FlowNodeInstance instance, User user)
    {
        super.close(instance, user);
        ProcessObserver obs=instance.getProcessSite().getProcessObserver();
        obs.removeTimeObserverInstance(instance);
    }

}
