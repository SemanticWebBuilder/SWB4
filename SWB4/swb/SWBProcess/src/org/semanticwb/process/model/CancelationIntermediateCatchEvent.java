package org.semanticwb.process.model;


public class CancelationIntermediateCatchEvent extends org.semanticwb.process.model.base.CancelationIntermediateCatchEventBase 
{
    public CancelationIntermediateCatchEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public void notifyEvent(FlowNodeInstance instance, FlowNodeInstance from)
    {
        instance.close(from.getCreator(),from.getSourceInstance().getAction());
    }
}
