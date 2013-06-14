package org.semanticwb.bsc.element.base;


public abstract class MeasureBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass bsc_State=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#State");
    public static final org.semanticwb.platform.SemanticProperty bsc_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#status");
   /**
   * Período de medición.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Period=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Period");
    public static final org.semanticwb.platform.SemanticProperty bsc_period=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#period");
    public static final org.semanticwb.platform.SemanticClass bsc_Measure=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Measure");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Measure");

    public static class ClassMgr
    {
       /**
       * Returns a list of Measure for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasures(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Measure for all models
       * @return Iterator of org.semanticwb.bsc.element.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasures()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure>(it, true);
        }

        public static org.semanticwb.bsc.element.Measure createMeasure(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Measure.ClassMgr.createMeasure(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Measure
       * @param id Identifier for org.semanticwb.bsc.element.Measure
       * @param model Model of the org.semanticwb.bsc.element.Measure
       * @return A org.semanticwb.bsc.element.Measure
       */
        public static org.semanticwb.bsc.element.Measure getMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Measure)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Measure
       * @param id Identifier for org.semanticwb.bsc.element.Measure
       * @param model Model of the org.semanticwb.bsc.element.Measure
       * @return A org.semanticwb.bsc.element.Measure
       */
        public static org.semanticwb.bsc.element.Measure createMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Measure)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Measure
       * @param id Identifier for org.semanticwb.bsc.element.Measure
       * @param model Model of the org.semanticwb.bsc.element.Measure
       */
        public static void removeMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Measure
       * @param id Identifier for org.semanticwb.bsc.element.Measure
       * @param model Model of the org.semanticwb.bsc.element.Measure
       * @return true if the org.semanticwb.bsc.element.Measure exists, false otherwise
       */

        public static boolean hasMeasure(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMeasure(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Measure with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Measure
       * @return Iterator with all the org.semanticwb.bsc.element.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasureByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Measure with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasureByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Measure with a determined Status
       * @param value Status of the type org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.element.Measure
       * @return Iterator with all the org.semanticwb.bsc.element.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasureByStatus(org.semanticwb.bsc.accessory.State value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_status, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Measure with a determined Status
       * @param value Status of the type org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.element.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasureByStatus(org.semanticwb.bsc.accessory.State value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_status,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Measure with a determined Period
       * @param value Period of the type org.semanticwb.bsc.accessory.Period
       * @param model Model of the org.semanticwb.bsc.element.Measure
       * @return Iterator with all the org.semanticwb.bsc.element.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasureByPeriod(org.semanticwb.bsc.accessory.Period value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_period, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Measure with a determined Period
       * @param value Period of the type org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.element.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasureByPeriod(org.semanticwb.bsc.accessory.Period value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_period,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Measure with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Measure
       * @return Iterator with all the org.semanticwb.bsc.element.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasureByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Measure with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Measure
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Measure> listMeasureByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a MeasureBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Measure
   */
    public MeasureBase(org.semanticwb.platform.SemanticObject base)
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
