package org.semanticwb.bsc.tracing.base;


public abstract class LessThanBase extends org.semanticwb.bsc.tracing.Operation implements org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.bsc.Help,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass bsc_LessThan=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#LessThan");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#LessThan");

    public static class ClassMgr
    {
       /**
       * Returns a list of LessThan for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.LessThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.LessThan> listLessThans(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.LessThan>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.LessThan for all models
       * @return Iterator of org.semanticwb.bsc.tracing.LessThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.LessThan> listLessThans()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.LessThan>(it, true);
        }

        public static org.semanticwb.bsc.tracing.LessThan createLessThan(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.LessThan.ClassMgr.createLessThan(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.LessThan
       * @param id Identifier for org.semanticwb.bsc.tracing.LessThan
       * @param model Model of the org.semanticwb.bsc.tracing.LessThan
       * @return A org.semanticwb.bsc.tracing.LessThan
       */
        public static org.semanticwb.bsc.tracing.LessThan getLessThan(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.LessThan)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.LessThan
       * @param id Identifier for org.semanticwb.bsc.tracing.LessThan
       * @param model Model of the org.semanticwb.bsc.tracing.LessThan
       * @return A org.semanticwb.bsc.tracing.LessThan
       */
        public static org.semanticwb.bsc.tracing.LessThan createLessThan(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.LessThan)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.LessThan
       * @param id Identifier for org.semanticwb.bsc.tracing.LessThan
       * @param model Model of the org.semanticwb.bsc.tracing.LessThan
       */
        public static void removeLessThan(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.LessThan
       * @param id Identifier for org.semanticwb.bsc.tracing.LessThan
       * @param model Model of the org.semanticwb.bsc.tracing.LessThan
       * @return true if the org.semanticwb.bsc.tracing.LessThan exists, false otherwise
       */

        public static boolean hasLessThan(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLessThan(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.LessThan with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.LessThan
       * @return Iterator with all the org.semanticwb.bsc.tracing.LessThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.LessThan> listLessThanByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.LessThan> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.LessThan with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.LessThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.LessThan> listLessThanByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.LessThan> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.LessThan with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.LessThan
       * @return Iterator with all the org.semanticwb.bsc.tracing.LessThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.LessThan> listLessThanByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.LessThan> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.LessThan with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.LessThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.LessThan> listLessThanByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.LessThan> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static LessThanBase.ClassMgr getLessThanClassMgr()
    {
        return new LessThanBase.ClassMgr();
    }

   /**
   * Constructs a LessThanBase with a SemanticObject
   * @param base The SemanticObject with the properties for the LessThan
   */
    public LessThanBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
