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
    public void start(User user)
    {
        super.start(user);
        SubProcess process=(SubProcess)getFlowNodeType();
        Iterator<GraphicalElement> actit=process.listContaineds();
        while (actit.hasNext())
        {
            GraphicalElement ele=actit.next();
            if(ele instanceof FlowNode)
            {
                FlowNode flowobject = (FlowNode)ele;
                if(flowobject instanceof StartEvent)
                {
                    StartEvent init=(StartEvent)flowobject;
                    FlowNodeInstance eventins=init.createInstance(this);
                    eventins.start(user);
                }
            }
        }
    }

}
