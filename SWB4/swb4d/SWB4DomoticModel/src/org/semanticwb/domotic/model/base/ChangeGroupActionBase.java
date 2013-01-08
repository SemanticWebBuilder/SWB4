package org.semanticwb.domotic.model.base;


public abstract class ChangeGroupActionBase extends org.semanticwb.domotic.model.DomAction implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomGroup");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasChangeGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasChangeGroup");
    public static final org.semanticwb.platform.SemanticProperty swb4d_changeGroupStat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#changeGroupStat");
    public static final org.semanticwb.platform.SemanticClass swb4d_ChangeGroupAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeGroupAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeGroupAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of ChangeGroupAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.ChangeGroupAction for all models
       * @return Iterator of org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction>(it, true);
        }

        public static org.semanticwb.domotic.model.ChangeGroupAction createChangeGroupAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.ChangeGroupAction.ClassMgr.createChangeGroupAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.ChangeGroupAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeGroupAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return A org.semanticwb.domotic.model.ChangeGroupAction
       */
        public static org.semanticwb.domotic.model.ChangeGroupAction getChangeGroupAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeGroupAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.ChangeGroupAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeGroupAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return A org.semanticwb.domotic.model.ChangeGroupAction
       */
        public static org.semanticwb.domotic.model.ChangeGroupAction createChangeGroupAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeGroupAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.ChangeGroupAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeGroupAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       */
        public static void removeChangeGroupAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.ChangeGroupAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeGroupAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return true if the org.semanticwb.domotic.model.ChangeGroupAction exists, false otherwise
       */

        public static boolean hasChangeGroupAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getChangeGroupAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined ChangeGroup
       * @param value ChangeGroup of the type org.semanticwb.domotic.model.DomGroup
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByChangeGroup(org.semanticwb.domotic.model.DomGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasChangeGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined ChangeGroup
       * @param value ChangeGroup of the type org.semanticwb.domotic.model.DomGroup
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByChangeGroup(org.semanticwb.domotic.model.DomGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasChangeGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeGroupAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeGroupAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeGroupAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeGroupAction> listChangeGroupActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeGroupAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ChangeGroupActionBase.ClassMgr getChangeGroupActionClassMgr()
    {
        return new ChangeGroupActionBase.ClassMgr();
    }

   /**
   * Constructs a ChangeGroupActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ChangeGroupAction
   */
    public ChangeGroupActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.domotic.model.DomGroup
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomGroup
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup> listChangeGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomGroup>(getSemanticObject().listObjectProperties(swb4d_hasChangeGroup));
    }

   /**
   * Gets true if has a ChangeGroup
   * @param value org.semanticwb.domotic.model.DomGroup to verify
   * @return true if the org.semanticwb.domotic.model.DomGroup exists, false otherwise
   */
    public boolean hasChangeGroup(org.semanticwb.domotic.model.DomGroup value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasChangeGroup,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a ChangeGroup
   * @param value org.semanticwb.domotic.model.DomGroup to add
   */

    public void addChangeGroup(org.semanticwb.domotic.model.DomGroup value)
    {
        getSemanticObject().addObjectProperty(swb4d_hasChangeGroup, value.getSemanticObject());
    }
   /**
   * Removes all the ChangeGroup
   */

    public void removeAllChangeGroup()
    {
        getSemanticObject().removeProperty(swb4d_hasChangeGroup);
    }
   /**
   * Removes a ChangeGroup
   * @param value org.semanticwb.domotic.model.DomGroup to remove
   */

    public void removeChangeGroup(org.semanticwb.domotic.model.DomGroup value)
    {
        getSemanticObject().removeObjectProperty(swb4d_hasChangeGroup,value.getSemanticObject());
    }

   /**
   * Gets the ChangeGroup
   * @return a org.semanticwb.domotic.model.DomGroup
   */
    public org.semanticwb.domotic.model.DomGroup getChangeGroup()
    {
         org.semanticwb.domotic.model.DomGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasChangeGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomGroup)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ChangeGroupStat property
* @return int with the ChangeGroupStat
*/
    public int getChangeGroupStat()
    {
        return getSemanticObject().getIntProperty(swb4d_changeGroupStat);
    }

/**
* Sets the ChangeGroupStat property
* @param value long with the ChangeGroupStat
*/
    public void setChangeGroupStat(int value)
    {
        getSemanticObject().setIntProperty(swb4d_changeGroupStat, value);
    }

   /**
   * Gets the DomiticSite
   * @return a instance of org.semanticwb.domotic.model.DomiticSite
   */
    public org.semanticwb.domotic.model.DomiticSite getDomiticSite()
    {
        return (org.semanticwb.domotic.model.DomiticSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
