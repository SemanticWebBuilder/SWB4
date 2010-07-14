package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

public class ProcessObserver extends org.semanticwb.process.model.base.ProcessObserverBase 
{
    private static Logger log=SWBUtils.getLogger(ProcessObserver.class);

    public ProcessObserver(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

}
