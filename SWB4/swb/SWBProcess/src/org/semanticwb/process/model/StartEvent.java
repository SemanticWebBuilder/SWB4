package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class StartEvent extends org.semanticwb.process.model.base.StartEventBase 
{
    public StartEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.close(user);
    }

}
