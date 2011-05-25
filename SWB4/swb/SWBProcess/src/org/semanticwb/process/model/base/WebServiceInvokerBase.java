package org.semanticwb.process.model.base;


public abstract class WebServiceInvokerBase extends org.semanticwb.process.model.ProcessService 
{
    public static final org.semanticwb.platform.SemanticClass swp_WebService=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WebService");
    public static final org.semanticwb.platform.SemanticProperty swp_webService=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#webService");
    public static final org.semanticwb.platform.SemanticClass swp_WebServiceInvoker=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WebServiceInvoker");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WebServiceInvoker");

    public static class ClassMgr
    {
       /**
       * Returns a list of WebServiceInvoker for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.WebServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.WebServiceInvoker> listWebServiceInvokers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceInvoker>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.WebServiceInvoker for all models
       * @return Iterator of org.semanticwb.process.model.WebServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.WebServiceInvoker> listWebServiceInvokers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceInvoker>(it, true);
        }

        public static org.semanticwb.process.model.WebServiceInvoker createWebServiceInvoker(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.WebServiceInvoker.ClassMgr.createWebServiceInvoker(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.WebServiceInvoker
       * @param id Identifier for org.semanticwb.process.model.WebServiceInvoker
       * @param model Model of the org.semanticwb.process.model.WebServiceInvoker
       * @return A org.semanticwb.process.model.WebServiceInvoker
       */
        public static org.semanticwb.process.model.WebServiceInvoker getWebServiceInvoker(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.WebServiceInvoker)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.WebServiceInvoker
       * @param id Identifier for org.semanticwb.process.model.WebServiceInvoker
       * @param model Model of the org.semanticwb.process.model.WebServiceInvoker
       * @return A org.semanticwb.process.model.WebServiceInvoker
       */
        public static org.semanticwb.process.model.WebServiceInvoker createWebServiceInvoker(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.WebServiceInvoker)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.WebServiceInvoker
       * @param id Identifier for org.semanticwb.process.model.WebServiceInvoker
       * @param model Model of the org.semanticwb.process.model.WebServiceInvoker
       */
        public static void removeWebServiceInvoker(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.WebServiceInvoker
       * @param id Identifier for org.semanticwb.process.model.WebServiceInvoker
       * @param model Model of the org.semanticwb.process.model.WebServiceInvoker
       * @return true if the org.semanticwb.process.model.WebServiceInvoker exists, false otherwise
       */

        public static boolean hasWebServiceInvoker(String id, org.semanticwb.model.SWBModel model)
        {
            return (getWebServiceInvoker(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.WebServiceInvoker with a determined WebService
       * @param value WebService of the type org.semanticwb.process.model.WebService
       * @param model Model of the org.semanticwb.process.model.WebServiceInvoker
       * @return Iterator with all the org.semanticwb.process.model.WebServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.WebServiceInvoker> listWebServiceInvokerByWebService(org.semanticwb.process.model.WebService value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceInvoker> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_webService, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.WebServiceInvoker with a determined WebService
       * @param value WebService of the type org.semanticwb.process.model.WebService
       * @return Iterator with all the org.semanticwb.process.model.WebServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.WebServiceInvoker> listWebServiceInvokerByWebService(org.semanticwb.process.model.WebService value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceInvoker> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_webService,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a WebServiceInvokerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the WebServiceInvoker
   */
    public WebServiceInvokerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property WebService
   * @param value WebService to set
   */

    public void setWebService(org.semanticwb.process.model.WebService value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_webService, value.getSemanticObject());
        }else
        {
            removeWebService();
        }
    }
   /**
   * Remove the value for WebService property
   */

    public void removeWebService()
    {
        getSemanticObject().removeProperty(swp_webService);
    }

   /**
   * Gets the WebService
   * @return a org.semanticwb.process.model.WebService
   */
    public org.semanticwb.process.model.WebService getWebService()
    {
         org.semanticwb.process.model.WebService ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_webService);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.WebService)obj.createGenericInstance();
         }
         return ret;
    }
}
