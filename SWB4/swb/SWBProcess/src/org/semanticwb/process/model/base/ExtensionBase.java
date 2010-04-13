package org.semanticwb.process.model.base;


public abstract class ExtensionBase extends org.semanticwb.process.model.BPMNElement 
{
    public static final org.semanticwb.platform.SemanticProperty swp_mustUnderstand=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#mustUnderstand");
    public static final org.semanticwb.platform.SemanticClass swp_ExtensionDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExtensionDefinition");
    public static final org.semanticwb.platform.SemanticProperty swp_definition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#definition");
    public static final org.semanticwb.platform.SemanticClass swp_Extension=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Extension");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Extension");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Extension> listExtensions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Extension>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Extension> listExtensions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Extension>(it, true);
        }

        public static org.semanticwb.process.model.Extension getExtension(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Extension)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Extension createExtension(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Extension)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeExtension(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasExtension(String id, org.semanticwb.model.SWBModel model)
        {
            return (getExtension(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Extension> listExtensionByDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Extension> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_definition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Extension> listExtensionByDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Extension> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_definition,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ExtensionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isMustUnderstand()
    {
        return getSemanticObject().getBooleanProperty(swp_mustUnderstand);
    }

    public void setMustUnderstand(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_mustUnderstand, value);
    }

    public void setDefinition(org.semanticwb.process.model.ExtensionDefinition value)
    {
        getSemanticObject().setObjectProperty(swp_definition, value.getSemanticObject());
    }

    public void removeDefinition()
    {
        getSemanticObject().removeProperty(swp_definition);
    }

    public org.semanticwb.process.model.ExtensionDefinition getDefinition()
    {
         org.semanticwb.process.model.ExtensionDefinition ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_definition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ExtensionDefinition)obj.createGenericInstance();
         }
         return ret;
    }
}
