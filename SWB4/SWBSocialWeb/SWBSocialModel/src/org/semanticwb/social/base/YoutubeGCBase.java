package org.semanticwb.social.base;


   /**
   * Clase que concentra propiedades para configuración general de youtube., para mostrar en sitio admin. 
   */
public abstract class YoutubeGCBase extends org.semanticwb.social.GeneralConfig implements org.semanticwb.social.DeveloperKeyable,org.semanticwb.social.Secreteable
{
   /**
   * Clase que concentra propiedades para configuración general de youtube., para mostrar en sitio admin.
   */
    public static final org.semanticwb.platform.SemanticClass social_YoutubeGC=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#YoutubeGC");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#YoutubeGC");

    public static class ClassMgr
    {
       /**
       * Returns a list of YoutubeGC for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.YoutubeGC
       */

        public static java.util.Iterator<org.semanticwb.social.YoutubeGC> listYoutubeGCs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.YoutubeGC>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.YoutubeGC for all models
       * @return Iterator of org.semanticwb.social.YoutubeGC
       */

        public static java.util.Iterator<org.semanticwb.social.YoutubeGC> listYoutubeGCs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.YoutubeGC>(it, true);
        }

        public static org.semanticwb.social.YoutubeGC createYoutubeGC(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.YoutubeGC.ClassMgr.createYoutubeGC(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.YoutubeGC
       * @param id Identifier for org.semanticwb.social.YoutubeGC
       * @param model Model of the org.semanticwb.social.YoutubeGC
       * @return A org.semanticwb.social.YoutubeGC
       */
        public static org.semanticwb.social.YoutubeGC getYoutubeGC(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.YoutubeGC)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.YoutubeGC
       * @param id Identifier for org.semanticwb.social.YoutubeGC
       * @param model Model of the org.semanticwb.social.YoutubeGC
       * @return A org.semanticwb.social.YoutubeGC
       */
        public static org.semanticwb.social.YoutubeGC createYoutubeGC(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.YoutubeGC)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.YoutubeGC
       * @param id Identifier for org.semanticwb.social.YoutubeGC
       * @param model Model of the org.semanticwb.social.YoutubeGC
       */
        public static void removeYoutubeGC(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.YoutubeGC
       * @param id Identifier for org.semanticwb.social.YoutubeGC
       * @param model Model of the org.semanticwb.social.YoutubeGC
       * @return true if the org.semanticwb.social.YoutubeGC exists, false otherwise
       */

        public static boolean hasYoutubeGC(String id, org.semanticwb.model.SWBModel model)
        {
            return (getYoutubeGC(id, model)!=null);
        }
    }

    public static YoutubeGCBase.ClassMgr getYoutubeGCClassMgr()
    {
        return new YoutubeGCBase.ClassMgr();
    }

   /**
   * Constructs a YoutubeGCBase with a SemanticObject
   * @param base The SemanticObject with the properties for the YoutubeGC
   */
    public YoutubeGCBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the RefreshToken property
* @return String with the RefreshToken
*/
    public String getRefreshToken()
    {
        return getSemanticObject().getProperty(social_refreshToken);
    }

/**
* Sets the RefreshToken property
* @param value long with the RefreshToken
*/
    public void setRefreshToken(String value)
    {
        getSemanticObject().setProperty(social_refreshToken, value);
    }

/**
* Gets the DeveloperKey property
* @return String with the DeveloperKey
*/
    public String getDeveloperKey()
    {
        return getSemanticObject().getProperty(social_developerKey);
    }

/**
* Sets the DeveloperKey property
* @param value long with the DeveloperKey
*/
    public void setDeveloperKey(String value)
    {
        getSemanticObject().setProperty(social_developerKey, value);
    }
}
