package org.semanticwb.process.model.base;


public abstract class CompensationSubProcessBase extends org.semanticwb.process.model.SubProcess implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.TemplateRefable,org.semanticwb.process.model.Containerable
{
    public static final org.semanticwb.platform.SemanticClass swp_CompensationSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationSubProcess");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CompensationSubProcess");

    public static class ClassMgr
    {
       /**
       * Returns a list of CompensationSubProcess for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcesses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.CompensationSubProcess for all models
       * @return Iterator of org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcesses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess>(it, true);
        }

        public static org.semanticwb.process.model.CompensationSubProcess createCompensationSubProcess(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CompensationSubProcess.ClassMgr.createCompensationSubProcess(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.CompensationSubProcess
       * @param id Identifier for org.semanticwb.process.model.CompensationSubProcess
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return A org.semanticwb.process.model.CompensationSubProcess
       */
        public static org.semanticwb.process.model.CompensationSubProcess getCompensationSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CompensationSubProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.CompensationSubProcess
       * @param id Identifier for org.semanticwb.process.model.CompensationSubProcess
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return A org.semanticwb.process.model.CompensationSubProcess
       */
        public static org.semanticwb.process.model.CompensationSubProcess createCompensationSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CompensationSubProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.CompensationSubProcess
       * @param id Identifier for org.semanticwb.process.model.CompensationSubProcess
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       */
        public static void removeCompensationSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.CompensationSubProcess
       * @param id Identifier for org.semanticwb.process.model.CompensationSubProcess
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return true if the org.semanticwb.process.model.CompensationSubProcess exists, false otherwise
       */

        public static boolean hasCompensationSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCompensationSubProcess(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByContained(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByContained(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CompensationSubProcess
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CompensationSubProcess with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CompensationSubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.CompensationSubProcess> listCompensationSubProcessByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CompensationSubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a CompensationSubProcessBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CompensationSubProcess
   */
    public CompensationSubProcessBase(org.semanticwb.platform.SemanticObject base)
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
