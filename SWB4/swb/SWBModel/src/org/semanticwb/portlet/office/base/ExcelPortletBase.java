package org.semanticwb.portlet.office.base;


public class ExcelPortletBase extends org.semanticwb.portlet.office.OfficePortlet implements org.semanticwb.model.XMLConfable,org.semanticwb.model.Hitable,org.semanticwb.model.Deleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RoleRefable,org.semanticwb.model.XMLable,org.semanticwb.model.Viewable,org.semanticwb.model.Priorityable,org.semanticwb.model.Versionable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Localeable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Calendarable,org.semanticwb.model.Indexable,org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticClass swbrep_ExcelPortlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#ExcelPortlet");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#ExcelPortlet");

    public ExcelPortletBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.portlet.office.ExcelPortlet getExcelPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portlet.office.ExcelPortlet)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.portlet.office.ExcelPortlet> listExcelPortlets(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portlet.office.ExcelPortlet>(org.semanticwb.portlet.office.ExcelPortlet.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.portlet.office.ExcelPortlet> listExcelPortlets()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portlet.office.ExcelPortlet>(org.semanticwb.portlet.office.ExcelPortlet.class, it, true);
    }

    public static org.semanticwb.portlet.office.ExcelPortlet createExcelPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portlet.office.ExcelPortlet)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeExcelPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasExcelPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (getExcelPortlet(id, model)!=null);
    }
}
