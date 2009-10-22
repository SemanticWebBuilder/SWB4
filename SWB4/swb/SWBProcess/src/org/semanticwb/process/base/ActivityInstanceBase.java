package org.semanticwb.process.base;


public class ActivityInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.process.ProcessTraceable,org.semanticwb.model.Traceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swbps_ended=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#ended");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swbps_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#status");
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
       public static final org.semanticwb.platform.SemanticProperty swbps_processInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processInstanceInv");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swbps_endedby=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#endedby");
       public static final org.semanticwb.platform.SemanticClass swbps_ActivityInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActivityInstance");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActivityInstance");

       public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstances(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstances()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance>(it, true);
       }

       public static org.semanticwb.process.ActivityInstance createActivityInstance(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.ActivityInstance.ClassMgr.createActivityInstance(String.valueOf(id), model);
       }

       public static org.semanticwb.process.ActivityInstance getActivityInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ActivityInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.ActivityInstance createActivityInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ActivityInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeActivityInstance(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasActivityInstance(String id, org.semanticwb.model.SWBModel model)
       {
           return (getActivityInstance(id, model)!=null);
       }
    }

    public ActivityInstanceBase(org.semanticwb.platform.SemanticObject base)
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

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy,modifiedby.getSemanticObject()));
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

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_updated, value);
    }

    public java.util.Date getEnded()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swbps_ended);
    }

    public void setEnded(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swbps_ended, value);
    }

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbps_status);
    }

    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbps_status, value);
    }

    public void setProcessInstance(org.semanticwb.process.ProcessInstance value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbps_processInstanceInv, value.getSemanticObject());
    }

    public void removeProcessInstance()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_processInstanceInv);
    }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByProcessInstance(org.semanticwb.process.ProcessInstance processinstanceinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_processInstanceInv, processinstanceinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByProcessInstance(org.semanticwb.process.ProcessInstance processinstanceinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(processinstanceinv.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_processInstanceInv,processinstanceinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.ProcessInstance getProcessInstance()
    {
         org.semanticwb.process.ProcessInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_processInstanceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ProcessInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator,creator.getSemanticObject()));
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

    public void setEndedby(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbps_endedby, value.getSemanticObject());
    }

    public void removeEndedby()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_endedby);
    }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByEndedby(org.semanticwb.model.User endedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_endedby, endedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ActivityInstance> listActivityInstanceByEndedby(org.semanticwb.model.User endedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ActivityInstance> it=new org.semanticwb.model.GenericIterator(endedby.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_endedby,endedby.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getEndedby()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_endedby);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
