package org.semanticwb.process.model.base;


public abstract class AdHocSubProcessBase extends org.semanticwb.process.model.EmbeddedSubProcess implements org.semanticwb.process.model.Modelable,org.semanticwb.process.model.MultiInstantiable,org.semanticwb.process.model.IOAble,org.semanticwb.process.model.StandardLoopable,org.semanticwb.process.model.Observable,org.semanticwb.process.model.AdHocable,org.semanticwb.process.model.Assignable,org.semanticwb.process.model.Performable,org.semanticwb.process.model.Diagramable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_AdHocSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#AdHocSubProcess");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#AdHocSubProcess");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcesses(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcesses()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess>(it, true);
       }

       public static org.semanticwb.process.model.AdHocSubProcess createAdHocSubProcess(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.AdHocSubProcess.ClassMgr.createAdHocSubProcess(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.AdHocSubProcess getAdHocSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.AdHocSubProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.AdHocSubProcess createAdHocSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.AdHocSubProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeAdHocSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasAdHocSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (getAdHocSubProcess(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByInputSet(org.semanticwb.process.model.InputSet hasinputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasInputSet, hasinputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByInputSet(org.semanticwb.process.model.InputSet hasinputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(hasinputset.getSemanticObject().getModel().listSubjects(swp_hasInputSet,hasinputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByMICondition(org.semanticwb.process.model.Expression micondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_mICondition, micondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByMICondition(org.semanticwb.process.model.Expression micondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(micondition.getSemanticObject().getModel().listSubjects(swp_mICondition,micondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasOutputSet, hasoutputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(hasoutputset.getSemanticObject().getModel().listSubjects(swp_hasOutputSet,hasoutputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByIORule(org.semanticwb.process.model.Expression hasiorule,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasIORule, hasiorule.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByIORule(org.semanticwb.process.model.Expression hasiorule)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(hasiorule.getSemanticObject().getModel().listSubjects(swp_hasIORule,hasiorule.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByGraphicalElement(org.semanticwb.process.model.GraphicalElement hasgraphicalelement,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasGraphicalElement, hasgraphicalelement.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByGraphicalElement(org.semanticwb.process.model.GraphicalElement hasgraphicalelement)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(hasgraphicalelement.getSemanticObject().getModel().listSubjects(swp_hasGraphicalElement,hasgraphicalelement.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByAdHocCompletionCondition(org.semanticwb.process.model.Expression adhoccompletioncondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_adHocCompletionCondition, adhoccompletioncondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByAdHocCompletionCondition(org.semanticwb.process.model.Expression adhoccompletioncondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(adhoccompletioncondition.getSemanticObject().getModel().listSubjects(swp_adHocCompletionCondition,adhoccompletioncondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByLane(org.semanticwb.process.model.Lane lane,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_lane, lane.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByLane(org.semanticwb.process.model.Lane lane)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(lane.getSemanticObject().getModel().listSubjects(swp_lane,lane.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition, complexmiflowcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(complexmiflowcondition.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition,complexmiflowcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByProperty(org.semanticwb.process.model.Property hasproperty,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasProperty, hasproperty.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByProperty(org.semanticwb.process.model.Property hasproperty)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(hasproperty.getSemanticObject().getModel().listSubjects(swp_hasProperty,hasproperty.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByLoopCondition(org.semanticwb.process.model.Expression loopcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_loopCondition, loopcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByLoopCondition(org.semanticwb.process.model.Expression loopcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(loopcondition.getSemanticObject().getModel().listSubjects(swp_loopCondition,loopcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByTransactionRef(org.semanticwb.process.model.Transaction transactionref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_transactionRef, transactionref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocSubProcess> listAdHocSubProcessByTransactionRef(org.semanticwb.process.model.Transaction transactionref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocSubProcess> it=new org.semanticwb.model.GenericIterator(transactionref.getSemanticObject().getModel().listSubjects(swp_transactionRef,transactionref.getSemanticObject()));
       return it;
   }
    }

    public AdHocSubProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getAdHocOrdering()
    {
        return getSemanticObject().getProperty(swp_adHocOrdering);
    }

    public void setAdHocOrdering(String value)
    {
        getSemanticObject().setProperty(swp_adHocOrdering, value);
    }

    public void setAdHocCompletionCondition(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_adHocCompletionCondition, value.getSemanticObject());
    }

    public void removeAdHocCompletionCondition()
    {
        getSemanticObject().removeProperty(swp_adHocCompletionCondition);
    }


    public org.semanticwb.process.model.Expression getAdHocCompletionCondition()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_adHocCompletionCondition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }
}
