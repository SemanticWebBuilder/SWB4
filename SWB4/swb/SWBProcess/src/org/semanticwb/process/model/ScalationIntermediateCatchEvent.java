package org.semanticwb.process.model;

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
        if(instance.getSourceInstance().getFlowNodeType() instanceof ScalationEndEvent)
        {
            instance.close(user);
        }
    }
}
