package org.semanticwb.portal.resources.sem.videolibrary.base;


public abstract class SWBVideoLibraryBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swb_ResourceCollectionCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollectionCategory");
    public static final org.semanticwb.platform.SemanticProperty video_category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#category");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceCollection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollection");
    public static final org.semanticwb.platform.SemanticProperty video_collection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/videoLibrary#collection");
    public static final org.semanticwb.platform.SemanticClass video_SWBVideoLibrary=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/videoLibrary#SWBVideoLibrary");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/videoLibrary#SWBVideoLibrary");

    public SWBVideoLibraryBase()
    {
    }

    public SWBVideoLibraryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setCategory(org.semanticwb.model.ResourceCollectionCategory value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(video_category, value.getSemanticObject());
        }else
        {
            removeCategory();
        }
    }

    public void removeCategory()
    {
        getSemanticObject().removeProperty(video_category);
    }

    public org.semanticwb.model.ResourceCollectionCategory getCategory()
    {
         org.semanticwb.model.ResourceCollectionCategory ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(video_category);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceCollectionCategory)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCollection(org.semanticwb.model.ResourceCollection value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(video_collection, value.getSemanticObject());
        }else
        {
            removeCollection();
        }
    }

    public void removeCollection()
    {
        getSemanticObject().removeProperty(video_collection);
    }

    public org.semanticwb.model.ResourceCollection getCollection()
    {
         org.semanticwb.model.ResourceCollection ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(video_collection);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceCollection)obj.createGenericInstance();
         }
         return ret;
    }
}
