package org.semanticwb.opensocial.model.data.base;


public abstract class AlbumBase extends org.semanticwb.model.base.GenericObjectBase implements org.semanticwb.model.Descriptiveable
{
   /**
   * Indica si el elemento es válido
   */
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticClass data_Album=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Album");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Album");

    public static class ClassMgr
    {
       /**
       * Returns a list of Album for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.opensocial.model.data.Album
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Album> listAlbums(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Album>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.opensocial.model.data.Album for all models
       * @return Iterator of org.semanticwb.opensocial.model.data.Album
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Album> listAlbums()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Album>(it, true);
        }

        public static org.semanticwb.opensocial.model.data.Album createAlbum(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.opensocial.model.data.Album.ClassMgr.createAlbum(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.opensocial.model.data.Album
       * @param id Identifier for org.semanticwb.opensocial.model.data.Album
       * @param model Model of the org.semanticwb.opensocial.model.data.Album
       * @return A org.semanticwb.opensocial.model.data.Album
       */
        public static org.semanticwb.opensocial.model.data.Album getAlbum(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.Album)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.opensocial.model.data.Album
       * @param id Identifier for org.semanticwb.opensocial.model.data.Album
       * @param model Model of the org.semanticwb.opensocial.model.data.Album
       * @return A org.semanticwb.opensocial.model.data.Album
       */
        public static org.semanticwb.opensocial.model.data.Album createAlbum(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.Album)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.opensocial.model.data.Album
       * @param id Identifier for org.semanticwb.opensocial.model.data.Album
       * @param model Model of the org.semanticwb.opensocial.model.data.Album
       */
        public static void removeAlbum(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.opensocial.model.data.Album
       * @param id Identifier for org.semanticwb.opensocial.model.data.Album
       * @param model Model of the org.semanticwb.opensocial.model.data.Album
       * @return true if the org.semanticwb.opensocial.model.data.Album exists, false otherwise
       */

        public static boolean hasAlbum(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAlbum(id, model)!=null);
        }
    }

   /**
   * Constructs a AlbumBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Album
   */
    public AlbumBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

/**
* Gets the Valid property
* @return boolean with the Valid
*/
    public boolean isValid()
    {
        //Override this method in Album object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

/**
* Sets the Valid property
* @param value long with the Valid
*/
    public void setValid(boolean value)
    {
        //Override this method in Album object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
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

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
