package org.semanticwb.portal.resources.sem.events.base;


public class EventBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass eve_Events=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Events");
    public static final org.semanticwb.platform.SemanticProperty eve_EventResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventResource");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticProperty eve_EventDescr=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventDescr");
    public static final org.semanticwb.platform.SemanticProperty eve_EventTarget=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventTarget");
    public static final org.semanticwb.platform.SemanticProperty eve_EventDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventDate");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticProperty eve_EventPlace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventPlace");
    public static final org.semanticwb.platform.SemanticProperty eve_EventTitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#EventTitle");
    public static final org.semanticwb.platform.SemanticClass eve_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Event");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Event");

    public EventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.resources.sem.events.Event> listEvents(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.resources.sem.events.Event> listEvents()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event>(it, true);
    }

    public static org.semanticwb.portal.resources.sem.events.Event createEvent(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.resources.sem.events.Event.createEvent(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.resources.sem.events.Event getEvent(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.resources.sem.events.Event)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.resources.sem.events.Event createEvent(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.resources.sem.events.Event)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeEvent(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasEvent(String id, org.semanticwb.model.SWBModel model)
    {
        return (getEvent(id, model)!=null);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(swb_created, created);
    }

    public void setEventResource(org.semanticwb.portal.resources.sem.events.Events events)
    {
        getSemanticObject().setObjectProperty(eve_EventResource, events.getSemanticObject());
    }

    public void removeEventResource()
    {
        getSemanticObject().removeProperty(eve_EventResource);
    }

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

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

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

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date updated)
    {
        getSemanticObject().setDateProperty(swb_updated, updated);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(eve_EventDescr);
    }

    public void setDescription(String EventDescr)
    {
        getSemanticObject().setProperty(eve_EventDescr, EventDescr);
    }

    public String getEventTarget()
    {
        return getSemanticObject().getProperty(eve_EventTarget);
    }

    public void setEventTarget(String EventTarget)
    {
        getSemanticObject().setProperty(eve_EventTarget, EventTarget);
    }

    public String getDate()
    {
        return getSemanticObject().getProperty(eve_EventDate);
    }

    public void setDate(String EventDate)
    {
        getSemanticObject().setProperty(eve_EventDate, EventDate);
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(swb_creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

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

    public String getPlace()
    {
        return getSemanticObject().getProperty(eve_EventPlace);
    }

    public void setPlace(String EventPlace)
    {
        getSemanticObject().setProperty(eve_EventPlace, EventPlace);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(eve_EventTitle);
    }

    public void setTitle(String EventTitle)
    {
        getSemanticObject().setProperty(eve_EventTitle, EventTitle);
    }
}
