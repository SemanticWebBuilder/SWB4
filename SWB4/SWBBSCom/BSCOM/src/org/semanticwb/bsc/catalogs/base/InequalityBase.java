package org.semanticwb.bsc.catalogs.base;


public abstract class InequalityBase extends org.semanticwb.bsc.catalogs.Operation implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Inequality=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Inequality");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Inequality");

    public static class ClassMgr
    {
       /**
       * Returns a list of Inequality for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.catalogs.Inequality
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Inequality> listInequalities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Inequality>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.catalogs.Inequality for all models
       * @return Iterator of org.semanticwb.bsc.catalogs.Inequality
       */

        public static java.util.Iterator<org.semanticwb.bsc.catalogs.Inequality> listInequalities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.catalogs.Inequality>(it, true);
        }

        public static org.semanticwb.bsc.catalogs.Inequality createInequality(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.catalogs.Inequality.ClassMgr.createInequality(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.catalogs.Inequality
       * @param id Identifier for org.semanticwb.bsc.catalogs.Inequality
       * @param model Model of the org.semanticwb.bsc.catalogs.Inequality
       * @return A org.semanticwb.bsc.catalogs.Inequality
       */
        public static org.semanticwb.bsc.catalogs.Inequality getInequality(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Inequality)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.catalogs.Inequality
       * @param id Identifier for org.semanticwb.bsc.catalogs.Inequality
       * @param model Model of the org.semanticwb.bsc.catalogs.Inequality
       * @return A org.semanticwb.bsc.catalogs.Inequality
       */
        public static org.semanticwb.bsc.catalogs.Inequality createInequality(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.catalogs.Inequality)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.catalogs.Inequality
       * @param id Identifier for org.semanticwb.bsc.catalogs.Inequality
       * @param model Model of the org.semanticwb.bsc.catalogs.Inequality
       */
        public static void removeInequality(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.catalogs.Inequality
       * @param id Identifier for org.semanticwb.bsc.catalogs.Inequality
       * @param model Model of the org.semanticwb.bsc.catalogs.Inequality
       * @return true if the org.semanticwb.bsc.catalogs.Inequality exists, false otherwise
       */

        public static boolean hasInequality(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInequality(id, model)!=null);
        }
    }

    public static InequalityBase.ClassMgr getInequalityClassMgr()
    {
        return new InequalityBase.ClassMgr();
    }

   /**
   * Constructs a InequalityBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Inequality
   */
    public InequalityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
