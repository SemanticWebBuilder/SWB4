package org.semanticwb.repository.office.base;


public abstract class OfficeContentBase extends org.semanticwb.repository.File implements org.semanticwb.repository.Referenceable,org.semanticwb.repository.office.Descriptiveable,org.semanticwb.repository.Traceable,org.semanticwb.repository.Lockable
{
    public static final org.semanticwb.platform.SemanticProperty swboffice_officetype=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#officetype");
    public static final org.semanticwb.platform.SemanticProperty swboffice_lastModified=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#lastModified");
    public static final org.semanticwb.platform.SemanticClass swboffice_OfficeContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeContent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeContent");

    public static class ClassMgr
    {

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
            return (org.semanticwb.repository.office.OfficeContent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeOfficeContent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasOfficeContent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOfficeContent(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContentByNode(org.semanticwb.repository.BaseNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbrep_hasNodes, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContentByNode(org.semanticwb.repository.BaseNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbrep_hasNodes,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContentByParent(org.semanticwb.repository.BaseNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbrep_parentNode, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeContent> listOfficeContentByParent(org.semanticwb.repository.BaseNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeContent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbrep_parentNode,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public OfficeContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String value)
    {
        getSemanticObject().setProperty(jcr_uuid, value);
    }

    public String getOfficetype()
    {
        return getSemanticObject().getProperty(swboffice_officetype);
    }

    public void setOfficetype(String value)
    {
        getSemanticObject().setProperty(swboffice_officetype, value);
    }

    public boolean isLockIsDeep()
    {
        return getSemanticObject().getBooleanProperty(jcr_lockIsDeep);
    }

    public void setLockIsDeep(boolean value)
    {
        getSemanticObject().setBooleanProperty(jcr_lockIsDeep, value);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swboffice_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swboffice_title, value);
    }

    public java.util.Date getLastModified()
    {
        return getSemanticObject().getDateProperty(swboffice_lastModified);
    }

    public void setLastModified(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swboffice_lastModified, value);
    }

    public String getLockOwner()
    {
        return getSemanticObject().getProperty(jcr_lockOwner);
    }

    public void setLockOwner(String value)
    {
        getSemanticObject().setProperty(jcr_lockOwner, value);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swboffice_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swboffice_description, value);
    }
}
