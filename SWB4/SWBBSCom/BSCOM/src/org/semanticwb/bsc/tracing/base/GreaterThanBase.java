package org.semanticwb.bsc.tracing.base;


public abstract class GreaterThanBase extends org.semanticwb.bsc.tracing.Operation implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass bsc_GreaterThan=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#GreaterThan");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#GreaterThan");

    public static class ClassMgr
    {
       /**
       * Returns a list of GreaterThan for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.GreaterThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.GreaterThan> listGreaterThans(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.GreaterThan>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.GreaterThan for all models
       * @return Iterator of org.semanticwb.bsc.tracing.GreaterThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.GreaterThan> listGreaterThans()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.GreaterThan>(it, true);
        }

        public static org.semanticwb.bsc.tracing.GreaterThan createGreaterThan(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.GreaterThan.ClassMgr.createGreaterThan(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.GreaterThan
       * @param id Identifier for org.semanticwb.bsc.tracing.GreaterThan
       * @param model Model of the org.semanticwb.bsc.tracing.GreaterThan
       * @return A org.semanticwb.bsc.tracing.GreaterThan
       */
        public static org.semanticwb.bsc.tracing.GreaterThan getGreaterThan(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.GreaterThan)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.GreaterThan
       * @param id Identifier for org.semanticwb.bsc.tracing.GreaterThan
       * @param model Model of the org.semanticwb.bsc.tracing.GreaterThan
       * @return A org.semanticwb.bsc.tracing.GreaterThan
       */
        public static org.semanticwb.bsc.tracing.GreaterThan createGreaterThan(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.GreaterThan)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.GreaterThan
       * @param id Identifier for org.semanticwb.bsc.tracing.GreaterThan
       * @param model Model of the org.semanticwb.bsc.tracing.GreaterThan
       */
        public static void removeGreaterThan(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.GreaterThan
       * @param id Identifier for org.semanticwb.bsc.tracing.GreaterThan
       * @param model Model of the org.semanticwb.bsc.tracing.GreaterThan
       * @return true if the org.semanticwb.bsc.tracing.GreaterThan exists, false otherwise
       */

        public static boolean hasGreaterThan(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGreaterThan(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.GreaterThan with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.GreaterThan
       * @return Iterator with all the org.semanticwb.bsc.tracing.GreaterThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.GreaterThan> listGreaterThanByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.GreaterThan> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.GreaterThan with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.GreaterThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.GreaterThan> listGreaterThanByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.GreaterThan> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.GreaterThan with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.GreaterThan
       * @return Iterator with all the org.semanticwb.bsc.tracing.GreaterThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.GreaterThan> listGreaterThanByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.GreaterThan> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.GreaterThan with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.GreaterThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.GreaterThan> listGreaterThanByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.GreaterThan> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static GreaterThanBase.ClassMgr getGreaterThanClassMgr()
    {
        return new GreaterThanBase.ClassMgr();
    }

   /**
   * Constructs a GreaterThanBase with a SemanticObject
   * @param base The SemanticObject with the properties for the GreaterThan
   */
    public GreaterThanBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
