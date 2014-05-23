package org.semanticwb.social.base;


   /**
   * Plataforma (Sistema Operativo) desde donde se envió el mensaje de entrada. 
   */
public abstract class DevicePlatformBase extends org.semanticwb.social.Device implements org.semanticwb.model.Tagable
{
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
    public static final org.semanticwb.platform.SemanticProperty social_hasPostInDevicePlatformInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostInDevicePlatformInv");
   /**
   * Plataforma (Sistema Operativo) desde donde se envió el mensaje de entrada.
   */
    public static final org.semanticwb.platform.SemanticClass social_DevicePlatform=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DevicePlatform");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DevicePlatform");

    public static class ClassMgr
    {
       /**
       * Returns a list of DevicePlatform for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatforms(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.DevicePlatform for all models
       * @return Iterator of org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatforms()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.DevicePlatform
       * @param id Identifier for org.semanticwb.social.DevicePlatform
       * @param model Model of the org.semanticwb.social.DevicePlatform
       * @return A org.semanticwb.social.DevicePlatform
       */
        public static org.semanticwb.social.DevicePlatform getDevicePlatform(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.DevicePlatform)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.DevicePlatform
       * @param id Identifier for org.semanticwb.social.DevicePlatform
       * @param model Model of the org.semanticwb.social.DevicePlatform
       * @return A org.semanticwb.social.DevicePlatform
       */
        public static org.semanticwb.social.DevicePlatform createDevicePlatform(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.DevicePlatform)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.DevicePlatform
       * @param id Identifier for org.semanticwb.social.DevicePlatform
       * @param model Model of the org.semanticwb.social.DevicePlatform
       */
        public static void removeDevicePlatform(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.DevicePlatform
       * @param id Identifier for org.semanticwb.social.DevicePlatform
       * @param model Model of the org.semanticwb.social.DevicePlatform
       * @return true if the org.semanticwb.social.DevicePlatform exists, false otherwise
       */

        public static boolean hasDevicePlatform(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDevicePlatform(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.DevicePlatform with a determined PostInDevicePlatformInv
       * @param value PostInDevicePlatformInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.DevicePlatform
       * @return Iterator with all the org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatformByPostInDevicePlatformInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInDevicePlatformInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DevicePlatform with a determined PostInDevicePlatformInv
       * @param value PostInDevicePlatformInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.DevicePlatform
       */

        public static java.util.Iterator<org.semanticwb.social.DevicePlatform> listDevicePlatformByPostInDevicePlatformInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DevicePlatform> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInDevicePlatformInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DevicePlatformBase.ClassMgr getDevicePlatformClassMgr()
    {
        return new DevicePlatformBase.ClassMgr();
    }

   /**
   * Constructs a DevicePlatformBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DevicePlatform
   */
    public DevicePlatformBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPostInDevicePlatformInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPostInDevicePlatformInv));
    }

   /**
   * Gets true if has a PostInDevicePlatformInv
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPostInDevicePlatformInv(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostInDevicePlatformInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostInDevicePlatformInv
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInDevicePlatformInv()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostInDevicePlatformInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }
}
