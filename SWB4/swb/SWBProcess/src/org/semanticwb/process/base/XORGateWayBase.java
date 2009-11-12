package org.semanticwb.process.base;


public class XORGateWayBase extends org.semanticwb.process.GateWay implements org.semanticwb.process.FlowObject,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swbps_XORGateWay=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#XORGateWay");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#XORGateWay");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.XORGateWay> listXORGateWays(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.XORGateWay>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.XORGateWay> listXORGateWays()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.XORGateWay>(it, true);
       }

       public static org.semanticwb.process.XORGateWay createXORGateWay(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.XORGateWay.ClassMgr.createXORGateWay(String.valueOf(id), model);
       }

       public static org.semanticwb.process.XORGateWay getXORGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.XORGateWay)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.XORGateWay createXORGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.XORGateWay)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeXORGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasXORGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (getXORGateWay(id, model)!=null);
       }
    }

    public XORGateWayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
