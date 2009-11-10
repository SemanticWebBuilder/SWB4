package org.semanticwb.process.base;


public class SequenceFlowBase extends org.semanticwb.process.ConnectionObject 
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticClass swbps_FlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_toFlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#toFlowObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_fromFlowObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#fromFlowObjectInv");
       public static final org.semanticwb.platform.SemanticClass swbps_SequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SequenceFlow");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SequenceFlow");

       public static java.util.Iterator<org.semanticwb.process.SequenceFlow> listSequenceFlows(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.SequenceFlow>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.SequenceFlow> listSequenceFlows()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.SequenceFlow>(it, true);
       }

       public static org.semanticwb.process.SequenceFlow createSequenceFlow(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.SequenceFlow.ClassMgr.createSequenceFlow(String.valueOf(id), model);
       }

       public static org.semanticwb.process.SequenceFlow getSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.SequenceFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.SequenceFlow createSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.SequenceFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (getSequenceFlow(id, model)!=null);
       }
    }

    public SequenceFlowBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
