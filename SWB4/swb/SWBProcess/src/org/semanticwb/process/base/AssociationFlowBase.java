package org.semanticwb.process.base;


public class AssociationFlowBase extends org.semanticwb.process.ConnectionObject 
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticClass swbps_FlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#FlowObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_toFlowObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#toFlowObject");
       public static final org.semanticwb.platform.SemanticProperty swbps_fromFlowObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#fromFlowObjectInv");
       public static final org.semanticwb.platform.SemanticClass swbps_AssociationFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AssociationFlow");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AssociationFlow");

       public static java.util.Iterator<org.semanticwb.process.AssociationFlow> listAssociationFlows(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.AssociationFlow>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.AssociationFlow> listAssociationFlows()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.AssociationFlow>(it, true);
       }

       public static org.semanticwb.process.AssociationFlow createAssociationFlow(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.AssociationFlow.ClassMgr.createAssociationFlow(String.valueOf(id), model);
       }

       public static org.semanticwb.process.AssociationFlow getAssociationFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.AssociationFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.AssociationFlow createAssociationFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.AssociationFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeAssociationFlow(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasAssociationFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (getAssociationFlow(id, model)!=null);
       }
    }

    public AssociationFlowBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
