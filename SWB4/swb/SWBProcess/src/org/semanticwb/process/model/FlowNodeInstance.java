package org.semanticwb.process.model;

import java.util.Date;
import java.util.Iterator;
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
        execute(user);
    }
    
    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo
     * @param user
     */
    public void start(FlowNodeInstance sourceInstance, ConnectionObject sourceConnection, User user)
    {
        super.start(user);
        setSourceInstance(sourceInstance);
        if(sourceConnection!=null)setFromConnection(sourceConnection);
        execute(user);
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
        FlowNode type=getFlowNodeType();
        type.nextObject(this, user);
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
     * Regresa instancia del FlowNode pasado por parametro,
     * busca dentro de los nodo hermanos detro del mismo proceso
     * Si la instancia no esta creada la crea, la inicia y la executa
     * Si la instancia ya existe, la resetea y la executa
     */
    public FlowNodeInstance executeRelatedFlowNodeInstance(FlowNode node, FlowNodeInstance ref, ConnectionObject con, User user)
    {
        FlowNodeInstance inst=getRelatedFlowNodeInstance(node);
        if(inst==null)
        {
            inst=node.createInstance(getContainerInstance());
        }else
        {
            //recrear instancia en ciclos
            int status=inst.getStatus();
            if(status==Instance.STATUS_ABORTED || status==Instance.STATUS_CLOSED)
            {
                inst.reset();
            }
        }
        if(inst.getStatus()==Instance.STATUS_INIT)
        {
            inst.start(ref,con,user);
        }else
        {
            if(con!=null)inst.setFromConnection(con);
            inst.setSourceInstance(ref);
            inst.execute(user);
        }
        return inst;
    }



    /**
     * Aborta las instancias anteriores ene el flujo a esta instancia
     * @param user
     */
    public void abortDependencies(User user)
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
        setIteration(getIteration()+1);
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

    /**
     * Regresa Pagina Web asociada al proceso
     * @return ProcessWebPage
     */
    public ProcessWebPage getProcessWebPage()
    {
        return getProcessInstance().getProcessType().getProcessWebPage();
    }


}
