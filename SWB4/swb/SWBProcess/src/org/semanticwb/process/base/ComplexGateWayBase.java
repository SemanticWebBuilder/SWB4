package org.semanticwb.process.base;


public class ComplexGateWayBase extends org.semanticwb.process.GateWay implements org.semanticwb.process.FlowObject,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swbps_ComplexGateWay=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ComplexGateWay");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ComplexGateWay");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.ComplexGateWay> listComplexGateWays(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ComplexGateWay>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.ComplexGateWay> listComplexGateWays()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ComplexGateWay>(it, true);
       }

       public static org.semanticwb.process.ComplexGateWay createComplexGateWay(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.ComplexGateWay.ClassMgr.createComplexGateWay(String.valueOf(id), model);
       }

       public static org.semanticwb.process.ComplexGateWay getComplexGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ComplexGateWay)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.ComplexGateWay createComplexGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ComplexGateWay)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeComplexGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasComplexGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (getComplexGateWay(id, model)!=null);
       }
    }

    public ComplexGateWayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
