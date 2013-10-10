package org.semanticwb.bsc.catalogs.base;


public abstract class GreaterThanOrEqualBase extends org.semanticwb.bsc.catalogs.Operation implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass bsc_GreaterThanOrEqual=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#GreaterThanOrEqual");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#GreaterThanOrEqual");

    public static class ClassMgr
    {
       /**
       * Returns a list of GreaterThanOrEqual for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.GreaterThanOrEqual> listGreaterThanOrEquals(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.GreaterThanOrEqual>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.GreaterThanOrEqual for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.GreaterThanOrEqual> listGreaterThanOrEquals()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.GreaterThanOrEqual>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.GreaterThanOrEqual createGreaterThanOrEqual(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.GreaterThanOrEqual.ClassMgr.createGreaterThanOrEqual(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       * @param id Identifier for org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       * @param model Model of the org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       * @return A org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       */
        public static org.semanticwb.bsc.catalogs.GreaterThanOrEqual getGreaterThanOrEqual(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.GreaterThanOrEqual)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       * @param id Identifier for org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       * @param model Model of the org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       * @return A org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       */
        public static org.semanticwb.bsc.catalogs.GreaterThanOrEqual createGreaterThanOrEqual(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.GreaterThanOrEqual)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       * @param id Identifier for org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       * @param model Model of the org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       */
        public static void removeGreaterThanOrEqual(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       * @param id Identifier for org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       * @param model Model of the org.semanticwb.bsc.catalogs.GreaterThanOrEqual
       * @return true if the org.semanticwb.bsc.catalogs.GreaterThanOrEqual exists, false otherwise
       */

        public static boolean hasGreaterThanOrEqual(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGreaterThanOrEqual(id, model)!=null);
        }
    }

    public static GreaterThanOrEqualBase.ClassMgr getGreaterThanOrEqualClassMgr()
    {
        return new GreaterThanOrEqualBase.ClassMgr();
    }

   /**
   * Constructs a GreaterThanOrEqualBase with a SemanticObject
   * @param base The SemanticObject with the properties for the GreaterThanOrEqual
   */
    public GreaterThanOrEqualBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
