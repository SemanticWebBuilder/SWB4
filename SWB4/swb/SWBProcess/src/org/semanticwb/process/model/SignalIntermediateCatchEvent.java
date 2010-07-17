package org.semanticwb.process.model;

import java.util.Date;
import org.semanticwb.model.User;


public class SignalIntermediateCatchEvent extends org.semanticwb.process.model.base.SignalIntermediateCatchEventBase 
{
    public SignalIntermediateCatchEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        ProcessObserver obs=instance.getProcessSite().getProcessObserver();
        if(!obs.hasSignalObserverInstance(instance))
        {
            obs.addSignalObserverInstance(instance);
        }
    }

//    @Override
//    public void notifyEvent(FlowNodeInstance instance, FlowNodeInstance from)
//    {
//        ProcessObserver obs=instance.getProcessSite().getProcessObserver();
//        obs.removeSignalObserverInstance(instance);
//        if(isInterruptor())
//        {
//            GraphicalElement subpro=instance.getFlowNodeType().getParent();
//            if(subpro!=null && subpro instanceof FlowNode)
//            {
//                FlowNodeInstance source=instance.getRelatedFlowNodeInstance((FlowNode)subpro);
//                source.close(from.getCreator(), Instance.STATUS_CLOSED, Instance.ACTION_EVENT, false);
//            }
//        }
//        instance.close(from.getCreator(),from.getSourceInstance().getAction());
//    }

    @Override
    public void close(FlowNodeInstance instance, User user)
    {
        super.close(instance, user);
        ProcessObserver obs=instance.getProcessSite().getProcessObserver();
        obs.removeSignalObserverInstance(instance);
    }

}
