package org.semanticwb.portal.resources.sem.events.base;


public abstract class EventsBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass evnts_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Event");
    public static final org.semanticwb.platform.SemanticProperty evnts_hasEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Events#hasEvent");
    public static final org.semanticwb.platform.SemanticClass evnts_Events=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Events");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Events#Events");

    public EventsBase()
    {
    }

    public EventsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event> listEvents()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.events.Event>(getSemanticObject().listObjectProperties(evnts_hasEvent));
    }

    public boolean hasEvent(org.semanticwb.portal.resources.sem.events.Event value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(evnts_hasEvent,value.getSemanticObject());
        }
        return ret;
    }

    public void addEvent(org.semanticwb.portal.resources.sem.events.Event value)
    {
        getSemanticObject().addObjectProperty(evnts_hasEvent, value.getSemanticObject());
    }

    public void removeAllEvent()
    {
        getSemanticObject().removeProperty(evnts_hasEvent);
    }

    public void removeEvent(org.semanticwb.portal.resources.sem.events.Event value)
    {
        getSemanticObject().removeObjectProperty(evnts_hasEvent,value.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.events.Event getEvent()
    {
         org.semanticwb.portal.resources.sem.events.Event ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(evnts_hasEvent);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.events.Event)obj.createGenericInstance();
         }
         return ret;
    }
}
