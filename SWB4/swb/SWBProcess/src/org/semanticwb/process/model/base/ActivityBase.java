package org.semanticwb.process.model.base;


public abstract class ActivityBase extends org.semanticwb.process.model.FlowNode implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Activity");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Activity");

    public static class ClassMgr
    {
       /**
       * Returns a list of Activity for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.Activity for all models
       * @return Iterator of org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.Activity
       * @param id Identifier for org.semanticwb.process.model.Activity
       * @param model Model of the org.semanticwb.process.model.Activity
       * @return A org.semanticwb.process.model.Activity
       */
        public static org.semanticwb.process.model.Activity getActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Activity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.Activity
       * @param id Identifier for org.semanticwb.process.model.Activity
       * @param model Model of the org.semanticwb.process.model.Activity
       * @return A org.semanticwb.process.model.Activity
       */
        public static org.semanticwb.process.model.Activity createActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Activity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.Activity
       * @param id Identifier for org.semanticwb.process.model.Activity
       * @param model Model of the org.semanticwb.process.model.Activity
       */
        public static void removeActivity(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.Activity
       * @param id Identifier for org.semanticwb.process.model.Activity
       * @param model Model of the org.semanticwb.process.model.Activity
       * @return true if the org.semanticwb.process.model.Activity exists, false otherwise
       */

        public static boolean hasActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActivity(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.Activity
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.Activity
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Activity
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.Activity
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.Activity
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.Activity
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Activity
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.Activity
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Activity with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a ActivityBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Activity
   */
    public ActivityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
