package org.semanticwb.process.model.base;


public abstract class ComplexGatewayBase extends org.semanticwb.process.model.Gateway implements org.semanticwb.process.model.Gateable,org.semanticwb.process.model.Assignable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
       public static final org.semanticwb.platform.SemanticProperty swp_outgoingCondition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#outgoingCondition");
       public static final org.semanticwb.platform.SemanticProperty swp_incomingCondition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#incomingCondition");
       public static final org.semanticwb.platform.SemanticClass swp_ComplexGateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ComplexGateway");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ComplexGateway");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGateways(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGateways()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway>(it, true);
       }

       public static org.semanticwb.process.model.ComplexGateway createComplexGateway(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.ComplexGateway.ClassMgr.createComplexGateway(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.ComplexGateway getComplexGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ComplexGateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.ComplexGateway createComplexGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ComplexGateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeComplexGateway(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasComplexGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (getComplexGateway(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByGate(org.semanticwb.process.model.Gate hasgate,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasGate, hasgate.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByGate(org.semanticwb.process.model.Gate hasgate)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(hasgate.getSemanticObject().getModel().listSubjects(swp_hasGate,hasgate.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByOutgoingCondition(org.semanticwb.process.model.Expression outgoingcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_outgoingCondition, outgoingcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByOutgoingCondition(org.semanticwb.process.model.Expression outgoingcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(outgoingcondition.getSemanticObject().getModel().listSubjects(swp_outgoingCondition,outgoingcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByIncomingCondition(org.semanticwb.process.model.Expression incomingcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_incomingCondition, incomingcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ComplexGateway> listComplexGatewayByIncomingCondition(org.semanticwb.process.model.Expression incomingcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexGateway> it=new org.semanticwb.model.GenericIterator(incomingcondition.getSemanticObject().getModel().listSubjects(swp_incomingCondition,incomingcondition.getSemanticObject()));
       return it;
   }
    }

    public ComplexGatewayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setOutgoingCondition(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_outgoingCondition, value.getSemanticObject());
    }

    public void removeOutgoingCondition()
    {
        getSemanticObject().removeProperty(swp_outgoingCondition);
    }


    public org.semanticwb.process.model.Expression getOutgoingCondition()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_outgoingCondition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public void setIncomingCondition(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_incomingCondition, value.getSemanticObject());
    }

    public void removeIncomingCondition()
    {
        getSemanticObject().removeProperty(swp_incomingCondition);
    }


    public org.semanticwb.process.model.Expression getIncomingCondition()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_incomingCondition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }
}
