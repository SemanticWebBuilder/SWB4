package org.semanticwb.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.model.User;


public class FlowObjectInstance extends org.semanticwb.process.base.FlowObjectInstanceBase 
{
    public FlowObjectInstance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public ProcessWebPage getProcessWebPage()
    {
        FlowObject fo=getFlowObjectType();
        ProcessWebPage page=null;
        if(fo instanceof Process)
        {
            page=((Process)fo).getProcessWebPage();
        }else
        {
            page=fo.getParentProcess().getProcessWebPage();
        }
        if(page==null)
        {
            ProcessInstance inst=getParentProcessInstance();
            if(inst!=null)
            {
                page=inst.getProcessWebPage();
            }
        }
        return page;
    }

    /**
     * Crea una instancia del objeto de flujo
     * @param fobj
     * @param pinst
     * @return
     */
    public static FlowObjectInstance createFlowObjectInstance(FlowObject fobj, ProcessInstance pinst)
    {
        FlowObjectInstance eventins=null;
        if(fobj instanceof Process)
        {
            eventins=((Process)fobj).createProcessInstance(pinst.getProcessSite());
        }else
        {
            eventins=pinst.getProcessSite().createFlowObjectInstance();
            eventins.setFlowObjectType(fobj);
            eventins.setStatus(Activity.STATUS_INIT);
        }
        pinst.addFlowObjectInstance(eventins);
        return eventins;
    }

    /**
     * Obtiene una instancia del objeto de flujo
     * @param fobj
     * @param processInstance
     * @return FlowObjectInstance, si no esta creada regresa null
     */
    public static FlowObjectInstance getFlowObjectInstance(FlowObject fobj, ProcessInstance processInstance)
    {
        Iterator<FlowObjectInstance> it=processInstance.listFlowObjectInstances();
        while (it.hasNext())
        {
            FlowObjectInstance flowObjectInstance = it.next();
            if(flowObjectInstance.getFlowObjectType().equals(fobj))
            {
                return flowObjectInstance;
            }
        }
        return null;
    }

    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo
     * @param user
     */
    public void start(User user)
    {
        //System.out.println("start:"+getId()+" "+getFlowObjectType().getClass().getName()+" "+getFlowObjectType().getTitle());
        setStatus(Activity.STATUS_PROCESSING);
        FlowObject type=getFlowObjectType();
        if(type instanceof Process)
        {
            Process process=(Process)type;
            Iterator<FlowObject> actit=process.listFlowObjects();
            while (actit.hasNext())
            {
                FlowObject flowobject = actit.next();
                if(flowobject instanceof InitEvent)
                {
                    InitEvent init=(InitEvent)flowobject;
                    FlowObjectInstance eventins=createFlowObjectInstance(init,(ProcessInstance)this);
                    eventins.start(user);
                }
            }
        }
        else if(type instanceof Event)
        {
            execute(user);
        }
    }

