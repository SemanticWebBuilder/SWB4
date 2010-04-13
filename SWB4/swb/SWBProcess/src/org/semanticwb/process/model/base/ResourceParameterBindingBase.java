package org.semanticwb.process.model.base;


public abstract class ResourceParameterBindingBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Expressionable,org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_ResourceParameter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ResourceParameter");
    public static final org.semanticwb.platform.SemanticProperty swp_parameterRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#parameterRef");
    public static final org.semanticwb.platform.SemanticClass swp_ResourceParameterBinding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ResourceParameterBinding");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ResourceParameterBinding");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindings(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindings()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding>(it, true);
        }

        public static org.semanticwb.process.model.ResourceParameterBinding createResourceParameterBinding(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ResourceParameterBinding.ClassMgr.createResourceParameterBinding(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ResourceParameterBinding getResourceParameterBinding(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ResourceParameterBinding)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ResourceParameterBinding createResourceParameterBinding(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ResourceParameterBinding)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeResourceParameterBinding(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasResourceParameterBinding(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResourceParameterBinding(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindingByParameterRef(org.semanticwb.process.model.ResourceParameter value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parameterRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindingByParameterRef(org.semanticwb.process.model.ResourceParameter value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parameterRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindingByExpression(org.semanticwb.process.model.Expression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_expression, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindingByExpression(org.semanticwb.process.model.Expression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_expression,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindingByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindingByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindingByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindingByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindingByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindingByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ResourceParameterBindingBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setParameterRef(org.semanticwb.process.model.ResourceParameter value)
    {
        getSemanticObject().setObjectProperty(swp_parameterRef, value.getSemanticObject());
    }

    public void removeParameterRef()
    {
        getSemanticObject().removeProperty(swp_parameterRef);
    }

    public org.semanticwb.process.model.ResourceParameter getParameterRef()
    {
         org.semanticwb.process.model.ResourceParameter ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_parameterRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ResourceParameter)obj.createGenericInstance();
         }
         return ret;
    }

    public void setExpression(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_expression, value.getSemanticObject());
    }

    public void removeExpression()
    {
        getSemanticObject().removeProperty(swp_expression);
    }

    public org.semanticwb.process.model.Expression getExpression()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_expression);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }
}
