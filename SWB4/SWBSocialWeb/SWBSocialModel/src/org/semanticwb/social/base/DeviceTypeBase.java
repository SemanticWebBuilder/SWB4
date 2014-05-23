package org.semanticwb.social.base;


   /**
   * Tipo de dispositivo desde donde se envía el mensaje de entrada. Android Phone, Android Table, Ipad, Windows phone, etc 
   */
public abstract class DeviceTypeBase extends org.semanticwb.social.Device implements org.semanticwb.model.Tagable
{
   /**
   * Clase que comprende todos los tipos de Post de entrada (Povientes del Listener)que pueden ir siendo creados en la herramienta.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
    public static final org.semanticwb.platform.SemanticProperty social_hasPostInDeviceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostInDeviceInv");
   /**
   * Tipo de dispositivo desde donde se envía el mensaje de entrada. Android Phone, Android Table, Ipad, Windows phone, etc
   */
    public static final org.semanticwb.platform.SemanticClass social_DeviceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DeviceType");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#DeviceType");

    public static class ClassMgr
    {
       /**
       * Returns a list of DeviceType for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.DeviceType for all models
       * @return Iterator of org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.DeviceType
       * @param id Identifier for org.semanticwb.social.DeviceType
       * @param model Model of the org.semanticwb.social.DeviceType
       * @return A org.semanticwb.social.DeviceType
       */
        public static org.semanticwb.social.DeviceType getDeviceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.DeviceType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.DeviceType
       * @param id Identifier for org.semanticwb.social.DeviceType
       * @param model Model of the org.semanticwb.social.DeviceType
       * @return A org.semanticwb.social.DeviceType
       */
        public static org.semanticwb.social.DeviceType createDeviceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.DeviceType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.DeviceType
       * @param id Identifier for org.semanticwb.social.DeviceType
       * @param model Model of the org.semanticwb.social.DeviceType
       */
        public static void removeDeviceType(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.DeviceType
       * @param id Identifier for org.semanticwb.social.DeviceType
       * @param model Model of the org.semanticwb.social.DeviceType
       * @return true if the org.semanticwb.social.DeviceType exists, false otherwise
       */

        public static boolean hasDeviceType(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDeviceType(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.DeviceType with a determined PostInDeviceInv
       * @param value PostInDeviceInv of the type org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.DeviceType
       * @return Iterator with all the org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypeByPostInDeviceInv(org.semanticwb.social.PostIn value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInDeviceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.DeviceType with a determined PostInDeviceInv
       * @param value PostInDeviceInv of the type org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.DeviceType
       */

        public static java.util.Iterator<org.semanticwb.social.DeviceType> listDeviceTypeByPostInDeviceInv(org.semanticwb.social.PostIn value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.DeviceType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostInDeviceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DeviceTypeBase.ClassMgr getDeviceTypeClassMgr()
    {
        return new DeviceTypeBase.ClassMgr();
    }

   /**
   * Constructs a DeviceTypeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DeviceType
   */
    public DeviceTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.social.PostIn
   * @return A GenericIterator with all the org.semanticwb.social.PostIn
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> listPostInDeviceInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(getSemanticObject().listObjectProperties(social_hasPostInDeviceInv));
    }

   /**
   * Gets true if has a PostInDeviceInv
   * @param value org.semanticwb.social.PostIn to verify
   * @return true if the org.semanticwb.social.PostIn exists, false otherwise
   */
    public boolean hasPostInDeviceInv(org.semanticwb.social.PostIn value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostInDeviceInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PostInDeviceInv
   * @return a org.semanticwb.social.PostIn
   */
    public org.semanticwb.social.PostIn getPostInDeviceInv()
    {
         org.semanticwb.social.PostIn ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostInDeviceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostIn)obj.createGenericInstance();
         }
         return ret;
    }
}
