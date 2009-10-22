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
        Iterator<ProcessInstance> it=ProcessInstance.listProcessInstanceByProcessType(process, site);
        while (it.hasNext()) {
            ProcessInstance processInstance = it.next();
            if(processInstance.getStatus()==Activity.STATUS_PROCESSING)
            {
                ret.add(processInstance);
            }
        }
        return ret;
    }

    public static ProcessInstance createProcessInstance(ProcessSite site, Process process)
    {
        ProcessInstance pinst=site.createProcessInstance();
        pinst.setProcessType(process);
        pinst.setStatus(Process.STATUS_INIT);

        Iterator<SemanticObject> it=process.listProcessClasses();
        while(it.hasNext())
        {
            SemanticObject sobj=it.next();
            SemanticModel model=site.getSemanticObject().getModel();
            SemanticClass cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(sobj.getURI());
            long id=model.getCounter(cls);
            SemanticObject ins=model.createSemanticObjectById(String.valueOf(id), cls);
            pinst.addProcessObject((ProcessObject)ins.createGenericInstance());
        }

        Iterator<Activity> actit=process.listActivities();
        while (actit.hasNext())
        {
            Activity activity = actit.next();
            if(activity instanceof Process)
            {
                ProcessInstance ins=createProcessInstance(site,(Process)activity);
                pinst.addActivityInstance(ins);
            }else if(activity instanceof Task)
            {
                Task task = (Task)activity;
                TaskInstance taskins=site.createTaskInstance();
                pinst.addActivityInstance(taskins);
                taskins.setTaskType(task);
                taskins.setStatus(Activity.STATUS_INIT);
            }
        }
        return pinst;
    }

    public static List<TaskInstance> getUserTaskInstances(ProcessInstance pinst, User user)
    {
        ArrayList ret=new ArrayList();
        Iterator<ActivityInstance> it=pinst.listActivityInstances();
        while(it.hasNext())
        {
            ActivityInstance actins=it.next();
            if(actins instanceof ProcessInstance)
            {
                List aux=getUserTaskInstances((ProcessInstance)actins, user);
                ret.addAll(aux);
            }else if(actins instanceof TaskInstance)
            {
                TaskInstance taskInstance = (TaskInstance)actins;
                if(taskInstance.getStatus()==Process.STATUS_INIT && canStartActivityInstance(taskInstance))
                {
                    taskInstance.setStatus(Process.STATUS_PROCESSING);
                }
                if(taskInstance.getStatus()==Process.STATUS_PROCESSING)
                {
                    if(user.haveAccess(taskInstance.getTaskType()))ret.add(taskInstance);
                }
            }
        }
        return ret;
    }
    
    public static boolean canStartActivityInstance(ActivityInstance instance)
    {
        boolean ret=true;
        Activity activity=instance.getActivityType();
        Iterator<Activity> it = activity.listDependences();
        while (it.hasNext())
        {
            Activity act1 = it.next();
            ProcessInstance proins=instance.getProcessInstance();
            if(proins!=null)
            {
                Iterator<ActivityInstance> tiit=proins.listActivityInstances();
                while (tiit.hasNext())
                {
                    ActivityInstance instance1 = tiit.next();
                    if(instance1.getActivityType().equals(act1))
                    {
                        if(instance1.getStatus()<Process.STATUS_CLOSED)ret=false;
                    }
                }
            }
        }
        return ret;
    }



}
