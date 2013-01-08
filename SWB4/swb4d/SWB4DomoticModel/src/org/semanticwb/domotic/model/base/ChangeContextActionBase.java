package org.semanticwb.domotic.model.base;


public abstract class ChangeContextActionBase extends org.semanticwb.domotic.model.DomAction implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomContext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomContext");
    public static final org.semanticwb.platform.SemanticProperty swb4d_changeContext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#changeContext");
    public static final org.semanticwb.platform.SemanticClass swb4d_ChangeContextAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeContextAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#ChangeContextAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of ChangeContextAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.ChangeContextAction for all models
       * @return Iterator of org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction>(it, true);
        }

        public static org.semanticwb.domotic.model.ChangeContextAction createChangeContextAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.ChangeContextAction.ClassMgr.createChangeContextAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.ChangeContextAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeContextAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       * @return A org.semanticwb.domotic.model.ChangeContextAction
       */
        public static org.semanticwb.domotic.model.ChangeContextAction getChangeContextAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeContextAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.ChangeContextAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeContextAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       * @return A org.semanticwb.domotic.model.ChangeContextAction
       */
        public static org.semanticwb.domotic.model.ChangeContextAction createChangeContextAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.ChangeContextAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.ChangeContextAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeContextAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       */
        public static void removeChangeContextAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.ChangeContextAction
       * @param id Identifier for org.semanticwb.domotic.model.ChangeContextAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       * @return true if the org.semanticwb.domotic.model.ChangeContextAction exists, false otherwise
       */

        public static boolean hasChangeContextAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getChangeContextAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeContextAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeContextAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeContextAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActionByDomRule(org.semanticwb.domotic.model.DomRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeContextAction with a determined DomRule
       * @param value DomRule of the type org.semanticwb.domotic.model.DomRule
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActionByDomRule(org.semanticwb.domotic.model.DomRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomRule,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeContextAction with a determined ChangeContext
       * @param value ChangeContext of the type org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActionByChangeContext(org.semanticwb.domotic.model.DomContext value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_changeContext, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeContextAction with a determined ChangeContext
       * @param value ChangeContext of the type org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActionByChangeContext(org.semanticwb.domotic.model.DomContext value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_changeContext,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeContextAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @param model Model of the org.semanticwb.domotic.model.ChangeContextAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.ChangeContextAction with a determined GetStartTimerAction
       * @param value GetStartTimerAction of the type org.semanticwb.domotic.model.StartTimerAction
       * @return Iterator with all the org.semanticwb.domotic.model.ChangeContextAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.ChangeContextAction> listChangeContextActionByGetStartTimerAction(org.semanticwb.domotic.model.StartTimerAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.ChangeContextAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_getStartTimerActionInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ChangeContextActionBase.ClassMgr getChangeContextActionClassMgr()
    {
        return new ChangeContextActionBase.ClassMgr();
    }

   /**
   * Constructs a ChangeContextActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ChangeContextAction
   */
    public ChangeContextActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ChangeContext
   * @param value ChangeContext to set
   */

    public void setChangeContext(org.semanticwb.domotic.model.DomContext value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_changeContext, value.getSemanticObject());
        }else
        {
            removeChangeContext();
        }
    }
   /**
   * Remove the value for ChangeContext property
   */

    public void removeChangeContext()
    {
        getSemanticObject().removeProperty(swb4d_changeContext);
    }

   /**
   * Gets the ChangeContext
   * @return a org.semanticwb.domotic.model.DomContext
   */
    public org.semanticwb.domotic.model.DomContext getChangeContext()
    {
         org.semanticwb.domotic.model.DomContext ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_changeContext);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomContext)obj.createGenericInstance();
         }
         return ret;
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
