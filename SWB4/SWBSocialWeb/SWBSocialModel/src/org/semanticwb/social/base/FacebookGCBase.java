package org.semanticwb.social.base;


   /**
   * Clase que concentra propiedades para configuración general de facebook., para mostrar en sitio admin. ESTA CLASE YA NO SE UTILIZA, ELIMINAR DESPUES. 
   */
public abstract class FacebookGCBase extends org.semanticwb.social.GeneralConfig implements org.semanticwb.social.Secreteable
{
   /**
   * Clase que concentra propiedades para configuración general de facebook., para mostrar en sitio admin. ESTA CLASE YA NO SE UTILIZA, ELIMINAR DESPUES.
   */
    public static final org.semanticwb.platform.SemanticClass social_FacebookGC=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#FacebookGC");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#FacebookGC");

    public static class ClassMgr
    {
       /**
       * Returns a list of FacebookGC for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.FacebookGC
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookGC> listFacebookGCs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookGC>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.FacebookGC for all models
       * @return Iterator of org.semanticwb.social.FacebookGC
       */

        public static java.util.Iterator<org.semanticwb.social.FacebookGC> listFacebookGCs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.FacebookGC>(it, true);
        }

        public static org.semanticwb.social.FacebookGC createFacebookGC(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.FacebookGC.ClassMgr.createFacebookGC(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.FacebookGC
       * @param id Identifier for org.semanticwb.social.FacebookGC
       * @param model Model of the org.semanticwb.social.FacebookGC
       * @return A org.semanticwb.social.FacebookGC
       */
        public static org.semanticwb.social.FacebookGC getFacebookGC(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.FacebookGC)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.FacebookGC
       * @param id Identifier for org.semanticwb.social.FacebookGC
       * @param model Model of the org.semanticwb.social.FacebookGC
       * @return A org.semanticwb.social.FacebookGC
       */
        public static org.semanticwb.social.FacebookGC createFacebookGC(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.FacebookGC)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.FacebookGC
       * @param id Identifier for org.semanticwb.social.FacebookGC
       * @param model Model of the org.semanticwb.social.FacebookGC
       */
        public static void removeFacebookGC(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.FacebookGC
       * @param id Identifier for org.semanticwb.social.FacebookGC
       * @param model Model of the org.semanticwb.social.FacebookGC
       * @return true if the org.semanticwb.social.FacebookGC exists, false otherwise
       */

        public static boolean hasFacebookGC(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFacebookGC(id, model)!=null);
        }
    }

    public static FacebookGCBase.ClassMgr getFacebookGCClassMgr()
    {
        return new FacebookGCBase.ClassMgr();
    }

   /**
   * Constructs a FacebookGCBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FacebookGC
   */
    public FacebookGCBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
