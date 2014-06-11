package org.semanticwb.bsc.tracing.base;


   /**
   * Clase que define el valor de un estado en un periodo. Ejemplo: Para el periodo "Enero 2013" - Estado "En Riesgo" 
   */
public abstract class PeriodStatusBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.model.UserGroupable,org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Roleable,org.semanticwb.bsc.Help,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Un estado define la situación de una medición  en un indicador respecto de las metas de su objetivo.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_State=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#State");
    public static final org.semanticwb.platform.SemanticProperty bsc_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#status");
   /**
   * Período de medición.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Period=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Period");
    public static final org.semanticwb.platform.SemanticProperty bsc_period=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#period");
   /**
   * Clase que define el valor de un estado en un periodo. Ejemplo: Para el periodo "Enero 2013" - Estado "En Riesgo"
   */
    public static final org.semanticwb.platform.SemanticClass bsc_PeriodStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#PeriodStatus");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#PeriodStatus");

    public static class ClassMgr
    {
       /**
       * Returns a list of PeriodStatus for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatuses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.PeriodStatus for all models
       * @return Iterator of org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatuses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus>(it, true);
        }

        public static org.semanticwb.bsc.tracing.PeriodStatus createPeriodStatus(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.PeriodStatus.ClassMgr.createPeriodStatus(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.PeriodStatus
       * @param id Identifier for org.semanticwb.bsc.tracing.PeriodStatus
       * @param model Model of the org.semanticwb.bsc.tracing.PeriodStatus
       * @return A org.semanticwb.bsc.tracing.PeriodStatus
       */
        public static org.semanticwb.bsc.tracing.PeriodStatus getPeriodStatus(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.PeriodStatus)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.PeriodStatus
       * @param id Identifier for org.semanticwb.bsc.tracing.PeriodStatus
       * @param model Model of the org.semanticwb.bsc.tracing.PeriodStatus
       * @return A org.semanticwb.bsc.tracing.PeriodStatus
       */
        public static org.semanticwb.bsc.tracing.PeriodStatus createPeriodStatus(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.PeriodStatus)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.PeriodStatus
       * @param id Identifier for org.semanticwb.bsc.tracing.PeriodStatus
       * @param model Model of the org.semanticwb.bsc.tracing.PeriodStatus
       */
        public static void removePeriodStatus(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.PeriodStatus
       * @param id Identifier for org.semanticwb.bsc.tracing.PeriodStatus
       * @param model Model of the org.semanticwb.bsc.tracing.PeriodStatus
       * @return true if the org.semanticwb.bsc.tracing.PeriodStatus exists, false otherwise
       */

        public static boolean hasPeriodStatus(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPeriodStatus(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.PeriodStatus with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.PeriodStatus
       * @return Iterator with all the org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatusByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.PeriodStatus with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatusByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.PeriodStatus with a determined Status
       * @param value Status of the type org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.tracing.PeriodStatus
       * @return Iterator with all the org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatusByStatus(org.semanticwb.bsc.accessory.State value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_status, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.PeriodStatus with a determined Status
       * @param value Status of the type org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatusByStatus(org.semanticwb.bsc.accessory.State value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_status,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.PeriodStatus with a determined Period
       * @param value Period of the type org.semanticwb.bsc.accessory.Period
       * @param model Model of the org.semanticwb.bsc.tracing.PeriodStatus
       * @return Iterator with all the org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatusByPeriod(org.semanticwb.bsc.accessory.Period value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_period, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.PeriodStatus with a determined Period
       * @param value Period of the type org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatusByPeriod(org.semanticwb.bsc.accessory.Period value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_period,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.PeriodStatus with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.tracing.PeriodStatus
       * @return Iterator with all the org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatusByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.PeriodStatus with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatusByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.PeriodStatus with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.PeriodStatus
       * @return Iterator with all the org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatusByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.PeriodStatus with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatusByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.PeriodStatus with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.tracing.PeriodStatus
       * @return Iterator with all the org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatusByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.PeriodStatus with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.tracing.PeriodStatus
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.PeriodStatus> listPeriodStatusByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.PeriodStatus> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PeriodStatusBase.ClassMgr getPeriodStatusClassMgr()
    {
        return new PeriodStatusBase.ClassMgr();
    }

   /**
   * Constructs a PeriodStatusBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PeriodStatus
   */
    public PeriodStatusBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Status
   * @param value Status to set
   */

    public void setStatus(org.semanticwb.bsc.accessory.State value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_status, value.getSemanticObject());
        }else
        {
            removeStatus();
        }
    }
   /**
   * Remove the value for Status property
   */

    public void removeStatus()
    {
        getSemanticObject().removeProperty(bsc_status);
    }

   /**
   * Gets the Status
   * @return a org.semanticwb.bsc.accessory.State
   */
    public org.semanticwb.bsc.accessory.State getStatus()
    {
         org.semanticwb.bsc.accessory.State ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_status);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.State)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Period
   * @param value Period to set
   */

    public void setPeriod(org.semanticwb.bsc.accessory.Period value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_period, value.getSemanticObject());
        }else
        {
            removePeriod();
        }
    }
   /**
   * Remove the value for Period property
   */

    public void removePeriod()
    {
        getSemanticObject().removeProperty(bsc_period);
    }

   /**
   * Gets the Period
   * @return a org.semanticwb.bsc.accessory.Period
   */
    public org.semanticwb.bsc.accessory.Period getPeriod()
    {
         org.semanticwb.bsc.accessory.Period ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_period);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.Period)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
