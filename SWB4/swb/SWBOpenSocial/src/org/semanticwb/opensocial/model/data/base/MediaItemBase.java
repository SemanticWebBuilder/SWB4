package org.semanticwb.opensocial.model.data.base;


public abstract class MediaItemBase extends org.semanticwb.model.base.GenericObjectBase 
{
   /**
   * Indica si el elemento es válido
   */
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticClass data_MediaItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#MediaItem");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#MediaItem");

    public static class ClassMgr
    {
       /**
       * Returns a list of MediaItem for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.opensocial.model.data.MediaItem
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.MediaItem> listMediaItems(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.MediaItem>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.opensocial.model.data.MediaItem for all models
       * @return Iterator of org.semanticwb.opensocial.model.data.MediaItem
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.MediaItem> listMediaItems()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.MediaItem>(it, true);
        }
       /**
       * Gets a org.semanticwb.opensocial.model.data.MediaItem
       * @param id Identifier for org.semanticwb.opensocial.model.data.MediaItem
       * @param model Model of the org.semanticwb.opensocial.model.data.MediaItem
       * @return A org.semanticwb.opensocial.model.data.MediaItem
       */
        public static org.semanticwb.opensocial.model.data.MediaItem getMediaItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.MediaItem)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.opensocial.model.data.MediaItem
       * @param id Identifier for org.semanticwb.opensocial.model.data.MediaItem
       * @param model Model of the org.semanticwb.opensocial.model.data.MediaItem
       * @return A org.semanticwb.opensocial.model.data.MediaItem
       */
        public static org.semanticwb.opensocial.model.data.MediaItem createMediaItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.MediaItem)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.opensocial.model.data.MediaItem
       * @param id Identifier for org.semanticwb.opensocial.model.data.MediaItem
       * @param model Model of the org.semanticwb.opensocial.model.data.MediaItem
       */
        public static void removeMediaItem(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.opensocial.model.data.MediaItem
       * @param id Identifier for org.semanticwb.opensocial.model.data.MediaItem
       * @param model Model of the org.semanticwb.opensocial.model.data.MediaItem
       * @return true if the org.semanticwb.opensocial.model.data.MediaItem exists, false otherwise
       */

        public static boolean hasMediaItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMediaItem(id, model)!=null);
        }
    }

   /**
   * Constructs a MediaItemBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MediaItem
   */
    public MediaItemBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Valid property
* @return boolean with the Valid
*/
    public boolean isValid()
    {
        //Override this method in MediaItem object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

/**
* Sets the Valid property
* @param value long with the Valid
*/
    public void setValid(boolean value)
    {
        //Override this method in MediaItem object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
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
