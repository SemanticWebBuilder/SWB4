package org.semanticwb.portal.resources.sem.documents.base;


public abstract class DocumentsBase extends org.semanticwb.portal.api.GenericSemResource implements org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticProperty swb_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#index");
    public static final org.semanticwb.platform.SemanticClass docs_Document=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://semanticwb.org/SWBDocuments#Document");
    public static final org.semanticwb.platform.SemanticProperty docs_hasDocument=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://semanticwb.org/SWBDocuments#hasDocument");
    public static final org.semanticwb.platform.SemanticClass docs_Documents=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://semanticwb.org/SWBDocuments#Documents");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://semanticwb.org/SWBDocuments#Documents");

    public DocumentsBase()
    {
    }

    public DocumentsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.documents.Document> listDocuments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.documents.Document>(getSemanticObject().listObjectProperties(docs_hasDocument));
    }

    public boolean hasDocument(org.semanticwb.portal.resources.sem.documents.Document value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(docs_hasDocument,value.getSemanticObject());
        }
        return ret;
    }

    public void addDocument(org.semanticwb.portal.resources.sem.documents.Document value)
    {
        getSemanticObject().addObjectProperty(docs_hasDocument, value.getSemanticObject());
    }

    public void removeAllDocument()
    {
        getSemanticObject().removeProperty(docs_hasDocument);
    }

    public void removeDocument(org.semanticwb.portal.resources.sem.documents.Document value)
    {
        getSemanticObject().removeObjectProperty(docs_hasDocument,value.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.documents.Document getDocument()
    {
         org.semanticwb.portal.resources.sem.documents.Document ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(docs_hasDocument);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.documents.Document)obj.createGenericInstance();
         }
         return ret;
    }
}
