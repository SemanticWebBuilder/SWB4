package org.semanticwb.process.model.base;


public abstract class ItemDefinitionBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable,org.semanticwb.process.model.Collectionable,org.semanticwb.process.model.Rootable
{
    public static final org.semanticwb.platform.SemanticProperty swp_itemKind=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#itemKind");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessImport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ProcessImport");
    public static final org.semanticwb.platform.SemanticProperty swp_processImport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#processImport");
    public static final org.semanticwb.platform.SemanticClass swp_ItemDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ItemDefinition");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ItemDefinition");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ItemDefinition> listItemDefinitions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemDefinition>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ItemDefinition> listItemDefinitions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemDefinition>(it, true);
        }

        public static org.semanticwb.process.model.ItemDefinition createItemDefinition(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ItemDefinition.ClassMgr.createItemDefinition(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ItemDefinition getItemDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ItemDefinition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ItemDefinition createItemDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ItemDefinition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeItemDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasItemDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (getItemDefinition(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ItemDefinition> listItemDefinitionByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ItemDefinition> listItemDefinitionByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ItemDefinition> listItemDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ItemDefinition> listItemDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ItemDefinition> listItemDefinitionByProcessImport(org.semanticwb.process.model.ProcessImport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processImport, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ItemDefinition> listItemDefinitionByProcessImport(org.semanticwb.process.model.ProcessImport value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processImport,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ItemDefinition> listItemDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ItemDefinition> listItemDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ItemDefinitionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getItemKind()
    {
        return getSemanticObject().getProperty(swp_itemKind);
    }

    public void setItemKind(String value)
    {
        getSemanticObject().setProperty(swp_itemKind, value);
    }

    public void setProcessImport(org.semanticwb.process.model.ProcessImport value)
    {
        getSemanticObject().setObjectProperty(swp_processImport, value.getSemanticObject());
    }

    public void removeProcessImport()
    {
        getSemanticObject().removeProperty(swp_processImport);
    }

    public org.semanticwb.process.model.ProcessImport getProcessImport()
    {
         org.semanticwb.process.model.ProcessImport ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processImport);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessImport)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isCollection()
    {
        return getSemanticObject().getBooleanProperty(swp_collection);
    }

    public void setCollection(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_collection, value);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
