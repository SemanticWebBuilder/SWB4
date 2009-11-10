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
        close(user, Process.STATUS_CLOSED);
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    public void abort(User user)
    {
        close(user, Process.STATUS_ABORTED);
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    public void close(User user, int status)
    {
        FlowObject type=getFlowObjectType();
        if(type instanceof Task && ((Task)type).isKeepOpen())
        {
            setStatus(Activity.STATUS_OPEN);
        }else
        {
            setStatus(status);
        }
        setEnded(new Date());
        setEndedby(user);
        nextObject(user);

        //Cerrar dependencias
        Iterator<ConnectionObject> it=type.listFromConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            FlowObject obj=connectionObject.getFromFlowObject();
            FlowObjectInstance inst=getFlowObjectInstance(obj, getParentProcessInstance());
            if(inst==null)
            {
                inst=createFlowObjectInstance(obj, getParentProcessInstance());
            }
            if(inst.getStatus()<Process.STATUS_ABORTED)
            {
                inst.abort(user);
            }
        }
    }


    /**
     * Continua el flujo al siguiente FlowObject
     * @param user
     */
    private void nextObject(User user)
    {
        FlowObject type=getFlowObjectType();
        Iterator<ConnectionObject> it=type.listToConnectionObjects();
        while (it.hasNext())
        {
            ConnectionObject connectionObject = it.next();
            if(connectionObject instanceof SequenceFlow)
            {
                //TODO: Validar condicion
                FlowObject toobj=connectionObject.getToFlowObject();
                FlowObjectInstance inst=getFlowObjectInstance(toobj, getParentProcessInstance());
                if(inst==null)
                {
                    inst=createFlowObjectInstance(toobj, getParentProcessInstance());
                    inst.start(user);
                }
                inst.execute(user);
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
}
