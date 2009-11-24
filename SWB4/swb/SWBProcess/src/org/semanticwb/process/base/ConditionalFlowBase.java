package org.semanticwb.process.base;


public abstract class ConditionalFlowBase extends org.semanticwb.process.SequenceFlow 
{
       public static final org.semanticwb.platform.SemanticProperty swbps_flowCondition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#flowCondition");
       public static final org.semanticwb.platform.SemanticClass swbps_ConditionalFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConditionalFlow");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConditionalFlow");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.ConditionalFlow> listConditionalFlows(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ConditionalFlow>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.ConditionalFlow> listConditionalFlows()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ConditionalFlow>(it, true);
       }

       public static org.semanticwb.process.ConditionalFlow createConditionalFlow(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.ConditionalFlow.ClassMgr.createConditionalFlow(String.valueOf(id), model);
       }

       public static org.semanticwb.process.ConditionalFlow getConditionalFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ConditionalFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.ConditionalFlow createConditionalFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ConditionalFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeConditionalFlow(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasConditionalFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (getConditionalFlow(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.ConditionalFlow> listConditionalFlowByToFlowObject(org.semanticwb.process.FlowObject toflowobject,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_toFlowObject, toflowobject.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ConditionalFlow> listConditionalFlowByToFlowObject(org.semanticwb.process.FlowObject toflowobject)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(toflowobject.getSemanticObject().getModel().listSubjects(swbps_toFlowObject,toflowobject.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.ConditionalFlow> listConditionalFlowByFromFlowObject(org.semanticwb.process.FlowObject fromflowobjectinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_fromFlowObjectInv, fromflowobjectinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ConditionalFlow> listConditionalFlowByFromFlowObject(org.semanticwb.process.FlowObject fromflowobjectinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ConditionalFlow> it=new org.semanticwb.model.GenericIterator(fromflowobjectinv.getSemanticObject().getModel().listSubjects(swbps_fromFlowObjectInv,fromflowobjectinv.getSemanticObject()));
       return it;
   }
    }

    public ConditionalFlowBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getFlowCondition()
    {
        return getSemanticObject().getProperty(swbps_flowCondition);
    }

    public void setFlowCondition(String value)
    {
        getSemanticObject().setProperty(swbps_flowCondition, value);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
