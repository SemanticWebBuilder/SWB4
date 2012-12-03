package org.semanticwb.process.model.base;


public abstract class WebServiceParameterBase extends org.semanticwb.process.model.BaseElement 
{
    public static final org.semanticwb.platform.SemanticProperty swp_wspValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#wspValue");
    public static final org.semanticwb.platform.SemanticProperty swp_wspName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#wspName");
    public static final org.semanticwb.platform.SemanticClass swp_WebServiceParameter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WebServiceParameter");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WebServiceParameter");

    public static class ClassMgr
    {
       /**
       * Returns a list of WebServiceParameter for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.WebServiceParameter
       */

        public static java.util.Iterator<org.semanticwb.process.model.WebServiceParameter> listWebServiceParameters(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceParameter>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.WebServiceParameter for all models
       * @return Iterator of org.semanticwb.process.model.WebServiceParameter
       */

        public static java.util.Iterator<org.semanticwb.process.model.WebServiceParameter> listWebServiceParameters()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceParameter>(it, true);
        }

        public static org.semanticwb.process.model.WebServiceParameter createWebServiceParameter(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.WebServiceParameter.ClassMgr.createWebServiceParameter(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.WebServiceParameter
       * @param id Identifier for org.semanticwb.process.model.WebServiceParameter
       * @param model Model of the org.semanticwb.process.model.WebServiceParameter
       * @return A org.semanticwb.process.model.WebServiceParameter
       */
        public static org.semanticwb.process.model.WebServiceParameter getWebServiceParameter(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.WebServiceParameter)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.WebServiceParameter
       * @param id Identifier for org.semanticwb.process.model.WebServiceParameter
       * @param model Model of the org.semanticwb.process.model.WebServiceParameter
       * @return A org.semanticwb.process.model.WebServiceParameter
       */
        public static org.semanticwb.process.model.WebServiceParameter createWebServiceParameter(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.WebServiceParameter)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.WebServiceParameter
       * @param id Identifier for org.semanticwb.process.model.WebServiceParameter
       * @param model Model of the org.semanticwb.process.model.WebServiceParameter
       */
        public static void removeWebServiceParameter(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.WebServiceParameter
       * @param id Identifier for org.semanticwb.process.model.WebServiceParameter
       * @param model Model of the org.semanticwb.process.model.WebServiceParameter
       * @return true if the org.semanticwb.process.model.WebServiceParameter exists, false otherwise
       */

        public static boolean hasWebServiceParameter(String id, org.semanticwb.model.SWBModel model)
        {
            return (getWebServiceParameter(id, model)!=null);
        }
    }

    public static WebServiceParameterBase.ClassMgr getWebServiceParameterClassMgr()
    {
        return new WebServiceParameterBase.ClassMgr();
    }

   /**
   * Constructs a WebServiceParameterBase with a SemanticObject
   * @param base The SemanticObject with the properties for the WebServiceParameter
   */
    public WebServiceParameterBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ParameterValue property
* @return String with the ParameterValue
*/
    public String getParameterValue()
    {
        return getSemanticObject().getProperty(swp_wspValue);
    }

/**
* Sets the ParameterValue property
* @param value long with the ParameterValue
*/
    public void setParameterValue(String value)
    {
        getSemanticObject().setProperty(swp_wspValue, value);
    }

/**
* Gets the ParameterName property
* @return String with the ParameterName
*/
    public String getParameterName()
    {
        return getSemanticObject().getProperty(swp_wspName);
    }

/**
* Sets the ParameterName property
* @param value long with the ParameterName
*/
    public void setParameterName(String value)
    {
        getSemanticObject().setProperty(swp_wspName, value);
    }
}
