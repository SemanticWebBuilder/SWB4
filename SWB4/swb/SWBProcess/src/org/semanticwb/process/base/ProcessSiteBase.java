package org.semanticwb.process.base;


public class ProcessSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Localeable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Indexable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Trashable
{
    public static final org.semanticwb.platform.SemanticClass swbps_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
    public static final org.semanticwb.platform.SemanticClass swbps_ActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActivityInstance");
    public static final org.semanticwb.platform.SemanticClass swbps_TaskInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TaskInstance");
    public static final org.semanticwb.platform.SemanticClass swbps_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticClass swbps_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Activity");
    public static final org.semanticwb.platform.SemanticClass swbps_Task=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Task");
    public static final org.semanticwb.platform.SemanticClass swbps_ProcessSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessSite");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessSite");

    public ProcessSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.process.ProcessSite> listProcessSites(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessSite>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.process.ProcessSite> listProcessSites()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessSite>(it, true);
    }

    public static org.semanticwb.process.ProcessSite getProcessSite(String id)
    {
       org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
       org.semanticwb.process.ProcessSite ret=null;
       org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
       if(model!=null)
       {
           org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
           if(obj!=null)
           {
               ret=(org.semanticwb.process.ProcessSite)obj.createGenericInstance();
           }
       }
       return ret;
    }

    public static org.semanticwb.process.ProcessSite createProcessSite(String id, String namespace)
    {
        org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
        org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
        return (org.semanticwb.process.ProcessSite)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
    }

    public static void removeProcessSite(String id)
    {
       org.semanticwb.process.ProcessSite obj=getProcessSite(id);
       if(obj!=null)
       {
           obj.remove();
       }
    }

    public static boolean hasProcessSite(String id)
    {
        return (getProcessSite(id)!=null);
    }

    public org.semanticwb.process.ProcessInstance getProcessInstance(String id)
    {
        return org.semanticwb.process.ProcessInstance.getProcessInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstances()
    {
        return org.semanticwb.process.ProcessInstance.listProcessInstances(this);
    }

    public org.semanticwb.process.ProcessInstance createProcessInstance(String id)
    {
        return org.semanticwb.process.ProcessInstance.createProcessInstance(id,this);
    }

    public org.semanticwb.process.ProcessInstance createProcessInstance()
    {
        long id=getSemanticObject().getModel().getCounter(swbps_ProcessInstance);
        return org.semanticwb.process.ProcessInstance.createProcessInstance(String.valueOf(id),this);
    } 

    public void removeProcessInstance(String id)
    {
        org.semanticwb.process.ProcessInstance.removeProcessInstance(id, this);
    }
    public boolean hasProcessInstance(String id)
    {
        return org.semanticwb.process.ProcessInstance.hasProcessInstance(id, this);
    }

    public org.semanticwb.process.ActivityInstance getActivityInstance(String id)
    {
        return org.semanticwb.process.ActivityInstance.getActivityInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstances()
    {
        return org.semanticwb.process.ActivityInstance.listActivityInstances(this);
    }

    public org.semanticwb.process.ActivityInstance createActivityInstance(String id)
    {
        return org.semanticwb.process.ActivityInstance.createActivityInstance(id,this);
    }

    public org.semanticwb.process.ActivityInstance createActivityInstance()
    {
        long id=getSemanticObject().getModel().getCounter(swbps_ActivityInstance);
        return org.semanticwb.process.ActivityInstance.createActivityInstance(String.valueOf(id),this);
    } 

    public void removeActivityInstance(String id)
    {
        org.semanticwb.process.ActivityInstance.removeActivityInstance(id, this);
    }
    public boolean hasActivityInstance(String id)
    {
        return org.semanticwb.process.ActivityInstance.hasActivityInstance(id, this);
    }

    public org.semanticwb.process.TaskInstance getTaskInstance(String id)
    {
        return org.semanticwb.process.TaskInstance.getTaskInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstances()
    {
        return org.semanticwb.process.TaskInstance.listTaskInstances(this);
    }

    public org.semanticwb.process.TaskInstance createTaskInstance(String id)
    {
        return org.semanticwb.process.TaskInstance.createTaskInstance(id,this);
    }

    public org.semanticwb.process.TaskInstance createTaskInstance()
    {
        long id=getSemanticObject().getModel().getCounter(swbps_TaskInstance);
        return org.semanticwb.process.TaskInstance.createTaskInstance(String.valueOf(id),this);
    } 

    public void removeTaskInstance(String id)
    {
        org.semanticwb.process.TaskInstance.removeTaskInstance(id, this);
    }
    public boolean hasTaskInstance(String id)
    {
        return org.semanticwb.process.TaskInstance.hasTaskInstance(id, this);
    }

    public org.semanticwb.process.Process getProcess(String id)
    {
        return org.semanticwb.process.Process.getProcess(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.Process> listProcesses()
    {
        return org.semanticwb.process.Process.listProcesses(this);
    }

    public org.semanticwb.process.Process createProcess(String id)
    {
        return org.semanticwb.process.Process.createProcess(id,this);
    }

    public org.semanticwb.process.Process createProcess()
    {
        long id=getSemanticObject().getModel().getCounter(swbps_Process);
        return org.semanticwb.process.Process.createProcess(String.valueOf(id),this);
    } 

    public void removeProcess(String id)
    {
        org.semanticwb.process.Process.removeProcess(id, this);
    }
    public boolean hasProcess(String id)
    {
        return org.semanticwb.process.Process.hasProcess(id, this);
    }

    public org.semanticwb.process.Activity getActivity(String id)
    {
        return org.semanticwb.process.Activity.getActivity(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.Activity> listActivities()
    {
        return org.semanticwb.process.Activity.listActivities(this);
    }

    public org.semanticwb.process.Activity createActivity(String id)
    {
        return org.semanticwb.process.Activity.createActivity(id,this);
    }

    public org.semanticwb.process.Activity createActivity()
    {
        long id=getSemanticObject().getModel().getCounter(swbps_Activity);
        return org.semanticwb.process.Activity.createActivity(String.valueOf(id),this);
    } 

    public void removeActivity(String id)
    {
        org.semanticwb.process.Activity.removeActivity(id, this);
    }
    public boolean hasActivity(String id)
    {
        return org.semanticwb.process.Activity.hasActivity(id, this);
    }

    public org.semanticwb.process.Task getTask(String id)
    {
        return org.semanticwb.process.Task.getTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.Task> listTasks()
    {
        return org.semanticwb.process.Task.listTasks(this);
    }

    public org.semanticwb.process.Task createTask(String id)
    {
        return org.semanticwb.process.Task.createTask(id,this);
    }

    public org.semanticwb.process.Task createTask()
    {
        long id=getSemanticObject().getModel().getCounter(swbps_Task);
        return org.semanticwb.process.Task.createTask(String.valueOf(id),this);
    } 

    public void removeTask(String id)
    {
        org.semanticwb.process.Task.removeTask(id, this);
    }
    public boolean hasTask(String id)
    {
        return org.semanticwb.process.Task.hasTask(id, this);
    }
}
