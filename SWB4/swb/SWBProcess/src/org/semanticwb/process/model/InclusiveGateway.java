package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class InclusiveGateway extends org.semanticwb.process.model.base.InclusiveGatewayBase 
{
    public InclusiveGateway(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        boolean ret=false;
        Iterator<ConnectionObject> it=listInputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            GraphicalElement obj=connectionObject.getSource();
            if(obj instanceof FlowNode)
            {
                FlowNodeInstance inst=instance.getRelatedFlowNodeInstance((FlowNode)obj);
                if(inst!=null && inst.getStatus()>=Instance.STATUS_CLOSED)
                {
                    ret=true;
                    break;
                }
            }
        }
        if(ret)
        {
            instance.close(user);
        }
    }

}
