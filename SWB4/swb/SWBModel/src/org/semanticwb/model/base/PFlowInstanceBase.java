package org.semanticwb.model.base;


   /**
   * Instancia de un recurso asociado a un flujo de publicación. 
   */
public abstract class PFlowInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.PFlowable
{
    public static final org.semanticwb.platform.SemanticProperty swb_pfiTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiTime");
   /**
   * Un recurso es un componente en una Página Web con el cual el usuario tiene interacción
   */
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_pfinstResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfinstResource");
   /**
   * Un Flujo de Publicación es una serie de autorizaciones por las que pasa un contenido antes de publicarse en un Sitio Web
   */
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiPFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiPFlow");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiStatus");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiVersion");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiStep");
   /**
   * Instancia de un recurso asociado a un flujo de publicación.
   */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");

    public static class ClassMgr
    {
       /**
       * Returns a list of PFlowInstance for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.PFlowInstance
       */

        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.PFlowInstance for all models
       * @return Iterator of org.semanticwb.model.PFlowInstance
       */

        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance>(it, true);
        }

        public static org.semanticwb.model.PFlowInstance createPFlowInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.PFlowInstance.ClassMgr.createPFlowInstance(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.PFlowInstance
       * @param id Identifier for org.semanticwb.model.PFlowInstance
       * @param model Model of the org.semanticwb.model.PFlowInstance
       * @return A org.semanticwb.model.PFlowInstance
       */
        public static org.semanticwb.model.PFlowInstance getPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PFlowInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.PFlowInstance
       * @param id Identifier for org.semanticwb.model.PFlowInstance
       * @param model Model of the org.semanticwb.model.PFlowInstance
       * @return A org.semanticwb.model.PFlowInstance
       */
        public static org.semanticwb.model.PFlowInstance createPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PFlowInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.PFlowInstance
       * @param id Identifier for org.semanticwb.model.PFlowInstance
       * @param model Model of the org.semanticwb.model.PFlowInstance
       */
        public static void removePFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.PFlowInstance
       * @param id Identifier for org.semanticwb.model.PFlowInstance
       * @param model Model of the org.semanticwb.model.PFlowInstance
       * @return true if the org.semanticwb.model.PFlowInstance exists, false otherwise
       */

        public static boolean hasPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPFlowInstance(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.PFlowInstance with a determined PfinstResource
       * @param value PfinstResource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.PFlowInstance
       * @return Iterator with all the org.semanticwb.model.PFlowInstance
       */

        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstanceByPfinstResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_pfinstResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.PFlowInstance with a determined PfinstResource
       * @param value PfinstResource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.PFlowInstance
       */

        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstanceByPfinstResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_pfinstResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.PFlowInstance with a determined Pflow
       * @param value Pflow of the type org.semanticwb.model.PFlow
       * @param model Model of the org.semanticwb.model.PFlowInstance
       * @return Iterator with all the org.semanticwb.model.PFlowInstance
       */

        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstanceByPflow(org.semanticwb.model.PFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_pfiPFlow, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.PFlowInstance with a determined Pflow
       * @param value Pflow of the type org.semanticwb.model.PFlow
       * @return Iterator with all the org.semanticwb.model.PFlowInstance
       */

        public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstanceByPflow(org.semanticwb.model.PFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_pfiPFlow,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a PFlowInstanceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PFlowInstance
   */
    public PFlowInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Time property
* @return java.util.Date with the Time
*/
    public java.util.Date getTime()
    {
        return getSemanticObject().getDateProperty(swb_pfiTime);
    }

/**
* Sets the Time property
* @param value long with the Time
*/
    public void setTime(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_pfiTime, value);
    }
   /**
   * Sets the value for the property PfinstResource
   * @param value PfinstResource to set
   */

    public void setPfinstResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_pfinstResource, value.getSemanticObject());
        }else
        {
            removePfinstResource();
        }
    }
   /**
   * Remove the value for PfinstResource property
   */

    public void removePfinstResource()
    {
        getSemanticObject().removeProperty(swb_pfinstResource);
    }

   /**
   * Gets the PfinstResource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getPfinstResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_pfinstResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Pflow
   * @param value Pflow to set
   */

    public void setPflow(org.semanticwb.model.PFlow value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_pfiPFlow, value.getSemanticObject());
        }else
        {
            removePflow();
        }
    }
   /**
   * Remove the value for Pflow property
   */

    public void removePflow()
    {
        getSemanticObject().removeProperty(swb_pfiPFlow);
    }

   /**
   * Gets the Pflow
   * @return a org.semanticwb.model.PFlow
   */
    public org.semanticwb.model.PFlow getPflow()
    {
         org.semanticwb.model.PFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_pfiPFlow);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlow)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Status property
* @return int with the Status
*/
    public int getStatus()
    {
        return getSemanticObject().getIntProperty(swb_pfiStatus);
    }

/**
* Sets the Status property
* @param value long with the Status
*/
    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(swb_pfiStatus, value);
    }

/**
* Gets the Version property
* @return int with the Version
*/
    public int getVersion()
    {
        return getSemanticObject().getIntProperty(swb_pfiVersion);
    }

/**
* Sets the Version property
* @param value long with the Version
*/
    public void setVersion(int value)
    {
        getSemanticObject().setIntProperty(swb_pfiVersion, value);
    }

/**
* Gets the Step property
* @return String with the Step
*/
    public String getStep()
    {
        return getSemanticObject().getProperty(swb_pfiStep);
    }

/**
* Sets the Step property
* @param value long with the Step
*/
    public void setStep(String value)
    {
        getSemanticObject().setProperty(swb_pfiStep, value);
    }

   /**
   * Gets the WebSite
   * @return a instance of org.semanticwb.model.WebSite
   */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
