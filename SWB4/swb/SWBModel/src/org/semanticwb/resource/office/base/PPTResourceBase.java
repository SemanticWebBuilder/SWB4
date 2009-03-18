package org.semanticwb.resource.office.base;


public class PPTResourceBase extends org.semanticwb.resource.office.OfficeResource implements org.semanticwb.model.Priorityable,org.semanticwb.model.Deleteable,org.semanticwb.model.Traceable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Campable,org.semanticwb.model.Calendarable,org.semanticwb.model.Localeable,org.semanticwb.model.Activeable,org.semanticwb.model.Hitable,org.semanticwb.model.XMLable,org.semanticwb.model.Indexable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Viewable,org.semanticwb.model.RoleRefable,org.semanticwb.model.XMLConfable,org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticProperty swbrep_showDownload=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#showDownload");
    public static final org.semanticwb.platform.SemanticClass swbrep_PPTResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#PPTResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#PPTResource");

    public PPTResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.resource.office.PPTResource getPPTResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.PPTResource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.resource.office.PPTResource> listPPTResources(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resource.office.PPTResource>(org.semanticwb.resource.office.PPTResource.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.resource.office.PPTResource> listPPTResources()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resource.office.PPTResource>(org.semanticwb.resource.office.PPTResource.class, it, true);
    }

    public static org.semanticwb.resource.office.PPTResource createPPTResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.PPTResource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removePPTResource(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPPTResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPPTResource(id, model)!=null);
    }

    public boolean isShowDownload()
    {
        return getSemanticObject().getBooleanProperty(swbrep_showDownload);
    }

    public void setShowDownload(boolean showDownload)
    {
        getSemanticObject().setBooleanProperty(swbrep_showDownload, showDownload);
    }
}
