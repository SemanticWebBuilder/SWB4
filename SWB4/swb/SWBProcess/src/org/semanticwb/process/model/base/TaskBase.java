package org.semanticwb.process.model.base;


public abstract class TaskBase extends org.semanticwb.process.model.Activity implements org.semanticwb.process.model.StandardLoopable,org.semanticwb.process.model.MultiInstantiable,org.semanticwb.process.model.Modelable,org.semanticwb.process.model.Observable,org.semanticwb.process.model.Assignable,org.semanticwb.process.model.Performable,org.semanticwb.process.model.IOAble,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Task=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Task");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Task");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Task> listTasks(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Task> listTasks()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task>(it, true);
       }

       public static org.semanticwb.process.model.Task createTask(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Task.ClassMgr.createTask(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Task getTask(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Task)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Task createTask(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Task)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeTask(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasTask(String id, org.semanticwb.model.SWBModel model)
       {
           return (getTask(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByInputSet(org.semanticwb.process.model.InputSet hasinputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasInputSet, hasinputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByInputSet(org.semanticwb.process.model.InputSet hasinputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(hasinputset.getSemanticObject().getModel().listSubjects(swp_hasInputSet,hasinputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByMICondition(org.semanticwb.process.model.Expression micondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_mICondition, micondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByMICondition(org.semanticwb.process.model.Expression micondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(micondition.getSemanticObject().getModel().listSubjects(swp_mICondition,micondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByIORule(org.semanticwb.process.model.Expression hasiorule,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasIORule, hasiorule.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByIORule(org.semanticwb.process.model.Expression hasiorule)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(hasiorule.getSemanticObject().getModel().listSubjects(swp_hasIORule,hasiorule.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasOutputSet, hasoutputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(hasoutputset.getSemanticObject().getModel().listSubjects(swp_hasOutputSet,hasoutputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByLane(org.semanticwb.process.model.Lane lane,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_lane, lane.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByLane(org.semanticwb.process.model.Lane lane)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(lane.getSemanticObject().getModel().listSubjects(swp_lane,lane.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition, complexmiflowcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(complexmiflowcondition.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition,complexmiflowcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByProperty(org.semanticwb.process.model.Property hasproperty,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasProperty, hasproperty.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByProperty(org.semanticwb.process.model.Property hasproperty)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(hasproperty.getSemanticObject().getModel().listSubjects(swp_hasProperty,hasproperty.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByLoopCondition(org.semanticwb.process.model.Expression loopcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_loopCondition, loopcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Task> listTaskByLoopCondition(org.semanticwb.process.model.Expression loopcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Task> it=new org.semanticwb.model.GenericIterator(loopcondition.getSemanticObject().getModel().listSubjects(swp_loopCondition,loopcondition.getSemanticObject()));
       return it;
   }
    }

    public TaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
