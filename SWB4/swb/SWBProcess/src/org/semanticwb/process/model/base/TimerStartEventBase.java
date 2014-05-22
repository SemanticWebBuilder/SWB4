package org.semanticwb.process.model.base;


public abstract class TimerStartEventBase extends org.semanticwb.process.model.StartEventNode implements org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.ProcessPeriodRefable
{
    public static final org.semanticwb.platform.SemanticClass swp_TimerStartEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TimerStartEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TimerStartEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of TimerStartEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.TimerStartEvent for all models
       * @return Iterator of org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent>(it, true);
        }

        public static org.semanticwb.process.model.TimerStartEvent createTimerStartEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.TimerStartEvent.ClassMgr.createTimerStartEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.TimerStartEvent
       * @param id Identifier for org.semanticwb.process.model.TimerStartEvent
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return A org.semanticwb.process.model.TimerStartEvent
       */
        public static org.semanticwb.process.model.TimerStartEvent getTimerStartEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TimerStartEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.TimerStartEvent
       * @param id Identifier for org.semanticwb.process.model.TimerStartEvent
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return A org.semanticwb.process.model.TimerStartEvent
       */
        public static org.semanticwb.process.model.TimerStartEvent createTimerStartEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TimerStartEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.TimerStartEvent
       * @param id Identifier for org.semanticwb.process.model.TimerStartEvent
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       */
        public static void removeTimerStartEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.TimerStartEvent
       * @param id Identifier for org.semanticwb.process.model.TimerStartEvent
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return true if the org.semanticwb.process.model.TimerStartEvent exists, false otherwise
       */

        public static boolean hasTimerStartEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTimerStartEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined Next
       * @param value Next of the type org.semanticwb.process.model.FlowNode
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByNext(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_ie_next, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined Next
       * @param value Next of the type org.semanticwb.process.model.FlowNode
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByNext(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_ie_next,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined ProcessPeriodRef
       * @param value ProcessPeriodRef of the type org.semanticwb.process.model.ProcessPeriodRef
       * @param model Model of the org.semanticwb.process.model.TimerStartEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessPeriodRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerStartEvent with a determined ProcessPeriodRef
       * @param value ProcessPeriodRef of the type org.semanticwb.process.model.ProcessPeriodRef
       * @return Iterator with all the org.semanticwb.process.model.TimerStartEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerStartEvent> listTimerStartEventByProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerStartEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessPeriodRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TimerStartEventBase.ClassMgr getTimerStartEventClassMgr()
    {
        return new TimerStartEventBase.ClassMgr();
    }

   /**
   * Constructs a TimerStartEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TimerStartEvent
   */
    public TimerStartEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.ProcessPeriodRef
   * @return A GenericIterator with all the org.semanticwb.process.model.ProcessPeriodRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriodRef> listProcessPeriodRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriodRef>(getSemanticObject().listObjectProperties(swp_hasProcessPeriodRef));
    }

   /**
   * Gets true if has a ProcessPeriodRef
   * @param value org.semanticwb.process.model.ProcessPeriodRef to verify
   * @return true if the org.semanticwb.process.model.ProcessPeriodRef exists, false otherwise
   */
    public boolean hasProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProcessPeriodRef,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a ProcessPeriodRef
   * @param value org.semanticwb.process.model.ProcessPeriodRef to add
   */

    public void addProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value)
    {
        getSemanticObject().addObjectProperty(swp_hasProcessPeriodRef, value.getSemanticObject());
    }
   /**
   * Removes all the ProcessPeriodRef
   */

    public void removeAllProcessPeriodRef()
    {
        getSemanticObject().removeProperty(swp_hasProcessPeriodRef);
    }
   /**
   * Removes a ProcessPeriodRef
   * @param value org.semanticwb.process.model.ProcessPeriodRef to remove
   */

    public void removeProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value)
    {
        getSemanticObject().removeObjectProperty(swp_hasProcessPeriodRef,value.getSemanticObject());
    }

   /**
   * Gets the ProcessPeriodRef
   * @return a org.semanticwb.process.model.ProcessPeriodRef
   */
    public org.semanticwb.process.model.ProcessPeriodRef getProcessPeriodRef()
    {
         org.semanticwb.process.model.ProcessPeriodRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProcessPeriodRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessPeriodRef)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
