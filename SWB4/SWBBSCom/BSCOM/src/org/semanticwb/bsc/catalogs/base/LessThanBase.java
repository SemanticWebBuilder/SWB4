package org.semanticwb.bsc.catalogs.base;


public abstract class LessThanBase extends org.semanticwb.bsc.catalogs.Operation implements org.semanticwb.model.Descriptiveable
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
       * @return Iterator of org.semanticwb.bsc.catalogs.LessThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.LessThan> listLessThans(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.LessThan>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.LessThan for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.LessThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.LessThan> listLessThans()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.LessThan>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.LessThan createLessThan(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.LessThan.ClassMgr.createLessThan(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.LessThan
       * @param id Identifier for org.semanticwb.bsc.catalogs.LessThan
       * @param model Model of the org.semanticwb.bsc.catalogs.LessThan
       * @return A org.semanticwb.bsc.catalogs.LessThan
       */
        public static org.semanticwb.bsc.catalogs.LessThan getLessThan(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.LessThan)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.LessThan
       * @param id Identifier for org.semanticwb.bsc.catalogs.LessThan
       * @param model Model of the org.semanticwb.bsc.catalogs.LessThan
       * @return A org.semanticwb.bsc.catalogs.LessThan
       */
        public static org.semanticwb.bsc.catalogs.LessThan createLessThan(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.LessThan)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.LessThan
       * @param id Identifier for org.semanticwb.bsc.catalogs.LessThan
       * @param model Model of the org.semanticwb.bsc.catalogs.LessThan
       */
        public static void removeLessThan(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.LessThan
       * @param id Identifier for org.semanticwb.bsc.catalogs.LessThan
       * @param model Model of the org.semanticwb.bsc.catalogs.LessThan
       * @return true if the org.semanticwb.bsc.catalogs.LessThan exists, false otherwise
       */

        public static boolean hasLessThan(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLessThan(id, model)!=null);
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
