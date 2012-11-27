/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.community.base;


public abstract class ClasifiedJobBase extends org.semanticwb.portal.community.Clasified implements org.semanticwb.model.Searchable,org.semanticwb.model.Tagable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.Interactiveable,org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable,org.semanticwb.portal.community.Contactable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_maxRequiredAge=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#maxRequiredAge");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_experience=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#experience");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_salary=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#salary");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_jobType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#jobType");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_minRequiredAge=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#minRequiredAge");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_educationalLevel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#educationalLevel");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_acceptedDisability=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#acceptedDisability");
    public static final org.semanticwb.platform.SemanticClass swbcomm_JobArea=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#JobArea");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_jobArea=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#jobArea");
    public static final org.semanticwb.platform.SemanticClass swbcomm_ClasifiedJob=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedJob");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedJob");

    public static class ClassMgr
    {

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

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByDirectoryResource(org.semanticwb.portal.community.DirectoryResource directoryresource,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_directoryResource, directoryresource.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByDirectoryResource(org.semanticwb.portal.community.DirectoryResource directoryresource)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(directoryresource.getSemanticObject().getModel().listSubjects(swbcomm_directoryResource,directoryresource.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByProfile(org.semanticwb.model.WebPage hasdirprofilewebpage,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasDirProfileWebPage, hasdirprofilewebpage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByProfile(org.semanticwb.model.WebPage hasdirprofilewebpage)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(hasdirprofilewebpage.getSemanticObject().getModel().listSubjects(swbcomm_hasDirProfileWebPage,hasdirprofilewebpage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByWebPage(org.semanticwb.model.WebPage dirwebpage,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_dirWebPage, dirwebpage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByWebPage(org.semanticwb.model.WebPage dirwebpage)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(dirwebpage.getSemanticObject().getModel().listSubjects(swbcomm_dirWebPage,dirwebpage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByTopicWebPage(org.semanticwb.model.WebPage hasdirtopicwebpage,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasDirTopicWebPage, hasdirtopicwebpage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByTopicWebPage(org.semanticwb.model.WebPage hasdirtopicwebpage)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(hasdirtopicwebpage.getSemanticObject().getModel().listSubjects(swbcomm_hasDirTopicWebPage,hasdirtopicwebpage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByComment(org.semanticwb.portal.community.Comment hascomment,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasComment, hascomment.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedJob> listClasifiedJobByComment(org.semanticwb.portal.community.Comment hascomment)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedJob> it=new org.semanticwb.model.GenericIterator(hascomment.getSemanticObject().getModel().listSubjects(swbcomm_hasComment,hascomment.getSemanticObject()));
            return it;
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
    }

    public ClasifiedJobBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getMaxRequiredAge()
    {
        return getSemanticObject().getIntProperty(swbcomm_maxRequiredAge);
    }

    public void setMaxRequiredAge(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_maxRequiredAge, value);
    }

    public int getExperience()
    {
        return getSemanticObject().getIntProperty(swbcomm_experience);
    }

    public void setExperience(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_experience, value);
    }

    public float getSalary()
    {
        return getSemanticObject().getFloatProperty(swbcomm_salary);
    }

    public void setSalary(float value)
    {
        getSemanticObject().setFloatProperty(swbcomm_salary, value);
    }

    public int getJobType()
    {
        return getSemanticObject().getIntProperty(swbcomm_jobType);
    }

    public void setJobType(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_jobType, value);
    }

    public int getMinRequiredAge()
    {
        return getSemanticObject().getIntProperty(swbcomm_minRequiredAge);
    }

    public void setMinRequiredAge(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_minRequiredAge, value);
    }

    public int getEducationalLevel()
    {
        return getSemanticObject().getIntProperty(swbcomm_educationalLevel);
    }

    public void setEducationalLevel(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_educationalLevel, value);
    }

    public int getAcceptedDissability()
    {
        return getSemanticObject().getIntProperty(swbcomm_acceptedDisability);
    }

    public void setAcceptedDissability(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_acceptedDisability, value);
    }

    public void setJobArea(org.semanticwb.portal.community.JobArea value)
    {
        getSemanticObject().setObjectProperty(swbcomm_jobArea, value.getSemanticObject());
    }

    public void removeJobArea()
    {
        getSemanticObject().removeProperty(swbcomm_jobArea);
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
