package org.semanticwb.process.model.base;


public abstract class PotentialOwnerBase extends org.semanticwb.process.model.HumanPerformer implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_PotentialOwner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#PotentialOwner");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#PotentialOwner");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwners(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwners()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner>(it, true);
        }

        public static org.semanticwb.process.model.PotentialOwner createPotentialOwner(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.PotentialOwner.ClassMgr.createPotentialOwner(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.PotentialOwner getPotentialOwner(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.PotentialOwner)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.PotentialOwner createPotentialOwner(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.PotentialOwner)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removePotentialOwner(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasPotentialOwner(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPotentialOwner(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwnerByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwnerByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwnerByResourceAssignmentExpression(org.semanticwb.process.model.ResourceAssignmentExpression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_resourceAssignmentExpression, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwnerByResourceAssignmentExpression(org.semanticwb.process.model.ResourceAssignmentExpression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_resourceAssignmentExpression,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwnerByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwnerByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwnerByResourceParameterBinding(org.semanticwb.process.model.ResourceParameterBinding value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasResourceParameterBinding, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwnerByResourceParameterBinding(org.semanticwb.process.model.ResourceParameterBinding value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasResourceParameterBinding,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwnerByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwnerByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwnerByResourceRef(org.semanticwb.process.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_resourceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.PotentialOwner> listPotentialOwnerByResourceRef(org.semanticwb.process.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.PotentialOwner> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_resourceRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public PotentialOwnerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
