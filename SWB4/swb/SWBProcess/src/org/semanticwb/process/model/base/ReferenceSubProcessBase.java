package org.semanticwb.process.model.base;


public abstract class ReferenceSubProcessBase extends org.semanticwb.process.model.SubProcess implements org.semanticwb.process.model.Modelable,org.semanticwb.process.model.MultiInstantiable,org.semanticwb.process.model.IOAble,org.semanticwb.process.model.StandardLoopable,org.semanticwb.process.model.Observable,org.semanticwb.process.model.Assignable,org.semanticwb.process.model.Performable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_SubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#SubProcess");
       public static final org.semanticwb.platform.SemanticProperty swp_subProcessRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#subProcessRef");
       public static final org.semanticwb.platform.SemanticClass swp_ReferenceSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ReferenceSubProcess");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ReferenceSubProcess");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcesses(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcesses()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess>(it, true);
       }

       public static org.semanticwb.process.model.ReferenceSubProcess createReferenceSubProcess(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.ReferenceSubProcess.ClassMgr.createReferenceSubProcess(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.ReferenceSubProcess getReferenceSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ReferenceSubProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.ReferenceSubProcess createReferenceSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ReferenceSubProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeReferenceSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasReferenceSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (getReferenceSubProcess(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByInputSet(org.semanticwb.process.model.InputSet hasinputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasInputSet, hasinputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByInputSet(org.semanticwb.process.model.InputSet hasinputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(hasinputset.getSemanticObject().getModel().listSubjects(swp_hasInputSet,hasinputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByMICondition(org.semanticwb.process.model.Expression micondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_mICondition, micondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByMICondition(org.semanticwb.process.model.Expression micondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(micondition.getSemanticObject().getModel().listSubjects(swp_mICondition,micondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasOutputSet, hasoutputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(hasoutputset.getSemanticObject().getModel().listSubjects(swp_hasOutputSet,hasoutputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByIORule(org.semanticwb.process.model.Expression hasiorule,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasIORule, hasiorule.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByIORule(org.semanticwb.process.model.Expression hasiorule)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(hasiorule.getSemanticObject().getModel().listSubjects(swp_hasIORule,hasiorule.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessBySubProcessRef(org.semanticwb.process.model.SubProcess subprocessref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_subProcessRef, subprocessref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessBySubProcessRef(org.semanticwb.process.model.SubProcess subprocessref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(subprocessref.getSemanticObject().getModel().listSubjects(swp_subProcessRef,subprocessref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByLane(org.semanticwb.process.model.Lane lane,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_lane, lane.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByLane(org.semanticwb.process.model.Lane lane)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(lane.getSemanticObject().getModel().listSubjects(swp_lane,lane.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition, complexmiflowcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(complexmiflowcondition.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition,complexmiflowcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByProperty(org.semanticwb.process.model.Property hasproperty,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasProperty, hasproperty.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByProperty(org.semanticwb.process.model.Property hasproperty)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(hasproperty.getSemanticObject().getModel().listSubjects(swp_hasProperty,hasproperty.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByLoopCondition(org.semanticwb.process.model.Expression loopcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_loopCondition, loopcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByLoopCondition(org.semanticwb.process.model.Expression loopcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(loopcondition.getSemanticObject().getModel().listSubjects(swp_loopCondition,loopcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByTransactionRef(org.semanticwb.process.model.Transaction transactionref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_transactionRef, transactionref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReferenceSubProcess> listReferenceSubProcessByTransactionRef(org.semanticwb.process.model.Transaction transactionref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReferenceSubProcess> it=new org.semanticwb.model.GenericIterator(transactionref.getSemanticObject().getModel().listSubjects(swp_transactionRef,transactionref.getSemanticObject()));
       return it;
   }
    }

    public ReferenceSubProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setSubProcessRef(org.semanticwb.process.model.SubProcess value)
    {
        getSemanticObject().setObjectProperty(swp_subProcessRef, value.getSemanticObject());
    }

    public void removeSubProcessRef()
    {
        getSemanticObject().removeProperty(swp_subProcessRef);
    }


    public org.semanticwb.process.model.SubProcess getSubProcessRef()
    {
         org.semanticwb.process.model.SubProcess ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_subProcessRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.SubProcess)obj.createGenericInstance();
         }
         return ret;
    }
}
