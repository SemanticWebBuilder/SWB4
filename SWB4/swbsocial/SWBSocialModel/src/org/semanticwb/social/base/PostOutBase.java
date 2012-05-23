package org.semanticwb.social.base;


public abstract class PostOutBase extends org.semanticwb.social.Post implements org.semanticwb.model.Traceable,org.semanticwb.model.Tagable,org.semanticwb.model.Descriptiveable
{
   /**
   * Clase que contiene todos los post que han sido enviados por una determinada red social. La intención de crear esta clase es para que se agrupen los Post de cada red social por mes y año, y de esta manera sea mucho mas sencillo, optimo y rapido realizar las busquedas.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostContainer");
    public static final org.semanticwb.platform.SemanticProperty social_hasPostContainer_PostInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostContainer_PostInv");
    public static final org.semanticwb.platform.SemanticClass social_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostOut for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOuts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostOut for all models
       * @return Iterator of org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOuts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.PostOut
       * @param id Identifier for org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOut
       * @return A org.semanticwb.social.PostOut
       */
        public static org.semanticwb.social.PostOut getPostOut(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOut)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostOut
       * @param id Identifier for org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOut
       * @return A org.semanticwb.social.PostOut
       */
        public static org.semanticwb.social.PostOut createPostOut(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOut)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostOut
       * @param id Identifier for org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOut
       */
        public static void removePostOut(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostOut
       * @param id Identifier for org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOut
       * @return true if the org.semanticwb.social.PostOut exists, false otherwise
       */

        public static boolean hasPostOut(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostOut(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostContainer
       * @param model Model of the org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostContainer_PostInv(org.semanticwb.social.PostContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOut with a determined PostContainer_PostInv
       * @param value PostContainer_PostInv of the type org.semanticwb.social.PostContainer
       * @return Iterator with all the org.semanticwb.social.PostOut
       */

        public static java.util.Iterator<org.semanticwb.social.PostOut> listPostOutByPostContainer_PostInv(org.semanticwb.social.PostContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostContainer_PostInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a PostOutBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostOut
   */
    public PostOutBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
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

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
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
   /**
   * Gets all the org.semanticwb.social.PostContainer
   * @return A GenericIterator with all the org.semanticwb.social.PostContainer
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer> listPostContainer_PostInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostContainer>(getSemanticObject().listObjectProperties(social_hasPostContainer_PostInv));
    }

   /**
   * Gets true if has a PostContainer_PostInv
   * @param value org.semanticwb.social.PostContainer to verify
   * @return true if the org.semanticwb.social.PostContainer exists, false otherwise
   */
    public boolean hasPostContainer_PostInv(org.semanticwb.social.PostContainer value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostContainer_PostInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostContainer_PostInv
   * @return a org.semanticwb.social.PostContainer
   */
    public org.semanticwb.social.PostContainer getPostContainer_PostInv()
    {
         org.semanticwb.social.PostContainer ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostContainer_PostInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostContainer)obj.createGenericInstance();
         }
         return ret;
    }
}
