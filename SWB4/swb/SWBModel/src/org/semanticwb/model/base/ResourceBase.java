package org.semanticwb.model.base;


public abstract class ResourceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Filterable,org.semanticwb.model.XMLable,org.semanticwb.model.Trashable,org.semanticwb.model.Sortable,org.semanticwb.model.Activeable,org.semanticwb.model.Expirable,org.semanticwb.model.Traceable,org.semanticwb.model.XMLConfable,org.semanticwb.model.Tagable,org.semanticwb.model.Localeable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Priorityable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Campable,org.semanticwb.model.Deviceable,org.semanticwb.model.Viewable,org.semanticwb.model.Hitable,org.semanticwb.model.Indexable,org.semanticwb.model.Searchable,org.semanticwb.model.RoleRefable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Referensable,org.semanticwb.model.RuleRefable,org.semanticwb.model.UserGroupRefable
{
    public static final org.semanticwb.platform.SemanticClass swb_ResourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceFilter");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceFilter");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasResourceRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceRefInv");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceType");
    public static final org.semanticwb.platform.SemanticClass swb_SWBSemanticResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBSemanticResource");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceData");
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
    public static final org.semanticwb.platform.SemanticProperty swb_pflowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pflowInstance");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceSubType");
    public static final org.semanticwb.platform.SemanticClass swb_Resourceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resourceable");
    public static final org.semanticwb.platform.SemanticProperty swb_hasResourceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceable");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceWindow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceWindow");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.Resource> listResources(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResources()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(it, true);
        }

        public static org.semanticwb.model.Resource createResource(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.Resource.ClassMgr.createResource(String.valueOf(id), model);
        }

        public static org.semanticwb.model.Resource getResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Resource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.Resource createResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Resource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeResource(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResource(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCamp(org.semanticwb.model.Camp value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_camp, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCamp(org.semanticwb.model.Camp value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_camp,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceFilter(org.semanticwb.model.ResourceFilter value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_resourceFilter, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceFilter(org.semanticwb.model.ResourceFilter value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_resourceFilter,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceRefInv(org.semanticwb.model.ResourceRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResourceRefInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceRefInv(org.semanticwb.model.ResourceRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResourceRefInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceType(org.semanticwb.model.ResourceType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_resourceType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceType(org.semanticwb.model.ResourceType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_resourceType,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByPflowInstance(org.semanticwb.model.PFlowInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_pflowInstance, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByPflowInstance(org.semanticwb.model.PFlowInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_pflowInstance,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceSubType(org.semanticwb.model.ResourceSubType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_resourceSubType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceSubType(org.semanticwb.model.ResourceSubType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_resourceSubType,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByDevice(org.semanticwb.model.Device value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_device, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByDevice(org.semanticwb.model.Device value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_device,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceable(org.semanticwb.model.Resourceable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResourceable, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceable(org.semanticwb.model.Resourceable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResourceable,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listObjectProperties(swb_hasUserGroupRef));
    }

    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUserGroupRef,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listInheritProperties(swb_hasUserGroupRef));
    }

    public void addUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroupRef, value.getSemanticObject());
    }

    public void removeAllUserGroupRef()
    {
        getSemanticObject().removeProperty(swb_hasUserGroupRef);
    }

    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroupRef,value.getSemanticObject());
    }

    public org.semanticwb.model.UserGroupRef getUserGroupRef()
    {
         org.semanticwb.model.UserGroupRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUserGroupRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroupRef)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCamp(org.semanticwb.model.Camp value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_camp, value.getSemanticObject());
        }else
        {
            removeCamp();
        }
    }

    public void removeCamp()
    {
        getSemanticObject().removeProperty(swb_camp);
    }

    public org.semanticwb.model.Camp getCamp()
    {
         org.semanticwb.model.Camp ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_camp);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Camp)obj.createGenericInstance();
         }
         return ret;
    }

    public String getXml()
    {
        return getSemanticObject().getProperty(swb_xml);
    }

    public void setXml(String value)
    {
        getSemanticObject().setProperty(swb_xml, value);
    }

    public long getHits()
    {
        //Override this method in Resource object
        return getSemanticObject().getLongProperty(swb_hits,false);
    }

    public void setHits(long value)
    {
        //Override this method in Resource object
        getSemanticObject().setLongProperty(swb_hits, value,false);
    }

    public String getXmlConf()
    {
        return getSemanticObject().getProperty(swb_xmlConf);
    }

    public void setXmlConf(String value)
    {
        getSemanticObject().setProperty(swb_xmlConf, value);
    }

    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }

    public void setResourceFilter(org.semanticwb.model.ResourceFilter value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_resourceFilter, value.getSemanticObject());
        }else
        {
            removeResourceFilter();
        }
    }

    public void removeResourceFilter()
    {
        getSemanticObject().removeProperty(swb_resourceFilter);
    }

    public org.semanticwb.model.ResourceFilter getResourceFilter()
    {
         org.semanticwb.model.ResourceFilter ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_resourceFilter);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceFilter)obj.createGenericInstance();
         }
         return ret;
    }

    public void setLanguage(org.semanticwb.model.Language value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_language, value.getSemanticObject());
        }else
        {
            removeLanguage();
        }
    }

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(swb_language);
    }

    public org.semanticwb.model.Language getLanguage()
    {
         org.semanticwb.model.Language ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_language);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Language)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(swb_hasCalendarRef));
    }

    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasCalendarRef, value.getSemanticObject());
    }

    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(swb_hasCalendarRef);
    }

    public void removeCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
    }

    public org.semanticwb.model.CalendarRef getCalendarRef()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasCalendarRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> listResourceRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef>(getSemanticObject().listObjectProperties(swb_hasResourceRefInv));
    }

    public boolean hasResourceRefInv(org.semanticwb.model.ResourceRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasResourceRefInv,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.ResourceRef getResourceRefInv()
    {
         org.semanticwb.model.ResourceRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasResourceRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceRef)obj.createGenericInstance();
         }
         return ret;
    }

    public void setResourceType(org.semanticwb.model.ResourceType value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_resourceType, value.getSemanticObject());
        }else
        {
            removeResourceType();
        }
    }

    public void removeResourceType()
    {
        getSemanticObject().removeProperty(swb_resourceType);
    }

    public org.semanticwb.model.ResourceType getResourceType()
    {
         org.semanticwb.model.ResourceType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_resourceType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceType)obj.createGenericInstance();
         }
         return ret;
    }

    public long getMaxViews()
    {
        return getSemanticObject().getLongProperty(swb_maxViews);
    }

    public void setMaxViews(long value)
    {
        getSemanticObject().setLongProperty(swb_maxViews, value);
    }

    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
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

    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
    }

    public void setResourceData(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swb_resourceData, value);
    }

    public void removeResourceData()
    {
        getSemanticObject().removeProperty(swb_resourceData);
    }

    public org.semanticwb.platform.SemanticObject getResourceData()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swb_resourceData);
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

    public long getMaxHits()
    {
        return getSemanticObject().getLongProperty(swb_maxHits);
    }

    public void setMaxHits(long value)
    {
        getSemanticObject().setLongProperty(swb_maxHits, value);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public void setPflowInstance(org.semanticwb.model.PFlowInstance value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_pflowInstance, value.getSemanticObject());
        }else
        {
            removePflowInstance();
        }
    }

    public void removePflowInstance()
    {
        getSemanticObject().removeProperty(swb_pflowInstance);
    }

    public org.semanticwb.model.PFlowInstance getPflowInstance()
    {
         org.semanticwb.model.PFlowInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_pflowInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlowInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(swb_indexable);
    }

    public void setIndexable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_indexable, value);
    }

    public void setResourceSubType(org.semanticwb.model.ResourceSubType value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_resourceSubType, value.getSemanticObject());
        }else
        {
            removeResourceSubType();
        }
    }

    public void removeResourceSubType()
    {
        getSemanticObject().removeProperty(swb_resourceSubType);
    }

    public org.semanticwb.model.ResourceSubType getResourceSubType()
    {
         org.semanticwb.model.ResourceSubType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_resourceSubType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceSubType)obj.createGenericInstance();
         }
         return ret;
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(swb_priority, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listObjectProperties(swb_hasRoleRef));
    }

    public boolean hasRoleRef(org.semanticwb.model.RoleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRoleRef,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listInheritProperties(swb_hasRoleRef));
    }

    public void addRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRoleRef, value.getSemanticObject());
    }

    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(swb_hasRoleRef);
    }

    public void removeRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasRoleRef,value.getSemanticObject());
    }

    public org.semanticwb.model.RoleRef getRoleRef()
    {
         org.semanticwb.model.RoleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRoleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RoleRef)obj.createGenericInstance();
         }
         return ret;
    }

    public void setDevice(org.semanticwb.model.Device value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_device, value.getSemanticObject());
        }else
        {
            removeDevice();
        }
    }

    public void removeDevice()
    {
        getSemanticObject().removeProperty(swb_device);
    }

    public org.semanticwb.model.Device getDevice()
    {
         org.semanticwb.model.Device ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_device);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Device)obj.createGenericInstance();
         }
         return ret;
    }

    public long getViews()
    {
        //Override this method in Resource object
        return getSemanticObject().getLongProperty(swb_views,false);
    }

    public void setViews(long value)
    {
        //Override this method in Resource object
        getSemanticObject().setLongProperty(swb_views, value,false);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resourceable> listResourceables()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resourceable>(getSemanticObject().listObjectProperties(swb_hasResourceable));
    }

    public boolean hasResourceable(org.semanticwb.model.Resourceable value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasResourceable,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.Resourceable getResourceable()
    {
         org.semanticwb.model.Resourceable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasResourceable);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resourceable)obj.createGenericInstance();
         }
         return ret;
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listObjectProperties(swb_hasRuleRef));
    }

    public boolean hasRuleRef(org.semanticwb.model.RuleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRuleRef,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listInheritRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listInheritProperties(swb_hasRuleRef));
    }

    public void addRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRuleRef, value.getSemanticObject());
    }

    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(swb_hasRuleRef);
    }

    public void removeRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasRuleRef,value.getSemanticObject());
    }

    public org.semanticwb.model.RuleRef getRuleRef()
    {
         org.semanticwb.model.RuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RuleRef)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isResourceWindow()
    {
        return getSemanticObject().getBooleanProperty(swb_resourceWindow);
    }

    public void setResourceWindow(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_resourceWindow, value);
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
