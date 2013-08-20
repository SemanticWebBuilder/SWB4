package org.semanticwb.social.base;


   /**
   * Attributos extendidos para un usuario de la plataforma swbsocial 
   */
public abstract class SocialUserExtAttributesBase extends org.semanticwb.model.UserTypeDef 
{
   /**
   * Propiedad que indica si un determinado usuario puede o no eliminar mensajes, ya sea de entrada o de salida, es decir a donde tenga permisos mediante los filtros de administración
   */
    public static final org.semanticwb.platform.SemanticProperty social_userCanRemoveMsg=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#userCanRemoveMsg");
   /**
   * Indica si un usuario puede reevaluar los mensajes de entrada.
   */
    public static final org.semanticwb.platform.SemanticProperty social_userCanReValueMsg=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#userCanReValueMsg");
   /**
   * Indica si un usuario puede o no responder mensajes, en la pantalla que se encuentra que soporte esa opción.
   */
    public static final org.semanticwb.platform.SemanticProperty social_userCanRespondMsg=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#userCanRespondMsg");
    public static final org.semanticwb.platform.SemanticProperty social_userCanReTopicMsg=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#userCanReTopicMsg");
   /**
   * Attributos extendidos para un usuario de la plataforma swbsocial
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialUserExtAttributes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialUserExtAttributes");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialUserExtAttributes");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialUserExtAttributes for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialUserExtAttributes
       */

        public static java.util.Iterator<org.semanticwb.social.SocialUserExtAttributes> listSocialUserExtAttributeses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialUserExtAttributes>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialUserExtAttributes for all models
       * @return Iterator of org.semanticwb.social.SocialUserExtAttributes
       */

        public static java.util.Iterator<org.semanticwb.social.SocialUserExtAttributes> listSocialUserExtAttributeses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialUserExtAttributes>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SocialUserExtAttributes
       * @param id Identifier for org.semanticwb.social.SocialUserExtAttributes
       * @param model Model of the org.semanticwb.social.SocialUserExtAttributes
       * @return A org.semanticwb.social.SocialUserExtAttributes
       */
        public static org.semanticwb.social.SocialUserExtAttributes getSocialUserExtAttributes(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialUserExtAttributes)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialUserExtAttributes
       * @param id Identifier for org.semanticwb.social.SocialUserExtAttributes
       * @param model Model of the org.semanticwb.social.SocialUserExtAttributes
       * @return A org.semanticwb.social.SocialUserExtAttributes
       */
        public static org.semanticwb.social.SocialUserExtAttributes createSocialUserExtAttributes(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialUserExtAttributes)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialUserExtAttributes
       * @param id Identifier for org.semanticwb.social.SocialUserExtAttributes
       * @param model Model of the org.semanticwb.social.SocialUserExtAttributes
       */
        public static void removeSocialUserExtAttributes(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialUserExtAttributes
       * @param id Identifier for org.semanticwb.social.SocialUserExtAttributes
       * @param model Model of the org.semanticwb.social.SocialUserExtAttributes
       * @return true if the org.semanticwb.social.SocialUserExtAttributes exists, false otherwise
       */

        public static boolean hasSocialUserExtAttributes(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialUserExtAttributes(id, model)!=null);
        }
    }

    public static SocialUserExtAttributesBase.ClassMgr getSocialUserExtAttributesClassMgr()
    {
        return new SocialUserExtAttributesBase.ClassMgr();
    }

   /**
   * Constructs a SocialUserExtAttributesBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialUserExtAttributes
   */
    public SocialUserExtAttributesBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the UserCanRemoveMsg property
* @return boolean with the UserCanRemoveMsg
*/
    public boolean isUserCanRemoveMsg()
    {
        return getSemanticObject().getBooleanProperty(social_userCanRemoveMsg);
    }

/**
* Sets the UserCanRemoveMsg property
* @param value long with the UserCanRemoveMsg
*/
    public void setUserCanRemoveMsg(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_userCanRemoveMsg, value);
    }

/**
* Gets the UserCanReValueMsg property
* @return boolean with the UserCanReValueMsg
*/
    public boolean isUserCanReValueMsg()
    {
        return getSemanticObject().getBooleanProperty(social_userCanReValueMsg);
    }

/**
* Sets the UserCanReValueMsg property
* @param value long with the UserCanReValueMsg
*/
    public void setUserCanReValueMsg(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_userCanReValueMsg, value);
    }

/**
* Gets the UserCanRespondMsg property
* @return boolean with the UserCanRespondMsg
*/
    public boolean isUserCanRespondMsg()
    {
        return getSemanticObject().getBooleanProperty(social_userCanRespondMsg);
    }

/**
* Sets the UserCanRespondMsg property
* @param value long with the UserCanRespondMsg
*/
    public void setUserCanRespondMsg(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_userCanRespondMsg, value);
    }

/**
* Gets the UserCanReTopicMsg property
* @return boolean with the UserCanReTopicMsg
*/
    public boolean isUserCanReTopicMsg()
    {
        return getSemanticObject().getBooleanProperty(social_userCanReTopicMsg);
    }

/**
* Sets the UserCanReTopicMsg property
* @param value long with the UserCanReTopicMsg
*/
    public void setUserCanReTopicMsg(boolean value)
    {
        getSemanticObject().setBooleanProperty(social_userCanReTopicMsg, value);
    }
}
