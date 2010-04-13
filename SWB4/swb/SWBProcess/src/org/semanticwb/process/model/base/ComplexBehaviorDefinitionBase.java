package org.semanticwb.process.model.base;


public abstract class ComplexBehaviorDefinitionBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable,org.semanticwb.process.model.Conditionable
{
    public static final org.semanticwb.platform.SemanticClass swp_ImplicitThrowEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ImplicitThrowEvent");
    public static final org.semanticwb.platform.SemanticProperty swp_event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#event");
    public static final org.semanticwb.platform.SemanticClass swp_ComplexBehaviorDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ComplexBehaviorDefinition");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ComplexBehaviorDefinition");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition>(it, true);
        }

        public static org.semanticwb.process.model.ComplexBehaviorDefinition createComplexBehaviorDefinition(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ComplexBehaviorDefinition.ClassMgr.createComplexBehaviorDefinition(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ComplexBehaviorDefinition getComplexBehaviorDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ComplexBehaviorDefinition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ComplexBehaviorDefinition createComplexBehaviorDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ComplexBehaviorDefinition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeComplexBehaviorDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasComplexBehaviorDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (getComplexBehaviorDefinition(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitionByCondition(org.semanticwb.process.model.FormalExpression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_condition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitionByCondition(org.semanticwb.process.model.FormalExpression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_condition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitionByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitionByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitionByEvent(org.semanticwb.process.model.ImplicitThrowEvent value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_event, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitionByEvent(org.semanticwb.process.model.ImplicitThrowEvent value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_event,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ComplexBehaviorDefinitionBase(org.semanticwb.platform.SemanticObject base)
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

    public void setEvent(org.semanticwb.process.model.ImplicitThrowEvent value)
    {
        getSemanticObject().setObjectProperty(swp_event, value.getSemanticObject());
    }

    public void removeEvent()
    {
        getSemanticObject().removeProperty(swp_event);
    }

    public org.semanticwb.process.model.ImplicitThrowEvent getEvent()
    {
         org.semanticwb.process.model.ImplicitThrowEvent ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_event);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ImplicitThrowEvent)obj.createGenericInstance();
         }
         return ret;
    }
}
