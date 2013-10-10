package org.semanticwb.bsc.catalogs.base;


public abstract class NoneBase extends org.semanticwb.bsc.catalogs.Operation implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass bsc_None=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#None");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#None");

    public static class ClassMgr
    {
       /**
       * Returns a list of None for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.catalogs.None
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.None> listNones(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.None>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.None for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.None
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.None> listNones()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.None>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.None createNone(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.None.ClassMgr.createNone(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.None
       * @param id Identifier for org.semanticwb.bsc.catalogs.None
       * @param model Model of the org.semanticwb.bsc.catalogs.None
       * @return A org.semanticwb.bsc.catalogs.None
       */
        public static org.semanticwb.bsc.catalogs.None getNone(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.None)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.None
       * @param id Identifier for org.semanticwb.bsc.catalogs.None
       * @param model Model of the org.semanticwb.bsc.catalogs.None
       * @return A org.semanticwb.bsc.catalogs.None
       */
        public static org.semanticwb.bsc.catalogs.None createNone(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.None)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.None
       * @param id Identifier for org.semanticwb.bsc.catalogs.None
       * @param model Model of the org.semanticwb.bsc.catalogs.None
       */
        public static void removeNone(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.None
       * @param id Identifier for org.semanticwb.bsc.catalogs.None
       * @param model Model of the org.semanticwb.bsc.catalogs.None
       * @return true if the org.semanticwb.bsc.catalogs.None exists, false otherwise
       */

        public static boolean hasNone(String id, org.semanticwb.model.SWBModel model)
        {
            return (getNone(id, model)!=null);
        }
    }

    public static NoneBase.ClassMgr getNoneClassMgr()
    {
        return new NoneBase.ClassMgr();
    }

   /**
   * Constructs a NoneBase with a SemanticObject
   * @param base The SemanticObject with the properties for the None
   */
    public NoneBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
