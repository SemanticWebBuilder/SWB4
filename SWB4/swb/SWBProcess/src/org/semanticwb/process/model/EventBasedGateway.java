package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class EventBasedGateway extends org.semanticwb.process.model.base.EventBasedGatewayBase 
{
    public EventBasedGateway(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.close(user,Instance.STATUS_OPEN,instance.getSourceInstance().getAction());
    }

    @Override
    public void nextObject(FlowNodeInstance instance, User user)
    {
        Iterator<ConnectionObject> it=listOutputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            connectionObject.execute(instance, user);
        }
        //SI LA COMPUERTA ES CONVERGENTE, PASAR EL CONTROL CON EL PRIMER FLUJO DE LLEGADA
    }

    @Override
    public void close(FlowNodeInstance instance, User user)
    {
        System.out.println("Cerrando EventBasedGateway");
/*
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
                    tari.close(user, Instance.ACTION_CANCEL);
                }
            }
        }
 *
 */
    }




}
