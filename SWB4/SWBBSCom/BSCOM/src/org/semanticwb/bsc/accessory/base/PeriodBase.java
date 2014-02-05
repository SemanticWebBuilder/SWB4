package org.semanticwb.bsc.accessory.base;


   /**
   * Período de medición. 
   */
public abstract class PeriodBase extends org.semanticwb.bsc.accessory.BSCAccessory implements org.semanticwb.model.Traceable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Undeleteable,org.semanticwb.model.Roleable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Help,org.semanticwb.model.UserGroupable,org.semanticwb.bsc.Machinable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable
{
   /**
   * Indica la fecha incial del periodo representado
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_start=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#start");
   /**
   * Cualquier elemento BSC al que se le puedan asignar períodos de medición
   */
    public static final org.semanticwb.platform.SemanticClass bsc_Seasonable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Seasonable");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasSeasonableInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasSeasonableInv");
   /**
   * Indica la fecha de término del periodo representado
   */
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
       * @param value Previus of the type org.semanticwb.bsc.Machinable
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByPrevius(org.semanticwb.bsc.Machinable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_previus, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Previus
       * @param value Previus of the type org.semanticwb.bsc.Machinable
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByPrevius(org.semanticwb.bsc.Machinable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_previus,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Next
       * @param value Next of the type org.semanticwb.bsc.Machinable
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByNext(org.semanticwb.bsc.Machinable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_next, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Next
       * @param value Next of the type org.semanticwb.bsc.Machinable
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByNext(org.semanticwb.bsc.Machinable value)
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
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Seasonable
       * @param value Seasonable of the type org.semanticwb.bsc.Seasonable
       * @param model Model of the org.semanticwb.bsc.accessory.Period
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodBySeasonable(org.semanticwb.bsc.Seasonable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasSeasonableInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Period with a determined Seasonable
       * @param value Seasonable of the type org.semanticwb.bsc.Seasonable
       * @return Iterator with all the org.semanticwb.bsc.accessory.Period
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Period> listPeriodBySeasonable(org.semanticwb.bsc.Seasonable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Period> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasSeasonableInv,value.getSemanticObject(),sclass));
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
   * Sets the value for the property Previus
   * @param value Previus to set
   */

    public void setPrevius(org.semanticwb.bsc.Machinable value)
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
   * @return a org.semanticwb.bsc.Machinable
   */
    public org.semanticwb.bsc.Machinable getPrevius()
    {
         org.semanticwb.bsc.Machinable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_previus);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.Machinable)obj.createGenericInstance();
         }
         return ret;
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
   * Sets the value for the property Next
   * @param value Next to set
   */

    public void setNext(org.semanticwb.bsc.Machinable value)
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
   * @return a org.semanticwb.bsc.Machinable
   */
    public org.semanticwb.bsc.Machinable getNext()
    {
         org.semanticwb.bsc.Machinable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_next);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.Machinable)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Orden property
* @return int with the Orden
*/
    public int getOrden()
    {
        return getSemanticObject().getIntProperty(bsc_orden);
    }

/**
* Sets the Orden property
* @param value long with the Orden
*/
    public void setOrden(int value)
    {
        getSemanticObject().setIntProperty(bsc_orden, value);
    }
   /**
   * Gets all the org.semanticwb.bsc.Seasonable
   * @return A GenericIterator with all the org.semanticwb.bsc.Seasonable
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Seasonable> listSeasonables()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Seasonable>(getSemanticObject().listObjectProperties(bsc_hasSeasonableInv));
    }

   /**
   * Gets true if has a Seasonable
   * @param value org.semanticwb.bsc.Seasonable to verify
   * @return true if the org.semanticwb.bsc.Seasonable exists, false otherwise
   */
    public boolean hasSeasonable(org.semanticwb.bsc.Seasonable value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasSeasonableInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Seasonable
   * @return a org.semanticwb.bsc.Seasonable
   */
    public org.semanticwb.bsc.Seasonable getSeasonable()
    {
         org.semanticwb.bsc.Seasonable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasSeasonableInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.Seasonable)obj.createGenericInstance();
         }
         return ret;
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
