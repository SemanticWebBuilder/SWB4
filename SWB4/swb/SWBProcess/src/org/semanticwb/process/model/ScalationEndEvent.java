package org.semanticwb.process.model;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.User;


public class ScalationEndEvent extends org.semanticwb.process.model.base.ScalationEndEventBase 
{
    public ScalationEndEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.close(user);
        Instance parent=instance.getParentInstance();
        if(parent instanceof SubProcessInstance)
        {
            //list atached events
            Iterator<GraphicalElement> it=((SubProcessInstance)parent).getFlowNodeType().listChilds();
            while (it.hasNext())
            {
                GraphicalElement graphicalElement = it.next();
                if(graphicalElement instanceof ScalationIntermediateCatchEvent)
                {
                    FlowNodeInstance source=(FlowNodeInstance)parent;
                    //TODO: Interruptora o no
                    source.setStatus(Instance.STATUS_CLOSED);
                    source.setAction(Instance.ACTION_EVENT);
                    source.setEnded(new Date());
                    source.setEndedby(user);
                    source.abortDependencies(user);

                    FlowNode node=(FlowNode)graphicalElement;
                    source.executeRelatedFlowNodeInstance(node, instance, null, user);
                }
            }
        }else
        {
            instance.getParentInstance().close(user);
        }
    }
}
