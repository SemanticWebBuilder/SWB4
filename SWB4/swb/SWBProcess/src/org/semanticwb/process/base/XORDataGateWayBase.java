package org.semanticwb.process.base;


public class XORDataGateWayBase extends org.semanticwb.process.XORGateWay implements org.semanticwb.process.FlowObject,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swbps_XORDataGateWay=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#XORDataGateWay");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#XORDataGateWay");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.XORDataGateWay> listXORDataGateWays(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.XORDataGateWay>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.XORDataGateWay> listXORDataGateWays()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.XORDataGateWay>(it, true);
       }

       public static org.semanticwb.process.XORDataGateWay createXORDataGateWay(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.XORDataGateWay.ClassMgr.createXORDataGateWay(String.valueOf(id), model);
       }

       public static org.semanticwb.process.XORDataGateWay getXORDataGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.XORDataGateWay)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.XORDataGateWay createXORDataGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.XORDataGateWay)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeXORDataGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasXORDataGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (getXORDataGateWay(id, model)!=null);
       }
    }

    public XORDataGateWayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
