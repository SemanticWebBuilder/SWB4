package org.semanticwb.process.model.base;


public abstract class ProcessObserverBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_FlowNodeInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNodeInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasSignalObserverInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasSignalObserverInstance");
   /**
   * Nodo raiz de Eventos Iniciales
   */
    public static final org.semanticwb.platform.SemanticClass swp_StartEventNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StartEventNode");
    public static final org.semanticwb.platform.SemanticProperty swp_hasMessageObserverNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasMessageObserverNode");
    public static final org.semanticwb.platform.SemanticProperty swp_hasTimeObserverNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasTimeObserverNode");
    public static final org.semanticwb.platform.SemanticProperty swp_hasRuleObserverInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasRuleObserverInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasSignalObserverNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasSignalObserverNode");
    public static final org.semanticwb.platform.SemanticProperty swp_hasMessageObserverInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasMessageObserverInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasTimeObserverInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasTimeObserverInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasRuleObserverNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasRuleObserverNode");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessObserver=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObserver");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessObserver");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessObserver for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObservers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessObserver for all models
       * @return Iterator of org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObservers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessObserver
       * @param id Identifier for org.semanticwb.process.model.ProcessObserver
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return A org.semanticwb.process.model.ProcessObserver
       */
        public static org.semanticwb.process.model.ProcessObserver getProcessObserver(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessObserver)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessObserver
       * @param id Identifier for org.semanticwb.process.model.ProcessObserver
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return A org.semanticwb.process.model.ProcessObserver
       */
        public static org.semanticwb.process.model.ProcessObserver createProcessObserver(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessObserver)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessObserver
       * @param id Identifier for org.semanticwb.process.model.ProcessObserver
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       */
        public static void removeProcessObserver(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessObserver
       * @param id Identifier for org.semanticwb.process.model.ProcessObserver
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return true if the org.semanticwb.process.model.ProcessObserver exists, false otherwise
       */

        public static boolean hasProcessObserver(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessObserver(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined SignalObserverInstance
       * @param value SignalObserverInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverBySignalObserverInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasSignalObserverInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined SignalObserverInstance
       * @param value SignalObserverInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverBySignalObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasSignalObserverInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined MessageObserverNode
       * @param value MessageObserverNode of the type org.semanticwb.process.model.StartEventNode
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByMessageObserverNode(org.semanticwb.process.model.StartEventNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageObserverNode, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined MessageObserverNode
       * @param value MessageObserverNode of the type org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByMessageObserverNode(org.semanticwb.process.model.StartEventNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageObserverNode,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined TimeObserverNode
       * @param value TimeObserverNode of the type org.semanticwb.process.model.StartEventNode
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByTimeObserverNode(org.semanticwb.process.model.StartEventNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasTimeObserverNode, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined TimeObserverNode
       * @param value TimeObserverNode of the type org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByTimeObserverNode(org.semanticwb.process.model.StartEventNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasTimeObserverNode,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined RuleObserverInstance
       * @param value RuleObserverInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByRuleObserverInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasRuleObserverInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined RuleObserverInstance
       * @param value RuleObserverInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByRuleObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasRuleObserverInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined SignalObserverNode
       * @param value SignalObserverNode of the type org.semanticwb.process.model.StartEventNode
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverBySignalObserverNode(org.semanticwb.process.model.StartEventNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasSignalObserverNode, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined SignalObserverNode
       * @param value SignalObserverNode of the type org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverBySignalObserverNode(org.semanticwb.process.model.StartEventNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasSignalObserverNode,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined MessageObserverInstance
       * @param value MessageObserverInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByMessageObserverInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageObserverInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined MessageObserverInstance
       * @param value MessageObserverInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByMessageObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasMessageObserverInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined TimeObserverInstance
       * @param value TimeObserverInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByTimeObserverInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasTimeObserverInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined TimeObserverInstance
       * @param value TimeObserverInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByTimeObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasTimeObserverInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined RuleObserverNode
       * @param value RuleObserverNode of the type org.semanticwb.process.model.StartEventNode
       * @param model Model of the org.semanticwb.process.model.ProcessObserver
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByRuleObserverNode(org.semanticwb.process.model.StartEventNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasRuleObserverNode, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessObserver with a determined RuleObserverNode
       * @param value RuleObserverNode of the type org.semanticwb.process.model.StartEventNode
       * @return Iterator with all the org.semanticwb.process.model.ProcessObserver
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessObserver> listProcessObserverByRuleObserverNode(org.semanticwb.process.model.StartEventNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessObserver> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasRuleObserverNode,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessObserverBase.ClassMgr getProcessObserverClassMgr()
    {
        return new ProcessObserverBase.ClassMgr();
    }

   /**
   * Constructs a ProcessObserverBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessObserver
   */
    public ProcessObserverBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.FlowNodeInstance
   * @return A GenericIterator with all the org.semanticwb.process.model.FlowNodeInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listSignalObserverInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(getSemanticObject().listObjectProperties(swp_hasSignalObserverInstance));
    }

   /**
   * Gets true if has a SignalObserverInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to verify
   * @return true if the org.semanticwb.process.model.FlowNodeInstance exists, false otherwise
   */
    public boolean hasSignalObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasSignalObserverInstance,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a SignalObserverInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to add
   */

    public void addSignalObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().addObjectProperty(swp_hasSignalObserverInstance, value.getSemanticObject());
    }
   /**
   * Removes all the SignalObserverInstance
   */

    public void removeAllSignalObserverInstance()
    {
        getSemanticObject().removeProperty(swp_hasSignalObserverInstance);
    }
   /**
   * Removes a SignalObserverInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to remove
   */

    public void removeSignalObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().removeObjectProperty(swp_hasSignalObserverInstance,value.getSemanticObject());
    }

   /**
   * Gets the SignalObserverInstance
   * @return a org.semanticwb.process.model.FlowNodeInstance
   */
    public org.semanticwb.process.model.FlowNodeInstance getSignalObserverInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasSignalObserverInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.StartEventNode
   * @return A GenericIterator with all the org.semanticwb.process.model.StartEventNode
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> listMessageObserverNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode>(getSemanticObject().listObjectProperties(swp_hasMessageObserverNode));
    }

   /**
   * Gets true if has a MessageObserverNode
   * @param value org.semanticwb.process.model.StartEventNode to verify
   * @return true if the org.semanticwb.process.model.StartEventNode exists, false otherwise
   */
    public boolean hasMessageObserverNode(org.semanticwb.process.model.StartEventNode value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasMessageObserverNode,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a MessageObserverNode
   * @param value org.semanticwb.process.model.StartEventNode to add
   */

    public void addMessageObserverNode(org.semanticwb.process.model.StartEventNode value)
    {
        getSemanticObject().addObjectProperty(swp_hasMessageObserverNode, value.getSemanticObject());
    }
   /**
   * Removes all the MessageObserverNode
   */

    public void removeAllMessageObserverNode()
    {
        getSemanticObject().removeProperty(swp_hasMessageObserverNode);
    }
   /**
   * Removes a MessageObserverNode
   * @param value org.semanticwb.process.model.StartEventNode to remove
   */

    public void removeMessageObserverNode(org.semanticwb.process.model.StartEventNode value)
    {
        getSemanticObject().removeObjectProperty(swp_hasMessageObserverNode,value.getSemanticObject());
    }

   /**
   * Gets the MessageObserverNode
   * @return a org.semanticwb.process.model.StartEventNode
   */
    public org.semanticwb.process.model.StartEventNode getMessageObserverNode()
    {
         org.semanticwb.process.model.StartEventNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasMessageObserverNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.StartEventNode)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.StartEventNode
   * @return A GenericIterator with all the org.semanticwb.process.model.StartEventNode
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> listTimeObserverNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode>(getSemanticObject().listObjectProperties(swp_hasTimeObserverNode));
    }

   /**
   * Gets true if has a TimeObserverNode
   * @param value org.semanticwb.process.model.StartEventNode to verify
   * @return true if the org.semanticwb.process.model.StartEventNode exists, false otherwise
   */
    public boolean hasTimeObserverNode(org.semanticwb.process.model.StartEventNode value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasTimeObserverNode,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a TimeObserverNode
   * @param value org.semanticwb.process.model.StartEventNode to add
   */

    public void addTimeObserverNode(org.semanticwb.process.model.StartEventNode value)
    {
        getSemanticObject().addObjectProperty(swp_hasTimeObserverNode, value.getSemanticObject());
    }
   /**
   * Removes all the TimeObserverNode
   */

    public void removeAllTimeObserverNode()
    {
        getSemanticObject().removeProperty(swp_hasTimeObserverNode);
    }
   /**
   * Removes a TimeObserverNode
   * @param value org.semanticwb.process.model.StartEventNode to remove
   */

    public void removeTimeObserverNode(org.semanticwb.process.model.StartEventNode value)
    {
        getSemanticObject().removeObjectProperty(swp_hasTimeObserverNode,value.getSemanticObject());
    }

   /**
   * Gets the TimeObserverNode
   * @return a org.semanticwb.process.model.StartEventNode
   */
    public org.semanticwb.process.model.StartEventNode getTimeObserverNode()
    {
         org.semanticwb.process.model.StartEventNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasTimeObserverNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.StartEventNode)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.FlowNodeInstance
   * @return A GenericIterator with all the org.semanticwb.process.model.FlowNodeInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listRuleObserverInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(getSemanticObject().listObjectProperties(swp_hasRuleObserverInstance));
    }

   /**
   * Gets true if has a RuleObserverInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to verify
   * @return true if the org.semanticwb.process.model.FlowNodeInstance exists, false otherwise
   */
    public boolean hasRuleObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasRuleObserverInstance,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a RuleObserverInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to add
   */

    public void addRuleObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().addObjectProperty(swp_hasRuleObserverInstance, value.getSemanticObject());
    }
   /**
   * Removes all the RuleObserverInstance
   */

    public void removeAllRuleObserverInstance()
    {
        getSemanticObject().removeProperty(swp_hasRuleObserverInstance);
    }
   /**
   * Removes a RuleObserverInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to remove
   */

    public void removeRuleObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().removeObjectProperty(swp_hasRuleObserverInstance,value.getSemanticObject());
    }

   /**
   * Gets the RuleObserverInstance
   * @return a org.semanticwb.process.model.FlowNodeInstance
   */
    public org.semanticwb.process.model.FlowNodeInstance getRuleObserverInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasRuleObserverInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.StartEventNode
   * @return A GenericIterator with all the org.semanticwb.process.model.StartEventNode
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> listSignalObserverNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode>(getSemanticObject().listObjectProperties(swp_hasSignalObserverNode));
    }

   /**
   * Gets true if has a SignalObserverNode
   * @param value org.semanticwb.process.model.StartEventNode to verify
   * @return true if the org.semanticwb.process.model.StartEventNode exists, false otherwise
   */
    public boolean hasSignalObserverNode(org.semanticwb.process.model.StartEventNode value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasSignalObserverNode,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a SignalObserverNode
   * @param value org.semanticwb.process.model.StartEventNode to add
   */

    public void addSignalObserverNode(org.semanticwb.process.model.StartEventNode value)
    {
        getSemanticObject().addObjectProperty(swp_hasSignalObserverNode, value.getSemanticObject());
    }
   /**
   * Removes all the SignalObserverNode
   */

    public void removeAllSignalObserverNode()
    {
        getSemanticObject().removeProperty(swp_hasSignalObserverNode);
    }
   /**
   * Removes a SignalObserverNode
   * @param value org.semanticwb.process.model.StartEventNode to remove
   */

    public void removeSignalObserverNode(org.semanticwb.process.model.StartEventNode value)
    {
        getSemanticObject().removeObjectProperty(swp_hasSignalObserverNode,value.getSemanticObject());
    }

   /**
   * Gets the SignalObserverNode
   * @return a org.semanticwb.process.model.StartEventNode
   */
    public org.semanticwb.process.model.StartEventNode getSignalObserverNode()
    {
         org.semanticwb.process.model.StartEventNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasSignalObserverNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.StartEventNode)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.FlowNodeInstance
   * @return A GenericIterator with all the org.semanticwb.process.model.FlowNodeInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listMessageObserverInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(getSemanticObject().listObjectProperties(swp_hasMessageObserverInstance));
    }

   /**
   * Gets true if has a MessageObserverInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to verify
   * @return true if the org.semanticwb.process.model.FlowNodeInstance exists, false otherwise
   */
    public boolean hasMessageObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasMessageObserverInstance,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a MessageObserverInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to add
   */

    public void addMessageObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().addObjectProperty(swp_hasMessageObserverInstance, value.getSemanticObject());
    }
   /**
   * Removes all the MessageObserverInstance
   */

    public void removeAllMessageObserverInstance()
    {
        getSemanticObject().removeProperty(swp_hasMessageObserverInstance);
    }
   /**
   * Removes a MessageObserverInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to remove
   */

    public void removeMessageObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().removeObjectProperty(swp_hasMessageObserverInstance,value.getSemanticObject());
    }

   /**
   * Gets the MessageObserverInstance
   * @return a org.semanticwb.process.model.FlowNodeInstance
   */
    public org.semanticwb.process.model.FlowNodeInstance getMessageObserverInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasMessageObserverInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.FlowNodeInstance
   * @return A GenericIterator with all the org.semanticwb.process.model.FlowNodeInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance> listTimeObserverInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowNodeInstance>(getSemanticObject().listObjectProperties(swp_hasTimeObserverInstance));
    }

   /**
   * Gets true if has a TimeObserverInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to verify
   * @return true if the org.semanticwb.process.model.FlowNodeInstance exists, false otherwise
   */
    public boolean hasTimeObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasTimeObserverInstance,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a TimeObserverInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to add
   */

    public void addTimeObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().addObjectProperty(swp_hasTimeObserverInstance, value.getSemanticObject());
    }
   /**
   * Removes all the TimeObserverInstance
   */

    public void removeAllTimeObserverInstance()
    {
        getSemanticObject().removeProperty(swp_hasTimeObserverInstance);
    }
   /**
   * Removes a TimeObserverInstance
   * @param value org.semanticwb.process.model.FlowNodeInstance to remove
   */

    public void removeTimeObserverInstance(org.semanticwb.process.model.FlowNodeInstance value)
    {
        getSemanticObject().removeObjectProperty(swp_hasTimeObserverInstance,value.getSemanticObject());
    }

   /**
   * Gets the TimeObserverInstance
   * @return a org.semanticwb.process.model.FlowNodeInstance
   */
    public org.semanticwb.process.model.FlowNodeInstance getTimeObserverInstance()
    {
         org.semanticwb.process.model.FlowNodeInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasTimeObserverInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNodeInstance)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.StartEventNode
   * @return A GenericIterator with all the org.semanticwb.process.model.StartEventNode
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode> listRuleObserverNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.StartEventNode>(getSemanticObject().listObjectProperties(swp_hasRuleObserverNode));
    }

   /**
   * Gets true if has a RuleObserverNode
   * @param value org.semanticwb.process.model.StartEventNode to verify
   * @return true if the org.semanticwb.process.model.StartEventNode exists, false otherwise
   */
    public boolean hasRuleObserverNode(org.semanticwb.process.model.StartEventNode value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasRuleObserverNode,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a RuleObserverNode
   * @param value org.semanticwb.process.model.StartEventNode to add
   */

    public void addRuleObserverNode(org.semanticwb.process.model.StartEventNode value)
    {
        getSemanticObject().addObjectProperty(swp_hasRuleObserverNode, value.getSemanticObject());
    }
   /**
   * Removes all the RuleObserverNode
   */

    public void removeAllRuleObserverNode()
    {
        getSemanticObject().removeProperty(swp_hasRuleObserverNode);
    }
   /**
   * Removes a RuleObserverNode
   * @param value org.semanticwb.process.model.StartEventNode to remove
   */

    public void removeRuleObserverNode(org.semanticwb.process.model.StartEventNode value)
    {
        getSemanticObject().removeObjectProperty(swp_hasRuleObserverNode,value.getSemanticObject());
    }

   /**
   * Gets the RuleObserverNode
   * @return a org.semanticwb.process.model.StartEventNode
   */
    public org.semanticwb.process.model.StartEventNode getRuleObserverNode()
    {
         org.semanticwb.process.model.StartEventNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasRuleObserverNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.StartEventNode)obj.createGenericInstance();
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
