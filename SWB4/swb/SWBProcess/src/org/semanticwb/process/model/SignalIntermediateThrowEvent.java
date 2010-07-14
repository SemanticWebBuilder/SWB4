package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class SignalIntermediateThrowEvent extends org.semanticwb.process.model.base.SignalIntermediateThrowEventBase 
{
    public SignalIntermediateThrowEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.getProcessSite().getProcessObserver().sendSignal(instance);
        instance.close(user,instance.getSourceInstance().getAction());
    }


}
