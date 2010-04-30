package org.semanticwb.process.model.base;


public abstract class CorrelationKeyBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationProperty");
    public static final org.semanticwb.platform.SemanticProperty swp_hasCorrelationPropertyRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasCorrelationPropertyRef");
    public static final org.semanticwb.platform.SemanticClass swp_Conversation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Conversation");
    public static final org.semanticwb.platform.SemanticProperty swp_conversation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#conversation");
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationKey=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationKey");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationKey");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeys(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeys()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey>(it, true);
        }

        public static org.semanticwb.process.model.CorrelationKey createCorrelationKey(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CorrelationKey.ClassMgr.createCorrelationKey(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.CorrelationKey getCorrelationKey(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CorrelationKey)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.CorrelationKey createCorrelationKey(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CorrelationKey)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCorrelationKey(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCorrelationKey(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCorrelationKey(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeyByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeyByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeyByCorrelationPropertyRef(org.semanticwb.process.model.CorrelationProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasCorrelationPropertyRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeyByCorrelationPropertyRef(org.semanticwb.process.model.CorrelationProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasCorrelationPropertyRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeyByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeyByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeyByConversation(org.semanticwb.process.model.Conversation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_conversation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeyByConversation(org.semanticwb.process.model.Conversation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_conversation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeyByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationKey> listCorrelationKeyByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationKey> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CorrelationKeyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationProperty> listCorrelationPropertyRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationProperty>(getSemanticObject().listObjectProperties(swp_hasCorrelationPropertyRef));
    }

    public boolean hasCorrelationPropertyRef(org.semanticwb.process.model.CorrelationProperty value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasCorrelationPropertyRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addCorrelationPropertyRef(org.semanticwb.process.model.CorrelationProperty value)
    {
        getSemanticObject().addObjectProperty(swp_hasCorrelationPropertyRef, value.getSemanticObject());
    }

    public void removeAllCorrelationPropertyRef()
    {
        getSemanticObject().removeProperty(swp_hasCorrelationPropertyRef);
    }

    public void removeCorrelationPropertyRef(org.semanticwb.process.model.CorrelationProperty value)
    {
        getSemanticObject().removeObjectProperty(swp_hasCorrelationPropertyRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.CorrelationProperty getCorrelationPropertyRef()
    {
         org.semanticwb.process.model.CorrelationProperty ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasCorrelationPropertyRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.CorrelationProperty)obj.createGenericInstance();
         }
         return ret;
    }

    public void setConversation(org.semanticwb.process.model.Conversation value)
    {
        getSemanticObject().setObjectProperty(swp_conversation, value.getSemanticObject());
    }

    public void removeConversation()
    {
        getSemanticObject().removeProperty(swp_conversation);
    }

    public org.semanticwb.process.model.Conversation getConversation()
    {
         org.semanticwb.process.model.Conversation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_conversation);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Conversation)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
