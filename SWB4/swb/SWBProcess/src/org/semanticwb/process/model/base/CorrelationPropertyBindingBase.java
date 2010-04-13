package org.semanticwb.process.model.base;


public abstract class CorrelationPropertyBindingBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationProperty");
    public static final org.semanticwb.platform.SemanticProperty swp_correlationPropertyRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#correlationPropertyRef");
    public static final org.semanticwb.platform.SemanticClass swp_FormalExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FormalExpression");
    public static final org.semanticwb.platform.SemanticProperty swp_dataPath=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#dataPath");
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationPropertyBinding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationPropertyBinding");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationPropertyBinding");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindings(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindings()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding>(it, true);
        }

        public static org.semanticwb.process.model.CorrelationPropertyBinding createCorrelationPropertyBinding(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CorrelationPropertyBinding.ClassMgr.createCorrelationPropertyBinding(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.CorrelationPropertyBinding getCorrelationPropertyBinding(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CorrelationPropertyBinding)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.CorrelationPropertyBinding createCorrelationPropertyBinding(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CorrelationPropertyBinding)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCorrelationPropertyBinding(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCorrelationPropertyBinding(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCorrelationPropertyBinding(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindingByCorrelationPropertyRef(org.semanticwb.process.model.CorrelationProperty value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_correlationPropertyRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindingByCorrelationPropertyRef(org.semanticwb.process.model.CorrelationProperty value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_correlationPropertyRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindingByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindingByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindingByDataPath(org.semanticwb.process.model.FormalExpression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_dataPath, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindingByDataPath(org.semanticwb.process.model.FormalExpression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_dataPath,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindingByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindingByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindingByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyBinding> listCorrelationPropertyBindingByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CorrelationPropertyBindingBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setCorrelationPropertyRef(org.semanticwb.process.model.CorrelationProperty value)
    {
        getSemanticObject().setObjectProperty(swp_correlationPropertyRef, value.getSemanticObject());
    }

    public void removeCorrelationPropertyRef()
    {
        getSemanticObject().removeProperty(swp_correlationPropertyRef);
    }

    public org.semanticwb.process.model.CorrelationProperty getCorrelationPropertyRef()
    {
         org.semanticwb.process.model.CorrelationProperty ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_correlationPropertyRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.CorrelationProperty)obj.createGenericInstance();
         }
         return ret;
    }

    public void setDataPath(org.semanticwb.process.model.FormalExpression value)
    {
        getSemanticObject().setObjectProperty(swp_dataPath, value.getSemanticObject());
    }

    public void removeDataPath()
    {
        getSemanticObject().removeProperty(swp_dataPath);
    }

    public org.semanticwb.process.model.FormalExpression getDataPath()
    {
         org.semanticwb.process.model.FormalExpression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_dataPath);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FormalExpression)obj.createGenericInstance();
         }
         return ret;
    }
}
