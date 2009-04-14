package org.semanticwb.repository.office.base;


public class OfficeDocumentBase extends org.semanticwb.repository.Resource implements org.semanticwb.repository.office.Traceable,org.semanticwb.repository.Referenceable
{
    public static final org.semanticwb.platform.SemanticProperty swboffice_file=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#file");
    public static final org.semanticwb.platform.SemanticProperty swboffice_user=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#user");
    public static final org.semanticwb.platform.SemanticClass swboffice_OfficeDocument=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeDocument");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeDocument");

    public OfficeDocumentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

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
        return (org.semanticwb.repository.office.OfficeDocument)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeOfficeDocument(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasOfficeDocument(String id, org.semanticwb.model.SWBModel model)
    {
        return (getOfficeDocument(id, model)!=null);
    }

    public String getFile()
    {
        return getSemanticObject().getProperty(swboffice_file);
    }

    public void setFile(String file)
    {
        getSemanticObject().setProperty(swboffice_file, file);
    }

    public String getUser()
    {
        return getSemanticObject().getProperty(swboffice_user);
    }

    public void setUser(String user)
    {
        getSemanticObject().setProperty(swboffice_user, user);
    }
}
