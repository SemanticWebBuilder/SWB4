package org.semanticwb.domotic.model.base;


public abstract class DomActionBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomEvent");
    public static final org.semanticwb.platform.SemanticProperty swb4d_domEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#domEvent");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomAction");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomAction");

    public static class ClassMgr
    {
       /**
       * Returns a list of DomAction for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.DomAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.DomAction for all models
       * @return Iterator of org.semanticwb.domotic.model.DomAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction>(it, true);
        }

        public static org.semanticwb.domotic.model.DomAction createDomAction(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.DomAction.ClassMgr.createDomAction(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.DomAction
       * @param id Identifier for org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.DomAction
       * @return A org.semanticwb.domotic.model.DomAction
       */
        public static org.semanticwb.domotic.model.DomAction getDomAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.DomAction
       * @param id Identifier for org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.DomAction
       * @return A org.semanticwb.domotic.model.DomAction
       */
        public static org.semanticwb.domotic.model.DomAction createDomAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.DomAction
       * @param id Identifier for org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.DomAction
       */
        public static void removeDomAction(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.DomAction
       * @param id Identifier for org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.DomAction
       * @return true if the org.semanticwb.domotic.model.DomAction exists, false otherwise
       */

        public static boolean hasDomAction(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDomAction(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.DomAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActionByDomEvent(org.semanticwb.domotic.model.DomEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEvent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomAction with a determined DomEvent
       * @param value DomEvent of the type org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.DomAction
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomAction> listDomActionByDomEvent(org.semanticwb.domotic.model.DomEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEvent,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DomActionBase.ClassMgr getDomActionClassMgr()
    {
        return new DomActionBase.ClassMgr();
    }

   /**
   * Constructs a DomActionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DomAction
   */
    public DomActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property DomEvent
   * @param value DomEvent to set
   */

    public void setDomEvent(org.semanticwb.domotic.model.DomEvent value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_domEvent, value.getSemanticObject());
        }else
        {
            removeDomEvent();
        }
    }
   /**
   * Remove the value for DomEvent property
   */

    public void removeDomEvent()
    {
        getSemanticObject().removeProperty(swb4d_domEvent);
    }

   /**
   * Gets the DomEvent
   * @return a org.semanticwb.domotic.model.DomEvent
   */
    public org.semanticwb.domotic.model.DomEvent getDomEvent()
    {
         org.semanticwb.domotic.model.DomEvent ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_domEvent);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomEvent)obj.createGenericInstance();
         }
         return ret;
    }
}
