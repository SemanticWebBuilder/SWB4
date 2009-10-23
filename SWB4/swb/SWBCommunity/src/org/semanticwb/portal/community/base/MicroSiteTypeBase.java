package org.semanticwb.portal.community.base;


public class MicroSiteTypeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Templateable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
       public static final org.semanticwb.platform.SemanticProperty swb_hasTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasTemplate");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteClass");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_microSiteClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#microSiteClass");
       public static final org.semanticwb.platform.SemanticProperty swb_active=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteUtil");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMicroSiteUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMicroSiteUtil");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");

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
    }

    public MicroSiteTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> listTemplates()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Template>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasTemplate));
    }

    public boolean hasTemplate(org.semanticwb.model.Template template)
    {
        if(template==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasTemplate,template.getSemanticObject());
    }

    public void addTemplate(org.semanticwb.model.Template value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swb_hasTemplate, value.getSemanticObject());
    }

    public void removeAllTemplate()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_hasTemplate);
    }

    public void removeTemplate(org.semanticwb.model.Template template)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swb_hasTemplate,template.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByTemplate(org.semanticwb.model.Template hastemplate,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasTemplate, hastemplate.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByTemplate(org.semanticwb.model.Template hastemplate)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(hastemplate.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasTemplate,hastemplate.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.Template getTemplate()
    {
         org.semanticwb.model.Template ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Template)obj.createGenericInstance();
         }
         return ret;
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

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy,modifiedby.getSemanticObject()));
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

    public void setMicroSiteClass(org.semanticwb.portal.community.MicroSiteClass value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbcomm_microSiteClass, value.getSemanticObject());
    }

    public void removeMicroSiteClass()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_microSiteClass);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByMicroSiteClass(org.semanticwb.portal.community.MicroSiteClass micrositeclass,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_microSiteClass, micrositeclass.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByMicroSiteClass(org.semanticwb.portal.community.MicroSiteClass micrositeclass)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(micrositeclass.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_microSiteClass,micrositeclass.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSiteClass getMicroSiteClass()
    {
         org.semanticwb.portal.community.MicroSiteClass ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_microSiteClass);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteClass)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_active);
    }

    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swb_active, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteUtil> listMicroSiteUtils()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteUtil>(getSemanticObject().listObjectProperties(ClassMgr.swbcomm_hasMicroSiteUtil));
    }

    public boolean hasMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil micrositeutil)
    {
        if(micrositeutil==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbcomm_hasMicroSiteUtil,micrositeutil.getSemanticObject());
    }

    public void addMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbcomm_hasMicroSiteUtil, value.getSemanticObject());
    }

    public void removeAllMicroSiteUtil()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_hasMicroSiteUtil);
    }

    public void removeMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil micrositeutil)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbcomm_hasMicroSiteUtil,micrositeutil.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil hasmicrositeutil,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasMicroSiteUtil, hasmicrositeutil.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil hasmicrositeutil)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(hasmicrositeutil.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasMicroSiteUtil,hasmicrositeutil.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSiteUtil getMicroSiteUtil()
    {
         org.semanticwb.portal.community.MicroSiteUtil ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_hasMicroSiteUtil);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteUtil)obj.createGenericInstance();
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

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator,creator.getSemanticObject()));
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

    public org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer()
    {
        return (org.semanticwb.portal.community.MicrositeContainer)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
