package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class ExclusiveIntermediateEventGateway extends org.semanticwb.process.model.base.ExclusiveIntermediateEventGatewayBase 
{
    public ExclusiveIntermediateEventGateway(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user) {
        nextObject(instance, user);
        //instance.close(user,Instance.STATUS_OPEN,instance.getSourceInstance().getAction(),false);
    }

    @Override
    public void close(FlowNodeInstance instance, User user) {
        Iterator<ConnectionObject> it=listOutputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            GraphicalElement tele=connectionObject.getTarget();
            if(tele instanceof FlowNode)
            {
                FlowNodeInstance tari=instance.getRelatedFlowNodeInstance((FlowNode)tele);
                if(tari.getStatus()!=Instance.STATUS_CLOSED)
                {
                    tari.close(user, Instance.STATUS_ABORTED, Instance.ACTION_CANCEL,false);
                }
            }
        }        
    }
 

    
    
    
    
}
