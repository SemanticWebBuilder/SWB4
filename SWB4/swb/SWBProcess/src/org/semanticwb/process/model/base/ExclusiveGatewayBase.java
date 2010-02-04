package org.semanticwb.process.model.base;


public abstract class ExclusiveGatewayBase extends org.semanticwb.process.model.Gateway implements org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Gateable,org.semanticwb.process.model.Assignable
{
       public static final org.semanticwb.platform.SemanticClass swp_ExclusiveGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExclusiveGateway");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ExclusiveGateway");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.ExclusiveGateway> listExclusiveGateways(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveGateway>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.ExclusiveGateway> listExclusiveGateways()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveGateway>(it, true);
       }

       public static org.semanticwb.process.model.ExclusiveGateway createExclusiveGateway(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.ExclusiveGateway.ClassMgr.createExclusiveGateway(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.ExclusiveGateway getExclusiveGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ExclusiveGateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.ExclusiveGateway createExclusiveGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ExclusiveGateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeExclusiveGateway(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasExclusiveGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (getExclusiveGateway(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.ExclusiveGateway> listExclusiveGatewayByGate(org.semanticwb.process.model.Gate hasgate,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasGate, hasgate.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ExclusiveGateway> listExclusiveGatewayByGate(org.semanticwb.process.model.Gate hasgate)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveGateway> it=new org.semanticwb.model.GenericIterator(hasgate.getSemanticObject().getModel().listSubjects(swp_hasGate,hasgate.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ExclusiveGateway> listExclusiveGatewayByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ExclusiveGateway> listExclusiveGatewayByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveGateway> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ExclusiveGateway> listExclusiveGatewayByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ExclusiveGateway> listExclusiveGatewayByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveGateway> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ExclusiveGateway> listExclusiveGatewayByDefaultGate(org.semanticwb.process.model.Gate defaultgate,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_defaultGate, defaultgate.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ExclusiveGateway> listExclusiveGatewayByDefaultGate(org.semanticwb.process.model.Gate defaultgate)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ExclusiveGateway> it=new org.semanticwb.model.GenericIterator(defaultgate.getSemanticObject().getModel().listSubjects(swp_defaultGate,defaultgate.getSemanticObject()));
       return it;
   }
    }

    public ExclusiveGatewayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
