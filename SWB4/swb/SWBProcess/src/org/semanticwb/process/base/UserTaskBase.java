package org.semanticwb.process.base;


public class UserTaskBase extends org.semanticwb.process.Task implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.UserGroupable,org.semanticwb.model.Roleable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swbxf_FormView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormView");
    public static final org.semanticwb.platform.SemanticProperty swbps_processFormView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processFormView");
    public static final org.semanticwb.platform.SemanticClass swbps_UserTask=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#UserTask");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#UserTask");

    public UserTaskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTasks(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTasks()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask>(it, true);
    }

    public static org.semanticwb.process.UserTask createUserTask(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.process.UserTask.createUserTask(String.valueOf(id), model);
    }

    public static org.semanticwb.process.UserTask getUserTask(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.process.UserTask)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.process.UserTask createUserTask(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.process.UserTask)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeUserTask(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasUserTask(String id, org.semanticwb.model.SWBModel model)
    {
        return (getUserTask(id, model)!=null);
    }

    public void setFormView(org.semanticwb.model.FormView value)
    {
        getSemanticObject().setObjectProperty(swbps_processFormView, value.getSemanticObject());
    }

    public void removeFormView()
    {
        getSemanticObject().removeProperty(swbps_processFormView);
    }

   public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTaskByFormView(org.semanticwb.model.FormView processformview,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_processFormView, processformview.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.UserTask> listUserTaskByFormView(org.semanticwb.model.FormView processformview)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.UserTask> it=new org.semanticwb.model.GenericIterator(processformview.getSemanticObject().getModel().listSubjects(swbps_processFormView,processformview.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.FormView getFormView()
    {
         org.semanticwb.model.FormView ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_processFormView);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.FormView)obj.createGenericInstance();
         }
         return ret;
    }
}
