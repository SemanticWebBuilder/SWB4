package org.semanticwb.model.base;


public abstract class ResourceTypeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Filterable,org.semanticwb.model.FilterableClass,org.semanticwb.model.FilterableNode,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swb_resourceBundle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceBundle");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceOWL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceOWL");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
    public static final org.semanticwb.platform.SemanticProperty swb_hasPTSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPTSubType");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceMode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceMode");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_hasPTResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPTResource");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceCache=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceCache");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceClassName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceClassName");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");

    public static class ClassMgr
    {

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
            return (org.semanticwb.model.ResourceType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasResourceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResourceType(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeBySubType(org.semanticwb.model.ResourceSubType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPTSubType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeBySubType(org.semanticwb.model.ResourceSubType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPTSubType,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPTResource, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPTResource,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ResourceTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
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

    public String getResourceBundle()
    {
        return getSemanticObject().getProperty(swb_resourceBundle);
    }

    public void setResourceBundle(String value)
    {
        getSemanticObject().setProperty(swb_resourceBundle, value);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public String getResourceOWL()
    {
        return getSemanticObject().getProperty(swb_resourceOWL);
    }

    public void setResourceOWL(String value)
    {
        getSemanticObject().setProperty(swb_resourceOWL, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceSubType> listSubTypes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceSubType>(getSemanticObject().listObjectProperties(swb_hasPTSubType));
    }

    public boolean hasSubType(org.semanticwb.model.ResourceSubType value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasPTSubType,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.ResourceSubType getSubType()
    {
         org.semanticwb.model.ResourceSubType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPTSubType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceSubType)obj.createGenericInstance();
         }
         return ret;
    }

    public int getResourceMode()
    {
        return getSemanticObject().getIntProperty(swb_resourceMode);
    }

    public void setResourceMode(int value)
    {
        getSemanticObject().setIntProperty(swb_resourceMode, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> listResources()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(getSemanticObject().listObjectProperties(swb_hasPTResource));
    }

    public boolean hasResource(org.semanticwb.model.Resource value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasPTResource,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPTResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

    public int getResourceCache()
    {
        return getSemanticObject().getIntProperty(swb_resourceCache);
    }

    public void setResourceCache(int value)
    {
        getSemanticObject().setIntProperty(swb_resourceCache, value);
    }

    public String getResourceClassName()
    {
        return getSemanticObject().getProperty(swb_resourceClassName);
    }

    public void setResourceClassName(String value)
    {
        getSemanticObject().setProperty(swb_resourceClassName, value);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
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

    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
