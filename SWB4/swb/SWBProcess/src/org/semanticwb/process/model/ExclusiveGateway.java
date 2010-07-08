package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class ExclusiveGateway extends org.semanticwb.process.model.base.ExclusiveGatewayBase 
{
    public ExclusiveGateway(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.close(user,instance.getSourceInstance().getAction());
    }

    @Override
    public void nextObject(FlowNodeInstance instance, User user)
    {
        //SI LA COMPUERTA ES DIVERGENTE, EVALUAR LAS CONDICIONES
        //System.out.println("nextObject:"+getId()+" "+getFlowNodeType().getClass().getName()+" "+getFlowNodeType().getTitle());
        DefaultFlow def=null;
        boolean execute=false;
        Iterator<ConnectionObject> it=listOutputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            if(!(connectionObject instanceof DefaultFlow))
            {
                if(connectionObject.evaluate(instance, user))
                {
                    connectionObject.execute(instance, user);
                    execute=true;
                    break;
                }
            }else
            {
                def=(DefaultFlow)connectionObject;
            }
        }
        if(!execute)def.execute(instance, user);

        //SI LA COMPUERTA ES CONVERGENTE, PASAR EL CONTROL CON EL PRIMER FLUJO DE LLEGADA
    }
}
