package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class ConditionalFlow extends org.semanticwb.process.model.base.ConditionalFlowBase 
{
    public ConditionalFlow(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public boolean evaluate(FlowNodeInstance source, User user)
    {
        boolean cond=true;
        String scond=getFlowCondition();
        String action=source.getAction();
        if(scond!=null)
        {
            if(!scond.equals(action))cond=false;
        }
        return cond;
    }



    @Override
    public void execute(FlowNodeInstance source, User user)
    {
        super.execute(source,user);
    }

}
