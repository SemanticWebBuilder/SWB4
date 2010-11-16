package org.semanticwb.process.model.base;


public abstract class LoopSubProcessBase extends org.semanticwb.process.model.SubProcess implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.TemplateRefable,org.semanticwb.process.model.Containerable
{
    public static final org.semanticwb.platform.SemanticClass swp_LoopSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#LoopSubProcess");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#LoopSubProcess");

    public static class ClassMgr
    {
       /**
       * Returns a list of LoopSubProcess for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcesses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.LoopSubProcess for all models
       * @return Iterator of org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcesses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess>(it, true);
        }

        public static org.semanticwb.process.model.LoopSubProcess createLoopSubProcess(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.LoopSubProcess.ClassMgr.createLoopSubProcess(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.LoopSubProcess
       * @param id Identifier for org.semanticwb.process.model.LoopSubProcess
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return A org.semanticwb.process.model.LoopSubProcess
       */
        public static org.semanticwb.process.model.LoopSubProcess getLoopSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.LoopSubProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.LoopSubProcess
       * @param id Identifier for org.semanticwb.process.model.LoopSubProcess
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return A org.semanticwb.process.model.LoopSubProcess
       */
        public static org.semanticwb.process.model.LoopSubProcess createLoopSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.LoopSubProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.LoopSubProcess
       * @param id Identifier for org.semanticwb.process.model.LoopSubProcess
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       */
        public static void removeLoopSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.LoopSubProcess
       * @param id Identifier for org.semanticwb.process.model.LoopSubProcess
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return true if the org.semanticwb.process.model.LoopSubProcess exists, false otherwise
       */

        public static boolean hasLoopSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLoopSubProcess(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByContained(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByContained(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.LoopSubProcess
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.LoopSubProcess with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.LoopSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.LoopSubProcess> listLoopSubProcessByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LoopSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a LoopSubProcessBase with a SemanticObject
   * @param base The SemanticObject with the properties for the LoopSubProcess
   */
    public LoopSubProcessBase(org.semanticwb.platform.SemanticObject base)
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
