package org.semanticwb.bsc.accessory.base;


public abstract class StateGroupBase extends org.semanticwb.bsc.accessory.BSCAccessory implements org.semanticwb.model.Roleable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Filterable,org.semanticwb.model.UserGroupable,org.semanticwb.model.RuleRefable,org.semanticwb.bsc.Help,org.semanticwb.model.Referensable,org.semanticwb.model.Undeleteable
{
    public static final org.semanticwb.platform.SemanticClass bsc_State=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#State");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasGroupedStates=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasGroupedStates");
    public static final org.semanticwb.platform.SemanticClass bsc_StateGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#StateGroup");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#StateGroup");

    public static class ClassMgr
    {
       /**
       * Returns a list of StateGroup for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.accessory.StateGroup for all models
       * @return Iterator of org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup>(it, true);
        }

        public static org.semanticwb.bsc.accessory.StateGroup createStateGroup(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.accessory.StateGroup.ClassMgr.createStateGroup(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.accessory.StateGroup
       * @param id Identifier for org.semanticwb.bsc.accessory.StateGroup
       * @param model Model of the org.semanticwb.bsc.accessory.StateGroup
       * @return A org.semanticwb.bsc.accessory.StateGroup
       */
        public static org.semanticwb.bsc.accessory.StateGroup getStateGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.StateGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.accessory.StateGroup
       * @param id Identifier for org.semanticwb.bsc.accessory.StateGroup
       * @param model Model of the org.semanticwb.bsc.accessory.StateGroup
       * @return A org.semanticwb.bsc.accessory.StateGroup
       */
        public static org.semanticwb.bsc.accessory.StateGroup createStateGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.StateGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.accessory.StateGroup
       * @param id Identifier for org.semanticwb.bsc.accessory.StateGroup
       * @param model Model of the org.semanticwb.bsc.accessory.StateGroup
       */
        public static void removeStateGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.accessory.StateGroup
       * @param id Identifier for org.semanticwb.bsc.accessory.StateGroup
       * @param model Model of the org.semanticwb.bsc.accessory.StateGroup
       * @return true if the org.semanticwb.bsc.accessory.StateGroup exists, false otherwise
       */

        public static boolean hasStateGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getStateGroup(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.StateGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.StateGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroupByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.StateGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroupByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.StateGroup with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.accessory.StateGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroupByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.StateGroup with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroupByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.StateGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.StateGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroupByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.StateGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroupByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.StateGroup with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.accessory.StateGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroupByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.StateGroup with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroupByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.StateGroup with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.bsc.accessory.StateGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroupByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.StateGroup with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroupByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.StateGroup with a determined GroupedStates
       * @param value GroupedStates of the type org.semanticwb.bsc.accessory.State
       * @param model Model of the org.semanticwb.bsc.accessory.StateGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroupByGroupedStates(org.semanticwb.bsc.accessory.State value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasGroupedStates, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.StateGroup with a determined GroupedStates
       * @param value GroupedStates of the type org.semanticwb.bsc.accessory.State
       * @return Iterator with all the org.semanticwb.bsc.accessory.StateGroup
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.StateGroup> listStateGroupByGroupedStates(org.semanticwb.bsc.accessory.State value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.StateGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasGroupedStates,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static StateGroupBase.ClassMgr getStateGroupClassMgr()
    {
        return new StateGroupBase.ClassMgr();
    }

   /**
   * Constructs a StateGroupBase with a SemanticObject
   * @param base The SemanticObject with the properties for the StateGroup
   */
    public StateGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.bsc.accessory.State
   * @return A GenericIterator with all the org.semanticwb.bsc.accessory.State
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State> listGroupedStateses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.State>(getSemanticObject().listObjectProperties(bsc_hasGroupedStates));
    }

   /**
   * Gets true if has a GroupedStates
   * @param value org.semanticwb.bsc.accessory.State to verify
   * @return true if the org.semanticwb.bsc.accessory.State exists, false otherwise
   */
    public boolean hasGroupedStates(org.semanticwb.bsc.accessory.State value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasGroupedStates,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a GroupedStates
   * @param value org.semanticwb.bsc.accessory.State to add
   */

    public void addGroupedStates(org.semanticwb.bsc.accessory.State value)
    {
        getSemanticObject().addObjectProperty(bsc_hasGroupedStates, value.getSemanticObject());
    }
   /**
   * Removes all the GroupedStates
   */

    public void removeAllGroupedStates()
    {
        getSemanticObject().removeProperty(bsc_hasGroupedStates);
    }
   /**
   * Removes a GroupedStates
   * @param value org.semanticwb.bsc.accessory.State to remove
   */

    public void removeGroupedStates(org.semanticwb.bsc.accessory.State value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasGroupedStates,value.getSemanticObject());
    }

   /**
   * Gets the GroupedStates
   * @return a org.semanticwb.bsc.accessory.State
   */
    public org.semanticwb.bsc.accessory.State getGroupedStates()
    {
         org.semanticwb.bsc.accessory.State ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasGroupedStates);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.accessory.State)obj.createGenericInstance();
         }
         return ret;
    }
}
