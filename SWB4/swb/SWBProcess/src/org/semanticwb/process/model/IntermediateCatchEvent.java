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
        //System.out.println("instance:"+instance);
        //System.out.println("from:"+from);
        if(isInterruptor())
        {
            GraphicalElement subpro=getParent();
            //System.out.println("subpro:"+subpro);
            if(subpro!=null && subpro instanceof FlowNode)
            {
                FlowNodeInstance source=instance.getRelatedFlowNodeInstance((FlowNode)subpro);
                //System.out.println("source"+source);
                if(from!=null)
                {
                    source.close(from.getCreator(), Instance.STATUS_CLOSED, Instance.ACTION_EVENT, false);
                }else
                {
                    source.close(instance.getCreator(), Instance.STATUS_CLOSED, Instance.ACTION_EVENT, false);
                }
            }
        }
        //System.out.println("source2:"+from.getSourceInstance());
        if(from!=null)
        {
            instance.close(from.getCreator(),from.getSourceInstance().getAction());
        }else
        {
            instance.close(instance.getCreator());
        }
    }
}
