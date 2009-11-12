package org.semanticwb.process.base;


public class XOREventGateWayBase extends org.semanticwb.process.XORGateWay implements org.semanticwb.process.FlowObject,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swbps_XOREventGateWay=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#XOREventGateWay");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#XOREventGateWay");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.XOREventGateWay> listXOREventGateWays(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.XOREventGateWay>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.XOREventGateWay> listXOREventGateWays()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.XOREventGateWay>(it, true);
       }

       public static org.semanticwb.process.XOREventGateWay createXOREventGateWay(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.XOREventGateWay.ClassMgr.createXOREventGateWay(String.valueOf(id), model);
       }

       public static org.semanticwb.process.XOREventGateWay getXOREventGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.XOREventGateWay)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.XOREventGateWay createXOREventGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.XOREventGateWay)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeXOREventGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasXOREventGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (getXOREventGateWay(id, model)!=null);
       }
    }

    public XOREventGateWayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
