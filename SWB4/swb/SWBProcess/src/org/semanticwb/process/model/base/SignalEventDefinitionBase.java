package org.semanticwb.process.model.base;


public abstract class SignalEventDefinitionBase extends org.semanticwb.process.model.EventDefinition implements org.semanticwb.process.model.Documentable,org.semanticwb.process.model.Rootable
{
    public static final org.semanticwb.platform.SemanticClass swp_Signal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Signal");
    public static final org.semanticwb.platform.SemanticProperty swp_signalRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#signalRef");
    public static final org.semanticwb.platform.SemanticClass swp_SignalEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SignalEventDefinition");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SignalEventDefinition");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.SignalEventDefinition> listSignalEventDefinitions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEventDefinition>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.SignalEventDefinition> listSignalEventDefinitions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEventDefinition>(it, true);
        }

        public static org.semanticwb.process.model.SignalEventDefinition createSignalEventDefinition(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.SignalEventDefinition.ClassMgr.createSignalEventDefinition(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.SignalEventDefinition getSignalEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SignalEventDefinition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.SignalEventDefinition createSignalEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SignalEventDefinition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeSignalEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasSignalEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSignalEventDefinition(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.SignalEventDefinition> listSignalEventDefinitionByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SignalEventDefinition> listSignalEventDefinitionByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SignalEventDefinition> listSignalEventDefinitionBySignalRef(org.semanticwb.process.model.Signal value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_signalRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SignalEventDefinition> listSignalEventDefinitionBySignalRef(org.semanticwb.process.model.Signal value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_signalRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SignalEventDefinition> listSignalEventDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SignalEventDefinition> listSignalEventDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SignalEventDefinition> listSignalEventDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.SignalEventDefinition> listSignalEventDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SignalEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public SignalEventDefinitionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setSignalRef(org.semanticwb.process.model.Signal value)
    {
        getSemanticObject().setObjectProperty(swp_signalRef, value.getSemanticObject());
    }

    public void removeSignalRef()
    {
        getSemanticObject().removeProperty(swp_signalRef);
    }

    public org.semanticwb.process.model.Signal getSignalRef()
    {
         org.semanticwb.process.model.Signal ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_signalRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Signal)obj.createGenericInstance();
         }
         return ret;
    }
}
