/**  
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
**/ 
 
package org.semanticwb.portal.resources.sem.events.base;


// TODO: Auto-generated Javadoc
/**
 * The Class EventBase.
 */
public class EventBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    
    /** The Constant swb_created. */
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    
    /** The Constant eve_Events. */
    public static final org.semanticwb.platform.SemanticClass eve_Events=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Events");
    
    /** The Constant eve_EventResource. */
    public static final org.semanticwb.platform.SemanticProperty eve_EventResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventResource");
    
    /** The Constant swb_User. */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    
    /** The Constant swb_modifiedBy. */
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    
    /** The Constant eve_EventTopic. */
    public static final org.semanticwb.platform.SemanticProperty eve_EventTopic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventTopic");
    
    /** The Constant swb_updated. */
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    
    /** The Constant eve_EventDescr. */
    public static final org.semanticwb.platform.SemanticProperty eve_EventDescr=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventDescr");
    
    /** The Constant eve_EventTarget. */
    public static final org.semanticwb.platform.SemanticProperty eve_EventTarget=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventTarget");
    
    /** The Constant eve_EventDate. */
    public static final org.semanticwb.platform.SemanticProperty eve_EventDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventDate");
    
    /** The Constant swb_creator. */
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    
    /** The Constant eve_EventPlace. */
    public static final org.semanticwb.platform.SemanticProperty eve_EventPlace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventPlace");
    
    /** The Constant eve_EventTitle. */
    public static final org.semanticwb.platform.SemanticProperty eve_EventTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventTitle");
    
    /** The Constant eve_Event. */
    public static final org.semanticwb.platform.SemanticClass eve_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Event");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Event");

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
        return org.semanticwb.portal.resources.sem.events.Event.createEvent(String.valueOf(id), model);
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

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreated()
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreated(java.util.Date)
     */
    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    /**
     * Sets the event resource.
     * 
     * @param events the new event resource
     */
    public void setEventResource(org.semanticwb.portal.resources.sem.events.Events events)
    {
        getSemanticObject().setObjectProperty(eve_EventResource, events.getSemanticObject());
    }

    /**
     * Removes the event resource.
     */
    public void removeEventResource()
    {
        getSemanticObject().removeProperty(eve_EventResource);
    }

    /**
     * Gets the event resource.
     * 
     * @return the event resource
     */
    public org.semanticwb.portal.resources.sem.events.Events getEventResource()
    {
         org.semanticwb.portal.resources.sem.events.Events ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(eve_EventResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.events.Events)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setModifiedBy(org.semanticwb.model.User)
     */
    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeModifiedBy()
     */
    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getModifiedBy()
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
     * Gets the event topic.
     * 
     * @return the event topic
     */
    public String getEventTopic()
    {
        return getSemanticObject().getProperty(eve_EventTopic);
    }

    /**
     * Sets the event topic.
     * 
     * @param EventTopic the new event topic
     */
    public void setEventTopic(String EventTopic)
    {
        getSemanticObject().setProperty(eve_EventTopic, EventTopic);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getUpdated()
     */
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setUpdated(java.util.Date)
     */
    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(eve_EventDescr);
    }

    /**
     * Sets the description.
     * 
     * @param EventDescr the new description
     */
    public void setDescription(String EventDescr)
    {
        getSemanticObject().setProperty(eve_EventDescr, EventDescr);
    }

    /**
     * Gets the event target.
     * 
     * @return the event target
     */
    public String getEventTarget()
    {
        return getSemanticObject().getProperty(eve_EventTarget);
    }

    /**
     * Sets the event target.
     * 
     * @param EventTarget the new event target
     */
    public void setEventTarget(String EventTarget)
    {
        getSemanticObject().setProperty(eve_EventTarget, EventTarget);
    }

    /**
     * Gets the date.
     * 
     * @return the date
     */
    public String getDate()
    {
        return getSemanticObject().getProperty(eve_EventDate);
    }

    /**
     * Sets the date.
     * 
     * @param EventDate the new date
     */
    public void setDate(String EventDate)
    {
        getSemanticObject().setProperty(eve_EventDate, EventDate);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#setCreator(org.semanticwb.model.User)
     */
    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#removeCreator()
     */
    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TraceableBase#getCreator()
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
     * Gets the place.
     * 
     * @return the place
     */
    public String getPlace()
    {
        return getSemanticObject().getProperty(eve_EventPlace);
    }

    /**
     * Sets the place.
     * 
     * @param EventPlace the new place
     */
    public void setPlace(String EventPlace)
    {
        getSemanticObject().setProperty(eve_EventPlace, EventPlace);
    }

    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(eve_EventTitle);
    }

    /**
     * Sets the title.
     * 
     * @param EventTitle the new title
     */
    public void setTitle(String EventTitle)
    {
        getSemanticObject().setProperty(eve_EventTitle, EventTitle);
    }
}
