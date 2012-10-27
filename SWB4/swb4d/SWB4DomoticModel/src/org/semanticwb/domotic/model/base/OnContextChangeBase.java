package org.semanticwb.domotic.model.base;


public abstract class OnContextChangeBase extends org.semanticwb.domotic.model.DomEvent implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swb4d_contextStat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#contextStat");
    public static final org.semanticwb.platform.SemanticClass swb4d_OnContextChange=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnContextChange");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnContextChange");

    public static class ClassMgr
    {
       /**
       * Returns a list of OnContextChange for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.OnContextChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnContextChange> listOnContextChanges(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnContextChange>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.OnContextChange for all models
       * @return Iterator of org.semanticwb.domotic.model.OnContextChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnContextChange> listOnContextChanges()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnContextChange>(it, true);
        }

        public static org.semanticwb.domotic.model.OnContextChange createOnContextChange(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.OnContextChange.ClassMgr.createOnContextChange(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.OnContextChange
       * @param id Identifier for org.semanticwb.domotic.model.OnContextChange
       * @param model Model of the org.semanticwb.domotic.model.OnContextChange
       * @return A org.semanticwb.domotic.model.OnContextChange
       */
        public static org.semanticwb.domotic.model.OnContextChange getOnContextChange(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnContextChange)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.OnContextChange
       * @param id Identifier for org.semanticwb.domotic.model.OnContextChange
       * @param model Model of the org.semanticwb.domotic.model.OnContextChange
       * @return A org.semanticwb.domotic.model.OnContextChange
       */
        public static org.semanticwb.domotic.model.OnContextChange createOnContextChange(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnContextChange)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.OnContextChange
       * @param id Identifier for org.semanticwb.domotic.model.OnContextChange
       * @param model Model of the org.semanticwb.domotic.model.OnContextChange
       */
        public static void removeOnContextChange(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.OnContextChange
       * @param id Identifier for org.semanticwb.domotic.model.OnContextChange
       * @param model Model of the org.semanticwb.domotic.model.OnContextChange
       * @return true if the org.semanticwb.domotic.model.OnContextChange exists, false otherwise
       */

        public static boolean hasOnContextChange(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOnContextChange(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnContextChange with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.OnContextChange
       * @return Iterator with all the org.semanticwb.domotic.model.OnContextChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnContextChange> listOnContextChangeByDomAction(org.semanticwb.domotic.model.DomAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnContextChange> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomActionInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnContextChange with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.OnContextChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnContextChange> listOnContextChangeByDomAction(org.semanticwb.domotic.model.DomAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnContextChange> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomActionInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnContextChange with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.OnContextChange
       * @return Iterator with all the org.semanticwb.domotic.model.OnContextChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnContextChange> listOnContextChangeByDomContext(org.semanticwb.domotic.model.DomContext value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnContextChange> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnContextChange with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.OnContextChange
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnContextChange> listOnContextChangeByDomContext(org.semanticwb.domotic.model.DomContext value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnContextChange> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static OnContextChangeBase.ClassMgr getOnContextChangeClassMgr()
    {
        return new OnContextChangeBase.ClassMgr();
    }

   /**
   * Constructs a OnContextChangeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the OnContextChange
   */
    public OnContextChangeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ContextStat property
* @return String with the ContextStat
*/
    public String getContextStat()
    {
        return getSemanticObject().getProperty(swb4d_contextStat);
    }

/**
* Sets the ContextStat property
* @param value long with the ContextStat
*/
    public void setContextStat(String value)
    {
        getSemanticObject().setProperty(swb4d_contextStat, value);
    }
}
