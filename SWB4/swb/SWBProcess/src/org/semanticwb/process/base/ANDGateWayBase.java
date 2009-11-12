package org.semanticwb.process.base;


public class ANDGateWayBase extends org.semanticwb.process.GateWay implements org.semanticwb.process.FlowObject,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swbps_ANDGateWay=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ANDGateWay");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ANDGateWay");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWays(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.ANDGateWay> listANDGateWays()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ANDGateWay>(it, true);
       }

       public static org.semanticwb.process.ANDGateWay createANDGateWay(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.ANDGateWay.ClassMgr.createANDGateWay(String.valueOf(id), model);
       }

       public static org.semanticwb.process.ANDGateWay getANDGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ANDGateWay)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.ANDGateWay createANDGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ANDGateWay)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeANDGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasANDGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (getANDGateWay(id, model)!=null);
       }
    }

    public ANDGateWayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
