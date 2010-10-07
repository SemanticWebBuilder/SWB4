package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class MessageIntermediateThrowEvent extends org.semanticwb.process.model.base.MessageIntermediateThrowEventBase 
{
    public MessageIntermediateThrowEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user) {
        super.execute(instance, user);
    }


}
