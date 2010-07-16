package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class SignalEndEvent extends org.semanticwb.process.model.base.SignalEndEventBase 
{
    public SignalEndEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.getProcessSite().getProcessObserver().sendSignal(instance);
        instance.close(user,instance.getSourceInstance().getAction());
        instance.getParentInstance().close(user);
    }
}
