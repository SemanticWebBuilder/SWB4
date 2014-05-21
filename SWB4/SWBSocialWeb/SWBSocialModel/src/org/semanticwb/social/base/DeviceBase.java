package org.semanticwb.social.base;


public abstract class DeviceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Tagable
{
    public static final org.semanticwb.platform.SemanticClass social_Device=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Device");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Device");

    public static class ClassMgr
    {
       /**
       * Returns a list of Device for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Device
       */

        public static java.util.Iterator<org.semanticwb.social.Device> listDevices(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Device>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Device for all models
       * @return Iterator of org.semanticwb.social.Device
       */

        public static java.util.Iterator<org.semanticwb.social.Device> listDevices()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Device>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.Device
       * @param id Identifier for org.semanticwb.social.Device
       * @param model Model of the org.semanticwb.social.Device
       * @return A org.semanticwb.social.Device
       */
        public static org.semanticwb.social.Device getDevice(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Device)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Device
       * @param id Identifier for org.semanticwb.social.Device
       * @param model Model of the org.semanticwb.social.Device
       * @return A org.semanticwb.social.Device
       */
        public static org.semanticwb.social.Device createDevice(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Device)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Device
       * @param id Identifier for org.semanticwb.social.Device
       * @param model Model of the org.semanticwb.social.Device
       */
        public static void removeDevice(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Device
       * @param id Identifier for org.semanticwb.social.Device
       * @param model Model of the org.semanticwb.social.Device
       * @return true if the org.semanticwb.social.Device exists, false otherwise
       */

        public static boolean hasDevice(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDevice(id, model)!=null);
        }
    }

    public static DeviceBase.ClassMgr getDeviceClassMgr()
    {
        return new DeviceBase.ClassMgr();
    }

   /**
   * Constructs a DeviceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Device
   */
    public DeviceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Tags property
* @return String with the Tags
*/
    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

/**
* Sets the Tags property
* @param value long with the Tags
*/
    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

    public String getTags(String lang)
    {
        return getSemanticObject().getProperty(swb_tags, null, lang);
    }

    public String getDisplayTags(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_tags, lang);
    }

    public void setTags(String tags, String lang)
    {
        getSemanticObject().setProperty(swb_tags, tags, lang);
    }
}
