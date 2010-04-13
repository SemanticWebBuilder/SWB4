package org.semanticwb.process.model.base;


public abstract class HumanPerformerBase extends org.semanticwb.process.model.Performer implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_HumanPerformer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#HumanPerformer");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#HumanPerformer");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer>(it, true);
        }

        public static org.semanticwb.process.model.HumanPerformer createHumanPerformer(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.HumanPerformer.ClassMgr.createHumanPerformer(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.HumanPerformer getHumanPerformer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.HumanPerformer)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.HumanPerformer createHumanPerformer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.HumanPerformer)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeHumanPerformer(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasHumanPerformer(String id, org.semanticwb.model.SWBModel model)
        {
            return (getHumanPerformer(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformerByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformerByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformerByResourceAssignmentExpression(org.semanticwb.process.model.ResourceAssignmentExpression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_resourceAssignmentExpression, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformerByResourceAssignmentExpression(org.semanticwb.process.model.ResourceAssignmentExpression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_resourceAssignmentExpression,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformerByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformerByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformerByResourceParameterBinding(org.semanticwb.process.model.ResourceParameterBinding value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasResourceParameterBinding, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformerByResourceParameterBinding(org.semanticwb.process.model.ResourceParameterBinding value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasResourceParameterBinding,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformerByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformerByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformerByResourceRef(org.semanticwb.process.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_resourceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.HumanPerformer> listHumanPerformerByResourceRef(org.semanticwb.process.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.HumanPerformer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_resourceRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public HumanPerformerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
