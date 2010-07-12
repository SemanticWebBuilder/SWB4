package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class SequenceFlow extends org.semanticwb.process.model.base.SequenceFlowBase 
{
    public SequenceFlow(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance source, User user)
    {
        GraphicalElement target=getTarget();
        if(target instanceof FlowNode)
        {
            FlowNode node=(FlowNode)target;
            source.executeRelatedFlowNodeInstance(node, this, user);
        }

    }    
}
