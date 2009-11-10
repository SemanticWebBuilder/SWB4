package org.semanticwb.process.base;


public class ConnectionObjectBase extends org.semanticwb.model.SWBClass 
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticClass swbps_FlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_toFlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#toFlowObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_fromFlowObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#fromFlowObjectInv");
       public static final org.semanticwb.platform.SemanticClass swbps_ConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");

       public static java.util.Iterator<org.semanticwb.process.ConnectionObject> listConnectionObjects(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.ConnectionObject> listConnectionObjects()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject>(it, true);
       }

       public static org.semanticwb.process.ConnectionObject getConnectionObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ConnectionObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.ConnectionObject createConnectionObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ConnectionObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeConnectionObject(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasConnectionObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (getConnectionObject(id, model)!=null);
       }
    }

    public ConnectionObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setToFlowObject(org.semanticwb.process.FlowObject value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbps_toFlowObject, value.getSemanticObject());
    }

    public void removeToFlowObject()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_toFlowObject);
    }

   public static java.util.Iterator<org.semanticwb.process.ConnectionObject> listConnectionObjectByToFlowObject(org.semanticwb.process.FlowObject toflowobject,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_toFlowObject, toflowobject.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ConnectionObject> listConnectionObjectByToFlowObject(org.semanticwb.process.FlowObject toflowobject)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> it=new org.semanticwb.model.GenericIterator(toflowobject.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_toFlowObject,toflowobject.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.FlowObject getToFlowObject()
    {
         org.semanticwb.process.FlowObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_toFlowObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.FlowObject)obj.createGenericInstance();
         }
         return ret;
    }

    public void setFromFlowObject(org.semanticwb.process.FlowObject value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbps_fromFlowObjectInv, value.getSemanticObject());
    }

    public void removeFromFlowObject()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_fromFlowObjectInv);
    }

   public static java.util.Iterator<org.semanticwb.process.ConnectionObject> listConnectionObjectByFromFlowObject(org.semanticwb.process.FlowObject fromflowobjectinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_fromFlowObjectInv, fromflowobjectinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ConnectionObject> listConnectionObjectByFromFlowObject(org.semanticwb.process.FlowObject fromflowobjectinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ConnectionObject> it=new org.semanticwb.model.GenericIterator(fromflowobjectinv.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_fromFlowObjectInv,fromflowobjectinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.FlowObject getFromFlowObject()
    {
         org.semanticwb.process.FlowObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_fromFlowObjectInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.FlowObject)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
