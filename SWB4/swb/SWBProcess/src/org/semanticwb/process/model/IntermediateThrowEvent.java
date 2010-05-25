package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class IntermediateThrowEvent extends org.semanticwb.process.model.base.IntermediateThrowEventBase 
{
    public IntermediateThrowEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.close(user);
    }
}
