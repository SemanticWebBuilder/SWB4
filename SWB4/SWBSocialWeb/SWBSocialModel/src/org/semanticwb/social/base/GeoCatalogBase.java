package org.semanticwb.social.base;


public abstract class GeoCatalogBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass social_GeoCatalog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#GeoCatalog");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#GeoCatalog");

    public static class ClassMgr
    {
       /**
       * Returns a list of GeoCatalog for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.GeoCatalog
       */

        public static java.util.Iterator<org.semanticwb.social.GeoCatalog> listGeoCatalogs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.GeoCatalog>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.GeoCatalog for all models
       * @return Iterator of org.semanticwb.social.GeoCatalog
       */

        public static java.util.Iterator<org.semanticwb.social.GeoCatalog> listGeoCatalogs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.GeoCatalog>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.GeoCatalog
       * @param id Identifier for org.semanticwb.social.GeoCatalog
       * @param model Model of the org.semanticwb.social.GeoCatalog
       * @return A org.semanticwb.social.GeoCatalog
       */
        public static org.semanticwb.social.GeoCatalog getGeoCatalog(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.GeoCatalog)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.GeoCatalog
       * @param id Identifier for org.semanticwb.social.GeoCatalog
       * @param model Model of the org.semanticwb.social.GeoCatalog
       * @return A org.semanticwb.social.GeoCatalog
       */
        public static org.semanticwb.social.GeoCatalog createGeoCatalog(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.GeoCatalog)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.GeoCatalog
       * @param id Identifier for org.semanticwb.social.GeoCatalog
       * @param model Model of the org.semanticwb.social.GeoCatalog
       */
        public static void removeGeoCatalog(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.GeoCatalog
       * @param id Identifier for org.semanticwb.social.GeoCatalog
       * @param model Model of the org.semanticwb.social.GeoCatalog
       * @return true if the org.semanticwb.social.GeoCatalog exists, false otherwise
       */

        public static boolean hasGeoCatalog(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGeoCatalog(id, model)!=null);
        }
    }

    public static GeoCatalogBase.ClassMgr getGeoCatalogClassMgr()
    {
        return new GeoCatalogBase.ClassMgr();
    }

   /**
   * Constructs a GeoCatalogBase with a SemanticObject
   * @param base The SemanticObject with the properties for the GeoCatalog
   */
    public GeoCatalogBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