    /**
     * Se ejecuta cada que obtiene el foco del flujo
     * @param user
     */
    public void execute(User user)
    {
        //System.out.println("execute:"+getId()+" "+getFlowObjectType().getClass().getName()+" "+getFlowObjectType().getTitle());
        FlowObject type=getFlowObjectType();
        if(type instanceof InitEvent)
        {
            //TODO: revisar tipo de evento inicial
            close(user);
        }else if(type instanceof EndEvent)
        {
            //TODO: revisar tipo de evento final
            close(user);
            getParentProcessInstance().close(user);
        }else if(type instanceof ANDGateWay)
        {
            boolean ret=true;
            ANDGateWay aux=(ANDGateWay)type;
            Iterator<ConnectionObject> it=type.listFromConnectionObjects();
            while (it.hasNext())
            {
                ConnectionObject connectionObject = it.next();
                FlowObject obj=connectionObject.getFromFlowObject();
                FlowObjectInstance inst=getFlowObjectInstance(obj, getParentProcessInstance());
                if(inst==null)
                {
                    ret=false;
                }else if(inst.getStatus()<Process.STATUS_CLOSED)
                {
                    ret=false;
                    break;
                }
            }
            if(ret)
            {
                close(user);
            }
        }else if(type instanceof ORGateWay)
        {
            boolean ret=false;
            ORGateWay aux=(ORGateWay)type;
            Iterator<ConnectionObject> it=type.listFromConnectionObjects();
            while (it.hasNext())
            {
                ConnectionObject connectionObject = it.next();
                FlowObject obj=connectionObject.getFromFlowObject();
                FlowObjectInstance inst=getFlowObjectInstance(obj, getParentProcessInstance());
                if(inst!=null && inst.getStatus()>=Process.STATUS_CLOSED)
                {
                    ret=true;
                    break;
                }
            }
            if(ret)
            {
                close(user);
            }
        }
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    public void close(User user)
    {
        close(user, Process.STATUS_CLOSED, Process.ACTION_ACCEPT);
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    public void close(User user, String action)
    {
        close(user, Process.STATUS_CLOSED, action);
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    public void abort(User user)
    {
        close(user, Process.STATUS_ABORTED, Process.ACTION_CANCEL);
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    public void close(User user, int status, String action)
    {
        //System.out.println("close:"+getId()+" "+getFlowObjectType().getClass().getName()+" "+getFlowObjectType().getTitle()+" "+status+" "+action);
        //Thread.dumpStack();
        FlowObject type=getFlowObjectType();

        if(type instanceof Task && ((Task)type).isKeepOpen())
        {
            setStatus(Activity.STATUS_OPEN);
        }else
        {
            setStatus(status);
        }
        setAction(action);
        setEnded(new Date());
        setEndedby(user);
        abortDependencies(user);
        nextObject(user);
    }


    /**
     * Continua el flujo al siguiente FlowObject
     * @param user
     */
    private void nextObject(User user)
    {
        //System.out.println("nextObject:"+getId()+" "+getFlowObjectType().getClass().getName()+" "+getFlowObjectType().getTitle());
        FlowObject type=getFlowObjectType();
        Iterator<ConnectionObject> it=type.listToConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            if(connectionObject instanceof SequenceFlow)
            {
                boolean cond=true;
                if(connectionObject instanceof ConditionalFlow)
                {
                    ConditionalFlow condFlow=(ConditionalFlow)connectionObject;
                    cond=condFlow.evaluate(this);
                }
                if(cond)
                {
                    FlowObject toobj=connectionObject.getToFlowObject();
                    FlowObjectInstance inst=getFlowObjectInstance(toobj, getParentProcessInstance());
                    if(inst==null)
                    {
                        inst=createFlowObjectInstance(toobj, getParentProcessInstance());
                    }else
                    {
                        //recrear instancia en ciclos
                        int status=inst.getStatus();
                        if(status==Process.STATUS_ABORTED || status==Process.STATUS_CLOSED)
                        {
//                            inst=createFlowObjectInstance(toobj, getParentProcessInstance());
                            inst.reset();
                        }
                    }
                    if(inst.getStatus()==Activity.STATUS_INIT)inst.start(user);
                    inst.execute(user);
                }
            }
        }
    }

    /**
     * Regresa todos los objetos del proceso y procesos padres
     * @return
     */
    public List<ProcessObject> getAllProcessObjects()
    {
        ArrayList ret=new ArrayList();
        if(this instanceof ProcessInstance)
        {
            Iterator<ProcessObject> it=((ProcessInstance)this).listProcessObjects();
            while (it.hasNext())
            {
                ProcessObject processObject = it.next();
                ret.add(processObject);
            }
        }
        FlowObjectInstance parent=getParentProcessInstance();
        if(parent!=null)
        {
            List p=parent.getAllProcessObjects();
            ret.addAll(p);
        }
        return ret;
    }


    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    private void abortDependencies(User user)
    {
        //System.out.println("abortDependencies:"+getId()+" "+getFlowObjectType().getClass().getName()+" "+getFlowObjectType().getTitle());
        FlowObject type=getFlowObjectType();

        //Cerrar dependencias
        Iterator<ConnectionObject> it=type.listFromConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            if(connectionObject instanceof SequenceFlow)
            {
                if(connectionObject.getClass().equals(SequenceFlow.class))
                {
                    //System.out.println(connectionObject);
                    FlowObject obj=connectionObject.getFromFlowObject();
                    FlowObjectInstance inst=getFlowObjectInstance(obj, getParentProcessInstance());
                    if(inst==null)
                    {
                        inst=createFlowObjectInstance(obj, getParentProcessInstance());
                    }
                    if(inst.getStatus()<Process.STATUS_ABORTED)
                    {
                        inst.setStatus(Activity.STATUS_ABORTED);
                        inst.setAction(Activity.ACTION_CANCEL);
                        inst.setEnded(new Date());
                        inst.setEndedby(user);
                        inst.abortDependencies(user);
                    }
                }
            }
        }
    }

    /**
     * Reinicia la instancia del objeto de flujo y sus sucesores
     */
    private void reset()
    {
        //System.out.println("reset:"+getId()+" "+getFlowObjectType().getClass().getName()+" "+getFlowObjectType().getTitle());
        //Thread.dumpStack();
        setStatus(Process.STATUS_INIT);
        setAction(null);
        setEnded(null);
        removeEndedby();

        FlowObject type=getFlowObjectType();
        //resetear subsecuentes
        Iterator<ConnectionObject> it=type.listToConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            if(connectionObject instanceof SequenceFlow)
            {
                if(connectionObject.getClass().equals(SequenceFlow.class))
                {
                    FlowObject obj=connectionObject.getToFlowObject();
                    FlowObjectInstance inst=getFlowObjectInstance(obj, getParentProcessInstance());
                    if(inst!=null && inst.getStatus()>Process.STATUS_PROCESSING)
                    {
                        inst.reset();
                    }
                }
            }
        }

    }
}
