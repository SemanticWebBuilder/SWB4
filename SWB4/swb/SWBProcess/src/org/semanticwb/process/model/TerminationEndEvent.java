package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class TerminationEndEvent extends org.semanticwb.process.model.base.TerminationEndEventBase 
{
    public TerminationEndEvent(org.semanticwb.platform.SemanticObject base)
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
