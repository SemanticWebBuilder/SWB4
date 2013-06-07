package org.semanticwb.bsc.accessory.base;


   /**
   * Período de medición. 
   */
public abstract class PeriodBase extends org.semanticwb.bsc.accessory.BSCAccessory implements org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.bsc.Blockable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty bsc_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#index");
    public static final org.semanticwb.platform.SemanticProperty bsc_inTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#inTime");
    public static final org.semanticwb.platform.SemanticProperty bsc_start=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#start");
    public static final org.semanticwb.platform.SemanticProperty bsc_end=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#end");
   /**
   * Período de medición.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Period=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Period");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Period");

    public static class ClassMgr
    {
       /**
       * Returns a list of Period for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriods(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.accessory.Period for all models
       * @return Iterator of org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriods()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period>(it, true);
        }

        public static org.semanticwb.bsc.accessory.Period createPeriod(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.accessory.Period.ClassMgr.createPeriod(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.accessory.Period
       * @param id Identifier for org.semanticwb.bsc.accessory.Period
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       * @return A org.semanticwb.bsc.accessory.Period
       */
        public static org.semanticwb.bsc.accessory.Period getPeriod(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.Period)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.accessory.Period
       * @param id Identifier for org.semanticwb.bsc.accessory.Period
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       * @return A org.semanticwb.bsc.accessory.Period
       */
        public static org.semanticwb.bsc.accessory.Period createPeriod(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.Period)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.accessory.Period
       * @param id Identifier for org.semanticwb.bsc.accessory.Period
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       */
        public static void removePeriod(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.accessory.Period
       * @param id Identifier for org.semanticwb.bsc.accessory.Period
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       * @return true if the org.semanticwb.bsc.accessory.Period exists, false otherwise
       */

        public static boolean hasPeriod(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPeriod(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PeriodBase.ClassMgr getPeriodClassMgr()
    {
        return new PeriodBase.ClassMgr();
    }

   /**
   * Constructs a PeriodBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Period
   */
    public PeriodBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(bsc_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(bsc_index, value);
    }

/**
* Gets the InTime property
* @return boolean with the InTime
*/
    public boolean isInTime()
    {
        return getSemanticObject().getBooleanProperty(bsc_inTime);
    }

/**
* Sets the InTime property
* @param value long with the InTime
*/
    public void setInTime(boolean value)
    {
        getSemanticObject().setBooleanProperty(bsc_inTime, value);
    }

/**
* Gets the Start property
* @return java.util.Date with the Start
*/
    public java.util.Date getStart()
    {
        return getSemanticObject().getDateProperty(bsc_start);
    }

/**
* Sets the Start property
* @param value long with the Start
*/
    public void setStart(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_start, value);
    }

/**
* Gets the Blocked property
* @return boolean with the Blocked
*/
    public boolean isBlocked()
    {
        return getSemanticObject().getBooleanProperty(bsc_blocked);
    }

/**
* Sets the Blocked property
* @param value long with the Blocked
*/
    public void setBlocked(boolean value)
    {
        getSemanticObject().setBooleanProperty(bsc_blocked, value);
    }

/**
* Gets the End property
* @return java.util.Date with the End
*/
    public java.util.Date getEnd()
    {
        return getSemanticObject().getDateProperty(bsc_end);
    }

/**
* Sets the End property
* @param value long with the End
*/
    public void setEnd(java.util.Date value)
    {
        getSemanticObject().setDateProperty(bsc_end, value);
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
