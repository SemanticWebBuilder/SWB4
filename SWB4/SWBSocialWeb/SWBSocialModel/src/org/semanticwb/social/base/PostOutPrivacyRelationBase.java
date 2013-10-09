package org.semanticwb.social.base;


   /**
   * Relación de Privacidad entre PostOut y las redes sociales a las cuales se envía. 
   */
public abstract class PostOutPrivacyRelationBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Clase de tipo catálogo que define las privacidades para los Post
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutPrivacy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutPrivacy");
   /**
   * Tipo de privacidad con la que se envía el post a la red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_popr_privacy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#popr_privacy");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * SocialNetwork a la cual se envia el postOut
   */
    public static final org.semanticwb.platform.SemanticProperty social_popr_socialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#popr_socialNetwork");
   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");
   /**
   * PostOut que se esta enviado.
   */
    public static final org.semanticwb.platform.SemanticProperty social_popr_postOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#popr_postOut");
   /**
   * Relación de Privacidad entre PostOut y las redes sociales a las cuales se envía.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutPrivacyRelation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutPrivacyRelation");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutPrivacyRelation");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostOutPrivacyRelation for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostOutPrivacyRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacyRelation> listPostOutPrivacyRelations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacyRelation>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostOutPrivacyRelation for all models
       * @return Iterator of org.semanticwb.social.PostOutPrivacyRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacyRelation> listPostOutPrivacyRelations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacyRelation>(it, true);
        }

        public static org.semanticwb.social.PostOutPrivacyRelation createPostOutPrivacyRelation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.PostOutPrivacyRelation.ClassMgr.createPostOutPrivacyRelation(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.PostOutPrivacyRelation
       * @param id Identifier for org.semanticwb.social.PostOutPrivacyRelation
       * @param model Model of the org.semanticwb.social.PostOutPrivacyRelation
       * @return A org.semanticwb.social.PostOutPrivacyRelation
       */
        public static org.semanticwb.social.PostOutPrivacyRelation getPostOutPrivacyRelation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutPrivacyRelation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostOutPrivacyRelation
       * @param id Identifier for org.semanticwb.social.PostOutPrivacyRelation
       * @param model Model of the org.semanticwb.social.PostOutPrivacyRelation
       * @return A org.semanticwb.social.PostOutPrivacyRelation
       */
        public static org.semanticwb.social.PostOutPrivacyRelation createPostOutPrivacyRelation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutPrivacyRelation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostOutPrivacyRelation
       * @param id Identifier for org.semanticwb.social.PostOutPrivacyRelation
       * @param model Model of the org.semanticwb.social.PostOutPrivacyRelation
       */
        public static void removePostOutPrivacyRelation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostOutPrivacyRelation
       * @param id Identifier for org.semanticwb.social.PostOutPrivacyRelation
       * @param model Model of the org.semanticwb.social.PostOutPrivacyRelation
       * @return true if the org.semanticwb.social.PostOutPrivacyRelation exists, false otherwise
       */

        public static boolean hasPostOutPrivacyRelation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostOutPrivacyRelation(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostOutPrivacyRelation with a determined Popr_privacy
       * @param value Popr_privacy of the type org.semanticwb.social.PostOutPrivacy
       * @param model Model of the org.semanticwb.social.PostOutPrivacyRelation
       * @return Iterator with all the org.semanticwb.social.PostOutPrivacyRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacyRelation> listPostOutPrivacyRelationByPopr_privacy(org.semanticwb.social.PostOutPrivacy value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacyRelation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_popr_privacy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutPrivacyRelation with a determined Popr_privacy
       * @param value Popr_privacy of the type org.semanticwb.social.PostOutPrivacy
       * @return Iterator with all the org.semanticwb.social.PostOutPrivacyRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacyRelation> listPostOutPrivacyRelationByPopr_privacy(org.semanticwb.social.PostOutPrivacy value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacyRelation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_popr_privacy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutPrivacyRelation with a determined Popr_socialNetwork
       * @param value Popr_socialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PostOutPrivacyRelation
       * @return Iterator with all the org.semanticwb.social.PostOutPrivacyRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacyRelation> listPostOutPrivacyRelationByPopr_socialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacyRelation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_popr_socialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutPrivacyRelation with a determined Popr_socialNetwork
       * @param value Popr_socialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PostOutPrivacyRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacyRelation> listPostOutPrivacyRelationByPopr_socialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacyRelation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_popr_socialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutPrivacyRelation with a determined Popr_postOut
       * @param value Popr_postOut of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOutPrivacyRelation
       * @return Iterator with all the org.semanticwb.social.PostOutPrivacyRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacyRelation> listPostOutPrivacyRelationByPopr_postOut(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacyRelation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_popr_postOut, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutPrivacyRelation with a determined Popr_postOut
       * @param value Popr_postOut of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOutPrivacyRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutPrivacyRelation> listPostOutPrivacyRelationByPopr_postOut(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutPrivacyRelation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_popr_postOut,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostOutPrivacyRelationBase.ClassMgr getPostOutPrivacyRelationClassMgr()
    {
        return new PostOutPrivacyRelationBase.ClassMgr();
    }

   /**
   * Constructs a PostOutPrivacyRelationBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostOutPrivacyRelation
   */
    public PostOutPrivacyRelationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Popr_privacy
   * @param value Popr_privacy to set
   */

    public void setPopr_privacy(org.semanticwb.social.PostOutPrivacy value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_popr_privacy, value.getSemanticObject());
        }else
        {
            removePopr_privacy();
        }
    }
   /**
   * Remove the value for Popr_privacy property
   */

    public void removePopr_privacy()
    {
        getSemanticObject().removeProperty(social_popr_privacy);
    }

   /**
   * Gets the Popr_privacy
   * @return a org.semanticwb.social.PostOutPrivacy
   */
    public org.semanticwb.social.PostOutPrivacy getPopr_privacy()
    {
         org.semanticwb.social.PostOutPrivacy ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_popr_privacy);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOutPrivacy)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Popr_socialNetwork
   * @param value Popr_socialNetwork to set
   */

    public void setPopr_socialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_popr_socialNetwork, value.getSemanticObject());
        }else
        {
            removePopr_socialNetwork();
        }
    }
   /**
   * Remove the value for Popr_socialNetwork property
   */

    public void removePopr_socialNetwork()
    {
        getSemanticObject().removeProperty(social_popr_socialNetwork);
    }

   /**
   * Gets the Popr_socialNetwork
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getPopr_socialNetwork()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_popr_socialNetwork);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Popr_postOut
   * @param value Popr_postOut to set
   */

    public void setPopr_postOut(org.semanticwb.social.PostOut value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_popr_postOut, value.getSemanticObject());
        }else
        {
            removePopr_postOut();
        }
    }
   /**
   * Remove the value for Popr_postOut property
   */

    public void removePopr_postOut()
    {
        getSemanticObject().removeProperty(social_popr_postOut);
    }

   /**
   * Gets the Popr_postOut
   * @return a org.semanticwb.social.PostOut
   */
    public org.semanticwb.social.PostOut getPopr_postOut()
    {
         org.semanticwb.social.PostOut ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_popr_postOut);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOut)obj.createGenericInstance();
         }
         return ret;
    }
}
