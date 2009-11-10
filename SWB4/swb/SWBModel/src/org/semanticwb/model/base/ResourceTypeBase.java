package org.semanticwb.model.base;


public class ResourceTypeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Filterable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swb_resourceBundle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceBundle");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swb_resourceOWL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceOWL");
       public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
       public static final org.semanticwb.platform.SemanticProperty swb_hasPTSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPTSubType");
       public static final org.semanticwb.platform.SemanticProperty swb_resourceMode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceMode");
       public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
       public static final org.semanticwb.platform.SemanticProperty swb_hasPTResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPTResource");
       public static final org.semanticwb.platform.SemanticProperty swb_resourceCache=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceCache");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swb_resourceClassName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceClassName");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");

       public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypes(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypes()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType>(it, true);
       }

       public static org.semanticwb.model.ResourceType getResourceType(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.model.ResourceType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.model.ResourceType createResourceType(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.model.ResourceType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeResourceType(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasResourceType(String id, org.semanticwb.model.SWBModel model)
       {
           return (getResourceType(id, model)!=null);
       }
    }

    public ResourceTypeBase(org.semanticwb.platform.SemanticObject base)
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

   public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy,modifiedby.getSemanticObject()));
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

    public String getResourceBundle()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_resourceBundle);
    }

    public void setResourceBundle(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_resourceBundle, value);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_updated, value);
    }

    public String getResourceOWL()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_resourceOWL);
    }

    public void setResourceOWL(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_resourceOWL, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceSubType> listSubTypes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceSubType>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasPTSubType));
    }

    public boolean hasSubType(org.semanticwb.model.ResourceSubType resourcesubtype)
    {
        if(resourcesubtype==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasPTSubType,resourcesubtype.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeBySubType(org.semanticwb.model.ResourceSubType hasptsubtype,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasPTSubType, hasptsubtype.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeBySubType(org.semanticwb.model.ResourceSubType hasptsubtype)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(hasptsubtype.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasPTSubType,hasptsubtype.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.ResourceSubType getSubType()
    {
         org.semanticwb.model.ResourceSubType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasPTSubType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceSubType)obj.createGenericInstance();
         }
         return ret;
    }

    public int getResourceMode()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swb_resourceMode);
    }

    public void setResourceMode(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swb_resourceMode, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> listResources()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasPTResource));
    }

    public boolean hasResource(org.semanticwb.model.Resource resource)
    {
        if(resource==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasPTResource,resource.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByResource(org.semanticwb.model.Resource hasptresource,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasPTResource, hasptresource.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByResource(org.semanticwb.model.Resource hasptresource)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(hasptresource.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasPTResource,hasptresource.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasPTResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

    public int getResourceCache()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swb_resourceCache);
    }

    public void setResourceCache(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swb_resourceCache, value);
    }

    public String getResourceClassName()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_resourceClassName);
    }

    public void setResourceClassName(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_resourceClassName, value);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator,creator.getSemanticObject()));
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

    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
