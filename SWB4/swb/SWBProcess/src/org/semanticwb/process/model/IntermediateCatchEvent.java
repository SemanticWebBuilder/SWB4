/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.model;

import java.util.Iterator;
import org.semanticwb.model.User;


public class IntermediateCatchEvent extends org.semanticwb.process.model.base.IntermediateCatchEventBase 
{
    public IntermediateCatchEvent(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        instance.close(user);
    }
    
    @Override
    public void notifyEvent(FlowNodeInstance instance, FlowNodeInstance from)
    {
        //Cerramos gateways basados en eventos
        instance.setStatus(Instance.STATUS_CLOSED);        
        Iterator<ConnectionObject> it=listInputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            GraphicalElement tele=connectionObject.getSource();
            if(tele instanceof ExclusiveIntermediateEventGateway)
            {
                FlowNodeInstance tari=instance.getRelatedFlowNodeInstance((FlowNode)tele);
                if(tari.getStatus()!=Instance.STATUS_CLOSED)
                {
                    tari.close(instance.getCreator(), Instance.STATUS_CLOSED, Instance.ACTION_EVENT,false);
                }
            }
        }           
        
        //System.out.println("instance:"+instance);
        //System.out.println("from:"+from);
        if(isInterruptor())
        {
            GraphicalElement subpro=getParent();
            //System.out.println("subpro:"+subpro);
            if(subpro!=null && subpro instanceof FlowNode)
            {
                FlowNodeInstance source=instance.getRelatedFlowNodeInstance((FlowNode)subpro);
                //System.out.println("source"+source);
                if(from!=null)
                {
                    source.close(from.getCreator(), Instance.STATUS_CLOSED, Instance.ACTION_EVENT, false);
                }else
                {
                    source.close(instance.getCreator(), Instance.STATUS_CLOSED, Instance.ACTION_EVENT, false);
                }
            }
        }
        //System.out.println("source2:"+from.getSourceInstance());
        if(from!=null)
        {
            instance.close(from.getCreator(),from.getSourceInstance().getAction());
        }else
        {
            instance.close(instance.getCreator());
        }
    }
}
