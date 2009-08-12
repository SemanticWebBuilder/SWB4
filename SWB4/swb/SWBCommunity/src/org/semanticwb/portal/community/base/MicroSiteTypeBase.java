package org.semanticwb.portal.community.base;


public class MicroSiteTypeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Templateable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    public static final org.semanticwb.platform.SemanticProperty swb_hasTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasTemplate");
    public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteUtil");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMicroSiteUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMicroSiteUtil");
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteClass");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_microSiteClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#microSiteClass");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");

    public MicroSiteTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypes(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypes()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType>(it, true);
    }

    public static org.semanticwb.portal.community.MicroSiteType getMicroSiteType(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.MicroSiteType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.MicroSiteType createMicroSiteType(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.MicroSiteType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeMicroSiteType(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasMicroSiteType(String id, org.semanticwb.model.SWBModel model)
    {
        return (getMicroSiteType(id, model)!=null);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> listTemplates()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Template>(getSemanticObject().listObjectProperties(swb_hasTemplate));
    }

    public boolean hasTemplate(org.semanticwb.model.Template template)
    {
        if(template==null)return false;
        return getSemanticObject().hasObjectProperty(swb_hasTemplate,template.getSemanticObject());
    }

    public void addTemplate(org.semanticwb.model.Template template)
    {
        getSemanticObject().addObjectProperty(swb_hasTemplate, template.getSemanticObject());
    }

    public void removeAllTemplate()
    {
        getSemanticObject().removeProperty(swb_hasTemplate);
    }

    public void removeTemplate(org.semanticwb.model.Template template)
    {
        getSemanticObject().removeObjectProperty(swb_hasTemplate,template.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByHasTemplate(org.semanticwb.model.Template hastemplate,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasTemplate, hastemplate.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByHasTemplate(org.semanticwb.model.Template hastemplate)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(hastemplate.getSemanticObject().getModel().listSubjects(swb_hasTemplate,hastemplate.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.Template getTemplate()
    {
         org.semanticwb.model.Template ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Template)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(swb_active, active);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteUtil> listMicroSiteUtils()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteUtil>(getSemanticObject().listObjectProperties(swbcomm_hasMicroSiteUtil));
    }

    public boolean hasMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil micrositeutil)
    {
        if(micrositeutil==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasMicroSiteUtil,micrositeutil.getSemanticObject());
    }

    public void addMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil micrositeutil)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasMicroSiteUtil, micrositeutil.getSemanticObject());
    }

    public void removeAllMicroSiteUtil()
    {
        getSemanticObject().removeProperty(swbcomm_hasMicroSiteUtil);
    }

    public void removeMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil micrositeutil)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasMicroSiteUtil,micrositeutil.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByHasMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil hasmicrositeutil,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasMicroSiteUtil, hasmicrositeutil.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByHasMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil hasmicrositeutil)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(hasmicrositeutil.getSemanticObject().getModel().listSubjects(swbcomm_hasMicroSiteUtil,hasmicrositeutil.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSiteUtil getMicroSiteUtil()
    {
         org.semanticwb.portal.community.MicroSiteUtil ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasMicroSiteUtil);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteUtil)obj.createGenericInstance();
         }
         return ret;
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

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
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

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    public void setMicroSiteClass(org.semanticwb.portal.community.MicroSiteClass micrositeclass)
    {
        getSemanticObject().setObjectProperty(swbcomm_microSiteClass, micrositeclass.getSemanticObject());
    }

    public void removeMicroSiteClass()
    {
        getSemanticObject().removeProperty(swbcomm_microSiteClass);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByMicroSiteClass(org.semanticwb.portal.community.MicroSiteClass micrositeclass,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_microSiteClass, micrositeclass.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByMicroSiteClass(org.semanticwb.portal.community.MicroSiteClass micrositeclass)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(micrositeclass.getSemanticObject().getModel().listSubjects(swbcomm_microSiteClass,micrositeclass.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSiteClass getMicroSiteClass()
    {
         org.semanticwb.portal.community.MicroSiteClass ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_microSiteClass);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteClass)obj.createGenericInstance();
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

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
       return it;
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

    public org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer()
    {
        return (org.semanticwb.portal.community.MicrositeContainer)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
