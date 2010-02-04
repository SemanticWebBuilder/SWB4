package org.semanticwb.process.model.base;


public abstract class DataBasedExclusiveGatewayBase extends org.semanticwb.process.model.ExclusiveGateway implements org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Gateable,org.semanticwb.process.model.Assignable
{
       public static final org.semanticwb.platform.SemanticProperty swp_markerVisible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#markerVisible");
       public static final org.semanticwb.platform.SemanticClass swp_DataBasedExclusiveGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataBasedExclusiveGateway");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataBasedExclusiveGateway");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.DataBasedExclusiveGateway> listDataBasedExclusiveGateways(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataBasedExclusiveGateway>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.DataBasedExclusiveGateway> listDataBasedExclusiveGateways()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataBasedExclusiveGateway>(it, true);
       }

       public static org.semanticwb.process.model.DataBasedExclusiveGateway getDataBasedExclusiveGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.DataBasedExclusiveGateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.DataBasedExclusiveGateway createDataBasedExclusiveGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.DataBasedExclusiveGateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeDataBasedExclusiveGateway(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasDataBasedExclusiveGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (getDataBasedExclusiveGateway(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.DataBasedExclusiveGateway> listDataBasedExclusiveGatewayByGate(org.semanticwb.process.model.Gate hasgate,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasGate, hasgate.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.DataBasedExclusiveGateway> listDataBasedExclusiveGatewayByGate(org.semanticwb.process.model.Gate hasgate)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(hasgate.getSemanticObject().getModel().listSubjects(swp_hasGate,hasgate.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.DataBasedExclusiveGateway> listDataBasedExclusiveGatewayByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.DataBasedExclusiveGateway> listDataBasedExclusiveGatewayByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.DataBasedExclusiveGateway> listDataBasedExclusiveGatewayByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.DataBasedExclusiveGateway> listDataBasedExclusiveGatewayByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.DataBasedExclusiveGateway> listDataBasedExclusiveGatewayByDefaultGate(org.semanticwb.process.model.Gate defaultgate,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_defaultGate, defaultgate.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.DataBasedExclusiveGateway> listDataBasedExclusiveGatewayByDefaultGate(org.semanticwb.process.model.Gate defaultgate)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(defaultgate.getSemanticObject().getModel().listSubjects(swp_defaultGate,defaultgate.getSemanticObject()));
       return it;
   }
    }

    public DataBasedExclusiveGatewayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isMarkerVisible()
    {
        return getSemanticObject().getBooleanProperty(swp_markerVisible);
    }

    public void setMarkerVisible(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_markerVisible, value);
    }
}
