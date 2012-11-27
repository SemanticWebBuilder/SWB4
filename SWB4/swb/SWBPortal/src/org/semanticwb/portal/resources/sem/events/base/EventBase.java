/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.resources.sem.events.base;


// TODO: Auto-generated Javadoc
/**
 * The Class EventBase.
 */
public abstract class EventBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    
    /** The Constant evnts_date. */
    public static final org.semanticwb.platform.SemanticProperty evnts_date=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#date");
    
    /** The Constant evnts_place. */
    public static final org.semanticwb.platform.SemanticProperty evnts_place=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#place");
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant evnts_hasAssistant. */
    public static final org.semanticwb.platform.SemanticProperty evnts_hasAssistant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#hasAssistant");
    
    /** The Constant evnts_duration. */
    public static final org.semanticwb.platform.SemanticProperty evnts_duration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#duration");
    
    /** The Constant evnts_Event. */
    public static final org.semanticwb.platform.SemanticClass evnts_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Event");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Event");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List events.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.events.Event> listEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event>(it, true);
        }

        /**
         * List events.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.events.Event> listEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event>(it, true);
        }

        /**
         * Creates the event.
         * 
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.events. event
         */
        public static org.semanticwb.portal.resources.sem.events.Event createEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.events.Event.ClassMgr.createEvent(String.valueOf(id), model);
        }

        /**
         * Gets the event.
         * 
         * @param id the id
         * @param model the model
         * @return the event
         */
        public static org.semanticwb.portal.resources.sem.events.Event getEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.events.Event)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the event.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.portal.resources.sem.events. event
         */
        public static org.semanticwb.portal.resources.sem.events.Event createEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.events.Event)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the event.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for event.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEvent(id, model)!=null);
        }

        /**
         * List event by modified by.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.events.Event> listEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List event by modified by.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.events.Event> listEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List event by assistant.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.events.Event> listEventByAssistant(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(evnts_hasAssistant, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List event by assistant.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.events.Event> listEventByAssistant(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(evnts_hasAssistant,value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List event by creator.
         * 
         * @param value the value
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.events.Event> listEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        /**
         * List event by creator.
         * 
         * @param value the value
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.portal.resources.sem.events.Event> listEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    /**
     * Instantiates a new event base.
     * 
     * @param base the base
     */
    public EventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the created.
     * 
     * @return the created
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /**
     * Sets the created.
     * 
     * @param value the new created
     */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    /**
     * Sets the modified by.
     * 
     * @param value the new modified by
     */
    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    /**
     * Removes the modified by.
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /**
     * Gets the modified by.
     * 
     * @return the modified by
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
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /**
     * Sets the title.
     * 
     * @param value the new title
     */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    /**
     * Gets the title.
     * 
     * @param lang the lang
     * @return the title
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /**
     * Gets the display title.
     * 
     * @param lang the lang
     * @return the display title
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /**
     * Sets the title.
     * 
     * @param title the title
     * @param lang the lang
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    /**
     * Gets the date.
     * 
     * @return the date
     */
    public long getDate()
    {
        return getSemanticObject().getLongProperty(evnts_date);
    }

    /**
     * Sets the date.
     * 
     * @param value the new date
     */
    public void setDate(long value)
    {
        getSemanticObject().setLongProperty(evnts_date, value);
    }

    /**
     * Gets the updated.
     * 
     * @return the updated
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /**
     * Sets the updated.
     * 
     * @param value the new updated
     */
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    /**
     * Gets the place.
     * 
     * @return the place
     */
    public String getPlace()
    {
        return getSemanticObject().getProperty(evnts_place);
    }

    /**
     * Sets the place.
     * 
     * @param value the new place
     */
    public void setPlace(String value)
    {
        getSemanticObject().setProperty(evnts_place, value);
    }

    /**
     * List assistants.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.User> listAssistants()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(getSemanticObject().listObjectProperties(evnts_hasAssistant));
    }

    /**
     * Checks for assistant.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasAssistant(org.semanticwb.model.User value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(evnts_hasAssistant,value.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the assistant.
     * 
     * @param value the value
     */
    public void addAssistant(org.semanticwb.model.User value)
    {
        getSemanticObject().addObjectProperty(evnts_hasAssistant, value.getSemanticObject());
    }

    /**
     * Removes the all assistant.
     */
    public void removeAllAssistant()
    {
        getSemanticObject().removeProperty(evnts_hasAssistant);
    }

    /**
     * Removes the assistant.
     * 
     * @param value the value
     */
    public void removeAssistant(org.semanticwb.model.User value)
    {
        getSemanticObject().removeObjectProperty(evnts_hasAssistant,value.getSemanticObject());
    }

    /**
     * Gets the assistant.
     * 
     * @return the assistant
     */
    public org.semanticwb.model.User getAssistant()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(evnts_hasAssistant);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the creator.
     * 
     * @param value the new creator
     */
    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    /**
     * Removes the creator.
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /**
     * Gets the creator.
     * 
     * @return the creator
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
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /**
     * Sets the description.
     * 
     * @param value the new description
     */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    /**
     * Gets the description.
     * 
     * @param lang the lang
     * @return the description
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /**
     * Gets the display description.
     * 
     * @param lang the lang
     * @return the display description
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /**
     * Sets the description.
     * 
     * @param description the description
     * @param lang the lang
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    /**
     * Gets the duration.
     * 
     * @return the duration
     */
    public int getDuration()
    {
        return getSemanticObject().getIntProperty(evnts_duration);
    }

    /**
     * Sets the duration.
     * 
     * @param value the new duration
     */
    public void setDuration(int value)
    {
        getSemanticObject().setIntProperty(evnts_duration, value);
    }
}
