package org.semanticwb.process.model.base;


public abstract class SequenceFlowBase extends org.semanticwb.process.model.ConnectionObject implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_SequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SequenceFlow");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SequenceFlow");

    public static class ClassMgr
    {
       /**
       * Returns a list of SequenceFlow for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.SequenceFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlows(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.SequenceFlow for all models
       * @return Iterator of org.semanticwb.process.model.SequenceFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlows()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow>(it, true);
        }

        public static org.semanticwb.process.model.SequenceFlow createSequenceFlow(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.SequenceFlow.ClassMgr.createSequenceFlow(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.SequenceFlow
       * @param id Identifier for org.semanticwb.process.model.SequenceFlow
       * @param model Model of the org.semanticwb.process.model.SequenceFlow
       * @return A org.semanticwb.process.model.SequenceFlow
       */
        public static org.semanticwb.process.model.SequenceFlow getSequenceFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SequenceFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.SequenceFlow
       * @param id Identifier for org.semanticwb.process.model.SequenceFlow
       * @param model Model of the org.semanticwb.process.model.SequenceFlow
       * @return A org.semanticwb.process.model.SequenceFlow
       */
        public static org.semanticwb.process.model.SequenceFlow createSequenceFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SequenceFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.SequenceFlow
       * @param id Identifier for org.semanticwb.process.model.SequenceFlow
       * @param model Model of the org.semanticwb.process.model.SequenceFlow
       */
        public static void removeSequenceFlow(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.SequenceFlow
       * @param id Identifier for org.semanticwb.process.model.SequenceFlow
       * @param model Model of the org.semanticwb.process.model.SequenceFlow
       * @return true if the org.semanticwb.process.model.SequenceFlow exists, false otherwise
       */

        public static boolean hasSequenceFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSequenceFlow(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.SequenceFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.SequenceFlow
       * @return Iterator with all the org.semanticwb.process.model.SequenceFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SequenceFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.SequenceFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SequenceFlow with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.SequenceFlow
       * @return Iterator with all the org.semanticwb.process.model.SequenceFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SequenceFlow with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.SequenceFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SequenceFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.SequenceFlow
       * @return Iterator with all the org.semanticwb.process.model.SequenceFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SequenceFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.SequenceFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SequenceFlow with a determined Source
       * @param value Source of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.SequenceFlow
       * @return Iterator with all the org.semanticwb.process.model.SequenceFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowBySource(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_source, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SequenceFlow with a determined Source
       * @param value Source of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.SequenceFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowBySource(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_source,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SequenceFlow with a determined Target
       * @param value Target of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.SequenceFlow
       * @return Iterator with all the org.semanticwb.process.model.SequenceFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByTarget(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_target, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SequenceFlow with a determined Target
       * @param value Target of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.SequenceFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByTarget(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_target,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SequenceFlowBase.ClassMgr getSequenceFlowClassMgr()
    {
        return new SequenceFlowBase.ClassMgr();
    }

   /**
   * Constructs a SequenceFlowBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SequenceFlow
   */
    public SequenceFlowBase(org.semanticwb.platform.SemanticObject base)
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
