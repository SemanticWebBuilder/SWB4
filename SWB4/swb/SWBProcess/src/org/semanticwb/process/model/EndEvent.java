package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class EndEvent extends org.semanticwb.process.model.base.EndEventBase 
{
    public EndEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.close(user);
        instance.getParentInstance().close(user);
    }
}
