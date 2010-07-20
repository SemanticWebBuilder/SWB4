package org.semanticwb.portal.resources.sem.documents.base;


public abstract class DocumentBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Iconable,org.semanticwb.model.Traceable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Sortable,org.semanticwb.model.Trashable,org.semanticwb.model.Rankable
{
    public static final org.semanticwb.platform.SemanticClass docs_Document=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://semanticwb.org/SWBDocuments#Document");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://semanticwb.org/SWBDocuments#Document");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.documents.Document> listDocuments(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.documents.Document>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.documents.Document> listDocuments()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.documents.Document>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.documents.Document getDocument(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.documents.Document)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.resources.sem.documents.Document createDocument(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.documents.Document)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeDocument(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasDocument(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDocument(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.documents.Document> listDocumentByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.documents.Document> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.documents.Document> listDocumentByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.documents.Document> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.documents.Document> listDocumentByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.documents.Document> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.documents.Document> listDocumentByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.documents.Document> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public DocumentBase(org.semanticwb.platform.SemanticObject base)
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

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

    public void setIconClass(String value)
    {
        getSemanticObject().setProperty(swb_iconClass, value);
    }

    public long getReviews()
    {
        return getSemanticObject().getLongProperty(swb_reviews);
    }

    public void setReviews(long value)
    {
        getSemanticObject().setLongProperty(swb_reviews, value);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public double getRank()
    {
        return getSemanticObject().getDoubleProperty(swb_rank);
    }

    public void setRank(double value)
    {
        getSemanticObject().setDoubleProperty(swb_rank, value);
    }

    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(swb_hidden);
    }

    public void setHidden(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_hidden, value);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
