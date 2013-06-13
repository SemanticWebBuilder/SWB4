package org.semanticwb.social.base;


   /**
   * Categorias a llenar en una colección. 
   */
public abstract class YouTubeCategoryBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
   /**
   * Categorias a llenar en una colección.
   */
    public static final org.semanticwb.platform.SemanticClass social_YouTubeCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#YouTubeCategory");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#YouTubeCategory");

    public static class ClassMgr
    {
       /**
       * Returns a list of YouTubeCategory for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.YouTubeCategory
       */

        public static java.util.Iterator<org.semanticwb.social.YouTubeCategory> listYouTubeCategories(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTubeCategory>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.YouTubeCategory for all models
       * @return Iterator of org.semanticwb.social.YouTubeCategory
       */

        public static java.util.Iterator<org.semanticwb.social.YouTubeCategory> listYouTubeCategories()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.YouTubeCategory>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.YouTubeCategory
       * @param id Identifier for org.semanticwb.social.YouTubeCategory
       * @param model Model of the org.semanticwb.social.YouTubeCategory
       * @return A org.semanticwb.social.YouTubeCategory
       */
        public static org.semanticwb.social.YouTubeCategory getYouTubeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.YouTubeCategory)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.YouTubeCategory
       * @param id Identifier for org.semanticwb.social.YouTubeCategory
       * @param model Model of the org.semanticwb.social.YouTubeCategory
       * @return A org.semanticwb.social.YouTubeCategory
       */
        public static org.semanticwb.social.YouTubeCategory createYouTubeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.YouTubeCategory)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.YouTubeCategory
       * @param id Identifier for org.semanticwb.social.YouTubeCategory
       * @param model Model of the org.semanticwb.social.YouTubeCategory
       */
        public static void removeYouTubeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.YouTubeCategory
       * @param id Identifier for org.semanticwb.social.YouTubeCategory
       * @param model Model of the org.semanticwb.social.YouTubeCategory
       * @return true if the org.semanticwb.social.YouTubeCategory exists, false otherwise
       */

        public static boolean hasYouTubeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (getYouTubeCategory(id, model)!=null);
        }
    }

    public static YouTubeCategoryBase.ClassMgr getYouTubeCategoryClassMgr()
    {
        return new YouTubeCategoryBase.ClassMgr();
    }

   /**
   * Constructs a YouTubeCategoryBase with a SemanticObject
   * @param base The SemanticObject with the properties for the YouTubeCategory
   */
    public YouTubeCategoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }
}
