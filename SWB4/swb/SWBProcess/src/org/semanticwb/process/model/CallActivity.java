package org.semanticwb.process.model;

import org.semanticwb.model.User;


public class CallActivity extends org.semanticwb.process.model.base.CallActivityBase 
{
    public CallActivity(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);
    }


}
