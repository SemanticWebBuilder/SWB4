package org.semanticwb.process.model.base;


public abstract class NormalTaskBase extends org.semanticwb.process.model.Task implements org.semanticwb.process.model.StandardLoopable,org.semanticwb.process.model.MultiInstantiable,org.semanticwb.process.model.Modelable,org.semanticwb.process.model.Assignable,org.semanticwb.process.model.Observable,org.semanticwb.process.model.Performable,org.semanticwb.process.model.IOAble,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_NormalTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#NormalTask");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#NormalTask");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTasks(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTasks()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask>(it, true);
       }

       public static org.semanticwb.process.model.NormalTask createNormalTask(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.NormalTask.ClassMgr.createNormalTask(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.NormalTask getNormalTask(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.NormalTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.NormalTask createNormalTask(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.NormalTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeNormalTask(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasNormalTask(String id, org.semanticwb.model.SWBModel model)
       {
           return (getNormalTask(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByInputSet(org.semanticwb.process.model.InputSet hasinputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasInputSet, hasinputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByInputSet(org.semanticwb.process.model.InputSet hasinputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(hasinputset.getSemanticObject().getModel().listSubjects(swp_hasInputSet,hasinputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByMICondition(org.semanticwb.process.model.Expression micondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_mICondition, micondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByMICondition(org.semanticwb.process.model.Expression micondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(micondition.getSemanticObject().getModel().listSubjects(swp_mICondition,micondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByIORule(org.semanticwb.process.model.Expression hasiorule,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasIORule, hasiorule.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByIORule(org.semanticwb.process.model.Expression hasiorule)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(hasiorule.getSemanticObject().getModel().listSubjects(swp_hasIORule,hasiorule.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasOutputSet, hasoutputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(hasoutputset.getSemanticObject().getModel().listSubjects(swp_hasOutputSet,hasoutputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByLane(org.semanticwb.process.model.Lane lane,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_lane, lane.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByLane(org.semanticwb.process.model.Lane lane)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(lane.getSemanticObject().getModel().listSubjects(swp_lane,lane.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition, complexmiflowcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(complexmiflowcondition.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition,complexmiflowcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByProperty(org.semanticwb.process.model.Property hasproperty,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasProperty, hasproperty.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByProperty(org.semanticwb.process.model.Property hasproperty)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(hasproperty.getSemanticObject().getModel().listSubjects(swp_hasProperty,hasproperty.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByLoopCondition(org.semanticwb.process.model.Expression loopcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_loopCondition, loopcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.NormalTask> listNormalTaskByLoopCondition(org.semanticwb.process.model.Expression loopcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.NormalTask> it=new org.semanticwb.model.GenericIterator(loopcondition.getSemanticObject().getModel().listSubjects(swp_loopCondition,loopcondition.getSemanticObject()));
       return it;
   }
    }

    public NormalTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
