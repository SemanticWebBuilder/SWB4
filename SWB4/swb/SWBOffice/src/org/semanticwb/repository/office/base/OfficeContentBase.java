package org.semanticwb.repository.office.base;


public class OfficeContentBase extends org.semanticwb.repository.File implements org.semanticwb.repository.Lockable,org.semanticwb.repository.office.Descriptiveable,org.semanticwb.repository.Traceable,org.semanticwb.repository.Referenceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty jcr_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#created");
       public static final org.semanticwb.platform.SemanticClass nt_BaseNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
       public static final org.semanticwb.platform.SemanticProperty swbrep_parentNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#parentNode");
       public static final org.semanticwb.platform.SemanticProperty jcr_primaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#primaryType");
       public static final org.semanticwb.platform.SemanticProperty swboffice_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#title");
       public static final org.semanticwb.platform.SemanticProperty swboffice_officetype=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#officetype");
       public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
       public static final org.semanticwb.platform.SemanticProperty swboffice_lastModified=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#lastModified");
       public static final org.semanticwb.platform.SemanticProperty jcr_lockIsDeep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lockIsDeep");
       public static final org.semanticwb.platform.SemanticProperty swbrep_path=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#path");
       public static final org.semanticwb.platform.SemanticProperty swbrep_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#name");
       public static final org.semanticwb.platform.SemanticProperty jcr_lockOwner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lockOwner");
       public static final org.semanticwb.platform.SemanticProperty swbrep_hasNodes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#hasNodes");
       public static final org.semanticwb.platform.SemanticProperty swboffice_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#description");
       public static final org.semanticwb.platform.SemanticProperty jcr_mixinTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mixinTypes");
       public static final org.semanticwb.platform.SemanticClass swboffice_OfficeContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeContent");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeContent");

       public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContents(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContents()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent>(it, true);
       }

       public static org.semanticwb.repository.office.OfficeContent getOfficeContent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.repository.office.OfficeContent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.repository.office.OfficeContent createOfficeContent(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.repository.office.OfficeContent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeOfficeContent(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasOfficeContent(String id, org.semanticwb.model.SWBModel model)
       {
           return (getOfficeContent(id, model)!=null);
       }
    }

    public OfficeContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(ClassMgr.swboffice_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swboffice_title, value);
    }

    public String getOfficetype()
    {
        return getSemanticObject().getProperty(ClassMgr.swboffice_officetype);
    }

    public void setOfficetype(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swboffice_officetype, value);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(ClassMgr.jcr_uuid);
    }

    public void setUuid(String value)
    {
        getSemanticObject().setProperty(ClassMgr.jcr_uuid, value);
    }

    public java.util.Date getLastModified()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swboffice_lastModified);
    }

    public void setLastModified(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swboffice_lastModified, value);
    }

    public boolean isLockIsDeep()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.jcr_lockIsDeep);
    }

    public void setLockIsDeep(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.jcr_lockIsDeep, value);
    }

    public String getLockOwner()
    {
        return getSemanticObject().getProperty(ClassMgr.jcr_lockOwner);
    }

    public void setLockOwner(String value)
    {
        getSemanticObject().setProperty(ClassMgr.jcr_lockOwner, value);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(ClassMgr.swboffice_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swboffice_description, value);
    }
}
