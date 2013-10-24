package org.semanticwb.portal.resources.sem.calendar.base;


   /**
   * Almacena la Subscripción de un usuario a un calendario 
   */
public abstract class CalendarSubscriptionBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
   /**
   * Recurso que presenta un calendario de eventos al que se pueden subscribir los usuarios del sistema en base al rol o grupo que tienen asignado.
   */
    public static final org.semanticwb.platform.SemanticClass cal_Calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/calendar#Calendar");
   /**
   * Almacena el calendario al que se subscribe un usuario
   */
    public static final org.semanticwb.platform.SemanticProperty cal_calendarSubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/calendar#calendarSubscription");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
   /**
   * Almacena el usuario subscrito a un Calendario
   */
    public static final org.semanticwb.platform.SemanticProperty cal_userCalendarSubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/calendar#userCalendarSubscription");
   /**
   * Almacena la Subscripción de un usuario a un calendario
   */
    public static final org.semanticwb.platform.SemanticClass cal_CalendarSubscription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/calendar#CalendarSubscription");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/calendar#CalendarSubscription");

    public static class ClassMgr
    {
       /**
       * Returns a list of CalendarSubscription for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> listCalendarSubscriptions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.calendar.CalendarSubscription for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> listCalendarSubscriptions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.calendar.CalendarSubscription createCalendarSubscription(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.calendar.CalendarSubscription.ClassMgr.createCalendarSubscription(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @param id Identifier for org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @return A org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */
        public static org.semanticwb.portal.resources.sem.calendar.CalendarSubscription getCalendarSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.calendar.CalendarSubscription)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @param id Identifier for org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @return A org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */
        public static org.semanticwb.portal.resources.sem.calendar.CalendarSubscription createCalendarSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.calendar.CalendarSubscription)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @param id Identifier for org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */
        public static void removeCalendarSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @param id Identifier for org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @return true if the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription exists, false otherwise
       */

        public static boolean hasCalendarSubscription(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCalendarSubscription(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.CalendarSubscription with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> listCalendarSubscriptionByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.CalendarSubscription with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> listCalendarSubscriptionByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.CalendarSubscription with a determined CalendarSubscription
       * @param value CalendarSubscription of the type org.semanticwb.portal.resources.sem.calendar.Calendar
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> listCalendarSubscriptionByCalendarSubscription(org.semanticwb.portal.resources.sem.calendar.Calendar value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(cal_calendarSubscription, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.CalendarSubscription with a determined CalendarSubscription
       * @param value CalendarSubscription of the type org.semanticwb.portal.resources.sem.calendar.Calendar
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> listCalendarSubscriptionByCalendarSubscription(org.semanticwb.portal.resources.sem.calendar.Calendar value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(cal_calendarSubscription,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.CalendarSubscription with a determined UserCalendarSubscription
       * @param value UserCalendarSubscription of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> listCalendarSubscriptionByUserCalendarSubscription(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(cal_userCalendarSubscription, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.CalendarSubscription with a determined UserCalendarSubscription
       * @param value UserCalendarSubscription of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> listCalendarSubscriptionByUserCalendarSubscription(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(cal_userCalendarSubscription,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.CalendarSubscription with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> listCalendarSubscriptionByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.CalendarSubscription with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.CalendarSubscription
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> listCalendarSubscriptionByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.CalendarSubscription> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CalendarSubscriptionBase.ClassMgr getCalendarSubscriptionClassMgr()
    {
        return new CalendarSubscriptionBase.ClassMgr();
    }

   /**
   * Constructs a CalendarSubscriptionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CalendarSubscription
   */
    public CalendarSubscriptionBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property CalendarSubscription
   * @param value CalendarSubscription to set
   */

    public void setCalendarSubscription(org.semanticwb.portal.resources.sem.calendar.Calendar value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(cal_calendarSubscription, value.getSemanticObject());
        }else
        {
            removeCalendarSubscription();
        }
    }
   /**
   * Remove the value for CalendarSubscription property
   */

    public void removeCalendarSubscription()
    {
        getSemanticObject().removeProperty(cal_calendarSubscription);
    }

   /**
   * Gets the CalendarSubscription
   * @return a org.semanticwb.portal.resources.sem.calendar.Calendar
   */
    public org.semanticwb.portal.resources.sem.calendar.Calendar getCalendarSubscription()
    {
         org.semanticwb.portal.resources.sem.calendar.Calendar ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(cal_calendarSubscription);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.calendar.Calendar)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property UserCalendarSubscription
   * @param value UserCalendarSubscription to set
   */

    public void setUserCalendarSubscription(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(cal_userCalendarSubscription, value.getSemanticObject());
        }else
        {
            removeUserCalendarSubscription();
        }
    }
   /**
   * Remove the value for UserCalendarSubscription property
   */

    public void removeUserCalendarSubscription()
    {
        getSemanticObject().removeProperty(cal_userCalendarSubscription);
    }

   /**
   * Gets the UserCalendarSubscription
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getUserCalendarSubscription()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(cal_userCalendarSubscription);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
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
}
