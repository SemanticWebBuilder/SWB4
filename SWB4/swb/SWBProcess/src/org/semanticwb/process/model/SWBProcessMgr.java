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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;

/**
 *
 * @author javier.solis
 */
public class SWBProcessMgr
{
    private static ConcurrentHashMap<Thread, Thread> linkedThreads=new ConcurrentHashMap();
    

    public static GenericIterator<ProcessInstance> getProcessInstanceWithStatus(ProcessSite site, int status)
    {
        GenericIterator it=new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance>(site.getSemanticModel().listSubjects(ProcessInstance.swp_processStatus, status));
        return it;
    }
    
    public static List<ProcessInstance> getActiveProcessInstance(ProcessSite site, Process process)
    {
        ArrayList ret=new ArrayList();
        
        Iterator<ProcessInstance> it=getProcessInstanceWithStatus(site,ProcessInstance.STATUS_PROCESSING);
        while (it.hasNext())
        {
            ProcessInstance processInstance = (ProcessInstance)it.next();
            if(processInstance.getProcessType().equals(process))
            {
                ret.add(processInstance);
            }
        }
        return ret;
    }

    public static ProcessInstance createProcessInstance(Process process, User user)
    {
        ProcessInstance pinst=process.createInstance();
        UserGroup usrgrp=user.getUserGroup();
        if(usrgrp!=null)pinst.setOwnerUserGroup(usrgrp);
        pinst.start(user);
        return pinst;
    }

    public static List<FlowNodeInstance> getUserTaskInstances(ContainerInstanceable pinst, User user)
    {
        ArrayList ret=new ArrayList();
        Iterator<FlowNodeInstance> it=pinst.listFlowNodeInstances();
        while(it.hasNext())
        {
            FlowNodeInstance actins=it.next();
            FlowNode type=actins.getFlowNodeType();
            if(actins instanceof SubProcessInstance)
            {
                SubProcessInstance processInstance=(SubProcessInstance)actins;
                if(processInstance.getStatus()==Instance.STATUS_PROCESSING || processInstance.getStatus()==Instance.STATUS_OPEN)
                {
                    List aux=getUserTaskInstances((SubProcessInstance)actins, user);
                    ret.addAll(aux);
                }
            }else if(type instanceof Task)
            {
                if(actins.getStatus()==Instance.STATUS_PROCESSING || actins.getStatus()==Instance.STATUS_OPEN)
                {
                    if(user.haveAccess(type))
                    {
                        //System.out.println("User:"+user+" TYPE:"+type);
                        boolean add=true;                    
                        GraphicalElement parent=type.getParent();
                        //System.out.println("parent:"+type);
                        if(parent !=null && parent instanceof Lane)
                        {
                            Lane lane=(Lane)parent;
                            if(!user.haveAccess(lane))
                            {
                                add=false;
                            }
                        }
                        //System.out.println("access:"+add);
                        if(add)ret.add(actins);
                    }                    
                }
            }
        }
        return ret;
    }

    public static Process getProcess(WebPage page)
    {
        Process ret=null;
        if(page!=null)
        {
            if(page instanceof WrapperProcessWebPage)
            {
                ret=((WrapperProcessWebPage)page).getProcess();
            }
            else
            {
                ret=getProcess(page.getParent());
            }
        }
        return ret;
    }
    
    
    
    private static boolean haveAccess(FlowNode node, User user)
    {
        boolean add=false;                    
        if(user.haveAccess(node))
        {
            add=true;                    
            GraphicalElement parent=node.getParent();
            //System.out.println("parent:"+type);
            if(parent !=null && parent instanceof Lane)
            {
                Lane lane=(Lane)parent;
                if(!user.haveAccess(lane))
                {
                    add=false;
                }
            }
        }
        return add;
    }
    
    public static List<User> getUsers(FlowNodeInstance instance)
    {
        ArrayList<User> arr=new ArrayList();
        FlowNode node=instance.getFlowNodeType();
        boolean groupFilter=node.getProcess().isFilterByOwnerUserGroup();
        if(groupFilter)
        {
            UserGroup ug=instance.getProcessInstance().getOwnerUserGroup();
            if(ug!=null)
            {
                Iterator<User> it=ug.listUsers();
                while (it.hasNext())
                {
                    User user = it.next();
                    if(haveAccess(node, user))arr.add(user);
                }
            }
        }else
        {
            Iterator<User> it=instance.getProcessSite().getUserRepository().listUsers();            
            while (it.hasNext())
            {
                User user = it.next();
                if(haveAccess(node, user))arr.add(user);
            }
        }
        return arr;
    }    
    
    static Thread addLinkedThread(Thread thread)
    {
        linkedThreads.put(thread, thread);
        return thread;
    }

    static Thread removeLinkedThread(Thread thread)
    {
        return linkedThreads.remove(thread);
    }
    
    static boolean hasLinkedThread(Thread thread)
    {
        return linkedThreads.contains(thread);
    }

}
