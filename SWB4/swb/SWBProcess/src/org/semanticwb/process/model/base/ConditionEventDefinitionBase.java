package org.semanticwb.process.model.base;


public abstract class ConditionEventDefinitionBase extends org.semanticwb.process.model.EventDefinition implements org.semanticwb.process.model.Documentable,org.semanticwb.process.model.Rootable,org.semanticwb.process.model.Conditionable
{
    public static final org.semanticwb.platform.SemanticClass swp_ConditionEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConditionEventDefinition");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConditionEventDefinition");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ConditionEventDefinition> listConditionEventDefinitions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionEventDefinition>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConditionEventDefinition> listConditionEventDefinitions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionEventDefinition>(it, true);
        }

        public static org.semanticwb.process.model.ConditionEventDefinition createConditionEventDefinition(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ConditionEventDefinition.ClassMgr.createConditionEventDefinition(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ConditionEventDefinition getConditionEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ConditionEventDefinition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ConditionEventDefinition createConditionEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ConditionEventDefinition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeConditionEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasConditionEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (getConditionEventDefinition(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConditionEventDefinition> listConditionEventDefinitionByCondition(org.semanticwb.process.model.FormalExpression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_condition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConditionEventDefinition> listConditionEventDefinitionByCondition(org.semanticwb.process.model.FormalExpression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_condition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConditionEventDefinition> listConditionEventDefinitionByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConditionEventDefinition> listConditionEventDefinitionByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConditionEventDefinition> listConditionEventDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConditionEventDefinition> listConditionEventDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConditionEventDefinition> listConditionEventDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConditionEventDefinition> listConditionEventDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ConditionEventDefinitionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setCondition(org.semanticwb.process.model.FormalExpression value)
    {
        getSemanticObject().setObjectProperty(swp_condition, value.getSemanticObject());
    }

    public void removeCondition()
    {
        getSemanticObject().removeProperty(swp_condition);
    }

    public org.semanticwb.process.model.FormalExpression getCondition()
    {
         org.semanticwb.process.model.FormalExpression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_condition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FormalExpression)obj.createGenericInstance();
         }
         return ret;
    }
}
