package org.semanticwb.social.base;


public abstract class PhotoImgBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Propiedad que almacena el nombre(referencia) de la foto en un objeto Post que lo requiera.
   */
    public static final org.semanticwb.platform.SemanticProperty social_photo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#photo");
    public static final org.semanticwb.platform.SemanticClass social_PhotoImg=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PhotoImg");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PhotoImg");

    public static class ClassMgr
    {
       /**
       * Returns a list of PhotoImg for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PhotoImg
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoImg> listPhotoImgs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoImg>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PhotoImg for all models
       * @return Iterator of org.semanticwb.social.PhotoImg
       */

        public static java.util.Iterator<org.semanticwb.social.PhotoImg> listPhotoImgs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PhotoImg>(it, true);
        }

        public static org.semanticwb.social.PhotoImg createPhotoImg(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.PhotoImg.ClassMgr.createPhotoImg(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.PhotoImg
       * @param id Identifier for org.semanticwb.social.PhotoImg
       * @param model Model of the org.semanticwb.social.PhotoImg
       * @return A org.semanticwb.social.PhotoImg
       */
        public static org.semanticwb.social.PhotoImg getPhotoImg(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PhotoImg)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PhotoImg
       * @param id Identifier for org.semanticwb.social.PhotoImg
       * @param model Model of the org.semanticwb.social.PhotoImg
       * @return A org.semanticwb.social.PhotoImg
       */
        public static org.semanticwb.social.PhotoImg createPhotoImg(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PhotoImg)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PhotoImg
       * @param id Identifier for org.semanticwb.social.PhotoImg
       * @param model Model of the org.semanticwb.social.PhotoImg
       */
        public static void removePhotoImg(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PhotoImg
       * @param id Identifier for org.semanticwb.social.PhotoImg
       * @param model Model of the org.semanticwb.social.PhotoImg
       * @return true if the org.semanticwb.social.PhotoImg exists, false otherwise
       */

        public static boolean hasPhotoImg(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPhotoImg(id, model)!=null);
        }
    }

    public static PhotoImgBase.ClassMgr getPhotoImgClassMgr()
    {
        return new PhotoImgBase.ClassMgr();
    }

   /**
   * Constructs a PhotoImgBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PhotoImg
   */
    public PhotoImgBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Photo property
* @return String with the Photo
*/
    public String getPhoto()
    {
        return getSemanticObject().getProperty(social_photo);
    }

/**
* Sets the Photo property
* @param value long with the Photo
*/
    public void setPhoto(String value)
    {
        getSemanticObject().setProperty(social_photo, value);
    }
}
