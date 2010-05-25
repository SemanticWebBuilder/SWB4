package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.model.User;


public class Instance extends org.semanticwb.process.model.base.InstanceBase 
{
    public final static int STATUS_INIT=0;
    public final static int STATUS_PROCESSING=1;
    public final static int STATUS_STOPED=2;
    public final static int STATUS_ABORTED=3;
    public final static int STATUS_CLOSED=4;
    public final static int STATUS_OPEN=5;

    public final static String ACTION_ACCEPT="accept";
    public final static String ACTION_REJECT="reject";
    public final static String ACTION_CANCEL="cancel";
    public final static String ACTION_EVENT="event";

    public Instance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public ProcessElement getProcessElementType()
    {
        ProcessElement ret=null;
        if(this instanceof FlowNodeInstance)ret=((FlowNodeInstance)this).getFlowNodeType();
        else ret=((ProcessInstance)this).getProcessType();
        return ret;
    }
    
    /**
     * Se ejecuta cada que se crea la intancia del objeto de flujo
     * @param user
     */
    public void start(User user)
    {
        System.out.println("start:"+getId()+" "+getProcessElementType().getClass().getName()+" "+getProcessElementType().getTitle());
        setStatus(STATUS_PROCESSING);
    }


    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    public void close(User user)
    {
        close(user, STATUS_CLOSED, ACTION_ACCEPT);
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    public void close(User user, String action)
    {
        close(user, STATUS_CLOSED, action);
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    public void abort(User user)
    {
        close(user, STATUS_ABORTED, ACTION_CANCEL);
    }

    /**
     * Cierra la instancia de objeto y continua el flujo al siguiente objeto
     * @param user
     */
    public void close(User user, int status, String action)
    {
        System.out.println("close:"+getId()+" "+getProcessElementType().getClass().getName()+" "+getProcessElementType().getTitle()+" "+status+" "+action);
        //Thread.dumpStack();
        setStatus(status);
        setAction(action);
        setEnded(new Date());
        setEndedby(user);
    }


    /**
     * Se ejecuta cada que obtiene el foco del flujo
     * @param user
     */
    public void execute(User user)
    {
        System.out.println("execute:"+getId()+" "+getProcessElementType().getClass().getName()+" "+getProcessElementType().getTitle());
        setExecution(getExecution()+1);
    }


    /**
     * Regresa todos los objetos del proceso y procesos padres
     * @return
     */
    public List<ProcessObject> getAllProcessObjects()
    {
        ArrayList<ProcessObject> ret=new ArrayList();
        if(!(this instanceof FlowNodeInstance))
        {
            Iterator<ProcessObject> it=((ContainerInstanceable)this).listProcessObjects();
            while (it.hasNext())
            {
                ProcessObject processObject = it.next();
                ret.add(processObject);
            }
        }

        if(this instanceof FlowNodeInstance)
        {
            FlowNodeInstance flow=(FlowNodeInstance)this;
            Instance parent=(Instance)flow.getContainerInstance();
            if(parent!=null)
            {
                ret.addAll(parent.getAllProcessObjects());
            }
        }
        return ret;
    }

}
