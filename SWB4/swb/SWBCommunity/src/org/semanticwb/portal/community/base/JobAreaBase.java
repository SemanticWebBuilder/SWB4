package org.semanticwb.portal.community.base;


public class JobAreaBase extends org.semanticwb.portal.community.MicroSite implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.Resourceable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Indexable,org.semanticwb.model.Viewable,org.semanticwb.model.Activeable,org.semanticwb.model.Trashable,org.semanticwb.model.Referensable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Expirable,org.semanticwb.model.Rankable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_JobArea=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#JobArea");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#JobArea");

    public JobAreaBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.JobArea> listJobAreas(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.JobArea>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.JobArea> listJobAreas()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.JobArea>(it, true);
    }

    public static org.semanticwb.portal.community.JobArea getJobArea(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.JobArea)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.JobArea createJobArea(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.JobArea)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeJobArea(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasJobArea(String id, org.semanticwb.model.SWBModel model)
    {
        return (getJobArea(id, model)!=null);
    }
}
