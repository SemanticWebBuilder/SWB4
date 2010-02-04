package org.semanticwb.process.model.base;


public abstract class BusinessProcessDiagramBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Versionable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Pool=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Pool");
       public static final org.semanticwb.platform.SemanticProperty swp_pools=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#pools");
       public static final org.semanticwb.platform.SemanticProperty swp_language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#language");
       public static final org.semanticwb.platform.SemanticProperty swp_queryLanguage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#queryLanguage");
       public static final org.semanticwb.platform.SemanticClass swp_BusinessProcessDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BusinessProcessDiagram");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BusinessProcessDiagram");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.BusinessProcessDiagram> listBusinessProcessDiagrams(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessProcessDiagram>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.BusinessProcessDiagram> listBusinessProcessDiagrams()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessProcessDiagram>(it, true);
       }

       public static org.semanticwb.process.model.BusinessProcessDiagram createBusinessProcessDiagram(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.BusinessProcessDiagram.ClassMgr.createBusinessProcessDiagram(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.BusinessProcessDiagram getBusinessProcessDiagram(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.BusinessProcessDiagram)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.BusinessProcessDiagram createBusinessProcessDiagram(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.BusinessProcessDiagram)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeBusinessProcessDiagram(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasBusinessProcessDiagram(String id, org.semanticwb.model.SWBModel model)
       {
           return (getBusinessProcessDiagram(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.BusinessProcessDiagram> listBusinessProcessDiagramByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessProcessDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.BusinessProcessDiagram> listBusinessProcessDiagramByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessProcessDiagram> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.BusinessProcessDiagram> listBusinessProcessDiagramByActualVersion(org.semanticwb.model.VersionInfo actualversion,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessProcessDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_actualVersion, actualversion.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.BusinessProcessDiagram> listBusinessProcessDiagramByActualVersion(org.semanticwb.model.VersionInfo actualversion)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessProcessDiagram> it=new org.semanticwb.model.GenericIterator(actualversion.getSemanticObject().getModel().listSubjects(swb_actualVersion,actualversion.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.BusinessProcessDiagram> listBusinessProcessDiagramByPools(org.semanticwb.process.model.Pool pools,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessProcessDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_pools, pools.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.BusinessProcessDiagram> listBusinessProcessDiagramByPools(org.semanticwb.process.model.Pool pools)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessProcessDiagram> it=new org.semanticwb.model.GenericIterator(pools.getSemanticObject().getModel().listSubjects(swp_pools,pools.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.BusinessProcessDiagram> listBusinessProcessDiagramByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessProcessDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.BusinessProcessDiagram> listBusinessProcessDiagramByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessProcessDiagram> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.BusinessProcessDiagram> listBusinessProcessDiagramByLastVersion(org.semanticwb.model.VersionInfo lastversion,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessProcessDiagram> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_lastVersion, lastversion.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.BusinessProcessDiagram> listBusinessProcessDiagramByLastVersion(org.semanticwb.model.VersionInfo lastversion)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BusinessProcessDiagram> it=new org.semanticwb.model.GenericIterator(lastversion.getSemanticObject().getModel().listSubjects(swb_lastVersion,lastversion.getSemanticObject()));
       return it;
   }
    }

    public BusinessProcessDiagramBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }


    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public void setActualVersion(org.semanticwb.model.VersionInfo value)
    {
        getSemanticObject().setObjectProperty(swb_actualVersion, value.getSemanticObject());
    }

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(swb_actualVersion);
    }


    public org.semanticwb.model.VersionInfo getActualVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_actualVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

    public void setPools(org.semanticwb.process.model.Pool value)
    {
        getSemanticObject().setObjectProperty(swp_pools, value.getSemanticObject());
    }

    public void removePools()
    {
        getSemanticObject().removeProperty(swp_pools);
    }


    public org.semanticwb.process.model.Pool getPools()
    {
         org.semanticwb.process.model.Pool ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_pools);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Pool)obj.createGenericInstance();
         }
         return ret;
    }

    public String getLanguage()
    {
        return getSemanticObject().getProperty(swp_language);
    }

    public void setLanguage(String value)
    {
        getSemanticObject().setProperty(swp_language, value);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }


    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public void setLastVersion(org.semanticwb.model.VersionInfo value)
    {
        getSemanticObject().setObjectProperty(swb_lastVersion, value.getSemanticObject());
    }

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(swb_lastVersion);
    }


    public org.semanticwb.model.VersionInfo getLastVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_lastVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

    public String getQueryLanguage()
    {
        return getSemanticObject().getProperty(swp_queryLanguage);
    }

    public void setQueryLanguage(String value)
    {
        getSemanticObject().setProperty(swp_queryLanguage, value);
    }
}
