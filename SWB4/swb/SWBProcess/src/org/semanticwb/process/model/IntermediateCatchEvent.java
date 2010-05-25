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
}
