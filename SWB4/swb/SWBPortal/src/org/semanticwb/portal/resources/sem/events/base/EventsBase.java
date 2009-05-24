package org.semanticwb.portal.resources.sem.events.base;


public class EventsBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass eve_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Event");
    public static final org.semanticwb.platform.SemanticProperty eve_hasEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#hasEvent");
    public static final org.semanticwb.platform.SemanticClass eve_Events=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Events");

    public EventsBase()
    {
    }

    public EventsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Events");

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event> listEvents()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event>(getSemanticObject().listObjectProperties(eve_hasEvent));
    }

    public boolean hasEvent(org.semanticwb.portal.resources.sem.events.Event event)
    {
        if(event==null)return false;        return getSemanticObject().hasObjectProperty(eve_hasEvent,event.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.events.Event getEvent()
    {
         org.semanticwb.portal.resources.sem.events.Event ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(eve_hasEvent);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.events.Event)obj.createGenericInstance();
         }
         return ret;
    }
}
