package org.semanticwb.social.base;


   /**
   * Clase de Configuración general. Solo para agrupar ciertas clases. 
   */
public abstract class GeneralConfigBase extends org.semanticwb.model.SWBClass implements org.semanticwb.social.Secreteable
{
   /**
   * Clase de Configuración general. Solo para agrupar ciertas clases.
   */
    public static final org.semanticwb.platform.SemanticClass social_GeneralConfig=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#GeneralConfig");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#GeneralConfig");

    public static class ClassMgr
    {
       /**
       * Returns a list of GeneralConfig for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.GeneralConfig
       */

        public static java.util.Iterator<org.semanticwb.social.GeneralConfig> listGeneralConfigs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.GeneralConfig>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.GeneralConfig for all models
       * @return Iterator of org.semanticwb.social.GeneralConfig
       */

        public static java.util.Iterator<org.semanticwb.social.GeneralConfig> listGeneralConfigs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.GeneralConfig>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.GeneralConfig
       * @param id Identifier for org.semanticwb.social.GeneralConfig
       * @param model Model of the org.semanticwb.social.GeneralConfig
       * @return A org.semanticwb.social.GeneralConfig
       */
        public static org.semanticwb.social.GeneralConfig getGeneralConfig(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.GeneralConfig)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.GeneralConfig
       * @param id Identifier for org.semanticwb.social.GeneralConfig
       * @param model Model of the org.semanticwb.social.GeneralConfig
       * @return A org.semanticwb.social.GeneralConfig
       */
        public static org.semanticwb.social.GeneralConfig createGeneralConfig(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.GeneralConfig)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.GeneralConfig
       * @param id Identifier for org.semanticwb.social.GeneralConfig
       * @param model Model of the org.semanticwb.social.GeneralConfig
       */
        public static void removeGeneralConfig(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.GeneralConfig
       * @param id Identifier for org.semanticwb.social.GeneralConfig
       * @param model Model of the org.semanticwb.social.GeneralConfig
       * @return true if the org.semanticwb.social.GeneralConfig exists, false otherwise
       */

        public static boolean hasGeneralConfig(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGeneralConfig(id, model)!=null);
        }
    }

    public static GeneralConfigBase.ClassMgr getGeneralConfigClassMgr()
    {
        return new GeneralConfigBase.ClassMgr();
    }

   /**
   * Constructs a GeneralConfigBase with a SemanticObject
   * @param base The SemanticObject with the properties for the GeneralConfig
   */
    public GeneralConfigBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the AppKey property
* @return String with the AppKey
*/
    public String getAppKey()
    {
        return getSemanticObject().getProperty(social_appKey);
    }

/**
* Sets the AppKey property
* @param value long with the AppKey
*/
    public void setAppKey(String value)
    {
        getSemanticObject().setProperty(social_appKey, value);
    }

/**
* Gets the SecretKey property
* @return String with the SecretKey
*/
    public String getSecretKey()
    {
        return getSemanticObject().getProperty(social_secretKey);
    }

/**
* Sets the SecretKey property
* @param value long with the SecretKey
*/
    public void setSecretKey(String value)
    {
        getSemanticObject().setProperty(social_secretKey, value);
    }
}
