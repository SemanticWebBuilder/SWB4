package org.semanticwb.process.model;

import java.util.Date;
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
    }

    @Override
    public void notifyEvent(FlowNodeInstance instance, FlowNodeInstance from)
    {
        instance.close(from.getCreator(),from.getSourceInstance().getAction());
    }
}
