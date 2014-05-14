package org.semanticwb.social.base;


   /**
   * Clase que concentra propiedades para configuración general de twitter., para mostrar en sitio admin. ESTA CLASE YA NO SE UTILIZA, ELIMINAR DESPUES. 
   */
public abstract class TwitterGCBase extends org.semanticwb.social.GeneralConfig implements org.semanticwb.social.Secreteable
{
   /**
   * Clase que concentra propiedades para configuración general de twitter., para mostrar en sitio admin. ESTA CLASE YA NO SE UTILIZA, ELIMINAR DESPUES.
   */
    public static final org.semanticwb.platform.SemanticClass social_TwitterGC=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TwitterGC");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TwitterGC");

    public static class ClassMgr
    {
       /**
       * Returns a list of TwitterGC for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.TwitterGC
       */

        public static java.util.Iterator<org.semanticwb.social.TwitterGC> listTwitterGCs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.TwitterGC>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.TwitterGC for all models
       * @return Iterator of org.semanticwb.social.TwitterGC
       */

        public static java.util.Iterator<org.semanticwb.social.TwitterGC> listTwitterGCs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.TwitterGC>(it, true);
        }

        public static org.semanticwb.social.TwitterGC createTwitterGC(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.TwitterGC.ClassMgr.createTwitterGC(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.TwitterGC
       * @param id Identifier for org.semanticwb.social.TwitterGC
       * @param model Model of the org.semanticwb.social.TwitterGC
       * @return A org.semanticwb.social.TwitterGC
       */
        public static org.semanticwb.social.TwitterGC getTwitterGC(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.TwitterGC)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.TwitterGC
       * @param id Identifier for org.semanticwb.social.TwitterGC
       * @param model Model of the org.semanticwb.social.TwitterGC
       * @return A org.semanticwb.social.TwitterGC
       */
        public static org.semanticwb.social.TwitterGC createTwitterGC(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.TwitterGC)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.TwitterGC
       * @param id Identifier for org.semanticwb.social.TwitterGC
       * @param model Model of the org.semanticwb.social.TwitterGC
       */
        public static void removeTwitterGC(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.TwitterGC
       * @param id Identifier for org.semanticwb.social.TwitterGC
       * @param model Model of the org.semanticwb.social.TwitterGC
       * @return true if the org.semanticwb.social.TwitterGC exists, false otherwise
       */

        public static boolean hasTwitterGC(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTwitterGC(id, model)!=null);
        }
    }

    public static TwitterGCBase.ClassMgr getTwitterGCClassMgr()
    {
        return new TwitterGCBase.ClassMgr();
    }

   /**
   * Constructs a TwitterGCBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TwitterGC
   */
    public TwitterGCBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
