package org.semanticwb.bsc.catalogs.base;


public abstract class GreaterThanBase extends org.semanticwb.bsc.catalogs.Operation implements org.semanticwb.model.Descriptiveable
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
       * @return Iterator of org.semanticwb.bsc.catalogs.GreaterThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.GreaterThan> listGreaterThans(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.GreaterThan>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.GreaterThan for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.GreaterThan
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.GreaterThan> listGreaterThans()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.GreaterThan>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.GreaterThan createGreaterThan(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.GreaterThan.ClassMgr.createGreaterThan(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.GreaterThan
       * @param id Identifier for org.semanticwb.bsc.catalogs.GreaterThan
       * @param model Model of the org.semanticwb.bsc.catalogs.GreaterThan
       * @return A org.semanticwb.bsc.catalogs.GreaterThan
       */
        public static org.semanticwb.bsc.catalogs.GreaterThan getGreaterThan(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.GreaterThan)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.GreaterThan
       * @param id Identifier for org.semanticwb.bsc.catalogs.GreaterThan
       * @param model Model of the org.semanticwb.bsc.catalogs.GreaterThan
       * @return A org.semanticwb.bsc.catalogs.GreaterThan
       */
        public static org.semanticwb.bsc.catalogs.GreaterThan createGreaterThan(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.GreaterThan)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.GreaterThan
       * @param id Identifier for org.semanticwb.bsc.catalogs.GreaterThan
       * @param model Model of the org.semanticwb.bsc.catalogs.GreaterThan
       */
        public static void removeGreaterThan(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.GreaterThan
       * @param id Identifier for org.semanticwb.bsc.catalogs.GreaterThan
       * @param model Model of the org.semanticwb.bsc.catalogs.GreaterThan
       * @return true if the org.semanticwb.bsc.catalogs.GreaterThan exists, false otherwise
       */

        public static boolean hasGreaterThan(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGreaterThan(id, model)!=null);
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
