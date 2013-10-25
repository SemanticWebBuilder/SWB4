package org.semanticwb.process.model.base;


public abstract class RuleIntermediateCatchEventBase extends org.semanticwb.process.model.IntermediateCatchEvent implements org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.ProcessRuleRefable
{
    public static final org.semanticwb.platform.SemanticClass swp_RuleIntermediateCatchEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RuleIntermediateCatchEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RuleIntermediateCatchEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of RuleIntermediateCatchEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.RuleIntermediateCatchEvent for all models
       * @return Iterator of org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent>(it, true);
        }

        public static org.semanticwb.process.model.RuleIntermediateCatchEvent createRuleIntermediateCatchEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.RuleIntermediateCatchEvent.ClassMgr.createRuleIntermediateCatchEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return A org.semanticwb.process.model.RuleIntermediateCatchEvent
       */
        public static org.semanticwb.process.model.RuleIntermediateCatchEvent getRuleIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.RuleIntermediateCatchEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return A org.semanticwb.process.model.RuleIntermediateCatchEvent
       */
        public static org.semanticwb.process.model.RuleIntermediateCatchEvent createRuleIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.RuleIntermediateCatchEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */
        public static void removeRuleIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @param id Identifier for org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return true if the org.semanticwb.process.model.RuleIntermediateCatchEvent exists, false otherwise
       */

        public static boolean hasRuleIntermediateCatchEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRuleIntermediateCatchEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined ProcessRuleRef
       * @param value ProcessRuleRef of the type org.semanticwb.process.model.ProcessRuleRef
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined ProcessRuleRef
       * @param value ProcessRuleRef of the type org.semanticwb.process.model.ProcessRuleRef
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.RuleIntermediateCatchEvent
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RuleIntermediateCatchEvent with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.RuleIntermediateCatchEvent
       */

        public static java.util.Iterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> listRuleIntermediateCatchEventByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RuleIntermediateCatchEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static RuleIntermediateCatchEventBase.ClassMgr getRuleIntermediateCatchEventClassMgr()
    {
        return new RuleIntermediateCatchEventBase.ClassMgr();
    }

   /**
   * Constructs a RuleIntermediateCatchEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the RuleIntermediateCatchEvent
   */
    public RuleIntermediateCatchEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.ProcessRuleRef
   * @return A GenericIterator with all the org.semanticwb.process.model.ProcessRuleRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef> listProcessRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef>(getSemanticObject().listObjectProperties(swp_hasProcessRuleRef));
    }

   /**
   * Gets true if has a ProcessRuleRef
   * @param value org.semanticwb.process.model.ProcessRuleRef to verify
   * @return true if the org.semanticwb.process.model.ProcessRuleRef exists, false otherwise
   */
    public boolean hasProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProcessRuleRef,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a ProcessRuleRef
   * @param value org.semanticwb.process.model.ProcessRuleRef to add
   */

    public void addProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value)
    {
        getSemanticObject().addObjectProperty(swp_hasProcessRuleRef, value.getSemanticObject());
    }
   /**
   * Removes all the ProcessRuleRef
   */

    public void removeAllProcessRuleRef()
    {
        getSemanticObject().removeProperty(swp_hasProcessRuleRef);
    }
   /**
   * Removes a ProcessRuleRef
   * @param value org.semanticwb.process.model.ProcessRuleRef to remove
   */

    public void removeProcessRuleRef(org.semanticwb.process.model.ProcessRuleRef value)
    {
        getSemanticObject().removeObjectProperty(swp_hasProcessRuleRef,value.getSemanticObject());
    }

   /**
   * Gets the ProcessRuleRef
   * @return a org.semanticwb.process.model.ProcessRuleRef
   */
    public org.semanticwb.process.model.ProcessRuleRef getProcessRuleRef()
    {
         org.semanticwb.process.model.ProcessRuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProcessRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessRuleRef)obj.createGenericInstance();
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
