package org.semanticwb.process.base;


public class UserTaskBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.PFlowRefable,org.semanticwb.process.FlowObject,org.semanticwb.model.Resourceable,org.semanticwb.model.Viewable,org.semanticwb.process.Task,org.semanticwb.model.Activeable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Expirable,org.semanticwb.model.Referensable,org.semanticwb.model.Indexable,org.semanticwb.model.Rankable,org.semanticwb.model.Traceable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Hiddenable,org.semanticwb.model.UserGroupRefable,org.semanticwb.process.Activity,org.semanticwb.model.Filterable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable,org.semanticwb.model.RoleRefable
{
       public static final org.semanticwb.platform.SemanticClass swbxf_FormView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormView");
       public static final org.semanticwb.platform.SemanticProperty swbps_processFormView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processFormView");
       public static final org.semanticwb.platform.SemanticClass swbps_UserTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#UserTask");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#UserTask");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTasks(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTasks()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask>(it, true);
       }

       public static org.semanticwb.process.UserTask getUserTask(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.UserTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.UserTask createUserTask(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.UserTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeUserTask(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasUserTask(String id, org.semanticwb.model.SWBModel model)
       {
           return (getUserTask(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTaskByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasFromConnectionObjectInv, hasfromconnectionobjectinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTaskByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask> it=new org.semanticwb.model.GenericIterator(hasfromconnectionobjectinv.getSemanticObject().getModel().listSubjects(swbps_hasFromConnectionObjectInv,hasfromconnectionobjectinv.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTaskByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasToConnectionObject, hastoconnectionobject.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTaskByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask> it=new org.semanticwb.model.GenericIterator(hastoconnectionobject.getSemanticObject().getModel().listSubjects(swbps_hasToConnectionObject,hastoconnectionobject.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTaskByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasFlowObjectInstansInv, hasflowobjectinstansinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTaskByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask> it=new org.semanticwb.model.GenericIterator(hasflowobjectinstansinv.getSemanticObject().getModel().listSubjects(swbps_hasFlowObjectInstansInv,hasflowobjectinstansinv.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTaskByParentProcess(org.semanticwb.process.Process parentprocessinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_parentProcessInv, parentprocessinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTaskByParentProcess(org.semanticwb.process.Process parentprocessinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask> it=new org.semanticwb.model.GenericIterator(parentprocessinv.getSemanticObject().getModel().listSubjects(swbps_parentProcessInv,parentprocessinv.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTaskByFormView(org.semanticwb.model.FormView processformview,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_processFormView, processformview.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTaskByFormView(org.semanticwb.model.FormView processformview)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask> it=new org.semanticwb.model.GenericIterator(processformview.getSemanticObject().getModel().listSubjects(swbps_processFormView,processformview.getSemanticObject()));
       return it;
   }
    }

    public UserTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> listFromConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject>(getSemanticObject().listObjectProperties(swbps_hasFromConnectionObjectInv));
    }

    public boolean hasFromConnectionObject(org.semanticwb.process.ConnectionObject connectionobject)
    {
        if(connectionobject==null)return false;
        return getSemanticObject().hasObjectProperty(swbps_hasFromConnectionObjectInv,connectionobject.getSemanticObject());
    }


    public org.semanticwb.process.ConnectionObject getFromConnectionObject()
    {
         org.semanticwb.process.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasFromConnectionObjectInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ConnectionObject)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> listToConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject>(getSemanticObject().listObjectProperties(swbps_hasToConnectionObject));
    }

    public boolean hasToConnectionObject(org.semanticwb.process.ConnectionObject connectionobject)
    {
        if(connectionobject==null)return false;
        return getSemanticObject().hasObjectProperty(swbps_hasToConnectionObject,connectionobject.getSemanticObject());
    }

    public void addToConnectionObject(org.semanticwb.process.ConnectionObject value)
    {
        getSemanticObject().addObjectProperty(swbps_hasToConnectionObject, value.getSemanticObject());
    }

    public void removeAllToConnectionObject()
    {
        getSemanticObject().removeProperty(swbps_hasToConnectionObject);
    }

    public void removeToConnectionObject(org.semanticwb.process.ConnectionObject connectionobject)
    {
        getSemanticObject().removeObjectProperty(swbps_hasToConnectionObject,connectionobject.getSemanticObject());
    }


    public org.semanticwb.process.ConnectionObject getToConnectionObject()
    {
         org.semanticwb.process.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasToConnectionObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ConnectionObject)obj.createGenericInstance();
         }
         return ret;
    }

    public int getExecutions()
    {
        return getSemanticObject().getIntProperty(swbps_executions);
    }

    public void setExecutions(int value)
    {
        getSemanticObject().setIntProperty(swbps_executions, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance>(getSemanticObject().listObjectProperties(swbps_hasFlowObjectInstansInv));
    }

    public boolean hasFlowObjectInstance(org.semanticwb.process.FlowObjectInstance flowobjectinstance)
    {
        if(flowobjectinstance==null)return false;
        return getSemanticObject().hasObjectProperty(swbps_hasFlowObjectInstansInv,flowobjectinstance.getSemanticObject());
    }


    public org.semanticwb.process.FlowObjectInstance getFlowObjectInstance()
    {
         org.semanticwb.process.FlowObjectInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasFlowObjectInstansInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.FlowObjectInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public void setParentProcess(org.semanticwb.process.Process value)
    {
        getSemanticObject().setObjectProperty(swbps_parentProcessInv, value.getSemanticObject());
    }

    public void removeParentProcess()
    {
        getSemanticObject().removeProperty(swbps_parentProcessInv);
    }


    public org.semanticwb.process.Process getParentProcess()
    {
         org.semanticwb.process.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_parentProcessInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Process)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isKeepOpen()
    {
        return getSemanticObject().getBooleanProperty(swbps_keepOpen);
    }

    public void setKeepOpen(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbps_keepOpen, value);
    }

    public void setFormView(org.semanticwb.model.FormView value)
    {
        getSemanticObject().setObjectProperty(swbps_processFormView, value.getSemanticObject());
    }

    public void removeFormView()
    {
        getSemanticObject().removeProperty(swbps_processFormView);
    }


    public org.semanticwb.model.FormView getFormView()
    {
         org.semanticwb.model.FormView ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_processFormView);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.FormView)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
