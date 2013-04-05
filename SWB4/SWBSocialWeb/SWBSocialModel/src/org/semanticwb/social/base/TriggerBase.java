package org.semanticwb.social.base;


public abstract class TriggerBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass social_Trigger=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Trigger");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Trigger");

    public static class ClassMgr
    {
       /**
       * Returns a list of Trigger for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Trigger
       */

        public static java.util.Iterator<org.semanticwb.social.Trigger> listTriggers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Trigger>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Trigger for all models
       * @return Iterator of org.semanticwb.social.Trigger
       */

        public static java.util.Iterator<org.semanticwb.social.Trigger> listTriggers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Trigger>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.Trigger
       * @param id Identifier for org.semanticwb.social.Trigger
       * @param model Model of the org.semanticwb.social.Trigger
       * @return A org.semanticwb.social.Trigger
       */
        public static org.semanticwb.social.Trigger getTrigger(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Trigger)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Trigger
       * @param id Identifier for org.semanticwb.social.Trigger
       * @param model Model of the org.semanticwb.social.Trigger
       * @return A org.semanticwb.social.Trigger
       */
        public static org.semanticwb.social.Trigger createTrigger(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Trigger)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Trigger
       * @param id Identifier for org.semanticwb.social.Trigger
       * @param model Model of the org.semanticwb.social.Trigger
       */
        public static void removeTrigger(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Trigger
       * @param id Identifier for org.semanticwb.social.Trigger
       * @param model Model of the org.semanticwb.social.Trigger
       * @return true if the org.semanticwb.social.Trigger exists, false otherwise
       */

        public static boolean hasTrigger(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTrigger(id, model)!=null);
        }
    }

    public static TriggerBase.ClassMgr getTriggerClassMgr()
    {
        return new TriggerBase.ClassMgr();
    }

   /**
   * Constructs a TriggerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Trigger
   */
    public TriggerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
