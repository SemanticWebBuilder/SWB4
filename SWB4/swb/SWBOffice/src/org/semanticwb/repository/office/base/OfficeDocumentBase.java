package org.semanticwb.repository.office.base;


public abstract class OfficeDocumentBase extends org.semanticwb.repository.Resource implements org.semanticwb.repository.Referenceable,org.semanticwb.repository.office.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swboffice_file=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#file");
    public static final org.semanticwb.platform.SemanticClass swboffice_OfficeDocument=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeDocument");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeDocument");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeDocument> listOfficeDocuments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeDocument>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeDocument> listOfficeDocuments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeDocument>(it, true);
        }

        public static org.semanticwb.repository.office.OfficeDocument getOfficeDocument(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.office.OfficeDocument)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.repository.office.OfficeDocument createOfficeDocument(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.office.OfficeDocument)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeOfficeDocument(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasOfficeDocument(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOfficeDocument(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeDocument> listOfficeDocumentByParent(org.semanticwb.repository.BaseNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeDocument> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbrep_parentNode, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeDocument> listOfficeDocumentByParent(org.semanticwb.repository.BaseNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeDocument> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbrep_parentNode,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeDocument> listOfficeDocumentByNode(org.semanticwb.repository.BaseNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeDocument> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbrep_hasNodes, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.office.OfficeDocument> listOfficeDocumentByNode(org.semanticwb.repository.BaseNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.office.OfficeDocument> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbrep_hasNodes,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public OfficeDocumentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getFile()
    {
        return getSemanticObject().getProperty(swboffice_file);
    }

    public void setFile(String value)
    {
        getSemanticObject().setProperty(swboffice_file, value);
    }

    public String getUser()
    {
        return getSemanticObject().getProperty(swboffice_user);
    }

    public void setUser(String value)
    {
        getSemanticObject().setProperty(swboffice_user, value);
    }
}
