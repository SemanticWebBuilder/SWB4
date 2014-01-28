package org.semanticwb.process.model.base;


public abstract class ProcessRuleBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Traceable,org.semanticwb.model.XMLable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swp_ruleCondition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#ruleCondition");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRuleRef");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessRuleRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessRuleRefInv");
   /**
   * Objeto que define un Grupo de reglas de negocio
   */
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRuleGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRuleGroup");
    public static final org.semanticwb.platform.SemanticProperty swp_processRuleGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processRuleGroup");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRule");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRule");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessRule for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessRule
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRules(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessRule for all models
       * @return Iterator of org.semanticwb.process.model.ProcessRule
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRules()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule>(it, true);
        }

        public static org.semanticwb.process.model.ProcessRule createProcessRule(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessRule.ClassMgr.createProcessRule(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessRule
       * @param id Identifier for org.semanticwb.process.model.ProcessRule
       * @param model Model of the org.semanticwb.process.model.ProcessRule
       * @return A org.semanticwb.process.model.ProcessRule
       */
        public static org.semanticwb.process.model.ProcessRule getProcessRule(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessRule)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessRule
       * @param id Identifier for org.semanticwb.process.model.ProcessRule
       * @param model Model of the org.semanticwb.process.model.ProcessRule
       * @return A org.semanticwb.process.model.ProcessRule
       */
        public static org.semanticwb.process.model.ProcessRule createProcessRule(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessRule)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessRule
       * @param id Identifier for org.semanticwb.process.model.ProcessRule
       * @param model Model of the org.semanticwb.process.model.ProcessRule
       */
        public static void removeProcessRule(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessRule
       * @param id Identifier for org.semanticwb.process.model.ProcessRule
       * @param model Model of the org.semanticwb.process.model.ProcessRule
       * @return true if the org.semanticwb.process.model.ProcessRule exists, false otherwise
       */

        public static boolean hasProcessRule(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessRule(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRule with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessRule
       * @return Iterator with all the org.semanticwb.process.model.ProcessRule
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRuleByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRule with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessRule
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRuleByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRule with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ProcessRule
       * @return Iterator with all the org.semanticwb.process.model.ProcessRule
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRuleByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRule with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ProcessRule
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRuleByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRule with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessRule
       * @return Iterator with all the org.semanticwb.process.model.ProcessRule
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRuleByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRule with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessRule
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRuleByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRule with a determined ProcessRuleRefInv
       * @param value ProcessRuleRefInv of the type org.semanticwb.process.model.ProcessRuleRef
       * @param model Model of the org.semanticwb.process.model.ProcessRule
       * @return Iterator with all the org.semanticwb.process.model.ProcessRule
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRuleByProcessRuleRefInv(org.semanticwb.process.model.ProcessRuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessRuleRefInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRule with a determined ProcessRuleRefInv
       * @param value ProcessRuleRefInv of the type org.semanticwb.process.model.ProcessRuleRef
       * @return Iterator with all the org.semanticwb.process.model.ProcessRule
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRuleByProcessRuleRefInv(org.semanticwb.process.model.ProcessRuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessRuleRefInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRule with a determined ProcessRuleGroup
       * @param value ProcessRuleGroup of the type org.semanticwb.process.model.ProcessRuleGroup
       * @param model Model of the org.semanticwb.process.model.ProcessRule
       * @return Iterator with all the org.semanticwb.process.model.ProcessRule
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRuleByProcessRuleGroup(org.semanticwb.process.model.ProcessRuleGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processRuleGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessRule with a determined ProcessRuleGroup
       * @param value ProcessRuleGroup of the type org.semanticwb.process.model.ProcessRuleGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessRule
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRule> listProcessRuleByProcessRuleGroup(org.semanticwb.process.model.ProcessRuleGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processRuleGroup,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessRuleBase.ClassMgr getProcessRuleClassMgr()
    {
        return new ProcessRuleBase.ClassMgr();
    }

   /**
   * Constructs a ProcessRuleBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessRule
   */
    public ProcessRuleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Xml property
* @return String with the Xml
*/
    public String getXml()
    {
        return getSemanticObject().getProperty(swb_xml);
    }

/**
* Sets the Xml property
* @param value long with the Xml
*/
    public void setXml(String value)
    {
        getSemanticObject().setProperty(swb_xml, value);
    }

/**
* Gets the RuleCondition property
* @return String with the RuleCondition
*/
    public String getRuleCondition()
    {
        return getSemanticObject().getProperty(swp_ruleCondition);
    }

/**
* Sets the RuleCondition property
* @param value long with the RuleCondition
*/
    public void setRuleCondition(String value)
    {
        getSemanticObject().setProperty(swp_ruleCondition, value);
    }
   /**
   * Gets all the org.semanticwb.process.model.ProcessRuleRef
   * @return A GenericIterator with all the org.semanticwb.process.model.ProcessRuleRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef> listProcessRuleRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef>(getSemanticObject().listObjectProperties(swp_hasProcessRuleRefInv));
    }

   /**
   * Gets true if has a ProcessRuleRefInv
   * @param value org.semanticwb.process.model.ProcessRuleRef to verify
   * @return true if the org.semanticwb.process.model.ProcessRuleRef exists, false otherwise
   */
    public boolean hasProcessRuleRefInv(org.semanticwb.process.model.ProcessRuleRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProcessRuleRefInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the ProcessRuleRefInv
   * @return a org.semanticwb.process.model.ProcessRuleRef
   */
    public org.semanticwb.process.model.ProcessRuleRef getProcessRuleRefInv()
    {
         org.semanticwb.process.model.ProcessRuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProcessRuleRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessRuleRef)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ProcessRuleGroup
   * @param value ProcessRuleGroup to set
   */

    public void setProcessRuleGroup(org.semanticwb.process.model.ProcessRuleGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_processRuleGroup, value.getSemanticObject());
        }else
        {
            removeProcessRuleGroup();
        }
    }
   /**
   * Remove the value for ProcessRuleGroup property
   */

    public void removeProcessRuleGroup()
    {
        getSemanticObject().removeProperty(swp_processRuleGroup);
    }

   /**
   * Gets the ProcessRuleGroup
   * @return a org.semanticwb.process.model.ProcessRuleGroup
   */
    public org.semanticwb.process.model.ProcessRuleGroup getProcessRuleGroup()
    {
         org.semanticwb.process.model.ProcessRuleGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processRuleGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessRuleGroup)obj.createGenericInstance();
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
