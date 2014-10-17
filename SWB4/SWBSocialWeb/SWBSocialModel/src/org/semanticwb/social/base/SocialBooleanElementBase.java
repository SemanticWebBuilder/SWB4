package org.semanticwb.social.base;


public abstract class SocialBooleanElementBase extends org.semanticwb.model.BooleanElement 
{
    public static final org.semanticwb.platform.SemanticProperty social_socialnets=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#socialnets");
    public static final org.semanticwb.platform.SemanticClass social_SocialBooleanElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialBooleanElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialBooleanElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialBooleanElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialBooleanElement
       */

        public static java.util.Iterator<org.semanticwb.social.SocialBooleanElement> listSocialBooleanElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialBooleanElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialBooleanElement for all models
       * @return Iterator of org.semanticwb.social.SocialBooleanElement
       */

        public static java.util.Iterator<org.semanticwb.social.SocialBooleanElement> listSocialBooleanElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialBooleanElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SocialBooleanElement
       * @param id Identifier for org.semanticwb.social.SocialBooleanElement
       * @param model Model of the org.semanticwb.social.SocialBooleanElement
       * @return A org.semanticwb.social.SocialBooleanElement
       */
        public static org.semanticwb.social.SocialBooleanElement getSocialBooleanElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialBooleanElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialBooleanElement
       * @param id Identifier for org.semanticwb.social.SocialBooleanElement
       * @param model Model of the org.semanticwb.social.SocialBooleanElement
       * @return A org.semanticwb.social.SocialBooleanElement
       */
        public static org.semanticwb.social.SocialBooleanElement createSocialBooleanElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialBooleanElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialBooleanElement
       * @param id Identifier for org.semanticwb.social.SocialBooleanElement
       * @param model Model of the org.semanticwb.social.SocialBooleanElement
       */
        public static void removeSocialBooleanElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialBooleanElement
       * @param id Identifier for org.semanticwb.social.SocialBooleanElement
       * @param model Model of the org.semanticwb.social.SocialBooleanElement
       * @return true if the org.semanticwb.social.SocialBooleanElement exists, false otherwise
       */

        public static boolean hasSocialBooleanElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialBooleanElement(id, model)!=null);
        }
    }

    public static SocialBooleanElementBase.ClassMgr getSocialBooleanElementClassMgr()
    {
        return new SocialBooleanElementBase.ClassMgr();
    }

   /**
   * Constructs a SocialBooleanElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialBooleanElement
   */
    public SocialBooleanElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Socialnets property
* @return String with the Socialnets
*/
    public String getSocialnets()
    {
        return getSemanticObject().getProperty(social_socialnets);
    }

/**
* Sets the Socialnets property
* @param value long with the Socialnets
*/
    public void setSocialnets(String value)
    {
        getSemanticObject().setProperty(social_socialnets, value);
    }
}
