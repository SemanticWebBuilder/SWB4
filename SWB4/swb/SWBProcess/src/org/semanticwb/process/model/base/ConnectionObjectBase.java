package org.semanticwb.process.model.base;


public abstract class ConnectionObjectBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swp_FlowNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowNode");
    public static final org.semanticwb.platform.SemanticProperty swp_toFlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#toFlowObject");
    public static final org.semanticwb.platform.SemanticProperty swp_fromFlowObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#fromFlowObjectInv");
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

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByToFlowObject(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_toFlowObject, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByToFlowObject(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_toFlowObject,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByFromFlowObject(org.semanticwb.process.model.FlowNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_fromFlowObjectInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByFromFlowObject(org.semanticwb.process.model.FlowNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_fromFlowObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ConnectionObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setToFlowObject(org.semanticwb.process.model.FlowNode value)
    {
        getSemanticObject().setObjectProperty(swp_toFlowObject, value.getSemanticObject());
    }

    public void removeToFlowObject()
    {
        getSemanticObject().removeProperty(swp_toFlowObject);
    }

    public org.semanticwb.process.model.FlowNode getToFlowObject()
    {
         org.semanticwb.process.model.FlowNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_toFlowObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNode)obj.createGenericInstance();
         }
         return ret;
    }

    public void setFromFlowObject(org.semanticwb.process.model.FlowNode value)
    {
        getSemanticObject().setObjectProperty(swp_fromFlowObjectInv, value.getSemanticObject());
    }

    public void removeFromFlowObject()
    {
        getSemanticObject().removeProperty(swp_fromFlowObjectInv);
    }

    public org.semanticwb.process.model.FlowNode getFromFlowObject()
    {
         org.semanticwb.process.model.FlowNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_fromFlowObjectInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowNode)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
