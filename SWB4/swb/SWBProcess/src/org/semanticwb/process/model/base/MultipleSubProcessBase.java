package org.semanticwb.process.model.base;


public abstract class MultipleSubProcessBase extends org.semanticwb.process.model.SubProcess implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.TemplateRefable,org.semanticwb.process.model.Containerable
{
    public static final org.semanticwb.platform.SemanticClass swp_MultipleSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleSubProcess");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#MultipleSubProcess");

    public static class ClassMgr
    {
       /**
       * Returns a list of MultipleSubProcess for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcesses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.MultipleSubProcess for all models
       * @return Iterator of org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcesses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess>(it, true);
        }

        public static org.semanticwb.process.model.MultipleSubProcess createMultipleSubProcess(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MultipleSubProcess.ClassMgr.createMultipleSubProcess(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.MultipleSubProcess
       * @param id Identifier for org.semanticwb.process.model.MultipleSubProcess
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return A org.semanticwb.process.model.MultipleSubProcess
       */
        public static org.semanticwb.process.model.MultipleSubProcess getMultipleSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultipleSubProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.MultipleSubProcess
       * @param id Identifier for org.semanticwb.process.model.MultipleSubProcess
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return A org.semanticwb.process.model.MultipleSubProcess
       */
        public static org.semanticwb.process.model.MultipleSubProcess createMultipleSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultipleSubProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.MultipleSubProcess
       * @param id Identifier for org.semanticwb.process.model.MultipleSubProcess
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       */
        public static void removeMultipleSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.MultipleSubProcess
       * @param id Identifier for org.semanticwb.process.model.MultipleSubProcess
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return true if the org.semanticwb.process.model.MultipleSubProcess exists, false otherwise
       */

        public static boolean hasMultipleSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMultipleSubProcess(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByContained(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByContained(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.MultipleSubProcess
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.MultipleSubProcess with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.MultipleSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.MultipleSubProcess> listMultipleSubProcessByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultipleSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a MultipleSubProcessBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MultipleSubProcess
   */
    public MultipleSubProcessBase(org.semanticwb.platform.SemanticObject base)
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
