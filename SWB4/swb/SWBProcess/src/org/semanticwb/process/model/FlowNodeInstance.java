package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.model.User;


public class FlowNodeInstance extends org.semanticwb.process.model.base.FlowNodeInstanceBase 
{
    public FlowNodeInstance(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
        //System.out.println("close:"+getId()+" "+getFlowObjectType().getClass().getName()+" "+getFlowObjectType().getTitle()+" "+status+" "+action);
        //Thread.dumpStack();
        //TODO
//        FlowObject type=getFlowObjectType();
//
//        if(type instanceof Task && ((Task)type).isKeepOpen())
//        {
//            setStatus(STATUS_OPEN);
//        }else
//        {
//            setStatus(status);
//        }
//        setAction(action);
//        setEnded(new Date());
//        setEndedby(user);
//        abortDependencies(user);
//        nextObject(user);
    }

    /**
     * Regresa todos los objetos del proceso y procesos padres
     * @return
     */
    public List<ProcessObject> getAllProcessObjects()
    {
        ArrayList ret=new ArrayList();
        if(this instanceof SubProcessInstance)
        {
            Iterator<ProcessObject> it=((SubProcessInstance)this).listProcessObjects();
            while (it.hasNext())
            {
                ProcessObject processObject = it.next();
                ret.add(processObject);
            }
        }
        ContainerInstanceable parent=getContainerInstance();
        if(parent!=null)
        {
            if(parent instanceof FlowNodeInstance)
            {
                List p=((FlowNodeInstance)parent).getAllProcessObjects();
                ret.addAll(p);
            }else
            {
                Iterator<ProcessObject> it=((ProcessInstance)parent).listProcessObjects();
                while (it.hasNext())
                {
                    ProcessObject processObject = it.next();
                    ret.add(processObject);
                }
            }
        }
        return ret;
    }

}
