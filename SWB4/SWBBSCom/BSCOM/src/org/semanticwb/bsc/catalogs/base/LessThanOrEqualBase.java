package org.semanticwb.bsc.catalogs.base;


public abstract class LessThanOrEqualBase extends org.semanticwb.bsc.catalogs.Operation implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass bsc_LessThanOrEqual=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#LessThanOrEqual");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#LessThanOrEqual");

    public static class ClassMgr
    {
       /**
       * Returns a list of LessThanOrEqual for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.catalogs.LessThanOrEqual
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.LessThanOrEqual> listLessThanOrEquals(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.LessThanOrEqual>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.LessThanOrEqual for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.LessThanOrEqual
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.LessThanOrEqual> listLessThanOrEquals()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.LessThanOrEqual>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.LessThanOrEqual createLessThanOrEqual(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.LessThanOrEqual.ClassMgr.createLessThanOrEqual(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.LessThanOrEqual
       * @param id Identifier for org.semanticwb.bsc.catalogs.LessThanOrEqual
       * @param model Model of the org.semanticwb.bsc.catalogs.LessThanOrEqual
       * @return A org.semanticwb.bsc.catalogs.LessThanOrEqual
       */
        public static org.semanticwb.bsc.catalogs.LessThanOrEqual getLessThanOrEqual(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.LessThanOrEqual)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.LessThanOrEqual
       * @param id Identifier for org.semanticwb.bsc.catalogs.LessThanOrEqual
       * @param model Model of the org.semanticwb.bsc.catalogs.LessThanOrEqual
       * @return A org.semanticwb.bsc.catalogs.LessThanOrEqual
       */
        public static org.semanticwb.bsc.catalogs.LessThanOrEqual createLessThanOrEqual(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.LessThanOrEqual)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.LessThanOrEqual
       * @param id Identifier for org.semanticwb.bsc.catalogs.LessThanOrEqual
       * @param model Model of the org.semanticwb.bsc.catalogs.LessThanOrEqual
       */
        public static void removeLessThanOrEqual(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.LessThanOrEqual
       * @param id Identifier for org.semanticwb.bsc.catalogs.LessThanOrEqual
       * @param model Model of the org.semanticwb.bsc.catalogs.LessThanOrEqual
       * @return true if the org.semanticwb.bsc.catalogs.LessThanOrEqual exists, false otherwise
       */

        public static boolean hasLessThanOrEqual(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLessThanOrEqual(id, model)!=null);
        }
    }

    public static LessThanOrEqualBase.ClassMgr getLessThanOrEqualClassMgr()
    {
        return new LessThanOrEqualBase.ClassMgr();
    }

   /**
   * Constructs a LessThanOrEqualBase with a SemanticObject
   * @param base The SemanticObject with the properties for the LessThanOrEqual
   */
    public LessThanOrEqualBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
