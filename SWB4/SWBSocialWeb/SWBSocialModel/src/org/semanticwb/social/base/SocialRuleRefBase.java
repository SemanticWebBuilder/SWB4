package org.semanticwb.social.base;


public abstract class SocialRuleRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
   /**
   * Clase principal para manejo de reglas en swbSocial
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialRule");
    public static final org.semanticwb.platform.SemanticProperty social_socialRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#socialRule");
    public static final org.semanticwb.platform.SemanticClass social_SocialRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialRuleRef");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialRuleRef");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialRuleRef for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialRuleRef
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRuleRef> listSocialRuleRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRuleRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialRuleRef for all models
       * @return Iterator of org.semanticwb.social.SocialRuleRef
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRuleRef> listSocialRuleRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRuleRef>(it, true);
        }

        public static org.semanticwb.social.SocialRuleRef createSocialRuleRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialRuleRef.ClassMgr.createSocialRuleRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialRuleRef
       * @param id Identifier for org.semanticwb.social.SocialRuleRef
       * @param model Model of the org.semanticwb.social.SocialRuleRef
       * @return A org.semanticwb.social.SocialRuleRef
       */
        public static org.semanticwb.social.SocialRuleRef getSocialRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialRuleRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialRuleRef
       * @param id Identifier for org.semanticwb.social.SocialRuleRef
       * @param model Model of the org.semanticwb.social.SocialRuleRef
       * @return A org.semanticwb.social.SocialRuleRef
       */
        public static org.semanticwb.social.SocialRuleRef createSocialRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialRuleRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialRuleRef
       * @param id Identifier for org.semanticwb.social.SocialRuleRef
       * @param model Model of the org.semanticwb.social.SocialRuleRef
       */
        public static void removeSocialRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialRuleRef
       * @param id Identifier for org.semanticwb.social.SocialRuleRef
       * @param model Model of the org.semanticwb.social.SocialRuleRef
       * @return true if the org.semanticwb.social.SocialRuleRef exists, false otherwise
       */

        public static boolean hasSocialRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialRuleRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialRuleRef with a determined SocialRule
       * @param value SocialRule of the type org.semanticwb.social.SocialRule
       * @param model Model of the org.semanticwb.social.SocialRuleRef
       * @return Iterator with all the org.semanticwb.social.SocialRuleRef
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRuleRef> listSocialRuleRefBySocialRule(org.semanticwb.social.SocialRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRuleRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialRuleRef with a determined SocialRule
       * @param value SocialRule of the type org.semanticwb.social.SocialRule
       * @return Iterator with all the org.semanticwb.social.SocialRuleRef
       */

        public static java.util.Iterator<org.semanticwb.social.SocialRuleRef> listSocialRuleRefBySocialRule(org.semanticwb.social.SocialRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialRuleRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialRule,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialRuleRefBase.ClassMgr getSocialRuleRefClassMgr()
    {
        return new SocialRuleRefBase.ClassMgr();
    }

   /**
   * Constructs a SocialRuleRefBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialRuleRef
   */
    public SocialRuleRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property SocialRule
   * @param value SocialRule to set
   */

    public void setSocialRule(org.semanticwb.social.SocialRule value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_socialRule, value.getSemanticObject());
        }else
        {
            removeSocialRule();
        }
    }
   /**
   * Remove the value for SocialRule property
   */

    public void removeSocialRule()
    {
        getSemanticObject().removeProperty(social_socialRule);
    }

   /**
   * Gets the SocialRule
   * @return a org.semanticwb.social.SocialRule
   */
    public org.semanticwb.social.SocialRule getSocialRule()
    {
         org.semanticwb.social.SocialRule ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_socialRule);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialRule)obj.createGenericInstance();
         }
         return ret;
    }
}
