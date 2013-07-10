package org.semanticwb.social.base;


   /**
   * Acción específica mediante la cual se envía un mensaje por defecto a una o varias redes sociales seleccionadas 
   */
public abstract class SendPostBase extends org.semanticwb.social.ActionMsg implements org.semanticwb.model.Descriptiveable,org.semanticwb.social.PostImageable,org.semanticwb.social.SocialNetworkable,org.semanticwb.social.PostVideoable,org.semanticwb.social.PostDataable,org.semanticwb.model.Traceable,org.semanticwb.social.PostTextable
{
   /**
   * Acción específica mediante la cual se envía un mensaje por defecto a una o varias redes sociales seleccionadas
   */
    public static final org.semanticwb.platform.SemanticClass social_SendPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SendPost");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SendPost");

    public static class ClassMgr
    {
       /**
       * Returns a list of SendPost for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SendPost
       */

        public static java.util.Iterator<org.semanticwb.social.SendPost> listSendPosts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SendPost>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SendPost for all models
       * @return Iterator of org.semanticwb.social.SendPost
       */

        public static java.util.Iterator<org.semanticwb.social.SendPost> listSendPosts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SendPost>(it, true);
        }

        public static org.semanticwb.social.SendPost createSendPost(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SendPost.ClassMgr.createSendPost(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SendPost
       * @param id Identifier for org.semanticwb.social.SendPost
       * @param model Model of the org.semanticwb.social.SendPost
       * @return A org.semanticwb.social.SendPost
       */
        public static org.semanticwb.social.SendPost getSendPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SendPost)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SendPost
       * @param id Identifier for org.semanticwb.social.SendPost
       * @param model Model of the org.semanticwb.social.SendPost
       * @return A org.semanticwb.social.SendPost
       */
        public static org.semanticwb.social.SendPost createSendPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SendPost)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SendPost
       * @param id Identifier for org.semanticwb.social.SendPost
       * @param model Model of the org.semanticwb.social.SendPost
       */
        public static void removeSendPost(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SendPost
       * @param id Identifier for org.semanticwb.social.SendPost
       * @param model Model of the org.semanticwb.social.SendPost
       * @return true if the org.semanticwb.social.SendPost exists, false otherwise
       */

        public static boolean hasSendPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSendPost(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SendPost with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SendPost
       * @return Iterator with all the org.semanticwb.social.SendPost
       */

        public static java.util.Iterator<org.semanticwb.social.SendPost> listSendPostByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendPost> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SendPost with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SendPost
       */

        public static java.util.Iterator<org.semanticwb.social.SendPost> listSendPostByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendPost> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SendPost with a determined ActionRuleInv
       * @param value ActionRuleInv of the type org.semanticwb.social.SocialRule
       * @param model Model of the org.semanticwb.social.SendPost
       * @return Iterator with all the org.semanticwb.social.SendPost
       */

        public static java.util.Iterator<org.semanticwb.social.SendPost> listSendPostByActionRuleInv(org.semanticwb.social.SocialRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendPost> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_actionRuleInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SendPost with a determined ActionRuleInv
       * @param value ActionRuleInv of the type org.semanticwb.social.SocialRule
       * @return Iterator with all the org.semanticwb.social.SendPost
       */

        public static java.util.Iterator<org.semanticwb.social.SendPost> listSendPostByActionRuleInv(org.semanticwb.social.SocialRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendPost> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_actionRuleInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SendPost with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SendPost
       * @return Iterator with all the org.semanticwb.social.SendPost
       */

        public static java.util.Iterator<org.semanticwb.social.SendPost> listSendPostByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendPost> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SendPost with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SendPost
       */

        public static java.util.Iterator<org.semanticwb.social.SendPost> listSendPostByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendPost> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SendPost with a determined SocialNetworks
       * @param value SocialNetworks of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SendPost
       * @return Iterator with all the org.semanticwb.social.SendPost
       */

        public static java.util.Iterator<org.semanticwb.social.SendPost> listSendPostBySocialNetworks(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendPost> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworks, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SendPost with a determined SocialNetworks
       * @param value SocialNetworks of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SendPost
       */

        public static java.util.Iterator<org.semanticwb.social.SendPost> listSendPostBySocialNetworks(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SendPost> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasSocialNetworks,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SendPostBase.ClassMgr getSendPostClassMgr()
    {
        return new SendPostBase.ClassMgr();
    }

   /**
   * Constructs a SendPostBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SendPost
   */
    public SendPostBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Video property
* @return String with the Video
*/
    public String getVideo()
    {
        return getSemanticObject().getProperty(social_video);
    }

/**
* Sets the Video property
* @param value long with the Video
*/
    public void setVideo(String value)
    {
        getSemanticObject().setProperty(social_video, value);
    }

/**
* Gets the Category property
* @return String with the Category
*/
    public String getCategory()
    {
        return getSemanticObject().getProperty(social_category);
    }

/**
* Sets the Category property
* @param value long with the Category
*/
    public void setCategory(String value)
    {
        getSemanticObject().setProperty(social_category, value);
    }

    public java.util.Iterator<String> listPhotos()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(social_hasPhoto);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addPhoto(String value)
    {
        getSemanticObject().addLiteralProperty(social_hasPhoto, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllPhoto()
    {
        getSemanticObject().removeProperty(social_hasPhoto);
    }

    public void removePhoto(String value)
    {
        getSemanticObject().removeLiteralProperty(social_hasPhoto,new org.semanticwb.platform.SemanticLiteral(value));
    }
   /**
   * Gets all the org.semanticwb.social.SocialNetwork
   * @return A GenericIterator with all the org.semanticwb.social.SocialNetwork
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork> listSocialNetworkses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetwork>(getSemanticObject().listObjectProperties(social_hasSocialNetworks));
    }

   /**
   * Gets true if has a SocialNetworks
   * @param value org.semanticwb.social.SocialNetwork to verify
   * @return true if the org.semanticwb.social.SocialNetwork exists, false otherwise
   */
    public boolean hasSocialNetworks(org.semanticwb.social.SocialNetwork value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasSocialNetworks,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a SocialNetworks
   * @param value org.semanticwb.social.SocialNetwork to add
   */

    public void addSocialNetworks(org.semanticwb.social.SocialNetwork value)
    {
        getSemanticObject().addObjectProperty(social_hasSocialNetworks, value.getSemanticObject());
    }
   /**
   * Removes all the SocialNetworks
   */

    public void removeAllSocialNetworks()
    {
        getSemanticObject().removeProperty(social_hasSocialNetworks);
    }
   /**
   * Removes a SocialNetworks
   * @param value org.semanticwb.social.SocialNetwork to remove
   */

    public void removeSocialNetworks(org.semanticwb.social.SocialNetwork value)
    {
        getSemanticObject().removeObjectProperty(social_hasSocialNetworks,value.getSemanticObject());
    }

   /**
   * Gets the SocialNetworks
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getSocialNetworks()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasSocialNetworks);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the SocialSite
   * @return a instance of org.semanticwb.social.SocialSite
   */
    public org.semanticwb.social.SocialSite getSocialSite()
    {
        return (org.semanticwb.social.SocialSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
