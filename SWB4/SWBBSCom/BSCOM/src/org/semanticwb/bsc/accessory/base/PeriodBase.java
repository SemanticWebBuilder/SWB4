package org.semanticwb.bsc.accessory.base;


   /**
   * Período de medición. 
   */
public abstract class PeriodBase extends org.semanticwb.bsc.accessory.BSCAccessory implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable,org.semanticwb.bsc.StateMachinable,org.semanticwb.model.Sortable,org.semanticwb.bsc.Blockeable,org.semanticwb.model.Traceable
{
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
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Previus
       * @param value Previus of the type org.semanticwb.bsc.StateMachinable
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByPrevius(org.semanticwb.bsc.StateMachinable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_previus, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Previus
       * @param value Previus of the type org.semanticwb.bsc.StateMachinable
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByPrevius(org.semanticwb.bsc.StateMachinable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_previus,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Next
       * @param value Next of the type org.semanticwb.bsc.StateMachinable
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByNext(org.semanticwb.bsc.StateMachinable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_next, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Next
       * @param value Next of the type org.semanticwb.bsc.StateMachinable
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByNext(org.semanticwb.bsc.StateMachinable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_next,value.getSemanticObject(),sclass));
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
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }
   /**
   * Sets the value for the property Previus
   * @param value Previus to set
   */

    public void setPrevius(org.semanticwb.bsc.StateMachinable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_previus, value.getSemanticObject());
        }else
        {
            removePrevius();
        }
    }
   /**
   * Remove the value for Previus property
   */

    public void removePrevius()
    {
        getSemanticObject().removeProperty(bsc_previus);
    }

   /**
   * Gets the Previus
   * @return a org.semanticwb.bsc.StateMachinable
   */
    public org.semanticwb.bsc.StateMachinable getPrevius()
    {
         org.semanticwb.bsc.StateMachinable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_previus);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.StateMachinable)obj.createGenericInstance();
         }
         return ret;
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
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }
   /**
   * Sets the value for the property Next
   * @param value Next to set
   */

    public void setNext(org.semanticwb.bsc.StateMachinable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_next, value.getSemanticObject());
        }else
        {
            removeNext();
        }
    }
   /**
   * Remove the value for Next property
   */

    public void removeNext()
    {
        getSemanticObject().removeProperty(bsc_next);
    }

   /**
   * Gets the Next
   * @return a org.semanticwb.bsc.StateMachinable
   */
    public org.semanticwb.bsc.StateMachinable getNext()
    {
         org.semanticwb.bsc.StateMachinable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_next);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.StateMachinable)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
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
