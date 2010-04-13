package org.semanticwb.process.model.base;


public abstract class EscalateEventDefinitionBase extends org.semanticwb.process.model.EventDefinition implements org.semanticwb.process.model.Documentable,org.semanticwb.process.model.Rootable
{
    public static final org.semanticwb.platform.SemanticClass swp_Escalation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Escalation");
    public static final org.semanticwb.platform.SemanticProperty swp_escalationRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#escalationRef");
    public static final org.semanticwb.platform.SemanticProperty swp_escalationCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#escalationCode");
    public static final org.semanticwb.platform.SemanticClass swp_EscalateEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EscalateEventDefinition");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EscalateEventDefinition");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.EscalateEventDefinition> listEscalateEventDefinitions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EscalateEventDefinition>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.EscalateEventDefinition> listEscalateEventDefinitions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EscalateEventDefinition>(it, true);
        }

        public static org.semanticwb.process.model.EscalateEventDefinition createEscalateEventDefinition(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.EscalateEventDefinition.ClassMgr.createEscalateEventDefinition(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.EscalateEventDefinition getEscalateEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.EscalateEventDefinition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.EscalateEventDefinition createEscalateEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.EscalateEventDefinition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeEscalateEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasEscalateEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEscalateEventDefinition(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.EscalateEventDefinition> listEscalateEventDefinitionByEscalationRef(org.semanticwb.process.model.Escalation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EscalateEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_escalationRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.EscalateEventDefinition> listEscalateEventDefinitionByEscalationRef(org.semanticwb.process.model.Escalation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EscalateEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_escalationRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.EscalateEventDefinition> listEscalateEventDefinitionByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EscalateEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.EscalateEventDefinition> listEscalateEventDefinitionByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EscalateEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.EscalateEventDefinition> listEscalateEventDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EscalateEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.EscalateEventDefinition> listEscalateEventDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EscalateEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.EscalateEventDefinition> listEscalateEventDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EscalateEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.EscalateEventDefinition> listEscalateEventDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EscalateEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public EscalateEventDefinitionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setEscalationRef(org.semanticwb.process.model.Escalation value)
    {
        getSemanticObject().setObjectProperty(swp_escalationRef, value.getSemanticObject());
    }

    public void removeEscalationRef()
    {
        getSemanticObject().removeProperty(swp_escalationRef);
    }

    public org.semanticwb.process.model.Escalation getEscalationRef()
    {
         org.semanticwb.process.model.Escalation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_escalationRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Escalation)obj.createGenericInstance();
         }
         return ret;
    }

    public String getEscalationCode()
    {
        return getSemanticObject().getProperty(swp_escalationCode);
    }

    public void setEscalationCode(String value)
    {
        getSemanticObject().setProperty(swp_escalationCode, value);
    }
}
