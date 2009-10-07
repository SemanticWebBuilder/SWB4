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
            if(processInstance.getStatus()==Process.STATUS_PROCESSING)
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
        pinst.setStatus(Process.STATUS_PROCESSING);

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
            ActivityInstance actins=site.createActivityInstance();
            pinst.addActivityInstance(actins);
            actins.setActivityType(activity);
            actins.setStatus(Process.STATUS_INIT);

            Iterator<Task> tskit=activity.listTasks();
            while (tskit.hasNext())
            {
                Task task = tskit.next();
                TaskInstance taskins=site.createTaskInstance();
                actins.addTaskinstance(taskins);
                taskins.setTaskType(task);
                taskins.setStatus(Process.STATUS_INIT);
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
            ActivityInstance act=it.next();
            if(act.getStatus()==Process.STATUS_INIT && canStartActivityInstance(act))
            {
                act.setStatus(Process.STATUS_PROCESSING);
            }
            if(act.getStatus()==Process.STATUS_PROCESSING)
            {
                Iterator<TaskInstance> itt=act.listTaskinstances();
                while (itt.hasNext())
                {
                    TaskInstance taskInstance = itt.next();
                    if(taskInstance.getStatus()==Process.STATUS_INIT && canStartTaskInstance(taskInstance))
                    {
                        taskInstance.setStatus(Process.STATUS_PROCESSING);
                    }
                    if(taskInstance.getStatus()==Process.STATUS_PROCESSING)
                    {
                        if(user.haveAccess(taskInstance.getTaskType()))ret.add(taskInstance);
                    }
                }
            }
        }
        return ret;
    }
    
    public static boolean canStartTaskInstance(TaskInstance taskInstance)
    {
        boolean ret=true;
        Task task=taskInstance.getTaskType();
        Iterator<Task> it = task.listDependences();
        while (it.hasNext()) {
            Task task1 = it.next();
            Iterator<TaskInstance> tiit=taskInstance.getActivityInstance().listTaskinstances();
            while (tiit.hasNext()) {
                TaskInstance taskInstance1 = tiit.next();
                if(taskInstance1.getTaskType().equals(task1))
                {
                    if(taskInstance1.getStatus()<Process.STATUS_CLOSED)ret=false;
                }
            }
        }
        return ret;
    }

    public static boolean canStartActivityInstance(ActivityInstance instance)
    {
        boolean ret=true;
        Activity act=instance.getActivityType();
        Iterator<Activity> it = act.listDependences();
        while (it.hasNext()) {
            Activity act1 = it.next();
            Iterator<ActivityInstance> tiit=instance.getProcessInstance().listActivityInstances();
            while (tiit.hasNext()) {
                ActivityInstance instance1 = tiit.next();
                if(instance1.getActivityType().equals(act1))
                {
                    if(instance1.getStatus()<Process.STATUS_CLOSED)ret=false;
                }
            }
        }
        return ret;
    }



}
