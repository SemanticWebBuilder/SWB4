package org.semanticwb.portal.resources.sem.news.base;


public abstract class SWBNewsBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swb_ResourceCollection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollection");
    public static final org.semanticwb.platform.SemanticProperty swbnews_collection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#collection");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceCollectionCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceCollectionCategory");
    public static final org.semanticwb.platform.SemanticProperty swbnews_category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#category");
    public static final org.semanticwb.platform.SemanticProperty swbnews_mobile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbnews#mobile");
    public static final org.semanticwb.platform.SemanticClass swbnews_SWBNews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbnews#SWBNews");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbnews#SWBNews");

    public SWBNewsBase()
    {
    }

   /**
   * Constructs a SWBNewsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWBNews
   */
    public SWBNewsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
        return getSemanticObject().hashCode();
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
        if(obj==null)return false;
        return hashCode()==obj.hashCode();
    }
   /**
   * Sets the value for the property Collection
   * @param value Collection to set
   */

    public void setCollection(org.semanticwb.model.ResourceCollection value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swbnews_collection, value.getSemanticObject());
        }else
        {
            removeCollection();
        }
    }
   /**
   * Remove the value for Collection property
   */

    public void removeCollection()
    {
        getSemanticObject().removeProperty(swbnews_collection);
    }

   /**
   * Gets the Collection
   * @return a org.semanticwb.model.ResourceCollection
   */
    public org.semanticwb.model.ResourceCollection getCollection()
    {
         org.semanticwb.model.ResourceCollection ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbnews_collection);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceCollection)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Resource
   * @param value Resource to set
   */

    public void setResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_semanticResourceInv, value.getSemanticObject());
        }else
        {
            removeResource();
        }
    }
   /**
   * Remove the value for Resource property
   */

    public void removeResource()
    {
        getSemanticObject().removeProperty(swb_semanticResourceInv);
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_semanticResourceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Category
   * @param value Category to set
   */

    public void setCategory(org.semanticwb.model.ResourceCollectionCategory value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swbnews_category, value.getSemanticObject());
        }else
        {
            removeCategory();
        }
    }
   /**
   * Remove the value for Category property
   */

    public void removeCategory()
    {
        getSemanticObject().removeProperty(swbnews_category);
    }

   /**
   * Gets the Category
   * @return a org.semanticwb.model.ResourceCollectionCategory
   */
    public org.semanticwb.model.ResourceCollectionCategory getCategory()
    {
         org.semanticwb.model.ResourceCollectionCategory ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbnews_category);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceCollectionCategory)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Mobile property
* @return boolean with the Mobile
*/
    public boolean isMobile()
    {
        return getSemanticObject().getBooleanProperty(swbnews_mobile);
    }

/**
* Sets the Mobile property
* @param value long with the Mobile
*/
    public void setMobile(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbnews_mobile, value);
    }
}
