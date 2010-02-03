package org.semanticwb.jcr283.repository.model.base;


public abstract class ConfigurationBase extends org.semanticwb.jcr283.repository.model.Base implements org.semanticwb.jcr283.repository.model.Versionable,org.semanticwb.jcr283.repository.model.Referenceable,org.semanticwb.jcr283.repository.model.SimpleVersionable
{
       public static final org.semanticwb.platform.SemanticClass nt_Configuration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#configuration");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#configuration");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurations(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurations()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration>(it, true);
       }

       public static org.semanticwb.jcr283.repository.model.Configuration getConfiguration(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.Configuration)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.jcr283.repository.model.Configuration createConfiguration(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.Configuration)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeConfiguration(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasConfiguration(String id, org.semanticwb.model.SWBModel model)
       {
           return (getConfiguration(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByActivity(org.semanticwb.jcr283.repository.model.Activity activity,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_activity, activity.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByActivity(org.semanticwb.jcr283.repository.model.Activity activity)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(activity.getSemanticObject().getModel().listSubjects(jcr_activity,activity.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByVersionHistory(org.semanticwb.jcr283.repository.model.VersionHistory versionhistory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_versionHistory, versionhistory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByVersionHistory(org.semanticwb.jcr283.repository.model.VersionHistory versionhistory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(versionhistory.getSemanticObject().getModel().listSubjects(jcr_versionHistory,versionhistory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByConfiguration(org.semanticwb.jcr283.repository.model.Configuration configuration,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_configuration, configuration.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByConfiguration(org.semanticwb.jcr283.repository.model.Configuration configuration)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(configuration.getSemanticObject().getModel().listSubjects(jcr_configuration,configuration.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByBaseVersion(org.semanticwb.jcr283.repository.model.Version baseversion,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_baseVersion, baseversion.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByBaseVersion(org.semanticwb.jcr283.repository.model.Version baseversion)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(baseversion.getSemanticObject().getModel().listSubjects(jcr_baseVersion,baseversion.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByMergeFailed(org.semanticwb.jcr283.repository.model.Version mergefailed,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_mergeFailed, mergefailed.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByMergeFailed(org.semanticwb.jcr283.repository.model.Version mergefailed)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(mergefailed.getSemanticObject().getModel().listSubjects(jcr_mergeFailed,mergefailed.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByNode(org.semanticwb.jcr283.repository.model.Base hasnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNode, hasnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Configuration> listConfigurationByNode(org.semanticwb.jcr283.repository.model.Base hasnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Configuration> it=new org.semanticwb.model.GenericIterator(hasnode.getSemanticObject().getModel().listSubjects(swbrep_hasNode,hasnode.getSemanticObject()));
       return it;
   }
    }

    public ConfigurationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String value)
    {
        getSemanticObject().setProperty(jcr_uuid, value);
    }

    public void setActivity(org.semanticwb.jcr283.repository.model.Activity value)
    {
        getSemanticObject().setObjectProperty(jcr_activity, value.getSemanticObject());
    }

    public void removeActivity()
    {
        getSemanticObject().removeProperty(jcr_activity);
    }


    public org.semanticwb.jcr283.repository.model.Activity getActivity()
    {
         org.semanticwb.jcr283.repository.model.Activity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_activity);
         if(obj!=null)
         {
             ret=(org.semanticwb.jcr283.repository.model.Activity)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isIsCheckedOut()
    {
        return getSemanticObject().getBooleanProperty(jcr_isCheckedOut);
    }

    public void setIsCheckedOut(boolean value)
    {
        getSemanticObject().setBooleanProperty(jcr_isCheckedOut, value);
    }

    public void setVersionHistory(org.semanticwb.jcr283.repository.model.VersionHistory value)
    {
        getSemanticObject().setObjectProperty(jcr_versionHistory, value.getSemanticObject());
    }

    public void removeVersionHistory()
    {
        getSemanticObject().removeProperty(jcr_versionHistory);
    }


    public org.semanticwb.jcr283.repository.model.VersionHistory getVersionHistory()
    {
         org.semanticwb.jcr283.repository.model.VersionHistory ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_versionHistory);
         if(obj!=null)
         {
             ret=(org.semanticwb.jcr283.repository.model.VersionHistory)obj.createGenericInstance();
         }
         return ret;
    }

    public void setConfiguration(org.semanticwb.jcr283.repository.model.Configuration value)
    {
        getSemanticObject().setObjectProperty(jcr_configuration, value.getSemanticObject());
    }

    public void removeConfiguration()
    {
        getSemanticObject().removeProperty(jcr_configuration);
    }


    public org.semanticwb.jcr283.repository.model.Configuration getConfiguration()
    {
         org.semanticwb.jcr283.repository.model.Configuration ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_configuration);
         if(obj!=null)
         {
             ret=(org.semanticwb.jcr283.repository.model.Configuration)obj.createGenericInstance();
         }
         return ret;
    }

    public void setBaseVersion(org.semanticwb.jcr283.repository.model.Version value)
    {
        getSemanticObject().setObjectProperty(jcr_baseVersion, value.getSemanticObject());
    }

    public void removeBaseVersion()
    {
        getSemanticObject().removeProperty(jcr_baseVersion);
    }


    public org.semanticwb.jcr283.repository.model.Version getBaseVersion()
    {
         org.semanticwb.jcr283.repository.model.Version ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_baseVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.jcr283.repository.model.Version)obj.createGenericInstance();
         }
         return ret;
    }

    public void setMergeFailed(org.semanticwb.jcr283.repository.model.Version value)
    {
        getSemanticObject().setObjectProperty(jcr_mergeFailed, value.getSemanticObject());
    }

    public void removeMergeFailed()
    {
        getSemanticObject().removeProperty(jcr_mergeFailed);
    }


    public org.semanticwb.jcr283.repository.model.Version getMergeFailed()
    {
         org.semanticwb.jcr283.repository.model.Version ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_mergeFailed);
         if(obj!=null)
         {
             ret=(org.semanticwb.jcr283.repository.model.Version)obj.createGenericInstance();
         }
         return ret;
    }
}
