package org.semanticwb.social.base;


   /**
   * Esta clase solo sirve para agrupar clases de flujos en swbsocial, la idea es NUNCA instanciarla. 
   */
public abstract class SocialPFlowsBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Esta clase solo sirve para agrupar clases de flujos en swbsocial, la idea es NUNCA instanciarla.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlows=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlows");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlows");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialPFlows for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialPFlows
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlows> listSocialPFlowses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlows>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialPFlows for all models
       * @return Iterator of org.semanticwb.social.SocialPFlows
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlows> listSocialPFlowses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlows>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SocialPFlows
       * @param id Identifier for org.semanticwb.social.SocialPFlows
       * @param model Model of the org.semanticwb.social.SocialPFlows
       * @return A org.semanticwb.social.SocialPFlows
       */
        public static org.semanticwb.social.SocialPFlows getSocialPFlows(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialPFlows)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialPFlows
       * @param id Identifier for org.semanticwb.social.SocialPFlows
       * @param model Model of the org.semanticwb.social.SocialPFlows
       * @return A org.semanticwb.social.SocialPFlows
       */
        public static org.semanticwb.social.SocialPFlows createSocialPFlows(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialPFlows)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialPFlows
       * @param id Identifier for org.semanticwb.social.SocialPFlows
       * @param model Model of the org.semanticwb.social.SocialPFlows
       */
        public static void removeSocialPFlows(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialPFlows
       * @param id Identifier for org.semanticwb.social.SocialPFlows
       * @param model Model of the org.semanticwb.social.SocialPFlows
       * @return true if the org.semanticwb.social.SocialPFlows exists, false otherwise
       */

        public static boolean hasSocialPFlows(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialPFlows(id, model)!=null);
        }
    }

    public static SocialPFlowsBase.ClassMgr getSocialPFlowsClassMgr()
    {
        return new SocialPFlowsBase.ClassMgr();
    }

   /**
   * Constructs a SocialPFlowsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialPFlows
   */
    public SocialPFlowsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
