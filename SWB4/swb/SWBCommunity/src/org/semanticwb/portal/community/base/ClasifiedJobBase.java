package org.semanticwb.portal.community.base;


public class ClasifiedJobBase extends org.semanticwb.portal.community.Clasified implements org.semanticwb.model.Rankable,org.semanticwb.portal.community.Contactable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.portal.community.Interactiveable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swbcomm_contactPhoneNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactPhoneNumber");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_maxRequiredAge=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#maxRequiredAge");
       public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_contactName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactName");
       public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_abused=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abused");
       public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryResource");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_directoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#directoryResource");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDirProfileWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDirProfileWebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_experience=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#experience");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_salary=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#salary");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_jobType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#jobType");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirHasExtraPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirHasExtraPhoto");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_minRequiredAge=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#minRequiredAge");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_educationalLevel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#educationalLevel");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirWebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_acceptedDisability=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#acceptedDisability");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDirTopicWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDirTopicWebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_clasifiedOperationType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#clasifiedOperationType");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirPhoto");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_contactEmail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#contactEmail");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasComment");
       public static final org.semanticwb.platform.SemanticClass swbcomm_JobArea=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#JobArea");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_jobArea=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#jobArea");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbcomm_ClasifiedJob=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedJob");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedJob");

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
           return org.semanticwb.portal.community.ClasifiedJob.ClassMgr.createClasifiedJob(String.valueOf(id), model);
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
    }

    public ClasifiedJobBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getMaxRequiredAge()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_maxRequiredAge);
    }

    public void setMaxRequiredAge(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_maxRequiredAge, value);
    }

    public int getExperience()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_experience);
    }

    public void setExperience(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_experience, value);
    }

    public float getSalary()
    {
        return getSemanticObject().getFloatProperty(ClassMgr.swbcomm_salary);
    }

    public void setSalary(float value)
    {
        getSemanticObject().setFloatProperty(ClassMgr.swbcomm_salary, value);
    }

    public int getJobType()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_jobType);
    }

    public void setJobType(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_jobType, value);
    }

    public int getMinRequiredAge()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_minRequiredAge);
    }

    public void setMinRequiredAge(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_minRequiredAge, value);
    }

    public int getEducationalLevel()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_educationalLevel);
    }

    public void setEducationalLevel(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_educationalLevel, value);
    }

    public int getAcceptedDissability()
    {
        return getSemanticObject().getIntProperty(ClassMgr.swbcomm_acceptedDisability);
    }

    public void setAcceptedDissability(int value)
    {
        getSemanticObject().setIntProperty(ClassMgr.swbcomm_acceptedDisability, value);
    }

    public void setJobArea(org.semanticwb.portal.community.JobArea value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbcomm_jobArea, value.getSemanticObject());
    }

    public void removeJobArea()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_jobArea);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByJobArea(org.semanticwb.portal.community.JobArea jobarea,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_jobArea, jobarea.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByJobArea(org.semanticwb.portal.community.JobArea jobarea)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(jobarea.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_jobArea,jobarea.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.JobArea getJobArea()
    {
         org.semanticwb.portal.community.JobArea ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_jobArea);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.JobArea)obj.createGenericInstance();
         }
         return ret;
    }
}
