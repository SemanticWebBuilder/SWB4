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
        //SI LA COMPUERTA ES DIVERGENTE, HABILITAR LOS FLUJOS QUE CUMPLEN SU CONDICIÓN
          //Obtener todos los flujos de salida
          //Para cada flujo de salida
            //Evaluar la condición del flujo
              //Si la condición se cumple, habilitar ese flujo
          //Si ninguna condición se cumple, seguir por el flujo de defecto

        //SI LA COMPUERTA ES CONVERGENTE, ESPERAR A QUE TERMINEN LOS FLUJOS DE ENTRADA
          //Obtener el número de flujos de entrada (fi)
          //Terminados = 0
          //Mientras terminados < fi
              //Obtener todos los flujos de entrada
                //Para cada flujo obtener el source
                  //Si la tarea source se ha completado
                    //Incrementar el contador de terminados
          //Continuar con el flujo
        //SI LA COMPUERTA ES MIXTA, EVALUAR LA CONDICION

        //SI LA COMPUERTA ES CONVERGENTE, ESPERAR A QUE SE COMPLETEN LOS FLUJOS HABILITADOS
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
                    //ret=false;
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
    }

}
