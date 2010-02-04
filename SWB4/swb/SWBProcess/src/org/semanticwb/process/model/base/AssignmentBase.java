package org.semanticwb.process.model.base;


public abstract class AssignmentBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticProperty swp_assignTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#assignTime");
       public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
       public static final org.semanticwb.platform.SemanticProperty swp_from=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#from");
       public static final org.semanticwb.platform.SemanticClass swp_Property=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Property");
       public static final org.semanticwb.platform.SemanticProperty swp_to=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#to");
       public static final org.semanticwb.platform.SemanticClass swp_Assignment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Assignment");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Assignment");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Assignment> listAssignments(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Assignment> listAssignments()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment>(it, true);
       }

       public static org.semanticwb.process.model.Assignment createAssignment(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Assignment.ClassMgr.createAssignment(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Assignment getAssignment(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Assignment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Assignment createAssignment(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Assignment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeAssignment(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasAssignment(String id, org.semanticwb.model.SWBModel model)
       {
           return (getAssignment(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Assignment> listAssignmentByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Assignment> listAssignmentByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Assignment> listAssignmentByFrom(org.semanticwb.process.model.Expression from,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_from, from.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Assignment> listAssignmentByFrom(org.semanticwb.process.model.Expression from)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment> it=new org.semanticwb.model.GenericIterator(from.getSemanticObject().getModel().listSubjects(swp_from,from.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Assignment> listAssignmentByTo(org.semanticwb.process.model.Property to,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_to, to.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Assignment> listAssignmentByTo(org.semanticwb.process.model.Property to)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Assignment> it=new org.semanticwb.model.GenericIterator(to.getSemanticObject().getModel().listSubjects(swp_to,to.getSemanticObject()));
       return it;
   }
    }

    public AssignmentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getAssignTime()
    {
        return getSemanticObject().getProperty(swp_assignTime);
    }

    public void setAssignTime(String value)
    {
        getSemanticObject().setProperty(swp_assignTime, value);
    }

    public void setFrom(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_from, value.getSemanticObject());
    }

    public void removeFrom()
    {
        getSemanticObject().removeProperty(swp_from);
    }


    public org.semanticwb.process.model.Expression getFrom()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_from);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public void setTo(org.semanticwb.process.model.Property value)
    {
        getSemanticObject().setObjectProperty(swp_to, value.getSemanticObject());
    }

    public void removeTo()
    {
        getSemanticObject().removeProperty(swp_to);
    }


    public org.semanticwb.process.model.Property getTo()
    {
         org.semanticwb.process.model.Property ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_to);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Property)obj.createGenericInstance();
         }
         return ret;
    }
}
