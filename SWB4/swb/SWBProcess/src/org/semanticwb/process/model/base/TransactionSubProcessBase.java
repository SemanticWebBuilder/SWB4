package org.semanticwb.process.model.base;


public abstract class TransactionSubProcessBase extends org.semanticwb.process.model.SubProcess implements org.semanticwb.process.model.ActivityConfable,org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.RoleRefable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RuleRefable,org.semanticwb.process.model.ResourceAssignmentable,org.semanticwb.process.model.BPMNSerializable,org.semanticwb.process.model.Containerable
{
    public static final org.semanticwb.platform.SemanticClass swp_TransactionSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TransactionSubProcess");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TransactionSubProcess");

    public static class ClassMgr
    {
       /**
       * Returns a list of TransactionSubProcess for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcesses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.TransactionSubProcess for all models
       * @return Iterator of org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcesses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess>(it, true);
        }

        public static org.semanticwb.process.model.TransactionSubProcess createTransactionSubProcess(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.TransactionSubProcess.ClassMgr.createTransactionSubProcess(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.TransactionSubProcess
       * @param id Identifier for org.semanticwb.process.model.TransactionSubProcess
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return A org.semanticwb.process.model.TransactionSubProcess
       */
        public static org.semanticwb.process.model.TransactionSubProcess getTransactionSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TransactionSubProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.TransactionSubProcess
       * @param id Identifier for org.semanticwb.process.model.TransactionSubProcess
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return A org.semanticwb.process.model.TransactionSubProcess
       */
        public static org.semanticwb.process.model.TransactionSubProcess createTransactionSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TransactionSubProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.TransactionSubProcess
       * @param id Identifier for org.semanticwb.process.model.TransactionSubProcess
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       */
        public static void removeTransactionSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.TransactionSubProcess
       * @param id Identifier for org.semanticwb.process.model.TransactionSubProcess
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return true if the org.semanticwb.process.model.TransactionSubProcess exists, false otherwise
       */

        public static boolean hasTransactionSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTransactionSubProcess(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByContained(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByContained(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined LoopCharacteristics
       * @param value LoopCharacteristics of the type org.semanticwb.process.model.LoopCharacteristics
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined LoopCharacteristics
       * @param value LoopCharacteristics of the type org.semanticwb.process.model.LoopCharacteristics
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByLoopCharacteristics(org.semanticwb.process.model.LoopCharacteristics value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_loopCharacteristics,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.TransactionSubProcess
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TransactionSubProcess with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.TransactionSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.TransactionSubProcess> listTransactionSubProcessByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TransactionSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TransactionSubProcessBase.ClassMgr getTransactionSubProcessClassMgr()
    {
        return new TransactionSubProcessBase.ClassMgr();
    }

   /**
   * Constructs a TransactionSubProcessBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TransactionSubProcess
   */
    public TransactionSubProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

   /**
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
