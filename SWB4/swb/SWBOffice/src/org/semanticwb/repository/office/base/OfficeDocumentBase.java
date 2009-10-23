package org.semanticwb.repository.office.base;


public class OfficeDocumentBase extends org.semanticwb.repository.Resource implements org.semanticwb.repository.office.Traceable,org.semanticwb.repository.Referenceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass nt_BaseNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
       public static final org.semanticwb.platform.SemanticProperty swbrep_parentNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#parentNode");
       public static final org.semanticwb.platform.SemanticProperty jcr_primaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#primaryType");
       public static final org.semanticwb.platform.SemanticProperty swboffice_file=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#file");
       public static final org.semanticwb.platform.SemanticProperty jcr_mimeType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mimeType");
       public static final org.semanticwb.platform.SemanticProperty jcr_encoding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#encoding");
       public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
       public static final org.semanticwb.platform.SemanticProperty jcr_lastModified=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#lastModified");
       public static final org.semanticwb.platform.SemanticProperty swbrep_path=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#path");
       public static final org.semanticwb.platform.SemanticProperty swbrep_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#name");
       public static final org.semanticwb.platform.SemanticProperty swboffice_user=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/office#user");
       public static final org.semanticwb.platform.SemanticProperty swbrep_hasNodes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#hasNodes");
       public static final org.semanticwb.platform.SemanticProperty jcr_data=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#data");
       public static final org.semanticwb.platform.SemanticProperty jcr_mixinTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mixinTypes");
       public static final org.semanticwb.platform.SemanticClass swboffice_OfficeDocument=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeDocument");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/office#OfficeDocument");

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
    }

    public OfficeDocumentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getFile()
    {
        return getSemanticObject().getProperty(ClassMgr.swboffice_file);
    }

    public void setFile(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swboffice_file, value);
    }

    public String getUser()
    {
        return getSemanticObject().getProperty(ClassMgr.swboffice_user);
    }

    public void setUser(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swboffice_user, value);
    }
}
