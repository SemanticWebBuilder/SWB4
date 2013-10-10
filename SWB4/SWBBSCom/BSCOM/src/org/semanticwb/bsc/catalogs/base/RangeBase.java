package org.semanticwb.bsc.catalogs.base;


public abstract class RangeBase extends org.semanticwb.bsc.catalogs.Operation implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Range=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Range");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Range");

    public static class ClassMgr
    {
       /**
       * Returns a list of Range for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.catalogs.Range
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Range> listRanges(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Range>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.Range for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.Range
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Range> listRanges()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Range>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.Range createRange(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.Range.ClassMgr.createRange(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.Range
       * @param id Identifier for org.semanticwb.bsc.catalogs.Range
       * @param model Model of the org.semanticwb.bsc.catalogs.Range
       * @return A org.semanticwb.bsc.catalogs.Range
       */
        public static org.semanticwb.bsc.catalogs.Range getRange(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Range)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.Range
       * @param id Identifier for org.semanticwb.bsc.catalogs.Range
       * @param model Model of the org.semanticwb.bsc.catalogs.Range
       * @return A org.semanticwb.bsc.catalogs.Range
       */
        public static org.semanticwb.bsc.catalogs.Range createRange(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Range)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.Range
       * @param id Identifier for org.semanticwb.bsc.catalogs.Range
       * @param model Model of the org.semanticwb.bsc.catalogs.Range
       */
        public static void removeRange(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.Range
       * @param id Identifier for org.semanticwb.bsc.catalogs.Range
       * @param model Model of the org.semanticwb.bsc.catalogs.Range
       * @return true if the org.semanticwb.bsc.catalogs.Range exists, false otherwise
       */

        public static boolean hasRange(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRange(id, model)!=null);
        }
    }

    public static RangeBase.ClassMgr getRangeClassMgr()
    {
        return new RangeBase.ClassMgr();
    }

   /**
   * Constructs a RangeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Range
   */
    public RangeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
