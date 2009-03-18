package org.semanticwb.resource.office.base;


public class ExcelResourceBase extends org.semanticwb.resource.office.OfficeResource implements org.semanticwb.model.Priorityable,org.semanticwb.model.Deleteable,org.semanticwb.model.Traceable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Campable,org.semanticwb.model.Calendarable,org.semanticwb.model.Localeable,org.semanticwb.model.Activeable,org.semanticwb.model.Hitable,org.semanticwb.model.XMLable,org.semanticwb.model.Indexable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Viewable,org.semanticwb.model.RoleRefable,org.semanticwb.model.XMLConfable,org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticClass swbrep_ExcelResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#ExcelResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#ExcelResource");

    public ExcelResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.resource.office.ExcelResource getExcelResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.ExcelResource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.resource.office.ExcelResource> listExcelResources(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resource.office.ExcelResource>(org.semanticwb.resource.office.ExcelResource.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.resource.office.ExcelResource> listExcelResources()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resource.office.ExcelResource>(org.semanticwb.resource.office.ExcelResource.class, it, true);
    }

    public static org.semanticwb.resource.office.ExcelResource createExcelResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.ExcelResource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeExcelResource(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasExcelResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (getExcelResource(id, model)!=null);
    }
}
