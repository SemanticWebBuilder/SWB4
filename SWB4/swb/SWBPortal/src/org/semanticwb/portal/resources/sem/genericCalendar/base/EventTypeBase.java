package org.semanticwb.portal.resources.sem.genericCalendar.base;


   /**
   * Catálogo de Tipos de Eventos 
   */
public abstract class EventTypeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
   /**
   * Define la estructura de datos de los eventos a mostrar en el calendario.
   */
    public static final org.semanticwb.platform.SemanticClass genCal_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/GenericCalendar#Event");
    public static final org.semanticwb.platform.SemanticProperty genCal_hasEventInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/GenericCalendar#hasEventInv");
   /**
   * Catálogo de Tipos de Eventos
   */
    public static final org.semanticwb.platform.SemanticClass genCal_EventType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/GenericCalendar#EventType");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/GenericCalendar#EventType");

    public static class ClassMgr
    {
       /**
       * Returns a list of EventType for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.genericCalendar.EventType
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> listEventTypes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.genericCalendar.EventType for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.genericCalendar.EventType
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> listEventTypes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType>(it, true);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @param id Identifier for org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @return A org.semanticwb.portal.resources.sem.genericCalendar.EventType
       */
        public static org.semanticwb.portal.resources.sem.genericCalendar.EventType getEventType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.genericCalendar.EventType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @param id Identifier for org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @return A org.semanticwb.portal.resources.sem.genericCalendar.EventType
       */
        public static org.semanticwb.portal.resources.sem.genericCalendar.EventType createEventType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.genericCalendar.EventType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @param id Identifier for org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       */
        public static void removeEventType(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @param id Identifier for org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @return true if the org.semanticwb.portal.resources.sem.genericCalendar.EventType exists, false otherwise
       */

        public static boolean hasEventType(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEventType(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.EventType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> listEventTypeByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.EventType with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> listEventTypeByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.EventType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> listEventTypeByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.EventType with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> listEventTypeByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.EventType with a determined EventInv
       * @param value EventInv of the type org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> listEventTypeByEventInv(org.semanticwb.portal.resources.sem.genericCalendar.Event value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(genCal_hasEventInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.EventType with a determined EventInv
       * @param value EventInv of the type org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.EventType
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> listEventTypeByEventInv(org.semanticwb.portal.resources.sem.genericCalendar.Event value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.EventType> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(genCal_hasEventInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static EventTypeBase.ClassMgr getEventTypeClassMgr()
    {
        return new EventTypeBase.ClassMgr();
    }

   /**
   * Constructs a EventTypeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the EventType
   */
    public EventTypeBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.portal.resources.sem.genericCalendar.Event
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.genericCalendar.Event
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEventInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event>(getSemanticObject().listObjectProperties(genCal_hasEventInv));
    }

   /**
   * Gets true if has a EventInv
   * @param value org.semanticwb.portal.resources.sem.genericCalendar.Event to verify
   * @return true if the org.semanticwb.portal.resources.sem.genericCalendar.Event exists, false otherwise
   */
    public boolean hasEventInv(org.semanticwb.portal.resources.sem.genericCalendar.Event value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(genCal_hasEventInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the EventInv
   * @return a org.semanticwb.portal.resources.sem.genericCalendar.Event
   */
    public org.semanticwb.portal.resources.sem.genericCalendar.Event getEventInv()
    {
         org.semanticwb.portal.resources.sem.genericCalendar.Event ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(genCal_hasEventInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.genericCalendar.Event)obj.createGenericInstance();
         }
         return ret;
    }
}
