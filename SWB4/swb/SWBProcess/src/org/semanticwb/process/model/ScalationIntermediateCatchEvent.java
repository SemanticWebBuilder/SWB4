package org.semanticwb.process.model;

import java.util.Date;
import org.semanticwb.model.User;


public class ScalationIntermediateCatchEvent extends org.semanticwb.process.model.base.ScalationIntermediateCatchEventBase 
{
    public ScalationIntermediateCatchEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
    }


    @Override
    public void notifyEvent(FlowNodeInstance instance, FlowNodeInstance from)
    {
        if(isInterruptor())
        {
            GraphicalElement subpro=instance.getFlowNodeType().getParent();
            if(subpro!=null && subpro instanceof SubProcess)
            {
                FlowNodeInstance source=instance.getRelatedFlowNodeInstance((SubProcess)subpro);
                source.close(from.getCreator(), Instance.STATUS_CLOSED, Instance.ACTION_EVENT, false);
            }
        }
        instance.close(from.getCreator(),from.getSourceInstance().getAction());
    }
}
