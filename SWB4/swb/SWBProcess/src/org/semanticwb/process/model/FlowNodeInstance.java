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

import bsh.Interpreter;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDF;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import static org.semanticwb.process.model.Instance.ACTION_CANCEL;
import static org.semanticwb.process.model.Instance.STATUS_ABORTED;

public class FlowNodeInstance extends org.semanticwb.process.model.base.FlowNodeInstanceBase 
{
    public static Logger log=SWBUtils.getLogger(ProcessRule.class);

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
        //System.out.println("start("+this+","+user+")");
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
        //System.out.println("close("+this+","+user+","+status+","+action+")");
        close(user, status, action, true);
    }
    
    private void _close(User user, int status, boolean nextObjects)
    {
        //System.out.println("_close("+this+","+user+","+status+","+nextObjects+")");
        
        abortDependencies(user);

        connectItemsAware(user);

        if(nextObjects)
        {
            FlowNode type=getFlowNodeType();
            type.nextObject(this, user);
        }
        getFlowNodeType().close(this, user);

        if(status==Instance.STATUS_CLOSED)
        {
            runActionCode(user, UserTask.CLOSE_ACTIONCODE);
        }else if(status==Instance.STATUS_ABORTED)
        {
            runActionCode(user, UserTask.ABORT_ACTIONCODE);
        }

        removeTemporallyDataobjects();        
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto si nexObjects = true
     * @param user
     */
    public void close(User user, int status, String action, boolean nextObjects)
    {
        //System.out.println("close("+this+","+user+","+status+","+action+","+nextObjects+")");
        //long time=System.currentTimeMillis();
        //System.out.println("Init close");        
        
        super.close(user,status,action);
        
        //Verificar si la instancia tiene eventos intermedios relacionados y cerrarlos
        if (this.getFlowNodeType() instanceof Activity) {
            Iterator<GraphicalElement> childs = this.getFlowNodeType().listChilds();
            while (childs.hasNext()) {
                GraphicalElement child = childs.next();
                if (child instanceof IntermediateCatchEvent) {
                    IntermediateCatchEvent inter = (IntermediateCatchEvent)child;
                    FlowNodeInstance tc = this.getRelatedFlowNodeInstance(inter);
                    tc.close(user, Instance.STATUS_CLOSED, Instance.ACTION_ACCEPT, false);
                }
            }
        }
        
        final User _user=user;
        final int _status=status;
        final boolean _nextObjects=nextObjects;
        final FlowNodeInstance _this=this;
        final Thread _thread=Thread.currentThread();        
        
        boolean linkThread=false;
        
        if(SWBProcessMgr.hasLinkedThread(Thread.currentThread()))
        {
            linkThread=true;
        }else
        {
            if(getFlowNodeType() instanceof UserTask)
            {
                if(((UserTask)getFlowNodeType()).isLinkNextUserTask())
                {
                    linkThread=true;
                }
            }
        }
        
        if(!linkThread)
        {
            synchronized(this)
            {
                Thread thread=new Thread()
                {
                    @Override
                    public void run()
                    {
                        //long time=System.currentTimeMillis();
                        //System.out.println("Init Thread");                    
                        _close(_user, _status, _nextObjects);

                        //System.out.println("End Thread:"+(System.currentTimeMillis()-time));
                        _thread.interrupt();                    
                    }
                };
                thread.start();

                try
                {
                    wait(500);
                }catch(InterruptedException e){}                
            }
        }else
        {
            if(SWBProcessMgr.hasLinkedThread(Thread.currentThread()))
            {
                _close(_user, _status, _nextObjects);
            }else
            {
                SWBProcessMgr.addLinkedThread(Thread.currentThread());
                _close(_user, _status, _nextObjects);
                SWBProcessMgr.removeLinkedThread(Thread.currentThread());
            }
        }
        //System.out.println("end close:"+(System.currentTimeMillis()-time));    
    }
    
    private void runActionCode(User user, int type)
    {
        //Correr accion de cierre
        if(this.getFlowNodeType() instanceof UserTask)
        {
            UserTask ut=(UserTask)this.getFlowNodeType();
            Iterator<TaskAction> it=ut.listTaskActions();
            while (it.hasNext())
            {
                TaskAction taskAction = it.next();
                if(taskAction.getActionType()==type && taskAction.getCode()!=null)
                {
                    try
                    {
                        Interpreter i = SWBPClassMgr.getInterpreter(this, user);
                        Object ret=i.eval(taskAction.getCode());
                    }catch(Exception e)
                    {
                        log.error(e);
                    }
                }
            }
        }        
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
        
        runActionCode(user, UserTask.START_ACTIONCODE);
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
    protected void connectItemsAware(User user)
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
                        //evaluacion

                        SemanticObject in=null;

                        String script=ass.getScript();
                        if(script!=null)
                        {
                            Interpreter inter=SWBPClassMgr.getInterpreter(this, user);
                            try
                            {
                                Object ret=inter.eval(script);
                                if(ret instanceof GenericObject)
                                {
                                    in=((GenericObject)ret).getSemanticObject();
                                }
                            } catch (Exception e)
                            {
                                log.error(e);
                            }
                        }

                        Iterator<ItemAwareReference> it2=listHeraquicalItemAwareReference().iterator();
                        while (it2.hasNext())
                        {
                            ItemAwareReference itemAwareReference = it2.next();
                            if(var.equals(itemAwareReference.getItemAware().getName()) && cls.equals(itemAwareReference.getItemAware().getItemSemanticClass()))
                            {
                                in=itemAwareReference.getProcessObject().getSemanticObject();
                                SWBModel model=this.getProcessSite();
                                if(store instanceof Collectionable)  //No es dataStore (es temporal)
                                {
                                    model=this.getProcessSite().getProcessDataInstanceModel();
                                }
                                SemanticObject out=null;
                                if(in.getModel().equals(model.getSemanticModel()))
                                {
                                    out=in;
                                }else
                                {
                                    //Se crea y copia sus propiedades
                                    long id=model.getSemanticModel().getCounter(cls);
                                    out=model.getSemanticModel().createSemanticObjectById(String.valueOf(id), cls);
                                    Iterator<Statement> it3=in.getRDFResource().listProperties();
                                    while (it3.hasNext())
                                    {
                                        Statement statement = it3.next();
                                        if(!statement.getPredicate().equals(RDF.type))
                                        {
                                            out.getRDFResource().addProperty(statement.getPredicate(), statement.getObject());
                                        }
                                    }
                                    ItemAwareReference ref=ItemAwareReference.ClassMgr.createItemAwareReference(this.getProcessSite());
                                    ref.setItemAware(store);
                                    ref.setProcessObject((SWBClass)out.createGenericInstance());
                                    this.addItemAwareReference(ref);
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
     * Revisa si existen dependencias ciclicas
     * @param orig
     * @param act
     * @param arr
     * @return 
     */
    private boolean checkCicle(FlowNode orig, FlowNode act, ArrayList<FlowNode> arr)
    {
        if(arr==null)
        {
            arr=new ArrayList();
        }        
        else
        {
            if(arr.contains(act))return false;
            arr.add(act);
        }
        Iterator<ConnectionObject> it=act.listInputConnectionObjects();
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
                        if(node.equals(orig))return true;
                        return checkCicle(orig,node,arr);
                    }
                }
            }
        }
        return false;
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
                        if(!checkCicle(type, node, null))
                        {
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
        
        type.initItemAwares(this);
        
        //resetear subsecuentes
        Iterator<ConnectionObject> it=type.listOutputConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it .next();
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
    public WebPage getProcessWebPage()
    {
        return getProcessInstance().getProcessType().getProcessWebPage();
    }
    
    /**
     * Obtiene la URL de la página Web asociada a la Bandeja de tareas del sitio.
     * @return URL de la bandeja de tareas o URL del proceso en su defecto.
     */
    public String getUserTaskInboxUrl() {
        String url = getProcessWebPage().getUrl();
        ResourceType rtype = ResourceType.ClassMgr.getResourceType("ProcessTaskInbox", getProcessSite());
        
        if (rtype != null) {
            //Resource res = rtype.getResource();
            Iterator<Resource> itres = rtype.listResources();
            while (itres.hasNext()) {
                Resource res = itres.next();
                if(res != null && res.isValid() && res.isActive()) {
                    Resourceable resable = res.getResourceable();
                    if(resable != null && resable instanceof WebPage) {
                        url = ((WebPage)resable).getUrl();
                        break;
                    }
                }
            }
        }
        return url;
    }
    
    /**
     * Regresa url de la instancia de la tarea siempre y cuando sea del tipo UserTask, de locontrario regresa null
     * @return 
     */
    public String getUserTaskUrl()
    {
        String ret=null;
        FlowNode node=getFlowNodeType();
        if(node instanceof UserTask)
        {
            UserTask ut=(UserTask)node;
            ret=ut.getTaskWebPage().getUrl()+"?suri="+getEncodedURI();
        }
        return ret;
    }
    
    public boolean haveAccess(User user) 
    {
        boolean canAccess = false;
        
        if (getProcessInstance().getStatus() == ProcessInstance.STATUS_CLOSED) return false;
        
        User owner = this.getAssignedto();
        GraphicalElement type = this.getFlowNodeType();
        //UserTask utask = (UserTask)this.getFlowNodeType(); //Esto causaría un ClassCastException en algunos casos
        
        //Verificar permisos del usuario sobre la instancia
        if (owner != null) { //Tiene propieario
            if (owner.equals(user)) {
                canAccess = true;
            }
        } else 
        {
            //Evaluar Padre
            canAccess=type.haveAccess(user);
        }
        return canAccess;
    }

    @Override
    public void setAssignedto(User user) {
        super.setAssignedto(user);
        runActionCode(user, UserTask.ASSIGN_ACTIONCODE);
    }
}