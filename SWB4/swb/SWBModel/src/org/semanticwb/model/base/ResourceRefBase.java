package org.semanticwb.model.base;


public abstract class ResourceRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable,org.semanticwb.model.Priorityable
{
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resource");
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef>(it, true);
        }

        public static org.semanticwb.model.ResourceRef createResourceRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.ResourceRef.ClassMgr.createResourceRef(String.valueOf(id), model);
        }

        public static org.semanticwb.model.ResourceRef getResourceRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.ResourceRef createResourceRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeResourceRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasResourceRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResourceRef(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_resource, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_resource,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ResourceRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(swb_priority, value);
    }

    public void setResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_resource, value.getSemanticObject());
        }else
        {
            removeResource();
        }
    }

    public void removeResource()
    {
        getSemanticObject().removeProperty(swb_resource);
    }

    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_resource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
