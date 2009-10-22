package org.semanticwb.process.base;


public class ProcessSiteBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Indexable,org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable,org.semanticwb.model.Traceable,org.semanticwb.model.Localeable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swb_indexable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#indexable");
       public static final org.semanticwb.platform.SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");
       public static final org.semanticwb.platform.SemanticProperty swb_userRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepository");
       public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
       public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
       public static final org.semanticwb.platform.SemanticClass swb_Language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Language");
       public static final org.semanticwb.platform.SemanticProperty swb_language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#language");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_homePage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#homePage");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
       public static final org.semanticwb.platform.SemanticProperty swb_hasSubModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasSubModel");
       public static final org.semanticwb.platform.SemanticProperty swb_undeleteable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#undeleteable");
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
       public static final org.semanticwb.platform.SemanticClass swbps_ActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActivityInstance");
       public static final org.semanticwb.platform.SemanticClass swbps_TaskInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TaskInstance");
       public static final org.semanticwb.platform.SemanticClass swbps_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
       public static final org.semanticwb.platform.SemanticClass swbps_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Activity");
       public static final org.semanticwb.platform.SemanticClass swbps_Task=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Task");
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessSite");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessSite");

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
    }

    public ProcessSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessInstance getProcessInstance(String id)
    {
        return org.semanticwb.process.ProcessInstance.ClassMgr.getProcessInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.ProcessInstance> listProcessInstances()
    {
        return org.semanticwb.process.ProcessInstance.ClassMgr.listProcessInstances(this);
    }

    public org.semanticwb.process.ProcessInstance createProcessInstance(String id)
    {
        return org.semanticwb.process.ProcessInstance.ClassMgr.createProcessInstance(id,this);
    }

    public org.semanticwb.process.ProcessInstance createProcessInstance()
    {
        long id=getSemanticObject().getModel().getCounter(ClassMgr.swbps_ProcessInstance);
        return org.semanticwb.process.ProcessInstance.ClassMgr.createProcessInstance(String.valueOf(id),this);
    } 

    public void removeProcessInstance(String id)
    {
        org.semanticwb.process.ProcessInstance.ClassMgr.removeProcessInstance(id, this);
    }
    public boolean hasProcessInstance(String id)
    {
        return org.semanticwb.process.ProcessInstance.ClassMgr.hasProcessInstance(id, this);
    }

    public org.semanticwb.process.ActivityInstance getActivityInstance(String id)
    {
        return org.semanticwb.process.ActivityInstance.ClassMgr.getActivityInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstances()
    {
        return org.semanticwb.process.ActivityInstance.ClassMgr.listActivityInstances(this);
    }

    public org.semanticwb.process.ActivityInstance createActivityInstance(String id)
    {
        return org.semanticwb.process.ActivityInstance.ClassMgr.createActivityInstance(id,this);
    }

    public org.semanticwb.process.ActivityInstance createActivityInstance()
    {
        long id=getSemanticObject().getModel().getCounter(ClassMgr.swbps_ActivityInstance);
        return org.semanticwb.process.ActivityInstance.ClassMgr.createActivityInstance(String.valueOf(id),this);
    } 

    public void removeActivityInstance(String id)
    {
        org.semanticwb.process.ActivityInstance.ClassMgr.removeActivityInstance(id, this);
    }
    public boolean hasActivityInstance(String id)
    {
        return org.semanticwb.process.ActivityInstance.ClassMgr.hasActivityInstance(id, this);
    }

    public org.semanticwb.process.TaskInstance getTaskInstance(String id)
    {
        return org.semanticwb.process.TaskInstance.ClassMgr.getTaskInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.TaskInstance> listTaskInstances()
    {
        return org.semanticwb.process.TaskInstance.ClassMgr.listTaskInstances(this);
    }

    public org.semanticwb.process.TaskInstance createTaskInstance(String id)
    {
        return org.semanticwb.process.TaskInstance.ClassMgr.createTaskInstance(id,this);
    }

    public org.semanticwb.process.TaskInstance createTaskInstance()
    {
        long id=getSemanticObject().getModel().getCounter(ClassMgr.swbps_TaskInstance);
        return org.semanticwb.process.TaskInstance.ClassMgr.createTaskInstance(String.valueOf(id),this);
    } 

    public void removeTaskInstance(String id)
    {
        org.semanticwb.process.TaskInstance.ClassMgr.removeTaskInstance(id, this);
    }
    public boolean hasTaskInstance(String id)
    {
        return org.semanticwb.process.TaskInstance.ClassMgr.hasTaskInstance(id, this);
    }

    public org.semanticwb.process.Process getProcess(String id)
    {
        return org.semanticwb.process.Process.ClassMgr.getProcess(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.Process> listProcesses()
    {
        return org.semanticwb.process.Process.ClassMgr.listProcesses(this);
    }

    public org.semanticwb.process.Process createProcess(String id)
    {
        return org.semanticwb.process.Process.ClassMgr.createProcess(id,this);
    }

    public org.semanticwb.process.Process createProcess()
    {
        long id=getSemanticObject().getModel().getCounter(ClassMgr.swbps_Process);
        return org.semanticwb.process.Process.ClassMgr.createProcess(String.valueOf(id),this);
    } 

    public void removeProcess(String id)
    {
        org.semanticwb.process.Process.ClassMgr.removeProcess(id, this);
    }
    public boolean hasProcess(String id)
    {
        return org.semanticwb.process.Process.ClassMgr.hasProcess(id, this);
    }

    public org.semanticwb.process.Activity getActivity(String id)
    {
        return org.semanticwb.process.Activity.ClassMgr.getActivity(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.Activity> listActivities()
    {
        return org.semanticwb.process.Activity.ClassMgr.listActivities(this);
    }

    public org.semanticwb.process.Activity createActivity(String id)
    {
        return org.semanticwb.process.Activity.ClassMgr.createActivity(id,this);
    }

    public org.semanticwb.process.Activity createActivity()
    {
        long id=getSemanticObject().getModel().getCounter(ClassMgr.swbps_Activity);
        return org.semanticwb.process.Activity.ClassMgr.createActivity(String.valueOf(id),this);
    } 

    public void removeActivity(String id)
    {
        org.semanticwb.process.Activity.ClassMgr.removeActivity(id, this);
    }
    public boolean hasActivity(String id)
    {
        return org.semanticwb.process.Activity.ClassMgr.hasActivity(id, this);
    }

    public org.semanticwb.process.Task getTask(String id)
    {
        return org.semanticwb.process.Task.ClassMgr.getTask(id, this);
    }

    public java.util.Iterator<org.semanticwb.process.Task> listTasks()
    {
        return org.semanticwb.process.Task.ClassMgr.listTasks(this);
    }

    public org.semanticwb.process.Task createTask(String id)
    {
        return org.semanticwb.process.Task.ClassMgr.createTask(id,this);
    }

    public org.semanticwb.process.Task createTask()
    {
        long id=getSemanticObject().getModel().getCounter(ClassMgr.swbps_Task);
        return org.semanticwb.process.Task.ClassMgr.createTask(String.valueOf(id),this);
    } 

    public void removeTask(String id)
    {
        org.semanticwb.process.Task.ClassMgr.removeTask(id, this);
    }
    public boolean hasTask(String id)
    {
        return org.semanticwb.process.Task.ClassMgr.hasTask(id, this);
    }
}
