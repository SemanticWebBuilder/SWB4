package org.semanticwb.process.model.base;


public abstract class EventBasedExclusiveGatewayBase extends org.semanticwb.process.model.ExclusiveGateway implements org.semanticwb.process.model.Gateable,org.semanticwb.process.model.Assignable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Instantiable
{
       public static final org.semanticwb.platform.SemanticClass swp_EventBasedExclusiveGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EventBasedExclusiveGateway");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EventBasedExclusiveGateway");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.EventBasedExclusiveGateway> listEventBasedExclusiveGateways(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedExclusiveGateway>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.EventBasedExclusiveGateway> listEventBasedExclusiveGateways()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedExclusiveGateway>(it, true);
       }

       public static org.semanticwb.process.model.EventBasedExclusiveGateway getEventBasedExclusiveGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.EventBasedExclusiveGateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.EventBasedExclusiveGateway createEventBasedExclusiveGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.EventBasedExclusiveGateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeEventBasedExclusiveGateway(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasEventBasedExclusiveGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (getEventBasedExclusiveGateway(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.EventBasedExclusiveGateway> listEventBasedExclusiveGatewayByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EventBasedExclusiveGateway> listEventBasedExclusiveGatewayByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EventBasedExclusiveGateway> listEventBasedExclusiveGatewayByGate(org.semanticwb.process.model.Gate hasgate,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasGate, hasgate.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EventBasedExclusiveGateway> listEventBasedExclusiveGatewayByGate(org.semanticwb.process.model.Gate hasgate)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(hasgate.getSemanticObject().getModel().listSubjects(swp_hasGate,hasgate.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EventBasedExclusiveGateway> listEventBasedExclusiveGatewayByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EventBasedExclusiveGateway> listEventBasedExclusiveGatewayByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EventBasedExclusiveGateway> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public EventBasedExclusiveGatewayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isInstantiate()
    {
        return getSemanticObject().getBooleanProperty(swp_instantiate);
    }

    public void setInstantiate(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_instantiate, value);
    }
}
