package org.semanticwb.portal.community.base;


public abstract class DocumentResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_DocumentElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DocumentElement");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDocument=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDocument");
    public static final org.semanticwb.platform.SemanticClass swbcomm_DocumentResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DocumentResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DocumentResource");

    public DocumentResourceBase()
    {
    }

    public DocumentResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement> listDocuments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement>(getSemanticObject().listObjectProperties(swbcomm_hasDocument));
    }

    public boolean hasDocument(org.semanticwb.portal.community.DocumentElement documentelement)
    {
        boolean ret=false;
        if(documentelement!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasDocument,documentelement.getSemanticObject());
        }
        return ret;
    }

    public void addDocument(org.semanticwb.portal.community.DocumentElement value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasDocument, value.getSemanticObject());
    }

    public void removeAllDocument()
    {
        getSemanticObject().removeProperty(swbcomm_hasDocument);
    }

    public void removeDocument(org.semanticwb.portal.community.DocumentElement documentelement)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasDocument,documentelement.getSemanticObject());
    }

    public org.semanticwb.portal.community.DocumentElement getDocument()
    {
         org.semanticwb.portal.community.DocumentElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasDocument);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.DocumentElement)obj.createGenericInstance();
         }
         return ret;
    }
}
