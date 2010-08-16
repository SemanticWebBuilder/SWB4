package org.semanticwb.repository.office.base;


public abstract class OfficeCategoryBase extends org.semanticwb.repository.Folder implements org.semanticwb.repository.Referenceable,org.semanticwb.repository.office.Descriptiveable,org.semanticwb.repository.Traceable,org.semanticwb.repository.office.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swboffice_OfficeCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeCategory");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeCategory");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategories(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategories()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory>(it, true);
        }

        public static org.semanticwb.repository.office.OfficeCategory getOfficeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.office.OfficeCategory)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.repository.office.OfficeCategory createOfficeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.office.OfficeCategory)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeOfficeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasOfficeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOfficeCategory(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategoryByParent(org.semanticwb.repository.BaseNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbrep_parentNode, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategoryByParent(org.semanticwb.repository.BaseNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbrep_parentNode,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategoryByNode(org.semanticwb.repository.BaseNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbrep_hasNodes, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeCategory> listOfficeCategoryByNode(org.semanticwb.repository.BaseNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeCategory> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbrep_hasNodes,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public OfficeCategoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swboffice_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swboffice_title, value);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String value)
    {
        getSemanticObject().setProperty(jcr_uuid, value);
    }

    public String getUser()
    {
        return getSemanticObject().getProperty(swboffice_user);
    }

    public void setUser(String value)
    {
        getSemanticObject().setProperty(swboffice_user, value);
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
