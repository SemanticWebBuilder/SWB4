package org.semanticwb.model.base;


public class ResourceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Viewable,org.semanticwb.model.Localeable,org.semanticwb.model.Priorityable,org.semanticwb.model.Indexable,org.semanticwb.model.RoleRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Calendarable,org.semanticwb.model.XMLable,org.semanticwb.model.Activeable,org.semanticwb.model.XMLConfable,org.semanticwb.model.Referensable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Hitable,org.semanticwb.model.Campable,org.semanticwb.model.Traceable,org.semanticwb.model.Deleteable
{
    public static final org.semanticwb.platform.SemanticClass swb_UserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasUserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUserGroupRef");
    public static final org.semanticwb.platform.SemanticClass swb_Camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Camp");
    public static final org.semanticwb.platform.SemanticProperty swb_camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#camp");
    public static final org.semanticwb.platform.SemanticProperty swb_xml=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#xml");
    public static final org.semanticwb.platform.SemanticProperty swb_hits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hits");
    public static final org.semanticwb.platform.SemanticProperty swb_xmlConf=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#xmlConf");
    public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
    public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceFilter");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceFilter");
    public static final org.semanticwb.platform.SemanticClass swb_Language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Language");
    public static final org.semanticwb.platform.SemanticProperty swb_language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#language");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasResourceRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceRefInv");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceType");
    public static final org.semanticwb.platform.SemanticProperty swb_maxViews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxViews");
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticProperty swb_maxHits=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxHits");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticClass swb_Calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");
    public static final org.semanticwb.platform.SemanticProperty swb_hasCalendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCalendar");
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
    public static final org.semanticwb.platform.SemanticProperty swb_pflowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pflowInstance");
    public static final org.semanticwb.platform.SemanticProperty swb_indexable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#indexable");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceSubType");
    public static final org.semanticwb.platform.SemanticProperty swb_priority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#priority");
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasRoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRoleRef");
    public static final org.semanticwb.platform.SemanticProperty swb_views=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#views");
    public static final org.semanticwb.platform.SemanticClass swb_Resourceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resourceable");
    public static final org.semanticwb.platform.SemanticProperty swb_hasResourceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceable");
    public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRuleRef");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticProperty swb_resourceWindow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resourceWindow");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");

    public ResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.Resource getResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.Resource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.Resource> listResources(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(org.semanticwb.model.Resource.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.Resource> listResources()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(org.semanticwb.model.Resource.class, it, true);
    }

    public static org.semanticwb.model.Resource createResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.Resource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static org.semanticwb.model.Resource createResource(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.model.Resource.createResource(String.valueOf(id), model);
    }

    public static void removeResource(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (getResource(id, model)!=null);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(org.semanticwb.model.UserGroupRef.class, getSemanticObject().listObjectProperties(swb_hasUserGroupRef));
    }

    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        if(usergroupref==null)return false;        return getSemanticObject().hasObjectProperty(swb_hasUserGroupRef,usergroupref.getSemanticObject());
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(org.semanticwb.model.UserGroupRef.class, getSemanticObject().listInheritProperties(swb_hasUserGroupRef));
    }

    public void addUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroupRef, usergroupref.getSemanticObject());
    }

    public void removeAllUserGroupRef()
    {
        getSemanticObject().removeProperty(swb_hasUserGroupRef);
    }

    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroupRef,usergroupref.getSemanticObject());
    }

    public org.semanticwb.model.UserGroupRef getUserGroupRef()
    {
         org.semanticwb.model.UserGroupRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUserGroupRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroupRef)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setCamp(org.semanticwb.model.Camp camp)
    {
        getSemanticObject().setObjectProperty(swb_camp, camp.getSemanticObject());
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
             ret=(org.semanticwb.model.Camp)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getXml()
    {
        return getSemanticObject().getProperty(swb_xml);
    }

    public void setXml(String xml)
    {
        getSemanticObject().setProperty(swb_xml, xml);
    }

    public long getHits()
    {
        return getSemanticObject().getLongProperty(swb_hits);
    }

    public void setHits(long hits)
    {
        getSemanticObject().setLongProperty(swb_hits, hits);
    }

    public String getXmlConf()
    {
        return getSemanticObject().getProperty(swb_xmlConf);
    }

    public void setXmlConf(String xmlConf)
    {
        getSemanticObject().setProperty(swb_xmlConf, xmlConf);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(swb_active, active);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, deleted);
    }

    public void setResourceFilter(org.semanticwb.model.ResourceFilter resourcefilter)
    {
        getSemanticObject().setObjectProperty(swb_resourceFilter, resourcefilter.getSemanticObject());
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
             ret=(org.semanticwb.model.ResourceFilter)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setLanguage(org.semanticwb.model.Language language)
    {
        getSemanticObject().setObjectProperty(swb_language, language.getSemanticObject());
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
             ret=(org.semanticwb.model.Language)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> listResourceRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef>(org.semanticwb.model.ResourceRef.class, getSemanticObject().listObjectProperties(swb_hasResourceRefInv));
    }

    public boolean hasResourceRefInv(org.semanticwb.model.ResourceRef resourceref)
    {
        if(resourceref==null)return false;        return getSemanticObject().hasObjectProperty(swb_hasResourceRefInv,resourceref.getSemanticObject());
    }

    public org.semanticwb.model.ResourceRef getResourceRefInv()
    {
         org.semanticwb.model.ResourceRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasResourceRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceRef)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setResourceType(org.semanticwb.model.ResourceType resourcetype)
    {
        getSemanticObject().setObjectProperty(swb_resourceType, resourcetype.getSemanticObject());
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
             ret=(org.semanticwb.model.ResourceType)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public long getMaxViews()
    {
        return getSemanticObject().getLongProperty(swb_maxViews);
    }

    public void setMaxViews(long maxViews)
    {
        getSemanticObject().setLongProperty(swb_maxViews, maxViews);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
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
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(swb_title, title);
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

    public void setMaxHits(long maxHits)
    {
        getSemanticObject().setLongProperty(swb_maxHits, maxHits);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar> listCalendars()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Calendar>(org.semanticwb.model.Calendar.class, getSemanticObject().listObjectProperties(swb_hasCalendar));
    }

    public boolean hasCalendar(org.semanticwb.model.Calendar calendar)
    {
        if(calendar==null)return false;        return getSemanticObject().hasObjectProperty(swb_hasCalendar,calendar.getSemanticObject());
    }

    public void addCalendar(org.semanticwb.model.Calendar calendar)
    {
        getSemanticObject().addObjectProperty(swb_hasCalendar, calendar.getSemanticObject());
    }

    public void removeAllCalendar()
    {
        getSemanticObject().removeProperty(swb_hasCalendar);
    }

    public void removeCalendar(org.semanticwb.model.Calendar calendar)
    {
        getSemanticObject().removeObjectProperty(swb_hasCalendar,calendar.getSemanticObject());
    }

    public org.semanticwb.model.Calendar getCalendar()
    {
         org.semanticwb.model.Calendar ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasCalendar);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Calendar)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setPflowInstance(org.semanticwb.model.PFlowInstance pflowinstance)
    {
        getSemanticObject().setObjectProperty(swb_pflowInstance, pflowinstance.getSemanticObject());
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
             ret=(org.semanticwb.model.PFlowInstance)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(swb_indexable);
    }

    public void setIndexable(boolean indexable)
    {
        getSemanticObject().setBooleanProperty(swb_indexable, indexable);
    }

    public void setResourceSubType(org.semanticwb.model.ResourceSubType resourcesubtype)
    {
        getSemanticObject().setObjectProperty(swb_resourceSubType, resourcesubtype.getSemanticObject());
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
             ret=(org.semanticwb.model.ResourceSubType)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

    public void setPriority(int priority)
    {
        getSemanticObject().setLongProperty(swb_priority, priority);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(org.semanticwb.model.RoleRef.class, getSemanticObject().listObjectProperties(swb_hasRoleRef));
    }

    public boolean hasRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        if(roleref==null)return false;        return getSemanticObject().hasObjectProperty(swb_hasRoleRef,roleref.getSemanticObject());
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(org.semanticwb.model.RoleRef.class, getSemanticObject().listInheritProperties(swb_hasRoleRef));
    }

    public void addRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().addObjectProperty(swb_hasRoleRef, roleref.getSemanticObject());
    }

    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(swb_hasRoleRef);
    }

    public void removeRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().removeObjectProperty(swb_hasRoleRef,roleref.getSemanticObject());
    }

    public org.semanticwb.model.RoleRef getRoleRef()
    {
         org.semanticwb.model.RoleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRoleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RoleRef)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public long getViews()
    {
        return getSemanticObject().getLongProperty(swb_views);
    }

    public void setViews(long views)
    {
        getSemanticObject().setLongProperty(swb_views, views);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resourceable> listResourceables()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resourceable>(org.semanticwb.model.Resourceable.class, getSemanticObject().listObjectProperties(swb_hasResourceable));
    }

    public boolean hasResourceable(org.semanticwb.model.Resourceable resourceable)
    {
        if(resourceable==null)return false;        return getSemanticObject().hasObjectProperty(swb_hasResourceable,resourceable.getSemanticObject());
    }

    public org.semanticwb.model.Resourceable getResourceable()
    {
         org.semanticwb.model.Resourceable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasResourceable);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resourceable)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(org.semanticwb.model.RuleRef.class, getSemanticObject().listObjectProperties(swb_hasRuleRef));
    }

    public boolean hasRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        if(ruleref==null)return false;        return getSemanticObject().hasObjectProperty(swb_hasRuleRef,ruleref.getSemanticObject());
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listInheritRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(org.semanticwb.model.RuleRef.class, getSemanticObject().listInheritProperties(swb_hasRuleRef));
    }

    public void addRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().addObjectProperty(swb_hasRuleRef, ruleref.getSemanticObject());
    }

    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(swb_hasRuleRef);
    }

    public void removeRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().removeObjectProperty(swb_hasRuleRef,ruleref.getSemanticObject());
    }

    public org.semanticwb.model.RuleRef getRuleRef()
    {
         org.semanticwb.model.RuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RuleRef)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
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
             ret=(org.semanticwb.model.User)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public boolean isResourceWindow()
    {
        return getSemanticObject().getBooleanProperty(swb_resourceWindow);
    }

    public void setResourceWindow(boolean resourceWindow)
    {
        getSemanticObject().setBooleanProperty(swb_resourceWindow, resourceWindow);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(swb_description, description);
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
        return new org.semanticwb.model.WebSite(getSemanticObject().getModel().getModelObject());
    }
}
