package org.semanticwb.process.base;


public abstract class EventBase extends org.semanticwb.model.base.GenericObjectBase implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.process.FlowObject
{
    public static final org.semanticwb.platform.SemanticClass swbps_Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Event");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Event");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.Event> listEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Event>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Event>(it, true);
        }

        public static org.semanticwb.process.Event getEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.Event)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.Event createEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.Event)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEvent(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEventByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEventByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.Event> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEventByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasFromConnectionObjectInv, hasfromconnectionobjectinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEventByFromConnectionObject(org.semanticwb.process.ConnectionObject hasfromconnectionobjectinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.Event> it=new org.semanticwb.model.GenericIterator(hasfromconnectionobjectinv.getSemanticObject().getModel().listSubjects(swbps_hasFromConnectionObjectInv,hasfromconnectionobjectinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEventByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasFlowObjectInstansInv, hasflowobjectinstansinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEventByFlowObjectInstance(org.semanticwb.process.FlowObjectInstance hasflowobjectinstansinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.Event> it=new org.semanticwb.model.GenericIterator(hasflowobjectinstansinv.getSemanticObject().getModel().listSubjects(swbps_hasFlowObjectInstansInv,hasflowobjectinstansinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEventByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_hasToConnectionObject, hastoconnectionobject.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEventByToConnectionObject(org.semanticwb.process.ConnectionObject hastoconnectionobject)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.Event> it=new org.semanticwb.model.GenericIterator(hastoconnectionobject.getSemanticObject().getModel().listSubjects(swbps_hasToConnectionObject,hastoconnectionobject.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEventByParentProcess(org.semanticwb.process.Process parentprocessinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_parentProcessInv, parentprocessinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEventByParentProcess(org.semanticwb.process.Process parentprocessinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.Event> it=new org.semanticwb.model.GenericIterator(parentprocessinv.getSemanticObject().getModel().listSubjects(swbps_parentProcessInv,parentprocessinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEventByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.Event> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.Event> listEventByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.Event> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }
    }

    public EventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
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

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

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

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> listFromConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject>(getSemanticObject().listObjectProperties(swbps_hasFromConnectionObjectInv));
    }

    public boolean hasFromConnectionObject(org.semanticwb.process.ConnectionObject connectionobject)
    {
        boolean ret=false;
        if(connectionobject!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbps_hasFromConnectionObjectInv,connectionobject.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.process.ConnectionObject getFromConnectionObject()
    {
         org.semanticwb.process.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasFromConnectionObjectInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ConnectionObject)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance> listFlowObjectInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.FlowObjectInstance>(getSemanticObject().listObjectProperties(swbps_hasFlowObjectInstansInv));
    }

    public boolean hasFlowObjectInstance(org.semanticwb.process.FlowObjectInstance flowobjectinstance)
    {
        boolean ret=false;
        if(flowobjectinstance!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbps_hasFlowObjectInstansInv,flowobjectinstance.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.process.FlowObjectInstance getFlowObjectInstance()
    {
         org.semanticwb.process.FlowObjectInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasFlowObjectInstansInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.FlowObjectInstance)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> listToConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject>(getSemanticObject().listObjectProperties(swbps_hasToConnectionObject));
    }

    public boolean hasToConnectionObject(org.semanticwb.process.ConnectionObject connectionobject)
    {
        boolean ret=false;
        if(connectionobject!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbps_hasToConnectionObject,connectionobject.getSemanticObject());
        }
        return ret;
    }

    public void addToConnectionObject(org.semanticwb.process.ConnectionObject value)
    {
        getSemanticObject().addObjectProperty(swbps_hasToConnectionObject, value.getSemanticObject());
    }

    public void removeAllToConnectionObject()
    {
        getSemanticObject().removeProperty(swbps_hasToConnectionObject);
    }

    public void removeToConnectionObject(org.semanticwb.process.ConnectionObject connectionobject)
    {
        getSemanticObject().removeObjectProperty(swbps_hasToConnectionObject,connectionobject.getSemanticObject());
    }

    public org.semanticwb.process.ConnectionObject getToConnectionObject()
    {
         org.semanticwb.process.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_hasToConnectionObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.ConnectionObject)obj.createGenericInstance();
         }
         return ret;
    }

    public void setParentProcess(org.semanticwb.process.Process value)
    {
        getSemanticObject().setObjectProperty(swbps_parentProcessInv, value.getSemanticObject());
    }

    public void removeParentProcess()
    {
        getSemanticObject().removeProperty(swbps_parentProcessInv);
    }

    public org.semanticwb.process.Process getParentProcess()
    {
         org.semanticwb.process.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_parentProcessInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Process)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isValid()
    {
        //Override this method in Event object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

    public void setValid(boolean value)
    {
        //Override this method in Event object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
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

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

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

    public int getY()
    {
        return getSemanticObject().getIntProperty(swbps_y);
    }

    public void setY(int value)
    {
        getSemanticObject().setIntProperty(swbps_y, value);
    }

    public int getX()
    {
        return getSemanticObject().getIntProperty(swbps_x);
    }

    public void setX(int value)
    {
        getSemanticObject().setIntProperty(swbps_x, value);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
