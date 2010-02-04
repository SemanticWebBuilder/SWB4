package org.semanticwb.process.model.base;


public abstract class ConditionBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Conditionable
{
       public static final org.semanticwb.platform.SemanticClass swp_Condition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Condition");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Condition");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Condition> listConditions(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Condition>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Condition> listConditions()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Condition>(it, true);
       }

       public static org.semanticwb.process.model.Condition createCondition(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Condition.ClassMgr.createCondition(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Condition getCondition(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Condition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Condition createCondition(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Condition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeCondition(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasCondition(String id, org.semanticwb.model.SWBModel model)
       {
           return (getCondition(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Condition> listConditionByConditionExpression(org.semanticwb.process.model.Expression conditionexpression,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Condition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_conditionExpression, conditionexpression.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Condition> listConditionByConditionExpression(org.semanticwb.process.model.Expression conditionexpression)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Condition> it=new org.semanticwb.model.GenericIterator(conditionexpression.getSemanticObject().getModel().listSubjects(swp_conditionExpression,conditionexpression.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Condition> listConditionByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Condition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Condition> listConditionByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Condition> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public ConditionBase(org.semanticwb.platform.SemanticObject base)
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
