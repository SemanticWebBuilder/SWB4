package org.semanticwb.social.base;


   /**
   * Cargador de Archivos con Dojo 
   */
public abstract class SocialFileUploadBase extends org.semanticwb.model.FileUpload 
{
   /**
   * Cargador de Archivos con Dojo
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialFileUpload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialFileUpload");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialFileUpload");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialFileUpload for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialFileUpload
       */

        public static java.util.Iterator<org.semanticwb.social.SocialFileUpload> listSocialFileUploads(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialFileUpload>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialFileUpload for all models
       * @return Iterator of org.semanticwb.social.SocialFileUpload
       */

        public static java.util.Iterator<org.semanticwb.social.SocialFileUpload> listSocialFileUploads()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialFileUpload>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SocialFileUpload
       * @param id Identifier for org.semanticwb.social.SocialFileUpload
       * @param model Model of the org.semanticwb.social.SocialFileUpload
       * @return A org.semanticwb.social.SocialFileUpload
       */
        public static org.semanticwb.social.SocialFileUpload getSocialFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialFileUpload)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialFileUpload
       * @param id Identifier for org.semanticwb.social.SocialFileUpload
       * @param model Model of the org.semanticwb.social.SocialFileUpload
       * @return A org.semanticwb.social.SocialFileUpload
       */
        public static org.semanticwb.social.SocialFileUpload createSocialFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialFileUpload)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialFileUpload
       * @param id Identifier for org.semanticwb.social.SocialFileUpload
       * @param model Model of the org.semanticwb.social.SocialFileUpload
       */
        public static void removeSocialFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialFileUpload
       * @param id Identifier for org.semanticwb.social.SocialFileUpload
       * @param model Model of the org.semanticwb.social.SocialFileUpload
       * @return true if the org.semanticwb.social.SocialFileUpload exists, false otherwise
       */

        public static boolean hasSocialFileUpload(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialFileUpload(id, model)!=null);
        }
    }

    public static SocialFileUploadBase.ClassMgr getSocialFileUploadClassMgr()
    {
        return new SocialFileUploadBase.ClassMgr();
    }

   /**
   * Constructs a SocialFileUploadBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialFileUpload
   */
    public SocialFileUploadBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
