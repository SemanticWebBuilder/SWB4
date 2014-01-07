package org.semanticwb.social.base;


   /**
   * Clase en la que se almacena la relación entre los PostOut enviados directamente a un usuario o usuarios en una determinada red social. 
   */
public abstract class PostOutDirectUserRelationBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Propiedad en la que se guardan los identificadores de usuarios de una red social especifica a la que haga referencia cada registro y a la cual se sevía el PostOut.
   */
    public static final org.semanticwb.platform.SemanticProperty social_podur_userIds=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#podur_userIds");
   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");
   /**
   * PostOut que se esta enviando
   */
    public static final org.semanticwb.platform.SemanticProperty social_podur_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#podur_PostOut");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * SocialNetwork al cual se envía el PostOut
   */
    public static final org.semanticwb.platform.SemanticProperty social_podur_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#podur_SocialNetwork");
   /**
   * Clase en la que se almacena la relación entre los PostOut enviados directamente a un usuario o usuarios en una determinada red social.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutDirectUserRelation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutDirectUserRelation");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutDirectUserRelation");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostOutDirectUserRelation for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostOutDirectUserRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutDirectUserRelation> listPostOutDirectUserRelations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutDirectUserRelation>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostOutDirectUserRelation for all models
       * @return Iterator of org.semanticwb.social.PostOutDirectUserRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutDirectUserRelation> listPostOutDirectUserRelations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutDirectUserRelation>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.PostOutDirectUserRelation
       * @param id Identifier for org.semanticwb.social.PostOutDirectUserRelation
       * @param model Model of the org.semanticwb.social.PostOutDirectUserRelation
       * @return A org.semanticwb.social.PostOutDirectUserRelation
       */
        public static org.semanticwb.social.PostOutDirectUserRelation getPostOutDirectUserRelation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutDirectUserRelation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostOutDirectUserRelation
       * @param id Identifier for org.semanticwb.social.PostOutDirectUserRelation
       * @param model Model of the org.semanticwb.social.PostOutDirectUserRelation
       * @return A org.semanticwb.social.PostOutDirectUserRelation
       */
        public static org.semanticwb.social.PostOutDirectUserRelation createPostOutDirectUserRelation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutDirectUserRelation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostOutDirectUserRelation
       * @param id Identifier for org.semanticwb.social.PostOutDirectUserRelation
       * @param model Model of the org.semanticwb.social.PostOutDirectUserRelation
       */
        public static void removePostOutDirectUserRelation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostOutDirectUserRelation
       * @param id Identifier for org.semanticwb.social.PostOutDirectUserRelation
       * @param model Model of the org.semanticwb.social.PostOutDirectUserRelation
       * @return true if the org.semanticwb.social.PostOutDirectUserRelation exists, false otherwise
       */

        public static boolean hasPostOutDirectUserRelation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostOutDirectUserRelation(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostOutDirectUserRelation with a determined Podur_PostOut
       * @param value Podur_PostOut of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOutDirectUserRelation
       * @return Iterator with all the org.semanticwb.social.PostOutDirectUserRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutDirectUserRelation> listPostOutDirectUserRelationByPodur_PostOut(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutDirectUserRelation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_podur_PostOut, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutDirectUserRelation with a determined Podur_PostOut
       * @param value Podur_PostOut of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOutDirectUserRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutDirectUserRelation> listPostOutDirectUserRelationByPodur_PostOut(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutDirectUserRelation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_podur_PostOut,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutDirectUserRelation with a determined Podur_SocialNetwork
       * @param value Podur_SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PostOutDirectUserRelation
       * @return Iterator with all the org.semanticwb.social.PostOutDirectUserRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutDirectUserRelation> listPostOutDirectUserRelationByPodur_SocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutDirectUserRelation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_podur_SocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutDirectUserRelation with a determined Podur_SocialNetwork
       * @param value Podur_SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PostOutDirectUserRelation
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutDirectUserRelation> listPostOutDirectUserRelationByPodur_SocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutDirectUserRelation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_podur_SocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostOutDirectUserRelationBase.ClassMgr getPostOutDirectUserRelationClassMgr()
    {
        return new PostOutDirectUserRelationBase.ClassMgr();
    }

   /**
   * Constructs a PostOutDirectUserRelationBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostOutDirectUserRelation
   */
    public PostOutDirectUserRelationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Podur_userIds property
* @return String with the Podur_userIds
*/
    public String getPodur_userIds()
    {
        return getSemanticObject().getProperty(social_podur_userIds);
    }

/**
* Sets the Podur_userIds property
* @param value long with the Podur_userIds
*/
    public void setPodur_userIds(String value)
    {
        getSemanticObject().setProperty(social_podur_userIds, value);
    }
   /**
   * Sets the value for the property Podur_PostOut
   * @param value Podur_PostOut to set
   */

    public void setPodur_PostOut(org.semanticwb.social.PostOut value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_podur_PostOut, value.getSemanticObject());
        }else
        {
            removePodur_PostOut();
        }
    }
   /**
   * Remove the value for Podur_PostOut property
   */

    public void removePodur_PostOut()
    {
        getSemanticObject().removeProperty(social_podur_PostOut);
    }

   /**
   * Gets the Podur_PostOut
   * @return a org.semanticwb.social.PostOut
   */
    public org.semanticwb.social.PostOut getPodur_PostOut()
    {
         org.semanticwb.social.PostOut ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_podur_PostOut);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOut)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Podur_SocialNetwork
   * @param value Podur_SocialNetwork to set
   */

    public void setPodur_SocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_podur_SocialNetwork, value.getSemanticObject());
        }else
        {
            removePodur_SocialNetwork();
        }
    }
   /**
   * Remove the value for Podur_SocialNetwork property
   */

    public void removePodur_SocialNetwork()
    {
        getSemanticObject().removeProperty(social_podur_SocialNetwork);
    }

   /**
   * Gets the Podur_SocialNetwork
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getPodur_SocialNetwork()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_podur_SocialNetwork);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }
}
