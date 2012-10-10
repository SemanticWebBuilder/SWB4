package org.semanticwb.domotic.model.base;


public abstract class TwitterActionBase extends org.semanticwb.domotic.model.DomAction 
{
    public static final org.semanticwb.platform.SemanticClass swb4d_TwitterAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#TwitterAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#TwitterAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of TwitterAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.TwitterAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.TwitterAction> listTwitterActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.TwitterAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.TwitterAction for all models
       * @return Iterator of org.semanticwb.domotic.model.TwitterAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.TwitterAction> listTwitterActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.TwitterAction>(it, true);
        }

        public static org.semanticwb.domotic.model.TwitterAction createTwitterAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.TwitterAction.ClassMgr.createTwitterAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.TwitterAction
       * @param id Identifier for org.semanticwb.domotic.model.TwitterAction
       * @param model Model of the org.semanticwb.domotic.model.TwitterAction
       * @return A org.semanticwb.domotic.model.TwitterAction
       */
        public static org.semanticwb.domotic.model.TwitterAction getTwitterAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.TwitterAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.TwitterAction
       * @param id Identifier for org.semanticwb.domotic.model.TwitterAction
       * @param model Model of the org.semanticwb.domotic.model.TwitterAction
       * @return A org.semanticwb.domotic.model.TwitterAction
       */
        public static org.semanticwb.domotic.model.TwitterAction createTwitterAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.TwitterAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.TwitterAction
       * @param id Identifier for org.semanticwb.domotic.model.TwitterAction
       * @param model Model of the org.semanticwb.domotic.model.TwitterAction
       */
        public static void removeTwitterAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.TwitterAction
       * @param id Identifier for org.semanticwb.domotic.model.TwitterAction
       * @param model Model of the org.semanticwb.domotic.model.TwitterAction
       * @return true if the org.semanticwb.domotic.model.TwitterAction exists, false otherwise
       */

        public static boolean hasTwitterAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTwitterAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.TwitterAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.TwitterAction
       * @return Iterator with all the org.semanticwb.domotic.model.TwitterAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.TwitterAction> listTwitterActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.TwitterAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEvent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.TwitterAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.TwitterAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.TwitterAction> listTwitterActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.TwitterAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEvent,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TwitterActionBase.ClassMgr getTwitterActionClassMgr()
    {
        return new TwitterActionBase.ClassMgr();
    }

   /**
   * Constructs a TwitterActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TwitterAction
   */
    public TwitterActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
