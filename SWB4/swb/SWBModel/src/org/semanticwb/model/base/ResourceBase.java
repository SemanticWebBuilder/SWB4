package org.semanticwb.model.base;


public class ResourceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Campable,org.semanticwb.model.Traceable,org.semanticwb.model.Indexable,org.semanticwb.model.Activeable,org.semanticwb.model.RoleRefable,org.semanticwb.model.XMLConfable,org.semanticwb.model.Localeable,org.semanticwb.model.Viewable,org.semanticwb.model.Hitable,org.semanticwb.model.Priorityable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Deviceable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Trashable,org.semanticwb.model.Sortable,org.semanticwb.model.Expirable,org.semanticwb.model.XMLable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass swb_UserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasUserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUserGroupRef");
       public static final org.semanticwb.platform.SemanticClass swb_Camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Camp");
       public static final org.semanticwb.platform.SemanticProperty swb_camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#camp");
       public static final org.semanticwb.platform.SemanticProperty swb_hits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hits");
       public static final org.semanticwb.platform.SemanticProperty swb_xml=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#xml");
       public static final org.semanticwb.platform.SemanticProperty swb_xmlConf=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#xmlConf");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
       public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
       public static final org.semanticwb.platform.SemanticClass swb_ResourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceFilter");
       public static final org.semanticwb.platform.SemanticProperty swb_resourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceFilter");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticClass swb_Language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Language");
       public static final org.semanticwb.platform.SemanticProperty swb_language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#language");
       public static final org.semanticwb.platform.SemanticClass swb_CalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasCalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCalendarRef");
       public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasResourceRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceRefInv");
       public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
       public static final org.semanticwb.platform.SemanticProperty swb_resourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceType");
       public static final org.semanticwb.platform.SemanticProperty swb_maxViews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxViews");
       public static final org.semanticwb.platform.SemanticProperty swb_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#index");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_expiration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#expiration");
       public static final org.semanticwb.platform.SemanticClass swb_SWBSemanticResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBSemanticResource");
       public static final org.semanticwb.platform.SemanticProperty swb_resourceData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceData");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swb_maxHits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxHits");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
       public static final org.semanticwb.platform.SemanticProperty swb_pflowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pflowInstance");
       public static final org.semanticwb.platform.SemanticProperty swb_indexable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#indexable");
       public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
       public static final org.semanticwb.platform.SemanticProperty swb_resourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceSubType");
       public static final org.semanticwb.platform.SemanticProperty swb_priority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#priority");
       public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasRoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRoleRef");
       public static final org.semanticwb.platform.SemanticClass swb_Device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");
       public static final org.semanticwb.platform.SemanticProperty swb_device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#device");
       public static final org.semanticwb.platform.SemanticProperty swb_views=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#views");
       public static final org.semanticwb.platform.SemanticClass swb_Resourceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resourceable");
       public static final org.semanticwb.platform.SemanticProperty swb_hasResourceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceable");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
       public static final org.semanticwb.platform.SemanticProperty swb_hasRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRuleRef");
       public static final org.semanticwb.platform.SemanticProperty swb_resourceWindow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceWindow");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");

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
           return (org.semanticwb.model.Resource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeResource(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasResource(String id, org.semanticwb.model.SWBModel model)
       {
           return (getResource(id, model)!=null);
       }
    }

    public ResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasUserGroupRef));
    }

    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        if(usergroupref==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasUserGroupRef,usergroupref.getSemanticObject());
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listInheritProperties(ClassMgr.swb_hasUserGroupRef));
    }

    public void addUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasUserGroupRef, value.getSemanticObject());
    }

    public void removeAllUserGroupRef()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasUserGroupRef);
    }

    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasUserGroupRef,usergroupref.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasUserGroupRef, hasusergroupref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(hasusergroupref.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasUserGroupRef,hasusergroupref.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.UserGroupRef getUserGroupRef()
    {
         org.semanticwb.model.UserGroupRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasUserGroupRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroupRef)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCamp(org.semanticwb.model.Camp value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_camp, value.getSemanticObject());
    }

    public void removeCamp()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_camp);
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCamp(org.semanticwb.model.Camp camp,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_camp, camp.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCamp(org.semanticwb.model.Camp camp)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(camp.getSemanticObject().getModel().listSubjects(ClassMgr.swb_camp,camp.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.Camp getCamp()
    {
         org.semanticwb.model.Camp ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_camp);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Camp)obj.createGenericInstance();
         }
         return ret;
    }

    public long getHits()
    {
        //Override this method in Resource object
        return getSemanticObject().getLongProperty(ClassMgr.swb_hits,false);
    }

    public void setHits(long value)
    {
        //Override this method in Resource object
        getSemanticObject().setLongProperty(ClassMgr.swb_hits, value,false);
    }

    public String getXml()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_xml);
    }

    public void setXml(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_xml, value);
    }

    public String getXmlConf()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_xmlConf);
    }

    public void setXmlConf(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_xmlConf, value);
    }

    public String getTags()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_tags);
    }

    public void setTags(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_tags, value);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_deleted);
    }

    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swb_deleted, value);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_active);
    }

    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swb_active, value);
    }

    public void setResourceFilter(org.semanticwb.model.ResourceFilter value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_resourceFilter, value.getSemanticObject());
    }

    public void removeResourceFilter()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_resourceFilter);
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceFilter(org.semanticwb.model.ResourceFilter resourcefilter,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_resourceFilter, resourcefilter.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceFilter(org.semanticwb.model.ResourceFilter resourcefilter)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(resourcefilter.getSemanticObject().getModel().listSubjects(ClassMgr.swb_resourceFilter,resourcefilter.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.ResourceFilter getResourceFilter()
    {
         org.semanticwb.model.ResourceFilter ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_resourceFilter);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceFilter)obj.createGenericInstance();
         }
         return ret;
    }

    public void setLanguage(org.semanticwb.model.Language value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_language, value.getSemanticObject());
    }

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_language);
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByLanguage(org.semanticwb.model.Language language,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_language, language.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByLanguage(org.semanticwb.model.Language language)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(language.getSemanticObject().getModel().listSubjects(ClassMgr.swb_language,language.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.Language getLanguage()
    {
         org.semanticwb.model.Language ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_language);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Language)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasCalendarRef));
    }

    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        if(calendarref==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasCalendarRef,calendarref.getSemanticObject());
    }

    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasCalendarRef, value.getSemanticObject());
    }

    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasCalendarRef);
    }

    public void removeCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasCalendarRef,calendarref.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasCalendarRef, hascalendarref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(hascalendarref.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasCalendarRef,hascalendarref.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.CalendarRef getCalendarRef()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasCalendarRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> listResourceRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasResourceRefInv));
    }

    public boolean hasResourceRefInv(org.semanticwb.model.ResourceRef resourceref)
    {
        if(resourceref==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasResourceRefInv,resourceref.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceRefInv(org.semanticwb.model.ResourceRef hasresourcerefinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasResourceRefInv, hasresourcerefinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceRefInv(org.semanticwb.model.ResourceRef hasresourcerefinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(hasresourcerefinv.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasResourceRefInv,hasresourcerefinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.ResourceRef getResourceRefInv()
    {
         org.semanticwb.model.ResourceRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasResourceRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceRef)obj.createGenericInstance();
         }
         return ret;
    }

    public void setResourceType(org.semanticwb.model.ResourceType value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_resourceType, value.getSemanticObject());
    }

    public void removeResourceType()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_resourceType);
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceType(org.semanticwb.model.ResourceType resourcetype,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_resourceType, resourcetype.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceType(org.semanticwb.model.ResourceType resourcetype)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(resourcetype.getSemanticObject().getModel().listSubjects(ClassMgr.swb_resourceType,resourcetype.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.ResourceType getResourceType()
    {
         org.semanticwb.model.ResourceType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_resourceType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceType)obj.createGenericInstance();
         }
         return ret;
    }

    public long getMaxViews()
    {
        return getSemanticObject().getLongProperty(ClassMgr.swb_maxViews);
    }

    public void setMaxViews(long value)
    {
        getSemanticObject().setLongProperty(ClassMgr.swb_maxViews, value);
    }

    public int getIndex()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swb_index);
    }

    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swb_index, value);
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

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy,modifiedby.getSemanticObject()));
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

    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_expiration);
    }

    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_expiration, value);
    }

    public void setResourceData(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_resourceData, value);
    }

    public void removeResourceData()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_resourceData);
    }

    public org.semanticwb.platform.SemanticObject getResourceData()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(ClassMgr.swb_resourceData);
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

    public long getMaxHits()
    {
        return getSemanticObject().getLongProperty(ClassMgr.swb_maxHits);
    }

    public void setMaxHits(long value)
    {
        getSemanticObject().setLongProperty(ClassMgr.swb_maxHits, value);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_updated, value);
    }

    public void setPflowInstance(org.semanticwb.model.PFlowInstance value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_pflowInstance, value.getSemanticObject());
    }

    public void removePflowInstance()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_pflowInstance);
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByPflowInstance(org.semanticwb.model.PFlowInstance pflowinstance,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_pflowInstance, pflowinstance.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByPflowInstance(org.semanticwb.model.PFlowInstance pflowinstance)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(pflowinstance.getSemanticObject().getModel().listSubjects(ClassMgr.swb_pflowInstance,pflowinstance.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.PFlowInstance getPflowInstance()
    {
         org.semanticwb.model.PFlowInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_pflowInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlowInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_indexable);
    }

    public void setIndexable(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swb_indexable, value);
    }

    public void setResourceSubType(org.semanticwb.model.ResourceSubType value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_resourceSubType, value.getSemanticObject());
    }

    public void removeResourceSubType()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_resourceSubType);
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceSubType(org.semanticwb.model.ResourceSubType resourcesubtype,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_resourceSubType, resourcesubtype.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceSubType(org.semanticwb.model.ResourceSubType resourcesubtype)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(resourcesubtype.getSemanticObject().getModel().listSubjects(ClassMgr.swb_resourceSubType,resourcesubtype.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.ResourceSubType getResourceSubType()
    {
         org.semanticwb.model.ResourceSubType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_resourceSubType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceSubType)obj.createGenericInstance();
         }
         return ret;
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swb_priority);
    }

    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swb_priority, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasRoleRef));
    }

    public boolean hasRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        if(roleref==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasRoleRef,roleref.getSemanticObject());
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listInheritProperties(ClassMgr.swb_hasRoleRef));
    }

    public void addRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasRoleRef, value.getSemanticObject());
    }

    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasRoleRef);
    }

    public void removeRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasRoleRef,roleref.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRoleRef(org.semanticwb.model.RoleRef hasroleref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasRoleRef, hasroleref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRoleRef(org.semanticwb.model.RoleRef hasroleref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(hasroleref.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasRoleRef,hasroleref.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.RoleRef getRoleRef()
    {
         org.semanticwb.model.RoleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasRoleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RoleRef)obj.createGenericInstance();
         }
         return ret;
    }

    public void setDevice(org.semanticwb.model.Device value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_device, value.getSemanticObject());
    }

    public void removeDevice()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_device);
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByDevice(org.semanticwb.model.Device device,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_device, device.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByDevice(org.semanticwb.model.Device device)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(device.getSemanticObject().getModel().listSubjects(ClassMgr.swb_device,device.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.Device getDevice()
    {
         org.semanticwb.model.Device ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_device);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Device)obj.createGenericInstance();
         }
         return ret;
    }

    public long getViews()
    {
        //Override this method in Resource object
        return getSemanticObject().getLongProperty(ClassMgr.swb_views,false);
    }

    public void setViews(long value)
    {
        //Override this method in Resource object
        getSemanticObject().setLongProperty(ClassMgr.swb_views, value,false);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resourceable> listResourceables()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resourceable>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasResourceable));
    }

    public boolean hasResourceable(org.semanticwb.model.Resourceable resourceable)
    {
        if(resourceable==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasResourceable,resourceable.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceable(org.semanticwb.model.Resourceable hasresourceable,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasResourceable, hasresourceable.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByResourceable(org.semanticwb.model.Resourceable hasresourceable)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(hasresourceable.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasResourceable,hasresourceable.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.Resourceable getResourceable()
    {
         org.semanticwb.model.Resourceable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasResourceable);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resourceable)obj.createGenericInstance();
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

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator,creator.getSemanticObject()));
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasRuleRef));
    }

    public boolean hasRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        if(ruleref==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasRuleRef,ruleref.getSemanticObject());
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listInheritRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listInheritProperties(ClassMgr.swb_hasRuleRef));
    }

    public void addRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasRuleRef, value.getSemanticObject());
    }

    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasRuleRef);
    }

    public void removeRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasRuleRef,ruleref.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRuleRef(org.semanticwb.model.RuleRef hasruleref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasRuleRef, hasruleref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Resource> listResourceByRuleRef(org.semanticwb.model.RuleRef hasruleref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> it=new org.semanticwb.model.GenericIterator(hasruleref.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasRuleRef,hasruleref.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.RuleRef getRuleRef()
    {
         org.semanticwb.model.RuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RuleRef)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isResourceWindow()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_resourceWindow);
    }

    public void setResourceWindow(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swb_resourceWindow, value);
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
