package org.semanticwb.process.model.base;


public abstract class AdHocProcessBase extends org.semanticwb.process.model.Process implements org.semanticwb.process.model.ProcessTraceable,org.semanticwb.process.model.Modelable,org.semanticwb.process.model.IOAble,org.semanticwb.process.model.AdHocable,org.semanticwb.model.Traceable,org.semanticwb.process.model.Assignable,org.semanticwb.process.model.Performable,org.semanticwb.process.model.Diagramable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_AdHocProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#AdHocProcess");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#AdHocProcess");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcesses(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcesses()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess>(it, true);
       }

       public static org.semanticwb.process.model.AdHocProcess getAdHocProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.AdHocProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.AdHocProcess createAdHocProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.AdHocProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeAdHocProcess(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasAdHocProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (getAdHocProcess(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByInputSet(org.semanticwb.process.model.InputSet hasinputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasInputSet, hasinputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByInputSet(org.semanticwb.process.model.InputSet hasinputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(hasinputset.getSemanticObject().getModel().listSubjects(swp_hasInputSet,hasinputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasOutputSet, hasoutputset.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByOutputSet(org.semanticwb.process.model.OutputSet hasoutputset)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(hasoutputset.getSemanticObject().getModel().listSubjects(swp_hasOutputSet,hasoutputset.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByGraphicalElement(org.semanticwb.process.model.GraphicalElement hasgraphicalelement,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasGraphicalElement, hasgraphicalelement.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByGraphicalElement(org.semanticwb.process.model.GraphicalElement hasgraphicalelement)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(hasgraphicalelement.getSemanticObject().getModel().listSubjects(swp_hasGraphicalElement,hasgraphicalelement.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByAdHocCompletionCondition(org.semanticwb.process.model.Expression adhoccompletioncondition,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_adHocCompletionCondition, adhoccompletioncondition.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByAdHocCompletionCondition(org.semanticwb.process.model.Expression adhoccompletioncondition)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(adhoccompletioncondition.getSemanticObject().getModel().listSubjects(swp_adHocCompletionCondition,adhoccompletioncondition.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByEndedBy(org.semanticwb.model.User endedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_endedBy, endedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByEndedBy(org.semanticwb.model.User endedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(endedby.getSemanticObject().getModel().listSubjects(swp_endedBy,endedby.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByProperty(org.semanticwb.process.model.Property hasproperty,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasProperty, hasproperty.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByProperty(org.semanticwb.process.model.Property hasproperty)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(hasproperty.getSemanticObject().getModel().listSubjects(swp_hasProperty,hasproperty.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.AdHocProcess> listAdHocProcessByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AdHocProcess> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public AdHocProcessBase(org.semanticwb.platform.SemanticObject base)
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
