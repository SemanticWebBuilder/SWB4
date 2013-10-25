package org.semanticwb.process.model.base;


public abstract class AssociationFlowBase extends org.semanticwb.process.model.ConnectionObject implements org.semanticwb.process.model.ConnectionObjectEditable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_AssociationFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AssociationFlow");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AssociationFlow");

    public static class ClassMgr
    {
       /**
       * Returns a list of AssociationFlow for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.AssociationFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlows(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.AssociationFlow for all models
       * @return Iterator of org.semanticwb.process.model.AssociationFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlows()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow>(it, true);
        }

        public static org.semanticwb.process.model.AssociationFlow createAssociationFlow(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.AssociationFlow.ClassMgr.createAssociationFlow(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.AssociationFlow
       * @param id Identifier for org.semanticwb.process.model.AssociationFlow
       * @param model Model of the org.semanticwb.process.model.AssociationFlow
       * @return A org.semanticwb.process.model.AssociationFlow
       */
        public static org.semanticwb.process.model.AssociationFlow getAssociationFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.AssociationFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.AssociationFlow
       * @param id Identifier for org.semanticwb.process.model.AssociationFlow
       * @param model Model of the org.semanticwb.process.model.AssociationFlow
       * @return A org.semanticwb.process.model.AssociationFlow
       */
        public static org.semanticwb.process.model.AssociationFlow createAssociationFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.AssociationFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.AssociationFlow
       * @param id Identifier for org.semanticwb.process.model.AssociationFlow
       * @param model Model of the org.semanticwb.process.model.AssociationFlow
       */
        public static void removeAssociationFlow(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.AssociationFlow
       * @param id Identifier for org.semanticwb.process.model.AssociationFlow
       * @param model Model of the org.semanticwb.process.model.AssociationFlow
       * @return true if the org.semanticwb.process.model.AssociationFlow exists, false otherwise
       */

        public static boolean hasAssociationFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAssociationFlow(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.AssociationFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.AssociationFlow
       * @return Iterator with all the org.semanticwb.process.model.AssociationFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AssociationFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.AssociationFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AssociationFlow with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.AssociationFlow
       * @return Iterator with all the org.semanticwb.process.model.AssociationFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AssociationFlow with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.AssociationFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AssociationFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.AssociationFlow
       * @return Iterator with all the org.semanticwb.process.model.AssociationFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AssociationFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.AssociationFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AssociationFlow with a determined Source
       * @param value Source of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.AssociationFlow
       * @return Iterator with all the org.semanticwb.process.model.AssociationFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowBySource(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_source, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AssociationFlow with a determined Source
       * @param value Source of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.AssociationFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowBySource(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_source,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AssociationFlow with a determined Target
       * @param value Target of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.AssociationFlow
       * @return Iterator with all the org.semanticwb.process.model.AssociationFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowByTarget(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_target, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AssociationFlow with a determined Target
       * @param value Target of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.AssociationFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.AssociationFlow> listAssociationFlowByTarget(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AssociationFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_target,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static AssociationFlowBase.ClassMgr getAssociationFlowClassMgr()
    {
        return new AssociationFlowBase.ClassMgr();
    }

   /**
   * Constructs a AssociationFlowBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AssociationFlow
   */
    public AssociationFlowBase(org.semanticwb.platform.SemanticObject base)
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
