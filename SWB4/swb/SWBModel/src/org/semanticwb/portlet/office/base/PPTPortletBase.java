package org.semanticwb.portlet.office.base;


public class PPTPortletBase extends org.semanticwb.portlet.office.OfficePortlet 
{
    public static final org.semanticwb.platform.SemanticClass swbrep_PPTPortlet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#PPTPortlet");


    public static org.semanticwb.portlet.office.PPTPortlet createPPTPortlet(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portlet.office.PPTPortlet)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, swbrep_PPTPortlet), swbrep_PPTPortlet);
    }

    public PPTPortletBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
