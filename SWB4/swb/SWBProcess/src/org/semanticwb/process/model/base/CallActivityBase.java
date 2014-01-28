package org.semanticwb.process.model.base;


public abstract class CallActivityBase extends org.semanticwb.process.model.Activity implements org.semanticwb.process.model.ResourceAssignmentable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable,org.semanticwb.model.RoleRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Referensable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_Callable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Callable");
    public static final org.semanticwb.platform.SemanticProperty swp_calledElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#calledElement");
    public static final org.semanticwb.platform.SemanticClass swp_CallActivity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CallActivity");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CallActivity");

    public static class ClassMgr
    {
       /**
       * Returns a list of CallActivity for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.CallActivity for all models
       * @return Iterator of org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.CallActivity
       * @param id Identifier for org.semanticwb.process.model.CallActivity
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return A org.semanticwb.process.model.CallActivity
       */
        public static org.semanticwb.process.model.CallActivity getCallActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallActivity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.CallActivity
       * @param id Identifier for org.semanticwb.process.model.CallActivity
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return A org.semanticwb.process.model.CallActivity
       */
        public static org.semanticwb.process.model.CallActivity createCallActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallActivity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.CallActivity
       * @param id Identifier for org.semanticwb.process.model.CallActivity
       * @param model Model of the org.semanticwb.process.model.CallActivity
       */
        public static void removeCallActivity(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.CallActivity
       * @param id Identifier for org.semanticwb.process.model.CallActivity
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return true if the org.semanticwb.process.model.CallActivity exists, false otherwise
       */

        public static boolean hasCallActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCallActivity(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined CalledElement
       * @param value CalledElement of the type org.semanticwb.process.model.Callable
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByCalledElement(org.semanticwb.process.model.Callable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined CalledElement
       * @param value CalledElement of the type org.semanticwb.process.model.Callable
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByCalledElement(org.semanticwb.process.model.Callable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_calledElement,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.process.model.CallActivity
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.CallActivity with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.process.model.CallActivity
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallActivity> listCallActivityByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallActivity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CallActivityBase.ClassMgr getCallActivityClassMgr()
    {
        return new CallActivityBase.ClassMgr();
    }

   /**
   * Constructs a CallActivityBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CallActivity
   */
    public CallActivityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property CalledElement
   * @param value CalledElement to set
   */

    public void setCalledElement(org.semanticwb.process.model.Callable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_calledElement, value.getSemanticObject());
        }else
        {
            removeCalledElement();
        }
    }
   /**
   * Remove the value for CalledElement property
   */

    public void removeCalledElement()
    {
        getSemanticObject().removeProperty(swp_calledElement);
    }

   /**
   * Gets the CalledElement
   * @return a org.semanticwb.process.model.Callable
   */
    public org.semanticwb.process.model.Callable getCalledElement()
    {
         org.semanticwb.process.model.Callable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_calledElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Callable)obj.createGenericInstance();
         }
         return ret;
    }
}
