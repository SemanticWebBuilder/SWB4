/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author javier.solis
 */
public class SWBProcessMgr
{

    public static List<ProcessInstance> getActiveProcessInstance(ProcessSite site, Process process)
    {
        ArrayList ret=new ArrayList();
        Iterator<FlowObjectInstance> it=FlowObjectInstance.ClassMgr.listFlowObjectInstanceByFlowObjectType(process, site);
        while (it.hasNext())
        {
            ProcessInstance processInstance = (ProcessInstance)it.next();
            if(processInstance.getStatus()==Activity.STATUS_PROCESSING)
            {
                ret.add(processInstance);
            }
        }
        return ret;
    }

    public static ProcessInstance createProcessInstance(ProcessSite site, Process process, User user)
    {
        ProcessInstance pinst=process.createProcessInstance(site);
        pinst.start(user);
        return pinst;
    }

    public static List<FlowObjectInstance> getUserTaskInstances(ProcessInstance pinst, User user)
    {
        ArrayList ret=new ArrayList();
        Iterator<FlowObjectInstance> it=pinst.listFlowObjectInstances();
        while(it.hasNext())
        {
            FlowObjectInstance actins=it.next();
            FlowObject type=actins.getFlowObjectType();
            if(actins instanceof ProcessInstance)
            {
                ProcessInstance processInstance=(ProcessInstance)actins;
                if(processInstance.getStatus()==Process.STATUS_PROCESSING || processInstance.getStatus()==Process.STATUS_OPEN)
                {
                    List aux=getUserTaskInstances((ProcessInstance)actins, user);
                    ret.addAll(aux);
                }
            }else if(type instanceof Task)
            {
                if(actins.getStatus()==Process.STATUS_PROCESSING || actins.getStatus()==Process.STATUS_OPEN)
                {
                    if(user.haveAccess(type))ret.add(actins);
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
            if(page instanceof ProcessWebPage)
            {
                ret=((ProcessWebPage)page).getProcess();
            }else
            {
                ret=getProcess(page.getParent());
            }
        }
        return ret;
    }


}
