/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
**/

package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.model.User;


public class ProcessInstance extends org.semanticwb.process.model.base.ProcessInstanceBase 
{
    public ProcessInstance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo desde un evento de inicio normal
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
                    startEvent(user, init);
                }
            }
        }
    }

    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo desde el processObserver
     * @param user
     */
    public void start(User user, StartEventNode event)
    {
        super.start(user);
        startEvent(user, event);
    }

    private void startEvent(User user, StartEventNode event)
    {
        FlowNodeInstance eventins=event.createInstance(this);
        eventins.start(user);
    }
    
    public void start(User user, StartEventNode event, FlowNodeInstance invoker)
    {
        super.start(user);
        FlowNodeInstance eventins=event.createInstance(this);
        eventins.start(invoker,null,user);
    }    

    private void listAllItemAwareReferences(Instance inst, ArrayList arr)
    {
        Iterator<ItemAwareReference> it=inst.listItemAwareReferences();
        while(it.hasNext())
        {
            ItemAwareReference item=it.next();
            arr.add(item);
        }

        if(inst instanceof ContainerInstanceable)
        {
            Iterator<FlowNodeInstance> it2=((ContainerInstanceable)inst).listFlowNodeInstances();
            while(it2.hasNext())
            {
                FlowNodeInstance obj = it2.next();
                listAllItemAwareReferences(obj, arr);
            }
        }

    }

    @Override
    public void close(User user, int status, String action)
    {
        super.close(user, status, action);
        
        connectItemsAware();
        
        Iterator<FlowNodeInstance> it=listAllFlowNodeInstance();
        while (it.hasNext())
        {
            FlowNodeInstance flowNodeInstance = it.next();
            if(flowNodeInstance.getStatus()!=Instance.STATUS_CLOSED && flowNodeInstance.getStatus()!=Instance.STATUS_ABORTED)
            {
                flowNodeInstance.setStatus(Instance.STATUS_ABORTED);
            }
        }

        removeTemporallyDataobjects();
    }

    /**
     * Relaciona los ItemAware de entrada o globales con los ItemAware de salida
     */
    protected void connectItemsAware()
    {
        //TODO: Revisar variables de salida
    }

    /**
     * Regresa todos los ItemAwareReferences del proceso y subprocesos
     * @return
     */
    public Iterator<ItemAwareReference> listAllItemAwareReferences()
    {
        ArrayList<ItemAwareReference> ret=new ArrayList();
        listAllItemAwareReferences(this, ret);
        return ret.iterator();
    }

    public Iterator<FlowNodeInstance> listAllFlowNodeInstance()
    {
        ArrayList<FlowNodeInstance> arr=new ArrayList();
        Iterator<FlowNodeInstance> it=listFlowNodeInstances();
        while (it.hasNext())
        {
            FlowNodeInstance flowNodeInstance = it.next();
            arr.add(flowNodeInstance);
            if(flowNodeInstance instanceof SubProcessInstance)
            {
                Iterator<FlowNodeInstance> it2=((SubProcessInstance)(flowNodeInstance)).listAllFlowNodeInstance();
                while (it2.hasNext())
                {
                    FlowNodeInstance flowNodeInstance2 = it2.next();
                    arr.add(flowNodeInstance2);
                }
            }
        }
        return arr.iterator();
    }


}
