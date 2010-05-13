package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.model.User;


public class FlowNodeInstance extends org.semanticwb.process.model.base.FlowNodeInstanceBase 
{
    public FlowNodeInstance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public ProcessInstance getProcessInstance()
    {
        Instance ins=getParentInstance();
        while(ins!=null && !(ins instanceof ProcessInstance))
        {
            ins=((FlowNodeInstance)ins).getParentInstance();
        }
        return (ProcessInstance)ins;
    }
    
    public Instance getParentInstance()
    {
        return (Instance)getContainerInstance();
    }
    
    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo
     * @param user
     */
    @Override
    public void start(User user)
    {
        super.start(user);
        FlowNode type=getFlowNodeType();
        //if(type instanceof Event)
        {
            execute(user);
        }
        
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    @Override
    public void close(User user, int status, String action)
    {
        super.close(user,status,action);
        abortDependencies(user);
        nextObject(user);
    }

    /**
     * Se ejecuta cada que obtiene el foco del flujo
     * @param user
     */
    @Override
    public void execute(User user)
    {
        super.execute(user);
        FlowNode type=getFlowNodeType();
        type.execute(this, user);
    }

    /**
     * Regresa instancia del FlowNode pasado por parametro,
     * busca dentro de los nodo hermanos detro del mismo proceso
     */
    public FlowNodeInstance getRelatedFlowNodeInstance(FlowNode node)
    {
        FlowNodeInstance ret=null;
        ContainerInstanceable parent=getContainerInstance();
        Iterator<FlowNodeInstance> it=parent.listFlowNodeInstances();
        while (it.hasNext())
        {
            FlowNodeInstance flowNodeInstance = it.next();
            if(flowNodeInstance.getFlowNodeType().equals(node))
            {
                ret=flowNodeInstance;
                break;
            }
        }
        return ret;
    }


    /**
     * Continua el flujo al siguiente FlowNode
     * @param user
     */
    private void nextObject(User user)
    {
        //System.out.println("nextObject:"+getId()+" "+getFlowNodeType().getClass().getName()+" "+getFlowNodeType().getTitle());
        FlowNode type=getFlowNodeType();
        Iterator<ConnectionObject> it=type.listOutputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            connectionObject.execute(this, user);
        }
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    private void abortDependencies(User user)
    {
        //System.out.println("abortDependencies:"+getId()+" "+getFlowNodeType().getClass().getName()+" "+getFlowNodeType().getTitle());
        FlowNode type=getFlowNodeType();

        //Cerrar dependencias
        Iterator<ConnectionObject> it=type.listInputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            if(connectionObject instanceof SequenceFlow)
            {
                if(connectionObject.getClass().equals(SequenceFlow.class))
                {
                    //System.out.println(connectionObject);
                    GraphicalElement obj=connectionObject.getSource();
                    if(obj instanceof FlowNode)
                    {
                        FlowNode node=(FlowNode)obj;
                        FlowNodeInstance inst=this.getRelatedFlowNodeInstance(node);
                        if(inst==null)
                        {
                            inst=node.createInstance(getContainerInstance());
                        }
                        if(inst.getStatus()<Instance.STATUS_ABORTED)
                        {
                            inst.setStatus(Instance.STATUS_ABORTED);
                            inst.setAction(Instance.ACTION_CANCEL);
                            inst.setEnded(new Date());
                            inst.setEndedby(user);
                            inst.abortDependencies(user);
                        }
                    }
                }
            }
        }
    }

    /**
     * Reinicia la instancia del objeto de flujo y sus sucesores
     */
    protected void reset()
    {
        //System.out.println("reset:"+getId()+" "+getFlowNodeType().getClass().getName()+" "+getFlowNodeType().getTitle());
        //Thread.dumpStack();
        setStatus(Instance.STATUS_INIT);
        setAction(null);
        setEnded(null);
        removeEndedby();

        FlowNode type=getFlowNodeType();
        //resetear subsecuentes
        Iterator<ConnectionObject> it=type.listOutputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            if(connectionObject instanceof SequenceFlow)
            {
                if(connectionObject.getClass().equals(SequenceFlow.class))
                {
                    GraphicalElement obj=connectionObject.getTarget();
                    if(obj instanceof FlowNode)
                    {
                        FlowNode node=(FlowNode)obj;
                        FlowNodeInstance inst=this.getRelatedFlowNodeInstance(node);
                        if(inst!=null && inst.getStatus()>Instance.STATUS_PROCESSING)
                        {
                            inst.reset();
                        }
                    }
                }
            }
        }
    }


}
