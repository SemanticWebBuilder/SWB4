package org.semanticwb.process.schema.base;


public abstract class URLBase extends org.semanticwb.process.model.DataTypes 
{
    public static final org.semanticwb.platform.SemanticProperty swp_urlValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#urlValue");
    public static final org.semanticwb.platform.SemanticClass swps_URL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#URL");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#URL");

    public static class ClassMgr
    {
       /**
       * Returns a list of URL for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.schema.URL
       */

        public static java.util.Iterator<org.semanticwb.process.schema.URL> listURLs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.URL>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.schema.URL for all models
       * @return Iterator of org.semanticwb.process.schema.URL
       */

        public static java.util.Iterator<org.semanticwb.process.schema.URL> listURLs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.URL>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.schema.URL
       * @param id Identifier for org.semanticwb.process.schema.URL
       * @param model Model of the org.semanticwb.process.schema.URL
       * @return A org.semanticwb.process.schema.URL
       */
        public static org.semanticwb.process.schema.URL getURL(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.URL)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.schema.URL
       * @param id Identifier for org.semanticwb.process.schema.URL
       * @param model Model of the org.semanticwb.process.schema.URL
       * @return A org.semanticwb.process.schema.URL
       */
        public static org.semanticwb.process.schema.URL createURL(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.URL)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.schema.URL
       * @param id Identifier for org.semanticwb.process.schema.URL
       * @param model Model of the org.semanticwb.process.schema.URL
       */
        public static void removeURL(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.schema.URL
       * @param id Identifier for org.semanticwb.process.schema.URL
       * @param model Model of the org.semanticwb.process.schema.URL
       * @return true if the org.semanticwb.process.schema.URL exists, false otherwise
       */

        public static boolean hasURL(String id, org.semanticwb.model.SWBModel model)
        {
            return (getURL(id, model)!=null);
        }
    }

   /**
   * Constructs a URLBase with a SemanticObject
   * @param base The SemanticObject with the properties for the URL
   */
    public URLBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Value property
* @return String with the Value
*/
    public String getValue()
    {
        return getSemanticObject().getProperty(swp_urlValue);
    }

/**
* Sets the Value property
* @param value long with the Value
*/
    public void setValue(String value)
    {
        getSemanticObject().setProperty(swp_urlValue, value);
    }
}
