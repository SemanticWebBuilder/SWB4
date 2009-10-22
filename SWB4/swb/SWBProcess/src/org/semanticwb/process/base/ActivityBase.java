package org.semanticwb.process.base;


public class ActivityBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticClass swbps_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Activity");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasDependence=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasDependence");
       public static final org.semanticwb.platform.SemanticProperty swbps_executions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#executions");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Activity");

       public static java.util.Iterator<org.semanticwb.process.Activity> listActivities(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.Activity> listActivities()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity>(it, true);
       }

       public static org.semanticwb.process.Activity createActivity(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.Activity.ClassMgr.createActivity(String.valueOf(id), model);
       }

       public static org.semanticwb.process.Activity getActivity(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.Activity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.Activity createActivity(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.Activity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeActivity(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasActivity(String id, org.semanticwb.model.SWBModel model)
       {
           return (getActivity(id, model)!=null);
       }
    }

    public ActivityBase(org.semanticwb.platform.SemanticObject base)
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

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy,modifiedby.getSemanticObject()));
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

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_updated, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> listDependences()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity>(getSemanticObject().listObjectProperties(ClassMgr.swbps_hasDependence));
    }

    public boolean hasDependence(org.semanticwb.process.Activity activity)
    {
        if(activity==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbps_hasDependence,activity.getSemanticObject());
    }

    public void addDependence(org.semanticwb.process.Activity value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbps_hasDependence, value.getSemanticObject());
    }

    public void removeAllDependence()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_hasDependence);
    }

    public void removeDependence(org.semanticwb.process.Activity activity)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbps_hasDependence,activity.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByDependence(org.semanticwb.process.Activity hasdependence,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasDependence, hasdependence.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByDependence(org.semanticwb.process.Activity hasdependence)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(hasdependence.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasDependence,hasdependence.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.Activity getDependence()
    {
         org.semanticwb.process.Activity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasDependence);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Activity)obj.createGenericInstance();
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

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Activity> listActivityByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator,creator.getSemanticObject()));
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

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
