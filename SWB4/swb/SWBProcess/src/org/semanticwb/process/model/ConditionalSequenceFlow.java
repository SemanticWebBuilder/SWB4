package org.semanticwb.process.model;


public class ConditionalSequenceFlow extends org.semanticwb.process.model.base.ConditionalSequenceFlowBase 
{
    public ConditionalSequenceFlow(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean evaluate(FlowObjectInstance instance)
    {
        boolean ret = true;
        String cond = getConditionExpression().getExpressionBody();
        String action = instance.getAction();
        if(cond != null) {
            if(!cond.equals(action)) {
                ret = false;
            }
        }
        return ret;
    }
}
