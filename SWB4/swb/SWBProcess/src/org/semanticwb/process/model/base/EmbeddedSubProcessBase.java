package org.semanticwb.process.model.base;


public abstract class EmbeddedSubProcessBase extends org.semanticwb.process.model.SubProcess implements org.semanticwb.process.model.Modelable,org.semanticwb.process.model.MultiInstantiable,org.semanticwb.process.model.IOAble,org.semanticwb.process.model.StandardLoopable,org.semanticwb.process.model.Observable,org.semanticwb.process.model.Assignable,org.semanticwb.process.model.Performable,org.semanticwb.process.model.Diagramable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_EmbeddedSubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EmbeddedSubProcess");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EmbeddedSubProcess");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcesses(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcesses()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess>(it, true);
       }

       public static org.semanticwb.process.model.EmbeddedSubProcess createEmbeddedSubProcess(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.EmbeddedSubProcess.ClassMgr.createEmbeddedSubProcess(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.EmbeddedSubProcess getEmbeddedSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.EmbeddedSubProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.EmbeddedSubProcess createEmbeddedSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.EmbeddedSubProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeEmbeddedSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasEmbeddedSubProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (getEmbeddedSubProcess(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByInputSet(org.semanticwb.process.model.InputSet hasinputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasInputSet, hasinputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByInputSet(org.semanticwb.process.model.InputSet hasinputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(hasinputset.getSemanticObject().getModel().listSubjects(swp_hasInputSet,hasinputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByMICondition(org.semanticwb.process.model.Expression micondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_mICondition, micondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByMICondition(org.semanticwb.process.model.Expression micondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(micondition.getSemanticObject().getModel().listSubjects(swp_mICondition,micondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasOutputSet, hasoutputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(hasoutputset.getSemanticObject().getModel().listSubjects(swp_hasOutputSet,hasoutputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByIORule(org.semanticwb.process.model.Expression hasiorule,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasIORule, hasiorule.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByIORule(org.semanticwb.process.model.Expression hasiorule)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(hasiorule.getSemanticObject().getModel().listSubjects(swp_hasIORule,hasiorule.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByGraphicalElement(org.semanticwb.process.model.GraphicalElement hasgraphicalelement,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasGraphicalElement, hasgraphicalelement.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByGraphicalElement(org.semanticwb.process.model.GraphicalElement hasgraphicalelement)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(hasgraphicalelement.getSemanticObject().getModel().listSubjects(swp_hasGraphicalElement,hasgraphicalelement.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByLane(org.semanticwb.process.model.Lane lane,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_lane, lane.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByLane(org.semanticwb.process.model.Lane lane)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(lane.getSemanticObject().getModel().listSubjects(swp_lane,lane.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition, complexmiflowcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(complexmiflowcondition.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition,complexmiflowcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByProperty(org.semanticwb.process.model.Property hasproperty,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasProperty, hasproperty.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByProperty(org.semanticwb.process.model.Property hasproperty)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(hasproperty.getSemanticObject().getModel().listSubjects(swp_hasProperty,hasproperty.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByLoopCondition(org.semanticwb.process.model.Expression loopcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_loopCondition, loopcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByLoopCondition(org.semanticwb.process.model.Expression loopcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(loopcondition.getSemanticObject().getModel().listSubjects(swp_loopCondition,loopcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByTransactionRef(org.semanticwb.process.model.Transaction transactionref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_transactionRef, transactionref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcess> listEmbeddedSubProcessByTransactionRef(org.semanticwb.process.model.Transaction transactionref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcess> it=new org.semanticwb.model.GenericIterator(transactionref.getSemanticObject().getModel().listSubjects(swp_transactionRef,transactionref.getSemanticObject()));
       return it;
   }
    }

    public EmbeddedSubProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElements()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(getSemanticObject().listObjectProperties(swp_hasGraphicalElement));
    }

    public boolean hasGraphicalElement(org.semanticwb.process.model.GraphicalElement graphicalelement)
    {
        if(graphicalelement==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasGraphicalElement,graphicalelement.getSemanticObject());
    }

    public void addGraphicalElement(org.semanticwb.process.model.GraphicalElement value)
    {
        getSemanticObject().addObjectProperty(swp_hasGraphicalElement, value.getSemanticObject());
    }

    public void removeAllGraphicalElement()
    {
        getSemanticObject().removeProperty(swp_hasGraphicalElement);
    }

    public void removeGraphicalElement(org.semanticwb.process.model.GraphicalElement graphicalelement)
    {
        getSemanticObject().removeObjectProperty(swp_hasGraphicalElement,graphicalelement.getSemanticObject());
    }


    public org.semanticwb.process.model.GraphicalElement getGraphicalElement()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasGraphicalElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
         }
         return ret;
    }
}
