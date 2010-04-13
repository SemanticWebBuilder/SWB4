package org.semanticwb.process.model.base;


public abstract class ActivityResourceBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_ResourceAssignmentExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ResourceAssignmentExpression");
    public static final org.semanticwb.platform.SemanticProperty swp_resourceAssignmentExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#resourceAssignmentExpression");
    public static final org.semanticwb.platform.SemanticClass swp_ResourceParameterBinding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ResourceParameterBinding");
    public static final org.semanticwb.platform.SemanticProperty swp_hasResourceParameterBinding=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasResourceParameterBinding");
    public static final org.semanticwb.platform.SemanticClass swp_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Resource");
    public static final org.semanticwb.platform.SemanticProperty swp_resourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#resourceRef");
    public static final org.semanticwb.platform.SemanticClass swp_ActivityResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ActivityResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ActivityResource");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResources(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResources()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource>(it, true);
        }

        public static org.semanticwb.process.model.ActivityResource createActivityResource(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ActivityResource.ClassMgr.createActivityResource(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ActivityResource getActivityResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ActivityResource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ActivityResource createActivityResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ActivityResource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeActivityResource(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasActivityResource(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActivityResource(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResourceByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResourceByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResourceByResourceAssignmentExpression(org.semanticwb.process.model.ResourceAssignmentExpression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_resourceAssignmentExpression, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResourceByResourceAssignmentExpression(org.semanticwb.process.model.ResourceAssignmentExpression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_resourceAssignmentExpression,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResourceByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResourceByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResourceByResourceParameterBinding(org.semanticwb.process.model.ResourceParameterBinding value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasResourceParameterBinding, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResourceByResourceParameterBinding(org.semanticwb.process.model.ResourceParameterBinding value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasResourceParameterBinding,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResourceByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResourceByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResourceByResourceRef(org.semanticwb.process.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_resourceRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ActivityResource> listActivityResourceByResourceRef(org.semanticwb.process.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActivityResource> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_resourceRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ActivityResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setResourceAssignmentExpression(org.semanticwb.process.model.ResourceAssignmentExpression value)
    {
        getSemanticObject().setObjectProperty(swp_resourceAssignmentExpression, value.getSemanticObject());
    }

    public void removeResourceAssignmentExpression()
    {
        getSemanticObject().removeProperty(swp_resourceAssignmentExpression);
    }

    public org.semanticwb.process.model.ResourceAssignmentExpression getResourceAssignmentExpression()
    {
         org.semanticwb.process.model.ResourceAssignmentExpression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_resourceAssignmentExpression);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ResourceAssignmentExpression)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding> listResourceParameterBindings()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceParameterBinding>(getSemanticObject().listObjectProperties(swp_hasResourceParameterBinding));
    }

    public boolean hasResourceParameterBinding(org.semanticwb.process.model.ResourceParameterBinding value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasResourceParameterBinding,value.getSemanticObject());
        }
        return ret;
    }

    public void addResourceParameterBinding(org.semanticwb.process.model.ResourceParameterBinding value)
    {
        getSemanticObject().addObjectProperty(swp_hasResourceParameterBinding, value.getSemanticObject());
    }

    public void removeAllResourceParameterBinding()
    {
        getSemanticObject().removeProperty(swp_hasResourceParameterBinding);
    }

    public void removeResourceParameterBinding(org.semanticwb.process.model.ResourceParameterBinding value)
    {
        getSemanticObject().removeObjectProperty(swp_hasResourceParameterBinding,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ResourceParameterBinding getResourceParameterBinding()
    {
         org.semanticwb.process.model.ResourceParameterBinding ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasResourceParameterBinding);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ResourceParameterBinding)obj.createGenericInstance();
         }
         return ret;
    }

    public void setResourceRef(org.semanticwb.process.model.Resource value)
    {
        getSemanticObject().setObjectProperty(swp_resourceRef, value.getSemanticObject());
    }

    public void removeResourceRef()
    {
        getSemanticObject().removeProperty(swp_resourceRef);
    }

    public org.semanticwb.process.model.Resource getResourceRef()
    {
         org.semanticwb.process.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_resourceRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }
}
