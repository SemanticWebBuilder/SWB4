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
                if(flownode.getClass().equals(StartEvent.class))
                {
                    StartEvent init=(StartEvent)flownode;
                    start(user, init);
                }
            }
        }
    }

    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo
     * @param user
     */
    public void start(User user, StartEvent event)
    {
        FlowNodeInstance eventins=event.createInstance(this);
        eventins.start(user);
    }

    private void listAllProcessObjects(ContainerInstanceable inst, ArrayList arr)
    {
        Iterator<ProcessObject> it=inst.listProcessObjects();
        while(it.hasNext())
        {
            ProcessObject obj = it.next();
            arr.add(obj);
        }

        Iterator<FlowNodeInstance> it2=inst.listFlowNodeInstances();
        while(it2.hasNext())
        {
            FlowNodeInstance obj = it2.next();
            if(obj instanceof ContainerInstanceable)
            {
                listAllProcessObjects((ContainerInstanceable)obj, arr);
            }
        }
    }

    /**
     * Regresa todos los Objetos de procesos del proceso y subprocesos
     * @return
     */
    public Iterator<ProcessObject> listAllProcessObjects()
    {
        ArrayList<ProcessObject> ret=new ArrayList();
        listAllProcessObjects(this, ret);
        return ret.iterator();
    }


}
