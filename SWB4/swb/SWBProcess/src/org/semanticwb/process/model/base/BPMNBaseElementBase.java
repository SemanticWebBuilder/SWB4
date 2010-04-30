package org.semanticwb.process.model.base;


public abstract class BPMNBaseElementBase extends org.semanticwb.process.model.BPMNElement implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionDefinition");
    public static final org.semanticwb.platform.SemanticProperty swp_hasExtensionDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasExtensionDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionAttributeValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionAttributeValue");
    public static final org.semanticwb.platform.SemanticProperty swp_hasExtensionValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasExtensionValue");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNBaseElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNBaseElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNBaseElement");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNBaseElement> listBPMNBaseElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNBaseElement>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNBaseElement> listBPMNBaseElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNBaseElement>(it, true);
        }

        public static org.semanticwb.process.model.BPMNBaseElement createBPMNBaseElement(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.BPMNBaseElement.ClassMgr.createBPMNBaseElement(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.BPMNBaseElement getBPMNBaseElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNBaseElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNBaseElement createBPMNBaseElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNBaseElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNBaseElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNBaseElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNBaseElement(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNBaseElement> listBPMNBaseElementByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNBaseElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNBaseElement> listBPMNBaseElementByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNBaseElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNBaseElement> listBPMNBaseElementByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNBaseElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNBaseElement> listBPMNBaseElementByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNBaseElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNBaseElement> listBPMNBaseElementByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNBaseElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNBaseElement> listBPMNBaseElementByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNBaseElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public BPMNBaseElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Documentation> listDocumentations()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Documentation>(getSemanticObject().listObjectProperties(swp_hasDocumentation));
    }

    public boolean hasDocumentation(org.semanticwb.process.model.Documentation value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasDocumentation,value.getSemanticObject());
        }
        return ret;
    }

    public void addDocumentation(org.semanticwb.process.model.Documentation value)
    {
        getSemanticObject().addObjectProperty(swp_hasDocumentation, value.getSemanticObject());
    }

    public void removeAllDocumentation()
    {
        getSemanticObject().removeProperty(swp_hasDocumentation);
    }

    public void removeDocumentation(org.semanticwb.process.model.Documentation value)
    {
        getSemanticObject().removeObjectProperty(swp_hasDocumentation,value.getSemanticObject());
    }

    public org.semanticwb.process.model.Documentation getDocumentation()
    {
         org.semanticwb.process.model.Documentation ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasDocumentation);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Documentation)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionDefinition> listExtensionDefinitions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionDefinition>(getSemanticObject().listObjectProperties(swp_hasExtensionDefinition));
    }

    public boolean hasExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasExtensionDefinition,value.getSemanticObject());
        }
        return ret;
    }

    public void addExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
    {
        getSemanticObject().addObjectProperty(swp_hasExtensionDefinition, value.getSemanticObject());
    }

    public void removeAllExtensionDefinition()
    {
        getSemanticObject().removeProperty(swp_hasExtensionDefinition);
    }

    public void removeExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
    {
        getSemanticObject().removeObjectProperty(swp_hasExtensionDefinition,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ExtensionDefinition getExtensionDefinition()
    {
         org.semanticwb.process.model.ExtensionDefinition ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasExtensionDefinition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ExtensionDefinition)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeValue> listExtensionValues()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeValue>(getSemanticObject().listObjectProperties(swp_hasExtensionValue));
    }

    public boolean hasExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasExtensionValue,value.getSemanticObject());
        }
        return ret;
    }

    public void addExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
    {
        getSemanticObject().addObjectProperty(swp_hasExtensionValue, value.getSemanticObject());
    }

    public void removeAllExtensionValue()
    {
        getSemanticObject().removeProperty(swp_hasExtensionValue);
    }

    public void removeExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
    {
        getSemanticObject().removeObjectProperty(swp_hasExtensionValue,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ExtensionAttributeValue getExtensionValue()
    {
         org.semanticwb.process.model.ExtensionAttributeValue ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasExtensionValue);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ExtensionAttributeValue)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
