package org.semanticwb.process.model;

import com.hp.hpl.jena.rdf.model.Statement;
import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class FlowNodeInstance extends org.semanticwb.process.model.base.FlowNodeInstanceBase 
{
    public FlowNodeInstance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
        //System.out.println("FlowNodeInstance("+base+")");
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
        //System.out.println("start("+user+")");
        execute(user);
    }
    
    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo
     * @param user
     */
    public void start(FlowNodeInstance sourceInstance, ConnectionObject sourceConnection, User user)
    {
        super.start(user);
        //System.out.println("start("+sourceInstance+","+sourceConnection+","+user+")");
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
        //System.out.println("close("+user+","+status+","+action+")");
        close(user, status, action, true);
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto si nexObjects = true
     * @param user
     */
    public void close(User user, int status, String action, boolean nextObjects)
    {
        super.close(user,status,action);
        //System.out.println("close("+user+","+status+","+action+","+nextObjects+")");
        abortDependencies(user);

        connectItemsAware();

        if(nextObjects)
        {
            FlowNode type=getFlowNodeType();
            type.nextObject(this, user);
        }
        this.getFlowNodeType().close(this, user);

        removeTemporallyDataobjects();
    }

    /**
     * Se ejecuta cada que obtiene el foco del flujo
     * @param user
     */
    @Override
    public void execute(User user)
    {
        super.execute(user);
        //System.out.println("execute("+user+")");
        FlowNode type=getFlowNodeType();
        type.execute(this, user);
    }

    @Override
    public void notifyEvent(FlowNodeInstance from)
    {
        CatchEvent type=(CatchEvent)getFlowNodeType();
        type.notifyEvent(this, from);
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
     * @param node
     * @param con
     * @param user
     * @return
     */
    public FlowNodeInstance executeRelatedFlowNodeInstance(FlowNode node, ConnectionObject con, User user)
    {
        return executeRelatedFlowNodeInstance(node, this, con, user);
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
            if(con!=null)
            {
                inst.setFromConnection(con);
            }
            inst.setSourceInstance(ref);
            inst.execute(user);
        }
        return inst;
    }

    /**
     * Relaciona los ItemAware de de salida con las entradas de los flow nods
     */
    protected void connectItemsAware()
    {
        //System.out.println("connectItemsAware:"+this);
        //Tipo de FlowNode
        FlowNode type=getFlowNodeType();
        Iterator<ConnectionObject> it=type.listOutputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            if(connectionObject instanceof DirectionalAssociation)
            {
                DirectionalAssociation ass=(DirectionalAssociation)connectionObject;
                GraphicalElement ele=ass.getTarget();
                //Si el objeto de salida es un DataStore
                if(ele instanceof ItemAware)
                {
                    ItemAware store=(ItemAware)ele;
                    String var=store.getName();
                    SemanticClass cls=store.getItemSemanticClass();

                    if(var!=null && cls!=null)
                    {
                        Iterator<ItemAwareReference> it2=listHeraquicalItemAwareReference().iterator();
                        while (it2.hasNext())
                        {
                            ItemAwareReference itemAwareReference = it2.next();
                            if(var.equals(itemAwareReference.getItemAware().getName()) && cls.equals(itemAwareReference.getItemAware().getItemSemanticClass()))
                            {
                                SemanticObject in=itemAwareReference.getProcessObject().getSemanticObject();
                                SWBModel model=this.getProcessSite();
                                if(store instanceof Collectionable)
                                {
                                    model=this.getProcessSite().getProcessDataInstanceModel();
                                }
                                SemanticObject out=null;
                                if(in.getModel().equals(model.getSemanticModel()))
                                {
                                    out=in;
                                }else
                                {
                                    long id=model.getSemanticModel().getCounter(cls);
                                    out=model.getSemanticModel().createSemanticObjectById(String.valueOf(id), cls);
                                    Iterator<Statement> it3=in.getRDFResource().listProperties();
                                    while (it3.hasNext())
                                    {
                                        Statement statement = it3.next();
                                        out.getRDFResource().addProperty(statement.getPredicate(), statement.getObject());
                                    }
                                }

                                //Revisar si hay salida
                                Iterator<ConnectionObject> it4=store.listOutputConnectionObjects();
                                while (it4.hasNext())
                                {
                                    ConnectionObject connectionObject1 = it4.next();
                                    if(connectionObject1 instanceof DirectionalAssociation)
                                    {
                                        GraphicalElement gele=connectionObject1.getTarget();
                                        if(gele instanceof FlowNode)
                                        {
                                            FlowNodeInstance inst=getRelatedFlowNodeInstance((FlowNode)gele);
                                            if(inst==null)
                                            {
                                                inst=((FlowNode)gele).createInstance(getContainerInstance());
                                            }
                                            //System.out.println("item:"+store);
                                            //System.out.println("this:"+this);
                                            //System.out.println("inst:"+inst);
                                            //System.out.println("gele:"+gele);
                                            //System.out.println("type:"+getFlowNodeType());
                                            //System.out.println("connectionObject1:"+connectionObject1);
                                            //System.out.println("source:"+connectionObject1.getSource());
                                            //System.out.println("target:"+connectionObject1.getTarget());

                                            ItemAwareReference ref=ItemAwareReference.ClassMgr.createItemAwareReference(this.getProcessSite());
                                            ref.setItemAware(store);
                                            ref.setProcessObject((SWBClass)out.createGenericInstance());
                                            inst.addItemAwareReference(ref);
                                            itemAwareReference.setProcessObjectReused(true);
                                            //System.out.println("connectItemsAware set:"+inst);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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
                            inst.close(user, STATUS_ABORTED, ACTION_CANCEL, false);
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
