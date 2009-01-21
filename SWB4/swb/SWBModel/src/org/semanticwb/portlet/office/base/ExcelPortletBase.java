package org.semanticwb.portlet.office.base;


public class ExcelPortletBase extends org.semanticwb.portlet.office.OfficePortlet 
{
    public static final org.semanticwb.platform.SemanticClass swbrep_ExcelPortlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#ExcelPortlet");


    public static org.semanticwb.portlet.office.ExcelPortlet createExcelPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portlet.office.ExcelPortlet)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, swbrep_ExcelPortlet), swbrep_ExcelPortlet);
    }

    public ExcelPortletBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
