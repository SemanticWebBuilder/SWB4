package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class SubProcessInstance extends org.semanticwb.process.model.base.SubProcessInstanceBase 
{
    public SubProcessInstance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo
     * @param user
     */
    @Override
    public void start(FlowNodeInstance sourceInstance, ConnectionObject sourceConnection, User user)
    {
        super.start(sourceInstance,sourceConnection,user);
        SubProcess process=(SubProcess)getFlowNodeType();
        Iterator<GraphicalElement> actit=process.listContaineds();
        while (actit.hasNext())
        {
            GraphicalElement ele=actit.next();
            if(ele instanceof FlowNode)
            {
                FlowNode flownode = (FlowNode)ele;
                if(flownode.getClass().equals(StartEvent.class))
                {
                    StartEvent init=(StartEvent)flownode;
                    start(user, init);
                }
            }
        }
    }

    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo
     * @param user
     */
    public void start(User user, StartEvent event)
    {
        FlowNodeInstance eventins=event.createInstance(this);
        eventins.start(user);
    }


}
