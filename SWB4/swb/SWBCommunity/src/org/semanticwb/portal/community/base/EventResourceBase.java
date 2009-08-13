package org.semanticwb.portal.community.base;


public class EventResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_EventElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#EventElement");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasEvent");
    public static final org.semanticwb.platform.SemanticClass swbcomm_EventResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#EventResource");

    public EventResourceBase()
    {
    }

    public EventResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#EventResource");

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement> listEvents()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement>(getSemanticObject().listObjectProperties(swbcomm_hasEvent));
    }

    public boolean hasEvent(org.semanticwb.portal.community.EventElement eventelement)
    {
        if(eventelement==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasEvent,eventelement.getSemanticObject());
    }

    public void addEvent(org.semanticwb.portal.community.EventElement eventelement)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasEvent, eventelement.getSemanticObject());
    }

    public void removeAllEvent()
    {
        getSemanticObject().removeProperty(swbcomm_hasEvent);
    }

    public void removeEvent(org.semanticwb.portal.community.EventElement eventelement)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasEvent,eventelement.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.EventResource> listEventResourceByHasEvent(org.semanticwb.portal.community.EventElement hasevent,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasEvent, hasevent.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.EventResource> listEventResourceByHasEvent(org.semanticwb.portal.community.EventElement hasevent)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventResource> it=new org.semanticwb.model.GenericIterator(hasevent.getSemanticObject().getModel().listSubjects(swbcomm_hasEvent,hasevent.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.EventElement getEvent()
    {
         org.semanticwb.portal.community.EventElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasEvent);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.EventElement)obj.createGenericInstance();
         }
         return ret;
    }
}
