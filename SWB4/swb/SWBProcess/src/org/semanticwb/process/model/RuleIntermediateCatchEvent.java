package org.semanticwb.process.model;

import bsh.Interpreter;
import java.util.Date;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;


public class RuleIntermediateCatchEvent extends org.semanticwb.process.model.base.RuleIntermediateCatchEventBase 
{
    public static Logger log=SWBUtils.getLogger(RuleIntermediateCatchEvent.class);

    public RuleIntermediateCatchEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        boolean cond=false;
        Iterator<ProcessRuleRef> it=listProcessRuleRefs();
        while (it.hasNext())
        {
            ProcessRuleRef ref = it.next();
            if(ref.isActive())
            {
                ProcessRule rule=ref.getProcessRule();
                if(rule.evaluate(instance, user))
                {
                    cond = true;
                    break;
                }
            }
        }

        if(cond)
        {
            instance.close(user);
            GraphicalElement parent=getParent();
            if(parent!=null)
            {
                FlowNodeInstance source=instance.getRelatedFlowNodeInstance((FlowNode)parent);
                source.setStatus(Instance.STATUS_CLOSED);
                source.setAction(Instance.ACTION_EVENT);
                source.setEnded(new Date());
                source.setEndedby(user);
                source.abortDependencies(user);
            }
        }
    }


}
