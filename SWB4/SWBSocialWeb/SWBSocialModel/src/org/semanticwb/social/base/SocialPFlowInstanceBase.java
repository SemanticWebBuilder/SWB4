package org.semanticwb.social.base;


   /**
   * Instancia de un recurso asociado a un flujo de publicación. 
   */
public abstract class SocialPFlowInstanceBase extends org.semanticwb.social.SocialPFlows implements org.semanticwb.social.SocialPFlowable
{
    public static final org.semanticwb.platform.SemanticProperty social_pfiStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pfiStatus");
    public static final org.semanticwb.platform.SemanticProperty social_pfiTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pfiTime");
    public static final org.semanticwb.platform.SemanticProperty social_pfiStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pfiStep");
   /**
   * Un Flujo de Publicación es una serie de autorizaciones por las que pasa un contenido antes de publicarse en un Sitio Web
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlow");
    public static final org.semanticwb.platform.SemanticProperty social_pfiPFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pfiPFlow");
    public static final org.semanticwb.platform.SemanticProperty social_pfiVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pfiVersion");
   /**
   * Instancia de un recurso asociado a un flujo de publicación.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlowInstance");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlowInstance");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialPFlowInstance for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialPFlowInstance
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlowInstance> listSocialPFlowInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowInstance>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialPFlowInstance for all models
       * @return Iterator of org.semanticwb.social.SocialPFlowInstance
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlowInstance> listSocialPFlowInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowInstance>(it, true);
        }

        public static org.semanticwb.social.SocialPFlowInstance createSocialPFlowInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialPFlowInstance.ClassMgr.createSocialPFlowInstance(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialPFlowInstance
       * @param id Identifier for org.semanticwb.social.SocialPFlowInstance
       * @param model Model of the org.semanticwb.social.SocialPFlowInstance
       * @return A org.semanticwb.social.SocialPFlowInstance
       */
        public static org.semanticwb.social.SocialPFlowInstance getSocialPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialPFlowInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialPFlowInstance
       * @param id Identifier for org.semanticwb.social.SocialPFlowInstance
       * @param model Model of the org.semanticwb.social.SocialPFlowInstance
       * @return A org.semanticwb.social.SocialPFlowInstance
       */
        public static org.semanticwb.social.SocialPFlowInstance createSocialPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialPFlowInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialPFlowInstance
       * @param id Identifier for org.semanticwb.social.SocialPFlowInstance
       * @param model Model of the org.semanticwb.social.SocialPFlowInstance
       */
        public static void removeSocialPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialPFlowInstance
       * @param id Identifier for org.semanticwb.social.SocialPFlowInstance
       * @param model Model of the org.semanticwb.social.SocialPFlowInstance
       * @return true if the org.semanticwb.social.SocialPFlowInstance exists, false otherwise
       */

        public static boolean hasSocialPFlowInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialPFlowInstance(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialPFlowInstance with a determined Pflow
       * @param value Pflow of the type org.semanticwb.social.SocialPFlow
       * @param model Model of the org.semanticwb.social.SocialPFlowInstance
       * @return Iterator with all the org.semanticwb.social.SocialPFlowInstance
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlowInstance> listSocialPFlowInstanceByPflow(org.semanticwb.social.SocialPFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_pfiPFlow, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialPFlowInstance with a determined Pflow
       * @param value Pflow of the type org.semanticwb.social.SocialPFlow
       * @return Iterator with all the org.semanticwb.social.SocialPFlowInstance
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlowInstance> listSocialPFlowInstanceByPflow(org.semanticwb.social.SocialPFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_pfiPFlow,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialPFlowInstanceBase.ClassMgr getSocialPFlowInstanceClassMgr()
    {
        return new SocialPFlowInstanceBase.ClassMgr();
    }

   /**
   * Constructs a SocialPFlowInstanceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialPFlowInstance
   */
    public SocialPFlowInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Status property
* @return int with the Status
*/
    public int getStatus()
    {
        return getSemanticObject().getIntProperty(social_pfiStatus);
    }

/**
* Sets the Status property
* @param value long with the Status
*/
    public void setStatus(int value)
    {
        getSemanticObject().setIntProperty(social_pfiStatus, value);
    }

/**
* Gets the Time property
* @return java.util.Date with the Time
*/
    public java.util.Date getTime()
    {
        return getSemanticObject().getDateProperty(social_pfiTime);
    }

/**
* Sets the Time property
* @param value long with the Time
*/
    public void setTime(java.util.Date value)
    {
        getSemanticObject().setDateProperty(social_pfiTime, value);
    }

/**
* Gets the Step property
* @return String with the Step
*/
    public String getStep()
    {
        return getSemanticObject().getProperty(social_pfiStep);
    }

/**
* Sets the Step property
* @param value long with the Step
*/
    public void setStep(String value)
    {
        getSemanticObject().setProperty(social_pfiStep, value);
    }
   /**
   * Sets the value for the property Pflow
   * @param value Pflow to set
   */

    public void setPflow(org.semanticwb.social.SocialPFlow value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_pfiPFlow, value.getSemanticObject());
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
        getSemanticObject().removeProperty(social_pfiPFlow);
    }

   /**
   * Gets the Pflow
   * @return a org.semanticwb.social.SocialPFlow
   */
    public org.semanticwb.social.SocialPFlow getPflow()
    {
         org.semanticwb.social.SocialPFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_pfiPFlow);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialPFlow)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Version property
* @return int with the Version
*/
    public int getVersion()
    {
        return getSemanticObject().getIntProperty(social_pfiVersion);
    }

/**
* Sets the Version property
* @param value long with the Version
*/
    public void setVersion(int value)
    {
        getSemanticObject().setIntProperty(social_pfiVersion, value);
    }

   /**
   * Gets the SocialSite
   * @return a instance of org.semanticwb.social.SocialSite
   */
    public org.semanticwb.social.SocialSite getSocialSite()
    {
        return (org.semanticwb.social.SocialSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
