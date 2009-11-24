package org.semanticwb.process.base;


public abstract class DefaultFlowBase extends org.semanticwb.process.SequenceFlow 
{
       public static final org.semanticwb.platform.SemanticClass swbps_DefaultFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DefaultFlow");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DefaultFlow");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.DefaultFlow> listDefaultFlows(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.DefaultFlow>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.DefaultFlow> listDefaultFlows()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.DefaultFlow>(it, true);
       }

       public static org.semanticwb.process.DefaultFlow createDefaultFlow(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.DefaultFlow.ClassMgr.createDefaultFlow(String.valueOf(id), model);
       }

       public static org.semanticwb.process.DefaultFlow getDefaultFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.DefaultFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.DefaultFlow createDefaultFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.DefaultFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeDefaultFlow(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasDefaultFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (getDefaultFlow(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.DefaultFlow> listDefaultFlowByToFlowObject(org.semanticwb.process.FlowObject toflowobject,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.DefaultFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_toFlowObject, toflowobject.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.DefaultFlow> listDefaultFlowByToFlowObject(org.semanticwb.process.FlowObject toflowobject)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.DefaultFlow> it=new org.semanticwb.model.GenericIterator(toflowobject.getSemanticObject().getModel().listSubjects(swbps_toFlowObject,toflowobject.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.DefaultFlow> listDefaultFlowByFromFlowObject(org.semanticwb.process.FlowObject fromflowobjectinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.DefaultFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_fromFlowObjectInv, fromflowobjectinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.DefaultFlow> listDefaultFlowByFromFlowObject(org.semanticwb.process.FlowObject fromflowobjectinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.DefaultFlow> it=new org.semanticwb.model.GenericIterator(fromflowobjectinv.getSemanticObject().getModel().listSubjects(swbps_fromFlowObjectInv,fromflowobjectinv.getSemanticObject()));
       return it;
   }
    }

    public DefaultFlowBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
