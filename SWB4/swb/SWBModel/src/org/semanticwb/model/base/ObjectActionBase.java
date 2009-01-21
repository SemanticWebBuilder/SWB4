package org.semanticwb.model.base;


public class ObjectActionBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Iconable
{
    public static final org.semanticwb.platform.SemanticProperty swb_iconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
    public static final org.semanticwb.platform.SemanticProperty swbxf_actGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#actGroup");
    public static final org.semanticwb.platform.SemanticProperty swbxf_actionURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#actionURL");
    public static final org.semanticwb.platform.SemanticClass swbxf_ObjectAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectAction");


    public static org.semanticwb.model.ObjectAction createObjectAction(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.ObjectAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, swbxf_ObjectAction), swbxf_ObjectAction);
    }

    public ObjectActionBase(org.semanticwb.platform.SemanticObject base)
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

    public String getActGroup()
    {
        return getSemanticObject().getProperty(swbxf_actGroup);
    }

    public void setActGroup(String actGroup)
    {
        getSemanticObject().setProperty(swbxf_actGroup, actGroup);
    }

    public String getActionURL()
    {
        return getSemanticObject().getProperty(swbxf_actionURL);
    }

    public void setActionURL(String actionURL)
    {
        getSemanticObject().setProperty(swbxf_actionURL, actionURL);
    }
}
