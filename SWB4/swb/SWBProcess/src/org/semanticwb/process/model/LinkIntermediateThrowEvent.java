package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class LinkIntermediateThrowEvent extends org.semanticwb.process.model.base.LinkIntermediateThrowEventBase 
{
    public LinkIntermediateThrowEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.close(user);
        ContainerInstanceable parent=instance.getContainerInstance();
        Iterator<GraphicalElement> it=null;
        if(parent instanceof SubProcessInstance)
        {
            it=((SubProcess)((SubProcessInstance)parent).getFlowNodeType()).listContaineds();
        }else
        {
            it=((Process)((ProcessInstance)parent).getProcessType()).listContaineds();
        }
        while (it.hasNext())
        {
            GraphicalElement graphicalElement = it.next();
            if(graphicalElement instanceof LinkIntermediateCatchEvent)
            {
                LinkIntermediateCatchEvent event=(LinkIntermediateCatchEvent)graphicalElement;
                String c1=event.getActionCode();
                String c2=((Event)instance.getFlowNodeType()).getActionCode();
                if((c1!=null && c1.equals(c2)) || c1==null && c2==null)
                {
                    FlowNodeInstance fn=instance.getRelatedFlowNodeInstance(event);
                    if(fn==null)
                    {
                        fn=event.createInstance(parent);
                        fn.start(user);
                    }
                    fn.setSourceInstance(instance);
                    event.notifyEvent(fn, instance);
                }
            }
        }
    }
}
