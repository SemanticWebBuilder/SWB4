package org.semanticwb.process.model.base;


public abstract class ExtensionAttributeValueBase extends org.semanticwb.process.model.BPMNElement 
{
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionAttributeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionAttributeDefinition");
    public static final org.semanticwb.platform.SemanticProperty swp_extensionAttributeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#extensionAttributeDefinition");
    public static final org.semanticwb.platform.SemanticClass owl_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2002/07/owl#Class");
    public static final org.semanticwb.platform.SemanticProperty swp_attributeValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#attributeValue");
    public static final org.semanticwb.platform.SemanticProperty swp_attributeValueRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#attributeValueRef");
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionAttributeValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionAttributeValue");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionAttributeValue");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ExtensionAttributeValue> listExtensionAttributeValues(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeValue>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ExtensionAttributeValue> listExtensionAttributeValues()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeValue>(it, true);
        }

        public static org.semanticwb.process.model.ExtensionAttributeValue createExtensionAttributeValue(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ExtensionAttributeValue.ClassMgr.createExtensionAttributeValue(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ExtensionAttributeValue getExtensionAttributeValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ExtensionAttributeValue)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ExtensionAttributeValue createExtensionAttributeValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ExtensionAttributeValue)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeExtensionAttributeValue(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasExtensionAttributeValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (getExtensionAttributeValue(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ExtensionAttributeValue> listExtensionAttributeValueByExtensionAttributeDefinition(org.semanticwb.process.model.ExtensionAttributeDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_extensionAttributeDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ExtensionAttributeValue> listExtensionAttributeValueByExtensionAttributeDefinition(org.semanticwb.process.model.ExtensionAttributeDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_extensionAttributeDefinition,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ExtensionAttributeValueBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setExtensionAttributeDefinition(org.semanticwb.process.model.ExtensionAttributeDefinition value)
    {
        getSemanticObject().setObjectProperty(swp_extensionAttributeDefinition, value.getSemanticObject());
    }

    public void removeExtensionAttributeDefinition()
    {
        getSemanticObject().removeProperty(swp_extensionAttributeDefinition);
    }

    public org.semanticwb.process.model.ExtensionAttributeDefinition getExtensionAttributeDefinition()
    {
         org.semanticwb.process.model.ExtensionAttributeDefinition ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_extensionAttributeDefinition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ExtensionAttributeDefinition)obj.createGenericInstance();
         }
         return ret;
    }

    public void setAttributeValue(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swp_attributeValue, value);
    }

    public void removeAttributeValue()
    {
        getSemanticObject().removeProperty(swp_attributeValue);
    }

    public org.semanticwb.platform.SemanticObject getAttributeValue()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swp_attributeValue);
         return ret;
    }

    public void setAttributeValueRef(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swp_attributeValueRef, value);
    }

    public void removeAttributeValueRef()
    {
        getSemanticObject().removeProperty(swp_attributeValueRef);
    }

    public org.semanticwb.platform.SemanticObject getAttributeValueRef()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swp_attributeValueRef);
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
