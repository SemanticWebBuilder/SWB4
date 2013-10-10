package org.semanticwb.bsc.catalogs.base;


public abstract class EqualityBase extends org.semanticwb.bsc.catalogs.Operation implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Equality=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Equality");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Equality");

    public static class ClassMgr
    {
       /**
       * Returns a list of Equality for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.catalogs.Equality
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Equality> listEqualities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Equality>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.Equality for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.Equality
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Equality> listEqualities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Equality>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.Equality createEquality(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.Equality.ClassMgr.createEquality(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.Equality
       * @param id Identifier for org.semanticwb.bsc.catalogs.Equality
       * @param model Model of the org.semanticwb.bsc.catalogs.Equality
       * @return A org.semanticwb.bsc.catalogs.Equality
       */
        public static org.semanticwb.bsc.catalogs.Equality getEquality(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Equality)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.Equality
       * @param id Identifier for org.semanticwb.bsc.catalogs.Equality
       * @param model Model of the org.semanticwb.bsc.catalogs.Equality
       * @return A org.semanticwb.bsc.catalogs.Equality
       */
        public static org.semanticwb.bsc.catalogs.Equality createEquality(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Equality)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.Equality
       * @param id Identifier for org.semanticwb.bsc.catalogs.Equality
       * @param model Model of the org.semanticwb.bsc.catalogs.Equality
       */
        public static void removeEquality(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.Equality
       * @param id Identifier for org.semanticwb.bsc.catalogs.Equality
       * @param model Model of the org.semanticwb.bsc.catalogs.Equality
       * @return true if the org.semanticwb.bsc.catalogs.Equality exists, false otherwise
       */

        public static boolean hasEquality(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEquality(id, model)!=null);
        }
    }

    public static EqualityBase.ClassMgr getEqualityClassMgr()
    {
        return new EqualityBase.ClassMgr();
    }

   /**
   * Constructs a EqualityBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Equality
   */
    public EqualityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
