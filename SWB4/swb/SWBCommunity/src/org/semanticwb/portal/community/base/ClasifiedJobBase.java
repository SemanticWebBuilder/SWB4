package org.semanticwb.portal.community.base;


public class ClasifiedJobBase extends org.semanticwb.portal.community.Clasified implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Contactable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_JobArea=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#JobArea");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_jobArea=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#jobArea");
    public static final org.semanticwb.platform.SemanticClass swbcomm_ClasifiedJob=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedJob");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedJob");

    public ClasifiedJobBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobs(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobs()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob>(it, true);
    }

    public static org.semanticwb.portal.community.ClasifiedJob createClasifiedJob(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.ClasifiedJob.createClasifiedJob(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.ClasifiedJob getClasifiedJob(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.ClasifiedJob)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.ClasifiedJob createClasifiedJob(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.ClasifiedJob)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeClasifiedJob(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasClasifiedJob(String id, org.semanticwb.model.SWBModel model)
    {
        return (getClasifiedJob(id, model)!=null);
    }

    public void setJobArea(org.semanticwb.portal.community.JobArea value)
    {
        getSemanticObject().setObjectProperty(swbcomm_jobArea, value.getSemanticObject());
    }

    public void removeJobArea()
    {
        getSemanticObject().removeProperty(swbcomm_jobArea);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByJobArea(org.semanticwb.portal.community.JobArea jobarea,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_jobArea, jobarea.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByJobArea(org.semanticwb.portal.community.JobArea jobarea)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(jobarea.getSemanticObject().getModel().listSubjects(swbcomm_jobArea,jobarea.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.JobArea getJobArea()
    {
         org.semanticwb.portal.community.JobArea ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_jobArea);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.JobArea)obj.createGenericInstance();
         }
         return ret;
    }
}
