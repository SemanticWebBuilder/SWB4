package org.semanticwb.model.base;


public class MenuItemBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Iconable
{
    public static final org.semanticwb.platform.SemanticProperty swb_iconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
    public static final org.semanticwb.platform.SemanticProperty swb_mnuItemShowIFrame=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#mnuItemShowIFrame");
    public static final org.semanticwb.platform.SemanticClass swbxf_MenuItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#MenuItem");


    public static org.semanticwb.model.MenuItem createMenuItem(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.MenuItem)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, swbxf_MenuItem), swbxf_MenuItem);
    }

    public MenuItemBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

    public void setIconClass(String iconClass)
    {
        getSemanticObject().setProperty(swb_iconClass, iconClass);
    }

    public String getShowAs()
    {
        return getSemanticObject().getProperty(swb_mnuItemShowIFrame);
    }

    public void setShowAs(String mnuItemShowIFrame)
    {
        getSemanticObject().setProperty(swb_mnuItemShowIFrame, mnuItemShowIFrame);
    }
}
