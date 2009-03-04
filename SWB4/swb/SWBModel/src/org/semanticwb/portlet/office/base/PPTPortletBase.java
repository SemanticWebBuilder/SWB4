package org.semanticwb.portlet.office.base;


public class PPTPortletBase extends org.semanticwb.portlet.office.OfficePortlet implements org.semanticwb.model.XMLConfable,org.semanticwb.model.Viewable,org.semanticwb.model.Referensable,org.semanticwb.model.Versionable,org.semanticwb.model.Priorityable,org.semanticwb.model.Hitable,org.semanticwb.model.Deleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RoleRefable,org.semanticwb.model.XMLable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Localeable,org.semanticwb.model.Activeable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Indexable,org.semanticwb.model.Calendarable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swbrep_PPTPortlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#PPTPortlet");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#PPTPortlet");

    public PPTPortletBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.portlet.office.PPTPortlet getPPTPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portlet.office.PPTPortlet)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.portlet.office.PPTPortlet> listPPTPortlets(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portlet.office.PPTPortlet>(org.semanticwb.portlet.office.PPTPortlet.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.portlet.office.PPTPortlet> listPPTPortlets()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portlet.office.PPTPortlet>(org.semanticwb.portlet.office.PPTPortlet.class, it, true);
    }

    public static org.semanticwb.portlet.office.PPTPortlet createPPTPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portlet.office.PPTPortlet)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removePPTPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPPTPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPPTPortlet(id, model)!=null);
    }
}
