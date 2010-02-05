package org.semanticwb.process.model.base;


public abstract class ReusableSubProcessBase extends org.semanticwb.process.model.SubProcess implements org.semanticwb.process.model.Modelable,org.semanticwb.process.model.MultiInstantiable,org.semanticwb.process.model.IOAble,org.semanticwb.process.model.StandardLoopable,org.semanticwb.process.model.Observable,org.semanticwb.process.model.Assignable,org.semanticwb.process.model.Performable,org.semanticwb.process.model.ProcessReferensable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
       public static final org.semanticwb.platform.SemanticProperty swp_hasInputMap=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasInputMap");
       public static final org.semanticwb.platform.SemanticProperty swp_hasOutputMap=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasOutputMap");
       public static final org.semanticwb.platform.SemanticClass swp_BusinessProcessDiagram=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BusinessProcessDiagram");
       public static final org.semanticwb.platform.SemanticProperty swp_diagramRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#diagramRef");
       public static final org.semanticwb.platform.SemanticClass swp_ReusableSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ReusableSubProcess");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ReusableSubProcess");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcesses(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcesses()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess>(it, true);
       }

       public static org.semanticwb.process.model.ReusableSubProcess createReusableSubProcess(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.ReusableSubProcess.ClassMgr.createReusableSubProcess(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.ReusableSubProcess getReusableSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ReusableSubProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.ReusableSubProcess createReusableSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ReusableSubProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeReusableSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasReusableSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (getReusableSubProcess(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByInputSet(org.semanticwb.process.model.InputSet hasinputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasInputSet, hasinputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByInputSet(org.semanticwb.process.model.InputSet hasinputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(hasinputset.getSemanticObject().getModel().listSubjects(swp_hasInputSet,hasinputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByMICondition(org.semanticwb.process.model.Expression micondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_mICondition, micondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByMICondition(org.semanticwb.process.model.Expression micondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(micondition.getSemanticObject().getModel().listSubjects(swp_mICondition,micondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasOutputSet, hasoutputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(hasoutputset.getSemanticObject().getModel().listSubjects(swp_hasOutputSet,hasoutputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByIORule(org.semanticwb.process.model.Expression hasiorule,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasIORule, hasiorule.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByIORule(org.semanticwb.process.model.Expression hasiorule)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(hasiorule.getSemanticObject().getModel().listSubjects(swp_hasIORule,hasiorule.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByInputMap(org.semanticwb.process.model.Expression hasinputmap,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasInputMap, hasinputmap.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByInputMap(org.semanticwb.process.model.Expression hasinputmap)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(hasinputmap.getSemanticObject().getModel().listSubjects(swp_hasInputMap,hasinputmap.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByOutputMap(org.semanticwb.process.model.Expression hasoutputmap,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasOutputMap, hasoutputmap.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByOutputMap(org.semanticwb.process.model.Expression hasoutputmap)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(hasoutputmap.getSemanticObject().getModel().listSubjects(swp_hasOutputMap,hasoutputmap.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByDiagramRef(org.semanticwb.process.model.BusinessProcessDiagram diagramref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_diagramRef, diagramref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByDiagramRef(org.semanticwb.process.model.BusinessProcessDiagram diagramref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(diagramref.getSemanticObject().getModel().listSubjects(swp_diagramRef,diagramref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByLane(org.semanticwb.process.model.Lane lane,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_lane, lane.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByLane(org.semanticwb.process.model.Lane lane)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(lane.getSemanticObject().getModel().listSubjects(swp_lane,lane.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition, complexmiflowcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(complexmiflowcondition.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition,complexmiflowcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByProperty(org.semanticwb.process.model.Property hasproperty,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasProperty, hasproperty.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByProperty(org.semanticwb.process.model.Property hasproperty)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(hasproperty.getSemanticObject().getModel().listSubjects(swp_hasProperty,hasproperty.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByProcessRef(org.semanticwb.process.model.Process processref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_processRef, processref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByProcessRef(org.semanticwb.process.model.Process processref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(processref.getSemanticObject().getModel().listSubjects(swp_processRef,processref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByLoopCondition(org.semanticwb.process.model.Expression loopcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_loopCondition, loopcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByLoopCondition(org.semanticwb.process.model.Expression loopcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(loopcondition.getSemanticObject().getModel().listSubjects(swp_loopCondition,loopcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByTransactionRef(org.semanticwb.process.model.Transaction transactionref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_transactionRef, transactionref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ReusableSubProcess> listReusableSubProcessByTransactionRef(org.semanticwb.process.model.Transaction transactionref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ReusableSubProcess> it=new org.semanticwb.model.GenericIterator(transactionref.getSemanticObject().getModel().listSubjects(swp_transactionRef,transactionref.getSemanticObject()));
       return it;
   }
    }

    public ReusableSubProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Expression> listInputMaps()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Expression>(getSemanticObject().listObjectProperties(swp_hasInputMap));
    }

    public boolean hasInputMap(org.semanticwb.process.model.Expression expression)
    {
        if(expression==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasInputMap,expression.getSemanticObject());
    }

    public void addInputMap(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().addObjectProperty(swp_hasInputMap, value.getSemanticObject());
    }

    public void removeAllInputMap()
    {
        getSemanticObject().removeProperty(swp_hasInputMap);
    }

    public void removeInputMap(org.semanticwb.process.model.Expression expression)
    {
        getSemanticObject().removeObjectProperty(swp_hasInputMap,expression.getSemanticObject());
    }


    public org.semanticwb.process.model.Expression getInputMap()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasInputMap);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Expression> listOutputMaps()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Expression>(getSemanticObject().listObjectProperties(swp_hasOutputMap));
    }

    public boolean hasOutputMap(org.semanticwb.process.model.Expression expression)
    {
        if(expression==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasOutputMap,expression.getSemanticObject());
    }

    public void addOutputMap(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().addObjectProperty(swp_hasOutputMap, value.getSemanticObject());
    }

    public void removeAllOutputMap()
    {
        getSemanticObject().removeProperty(swp_hasOutputMap);
    }

    public void removeOutputMap(org.semanticwb.process.model.Expression expression)
    {
        getSemanticObject().removeObjectProperty(swp_hasOutputMap,expression.getSemanticObject());
    }


    public org.semanticwb.process.model.Expression getOutputMap()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasOutputMap);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public void setDiagramRef(org.semanticwb.process.model.BusinessProcessDiagram value)
    {
        getSemanticObject().setObjectProperty(swp_diagramRef, value.getSemanticObject());
    }

    public void removeDiagramRef()
    {
        getSemanticObject().removeProperty(swp_diagramRef);
    }


    public org.semanticwb.process.model.BusinessProcessDiagram getDiagramRef()
    {
         org.semanticwb.process.model.BusinessProcessDiagram ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_diagramRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BusinessProcessDiagram)obj.createGenericInstance();
         }
         return ret;
    }

    public void setProcessRef(org.semanticwb.process.model.Process value)
    {
        getSemanticObject().setObjectProperty(swp_processRef, value.getSemanticObject());
    }

    public void removeProcessRef()
    {
        getSemanticObject().removeProperty(swp_processRef);
    }


    public org.semanticwb.process.model.Process getProcessRef()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
         }
         return ret;
    }
}
