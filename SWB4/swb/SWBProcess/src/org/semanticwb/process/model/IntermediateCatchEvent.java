package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class IntermediateCatchEvent extends org.semanticwb.process.model.base.IntermediateCatchEventBase 
{
    public IntermediateCatchEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.close(user);
    }
    
    @Override
    public void notifyEvent(FlowNodeInstance instance, FlowNodeInstance from)
    {
        if(isInterruptor())
        {
            GraphicalElement subpro=getParent();
            if(subpro!=null && subpro instanceof FlowNode)
            {
                FlowNodeInstance source=instance.getRelatedFlowNodeInstance((FlowNode)subpro);
                source.close(from.getCreator(), Instance.STATUS_CLOSED, Instance.ACTION_EVENT, false);
            }
        }
        instance.close(from.getCreator(),from.getSourceInstance().getAction());
    }
}
