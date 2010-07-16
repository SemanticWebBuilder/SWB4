package org.semanticwb.process.model;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.User;


public class ErrorEndEvent extends org.semanticwb.process.model.base.ErrorEndEventBase 
{
    public ErrorEndEvent(org.semanticwb.platform.SemanticObject base)
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
                if(graphicalElement instanceof ErrorIntermediateCatchEvent)
                {
                    ErrorIntermediateCatchEvent event=(ErrorIntermediateCatchEvent)graphicalElement;
                    String c1=event.getActionCode();
                    String c2=((Event)instance.getFlowNodeType()).getActionCode();
                    if((c1!=null && c1.equals(c2)) || c1==null && c2==null)
                    {
                        FlowNodeInstance source=(FlowNodeInstance)parent;
                        source.close(user, Instance.STATUS_ABORTED, Instance.ACTION_EVENT, false);

                        FlowNodeInstance fn=((FlowNodeInstance)parent).getRelatedFlowNodeInstance(event);
                        fn.setSourceInstance(instance);
                        event.notifyEvent(fn, instance);
                    }
                }
            }
        }else
        {
            instance.getParentInstance().close(user);
        }
    }

}
