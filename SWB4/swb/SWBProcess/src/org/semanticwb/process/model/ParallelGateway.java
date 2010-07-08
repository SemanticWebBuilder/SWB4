package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class ParallelGateway extends org.semanticwb.process.model.base.ParallelGatewayBase 
{
    public ParallelGateway(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }


    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        //SI LA COMPUERTA ES CONVERGENTE, ESPERAR A QUE SE COMPLETEN LOS FLUJOS
        boolean ret=true;
        Iterator<ConnectionObject> it=listInputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            GraphicalElement obj=connectionObject.getSource();
            if(obj instanceof FlowNode)
            {
                FlowNodeInstance inst=instance.getRelatedFlowNodeInstance((FlowNode)obj);
                if(inst==null)
                {
                    ret=false;
                    break;
                }else if(inst.getStatus()<Instance.STATUS_CLOSED)
                {
                    ret=false;
                    break;
                }
            }
        }
        if(ret)
        {
            instance.close(user,instance.getSourceInstance().getAction());
        }

        //SI LA COMPUERTA ES DIVERGENTE, HABILITAR TODOS LOS FLUJOS
    }


}
