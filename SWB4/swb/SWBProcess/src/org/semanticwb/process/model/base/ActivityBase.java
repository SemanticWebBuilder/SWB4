package org.semanticwb.process.model.base;


public abstract class ActivityBase extends org.semanticwb.process.model.FlowObject implements org.semanticwb.process.model.StandardLoopable,org.semanticwb.process.model.MultiInstantiable,org.semanticwb.process.model.Modelable,org.semanticwb.process.model.Observable,org.semanticwb.process.model.Assignable,org.semanticwb.process.model.Performable,org.semanticwb.process.model.IOAble,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
       public static final org.semanticwb.platform.SemanticProperty swp_hasIORule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasIORule");
       public static final org.semanticwb.platform.SemanticProperty swp_completionQuantity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#completionQuantity");
       public static final org.semanticwb.platform.SemanticProperty swp_startQuantity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#startQuantity");
       public static final org.semanticwb.platform.SemanticClass swp_Lane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Lane");
       public static final org.semanticwb.platform.SemanticProperty swp_lane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#lane");
       public static final org.semanticwb.platform.SemanticClass swp_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Activity");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Activity");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivities(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivities()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity>(it, true);
       }

       public static org.semanticwb.process.model.Activity createActivity(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Activity.ClassMgr.createActivity(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Activity getActivity(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Activity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Activity createActivity(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Activity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeActivity(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasActivity(String id, org.semanticwb.model.SWBModel model)
       {
           return (getActivity(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByInputSet(org.semanticwb.process.model.InputSet hasinputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasInputSet, hasinputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByInputSet(org.semanticwb.process.model.InputSet hasinputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(hasinputset.getSemanticObject().getModel().listSubjects(swp_hasInputSet,hasinputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByMICondition(org.semanticwb.process.model.Expression micondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_mICondition, micondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByMICondition(org.semanticwb.process.model.Expression micondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(micondition.getSemanticObject().getModel().listSubjects(swp_mICondition,micondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByIORule(org.semanticwb.process.model.Expression hasiorule,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasIORule, hasiorule.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByIORule(org.semanticwb.process.model.Expression hasiorule)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(hasiorule.getSemanticObject().getModel().listSubjects(swp_hasIORule,hasiorule.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasOutputSet, hasoutputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(hasoutputset.getSemanticObject().getModel().listSubjects(swp_hasOutputSet,hasoutputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByLane(org.semanticwb.process.model.Lane lane,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_lane, lane.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByLane(org.semanticwb.process.model.Lane lane)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(lane.getSemanticObject().getModel().listSubjects(swp_lane,lane.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition, complexmiflowcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByComplexMIFlowCondition(org.semanticwb.process.model.Expression complexmiflowcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(complexmiflowcondition.getSemanticObject().getModel().listSubjects(swp_complexMIFlowCondition,complexmiflowcondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByProperty(org.semanticwb.process.model.Property hasproperty,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasProperty, hasproperty.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByProperty(org.semanticwb.process.model.Property hasproperty)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(hasproperty.getSemanticObject().getModel().listSubjects(swp_hasProperty,hasproperty.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByLoopCondition(org.semanticwb.process.model.Expression loopcondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_loopCondition, loopcondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Activity> listActivityByLoopCondition(org.semanticwb.process.model.Expression loopcondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Activity> it=new org.semanticwb.model.GenericIterator(loopcondition.getSemanticObject().getModel().listSubjects(swp_loopCondition,loopcondition.getSemanticObject()));
       return it;
   }
    }

    public ActivityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> listInputSets()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet>(getSemanticObject().listObjectProperties(swp_hasInputSet));
    }

    public boolean hasInputSet(org.semanticwb.process.model.InputSet inputset)
    {
        if(inputset==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasInputSet,inputset.getSemanticObject());
    }

    public void addInputSet(org.semanticwb.process.model.InputSet value)
    {
        getSemanticObject().addObjectProperty(swp_hasInputSet, value.getSemanticObject());
    }

    public void removeAllInputSet()
    {
        getSemanticObject().removeProperty(swp_hasInputSet);
    }

    public void removeInputSet(org.semanticwb.process.model.InputSet inputset)
    {
        getSemanticObject().removeObjectProperty(swp_hasInputSet,inputset.getSemanticObject());
    }


    public org.semanticwb.process.model.InputSet getInputSet()
    {
         org.semanticwb.process.model.InputSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasInputSet);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.InputSet)obj.createGenericInstance();
         }
         return ret;
    }

    public void setMICondition(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_mICondition, value.getSemanticObject());
    }

    public void removeMICondition()
    {
        getSemanticObject().removeProperty(swp_mICondition);
    }


    public org.semanticwb.process.model.Expression getMICondition()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_mICondition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Expression> listIORules()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Expression>(getSemanticObject().listObjectProperties(swp_hasIORule));
    }

    public boolean hasIORule(org.semanticwb.process.model.Expression expression)
    {
        if(expression==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasIORule,expression.getSemanticObject());
    }

    public void addIORule(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().addObjectProperty(swp_hasIORule, value.getSemanticObject());
    }

    public void removeAllIORule()
    {
        getSemanticObject().removeProperty(swp_hasIORule);
    }

    public void removeIORule(org.semanticwb.process.model.Expression expression)
    {
        getSemanticObject().removeObjectProperty(swp_hasIORule,expression.getSemanticObject());
    }


    public org.semanticwb.process.model.Expression getIORule()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasIORule);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> listOutputSets()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet>(getSemanticObject().listObjectProperties(swp_hasOutputSet));
    }

    public boolean hasOutputSet(org.semanticwb.process.model.OutputSet outputset)
    {
        if(outputset==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasOutputSet,outputset.getSemanticObject());
    }

    public void addOutputSet(org.semanticwb.process.model.OutputSet value)
    {
        getSemanticObject().addObjectProperty(swp_hasOutputSet, value.getSemanticObject());
    }

    public void removeAllOutputSet()
    {
        getSemanticObject().removeProperty(swp_hasOutputSet);
    }

    public void removeOutputSet(org.semanticwb.process.model.OutputSet outputset)
    {
        getSemanticObject().removeObjectProperty(swp_hasOutputSet,outputset.getSemanticObject());
    }


    public org.semanticwb.process.model.OutputSet getOutputSet()
    {
         org.semanticwb.process.model.OutputSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasOutputSet);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.OutputSet)obj.createGenericInstance();
         }
         return ret;
    }

    public String getMIOrdering()
    {
        return getSemanticObject().getProperty(swp_mIOrdering);
    }

    public void setMIOrdering(String value)
    {
        getSemanticObject().setProperty(swp_mIOrdering, value);
    }

    public int getCompletionQuantity()
    {
        return getSemanticObject().getIntProperty(swp_completionQuantity);
    }

    public void setCompletionQuantity(int value)
    {
        getSemanticObject().setIntProperty(swp_completionQuantity, value);
    }

    public String getMIFlowCondition()
    {
        return getSemanticObject().getProperty(swp_mIFlowCondition);
    }

    public void setMIFlowCondition(String value)
    {
        getSemanticObject().setProperty(swp_mIFlowCondition, value);
    }

    public String getStatus()
    {
        return getSemanticObject().getProperty(swp_status);
    }

    public void setStatus(String value)
    {
        getSemanticObject().setProperty(swp_status, value);
    }

    public String getTestTime()
    {
        return getSemanticObject().getProperty(swp_testTime);
    }

    public void setTestTime(String value)
    {
        getSemanticObject().setProperty(swp_testTime, value);
    }

    public int getLoopCounter()
    {
        return getSemanticObject().getIntProperty(swp_loopCounter);
    }

    public void setLoopCounter(int value)
    {
        getSemanticObject().setIntProperty(swp_loopCounter, value);
    }

    public int getStartQuantity()
    {
        return getSemanticObject().getIntProperty(swp_startQuantity);
    }

    public void setStartQuantity(int value)
    {
        getSemanticObject().setIntProperty(swp_startQuantity, value);
    }

    public void setLane(org.semanticwb.process.model.Lane value)
    {
        getSemanticObject().setObjectProperty(swp_lane, value.getSemanticObject());
    }

    public void removeLane()
    {
        getSemanticObject().removeProperty(swp_lane);
    }


    public org.semanticwb.process.model.Lane getLane()
    {
         org.semanticwb.process.model.Lane ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_lane);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Lane)obj.createGenericInstance();
         }
         return ret;
    }

    public void setComplexMIFlowCondition(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_complexMIFlowCondition, value.getSemanticObject());
    }

    public void removeComplexMIFlowCondition()
    {
        getSemanticObject().removeProperty(swp_complexMIFlowCondition);
    }


    public org.semanticwb.process.model.Expression getComplexMIFlowCondition()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_complexMIFlowCondition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public int getLoopMaximum()
    {
        return getSemanticObject().getIntProperty(swp_loopMaximum);
    }

    public void setLoopMaximum(int value)
    {
        getSemanticObject().setIntProperty(swp_loopMaximum, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property> listProperties()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property>(getSemanticObject().listObjectProperties(swp_hasProperty));
    }

    public boolean hasProperty(org.semanticwb.process.model.Property property)
    {
        if(property==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasProperty,property.getSemanticObject());
    }

    public void addProperty(org.semanticwb.process.model.Property value)
    {
        getSemanticObject().addObjectProperty(swp_hasProperty, value.getSemanticObject());
    }

    public void removeAllProperty()
    {
        getSemanticObject().removeProperty(swp_hasProperty);
    }

    public void removeProperty(org.semanticwb.process.model.Property property)
    {
        getSemanticObject().removeObjectProperty(swp_hasProperty,property.getSemanticObject());
    }


    public org.semanticwb.process.model.Property getProperty()
    {
         org.semanticwb.process.model.Property ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProperty);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Property)obj.createGenericInstance();
         }
         return ret;
    }

    public String getLoopType()
    {
        return getSemanticObject().getProperty(swp_loopType);
    }

    public void setLoopType(String value)
    {
        getSemanticObject().setProperty(swp_loopType, value);
    }

    public void setLoopCondition(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_loopCondition, value.getSemanticObject());
    }

    public void removeLoopCondition()
    {
        getSemanticObject().removeProperty(swp_loopCondition);
    }


    public org.semanticwb.process.model.Expression getLoopCondition()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_loopCondition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Iterator<String> listPerformers()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(swp_hasPerformer);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addPerformer(String performer)
    {
        getSemanticObject().setProperty(swp_hasPerformer, performer);
    }

    public void removeAllPerformer()
    {
        getSemanticObject().removeProperty(swp_hasPerformer);
    }

    public void removePerformer(String performer)
    {
        getSemanticObject().removeProperty(swp_hasPerformer,performer);
    }
}
