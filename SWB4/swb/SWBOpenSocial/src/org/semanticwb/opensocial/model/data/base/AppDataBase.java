package org.semanticwb.opensocial.model.data.base;


public abstract class AppDataBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticProperty data_appid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#appid");
    public static final org.semanticwb.platform.SemanticProperty data_value=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#value");
   /**
   * Indica si el elemento es válido
   */
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticProperty data_key=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#key");
    public static final org.semanticwb.platform.SemanticClass data_AppData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#AppData");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#AppData");

    public static class ClassMgr
    {
       /**
       * Returns a list of AppData for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.opensocial.model.data.AppData
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.AppData> listAppDatas(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.AppData>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.opensocial.model.data.AppData for all models
       * @return Iterator of org.semanticwb.opensocial.model.data.AppData
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.AppData> listAppDatas()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.AppData>(it, true);
        }
       /**
       * Gets a org.semanticwb.opensocial.model.data.AppData
       * @param id Identifier for org.semanticwb.opensocial.model.data.AppData
       * @param model Model of the org.semanticwb.opensocial.model.data.AppData
       * @return A org.semanticwb.opensocial.model.data.AppData
       */
        public static org.semanticwb.opensocial.model.data.AppData getAppData(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.AppData)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.opensocial.model.data.AppData
       * @param id Identifier for org.semanticwb.opensocial.model.data.AppData
       * @param model Model of the org.semanticwb.opensocial.model.data.AppData
       * @return A org.semanticwb.opensocial.model.data.AppData
       */
        public static org.semanticwb.opensocial.model.data.AppData createAppData(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.AppData)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.opensocial.model.data.AppData
       * @param id Identifier for org.semanticwb.opensocial.model.data.AppData
       * @param model Model of the org.semanticwb.opensocial.model.data.AppData
       */
        public static void removeAppData(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.opensocial.model.data.AppData
       * @param id Identifier for org.semanticwb.opensocial.model.data.AppData
       * @param model Model of the org.semanticwb.opensocial.model.data.AppData
       * @return true if the org.semanticwb.opensocial.model.data.AppData exists, false otherwise
       */

        public static boolean hasAppData(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAppData(id, model)!=null);
        }
    }

   /**
   * Constructs a AppDataBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AppData
   */
    public AppDataBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Appid property
* @return String with the Appid
*/
    public String getAppid()
    {
        return getSemanticObject().getProperty(data_appid);
    }

/**
* Sets the Appid property
* @param value long with the Appid
*/
    public void setAppid(String value)
    {
        getSemanticObject().setProperty(data_appid, value);
    }

/**
* Gets the Value property
* @return String with the Value
*/
    public String getValue()
    {
        return getSemanticObject().getProperty(data_value);
    }

/**
* Sets the Value property
* @param value long with the Value
*/
    public void setValue(String value)
    {
        getSemanticObject().setProperty(data_value, value);
    }

/**
* Gets the Valid property
* @return boolean with the Valid
*/
    public boolean isValid()
    {
        //Override this method in AppData object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

/**
* Sets the Valid property
* @param value long with the Valid
*/
    public void setValid(boolean value)
    {
        //Override this method in AppData object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
    }

/**
* Gets the Key property
* @return String with the Key
*/
    public String getKey()
    {
        return getSemanticObject().getProperty(data_key);
    }

/**
* Sets the Key property
* @param value long with the Key
*/
    public void setKey(String value)
    {
        getSemanticObject().setProperty(data_key, value);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
