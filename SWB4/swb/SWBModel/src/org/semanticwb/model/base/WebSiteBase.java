package org.semanticwb.model.base;


public abstract class WebSiteBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.Traceable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Filterable,org.semanticwb.model.Indexable,org.semanticwb.model.Localeable,org.semanticwb.model.Trashable,org.semanticwb.model.OntologyDepable,org.semanticwb.model.Undeleteable
{
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
    public static final org.semanticwb.platform.SemanticProperty swb_hasSubModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasSubModel");
    public static final org.semanticwb.platform.SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");
    public static final org.semanticwb.platform.SemanticProperty swb_userRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepository");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swb_homePage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#homePage");
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    public static final org.semanticwb.platform.SemanticProperty swb_defaultTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#defaultTemplate");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceSubType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceSubType");
    public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
    public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
    public static final org.semanticwb.platform.SemanticClass swb_Language=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Language");
    public static final org.semanticwb.platform.SemanticClass swb_Dns=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Dns");
    public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
    public static final org.semanticwb.platform.SemanticClass swb_Rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
    public static final org.semanticwb.platform.SemanticClass swb_Camp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Camp");
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    public static final org.semanticwb.platform.SemanticClass swb_Calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");
    public static final org.semanticwb.platform.SemanticClass swb_Device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");
    public static final org.semanticwb.platform.SemanticClass swb_AdminFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminFilter");
    public static final org.semanticwb.platform.SemanticClass swb_IPFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#IPFilter");
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
    public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceFilter");
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
    public static final org.semanticwb.platform.SemanticClass swb_CalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#CalendarRef");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceType");
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
    public static final org.semanticwb.platform.SemanticClass swb_TemplateGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateGroup");
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
    public static final org.semanticwb.platform.SemanticClass swb_WebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite>(it, true);
        }

        public static org.semanticwb.model.WebSite getWebSite(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.model.WebSite ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    ret=(org.semanticwb.model.WebSite)obj.createGenericInstance();
                }
            }
            return ret;
        }

        public static org.semanticwb.model.WebSite createWebSite(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.model.WebSite)model.createGenericObject(model.getObjectUri(id,sclass),sclass);
        }

        public static void removeWebSite(String id)
        {
            org.semanticwb.model.WebSite obj=getWebSite(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }

        public static boolean hasWebSite(String id)
        {
            return (getWebSite(id)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteBySubModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteBySubModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasSubModel,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByUserRepository(org.semanticwb.model.UserRepository value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByUserRepository(org.semanticwb.model.UserRepository value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_userRepository,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByOntology(org.semanticwb.model.Ontology value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByOntology(org.semanticwb.model.Ontology value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasOntology,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByHomePage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_homePage, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByHomePage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_homePage,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByDefaultTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSiteByDefaultTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebSite> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_defaultTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public WebSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel> listSubModels()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel>(getSemanticObject().listObjectProperties(swb_hasSubModel));
    }

    public boolean hasSubModel(org.semanticwb.model.SWBModel value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasSubModel,value.getSemanticObject());
        }
        return ret;
    }

    public void addSubModel(org.semanticwb.model.SWBModel value)
    {
        getSemanticObject().addObjectProperty(swb_hasSubModel, value.getSemanticObject());
    }

    public void removeAllSubModel()
    {
        getSemanticObject().removeProperty(swb_hasSubModel);
    }

    public void removeSubModel(org.semanticwb.model.SWBModel value)
    {
        getSemanticObject().removeObjectProperty(swb_hasSubModel,value.getSemanticObject());
    }

    public org.semanticwb.model.SWBModel getSubModel()
    {
         org.semanticwb.model.SWBModel ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasSubModel);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.SWBModel)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
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

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public void setUserRepository(org.semanticwb.model.UserRepository value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_userRepository, value.getSemanticObject());
        }else
        {
            removeUserRepository();
        }
    }

    public void removeUserRepository()
    {
        getSemanticObject().removeProperty(swb_userRepository);
    }

    public org.semanticwb.model.UserRepository getUserRepository()
    {
         org.semanticwb.model.UserRepository ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_userRepository);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserRepository)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology> listOntologies()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Ontology>(getSemanticObject().listObjectProperties(swb_hasOntology));
    }

    public boolean hasOntology(org.semanticwb.model.Ontology value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasOntology,value.getSemanticObject());
        }
        return ret;
    }

    public void addOntology(org.semanticwb.model.Ontology value)
    {
        getSemanticObject().addObjectProperty(swb_hasOntology, value.getSemanticObject());
    }

    public void removeAllOntology()
    {
        getSemanticObject().removeProperty(swb_hasOntology);
    }

    public void removeOntology(org.semanticwb.model.Ontology value)
    {
        getSemanticObject().removeObjectProperty(swb_hasOntology,value.getSemanticObject());
    }

    public org.semanticwb.model.Ontology getOntology()
    {
         org.semanticwb.model.Ontology ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasOntology);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Ontology)obj.createGenericInstance();
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

    public void setHomePage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_homePage, value.getSemanticObject());
        }else
        {
            removeHomePage();
        }
    }

    public void removeHomePage()
    {
        getSemanticObject().removeProperty(swb_homePage);
    }

    public org.semanticwb.model.WebPage getHomePage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_homePage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public void setDefaultTemplate(org.semanticwb.model.Template value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_defaultTemplate, value.getSemanticObject());
        }else
        {
            removeDefaultTemplate();
        }
    }

    public void removeDefaultTemplate()
    {
        getSemanticObject().removeProperty(swb_defaultTemplate);
    }

    public org.semanticwb.model.Template getDefaultTemplate()
    {
         org.semanticwb.model.Template ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_defaultTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Template)obj.createGenericInstance();
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

    public org.semanticwb.model.ResourceSubType getResourceSubType(String id)
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.getResourceSubType(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.ResourceSubType> listResourceSubTypes()
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.listResourceSubTypes(this);
    }

    public org.semanticwb.model.ResourceSubType createResourceSubType(String id)
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.createResourceSubType(id,this);
    }

    public void removeResourceSubType(String id)
    {
        org.semanticwb.model.ResourceSubType.ClassMgr.removeResourceSubType(id, this);
    }
    public boolean hasResourceSubType(String id)
    {
        return org.semanticwb.model.ResourceSubType.ClassMgr.hasResourceSubType(id, this);
    }

    public org.semanticwb.model.Association getAssociation(String id)
    {
        return org.semanticwb.model.Association.ClassMgr.getAssociation(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Association> listAssociations()
    {
        return org.semanticwb.model.Association.ClassMgr.listAssociations(this);
    }

    public org.semanticwb.model.Association createAssociation(String id)
    {
        return org.semanticwb.model.Association.ClassMgr.createAssociation(id,this);
    }

    public org.semanticwb.model.Association createAssociation()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Association);
        return org.semanticwb.model.Association.ClassMgr.createAssociation(String.valueOf(id),this);
    } 

    public void removeAssociation(String id)
    {
        org.semanticwb.model.Association.ClassMgr.removeAssociation(id, this);
    }
    public boolean hasAssociation(String id)
    {
        return org.semanticwb.model.Association.ClassMgr.hasAssociation(id, this);
    }

    public org.semanticwb.model.ResourceRef getResourceRef(String id)
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.getResourceRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefs()
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.listResourceRefs(this);
    }

    public org.semanticwb.model.ResourceRef createResourceRef(String id)
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.createResourceRef(id,this);
    }

    public org.semanticwb.model.ResourceRef createResourceRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_ResourceRef);
        return org.semanticwb.model.ResourceRef.ClassMgr.createResourceRef(String.valueOf(id),this);
    } 

    public void removeResourceRef(String id)
    {
        org.semanticwb.model.ResourceRef.ClassMgr.removeResourceRef(id, this);
    }
    public boolean hasResourceRef(String id)
    {
        return org.semanticwb.model.ResourceRef.ClassMgr.hasResourceRef(id, this);
    }

    public org.semanticwb.model.RuleRef getRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.ClassMgr.getRuleRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return org.semanticwb.model.RuleRef.ClassMgr.listRuleRefs(this);
    }

    public org.semanticwb.model.RuleRef createRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.ClassMgr.createRuleRef(id,this);
    }

    public org.semanticwb.model.RuleRef createRuleRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_RuleRef);
        return org.semanticwb.model.RuleRef.ClassMgr.createRuleRef(String.valueOf(id),this);
    } 

    public void removeRuleRef(String id)
    {
        org.semanticwb.model.RuleRef.ClassMgr.removeRuleRef(id, this);
    }
    public boolean hasRuleRef(String id)
    {
        return org.semanticwb.model.RuleRef.ClassMgr.hasRuleRef(id, this);
    }

    public org.semanticwb.model.Language getLanguage(String id)
    {
        return org.semanticwb.model.Language.ClassMgr.getLanguage(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Language> listLanguages()
    {
        return org.semanticwb.model.Language.ClassMgr.listLanguages(this);
    }

    public org.semanticwb.model.Language createLanguage(String id)
    {
        return org.semanticwb.model.Language.ClassMgr.createLanguage(id,this);
    }

    public void removeLanguage(String id)
    {
        org.semanticwb.model.Language.ClassMgr.removeLanguage(id, this);
    }
    public boolean hasLanguage(String id)
    {
        return org.semanticwb.model.Language.ClassMgr.hasLanguage(id, this);
    }

    public org.semanticwb.model.Dns getDns(String id)
    {
        return org.semanticwb.model.Dns.ClassMgr.getDns(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Dns> listDnses()
    {
        return org.semanticwb.model.Dns.ClassMgr.listDnses(this);
    }

    public org.semanticwb.model.Dns createDns(String id)
    {
        return org.semanticwb.model.Dns.ClassMgr.createDns(id,this);
    }

    public org.semanticwb.model.Dns createDns()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Dns);
        return org.semanticwb.model.Dns.ClassMgr.createDns(String.valueOf(id),this);
    } 

    public void removeDns(String id)
    {
        org.semanticwb.model.Dns.ClassMgr.removeDns(id, this);
    }
    public boolean hasDns(String id)
    {
        return org.semanticwb.model.Dns.ClassMgr.hasDns(id, this);
    }

    public org.semanticwb.model.AssMember getAssMember(String id)
    {
        return org.semanticwb.model.AssMember.ClassMgr.getAssMember(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.AssMember> listAssMembers()
    {
        return org.semanticwb.model.AssMember.ClassMgr.listAssMembers(this);
    }

    public org.semanticwb.model.AssMember createAssMember(String id)
    {
        return org.semanticwb.model.AssMember.ClassMgr.createAssMember(id,this);
    }

    public org.semanticwb.model.AssMember createAssMember()
    {
        long id=getSemanticObject().getModel().getCounter(swb_AssMember);
        return org.semanticwb.model.AssMember.ClassMgr.createAssMember(String.valueOf(id),this);
    } 

    public void removeAssMember(String id)
    {
        org.semanticwb.model.AssMember.ClassMgr.removeAssMember(id, this);
    }
    public boolean hasAssMember(String id)
    {
        return org.semanticwb.model.AssMember.ClassMgr.hasAssMember(id, this);
    }

    public org.semanticwb.model.Rule getRule(String id)
    {
        return org.semanticwb.model.Rule.ClassMgr.getRule(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Rule> listRules()
    {
        return org.semanticwb.model.Rule.ClassMgr.listRules(this);
    }

    public org.semanticwb.model.Rule createRule(String id)
    {
        return org.semanticwb.model.Rule.ClassMgr.createRule(id,this);
    }

    public org.semanticwb.model.Rule createRule()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Rule);
        return org.semanticwb.model.Rule.ClassMgr.createRule(String.valueOf(id),this);
    } 

    public void removeRule(String id)
    {
        org.semanticwb.model.Rule.ClassMgr.removeRule(id, this);
    }
    public boolean hasRule(String id)
    {
        return org.semanticwb.model.Rule.ClassMgr.hasRule(id, this);
    }

    public org.semanticwb.model.Camp getCamp(String id)
    {
        return org.semanticwb.model.Camp.ClassMgr.getCamp(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Camp> listCamps()
    {
        return org.semanticwb.model.Camp.ClassMgr.listCamps(this);
    }

    public org.semanticwb.model.Camp createCamp(String id)
    {
        return org.semanticwb.model.Camp.ClassMgr.createCamp(id,this);
    }

    public org.semanticwb.model.Camp createCamp()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Camp);
        return org.semanticwb.model.Camp.ClassMgr.createCamp(String.valueOf(id),this);
    } 

    public void removeCamp(String id)
    {
        org.semanticwb.model.Camp.ClassMgr.removeCamp(id, this);
    }
    public boolean hasCamp(String id)
    {
        return org.semanticwb.model.Camp.ClassMgr.hasCamp(id, this);
    }

    public org.semanticwb.model.VersionInfo getVersionInfo(String id)
    {
        return org.semanticwb.model.VersionInfo.ClassMgr.getVersionInfo(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.VersionInfo> listVersionInfos()
    {
        return org.semanticwb.model.VersionInfo.ClassMgr.listVersionInfos(this);
    }

    public org.semanticwb.model.VersionInfo createVersionInfo(String id)
    {
        return org.semanticwb.model.VersionInfo.ClassMgr.createVersionInfo(id,this);
    }

    public org.semanticwb.model.VersionInfo createVersionInfo()
    {
        long id=getSemanticObject().getModel().getCounter(swb_VersionInfo);
        return org.semanticwb.model.VersionInfo.ClassMgr.createVersionInfo(String.valueOf(id),this);
    } 

    public void removeVersionInfo(String id)
    {
        org.semanticwb.model.VersionInfo.ClassMgr.removeVersionInfo(id, this);
    }
    public boolean hasVersionInfo(String id)
    {
        return org.semanticwb.model.VersionInfo.ClassMgr.hasVersionInfo(id, this);
    }

    public org.semanticwb.model.WebPage getWebPage(String id)
    {
        return org.semanticwb.model.WebPage.ClassMgr.getWebPage(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.WebPage> listWebPages()
    {
        return org.semanticwb.model.WebPage.ClassMgr.listWebPages(this);
    }

    public org.semanticwb.model.WebPage createWebPage(String id)
    {
        return org.semanticwb.model.WebPage.ClassMgr.createWebPage(id,this);
    }

    public void removeWebPage(String id)
    {
        org.semanticwb.model.WebPage.ClassMgr.removeWebPage(id, this);
    }
    public boolean hasWebPage(String id)
    {
        return org.semanticwb.model.WebPage.ClassMgr.hasWebPage(id, this);
    }

    public org.semanticwb.model.Calendar getCalendar(String id)
    {
        return org.semanticwb.model.Calendar.ClassMgr.getCalendar(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Calendar> listCalendars()
    {
        return org.semanticwb.model.Calendar.ClassMgr.listCalendars(this);
    }

    public org.semanticwb.model.Calendar createCalendar(String id)
    {
        return org.semanticwb.model.Calendar.ClassMgr.createCalendar(id,this);
    }

    public org.semanticwb.model.Calendar createCalendar()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Calendar);
        return org.semanticwb.model.Calendar.ClassMgr.createCalendar(String.valueOf(id),this);
    } 

    public void removeCalendar(String id)
    {
        org.semanticwb.model.Calendar.ClassMgr.removeCalendar(id, this);
    }
    public boolean hasCalendar(String id)
    {
        return org.semanticwb.model.Calendar.ClassMgr.hasCalendar(id, this);
    }

    public org.semanticwb.model.Device getDevice(String id)
    {
        return org.semanticwb.model.Device.ClassMgr.getDevice(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Device> listDevices()
    {
        return org.semanticwb.model.Device.ClassMgr.listDevices(this);
    }

    public org.semanticwb.model.Device createDevice(String id)
    {
        return org.semanticwb.model.Device.ClassMgr.createDevice(id,this);
    }

    public void removeDevice(String id)
    {
        org.semanticwb.model.Device.ClassMgr.removeDevice(id, this);
    }
    public boolean hasDevice(String id)
    {
        return org.semanticwb.model.Device.ClassMgr.hasDevice(id, this);
    }

    public org.semanticwb.model.AdminFilter getAdminFilter(String id)
    {
        return org.semanticwb.model.AdminFilter.ClassMgr.getAdminFilter(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.AdminFilter> listAdminFilters()
    {
        return org.semanticwb.model.AdminFilter.ClassMgr.listAdminFilters(this);
    }

    public org.semanticwb.model.AdminFilter createAdminFilter(String id)
    {
        return org.semanticwb.model.AdminFilter.ClassMgr.createAdminFilter(id,this);
    }

    public org.semanticwb.model.AdminFilter createAdminFilter()
    {
        long id=getSemanticObject().getModel().getCounter(swb_AdminFilter);
        return org.semanticwb.model.AdminFilter.ClassMgr.createAdminFilter(String.valueOf(id),this);
    } 

    public void removeAdminFilter(String id)
    {
        org.semanticwb.model.AdminFilter.ClassMgr.removeAdminFilter(id, this);
    }
    public boolean hasAdminFilter(String id)
    {
        return org.semanticwb.model.AdminFilter.ClassMgr.hasAdminFilter(id, this);
    }

    public org.semanticwb.model.IPFilter getIPFilter(String id)
    {
        return org.semanticwb.model.IPFilter.ClassMgr.getIPFilter(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.IPFilter> listIPFilters()
    {
        return org.semanticwb.model.IPFilter.ClassMgr.listIPFilters(this);
    }

    public org.semanticwb.model.IPFilter createIPFilter(String id)
    {
        return org.semanticwb.model.IPFilter.ClassMgr.createIPFilter(id,this);
    }

    public org.semanticwb.model.IPFilter createIPFilter()
    {
        long id=getSemanticObject().getModel().getCounter(swb_IPFilter);
        return org.semanticwb.model.IPFilter.ClassMgr.createIPFilter(String.valueOf(id),this);
    } 

    public void removeIPFilter(String id)
    {
        org.semanticwb.model.IPFilter.ClassMgr.removeIPFilter(id, this);
    }
    public boolean hasIPFilter(String id)
    {
        return org.semanticwb.model.IPFilter.ClassMgr.hasIPFilter(id, this);
    }

    public org.semanticwb.model.PFlowRef getPFlowRef(String id)
    {
        return org.semanticwb.model.PFlowRef.ClassMgr.getPFlowRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        return org.semanticwb.model.PFlowRef.ClassMgr.listPFlowRefs(this);
    }

    public org.semanticwb.model.PFlowRef createPFlowRef(String id)
    {
        return org.semanticwb.model.PFlowRef.ClassMgr.createPFlowRef(id,this);
    }

    public org.semanticwb.model.PFlowRef createPFlowRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PFlowRef);
        return org.semanticwb.model.PFlowRef.ClassMgr.createPFlowRef(String.valueOf(id),this);
    } 

    public void removePFlowRef(String id)
    {
        org.semanticwb.model.PFlowRef.ClassMgr.removePFlowRef(id, this);
    }
    public boolean hasPFlowRef(String id)
    {
        return org.semanticwb.model.PFlowRef.ClassMgr.hasPFlowRef(id, this);
    }

    public org.semanticwb.model.Template getTemplate(String id)
    {
        return org.semanticwb.model.Template.ClassMgr.getTemplate(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Template> listTemplates()
    {
        return org.semanticwb.model.Template.ClassMgr.listTemplates(this);
    }

    public org.semanticwb.model.Template createTemplate(String id)
    {
        return org.semanticwb.model.Template.ClassMgr.createTemplate(id,this);
    }

    public org.semanticwb.model.Template createTemplate()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Template);
        return org.semanticwb.model.Template.ClassMgr.createTemplate(String.valueOf(id),this);
    } 

    public void removeTemplate(String id)
    {
        org.semanticwb.model.Template.ClassMgr.removeTemplate(id, this);
    }
    public boolean hasTemplate(String id)
    {
        return org.semanticwb.model.Template.ClassMgr.hasTemplate(id, this);
    }

    public org.semanticwb.model.Country getCountry(String id)
    {
        return org.semanticwb.model.Country.ClassMgr.getCountry(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Country> listCountries()
    {
        return org.semanticwb.model.Country.ClassMgr.listCountries(this);
    }

    public org.semanticwb.model.Country createCountry(String id)
    {
        return org.semanticwb.model.Country.ClassMgr.createCountry(id,this);
    }

    public void removeCountry(String id)
    {
        org.semanticwb.model.Country.ClassMgr.removeCountry(id, this);
    }
    public boolean hasCountry(String id)
    {
        return org.semanticwb.model.Country.ClassMgr.hasCountry(id, this);
    }

    public org.semanticwb.model.PFlow getPFlow(String id)
    {
        return org.semanticwb.model.PFlow.ClassMgr.getPFlow(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.PFlow> listPFlows()
    {
        return org.semanticwb.model.PFlow.ClassMgr.listPFlows(this);
    }

    public org.semanticwb.model.PFlow createPFlow(String id)
    {
        return org.semanticwb.model.PFlow.ClassMgr.createPFlow(id,this);
    }

    public org.semanticwb.model.PFlow createPFlow()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PFlow);
        return org.semanticwb.model.PFlow.ClassMgr.createPFlow(String.valueOf(id),this);
    } 

    public void removePFlow(String id)
    {
        org.semanticwb.model.PFlow.ClassMgr.removePFlow(id, this);
    }
    public boolean hasPFlow(String id)
    {
        return org.semanticwb.model.PFlow.ClassMgr.hasPFlow(id, this);
    }

    public org.semanticwb.model.ResourceFilter getResourceFilter(String id)
    {
        return org.semanticwb.model.ResourceFilter.ClassMgr.getResourceFilter(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.ResourceFilter> listResourceFilters()
    {
        return org.semanticwb.model.ResourceFilter.ClassMgr.listResourceFilters(this);
    }

    public org.semanticwb.model.ResourceFilter createResourceFilter(String id)
    {
        return org.semanticwb.model.ResourceFilter.ClassMgr.createResourceFilter(id,this);
    }

    public org.semanticwb.model.ResourceFilter createResourceFilter()
    {
        long id=getSemanticObject().getModel().getCounter(swb_ResourceFilter);
        return org.semanticwb.model.ResourceFilter.ClassMgr.createResourceFilter(String.valueOf(id),this);
    } 

    public void removeResourceFilter(String id)
    {
        org.semanticwb.model.ResourceFilter.ClassMgr.removeResourceFilter(id, this);
    }
    public boolean hasResourceFilter(String id)
    {
        return org.semanticwb.model.ResourceFilter.ClassMgr.hasResourceFilter(id, this);
    }

    public org.semanticwb.model.TemplateRef getTemplateRef(String id)
    {
        return org.semanticwb.model.TemplateRef.ClassMgr.getTemplateRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return org.semanticwb.model.TemplateRef.ClassMgr.listTemplateRefs(this);
    }

    public org.semanticwb.model.TemplateRef createTemplateRef(String id)
    {
        return org.semanticwb.model.TemplateRef.ClassMgr.createTemplateRef(id,this);
    }

    public org.semanticwb.model.TemplateRef createTemplateRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_TemplateRef);
        return org.semanticwb.model.TemplateRef.ClassMgr.createTemplateRef(String.valueOf(id),this);
    } 

    public void removeTemplateRef(String id)
    {
        org.semanticwb.model.TemplateRef.ClassMgr.removeTemplateRef(id, this);
    }
    public boolean hasTemplateRef(String id)
    {
        return org.semanticwb.model.TemplateRef.ClassMgr.hasTemplateRef(id, this);
    }

    public org.semanticwb.model.CalendarRef getCalendarRef(String id)
    {
        return org.semanticwb.model.CalendarRef.ClassMgr.getCalendarRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return org.semanticwb.model.CalendarRef.ClassMgr.listCalendarRefs(this);
    }

    public org.semanticwb.model.CalendarRef createCalendarRef(String id)
    {
        return org.semanticwb.model.CalendarRef.ClassMgr.createCalendarRef(id,this);
    }

    public org.semanticwb.model.CalendarRef createCalendarRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_CalendarRef);
        return org.semanticwb.model.CalendarRef.ClassMgr.createCalendarRef(String.valueOf(id),this);
    } 

    public void removeCalendarRef(String id)
    {
        org.semanticwb.model.CalendarRef.ClassMgr.removeCalendarRef(id, this);
    }
    public boolean hasCalendarRef(String id)
    {
        return org.semanticwb.model.CalendarRef.ClassMgr.hasCalendarRef(id, this);
    }

    public org.semanticwb.model.Resource getResource(String id)
    {
        return org.semanticwb.model.Resource.ClassMgr.getResource(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.Resource> listResources()
    {
        return org.semanticwb.model.Resource.ClassMgr.listResources(this);
    }

    public org.semanticwb.model.Resource createResource(String id)
    {
        return org.semanticwb.model.Resource.ClassMgr.createResource(id,this);
    }

    public org.semanticwb.model.Resource createResource()
    {
        long id=getSemanticObject().getModel().getCounter(swb_Resource);
        return org.semanticwb.model.Resource.ClassMgr.createResource(String.valueOf(id),this);
    } 

    public void removeResource(String id)
    {
        org.semanticwb.model.Resource.ClassMgr.removeResource(id, this);
    }
    public boolean hasResource(String id)
    {
        return org.semanticwb.model.Resource.ClassMgr.hasResource(id, this);
    }

    public org.semanticwb.model.ResourceType getResourceType(String id)
    {
        return org.semanticwb.model.ResourceType.ClassMgr.getResourceType(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.ResourceType> listResourceTypes()
    {
        return org.semanticwb.model.ResourceType.ClassMgr.listResourceTypes(this);
    }

    public org.semanticwb.model.ResourceType createResourceType(String id)
    {
        return org.semanticwb.model.ResourceType.ClassMgr.createResourceType(id,this);
    }

    public void removeResourceType(String id)
    {
        org.semanticwb.model.ResourceType.ClassMgr.removeResourceType(id, this);
    }
    public boolean hasResourceType(String id)
    {
        return org.semanticwb.model.ResourceType.ClassMgr.hasResourceType(id, this);
    }

    public org.semanticwb.model.PFlowInstance getPFlowInstance(String id)
    {
        return org.semanticwb.model.PFlowInstance.ClassMgr.getPFlowInstance(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances()
    {
        return org.semanticwb.model.PFlowInstance.ClassMgr.listPFlowInstances(this);
    }

    public org.semanticwb.model.PFlowInstance createPFlowInstance(String id)
    {
        return org.semanticwb.model.PFlowInstance.ClassMgr.createPFlowInstance(id,this);
    }

    public org.semanticwb.model.PFlowInstance createPFlowInstance()
    {
        long id=getSemanticObject().getModel().getCounter(swb_PFlowInstance);
        return org.semanticwb.model.PFlowInstance.ClassMgr.createPFlowInstance(String.valueOf(id),this);
    } 

    public void removePFlowInstance(String id)
    {
        org.semanticwb.model.PFlowInstance.ClassMgr.removePFlowInstance(id, this);
    }
    public boolean hasPFlowInstance(String id)
    {
        return org.semanticwb.model.PFlowInstance.ClassMgr.hasPFlowInstance(id, this);
    }

    public org.semanticwb.model.TemplateGroup getTemplateGroup(String id)
    {
        return org.semanticwb.model.TemplateGroup.ClassMgr.getTemplateGroup(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.TemplateGroup> listTemplateGroups()
    {
        return org.semanticwb.model.TemplateGroup.ClassMgr.listTemplateGroups(this);
    }

    public org.semanticwb.model.TemplateGroup createTemplateGroup(String id)
    {
        return org.semanticwb.model.TemplateGroup.ClassMgr.createTemplateGroup(id,this);
    }

    public org.semanticwb.model.TemplateGroup createTemplateGroup()
    {
        long id=getSemanticObject().getModel().getCounter(swb_TemplateGroup);
        return org.semanticwb.model.TemplateGroup.ClassMgr.createTemplateGroup(String.valueOf(id),this);
    } 

    public void removeTemplateGroup(String id)
    {
        org.semanticwb.model.TemplateGroup.ClassMgr.removeTemplateGroup(id, this);
    }
    public boolean hasTemplateGroup(String id)
    {
        return org.semanticwb.model.TemplateGroup.ClassMgr.hasTemplateGroup(id, this);
    }

    public org.semanticwb.model.RoleRef getRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.ClassMgr.getRoleRef(id, this);
    }

    public java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return org.semanticwb.model.RoleRef.ClassMgr.listRoleRefs(this);
    }

    public org.semanticwb.model.RoleRef createRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.ClassMgr.createRoleRef(id,this);
    }

    public org.semanticwb.model.RoleRef createRoleRef()
    {
        long id=getSemanticObject().getModel().getCounter(swb_RoleRef);
        return org.semanticwb.model.RoleRef.ClassMgr.createRoleRef(String.valueOf(id),this);
    } 

    public void removeRoleRef(String id)
    {
        org.semanticwb.model.RoleRef.ClassMgr.removeRoleRef(id, this);
    }
    public boolean hasRoleRef(String id)
    {
        return org.semanticwb.model.RoleRef.ClassMgr.hasRoleRef(id, this);
    }
}
