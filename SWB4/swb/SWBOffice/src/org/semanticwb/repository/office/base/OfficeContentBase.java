package org.semanticwb.repository.office.base;


public class OfficeContentBase extends org.semanticwb.repository.File implements org.semanticwb.repository.office.Descriptiveable,org.semanticwb.repository.Traceable,org.semanticwb.repository.Referenceable,org.semanticwb.repository.Lockable
{
    public static final org.semanticwb.platform.SemanticProperty office_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#title");
    public static final org.semanticwb.platform.SemanticProperty office_officetype=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#officetype");
    public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
    public static final org.semanticwb.platform.SemanticProperty office_lastModified=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#lastModified");
    public static final org.semanticwb.platform.SemanticProperty jcr_lockIsDeep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lockIsDeep");
    public static final org.semanticwb.platform.SemanticProperty jcr_lockOwner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lockOwner");
    public static final org.semanticwb.platform.SemanticProperty office_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#description");
    public static final org.semanticwb.platform.SemanticClass office_OfficeContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeContent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeContent");

    public OfficeContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

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

    public String getTitle()
    {
        return getSemanticObject().getProperty(office_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(office_title, title);
    }

    public String getOfficetype()
    {
        return getSemanticObject().getProperty(office_officetype);
    }

    public void setOfficetype(String officetype)
    {
        getSemanticObject().setProperty(office_officetype, officetype);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String uuid)
    {
        getSemanticObject().setProperty(jcr_uuid, uuid);
    }

    public java.util.Date getLastModified()
    {
        return getSemanticObject().getDateProperty(office_lastModified);
    }

    public void setLastModified(java.util.Date lastModified)
    {
        getSemanticObject().setDateProperty(office_lastModified, lastModified);
    }

    public boolean isLockIsDeep()
    {
        return getSemanticObject().getBooleanProperty(jcr_lockIsDeep);
    }

    public void setLockIsDeep(boolean lockIsDeep)
    {
        getSemanticObject().setBooleanProperty(jcr_lockIsDeep, lockIsDeep);
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
        return getSemanticObject().getProperty(office_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(office_description, description);
    }
}
