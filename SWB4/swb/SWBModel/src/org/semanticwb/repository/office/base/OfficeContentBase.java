package org.semanticwb.repository.office.base;


public class OfficeContentBase extends org.semanticwb.repository.File implements org.semanticwb.content.Descriptiveable,org.semanticwb.repository.Referenceable,org.semanticwb.repository.Lockable,org.semanticwb.repository.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
    public static final org.semanticwb.platform.SemanticProperty cm_officetype=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#officetype");
    public static final org.semanticwb.platform.SemanticProperty jcr_lockIsDeep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lockIsDeep");
    public static final org.semanticwb.platform.SemanticProperty cm_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#title");
    public static final org.semanticwb.platform.SemanticProperty cm_lastModified=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#lastModified");
    public static final org.semanticwb.platform.SemanticProperty jcr_lockOwner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lockOwner");
    public static final org.semanticwb.platform.SemanticProperty cm_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#description");
    public static final org.semanticwb.platform.SemanticClass cm_OfficeContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#OfficeContent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org.mx/swb4/content#OfficeContent");

    public OfficeContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.repository.office.OfficeContent getOfficeContent(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.office.OfficeContent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContents(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent>(org.semanticwb.repository.office.OfficeContent.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContents()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent>(org.semanticwb.repository.office.OfficeContent.class, it, true);
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

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String uuid)
    {
        getSemanticObject().setProperty(jcr_uuid, uuid);
    }

    public String getOfficetype()
    {
        return getSemanticObject().getProperty(cm_officetype);
    }

    public void setOfficetype(String officetype)
    {
        getSemanticObject().setProperty(cm_officetype, officetype);
    }

    public boolean isLockIsDeep()
    {
        return getSemanticObject().getBooleanProperty(jcr_lockIsDeep);
    }

    public void setLockIsDeep(boolean lockIsDeep)
    {
        getSemanticObject().setBooleanProperty(jcr_lockIsDeep, lockIsDeep);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(cm_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(cm_title, title);
    }

    public java.util.Date getLastModified()
    {
        return getSemanticObject().getDateProperty(cm_lastModified);
    }

    public void setLastModified(java.util.Date lastModified)
    {
        getSemanticObject().setDateProperty(cm_lastModified, lastModified);
    }

    public String getLockOwner()
    {
        return getSemanticObject().getProperty(jcr_lockOwner);
    }

    public void setLockOwner(String lockOwner)
    {
        getSemanticObject().setProperty(jcr_lockOwner, lockOwner);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(cm_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(cm_description, description);
    }
}
