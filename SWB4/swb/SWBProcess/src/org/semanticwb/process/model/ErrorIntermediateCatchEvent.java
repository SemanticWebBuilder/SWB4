package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class ErrorIntermediateCatchEvent extends org.semanticwb.process.model.base.ErrorIntermediateCatchEventBase 
{
    public ErrorIntermediateCatchEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        if(instance.getSourceInstance().getFlowNodeType() instanceof ErrorEndEvent)
        {
            instance.close(user);
        }
    }
}
