package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class ProcessInstance extends org.semanticwb.process.model.base.ProcessInstanceBase 
{
    public ProcessInstance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo
     * @param user
     */
    @Override
    public void start(User user)
    {
        super.start(user);
        Process process=getProcessType();
        Iterator<GraphicalElement> actit=process.listContaineds();
        while (actit.hasNext())
        {
            GraphicalElement ele=actit.next();
            if(ele instanceof FlowNode)
            {
                FlowNode flownode = (FlowNode)ele;
                if(flownode instanceof StartEvent)
                {
                    StartEvent init=(StartEvent)flownode;
                    FlowNodeInstance eventins=init.createInstance(this);
                    eventins.start(user);
                }
            }
        }
    }

}
