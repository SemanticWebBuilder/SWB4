package org.semanticwb.process.model;

import bsh.Interpreter;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;


public class ConditionalFlow extends org.semanticwb.process.model.base.ConditionalFlowBase 
{
    public static Logger log=SWBUtils.getLogger(ConditionalFlow.class);

    public ConditionalFlow(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public boolean evaluate(FlowNodeInstance sourceInstance, User user)
    {
        boolean cond=false;
        Iterator<ProcessRuleRef> it=listProcessRuleRefs();
        while (it.hasNext())
        {
            ProcessRuleRef ref = it.next();
            if(ref.isActive())
            {
                ProcessRule rule=ref.getProcessRule();
                if(rule.evaluate(sourceInstance, user))
                {
                    cond = true;
                    break;
                }
            }
        }
        return cond;
    }



    @Override
    public void execute(FlowNodeInstance source, User user)
    {
        super.execute(source,user);
    }

}
