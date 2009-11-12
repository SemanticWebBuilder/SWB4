package org.semanticwb.process.base;


public class ProcessWebPageBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.PFlowRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Indexable,org.semanticwb.model.Resourceable,org.semanticwb.model.Rankable,org.semanticwb.model.Viewable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Undeleteable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Expirable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Filterable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.TemplateRefable
{
       public static final org.semanticwb.platform.SemanticClass swbps_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
       public static final org.semanticwb.platform.SemanticProperty swbps_process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#process");
       public static final org.semanticwb.platform.SemanticClass swbps_ProcessWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessWebPage");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessWebPage");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.ProcessWebPage> listProcessWebPages(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessWebPage>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.ProcessWebPage> listProcessWebPages()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessWebPage>(it, true);
       }

       public static org.semanticwb.process.ProcessWebPage getProcessWebPage(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ProcessWebPage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.ProcessWebPage createProcessWebPage(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ProcessWebPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeProcessWebPage(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasProcessWebPage(String id, org.semanticwb.model.SWBModel model)
       {
           return (getProcessWebPage(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.ProcessWebPage> listProcessWebPageByProcess(org.semanticwb.process.Process process,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbps_process, process.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.ProcessWebPage> listProcessWebPageByProcess(org.semanticwb.process.Process process)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.ProcessWebPage> it=new org.semanticwb.model.GenericIterator(process.getSemanticObject().getModel().listSubjects(swbps_process,process.getSemanticObject()));
       return it;
   }
    }

    public ProcessWebPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setProcess(org.semanticwb.process.Process value)
    {
        getSemanticObject().setObjectProperty(swbps_process, value.getSemanticObject());
    }

    public void removeProcess()
    {
        getSemanticObject().removeProperty(swbps_process);
    }


    public org.semanticwb.process.Process getProcess()
    {
         org.semanticwb.process.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbps_process);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.Process)obj.createGenericInstance();
         }
         return ret;
    }
}
