package org.semanticwb.bsc.tracing.base;


public abstract class ActionBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.FilterableNode,org.semanticwb.bsc.Help
{
    public static final org.semanticwb.platform.SemanticClass bsc_Action=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Action");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Action");

    public static class ClassMgr
    {
       /**
       * Returns a list of Action for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.Action
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Action> listActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Action>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.Action for all models
       * @return Iterator of org.semanticwb.bsc.tracing.Action
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Action> listActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Action>(it, true);
        }

        public static org.semanticwb.bsc.tracing.Action createAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.Action.ClassMgr.createAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.Action
       * @param id Identifier for org.semanticwb.bsc.tracing.Action
       * @param model Model of the org.semanticwb.bsc.tracing.Action
       * @return A org.semanticwb.bsc.tracing.Action
       */
        public static org.semanticwb.bsc.tracing.Action getAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Action)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.Action
       * @param id Identifier for org.semanticwb.bsc.tracing.Action
       * @param model Model of the org.semanticwb.bsc.tracing.Action
       * @return A org.semanticwb.bsc.tracing.Action
       */
        public static org.semanticwb.bsc.tracing.Action createAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Action)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.Action
       * @param id Identifier for org.semanticwb.bsc.tracing.Action
       * @param model Model of the org.semanticwb.bsc.tracing.Action
       */
        public static void removeAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.Action
       * @param id Identifier for org.semanticwb.bsc.tracing.Action
       * @param model Model of the org.semanticwb.bsc.tracing.Action
       * @return true if the org.semanticwb.bsc.tracing.Action exists, false otherwise
       */

        public static boolean hasAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Action with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Action
       * @return Iterator with all the org.semanticwb.bsc.tracing.Action
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Action> listActionByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Action> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Action with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Action
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Action> listActionByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Action> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Action with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Action
       * @return Iterator with all the org.semanticwb.bsc.tracing.Action
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Action> listActionByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Action> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Action with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Action
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Action> listActionByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Action> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ActionBase.ClassMgr getActionClassMgr()
    {
        return new ActionBase.ClassMgr();
    }

   /**
   * Constructs a ActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Action
   */
    public ActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
