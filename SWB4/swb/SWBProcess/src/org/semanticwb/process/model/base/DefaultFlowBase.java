package org.semanticwb.process.model.base;


public abstract class DefaultFlowBase extends org.semanticwb.process.model.SequenceFlow implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_DefaultFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DefaultFlow");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DefaultFlow");

    public static class ClassMgr
    {
       /**
       * Returns a list of DefaultFlow for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.DefaultFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlows(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultFlow>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.DefaultFlow for all models
       * @return Iterator of org.semanticwb.process.model.DefaultFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlows()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultFlow>(it, true);
        }

        public static org.semanticwb.process.model.DefaultFlow createDefaultFlow(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DefaultFlow.ClassMgr.createDefaultFlow(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.DefaultFlow
       * @param id Identifier for org.semanticwb.process.model.DefaultFlow
       * @param model Model of the org.semanticwb.process.model.DefaultFlow
       * @return A org.semanticwb.process.model.DefaultFlow
       */
        public static org.semanticwb.process.model.DefaultFlow getDefaultFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DefaultFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.DefaultFlow
       * @param id Identifier for org.semanticwb.process.model.DefaultFlow
       * @param model Model of the org.semanticwb.process.model.DefaultFlow
       * @return A org.semanticwb.process.model.DefaultFlow
       */
        public static org.semanticwb.process.model.DefaultFlow createDefaultFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DefaultFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.DefaultFlow
       * @param id Identifier for org.semanticwb.process.model.DefaultFlow
       * @param model Model of the org.semanticwb.process.model.DefaultFlow
       */
        public static void removeDefaultFlow(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.DefaultFlow
       * @param id Identifier for org.semanticwb.process.model.DefaultFlow
       * @param model Model of the org.semanticwb.process.model.DefaultFlow
       * @return true if the org.semanticwb.process.model.DefaultFlow exists, false otherwise
       */

        public static boolean hasDefaultFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDefaultFlow(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.DefaultFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.DefaultFlow
       * @return Iterator with all the org.semanticwb.process.model.DefaultFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlowByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DefaultFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.DefaultFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlowByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DefaultFlow with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.DefaultFlow
       * @return Iterator with all the org.semanticwb.process.model.DefaultFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlowByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DefaultFlow with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.DefaultFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlowByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DefaultFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.DefaultFlow
       * @return Iterator with all the org.semanticwb.process.model.DefaultFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlowByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DefaultFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.DefaultFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlowByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DefaultFlow with a determined Source
       * @param value Source of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.DefaultFlow
       * @return Iterator with all the org.semanticwb.process.model.DefaultFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlowBySource(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_source, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DefaultFlow with a determined Source
       * @param value Source of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.DefaultFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlowBySource(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_source,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DefaultFlow with a determined Target
       * @param value Target of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.DefaultFlow
       * @return Iterator with all the org.semanticwb.process.model.DefaultFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlowByTarget(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_target, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DefaultFlow with a determined Target
       * @param value Target of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.DefaultFlow
       */

        public static java.util.Iterator<org.semanticwb.process.model.DefaultFlow> listDefaultFlowByTarget(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DefaultFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_target,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DefaultFlowBase.ClassMgr getDefaultFlowClassMgr()
    {
        return new DefaultFlowBase.ClassMgr();
    }

   /**
   * Constructs a DefaultFlowBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DefaultFlow
   */
    public DefaultFlowBase(org.semanticwb.platform.SemanticObject base)
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
