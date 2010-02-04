package org.semanticwb.process.model.base;


public abstract class ConditionalSequenceFlowBase extends org.semanticwb.process.model.SequenceFlow implements org.semanticwb.process.model.Conditionable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_ConditionalSequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConditionalSequenceFlow");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConditionalSequenceFlow");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.ConditionalSequenceFlow> listConditionalSequenceFlows(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalSequenceFlow>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.ConditionalSequenceFlow> listConditionalSequenceFlows()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalSequenceFlow>(it, true);
       }

       public static org.semanticwb.process.model.ConditionalSequenceFlow createConditionalSequenceFlow(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.ConditionalSequenceFlow.ClassMgr.createConditionalSequenceFlow(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.ConditionalSequenceFlow getConditionalSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ConditionalSequenceFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.ConditionalSequenceFlow createConditionalSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ConditionalSequenceFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeConditionalSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasConditionalSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (getConditionalSequenceFlow(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.ConditionalSequenceFlow> listConditionalSequenceFlowBySourceRef(org.semanticwb.process.model.GraphicalElement sourceref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalSequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_sourceRef, sourceref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ConditionalSequenceFlow> listConditionalSequenceFlowBySourceRef(org.semanticwb.process.model.GraphicalElement sourceref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalSequenceFlow> it=new org.semanticwb.model.GenericIterator(sourceref.getSemanticObject().getModel().listSubjects(swp_sourceRef,sourceref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ConditionalSequenceFlow> listConditionalSequenceFlowByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalSequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ConditionalSequenceFlow> listConditionalSequenceFlowByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalSequenceFlow> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ConditionalSequenceFlow> listConditionalSequenceFlowByConditionExpression(org.semanticwb.process.model.Expression conditionexpression,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalSequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_conditionExpression, conditionexpression.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ConditionalSequenceFlow> listConditionalSequenceFlowByConditionExpression(org.semanticwb.process.model.Expression conditionexpression)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalSequenceFlow> it=new org.semanticwb.model.GenericIterator(conditionexpression.getSemanticObject().getModel().listSubjects(swp_conditionExpression,conditionexpression.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ConditionalSequenceFlow> listConditionalSequenceFlowByTargetRef(org.semanticwb.process.model.GraphicalElement targetref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalSequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_targetRef, targetref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ConditionalSequenceFlow> listConditionalSequenceFlowByTargetRef(org.semanticwb.process.model.GraphicalElement targetref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConditionalSequenceFlow> it=new org.semanticwb.model.GenericIterator(targetref.getSemanticObject().getModel().listSubjects(swp_targetRef,targetref.getSemanticObject()));
       return it;
   }
    }

    public ConditionalSequenceFlowBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setConditionExpression(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_conditionExpression, value.getSemanticObject());
    }

    public void removeConditionExpression()
    {
        getSemanticObject().removeProperty(swp_conditionExpression);
    }


    public org.semanticwb.process.model.Expression getConditionExpression()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_conditionExpression);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }
}
