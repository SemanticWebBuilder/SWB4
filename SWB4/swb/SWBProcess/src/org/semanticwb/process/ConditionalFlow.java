package org.semanticwb.process;


public class ConditionalFlow extends org.semanticwb.process.base.ConditionalFlowBase 
{
    public ConditionalFlow(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean evaluate(FlowObjectInstance instance)
    {
        boolean ret=true;
        String cond=getFlowCondition();
        String action=instance.getAction();
        if(cond!=null)
        {
            if(!cond.equals(action))ret=false;
        }
        return ret;
    }
}
