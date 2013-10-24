package org.semanticwb.portal.resources.sem.genericCalendar.base;


   /**
   * Define la estructura de datos de los eventos a mostrar en el calendario. 
   */
public abstract class EventBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Localeable,org.semanticwb.model.Resourceable,org.semanticwb.model.Searchable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
   /**
   * Fecha final del Evento
   */
    public static final org.semanticwb.platform.SemanticProperty genCal_eventEndDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/GenericCalendar#eventEndDate");
   /**
   * Almacena la url de una página externa para mostrar el detalle del Evento
   */
    public static final org.semanticwb.platform.SemanticProperty genCal_urlExternal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/GenericCalendar#urlExternal");
   /**
   * Catálogo de Tipos de Eventos
   */
    public static final org.semanticwb.platform.SemanticClass genCal_EventType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/GenericCalendar#EventType");
    public static final org.semanticwb.platform.SemanticProperty genCal_evType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/GenericCalendar#evType");
   /**
   * Utilizado para mostrar en una ventana o pestaña distinta a la actual
   */
    public static final org.semanticwb.platform.SemanticProperty genCal_newWindow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/GenericCalendar#newWindow");
   /**
   * Visualiza los eventos en una página interna del sitio
   */
    public static final org.semanticwb.platform.SemanticProperty genCal_urlInternal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/GenericCalendar#urlInternal");
   /**
   * Fecha de Inicio del Evento
   */
    public static final org.semanticwb.platform.SemanticProperty genCal_eventInitDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/GenericCalendar#eventInitDate");
   /**
   * Foto principal, que se visualiza en el detalle del evento
   */
    public static final org.semanticwb.platform.SemanticProperty genCal_mainImage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/GenericCalendar#mainImage");
   /**
   * Almacena la foto relacionada al Evento
   */
    public static final org.semanticwb.platform.SemanticProperty genCal_tooltipImage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/GenericCalendar#tooltipImage");
   /**
   * Determina si el evento se repite en la misma fecha indicada de inicio y de fin
   */
    public static final org.semanticwb.platform.SemanticProperty genCal_periodicity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/GenericCalendar#periodicity");
   /**
   * Define la estructura de datos de los eventos a mostrar en el calendario.
   */
    public static final org.semanticwb.platform.SemanticClass genCal_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/GenericCalendar#Event");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/GenericCalendar#Event");

    public static class ClassMgr
    {
       /**
       * Returns a list of Event for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.genericCalendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.genericCalendar.Event for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.genericCalendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.genericCalendar.Event createEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.genericCalendar.Event.ClassMgr.createEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @param id Identifier for org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @return A org.semanticwb.portal.resources.sem.genericCalendar.Event
       */
        public static org.semanticwb.portal.resources.sem.genericCalendar.Event getEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.genericCalendar.Event)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @param id Identifier for org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @return A org.semanticwb.portal.resources.sem.genericCalendar.Event
       */
        public static org.semanticwb.portal.resources.sem.genericCalendar.Event createEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.genericCalendar.Event)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @param id Identifier for org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.Event
       */
        public static void removeEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @param id Identifier for org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @return true if the org.semanticwb.portal.resources.sem.genericCalendar.Event exists, false otherwise
       */

        public static boolean hasEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.Event with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.Event with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.Event with a determined EvType
       * @param value EvType of the type org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEventByEvType(org.semanticwb.portal.resources.sem.genericCalendar.EventType value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(genCal_evType, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.Event with a determined EvType
       * @param value EvType of the type org.semanticwb.portal.resources.sem.genericCalendar.EventType
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEventByEvType(org.semanticwb.portal.resources.sem.genericCalendar.EventType value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(genCal_evType,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.Event with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEventByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.Event with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEventByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.Event with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.Event with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.Event with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.portal.resources.sem.genericCalendar.Event
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEventByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.genericCalendar.Event with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.portal.resources.sem.genericCalendar.Event
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> listEventByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.genericCalendar.Event> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
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
        return getSemanticObject().getProperty(genCal_eventEndDate);
    }

/**
* Sets the EventEndDate property
* @param value long with the EventEndDate
*/
    public void setEventEndDate(String value)
    {
        getSemanticObject().setProperty(genCal_eventEndDate, value);
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
        return getSemanticObject().getProperty(genCal_urlExternal);
    }

/**
* Sets the UrlExternal property
* @param value long with the UrlExternal
*/
    public void setUrlExternal(String value)
    {
        getSemanticObject().setProperty(genCal_urlExternal, value);
    }
   /**
   * Sets the value for the property EvType
   * @param value EvType to set
   */

    public void setEvType(org.semanticwb.portal.resources.sem.genericCalendar.EventType value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(genCal_evType, value.getSemanticObject());
        }else
        {
            removeEvType();
        }
    }
   /**
   * Remove the value for EvType property
   */

    public void removeEvType()
    {
        getSemanticObject().removeProperty(genCal_evType);
    }

   /**
   * Gets the EvType
   * @return a org.semanticwb.portal.resources.sem.genericCalendar.EventType
   */
    public org.semanticwb.portal.resources.sem.genericCalendar.EventType getEvType()
    {
         org.semanticwb.portal.resources.sem.genericCalendar.EventType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(genCal_evType);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.genericCalendar.EventType)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the NewWindow property
* @return boolean with the NewWindow
*/
    public boolean isNewWindow()
    {
        return getSemanticObject().getBooleanProperty(genCal_newWindow);
    }

/**
* Sets the NewWindow property
* @param value long with the NewWindow
*/
    public void setNewWindow(boolean value)
    {
        getSemanticObject().setBooleanProperty(genCal_newWindow, value);
    }

/**
* Gets the UrlInternal property
* @return boolean with the UrlInternal
*/
    public boolean isUrlInternal()
    {
        return getSemanticObject().getBooleanProperty(genCal_urlInternal);
    }

/**
* Sets the UrlInternal property
* @param value long with the UrlInternal
*/
    public void setUrlInternal(boolean value)
    {
        getSemanticObject().setBooleanProperty(genCal_urlInternal, value);
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
* Gets the EventInitDate property
* @return String with the EventInitDate
*/
    public String getEventInitDate()
    {
        return getSemanticObject().getProperty(genCal_eventInitDate);
    }

/**
* Sets the EventInitDate property
* @param value long with the EventInitDate
*/
    public void setEventInitDate(String value)
    {
        getSemanticObject().setProperty(genCal_eventInitDate, value);
    }

/**
* Gets the MainImage property
* @return String with the MainImage
*/
    public String getMainImage()
    {
        return getSemanticObject().getProperty(genCal_mainImage);
    }

/**
* Sets the MainImage property
* @param value long with the MainImage
*/
    public void setMainImage(String value)
    {
        getSemanticObject().setProperty(genCal_mainImage, value);
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
   * Gets all the org.semanticwb.model.Resource
   * @return A GenericIterator with all the org.semanticwb.model.Resource
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> listResources()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(getSemanticObject().listObjectProperties(swb_hasResource));
    }

   /**
   * Gets true if has a Resource
   * @param value org.semanticwb.model.Resource to verify
   * @return true if the org.semanticwb.model.Resource exists, false otherwise
   */
    public boolean hasResource(org.semanticwb.model.Resource value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasResource,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Resource
   * @param value org.semanticwb.model.Resource to add
   */

    public void addResource(org.semanticwb.model.Resource value)
    {
        getSemanticObject().addObjectProperty(swb_hasResource, value.getSemanticObject());
    }
   /**
   * Removes all the Resource
   */

    public void removeAllResource()
    {
        getSemanticObject().removeProperty(swb_hasResource);
    }
   /**
   * Removes a Resource
   * @param value org.semanticwb.model.Resource to remove
   */

    public void removeResource(org.semanticwb.model.Resource value)
    {
        getSemanticObject().removeObjectProperty(swb_hasResource,value.getSemanticObject());
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the TooltipImage property
* @return String with the TooltipImage
*/
    public String getTooltipImage()
    {
        return getSemanticObject().getProperty(genCal_tooltipImage);
    }

/**
* Sets the TooltipImage property
* @param value long with the TooltipImage
*/
    public void setTooltipImage(String value)
    {
        getSemanticObject().setProperty(genCal_tooltipImage, value);
    }

/**
* Gets the Periodicity property
* @return String with the Periodicity
*/
    public String getPeriodicity()
    {
        return getSemanticObject().getProperty(genCal_periodicity);
    }

/**
* Sets the Periodicity property
* @param value long with the Periodicity
*/
    public void setPeriodicity(String value)
    {
        getSemanticObject().setProperty(genCal_periodicity, value);
    }
}
