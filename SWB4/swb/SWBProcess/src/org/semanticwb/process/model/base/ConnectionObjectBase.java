package org.semanticwb.process.model.base;


public abstract class ConnectionObjectBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_GraphicalElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#GraphicalElement");
    public static final org.semanticwb.platform.SemanticProperty swp_source=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#source");
    public static final org.semanticwb.platform.SemanticProperty swp_target=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#target");
    public static final org.semanticwb.platform.SemanticClass swp_ConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjects(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjects()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject>(it, true);
        }

        public static org.semanticwb.process.model.ConnectionObject getConnectionObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ConnectionObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ConnectionObject createConnectionObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ConnectionObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeConnectionObject(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasConnectionObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (getConnectionObject(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectBySource(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_source, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectBySource(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_source,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByTarget(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_target, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByTarget(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_target,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ConnectionObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setSource(org.semanticwb.process.model.GraphicalElement value)
    {
        getSemanticObject().setObjectProperty(swp_source, value.getSemanticObject());
    }

    public void removeSource()
    {
        getSemanticObject().removeProperty(swp_source);
    }

    public org.semanticwb.process.model.GraphicalElement getSource()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_source);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
         }
         return ret;
    }

    public void setTarget(org.semanticwb.process.model.GraphicalElement value)
    {
        getSemanticObject().setObjectProperty(swp_target, value.getSemanticObject());
    }

    public void removeTarget()
    {
        getSemanticObject().removeProperty(swp_target);
    }

    public org.semanticwb.process.model.GraphicalElement getTarget()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_target);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
