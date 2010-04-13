package org.semanticwb.process.model.base;


public abstract class ExtensionDefinitionBase extends org.semanticwb.process.model.BPMNElement implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionAttributeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionAttributeDefinition");
    public static final org.semanticwb.platform.SemanticProperty swp_hasExtensionAttributeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasExtensionAttributeDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionDefinition");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionDefinition");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ExtensionDefinition> listExtensionDefinitions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionDefinition>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ExtensionDefinition> listExtensionDefinitions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionDefinition>(it, true);
        }

        public static org.semanticwb.process.model.ExtensionDefinition createExtensionDefinition(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ExtensionDefinition.ClassMgr.createExtensionDefinition(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ExtensionDefinition getExtensionDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ExtensionDefinition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ExtensionDefinition createExtensionDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ExtensionDefinition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeExtensionDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasExtensionDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (getExtensionDefinition(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ExtensionDefinition> listExtensionDefinitionByExtensionAttributeDefinition(org.semanticwb.process.model.ExtensionAttributeDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionAttributeDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ExtensionDefinition> listExtensionDefinitionByExtensionAttributeDefinition(org.semanticwb.process.model.ExtensionAttributeDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionAttributeDefinition,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ExtensionDefinitionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeDefinition> listExtensionAttributeDefinitions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExtensionAttributeDefinition>(getSemanticObject().listObjectProperties(swp_hasExtensionAttributeDefinition));
    }

    public boolean hasExtensionAttributeDefinition(org.semanticwb.process.model.ExtensionAttributeDefinition value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasExtensionAttributeDefinition,value.getSemanticObject());
        }
        return ret;
    }

    public void addExtensionAttributeDefinition(org.semanticwb.process.model.ExtensionAttributeDefinition value)
    {
        getSemanticObject().addObjectProperty(swp_hasExtensionAttributeDefinition, value.getSemanticObject());
    }

    public void removeAllExtensionAttributeDefinition()
    {
        getSemanticObject().removeProperty(swp_hasExtensionAttributeDefinition);
    }

    public void removeExtensionAttributeDefinition(org.semanticwb.process.model.ExtensionAttributeDefinition value)
    {
        getSemanticObject().removeObjectProperty(swp_hasExtensionAttributeDefinition,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ExtensionAttributeDefinition getExtensionAttributeDefinition()
    {
         org.semanticwb.process.model.ExtensionAttributeDefinition ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasExtensionAttributeDefinition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ExtensionAttributeDefinition)obj.createGenericInstance();
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
