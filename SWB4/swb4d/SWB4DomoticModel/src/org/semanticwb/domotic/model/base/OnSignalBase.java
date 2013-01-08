package org.semanticwb.domotic.model.base;


public abstract class OnSignalBase extends org.semanticwb.domotic.model.DomEvent implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swb4d_signal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#signal");
    public static final org.semanticwb.platform.SemanticClass swb4d_OnSignal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnSignal");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnSignal");

    public static class ClassMgr
    {
       /**
       * Returns a list of OnSignal for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.OnSignal
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSignal> listOnSignals(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSignal>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.OnSignal for all models
       * @return Iterator of org.semanticwb.domotic.model.OnSignal
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSignal> listOnSignals()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSignal>(it, true);
        }

        public static org.semanticwb.domotic.model.OnSignal createOnSignal(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.OnSignal.ClassMgr.createOnSignal(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.OnSignal
       * @param id Identifier for org.semanticwb.domotic.model.OnSignal
       * @param model Model of the org.semanticwb.domotic.model.OnSignal
       * @return A org.semanticwb.domotic.model.OnSignal
       */
        public static org.semanticwb.domotic.model.OnSignal getOnSignal(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnSignal)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.OnSignal
       * @param id Identifier for org.semanticwb.domotic.model.OnSignal
       * @param model Model of the org.semanticwb.domotic.model.OnSignal
       * @return A org.semanticwb.domotic.model.OnSignal
       */
        public static org.semanticwb.domotic.model.OnSignal createOnSignal(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnSignal)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.OnSignal
       * @param id Identifier for org.semanticwb.domotic.model.OnSignal
       * @param model Model of the org.semanticwb.domotic.model.OnSignal
       */
        public static void removeOnSignal(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.OnSignal
       * @param id Identifier for org.semanticwb.domotic.model.OnSignal
       * @param model Model of the org.semanticwb.domotic.model.OnSignal
       * @return true if the org.semanticwb.domotic.model.OnSignal exists, false otherwise
       */

        public static boolean hasOnSignal(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOnSignal(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnSignal with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.OnSignal
       * @return Iterator with all the org.semanticwb.domotic.model.OnSignal
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSignal> listOnSignalByDomAction(org.semanticwb.domotic.model.DomAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSignal> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomAction, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnSignal with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.OnSignal
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSignal> listOnSignalByDomAction(org.semanticwb.domotic.model.DomAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSignal> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomAction,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnSignal with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.OnSignal
       * @return Iterator with all the org.semanticwb.domotic.model.OnSignal
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSignal> listOnSignalByDomContext(org.semanticwb.domotic.model.DomContext value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSignal> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnSignal with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.OnSignal
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSignal> listOnSignalByDomContext(org.semanticwb.domotic.model.DomContext value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSignal> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static OnSignalBase.ClassMgr getOnSignalClassMgr()
    {
        return new OnSignalBase.ClassMgr();
    }

   /**
   * Constructs a OnSignalBase with a SemanticObject
   * @param base The SemanticObject with the properties for the OnSignal
   */
    public OnSignalBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Signal property
* @return String with the Signal
*/
    public String getSignal()
    {
        return getSemanticObject().getProperty(swb4d_signal);
    }

/**
* Sets the Signal property
* @param value long with the Signal
*/
    public void setSignal(String value)
    {
        getSemanticObject().setProperty(swb4d_signal, value);
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
