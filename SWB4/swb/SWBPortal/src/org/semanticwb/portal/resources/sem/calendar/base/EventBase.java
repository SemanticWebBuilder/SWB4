package org.semanticwb.portal.resources.sem.calendar.base;


   /**
   * Define la estructura de datos de los eventos a mostrar en el calendario. 
   */
public abstract class EventBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Localeable,org.semanticwb.model.Searchable,org.semanticwb.model.Activeable
{
   /**
   * Fecha final del Evento
   */
    public static final org.semanticwb.platform.SemanticProperty cal_eventEndDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/calendar#eventEndDate");
   /**
   * Almacena la url de una página externa para mostrar el detalle del Evento
   */
    public static final org.semanticwb.platform.SemanticProperty cal_urlExternal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/calendar#urlExternal");
   /**
   * Utilizado para mostrar en una ventana o pestaña distinta a la actual
   */
    public static final org.semanticwb.platform.SemanticProperty cal_newWindow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/calendar#newWindow");
   /**
   * Almacena la foto relacionada al Evento
   */
    public static final org.semanticwb.platform.SemanticProperty cal_image=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/calendar#image");
   /**
   * Visualiza los eventos en una página interna del sitio
   */
    public static final org.semanticwb.platform.SemanticProperty cal_urlInternal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/calendar#urlInternal");
   /**
   * Almacena el detalle del evento
   */
    public static final org.semanticwb.platform.SemanticProperty cal_contentEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/calendar#contentEvent");
   /**
   * Fecha de Inicio del Evento
   */
    public static final org.semanticwb.platform.SemanticProperty cal_eventInitDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/calendar#eventInitDate");
   /**
   * Foto principal, que se visualiza en el detalle del evento
   */
    public static final org.semanticwb.platform.SemanticProperty cal_photoPrincipal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/calendar#photoPrincipal");
   /**
   * Determina si el evento se repite en la misma fecha indicada de inicio y de fin
   */
    public static final org.semanticwb.platform.SemanticProperty cal_periodicity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/calendar#periodicity");
   /**
   * Define la estructura de datos de los eventos a mostrar en el calendario.
   */
    public static final org.semanticwb.platform.SemanticClass cal_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/calendar#Event");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/calendar#Event");

    public static class ClassMgr
    {
       /**
       * Returns a list of Event for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.calendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.Event> listEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.Event>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.calendar.Event for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.calendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.Event> listEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.Event>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.calendar.Event createEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.calendar.Event.ClassMgr.createEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.calendar.Event
       * @param id Identifier for org.semanticwb.portal.resources.sem.calendar.Event
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.Event
       * @return A org.semanticwb.portal.resources.sem.calendar.Event
       */
        public static org.semanticwb.portal.resources.sem.calendar.Event getEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.calendar.Event)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.calendar.Event
       * @param id Identifier for org.semanticwb.portal.resources.sem.calendar.Event
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.Event
       * @return A org.semanticwb.portal.resources.sem.calendar.Event
       */
        public static org.semanticwb.portal.resources.sem.calendar.Event createEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.calendar.Event)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.calendar.Event
       * @param id Identifier for org.semanticwb.portal.resources.sem.calendar.Event
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.Event
       */
        public static void removeEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.calendar.Event
       * @param id Identifier for org.semanticwb.portal.resources.sem.calendar.Event
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.Event
       * @return true if the org.semanticwb.portal.resources.sem.calendar.Event exists, false otherwise
       */

        public static boolean hasEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.Event with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.Event
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.Event> listEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.Event with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.Event> listEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.Event> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.Event with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.Event
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.Event> listEventByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.Event with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.Event> listEventByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.Event> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.Event with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.calendar.Event
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.Event> listEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.calendar.Event with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.calendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.calendar.Event> listEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.calendar.Event> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static EventBase.ClassMgr getEventClassMgr()
    {
        return new EventBase.ClassMgr();
    }

   /**
   * Constructs a EventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Event
   */
    public EventBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the EventEndDate property
