package org.semanticwb.process.model.base;


public abstract class SequenceFlowBase extends org.semanticwb.process.model.ConnectingObject implements org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Conditionable
{
       public static final org.semanticwb.platform.SemanticProperty swp_conditionType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#conditionType");
       public static final org.semanticwb.platform.SemanticClass swp_SequenceFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SequenceFlow");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SequenceFlow");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlows(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlows()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow>(it, true);
       }

       public static org.semanticwb.process.model.SequenceFlow getSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.SequenceFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.SequenceFlow createSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.SequenceFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasSequenceFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (getSequenceFlow(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowBySourceRef(org.semanticwb.process.model.GraphicalElement sourceref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_sourceRef, sourceref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowBySourceRef(org.semanticwb.process.model.GraphicalElement sourceref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(sourceref.getSemanticObject().getModel().listSubjects(swp_sourceRef,sourceref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByConditionExpression(org.semanticwb.process.model.Expression conditionexpression,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_conditionExpression, conditionexpression.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByConditionExpression(org.semanticwb.process.model.Expression conditionexpression)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(conditionexpression.getSemanticObject().getModel().listSubjects(swp_conditionExpression,conditionexpression.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByTargetRef(org.semanticwb.process.model.GraphicalElement targetref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_targetRef, targetref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.SequenceFlow> listSequenceFlowByTargetRef(org.semanticwb.process.model.GraphicalElement targetref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SequenceFlow> it=new org.semanticwb.model.GenericIterator(targetref.getSemanticObject().getModel().listSubjects(swp_targetRef,targetref.getSemanticObject()));
       return it;
   }
    }

    public SequenceFlowBase(org.semanticwb.platform.SemanticObject base)
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

    public String getConditionType()
    {
        return getSemanticObject().getProperty(swp_conditionType);
    }

    public void setConditionType(String value)
    {
        getSemanticObject().setProperty(swp_conditionType, value);
    }
}
