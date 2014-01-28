package org.semanticwb.process.model.base;


public abstract class TimerIntermediateCatchEventBase extends org.semanticwb.process.model.IntermediateCatchEvent implements org.semanticwb.model.Traceable,org.semanticwb.process.model.ProcessPeriodRefable,org.semanticwb.model.Sortable,org.semanticwb.model.Referensable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_TimerIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TimerIntermediateCatchEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#TimerIntermediateCatchEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of TimerIntermediateCatchEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.TimerIntermediateCatchEvent for all models
       * @return Iterator of org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent>(it, true);
        }

        public static org.semanticwb.process.model.TimerIntermediateCatchEvent createTimerIntermediateCatchEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.TimerIntermediateCatchEvent.ClassMgr.createTimerIntermediateCatchEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return A org.semanticwb.process.model.TimerIntermediateCatchEvent
       */
        public static org.semanticwb.process.model.TimerIntermediateCatchEvent getTimerIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TimerIntermediateCatchEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return A org.semanticwb.process.model.TimerIntermediateCatchEvent
       */
        public static org.semanticwb.process.model.TimerIntermediateCatchEvent createTimerIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TimerIntermediateCatchEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */
        public static void removeTimerIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return true if the org.semanticwb.process.model.TimerIntermediateCatchEvent exists, false otherwise
       */

        public static boolean hasTimerIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTimerIntermediateCatchEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined ProcessPeriodRef
       * @param value ProcessPeriodRef of the type org.semanticwb.process.model.ProcessPeriodRef
       * @param model Model of the org.semanticwb.process.model.TimerIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessPeriodRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.TimerIntermediateCatchEvent with a determined ProcessPeriodRef
       * @param value ProcessPeriodRef of the type org.semanticwb.process.model.ProcessPeriodRef
       * @return Iterator with all the org.semanticwb.process.model.TimerIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> listTimerIntermediateCatchEventByProcessPeriodRef(org.semanticwb.process.model.ProcessPeriodRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessPeriodRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TimerIntermediateCatchEventBase.ClassMgr getTimerIntermediateCatchEventClassMgr()
    {
        return new TimerIntermediateCatchEventBase.ClassMgr();
    }

   /**
   * Constructs a TimerIntermediateCatchEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TimerIntermediateCatchEvent
   */
    public TimerIntermediateCatchEventBase(org.semanticwb.platform.SemanticObject base)
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