* @return String with the EventEndDate
*/
    public String getEventEndDate()
    {
        return getSemanticObject().getProperty(cal_eventEndDate);
    }

/**
* Sets the EventEndDate property
* @param value long with the EventEndDate
*/
    public void setEventEndDate(String value)
    {
        getSemanticObject().setProperty(cal_eventEndDate, value);
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
* Gets the UrlExternal property
* @return String with the UrlExternal
*/
    public String getUrlExternal()
    {
        return getSemanticObject().getProperty(cal_urlExternal);
    }

/**
* Sets the UrlExternal property
* @param value long with the UrlExternal
*/
    public void setUrlExternal(String value)
    {
        getSemanticObject().setProperty(cal_urlExternal, value);
    }

/**
* Gets the NewWindow property
* @return boolean with the NewWindow
*/
    public boolean isNewWindow()
    {
        return getSemanticObject().getBooleanProperty(cal_newWindow);
    }

/**
* Sets the NewWindow property
* @param value long with the NewWindow
*/
    public void setNewWindow(boolean value)
    {
        getSemanticObject().setBooleanProperty(cal_newWindow, value);
    }

/**
* Gets the Image property
* @return String with the Image
*/
    public String getImage()
    {
        return getSemanticObject().getProperty(cal_image);
    }

/**
* Sets the Image property
* @param value long with the Image
*/
    public void setImage(String value)
    {
        getSemanticObject().setProperty(cal_image, value);
    }

/**
* Gets the UrlInternal property
* @return boolean with the UrlInternal
*/
    public boolean isUrlInternal()
    {
        return getSemanticObject().getBooleanProperty(cal_urlInternal);
    }

/**
* Sets the UrlInternal property
* @param value long with the UrlInternal
*/
    public void setUrlInternal(boolean value)
    {
        getSemanticObject().setBooleanProperty(cal_urlInternal, value);
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
   * Sets the value for the property Language
   * @param value Language to set
   */

    public void setLanguage(org.semanticwb.model.Language value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_language, value.getSemanticObject());
        }else
        {
            removeLanguage();
        }
    }
   /**
   * Remove the value for Language property
   */

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(swb_language);
    }

   /**
   * Gets the Language
   * @return a org.semanticwb.model.Language
   */
    public org.semanticwb.model.Language getLanguage()
    {
         org.semanticwb.model.Language ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_language);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Language)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ContentEvent property
* @return String with the ContentEvent
*/
    public String getContentEvent()
    {
        return getSemanticObject().getProperty(cal_contentEvent);
    }

/**
* Sets the ContentEvent property
* @param value long with the ContentEvent
*/
    public void setContentEvent(String value)
    {
        getSemanticObject().setProperty(cal_contentEvent, value);
    }

/**
* Gets the EventInitDate property
* @return String with the EventInitDate
*/
    public String getEventInitDate()
    {
        return getSemanticObject().getProperty(cal_eventInitDate);
    }

/**
* Sets the EventInitDate property
* @param value long with the EventInitDate
*/
    public void setEventInitDate(String value)
    {
        getSemanticObject().setProperty(cal_eventInitDate, value);
    }

/**
* Gets the PhotoPrincipal property
* @return String with the PhotoPrincipal
*/
    public String getPhotoPrincipal()
    {
        return getSemanticObject().getProperty(cal_photoPrincipal);
    }

/**
* Sets the PhotoPrincipal property
* @param value long with the PhotoPrincipal
*/
    public void setPhotoPrincipal(String value)
    {
        getSemanticObject().setProperty(cal_photoPrincipal, value);
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
* Gets the Periodicity property
* @return String with the Periodicity
*/
    public String getPeriodicity()
    {
        return getSemanticObject().getProperty(cal_periodicity);
    }

/**
* Sets the Periodicity property
* @param value long with the Periodicity
*/
    public void setPeriodicity(String value)
    {
        getSemanticObject().setProperty(cal_periodicity, value);
    }
}
