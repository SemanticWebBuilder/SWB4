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
                    startEvent(user, init);
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
        super.start(user);
        startEvent(user, event);
    }

    private void startEvent(User user, StartEvent event)
    {
        FlowNodeInstance eventins=event.createInstance(this);
        eventins.start(user);
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


}
