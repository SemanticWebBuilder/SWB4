package org.semanticwb.process.base;


public class ProcessBase extends org.semanticwb.model.base.GenericObjectBase implements org.semanticwb.process.FlowObject,org.semanticwb.process.Activity,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessWebPage");
       public static final org.semanticwb.platform.SemanticProperty swbps_processWebPageInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processWebPageInv");
       public static final org.semanticwb.platform.SemanticClass swbps_ConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasFromConnectionObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFromConnectionObjectInv");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticClass swbps_FlowObjectInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObjectInstance");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasFlowObjectInstansInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFlowObjectInstansInv");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasToConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasToConnectionObject");
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessClass");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasProcessClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessClass");
       public static final org.semanticwb.platform.SemanticClass swbps_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
       public static final org.semanticwb.platform.SemanticProperty swbps_parentProcessInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#parentProcessInv");
       public static final org.semanticwb.platform.SemanticProperty swbps_executions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#executions");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbps_FlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasFlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFlowObject");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");

       public static java.util.Iterator<org.semanticwb.process.Process> listProcesses(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Process>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.Process> listProcesses()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Process>(it, true);
       }

       public static org.semanticwb.process.Process createProcess(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.Process.ClassMgr.createProcess(String.valueOf(id), model);
       }

       public static org.semanticwb.process.Process getProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.Process)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.Process createProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.Process)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeProcess(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (getProcess(id, model)!=null);
       }
    }

    public ProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_modifiedBy, value.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_modifiedBy);
    }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(ClassMgr.swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(ClassMgr.swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(ClassMgr.swb_title, title, lang);
    }

    public void setProcessWebPage(org.semanticwb.process.ProcessWebPage value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbps_processWebPageInv, value.getSemanticObject());
    }

    public void removeProcessWebPage()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_processWebPageInv);
    }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByProcessWebPage(org.semanticwb.process.ProcessWebPage processwebpageinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_processWebPageInv, processwebpageinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByProcessWebPage(org.semanticwb.process.ProcessWebPage processwebpageinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(processwebpageinv.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_processWebPageInv,processwebpageinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.ProcessWebPage getProcessWebPage()
    {
         org.semanticwb.process.ProcessWebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_processWebPageInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ProcessWebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> listFromConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject>(getSemanticObject().listObjectProperties(ClassMgr.swbps_hasFromConnectionObjectInv));
    }

    public boolean hasFromConnectionObject(org.semanticwb.process.ConnectionObject connectionobject)
    {
        if(connectionobject==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbps_hasFromConnectionObjectInv,connectionobject.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasFromConnectionObjectInv, hasfromconnectionobjectinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(hasfromconnectionobjectinv.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasFromConnectionObjectInv,hasfromconnectionobjectinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.ConnectionObject getFromConnectionObject()
    {
         org.semanticwb.process.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasFromConnectionObjectInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ConnectionObject)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_updated, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance>(getSemanticObject().listObjectProperties(ClassMgr.swbps_hasFlowObjectInstansInv));
    }

    public boolean hasFlowObjectInstance(org.semanticwb.process.FlowObjectInstance flowobjectinstance)
    {
        if(flowobjectinstance==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbps_hasFlowObjectInstansInv,flowobjectinstance.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasFlowObjectInstansInv, hasflowobjectinstansinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(hasflowobjectinstansinv.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasFlowObjectInstansInv,hasflowobjectinstansinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.FlowObjectInstance getFlowObjectInstance()
    {
         org.semanticwb.process.FlowObjectInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasFlowObjectInstansInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.FlowObjectInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> listToConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject>(getSemanticObject().listObjectProperties(ClassMgr.swbps_hasToConnectionObject));
    }

    public boolean hasToConnectionObject(org.semanticwb.process.ConnectionObject connectionobject)
    {
        if(connectionobject==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbps_hasToConnectionObject,connectionobject.getSemanticObject());
    }

    public void addToConnectionObject(org.semanticwb.process.ConnectionObject value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbps_hasToConnectionObject, value.getSemanticObject());
    }

    public void removeAllToConnectionObject()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_hasToConnectionObject);
    }

    public void removeToConnectionObject(org.semanticwb.process.ConnectionObject connectionobject)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbps_hasToConnectionObject,connectionobject.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasToConnectionObject, hastoconnectionobject.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(hastoconnectionobject.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasToConnectionObject,hastoconnectionobject.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.ConnectionObject getToConnectionObject()
    {
         org.semanticwb.process.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasToConnectionObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ConnectionObject)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listProcessClasses()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(ClassMgr.swbps_hasProcessClass.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addProcessClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbps_hasProcessClass, value);
    }

    public void removeAllProcessClass()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_hasProcessClass);
    }

    public void removeProcessClass(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbps_hasProcessClass,semanticobject);
    }

    public org.semanticwb.platform.SemanticObject getProcessClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasProcessClass);
         return ret;
    }

    public void setParentProcess(org.semanticwb.process.Process value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbps_parentProcessInv, value.getSemanticObject());
    }

    public void removeParentProcess()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_parentProcessInv);
    }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByParentProcess(org.semanticwb.process.Process parentprocessinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_parentProcessInv, parentprocessinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByParentProcess(org.semanticwb.process.Process parentprocessinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(parentprocessinv.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_parentProcessInv,parentprocessinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.Process getParentProcess()
    {
         org.semanticwb.process.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_parentProcessInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Process)obj.createGenericInstance();
         }
         return ret;
    }

    public int getExecutions()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbps_executions);
    }

    public void setExecutions(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbps_executions, value);
    }

    public boolean isValid()
    {
        //Override this method in Process object
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_valid,false);
    }

    public void setValid(boolean value)
    {
        //Override this method in Process object
        getSemanticObject().setBooleanProperty(ClassMgr.swb_valid, value,false);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator,creator.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(ClassMgr.swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(ClassMgr.swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(ClassMgr.swb_description, description, lang);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObject> listFlowObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObject>(getSemanticObject().listObjectProperties(ClassMgr.swbps_hasFlowObject));
    }

    public boolean hasFlowObject(org.semanticwb.process.FlowObject flowobject)
    {
        if(flowobject==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbps_hasFlowObject,flowobject.getSemanticObject());
    }

    public void addFlowObject(org.semanticwb.process.FlowObject value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbps_hasFlowObject, value.getSemanticObject());
    }

    public void removeAllFlowObject()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_hasFlowObject);
    }

    public void removeFlowObject(org.semanticwb.process.FlowObject flowobject)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbps_hasFlowObject,flowobject.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByFlowObject(org.semanticwb.process.FlowObject hasflowobject,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasFlowObject, hasflowobject.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByFlowObject(org.semanticwb.process.FlowObject hasflowobject)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(hasflowobject.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasFlowObject,hasflowobject.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.FlowObject getFlowObject()
    {
         org.semanticwb.process.FlowObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasFlowObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.FlowObject)obj.createGenericInstance();
         }
         return ret;
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
