package org.semanticwb.process.base;


public class ProcessBase extends org.semanticwb.process.Activity implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessClass");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasProcessClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessClass");
       public static final org.semanticwb.platform.SemanticClass swbps_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Activity");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasDependence=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasDependence");
       public static final org.semanticwb.platform.SemanticProperty swbps_executions=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#executions");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swbps_hasActivity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasActivity");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbps_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");

       public static java.util.Iterator<org.semanticwb.process.Process> listProcesses(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Process>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.Process> listProcesses()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Process>(it, true);
       }

       public static org.semanticwb.process.Process createProcess(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.Process.ClassMgr.createProcess(String.valueOf(id), model);
       }

       public static org.semanticwb.process.Process getProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.Process)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.Process createProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.Process)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeProcess(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasProcess(String id, org.semanticwb.model.SWBModel model)
       {
           return (getProcess(id, model)!=null);
       }
    }

    public ProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listProcessClasses()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(ClassMgr.swbps_hasProcessClass.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addProcessClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbps_hasProcessClass, value);
    }

    public void removeAllProcessClass()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_hasProcessClass);
    }

    public void removeProcessClass(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbps_hasProcessClass,semanticobject);
    }

    public org.semanticwb.platform.SemanticObject getProcessClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasProcessClass);
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity> listActivities()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.Activity>(getSemanticObject().listObjectProperties(ClassMgr.swbps_hasActivity));
    }

    public boolean hasActivity(org.semanticwb.process.Activity activity)
    {
        if(activity==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbps_hasActivity,activity.getSemanticObject());
    }

    public void addActivity(org.semanticwb.process.Activity value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbps_hasActivity, value.getSemanticObject());
    }

    public void removeAllActivity()
    {
        getSemanticObject().removeProperty(ClassMgr.swbps_hasActivity);
    }

    public void removeActivity(org.semanticwb.process.Activity activity)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbps_hasActivity,activity.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByActivity(org.semanticwb.process.Activity hasactivity,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasActivity, hasactivity.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.Process> listProcessByActivity(org.semanticwb.process.Activity hasactivity)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.Process> it=new org.semanticwb.model.GenericIterator(hasactivity.getSemanticObject().getModel().listSubjects(ClassMgr.swbps_hasActivity,hasactivity.getSemanticObject()));
       return it;
   }

    public org.semanticwb.process.Activity getActivity()
    {
         org.semanticwb.process.Activity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbps_hasActivity);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Activity)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
