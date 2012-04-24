package org.semanticwb.social.base;


public abstract class PhotoBase extends org.semanticwb.social.Post implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Tagable,org.semanticwb.model.Traceable,org.semanticwb.model.Expirable,org.semanticwb.social.Commentable
{
    public static final org.semanticwb.platform.SemanticProperty social_photo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#photo");
    public static final org.semanticwb.platform.SemanticClass social_Photo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Photo");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Photo");

    public static class ClassMgr
    {
       /**
       * Returns a list of Photo for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotos(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Photo for all models
       * @return Iterator of org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotos()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo>(it, true);
        }

        public static org.semanticwb.social.Photo createPhoto(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.Photo.ClassMgr.createPhoto(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.Photo
       * @param id Identifier for org.semanticwb.social.Photo
       * @param model Model of the org.semanticwb.social.Photo
       * @return A org.semanticwb.social.Photo
       */
        public static org.semanticwb.social.Photo getPhoto(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Photo)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Photo
       * @param id Identifier for org.semanticwb.social.Photo
       * @param model Model of the org.semanticwb.social.Photo
       * @return A org.semanticwb.social.Photo
       */
        public static org.semanticwb.social.Photo createPhoto(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Photo)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Photo
       * @param id Identifier for org.semanticwb.social.Photo
       * @param model Model of the org.semanticwb.social.Photo
       */
        public static void removePhoto(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Photo
       * @param id Identifier for org.semanticwb.social.Photo
       * @param model Model of the org.semanticwb.social.Photo
       * @return true if the org.semanticwb.social.Photo exists, false otherwise
       */

        public static boolean hasPhoto(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPhoto(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.Photo
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByPostContainer_PostInv(org.semanticwb.social.PostContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Photo with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostContainer
       * @return Iterator with all the org.semanticwb.social.Photo
       */

        public static java.util.Iterator<org.semanticwb.social.Photo> listPhotoByPostContainer_PostInv(org.semanticwb.social.PostContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Photo> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a PhotoBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Photo
   */
    public PhotoBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Tags property
* @return String with the Tags
*/
    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

/**
* Sets the Tags property
* @param value long with the Tags
*/
    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

    public String getTags(String lang)
    {
        return getSemanticObject().getProperty(swb_tags, null, lang);
    }

    public String getDisplayTags(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_tags, lang);
    }

    public void setTags(String tags, String lang)
    {
        getSemanticObject().setProperty(swb_tags, tags, lang);
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

/**
* Gets the Comment property
* @return String with the Comment
*/
    public String getComment()
    {
        return getSemanticObject().getProperty(social_comment);
    }

/**
* Sets the Comment property
* @param value long with the Comment
*/
    public void setComment(String value)
    {
        getSemanticObject().setProperty(social_comment, value);
    }
}
